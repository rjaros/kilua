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

import dev.kilua.externals.TomSelectOptionsJs
import dev.kilua.utils.StringPair
import dev.kilua.utils.cast
import dev.kilua.utils.jsObjectOf
import dev.kilua.utils.toJsAny
import js.core.JsAny
import js.core.JsPrimitives.toJsBoolean
import js.core.JsPrimitives.toJsString
import js.objects.jso
import web.html.HTMLElement

/**
 * Tom Select options.
 */
public data class TomSelectOptions(
    val create: Boolean? = null,
    val createFun: ((input: String, callback: (StringPair) -> Unit) -> Unit)? = null,
    val createOnBlur: Boolean? = null,
    val createFilter: String? = null,
    val highlight: Boolean? = null,
    val persist: Boolean? = null,
    val openOnFocus: Boolean? = null,
    val maxItems: Int? = null,
    val hideSelected: Boolean? = null,
    val closeAfterSelect: Boolean? = null,
    val loadThrottle: Int? = 300,
    val loadingClass: String? = null,
    val hidePlaceholder: Boolean? = null,
    val preload: Boolean? = null,
    val preloadOnFocus: Boolean? = null,
    val addPrecedence: Boolean? = null,
    val selectOnTab: Boolean? = null,
    val duplicates: Boolean? = null,
    val controlInput: HTMLElement? = null,
    val hideControlInput: Boolean? = null,
    val caretPosition: Boolean? = null,
    val checkboxOptions: Boolean? = null,
    val clearButtonTitle: String? = null,
    val dropdownHeaderTitle: String? = null,
    val dropdownInput: Boolean? = null,
    val inputAutogrow: Boolean? = null,
    val noActiveItems: Boolean? = null,
    val noBackspaceDelete: Boolean? = null,
    val removeButtonTitle: String? = null,
    val restoreOnBackspace: Boolean? = null,
    val options: List<JsAny>? = null,
    val dataAttr: String? = null,
    val valueField: String? = null,
    val labelField: String? = null,
    val disabledField: String? = null,
    val sortField: String? = null,
    val searchField: List<String>? = null,
    val searchConjunction: String? = null
)

public fun TomSelectOptions.toJs(emptyOption: Boolean): TomSelectOptionsJs {
    val self = this
    val createTemp: JsAny? = if (createFun != null) {
        { input: String, callback: (JsAny) -> Unit ->
            createFun.invoke(input) {
                callback(
                    jsObjectOf(
                        "value" to it.first,
                        "text" to it.second
                    )
                )
            }
        }.cast()
    } else {
        create?.toJsBoolean()
    }
    val plugins = jsObjectOf(
        "change_listener" to jso(),
        "caret_position" to if (caretPosition != null) jso() else null,
        "checkbox_options" to if (checkboxOptions != null) jso() else null,
        "clear_button" to if (clearButtonTitle != null) mapOf(
            "title" to clearButtonTitle
        ) else null,
        "dropdown_header" to if (dropdownHeaderTitle != null) mapOf(
            "title" to dropdownHeaderTitle
        ) else null,
        "dropdown_input" to if (dropdownInput != null) jso() else null,
        "input_autogrow" to if (inputAutogrow != null) jso() else null,
        "no_active_items" to if (noActiveItems != null) jso() else null,
        "no_backspace_delete" to if (noBackspaceDelete != null) jso() else null,
        "remove_button" to if (removeButtonTitle != null) mapOf(
            "title" to removeButtonTitle
        ) else null,
        "restore_on_backspace" to if (restoreOnBackspace != null) jso() else null
    )
    return jso {
        if (createTemp != null) this.create = createTemp
        if (self.createOnBlur != null) this.createOnBlur = self.createOnBlur
        if (self.createFilter != null) this.createFilter = self.createFilter
        if (self.highlight != null) this.highlight = self.highlight
        if (self.persist != null) this.persist = self.persist
        if (self.openOnFocus != null) this.openOnFocus = self.openOnFocus
        if (self.maxItems != null) this.maxItems = self.maxItems
        if (self.hideSelected != null) this.hideSelected = self.hideSelected
        if (self.closeAfterSelect != null) this.closeAfterSelect = self.closeAfterSelect
        this.loadThrottle = self.loadThrottle
        if (self.loadingClass != null) this.loadingClass = self.loadingClass
        if (self.hidePlaceholder != null) this.hidePlaceholder = self.hidePlaceholder
        if (self.preload != null) {
            this.preload = self.preload.toJsBoolean()
        } else if (self.preloadOnFocus == true) {
            this.preload = "focus".toJsString()
        }
        if (self.addPrecedence != null) this.addPrecedence = self.addPrecedence
        if (self.selectOnTab != null) this.selectOnTab = self.selectOnTab
        if (self.duplicates != null) this.duplicates = self.duplicates
        if (self.hideControlInput == true) {
            this.controlInput = null
        } else if (self.controlInput != null) {
            this.controlInput = self.controlInput
        }
        this.plugins = plugins
        if (self.options != null) this.options = if (emptyOption) (listOf(
            jsObjectOf(
                "value" to "",
                "text" to "\u00a0"
            )
        ) + self.options).toJsAny().cast() else self.options.toJsAny().cast()
        if (self.dataAttr != null) this.dataAttr = self.dataAttr
        if (self.valueField != null) this.valueField = self.valueField
        if (self.labelField != null) this.labelField = self.labelField
        if (self.disabledField != null) this.disabledField = self.disabledField
        if (self.sortField != null) this.sortField = self.sortField
        if (self.searchField != null) this.searchField = self.searchField.toJsAny().cast()
        if (self.searchConjunction != null) this.searchConjunction = self.searchConjunction
    }
}
