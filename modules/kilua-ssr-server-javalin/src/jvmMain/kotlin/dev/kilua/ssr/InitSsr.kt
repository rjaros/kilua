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

import io.javalin.Javalin
import io.javalin.config.Key
import io.javalin.http.Context
import io.javalin.http.HttpStatus
import io.javalin.http.staticfiles.Location
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.future.future
import java.util.*

private val ssrEngineKey = Key<SsrEngine>("ssrEngine")
private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

/**
 * Initialization function for Kilua Server-Side Rendering.
 */
public fun Javalin.initSsr() {
    val prop = Properties()
    prop.load(this::class.java.getResourceAsStream("/application.properties"))
    val nodeExecutable = prop.getProperty("ssr.nodeExecutable")
    val port = prop.getProperty("ssr.port")?.toIntOrNull()
    val externalSsrService = prop.getProperty("ssr.externalSsrService")
    val rpcUrlPrefix = prop.getProperty("ssr.rpcUrlPrefix")
    val rootId = prop.getProperty("ssr.rootId")
    val ssrEngine = SsrEngine(nodeExecutable, port, externalSsrService, rpcUrlPrefix, rootId)
    with(unsafeConfig()) {
        appData(ssrEngineKey, ssrEngine)
        get("/") {
            it.respondSsr()
        }
        get("/index.html") {
            it.respondSsr()
        }
        staticFiles.add("/assets", Location.CLASSPATH)
        spaRoot.addHandler("/") { ctx ->
            ctx.respondSsr()
        }
    }
}

private fun Context.respondSsr() {
    if (path() == "/favicon.ico") {
        status(HttpStatus.NOT_FOUND)
    } else {
        val uri = path() + (queryString()?.let { "?$it" } ?: "")
        val language = header("Accept-Language")?.split(",")?.firstOrNull()?.split(";")?.firstOrNull()
        val ssrEngine = appData(ssrEngineKey)
        val future = applicationScope.future {
            ssrEngine.getSsrContent(uri, language)
        }
        future { future.thenAccept { html(it) } }
    }
}
