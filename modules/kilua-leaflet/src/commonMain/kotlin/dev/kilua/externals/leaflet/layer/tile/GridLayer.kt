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


package dev.kilua.externals.leaflet.layer.tile

import dev.kilua.externals.leaflet.DoneCallback
import dev.kilua.externals.leaflet.geo.Coords
import dev.kilua.externals.leaflet.geo.LatLngBounds
import dev.kilua.externals.leaflet.geometry.Point
import dev.kilua.externals.leaflet.layer.Layer
import dev.kilua.externals.leaflet.layer.tile.GridLayer.GridLayerOptions
import js.core.JsAny
import js.import.JsModule
import web.html.HTMLElement
import kotlin.js.definedExternally

/**
 * Generic class for handling a tiled grid of HTML elements. This is the base class for all tile
 * layers and replaces [Canvas][dev.kilua.externals.leaflet.layer.vector.Canvas].
 *
 * GridLayer can be extended to create a tiled grid of HTML elements like `<canvas>`, `<img>` or
 * `<div>`. GridLayer will handle creating and animating these DOM elements for you.
 *
 * [`https://leafletjs.com/reference.html#gridlayer`](https://leafletjs.com/reference.html#gridlayer)
 */
public abstract external class GridLayer<T : GridLayerOptions>(
    options: T = definedExternally
) : Layer<T> {

    public open fun bringToFront(): GridLayer<*> /* this */
    public open fun bringToBack(): GridLayer<*> /* this */
    public open fun getContainer(): HTMLElement?
    public open fun setOpacity(opacity: Double): GridLayer<*> /* this */
    public open fun setZIndex(zIndex: Int): GridLayer<*> /* this */
    public open fun isLoading(): Boolean
    public open fun redraw(): GridLayer<*> /* this */
    public open fun getTileSize(): Point
    public open fun createTile(coords: Coords, done: DoneCallback): HTMLElement

    public interface GridLayerOptions : LayerOptions {
        /**
         * Width and height of tiles in the grid. Use a number if width and height are equal, or
         * [`Point(width, height)`][Point] otherwise.
         */
        public var tileSize: JsAny? /* Number? | Point? */
        public var opacity: Double?
        public var updateWhenIdle: Boolean?
        public var updateWhenZooming: Boolean?
        public var updateInterval: Int?
        public var zIndex: Int?
        public var bounds: LatLngBounds?

        /** The minimum zoom level down to which this layer will be displayed (inclusive). */
        public var minZoom: Int?

        /** The maximum zoom level up to which this layer will be displayed (inclusive). */
        public var maxZoom: Int?

        /**
         * 	Maximum zoom number the tile source has available. If it is specified, the tiles on
         * 	all zoom levels higher than maxNativeZoom will be loaded from maxNativeZoom level and
         * 	auto-scaled.
         */
        public var maxNativeZoom: Int?

        /**
         * 	Minimum zoom number the tile source has available. If it is specified, the tiles on
         * 	all zoom levels lower than minNativeZoom will be loaded from minNativeZoom level and
         * 	auto-scaled.
         */
        public var minNativeZoom: Int?
        public var noWrap: Boolean?
        public var className: String?
        public var keepBuffer: Int?
    }

}
