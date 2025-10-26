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

import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.web.server.autoconfigure.ServerProperties
import org.springframework.boot.webflux.autoconfigure.error.DefaultErrorWebExceptionHandler
import org.springframework.boot.webflux.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.result.view.ViewResolver
import reactor.core.publisher.Mono
import java.util.stream.Collectors

/**
 * 404 error handler for SSR and history api routing.
 */
@Component
@Order(-2)
public open class SsrErrorWebExceptionHandler(
    viewResolvers: ObjectProvider<ViewResolver>,
    serverCodecConfigurer: ServerCodecConfigurer,
    errorAttributes: ErrorAttributes,
    resources: WebProperties,
    serverProperties: ServerProperties,
    applicationContext: ApplicationContext,
    private val ssrEngine: SsrEngine
) :
    DefaultErrorWebExceptionHandler(errorAttributes, resources.resources, serverProperties.error, applicationContext) {
    init {
        super.setMessageWriters(serverCodecConfigurer.writers)
        super.setMessageReaders(serverCodecConfigurer.readers)
        super.setViewResolvers(viewResolvers.orderedStream().collect(Collectors.toList()))
    }

    override fun renderErrorView(request: ServerRequest): Mono<ServerResponse> {
        val error = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML))
        val errorStatus = getHttpStatus(error)
        return if (errorStatus == HttpStatus.NOT_FOUND.value()) {
            mono {
                val uri = request.uriBuilder().scheme(null).host(null).port(null).build().toString()
                val language = request.headers().acceptLanguage().firstOrNull()?.range
                ssrEngine.getSsrContent(uri, language)
            }.flatMap { content ->
                ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(content)
            }
        } else {
            super.renderErrorView(request)
        }
    }
}
