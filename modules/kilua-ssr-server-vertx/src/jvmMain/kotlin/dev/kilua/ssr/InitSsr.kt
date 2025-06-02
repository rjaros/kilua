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

/**
 * A key for storing the default SSR engine in the Vert.x routing context.
 * It can be used to retrieve the default SsrEngine class instance from the Vert.x context.
 */
public const val SsrEngineKey: String = "dev.kilua.ssr.engine.key"

private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

/**
 * Initialization function for Kilua Server-Side Rendering.
 */
public fun Vertx.initSsr(router: Router) {
    val prop = Properties()
    val stream = this::class.java.getResourceAsStream("/application.properties")
    if (stream != null) {
        prop.load(stream)
    }
    val nodeExecutable = prop.getProperty("ssr.nodeExecutable")
    val port = prop.getProperty("ssr.port")?.toIntOrNull()
    val externalSsrService = prop.getProperty("ssr.externalSsrService")
    val rpcUrlPrefix = prop.getProperty("ssr.rpcUrlPrefix")
    val rootId = prop.getProperty("ssr.rootId")
    val contextPath = prop.getProperty("ssr.contextPath")
    val cacheTime = prop.getProperty("ssr.cacheTime")?.toIntOrNull() ?: DEFAULT_SSR_CACHE_TIME
    val ssrEngine = SsrEngine(nodeExecutable, port, externalSsrService, rpcUrlPrefix, rootId, contextPath, cacheTime)

    router.get("/").handler { ctx ->
        ctx.put(SsrEngineKey, ssrEngine)
        applicationScope.launch(ctx.vertx().dispatcher()) {
            ctx.respondSsr()
        }
    }
    router.get("/index.html").handler { ctx ->
        ctx.put(SsrEngineKey, ssrEngine)
        applicationScope.launch(ctx.vertx().dispatcher()) {
            ctx.respondSsr()
        }
    }
    router.route("/*").last().handler(StaticHandler.create())
    router.get().last().handler { ctx ->
        ctx.put(SsrEngineKey, ssrEngine)
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
        val language = request().getHeader("Accept-Language")?.split(",")?.firstOrNull()?.split(";")?.firstOrNull()
        val ssrEngine = get<SsrEngine>(SsrEngineKey)
        response().putHeader("Content-Type", "text/html").end(ssrEngine.getSsrContent(uri, language))
    }
}
