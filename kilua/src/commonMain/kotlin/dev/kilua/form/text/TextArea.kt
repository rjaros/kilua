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

package dev.kilua.form.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.form.StringFormControl
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.toKebabCase
import web.dom.HTMLTextAreaElement

/**
 * Textarea wrap types.
 */
public enum class WrapType {
    Soft,
    Hard;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Textarea component.
 */
public interface ITextArea : ITag<HTMLTextAreaElement>, StringFormControl, WithStateFlow<String?> {
    /**
     * The number of columns of the textarea.
     */
    public val cols: Int?

    /**
     * Set the number of columns of the textarea.
     */
    @Composable
    public fun cols(cols: Int?)

    /**
     * The number of rows of the textarea.
     */
    public val rows: Int?

    /**
     * Set the number of rows of the textarea.
     */
    @Composable
    public fun rows(rows: Int?)

    /**
     * The name attribute of the generated HTML textarea element.
     */
    public val name: String?

    /**
     * Set the name attribute of the generated HTML textarea element.
     */
    @Composable
    public fun name(name: String?)

    /**
     * The maxlength attribute of the generated HTML textarea element.
     */
    public val maxlength: Int?

    /**
     * Set the maxlength attribute of the generated HTML textarea element.
     */
    @Composable
    public fun maxlength(maxlength: Int?)

    /**
     * The placeholder attribute of the generated HTML textarea element.
     */
    public val placeholder: String?

    /**
     * Set the placeholder attribute of the generated HTML textarea element.
     */
    @Composable
    public fun placeholder(placeholder: String?)

    /**
     * The disabled attribute of the generated HTML textarea element.
     */
    public val disabled: Boolean?

    /**
     * Set the disabled attribute of the generated HTML textarea element.
     */
    @Composable
    public fun disabled(disabled: Boolean?)

    /**
     * Set the required attribute of the generated HTML textarea element.
     */
    @Composable
    public fun required(required: Boolean?)

    /**
     * The readonly attribute of the generated HTML textarea element.
     */
    public val readonly: Boolean?

    /**
     * Set the readonly attribute of the generated HTML textarea element.
     */
    @Composable
    public fun readonly(readonly: Boolean?)

    /**
     * The wrap attribute of the generated HTML textarea element.
     */
    public val wrap: WrapType?

    /**
     * Set the wrap attribute of the generated HTML textarea element.
     */
    @Composable
    public fun wrap(wrap: WrapType?)
}

/**
 * Textarea component.
 */
public open class TextArea(
    value: String? = null,
    cols: Int? = null,
    rows: Int? = null,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default,
    protected val withStateFlowDelegate: WithStateFlowDelegate<String?> = WithStateFlowDelegateImpl()
) : Tag<HTMLTextAreaElement>("textarea", className, id, renderConfig = renderConfig),
    StringFormControl, WithStateFlow<String?> by withStateFlowDelegate, ITextArea {

    public override var value: String? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        element.value = it ?: ""
    }

    /**
     * The number of columns of the textarea.
     */
    public override var cols: Int? by updatingProperty(cols) {
        if (it != null) {
            element.cols = it
        } else {
            element.removeAttribute("cols")
        }
    }

    /**
     * Set the number of columns of the textarea.
     */
    @Composable
    override fun cols(cols: Int?): Unit = composableProperty("cols", {
        this.cols = null
    }) {
        this.cols = cols
    }

    /**
     * The number of rows of the textarea.
     */
    public override var rows: Int? by updatingProperty(rows) {
        if (it != null) {
            element.rows = it
        } else {
            element.removeAttribute("rows")
        }
    }

    /**
     * Set the number of rows of the textarea.
     */
    @Composable
    override fun rows(rows: Int?): Unit = composableProperty("rows", {
        this.rows = null
    }) {
        this.rows = rows
    }

    /**
     * The name attribute of the generated HTML textarea element.
     */
    public override var name: String? by updatingProperty(name) {
        if (it != null) {
            element.name = it
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * Set the name attribute of the generated HTML textarea element.
     */
    @Composable
    override fun name(name: String?): Unit = composableProperty("name", {
        this.name = null
    }) {
        this.name = name
    }

    /**
     * The maxlength attribute of the generated HTML textarea element.
     */
    public override var maxlength: Int? by updatingProperty(maxlength) {
        if (it != null) {
            element.maxLength = it
        } else {
            element.removeAttribute("maxlength")
        }
    }

    /**
     * Set the maxlength attribute of the generated HTML textarea element.
     */
    @Composable
    override fun maxlength(maxlength: Int?): Unit = composableProperty("maxlength", {
        this.maxlength = null
    }) {
        this.maxlength = maxlength
    }

    /**
     * The placeholder attribute of the generated HTML textarea element.
     */
    public override var placeholder: String? by updatingProperty(placeholder) {
        if (it != null) {
            element.placeholder = it
        } else {
            element.removeAttribute("placeholder")
        }
    }

    /**
     * Set the placeholder attribute of the generated HTML textarea element.
     */
    @Composable
    override fun placeholder(placeholder: String?): Unit = composableProperty("placeholder", {
        this.placeholder = null
    }) {
        this.placeholder = placeholder
    }

    /**
     * The disabled attribute of the generated HTML textarea element.
     */
    public override var disabled: Boolean? by updatingProperty(disabled) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
    }

    /**
     * Set the disabled attribute of the generated HTML textarea element.
     */
    @Composable
    override fun disabled(disabled: Boolean?): Unit = composableProperty("disabled", {
        this.disabled = null
    }) {
        this.disabled = disabled
    }

    /**
     * The required attribute of the generated HTML textarea element.
     */
    public override var required: Boolean? by updatingProperty(required) {
        if (it != null) {
            element.required = it
        } else {
            element.removeAttribute("required")
        }
    }

    /**
     * Set the required attribute of the generated HTML textarea element.
     */
    @Composable
    public override fun required(required: Boolean?): Unit = composableProperty("required", {
        this.required = null
    }) {
        this.required = required
    }

    /**
     * The readonly attribute of the generated HTML textarea element.
     */
    public override var readonly: Boolean? by updatingProperty {
        if (it != null) {
            element.readOnly = it
        } else {
            element.removeAttribute("readonly")
        }
    }

    /**
     * Set the readonly attribute of the generated HTML textarea element.
     */
    @Composable
    override fun readonly(readonly: Boolean?): Unit = composableProperty("readonly", {
        this.readonly = null
    }) {
        this.readonly = readonly
    }

    /**
     * The wrap attribute of the generated HTML textarea element.
     */
    public override var wrap: WrapType? by updatingProperty {
        if (it != null) {
            element.wrap = it.value
        } else {
            element.removeAttribute("wrap")
        }
    }

    /**
     * Set the wrap attribute of the generated HTML textarea element.
     */
    @Composable
    override fun wrap(wrap: WrapType?): Unit = composableProperty("wrap", {
        this.wrap = null
    }) {
        this.wrap = wrap
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
                element.value = value
            }
            if (cols != null) {
                @Suppress("LeakingThis")
                element.cols = cols
            }
            if (rows != null) {
                @Suppress("LeakingThis")
                element.rows = rows
            }
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
            onInputDirect {
                setInternalValueFromString(element.value)
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::cols,
            ::rows,
            ::name,
            ::maxlength,
            ::placeholder,
            ::disabled,
            ::required,
            ::autofocus,
            ::readonly,
            ::wrap
        )
    }

    override fun getValueAsString(): String? {
        return value
    }

    protected open fun stringToValue(text: String?): String? {
        return if (text.isNullOrEmpty()) {
            null
        } else {
            text
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
}

/**
 * Creates a [TextArea] component, returnig a reference.
 *
 * @param value the initial value
 * @param cols the number of columns of the textarea
 * @param rows the number of rows of the textarea
 * @param name the name attribute of the generated HTML textarea element
 * @param maxlength the maxlength attribute of the generated HTML textarea element
 * @param placeholder the placeholder attribute of the generated HTML textarea element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param className the CSS class name
 * @param id the ID of the component
 * @param setup a function for setting up the component
 * @return A [TextArea] component.
 */
@Composable
public fun IComponent.textAreaRef(
    value: String? = null,
    cols: Int? = null,
    rows: Int? = null,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITextArea.() -> Unit = {}
): TextArea {
    val component =
        remember {
            TextArea(
                value,
                cols,
                rows,
                name,
                maxlength,
                placeholder,
                disabled,
                required,
                className,
                id,
                renderConfig
            )
        }
    ComponentNode(component, {
        set(value) { updateProperty(TextArea::value, it) }
        set(cols) { updateProperty(TextArea::cols, it) }
        set(rows) { updateProperty(TextArea::rows, it) }
        set(name) { updateProperty(TextArea::name, it) }
        set(maxlength) { updateProperty(TextArea::maxlength, it) }
        set(placeholder) { updateProperty(TextArea::placeholder, it) }
        set(disabled) { updateProperty(TextArea::disabled, it) }
        set(required) { updateProperty(TextArea::required, it) }
        set(className) { updateProperty(TextArea::className, it) }
        set(id) { updateProperty(TextArea::id, it) }
    }, setup)
    return component
}

/**
 * Creates a [TextArea] component.
 *
 * @param value the initial value
 * @param cols the number of columns of the textarea
 * @param rows the number of rows of the textarea
 * @param name the name attribute of the generated HTML textarea element
 * @param maxlength the maxlength attribute of the generated HTML textarea element
 * @param placeholder the placeholder attribute of the generated HTML textarea element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param className the CSS class name
 * @param id the ID of the component
 * @param setup a function for setting up the component
 */
@Composable
public fun IComponent.textArea(
    value: String? = null,
    cols: Int? = null,
    rows: Int? = null,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITextArea.() -> Unit = {}
) {
    val component =
        remember {
            TextArea(
                value,
                cols,
                rows,
                name,
                maxlength,
                placeholder,
                disabled,
                required,
                className,
                id,
                renderConfig
            )
        }
    ComponentNode(component, {
        set(value) { updateProperty(TextArea::value, it) }
        set(cols) { updateProperty(TextArea::cols, it) }
        set(rows) { updateProperty(TextArea::rows, it) }
        set(name) { updateProperty(TextArea::name, it) }
        set(maxlength) { updateProperty(TextArea::maxlength, it) }
        set(placeholder) { updateProperty(TextArea::placeholder, it) }
        set(disabled) { updateProperty(TextArea::disabled, it) }
        set(required) { updateProperty(TextArea::required, it) }
        set(className) { updateProperty(TextArea::className, it) }
        set(id) { updateProperty(TextArea::id, it) }
    }, setup)
}
