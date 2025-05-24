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

package dev.kilua.externals.leaflet.layer

import dev.kilua.externals.leaflet.events.Evented
import dev.kilua.externals.leaflet.events.LeafletEventHandlerFn
import dev.kilua.externals.leaflet.geo.LatLng
import dev.kilua.externals.leaflet.layer.Layer.LayerOptions
import dev.kilua.externals.leaflet.layer.overlay.Popup
import dev.kilua.externals.leaflet.layer.overlay.Popup.PopupOptions
import dev.kilua.externals.leaflet.layer.overlay.Tooltip
import dev.kilua.externals.leaflet.layer.overlay.Tooltip.TooltipOptions
import dev.kilua.externals.leaflet.map.LeafletMap
import js.core.JsAny
import js.import.JsModule
import web.html.HTMLElement
import kotlin.js.definedExternally

/**
 * A set of methods from the Layer base class that all Leaflet layers use.
 * Inherits all methods, options and events from [Evented].
 */
public abstract external class  Layer<T : LayerOptions>(
    options: T = definedExternally
) : Evented {

    public open var options: T

    public open fun addTo(map: LeafletMap): Layer<T> /* this */
    public open fun addTo(map: LayerGroup): Layer<T> /* this */
    public open fun remove(): Layer<T> /* this */
    public open fun removeFrom(map: LeafletMap): Layer<T> /* this */
    public open fun getEvents(): LeafletEventHandlerFn?
    public open fun getAttribution(): String?
    public open fun beforeAdd(map: LeafletMap): Layer<T>? /* this */

    public open fun getPane(name: String = definedExternally): HTMLElement?

    public open fun bindPopup(
        content: (layer: Layer<T>) -> JsAny,
        options: PopupOptions = definedExternally
    ): Layer<T> /* this */

    public open fun bindPopup(
        content: String,
        options: PopupOptions = definedExternally
    ): Layer<T> /* this */

    public open fun bindPopup(
        content: HTMLElement,
        options: PopupOptions = definedExternally
    ): Layer<T> /* this */

    public open fun bindPopup(
        content: Popup,
        options: PopupOptions = definedExternally
    ): Layer<T> /* this */

    public open fun unbindPopup(): Layer<T> /* this */
    public open fun openPopup(latlng: LatLng = definedExternally): Layer<T> /* this */
    public open fun openPopup(): Layer<T> /* this */
    public open fun closePopup(): Layer<T> /* this */
    public open fun togglePopup(): Layer<T> /* this */
    public open fun isPopupOpen(): Boolean
    public open fun setPopupContent(content: String): Layer<T> /* this */
    public open fun setPopupContent(content: HTMLElement): Layer<T> /* this */
    public open fun setPopupContent(content: Popup): Layer<T> /* this */
    public open fun getPopup(): Popup?

    public open fun bindTooltip(
        content: (layer: Layer<T>) -> JsAny,
        options: TooltipOptions = definedExternally
    ): Layer<T> /* this */

    public open fun bindTooltip(
        content: Tooltip,
        options: TooltipOptions = definedExternally
    ): Layer<T> /* this */

    public open fun bindTooltip(
        content: String,
        options: TooltipOptions = definedExternally
    ): Layer<T> /* this */

    public open fun bindTooltip(
        content: HTMLElement,
        options: TooltipOptions = definedExternally
    ): Layer<T> /* this */

    public open fun unbindTooltip(): Layer<T> /* this */
    public open fun openTooltip(latlng: LatLng = definedExternally): Layer<T> /* this */
    public open fun openTooltip(): Layer<T> /* this */
    public open fun closeTooltip(): Layer<T> /* this */
    public open fun toggleTooltip(): Layer<T> /* this */
    public open fun isTooltipOpen(): Boolean
    public open fun setTooltipContent(content: String): Layer<T> /* this */
    public open fun setTooltipContent(content: HTMLElement): Layer<T> /* this */
    public open fun setTooltipContent(content: Tooltip): Layer<T> /* this */
    public open fun getTooltip(): Tooltip?

    public open fun onAdd(map: LeafletMap): Layer<T> /* this */
    public open fun onRemove(map: LeafletMap): Layer<T> /* this */

    public interface LayerOptions : JsAny {
        /**
         * By default, the layer will be added to the map's
         * [overlay pane][dev.kilua.externals.leaflet.map.DefaultMapPanes.overlayPane].
         * Overriding this option will cause the layer to be placed on another pane by default.
         */
        public var pane: String?

        /**
         * String to be shown in the attribution control, e.g. "© OpenStreetMap contributors".
         * It describes the layer data and is often a legal obligation towards copyright holders
         * and tile providers.
         */
        public var attribution: String?
    }

    /**
     * Some [Layer]s can be made interactive - when the user interacts with such a layer, mouse
     * events like `click` and `mouseover` can be handled. Use the
     * [event handling methods](https://leafletjs.com/reference.html#evented-method) to handle
     * these events.
     */
    public interface InteractiveLayerOptions : LayerOptions {
        /** If true, the popup/tooltip will listen to the mouse events. */
        public var interactive: Boolean?
        public var bubblingMouseEvents: Boolean?
    }

}
