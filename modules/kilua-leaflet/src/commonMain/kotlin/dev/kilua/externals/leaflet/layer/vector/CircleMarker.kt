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

import dev.kilua.externals.leaflet.geo.LatLng
import js.core.JsAny
import js.import.JsModule
import kotlin.js.definedExternally

public open external class CircleMarker(
    latlng: LatLng,
    options: CircleMarkerOptions = definedExternally,
) : Path<CircleMarker.CircleMarkerOptions> {

    public open var feature: JsAny?

    public open fun toGeoJSON(precision: Double = definedExternally): JsAny
    public open fun setLatLng(latLng: LatLng): CircleMarker /* this */
    public open fun getLatLng(): LatLng
    public open fun setRadius(radius: Int): CircleMarker /* this */
    public open fun getRadius(): Int

    public interface CircleMarkerOptions : PathOptions {
        public var radius: Int?
    }

}
