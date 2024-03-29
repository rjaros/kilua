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
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
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
) : Input<Number>(value, InputType.Range, name, null, null, disabled, required, className, id, renderConfig = renderConfig),
    NumberFormControl {

    /**
     * The minimum value of the range.
     */
    public open var min: Number by updatingProperty(min) {
        element.min = it.toString()
    }

    /**
     * The maximum value of the range.
     */
    public open var max: Number by updatingProperty(max) {
        element.max = it.toString()
    }

    /**
     * The step value of the range.
     */
    public open var step: Number by updatingProperty(step) {
        element.step = it.toString()
    }

    init {
        if (renderConfig.isDom) {
            element.min = min.toString()
            element.max = max.toString()
            element.step = step.toString()
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
    public open fun stepUp() {
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
    public open fun stepDown() {
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
public fun ComponentBase.range(
    value: Number? = null,
    min: Number = RANGE_DEFAULT_MIN,
    max: Number = RANGE_DEFAULT_MAX,
    step: Number = RANGE_DEFAULT_STEP,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable Range.() -> Unit = {}
): Range {
    val component = remember { Range(value, min, max, step, name, disabled, required, className, id, renderConfig = renderConfig) }
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
