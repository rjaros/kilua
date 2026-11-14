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

/**
 * Utility functions for polyline points processing.
 */
public external object LineUtil : JsAny {
    /** Reduces the number of points in a polyline while retaining its shape. */
    public fun simplify(points: JsArray<Point>, tolerance: Double): JsArray<Point>

    /** Returns the distance between `p` and the segment `p1`-`p2`. */
    public fun pointToSegmentDistance(p: Point, p1: Point, p2: Point): Double

    /** Returns the closest point on the segment `p1`-`p2` to `p`. */
    public fun closestPointOnSegment(p: Point, p1: Point, p2: Point): Point

    /** Clips the segment `a`-`b` by rectangular bounds. */
    public fun clipSegment(
        a: Point,
        b: Point,
        bounds: Bounds,
        useLastCode: Boolean = definedExternally,
        round: Boolean = definedExternally
    ): JsAny

    public fun _getEdgeIntersection(
        a: Point,
        b: Point,
        code: Int,
        bounds: Bounds,
        round: Boolean = definedExternally
    ): Point

    public fun _getBitCode(p: Point, bounds: Bounds): Int

    public fun _sqClosestPointOnSegment(p: Point, p1: Point, p2: Point, sqDist: Boolean): JsAny

    /** Returns `true` if `latlngs` is a flat array. */
    public fun isFlat(latlngs: JsArray<JsAny>): Boolean

    /** Returns the center of the passed LatLngs. */
    public fun polylineCenter(latlngs: JsArray<JsAny>, crs: JsAny): LatLng

    public fun _projectRing(latlngs: JsArray<JsAny>, crs: JsAny): JsArray<Point>
}
