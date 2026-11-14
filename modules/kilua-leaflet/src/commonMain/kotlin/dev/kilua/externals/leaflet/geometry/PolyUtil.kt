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

import dev.kilua.externals.leaflet.geo.LatLng
import js.array.JsArray
import kotlin.js.JsAny
import kotlin.js.JsModule
import kotlin.js.definedExternally

/** Utility functions for polygon geometries. */
public external object PolyUtil : JsAny {
    /** Clips a polygon by the given bounds. */
    public fun clipPolygon(
        points: JsArray<Point>,
        bounds: Bounds,
        round: Boolean = definedExternally
    ): JsArray<Point>

    /** Returns the center of the passed LatLngs. */
    public fun polygonCenter(latlngs: JsArray<JsAny>, crs: JsAny): LatLng

    /** Returns the center of mass of the passed LatLngs. */
    public fun centroid(coords: JsArray<JsAny>): LatLng
}
