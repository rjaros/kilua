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

package dev.kilua.externals.leaflet.geo

import dev.kilua.externals.leaflet.geometry.Bounds
import dev.kilua.externals.leaflet.geometry.Point
import kotlin.js.JsAny
import kotlin.js.JsNumber

/** An object with methods for projecting geographical coordinates onto a flat surface and back. */
public external interface Projection : JsAny {
    /** The bounds where the projection is valid. */
    public var bounds: Bounds

    public fun project(latlng: LatLng): Point
    public fun unproject(point: Point): LatLng

    /** Earth radius used by the projection. */
    public var R: Double?

    /** Minor earth radius used by the elliptical Mercator projection. */
    public var R_MINOR: Double?

    /** Maximum latitude supported by the spherical Mercator projection. */
    public var MAX_LATITUDE: Double?
}

/** Equirectangular projection used by [EPSG4326] and [SimpleCRS]. */
public external val LonLatProjection: Projection

/** Elliptical Mercator projection used by [EPSG3395]. */
public external val MercatorProjection: Projection

/** Spherical Mercator projection used by [EPSG3857]. */
public external val SphericalMercatorProjection: Projection

