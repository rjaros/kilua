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

package dev.kilua.i18n

import dev.kilua.utils.console
import dev.kilua.utils.toLocaleString

/**
 * Base i18n interface.
 */
public interface Locale {
    /**
     * Language code.
     */
    public val language: String

    /**
     * Decimal separator.
     */
    public val decimalSeparator: Char
}

/**
 * Calculate decimal separator for the given language.
 */
@Suppress("TooGenericExceptionCaught", "MagicNumber")
public fun decimalSeparator(language: String): Char {
    return try {
        (1.1).toLocaleString(language).dropLast(1).last()
    } catch (e: Exception) {
        console.log("Cannot determine decimal separator for language: $language (${e.message})")
        '.'
    }
}
