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

package dev.kilua.form.check

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.form.InputType
import dev.kilua.form.TriStateFormControl
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.unsafeCast
import js.core.JsPrimitives.toJsString
import web.events.Event
import web.html.HTMLInputElement

/**
 * Tri-state CheckBox input component.
 */
public interface ITriStateCheckBox : ITag<HTMLInputElement>, TriStateFormControl, WithStateFlow<Boolean?> {

    /**
     * The name attribute of the generated HTML input element.
     */
    public val name: String?

    /**
     * Set the name attribute of the generated HTML input element.
     */
    @Composable
    public fun name(name: String?)


    /**
     * The disabled attribute of the generated HTML input element.
     */
    public val disabled: Boolean?

    /**
     * Set the disabled attribute of the generated HTML input element.
     */
    @Composable
    public fun disabled(disabled: Boolean?)

    /**
     * Set the required attribute of the generated HTML input element.
     */
    @Composable
    public fun required(required: Boolean?)

    /**
     * The checked attribute of the generated HTML input element.
     */
    public val defaultChecked: Boolean?

    /**
     * Set the checked attribute of the generated HTML input element.
     */
    @Composable
    public fun defaultChecked(defaultChecked: Boolean?)

    /**
     * The additional value attribute of the generated HTML input element.
     */
    public val extraValue: String?

    /**
     * Set the additional value attribute of the generated HTML input element.
     */
    @Composable
    public fun extraValue(extraValue: String?)
}

/**
 * Tri-state CheckBox input component.
 */
public open class TriStateCheckBox(
    value: Boolean? = null,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default,
    protected val withStateFlowDelegate: WithStateFlowDelegate<Boolean?> = WithStateFlowDelegateImpl()
) : Tag<HTMLInputElement>("input", className, id, renderConfig = renderConfig), TriStateFormControl,
    WithStateFlow<Boolean?> by withStateFlowDelegate, ITriStateCheckBox {

    public override var value: Boolean? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        if (it != null) {
            element.checked = it
            element.indeterminate = false
        } else {
            element.checked = false
            element.indeterminate = true
        }
    }

    /**
     * The name attribute of the generated HTML input element.
     */
    public override var name: String? by updatingProperty(name) {
        if (it != null) {
            element.name = it
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * Set the name attribute of the generated HTML input element.
     */
    @Composable
    public override fun name(name: String?): Unit = composableProperty("name", {
        this.name = null
    }) {
        this.name = name
    }

    /**
     * The disabled attribute of the generated HTML input element.
     */
    public override var disabled: Boolean? by updatingProperty(disabled) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
    }

    /**
     * Set the disabled attribute of the generated HTML input element.
     */
    @Composable
    public override fun disabled(disabled: Boolean?): Unit = composableProperty("disabled", {
        this.disabled = null
    }) {
        this.disabled = disabled
    }

    /**
     * The required attribute of the generated HTML input element.
     */
    public override var required: Boolean? by updatingProperty(required) {
        if (it != null) {
            element.required = it
        } else {
            element.removeAttribute("required")
        }
    }

    /**
     * Set the required attribute of the generated HTML input element.
     */
    @Composable
    public override fun required(required: Boolean?): Unit = composableProperty("required", {
        this.required = null
    }) {
        this.required = required
    }

    /**
     * The checked attribute of the generated HTML input element.
     */
    public override var defaultChecked: Boolean? by updatingProperty(name = "checked") {
        if (it != null) {
            element.defaultChecked = it
        } else {
            element.removeAttribute("checked")
        }
    }

    /**
     * Set the checked attribute of the generated HTML input element.
     */
    @Composable
    public override fun defaultChecked(defaultChecked: Boolean?): Unit = composableProperty("defaultChecked", {
        this.defaultChecked = null
    }) {
        this.defaultChecked = defaultChecked
    }

    /**
     * The additional value attribute of the generated HTML input element.
     */
    public override var extraValue: String? = null
        set(value) {
            field = value
            setAttribute("value", extraValue)
        }

    /**
     * Set the additional value attribute of the generated HTML input element.
     */
    @Composable
    public override fun extraValue(extraValue: String?): Unit = composableProperty("extraValue", {
        this.extraValue = null
    }) {
        this.extraValue = extraValue
    }

    public override var customValidity: String? by updatingProperty {
        element.setCustomValidity(it ?: "")
    }

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
        if (renderConfig.isDom) {
            if (value != null) {
                @Suppress("LeakingThis")
                element.checked = value
            } else {
                @Suppress("LeakingThis")
                element.indeterminate = true
            }
            @Suppress("LeakingThis")
            element.type = InputType.Checkbox.value.toJsString().unsafeCast()
            if (name != null) {
                @Suppress("LeakingThis")
                element.name = name
            }
            if (disabled != null) {
                @Suppress("LeakingThis")
                element.disabled = disabled
            }
            if (required != null) {
                @Suppress("LeakingThis")
                element.required = required
            }
        }
        @Suppress("LeakingThis")
        setAttribute("type", "checkbox")
        @Suppress("LeakingThis")
        onEventDirect<Event>("click") {
            cycleValue()
        }
    }

    /**
     * Cycle through the three states.
     */
    protected open fun cycleValue() {
        when (this.value) {
            null -> {
                this.value = true
            }

            true -> {
                this.value = false
            }

            else -> {
                this.value = null
            }
        }
    }

    override fun getValueAsString(): String? {
        return value?.toString()
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::name,
            ::disabled,
            ::required,
            ::autofocus,
        )
        propertyListBuilder.addByName("checked")
    }
}

/**
 * Creates [TriStateCheckBox] component, returning a reference.
 *
 * @param value initial value
 * @param name the name of the input
 * @param disabled whether the input is disabled
 * @param required whether the input is required
 * @param className the CSS class name
 * @param id the ID of the input
 * @param setup a function for setting up the component
 * @return a [TriStateCheckBox] component
 */
@Composable
public fun IComponent.triStateCheckBoxRef(
    value: Boolean? = null,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITriStateCheckBox.() -> Unit = {}
): TriStateCheckBox {
    val component = remember { TriStateCheckBox(value, name, disabled, required, className, id, renderConfig) }
    ComponentNode(component, {
        set(value) { updateProperty(TriStateCheckBox::value, it) }
        set(name) { updateProperty(TriStateCheckBox::name, it) }
        set(disabled) { updateProperty(TriStateCheckBox::disabled, it) }
        set(required) { updateProperty(TriStateCheckBox::required, it) }
        set(className) { updateProperty(TriStateCheckBox::className, it) }
        set(id) { updateProperty(TriStateCheckBox::id, it) }
    }, setup)
    return component
}

/**
 * Creates [TriStateCheckBox] component.
 *
 * @param value initial value
 * @param name the name of the input
 * @param disabled whether the input is disabled
 * @param required whether the input is required
 * @param className the CSS class name
 * @param id the ID of the input
 * @param setup a function for setting up the component
 */
@Composable
public fun IComponent.triStateCheckBox(
    value: Boolean? = null,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITriStateCheckBox.() -> Unit = {}
) {
    val component = remember { TriStateCheckBox(value, name, disabled, required, className, id, renderConfig) }
    ComponentNode(component, {
        set(value) { updateProperty(TriStateCheckBox::value, it) }
        set(name) { updateProperty(TriStateCheckBox::name, it) }
        set(disabled) { updateProperty(TriStateCheckBox::disabled, it) }
        set(required) { updateProperty(TriStateCheckBox::required, it) }
        set(className) { updateProperty(TriStateCheckBox::className, it) }
        set(id) { updateProperty(TriStateCheckBox::id, it) }
    }, setup)
}
