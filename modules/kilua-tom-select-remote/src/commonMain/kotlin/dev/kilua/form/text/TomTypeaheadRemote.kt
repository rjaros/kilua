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

package dev.kilua.form.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.utils.KiluaScope
import dev.kilua.core.IComponent
import dev.kilua.form.InputType
import dev.kilua.form.select.TomSelectCallbacks
import dev.kilua.rpc.CallAgent
import dev.kilua.rpc.RpcSerialization
import dev.kilua.rpc.RpcServiceMgr
import dev.kilua.externals.JsArray
import dev.kilua.utils.toJsArray
import dev.kilua.utils.unsafeCast
import kotlinx.coroutines.launch
import js.core.JsAny
import js.core.JsPrimitives.toJsString
import js.json.stringify
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import web.console.console
import web.http.RequestInit

/**
 * Creates [TomTypeahead] component with a remote data source, returning a reference.
 *
 * @param serviceManager RPC service manager
 * @param function RPC service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param requestFilter a request filtering function
 * @param options a list of options
 * @param value initial value
 * @param type the type of the input
 * @param tsCallbacks Tom Select callbacks
 * @param name the name of the select
 * @param placeholder the placeholder for the input component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 * @return a [TomTypeahead] component
 */
@Composable
public fun <T : Any> IComponent.tomTypeaheadRemoteRef(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    stateFunction: (() -> String)? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    options: List<String>? = null,
    value: String? = null,
    type: InputType = InputType.Text,
    tsCallbacks: TomSelectCallbacks? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITomTypeahead.() -> Unit = {}
): TomTypeahead {

    val tsCallbacksState: TomSelectCallbacks = remember(tsCallbacks) {
        val loadCallback: ((query: String, callback: (JsArray<JsAny>) -> Unit) -> Unit) =
            { query, callback ->
                KiluaScope.launch {
                    val result = getOptionsForTomTypeaheadRemote(
                        serviceManager,
                        function,
                        stateFunction,
                        requestFilter,
                        query
                    )
                    callback(result.map { it.toJsString() }.toJsArray())
                }
            }
        tsCallbacks?.copy(load = loadCallback) ?: TomSelectCallbacks(load = loadCallback)
    }
    return tomTypeaheadRef(
        options = options,
        value = value,
        type = type,
        tsCallbacks = tsCallbacksState,
        name = name,
        placeholder = placeholder,
        disabled = disabled,
        required = required,
        className = className,
        id = id,
        setup = setup
    )
}

/**
 * Creates [TomTypeahead] component with a remote data source.
 *
 * @param serviceManager RPC service manager
 * @param function RPC service method returning the list of options
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param requestFilter a request filtering function
 * @param options a list of options
 * @param value initial value
 * @param type the type of the input
 * @param tsCallbacks Tom Select callbacks
 * @param name the name of the select
 * @param placeholder the placeholder for the input component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 */
@Composable
public fun <T : Any> IComponent.tomTypeaheadRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    stateFunction: (() -> String)? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    options: List<String>? = null,
    value: String? = null,
    type: InputType = InputType.Text,
    tsCallbacks: TomSelectCallbacks? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITomTypeahead.() -> Unit = {}
) {

    val tsCallbacksState: TomSelectCallbacks = remember(tsCallbacks) {
        val loadCallback: ((query: String, callback: (JsArray<JsAny>) -> Unit) -> Unit) =
            { query, callback ->
                KiluaScope.launch {
                    val result = getOptionsForTomTypeaheadRemote(
                        serviceManager,
                        function,
                        stateFunction,
                        requestFilter,
                        query
                    )
                    callback(result.map { it.toJsString() }.toJsArray())
                }
            }
        tsCallbacks?.copy(load = loadCallback) ?: TomSelectCallbacks(load = loadCallback)
    }
    tomTypeahead(
        options = options,
        value = value,
        type = type,
        tsCallbacks = tsCallbacksState,
        name = name,
        placeholder = placeholder,
        disabled = disabled,
        required = required,
        className = className,
        id = id,
        setup = setup
    )
}

internal suspend fun <T : Any> getOptionsForTomTypeaheadRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    stateFunction: (() -> String)?,
    requestFilter: (suspend RequestInit.() -> Unit)?,
    query: String?
): List<String> {
    val (url, method) = serviceManager.requireCall(function)
    val callAgent = CallAgent()
    val state = stateFunction?.invoke()?.let { stringify(it.toJsString()) }
    val queryParam = query?.let { stringify(it.toJsString()) }
    return try {
        val result = callAgent.jsonRpcCall(
            url,
            listOf(queryParam, state),
            method = method,
            requestFilter = requestFilter?.let { requestFilterParam ->
                {
                    val self = this.unsafeCast<RequestInit>()
                    self.requestFilterParam()
                }
            }
        )
        RpcSerialization.plain.decodeFromString(ListSerializer(String.serializer()), result)
    } catch (e: Exception) {
        console.error(e.message)
        emptyList()
    }
}
