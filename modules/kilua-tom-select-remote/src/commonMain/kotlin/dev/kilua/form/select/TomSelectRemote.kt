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
import dev.kilua.KiluaScope
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.get
import dev.kilua.rpc.RemoteOption
import dev.kilua.rpc.RpcServiceMgr
import dev.kilua.utils.StringPair
import dev.kilua.utils.rem
import dev.kilua.utils.toJsArray
import dev.kilua.utils.unsafeCast
import kotlinx.coroutines.launch
import dev.kilua.dom.JsAny
import dev.kilua.dom.fetch.RequestInit

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
        if (value != null && tomSelectInstance != null && tomSelectInstance!!.options[value!!] == null) {
            KiluaScope.launch {
                val result = getOptionsForTomSelectRemote(
                    serviceManager,
                    function,
                    stateFunction,
                    requestFilter,
                    null,
                    value
                ).map {
                    it.unsafeCast<JsAny>()
                }.toJsArray()
                tomSelectInstance?.addOptions(result, false)
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
        val loadCallback: ((query: String, callback: (dev.kilua.dom.JsArray<JsAny>) -> Unit) -> Unit)? = if (!preload) {
            { query, callback ->
                tomSelectRemote.tomSelectInstance?.clearOptions { true }
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
        mutableStateOf(
            tsOptions?.copy(preload = preload, openOnFocus = openOnFocus, searchField = emptyList())
                ?: TomSelectOptions(
                    preload = preload,
                    openOnFocus = openOnFocus,
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
        val loadCallback: ((query: String, callback: (dev.kilua.dom.JsArray<JsAny>) -> Unit) -> Unit)? = if (!preload) {
            { query, callback ->
                tomSelectRemote.tomSelectInstance?.clearOptions { true }
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
        mutableStateOf(
            tsOptions?.copy(preload = preload, openOnFocus = openOnFocus, searchField = emptyList())
                ?: TomSelectOptions(
                    preload = preload,
                    openOnFocus = openOnFocus,
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

internal expect suspend fun <T : Any> getOptionsForTomSelectRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?, String?) -> List<RemoteOption>,
    stateFunction: (() -> String)? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    query: String?,
    initial: String?,
): List<RemoteOptionExt>
