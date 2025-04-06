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

package dev.kilua.utils

import dev.kilua.externals.NumberExt
import js.core.JsPrimitives.toJsDouble
import js.core.JsPrimitives.toJsInt

/**
 * Utility extension function to format a Double with a given number of digits after the decimal point.
 */
public fun Double.toFixed(size: Int = 2): String {
    return this.toJsDouble().unsafeCast<NumberExt>().toFixed(size)
}

/**
 * Utility extension function to format a Double according to the given locale.
 */
public fun Double.toLocaleString(locale: String): String {
    return this.toJsDouble().unsafeCast<NumberExt>().toLocaleString(locale)
}

/**
 * Utility extension function to format an Int according to the given locale.
 */
public fun Int.toLocaleString(locale: String): String {
    return this.toJsInt().unsafeCast<NumberExt>().toLocaleString(locale)
}

/**
 * Utility extension function to convert string from kebab-case to camelCase.
 */
public fun String.toCamelCase(): String {
    return this.replace(Regex("(-\\w)")) {
        it.value.drop(1).uppercase()
    }
}

/**
 * Utility extension function to convert string from camelCase to kebab-case.
 */
public fun String.toKebabCase(): String {
    return this.replace(Regex("(?<=.)[A-Z]"), "-$0").lowercase()
}

/**
 * Formats a number to fixed decimal digits without rounding.
 */
public fun Number.toFixedNoRound(precision: Int): String {
    val r = Regex("^-?\\d+(?:\\.\\d{0,$precision})?")
    return r.find(this.toString())?.value ?: "0"
}
