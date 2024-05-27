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
import dev.kilua.compose.Root
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.JSON
import dev.kilua.externals.TabulatorJs
import dev.kilua.externals.TabulatorTablesJs.TabulatorFull
import dev.kilua.externals.buildCustomEventInit
import dev.kilua.externals.obj
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.utils.Serialization
import dev.kilua.utils.cast
import dev.kilua.utils.deepMerge
import dev.kilua.utils.jsObjectOf
import dev.kilua.utils.nativeListOf
import dev.kilua.utils.rem
import dev.kilua.utils.toJsArray
import dev.kilua.utils.toKebabCase
import dev.kilua.utils.unsafeCast
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import kotlinx.serialization.serializer
import web.JsAny
import web.JsArray
import web.JsNumber
import web.dom.HTMLDivElement
import web.toInt
import web.toJsBoolean
import web.toJsNumber
import web.window
import kotlin.reflect.KClass

/**
 * Tabulator table types.
 */
public enum class TableType {
    TableStriped,
    TableBordered,
    TableBorderless,
    TableHover,
    TableSm;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Tabulator component.
 */
public interface ITabulator<T : Any> : ITag<HTMLDivElement> {
    /**
     * The tabulator data.
     */
    public val data: List<T>?

    /**
     * The tabulator options.
     */
    public val options: TabulatorOptions<T>

    /**
     * The native Tabulator component instance.
     */
    public val tabulatorJs: TabulatorJs?

    /**
     * Converts an internal (dynamic) data model to Kotlin data model
     */
    public fun toKotlinObj(data: JsAny): T

    /**
     * Converts a Kotlin data model to an internal (dynamic) data model
     */
    public fun toPlainObj(data: T): JsAny
}

/**
 * Tabulator component.
 */
public open class Tabulator<T : Any>(
    data: List<T>? = null,
    options: TabulatorOptions<T> = TabulatorOptions(),
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val kClass: KClass<T>? = null,
    protected val serializer: KSerializer<T>? = null,
    protected val module: SerializersModule? = null
) :
    Tag<HTMLDivElement>("div", className, id, renderConfig = renderConfig), ITabulator<T> {

    /**
     * The kotlinx.serialization configuration object.
     */
    protected open val jsonHelper: Json? =
        if (serializer != null) Json(from = (Serialization.customConfiguration ?: Json {
            ignoreUnknownKeys = true
            isLenient = true
        })) {
            serializersModule = SerializersModule {
                module?.let { this.include(it) }
            }.overwriteWith(serializersModule)
        } else null

    /**
     * The tabulator data.
     */
    public override var data: List<T>? by updatingProperty(data) {
        internalData = data?.let { toPlainObjList(it) }
        refreshData()
    }

    /**
     * The tabulator options.
     */
    public override var options: TabulatorOptions<T> by updatingProperty(options) {
        refresh()
    }

    /**
     * The native Tabulator component instance.
     */
    public override var tabulatorJs: TabulatorJs? = null

    protected var initialized: Boolean = false

    /**
     * The internal data model.
     */
    protected var internalData: JsArray<JsAny>? = null

    /**
     * The current page size.
     */
    protected var pageSize: Int? = null

    /**
     * The current page.
     */
    protected var currentPage: Int? = null

    private var customRoots: MutableList<Root> = nativeListOf()

    init {
        internalData = data?.let { toPlainObjList(it) }
    }

    /**
     * Destroy and recreate the Tabulator component.
     */
    protected open fun refresh() {
        if (tabulatorJs != null) {
            destroyTabulator()
            initializeTabulator()
        }
    }

    /**
     * Update the Tabulator data.
     */
    protected open fun refreshData() {
        if (tabulatorJs != null) {
            if (element.querySelectorAll(".tabulator-editing").length > 0) {
                removeCustomEditors()
            }
            if (internalData != null) {
                tabulatorJs!!.replaceData(internalData!!, null, null)
            } else {
                tabulatorJs!!.clearData()
            }
        }
    }

    private fun removeCustomEditors() {
        EditorRoot.cancel?.invoke(null)
        EditorRoot.disposeTimer?.let { window.clearTimeout(it) }
        EditorRoot.root?.dispose()
        EditorRoot.root = null
    }

    override fun onInsert() {
        initializeTabulator()
    }

    override fun onRemove() {
        destroyTabulator()
    }

    /**
     * Initialize the Tabulator component.
     */
    protected open fun initializeTabulator() {
        if (renderConfig.isDom) {
            val defaultLangs = jsObjectOf(
                "default" to mapOf(
                    "groups" to mapOf(
                        "item" to "",
                        "items" to ""
                    ),
                    "data" to mapOf(
                        "loading" to "...",
                        "error" to "!!!"
                    ),
                    "pagination" to mapOf(
                        "page_size" to "<i class=\"fas fa-up-down\"></i>",
                        "page_title" to "#",
                        "first" to "<i class=\"fas fa-angles-left\"></i>",
                        "first_title" to "<<",
                        "last" to "<i class=\"fas fa-angles-right\"></i>",
                        "last_title" to ">>",
                        "prev" to "<i class=\"fas fa-angle-left\"></i>",
                        "prev_title" to "<",
                        "next" to "<i class=\"fas fa-angle-right\"></i>",
                        "next_title" to ">",
                        "all" to "*",
                        "counter" to mapOf(
                            "showing" to "",
                            "of" to "/",
                            "rows" to "",
                            "pages" to ""
                        )
                    ),
                    "headerFilters" to mapOf(
                        "default" to "..."
                    )
                )
            )
            val langs = if (options.langs == null) {
                defaultLangs
            } else {
                deepMerge(defaultLangs, options.langs!!)
            }
            val newOptions = if (options.data == null) {
                options.copy(data = internalData, langs = langs)
            } else options.copy(langs = langs)
            tabulatorJs = TabulatorFull(element, newOptions.toJs(this, kClass))
            tabulatorJs?.on("tableBuilt") { ->
                initialized = true
                if (currentPage != null) {
                    tabulatorJs?.setPageSize(pageSize ?: 0)
                    tabulatorJs?.setPage(currentPage!!.toJsNumber())
                }
                dispatchEvent("tableBuilt", buildCustomEventInit(obj()))
            }
            tabulatorJs?.on("pageLoaded") { ->
                dispatchEvent("pageLoaded", buildCustomEventInit(obj()))
            }
            tabulatorJs?.on("renderComplete") { ->
                dispatchEvent("renderComplete", buildCustomEventInit(obj()))
            }
        }
    }

    /**
     * Destroy the Tabulator component.
     */
    protected fun destroyTabulator() {
        if (renderConfig.isDom) {
            if (initialized) {
                val page = tabulatorJs?.getPage()
                if (page != null && page != false.toJsBoolean()) {
                    pageSize = tabulatorJs?.getPageSize()
                    currentPage = page.unsafeCast<JsNumber>().toInt()
                }
            }
            tabulatorJs?.destroy()
            tabulatorJs = null
            initialized = false
            customRoots.forEach { it.dispose() }
            customRoots.clear()
        }
    }

    internal fun addCustomRoot(root: Root) {
        customRoots.add(root)
    }

    /**
     * Converts an internal (dynamic) data model to Kotlin data model
     */
    public override fun toKotlinObj(data: JsAny): T {
        return if (kClass != null) {
            if (jsonHelper == null || serializer == null) {
                throw IllegalStateException("The data class can't be deserialized. Please provide a serializer when creating the Tabulator instance.")
            } else {
                jsonHelper!!.decodeFromString(serializer, JSON.stringify(data))
            }
        } else data.cast()
    }

    /**
     * Converts a list of Kotlin data models to an internal (dynamic) data model array
     */
    protected open fun toPlainObjList(data: List<T>): JsArray<JsAny> {
        return if (kClass != null) {
            if (jsonHelper == null || serializer == null) {
                throw IllegalStateException("The data class can't be deserialized. Please provide a serializer when creating the Tabulator component.")
            } else {
                data.map { toPlainObj(it) }.toJsArray()
            }
        } else data.cast<List<JsAny>>().toJsArray()
    }

    /**
     * Converts a Kotlin data model to an internal (dynamic) data model
     */
    public override fun toPlainObj(data: T): JsAny {
        return if (jsonHelper == null || serializer == null) {
            throw IllegalStateException("The data class can't be serialized. Please provide a serializer when creating the Tabulator instance.")
        } else {
            JSON.parse<JsAny>(jsonHelper!!.encodeToString(serializer, data))
        }
    }

}

@PublishedApi
@Composable
internal fun <T : Any> IComponent.tabulatorIntRef(
    data: List<T>?,
    options: TabulatorOptions<T>,
    kClass: KClass<T>?,
    serializer: KSerializer<T>?,
    serializersModule: SerializersModule?,
    className: String?,
    id: String?,
    content: @Composable ITabulator<T>.() -> Unit
): Tabulator<T> {
    val component = remember {
        Tabulator(
            data,
            options,
            className,
            id,
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
        set(data?.toList()) { updateProperty(Tabulator<T>::data, it) }
        set(options) { updateProperty(Tabulator<T>::options, it) }
        set(className) { updateProperty(Tabulator<T>::className, it) }
        set(id) { updateProperty(Tabulator<T>::id, it) }
    }, content)
    return component
}

@PublishedApi
@Composable
internal fun <T : Any> IComponent.tabulatorInt(
    data: List<T>?,
    options: TabulatorOptions<T>,
    kClass: KClass<T>?,
    serializer: KSerializer<T>?,
    serializersModule: SerializersModule?,
    className: String?,
    id: String?,
    content: @Composable ITabulator<T>.() -> Unit
) {
    val component = remember {
        Tabulator(
            data,
            options,
            className,
            id,
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
        set(data?.toList()) { updateProperty(Tabulator<T>::data, it) }
        set(options) { updateProperty(Tabulator<T>::options, it) }
        set(className) { updateProperty(Tabulator<T>::className, it) }
        set(id) { updateProperty(Tabulator<T>::id, it) }
    }, content)
}

/**
 * Create [Tabulator] component with a Kotlin data model, returning a reference.
 *
 * @param data the tabulator data
 * @param options the tabulator options
 * @param types the set of table types
 * @param serializer the serializer for the data
 * @param serializersModule the serializers module
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 * @return the [Tabulator] component
 */
@Composable
public inline fun <reified T : Any> IComponent.tabulatorRef(
    data: List<T>,
    options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    serializer: KSerializer<T> = serializer(),
    serializersModule: SerializersModule? = null,
    className: String? = null,
    id: String? = null,
    noinline content: @Composable ITabulator<T>.() -> Unit = {}
): Tabulator<T> {
    val classes = types.joinToString(" ") { it.value }
    return tabulatorIntRef(
        data,
        options,
        getClass<T>(),
        serializer,
        serializersModule,
        className % classes,
        id,
        content
    )
}

/**
 * Create [Tabulator] component with a dynamic data model.
 *
 * @param data the tabulator data
 * @param options the tabulator options
 * @param types the set of table types
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 * @return the [Tabulator] component
 */
@Composable
public fun IComponent.tabulatorRef(
    data: List<JsAny>? = null,
    options: TabulatorOptions<JsAny> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    className: String? = null,
    id: String? = null,
    content: @Composable ITabulator<JsAny>.() -> Unit = {}
): Tabulator<JsAny> {
    val classes = types.joinToString(" ") { it.value }
    return tabulatorIntRef(
        data,
        options,
        null,
        null,
        null,
        className % classes,
        id,
        content
    )
}

/**
 * Create [Tabulator] component with a Kotlin data model.
 *
 * @param data the tabulator data
 * @param options the tabulator options
 * @param types the set of table types
 * @param serializer the serializer for the data
 * @param serializersModule the serializers module
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 */
@Composable
public inline fun <reified T : Any> IComponent.tabulator(
    data: List<T>,
    options: TabulatorOptions<T> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    serializer: KSerializer<T> = serializer(),
    serializersModule: SerializersModule? = null,
    className: String? = null,
    id: String? = null,
    noinline content: @Composable ITabulator<T>.() -> Unit = {}
) {
    val classes = types.joinToString(" ") { it.value }
    tabulatorInt(
        data,
        options,
        getClass<T>(),
        serializer,
        serializersModule,
        className % classes,
        id,
        content
    )
}

/**
 * Create [Tabulator] component with a dynamic data model.
 * @param data the tabulator data
 * @param options the tabulator options
 * @param types the set of table types
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 */
@Composable
public fun IComponent.tabulator(
    data: List<JsAny>? = null,
    options: TabulatorOptions<JsAny> = TabulatorOptions(),
    types: Set<TableType> = setOf(),
    className: String? = null,
    id: String? = null,
    content: @Composable ITabulator<JsAny>.() -> Unit = {}
) {
    val classes = types.joinToString(" ") { it.value }
    tabulatorInt(
        data,
        options,
        null,
        null,
        null,
        className % classes,
        id,
        content
    )
}

/*
 * Get class reference for a reified type.
 * Workaround https://github.com/JetBrains/compose-multiplatform/issues/3147
 */
public inline fun <reified T : Any> getClass(): KClass<T> {
    return T::class
}
