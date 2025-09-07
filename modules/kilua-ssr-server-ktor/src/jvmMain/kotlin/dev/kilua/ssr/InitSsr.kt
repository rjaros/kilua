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

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.hooks.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import java.util.*

/**
 * A key for storing the SsrEngine in the Ktor application attributes.
 * It can be used to retrieve the default SsrEngine class instance from the Ktor application attributes.
 */
public val SsrEngineKey: AttributeKey<SsrEngine> = AttributeKey("ssrEngine")

/**
 * Initialization function for Kilua Server-Side Rendering.
 */
public fun Application.initSsr() {
    val nodeExecutable = environment.config.propertyOrNull("ssr.nodeExecutable")?.getString()
    val port = environment.config.propertyOrNull("ssr.port")?.getString()?.toIntOrNull()
    val externalSsrService = environment.config.propertyOrNull("ssr.externalSsrService")?.getString()
    val rpcUrlPrefix = environment.config.propertyOrNull("ssr.rpcUrlPrefix")?.getString()
    val rootId = environment.config.propertyOrNull("ssr.rootId")?.getString()
    val contextPath = environment.config.propertyOrNull("ssr.contextPath")?.getString()
    val cacheTime =
        environment.config.propertyOrNull("ssr.cacheTime")?.getString()?.toIntOrNull() ?: DEFAULT_SSR_CACHE_TIME
    val sitemap = environment.config.propertyOrNull("ssr.sitemap")?.getString()?.toBooleanStrictOrNull() ?: true
    val ssrEngine = SsrEngine(nodeExecutable, port, externalSsrService, rpcUrlPrefix, rootId, contextPath, cacheTime)
    attributes.put(SsrEngineKey, ssrEngine)
    install(SsrPlugin)
    routing {
        get("/index.html") {
            call.respondSsr()
        }
        if (sitemap) {
            get("/sitemap.xml") {
                call.respondSitemap()
            }
        }
        singlePageApplication {
            defaultPage = UUID.randomUUID().toString() // Non-existing resource
            filesPath = "/assets"
            useResources = true
        }
    }
}

private val SsrPlugin: ApplicationPlugin<Unit> = createApplicationPlugin(name = "KiluaSsrPlugin") {
    on(ResponseBodyReadyForSend) { call, content ->
        if (content.status == HttpStatusCode.NotFound && call.request.path() != "/favicon.ico") {
            call.respondSsr()
        }
    }
}

private suspend fun ApplicationCall.respondSsr() {
    val ssrEngine = application.attributes[SsrEngineKey]
    respondText(ContentType.Text.Html, HttpStatusCode.OK) {
        val language = request.acceptLanguageItems().firstOrNull()?.value
        ssrEngine.getSsrContent(request.uri, language)
    }
}

private suspend fun ApplicationCall.respondSitemap() {
    val ssrEngine = application.attributes[SsrEngineKey]
    respondText(ContentType.Text.Xml, HttpStatusCode.OK) {
        val portPart =
            if (request.origin.scheme == "http" && request.port() == 80 || request.origin.scheme == "https" && request.port() == 443) {
                ""
            } else {
                ":${request.port()}"
            }
        val baseUrl = "${request.origin.scheme}://${request.host()}$portPart"
        ssrEngine.getSitemapContent(baseUrl)
    }
}
