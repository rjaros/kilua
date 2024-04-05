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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import app.softwork.routingcompose.BrowserRouter
import app.softwork.routingcompose.Path
import app.softwork.routingcompose.RouteBuilder
import app.softwork.routingcompose.Router
import app.softwork.routingcompose.route
import dev.kilua.CssRegister
import dev.kilua.KiluaScope
import dev.kilua.core.ComponentBase
import dev.kilua.externals.globalThis
import dev.kilua.externals.set
import dev.kilua.utils.unsafeCast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import web.JsAny
import web.toJsString

/**
 * A router supporting Server-Side Rendering (SSR).
 * Uses a [BrowserRouter] when running in the browser and a custom router when running on the server.
 */
@Composable
public fun ComponentBase.SsrRouter(
    initPath: String,
    routeBuilder: @Composable RouteBuilder.() -> Unit
) {
    if (renderConfig.isDom) {
        BrowserRouter(initPath, routeBuilder)
    } else {
        val router = SsrRouter(initPath, this)
        router.route(initPath) {
            routeBuilder()
            SideEffect {
                router.sendRender?.let {
                    it(this@SsrRouter.innerHTML)
                    router.sendRender = null
                    router.lock = false
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
internal class SsrRouter(initPath: String, val root: ComponentBase) : Router {
    internal var lock: Boolean = false
    internal var sendRender: ((String) -> Unit)? = null

    init {
        if (!root.renderConfig.isDom) {
            val rpcUrlPrefix = getCommandLineParameter("--rpc-url-prefix")
            if (rpcUrlPrefix != null) {
                globalThis["rpc_url_prefix"] = rpcUrlPrefix.toJsString()
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

    override fun navigate(to: String, hide: Boolean) {
        currentLocation.value = to
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
                    if (currentPath.toString() != req.url) {
                        sendRender = {
                            res.end(it)
                        }
                        navigate(req.url)
                    } else {
                        res.end(root.innerHTML)
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
        }.listen(port) {}
    }
}
