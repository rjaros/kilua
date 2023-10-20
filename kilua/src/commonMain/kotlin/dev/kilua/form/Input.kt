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
import dev.kilua.state.ObservableDelegate
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event

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
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Tag<HTMLInputElement>("input", className, renderConfig), GenericFormControl<T> {

    protected val observableDelegate: ObservableDelegate<T?> = ObservableDelegate()

    public override var value: T? by updatingProperty(
        value,
        skipUpdate,
        notifyFunction = { observableDelegate.notifyObservers(it) }) {
        if (type != InputType.File) {
            element.value = it?.toString() ?: ""
        }
    }

    /**
     * The type attribute of the generated HTML input element.
     */
    public open var type: InputType by updatingProperty(type, skipUpdate) {
        element.type = it.value
    }

    /**
     * The name attribute of the generated HTML input element.
     */
    public override var name: String? by updatingProperty(name, skipUpdate) {
        if (it != null) {
            element.name = it
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * The maxlength attribute of the generated HTML input element.
     */
    public var maxlength: Int? by updatingProperty(maxlength, skipUpdate) {
        if (it != null) {
            element.maxLength = it
        } else {
            element.removeAttribute("maxlength")
        }
    }

    /**
     * The placeholder attribute of the generated HTML input element.
     */
    public var placeholder: String? by updatingProperty(placeholder, skipUpdate) {
        if (it != null) {
            element.placeholder = it
        } else {
            element.removeAttribute("placeholder")
        }
    }

    /**
     * The disabled attribute of the generated HTML input element.
     */
    public override var disabled: Boolean? by updatingProperty(disabled, skipUpdate) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
    }

    /**
     * The autocomplete attribute of the generated HTML input element.
     */
    public open var autocomplete: Autocomplete? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.autocomplete = it.value
        } else {
            element.removeAttribute("autocomplete")
        }
    }

    /**
     * The autofocus attribute of the generated HTML input element.
     */
    public open var autofocus: Boolean? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.autofocus = it
        } else {
            element.removeAttribute("autofocus")
        }
    }

    /**
     * The readonly attribute of the generated HTML input element.
     */
    public open var readonly: Boolean? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.readOnly = it
        } else {
            element.removeAttribute("readonly")
        }
    }

    /**
     * The list attribute of the generated HTML input element.
     */
    public open var list: String? by updatingProperty(skipUpdate = skipUpdate) {
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
            setAttribute("value", defaultValue?.toString())
        }

    init {
        @Suppress("LeakingThis")
        elementNullable?.let {
            if (value != null) {
                it.value = value.toString()
            }
            it.type = type.value
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
        onEventDirect<Event>("input") {
            setValueFromString(element.value)
        }
    }

    override fun subscribe(observer: (T?) -> Unit): () -> Unit {
        return observableDelegate.subscribe(value, observer)
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::type,
            ::name,
            ::maxlength,
            ::placeholder,
            ::disabled,
            ::autocomplete,
            ::autofocus,
            ::readonly
        )
    }

}
