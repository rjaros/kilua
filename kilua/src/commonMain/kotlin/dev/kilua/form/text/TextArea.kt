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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.form.StringFormControl
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.toKebabCase
import org.w3c.dom.HTMLTextAreaElement

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
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val withStateFlowDelegate: WithStateFlowDelegate<String?> = WithStateFlowDelegateImpl()
) : Tag<HTMLTextAreaElement>("textarea", className, renderConfig),
    StringFormControl, WithStateFlow<String?> by withStateFlowDelegate {

    public override var value: String? by updatingProperty(
        value,
        skipUpdate,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        element.value = it ?: ""
    }

    /**
     * The number of columns of the textarea.
     */
    public var cols: Int? by updatingProperty(cols, skipUpdate) {
        if (it != null) {
            element.cols = it
        } else {
            element.removeAttribute("cols")
        }
    }

    /**
     * The number of rows of the textarea.
     */
    public var rows: Int? by updatingProperty(rows, skipUpdate) {
        if (it != null) {
            element.rows = it
        } else {
            element.removeAttribute("rows")
        }
    }

    public override var name: String? by updatingProperty(name, skipUpdate) {
        if (it != null) {
            element.name = it
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * The maxlength attribute of the generated HTML textarea element.
     */
    public var maxlength: Int? by updatingProperty(maxlength, skipUpdate) {
        if (it != null) {
            element.maxLength = it
        } else {
            element.removeAttribute("maxlength")
        }
    }

    /**
     * The placeholder attribute of the generated HTML textarea element.
     */
    public var placeholder: String? by updatingProperty(placeholder, skipUpdate) {
        if (it != null) {
            element.placeholder = it
        } else {
            element.removeAttribute("placeholder")
        }
    }

    public override var disabled: Boolean? by updatingProperty(disabled, skipUpdate) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
    }

    public override var required: Boolean? by updatingProperty(required, skipUpdate) {
        if (it != null) {
            element.required = it
        } else {
            element.removeAttribute("required")
        }
    }

    /**
     * The autofocus attribute of the generated HTML textarea element.
     */
    public override var autofocus: Boolean? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.autofocus = it
        } else {
            element.removeAttribute("autofocus")
        }
    }

    /**
     * The readonly attribute of the generated HTML textarea element.
     */
    public open var readonly: Boolean? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.readOnly = it
        } else {
            element.removeAttribute("readonly")
        }
    }

    /**
     * The wrap attribute of the generated HTML textarea element.
     */
    public open var wrap: WrapType? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.wrap = it.value
        } else {
            element.removeAttribute("wrap")
        }
    }

    public override var customValidity: String? by updatingProperty(skipUpdate = skipUpdate) {
        element.setCustomValidity(it ?: "")
    }

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
        if (renderConfig.isDom) {
            if (value != null) {
                element.value = value
            }
            if (cols != null) {
                element.cols = cols
            }
            if (rows != null) {
                element.rows = rows
            }
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
 * @param setup a function for setting up the component
 * @return A [TextArea] component.
 */
@Composable
public fun ComponentBase.textArea(
    value: String? = null,
    cols: Int? = null,
    rows: Int? = null,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    setup: @Composable TextArea.() -> Unit = {}
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
                renderConfig
            )
        }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
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
    }, setup)
    return component
}
