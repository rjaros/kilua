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

package dev.kilua.externals.leaflet.geometry

import js.array.Tuple4
import kotlin.js.JsModule
import kotlin.js.JsNumber
import kotlin.js.definedExternally

/** Represents an affine transformation: a set of coefficients `a`, `b`, `c`, `d`. */
public open external class Transformation(
    a: Double,
    b: Double,
    c: Double,
    d: Double,
) {
    public constructor(coefficients: Tuple4<JsNumber, JsNumber, JsNumber, JsNumber>)

    /** Returns a transformed point, optionally multiplied by [scale]. */
    public fun transform(point: Point, scale: Double = definedExternally): Point

    /** Returns the transformation of the given point with the inverse transformation. */
    public fun untransform(point: Point, scale: Double = definedExternally): Point
}
