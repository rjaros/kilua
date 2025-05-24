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

package dev.kilua.externals.leaflet.layer.vector

import dev.kilua.externals.JsArray
import dev.kilua.externals.leaflet.geo.LatLng
import dev.kilua.externals.leaflet.geo.LatLngBounds
import dev.kilua.externals.leaflet.geometry.Point
import js.core.JsAny
import js.import.JsModule
import kotlin.js.definedExternally

/**
 * A class for drawing polyline overlays on a map. Extends [Path].
 *
 * See [`https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/layer/vector/Polyline.js`](https://github.com/Leaflet/Leaflet/blob/v1.7.1/src/layer/vector/Polyline.js)
 *
 * @param T [dev.kilua.externals.geojson.LineString] or [dev.kilua.externals.geojson.MultiLineString]
 */
public open external class Polyline : Path<Polyline.PolylineOptions> {

    /** Create a polyline from an array of [LatLng] points */
    public constructor(
        latlngs: JsArray<LatLng>,
        options: PolylineOptions = definedExternally
    )

    /** @param[latlngs] pass a multi-dimensional array to represent a `MultiPolyline` shape */
    public constructor(
        latlngs: JsArray<JsArray<LatLng>>,
        options: PolylineOptions = definedExternally
    )

    public open var feature: JsAny?

    public open fun toGeoJSON(precision: Double = definedExternally): JsAny

    public open fun getLatLngs(): JsAny /* Array<LatLng> | Array<Array<LatLng>> | Array<Array<Array<LatLng>>> */

    public open fun setLatLngs(latlngs: JsArray<LatLng>): Polyline /* this */
    public open fun setLatLngs(latlngs: JsArray<JsArray<LatLng>>): Polyline /* this */
    public open fun setLatLngs(latlngs: JsArray<JsArray<JsArray<LatLng>>>): Polyline /* this */

    public open fun addLatLng(
        latlng: LatLng,
        latlngs: JsArray<LatLng> = definedExternally
    ): Polyline /* this */

    public open fun addLatLng(
        latlng: JsArray<LatLng>,
        latlngs: JsArray<LatLng> = definedExternally
    ): Polyline /* this */

    public open fun isEmpty(): Boolean
    public open fun getCenter(): LatLng
    public open fun getBounds(): LatLngBounds

    public open fun closestLayerPoint(p: Point): Point

    public interface PolylineOptions : PathOptions {
        /**
         * How much to simplify the polyline on each zoom level. More means better performance and
         * smoother look, and less means more accurate representation.
         */
        public var smoothFactor: Double?

        /** Disable polyline clipping. */
        public var noClip: Boolean?
    }

}
