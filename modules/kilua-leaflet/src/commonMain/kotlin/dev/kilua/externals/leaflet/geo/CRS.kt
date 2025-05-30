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

import dev.kilua.externals.leaflet.core.Class
import dev.kilua.externals.leaflet.geometry.Bounds
import dev.kilua.externals.leaflet.geometry.Point
import js.array.Tuple2
import js.core.JsAny
import js.core.JsNumber
import js.import.JsModule

/**
 * ### Coordinate Reference System
 *
 * Object that defines coordinate reference systems for projecting geographical points into pixel
 * (screen) coordinates and back (and to coordinates in other units for
 * [WMS][dev.kilua.externals.leaflet.layer.tile.WMS] services).
 *
 * Leaflet defines the most usual CRSs by default. If you want to use a CRS not defined by default,
 * take a look at the [Proj4Leaflet plugin](https://github.com/kartena/Proj4Leaflet).
 *
 * Note that the CRS instances do not inherit from Leaflet's [Class] object, and can't be
 * instantiated. Also, new classes can't inherit from them, and methods can't be added to them
 * with the `include` public function.
 *
 * See: [`https://leafletjs.com/reference.html#crs-l-crs-base`](https://leafletjs.com/reference.html#crs-l-crs-base)
 */
public abstract external class CRS : JsAny {

    /** Standard code name of the CRS passed into WMS services (e.g. 'EPSG:3857') */
    public var code: String?

    /**
     * A pair of two numbers defining whether the longitude (horizontal) coordinate axis wraps
     * around a given range and how. Defaults to `[-180, 180]` in most geographical CRSs.
     *
     * If undefined, the longitude axis does not wrap around.
     * @see [wrapLat]
     */
    public var wrapLng: Tuple2<JsNumber, JsNumber>? /* JsTuple<Number, Number> */

    /**
     * Like [wrapLng], but for the latitude (vertical) axis.
     * @see [wrapLng]
     */
    public var wrapLat: Tuple2<JsNumber, JsNumber>? /* JsTuple<Number, Number> */

    /** If true, the coordinate space will be unbounded (infinite in both axes) */
    public var infinite: Boolean?

    public fun latLngToPoint(latlng: LatLng, zoom: Int): Point
    public fun pointToLatLng(point: Point, zoom: Int): LatLng
    public fun project(latlng: LatLng): Point
    public fun unproject(point: Point): LatLng
    public fun scale(zoom: Int): Int
    public fun zoom(scale: Int): Int
    public fun getProjectedBounds(zoom: Int): Bounds
    public fun distance(latlng1: LatLng, latlng2: LatLng): JsNumber
    public fun wrapLatLng(latlng: LatLng): LatLng

    public companion object {
        /**
         * Rarely used by some commercial tile providers. Uses Elliptical Mercator projection.
         *
         * See: [`https://leafletjs.com/reference.html#crs-l-crs-epsg3395`](https://leafletjs.com/reference.html#crs-l-crs-epsg3395)
         */
        public val EPSG3395: CRS

        /**
         * The most common [CRS] for online maps, used by almost all free and commercial tile
         * providers. Uses Spherical Mercator projection. Set in by default in Map's
         * [`crs` option][dev.kilua.externals.leaflet.map.LeafletMap.LeafletMapOptions.crs].
         */
        public val EPSG3857: CRS

        /** A common CRS among GIS enthusiasts. Uses simple equirectangular projection. */
        public val EPSG4326: CRS

        /**
         * Serves as the base for [CRS] that are global such that they cover the earth. Can only be
         * used as the base for other CRS and cannot be used directly, since it does not have a
         * [code], `projection` or `transformation`.
         *
         * [CRS.distance] returns meters.
         */
        public val Earth: CRS

        /**
         * A simple CRS that maps longitude and latitude into x and y directly. May be used for
         * maps of flat surfaces (e.g. game maps). Note that the y-axis should still be inverted
         * (going from bottom to top). `distance()` returns simple euclidean distance.
         *
         * See: [`https://leafletjs.com/reference.html#crs-l-crs-simple`](https://leafletjs.com/reference.html#crs-l-crs-simple)
         */
        public val Simple: CRS

        /**
         * Object that defines coordinate reference systems for projecting geographical points
         * into pixel (screen) coordinates and back (and to coordinates in other units for
         * [WMS][dev.kilua.externals.leaflet.layer.tile.WMS] services).
         */
        public val Base: CRS

        // public val EPSG900913: CRS // defined in DefinitelyTyped, but undocumented
    }
}
