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

import dev.kilua.rpc.CallAgent
import dev.kilua.rpc.JsonRpcRequest
import dev.kilua.rpc.RpcSerialization
import dev.kilua.rpc.RpcServiceMgr
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.encodeToString
import dev.kilua.dom.fetch.RequestInit

internal actual suspend fun <T : Any> getOptionsForTomTypeaheadRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?, String?) -> List<String>,
    stateFunction: (() -> String)?,
    requestFilter: (suspend RequestInit.() -> Unit)?,
    query: String?
): List<String> {
    val (url, method) = serviceManager.requireCall(function)
    val callAgent = CallAgent()
    val state = stateFunction?.invoke()?.let { JSON.stringify(it) }
    val queryParam = query?.let { JSON.stringify(it) }
    return try {
        val values = callAgent.remoteCall(
            url,
            RpcSerialization.plain.encodeToString(JsonRpcRequest(0, url, listOf(queryParam, state))),
            method = method,
            requestFilter = requestFilter?.let { requestFilterParam ->
                {
                    val self = this.unsafeCast<RequestInit>()
                    self.requestFilterParam()
                }
            }
        )
        if (values.result != null) {
            RpcSerialization.plain.decodeFromString(
                ListSerializer(String.serializer()),
                values.result.unsafeCast<String>()
            )
        } else {
            console.error(values.error)
            emptyList()
        }
    } catch (e: Exception) {
        console.error(e.message)
        emptyList()
    }
}
