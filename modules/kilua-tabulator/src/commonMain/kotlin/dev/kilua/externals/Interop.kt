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

import dev.kilua.dom.JsAny
import dev.kilua.dom.JsNumber
import dev.kilua.dom.Promise
import dev.kilua.dom.api.Element

/**
 * A set of hrlper functions to convert different Kotlin functions to JavaScript functions (JsAny).
 */

internal expect fun toJsAny(f: (JsAny, JsAny, RowComponent, RowComponent, ColumnComponent, String, JsAny?) -> JsNumber): JsAny

internal expect fun toJsAny(f: (CellComponentBase, JsAny?, (() -> Unit) -> Unit) -> JsAny): JsAny

internal expect fun toJsAny(f: (JsAny, JsAny?, (() -> Unit) -> Unit) -> JsAny): JsAny

internal expect fun toJsAny(f: (CellComponent, (() -> Unit) -> Unit, (JsAny?) -> Unit, (JsAny?) -> Unit, JsAny?) -> JsAny): JsAny

internal expect fun toJsAny(f: (JsAny, ColumnComponent) -> Unit): JsAny

internal expect fun toJsAny(f: (CellComponent) -> Boolean): JsAny

internal expect fun toJsAny(f: (JsAny, JsAny, JsAny, JsAny) -> Boolean): JsAny

internal expect fun toJsAny(f: (JsAny, CellComponent) -> Unit): JsAny

internal expect fun toJsAny(f: (CellComponent) -> Unit): JsAny

internal expect fun toJsAny(f: (JsAny, JsAny, String, JsAny, CellComponent) -> JsAny): JsAny

internal expect fun toJsAny(f: () -> String?): JsAny

internal expect fun toJsAny(f: (RowComponent) -> Unit): JsAny

internal expect fun toJsAny(f: (RowComponent) -> Boolean): JsAny

internal expect fun toJsAny(f: (ColumnComponent) -> Boolean): JsAny

internal expect fun toJsAny(f: (String, JsAny, JsAny) -> String): JsAny

internal expect fun toJsAny(f: (String, JsAny, JsAny) -> Promise<JsAny>): JsAny

internal expect fun toJsAny(f: (String, JsAny) -> Unit): JsAny

internal expect fun toJsAny(f: (JsAny, JsAny) -> Unit): JsAny

internal expect fun toJsAny(f: (RowComponent, JsNumber) -> Boolean): JsAny

internal expect fun toJsAny(f: (String, JsAny) -> Boolean): JsAny

internal expect fun toJsAny(f: (String, JsAny, JsAny) -> JsAny): JsAny

internal expect fun toJsAny(f: (dev.kilua.dom.JsArray<JsAny>) -> Element): JsAny

internal expect fun toJsAny(f: (JsAny) -> Boolean): JsAny

internal expect fun toJsAny(f: (dev.kilua.dom.api.events.Event) -> dev.kilua.dom.JsArray<JsAny>): JsAny
