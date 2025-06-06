@file:JsModule("leaflet")
@file:JsQualifier("Control")
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

package dev.kilua.externals.leaflet.control

import dev.kilua.externals.leaflet.control.Scale.ScaleOptions
import dev.kilua.externals.leaflet.events.LeafletEventHandlerFnMap
import js.import.JsModule
import js.import.JsQualifier
import kotlin.js.definedExternally

/**
 * A simple scale control that shows the scale of the current center of screen in metric (m/km) and
 * imperial (mi/ft) systems.
 */
public open external class Scale(options: ScaleOptions = definedExternally) : Control<ScaleOptions> {

    public interface ScaleOptions : ControlOptions {
        /**
         * Maximum width of the control in pixels. The width is set dynamically to show round
         * values (e.g. 100, 200, 500).
         */
        public var maxWidth: Int?
        /** Whether to show the metric scale line (m/km). */
        public var metric: Boolean?
        /** Whether to show the imperial scale line (mi/ft). */
        public var imperial: Boolean?
        /**
         * If `true`, the control is updated on [LeafletEventHandlerFnMap.moveend], otherwise it's
         * always up-to-date (updated on [LeafletEventHandlerFnMap.move]).
         */
        public var updateWhenIdle: Boolean?
    }

}
