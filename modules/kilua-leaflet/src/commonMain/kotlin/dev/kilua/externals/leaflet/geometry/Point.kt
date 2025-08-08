@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2022 Adam S.
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

import js.core.JsAny
import kotlin.js.JsModule
import kotlin.js.definedExternally

/**
 * Represents a point with `x` and `y` coordinates in pixels.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#point
 */
public open external class Point(
    x: Double,
    y: Double,
    round: Boolean? = definedExternally
) : JsAny {
    public var x: Double
    public var y: Double

    public fun clone(): Point

    /** non-destructive, returns a new point */
    public fun add(otherPoint: Point): Point
    public fun subtract(otherPoint: Point): Point
    public fun divideBy(num: Double): Point
    public fun multiplyBy(num: Double): Point
    public fun scaleBy(scale: Point): Point
    public fun unscaleBy(scale: Point): Point
    public fun round(): Point
    public fun floor(): Point
    public fun ceil(): Point
    public fun trunc(): Point
    public fun distanceTo(otherPoint: Point): Double
    public fun contains(otherPoint: Point): Boolean

    @Suppress("CovariantEquals") // 'equals' is external, we can't change it, so the warning isn't useful
    public fun equals(otherPoint: Point): Boolean
}
