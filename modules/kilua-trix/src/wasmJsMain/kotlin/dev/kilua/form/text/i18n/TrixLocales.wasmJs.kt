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

package dev.kilua.form.text.i18n

import dev.kilua.externals.Trix
import dev.kilua.externals.TrixLocale
import dev.kilua.i18n.Locale
import dev.kilua.utils.assign
import dev.kilua.utils.unsafeCast

/**
 * A TrixLocale class implementing JsAny interface.
 */
internal external interface TrixLocaleWasm : TrixLocale, JsAny

internal actual fun getToolbarContent(locale: Locale): String {
    val trixLocale =
        TrixLocales.locales[locale.language] ?: TrixLocales.locales[locale.languageBase] ?: TrixLocales.trixLocaleEn
    assign(Trix.config.lang, trixLocale.unsafeCast())
    return Trix.config.toolbar.getDefaultHTML()
}
