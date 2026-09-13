/*
 * Copyright (c) 2026 Robert Jaros
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

import io.quarkus.runtime.StartupEvent
import io.vertx.core.http.HttpHeaders
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.slf4j.LoggerFactory

/**
 * Quarkus CDI bean registering Kilua Server-Side Rendering routes on the Vert.x Router
 * at application startup.
 */
@ApplicationScoped
public open class SsrModules {

    private val logger = LoggerFactory.getLogger(SsrModules::class.java)

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    public lateinit var ssrEngine: SsrEngine

    @Inject
    @ConfigProperty(name = "ssr.sitemap", defaultValue = "true")
    public var sitemap: Boolean? = null

    /**
     * Registers SSR routes on the Vert.x Router at application startup.
     */
    public fun registerRoutes(@Observes event: StartupEvent, router: Router) {
        val ssrOrder = -200
        router.get("/").order(ssrOrder).handler { ctx -> handleSsr(ctx) }
        router.get("/index.html").order(ssrOrder).handler { ctx -> handleSsr(ctx) }
        if (sitemap != false) {
            router.get("/sitemap.xml").order(ssrOrder).handler { ctx -> handleSitemap(ctx) }
        }
        router.route("/*").order(-100).handler { ctx -> handleFallback(ctx) }
    }

    private fun handleSsr(ctx: RoutingContext) {
        val uri = ctx.request().uri()
        val language = ctx.request().getHeader("Accept-Language")?.split(",")?.firstOrNull()?.split(";")?.firstOrNull()
        applicationScope.launch(ctx.vertx().dispatcher()) {
            try {
                val content = ssrEngine.getSsrContent(uri, language)
                ctx.response()
                    .putHeader(HttpHeaders.CONTENT_TYPE, "text/html;charset=UTF-8")
                    .setStatusCode(200)
                    .end(content)
            } catch (e: Exception) {
                logger.error("SSR rendering error", e)
                ctx.response().setStatusCode(500).end()
            }
        }
    }

    private fun handleSitemap(ctx: RoutingContext) {
        val baseUrl = "${ctx.request().scheme()}://${ctx.request().authority()?.host()}"
        applicationScope.launch(ctx.vertx().dispatcher()) {
            try {
                val content = ssrEngine.getSitemapContent(baseUrl)
                ctx.response()
                    .putHeader(HttpHeaders.CONTENT_TYPE, "text/xml;charset=UTF-8")
                    .setStatusCode(200)
                    .end(content)
            } catch (e: Exception) {
                logger.error("Sitemap generation error", e)
                ctx.response().setStatusCode(404).end()
            }
        }
    }

    private fun handleFallback(ctx: RoutingContext) {
        val path = ctx.normalizedPath()
        val accept = ctx.request().getHeader("Accept") ?: ""
        if (path != "/favicon.ico" && accept.contains("text/html")) {
            handleSsr(ctx)
        } else {
            ctx.next()
        }
    }
}
