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

import dev.kilua.externals.JsArray
import dev.kilua.externals.leaflet.layer.Layer.LayerOptions
import kotlin.js.JsAny
import kotlin.js.JsModule
import kotlin.js.definedExternally

/**
 * Used to group several layers and handle them as one.
 *
 * If you add it to the map, any layers added or removed from the group will be
 * added/removed on the map as well. Extends [Layer].
 */
public open external class LayerGroup(
    layers: JsArray<Layer<*>> = definedExternally,
    options: LayerOptions = definedExternally
) : Layer<LayerOptions> {
    /**
     * Returns a GeoJSON representation of the layer group.
     */
    public open fun toGeoJSON(precision: Double = definedExternally): JsAny

    /** Adds the given layer to the group. */
    public open fun addLayer(layer: Layer<*>): LayerGroup /* this */

    /** Removes the layer with the given internal ID from the group. */
    public open fun removeLayer(layer: Int): LayerGroup /* this */

    /** Removes the given layer from the group. */
    public open fun removeLayer(layer: Layer<*>): LayerGroup /* this */

    /** Returns true if the given layer is currently added to the group. */
    public open fun hasLayer(layer: Layer<*>): Boolean

    /** Removes all the layers from the group. */
    public open fun clearLayers(): LayerGroup /* this */

    /**
     * Calls [methodName] on every layer contained in this group, passing any additional parameters.
     * Has no effect if the layers contained do not implement methodName.
     */
    public open fun invoke(methodName: String, vararg params: JsAny): LayerGroup /* this */

    /**
     * Iterates over the layers of the group, optionally specifying context of the iterator function.
     */
    public open fun eachLayer(
        fn: (layer: Layer<*>) -> Unit,
        context: JsAny = definedExternally
    ): LayerGroup /* this */

    public open fun getLayer(id: Int): Layer<*>?
    public open fun getLayers(): JsArray<Layer<*>>
    public open fun setZIndex(zIndex: Int): LayerGroup /* this */
    public open fun getLayerId(layer: Layer<*>): Int

    public open var feature: JsAny
}
