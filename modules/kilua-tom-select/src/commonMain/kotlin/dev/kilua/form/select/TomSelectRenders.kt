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

import dev.kilua.externals.TomSelectRendersJs
import dev.kilua.externals.obj
import web.JsAny

/**
 * Tom Select rendering options.
 */
public data class TomSelectRenders(
    val option: ((data: JsAny, escape: (String) -> String) -> String)? = null,
    val item: ((data: JsAny, escape: (String) -> String) -> String)? = null,
    val optionCreate: ((data: JsAny, escape: (String) -> String) -> String)? = null,
    val noResults: ((data: JsAny, escape: (String) -> String) -> String)? = null,
    val notLoading: ((data: JsAny, escape: (String) -> String) -> String)? = null,
    val optGroup: ((data: JsAny) -> String)? = null,
    val optGroupHeader: ((JsAny, escape: (String) -> String) -> String)? = null,
    val loading: ((data: JsAny, escape: (String) -> String) -> String)? = null,
    val dropdown: (() -> String)? = null,
)

/**
 * Convert [TomSelectRenders] to native JS object [TomSelectRendersJs]
 */
public fun TomSelectRenders.toJs(): TomSelectRendersJs {
    val self = this
    return obj {
        if (self.option != null) this.option = self.option
        if (self.item != null) this.item = self.item
        if (self.optionCreate != null) this.option_create = self.optionCreate
        this.no_results = self.noResults
        if (self.notLoading != null) this.not_loading = self.notLoading
        if (self.optGroup != null) this.optgroup = self.optGroup
        if (self.optGroupHeader != null) this.optgroup_header = self.optGroupHeader
        if (self.loading != null) this.loading = self.loading
        if (self.dropdown != null) this.dropdown = self.dropdown
    }
}
