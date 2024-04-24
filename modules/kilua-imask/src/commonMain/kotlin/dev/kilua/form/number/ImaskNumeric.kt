/*
 * Copyright (c) 2024 Robert Jaros
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
import dev.kilua.core.IComponent
import dev.kilua.form.ImaskOptions
import dev.kilua.form.NumberMask
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager

/**
 * Creates a [Numeric] component with masked input.
 * @param value the initial value
 * @param min the minimum value
 * @param max the maximum value
 * @param decimals the number of decimal digits
 * @param name the name attribute of the generated HTML input element
 * @param placeholder the placeholder attribute of the generated HTML input element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for formatting the number
 * @param padFractionalZeros determines if the fractional part should be padded with zeros
 * @param normalizeZeros determines if the fractional part should be normalized
 * @param className the CSS class name
 * @param id the id attribute of the generated HTML input element
 * @param setup a function for setting up the component
 * @return a [Numeric] component
 */
@Composable
public fun IComponent.imaskNumeric(
    value: Number? = null,
    min: Number? = null,
    max: Number? = null,
    decimals: Int = NUMERIC_DEFAULT_DECIMALS,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    locale: Locale = LocaleManager.currentLocale,
    padFractionalZeros: Boolean? = null,
    normalizeZeros: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable INumeric.() -> Unit = {}
): Numeric {
    return numeric(value, min, max, decimals, name, placeholder, disabled, required, locale, className, id) {
        maskOptions = ImaskOptions(
            number = NumberMask(
                scale = decimals,
                padFractionalZeros = padFractionalZeros,
                normalizeZeros = normalizeZeros,
                min = min,
                max = max,
                locale = locale
            )
        )
        setup()
    }
}
