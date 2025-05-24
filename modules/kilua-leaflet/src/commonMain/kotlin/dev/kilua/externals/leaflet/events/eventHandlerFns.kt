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

/** `baselayerchange` | `overlayadd` | `overlayremove` | `layeradd` | `layerremove` | `zoomlevelschange` | `unload` | `viewreset` | `load` | `zoomstart` | `movestart` | `zoom` | `move` | `zoomend` | `moveend` | `autopanstart` | `dragstart` | `drag` | `add` | `remove` | `loading` | `error` | `update` | `down` | `predrag` | `resize` | `popupopen` | `popupclose` | `tooltipopen` | `tooltipclose` | `locationerror` | `locationfound` | `click` | `dblclick` | `mousedown` | `mouseup` | `mouseover` | `mouseout` | `mousemove` | `contextmenu` | `preclick` | `keypress` | `keydown` | `keyup` | `zoomanim` | `dragend` | `tileunload` | `tileloadstart` | `tileload` | `tileerror` */
public typealias LeafletAnyEventId = String


/** `zoomlevelschange` | `unload` | `viewreset` | `load` | `zoomstart` | `movestart` | `zoom` | `move` | `zoomend` | `moveend` | `autopanstart` | `dragstart` | `drag` | `add` | `remove` | `loading` | `error` | `update` | `down` | `predrag` */
public typealias LeafletEventId = String
public typealias LeafletEventHandlerFn = (event: LeafletEvent) -> Unit


/** `baselayerchange` | `overlayadd` | `overlayremove` */
public typealias LeafletControlEventId = String
public typealias LayersControlEventHandlerFn = (event: LayersControlEvent) -> Unit


/** `layeradd` | `layerremove` */
public typealias LeafletLayerEventId = String
public typealias LayerEventHandlerFn = (event: LayerEvent) -> Unit


/** `resize` */
public typealias LeafletResizeEventId = String
public typealias ResizeEventHandlerFn = (event: ResizeEvent) -> Unit


/** `popupopen` | `popupclose` */
public typealias LeafletPopupEventId = String
public typealias PopupEventHandlerFn = (event: PopupEvent) -> Unit


/** `tooltipopen` | `tooltipclose` */
public typealias LeafletTooltipEventId = String
public typealias TooltipEventHandlerFn = (event: TooltipEvent) -> Unit


/** `locationerror` */
public typealias LeafletErrorEventId = String
public typealias ErrorEventHandlerFn = (event: ErrorEvent) -> Unit


/** `locationfound` */
public typealias LeafletLocationEventId = String
public typealias LocationEventHandlerFn = (event: LocationEvent) -> Unit


/** `click` | `dblclick` | `mousedown` | `mouseup` | `mouseover` | `mouseout` | `mousemove` | `contextmenu` | `preclick` */
public typealias LeafletMouseEventId = String
public typealias LeafletMouseEventHandlerFn = (event: LeafletMouseEvent) -> Unit


/** `keypress` | `keydown` | `keyup` */
public typealias LeafletKeyboardEventId = String
public typealias LeafletKeyboardEventHandlerFn = (event: LeafletKeyboardEvent) -> Unit


/** `zoomanim` */
public typealias LeafletZoomAnimEventId = String
public typealias ZoomAnimEventHandlerFn = (event: ZoomAnimEvent) -> Unit


/** `dragend` */
public typealias LeafletDragEndEventId = String
public typealias DragEndEventHandlerFn = (event: DragEndEvent) -> Unit


/** `tileunload` | `tileloadstart` | `tileload` | `tileerror` */
public typealias LeafletTileEventId = String
public typealias TileEventHandlerFn = (event: TileEvent) -> Unit


/** `tileerror` */
public typealias LeafletTileErrorId = String
public typealias TileErrorEventHandlerFn = (event: TileErrorEvent) -> Unit
