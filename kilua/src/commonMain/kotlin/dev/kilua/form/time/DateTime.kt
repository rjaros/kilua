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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.form.DateTimeFormControl
import dev.kilua.form.Input
import dev.kilua.form.InputType
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.utils.now
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

internal const val DATETIME_DEFAULT_STEP = 60

/**
 * Date and time input component.
 */
public open class DateTime(
    value: LocalDateTime? = null,
    min: LocalDateTime? = null,
    max: LocalDateTime? = null,
    step: Int = DATETIME_DEFAULT_STEP,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Input<LocalDateTime>(
    value,
    InputType.DatetimeLocal,
    name,
    maxlength,
    placeholder,
    disabled,
    className,
    renderConfig
),
    DateTimeFormControl {

    /**
     * The minimum value of the date and time.
     */
    public open var min: LocalDateTime? by updatingProperty(min, skipUpdate) {
        if (it != null) {
            element.min = it.toString()
        } else {
            element.removeAttribute("min")
        }
    }

    /**
     * The maximum value of the date and time.
     */
    public open var max: LocalDateTime? by updatingProperty(max, skipUpdate) {
        if (it != null) {
            element.max = it.toString()
        } else {
            element.removeAttribute("max")
        }
    }

    /**
     * The step value of the date and time.
     */
    public open var step: Int by updatingProperty(step, skipUpdate) {
        element.step = it.toString()
    }

    init {
        @Suppress("LeakingThis")
        elementNullable?.let {
            if (min != null) {
                it.min = min.toString()
            }
            if (max != null) {
                it.max = max.toString()
            }
            it.step = step.toString()
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::min, ::max, ::step)
    }

    override fun setValueFromString(text: String?) {
        value = if (text.isNullOrEmpty()) {
            null
        } else {
            val date = try {
                text.toLocalDateTime()
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
    public open fun stepUp() {
        if (elementAvailable) {
            element.stepUp()
            setValueFromString(element.value)
        } else {
            val now = now()
            val newValue = (value ?: min ?: now)
                .toInstant(TimeZone.currentSystemDefault())
                .plus(step, DateTimeUnit.SECOND)
                .toLocalDateTime(TimeZone.currentSystemDefault())
            value = if (max != null && newValue > max!!) max else newValue
        }
    }

    /**
     * Decrements the value by the step value.
     */
    public open fun stepDown() {
        if (elementAvailable) {
            element.stepDown()
            setValueFromString(element.value)
        } else {
            val now = now()
            val newValue = (value ?: max ?: now)
                .toInstant(TimeZone.currentSystemDefault())
                .minus(step, DateTimeUnit.SECOND)
                .toLocalDateTime(TimeZone.currentSystemDefault())
            value = if (min != null && newValue < min!!) min else newValue
        }
    }

}

/**
 * Creates a [DateTime] component.
 *
 * @param value the initial value
 * @param min the minimum value
 * @param max the maximum value
 * @param step the step value
 * @param name the name attribute of the generated HTML input element
 * @param maxlength the maxlength attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param className the CSS class name
 * @param content a function for setting up the component
 * @return a [DateTime] component
 */
@Composable
public fun ComponentBase.dateTime(
    value: LocalDateTime? = null,
    min: LocalDateTime? = null,
    max: LocalDateTime? = null,
    step: Int = DATETIME_DEFAULT_STEP,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    content: @Composable DateTime.() -> Unit = {}
): DateTime {
    val component =
        remember { DateTime(value, min, max, step, name, maxlength, placeholder, disabled, className, renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
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
        set(className) { updateProperty(Date::className, it) }
    }, content)
    return component
}
