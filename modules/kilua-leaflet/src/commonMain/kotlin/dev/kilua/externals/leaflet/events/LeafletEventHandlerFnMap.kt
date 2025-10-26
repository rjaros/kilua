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

import kotlin.js.JsAny
import kotlin.js.JsModule


public external interface LeafletEventHandlerFnMap : JsAny {
    // control events
    public var baselayerchange: LayersControlEventHandlerFn?
    public var overlayadd: LayersControlEventHandlerFn?
    public var overlayremove: LayersControlEventHandlerFn?

    // layer events
    public var layeradd: LayerEventHandlerFn?
    public var layerremove: LayerEventHandlerFn?

    // leaflet events
    public var zoomlevelschange: LeafletEventHandlerFn?
    public var unload: LeafletEventHandlerFn?
    public var viewreset: LeafletEventHandlerFn?
    public var load: LeafletEventHandlerFn?
    public var zoomstart: LeafletEventHandlerFn?
    public var movestart: LeafletEventHandlerFn?
    public var zoom: LeafletEventHandlerFn?
    public var move: LeafletEventHandlerFn?
    public var zoomend: LeafletEventHandlerFn?
    public var moveend: LeafletEventHandlerFn?
    public var autopanstart: LeafletEventHandlerFn?
    public var dragstart: LeafletEventHandlerFn?
    public var drag: LeafletEventHandlerFn?
    public var add: LeafletEventHandlerFn?
    public var remove: LeafletEventHandlerFn?
    public var loading: LeafletEventHandlerFn?
    public var error: LeafletEventHandlerFn?
    public var update: LeafletEventHandlerFn?
    public var down: LeafletEventHandlerFn?
    public var predrag: LeafletEventHandlerFn?

    // resize events
    public var resize: ResizeEventHandlerFn?

    // popup events
    public var popupopen: PopupEventHandlerFn?
    public var popupclose: PopupEventHandlerFn?

    // tooltip events
    public var tooltipopen: TooltipEventHandlerFn?
    public var tooltipclose: TooltipEventHandlerFn?

    // error events
    public var locationerror: ErrorEventHandlerFn?

    // location events
    public var locationfound: LocationEventHandlerFn?

    // mouse events
    public var click: LeafletMouseEventHandlerFn?
    public var dblclick: LeafletMouseEventHandlerFn?
    public var mousedown: LeafletMouseEventHandlerFn?
    public var mouseup: LeafletMouseEventHandlerFn?
    public var mouseover: LeafletMouseEventHandlerFn?
    public var mouseout: LeafletMouseEventHandlerFn?
    public var mousemove: LeafletMouseEventHandlerFn?
    public var contextmenu: LeafletMouseEventHandlerFn?
    public var preclick: LeafletMouseEventHandlerFn?

    // keyboard events
    public var keypress: LeafletKeyboardEventHandlerFn?
    public var keydown: LeafletKeyboardEventHandlerFn?
    public var keyup: LeafletKeyboardEventHandlerFn?

    // zoom animation events
    public var zoomanim: ZoomAnimEventHandlerFn?

    // drag events
    public var dragend: DragEndEventHandlerFn?

    // tile events
    public var tileunload: TileEventHandlerFn?
    public var tileloadstart: TileEventHandlerFn?
    public var tileload: TileEventHandlerFn?

    // tile error events
    public var tileerror: TileErrorEventHandlerFn?
}
