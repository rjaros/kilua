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

package dev.kilua.form.time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.form.DateFormControl
import dev.kilua.form.IInput
import dev.kilua.form.Input
import dev.kilua.form.InputType
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.utils.today
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

internal const val DATE_DEFAULT_STEP = 1

/**
 * Date input component.
 */
public interface IDate : IInput<LocalDate>, DateFormControl {
    /**
     * The minimum value of the date.
     */
    public val min: LocalDate?

    /**
     * Set the minimum value of the date.
     */
    @Composable
    public fun min(min: LocalDate?)

    /**
     * The maximum value of the date.
     */
    public val max: LocalDate?

    /**
     * Set the maximum value of the date.
     */
    @Composable
    public fun max(max: LocalDate?)

    /**
     * The step value of the date.
     */
    public val step: Int

    /**
     * Set the step value of the date.
     */
    @Composable
    public fun step(step: Int)

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
 * Date input component.
 */
public open class Date(
    value: LocalDate? = null,
    min: LocalDate? = null,
    max: LocalDate? = null,
    step: Int = DATE_DEFAULT_STEP,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) : Input<LocalDate>(
    value,
    InputType.Date,
    name,
    maxlength,
    placeholder,
    disabled,
    required,
    className,
    id,
    renderConfig
), DateFormControl, IDate {

    /**
     * The minimum value of the date.
     */
    public override var min: LocalDate? by updatingProperty(min) {
        if (it != null) {
            element.min = it.toString()
        } else {
            element.removeAttribute("min")
        }
    }

    /**
     * Set the minimum value of the date.
     */
    @Composable
    public override fun min(min: LocalDate?): Unit = composableProperty("min", {
        this.min = null
    }) {
        this.min = min
    }

    /**
     * The maximum value of the date.
     */
    public override var max: LocalDate? by updatingProperty(max) {
        if (it != null) {
            element.max = it.toString()
        } else {
            element.removeAttribute("max")
        }
    }

    /**
     * Set the maximum value of the date.
     */
    @Composable
    public override fun max(max: LocalDate?): Unit = composableProperty("max", {
        this.max = null
    }) {
        this.max = max
    }

    /**
     * The step value of the date.
     */
    public override var step: Int by updatingProperty(step) {
        element.step = it.toString()
    }

    /**
     * Set the step value of the date.
     */
    @Composable
    public override fun step(step: Int): Unit = composableProperty("step", {
        this.step = DATE_DEFAULT_STEP
    }) {
        this.step = step
    }

    init {
        if (renderConfig.isDom) {
            if (min != null) {
                @Suppress("LeakingThis")
                element.min = min.toString()
            }
            if (max != null) {
                @Suppress("LeakingThis")
                element.max = max.toString()
            }
            @Suppress("LeakingThis")
            element.step = step.toString()
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::min, ::max, ::step)
    }

    @Suppress("SwallowedException")
    override fun stringToValue(text: String?): LocalDate? {
        return if (text.isNullOrEmpty()) {
            null
        } else {
            val date = try {
                LocalDate.parse(text)
            } catch (e: IllegalArgumentException) {
                null
            }
            date?.let {
                if (min != null && it < (min!!))
                    min
                else if (max != null && it > (max!!))
                    max
                else it
            }
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
            val newValue = (value ?: min ?: today()).plus(step, DateTimeUnit.DAY)
            value = if (max != null && newValue > max!!) max else newValue
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
            val newValue = (value ?: min ?: today()).minus(step, DateTimeUnit.DAY)
            value = if (min != null && newValue < min!!) min else newValue
        }
    }
}

/**
 * Creates a [Date] component, returning a reference.
 *
 * @param value the initial value
 * @param min the minimum value
 * @param max the maximum value
 * @param step the step value
 * @param name the name attribute of the generated HTML input element
 * @param maxlength the maxlength attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param className the CSS class name
 * @param id the ID of the generated HTML input element
 * @param setup a function for setting up the component
 * @return a [Date] component
 *
 */
@Composable
public fun IComponent.dateRef(
    value: LocalDate? = null,
    min: LocalDate? = null,
    max: LocalDate? = null,
    step: Int = DATE_DEFAULT_STEP,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IDate.() -> Unit = {}
): Date {
    val component =
        remember {
            Date(
                value,
                min,
                max,
                step,
                name,
                maxlength,
                placeholder,
                disabled,
                required,
                className,
                id,
                renderConfig
            )
        }
    ComponentNode(component, {
        set(value) { updateProperty(Date::value, it) }
        set(min) { updateProperty(Date::min, it) }
        set(max) { updateProperty(Date::max, it) }
        set(step) { updateProperty(Date::step, it) }
        set(name) { updateProperty(Date::name, it) }
        set(maxlength) { updateProperty(Date::maxlength, it) }
        set(placeholder) { updateProperty(Date::placeholder, it) }
        set(disabled) { updateProperty(Date::disabled, it) }
        set(required) { updateProperty(Date::required, it) }
        set(className) { updateProperty(Date::className, it) }
        set(id) { updateProperty(Date::id, it) }
    }, setup)
    return component
}

/**
 * Creates a [Date] component.
 *
 * @param value the initial value
 * @param min the minimum value
 * @param max the maximum value
 * @param step the step value
 * @param name the name attribute of the generated HTML input element
 * @param maxlength the maxlength attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param className the CSS class name
 * @param id the ID of the generated HTML input element
 * @param setup a function for setting up the component
 */
@Composable
public fun IComponent.date(
    value: LocalDate? = null,
    min: LocalDate? = null,
    max: LocalDate? = null,
    step: Int = DATE_DEFAULT_STEP,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IDate.() -> Unit = {}
) {
    val component =
        remember {
            Date(
                value,
                min,
                max,
                step,
                name,
                maxlength,
                placeholder,
                disabled,
                required,
                className,
                id,
                renderConfig
            )
        }
    ComponentNode(component, {
        set(value) { updateProperty(Date::value, it) }
        set(min) { updateProperty(Date::min, it) }
        set(max) { updateProperty(Date::max, it) }
        set(step) { updateProperty(Date::step, it) }
        set(name) { updateProperty(Date::name, it) }
        set(maxlength) { updateProperty(Date::maxlength, it) }
        set(placeholder) { updateProperty(Date::placeholder, it) }
        set(disabled) { updateProperty(Date::disabled, it) }
        set(required) { updateProperty(Date::required, it) }
        set(className) { updateProperty(Date::className, it) }
        set(id) { updateProperty(Date::id, it) }
    }, setup)
}
