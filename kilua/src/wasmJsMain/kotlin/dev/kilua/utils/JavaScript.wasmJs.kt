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

package dev.kilua.utils

import kotlin.js.JsArray
import kotlin.js.unsafeCast as unsafeCastJs

public actual fun delete(o: JsAny, key: String): Unit = js("delete o[key]")

public actual fun jsTypeOf(o: JsAny?): String = js("typeof o")

@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
public actual inline fun <T> Any?.cast(): T {
    return this as T
}

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T : JsAny> JsAny.unsafeCast(): T {
    return this.unsafeCastJs()
}

public actual fun isArray(o: JsAny?): Boolean = js("Array.isArray(o)")

public actual fun <T : JsAny> concat(array1: JsArray<T>, array2: JsArray<T>): JsArray<T> =
    js("array1.concat(array2)")
