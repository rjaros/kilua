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
import dev.kilua.externals.leaflet.geometry.Transformation
import js.array.Tuple2
import kotlin.js.JsAny
import kotlin.js.JsNumber

/**
 * Object that defines coordinate reference systems for projecting geographical points into pixel
 * coordinates and back.
 */
public external interface CRS : JsAny {
    /** Standard code name of the CRS passed into WMS services (e.g. `EPSG:3857`). */
    public var code: String?

    /** The projection the CRS uses to turn geographical coordinates into projected ones. */
    public var projection: Projection

    /** The transformation the CRS uses to turn projected coordinates into pixels at zoom 0. */
    public var transformation: Transformation?

    /** The longitude wrapping range. */
    public var wrapLng: Tuple2<JsNumber, JsNumber>?

    /** The latitude wrapping range. */
    public var wrapLat: Tuple2<JsNumber, JsNumber>?

    /** If true, the coordinate space is unbounded in both axes. */
    public var infinite: Boolean?

    public fun latLngToPoint(latlng: LatLng, zoom: Int): Point
    public fun pointToLatLng(point: Point, zoom: Int): LatLng
    public fun project(latlng: LatLng): Point
    public fun unproject(point: Point): LatLng
    public fun scale(zoom: Int): Double
    public fun zoom(scale: Double): Double
    public fun getProjectedBounds(zoom: Int): Bounds?
    public fun distance(latlng1: LatLng, latlng2: LatLng): JsNumber
    public fun wrapLatLng(latlng: LatLng): LatLng
    public fun wrapLatLngBounds(bounds: Bounds): LatLngBounds
}

/** Rarely used by some commercial tile providers. Uses Elliptical Mercator projection. */
public external val EPSG3395: CRS

/** The most common CRS for online maps. Uses Spherical Mercator projection. */
public external val EPSG3857: CRS

/** The same CRS as [EPSG3857], with the legacy `EPSG:900913` code. */
public external val EPSG900913: CRS

/** A common CRS among GIS enthusiasts. Uses simple equirectangular projection. */
public external val EPSG4326: CRS

/** Serves as the base for CRS that cover the earth. */
public external val EarthCRS: CRS

/** A simple CRS that maps longitude and latitude into x and y directly. */
public external val SimpleCRS: CRS
