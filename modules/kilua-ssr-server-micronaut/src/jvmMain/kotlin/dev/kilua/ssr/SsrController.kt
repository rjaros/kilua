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

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import jakarta.inject.Inject

/**
 * Server-Side Rendering controller.
 */
@Controller("/")
public open class SsrController {

    @Inject
    public lateinit var ssrEngine: SsrEngine

    @Produces(MediaType.TEXT_HTML)
    @Get("/")
    public suspend fun root(request: HttpRequest<*>): HttpResponse<String> =
        HttpResponse.ok(ssrEngine.getSsrContent(request.uri.toString(), request.headers.acceptLanguage()?.language))

    @Produces(MediaType.TEXT_HTML)
    @Get("/index.html")
    public suspend fun index(request: HttpRequest<*>): HttpResponse<String> =
        HttpResponse.ok(ssrEngine.getSsrContent(request.uri.toString(), request.headers.acceptLanguage()?.language))

}

/**
 * Server-Side Rendering controller for handling 404 fallback.
 */
@Controller("/notfound")
public open class SsrFallbackController {

    @Inject
    public lateinit var ssrEngine: SsrEngine

    @Produces(MediaType.TEXT_HTML)
    @Error(status = HttpStatus.NOT_FOUND, global = true)
    public suspend fun forward(request: HttpRequest<*>): HttpResponse<*> {
        return if (request.headers
                .accept()
                .stream()
                .anyMatch { mediaType: MediaType ->
                    mediaType.name.contains(MediaType.TEXT_HTML)
                }
        ) {
            HttpResponse.ok(ssrEngine.getSsrContent(request.uri.toString(), request.headers.acceptLanguage()?.language))
        } else {
            HttpResponse.notFound<String>()
        }
    }
}
