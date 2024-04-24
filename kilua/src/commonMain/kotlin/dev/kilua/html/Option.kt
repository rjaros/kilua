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
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.helpers.PropertyListBuilder
import web.dom.HTMLOptionElement

/**
 * HTML Option component.
 */
public interface IOption : ITag<HTMLOptionElement> {
    /**
     * The value of the option.
     */
    public val value: String?

    /**
     * Set the value of the option.
     */
    @Composable
    public fun value(value: String?)

    /**
     * The label of the option.
     */
    public val label: String?

    /**
     * Set the label of the option.
     */
    @Composable
    public fun label(label: String?)

    /**
     * The selected state of the option.
     */
    public val selected: Boolean?

    /**
     * Set the selected state of the option.
     */
    @Composable
    public fun selected(selected: Boolean?)

    /**
     * The disabled state of the option.
     */
    public val disabled: Boolean?

    /**
     * Set the disabled state of the option.
     */
    @Composable
    public fun disabled(disabled: Boolean?)

    /**
     * The hidden state of the option.
     */
    public val hidden: Boolean?

    /**
     * Set the hidden state of the option.
     */
    @Composable
    public fun hidden(hidden: Boolean?)

    /**
     * The currently selected state of the option.
     */
    public val currentlySelected: Boolean

    /**
     * Set the currently selected state of the option.
     */
    @Composable
    public fun currentlySelected(currentlySelected: Boolean)
}

/**
 * HTML Option component.
 */
public open class Option(
    value: String? = null,
    label: String? = null,
    selected: Boolean? = null,
    disabled: Boolean? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLOptionElement>("option", className, renderConfig = renderConfig), IOption {

    /**
     * The value of the option.
     */
    public override var value: String? by updatingProperty(value) {
        if (it != null) {
            element.value = it
        } else {
            element.removeAttribute("value")
        }
    }

    /**
     * Set the value of the option.
     */
    @Composable
    public override fun value(value: String?): Unit = composableProperty("value", {
        this.value = null
    }) {
        this.value = value
    }

    /**
     * The label of the option.
     */
    public override var label: String? by updatingProperty(label) {
        if (it != null) {
            element.label = it
        } else {
            element.removeAttribute("label")
        }
    }

    /**
     * Set the label of the option.
     */
    @Composable
    public override fun label(label: String?): Unit = composableProperty("label", {
        this.label = null
    }) {
        this.label = label
    }

    /**
     * The selected attribute of the generated HTML option element.
     */
    public override var selected: Boolean? by updatingProperty(selected) {
        if (it != null) {
            element.defaultSelected = it
        } else {
            element.removeAttribute("selected")
        }
    }

    /**
     * Set the selected state of the option.
     */
    @Composable
    public override fun selected(selected: Boolean?): Unit = composableProperty("selected", {
        this.selected = null
    }) {
        this.selected = selected
    }

    /**
     * Whether the option is disabled.
     */
    public override var disabled: Boolean? by updatingProperty(disabled) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
    }

    /**
     * Set the disabled state of the option.
     */
    @Composable
    public override fun disabled(disabled: Boolean?): Unit = composableProperty("disabled", {
        this.disabled = null
    }) {
        this.disabled = disabled
    }

    /**
     * Whether the option is hidden.
     */
    public override var hidden: Boolean? by updatingProperty {
        if (it != null) {
            element.hidden = it
        } else {
            element.removeAttribute("hidden")
        }
    }

    /**
     * Set the hidden state of the option.
     */
    @Composable
    public override fun hidden(hidden: Boolean?): Unit = composableProperty("hidden", {
        this.hidden = null
    }) {
        this.hidden = hidden
    }

    /**
     * Whether the option is currently selected.
     */
    public override var currentlySelected: Boolean by updatingProperty(selected ?: false) {
        element.selected = it
    }

    /**
     * Set the currently selected state of the option.
     */
    @Composable
    public override fun currentlySelected(currentlySelected: Boolean): Unit = composableProperty("currentlySelected", {
        this.currentlySelected = false
    }) {
        this.currentlySelected = currentlySelected
    }

    init {
        if (renderConfig.isDom) {
            if (value != null) {
                @Suppress("LeakingThis")
                element.value = value
            }
            if (label != null) {
                @Suppress("LeakingThis")
                element.label = label
            }
            if (selected != null) {
                @Suppress("LeakingThis")
                element.defaultSelected = selected
            }
            if (disabled != null) {
                @Suppress("LeakingThis")
                element.disabled = disabled
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::value, ::label, ::selected, ::disabled, ::hidden)
    }

}

/**
 * Creates a [Option] component.
 *
 * @param value the value of the option
 * @param label the label of the option
 * @param selected the selected state of the option
 * @param disabled whether the option is disabled
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [Option] component
 */
@Composable
public fun IComponent.option(
    value: String? = null,
    label: String? = null,
    selected: Boolean? = null,
    disabled: Boolean? = null,
    className: String? = null,
    content: @Composable IOption.() -> Unit = {}
): Option {
    val component = remember { Option(value, label, selected, disabled, className, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(value) { updateProperty(Option::value, it) }
        set(label) { updateProperty(Option::label, it) }
        set(selected) { updateProperty(Option::selected, it) }
        set(disabled) { updateProperty(Option::disabled, it) }
        set(className) { updateProperty(Option::className, it) }
    }, content)
    return component
}
