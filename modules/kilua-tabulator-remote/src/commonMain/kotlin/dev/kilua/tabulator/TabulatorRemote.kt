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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.JSON
import dev.kilua.externals.get
import dev.kilua.externals.globalThis
import dev.kilua.externals.undefined
import dev.kilua.promise
import dev.kilua.rpc.RemoteData
import dev.kilua.rpc.RemoteFilter
import dev.kilua.rpc.RemoteSorter
import dev.kilua.rpc.RpcSerialization
import dev.kilua.rpc.RpcServiceMgr
import dev.kilua.utils.Serialization
import dev.kilua.utils.rem
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import kotlinx.serialization.serializer
import web.JsAny
import web.JsArray
import web.fetch.RequestInit
import kotlin.reflect.KClass

public open class TabulatorRemote<T : Any>(
    data: List<T>? = null,
    options: TabulatorOptions<T> = TabulatorOptions(),
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    kClass: KClass<T>? = null,
    serializer: KSerializer<T>? = null,
    module: SerializersModule? = null
) : Tabulator<T>(data, options, className, renderConfig, kClass, serializer, module) {

    override val jsonHelper: Json? = if (serializer != null) Json(
        from = (RpcSerialization.getCustomJson() ?: Serialization.customConfiguration ?: Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    ) {
        serializersModule = SerializersModule {
            include(RpcSerialization.plain.serializersModule)
            module?.let { this.include(it) }
        }.overwriteWith(serializersModule)
    } else null

}

@PublishedApi
@Composable
internal fun <T : Any> IComponent.tabulatorRemoteInt(
    data: List<T>?,
    options: TabulatorOptions<T>,
    kClass: KClass<T>?,
    serializer: KSerializer<T>?,
    serializersModule: SerializersModule?,
    className: String?,
    content: @Composable ITabulator<T>.() -> Unit
): TabulatorRemote<T> {
    val component = remember {
        TabulatorRemote(
            data,
            options,
            className,
            renderConfig,
            kClass,
            serializer,
            serializersModule
        )
    }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        // Workaround https://issuetracker.google.com/issues/232271525
        set(data?.toList()) { updateProperty(TabulatorRemote<T>::data, it) }
        set(options) { updateProperty(TabulatorRemote<T>::options, it) }
        set(className) { updateProperty(TabulatorRemote<T>::className, it) }
    }, content)
    return component
}

/**
 * Create [TabulatorRemote] component with a Kotlin data model.
 * @param serviceManager RPC service manager
 * @param function RPC service method returning the tabulator data
 * @param stateFunction a function to generate the state object passed with the remote request
 * @param requestFilter a request filtering function
 * @param options the tabulator options
 * @param types the set of table types
 * @param serializer the serializer for the data
 * @param serializersModule the serializers module
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [TabulatorRemote] component
 */
@Composable
public inline fun <reified T : Any, E : Any> IComponent.tabulatorRemote(
    serviceManager: RpcServiceMgr<E>,
    noinline function: suspend E.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?, String?) -> RemoteData<T>,
    noinline stateFunction: (() -> String)? = null,
    noinline requestFilter: (suspend RequestInit.() -> Unit)? = null,
    options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    serializer: KSerializer<T> = serializer(),
    serializersModule: SerializersModule? = null,
    className: String? = null,
    noinline content: @Composable ITabulator<T>.() -> Unit = {}
): TabulatorRemote<T> {

    val optionsState = remember {
        val (url, _) = serviceManager.requireCall(function)
        val rpcUrlPrefix = globalThis["rpc_url_prefix"]
        val urlPrefix: String = if (rpcUrlPrefix != undefined()) "$rpcUrlPrefix/" else ""
        options.copy(
            ajaxURL = urlPrefix + url.drop(1),
            ajaxRequestFunc = { _, _, params ->
                val page = params["page"]?.toString()
                val size = params["size"]?.toString()
                val filters = params["filter"]?.let { JSON.stringify(it) }
                val sorters = params["sort"]?.let { JSON.stringify(it) }
                promise {
                    getDataForTabulatorRemote(
                        serviceManager,
                        function,
                        stateFunction,
                        requestFilter,
                        page,
                        size,
                        filters,
                        sorters
                    )
                }
            }
        )
    }
    val classNameState = remember {
        className % types.joinToString(" ") { it.value }
    }
    return tabulatorRemoteInt(
        null,
        optionsState,
        getClass<T>(),
        serializer,
        serializersModule,
        classNameState,
        content
    )
}

public expect suspend fun <T : Any, E : Any> getDataForTabulatorRemote(
    serviceManager: RpcServiceMgr<E>,
    function: suspend E.(Int?, Int?, List<RemoteFilter>?, List<RemoteSorter>?, String?) -> RemoteData<T>,
    stateFunction: (() -> String)? = null,
    requestFilter: (suspend RequestInit.() -> Unit)? = null,
    page: String?,
    size: String?,
    filters: String?,
    sorters: String?
): JsArray<JsAny>
