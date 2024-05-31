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
import web.dom.HTMLOListElement

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
public interface IOl : ITag<HTMLOListElement> {
    /**
     * The type of the numbered list.
     */
    public val type: OlType?

    /**
     * Set the type of the numbered list.
     */
    @Composable
    public fun type(type: OlType?)

    /**
     * A starting number.
     */
    public val start: Int?

    /**
     * Set a starting number.
     */
    @Composable
    public fun start(start: Int?)

    /**
     * Number items from high to low.
     */
    public val reversed: Boolean?

    /**
     * Set number items from high to low.
     */
    @Composable
    public fun reversed(reversed: Boolean?)
}

/**
 * HTML Ol component.
 */
public open class Ol(
    type: OlType? = null,
    start: Int? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLOListElement>("ol", className, id, renderConfig = renderConfig), IOl {

    /**
     * The type of the numbered list.
     */
    public override var type: OlType? by updatingProperty(type) {
        if (it != null) {
            element.type = it.value
        } else {
            element.removeAttribute("type")
        }
    }

    /**
     * Set the type of the numbered list.
     */
    @Composable
    public override fun type(type: OlType?): Unit = composableProperty("type", {
        this.type = null
    }) {
        this.type = type
    }

    /**
     * A starting number.
     */
    public override var start: Int? by updatingProperty(start) {
        if (it != null) {
            element.start = it
        } else {
            element.removeAttribute("start")
        }
    }

    /**
     * Set a starting number.
     */
    @Composable
    public override fun start(start: Int?): Unit = composableProperty("start", {
        this.start = null
    }) {
        this.start = start
    }

    /**
     * Number items from high to low.
     */
    public override var reversed: Boolean? by updatingProperty {
        if (it != null) {
            element.reversed = it
        } else {
            element.removeAttribute("reversed")
        }
    }

    /**
     * Set number items from high to low.
     */
    @Composable
    public override fun reversed(reversed: Boolean?): Unit = composableProperty("reversed", {
        this.reversed = null
    }) {
        this.reversed = reversed
    }

    init {
        if (renderConfig.isDom) {
            if (type != null) {
                @Suppress("LeakingThis")
                element.type = type.value
            }
            if (start != null) {
                @Suppress("LeakingThis")
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
 * Creates a [Ol] component, returninig a reference.
 *
 * @param type the type of the numbered list
 * @param start a starting number
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 * @return the [Ol] component
 */
@Composable
public fun IComponent.olRef(
    type: OlType? = null, start: Int? = null,
    className: String? = null, id: String? = null,
    content: @Composable IOl.() -> Unit = {}
): Ol {
    val component = remember { Ol(type, start, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(type) { updateProperty(Ol::type, it) }
        set(start) { updateProperty(Ol::start, it) }
        set(className) { updateProperty(Ol::className, it) }
        set(id) { updateProperty(Ol::id, it) }
    }, content)
    return component
}

/**
 * Creates a [Ol] component.
 *
 * @param type the type of the numbered list
 * @param start a starting number
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 */
@Composable
public fun IComponent.ol(
    type: OlType? = null, start: Int? = null,
    className: String? = null, id: String? = null,
    content: @Composable IOl.() -> Unit = {}
) {
    val component = remember { Ol(type, start, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(type) { updateProperty(Ol::type, it) }
        set(start) { updateProperty(Ol::start, it) }
        set(className) { updateProperty(Ol::className, it) }
        set(id) { updateProperty(Ol::id, it) }
    }, content)
}
