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
import dev.kilua.form.IInput
import dev.kilua.form.Input
import dev.kilua.form.InputType
import dev.kilua.form.TimeFormControl
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.utils.hour
import dev.kilua.utils.today
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

internal const val TIME_DEFAULT_STEP = 60

/**
 * Time input component.
 */
public interface ITime : IInput<LocalTime>, TimeFormControl {
    /**
     * The minimum value of the time.
     */
    public val min: LocalTime?

    /**
     * Set the minimum value of the time.
     */
    @Composable
    public fun min(min: LocalTime?)

    /**
     * The maximum value of the time.
     */
    public val max: LocalTime?

    /**
     * Set the maximum value of the time.
     */
    @Composable
    public fun max(max: LocalTime?)

    /**
     * The step value of the time.
     */
    public val step: Int

    /**
     * Set the step value of the time.
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
 * Time input component.
 */
public open class Time(
    value: LocalTime? = null,
    min: LocalTime? = null,
    max: LocalTime? = null,
    step: Int = TIME_DEFAULT_STEP,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) : Input<LocalTime>(
    value,
    InputType.Time,
    name,
    maxlength,
    placeholder,
    disabled,
    required,
    className,
    id,
    renderConfig
), TimeFormControl, ITime {

    /**
     * The minimum value of the time.
     */
    public override var min: LocalTime? by updatingProperty(min) {
        if (it != null) {
            element.min = it.toString()
        } else {
            element.removeAttribute("min")
        }
    }

    /**
     * Set the minimum value of the time.
     */
    @Composable
    public override fun min(min: LocalTime?): Unit = composableProperty("min", {
        this.min = null
    }) {
        this.min = min
    }

    /**
     * The maximum value of the time.
     */
    public override var max: LocalTime? by updatingProperty(max) {
        if (it != null) {
            element.max = it.toString()
        } else {
            element.removeAttribute("max")
        }
    }

    /**
     * Set the maximum value of the time.
     */
    @Composable
    public override fun max(max: LocalTime?): Unit = composableProperty("max", {
        this.max = null
    }) {
        this.max = max
    }

    /**
     * The step value of the time.
     */
    public override var step: Int by updatingProperty(step) {
        element.step = it.toString()
    }

    /**
     * Set the step value of the time.
     */
    @Composable
    public override fun step(step: Int): Unit = composableProperty("step", {
        this.step = TIME_DEFAULT_STEP
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
    override fun stringToValue(text: String?): LocalTime? {
        return if (text.isNullOrEmpty()) {
            null
        } else {
            val date = try {
                LocalTime.parse(text)
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
            val today = today()
            val now = hour()
            val newValue = LocalDateTime(today, (value ?: min ?: now))
                .toInstant(TimeZone.currentSystemDefault())
                .plus(step, DateTimeUnit.SECOND)
                .toLocalDateTime(TimeZone.currentSystemDefault()).time
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
            val today = today()
            val now = hour()
            val newValue = LocalDateTime(today, (value ?: max ?: now))
                .toInstant(TimeZone.currentSystemDefault())
                .minus(step, DateTimeUnit.SECOND)
                .toLocalDateTime(TimeZone.currentSystemDefault()).time
            value = if (min != null && newValue < min!!) min else newValue
        }
    }

}

/**
 * Creates a [Time] component, returning a reference.
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
 * @return a [Time] component
 */
@Composable
public fun IComponent.timeRef(
    value: LocalTime? = null,
    min: LocalTime? = null,
    max: LocalTime? = null,
    step: Int = TIME_DEFAULT_STEP,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITime.() -> Unit = {}
): Time {
    val component =
        remember {
            Time(
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
        set(value) { updateProperty(Time::value, it) }
        set(min) { updateProperty(Time::min, it) }
        set(max) { updateProperty(Time::max, it) }
        set(step) { updateProperty(Time::step, it) }
        set(name) { updateProperty(Time::name, it) }
        set(maxlength) { updateProperty(Time::maxlength, it) }
        set(placeholder) { updateProperty(Time::placeholder, it) }
        set(disabled) { updateProperty(Time::disabled, it) }
        set(required) { updateProperty(Time::required, it) }
        set(className) { updateProperty(Time::className, it) }
        set(id) { updateProperty(Time::id, it) }
    }, setup)
    return component
}


/**
 * Creates a [Time] component.
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
public fun IComponent.time(
    value: LocalTime? = null,
    min: LocalTime? = null,
    max: LocalTime? = null,
    step: Int = TIME_DEFAULT_STEP,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITime.() -> Unit = {}
) {
    val component =
        remember {
            Time(
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
        set(value) { updateProperty(Time::value, it) }
        set(min) { updateProperty(Time::min, it) }
        set(max) { updateProperty(Time::max, it) }
        set(step) { updateProperty(Time::step, it) }
        set(name) { updateProperty(Time::name, it) }
        set(maxlength) { updateProperty(Time::maxlength, it) }
        set(placeholder) { updateProperty(Time::placeholder, it) }
        set(disabled) { updateProperty(Time::disabled, it) }
        set(required) { updateProperty(Time::required, it) }
        set(className) { updateProperty(Time::className, it) }
        set(id) { updateProperty(Time::id, it) }
    }, setup)
}
