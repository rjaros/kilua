@file:JsModule("split.js")

/*
 * Copyright (c) 2023 Robert Jaros
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

import web.JsAny
import web.dom.HTMLElement

internal external class SplitJsOptionsExt : JsAny {
    var sizes: Array<Number>
    var direction: String
    var gutterSize: Int = definedExternally
    var gutterAlign: String? = definedExternally
    var minSize: Int = definedExternally
    var maxSize: Int? = definedExternally
    var expandToMin: Boolean? = definedExternally
    var snapOffset: Int = definedExternally
    var dragInterval: Int? = definedExternally
    var gutter: (index: Int, direction: String) -> HTMLElement = definedExternally
    var onDrag: (sizes: Array<Number>, index: Int) -> Unit = definedExternally
    var onDragStart: (sizes: Array<Number>, index: Int) -> Unit = definedExternally
    var onDragEnd: (sizes: Array<Number>, index: Int) -> Unit = definedExternally
}

@JsName("default")
internal external fun splitJsExt(elements: Array<HTMLElement>, options: SplitJsOptionsExt): SplitJsInstance
