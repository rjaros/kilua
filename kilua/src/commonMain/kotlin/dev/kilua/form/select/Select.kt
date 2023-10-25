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

package dev.kilua.form.select

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.form.StringFormControl
import dev.kilua.html.Optgroup
import dev.kilua.html.Option
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.html.option
import dev.kilua.state.ObservableDelegate
import dev.kilua.utils.StringPair
import dev.kilua.utils.nativeListOf
import org.w3c.dom.HTMLSelectElement

/**
 * The special value for an empty option.
 */
public const val SELECT_EMPTY_VALUE: String = "kilua_select_empty_value"

/**
 * Select component.
 */
public open class Select(
    value: String? = null,
    multiple: Boolean = false,
    size: Int? = null,
    name: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Tag<HTMLSelectElement>("select", className, renderConfig), StringFormControl {

    protected val observableDelegate: ObservableDelegate<String?> = ObservableDelegate()

    public override var value: String? by updatingProperty(
        value,
        notifyFunction = { observableDelegate.notifyObservers(it) }) {
        mapValueToOptions()
    }

    /**
     * Determines if multiple value selection is allowed.
     */
    public var multiple: Boolean by updatingProperty(multiple, skipUpdate) {
        element.multiple = it
    }

    /**
     * The number of visible options.
     */
    public var size: Int? by updatingProperty(size, skipUpdate) {
        if (it != null) {
            element.size = it
        } else {
            element.removeAttribute("size")
        }
    }

    public override var name: String? by updatingProperty(name, skipUpdate) {
        if (it != null) {
            element.name = it
        } else {
            element.removeAttribute("name")
        }
    }

    public override var disabled: Boolean? by updatingProperty(disabled, skipUpdate) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
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
     * The index of the currently selected option.
     */
    public open var selectedIndex: Int
        get() = value?.let {
            val values = if (multiple) it.split(",") else nativeListOf(it)
            findAllNotEmptyOptions().indexOfFirst { values.contains(it.value ?: it.label) }
        } ?: -1
        set(value) {
            findAllNotEmptyOptions().getOrNull(value)?.let {
                it.currentlySelected = true
                this.value = it.value ?: it.label
                mapValueToOptions()
            } ?: run {
                this.value = null
                mapValueToOptions()
            }
        }

    /**
     * The label of the currently selected option.
     */
    public open val selectedLabel: String?
        get() = findAllNotEmptyOptions().getOrNull(selectedIndex)?.label

    init {
        @Suppress("LeakingThis")
        elementNullable?.let {
            it.multiple = multiple
            if (size != null) {
                it.size = size
            }
            if (name != null) {
                it.name = name
            }
            if (disabled != null) {
                it.disabled = disabled
            }
        }
        @Suppress("LeakingThis")
        onChangeDirect {
            mapOptionsToValue()
        }
    }

    /**
     * Finds all Option components inside this Select.
     */
    protected open fun findAllOptions(): List<Option> {
        return children.flatMap { child ->
            when (child) {
                is Optgroup -> {
                    child.children.mapNotNull { subChild ->
                        if (subChild is Option) {
                            subChild
                        } else null
                    }
                }

                is Option -> {
                    listOf(child)
                }

                else -> emptyList()
            }
        }.filter { it.hidden != true }
    }

    /**
     * Finds all not empty Option components inside this Select.
     */
    protected open fun findAllNotEmptyOptions(): List<Option> {
        return findAllOptions().filter { it.value != SELECT_EMPTY_VALUE }
    }

    override fun subscribe(observer: (String?) -> Unit): () -> Unit {
        return observableDelegate.subscribe(value, observer)
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::multiple,
            ::size,
            ::name,
            ::disabled,
            ::autofocus,
        )
    }

    override fun getValueAsString(): String? {
        return value
    }

    /**
     * Map current value to the states of the option components inside this Select.
     */
    protected open fun mapValueToOptions() {
        val values = if (multiple) value?.split(",") else value?.let { listOf(it) }
        findAllOptions().forEach { option ->
            // set selected state for all options which value is contained in the current value
            // selects empty option if available and value is null
            option.currentlySelected =
                values?.contains(option.value ?: option.label) ?: (option.value == SELECT_EMPTY_VALUE)
        }
    }

    /**
     * Map the states of the option components inside this Select to the current value.
     */
    protected open fun mapOptionsToValue() {
        val newValue = if (multiple) {
            // selected values are joined to a comma separated string
            findAllOptions().filter {
                it.elementNullable?.selected == true
            }.map {
                it.value ?: it.label
            }.joinToString(",")
        } else if (value == null && elementNullable == null) {
            // first option value is automatically mapped if nothing is selected and element is null
            findAllOptions().firstOrNull()?.let { option ->
                (if (option.value == SELECT_EMPTY_VALUE) null else option.value) ?: option.label
            }
        } else if (elementNullable?.value != SELECT_EMPTY_VALUE) {
            // a selected value if not an empty option
            elementNullable?.value
        } else {
            // an empty option
            null
        }
        value = newValue?.ifEmpty { null }
    }

    override fun onInsert() {
        super.onInsert()
        if (value == null)
            mapOptionsToValue()
        else
            mapValueToOptions()
    }

    /**
     * Determines if the given value is currently selected.
     */
    internal fun isValueSelected(v: String): Boolean {
        return if (multiple) {
            value?.split(",")?.contains(v) ?: false
        } else {
            value == v
        }
    }
}

/**
 * Creates [Select] component.
 *
 * @param options a list of options (value to label pairs)
 * @param value initial value
 * @param multiple determines if multiple value selection is allowed
 * @param size the number of visible options
 * @param name the name of the input
 * @param placeholder the placeholder for the select component
 * @param disabled whether the input is disabled
 * @param className the CSS class name
 * @param setup a function for setting up the component
 * @return a [Select] component
 */
@Composable
public fun ComponentBase.select(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    size: Int? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    setup: @Composable Select.() -> Unit = {}
): Select {
    val component = remember { Select(value, multiple, size, name, disabled, className, renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(value) { updateProperty(Select::value, it) }
        set(multiple) { updateProperty(Select::multiple, it) }
        set(size) { updateProperty(Select::size, it) }
        set(name) { updateProperty(Select::name, it) }
        set(disabled) { updateProperty(Select::disabled, it) }
        set(className) { updateProperty(Select::className, it) }
    }) {
        setup(component)
        if (placeholder != null) {
            setAttribute("required", "")
            option(value = "", label = placeholder, disabled = true, selected = true) {
                hidden = true
            }
        }
        if (emptyOption) {
            option(value = SELECT_EMPTY_VALUE, label = "")
        }
        options?.forEach { option ->
            option(
                value = option.first,
                label = option.second,
            ) {
                currentlySelected = component.isValueSelected(option.first)
            }
        }
    }
    return component
}
