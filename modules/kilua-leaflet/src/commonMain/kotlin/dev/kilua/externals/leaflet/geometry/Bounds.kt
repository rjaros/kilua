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
 * Represents a rectangular area in pixel coordinates.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#bounds
 */
public open external class Bounds : JsAny {
    public constructor()
    public constructor(topLeft: Point, bottomRight: Point)

    public open var min: Point?
    public open var max: Point?

    public open fun extend(point: Point): Bounds /* this */
    public open fun getCenter(round: Boolean = definedExternally): Point
    public open fun getBottomLeft(): Point
    public open fun getBottomRight(): Point
    public open fun getTopLeft(): Point
    public open fun getTopRight(): Point
    public open fun getSize(): Point
    public open fun contains(otherBounds: Bounds): Boolean
    public open fun contains(point: Point): Boolean
    public open fun intersects(otherBounds: Bounds): Boolean
    public open fun overlaps(otherBounds: Bounds): Boolean
}
