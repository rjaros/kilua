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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.form.StringFormControl
import dev.kilua.html.ITag
import dev.kilua.html.Optgroup
import dev.kilua.html.Option
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.html.option
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.StringPair
import dev.kilua.utils.nativeListOf
import dev.kilua.dom.api.HTMLSelectElement

/**
 * The special value for an empty option.
 */
public const val SELECT_EMPTY_VALUE: String = "kilua_select_empty_value"

/**
 * Select component.
 */
public interface ISelect : ITag<HTMLSelectElement>, StringFormControl, WithStateFlow<String?> {
    /**
     * Determines if multiple value selection is allowed.
     */
    public val multiple: Boolean

    /**
     * Set the multiple value selection.
     */
    @Composable
    public fun multiple(multiple: Boolean)

    /**
     * The number of visible options.
     */
    public val size: Int?

    /**
     * Set the number of visible options.
     */
    @Composable
    public fun size(size: Int?)

    /**
     * The name attribute of the generated HTML input element.
     */
    public val name: String?

    /**
     * Set the name attribute of the generated HTML select element.
     */
    @Composable
    public fun name(name: String?)

    /**
     * The disabled attribute of the generated HTML select element.
     */
    public val disabled: Boolean?

    /**
     * Set the disabled attribute of the generated HTML select element.
     */
    @Composable
    public fun disabled(disabled: Boolean?)

    /**
     * Set the required attribute of the generated HTML select element.
     */
    @Composable
    public fun required(required: Boolean?)

    /**
     * The index of the currently selected option.
     */
    public var selectedIndex: Int

    /**
     * The label of the currently selected option.
     */
    public val selectedLabel: String?

}

/**
 * Select component.
 */
public open class Select(
    value: String? = null,
    multiple: Boolean = false,
    size: Int? = null,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default,
    protected val withStateFlowDelegate: WithStateFlowDelegate<String?> = WithStateFlowDelegateImpl()
) : Tag<HTMLSelectElement>("select", className, id, renderConfig = renderConfig), StringFormControl,
    WithStateFlow<String?> by withStateFlowDelegate, ISelect {

    public override var value: String? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        mapValueToOptions()
    }

    /**
     * Determines if multiple value selection is allowed.
     */
    public override var multiple: Boolean by updatingProperty(multiple) {
        element.multiple = it
    }

    /**
     * Set the multiple value selection.
     */
    @Composable
    public override fun multiple(multiple: Boolean): Unit = composableProperty("multiple", {
        this.multiple = false
    }) {
        this.multiple = multiple
    }

    /**
     * The number of visible options.
     */
    public override var size: Int? by updatingProperty(size) {
        if (it != null) {
            element.size = it
        } else {
            element.removeAttribute("size")
        }
    }

    /**
     * Set the number of visible options.
     */
    @Composable
    public override fun size(size: Int?): Unit = composableProperty("size", {
        this.size = null
    }) {
        this.size = size
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
     * Set the name attribute of the generated HTML select element.
     */
    @Composable
    public override fun name(name: String?): Unit = composableProperty("name", {
        this.name = null
    }) {
        this.name = name
    }

    /**
     * The disabled attribute of the generated HTML select element.
     */
    public override var disabled: Boolean? by updatingProperty(disabled) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
    }

    /**
     * Set the disabled attribute of the generated HTML select element.
     */
    @Composable
    public override fun disabled(disabled: Boolean?): Unit = composableProperty("disabled", {
        this.disabled = null
    }) {
        this.disabled = disabled
    }

    /**
     * The required attribute of the generated HTML select element.
     */
    public override var required: Boolean? by updatingProperty(required) {
        if (it != null) {
            element.required = it
        } else {
            element.removeAttribute("required")
        }
    }

    /**
     * Set the required attribute of the generated HTML select element.
     */
    @Composable
    public override fun required(required: Boolean?): Unit = composableProperty("required", {
        this.required = null
    }) {
        this.required = required
    }

    public override var customValidity: String? by updatingProperty {
        element.setCustomValidity(it ?: "")
    }

    /**
     * The index of the currently selected option.
     */
    public override var selectedIndex: Int
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
    public override val selectedLabel: String?
        get() = findAllNotEmptyOptions().getOrNull(selectedIndex)?.label

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
        if (renderConfig.isDom) {
            @Suppress("LeakingThis")
            element.multiple = multiple
            if (size != null) {
                @Suppress("LeakingThis")
                element.size = size
            }
            if (name != null) {
                @Suppress("LeakingThis")
                element.name = name
            }
            if (disabled != null) {
                @Suppress("LeakingThis")
                element.disabled = disabled
            }
            if (required != null) {
                @Suppress("LeakingThis")
                element.required = required
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
                        subChild as? Option
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

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::multiple,
            ::size,
            ::name,
            ::disabled,
            ::required,
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
        val elementNullable = if (renderConfig.isDom) element else null
        val newValue = if (multiple) {
            // selected values are joined to a comma separated string
            findAllOptions().filter {
                if (renderConfig.isDom) {
                    it.element.selected
                } else false
            }.filter {
                it.value != SELECT_EMPTY_VALUE
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
 * Creates [Select] component, returning a reference.
 *
 * @param options a list of options (value to label pairs)
 * @param value initial value
 * @param multiple determines if multiple value selection is allowed
 * @param size the number of visible options
 * @param name the name of the select
 * @param placeholder the placeholder for the select component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 * @return a [Select] component
 */
@Composable
public fun IComponent.selectRef(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    size: Int? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ISelect.() -> Unit = {}
): Select {
    val component = remember { Select(value, multiple, size, name, disabled, required, className, id, renderConfig) }
    LaunchedEffect(component.componentId) {
        component.onInsert()
    }
    ComponentNode(component, {
        set(value) { updateProperty(Select::value, it) }
        set(multiple) { updateProperty(Select::multiple, it) }
        set(size) { updateProperty(Select::size, it) }
        set(name) { updateProperty(Select::name, it) }
        set(disabled) { updateProperty(Select::disabled, it) }
        set(required) { updateProperty(Select::required, it) }
        set(className) { updateProperty(Select::className, it) }
        set(id) { updateProperty(Select::id, it) }
    }) {
        setup(component)
        setupSelect(placeholder, emptyOption, options, component)
    }
    return component
}

/**
 * Creates [Select] component.
 *
 * @param options a list of options (value to label pairs)
 * @param value initial value
 * @param multiple determines if multiple value selection is allowed
 * @param size the number of visible options
 * @param name the name of the select
 * @param placeholder the placeholder for the select component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 */
@Composable
public fun IComponent.select(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    size: Int? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ISelect.() -> Unit = {}
) {
    val component = remember { Select(value, multiple, size, name, disabled, required, className, id, renderConfig) }
    LaunchedEffect(component.componentId) {
        component.onInsert()
    }
    ComponentNode(component, {
        set(value) { updateProperty(Select::value, it) }
        set(multiple) { updateProperty(Select::multiple, it) }
        set(size) { updateProperty(Select::size, it) }
        set(name) { updateProperty(Select::name, it) }
        set(disabled) { updateProperty(Select::disabled, it) }
        set(required) { updateProperty(Select::required, it) }
        set(className) { updateProperty(Select::className, it) }
        set(id) { updateProperty(Select::id, it) }
    }) {
        setup(component)
        setupSelect(placeholder, emptyOption, options, component)
    }
}

@Composable
private fun Select.setupSelect(
    placeholder: String?,
    emptyOption: Boolean,
    options: List<StringPair>?,
    component: Select
) {
    if (placeholder != null) {
        this.required = true
        option(value = "", label = placeholder, disabled = true, selected = true) {
            hidden(true)
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
            currentlySelected(component.isValueSelected(option.first))
        }
    }
}
