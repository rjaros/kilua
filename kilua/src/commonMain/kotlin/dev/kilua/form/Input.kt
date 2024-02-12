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

package dev.kilua.form

import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import web.dom.HTMLInputElement
import web.dom.events.Event

/**
 * Base abstract class for HTML input components.
 */
public abstract class Input<T : Any>(
    value: T? = null,
    type: InputType = InputType.Text,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val withStateFlowDelegate: WithStateFlowDelegate<T?> = WithStateFlowDelegateImpl()
) : Tag<HTMLInputElement>("input", className, id, renderConfig), GenericFormControl<T>,
    WithStateFlow<T?> by withStateFlowDelegate {

    public override var value: T? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        if (type != InputType.File) {
            if (mask == null) {
                element.value = valueToString(it) ?: ""
            } else {
                element.value = valueToString(it) ?: ""
                mask!!.refresh()
                val newValue =
                    stringToValue(mask?.getValue()?.let { maskOptions?.maskNumericValue(it) ?: it }?.ifEmpty { null })
                if (this.value != newValue) {
                    this.value = newValue
                }
            }
        }
    }

    /**
     * The type attribute of the generated HTML input element.
     */
    public open var type: InputType by updatingProperty(type) {
        element.type = it.value
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
     * The maxlength attribute of the generated HTML input element.
     */
    public var maxlength: Int? by updatingProperty(maxlength) {
        if (it != null) {
            element.maxLength = it
        } else {
            element.removeAttribute("maxlength")
        }
    }

    /**
     * The placeholder attribute of the generated HTML input element.
     */
    public var placeholder: String? by updatingProperty(placeholder) {
        if (it != null) {
            element.placeholder = it
        } else {
            element.removeAttribute("placeholder")
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

    public override var required: Boolean? by updatingProperty(required) {
        if (it != null) {
            element.required = it
        } else {
            element.removeAttribute("required")
        }
    }

    /**
     * The autocomplete attribute of the generated HTML input element.
     */
    public open var autocomplete: Autocomplete? by updatingProperty {
        if (it != null) {
            element.autocomplete = it.value
        } else {
            element.removeAttribute("autocomplete")
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
     * The readonly attribute of the generated HTML input element.
     */
    public open var readonly: Boolean? by updatingProperty {
        if (it != null) {
            element.readOnly = it
        } else {
            element.removeAttribute("readonly")
        }
    }

    /**
     * The list attribute of the generated HTML input element.
     */
    public open var list: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("list", it)
        } else {
            element.removeAttribute("list")
        }
    }

    /**
     * The value attribute of the generated HTML input element.
     */
    public open var defaultValue: T? = null
        set(value) {
            field = value
            if (this.value == null) {
                this.value = value
            }
            setAttribute("value", valueToString(defaultValue))
        }

    public override var customValidity: String? by updatingProperty {
        element.setCustomValidity(it ?: "")
    }

    /**
     * The input mask options.
     */
    public open var maskOptions: MaskOptions? = null
        set(value) {
            if (field != null) {
                uninstallMask()
            }
            field = value
            installMask()
        }

    /**
     * The input mask controller.
     */
    protected var mask: Mask? = null

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
        if (renderConfig.isDom) {
            if (value != null) {
                element.value = value.toString()
            }
            element.type = type.value
            if (name != null) {
                element.name = name
            }
            if (maxlength != null) {
                element.maxLength = maxlength
            }
            if (placeholder != null) {
                element.placeholder = placeholder
            }
            if (disabled != null) {
                element.disabled = disabled
            }
            if (required != null) {
                element.required = required
            }
            @Suppress("LeakingThis")
            onEventDirect<Event>("input") {
                if (mask == null) setInternalValueFromString(element.value)
            }
        }
    }

    /**
     * Set value from string without setting element value again.
     */
    protected open fun setInternalValueFromString(text: String?) {
        val newValue = stringToValue(text)
        if (value != newValue) {
            if (newValue != null) {
                propertyValues["value"] = newValue
            } else {
                propertyValues.remove("value")
            }
            withStateFlowDelegate.updateStateFlow(newValue)
        }
    }

    /**
     * Convert string to value.
     */
    protected abstract fun stringToValue(text: String?): T?

    /**
     * Convert value to String.
     */
    protected open fun valueToString(value: T?): String? {
        return value?.toString()
    }

    override fun getValueAsString(): String? {
        return valueToString(value)
    }

    /**
     * Install the input mask controller.
     */
    public open fun installMask() {
        if (renderConfig.isDom && maskOptions != null) {
            if (MaskManager.factory == null) throw IllegalStateException("Input mask module has not been initialized")
            mask = MaskManager.factory!!.createMask(element, maskOptions!!)
            mask!!.onChange {
                setInternalValueFromString(it?.let { maskOptions?.maskNumericValue(it) ?: it }?.ifEmpty { null })
            }
        }
    }

    /**
     * Uninstall the input mask controller.
     */
    public open fun uninstallMask() {
        mask?.destroy()
        mask = null
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::type,
            ::name,
            ::maxlength,
            ::placeholder,
            ::disabled,
            ::required,
            ::autocomplete,
            ::autofocus,
            ::readonly
        )
    }

}
