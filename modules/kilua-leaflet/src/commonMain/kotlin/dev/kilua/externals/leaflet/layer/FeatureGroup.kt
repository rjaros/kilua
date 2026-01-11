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

package dev.kilua.externals.leaflet.layer

import dev.kilua.externals.leaflet.geo.LatLngBounds
import dev.kilua.externals.leaflet.layer.vector.Path.PathOptions
import kotlin.js.JsArray
import kotlin.js.JsModule
import kotlin.js.definedExternally


/**
 * Extended [LayerGroup] that makes it easier to do the same thing to all its member layers:
 *
 * * [Layer.bindPopup] binds a popup to all the layers at once (likewise with [Layer.bindTooltip])
 * * Events are propagated to the [FeatureGroup], so if the group has an event handler, it will
 *   handle events from any of the layers. This includes mouse events and custom events.
 * * Has `layeradd` and `layerremove` events
 */
public open external class FeatureGroup(
    layers: JsArray<Layer<*>> = definedExternally,
    options: LayerOptions = definedExternally
) : LayerGroup {
    public open fun setStyle(style: PathOptions): FeatureGroup /* this */
    public open fun bringToFront(): FeatureGroup /* this */
    public open fun bringToBack(): FeatureGroup /* this */
    public open fun getBounds(): LatLngBounds
}
