@file:Suppress("TooManyFunctions")
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

import js.core.JsAny

/**
 * Get the list of keys from JS Object
 */
public actual fun keys(o: JsAny): List<String> {
    return js("Object").keys(o).unsafeCast<Array<String>>().toList()
}

/**
 * Copies all properties from source object to the target object
 */
public actual fun assign(target: JsAny, source: JsAny) {
    js("Object").assign(target, source)
}

/**
 * Delete a property from an object
 */
@Suppress("UnsafeCastFromDynamic")
public actual fun delete(o: JsAny, key: String): Unit = js("delete o[key]")

/**
 * Returns JS type of a given value
 */
public actual fun jsTypeOf(o: JsAny?): String = kotlin.js.jsTypeOf(o)
