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
import web.dom.HTMLOptGroupElement

/**
 * HTML Optgroup component.
 */
public interface IOptgroup : ITag<HTMLOptGroupElement> {
    /**
     * The label of the option group.
     */
    public val label: String?

    /**
     * Set the label of the option group.
     */
    @Composable
    public fun label(label: String?)

    /**
     * The disabled state of the option group.
     */
    public val disabled: Boolean?

    /**
     * Set the disabled state of the option group.
     */
    @Composable
    public fun disabled(disabled: Boolean?)
}

/**
 * HTML Optgroup component.
 */
public open class Optgroup(
    label: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLOptGroupElement>("optgroup", className, id, renderConfig = renderConfig), IOptgroup {

    /**
     * The label of the option group.
     */
    public override var label: String? by updatingProperty(label) {
        if (it != null) {
            element.label = it
        } else {
            element.removeAttribute("label")
        }
    }

    /**
     * Set the label of the option group.
     */
    @Composable
    public override fun label(label: String?): Unit = composableProperty("label", {
        this.label = null
    }) {
        this.label = label
    }

    /**
     * Whether the option group is disabled.
     */
    public override var disabled: Boolean? by updatingProperty(disabled) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
    }

    /**
     * Set the disabled state of the option group.
     */
    @Composable
    public override fun disabled(disabled: Boolean?): Unit = composableProperty("disabled", {
        this.disabled = null
    }) {
        this.disabled = disabled
    }

    init {
        if (renderConfig.isDom) {
            if (label != null) {
                @Suppress("LeakingThis")
                element.label = label
            }
            if (disabled != null) {
                @Suppress("LeakingThis")
                element.disabled = disabled
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::label, ::disabled)
    }

}

/**
 * Creates a [Optgroup] component, returning a reference.
 *
 * @param label the label of the option group
 * @param disabled whether the option group is disabled
 * @param className the CSS class name
 * @param id the ID attribute of the option group
 * @param content the content of the component
 * @return the [Optgroup] component
 */
@Composable
public fun IComponent.optgroupRef(
    label: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IOptgroup.() -> Unit = {}
): Optgroup {
    val component = remember { Optgroup(label, disabled, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(label) { updateProperty(Optgroup::label, it) }
        set(disabled) { updateProperty(Optgroup::disabled, it) }
        set(className) { updateProperty(Optgroup::className, it) }
        set(id) { updateProperty(Optgroup::id, it) }
    }, content)
    return component
}

/**
 * Creates a [Optgroup] component.
 *
 * @param label the label of the option group
 * @param disabled whether the option group is disabled
 * @param className the CSS class name
 * @param id the ID attribute of the option group
 * @param content the content of the component
 */
@Composable
public fun IComponent.optgroup(
    label: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IOptgroup.() -> Unit = {}
) {
    val component = remember { Optgroup(label, disabled, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(label) { updateProperty(Optgroup::label, it) }
        set(disabled) { updateProperty(Optgroup::disabled, it) }
        set(className) { updateProperty(Optgroup::className, it) }
        set(id) { updateProperty(Optgroup::id, it) }
    }, content)
}
