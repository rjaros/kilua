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

import js.core.JsNumber
import web.html.HTMLElement
import kotlin.js.JsAny
import kotlin.js.JsArray
import kotlin.js.JsModule
import kotlin.js.JsString

internal external class ImaskJs : JsAny {
    var unmaskedValue: String?
    fun on(event: String, callback: () -> Unit)
    fun updateValue()
    fun updateControl(cursorPos: String)
    fun destroy()
}

@JsModule("imask")
internal external object ImaskObjJs : JsAny {
    @kotlin.js.JsName("default")
    fun imask(htmlElement: HTMLElement, imaskOptionsJs: ImaskOptionsJs): ImaskJs

    val MaskedRange: JsAny
    val MaskedEnum: JsAny
}

internal open external class ImaskOptionsJs : JsAny {
    var overwrite: JsAny?
}

internal external class PatternMaskOptionsJs : ImaskOptionsJs {
    var mask: String
    var lazy: Boolean?
    var eager: Boolean?
    var placeholderChar: String?
    var definitions: JsAny?
    var blocks: JsAny?
}

internal external class RangeMaskOptionsJs : ImaskOptionsJs {
    var mask: JsAny
    var from: Int
    var to: Int
    var maxLength: Int?
    var autofix: JsAny?
    var lazy: Boolean?
    var eager: Boolean?
    var placeholderChar: String?
}

internal external class EnumMaskOptionsJs : ImaskOptionsJs {
    var mask: JsAny
    var enum: JsArray<JsString>
    var lazy: Boolean?
    var eager: Boolean?
    var placeholderChar: String?
}


internal external class NumberMaskOptionsJs : ImaskOptionsJs {
    var mask: JsAny
    var scale: Int?
    var thousandsSeparator: String?
    var padFractionalZeros: Boolean?
    var normalizeZeros: Boolean?
    var radix: String?
    var mapToRadix: JsArray<JsString>?
    var min: JsNumber?
    var max: JsNumber?
}

internal external class FunctionMaskOptionsJs : ImaskOptionsJs {
    var mask: (String) -> Boolean
}
