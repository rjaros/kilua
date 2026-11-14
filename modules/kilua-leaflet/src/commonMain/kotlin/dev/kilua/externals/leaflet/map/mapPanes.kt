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

//@file:JsModule("leaflet/dist/leaflet-src.esm.js")
//@file:JsNonModule

package dev.kilua.externals.leaflet.map

import dev.kilua.utils.jsGet
import dev.kilua.utils.jsSet
import web.html.HTMLElement
import kotlin.js.JsAny

/** Map panes available on a Leaflet map. */
public external interface MapPanes : JsAny {
    /** Pane that contains all other map panes. */
    public var mapPane: HTMLElement

    /** Pane for [GridLayer][dev.kilua.externals.leaflet.layer.tile.GridLayer]s and [TileLayer][dev.kilua.externals.leaflet.layer.tile.TileLayer]s. */
    public var tilePane: HTMLElement

    /** Pane for vectors, [ImageOverlay][dev.kilua.externals.leaflet.layer.overlay.ImageOverlay]s and [VideoOverlay][dev.kilua.externals.leaflet.layer.overlay.VideoOverlay]s. */
    public var overlayPane: HTMLElement

    /** Pane for overlay shadows. */
    public var shadowPane: HTMLElement

    /** Pane for [Icon][dev.kilua.externals.leaflet.layer.marker.Icon]s of [Marker][dev.kilua.externals.leaflet.layer.marker.Marker]s. */
    public var markerPane: HTMLElement

    /** Pane for [Tooltip][dev.kilua.externals.leaflet.layer.overlay.Tooltip]s. */
    public var tooltipPane: HTMLElement

    /** Pane for [Popup][dev.kilua.externals.leaflet.layer.overlay.Popup]s. */
    public var popupPane: HTMLElement
}

@Suppress("NOTHING_TO_INLINE")
public inline operator fun MapPanes.get(name: String): HTMLElement? = jsGet(name) as HTMLElement?

@Suppress("NOTHING_TO_INLINE")
public inline operator fun MapPanes.set(name: String, value: HTMLElement) {
    jsSet(name, value)
}
