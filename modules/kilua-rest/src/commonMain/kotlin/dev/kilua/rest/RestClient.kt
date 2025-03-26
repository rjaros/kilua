/*
 * Copyright (c) 2023 Robert Jaros
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

package dev.kilua.rest

import dev.kilua.externals.JSON
import dev.kilua.externals.console
import dev.kilua.externals.delete
import dev.kilua.utils.jsGet
import dev.kilua.externals.keys
import dev.kilua.utils.jsSet
import dev.kilua.utils.Serialization
import dev.kilua.utils.cast
import dev.kilua.utils.unsafeCast
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import kotlinx.serialization.serializer
import js.core.JsAny
import js.core.JsString
import js.objects.ReadonlyRecord
import js.objects.jso
import js.promise.catch
import web.http.BodyInit
import web.http.Headers
import web.http.RequestInit
import web.http.RequestMethod
import web.http.Response
import web.http.fetchAsync
import web.url.URLSearchParams
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


/**
 * HTTP methods.
 */
public enum class HttpMethod {
    Get,
    Post,
    Put,
    Delete,
    Options,
    Head
}

/**
 * HTTP response body types.
 */
public enum class ResponseBodyType {
    Json,
    Text,
    Blob,
    FormData,
    ArrayBuffer,
    ReadableStream
}

/**
 * A response wrapper
 */
public data class RestResponse<T>(val data: T?, val textStatus: String, val response: Response)

public const val XHR_ERROR: Short = 0
public const val HTTP_NO_CONTENT: Short = 204
public const val HTTP_BAD_REQUEST: Short = 400
public const val HTTP_UNAUTHORIZED: Short = 401
public const val HTTP_FORBIDDEN: Short = 403
public const val HTTP_NOT_FOUND: Short = 404
public const val HTTP_NOT_ALLOWED: Short = 405
public const val HTTP_SERVER_ERROR: Short = 500
public const val HTTP_NOT_IMPLEMENTED: Short = 501
public const val HTTP_BAD_GATEWAY: Short = 502
public const val HTTP_SERVICE_UNAVAILABLE: Short = 503

public open class RemoteRequestException(
    public val code: Short,
    public val url: String,
    public val method: HttpMethod,
    message: String,
    public val response: Response? = null,
) :
    Exception(message) {

    override fun toString(): String = "${this::class.simpleName}($code) [${method.name} $url] $message"

    public companion object {
        public fun create(
            code: Short,
            url: String,
            method: HttpMethod,
            message: String,
            response: Response? = null
        ): RemoteRequestException =
            when (code) {
                XHR_ERROR -> XHRError(url, method, message, response)
                HTTP_BAD_REQUEST -> BadRequest(url, method, message, response)
                HTTP_UNAUTHORIZED -> Unauthorized(url, method, message, response)
                HTTP_FORBIDDEN -> Forbidden(url, method, message, response)
                HTTP_NOT_FOUND -> NotFound(url, method, message, response)
                HTTP_NOT_ALLOWED -> NotAllowed(url, method, message, response)
                HTTP_SERVER_ERROR -> ServerError(url, method, message, response)
                HTTP_NOT_IMPLEMENTED -> NotImplemented(url, method, message, response)
                HTTP_BAD_GATEWAY -> BadGateway(url, method, message, response)
                HTTP_SERVICE_UNAVAILABLE -> ServiceUnavailable(url, method, message, response)
                else -> RemoteRequestException(code, url, method, message, response)
            }
    }
}

/**
 * Code 0 does not represent any http status, it represent XHR error (e.g. network error, CORS failure).
 */
public class XHRError(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(XHR_ERROR, url, method, message, response)

public class BadRequest(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_BAD_REQUEST, url, method, message, response)

public class Unauthorized(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_UNAUTHORIZED, url, method, message, response)

public class Forbidden(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_FORBIDDEN, url, method, message, response)

public class NotFound(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_NOT_FOUND, url, method, message, response)

public class NotAllowed(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_NOT_ALLOWED, url, method, message, response)

public class ServerError(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_SERVER_ERROR, url, method, message, response)

public class NotImplemented(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_NOT_IMPLEMENTED, url, method, message, response)

public class BadGateway(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_BAD_GATEWAY, url, method, message, response)

public class ServiceUnavailable(url: String, method: HttpMethod, message: String, response: Response? = null) :
    RemoteRequestException(HTTP_SERVICE_UNAVAILABLE, url, method, message, response)

/**
 * REST Client configuration
 */
public class RestClientConfig {
    /**
     * Optional serialization module.
     */
    public var serializersModule: SerializersModule? = null

    /**
     * A function returning a list of HTTP headers.
     */
    public var headers: (() -> List<Pair<String, String>>)? = null

    /**
     * A request filtering function.
     */
    public var requestFilter: (RequestInit.() -> Unit)? = null

    /**
     * Base URL address.
     */
    public var baseUrl: String? = null
}

/**
 * REST request configuration
 */
public class RestRequestConfig<T : Any, V : Any> {
    /**
     * Data to send.
     */
    public var data: V? = null

    /**
     * An HTTP method.
     */
    public var method: HttpMethod = HttpMethod.Get

    /**
     * Request content type.
     */
    public var contentType: String? = "application/json"

    /**
     * Response body type.
     */
    public var responseBodyType: ResponseBodyType = ResponseBodyType.Json

    /**
     * A function returning a list of HTTP headers.
     */
    public var headers: (() -> List<Pair<String, String>>)? = null

    /**
     * A request filtering function.
     */
    public var requestFilter: (RequestInit.() -> Unit)? = null

    /**
     * An optional transformation function, to modify result received from the server before deserialization.
     */
    public var resultTransform: ((JsAny?) -> JsAny?)? = null

    /**
     * Request data serializer.
     */
    public var serializer: SerializationStrategy<V>? = null

    /**
     * Response data deserializer.
     */
    public var deserializer: DeserializationStrategy<T>? = null
}

/**
 * A HTTP REST client
 */
public open class RestClient(block: (RestClientConfig.() -> Unit) = {}) {

    protected val restClientConfig: RestClientConfig = RestClientConfig().apply(block)

    protected val JsonInstance: Json = Json(from = (Serialization.customConfiguration ?: Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    })) {
        serializersModule = SerializersModule {
            restClientConfig.serializersModule?.let { this.include(it) }
        }.overwriteWith(serializersModule)
    }

    /**
     * Makes a http request to the server, returning the response object.
     * @param url a URL address
     * @param block an optional block for configuring the request
     * @return the response object
     */
    public suspend fun <T : Any, V : Any> receive(
        url: String,
        block: RestRequestConfig<T, V>.() -> Unit = {}
    ): RestResponse<T> {
        val restRequestConfig = RestRequestConfig<T, V>().apply(block)
        val method = when(restRequestConfig.method) {
            HttpMethod.Get -> RequestMethod.GET
            HttpMethod.Post -> RequestMethod.POST
            HttpMethod.Put -> RequestMethod.PUT
            HttpMethod.Delete -> RequestMethod.DELETE
            HttpMethod.Options -> RequestMethod.OPTIONS
            HttpMethod.Head -> RequestMethod.HEAD
        }
        val body = if (restRequestConfig.data != null &&
            restRequestConfig.method != HttpMethod.Get && restRequestConfig.method != HttpMethod.Head
        ) {
            when (restRequestConfig.contentType) {
                "application/json" -> {
                    if (restRequestConfig.serializer != null) {
                        BodyInit(JsonInstance.encodeToString(restRequestConfig.serializer!!, restRequestConfig.data!!))
                    } else {
                        BodyInit(JSON.stringify(restRequestConfig.data!!.cast()))
                    }
                }

                "application/x-www-form-urlencoded" -> {
                    val dataSer = if (restRequestConfig.serializer != null) {
                        restRequestConfig.data!!.toObj(restRequestConfig.serializer!!)
                    } else {
                        restRequestConfig.data!!.cast()
                    }
                    URLSearchParams(removeNulls(dataSer).unsafeCast<ReadonlyRecord<JsString, JsString>>())
                }

                else -> {
                    if (restRequestConfig.serializer != null) {
                        restRequestConfig.data!!.toObj(restRequestConfig.serializer!!)
                    } else {
                        restRequestConfig.data!!.cast()
                    }
                }
            }
        } else null
        val headers = Headers()
        if (restRequestConfig.contentType != null) {
            headers.append("Content-Type", restRequestConfig.contentType!!)
        }
        restClientConfig.headers?.invoke()?.forEach {
            headers.append(it.first, it.second)
        }
        restRequestConfig.headers?.invoke()?.forEach {
            headers.append(it.first, it.second)
        }
        val requestInit = jso<RequestInit> {
            jsSet("method", method)
            if (body != null) jsSet("body", body)
            jsSet("headers", headers)
        }
        val dataUrl = if (restRequestConfig.method == HttpMethod.Get && restRequestConfig.data != null) {
            val dataSer = if (restRequestConfig.serializer != null) {
                restRequestConfig.data!!.toObj(restRequestConfig.serializer!!)
            } else {
                restRequestConfig.data!!.cast()
            }
            url + "?" + URLSearchParams(removeNulls(dataSer).unsafeCast<ReadonlyRecord<JsString, JsString>>()).toString()
        } else {
            url
        }
        val fetchUrl = if (restClientConfig.baseUrl != null) restClientConfig.baseUrl + dataUrl else dataUrl
        restClientConfig.requestFilter?.invoke(requestInit)
        restRequestConfig.requestFilter?.invoke(requestInit)
        return suspendCancellableCoroutine { continuation ->
            fetchAsync(fetchUrl, requestInit).then { response ->
                if (response.ok) {
                    val statusText = response.statusText
                    if (response.status != HTTP_NO_CONTENT) {
                        if (restRequestConfig.responseBodyType == ResponseBodyType.ReadableStream) {
                            val transformed = if (restRequestConfig.resultTransform != null) {
                                restRequestConfig.resultTransform!!.invoke(response.body)
                            } else {
                                response.body
                            }
                            val result = if (restRequestConfig.deserializer != null) {
                                JsonInstance.decodeFromString(
                                    restRequestConfig.deserializer!!,
                                    JSON.stringify(transformed)
                                )
                            } else {
                                transformed.cast()
                            }
                            continuation.resume(RestResponse(result, statusText, response))
                        } else {
                            val body = when (restRequestConfig.responseBodyType) {
                                ResponseBodyType.Json -> response.jsonAsync()
                                ResponseBodyType.Text -> response.textAsync()
                                ResponseBodyType.Blob -> response.blobAsync()
                                ResponseBodyType.FormData -> response.formDataAsync()
                                ResponseBodyType.ArrayBuffer -> response.arrayBufferAsync()
                                ResponseBodyType.ReadableStream -> throw IllegalStateException() // not possible
                            }
                            body.then {
                                val transformed = if (restRequestConfig.resultTransform != null) {
                                    restRequestConfig.resultTransform!!.invoke(it)
                                } else {
                                    it
                                }
                                val result = if (restRequestConfig.deserializer != null) {
                                    JsonInstance.decodeFromString(
                                        restRequestConfig.deserializer!!,
                                        JSON.stringify(transformed)
                                    )
                                } else {
                                    transformed.cast()
                                }
                                continuation.resume(RestResponse(result, statusText, response))
                                null
                            }.catch {
                                console.log(it)
                                continuation.resumeWithException(
                                    RemoteRequestException.create(
                                        XHR_ERROR,
                                        fetchUrl,
                                        restRequestConfig.method,
                                        "Incorrect body type",
                                        response
                                    )
                                )
                                null
                            }
                        }
                    } else {
                        if (restRequestConfig.deserializer != null) {
                            continuation.resumeWithException(
                                RemoteRequestException.create(
                                    XHR_ERROR,
                                    fetchUrl,
                                    restRequestConfig.method,
                                    "Empty body",
                                    response
                                )
                            )
                        } else {
                            continuation.resume(RestResponse(null, statusText, response))
                        }
                    }
                } else {
                    continuation.resumeWithException(
                        RemoteRequestException.create(
                            response.status,
                            fetchUrl,
                            restRequestConfig.method,
                            response.statusText,
                            response
                        )
                    )
                }
                null
            }.catch {
                continuation.resumeWithException(
                    RemoteRequestException.create(
                        XHR_ERROR,
                        fetchUrl,
                        restRequestConfig.method,
                        "Connection error"
                    )
                )
                null
            }
        }
    }

    /**
     * An extension function to convert Serializable object to JS dynamic object
     * @param serializer a serializer for T
     */
    protected fun <T> T.toObj(serializer: SerializationStrategy<T>): JsAny {
        return JSON.parse(JsonInstance.encodeToString(serializer, this))
    }

    /**
     * Removes all null values from JS object
     */
    protected fun removeNulls(data: JsAny): JsAny {
        keys(data).forEach {
            if (data.jsGet(it) == null) {
                delete(data, it)
            }
        }
        return data
    }
}

/**
 * Makes a http request to the server, returning the response object.
 * @param url a URL address
 * @param block an optional block for configuring the request
 * @return the response object
 */
public suspend fun RestClient.requestDynamic(
    url: String,
    block: RestRequestConfig<JsAny, JsAny>.() -> Unit = {}
): RestResponse<JsAny> {
    return receive(url) {
        block.invoke(this)
    }
}

/**
 * Makes a http request to the server, returning the response object.
 * @param url a URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return the response object
 */
public suspend inline fun <reified V : Any> RestClient.requestDynamic(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<JsAny, V>.() -> Unit = {}
): RestResponse<JsAny> {
    return receive(url) {
        block.invoke(this)
        this.data = data
        this.serializer = serializer()
    }
}

/**
 * Makes a http request to the server, returning the response object.
 * @param url a URL address
 * @param block an optional block for configuring the request
 * @return the response object
 */
public suspend inline fun <reified T : Any> RestClient.request(
    url: String,
    crossinline block: RestRequestConfig<T, JsAny>.() -> Unit = {}
): RestResponse<T> {
    return receive(url) {
        block.invoke(this)
        this.deserializer = serializer()
    }
}

/**
 * Makes a http request to the server, returning the response object.
 * @param url a URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return the response object
 */
public suspend inline fun <reified T : Any, reified V : Any> RestClient.request(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<T, V>.() -> Unit = {}
): RestResponse<T> {
    return receive(url) {
        block.invoke(this)
        this.data = data
        this.serializer = serializer()
        this.deserializer = serializer()
    }
}

/**
 * Makes a http request to the server, returning data directly.
 * @param url a URL address
 * @param block an optional block for configuring the request
 * @return the data
 */
public suspend fun RestClient.callDynamic(
    url: String,
    block: RestRequestConfig<JsAny, JsAny>.() -> Unit = {}
): JsAny? {
    return requestDynamic(url, block).data
}

/**
 * Makes a http request to the server, returning data directly.
 * @param url a URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return the data
 */
public suspend inline fun <reified V : Any> RestClient.callDynamic(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<JsAny, V>.() -> Unit = {}
): JsAny? {
    return requestDynamic(url, data, block).data
}

/**
 * Makes a http request to the server, returning data directly.
 * @param url a URL address
 * @param block an optional block for configuring the request
 * @return the data
 */
public suspend inline fun <reified T : Any> RestClient.call(
    url: String,
    crossinline block: RestRequestConfig<T, JsAny>.() -> Unit = {}
): T {
    return request(url, block).data!!
}

/**
 * Makes a http request to the server, returning data directly.
 * @param url a URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return the data
 */
public suspend inline fun <reified T : Any, reified V : Any> RestClient.call(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<T, V>.() -> Unit = {}
): T {
    return request(url, data, block).data!!
}

/**
 * Makes a http POST request to the server, returning data directly.
 * @param url a URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return the data
 */
public suspend inline fun <reified V : Any> RestClient.postDynamic(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<JsAny, V>.() -> Unit = {}
): JsAny? {
    return requestDynamic(url, data) {
        block.invoke(this)
        method = HttpMethod.Post
    }.data
}

/**
 * Makes a http POST request to the server, returning data directly.
 * @param url a URL address
 * @param data data to send
 * @param block an optional block for configuring the request
 * @return the data
 */
public suspend inline fun <reified T : Any, reified V : Any> RestClient.post(
    url: String,
    data: V,
    crossinline block: RestRequestConfig<T, V>.() -> Unit = {}
): T {
    return request<T, V>(url, data) {
        block.invoke(this)
        method = HttpMethod.Post
    }.data!!
}
