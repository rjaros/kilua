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
import androidx.compose.runtime.LaunchedEffect
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
import app.softwork.routingcompose.route
import dev.kilua.CssRegister
import dev.kilua.KiluaScope
import dev.kilua.core.ComponentBase
import dev.kilua.core.IComponent
import dev.kilua.externals.get
import dev.kilua.externals.globalThis
import dev.kilua.externals.set
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager
import dev.kilua.i18n.SimpleLocale
import dev.kilua.utils.cast
import dev.kilua.utils.isDom
import dev.kilua.utils.nativeMapOf
import dev.kilua.utils.unsafeCast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import dev.kilua.dom.JsAny
import dev.kilua.dom.toJsString

/**
 * A router supporting Server-Side Rendering (SSR).
 * Uses a [BrowserRouter] when running in the browser and a custom router when running on the server.
 */
@Composable
public fun IComponent.SsrRouter(
    initPath: String,
    ssrCondition: Boolean = true,
    stateSerializer: (() -> String)? = null,
    routeBuilder: @Composable RouteBuilder.() -> Unit
) {
    SsrRouter(initPath, ssrCondition, stateSerializer, routeBuilder, null)
}

/**
 * A router supporting Server-Side Rendering (SSR) with manual "done" callback.
 * Uses a [BrowserRouter] when running in the browser and a custom router when running on the server.
 */
@Composable
public fun IComponent.SsrRouter(
    initPath: String,
    ssrCondition: Boolean = true,
    stateSerializer: (() -> String)? = null,
    routeBuilder: @Composable RouteBuilder.(done: () -> Unit) -> Unit
) {
    SsrRouter(initPath, ssrCondition, stateSerializer, null, routeBuilder)
}

@Composable
private fun IComponent.SsrRouter(
    initPath: String,
    ssrCondition: Boolean = true,
    stateSerializer: (() -> String)? = null,
    routeBuilder: @Composable (RouteBuilder.() -> Unit)?,
    routeBuilderWithCallback: @Composable (RouteBuilder.(done: () -> Unit) -> Unit)?
) {
    if (renderConfig.isDom) {
        if (routeBuilder != null) {
            BrowserRouter(initPath, routeBuilder)
        } else if (routeBuilderWithCallback != null) {
            BrowserRouter(initPath) { routeBuilderWithCallback {} }
        }
    } else {
        val router = remember {
            SsrRouter(initPath, this.cast(), stateSerializer).also { Router.internalGlobalRouter = it }
        }

        var externalCondition by remember {
            val state = mutableStateOf(false)
            LocaleManager.registerSsrLocaleListener {
                state.value = true
            }
            state
        }

        router.route(initPath) {
            if (routeBuilder != null) {
                routeBuilder()
            } else if (routeBuilderWithCallback != null) {
                routeBuilderWithCallback {
                    externalCondition = true
                }
            }
            @Suppress("UNUSED_EXPRESSION")
            externalCondition // access value to trigger recomposition on locale change
            if (ssrCondition && (routeBuilderWithCallback == null || externalCondition)) {
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

/**
 * A Server-Side Rendering (SSR) router. It spins off a Node.js http server
 * listening to server-side URL requests on a default port 7788
 * (or chosen with a --port parameter).
 *
 * Every http request is passed as a routing command to the Kilua application and triggers
 * an appropriate recomposition. The result of the recomposition (rendered to String) is returned
 * as a http response.
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
                globalThis["rpc_url_prefix"] = rpcUrlPrefix.toJsString()
            }
            val contextPath = getCommandLineParameter("--context-path")
            if (contextPath != null) {
                globalThis["ssr_context_path"] = contextPath.toJsString()
            }
            val port = getCommandLineParameter("--port")?.toIntOrNull()
            startSsr(port ?: 7788)
        }
    }

    override val currentPath: Path
        get() = Path.from(currentLocation.value)

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
            if (req.method == "GET") {
                KiluaScope.launch {
                    res.statusCode = 200
                    res.setHeader("Content-Type", "text/plain")
                    while (lock) {
                        delay(1)
                    }
                    lock = true
                    val clientLanguage =
                        req.headers["x-kilua-locale"]?.toString() ?: LocaleManager.defaultLocale.language
                    val localeChanged = LocaleManager.currentLocale.language != clientLanguage
                    val pathChanged = currentPath.toString() != req.url
                    if (localeChanged || pathChanged) {
                        sendRender = {
                            res.end(it)
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
                        res.end(getRenderingResult())
                        lock = false
                    }
                }
            } else if (req.method == "POST") {
                res.statusCode = 200
                res.setHeader("Content-Type", "text/plain")
                res.end(CssRegister.cssFiles.joinToString("\n") { it })
            } else {
                res.statusCode = 404
                res.end("Not Found")
            }
        }.listen(port)
    }
}

private val RouteBuilder.pathKey: String
    get() = path + (parameters?.raw?.let { "?$it" } ?: "")

private val RouteBuilder.NoMatch.pathKey: String
    get() = remainingPath + (parameters?.raw?.let { "?$it" } ?: "")

/**
 * LaunchedEffect wrapper prepared to catch and execute effect for all not matched routes.
 * Use to make sure all routes are processed with SSR engine.
 */
@Composable
public fun RouteBuilder.SsrRouteEffect(key: String? = null, block: suspend CoroutineScope.() -> Unit) {
    LaunchedEffect(key?.let { "$it$pathKey" } ?: pathKey) {
        block()
    }
}

/**
 * LaunchedEffect wrapper prepared to catch and execute effect for all not matched routes.
 * Use to make sure all routes are processed with SSR engine.
 */
@Composable
public fun RouteBuilder.NoMatch.SsrRouteEffect(key: String? = null, block: suspend CoroutineScope.() -> Unit) {
    LaunchedEffect(key?.let { "$it$pathKey" } ?: pathKey) {
        block()
    }
}

/**
 * Get the context path configured for the SSR engine.
 */
public fun getContextPath(): String {
    return (if (isDom) globalThis["ssr_context_path"]?.toString() else getCommandLineParameter("--context-path")) ?: ""
}
