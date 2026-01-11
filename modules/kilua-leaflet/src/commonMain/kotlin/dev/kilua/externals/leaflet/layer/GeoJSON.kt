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

package dev.kilua.externals.leaflet.layer

import dev.kilua.externals.leaflet.geo.LatLng
import dev.kilua.externals.leaflet.layer.vector.Path.PathOptions
import js.array.Tuple2
import js.array.Tuple3
import js.core.JsNumber
import kotlin.js.JsAny
import kotlin.js.JsArray
import kotlin.js.JsModule
import kotlin.js.definedExternally

/**
 * Represents a GeoJSON object or an array of GeoJSON objects.
 * Allows you to parse GeoJSON data and display it on the map.
 */
public open external class GeoJSON(
    geojson: JsAny = definedExternally,
    options: GeoJSONOptions = definedExternally
) : FeatureGroup {

    public open fun addData(data: JsAny): GeoJSON /* this */
    public open fun resetStyle(layer: Layer<*> = definedExternally): GeoJSON /* this */
    override fun setStyle(style: PathOptions): GeoJSON /* this */
    public fun setStyle(style: StyleFunction): GeoJSON /* this */

    public companion object {
        public fun geometryToLayer(
            featureData: JsAny,
            options: GeoJSONOptions = definedExternally
        ): Layer<*>

        /**
         * Creates a LatLng object from 2 numbers (longitude, latitude) used in GeoJSON for points.
         */
        public fun coordsToLatLng(coords: Tuple2<JsNumber, JsNumber>): LatLng

        /**
         * Creates a LatLng object from 3 numbers (longitude, latitude, altitude) used in GeoJSON
         * for points.
         */
        public fun coordsToLatLng(coords: Tuple3<JsNumber, JsNumber, JsNumber>): LatLng

        public fun coordsToLatLngs(
            coords: JsArray<JsAny>,
            levelsDeep: Int = definedExternally,
            coordsToLatLng: (coords: Tuple2<JsNumber, JsNumber>) -> LatLng = definedExternally
        ): JsArray<JsAny>

        public fun coordsToLatLngs(
            coords: JsArray<JsAny>,
            levelsDeep: Int = definedExternally,
            coordsToLatLng: (coords: Tuple3<JsNumber, JsNumber, JsNumber>) -> LatLng = definedExternally
        ): JsArray<JsAny>

        public fun latLngToCoords(latlng: LatLng): JsArray<JsNumber>
        public fun latLngsToCoords(
            latlngs: JsArray<JsAny>,
            levelsDeep: Int = definedExternally,
            closed: Boolean = definedExternally
        ): JsArray<JsAny>

        public fun asFeature(geojson: JsAny): JsAny
    }

    public interface GeoJSONOptions : InteractiveLayerOptions {

        /**
         * A Function defining how GeoJSON points spawn Leaflet layers. It is internally called
         * when data is added, passing the GeoJSON and its [LatLng].
         *
         * The default is to spawn a default Marker:
         */
        public var pointToLayer: ((geoJsonPoint: JsAny, latlng: LatLng) -> Layer<*>)?

        /**
         * 	A Function defining the [PathOptions] for styling GeoJSON lines and polygons, called
         * 	internally when data is added.
         *
         * 	The default value is to not override any defaults:
         */
        public var style: StyleFunction /* PathOptions? | StyleFunction<P>? */

        public var onEachFeature: ((feature: JsAny, layer: Layer<*>) -> Unit)?

        public var filter: ((geoJsonFeature: JsAny) -> Boolean)?

        /**
         * A Function that will be used for converting GeoJSON coordinates to [LatLng]s.
         *
         * The default is [GeoJSON.Companion.coordsToLatLng].
         */
        public var coordsToLatLng: ((coords: JsArray<JsNumber> /* JsTuple<Number, Number> | JsTuple<Number, Number, Number> */) -> LatLng)?

        public var markersInheritOptions: Boolean?
    }

}
