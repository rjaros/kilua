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

package dev.kilua.externals.leaflet.layer.marker

import dev.kilua.externals.leaflet.geometry.Point
import dev.kilua.externals.leaflet.layer.Layer
import js.core.JsAny
import js.import.JsModule
import web.html.HTMLElement
import kotlin.js.JsName
import kotlin.js.definedExternally

/**
 * Represents an icon to provide when creating a marker.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#icon
 */
public open external class Icon<T : Icon.IconOptions>(options: IconOptions) : Layer<T> {

    public open fun createIcon(oldIcon: HTMLElement = definedExternally): HTMLElement
    public open fun createShadow(oldIcon: HTMLElement = definedExternally): HTMLElement

    public interface DefaultIconOptions : IconOptions {
        public var imagePath: String?
    }

    public open class Default(options: DefaultIconOptions = definedExternally) : Icon<DefaultIconOptions> {

        public companion object {
            public var imagePath: String?
        }
    }

    public companion object {
        @JsName("Default")
        public val DefaultIcon: Default
    }

    public interface IconOptions : LayerOptions {
        /** **(required)** The URL to the icon image (absolute or relative to your script path). */
        public var iconUrl: String
        public var iconRetinaUrl: String?
        public var iconSize: Point?
        public var iconAnchor: Point?
        public var popupAnchor: Point
        public var tooltipAnchor: Point
        public var shadowUrl: String?
        public var shadowRetinaUrl: String?

        /** Size of the shadow image in pixels. */
        public var shadowSize: Point?

        /**
         * The coordinates of the "tip" of the shadow (relative to its top left corner)
         * (the same as [iconAnchor] if not specified).
         */
        public var shadowAnchor: Point?

        /** A custom class name to assign to both icon and shadow images. Empty by default. */
        public var className: String?

        /**
         * Whether the crossOrigin attribute will be added to the tiles. If a String is provided,
         * all tiles will have their crossOrigin attribute set to the String provided. This is needed if you want to access tile pixel data. Refer to CORS Settings for valid String values.
         */
        public var crossOrigin: JsAny? /* String? | Boolean? */
    }

}
