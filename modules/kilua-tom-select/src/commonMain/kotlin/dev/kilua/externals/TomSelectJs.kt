@file:Suppress("EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE")
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

package dev.kilua.externals

import js.import.JsModule
import js.core.JsAny
import js.core.JsString
import web.html.HTMLElement

/**
 * Tom Select renders configuration.
 */
public external class TomSelectRendersJs : JsAny {
    public var option: ((data: JsAny, escape: (String) -> String) -> String)
    public var item: ((data: JsAny, escape: (String) -> String) -> String)
    public var option_create: ((data: JsAny, escape: (String) -> String) -> String)
    public var no_results: ((data: JsAny, escape: (String) -> String) -> String)?
    public var not_loading: ((data: JsAny, escape: (String) -> String) -> String)
    public var optgroup: ((data: JsAny) -> String)
    public var optgroup_header: ((JsAny, escape: (String) -> String) -> String)
    public var loading: ((data: JsAny, escape: (String) -> String) -> String)
    public var dropdown: (() -> String)
}

/**
 * Tom Select callbacks configuration.
 */
public external class TomSelectCallbacksJs : JsAny {
    public var load: ((query: String, callback: (JsArray<JsAny>) -> Unit) -> Unit)
    public var shouldLoad: ((query: String) -> Boolean)
    public var score: ((search: String) -> (JsAny) -> Int)
    public var onInitialize: (() -> Unit)
    public var onFocus: (() -> Unit)
    public var onBlur: (() -> Unit)
    public var onChange: ((value: JsAny) -> Unit)
    public var onItemAdd: ((value: JsAny, item: JsAny) -> Unit)
    public var onItemRemove: ((value: JsAny) -> Unit)
    public var onClear: (() -> Unit)
    public var onDelete: ((value: JsAny, event: JsAny) -> Unit)
    public var onOptionAdd: ((value: JsAny, data: JsAny) -> Unit)
    public var onOptionRemove: ((value: JsAny) -> Unit)
    public var onDropdownOpen: ((dropdown: JsAny) -> Unit)
    public var onDropdownClose: ((dropdown: JsAny) -> Unit)
    public var onType: ((str: String) -> Unit)
    public var onLoad: ((options: JsAny, optgroup: JsAny) -> Unit)
}

/**
 * Tom Select options.
 */
public external class TomSelectOptionsJs : JsAny {
    public var create: JsAny?
    public var createFun: ((input: String, callback: (JsAny) -> Unit) -> Unit)
    public var createOnBlur: Boolean
    public var createFilter: String
    public var highlight: Boolean
    public var persist: Boolean
    public var openOnFocus: Boolean
    public var maxItems: Int?
    public var maxOptions: Int?
    public var allowEmptyOption: Boolean
    public var options: JsArray<JsAny>
    public var controlInput: HTMLElement?
    public var plugins: JsAny
    public var load: ((query: String, callback: (JsArray<JsAny>) -> Unit) -> Unit)
    public var render: TomSelectRendersJs
    public var hideSelected: Boolean
    public var closeAfterSelect: Boolean
    public var loadThrottle: Int?
    public var loadingClass: String
    public var hidePlaceholder: Boolean
    public var preload: JsAny
    public var addPrecedence: Boolean
    public var selectOnTab: Boolean
    public var duplicates: Boolean
    public var caretPosition: Boolean
    public var checkboxOptions: Boolean
    public var clearButtonTitle: String
    public var dropdownHeaderTitle: String
    public var dropdownInput: Boolean
    public var inputAutogrow: Boolean
    public var noActiveItems: Boolean
    public var noBackspaceDelete: Boolean
    public var removeButtonTitle: String
    public var restoreOnBackspace: Boolean
    public var dataAttr: String
    public var valueField: String
    public var labelField: String
    public var disabledField: String
    public var sortField: String
    public var searchField: JsArray<JsString>
    public var searchConjunction: String
    public var onOptionAdd: ((value: String, data: JsAny) -> Unit)
    public var onOptionRemove: ((value: JsAny) -> Unit)
    public var onFocus: (() -> Unit)
    public var onBlur: (() -> Unit)
}

/**
 * Native Tom Select class.
 */
@JsModule("tom-select")
@kotlin.js.JsName("TomSelect")
public external class TomSelectJs(element: HTMLElement, options: TomSelectOptionsJs) : JsAny {
    public fun addOption(value: JsAny, userCreated: Boolean)
    public fun addOptions(value: JsArray<JsAny>, userCreated: Boolean)
    public fun updateOption(value: String, data: JsAny)
    public fun removeOption(value: String)
    public fun getOption(value: String, create: Boolean): JsAny?
    public fun refreshOptions(triggerDropdown: Boolean)
    public fun clearOptions(clearFilter: (JsAny) -> Boolean)

    public fun clear(silent: Boolean)
    public fun getItem(value: String)
    public fun addItem(value: String, silent: Boolean)
    public fun removeItem(value: String, silent: Boolean)
    public fun createItem(value: String, callback: (JsAny) -> Unit)
    public fun refreshItems()

    public fun addOptionGroup(id: String, data: JsAny)
    public fun removeOptionGroup(id: String)
    public fun clearOptionGroups()

    public fun on(event: String, handler: JsAny)
    public fun on(event: String, handler: () -> Unit)
    public fun on(event: String, handler: (JsAny) -> Unit)
    public fun off(event: String, handler: JsAny)
    public fun off(event: String)
    public fun trigger(event: String)

    public fun open()
    public fun close()
    public fun positionDropdown()

    public fun load(query: String)
    public fun focus()
    public fun blur()
    public fun lock()
    public fun unlock()
    public fun disable()
    public fun enable()
    public fun getValue(): JsAny?
    public fun setValue(value: JsAny?, silent: Boolean)
    public fun setCaret(index: Int)
    public fun isFull(): Boolean
    public fun clearCache()
    public fun setTextboxValue(value: String)
    public fun sync()
    public fun destroy()

    public val options: JsAny
}
