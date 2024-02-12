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

import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.form.BoolFormControl
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.toKebabCase
import web.dom.HTMLInputElement
import web.dom.events.Event

/**
 * Type of the check input control (checkbox or radiobutton).
 */
public enum class CheckInputType {
    Checkbox,
    Radio;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Abstract class for check input control (checkbox or radiobutton).
 */
public abstract class AbstractCheck(
    type: CheckInputType,
    value: Boolean = false,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val withStateFlowDelegate: WithStateFlowDelegate<Boolean> = WithStateFlowDelegateImpl()
) : Tag<HTMLInputElement>("input", className, id, renderConfig), BoolFormControl,
    WithStateFlow<Boolean> by withStateFlowDelegate {

    public override var value: Boolean by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        element.checked = it
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
     * The autofocus attribute of the generated HTML input element.
     */
    public override var autofocus: Boolean? by updatingProperty {
        if (it != null) {
            element.autofocus = it
        } else {
            element.removeAttribute("autofocus")
        }
    }

    /**
     * The checked attribute of the generated HTML input element.
     */
    public open var defaultChecked: Boolean? by updatingProperty(name = "checked") {
        if (it != null) {
            element.defaultChecked = it
        } else {
            element.removeAttribute("checked")
        }
    }

    /**
     * The additional value attribute of the generated HTML input element.
     */
    public open var extraValue: String? = null
        set(value) {
            field = value
            setAttribute("value", extraValue)
        }

    public override var customValidity: String? by updatingProperty {
        element.setCustomValidity(it ?: "")
    }

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
        if (renderConfig.isDom) {
            element.checked = value
            element.type = type.value
            if (name != null) {
                element.name = name
            }
            if (disabled != null) {
                element.disabled = disabled
            }
            if (required != null) {
                element.required = required
            }
            @Suppress("LeakingThis")
            onEventDirect<Event>("click") {
                setInternalValueFromBoolean(element.checked)
            }
            @Suppress("LeakingThis")
            onEventDirect<Event>("change") {
                setInternalValueFromBoolean(element.checked)
            }
        }
        @Suppress("LeakingThis")
        setAttribute("type", type.value)
    }

    /**
     * Set value from string without setting element value again.
     */
    protected open fun setInternalValueFromBoolean(boolean: Boolean) {
        if (value != boolean) {
            propertyValues["value"] = boolean
            withStateFlowDelegate.updateStateFlow(boolean)
        }
    }

    override fun getValueAsString(): String? {
        return value.toString()
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
