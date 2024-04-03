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

import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.StaticHandler
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*

private const val SSR_ENGINE_KEY = "dev.kilua.ssr.engine.key"
private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

/**
 * Initialization function for Kilua Server-Side Rendering.
 */
public fun Vertx.initSsr(router: Router) {
    val prop = Properties()
    prop.load(this::class.java.getResourceAsStream("/application.properties"))
    val nodeExecutable = prop.getProperty("ssr.nodeExecutable")
    val port = prop.getProperty("ssr.port")?.toIntOrNull()
    val externalSsrService = prop.getProperty("ssr.externalSsrService")
    val rpcUrlPrefix = prop.getProperty("ssr.rpcUrlPrefix")
    val rootId = prop.getProperty("ssr.rootId")
    val ssrEngine = SsrEngine(nodeExecutable, port, externalSsrService, rpcUrlPrefix, rootId)

    router.get("/").handler { ctx ->
        ctx.put(SSR_ENGINE_KEY, ssrEngine)
        applicationScope.launch(ctx.vertx().dispatcher()) {
            ctx.respondSsr()
        }
    }
    router.get("/index.html").handler { ctx ->
        ctx.put(SSR_ENGINE_KEY, ssrEngine)
        applicationScope.launch(ctx.vertx().dispatcher()) {
            ctx.respondSsr()
        }
    }
    router.route("/*").last().handler(StaticHandler.create())
    router.get().last().handler { ctx ->
        ctx.put(SSR_ENGINE_KEY, ssrEngine)
        applicationScope.launch(ctx.vertx().dispatcher()) {
            ctx.respondSsr()
        }
    }
}

private suspend fun RoutingContext.respondSsr() {
    if (request().path() == "/favicon.ico") {
        response().setStatusCode(404).end()
    } else {
        val uri = request().path() + (request().query()?.let { "?$it" } ?: "")
        val ssrEngine = get<SsrEngine>(SSR_ENGINE_KEY)
        response().putHeader("Content-Type", "text/html").end(ssrEngine.getSsrContent(uri))
    }
}
