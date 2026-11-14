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

package dev.kilua.externals.leaflet.control

import dev.kilua.externals.leaflet.control.FullscreenControl.FullscreenControlOptions
import web.html.HTMLElement
import kotlin.js.JsModule
import kotlin.js.definedExternally

/** A button that toggles fullscreen mode for the map. */
public open external class FullscreenControl(
    options: FullscreenControlOptions = definedExternally
) : Control<FullscreenControlOptions> {

    /** Returns `true` if the map is in fullscreen mode. */
    public open fun isFullscreen(): Boolean

    /** Enters fullscreen mode, or exits it if the map is already fullscreen. */
    public open fun toggle(): FullscreenControl /* this */

    /** Enters fullscreen mode if [on] is `true`, or exits it otherwise. */
    public open fun setFullscreen(on: Boolean): FullscreenControl /* this */

    public interface FullscreenControlOptions : ControlOptions {
        /** The text set on the button while the map is not fullscreen. */
        public var enterText: String?

        /** The title set on the button while the map is not fullscreen. */
        public var enterTitle: String?

        /** The text set on the button while the map is fullscreen. */
        public var exitText: String?

        /** The title set on the button while the map is fullscreen. */
        public var exitTitle: String?

        /** The element to make fullscreen. Defaults to the map container. */
        public var container: HTMLElement?

        /** Whether to always fill the window with CSS instead of using the Fullscreen API. */
        public var pseudo: Boolean?
    }
}
