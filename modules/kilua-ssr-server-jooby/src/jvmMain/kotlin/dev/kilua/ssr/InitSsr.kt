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

import io.jooby.Context
import io.jooby.MediaType
import io.jooby.StatusCode
import io.jooby.exception.NotFoundException
import io.jooby.handler.AssetHandler
import io.jooby.handler.AssetSource
import io.jooby.kt.Kooby
import kotlinx.coroutines.runBlocking

/**
 * A key for storing the SSR engine in the Jooby context.
 * It can be used to retrieve the default SsrEngine class instance from the Jooby context.
 */
public const val SsrEngineKey: String = "dev.kilua.ssr.engine.key"

/**
 * Initialization function for Kilua Server-Side Rendering.
 */
public fun Kooby.initSsr() {
    val config = environment.config
    val nodeExecutable = config.getString("ssr.nodeExecutable")
    val port = if (config.hasPath("ssr.port")) config.getString("ssr.port")?.toIntOrNull() else null
    val externalSsrService =
        if (config.hasPath("ssr.externalSsrService")) config.getString("ssr.externalSsrService") else null
    val rpcUrlPrefix = if (config.hasPath("ssr.rpcUrlPrefix")) config.getString("ssr.rpcUrlPrefix") else null
    val rootId = if (config.hasPath("ssr.rootId")) config.getString("ssr.rootId") else null
    val contextPath = if (config.hasPath("ssr.contextPath")) config.getString("ssr.contextPath") else null
    val cacheTime = if (config.hasPath("ssr.cacheTime"))
        (config.getString("ssr.cacheTime")?.toIntOrNull() ?: DEFAULT_SSR_CACHE_TIME) else DEFAULT_SSR_CACHE_TIME
    val ssrEngine = SsrEngine(nodeExecutable, port, externalSsrService, rpcUrlPrefix, rootId, contextPath, cacheTime)
    before { ctx ->
        ctx.setAttribute(SsrEngineKey, ssrEngine)
    }
    use {
        val response = next.apply(ctx)
        if (ctx.requestPath.endsWith(".wasm")) {
            ctx.responseType = MediaType.valueOf("application/wasm")
        }
        response
    }
    coroutine {
        get("/") {
            ctx.respondSsr()
        }
        get("/index.html") {
            ctx.respondSsr()
        }
    }
    val assets = AssetSource.create(javaClass.classLoader, "assets")
    assets("/?*", AssetHandler(assets, {
        throw NotFoundException(it)
    }))
    error(NotFoundException::class.java) { ctx, _, _ ->
        runBlocking {
            ctx.respondSsr()
        }
    }
}

private suspend fun Context.respondSsr() {
    if (requestPath == "/favicon.ico") {
        send(StatusCode.NOT_FOUND)
    } else {
        val uri = requestPath + queryString()
        val language = header("Accept-Language").valueOrNull()?.split(",")?.firstOrNull()?.split(";")?.firstOrNull()
        val ssrEngine = getAttribute<SsrEngine>(SsrEngineKey)!!
        responseType = MediaType.html
        responseCode = StatusCode.OK
        send(ssrEngine.getSsrContent(uri, language))
    }
}
