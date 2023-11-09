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
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.helpers.PropertyListBuilder
import org.w3c.dom.HTMLOListElement

/**
 * Numbered list types.
 */
@Suppress("EnumNaming")
public enum class OlType {
    a,
    A,
    i,
    I;

    public val value: String = name
    override fun toString(): String {
        return value
    }
}

/**
 * HTML Ol component.
 */
public open class Ol(
    type: OlType? = null,
    start: Int? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLOListElement>("ol", className, renderConfig) {

    /**
     * The type of the numbered list.
     */
    public open var type: OlType? by updatingProperty(type, skipUpdate) {
        if (it != null) {
            element.type = it.value
        } else {
            element.removeAttribute("type")
        }
    }

    /**
     * A starting number.
     */
    public open var start: Int? by updatingProperty(start, skipUpdate) {
        if (it != null) {
            element.start = it
        } else {
            element.removeAttribute("start")
        }
    }

    /**
     * Number items from high to low.
     */
    public open var reversed: Boolean? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.reversed = it
        } else {
            element.removeAttribute("reversed")
        }
    }

    init {
        if (renderConfig.isDom) {
            if (type != null) {
                element.type = type.value
            }
            if (start != null) {
                element.start = start
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::type, ::start, ::reversed)
    }

}

/**
 * Creates a [Ol] component.
 *
 * @param type the type of the numbered list
 * @param start a starting number
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [Ol] component
 */
@Composable
public fun ComponentBase.ol(
    type: OlType? = null, start: Int? = null, className: String? = null, content: @Composable Ol.() -> Unit = {}
): Ol {
    val component = remember { Ol(type, start, className, renderConfig) }
    ComponentNode(component, {
        set(type) { updateProperty(Ol::type, it) }
        set(start) { updateProperty(Ol::start, it) }
        set(className) { updateProperty(Ol::className, it) }
    }, content)
    return component
}
