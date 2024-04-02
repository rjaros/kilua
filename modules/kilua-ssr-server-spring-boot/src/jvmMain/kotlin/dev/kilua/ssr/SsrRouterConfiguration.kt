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

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.coRouter

/**
 * SSR router configuration for Spring Boot.
 */
@Configuration
public open class SsrRouterConfiguration {
    /**
     * These routes have higher priority then the index route from Kilua-RPC library.
     */
    @Bean
    @Order(10)
    public open fun ssrRoutes(ssrHandler: SsrHandler): RouterFunction<ServerResponse> = coRouter {
        GET("/", ssrHandler::handleRoot)
        GET("/index.html", ssrHandler::handleRoot)
    }
}

/**
 * SSR handler for the root route.
 */
@Component
public open class SsrHandler(
    private val ssrEngine: SsrEngine
) {
    public open suspend fun handleRoot(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValueAndAwait(ssrEngine.getSsrContent("/"))
    }
}
