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

package dev.kilua.externals.leaflet.events

import dev.kilua.externals.leaflet.geo.Coords
import dev.kilua.externals.leaflet.geo.LatLng
import dev.kilua.externals.leaflet.geo.LatLngBounds
import dev.kilua.externals.leaflet.geometry.Point
import dev.kilua.externals.leaflet.layer.Layer
import dev.kilua.externals.leaflet.layer.overlay.Popup
import dev.kilua.externals.leaflet.layer.overlay.Tooltip
import dev.kilua.externals.leaflet.events.Evented
import web.html.HTMLElement
import web.keyboard.KeyboardEvent
import web.mouse.MouseEvent
import kotlin.js.JsAny
import kotlin.js.JsModule

public external interface LeafletEvent : JsAny {
    public var type: String
    public var target: Evented
    public var sourceTarget: Evented

    /** For propagated events, the last object that propagated the event to its event parent. */
    public var propagatedFrom: Evented?
}

public external interface LeafletPointerEvent : LeafletEvent {
    public var latlng: LatLng
    public var layerPoint: Point
    public var containerPoint: Point
    public var originalEvent: MouseEvent /* MouseEvent | PointerEvent */
}

public external interface LeafletKeyboardEvent : LeafletEvent {
    public var originalEvent: KeyboardEvent
}

public external interface LocationEvent : LeafletEvent {
    public var latlng: LatLng
    public var bounds: LatLngBounds
    public var accuracy: Double
    public var altitude: Double?
    public var altitudeAccuracy: Double?
    public var heading: Double?
    public var speed: Double?
    public var timestamp: Double
}

public external interface ErrorEvent : LeafletEvent {
    public var message: String
    public var code: Int
}

public external interface LayerEvent : LeafletEvent {
    public var layer: Layer<*>
}

public external interface LayersControlEvent : LayerEvent {
    public var name: String
}

public external interface TileEvent : LeafletEvent {
    public var tile: HTMLElement
    public var coords: Coords
}

public external interface TileErrorEvent : TileEvent {
    public var error: JsAny
}

public external interface ResizeEvent : LeafletEvent {
    public var oldSize: Point
    public var newSize: Point
}

public external interface GeoJSONEvent : LeafletEvent {
    public var layer: Layer<*>
    public var properties: JsAny?
    public var geometryType: String
    public var id: JsAny? /* String | Number */
}

public external interface PopupEvent : LeafletEvent {
    public var popup: Popup
}

public external interface TooltipEvent : LeafletEvent {
    public var tooltip: Tooltip
}

public external interface DragEndEvent : LeafletEvent {
    public var distance: Double
    public var noInertia: Boolean?
}

public external interface ZoomAnimEvent : LeafletEvent {
    public var center: LatLng
    public var zoom: Int
    public var noUpdate: Boolean
}

public external interface ViewChangeEvent : LeafletEvent {
    public var originalEvent: web.events.Event?
    public var flyTo: Boolean?
    public var pinch: Boolean?
}

public external interface ViewPreResetEvent : LeafletEvent {
    public var center: LatLng
    public var zoom: Int
}

public external interface WorldCopyJumpEvent : LeafletEvent {
    public var worlds: Double
}

public external interface BoxZoomEvent : LeafletEvent {
    public var boxZoomBounds: LatLngBounds
}

public external interface MarkerMoveEvent : LeafletEvent {
    public var oldLatLng: LatLng
    public var latlng: LatLng
}
