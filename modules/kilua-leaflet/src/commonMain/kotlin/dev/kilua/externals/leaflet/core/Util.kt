@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
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

package dev.kilua.externals.leaflet.core

import js.array.JsArray
import kotlin.js.JsAny
import kotlin.js.JsModule
import kotlin.js.JsNumber
import kotlin.js.JsString
import kotlin.js.definedExternally

/** Various utility functions, used by Leaflet internally. */
public external object Util : JsAny {
    /** Last unique ID used by [stamp]. */
    public var lastId: Int

    /** Returns the unique ID of an object, assigning one if it doesn't have it. */
    public fun stamp(obj: JsAny): Int

    /** Returns a throttled function. */
    public fun throttle(
        fn: (JsAny?) -> Unit,
        time: Double,
        context: JsAny = definedExternally
    ): (JsAny?) -> Unit

    /** Returns `num` modulo `range`, wrapped into the range. */
    public fun wrapNum(
        num: Double,
        range: JsArray<JsNumber>,
        includeMax: Boolean = definedExternally
    ): Double

    /** Always returns `false`. */
    public fun falseFn(): Boolean

    /** Returns `num` rounded with the specified `precision`. */
    public fun formatNum(num: Double, precision: Double = definedExternally): Double

    /** Converts a value to a number, returning `NaN` for non-numeric values. */
    public fun toNumber(value: JsAny?): Double

    /** Trims and splits a string on whitespace. */
    public fun splitWords(str: String): JsArray<JsString>

    /** Merges properties into the options of an object. */
    public fun setOptions(obj: JsAny, options: JsAny = definedExternally): JsAny

    /** Evaluates a `Hello {a}, {b}` style template. */
    public fun template(str: String, data: JsAny): String
}
