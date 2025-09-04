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

package dev.kilua.externals.leaflet.layer.vector

import dev.kilua.externals.leaflet.layer.Layer
import dev.kilua.externals.leaflet.layer.vector.Path.PathOptions
import js.core.JsAny
import kotlin.js.JsModule
import web.dom.Element
import kotlin.js.definedExternally


/**
 * An abstract class that contains options and constants shared between vector overlays
 * ([Polygon], [Polyline], [Circle]). Do not use it directly.
 */
public abstract external class Path<T : PathOptions>(
    options: T = definedExternally
) : Layer<T> {

    public open fun redraw(): Path<T> /* this */
    public open fun setStyle(style: PathOptions): Path<T> /* this */
    public open fun bringToFront(): Path<T> /* this */
    public open fun bringToBack(): Path<T> /* this */
    public open fun getElement(): Element?

    public interface PathOptions : InteractiveLayerOptions {
        /**
         * Whether to draw stroke along the path. Set it to `false` to disable borders on polygons
         * or circles.
         */
        public var stroke: Boolean?

        /** Stroke color */
        public var color: String?

        /** Stroke width in pixels */
        public var weight: Int?

        /** Stroke opacity */
        public var opacity: Double?

        /** A string that defines shape to be used at the end of the stroke. */
        public var lineCap: String? /* "butt" | "round" | "square" | "inherit" */

        /** A string that defines shape to be used at the corners of the stroke.*/
        public var lineJoin: String? /* "miter" | "round" | "bevel" | "inherit" */

        /**
         * A string that defines the stroke dash pattern. Doesn't work on Canvas-powered layers in
         * some old browsers.
         */
        public var dashArray: JsAny? /* String? | Array<Number>? */

        /**
         * A string that defines the distance into the dash pattern to start the dash. Doesn't
         * work on Canvas-powered layers in some old browsers.
         */
        public var dashOffset: String?

        /**
         * Whether to fill the path with color. Set it to `false` to disable filling on polygons or
         * circles.
         */
        public var fill: Boolean?

        /** Fill color. Defaults to the value of the [color] option */
        public var fillColor: String?

        /** Fill opacity. */
        public var fillOpacity: Double?

        /** A string that defines how the inside of a shape is determined. */
        public var fillRule: String? /* "nonzero" | "evenodd" | "inherit" */

        /**
         * When `true`, a mouse event on this path will trigger the same event on the map
         * (unless `L.DomEvent.stopPropagation` is used).
         */
        public var renderer: Renderer<Renderer.RendererOptions>?

        /** Custom class name set on an element. Only for SVG renderer. */
        public var className: String?
    }

}
