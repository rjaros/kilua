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

package dev.kilua.externals.leaflet.events

import dev.kilua.externals.leaflet.core.Class
import js.core.JsAny
import kotlin.js.JsModule
import kotlin.js.definedExternally

// Note: this can be simplified
//      - each event handler is a subtype of LeafletEventHandlerFn, so just have one type?
//      - add a wrapper for this class, so it works nicely in Kotlin, with an enum for event types

//@formatter:off
public open external class Evented : Class {

    public open fun on(type: LeafletEventId,                               fn: LeafletEventHandlerFn,         context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletControlEventId,                        fn: LayersControlEventHandlerFn,   context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletLayerEventId,                          fn: LayerEventHandlerFn,           context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletResizeEventId,                         fn: ResizeEventHandlerFn,          context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletPopupEventId,                          fn: PopupEventHandlerFn,           context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletTooltipEventId,                        fn: TooltipEventHandlerFn,         context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletErrorEventId,                          fn: ErrorEventHandlerFn,           context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletLocationEventId,                       fn: LocationEventHandlerFn,        context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletMouseEventId,                          fn: LeafletMouseEventHandlerFn,    context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletKeyboardEventId,                       fn: LeafletKeyboardEventHandlerFn, context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletZoomAnimEventId,                       fn: ZoomAnimEventHandlerFn,        context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletDragEndEventId,                        fn: DragEndEventHandlerFn,         context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletTileEventId,                           fn: TileEventHandlerFn,            context: JsAny = definedExternally): Evented /* this */
    public open fun on(type: LeafletTileErrorId,                           fn: TileErrorEventHandlerFn,       context: JsAny = definedExternally): Evented /* this */
    public open fun on(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    public open fun off(type: LeafletEventId,                              fn: LeafletEventHandlerFn = definedExternally,         context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletAnyEventId): Evented /* this */
    public open fun off(type: LeafletControlEventId,                       fn: LayersControlEventHandlerFn = definedExternally,   context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletLayerEventId,                         fn: LayerEventHandlerFn = definedExternally,           context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletResizeEventId,                        fn: ResizeEventHandlerFn = definedExternally,          context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletPopupEventId,                         fn: PopupEventHandlerFn = definedExternally,           context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletTooltipEventId,                       fn: TooltipEventHandlerFn = definedExternally,         context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletErrorEventId,                         fn: ErrorEventHandlerFn = definedExternally,           context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletLocationEventId,                      fn: LocationEventHandlerFn = definedExternally,        context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletMouseEventId,                         fn: LeafletMouseEventHandlerFn = definedExternally,    context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletKeyboardEventId,                      fn: LeafletKeyboardEventHandlerFn = definedExternally, context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletZoomAnimEventId,                      fn: ZoomAnimEventHandlerFn = definedExternally,        context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletDragEndEventId,                       fn: DragEndEventHandlerFn = definedExternally,         context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletTileEventId,                          fn: TileEventHandlerFn = definedExternally,            context: JsAny = definedExternally): Evented /* this */
    public open fun off(type: LeafletTileErrorId,                          fn: TileErrorEventHandlerFn = definedExternally,       context: JsAny = definedExternally): Evented /* this */
    public open fun off(eventMap: LeafletEventHandlerFnMap): Evented /* this */
    public open fun off(): Evented /* this */

    public open fun fire(type: String, data: JsAny = definedExternally, propagate: Boolean = definedExternally): Evented /* this */

    public open fun listens(type: String): Boolean

    public open fun once(type: LeafletEventId,                             fn: LeafletEventHandlerFn,         context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletControlEventId,                      fn: LayersControlEventHandlerFn,   context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletLayerEventId,                        fn: LayerEventHandlerFn,           context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletResizeEventId,                       fn: ResizeEventHandlerFn,          context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletPopupEventId,                        fn: PopupEventHandlerFn,           context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletTooltipEventId,                      fn: TooltipEventHandlerFn,         context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletErrorEventId,                        fn: ErrorEventHandlerFn,           context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletLocationEventId,                     fn: LocationEventHandlerFn,        context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletMouseEventId,                        fn: LeafletMouseEventHandlerFn,    context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletKeyboardEventId,                     fn: LeafletKeyboardEventHandlerFn, context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletZoomAnimEventId,                     fn: ZoomAnimEventHandlerFn,        context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletDragEndEventId,                      fn: DragEndEventHandlerFn,         context: JsAny = definedExternally): Evented /* this */
    public open fun once(type: LeafletTileEventId,                         fn: TileEventHandlerFn,            context: JsAny = definedExternally): Evented /* this */
    public open fun once(eventMap: LeafletEventHandlerFnMap): Evented /* this */

    public open fun addEventParent(obj: Evented): Evented /* this */

    public open fun removeEventParent(obj: Evented): Evented /* this */
}
