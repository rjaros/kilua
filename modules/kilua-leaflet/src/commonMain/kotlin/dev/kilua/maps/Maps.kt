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

package dev.kilua.maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.leaflet.map.LeafletMap
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import web.html.HTMLDivElement


/**
 * Leaflet Map component.
 */
public interface IMaps : ITag<HTMLDivElement> {

    /**
     * Leaflet Map options.
     */
    public val options: MapsOptions?

    /**
     * Set the Leaflet Map options.
     */
    @Composable
    public fun options(options: MapsOptions?)

    /**
     * Apply some configuration to [Leaflet Map][LeafletMap].
     */
    public fun configureLeafletMap(configure: LeafletMap.() -> Unit)

    /**
     * The native Leaflet Map instance.
     */
    public val leafletMap: LeafletMap?

}

/**
 * Leaflet Map component.
 */
public open class Maps(
    options: MapsOptions? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLDivElement>("div", className, id, renderConfig = renderConfig), IMaps {

    public override var leafletMap: LeafletMap? = null

    protected var leafletMapConfigurer: LeafletMap.() -> Unit = {}

    /**
     * Leaflet Map options.
     */
    public override var options: MapsOptions? by updatingProperty(options) {
        refresh()
    }

    /**
     * Set the Leaflet Map options.
     */
    @Composable
    public override fun options(options: MapsOptions?): Unit = composableProperty("options", {
        this.options = null
    }) {
        this.options = options
    }

    public override fun configureLeafletMap(configure: LeafletMap.() -> Unit) {
        leafletMapConfigurer = configure
        leafletMap?.leafletMapConfigurer()
    }

    override fun onInsert() {
        initializeLeafletMap()
    }

    override fun onRemove() {
        destroyLeafletMap()
    }

    /**
     * Refresh Leaflet Map by destroying and recreating it.
     */
    protected open fun refresh() {
        destroyLeafletMap()
        initializeLeafletMap()
    }

    /**
     * Create and initialize the Leaflet Map instance.
     */
    protected open fun initializeLeafletMap() {
        if (renderConfig.isDom) {
            leafletMap = LeafletObjectFactory.map(element, options?.toJs())
            leafletMap!!.leafletMapConfigurer()
        }
    }

    /**
     * Destroy the Leaflet Map instance.
     */
    protected open fun destroyLeafletMap() {
        leafletMap?.remove()
        leafletMap = null
    }
}

/**
 * Create [Maps] component, returning a reference.
 * @param options the Leaflet Map options
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 * @return the [Maps] component
 */
@Composable
public fun IComponent.mapsRef(
    options: MapsOptions? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IMaps.() -> Unit = {}
): Maps {
    val component = remember { Maps(options, className, id, renderConfig = renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(options) { updateProperty(Maps::options, it) }
        set(className) { updateProperty(Maps::className, it) }
        set(id) { updateProperty(Maps::id, it) }
    }, content)
    return component
}

/**
 * Create [Maps] component.
 * @param options the Leaflet Map options
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 */
@Composable
public fun IComponent.maps(
    options: MapsOptions? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IMaps.() -> Unit = {}
) {
    val component = remember { Maps(options, className, id, renderConfig = renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(options) { updateProperty(Maps::options, it) }
        set(className) { updateProperty(Maps::className, it) }
        set(id) { updateProperty(Maps::id, it) }
    }, content)
}
