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

import dev.kilua.externals.leaflet.DirectionsUnion
import dev.kilua.externals.leaflet.layer.Layer
import dev.kilua.externals.leaflet.layer.overlay.Tooltip.TooltipOptions
import js.import.JsModule
import kotlin.js.definedExternally

public open external class Tooltip(
    source: Layer<*> = definedExternally,
    options: TooltipOptions? = definedExternally,
) : DivOverlay<TooltipOptions> {

    public open fun setOpacity(param_val: Double)

    public interface TooltipOptions : DivOverlayOptions {
        /**
         * Direction where to open the tooltip. Possible values are: `right`, `left`, `top`,
         * `bottom`, `center`, `auto`.
         *
         * `auto` will dynamically switch between `right` and `left` according to the tooltip
         * position on the map.
         */
        public var direction: DirectionsUnion?

        /** Whether to open the tooltip permanently or only on mouseover. */
        public var permanent: Boolean?

        /** If true, the tooltip will follow the mouse instead of being fixed at the feature center. */
        public var sticky: Boolean?

        /** Tooltip container opacity. */
        public var opacity: Double?
    }
}
