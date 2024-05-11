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

package dev.kilua.form.number

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.form.IInput
import dev.kilua.form.Input
import dev.kilua.form.InputType
import dev.kilua.form.NumberFormControl
import dev.kilua.html.helpers.PropertyListBuilder

internal const val RANGE_DEFAULT_MIN = 0
internal const val RANGE_DEFAULT_MAX = 100
internal const val RANGE_DEFAULT_STEP = 1

/**
 * Range input component.
 */
public interface IRange : IInput<Number>, NumberFormControl {
    /**
     * The minimum value.
     */
    public val min: Number?

    /**
     * Set the minimum value.
     */
    @Composable
    public fun min(min: Number)

    /**
     * The maximum value.
     */
    public val max: Number?

    /**
     * Set the maximum value.
     */
    @Composable
    public fun max(max: Number)

    /**
     * The step value.
     */
    public val step: Number

    /**
     * Set the step value.
     */
    @Composable
    public fun step(step: Number)

    /**
     * Increments the value by the step value.
     */
    public fun stepUp()

    /**
     * Decrements the value by the step value.
     */
    public fun stepDown()
}


/**
 * Range input component.
 */
public open class Range(
    value: Number? = null,
    min: Number = RANGE_DEFAULT_MIN,
    max: Number = RANGE_DEFAULT_MAX,
    step: Number = RANGE_DEFAULT_STEP,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Input<Number>(
    value,
    InputType.Range,
    name,
    null,
    null,
    disabled,
    required,
    className,
    id,
    renderConfig = renderConfig
),
    NumberFormControl, IRange {

    /**
     * The minimum value of the range.
     */
    public override var min: Number by updatingProperty(min) {
        element.min = it.toString()
    }

    /**
     * Set the minimum value.
     */
    @Composable
    public override fun min(min: Number): Unit = composableProperty("min", {
        this.min = RANGE_DEFAULT_MIN
    }) {
        this.min = min
    }

    /**
     * The maximum value of the range.
     */
    public override var max: Number by updatingProperty(max) {
        element.max = it.toString()
    }

    /**
     * Set the maximum value.
     */
    @Composable
    public override fun max(max: Number): Unit = composableProperty("max", {
        this.max = RANGE_DEFAULT_MAX
    }) {
        this.max = max
    }

    /**
     * The step value of the range.
     */
    public override var step: Number by updatingProperty(step) {
        element.step = it.toString()
    }

    /**
     * Set the step value.
     */
    @Composable
    public override fun step(step: Number): Unit = composableProperty("step", {
        this.step = RANGE_DEFAULT_STEP
    }) {
        this.step = step
    }

    init {
        if (renderConfig.isDom) {
            @Suppress("LeakingThis")
            element.min = min.toString()
            @Suppress("LeakingThis")
            element.max = max.toString()
            @Suppress("LeakingThis")
            element.step = step.toString()
            if (value != null) {
                @Suppress("LeakingThis")
                element.value = value.toString()
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::min, ::max, ::step)
    }

    override fun stringToValue(text: String?): Number? {
        return if (text.isNullOrEmpty()) {
            null
        } else {
            text.toDoubleOrNull()
        }
    }

    /**
     * Increments the value by the step value.
     */
    public override fun stepUp() {
        if (renderConfig.isDom) {
            element.stepUp()
            setInternalValueFromString(element.value)
        } else {
            val newValue = (value?.toDouble() ?: min.toDouble()) + step.toDouble()
            value = if (newValue > max.toDouble()) max else newValue
        }
    }

    /**
     * Decrements the value by the step value.
     */
    public override fun stepDown() {
        if (renderConfig.isDom) {
            element.stepDown()
            setInternalValueFromString(element.value)
        } else {
            val newValue = (value?.toDouble() ?: max.toDouble()) - step.toDouble()
            value = if (newValue < min.toDouble()) min else newValue
        }
    }

}

/**
 * Creates a [Range] component.
 * @param value the initial value
 * @param min the minimum value of the range
 * @param max the maximum value of the range
 * @param step the step value of the range
 * @param name the name attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param className the CSS class name
 * @param id the ID of the component
 * @param setup a function for setting up the component
 * @return a [Range] component
 */
@Composable
public fun IComponent.range(
    value: Number? = null,
    min: Number = RANGE_DEFAULT_MIN,
    max: Number = RANGE_DEFAULT_MAX,
    step: Number = RANGE_DEFAULT_STEP,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRange.() -> Unit = {}
): Range {
    val component =
        remember { Range(value, min, max, step, name, disabled, required, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(value) { updateProperty(Range::value, it) }
        set(min) { updateProperty(Range::min, it) }
        set(max) { updateProperty(Range::max, it) }
        set(step) { updateProperty(Range::step, it) }
        set(name) { updateProperty(Range::name, it) }
        set(disabled) { updateProperty(Range::disabled, it) }
        set(required) { updateProperty(Range::required, it) }
        set(className) { updateProperty(Range::className, it) }
        set(id) { updateProperty(Range::id, it) }
    }, setup)
    return component
}
