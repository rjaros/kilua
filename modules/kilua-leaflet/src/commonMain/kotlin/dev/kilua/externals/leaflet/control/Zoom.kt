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

import dev.kilua.externals.leaflet.control.Zoom.ZoomControlOptions
import dev.kilua.externals.leaflet.map.LeafletMap
import js.import.JsModule
import js.import.JsQualifier
import kotlin.js.definedExternally

/**
 * A basic zoom control with two buttons (zoom in and zoom out). It is put on the map by default
 * unless you set its [LeafletMap.zoomControl] option to `false`.
 */
public open external class Zoom(options: ZoomControlOptions = definedExternally) : Control<ZoomControlOptions> {

    public interface ZoomControlOptions : ControlOptions {
        /** The text set on the 'zoom in' button. */
        public var zoomInText: String?
        /** The title  set on the 'zoom in' button. */
        public var zoomInTitle: String?
        /** The text set on the 'zoom out' button. */
        public var zoomOutText: String?
        /** The title set on the 'zoom out' button. */
        public var zoomOutTitle: String?
    }

}
