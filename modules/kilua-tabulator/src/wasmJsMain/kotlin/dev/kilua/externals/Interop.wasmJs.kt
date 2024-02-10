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

import web.JsAny
import web.Promise
import web.dom.Element

internal actual fun toJsAny(f: (JsAny, JsAny, RowComponent, RowComponent, ColumnComponent, String, JsAny?) -> Number): JsAny =
    js("f")

internal actual fun toJsAny(f: (CellComponent, JsAny?, (() -> Unit) -> Unit) -> JsAny): JsAny = js("f")
internal actual fun toJsAny(f: (CellComponent, (() -> Unit) -> Unit, (JsAny) -> Unit, (JsAny) -> Unit, JsAny?) -> JsAny): JsAny =
    js("f")

internal actual fun toJsAny(f: (JsAny, ColumnComponent) -> Unit): JsAny = js("f")
internal actual fun toJsAny(f: (CellComponent) -> Boolean): JsAny = js("f")
internal actual fun toJsAny(f: (JsAny, JsAny, JsAny, JsAny) -> Boolean): JsAny = js("f")

internal actual fun toJsAny(f: (JsAny, CellComponent) -> Unit): JsAny = js("f")
internal actual fun toJsAny(f: (CellComponent) -> Unit): JsAny = js("f")
internal actual fun toJsAny(f: (JsAny, JsAny, String, JsAny, CellComponent) -> Any): JsAny = js("f")

internal actual fun toJsAny(f: () -> String?): JsAny = js("f")

internal actual fun toJsAny(f: (RowComponent) -> Unit): JsAny = js("f")

internal actual fun toJsAny(f: (RowComponent) -> Boolean): JsAny = js("f")

internal actual fun toJsAny(f: (String, JsAny, JsAny) -> String): JsAny = js("f")

internal actual fun toJsAny(f: (String, JsAny, JsAny) -> Promise<JsAny>): JsAny = js("f")

internal actual fun toJsAny(f: (String, JsAny) -> Unit): JsAny = js("f")

internal actual fun toJsAny(f: (JsAny, JsAny) -> Unit): JsAny = js("f")

internal actual fun toJsAny(f: (RowComponent, Number) -> Boolean): JsAny = js("f")

internal actual fun toJsAny(f: (String, JsAny) -> Boolean): JsAny = js("f")

internal actual fun toJsAny(f: (String, JsAny, JsAny) -> Any): JsAny = js("f")

internal actual fun toJsAny(f: (Array<JsAny>) -> Element): JsAny = js("f")

internal actual fun toJsAny(f: (JsAny) -> Boolean): JsAny = js("f")
