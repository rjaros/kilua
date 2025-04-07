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
import dev.kilua.core.RenderConfig
import dev.kilua.form.IInput
import dev.kilua.form.Input
import dev.kilua.form.InputType
import dev.kilua.form.NumberFormControl
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager
import dev.kilua.utils.toFixedNoRound
import js.core.JsPrimitives.toJsString
import js.core.toLocaleString
import web.events.Event

/**
 * The default number of decimal digits.
 */
public const val NUMERIC_DEFAULT_DECIMALS: Int = 2

internal const val NUMERIC_MAX_LENGTH = 14

/**
 * Number input component.
 */
public interface INumeric : IInput<Number>, NumberFormControl {
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
     * The number of decimal digits.
     */
    public val decimals: Int

    /**
     * Set the number of decimal digits.
     */
    @Composable
    public fun decimals(decimals: Int)

    /**
     * The locale for formatting the number.
     */
    public val locale: Locale

    /**
     * Set the locale for formatting the number.
     */
    @Composable
    public fun locale(locale: Locale)

}

/**
 * Numeric input component.
 */
public open class Numeric(
    value: Number? = null,
    min: Number? = null,
    max: Number? = null,
    decimals: Int = NUMERIC_DEFAULT_DECIMALS,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) : Input<Number>(
    value,
    InputType.Text,
    name,
    NUMERIC_MAX_LENGTH,
    placeholder,
    disabled,
    required,
    className,
    id,
    renderConfig
),
    NumberFormControl, INumeric {

    /**
     * The minimum value.
     */
    public override var min: Number? by updatingProperty(min)

    /**
     * Set the minimum value.
     */
    @Composable
    public override fun min(min: Number): Unit = composableProperty("min", {
        this.min = null
    }) {
        this.min = min
    }

    /**
     * The maximum value.
     */
    public override var max: Number? by updatingProperty(max)

    /**
     * Set the maximum value.
     */
    @Composable
    public override fun max(max: Number): Unit = composableProperty("max", {
        this.max = null
    }) {
        this.max = max
    }

    /**
     * The number of decimal digits.
     */
    public override var decimals: Int by updatingProperty(decimals)

    /**
     * Set the number of decimal digits.
     */
    @Composable
    public override fun decimals(decimals: Int): Unit = composableProperty("decimals", {
        this.decimals = NUMERIC_DEFAULT_DECIMALS
    }) {
        this.decimals = decimals
    }

    /**
     * The locale for formatting the number.
     */
    public override var locale: Locale by updatingProperty(locale)

    /**
     * Set the locale for formatting the number.
     */
    @Composable
    public override fun locale(locale: Locale): Unit = composableProperty("locale", {
        this.locale = LocaleManager.currentLocale
    }) {
        this.locale = locale
    }

    init {
        if (renderConfig.isDom) {
            if (value != null) {
                @Suppress("LeakingThis")
                element.value = valueToString(value) ?: ""
            }
        }
        @Suppress("LeakingThis")
        onEventDirect<Event>("change") {
            formatElement()
        }
    }

    override fun stringToValue(text: String?): Number? {
        return if (text.isNullOrEmpty()) {
            null
        } else {
            text.replace(locale.decimalSeparator, '.').replace(Regex("[ \u00A0]*"), "").toDoubleOrNull()?.let {
                if (min != null && it < (min?.toDouble() ?: 0.0))
                    min
                else if (max != null && it > (max?.toDouble() ?: 0.0))
                    max
                else it
            }
        }
    }

    override fun valueToString(value: Number?): String? {
        return value?.toFixedNoRound(decimals)?.toDouble()?.toLocaleString(locale.language.toJsString())
    }

    protected open fun formatElement() {
        if (value != null) {
            value = value!!.toFixedNoRound(decimals).toDouble()
        }
        if (renderConfig.isDom) {
            element.value = valueToString(value) ?: ""
        }
    }

}

/**
 * Creates a [Numeric] component, returning a reference.
 * @param value the initial value
 * @param min the minimum value
 * @param max the maximum value
 * @param decimals the number of decimal digits
 * @param name the name attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for formatting the number
 * @param className the CSS class name
 * @param id the id attribute of the generated HTML input element
 * @param setup a function for setting up the component
 * @return a [Numeric] component
 */
@Composable
public fun IComponent.numericRef(
    value: Number? = null,
    min: Number? = null,
    max: Number? = null,
    decimals: Int = NUMERIC_DEFAULT_DECIMALS,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    setup: @Composable INumeric.() -> Unit = {}
): Numeric {
    val component =
        remember {
            Numeric(
                value,
                min,
                max,
                decimals,
                name,
                placeholder,
                disabled,
                required,
                locale,
                className,
                id,
                renderConfig
            )
        }
    ComponentNode(component, {
        set(value) { updateProperty(Numeric::value, it) }
        set(min) { updateProperty(Numeric::min, it) }
        set(max) { updateProperty(Numeric::max, it) }
        set(decimals) { updateProperty(Numeric::decimals, it) }
        set(name) { updateProperty(Numeric::name, it) }
        set(placeholder) { updateProperty(Numeric::placeholder, it) }
        set(disabled) { updateProperty(Numeric::disabled, it) }
        set(required) { updateProperty(Numeric::required, it) }
        set(locale) { updateProperty(Numeric::locale, it) }
        set(className) { updateProperty(Numeric::className, it) }
        set(id) { updateProperty(Numeric::id, it) }
    }, setup)
    return component
}

/**
 * Creates a [Numeric] component.
 * @param value the initial value
 * @param min the minimum value
 * @param max the maximum value
 * @param decimals the number of decimal digits
 * @param name the name attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for formatting the number
 * @param className the CSS class name
 * @param id the id attribute of the generated HTML input element
 * @param setup a function for setting up the component
 */
@Composable
public fun IComponent.numeric(
    value: Number? = null,
    min: Number? = null,
    max: Number? = null,
    decimals: Int = NUMERIC_DEFAULT_DECIMALS,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    setup: @Composable INumeric.() -> Unit = {}
) {
    val component =
        remember {
            Numeric(
                value,
                min,
                max,
                decimals,
                name,
                placeholder,
                disabled,
                required,
                locale,
                className,
                id,
                renderConfig
            )
        }
    ComponentNode(component, {
        set(value) { updateProperty(Numeric::value, it) }
        set(min) { updateProperty(Numeric::min, it) }
        set(max) { updateProperty(Numeric::max, it) }
        set(decimals) { updateProperty(Numeric::decimals, it) }
        set(name) { updateProperty(Numeric::name, it) }
        set(placeholder) { updateProperty(Numeric::placeholder, it) }
        set(disabled) { updateProperty(Numeric::disabled, it) }
        set(required) { updateProperty(Numeric::required, it) }
        set(locale) { updateProperty(Numeric::locale, it) }
        set(className) { updateProperty(Numeric::className, it) }
        set(id) { updateProperty(Numeric::id, it) }
    }, setup)
}
