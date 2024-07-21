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

import dev.kilua.externals.JSON
import dev.kilua.externals.console
import dev.kilua.externals.get
import dev.kilua.rpc.CallAgent
import dev.kilua.rpc.JsonRpcRequest
import dev.kilua.rpc.RpcSerialization
import dev.kilua.rpc.RpcServiceMgr
import dev.kilua.rpc.SimpleRemoteOption
import dev.kilua.utils.StringPair
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import dev.kilua.dom.fetch.RequestInit
import dev.kilua.dom.toJsString

internal actual suspend fun <T : Any> getOptionsForSelectRemote(
    serviceManager: RpcServiceMgr<T>,
    function: suspend T.(String?) -> List<SimpleRemoteOption>,
    stateFunction: (() -> String)?,
    requestFilter: (suspend RequestInit.() -> Unit)?,
): List<StringPair> {
    val (url, method) = serviceManager.requireCall(function)
    val callAgent = CallAgent()
    val state = stateFunction?.invoke()?.let { JSON.stringify(it.toJsString()) }
    return try {
        val values = callAgent.remoteCall(
            url,
            stringData = RpcSerialization.plain.encodeToString(JsonRpcRequest(0, url, listOf(state))),
            method = method, requestFilter = requestFilter?.let { requestFilterParam ->
                {
                    val self = this.unsafeCast<RequestInit>()
                    self.requestFilterParam()
                }
            }
        )
        if (values!!["result"] != null) {
            RpcSerialization.plain.decodeFromString(
                ListSerializer(SimpleRemoteOption.serializer()),
                values["result"].toString()
            ).map {
                it.value to (it.text ?: it.value)
            }
        } else {
            console.error(values["error"])
            emptyList()
        }
    } catch (e: Exception) {
        console.error(e.message)
        emptyList()
    }
}
