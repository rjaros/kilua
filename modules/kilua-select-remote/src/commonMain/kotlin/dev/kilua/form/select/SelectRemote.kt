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

package dev.kilua.form.select

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.core.IComponent
import dev.kilua.rpc.AbstractServiceException
import dev.kilua.rpc.ContentTypeException
import dev.kilua.rpc.HTTP_UNAUTHORIZED
import dev.kilua.rpc.HttpMethod
import dev.kilua.rpc.JsonRpcRequest
import dev.kilua.rpc.JsonRpcResponseJs
import dev.kilua.rpc.RpcSerialization
import dev.kilua.rpc.RpcServiceMgr
import dev.kilua.rpc.SecurityException
import dev.kilua.rpc.ServiceException
import dev.kilua.rpc.SimpleRemoteOption
import dev.kilua.utils.KiluaScope
import dev.kilua.utils.StringPair
import dev.kilua.utils.jsGet
import dev.kilua.utils.jsSet
import dev.kilua.utils.unsafeCast
import js.globals.globalThis
import js.json.stringify
import js.objects.unsafeJso
import js.promise.await
import js.uri.encodeURIComponent
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import web.console.console
import web.http.BodyInit
import web.http.Headers
import web.http.RequestInit
import web.http.RequestMethod
import web.http.fetchAsync
import web.url.URLSearchParams
import kotlin.js.JsAny
import kotlin.js.toJsString
import kotlin.js.undefined

/**
 * Creates [Select] component with a remote data source, returning a reference.
 *
 * @param serviceManager RPC service manager
 * @param function RPC service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param requestFilter a request filtering function
 * @param refreshOnFocus determines if the options should be refreshed when the component gets focus
 * @param options an initial list of options (value to label pairs)
 * @param value initial value
 * @param multiple determines if multiple value selection is allowed
 * @param size the number of visible options
 * @param name the name of the select
 * @param placeholder the placeholder for the select component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 * @return a [Select] component
 */
@Composable
public fun <T : Any> IComponent.selectRemoteRef(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?) -> List<SimpleRemoteOption>,
    stateFunction: (() -> String)? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    refreshOnFocus: Boolean = false,
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    size: Int? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ISelect.() -> Unit = {}
): Select {
    var optionsState: List<StringPair>? by remember {
        mutableStateOf(options)
    }
    val select = selectRef(
        options = optionsState,
        value = value,
        emptyOption = emptyOption,
        multiple = multiple,
        size = size,
        name = name,
        placeholder = placeholder,
        disabled = disabled,
        required = required,
        className = className,
        id = id
    ) {
        if (refreshOnFocus) {
            onFocus {
                KiluaScope.launch {
                    optionsState = getOptionsForSelectRemote(serviceManager, function, stateFunction, requestFilter)
                }
            }
        }
        setup()
    }
    LaunchedEffect(select.componentId) {
        optionsState = getOptionsForSelectRemote(serviceManager, function, stateFunction, requestFilter)
    }
    return select
}

/**
 * Creates [Select] component with a remote data source.
 *
 * @param serviceManager RPC service manager
 * @param function RPC service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param requestFilter a request filtering function
 * @param refreshOnFocus determines if the options should be refreshed when the component gets focus
 * @param options an initial list of options (value to label pairs)
 * @param value initial value
 * @param multiple determines if multiple value selection is allowed
 * @param size the number of visible options
 * @param name the name of the select
 * @param placeholder the placeholder for the select component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 */
@Composable
public fun <T : Any> IComponent.selectRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?) -> List<SimpleRemoteOption>,
    stateFunction: (() -> String)? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    refreshOnFocus: Boolean = false,
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    size: Int? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ISelect.() -> Unit = {}
) {
    var optionsState: List<StringPair>? by remember {
        mutableStateOf(options)
    }
    val select = selectRef(
        options = optionsState,
        value = value,
        emptyOption = emptyOption,
        multiple = multiple,
        size = size,
        name = name,
        placeholder = placeholder,
        disabled = disabled,
        required = required,
        className = className,
        id = id
    ) {
        if (refreshOnFocus) {
            onFocus {
                KiluaScope.launch {
                    optionsState = getOptionsForSelectRemote(serviceManager, function, stateFunction, requestFilter)
                }
            }
        }
        setup()
    }
    LaunchedEffect(select.componentId) {
        optionsState = getOptionsForSelectRemote(serviceManager, function, stateFunction, requestFilter)
    }
}

internal suspend fun <T : Any> getOptionsForSelectRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?) -> List<SimpleRemoteOption>,
    stateFunction: (() -> String)?,
    requestFilter: (suspend RequestInit.() -> Unit)?,
): List<StringPair> {
    val (url, method) = serviceManager.requireCall(function)
    val callAgent = KiluaCallAgent()
    val state = stateFunction?.invoke()?.let { stringify(it.toJsString()) }
    return try {
        val result = callAgent.jsonRpcCall(
            url,
            listOf(state),
            method = method, requestFilter = requestFilter?.let { requestFilterParam ->
                {
                    val self = this.unsafeCast<RequestInit>()
                    self.requestFilterParam()
                }
            }
        )
        RpcSerialization.plain.decodeFromString(
            ListSerializer(SimpleRemoteOption.serializer()), result
        ).map {
            it.value to (it.text ?: it.value)
        }
    } catch (e: Exception) {
        console.error(e.message)
        emptyList()
    }
}

//
// Workaround https://youtrack.jetbrains.com/issue/KT-82005/JS-2.3.0-Beta1-2-TypeError-after-code-change
//
internal class KiluaCallAgent {

    suspend fun jsonRpcCall(
        url: String,
        data: List<String?> = listOf(),
        method: HttpMethod = HttpMethod.POST,
        requestFilter: (suspend RequestInit.() -> Unit)? = null
    ): String {
        val rpcUrlPrefix = globalThis.jsGet("rpc_url_prefix")
        val urlPrefix: String = if (rpcUrlPrefix != undefined) "$rpcUrlPrefix/" else ""
        val jsonRpcRequest = JsonRpcRequest(1, url, data)
        val body =
            if (method == HttpMethod.GET) null else BodyInit(RpcSerialization.plain.encodeToString(jsonRpcRequest))
        val requestInit = getRequestInit(method, body, "application/json")
        requestFilter?.invoke(requestInit)
        val urlAddr = urlPrefix + url.drop(1)
        val fetchUrl = if (method == HttpMethod.GET) {
            val urlSearchParams = URLSearchParams()
            data.forEachIndexed { index, s ->
                if (s != null) urlSearchParams.append("p$index", encodeURIComponent(s))
            }
            "$urlAddr?$urlSearchParams"
        } else {
            urlAddr
        }
        val response = try {
            fetchAsync(fetchUrl, requestInit).await()
        } catch (e: Throwable) {
            throw Exception("Failed to fetch $fetchUrl: ${e.message}")
        }
        return if (response.ok && response.headers.get("content-type") == "application/json") {
            val jsonRpcResponse = js.reflect.unsafeCast<JsonRpcResponseJs>(response.jsonAsync().await()!!)
            when {
                method != HttpMethod.GET && jsonRpcResponse.id != jsonRpcRequest.id ->
                    throw Exception("Invalid response ID")

                jsonRpcResponse.error != null -> {
                    if (jsonRpcResponse.exceptionType == "dev.kilua.rpc.ServiceException") {
                        throw ServiceException(jsonRpcResponse.error!!)
                    } else if (jsonRpcResponse.exceptionJson != null) {
                        throw RpcSerialization.getJson()
                            .decodeFromString<AbstractServiceException>(jsonRpcResponse.exceptionJson!!)
                    } else {
                        throw Exception(jsonRpcResponse.error)
                    }
                }

                jsonRpcResponse.result != null -> jsonRpcResponse.result!!
                else -> throw Exception("Invalid response")
            }
        } else if (response.ok) {
            throw ContentTypeException("Invalid response content type: ${response.headers.get("content-type")}")
        } else {
            if (response.status.toInt() == HTTP_UNAUTHORIZED) {
                throw SecurityException(response.statusText)
            } else {
                throw Exception(response.statusText)
            }
        }
    }

    private fun getRequestMethod(httpMethod: HttpMethod): RequestMethod = when (httpMethod) {
        HttpMethod.GET -> js.reflect.unsafeCast("GET")
        HttpMethod.POST -> js.reflect.unsafeCast("POST")
        HttpMethod.PUT -> js.reflect.unsafeCast("PUT")
        HttpMethod.DELETE -> js.reflect.unsafeCast("DELETE")
        HttpMethod.OPTIONS -> js.reflect.unsafeCast("OPTIONS")
    }

    private fun getRequestInit(
        method: HttpMethod,
        body: JsAny?,
        contentType: String
    ): RequestInit {
        val requestMethod = getRequestMethod(method)
        val headers = Headers()
        headers.append("Content-Type", contentType)
        headers.append("X-Requested-With", "XMLHttpRequest")
        return unsafeJso {
            jsSet("method", requestMethod)
            if (body != null) jsSet("body", body)
            jsSet("headers", headers)
            jsSet("credentials", "include".toJsString())
        }
    }
}
