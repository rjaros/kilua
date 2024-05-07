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

import androidx.compose.runtime.Composable
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import web.dom.HTMLInputElement
import web.dom.events.Event

/**
 * Base interface form HTML input components.
 */
public interface IInput<T : Any> : ITag<HTMLInputElement>, GenericFormControl<T>, WithStateFlow<T?> {

    /**
     * The type attribute of the generated HTML input element.
     */
    public val type: InputType

    /**
     * Set the type attribute of the generated HTML input element.
     */
    @Composable
    public fun type(type: InputType)

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
     * The maxlength attribute of the generated HTML input element.
     */
    public val maxlength: Int?

    /**
     * Set the maxlength attribute of the generated HTML input element.
     */
    @Composable
    public fun maxlength(maxlength: Int?)

    /**
     * The placeholder attribute of the generated HTML input element.
     */
    public val placeholder: String?

    /**
     * Set the placeholder attribute of the generated HTML input element.
     */
    @Composable
    public fun placeholder(placeholder: String?)

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
     * The autocomplete attribute of the generated HTML input element.
     */
    public val autocomplete: Autocomplete?

    /**
     * Set the autocomplete attribute of the generated HTML input element.
     */
    @Composable
    public fun autocomplete(autocomplete: Autocomplete?)

    /**
     * The readonly attribute of the generated HTML input element.
     */
    public val readonly: Boolean?

    /**
     * Set the readonly attribute of the generated HTML input element.
     */
    @Composable
    public fun readonly(readonly: Boolean?)

    /**
     * The list attribute of the generated HTML input element.
     */
    public val list: String?

    /**
     * Set the list attribute of the generated HTML input element.
     */
    @Composable
    public fun list(list: String?)

    /**
     * The value attribute of the generated HTML input element.
     */
    public val defaultValue: T?

    /**
     * Set the value attribute of the generated HTML input element.
     */
    @Composable
    public fun defaultValue(defaultValue: T?)

    /**
     * The input mask options.
     */
    public var maskOptions: MaskOptions?

    /**
     * Install the input mask controller.
     */
    public fun installMask()

    /**
     * Uninstall the input mask controller.
     */
    public fun uninstallMask()
}

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
) : Tag<HTMLInputElement>("input", className, id, null, renderConfig), GenericFormControl<T>,
    WithStateFlow<T?> by withStateFlowDelegate, IInput<T> {

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
    public override var type: InputType by updatingProperty(type) {
        element.type = it.value
    }

    /**
     * Set the type attribute of the generated HTML input element.
     */
    @Composable
    public override fun type(type: InputType): Unit = composableProperty("type", {
        this.type = InputType.Text
    }) {
        this.type = type
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
     * The maxlength attribute of the generated HTML input element.
     */
    public override var maxlength: Int? by updatingProperty(maxlength) {
        if (it != null) {
            element.maxLength = it
        } else {
            element.removeAttribute("maxlength")
        }
    }

    /**
     * Set the maxlength attribute of the generated HTML input element.
     */
    @Composable
    public override fun maxlength(maxlength: Int?): Unit = composableProperty("maxlength", {
        this.maxlength = null
    }) {
        this.maxlength = maxlength
    }

    /**
     * The placeholder attribute of the generated HTML input element.
     */
    public override var placeholder: String? by updatingProperty(placeholder) {
        if (it != null) {
            element.placeholder = it
        } else {
            element.removeAttribute("placeholder")
        }
    }

    /**
     * Set the placeholder attribute of the generated HTML input element.
     */
    @Composable
    public override fun placeholder(placeholder: String?): Unit = composableProperty("placeholder", {
        this.placeholder = null
    }) {
        this.placeholder = placeholder
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
     * The autocomplete attribute of the generated HTML input element.
     */
    public override var autocomplete: Autocomplete? by updatingProperty {
        if (it != null) {
            element.autocomplete = it.value
        } else {
            element.removeAttribute("autocomplete")
        }
    }

    /**
     * Set the autocomplete attribute of the generated HTML input element.
     */
    @Composable
    public override fun autocomplete(autocomplete: Autocomplete?): Unit = composableProperty("autocomplete", {
        this.autocomplete = null
    }) {
        this.autocomplete = autocomplete
    }

    /**
     * The readonly attribute of the generated HTML input element.
     */
    public override var readonly: Boolean? by updatingProperty {
        if (it != null) {
            element.readOnly = it
        } else {
            element.removeAttribute("readonly")
        }
    }

    /**
     * Set the readonly attribute of the generated HTML input element.
     */
    @Composable
    public override fun readonly(readonly: Boolean?): Unit = composableProperty("readonly", {
        this.readonly = null
    }) {
        this.readonly = readonly
    }

    /**
     * The list attribute of the generated HTML input element.
     */
    public override var list: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("list", it)
        } else {
            element.removeAttribute("list")
        }
    }

    /**
     * Set the list attribute of the generated HTML input element.
     */
    @Composable
    public override fun list(list: String?): Unit = composableProperty("list", {
        this.list = null
    }) {
        this.list = list
    }

    /**
     * The value attribute of the generated HTML input element.
     */
    public override var defaultValue: T? = null
        set(value) {
            field = value
            if (this.value == null) {
                this.value = value
            }
            setAttribute("value", valueToString(defaultValue))
        }

    /**
     * Set the value attribute of the generated HTML input element.
     */
    @Composable
    public override fun defaultValue(defaultValue: T?): Unit = composableProperty("defaultValue", {
        this.defaultValue = null
    }) {
        this.defaultValue = defaultValue
    }

    public override var customValidity: String? by updatingProperty {
        element.setCustomValidity(it ?: "")
    }

    /**
     * The input mask options.
     */
    public override var maskOptions: MaskOptions? = null
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
                @Suppress("LeakingThis")
                element.value = value.toString()
            }
            @Suppress("LeakingThis")
            element.type = type.value
            if (name != null) {
                @Suppress("LeakingThis")
                element.name = name
            }
            if (maxlength != null) {
                @Suppress("LeakingThis")
                element.maxLength = maxlength
            }
            if (placeholder != null) {
                @Suppress("LeakingThis")
                element.placeholder = placeholder
            }
            if (disabled != null) {
                @Suppress("LeakingThis")
                element.disabled = disabled
            }
            if (required != null) {
                @Suppress("LeakingThis")
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
    public override fun installMask() {
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
    public override fun uninstallMask() {
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
