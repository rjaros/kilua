@file:JsModule("leaflet")
@file:Suppress("INTERFACE_WITH_SUPERCLASS")
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

package dev.kilua.externals.leaflet.layer.marker

import dev.kilua.externals.leaflet.core.Handler
import dev.kilua.externals.leaflet.geo.LatLng
import dev.kilua.externals.leaflet.geometry.Point
import dev.kilua.externals.leaflet.layer.Layer
import web.html.HTMLElement
import kotlin.js.JsAny
import kotlin.js.JsModule
import kotlin.js.definedExternally

/**
 * Marker is used to display clickable/draggable icons on the map.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#marker
 */
public open external class Marker(
    latlng: LatLng,
    options: MarkerOptions = definedExternally,
) : Layer<Marker.MarkerOptions> {

    public open var dragging: Handler?

    public open fun toGeoJSON(precision: Double = definedExternally): JsAny
    public open fun getLatLng(): LatLng
    public open fun setLatLng(latlng: LatLng): Marker /* this */
    public open fun setZIndexOffset(offset: Int): Marker /* this */
    public open fun getIcon(): Icon<*>
    public open fun setIcon(icon: Icon<*>): Marker /* this */
    public open fun setOpacity(opacity: Double): Marker /* this */
    public open fun getElement(): HTMLElement?

    public interface MarkerOptions : InteractiveLayerOptions, DraggableMarkerOptions {
        /**
         * Icon instance to use for rendering the marker. See [Icon] documentation for details on
         * how to customize the marker icon. If not specified, a common instance of [Icon.Default]
         * is used.
         */
        public var icon: Icon<*>?

        /** Whether the marker can be tabbed to with a keyboard and clicked by pressing enter.*/
        public var keyboard: Boolean?

        /**
         * Text for the browser tooltip that appear on marker hover (no tooltip by default).
         * Useful for accessibility.
         */
        public var title: String?

        /** Text for the alt attribute of the icon image. Useful for accessibility.*/
        public var alt: String?

        /**
         * By default, marker images zIndex is set automatically based on its latitude.
         * Use this option if you want to put the marker on top of all others (or below),
         * specifying a high value like 1000 (or high negative value, respectively).
         */
        public var zIndexOffset: Int?

        /** The opacity of the marker.*/
        public var opacity: Double?

        /** If `true`, the marker will get on top of others when you hover the mouse over it.*/
        public var riseOnHover: Boolean?

        /** The z-index offset used for the `riseOnHover` feature.*/
        public var riseOffset: Int?

        /** Map pane where the markers shadow will be added.*/
        public var shadowPane: String?

        /**
         * When true, the map will pan whenever the marker is focused (via e.g. pressing tab on
         * the keyboard) to ensure the marker is visible within the map's bounds.
         */
        public var autoPanOnFocus: Boolean?
    }

    public interface DraggableMarkerOptions : JsAny {
        /** Whether the marker is draggable with mouse/touch or not. */
        public var draggable: Boolean?

        /** Whether to pan the map when dragging this marker near its edge or not. */
        public var autoPan: Boolean?

        /**
         * Distance (in pixels to the left/right and to the top/bottom) of the map edge to start
         * panning the map.
         */
        public var autoPanPadding: Point

        /** Number of pixels the map should pan by. */
        public var autoPanSpeed: Int?
    }

}
