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

import dev.kilua.utils.unsafeCast
import dev.kilua.dom.JsAny

/**
 * Return empty JS Object
 */
public expect fun obj(): JsAny

/**
 * Helper function for creating JavaScript objects with given type.
 */
public inline fun <T : JsAny> obj(init: T.() -> Unit): T {
    return (obj().unsafeCast<T>()).apply(init)
}

/**
 * Operator to set property on JS Object
 */
public expect operator fun JsAny.set(key: String, value: JsAny)

/**
 * Operator to get property from JS Object
 */
public expect operator fun JsAny.get(key: String): JsAny?

/**
 * Get the list of keys from JS Object
 */
public expect fun keys(o: JsAny): List<String>

/**
 * Copies all properties from source object to target object
 */
public expect fun assign(target: JsAny, source: JsAny)

/**
 * Delete a property from an object
 */
public expect fun delete(o: JsAny, key: String)

/**
 * Return native JS type of a given value
 */
public expect fun jsTypeOf(o: JsAny?): String

/**
 * Return undefined value
 */
public expect fun undefined(): JsAny?

/**
 * JavaScript global object
 */
public external val globalThis: JsAny
