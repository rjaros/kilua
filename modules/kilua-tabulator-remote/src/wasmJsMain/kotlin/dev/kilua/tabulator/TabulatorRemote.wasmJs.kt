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

import dev.kilua.externals.JSON
import dev.kilua.externals.get
import dev.kilua.externals.set
import dev.kilua.rpc.CallAgent
import dev.kilua.rpc.JsonRpcRequest
import dev.kilua.rpc.RemoteData
import dev.kilua.rpc.RemoteFilter
import dev.kilua.rpc.RemoteSorter
import dev.kilua.rpc.RpcServiceMgr
import dev.kilua.utils.jsArrayOf
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import dev.kilua.dom.JsAny
import dev.kilua.dom.JsArray
import dev.kilua.dom.fetch.RequestInit
import dev.kilua.dom.toJsString

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
    val state = stateFunction?.invoke()?.let { JSON.stringify(it.toJsString()) }
    val data = Json.encodeToString(JsonRpcRequest(0, url, listOf(page, size, filters, sorters, state)))
    val r = callAgent.remoteCall(
        url,
        stringData = data,
        method = method,
        requestFilter = requestFilter?.let { requestFilterParam ->
            {
                val self = this.unsafeCast<RequestInit>()
                self.requestFilterParam()
            }
        })
    return if (r != null) {
        val result = JSON.parse<JsAny>(r["result"].toString())
        if (page != null) {
            if (result["data"] == null) {
                result["data"] = jsArrayOf<JsAny>()
            }
            result.unsafeCast()
        } else if (result["data"] == null) {
            jsArrayOf()
        } else {
            result["data"]!!.unsafeCast()
        }
    } else jsArrayOf()
}
