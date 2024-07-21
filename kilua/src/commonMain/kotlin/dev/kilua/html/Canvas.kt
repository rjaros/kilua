/*
 * Copyright (c) 2023 Robert Jaros
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

package dev.kilua.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.utils.cast
import dev.kilua.dom.api.CanvasRenderingContext2D
import dev.kilua.dom.api.HTMLCanvasElement

/**
 * HTML Canvas component.
 */
public interface ICanvas : ITag<HTMLCanvasElement> {
    /**
     * The width of the canvas.
     */
    public val canvasWidth: Int?

    /**
     * Sets the width of the canvas.
     */
    @Composable
    public fun canvasWidth(canvasWidth: Int?)

    /**
     * The height of the canvas.
     */
    public val canvasHeight: Int?

    /**
     * Sets the height of the canvas.
     */
    @Composable
    public fun canvasHeight(canvasHeight: Int?)

    /**
     * The canvas rendering context.
     */
    public val context2D: CanvasRenderingContext2D?
}

/**
 * HTML Canvas component.
 */
public open class Canvas(
    canvasWidth: Int? = null,
    canvasHeight: Int? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLCanvasElement>("canvas", className, id, renderConfig = renderConfig), ICanvas {

    /**
     * The width of the canvas.
     */
    public override var canvasWidth: Int? by updatingProperty(canvasWidth, name = "width") {
        if (it != null) {
            element.width = it
        } else {
            element.removeAttribute("width")
        }
    }

    @Composable
    public override fun canvasWidth(canvasWidth: Int?): Unit = composableProperty("canvasWidth", {
        this.canvasWidth = null
    }) {
        this.canvasWidth = canvasWidth
    }

    /**
     * The height of the canvas.
     */
    public override var canvasHeight: Int? by updatingProperty(canvasHeight, name = "height") {
        if (it != null) {
            element.height = it
        } else {
            element.removeAttribute("height")
        }
    }

    @Composable
    public override fun canvasHeight(canvasHeight: Int?): Unit = composableProperty("canvasHeight", {
        this.canvasHeight = null
    }) {
        this.canvasHeight = canvasHeight
    }

    /**
     * The canvas rendering context.
     */
    public override val context2D: CanvasRenderingContext2D? =
        @Suppress("LeakingThis")
        if (renderConfig.isDom) element.getContext("2d").cast() else null

    init {
        if (renderConfig.isDom) {
            if (canvasWidth != null) {
                @Suppress("LeakingThis")
                element.width = canvasWidth
            }
            if (canvasHeight != null) {
                @Suppress("LeakingThis")
                element.height = canvasHeight
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.addByName("width", "height")
    }

}

/**
 * Creates a [Canvas] component, returning a reference.
 *
 * @param canvasWidth the width of the canvas
 * @param canvasHeight the height of the canvas
 * @param className the CSS class name
 * @param id the ID attribute of the canvas
 * @param content the content of the component
 * @return the [Canvas] component
 */
@Composable
public fun IComponent.canvasRef(
    canvasWidth: Int? = null,
    canvasHeight: Int? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable ICanvas.() -> Unit = {}
): Canvas {
    val component = remember { Canvas(canvasWidth, canvasHeight, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(canvasWidth) { updateProperty("width", it) }
        set(canvasHeight) { updateProperty("height", it) }
        set(className) { updateProperty(Canvas::className, it) }
        set(id) { updateProperty(Canvas::id, it) }
    }, content)
    return component
}

/**
 * Creates a [Canvas] component.
 *
 * @param canvasWidth the width of the canvas
 * @param canvasHeight the height of the canvas
 * @param className the CSS class name
 * @param id the ID attribute of the canvas
 * @param content the content of the component
 */
@Composable
public fun IComponent.canvas(
    canvasWidth: Int? = null,
    canvasHeight: Int? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable ICanvas.() -> Unit = {}
) {
    val component = remember { Canvas(canvasWidth, canvasHeight, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(canvasWidth) { updateProperty("width", it) }
        set(canvasHeight) { updateProperty("height", it) }
        set(className) { updateProperty(Canvas::className, it) }
        set(id) { updateProperty(Canvas::id, it) }
    }, content)
}
