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
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.form.StringFormControl
import dev.kilua.form.fieldWithLabel
import dev.kilua.html.Div
import dev.kilua.html.IDiv
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.StringPair

/**
 * Radio button component.
 */
public interface IRadioGroup : IDiv, StringFormControl, WithStateFlow<String?> {
    /**
     * Render radio buttons inline.
     */
    public val inline: Boolean

    /**
     * Set to render radio buttons inline.
     */
    @Composable
    public fun inline(inline: Boolean)

    /**
     * The name attribute of the generated HTML radio input elements.
     */
    public val name: String?

    /**
     * Set the name attribute of the generated HTML radio input elements.
     */
    @Composable
    public fun name(name: String?)

    /**
     * The disabled attribute of the generated HTML radio input elements.
     */
    public val disabled: Boolean?

    /**
     * Set the disabled attribute of the generated HTML radio input elements.
     */
    @Composable
    public fun disabled(disabled: Boolean?)

    /**
     * Set the required attribute of the generated HTML radio input elements.
     */
    @Composable
    public fun required(required: Boolean?)
}

/**
 * Radio button group component.
 */
public open class RadioGroup(
    value: String? = null,
    inline: Boolean = false,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val withStateFlowDelegate: WithStateFlowDelegate<String?> = WithStateFlowDelegateImpl()
) : Div(className, id, renderConfig), StringFormControl, WithStateFlow<String?> by withStateFlowDelegate, IRadioGroup {

    public override var value: String? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        findAllRadios().forEach { radio ->
            radio.value = radio.extraValue == it
        }
    }

    /**
     * Render radio buttons inline.
     */
    public override var inline: Boolean by updatingProperty(inline) {
        children.forEach { child ->
            if (child is Div) {
                child.className = if (it) "kilua-radio-inline" else null
            }
        }
    }

    /**
     * Set to render radio buttons inline.
     */
    @Composable
    public override fun inline(inline: Boolean): Unit = composableProperty("inline", {
        this.inline = false
    }) {
        this.inline = inline
    }

    /**
     * The name attribute of the generated HTML radio input elements.
     */
    public override var name: String? by updatingProperty(name) {
        findAllRadios().forEach { radio ->
            radio.name = it ?: "name_${componentId}"
        }
    }

    /**
     * Set the name attribute of the generated HTML radio input elements.
     */
    @Composable
    public override fun name(name: String?): Unit = composableProperty("name", {
        this.name = null
    }) {
        this.name = name
    }

    /**
     * The disabled attribute of the generated HTML radio input elements.
     */
    public override var disabled: Boolean? by updatingProperty(disabled) {
        findAllRadios().forEach { radio ->
            radio.disabled = it
        }
    }

    /**
     * Set the disabled attribute of the generated HTML radio input elements.
     */
    @Composable
    public override fun disabled(disabled: Boolean?): Unit = composableProperty("disabled", {
        this.disabled = null
    }) {
        this.disabled = disabled
    }

    /**
     * The required attribute of the generated HTML radio input elements.
     */
    public override var required: Boolean? by updatingProperty(required) {
        findAllRadios().forEach { radio ->
            radio.required = it
        }
    }

    /**
     * Set the required attribute of the generated HTML radio input elements.
     */
    @Composable
    public override fun required(required: Boolean?): Unit = composableProperty("required", {
        this.required = null
    }) {
        this.required = required
    }

    /**
     * The autofocus attribute of the generated HTML radio input elements.
     */
    public override var autofocus: Boolean? by updatingProperty {
        findAllRadios().firstOrNull()?.let { radio ->
            radio.autofocus = it
        }
    }

    public override var customValidity: String? by updatingProperty {
        findAllRadios().forEach { radio ->
            radio.customValidity = it
        }
    }

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
    }

    /**
     * Find all radio button components in this group.
     */
    protected open fun findAllRadios(): List<Radio> {
        return children.flatMap { child ->
            if (child is Div) {
                child.children.mapNotNull { subChild ->
                    if (subChild is Radio) {
                        subChild
                    } else null
                }
            } else emptyList()
        }
    }

    override fun getValueAsString(): String? {
        return value.toString()
    }
}

/**
 * Creates [RadioGroup] component.
 *
 * @param options a list of options (value to label pairs)
 * @param value initial value
 * @param inline determines if the options are rendered inline
 * @param name the name of the input
 * @param disabled whether the input is disabled
 * @param required whether the input is required
 * @param className the CSS class name
 * @param id the ID of the input
 * @param setup a function for setting up the component
 * @return a [RadioGroup] component
 */
@Composable
public fun IComponent.radioGroup(
    options: List<StringPair>? = null,
    value: String? = null,
    inline: Boolean = false,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRadioGroup.() -> Unit = {}
): RadioGroup {
    val component = remember { RadioGroup(value, inline, name, disabled, required, className, id, renderConfig) }
    ComponentNode(component, {
        set(value) { updateProperty(RadioGroup::value, it) }
        set(inline) { updateProperty(RadioGroup::inline, it) }
        set(name) { updateProperty(RadioGroup::name, it) }
        set(disabled) { updateProperty(RadioGroup::disabled, it) }
        set(required) { updateProperty(RadioGroup::required, it) }
        set(className) { updateProperty(RadioGroup::className, it) }
        set(id) { updateProperty(RadioGroup::id, it) }
    }) {
        setup(component)
        options?.forEachIndexed { index, option ->
            fieldWithLabel(
                option.second,
                labelAfter = true,
                groupClassName = if (component.inline) "kilua-radio-inline" else null
            ) {
                radio(
                    value = option.first == component.value,
                    name = component.name ?: "name_${component.componentId}",
                    disabled = component.disabled,
                    required = component.required,
                    id = it
                ) {
                    if (index == 0 && component.autofocus == true) {
                        this.autofocus(true)
                    }
                    this.extraValue(option.first)
                    onChange {
                        component.value = this.extraValue
                    }
                }
            }
        }
    }
    return component
}
