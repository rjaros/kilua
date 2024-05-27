/*
 * Copyright (c) 2024-present Robert Jaros
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
import dev.kilua.compose.Root
import dev.kilua.compose.root
import dev.kilua.core.Component
import dev.kilua.core.IComponent
import dev.kilua.core.SafeDomFactory
import dev.kilua.externals.CellComponent
import dev.kilua.externals.CellComponentBase
import dev.kilua.externals.ColumnComponent
import dev.kilua.externals.RowComponent
import dev.kilua.externals.TabulatorMenuItem
import dev.kilua.externals.get
import dev.kilua.externals.obj
import dev.kilua.externals.set
import dev.kilua.externals.toJsAny
import dev.kilua.externals.undefined
import dev.kilua.utils.cast
import dev.kilua.utils.jsObjectOf
import dev.kilua.utils.toJsArray
import dev.kilua.utils.toKebabCase
import dev.kilua.utils.toList
import dev.kilua.utils.unsafeCast
import web.JsAny
import web.JsArray
import web.JsNumber
import web.Promise
import web.document
import web.dom.Element
import web.dom.HTMLElement
import web.dom.asList
import web.dom.events.Event
import web.localStorage
import web.toJsBoolean
import web.toJsNumber
import web.toJsString
import web.window
import kotlin.reflect.KClass

/**
 * Column align.
 */
public enum class Align {
    Left,
    Center,
    Right;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Column align.
 */
public enum class VAlign {
    Top,
    Middle,
    Bottom;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Built-in sorters.
 */
public enum class Sorter {
    String,
    Number,
    Alphanum,
    Boolean,
    Exists,
    Date,
    Time,
    Datetime,
    Array;

    public val value: kotlin.String = name.toKebabCase()
    override fun toString(): kotlin.String {
        return value
    }
}

/**
 * Built-in formatters.
 */
public enum class Formatter(public val formatter: String? = null) {
    Plaintext,
    Textarea,
    Html,
    Money,
    Image,
    Link,
    Datetime,
    Datetimediff,
    TickCross("tickCross"),
    Color,
    Star,
    Traffic,
    Progress,
    Lookup,
    ButtonTick("buttonTick"),
    ButtonCross("buttonCross"),
    Rownum,
    Handle,
    RowSelection("rowSelection"),
    ResponsiveCollapse("responsiveCollapse"),
    ResponsiveCollapseAuto("responsiveCollapseAuto"),
    Toggle;

    public val value: String = formatter ?: name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Built-in editors.
 */
public enum class Editor {
    Input,
    Textarea,
    Number,
    Range,
    Tick,
    Star,
    List,
    Date,
    Time,
    Datetime;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Built-in validators.
 */
public enum class Validator(public val validator: kotlin.String? = null) {
    Required,
    Unique,
    Integer,
    Float,
    Numeric,
    String,
    Min,
    Max,
    MinLength("minLength"),
    MaxLength("maxLength"),
    In,
    Regex,
    Alphanumeric;

    public val value: kotlin.String = validator ?: name.toKebabCase()
    override fun toString(): kotlin.String {
        return value
    }
}

/**
 * Built-in calc functions.
 */
public enum class Calc {
    Avg,
    Max,
    Min,
    Sum,
    Concat,
    Count,
    Unique;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Sorting directions.
 */
public enum class SortingDir {
    Asc,
    Desc;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Filters.
 */
public enum class Filter(public val filter: String) {
    Equal("="),
    NotEqual("!="),
    Like("like"),
    Less("<"),
    LessEq("<="),
    Greater(">"),
    GreaterEq(">="),
    I("in"),
    Regex("regex");

    public val value: String = filter
    override fun toString(): String {
        return value
    }
}

/**
 * Table layouts.
 */
public enum class Layout(public val layout: String) {
    FitData("fitData"),
    FitDataFill("fitDataFill"),
    FitColumns("fitColumns"),
    FitDataStretch("fitDataStretch"),
    FitDataTable("fitDataTable");

    public val value: String = layout
    override fun toString(): String {
        return value
    }
}

/**
 * Responsive layout modes.
 */
public enum class ResponsiveLayout {
    Hide,
    Collapse;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Column positions.
 */
public enum class ColumnPosition {
    Middle,
    Left,
    Right;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Row scroll positions .
 */
public enum class RowPosition {
    Bottom,
    Top,
    Center,
    Nearest;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Row positions.
 */
public enum class RowPos {
    Bottom,
    Top;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Range select modes.
 */
public enum class RangeMode {
    Click;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Progressive modes.
 */
public enum class ProgressiveMode {
    Load,
    Scroll;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Pagination modes.
 */
public enum class PaginationMode {
    Local,
    Remote;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Sort modes.
 */
public enum class SortMode {
    Local,
    Remote;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Filter modes.
 */
public enum class FilterMode {
    Local,
    Remote;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Add row modes.
 */
public enum class AddRowMode {
    Table,
    Page;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Text direction.
 */
public enum class TextDirection {
    Auto,
    Ltr,
    Rtl;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Virtual DOM options.
 */
public enum class RenderType {
    Basic,
    Virtual;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Import formats.
 */
public enum class ImportFormat {
    Json,
    Csv,
    Array;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Import readers.
 */
public enum class ImportReader {
    Text,
    Buffer,
    Binary,
    Url;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Possible header sort click elements.
 */
public enum class HeaderSortClickElement {
    Header,
    Icon;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Possible edit trigger events.
 */
public enum class EditTriggerEvent {
    Focus,
    Click,
    Dblclick;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}


/**
 * Download config options.
 */
public data class DownloadConfig(
    val columnGroups: Boolean? = null,
    val rowGroups: Boolean? = null,
    val columnCalcs: Boolean? = null,
    val rowHeaders: Boolean? = null
)

/**
 * An extension function to convert download config class to JS object.
 */
internal fun DownloadConfig.toJs(): JsAny {
    return jsObjectOf(
        "columnGroups" to columnGroups,
        "rowGroups" to rowGroups,
        "columnCalcs" to columnCalcs,
        "rowHeaders" to rowHeaders
    )
}

/**
 * Column definition options.
 */
public data class ColumnDefinition<T : Any>(
    val title: String,
    val field: String? = null,
    val columns: List<ColumnDefinition<T>>? = null,
    val visible: Boolean? = null,
    val align: Align? = null,
    val width: String? = null,
    val minWidth: Int? = null,
    val widthGrow: Int? = null,
    val widthShrink: Int? = null,
    val resizable: JsAny? = null,
    val resizableBool: Boolean? = null,
    val frozen: Boolean? = null,
    val responsive: Int? = null,
    val tooltip: JsAny? = null,
    val tooltipString: String? = null,
    val cssClass: String? = null,
    val rowHandle: Boolean? = null,
    val hideInHtml: Boolean? = null,
    val sorter: Sorter? = null,
    val sorterFunction: ((
        a: JsAny, b: JsAny, aRow: RowComponent, bRow: RowComponent,
        column: ColumnComponent, dir: String, sorterParams: JsAny?
    ) -> Double)? = null,
    val sorterParams: JsAny? = null,
    val formatter: Formatter? = null,
    val formatterFunction: ((
        cell: CellComponentBase, formatterParams: JsAny?,
        onRendered: (callback: () -> Unit) -> Unit
    ) -> JsAny)? = null,
    val formatterStringFunction: ((
        cell: CellComponentBase, formatterParams: JsAny?,
        onRendered: (callback: () -> Unit) -> Unit
    ) -> String)? = null,
    val formatterComponentFunction: (@Composable IComponent.(
        cell: CellComponentBase, onRendered: (callback: () -> Unit) -> Unit, data: T
    ) -> Unit)? = null,
    val formatterParams: JsAny? = null,
    val variableHeight: Boolean? = null,
    val editable: ((cell: CellComponent) -> Boolean)? = null,
    val editor: Editor? = null,
    val editorFunction: ((
        cell: CellComponent,
        onRendered: (callback: () -> Unit) -> Unit,
        success: (value: JsAny?) -> Unit, cancel: (value: JsAny?) -> Unit, editorParams: JsAny?
    ) -> JsAny)? = null,
    val editorComponentFunction: (@Composable IComponent.(
        cell: CellComponent,
        onRendered: (callback: () -> Unit) -> Unit,
        success: (value: JsAny?) -> Unit, cancel: (value: JsAny?) -> Unit, data: T
    ) -> Unit)? = null,
    val editorParams: JsAny? = null,
    val validator: Validator? = null,
    val validatorFunction: JsAny? = null,
    val validatorParams: String? = null,
    val download: ((column: ColumnComponent) -> Boolean)? = null,
    val titleDownload: String? = null,
    val topCalc: Calc? = null,
    val topCalcParams: JsAny? = null,
    val topCalcFormatter: Formatter? = null,
    val topCalcFormatterParams: JsAny? = null,
    val bottomCalc: Calc? = null,
    val bottomCalcParams: JsAny? = null,
    val bottomCalcFormatter: Formatter? = null,
    val bottomCalcFormatterParams: JsAny? = null,
    val headerSort: Boolean? = null,
    val headerSortStartingDir: SortingDir? = null,
    val headerSortTristate: Boolean? = null,
    val headerClick: ((e: JsAny, column: ColumnComponent) -> Unit)? = null,
    val headerDblClick: ((e: JsAny, column: ColumnComponent) -> Unit)? = null,
    val headerContext: ((e: JsAny, column: ColumnComponent) -> Unit)? = null,
    val headerTap: ((e: JsAny, column: ColumnComponent) -> Unit)? = null,
    val headerDblTap: ((e: JsAny, column: ColumnComponent) -> Unit)? = null,
    val headerTapHold: ((e: JsAny, column: ColumnComponent) -> Unit)? = null,
    val headerTooltip: JsAny? = null,
    val headerTooltipString: String? = null,
    val headerVertical: Boolean? = null,
    val editableTitle: Boolean? = null,
    val titleFormatter: Formatter? = null,
    val titleFormatterFunction: ((
        cell: CellComponentBase, formatterParams: JsAny?,
        onRendered: (callback: () -> Unit) -> Unit
    ) -> JsAny)? = null,
    val titleFormatterStringFunction: ((
        cell: CellComponentBase, formatterParams: JsAny?,
        onRendered: (callback: () -> Unit) -> Unit
    ) -> String)? = null,
    val titleFormatterComponentFunction: (@Composable IComponent.(
        cell: CellComponentBase, onRendered: (callback: () -> Unit) -> Unit
    ) -> Unit)? = null,
    val titleFormatterParams: JsAny? = null,
    val headerFilter: Editor? = null,
    val headerFilterParams: JsAny? = null,
    val headerFilterCustom: ((
        cell: CellComponent,
        onRendered: (callback: () -> Unit) -> Unit,
        success: (value: JsAny) -> Unit, cancel: (value: JsAny) -> Unit, editorParams: JsAny?
    ) -> JsAny)? = null,
    val headerFilterPlaceholder: String? = null,
    val headerFilterEmptyCheck: ((value: JsAny) -> Boolean)? = null,
    val headerFilterFunc: Filter? = null,
    val headerFilterFuncCustom: ((headerValue: JsAny, rowValue: JsAny, rowData: JsAny, filterParams: JsAny) -> Boolean)? = null,
    val headerFilterFuncParams: JsAny? = null,
    val headerFilterLiveFilter: Boolean? = null,
    val htmlOutput: JsAny? = null,
    val htmlOutputBool: Boolean? = null,
    val print: JsAny? = null,
    val printBool: Boolean? = null,
    val formatterPrint: ((
        cell: CellComponentBase,
        formatterParams: JsAny?,
        onRendered: (callback: () -> Unit) -> Unit
    ) -> JsAny)? = null,
    val formatterPrintParams: JsAny? = null,
    val cellClick: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellDblClick: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellContext: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellTap: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellDblTap: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellTapHold: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellMouseEnter: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellMouseLeave: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellMouseOver: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellMouseOut: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellMouseMove: ((e: JsAny, cell: CellComponent) -> Unit)? = null,
    val cellEditing: ((cell: CellComponent) -> Unit)? = null,
    val cellEdited: ((cell: CellComponent) -> Unit)? = null,
    val cellEditCancelled: ((cell: CellComponent) -> Unit)? = null,
    val headerMenu: JsAny? = null,
    val headerContextMenu: JsAny? = null,
    val contextMenu: JsAny? = null,
    val hozAlign: Align? = null,
    val vertAlign: VAlign? = null,
    val clickMenu: JsAny? = null,
    val headerHozAlign: Align? = null,
    val accessor: JsAny? = null,
    val accessorParams: JsAny? = null,
    val maxWidth: Int? = null,
    val mutatorData: ((value: JsAny, data: JsAny, type: String, params: JsAny, cell: CellComponent) -> JsAny)? = null,
    val mutatorDataParams: JsAny? = null,
    val mutatorEdit: ((value: JsAny, data: JsAny, type: String, params: JsAny, cell: CellComponent) -> JsAny)? = null,
    val mutatorEditParams: JsAny? = null,
    val mutatorClipboard: ((value: JsAny, data: JsAny, type: String, params: JsAny, cell: CellComponent) -> JsAny)? = null,
    val mutatorClipboardParams: JsAny? = null,
    val mutator: ((value: JsAny, data: JsAny, type: String, params: JsAny, cell: CellComponent) -> JsAny)? = null,
    val mutatorParams: JsAny? = null,
    val maxInitialWidth: Int? = null,
    val cellPopup: JsAny? = null,
    val headerPopup: JsAny? = null,
    val headerPopupIcon: JsAny? = null,
    val headerContextPopup: JsAny? = null,
    val clickPopup: JsAny? = null,
    val contextPopup: JsAny? = null,
    val headerMenuIcon: JsAny? = null,
    val headerWordWrap: Boolean? = null,
    val dblClickPopup: JsAny? = null,
    val headerClickPopup: JsAny? = null,
    val headerDblClickPopup: JsAny? = null,
    val headerClickMenu: JsAny? = null,
    val headerDblClickMenu: JsAny? = null,
    val dblClickMenu: JsAny? = null,
    val headerColumnsMenu: Boolean? = null,
    val headerColumnsMenuTitle: String? = null,
    val headerColumnsMenuResetTitle: String? = null,
)

/**
 * An extension function to convert column definition class to JS object.
 */
internal fun <T : Any> ColumnDefinition<T>.toJs(
    tabulator: Tabulator<T>,
    kClass: KClass<T>?
): JsAny {
    val tmpFormatterFunction = if (formatterComponentFunction != null) {
        { cell: CellComponentBase, _: JsAny?, onRendered: (callback: () -> Unit) -> Unit ->
            var onRenderedCallback: (() -> Unit)? = null
            val data: T = if (kClass != null) {
                tabulator.toKotlinObj(cell.getData())
            } else {
                cell.getData().cast()
            }
            val rootElement = SafeDomFactory.createElement("div").unsafeCast<HTMLElement>()
            if (onRendered != undefined()) {
                onRendered {
                    val root = root(rootElement, false, tabulator.renderConfig) {
                        formatterComponentFunction.invoke(this, cell, { callback ->
                            onRenderedCallback = callback
                        }, data)
                    }
                    tabulator.addCustomRoot(root)
                    if (cell["checkHeight"] != undefined()) cell.unsafeCast<CellComponent>().checkHeight()
                    (rootElement.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowX = "visible"
                    (rootElement.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowY = "visible"
                    onRenderedCallback?.invoke()
                }
            }
            rootElement
        }
    } else if (formatterStringFunction != null) {
        { cell: CellComponentBase, formatterParams: JsAny?, onRendered: (callback: () -> Unit) -> Unit ->
            formatterStringFunction.invoke(cell, formatterParams, onRendered).toJsString()
        }
    } else formatterFunction

    val tmpTitleFormatterFunction = if (titleFormatterComponentFunction != null) {
        { cell: CellComponentBase, _: JsAny?, onRendered: (callback: () -> Unit) -> Unit ->
            var onRenderedCallback: (() -> Unit)? = null
            val rootElement = SafeDomFactory.createElement("div").unsafeCast<HTMLElement>()
            if (onRendered != undefined()) {
                onRendered {
                    val root = root(rootElement, false, tabulator.renderConfig) {
                        titleFormatterComponentFunction.invoke(this, cell) { callback ->
                            onRenderedCallback = callback
                        }
                    }
                    tabulator.addCustomRoot(root)
                    (rootElement.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowX = "visible"
                    (rootElement.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowY = "visible"
                    (rootElement.parentElement?.parentElement?.parentElement?.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowX =
                        "visible"
                    (rootElement.parentElement?.parentElement?.parentElement?.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowY =
                        "visible"
                    (rootElement.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowX =
                        "visible"
                    (rootElement.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowY =
                        "visible"
                    (rootElement.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowX =
                        "visible"
                    (rootElement.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowY =
                        "visible"
                    onRenderedCallback?.invoke()
                }
            }
            rootElement
        }
    } else if (titleFormatterStringFunction != null) {
        { cell: CellComponentBase, formatterParams: JsAny?, onRendered: (callback: () -> Unit) -> Unit ->
            titleFormatterStringFunction.invoke(cell, formatterParams, onRendered).toJsString()
        }
    } else titleFormatterFunction

    val tmpEditorFunction = if (editorComponentFunction != null) {
        { cell: CellComponent,
          onRendered: (callback: () -> Unit) -> Unit,
          success: (value: JsAny?) -> Unit, cancel: (value: JsAny?) -> Unit, _: JsAny? ->
            if (cell.getElement()["style"] != null) cell.getElement().unsafeCast<HTMLElement>().style["overflow"] =
                "visible".toJsString()
            var onRenderedCallback: (() -> Unit)? = null
            val data: T = if (kClass != null) {
                tabulator.toKotlinObj(cell.getData())
            } else {
                cell.getData().cast()
            }
            val rootElement = SafeDomFactory.createElement("div").unsafeCast<HTMLElement>()
            if (onRendered != undefined()) {
                onRendered {
                    if (EditorRoot.root != null) {
                        EditorRoot.disposeTimer?.let { window.clearTimeout(it) }
                        EditorRoot.root?.dispose()
                    }
                    EditorRoot.root = root(rootElement, false, tabulator.renderConfig) {
                        editorComponentFunction.invoke(this, cell, { callback ->
                            onRenderedCallback = callback
                        }, { value ->
                            success(value)
                            EditorRoot.disposeTimer = window.setTimeout({
                                EditorRoot.root?.dispose()
                                EditorRoot.disposeTimer = null
                                EditorRoot.root = null
                                EditorRoot.cancel = null
                                null
                            }, 500)
                        }, cancel, data)
                    }
                    EditorRoot.cancel = cancel
                    if (cell["checkHeight"] != undefined()) cell.checkHeight()
                    (rootElement.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowX = "visible"
                    (rootElement.parentElement?.unsafeCast<HTMLElement>())?.style?.overflowY = "visible"
                    onRenderedCallback?.invoke()
                }
            }
            rootElement
        }
    } else editorFunction

    val tmpHeaderColumnsMenu: ((Event) -> JsArray<JsAny>)? = if (this.headerColumnsMenu == true) {
        { _ ->
            val resetTitle = this.headerColumnsMenuResetTitle ?: "Default columns"
            fun resetColumns() {
                val persistenceID =
                    tabulator.tabulatorJs?.options?.get("persistenceID")?.let { "tabulator-$it" } ?: "tabulator"
                localStorage.removeItem("$persistenceID-columns")
                window.location.reload()
            }

            val columns = tabulator.tabulatorJs?.getColumns(false)?.toList()?.filter {
                !it.getDefinition()["title"]?.toString().isNullOrEmpty()
            }?.map {
                val responsiveHiddenColumns =
                    (tabulator.tabulatorJs?.modules?.get("responsiveLayout")?.get("hiddenColumns")
                        ?.unsafeCast<JsArray<ColumnComponent>>())?.toList()?.map {
                            it.getField()
                        } ?: emptyList()
                val icon = SafeDomFactory.createElement("i")
                icon.classList.add("far")
                icon.classList.add(if (!it.isVisible() && !responsiveHiddenColumns.contains(it.getField())) "fa-square" else "fa-check-square")
                val label = SafeDomFactory.createElement("span")
                val title = SafeDomFactory.createElement("span")
                title.textContent = " " + it.getDefinition()["title"]
                label.appendChild(icon)
                label.appendChild(title)
                obj<TabulatorMenuItem> {
                    this.label = label
                    this.action = { e: Event ->
                        e.stopPropagation()
                        if (it.isVisible()) {
                            it.hide()
                            icon.classList.remove("fa-check-square")
                            icon.classList.add("fa-square")
                        } else if (responsiveHiddenColumns.contains(it.getField())) {
                            it.show()
                            it.hide()
                            icon.classList.remove("fa-check-square")
                            icon.classList.add("fa-square")
                        } else {
                            it.show()
                            icon.classList.remove("fa-square")
                            icon.classList.add("fa-check-square")
                        }
                        tabulator.tabulatorJs?.redraw(true)
                    }
                }
            } ?: emptyList()
            (columns + listOf(obj {
                separator = true
            }, obj {
                val icon = SafeDomFactory.createElement("i")
                icon.classList.add("fas")
                icon.classList.add("fa-rotate")
                val label = SafeDomFactory.createElement("span")
                val title = SafeDomFactory.createElement("span")
                title.textContent = " $resetTitle"
                label.appendChild(icon)
                label.appendChild(title)
                this.label = label
                this.action = { e: Event ->
                    e.stopPropagation()
                    resetColumns()
                }
            })).toJsArray()
        }
    } else null
    val headerColumnsMenuTitle = this.headerColumnsMenuTitle ?: "Customize"
    val responsiveCollapseAuto = this.formatter == Formatter.ResponsiveCollapseAuto

    val responsiveCollapseOptions = if (responsiveCollapseAuto) {
        val headerClick: (JsAny) -> Boolean = {
            val columnsOpened = document.querySelectorAll("div.tabulator-responsive-collapse-toggle")
                .asList().firstOrNull()?.let {
                    (it.unsafeCast<HTMLElement>()).classList.contains("open")
                } ?: false
            if (columnsOpened) {
                document.querySelectorAll("div.tabulator-responsive-collapse-toggle.open").asList()
                    .forEach {
                        (it.unsafeCast<HTMLElement>()).click()
                    }
            } else {
                document.querySelectorAll("div.tabulator-responsive-collapse-toggle:not(.open)").asList()
                    .forEach {
                        (it.unsafeCast<HTMLElement>()).click()
                    }
            }
            true
        }
        listOf(
            "formatter" to "responsiveCollapse",
            "titleFormatter" to "tickCross",
            "titleFormatterParams" to jsObjectOf(
                "crossElement" to "<i class='fas fa-arrows-up-down'></i>"
            ),
            "width" to "40",
            "headerSort" to false,
            "responsive" to 0,
            "headerHozAlign" to "center",
            "headerClick" to toJsAny(headerClick)
        )
    } else listOf(
        "formatter" to (tmpFormatterFunction?.let { toJsAny(it) } ?: formatter?.value),
        "formatterParams" to formatterParams,
    )

    val headerMenuOptions = if (tmpHeaderColumnsMenu != null) {
        listOf(
            "headerHozAlign" to "center",
            "headerMenu" to toJsAny(tmpHeaderColumnsMenu),
            "headerMenuIcon" to "<i class='far fa-square-caret-down'></i> $headerColumnsMenuTitle"
        )
    } else emptyList()

    return jsObjectOf(
        "title" to title,
        "field" to field,
        "columns" to columns?.map { it.toJs(tabulator, kClass) }?.toJsArray(),
        "visible" to visible,
        "align" to align?.value,
        "width" to width,
        "minWidth" to minWidth,
        "widthGrow" to widthGrow,
        "widthShrink" to widthShrink,
        "resizable" to (resizableBool?.toJsBoolean() ?: resizable),
        "frozen" to frozen,
        "responsive" to responsive,
        "tooltip" to (tooltipString?.toJsString() ?: tooltip),
        "cssClass" to cssClass,
        "rowHandle" to rowHandle,
        "hideInHtml" to hideInHtml,
        "sorter" to (sorterFunction?.let { f ->
            toJsAny { a: JsAny, b: JsAny, aRow: RowComponent, bRow: RowComponent, column: ColumnComponent, dir: String, sorterParams: JsAny? ->
                f(a, b, aRow, bRow, column, dir, sorterParams).toJsNumber()
            }
        } ?: sorter?.value),
        "sorterParams" to sorterParams,
        "variableHeight" to variableHeight,
        "editable" to editable?.let { toJsAny(it) },
        "editor" to (tmpEditorFunction?.let { toJsAny(it) } ?: editor?.value),
        "editorParams" to editorParams,
        "validator" to (validatorFunction ?: validator?.value),
        "validatorParams" to validatorParams,
        "download" to download?.let { toJsAny(it) },
        "titleDownload" to titleDownload,
        "topCalc" to topCalc?.value,
        "topCalcParams" to topCalcParams,
        "topCalcFormatter" to topCalcFormatter?.value,
        "topCalcFormatterParams" to topCalcFormatterParams,
        "bottomCalc" to bottomCalc?.value,
        "bottomCalcParams" to bottomCalcParams,
        "bottomCalcFormatter" to bottomCalcFormatter?.value,
        "bottomCalcFormatterParams" to bottomCalcFormatterParams,
        "headerSort" to headerSort,
        "headerSortStartingDir" to headerSortStartingDir?.value,
        "headerSortTristate" to headerSortTristate,
        "headerClick" to headerClick?.let { toJsAny(it) },
        "headerDblClick" to headerDblClick?.let { toJsAny(it) },
        "headerContext" to headerContext?.let { toJsAny(it) },
        "headerTap" to headerTap?.let { toJsAny(it) },
        "headerDblTap" to headerDblTap?.let { toJsAny(it) },
        "headerTapHold" to headerTapHold?.let { toJsAny(it) },
        "headerTooltip" to (headerTooltipString?.toJsString() ?: headerTooltip),
        "headerVertical" to headerVertical,
        "editableTitle" to editableTitle,
        "titleFormatter" to (tmpTitleFormatterFunction?.let { toJsAny(it) } ?: titleFormatter?.value),
        "titleFormatterParams" to titleFormatterParams,
        "headerFilter" to (headerFilterCustom?.let { toJsAny(it) } ?: headerFilter?.value),
        "headerFilterParams" to headerFilterParams,
        "headerFilterPlaceholder" to headerFilterPlaceholder,
        "headerFilterEmptyCheck" to headerFilterEmptyCheck?.let { toJsAny(it) },
        "headerFilterFunc" to (headerFilterFuncCustom?.let { toJsAny(it) } ?: headerFilterFunc?.value),
        "headerFilterFuncParams" to headerFilterFuncParams,
        "headerFilterLiveFilter" to headerFilterLiveFilter,
        "htmlOutput" to (htmlOutputBool?.toJsBoolean() ?: htmlOutput),
        "print" to (printBool?.toJsBoolean() ?: print),
        "formatterPrint" to formatterPrint?.let { toJsAny(it) },
        "formatterPrintParams" to formatterPrintParams,
        "cellClick" to cellClick?.let { toJsAny(it) },
        "cellDblClick" to cellDblClick?.let { toJsAny(it) },
        "cellContext" to cellContext?.let { toJsAny(it) },
        "cellTap" to cellTap?.let { toJsAny(it) },
        "cellDblTap" to cellDblTap?.let { toJsAny(it) },
        "cellTapHold" to cellTapHold?.let { toJsAny(it) },
        "cellMouseEnter" to cellMouseEnter?.let { toJsAny(it) },
        "cellMouseLeave" to cellMouseLeave?.let { toJsAny(it) },
        "cellMouseOver" to cellMouseOver?.let { toJsAny(it) },
        "cellMouseOut" to cellMouseOut?.let { toJsAny(it) },
        "cellMouseMove" to cellMouseMove?.let { toJsAny(it) },
        "cellEditing" to cellEditing?.let { toJsAny(it) },
        "cellEdited" to cellEdited?.let { toJsAny(it) },
        "cellEditCancelled" to cellEditCancelled?.let { toJsAny(it) },
        "headerMenu" to headerMenu,
        "headerContextMenu" to headerContextMenu,
        "contextMenu" to contextMenu,
        "hozAlign" to hozAlign?.value,
        "vertAlign" to vertAlign?.value,
        "clickMenu" to clickMenu,
        "headerHozAlign" to headerHozAlign?.value,
        "accessor" to accessor,
        "accessorParams" to accessorParams,
        "maxWidth" to maxWidth,
        "mutatorData" to mutatorData?.let { toJsAny(it) },
        "mutatorDataParams" to mutatorDataParams,
        "mutatorEdit" to mutatorEdit?.let { toJsAny(it) },
        "mutatorEditParams" to mutatorEditParams,
        "mutatorClipboard" to mutatorClipboard?.let { toJsAny(it) },
        "mutatorClipboardParams" to mutatorClipboardParams,
        "mutator" to mutator?.let { toJsAny(it) },
        "mutatorParams" to mutatorParams,
        "maxInitialWidth" to maxInitialWidth,
        "cellPopup" to cellPopup,
        "headerPopup" to headerPopup,
        "headerPopupIcon" to headerPopupIcon,
        "headerContextPopup" to headerContextPopup,
        "clickPopup" to clickPopup,
        "contextPopup" to contextPopup,
        "headerMenuIcon" to headerMenuIcon,
        "headerWordWrap" to headerWordWrap,
        "dblClickPopup" to dblClickPopup,
        "headerClickPopup" to headerClickPopup,
        "headerDblClickPopup" to headerDblClickPopup,
        "headerClickMenu" to headerClickMenu,
        "headerDblClickMenu" to headerDblClickMenu,
        "dblClickMenu" to dblClickMenu,
        *(responsiveCollapseOptions + headerMenuOptions).toTypedArray(),
    )
}

internal object EditorRoot {
    internal var root: Root? = null
    internal var cancel: ((value: JsAny?) -> Unit)? = null
    internal var disposeTimer: Int? = null
}

/**
 * Tabulator options.
 */
public data class TabulatorOptions<T : Any>(
    val height: String? = null,
    val placeholder: String? = null,
    val placeholderFunc: (() -> String?)? = null,
    val placeholderHeaderFilter: String? = null,
    val footerElement: String? = null,
    val history: Boolean? = null,
    val keybindings: JsAny? = null,
    val downloadDataFormatter: JsAny? = null,
    val downloadConfig: DownloadConfig? = null,
    val reactiveData: Boolean? = null,
    val autoResize: Boolean? = null,
    val columns: List<ColumnDefinition<T>>? = null,
    val autoColumns: Boolean? = null,
    val autoColumnsFull: Boolean? = null,
    val layout: Layout? = null,
    val layoutColumnsOnNewData: Boolean? = null,
    val responsiveLayout: ResponsiveLayout? = null,
    val responsiveLayoutCollapseStartOpen: Boolean? = null,
    val responsiveLayoutCollapseUseFormatters: Boolean? = null,
    val movableColumns: Boolean? = null,
    val scrollToColumnPosition: ColumnPosition? = null,
    val scrollToColumnIfVisible: Boolean? = null,
    val rowFormatter: ((row: RowComponent) -> Unit)? = null,
    val addRowPos: RowPos? = null,
    val selectableRows: JsAny? = null,
    val selectableRowsBool: Boolean? = null,
    val selectableRowsRangeMode: RangeMode? = null,
    val selectableRowsRollingSelection: Boolean? = null,
    val selectableRowsPersistence: Boolean? = null,
    val selectableRowsCheck: ((row: RowComponent) -> Boolean)? = null,
    val movableRows: Boolean? = null,
    val movableRowsConnectedTables: JsAny? = null,
    val movableRowsSender: JsAny? = null,
    val movableRowsReceiver: JsAny? = null,
    val resizableRows: Boolean? = null,
    val scrollToRowPosition: RowPosition? = null,
    val scrollToRowIfVisible: Boolean? = null,
    val index: String? = null,
    val data: JsArray<JsAny>? = null,
    val ajaxURL: String? = null,
    val ajaxParams: JsAny? = null,
    val ajaxConfig: JsAny? = null,
    val ajaxContentType: JsAny? = null,
    val ajaxURLGenerator: ((url: String, config: JsAny, params: JsAny) -> String)? = null,
    val ajaxRequestFunc: ((url: String, config: JsAny, params: JsAny) -> Promise<JsAny>)? = null,
    val progressiveLoad: ProgressiveMode? = null,
    val progressiveLoadDelay: Int? = null,
    val progressiveLoadScrollMargin: Int? = null,
    val dataLoader: Boolean? = null,
    val dataLoaderLoading: String? = null,
    val dataLoaderError: String? = null,
    val initialSort: List<Sorter>? = null,
    val sortOrderReverse: Boolean? = null,
    val initialFilter: List<Filter>? = null,
    val initialHeaderFilter: List<JsAny>? = null,
    val pagination: Boolean? = null,
    val paginationMode: PaginationMode? = null,
    val paginationSize: Int? = null,
    val paginationSizeSelector: JsAny? = null,
    val paginationElement: JsAny? = null,
    val dataReceiveParams: JsAny? = null,
    val dataSendParams: JsAny? = null,
    val paginationAddRow: AddRowMode? = null,
    val paginationButtonCount: Int? = null,
    val persistenceID: String? = null,
    val persistenceMode: Boolean? = null,
    val persistentLayout: Boolean? = null,
    val persistentSort: Boolean? = null,
    val persistentFilter: Boolean? = null,
    val locale: String? = null,
    val langs: JsAny? = null,
    val localized: ((locale: String, lang: JsAny) -> Unit)? = null,
    val headerVisible: Boolean? = null,
    val htmlOutputConfig: JsAny? = null,
    val printAsHtml: Boolean? = null,
    val printConfig: JsAny? = null,
    val printCopyStyle: Boolean? = null,
    val printVisibleRows: Boolean? = null,
    val printHeader: String? = null,
    val printFooter: String? = null,
    val printFormatter: ((tableHolder: JsAny, table: JsAny) -> Unit)? = null,
    val tabEndNewRow: JsAny? = null,
    val invalidOptionWarnings: Boolean? = null,
    val dataTree: Boolean? = null,
    val dataTreeChildField: String? = null,
    val dataTreeCollapseElement: JsAny? = null,
    val dataTreeExpandElement: JsAny? = null,
    val dataTreeElementColumn: String? = null,
    val dataTreeBranchElement: JsAny? = null,
    val dataTreeChildIndent: Number? = null,
    val dataTreeStartExpanded: ((row: RowComponent, level: JsNumber) -> Boolean)? = null,
    val ajaxRequesting: ((url: String, params: JsAny) -> Boolean)? = null,
    val ajaxResponse: ((url: String, params: JsAny, response: JsAny) -> JsAny)? = null,
    val persistence: JsAny? = null,
    val persistenceBool: Boolean? = null,
    val persistenceReaderFunc: JsAny? = null,
    val persistenceWriterFunc: JsAny? = null,
    val paginationInitialPage: Int? = null,
    val columnHeaderVertAlign: VAlign? = null,
    val maxHeight: String? = null,
    val minHeight: String? = null,
    val rowContextMenu: JsAny? = null,
    val dataTreeChildColumnCalcs: Boolean? = null,
    val dataTreeSelectPropagate: Boolean? = null,
    val headerFilterLiveFilterDelay: Int? = null,
    val textDirection: TextDirection? = null,
    val autoColumnsDefinitions: JsAny? = null,
    val rowClickMenu: JsAny? = null,
    val headerSortElement: JsAny? = null,
    val dataTreeFilter: Boolean? = null,
    val dataTreeSort: Boolean? = null,
    val renderVertical: RenderType? = null,
    val renderVerticalBuffer: Int? = null,
    val renderHorizontal: RenderType? = null,
    val columnDefaults: ColumnDefinition<T>? = null,
    val sortMode: SortMode? = null,
    val filterMode: FilterMode? = null,
    val importFormat: ImportFormat? = null,
    val importReader: ImportReader? = null,
    val dataLoaderErrorTimeout: Int? = null,
    val popupContainer: JsAny? = null,
    val paginationCounter: JsAny? = null,
    val paginationCounterElement: JsAny? = null,
    val rowClickPopup: JsAny? = null,
    val rowContextPopup: JsAny? = null,
    val resizableColumnFit: Boolean? = null,
    val rowHeight: Int? = null,
    val frozenRows: JsAny? = null,
    val frozenRowsField: String? = null,
    val headerSortClickElement: HeaderSortClickElement? = null,
    val rowDblClickPopup: JsAny? = null,
    val rowDblClickMenu: JsAny? = null,
    val responsiveLayoutCollapseFormatter: ((data: JsArray<JsAny>) -> Element)? = null,
    val selectableRange: JsAny? = null,
    val selectableRangeColumns: Boolean? = null,
    val selectableRangeRows: Boolean? = null,
    val selectableRangeClearCells: Boolean? = null,
    val selectableRangeClearCellsValue: String? = null,
    val editTriggerEvent: EditTriggerEvent? = null,
    val rowHeader: JsAny? = null,
    val spreadsheet: Boolean? = null,
    val spreadsheetColumns: Int? = null,
    val spreadsheetRows: Int? = null,
    val spreadsheetData: JsAny? = null,
    val spreadsheetColumnDefinition: JsAny? = null,
    val spreadsheetOutputFull: Boolean? = null,
    val spreadsheetSheets: JsAny? = null,
    val spreadsheetSheetTabs: Boolean? = null,
    val resizableColumnGuide: Boolean? = null,
    val resizableRowGuide: Boolean? = null,
    val editorEmptyValue: JsAny? = null,
    val editorEmptyValueFunc: ((JsAny) -> Boolean)? = null,
)

/**
 * An extension function to convert column definition class to JS object.
 */
internal fun <T : Any> TabulatorOptions<T>.toJs(
    tabulator: Tabulator<T>,
    kClass: KClass<T>?
): JsAny {
    return jsObjectOf(
        "height" to height,
        "placeholder" to (placeholderFunc?.let { toJsAny(it) } ?: placeholder),
        "placeholderHeaderFilter" to placeholderHeaderFilter,
        "footerElement" to footerElement,
        "history" to history,
        "keybindings" to keybindings,
        "downloadDataFormatter" to downloadDataFormatter,
        "downloadConfig" to downloadConfig?.toJs(),
        "reactiveData" to reactiveData,
        "autoResize" to autoResize,
        "columns" to columns?.map { it.toJs(tabulator, kClass) }?.toJsArray(),
        "autoColumns" to (if (autoColumnsFull == true) {
            "full"
        } else {
            autoColumns ?: (columns == null)
        }),
        "layout" to layout?.value,
        "layoutColumnsOnNewData" to layoutColumnsOnNewData,
        "responsiveLayout" to responsiveLayout?.value,
        "responsiveLayoutCollapseStartOpen" to responsiveLayoutCollapseStartOpen,
        "responsiveLayoutCollapseUseFormatters" to responsiveLayoutCollapseUseFormatters,
        "movableColumns" to movableColumns,
        "scrollToColumnPosition" to scrollToColumnPosition?.value,
        "scrollToColumnIfVisible" to scrollToColumnIfVisible,
        "rowFormatter" to rowFormatter?.let { toJsAny(it) },
        "addRowPos" to addRowPos?.value,
        "selectableRows" to (selectableRowsBool?.toJsBoolean() ?: selectableRows),
        "selectableRowsRangeMode" to selectableRowsRangeMode?.value,
        "selectableRowsRollingSelection" to selectableRowsRollingSelection,
        "selectableRowsPersistence" to selectableRowsPersistence,
        "selectableRowsCheck" to selectableRowsCheck?.let { toJsAny(it) },
        "movableRows" to movableRows,
        "movableRowsConnectedTables" to movableRowsConnectedTables,
        "movableRowsSender" to movableRowsSender,
        "movableRowsReceiver" to movableRowsReceiver,
        "resizableRows" to resizableRows,
        "scrollToRowPosition" to scrollToRowPosition?.value,
        "scrollToRowIfVisible" to scrollToRowIfVisible,
        "index" to index,
        "data" to data,
        "ajaxURL" to ajaxURL,
        "ajaxParams" to ajaxParams,
        "ajaxConfig" to ajaxConfig,
        "ajaxContentType" to ajaxContentType,
        "ajaxURLGenerator" to ajaxURLGenerator?.let { toJsAny(it) },
        "ajaxRequestFunc" to ajaxRequestFunc?.let { toJsAny(it) },
        "progressiveLoad" to progressiveLoad?.value,
        "progressiveLoadDelay" to progressiveLoadDelay,
        "progressiveLoadScrollMargin" to progressiveLoadScrollMargin,
        "dataLoader" to dataLoader,
        "dataLoaderLoading" to dataLoaderLoading,
        "dataLoaderError" to dataLoaderError,
        "initialSort" to initialSort?.map { it.value.toJsString() }?.toJsArray(),
        "sortOrderReverse" to sortOrderReverse,
        "initialFilter" to initialFilter?.map { it.value.toJsString() }?.toJsArray(),
        "initialHeaderFilter" to initialHeaderFilter?.toJsArray(),
        "pagination" to pagination,
        "paginationMode" to paginationMode?.value,
        "paginationSize" to paginationSize,
        "paginationSizeSelector" to paginationSizeSelector,
        "paginationElement" to paginationElement,
        "dataReceiveParams" to dataReceiveParams,
        "dataSendParams" to dataSendParams,
        "paginationAddRow" to paginationAddRow?.value,
        "paginationButtonCount" to paginationButtonCount,
        "persistenceID" to persistenceID,
        "persistenceMode" to persistenceMode,
        "persistentLayout" to persistentLayout,
        "persistentSort" to persistentSort,
        "persistentFilter" to persistentFilter,
        "locale" to locale,
        "langs" to langs,
        "localized" to localized?.let { toJsAny(it) },
        "headerVisible" to headerVisible,
        "htmlOutputConfig" to htmlOutputConfig,
        "printAsHtml" to printAsHtml,
        "printConfig" to printConfig,
        "printCopyStyle" to printCopyStyle,
        "printVisibleRows" to printVisibleRows,
        "printHeader" to printHeader,
        "printFooter" to printFooter,
        "printFormatter" to printFormatter?.let { toJsAny(it) },
        "tabEndNewRow" to tabEndNewRow,
        "invalidOptionWarnings" to invalidOptionWarnings,
        "dataTree" to dataTree,
        "dataTreeChildField" to dataTreeChildField,
        "dataTreeCollapseElement" to dataTreeCollapseElement,
        "dataTreeExpandElement" to dataTreeExpandElement,
        "dataTreeElementColumn" to dataTreeElementColumn,
        "dataTreeBranchElement" to dataTreeBranchElement,
        "dataTreeChildIndent" to dataTreeChildIndent,
        "dataTreeStartExpanded" to dataTreeStartExpanded?.let { toJsAny(it) },
        "ajaxRequesting" to ajaxRequesting?.let { toJsAny(it) },
        "ajaxResponse" to ajaxResponse?.let { toJsAny(it) },
        "persistence" to (persistenceBool?.toJsBoolean() ?: persistence),
        "persistenceReaderFunc" to persistenceReaderFunc,
        "persistenceWriterFunc" to persistenceWriterFunc,
        "paginationInitialPage" to paginationInitialPage,
        "columnHeaderVertAlign" to columnHeaderVertAlign?.value,
        "maxHeight" to maxHeight,
        "minHeight" to minHeight,
        "rowContextMenu" to rowContextMenu,
        "dataTreeChildColumnCalcs" to dataTreeChildColumnCalcs,
        "dataTreeSelectPropagate" to dataTreeSelectPropagate,
        "headerFilterLiveFilterDelay" to headerFilterLiveFilterDelay,
        "textDirection" to textDirection?.value,
        "autoColumnsDefinitions" to autoColumnsDefinitions,
        "rowClickMenu" to rowClickMenu,
        "headerSortElement" to headerSortElement,
        "dataTreeFilter" to dataTreeFilter,
        "dataTreeSort" to dataTreeSort,
        "renderVertical" to renderVertical?.value,
        "renderVerticalBuffer" to renderVerticalBuffer,
        "renderHorizontal" to renderHorizontal?.value,
        "columnDefaults" to columnDefaults?.toJs(tabulator, kClass),
        "sortMode" to sortMode?.value,
        "filterMode" to filterMode?.value,
        "importFormat" to importFormat?.value,
        "importReader" to importReader?.value,
        "dataLoaderErrorTimeout" to dataLoaderErrorTimeout,
        "popupContainer" to popupContainer,
        "paginationCounter" to paginationCounter,
        "paginationCounterElement" to paginationCounterElement,
        "rowClickPopup" to rowClickPopup,
        "rowContextPopup" to rowContextPopup,
        "resizableColumnFit" to resizableColumnFit,
        "rowHeight" to rowHeight,
        "frozenRows" to frozenRows,
        "frozenRowsField" to frozenRowsField,
        "headerSortClickElement" to headerSortClickElement?.value,
        "rowDblClickPopup" to rowDblClickPopup,
        "rowDblClickMenu" to rowDblClickMenu,
        "responsiveLayoutCollapseFormatter" to responsiveLayoutCollapseFormatter?.let { toJsAny(it) },
        "selectableRange" to selectableRange,
        "selectableRangeColumns" to selectableRangeColumns,
        "selectableRangeRows" to selectableRangeRows,
        "selectableRangeClearCells" to selectableRangeClearCells,
        "selectableRangeClearCellsValue" to selectableRangeClearCellsValue,
        "editTriggerEvent" to editTriggerEvent?.value,
        "rowHeader" to rowHeader,
        "spreadsheet" to spreadsheet,
        "spreadsheetColumns" to spreadsheetColumns,
        "spreadsheetRows" to spreadsheetRows,
        "spreadsheetData" to spreadsheetData,
        "spreadsheetColumnDefinition" to spreadsheetColumnDefinition,
        "spreadsheetOutputFull" to spreadsheetOutputFull,
        "spreadsheetSheets" to spreadsheetSheets,
        "spreadsheetSheetTabs" to spreadsheetSheetTabs,
        "resizableColumnGuide" to resizableColumnGuide,
        "resizableRowGuide" to resizableRowGuide,
        "editorEmptyValue" to editorEmptyValue,
        "editorEmptyValueFunc" to editorEmptyValueFunc?.let { toJsAny(it) }
    )
}
