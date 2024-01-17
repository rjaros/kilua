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

import dev.kilua.externals.TomSelectCallbacksJs
import dev.kilua.externals.obj
import web.JsAny
import web.JsArray

/**
 * Tom Select callback functions.
 */
public data class TomSelectCallbacks(
    val load: ((query: String, callback: (JsArray<JsAny>) -> Unit) -> Unit)? = null,
    val shouldLoad: ((query: String) -> Boolean)? = null,
    val score: ((search: String) -> (JsAny) -> Int)? = null,
    val onInitialize: (() -> Unit)? = null,
    val onFocus: (() -> Unit)? = null,
    val onBlur: (() -> Unit)? = null,
    val onChange: ((value: JsAny) -> Unit)? = null,
    val onItemAdd: ((value: JsAny, item: JsAny) -> Unit)? = null,
    val onItemRemove: ((value: JsAny) -> Unit)? = null,
    val onClear: (() -> Unit)? = null,
    val onDelete: ((value: JsAny, event: JsAny) -> Unit)? = null,
    val onOptionAdd: ((value: JsAny, data: JsAny) -> Unit)? = null,
    val onOptionRemove: ((value: JsAny) -> Unit)? = null,
    val onDropdownOpen: ((dropdown: JsAny) -> Unit)? = null,
    val onDropdownClose: ((dropdown: JsAny) -> Unit)? = null,
    val onType: ((str: String) -> Unit)? = null,
    val onLoad: ((options: JsAny, optgroup: JsAny) -> Unit)? = null,
)

/**
 * Convert [TomSelectCallbacks] to native JS object [TomSelectCallbacksJs]
 */
public fun TomSelectCallbacks.toJs(): TomSelectCallbacksJs {
    val self = this
    return obj {
        if (self.load != null) this.load = self.load
        if (self.shouldLoad != null) this.shouldLoad = self.shouldLoad
        if (self.score != null) this.score = self.score
        if (self.onInitialize != null) this.onInitialize = self.onInitialize
        if (self.onFocus != null) this.onFocus = self.onFocus
        if (self.onBlur != null) this.onBlur = self.onBlur
        if (self.onChange != null) this.onChange = self.onChange
        if (self.onItemAdd != null) this.onItemAdd = self.onItemAdd
        if (self.onItemRemove != null) this.onItemRemove = self.onItemRemove
        if (self.onClear != null) this.onClear = self.onClear
        if (self.onDelete != null) this.onDelete = self.onDelete
        if (self.onOptionAdd != null) this.onOptionAdd = self.onOptionAdd
        if (self.onOptionRemove != null) this.onOptionRemove = self.onOptionRemove
        if (self.onDropdownOpen != null) this.onDropdownOpen = self.onDropdownOpen
        if (self.onDropdownClose != null) this.onDropdownClose = self.onDropdownClose
        if (self.onType != null) this.onType = self.onType
        if (self.onLoad != null) this.onLoad = self.onLoad
    }
}
