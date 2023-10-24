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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.form.InputType
import dev.kilua.form.TriStateFormControl
import dev.kilua.html.Tag
import dev.kilua.html.div
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.html.label
import dev.kilua.html.unaryPlus
import dev.kilua.state.ObservableDelegate
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event

/**
 * Tri-state CheckBox input component.
 */
public open class TriStateCheckBox(
    value: Boolean? = null,
    name: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Tag<HTMLInputElement>("input", className, renderConfig), TriStateFormControl {

    protected val observableDelegate: ObservableDelegate<Boolean?> = ObservableDelegate()

    public override var value: Boolean? by updatingProperty(
        value,
        skipUpdate,
        notifyFunction = { observableDelegate.notifyObservers(it) }) {
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
            if (value != null) {
                it.checked = value
            } else {
                it.indeterminate = true
            }
            it.type = InputType.Checkbox.value
            if (name != null) {
                it.name = name
            }
            if (disabled != null) {
                it.disabled = disabled
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

    override fun subscribe(observer: (Boolean?) -> Unit): () -> Unit {
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

/**
 * Creates [TriStateCheckBox] component.
 *
 * @param value initial value
 * @param name the name of the input
 * @param disabled whether the input is disabled
 * @param className the CSS class name
 * @param setup a function for setting up the component
 * @return a [TriStateCheckBox] component
 */
@Composable
public fun ComponentBase.triStateCheckBox(
    value: Boolean? = null,
    name: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    setup: @Composable TriStateCheckBox.() -> Unit = {}
): TriStateCheckBox {
    val component = remember { TriStateCheckBox(value, name, disabled, className, renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(value) { updateProperty(TriStateCheckBox::value, it) }
        set(name) { updateProperty(TriStateCheckBox::name, it) }
        set(disabled) { updateProperty(TriStateCheckBox::disabled, it) }
        set(className) { updateProperty(TriStateCheckBox::className, it) }
    }, setup)
    return component
}

/**
 * Creates [TriStateCheckBox] component with a label.
 *
 * @param label the label of the input
 * @param value initial value
 * @param name the name of the input
 * @param disabled whether the input is disabled
 * @param className the CSS class name
 * @param groupClassName the CSS class name of the grouping div
 * @param setup a function for setting up the component
 * @return a [TriStateCheckBox] component
 */
@Composable
public fun ComponentBase.triStateCheckBox(
    label: String,
    value: Boolean = false,
    name: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    groupClassName: String? = null,
    setup: @Composable TriStateCheckBox.() -> Unit = {}
): TriStateCheckBox {
    lateinit var triStateCheckBox: TriStateCheckBox
    div(groupClassName) {
        triStateCheckBox = triStateCheckBox(value, name, disabled, className) {
            id = "id_${componentId}"
            setup()
        }
        label(triStateCheckBox.id) { +label }
    }
    return triStateCheckBox
}
