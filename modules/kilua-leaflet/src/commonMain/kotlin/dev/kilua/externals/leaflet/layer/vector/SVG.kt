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

import dev.kilua.externals.JsArray
import dev.kilua.externals.leaflet.geometry.Point
import kotlin.js.JsModule
import web.svg.SVGElement
import kotlin.js.definedExternally

public open external class SVG(
    options: RendererOptions = definedExternally
) : Renderer<Renderer.RendererOptions> {

    public companion object {

        /** @param[name] The name of an [SVG element](https://developer.mozilla.org/en-US/docs/Web/SVG/Element),
         * for example `line` or `circle` */
        public fun create(name: String): SVGElement

        /**
         * Generates an SVG path string for multiple rings, with each ring turning into `M..L..L..`
         * instructions.
         */
        public fun pointsToPath(
            rings: JsArray<Point>,
            closed: Boolean
        ): String
    }
}
