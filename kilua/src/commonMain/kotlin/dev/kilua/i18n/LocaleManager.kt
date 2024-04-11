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

package dev.kilua.i18n

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.kilua.utils.nativeListOf

/**
 * Global locale manager.
 * Exposes current locale instance. Notifies subscribers about locale changes.
 */
public object LocaleManager {
    private val listeners = nativeListOf<LocaleChangeListener>()
    private val ssrListeners = nativeListOf<LocaleChangeListener>()

    public val defaultLocale: Locale = DefaultLocale()

    public var currentLocale: Locale by mutableStateOf(defaultLocale)

    public fun setCurrentLocale(locale: Locale, skipSsr: Boolean = false) {
        currentLocale = locale
        listeners.forEach { it.setLocale(locale) }
        if (!skipSsr) ssrListeners.forEach { it.setLocale(locale) }
    }

    public fun registerLocaleListener(localeChangeListener: LocaleChangeListener) {
        localeChangeListener.setLocale(currentLocale)
        listeners += localeChangeListener
    }

    public fun registerSsrLocaleListener(ssrLocaleChangeListener: LocaleChangeListener) {
        ssrLocaleChangeListener.setLocale(currentLocale)
        ssrListeners += ssrLocaleChangeListener
    }
}
