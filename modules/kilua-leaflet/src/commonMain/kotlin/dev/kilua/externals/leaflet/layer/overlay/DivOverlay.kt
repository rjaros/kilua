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

package dev.kilua.externals.leaflet.layer.overlay

import dev.kilua.externals.leaflet.geo.LatLng
import dev.kilua.externals.leaflet.geometry.Point
import dev.kilua.externals.leaflet.layer.Layer
import dev.kilua.externals.leaflet.layer.overlay.DivOverlay.DivOverlayOptions
import dev.kilua.externals.leaflet.map.LeafletMap
import web.html.HTMLElement
import kotlin.js.JsAny
import kotlin.js.JsModule
import kotlin.js.definedExternally

public open external class DivOverlay<T : DivOverlayOptions>(
    options: DivOverlayOptions = definedExternally,
    source: Layer<*> = definedExternally,
) : Layer<Layer.LayerOptions> {

    public open fun openOn(map: LeafletMap)
    public open fun close()
    public open fun toggle(): DivOverlay<T>?
    public open fun getLatLng(): LatLng?
    public open fun setLatLng(latlng: LatLng): DivOverlay<T> /* this */
    public open fun getContent(): JsAny? /* String? | HTMLElement? */
    public open fun setContent(htmlContent: (source: Layer<*>) -> JsAny): DivOverlay<T> /* this */
    public open fun setContent(htmlContent: String): DivOverlay<T> /* this */
    public open fun setContent(htmlContent: HTMLElement): DivOverlay<T> /* this */
    public open fun getElement(): JsAny? /* String? | HTMLElement? */
    public open fun update()
    public open fun isOpen(): Boolean
    public open fun bringToFront(): DivOverlay<T> /* this */
    public open fun bringToBack(): DivOverlay<T> /* this */

    public interface DivOverlayOptions : InteractiveLayerOptions {
        /** The offset of the overlay position. */
        public var offset: Point

        /** A custom CSS class name to assign to the overlay. */
        public var className: String?
    }

}
