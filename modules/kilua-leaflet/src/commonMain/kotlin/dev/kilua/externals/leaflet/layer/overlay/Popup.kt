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

import dev.kilua.externals.leaflet.geometry.Point
import dev.kilua.externals.leaflet.layer.Layer
import dev.kilua.externals.leaflet.layer.overlay.Popup.PopupOptions
import dev.kilua.externals.leaflet.map.LeafletMap
import js.import.JsModule
import kotlin.js.definedExternally

/**
 * Used to open popups in certain places of the map. Use [LeafletMap.openPopup] to open popups
 * while making sure that only one popup is open at one time (recommended for usability), or use
 * [LeafletMap.addLayer] to open as many as you want.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#popup
 */
public external class Popup(
    source: Layer<*> = definedExternally,
    options: PopupOptions = definedExternally,
) : DivOverlay<PopupOptions> {

    public interface PopupOptions : DivOverlayOptions {
        /** Max width of the popup, in pixels.*/
        public var maxWidth: Int?
        /** Min width of the popup, in pixels.*/
        public var minWidth: Int?
        /**
         * If set, creates a scrollable container of the given height inside a popup if its content
         * exceeds it.
         */
        public var maxHeight: Int?
        /**
         * Set it to false if you don't want the map to do panning animation to fit the opened
         * popup.
         */
        public var autoPan: Boolean?
        /**
         * The margin between the popup and the top left corner of the map view after
         * auto-panning was performed.
         */
        public var autoPanPaddingTopLeft: Point?
        /**
         * The margin between the popup and the bottom right corner of the map view after
         * auto-panning was performed.
         */
        public var autoPanPaddingBottomRight: Point?
        /**
         * Equivalent of setting both top left and bottom right auto-pan padding to the same value.
         */
        public var autoPanPadding: Point?
        /**
         * Set it to true if you want to prevent users from panning the popup off of the screen
         * while it is open.
         * */
        public var keepInView: Boolean?
        /** Controls the presence of a close button in the popup.*/
        public var closeButton: Boolean?
        /**
         * Set it to false if you want to override the default behavior of the popup closing when
         * another popup is opened.
         */
        public var autoClose: Boolean?
        /**
         * Set it to `false` if you want to override the default behavior of the ESC key for
         * closing of the popup.
         */
        public var closeOnEscapeKey: Boolean?
        /**
         * Set it if you want to override the default behavior of the popup closing when user
         * clicks on the map. Defaults to the map's
         * [`closePopupOnClick`][LeafletMap.LeafletMapOptions.closePopupOnClick] option.
         */
        public var closeOnClick: Boolean?
    }
}
