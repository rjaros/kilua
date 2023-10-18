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
import dev.kilua.state.ObservableDelegate
import dev.kilua.utils.console
import dev.kilua.utils.toKebabCase
import org.w3c.dom.HTMLTextAreaElement

/**
 * Button types.
 */
public enum class WrapType {
    Soft,
    Hard;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

public open class TextArea(
    value: String? = null,
    cols: Int? = null,
    rows: Int? = null,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Tag<HTMLTextAreaElement>("textarea", className, renderConfig), StringFormControl {

    protected val observableDelegate: ObservableDelegate<String?> = ObservableDelegate()

    public override var value: String? by updatingProperty(
        value,
        skipUpdate,
        notifyFunction = { observableDelegate.notifyObservers(it) }) {
        element.value = it ?: ""
    }

    public var cols: Int? by updatingProperty(cols, skipUpdate) {
        if (it != null) {
            element.cols = it
        } else {
            element.removeAttribute("cols")
        }
    }

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

    public var maxlength: Int? by updatingProperty(maxlength, skipUpdate) {
        if (it != null) {
            element.maxLength = it
        } else {
            element.removeAttribute("maxlength")
        }
    }

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

    public open var autofocus: Boolean? by updatingProperty(skipUpdate = skipUpdate) {
        console.log("set autofocus $it")
        if (it != null) {
            element.autofocus = it
        } else {
            element.removeAttribute("autofocus")
        }
    }

    public open var readonly: Boolean? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.readOnly = it
        } else {
            element.removeAttribute("readonly")
        }
    }

    public open var wrap: WrapType? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.wrap = it.value
        } else {
            element.removeAttribute("wrap")
        }
    }

    init {
        @Suppress("LeakingThis")
        elementNullable?.let {
            if (value != null) {
                it.value = value.toString()
            }
            if (cols != null) {
                it.cols = cols
            }
            if (rows != null) {
                it.rows = rows
            }
            if (name != null) {
                it.name = name
            }
            if (maxlength != null) {
                it.maxLength = maxlength
            }
            if (placeholder != null) {
                it.placeholder = placeholder
            }
            if (disabled != null) {
                it.disabled = disabled
            }
        }
        @Suppress("LeakingThis")
        onInputDirect {
            setValueFromString(element.value)
        }
    }

    override fun subscribe(observer: (String?) -> Unit): () -> Unit {
        return observableDelegate.subscribe(value, observer)
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
            ::autofocus,
            ::readonly,
            ::wrap
        )
    }

    override fun setValueFromString(text: String?) {
        if (text.isNullOrEmpty()) {
            this.value = null
        } else {
            this.value = text
        }
    }
}

@Composable
public fun ComponentBase.textArea(
    value: String? = null,
    cols: Int? = null,
    rows: Int? = null,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    content: @Composable TextArea.() -> Unit = {}
): TextArea {
    val component =
        remember { TextArea(value, cols, rows, name, maxlength, placeholder, disabled, className, renderConfig) }
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
        set(className) { updateProperty(TextArea::className, it) }
    }, content)
    return component
}
