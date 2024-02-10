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

import dev.kilua.externals.CellComponent
import dev.kilua.externals.ColumnComponent
import dev.kilua.externals.RowComponent
import dev.kilua.externals.toJsAny
import dev.kilua.utils.jsObjectOf
import dev.kilua.utils.toJsArray
import dev.kilua.utils.toKebabCase
import web.JsAny
import web.JsArray
import web.Promise
import web.dom.Element
import web.toJsString

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
public enum class Formatter(internal val formatter: String? = null) {
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
    ResponsiveCollapseAuto("responsiveCollapseAuto");

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
public enum class Validator(internal val validator: kotlin.String? = null) {
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
    Regex;

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
public enum class Filter(internal val filter: String) {
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
public enum class Layout(internal val layout: String) {
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
 * Download config options.
 */
public data class DownloadConfig(
    val columnGroups: Boolean? = null,
    val rowGroups: Boolean? = null,
    val columnCalcs: Boolean? = null
)

/**
 * An extension function to convert download config class to JS object.
 */
internal fun DownloadConfig.toJs(): JsAny {
    return jsObjectOf(
        "columnGroups" to columnGroups,
        "rowGroups" to rowGroups,
        "columnCalcs" to columnCalcs
    )
}

/**
 * Column definition options.
 */
public data class ColumnDefinition(
    val title: String,
    val field: String? = null,
    val columns: List<ColumnDefinition>? = null,
    val visible: Boolean? = null,
    val align: Align? = null,
    val width: String? = null,
    val minWidth: Int? = null,
    val widthGrow: Int? = null,
    val widthShrink: Int? = null,
    val resizable: JsAny? = null,
    val frozen: Boolean? = null,
    val responsive: Int? = null,
    val tooltip: JsAny? = null,
    val cssClass: String? = null,
    val rowHandle: Boolean? = null,
    val hideInHtml: Boolean? = null,
    val sorter: Sorter? = null,
    val sorterFunction: ((
        a: JsAny, b: JsAny, aRow: RowComponent, bRow: RowComponent,
        column: ColumnComponent, dir: String, sorterParams: JsAny?
    ) -> Number)? = null,
    val sorterParams: JsAny? = null,
    val formatter: Formatter? = null,
    val formatterFunction: ((
        cell: CellComponent, formatterParams: JsAny?,
        onRendered: (callback: () -> Unit) -> Unit
    ) -> JsAny)? = null,
    val formatterParams: JsAny? = null,
    val variableHeight: Boolean? = null,
    val editable: ((cell: CellComponent) -> Boolean)? = null,
    val editor: Editor? = null,
    val editorFunction: ((
        cell: CellComponent,
        onRendered: (callback: () -> Unit) -> Unit,
        success: (value: JsAny) -> Unit, cancel: (value: JsAny) -> Unit, editorParams: JsAny?
    ) -> JsAny)? = null,
    val editorParams: JsAny? = null,
    val validator: Validator? = null,
    val validatorFunction: JsAny? = null,
    val validatorParams: String? = null,
    val download: JsAny? = null,
    val downloadTitle: String? = null,
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
    val headerVertical: Boolean? = null,
    val editableTitle: Boolean? = null,
    val titleFormatter: Formatter? = null,
    val titleFormatterFunction: ((
        cell: CellComponent, formatterParams: JsAny?,
        onRendered: (callback: () -> Unit) -> Unit
    ) -> JsAny)? = null,
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
    val print: JsAny? = null,
    val formatterPrint: ((
        cell: CellComponent,
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
    val mutatorData: ((value: JsAny, data: JsAny, type: String, params: JsAny, cell: CellComponent) -> Any)? = null,
    val mutatorDataParams: JsAny? = null,
    val mutatorEdit: ((value: JsAny, data: JsAny, type: String, params: JsAny, cell: CellComponent) -> Any)? = null,
    val mutatorEditParams: JsAny? = null,
    val mutatorClipboard: ((value: JsAny, data: JsAny, type: String, params: JsAny, cell: CellComponent) -> Any)? = null,
    val mutatorClipboardParams: JsAny? = null,
    val mutator: ((value: JsAny, data: JsAny, type: String, params: JsAny, cell: CellComponent) -> Any)? = null,
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
internal fun ColumnDefinition.toJs(): JsAny {
    return jsObjectOf(
        "title" to title,
        "field" to field,
        "columns" to columns?.map { it.toJs() }?.toJsArray(),
        "visible" to visible,
        "align" to align?.value,
        "width" to width,
        "minWidth" to minWidth,
        "widthGrow" to widthGrow,
        "widthShrink" to widthShrink,
        "resizable" to resizable,
        "frozen" to frozen,
        "responsive" to responsive,
        "tooltip" to tooltip,
        "cssClass" to cssClass,
        "rowHandle" to rowHandle,
        "hideInHtml" to hideInHtml,
        "sorter" to (sorterFunction?.let { toJsAny(it) } ?: sorter?.value),
        "sorterParams" to sorterParams,
        "formatter" to (formatterFunction?.let { toJsAny(it) } ?: formatter?.value),
        "formatterParams" to formatterParams,
        "variableHeight" to variableHeight,
        "editable" to editable?.let { toJsAny(it) },
        "editor" to (editorFunction?.let { toJsAny(it) } ?: editor?.value),
        "editorParams" to editorParams,
        "validator" to (validatorFunction ?: validator?.value),
        "validatorParams" to validatorParams,
        "download" to download,
        "downloadTitle" to downloadTitle,
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
        "headerTooltip" to headerTooltip,
        "headerVertical" to headerVertical,
        "editableTitle" to editableTitle,
        "titleFormatter" to (titleFormatterFunction?.let { toJsAny(it) } ?: titleFormatter?.value),
        "titleFormatterParams" to titleFormatterParams,
        "headerFilter" to (headerFilterCustom?.let { toJsAny(it) } ?: headerFilter?.value),
        "headerFilterParams" to headerFilterParams,
        "headerFilterPlaceholder" to headerFilterPlaceholder,
        "headerFilterEmptyCheck" to headerFilterEmptyCheck?.let { toJsAny(it) },
        "headerFilterFunc" to (headerFilterFuncCustom?.let { toJsAny(it) } ?: headerFilterFunc?.value),
        "headerFilterFuncParams" to headerFilterFuncParams,
        "headerFilterLiveFilter" to headerFilterLiveFilter,
        "htmlOutput" to htmlOutput,
        "print" to print,
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
        "dblClickMenu" to dblClickMenu
    )
}

/**
 * Tabulator options.
 */
public data class TabulatorOptions(
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
    val columns: List<ColumnDefinition>? = null,
    val autoColumns: Boolean? = null,
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
    val selectable: JsAny? = null,
    val selectableRangeMode: RangeMode? = null,
    val selectableRollingSelection: Boolean? = null,
    val selectablePersistence: Boolean? = null,
    val selectableCheck: ((row: RowComponent) -> Boolean)? = null,
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
    val dataTreeStartExpanded: ((row: RowComponent, level: Number) -> Boolean)? = null,
    val ajaxRequesting: ((url: String, params: JsAny) -> Boolean)? = null,
    val ajaxResponse: ((url: String, params: JsAny, response: JsAny) -> Any)? = null,
    val persistence: JsAny? = null,
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
    val columnDefaults: ColumnDefinition? = null,
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
    val responsiveLayoutCollapseFormatter: ((data: Array<JsAny>) -> Element)? = null,
)

/**
 * An extension function to convert column definition class to JS object.
 */
internal fun TabulatorOptions.toJs(): JsAny {
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
        "columns" to columns?.map { it.toJs() }?.toJsArray(),
        "autoColumns" to (autoColumns ?: (columns == null)),
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
        "selectable" to selectable,
        "selectableRangeMode" to selectableRangeMode?.value,
        "selectableRollingSelection" to selectableRollingSelection,
        "selectablePersistence" to selectablePersistence,
        "selectableCheck" to selectableCheck?.let { toJsAny(it) },
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
        "persistence" to persistence,
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
        "columnDefaults" to columnDefaults?.toJs(),
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
        "responsiveLayoutCollapseFormatter" to responsiveLayoutCollapseFormatter?.let { toJsAny(it) }
    )
}
