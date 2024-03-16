@file:Suppress("EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE")
@file:JsModule("tabulator-tables")
/*
 * Copyright (c) 2024 Robert Jaros
 *
 * Permission is hereby granted, free of charge, to JsAny person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF JsAny KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR JsAny CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.kilua.externals

import dev.kilua.utils.JsModule
import dev.kilua.utils.JsName
import web.JsAny
import web.JsArray
import web.Promise
import web.dom.HTMLElement

/**
 * JavaScript Tabulator component.
 */
@JsName("TabulatorFull")
public open external class TabulatorJs(element: HTMLElement, options: JsAny?) : JsAny {
    public val columnManager: JsAny
    public val rowManager: JsAny
    public val footerManager: JsAny
    public val browser: String
    public val browserSlow: Boolean
    public val modules: JsAny
    public val options: JsAny
    public val element: HTMLElement

    public fun download(
        downloadType: JsAny,
        fileName: String?,
        params: JsAny?,
        filter: String
    )

    public fun downloadToTab(
        downloadType: String,
        fileName: String?,
        params: JsAny?,
        filter: String?
    )

    public fun copyToClipboard(rowRangeLookup: String)
    public fun undo(): Boolean
    public fun getHistoryUndoSize(): JsAny
    public fun redo(): Boolean
    public fun getHistoryRedoSize(): JsAny
    public fun getEditedCells(): JsArray<CellComponent>
    public fun clearCellEdited(clear: JsAny)
    public fun destroy()
    public fun import(importer: JsAny, extensions: String)
    public fun setData(data: JsAny?, params: JsAny?, config: JsAny?): Promise<JsAny>
    public fun clearData()
    public fun getData(activeOnly: String?): JsArray<JsAny>
    public fun getDataCount(activeOnly: String? /* 'active' | 'visible' */): Int
    public fun searchRows(
        field: String,
        type: String /* '=' | '!=' | 'like' | '<' | '>' | '<=' | '>=' | 'in' | 'regex' | 'starts' | 'ends' */,
        value: JsAny
    ): JsArray<RowComponent>

    public fun searchData(
        field: String,
        type: String /* '=' | '!=' | 'like' | '<' | '>' | '<=' | '>=' | 'in' | 'regex' | 'starts' | 'ends' */,
        value: JsAny
    ): JsArray<JsAny>

    public fun getHtml(
        rowRangeLookup: String /* 'visible' | 'active' | 'selected' | 'all' */,
        style: Boolean,
        config: JsAny?
    ): String?

    public fun print(
        rowRangeLookup: String /* 'visible' | 'active' | 'selected' | 'all' */,
        style: Boolean,
        config: JsAny?
    )

    public fun getAjaxUrl(): String
    public fun replaceData(data: JsAny /* JsArray<JsAny> | String */, params: JsAny?, config: JsAny?): Promise<JsAny>

    public fun updateData(data: JsArray<JsAny>): Promise<JsAny>
    public fun addData(
        data: JsArray<JsAny>,
        addToTop: Boolean,
        positionTarget: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */
    ): Promise<RowComponent>

    public fun updateOrAddData(data: JsArray<JsAny>): Promise<JsArray<RowComponent>>
    public fun getRow(row: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */): RowComponent

    public fun getRowFromPosition(position: Int): RowComponent
    public fun deleteRow(index: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> | JsArray<JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */> */)
    public fun addRow(
        data: JsAny,
        addToTop: Boolean,
        positionTarget: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */
    ): Promise<RowComponent>

    public fun updateOrAddRow(
        row: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */,
        data: JsAny
    ): Promise<RowComponent>

    public fun updateRow(
        row: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */,
        data: JsAny
    ): Boolean

    public fun scrollToRow(
        row: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */,
        position: String? /* 'top' | 'center' | 'bottom' | 'nearest' */,
        ifVisible: Boolean?
    ): Promise<JsAny>

    public fun moveRow(
        fromRow: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */,
        toRow: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */,
        placeAboveTarget: Boolean
    )

    public fun getRows(activeOnly: String /* 'active' | 'visible' */): JsArray<RowComponent>
    public fun getRowPosition(
        row: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */
    ): JsAny

    public fun setColumns(definitions: JsArray<JsAny>)
    public fun getColumns(includeColumnGroups: Boolean): JsArray<ColumnComponent>
    public fun getColumn(column: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */): ColumnComponent

    public fun getColumnDefinitions(): JsArray<JsAny>
    public fun getColumnLayout(): JsArray<JsAny>
    public fun setColumnLayout(layout: JsAny)
    public fun showColumn(column: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */)

    public fun hideColumn(column: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */)

    public fun toggleColumn(column: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */)

    public fun addColumn(
        definition: JsAny,
        insertRightOfTarget: Boolean?,
        positionTarget: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */
    ): Promise<JsAny>

    public fun deleteColumn(column: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */): Promise<JsAny>

    public fun moveColumn(
        fromColumn: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */,
        toColumn: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */,
        after: Boolean
    )

    public fun scrollToColumn(
        column: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */,
        position: String /* 'left' | 'center' | 'middle' | 'right' */,
        ifVisible: Boolean
    ): Promise<JsAny>

    public fun updateColumnDefinition(
        column: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */,
        definition: JsAny
    ): Promise<JsAny>

    public fun setLocale(locale: JsAny /* String | Boolean */)
    public fun getLocale(): String
    public fun getLang(locale: String): JsAny
    public fun redraw(force: Boolean)
    public fun blockRedraw()
    public fun restoreRedraw()
    public fun setHeight(height: JsAny /* JsNumber | String */)
    public fun setSort(
        sortList: JsAny /* String | JsArray<Tabulator.Sorter> */,
        dir: String /* 'asc' | 'desc' */
    )

    public fun getSorters(): JsArray<JsAny>
    public fun clearSort()
    public fun setFilter(
        p1: JsAny /* String | JsArray<Tabulator.Filter> | JsArray<JsAny> | (data: JsAny, filterParams: JsAny):Boolean */,
        p2: JsAny /* '=' | '!=' | 'like' | '<' | '>' | '<=' | '>=' | 'in' | 'regex' | 'starts' | 'ends' | JsAny */,
        value: JsAny?,
        filterParams: JsAny?
    )

    public fun addFilter(): JsAny
    public fun getFilters(includeHeaderFilters: Boolean): JsArray<JsAny>
    public fun setHeaderFilterValue(
        column: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */,
        value: String
    )

    public fun setHeaderFilterFocus(column: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */)

    public fun getHeaderFilters(): JsArray<JsAny>
    public fun getHeaderFilterValue(column: JsAny /* ColumnComponent | ColumnDefinition | HTMLElement | String */): String

    public fun removeFilter(): JsAny
    public fun clearFilter(includeHeaderFilters: Boolean)
    public fun clearHeaderFilter()
    public fun selectRow(lookup: JsAny /* JsArray<JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */> | 'all' | 'active' | 'visible' | Boolean */)
    public fun deselectRow(row: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */)
    public fun toggleSelectRow(row: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */)
    public fun getSelectedRows(): JsArray<RowComponent>
    public fun getSelectedData(): JsArray<JsAny>
    public fun setMaxPage(max: Int)
    public fun setPage(page: JsAny /* JsNumber | 'first' | 'prev' | 'next' | 'last' */): Promise<JsAny>
    public fun setPageToRow(row: JsAny /* RowComponent | HTMLElement | String | JsNumber | JsArray<JsNumber> | JsArray<String> */): Promise<JsAny>
    public fun setPageSize(size: Int)
    public fun getPageSize(): Int
    public fun previousPage(): Promise<JsAny>
    public fun nextPage(): Promise<JsAny>
    public fun getPage(): JsAny
    public fun getPageMax(): JsAny
    public fun setGroupBy(groups: JsAny /* String | (data: JsAny):JsAny */)
    public fun setGroupStartOpen(values: JsAny /* Boolean | (value: JsAny, count: JsNumber, data: JsAny, group: Tabulator.GroupComponent):Boolean */)
    public fun setGroupHeader(values: JsAny /* (value: JsAny, count: JsNumber, data: JsAny, group: Tabulator.GroupComponent):String | JsArray<(value: JsAny, count: JsNumber, data: JsAny):String> */)
    public fun getGroups(): JsArray<GroupComponent>
    public fun getGroupedData(activeOnly: Boolean): JsAny
    public fun getCalcResults(): JsAny
    public fun recalc()
    public fun navigatePrev()
    public fun navigateNext()
    public fun navigateLeft()
    public fun navigateRight()
    public fun navigateUp()
    public fun navigateDown()
    public fun getInvalidCells(): JsArray<CellComponent>
    public fun clearCellValidation(clearType: JsAny /* Tabulator.CellComponent | JsArray<Tabulator.CellComponent> */)
    public fun validate(): JsAny
    public fun refreshFilters(): JsAny
    public fun clearHistory(): JsAny

    public fun setGroupValues(data: JsAny)
    public fun on(event: String, callback: () -> Unit)
    public fun on(event: String, callback: (JsAny) -> Unit)
    public fun on(event: String, callback: (JsAny, JsAny) -> Unit)
    public fun on(event: String, callback: (JsAny, JsAny, JsAny) -> Unit)
    public fun on(event: String, callback: (JsAny, JsAny, JsAny, JsAny) -> Unit)
    public fun off(event: String, callback: () -> Unit)
    public fun off(event: String, callback: (JsAny) -> Unit)
    public fun off(event: String, callback: (JsAny, JsAny) -> Unit)
    public fun off(event: String, callback: (JsAny, JsAny, JsAny) -> Unit)
    public fun off(event: String, callback: (JsAny, JsAny, JsAny, JsAny) -> Unit)
    public fun alert(message: String, style: String)
    public fun clearAlert()
    public fun addRange(topLeft: JsAny, bottomRight: JsAny): JsAny
    public fun getRanges(): JsAny
    public fun getRangesData(): JsAny
}

/**
 * JavaScript Tabulator row component.
 */
public external class RowComponent : JsAny {
    public fun getData(): JsAny
    public fun getElement(): HTMLElement
    public fun getTable(): TabulatorJs
    public fun getCells(): JsArray<CellComponent>
    public fun getCell(column: JsAny): CellComponent
    public fun getNextRow(): JsAny
    public fun getPrevRow(): JsAny
    public fun getIndex(): JsAny
    public fun getPosition(): JsAny
    public fun getGroup(): GroupComponent
    public fun delete(): Promise<JsAny>
    public fun scrollTo(): Promise<JsAny>
    public fun pageTo(): Promise<JsAny>
    public fun move(lookup: JsAny, belowTarget: Boolean)
    public fun update(data: JsAny): Promise<JsAny>
    public fun select()
    public fun deselect()
    public fun toggleSelect()
    public fun isSelected(): Boolean
    public fun normalizeHeight()
    public fun reformat()
    public fun freeze()
    public fun unfreeze()
    public fun treeExpand()
    public fun treeCollapse()
    public fun treeToggle()
    public fun getTreeParent(): JsAny
    public fun getTreeChildren(): JsArray<RowComponent>
    public fun addTreeChild(rowData: JsAny, position: Boolean, existingRow: RowComponent)
    public fun validate(): JsAny
    public fun isFrozen(): Boolean
    public fun isTreeExpanded(): Boolean
}

/**
 * JavaScript Tabulator group component.
 */
public external class GroupComponent : JsAny {
    public fun getElement(): HTMLElement
    public fun getTable(): TabulatorJs
    public fun getKey(): JsAny
    public fun getField(): String
    public fun getRows(): JsArray<RowComponent>
    public fun getSubGroups(): JsArray<GroupComponent>
    public fun getParentGroup(): JsAny
    public fun isVisible(): Boolean
    public fun show()
    public fun hide()
    public fun toggle()
}

/**
 * JavaScript Tabulator column component.
 */
public external class ColumnComponent : JsAny {
    public fun getElement(): HTMLElement
    public fun getTable(): TabulatorJs
    public fun getDefinition(): JsAny
    public fun getField(): String
    public fun getCells(): JsArray<CellComponent>
    public fun getNextColumn(): JsAny
    public fun getPrevColumn(): JsAny
    public fun move(toColumn: JsAny, after: Boolean)
    public fun isVisible(): Boolean
    public fun show()
    public fun hide()
    public fun toggle()
    public fun delete(): Promise<JsAny>
    public fun scrollTo(): Promise<JsAny>
    public fun getSubColumns(): JsArray<ColumnComponent>
    public fun getParentColumn(): JsAny
    public fun headerFilterFocus()
    public fun setHeaderFilterValue(value: JsAny)
    public fun reloadHeaderFilter()
    public fun getHeaderFilterValue(): JsAny
    public fun updateDefinition(definition: JsAny): Promise<JsAny>
    public fun getWidth(): Int
    public fun setWidth(width: JsAny)
    public fun validate(): JsAny
}

/**
 * JavaScript Tabulator cell component.
 */
public external class CellComponent : JsAny {
    public fun getValue(): JsAny?
    public fun getOldValue(): JsAny?
    public fun restoreOldValue(): JsAny?
    public fun getInitialValue(): JsAny?
    public fun restoreInitialValue(): JsAny?
    public fun getElement(): HTMLElement
    public fun getTable(): TabulatorJs
    public fun getRow(): RowComponent
    public fun getColumn(): ColumnComponent
    public fun getData(): JsAny
    public fun getField(): String
    public fun setValue(value: JsAny, mutate: Boolean)
    public fun checkHeight()
    public fun edit(ignoreEditable: Boolean)
    public fun cancelEdit()
    public fun navigatePrev(): Boolean
    public fun navigateNext(): Boolean
    public fun navigateLeft(): Boolean
    public fun navigateRight(): Boolean
    public fun navigateUp()
    public fun navigateDown()
    public fun isEdited(): Boolean
    public fun clearEdited()
    public fun isValid(): JsAny
    public fun clearValidation()
    public fun validate(): Boolean
    public fun getType(): String
}
