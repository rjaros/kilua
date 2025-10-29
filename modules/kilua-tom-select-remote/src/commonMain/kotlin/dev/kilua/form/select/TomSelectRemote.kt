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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.JsArray
import dev.kilua.html.div
import dev.kilua.rpc.AbstractServiceException
import dev.kilua.rpc.ContentTypeException
import dev.kilua.rpc.HTTP_UNAUTHORIZED
import dev.kilua.rpc.HttpMethod
import dev.kilua.rpc.JsonRpcRequest
import dev.kilua.rpc.JsonRpcResponseJs
import dev.kilua.rpc.RemoteOption
import dev.kilua.rpc.RpcSerialization
import dev.kilua.rpc.RpcServiceMgr
import dev.kilua.rpc.SecurityException
import dev.kilua.rpc.ServiceException
import dev.kilua.utils.KiluaScope
import dev.kilua.utils.StringPair
import dev.kilua.utils.jsGet
import dev.kilua.utils.jsSet
import dev.kilua.utils.obj
import dev.kilua.utils.rem
import dev.kilua.utils.toJsArray
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

internal external class RemoteOptionExt : JsAny {
    var value: String?
    var text: String?
    var className: String?
    var subtext: String?
    var icon: String?
    var content: String?
    var disabled: Boolean
    var divider: Boolean
}

/**
 * Tom Select component with a remote data source.
 */
public interface ITomSelectRemote : ITomSelect {
    /**
     * Determines if the options should be opened when the component gets focus
     */
    public val openOnFocus: Boolean

    /**
     * Set whether the options should be opened when the component gets focus
     */
    @Composable
    public fun openOnFocus(openOnFocus: Boolean)
}

/**
 * Tom Select component with a remote data source.
 */
public open class TomSelectRemote<out T : Any>(
    private val serviceManager: RpcServiceMgr<T>,
    private val function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    private val stateFunction: (() -> String)? = null,
    private val requestFilter: (suspend RequestInit.() -> Unit)? = null,
    openOnFocus: Boolean = false,
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    maxOptions: Int? = null,
    tsOptions: TomSelectOptions? = null,
    tsCallbacks: TomSelectCallbacks? = null,
    tsRenders: TomSelectRenders? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) : TomSelect(
    options,
    value,
    emptyOption,
    multiple,
    maxOptions,
    tsOptions,
    tsCallbacks,
    tsRenders,
    name,
    placeholder,
    disabled,
    required,
    className,
    id,
    renderConfig
), ITomSelectRemote {

    private val initialOpenOnFocus: Boolean = openOnFocus

    /**
     * Determines if the options should be opened when the component gets focus
     */
    public override var openOnFocus: Boolean by updatingProperty(openOnFocus) {
        refresh()
    }

    /**
     * Set whether the options should be opened when the component gets focus
     */
    @Composable
    public override fun openOnFocus(openOnFocus: Boolean): Unit = composableProperty("openOnFocus", {
        this.openOnFocus = initialOpenOnFocus
    }) {
        this.openOnFocus = openOnFocus
    }

    override fun refreshValue() {
        if (value != null && tomSelectInstance != null && tomSelectInstance!!.options.jsGet(value!!) == null) {
            KiluaScope.launch {
                val result = getOptionsForTomSelectRemote(
                    serviceManager,
                    function,
                    stateFunction,
                    requestFilter,
                    null,
                    value
                )
                val resultWithOption = if (emptyOption) {
                    listOf(obj<RemoteOptionExt> {
                        value = ""
                        text = "\u00a0"
                    }) + result
                } else result
                tomSelectInstance?.addOptions(resultWithOption.toJsArray(), false)
                super.refreshValue()
            }
        } else {
            super.refreshValue()
        }
    }

    override fun initializeTomSelect() {
        super.initializeTomSelect()
        if (openOnFocus) {
            tomSelectInstance?.on("focus") { ->
                if (this.value == null) {
                    tomSelectInstance?.load("")
                }
            }
            tomSelectInstance?.on("change") { v: JsAny ->
                if (v.toString() == "") {
                    tomSelectInstance?.load("")
                }
            }
        }
    }
}

@Composable
internal fun <T : Any> IComponent.tomSelectRemoteRef(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    stateFunction: (() -> String)? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    openOnFocus: Boolean = false,
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    maxOptions: Int? = null,
    tsOptions: TomSelectOptions? = null,
    tsCallbacks: TomSelectCallbacks? = null,
    tsRenders: TomSelectRenders? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITomSelectRemote.() -> Unit = {}
): TomSelectRemote<T> {
    return key(multiple) {
        val component = remember {
            TomSelectRemote(
                serviceManager,
                function,
                stateFunction,
                requestFilter,
                openOnFocus,
                options,
                value,
                emptyOption,
                multiple,
                maxOptions,
                tsOptions,
                tsCallbacks,
                tsRenders,
                name,
                placeholder,
                disabled,
                required,
                className % "form-select",
                id,
                renderConfig
            )
        }
        DisposableEffect(component.componentId) {
            component.onInsert()
            onDispose {
                component.onRemove()
            }
        }
        ComponentNode(component, {
            set(openOnFocus) { updateProperty(TomSelectRemote<T>::openOnFocus, it) }
            set(options) { updateProperty(TomSelectRemote<T>::options, it) }
            set(value) { updateProperty(TomSelectRemote<T>::value, it) }
            set(emptyOption) { updateProperty(TomSelectRemote<T>::emptyOption, it) }
            set(maxOptions) { updateProperty(TomSelectRemote<T>::maxOptions, it) }
            set(tsOptions) { updateProperty(TomSelectRemote<T>::tsOptions, it) }
            set(tsCallbacks) { updateProperty(TomSelectRemote<T>::tsCallbacks, it) }
            set(tsRenders) { updateProperty(TomSelectRemote<T>::tsRenders, it) }
            set(name) { updateProperty(TomSelectRemote<T>::name, it) }
            set(placeholder) { updateProperty(TomSelectRemote<T>::placeholder, it) }
            set(disabled) { updateProperty(TomSelectRemote<T>::disabled, it) }
            set(required) { updateProperty(TomSelectRemote<T>::required, it) }
            set(className) { updateProperty(TomSelectRemote<T>::className, it % "form-select") }
            set(id) { updateProperty(TomSelectRemote<T>::id, it) }
        }, setup)
        div() // Empty div as a placeholder for the generated HTML element
        component
    }
}

@Composable
internal fun <T : Any> IComponent.tomSelectRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    stateFunction: (() -> String)? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    openOnFocus: Boolean = false,
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    maxOptions: Int? = null,
    tsOptions: TomSelectOptions? = null,
    tsCallbacks: TomSelectCallbacks? = null,
    tsRenders: TomSelectRenders? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITomSelectRemote.() -> Unit = {}
) {
    key(multiple) {
        val component = remember {
            TomSelectRemote(
                serviceManager,
                function,
                stateFunction,
                requestFilter,
                openOnFocus,
                options,
                value,
                emptyOption,
                multiple,
                maxOptions,
                tsOptions,
                tsCallbacks,
                tsRenders,
                name,
                placeholder,
                disabled,
                required,
                className % "form-select",
                id,
                renderConfig
            )
        }
        DisposableEffect(component.componentId) {
            component.onInsert()
            onDispose {
                component.onRemove()
            }
        }
        ComponentNode(component, {
            set(openOnFocus) { updateProperty(TomSelectRemote<T>::openOnFocus, it) }
            set(options) { updateProperty(TomSelectRemote<T>::options, it) }
            set(value) { updateProperty(TomSelectRemote<T>::value, it) }
            set(emptyOption) { updateProperty(TomSelectRemote<T>::emptyOption, it) }
            set(maxOptions) { updateProperty(TomSelectRemote<T>::maxOptions, it) }
            set(tsOptions) { updateProperty(TomSelectRemote<T>::tsOptions, it) }
            set(tsCallbacks) { updateProperty(TomSelectRemote<T>::tsCallbacks, it) }
            set(tsRenders) { updateProperty(TomSelectRemote<T>::tsRenders, it) }
            set(name) { updateProperty(TomSelectRemote<T>::name, it) }
            set(placeholder) { updateProperty(TomSelectRemote<T>::placeholder, it) }
            set(disabled) { updateProperty(TomSelectRemote<T>::disabled, it) }
            set(required) { updateProperty(TomSelectRemote<T>::required, it) }
            set(className) { updateProperty(TomSelectRemote<T>::className, it % "form-select") }
            set(id) { updateProperty(TomSelectRemote<T>::id, it) }
        }, setup)
        div() // Empty div as a placeholder for the generated HTML elements
    }
}

/**
 * Creates [TomSelectRemote] component with a remote data source, returning a reference.
 *
 * @param serviceManager RPC service manager
 * @param function RPC service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param requestFilter a request filtering function
 * @param openOnFocus determines if the options should be opened when the component gets focus
 * @param preload preload all options from remote data source
 * @param options a list of options (value to label pairs)
 * @param value initial value
 * @param emptyOption determines if an empty option is allowed
 * @param multiple determines if multiple value selection is allowed
 * @param maxOptions the maximum number of visible options
 * @param tsOptions Tom Select options
 * @param tsCallbacks Tom Select callbacks
 * @param tsRenders Tom Select renders
 * @param name the name of the select
 * @param placeholder the placeholder for the select component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 * @return a [TomSelectRemote] component
 */
@Composable
public fun <T : Any> IComponent.tomSelectRemoteRef(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    stateFunction: (() -> String)? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    openOnFocus: Boolean = false,
    preload: Boolean = false,
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    maxOptions: Int? = null,
    tsOptions: TomSelectOptions? = null,
    tsCallbacks: TomSelectCallbacks? = null,
    tsRenders: TomSelectRenders? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITomSelectRemote.() -> Unit = {}
): TomSelectRemote<T> {

    lateinit var tomSelectRemote: TomSelectRemote<T>

    val tsCallbacksState: TomSelectCallbacks = remember(tsCallbacks, preload, openOnFocus) {
        val loadCallback: ((query: String, callback: (JsArray<JsAny>) -> Unit) -> Unit)? = if (!preload) {
            { query, callback ->
                tomSelectRemote.clearOptions()
                KiluaScope.launch {
                    val result = getOptionsForTomSelectRemote(
                        serviceManager,
                        function,
                        stateFunction,
                        requestFilter,
                        query,
                        tomSelectRemote.value
                    )
                    callback(result.toJsArray())
                }
            }
        } else null
        val shouldLoadCallback = if (openOnFocus) {
            { _: String -> true }
        } else null
        tsCallbacks?.copy(load = loadCallback, shouldLoad = shouldLoadCallback)
            ?: TomSelectCallbacks(load = loadCallback, shouldLoad = shouldLoadCallback)
    }
    val tsRendersState: TomSelectRenders = remember(tsRenders) {
        tsRenders?.copy(option = ::renderOption, item = ::renderItem) ?: TomSelectRenders(
            option = ::renderOption,
            item = ::renderItem
        )
    }
    var tsOptionsState: TomSelectOptions? by remember(tsOptions, preload, openOnFocus) {
        val forcedPreload = if (preload) true else tsOptions?.preload
        val forcedOpenOnFocus = if (openOnFocus) true else tsOptions?.openOnFocus
        mutableStateOf(
            tsOptions?.copy(preload = forcedPreload, openOnFocus = forcedOpenOnFocus, searchField = emptyList())
                ?: TomSelectOptions(
                    preload = forcedPreload,
                    openOnFocus = forcedOpenOnFocus,
                    searchField = emptyList()
                )
        )
    }
    tomSelectRemote = tomSelectRemoteRef(
        serviceManager = serviceManager,
        function = function,
        stateFunction = stateFunction,
        requestFilter = requestFilter,
        openOnFocus = openOnFocus,
        options = options,
        value = value,
        emptyOption = emptyOption,
        multiple = multiple,
        maxOptions = maxOptions,
        tsOptions = tsOptionsState,
        tsCallbacks = tsCallbacksState,
        tsRenders = tsRendersState,
        name = name,
        placeholder = placeholder,
        disabled = disabled,
        required = required,
        className = className,
        id = id,
        setup = setup
    )
    if (preload) {
        LaunchedEffect(tomSelectRemote.componentId) {
            val result =
                getOptionsForTomSelectRemote(serviceManager, function, stateFunction, requestFilter, null, null)
            tsOptionsState = tsOptionsState?.copy(options = result)
        }
    }
    return tomSelectRemote
}

/**
 * Creates [TomSelectRemote] component with a remote data source.
 *
 * @param serviceManager RPC service manager
 * @param function RPC service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param requestFilter a request filtering function
 * @param openOnFocus determines if the options should be opened when the component gets focus
 * @param preload preload all options from remote data source
 * @param options a list of options (value to label pairs)
 * @param value initial value
 * @param emptyOption determines if an empty option is allowed
 * @param multiple determines if multiple value selection is allowed
 * @param maxOptions the maximum number of visible options
 * @param tsOptions Tom Select options
 * @param tsCallbacks Tom Select callbacks
 * @param tsRenders Tom Select renders
 * @param name the name of the select
 * @param placeholder the placeholder for the select component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 */
@Composable
public fun <T : Any> IComponent.tomSelectRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    stateFunction: (() -> String)? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    openOnFocus: Boolean = false,
    preload: Boolean = false,
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    maxOptions: Int? = null,
    tsOptions: TomSelectOptions? = null,
    tsCallbacks: TomSelectCallbacks? = null,
    tsRenders: TomSelectRenders? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITomSelectRemote.() -> Unit = {}
) {

    lateinit var tomSelectRemote: TomSelectRemote<T>

    val tsCallbacksState: TomSelectCallbacks = remember(tsCallbacks, preload, openOnFocus) {
        val loadCallback: ((query: String, callback: (JsArray<JsAny>) -> Unit) -> Unit)? = if (!preload) {
            { query, callback ->
                tomSelectRemote.clearOptions()
                KiluaScope.launch {
                    val result = getOptionsForTomSelectRemote(
                        serviceManager,
                        function,
                        stateFunction,
                        requestFilter,
                        query,
                        tomSelectRemote.value
                    )
                    callback(result.toJsArray())
                }
            }
        } else null
        val shouldLoadCallback = if (openOnFocus) {
            { _: String -> true }
        } else null
        tsCallbacks?.copy(load = loadCallback, shouldLoad = shouldLoadCallback)
            ?: TomSelectCallbacks(load = loadCallback, shouldLoad = shouldLoadCallback)
    }
    val tsRendersState: TomSelectRenders = remember(tsRenders) {
        tsRenders?.copy(option = ::renderOption, item = ::renderItem) ?: TomSelectRenders(
            option = ::renderOption,
            item = ::renderItem
        )
    }
    var tsOptionsState: TomSelectOptions? by remember(tsOptions, preload, openOnFocus) {
        val forcedPreload = if (preload) true else tsOptions?.preload
        val forcedOpenOnFocus = if (openOnFocus) true else tsOptions?.openOnFocus
        mutableStateOf(
            tsOptions?.copy(preload = forcedPreload, openOnFocus = forcedOpenOnFocus, searchField = emptyList())
                ?: TomSelectOptions(
                    preload = forcedPreload,
                    openOnFocus = forcedOpenOnFocus,
                    searchField = emptyList()
                )
        )
    }
    tomSelectRemote = tomSelectRemoteRef(
        serviceManager = serviceManager,
        function = function,
        stateFunction = stateFunction,
        requestFilter = requestFilter,
        openOnFocus = openOnFocus,
        options = options,
        value = value,
        emptyOption = emptyOption,
        multiple = multiple,
        maxOptions = maxOptions,
        tsOptions = tsOptionsState,
        tsCallbacks = tsCallbacksState,
        tsRenders = tsRendersState,
        name = name,
        placeholder = placeholder,
        disabled = disabled,
        required = required,
        className = className,
        id = id,
        setup = setup
    )
    if (preload) {
        LaunchedEffect(tomSelectRemote.componentId) {
            val result =
                getOptionsForTomSelectRemote(serviceManager, function, stateFunction, requestFilter, null, null)
            tsOptionsState = tsOptionsState?.copy(options = result)
        }
    }
}

internal fun renderOption(data: JsAny, escape: (String) -> String): String {
    val remoteOptionExt = data.unsafeCast<RemoteOptionExt>()
    val text = remoteOptionExt.text?.let { escape(it) }
    val className = if (remoteOptionExt.className != null) " class=\"${escape(remoteOptionExt.className!!)}\"" else ""
    return if (remoteOptionExt.divider) {
        "<div class=\"kv-tom-select-remote-divider\"></div>"
    } else if (remoteOptionExt.content != null) {
        "<div${className}>${remoteOptionExt.content}</div>"
    } else {
        val subtext =
            if (remoteOptionExt.subtext != null) " <small class=\"text-body-secondary\">${escape(remoteOptionExt.subtext!!)}</small> " else ""
        val icon = if (remoteOptionExt.icon != null) "<i class=\"${escape(remoteOptionExt.icon!!)}\"></i> " else ""
        "<div${className}>${icon}${text}${subtext}</div>"
    }
}

internal fun renderItem(data: JsAny, escape: (String) -> String): String {
    return "<div>${renderOption(data, escape)}</div"
}

internal suspend fun <T : Any> getOptionsForTomSelectRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    stateFunction: (() -> String)?,
    requestFilter: (suspend RequestInit.() -> Unit)?,
    query: String?,
    initial: String?
): List<RemoteOptionExt> {
    val (url, method) = serviceManager.requireCall(function)
    val callAgent = KiluaCallAgentTs()
    val state = stateFunction?.invoke()?.let { stringify(it.toJsString()) }
    val queryParam = query?.let { stringify(it.toJsString()) }
    val initialParam = initial?.let { stringify(it.toJsString()) }
    return try {
        val result = callAgent.jsonRpcCall(
            url,
            listOf(queryParam, initialParam, state),
            method = method,
            requestFilter = requestFilter?.let { requestFilterParam ->
                {
                    val self = this.unsafeCast<RequestInit>()
                    self.requestFilterParam()
                }
            }
        )
        RpcSerialization.plain.decodeFromString(ListSerializer(RemoteOption.serializer()), result)
            .mapIndexed { index, option ->
                obj {
                    if (option.divider) {
                        this.value = "divider${index}"
                        this.divider = true
                        this.disabled = true
                    } else {
                        this.value = option.value
                        if (option.text != null) this.text = option.text
                        if (option.className != null) this.className = option.className
                        if (option.disabled) this.disabled = true
                        if (option.subtext != null) this.subtext = option.subtext
                        if (option.icon != null) this.icon = option.icon
                        if (option.content != null) this.content = option.content
                    }
                }
            }
    } catch (e: Exception) {
        console.error(e.message)
        emptyList()
    }
}


//
// Workaround https://youtrack.jetbrains.com/issue/KT-82005/JS-2.3.0-Beta1-2-TypeError-after-code-change
//
internal class KiluaCallAgentTs {

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
