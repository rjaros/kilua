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
import dev.kilua.KiluaScope
import dev.kilua.core.IComponent
import dev.kilua.rpc.CallAgent
import dev.kilua.rpc.RpcSerialization
import dev.kilua.rpc.RpcServiceMgr
import dev.kilua.rpc.SimpleRemoteOption
import dev.kilua.utils.StringPair
import dev.kilua.utils.unsafeCast
import js.core.JsPrimitives.toJsString
import js.json.stringify
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.ListSerializer
import web.console.console
import web.http.RequestInit

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
    val callAgent = CallAgent()
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
