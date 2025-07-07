/*
 * Copyright (c) 2024 Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.kilua.ssr

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.softwork.routingcompose.BrowserRouter
import app.softwork.routingcompose.Path
import app.softwork.routingcompose.RouteBuilder
import app.softwork.routingcompose.Router
import app.softwork.routingcompose.invoke
import dev.kilua.CssRegister
import dev.kilua.core.ComponentBase
import dev.kilua.core.IComponent
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager
import dev.kilua.i18n.SimpleLocale
import dev.kilua.routing.DoneCallbackCompositionLocal
import dev.kilua.routing.Meta
import dev.kilua.routing.MetaImpl
import dev.kilua.routing.globalRouter
import dev.kilua.utils.KiluaScope
import dev.kilua.utils.cast
import dev.kilua.utils.isDom
import dev.kilua.utils.jsGet
import dev.kilua.utils.jsSet
import dev.kilua.utils.nativeMapOf
import dev.kilua.utils.unsafeCast
import js.core.JsAny
import js.core.JsPrimitives.toJsString
import js.globals.globalThis
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A router supporting Server-Side Rendering (SSR).
 * Uses a [BrowserRouter] when running in the browser and a custom router when running on the server.
 */
@Composable
internal fun IComponent.GlobalSsrRouter(
    initPath: String,
    active: Boolean = true,
    stateSerializer: (() -> String)? = null,
    useDoneCallback: Boolean = false,
    routeBuilder: @Composable (RouteBuilder.() -> Unit),
) {
    if (renderConfig.isDom) {
        CompositionLocalProvider(DoneCallbackCompositionLocal provides {}) {
            BrowserRouter(initPath, routeBuilder)
            globalRouter = BrowserRouter
        }
    } else {
        val router = remember {
            SsrRouter(initPath, this.cast(), stateSerializer).also { globalRouter = it }
        }

        var externalCondition by remember {
            val state = mutableStateOf(false)
            LocaleManager.registerSsrLocaleListener {
                state.value = true
            }
            state
        }

        if (active) {
            CompositionLocalProvider(DoneCallbackCompositionLocal provides {
                externalCondition = true
            }) {
                router(initPath) {
                    routeBuilder()
                    @Suppress("UNUSED_EXPRESSION")
                    externalCondition // access value to trigger recomposition on locale change
                    if (!useDoneCallback || externalCondition) {
                        SideEffect {
                            router.sendRender?.let {
                                it(router.getRenderingResult())
                                router.sendRender = null
                                router.lock = false
                            }
                            externalCondition = false
                        }
                    }
                }
            }
        }
    }
}

/**
 * A Server-Side Rendering (SSR) router. It spins off a Node.js http server
 * listening to server-side URL requests on a default port 7788
 * (or chosen with a --port parameter).
 *
 * Every http request is passed as a routing command to the Kilua application and triggers
 * an appropriate recomposition. The result of the recomposition (rendered to String) is returned
 * as an http response.
 */
internal class SsrRouter(
    initPath: String,
    val root: ComponentBase,
    val stateSerializer: (() -> String)? = null
) : Router {
    internal var lock: Boolean = false
    internal var sendRender: ((String) -> Unit)? = null

    private var ssrLocaleCache = nativeMapOf<Locale>()

    init {
        if (!root.renderConfig.isDom) {
            val rpcUrlPrefix = getCommandLineParameter("--rpc-url-prefix")
            if (rpcUrlPrefix != null) {
                globalThis.jsSet("rpc_url_prefix", rpcUrlPrefix.toJsString())
            }
            val contextPath = getCommandLineParameter("--context-path")
            if (contextPath != null) {
                globalThis.jsSet("ssr_context_path", contextPath.toJsString())
            }
            val port = getCommandLineParameter("--port")?.toIntOrNull()
            startSsr(port ?: 7788)
        }
    }

    override fun currentPath(): Path = Path.from(currentLocation.value)

    private val currentLocation: MutableState<String> = mutableStateOf(initPath)

    @Composable
    override fun getPath(initPath: String): State<String> {
        return currentLocation
    }

    override fun navigate(to: String, hide: Boolean, replace: Boolean) {
        currentLocation.value = to
    }

    fun getRenderingResult(): String {
        val html = root.innerHTML
        val state = stateSerializer?.invoke()
        return if (state != null) {
            """
            <script>
            window.KILUA_SSR_STATE = "${compressToEncodedURIComponent(state)}";
            </script>
            $html
            """.trimIndent()
        } else {
            html
        }
    }

    private fun startSsr(port: Int) {
        http.createServer { rq: JsAny, rs: JsAny ->
            val req = rq.unsafeCast<IncomingMessage>()
            val res = rs.unsafeCast<ServerResponse>()
            if (req.method == "GET" || req.method == "PUT") {
                KiluaScope.launch {
                    res.statusCode = 200
                    res.setHeader("Content-Type", "text/plain")
                    while (lock) {
                        delay(1)
                    }
                    lock = true
                    val clientLanguage =
                        req.headers.jsGet("x-kilua-locale")?.toString() ?: LocaleManager.defaultLocale.language
                    val localeChanged = LocaleManager.currentLocale.language != clientLanguage
                    val pathChanged = currentPath().toString() != req.url
                    if (localeChanged || pathChanged) {
                        sendRender = {
                            if (req.method == "GET") {
                                res.end(it)
                            } else {
                                res.end(Meta.current?.cast<MetaImpl>()?.toJson() ?: "")
                            }
                        }
                        if (localeChanged) {
                            val newLocale = ssrLocaleCache.getOrPut(clientLanguage) {
                                if (clientLanguage == LocaleManager.defaultLocale.language) {
                                    LocaleManager.defaultLocale
                                } else {
                                    SimpleLocale(clientLanguage)
                                }
                            }
                            LocaleManager.setCurrentLocale(newLocale, skipSsr = pathChanged)
                        }
                        if (pathChanged) {
                            navigate(req.url)
                        }
                    } else {
                        if (req.method == "GET") {
                            res.end(getRenderingResult())
                        } else {
                            res.end(Meta.current?.cast<MetaImpl>()?.toJson() ?: "{}")
                        }
                        lock = false
                    }
                }
            } else if (req.method == "POST") {
                res.statusCode = 200
                res.setHeader("Content-Type", "text/plain")
                res.end(CssRegister.cssFiles.joinToString("\n") { it })
            } else if (req.method == "DELETE") {
                process?.exit(1)
            } else {
                res.statusCode = 404
                res.end("Not Found")
            }
        }.listen(port)
    }
}

/**
 * Get the context path configured for the SSR engine.
 */
public fun getContextPath(): String {
    return (if (isDom) globalThis.jsGet("ssr_context_path")?.toString() else getCommandLineParameter("--context-path"))
        ?: ""
}
