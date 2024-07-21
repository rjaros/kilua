@file:Suppress("EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE")
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

import dev.kilua.dom.JsAny

/**
 * Trix editor locale strings.
 */
@Suppress("PropertyName", "VariableNaming")
public open external class TrixLocale : JsAny {
    public var GB: String
    public var KB: String
    public var MB: String
    public var PB: String
    public var TB: String
    public var bold: String
    public var bullets: String
    public var byte: String
    public var bytes: String
    public var captionPlaceholder: String
    public var code: String
    public var heading1: String
    public var indent: String
    public var italic: String
    public var link: String
    public var numbers: String
    public var outdent: String
    public var quote: String
    public var redo: String
    public var remove: String
    public var strike: String
    public var undo: String
    public var unlink: String
    public var urlPlaceholder: String
    public var url: String
    public var attachFiles: String
}
