@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
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

package dev.kilua.externals.leaflet.map.handler

import dev.kilua.externals.leaflet.core.Handler
import dev.kilua.externals.leaflet.map.LeafletMap
import kotlin.js.JsAny
import kotlin.js.JsModule

/** Zooms to a box drawn with Shift-drag (`map.boxZoom`). */
public open external class BoxZoomHandler : Handler {
    public constructor(map: LeafletMap)
    /** Returns `true` if a box is being drawn or was just drawn. */
    public open fun moved(): Boolean?
}

/** Zooms in on double click, and out on Shift-double click (`map.doubleClickZoom`). */
public open external class DoubleClickZoomHandler : Handler {
    public constructor(map: LeafletMap)
}

/** Makes the map draggable with panning inertia (`map.dragging`). */
public open external class DragHandler : Handler {
    public constructor(map: LeafletMap)
    /** Returns `true` if the current or last drag moved the map. */
    public open fun moved(): Boolean?

    /** Returns `true` while the map is being dragged. */
    public open fun moving(): Boolean?
}

/** Pans and zooms the map with the keyboard (`map.keyboard`). */
public open external class KeyboardHandler : Handler {
    public constructor(map: LeafletMap)
    public companion object {
        /** The `KeyboardEvent.key` values for each action. */
        public var keys: JsAny
    }
}

/** Zooms with the mouse scroll wheel (`map.scrollWheelZoom`). */
public open external class ScrollWheelZoomHandler : Handler {
    public constructor(map: LeafletMap)
}

/** Fires a `contextmenu` event on long tap (`map.tapHold`). */
public open external class TapHoldHandler : Handler {
    public constructor(map: LeafletMap)
}

/** Zooms with a two-finger pinch on touch devices (`map.pinchZoom`). */
public open external class PinchZoomHandler : Handler {
    public constructor(map: LeafletMap)
}
