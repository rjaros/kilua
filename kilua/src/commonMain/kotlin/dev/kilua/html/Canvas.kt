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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.utils.cast
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

/**
 * HTML Canvas component.
 */
public open class Canvas(
    canvasWidth: Int? = null,
    canvasHeight: Int? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLCanvasElement>("canvas", className, renderConfig) {

    /**
     * The width of the canvas.
     */
    public open var canvasWidth: Int? by updatingProperty(canvasWidth, skipUpdate, "width") {
        if (it != null) {
            element.width = it
        } else {
            element.removeAttribute("width")
        }
    }

    /**
     * The height of the canvas.
     */
    public open var canvasHeight: Int? by updatingProperty(canvasHeight, skipUpdate, "height") {
        if (it != null) {
            element.height = it
        } else {
            element.removeAttribute("height")
        }
    }

    /**
     * The canvas rendering context.
     */
    public val context2D: CanvasRenderingContext2D?

    init {
        if (renderConfig.isDom) {
            if (canvasWidth != null) {
                element.width = canvasWidth
            }
            if (canvasHeight != null) {
                element.height = canvasHeight
            }
            context2D = element.getContext("2d").cast()
        } else {
            context2D = null
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.addByName("width", "height")
    }

}

/**
 * Creates a [Canvas] component.
 *
 * @param canvasWidth the width of the canvas
 * @param canvasHeight the height of the canvas
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [Canvas] component
 */
@Composable
public fun ComponentBase.canvas(
    canvasWidth: Int? = null,
    canvasHeight: Int? = null,
    className: String? = null, content: @Composable Canvas.() -> Unit = {}
): Canvas {
    val component = remember { Canvas(canvasWidth, canvasHeight, className, renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(canvasWidth) { updateProperty("width", it) }
        set(canvasHeight) { updateProperty("height", it) }
        set(className) { updateProperty(Canvas::className, it) }
    }, content)
    return component
}
