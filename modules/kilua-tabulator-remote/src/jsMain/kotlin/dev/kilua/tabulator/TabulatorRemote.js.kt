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

package dev.kilua.tabulator

import dev.kilua.rpc.CallAgent
import dev.kilua.rpc.JsonRpcRequest
import dev.kilua.rpc.RemoteData
import dev.kilua.rpc.RemoteFilter
import dev.kilua.rpc.RemoteSorter
import dev.kilua.rpc.RpcServiceMgr
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import web.JsAny
import web.JsArray
import web.fetch.RequestInit

@Suppress("UnsafeCastFromDynamic")
public actual suspend fun <T: Any, E : Any> getDataForTabulatorRemote(
    serviceManager: RpcServiceMgr<E>,
    function: suspend E.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?, String?) -> RemoteData<T>,
    stateFunction: (() -> String)?,
    requestFilter: (suspend RequestInit.() -> Unit)?,
    page: String?,
    size: String?,
    filters: String?,
    sorters: String?
): JsArray<JsAny> {
    val (url, method) = serviceManager.requireCall(function)
    val callAgent = CallAgent()
    val state = stateFunction?.invoke()?.let { JSON.stringify(it) }
    val data = Json.encodeToString(JsonRpcRequest(0, url, listOf(page, size, filters, sorters, state)))
    val r = callAgent.remoteCall(url, data, method = method, requestFilter = requestFilter?.let { requestFilterParam ->
        {
            val self = this.unsafeCast<RequestInit>()
            self.requestFilterParam()
        }
    })
    return if (r != null) {
        val result = JSON.parse<dynamic>(r.result.unsafeCast<String>())
        if (page != null) {
            if (result.data == undefined) {
                result.data = js("[]")
            }
            result
        } else if (result.data == undefined) {
            js("[]")
        } else {
            result.data
        }
    } else js("[]")
}
