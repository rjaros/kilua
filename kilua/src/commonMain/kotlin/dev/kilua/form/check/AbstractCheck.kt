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
import dev.kilua.state.ObservableDelegate
import dev.kilua.utils.toKebabCase
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event

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
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Tag<HTMLInputElement>("input", className, renderConfig), BoolFormControl {

    protected val observableDelegate: ObservableDelegate<Boolean> = ObservableDelegate()

    public override var value: Boolean by updatingProperty(
        value,
        skipUpdate,
        notifyFunction = { observableDelegate.notifyObservers(it) }) {
        element.checked = it
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
     * The autofocus attribute of the generated HTML input element.
     */
    public override var autofocus: Boolean? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.autofocus = it
        } else {
            element.removeAttribute("autofocus")
        }
    }

    /**
     * The checked attribute of the generated HTML input element.
     */
    public open var defaultChecked: Boolean? by updatingProperty(skipUpdate = skipUpdate, "checked") {
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

    init {
        @Suppress("LeakingThis")
        elementNullable?.let {
            it.checked = value
            it.type = type.value
            if (name != null) {
                it.name = name
            }
            if (disabled != null) {
                it.disabled = disabled
            }
        }
        @Suppress("LeakingThis")
        setAttribute("type", type.value)
        @Suppress("LeakingThis")
        onEventDirect<Event>("click") {
            setInternalValueFromBoolean(element.checked)
        }
        @Suppress("LeakingThis")
        onEventDirect<Event>("change") {
            setInternalValueFromBoolean(element.checked)
        }
    }

    /**
     * Set value from string without setting element value again.
     */
    protected open fun setInternalValueFromBoolean(boolean: Boolean) {
        if (value != boolean) {
            propertyValues["value"] = boolean
            observableDelegate.notifyObservers(boolean)
        }
    }

    override fun getValueAsString(): String? {
        return value.toString()
    }

    override fun subscribe(observer: (Boolean) -> Unit): () -> Unit {
        return observableDelegate.subscribe(value, observer)
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::name,
            ::disabled,
            ::autofocus,
        )
        propertyListBuilder.addByName("checked")
    }
}
