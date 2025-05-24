@file:Suppress("INTERFACE_WITH_SUPERCLASS")
@file:JsModule("leaflet")
@file:JsQualifier("TileLayer")
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

package dev.kilua.externals.leaflet.layer.tile

import dev.kilua.externals.leaflet.geo.CRS
import js.core.JsAny
import js.import.JsModule
import js.import.JsQualifier
import kotlin.js.definedExternally

/**
 * Used to display WMS services as tile layers on the map.
 */
public open external class WMS(
    baseUrl: String,
    options: WMSOptions,
) : TileLayer<WMS.WMSOptions> {

    public open fun setParams(params: WMSParams, noRedraw: Boolean = definedExternally): WMS /* this */

    public interface WMSOptions : TileLayerOptions {
        public var layers: String?
        public var styles: String?
        public var format: String?
        public var transparent: Boolean?
        public var version: String?
        public var crs: CRS?
        public var uppercase: Boolean?
    }

    public interface WMSParams : JsAny {
        public var format: String?
        public var layers: String
        public var request: String?
        public var service: String?
        public var styles: String?
        public var version: String?
        public var transparent: Boolean?
        public var width: Int?
        public var height: Int?
    }

}
