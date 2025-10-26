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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.kilua.utils.jsGet
import name.kropp.kotlinx.gettext.Gettext
import kotlin.js.JsAny

/**
 * Provides translations by the underlying gettext library.
 */
public class I18n(vararg localeData: Pair<String, JsAny>) : LocaleChangeListener {

    private val localeMap: Map<String, Gettext> = localeData.associate { (locale, data) ->
        val poFile = try {
            data.jsGet("default")!!.toString()
        } catch (e: Throwable) {
            data.toString()
        }
        locale to Gettext.load(name.kropp.kotlinx.gettext.Locale(locale), poFile)
    }

    private var currentGettext: Gettext? by mutableStateOf(null)

    init {
        LocaleManager.registerLocaleListener(this)
    }

    override fun setLocale(locale: Locale) {
        currentGettext = localeMap[locale.language] ?: localeMap[locale.languageBase]
    }

    /**
     * Translate [text].
     */
    @Composable
    public fun tr(text: String): String {
        return currentGettext?.tr(text) ?: text
    }

    /**
     * Translate formatted [text] with provided [args].
     */
    @Composable
    public fun tr(text: String, vararg args: Pair<String, String>): String =
        currentGettext?.tr(text, *args) ?: text

    /**
     * Translate [text] with different [plural] form, chosen based on provided number [n].
     */
    @Composable
    public fun trn(text: String, plural: String, n: Int): String = currentGettext?.trn(text, plural, n) ?: text

    /**
     * Translate [text] with different [plural] form, chosen based on provided number [n].
     */
    @Composable
    public fun trn(text: String, plural: String, n: Long): String = currentGettext?.trn(text, plural, n) ?: text

    /**
     * Translate formatted [text] with different [plural] form
     * with provided [args], chosen based on provided number [n].
     */
    @Composable
    public fun trn(text: String, plural: String, n: Int, vararg args: Pair<String, String>): String =
        currentGettext?.trn(text, plural, n, *args) ?: text

    /**
     * Translate formatted [text] with different [plural] form
     * with provided [args], chosen based on provided number [n].
     */
    @Composable
    public fun trn(text: String, plural: String, n: Long, vararg args: Pair<String, String>): String =
        currentGettext?.trn(text, plural, n, *args) ?: text

    /**
     * Translate [text] in a given [context].
     */
    @Composable
    public fun trc(context: String, text: String): String = currentGettext?.trc(context, text) ?: text

    /**
     * Translate formatted [text] with provided [args] in a given [context].
     */
    @Composable
    public fun trc(context: String, text: String, vararg args: Pair<String, String>): String =
        currentGettext?.trc(context, text, *args) ?: text

    /**
     * Translate [text] with different [plural] form, chosen based on provided number [n] in a given [context].
     */
    @Composable
    public fun trnc(context: String, text: String, plural: String, n: Int): String =
        currentGettext?.trnc(context, text, plural, n) ?: text

    /**
     * Translate [text] with different [plural] form, chosen based on provided number [n] in a given [context].
     */
    @Composable
    public fun trnc(context: String, text: String, plural: String, n: Long): String =
        currentGettext?.trnc(context, text, plural, n) ?: text

    /**
     * Translate formatted [text] with different [plural] form
     * with provided [args], chosen based on provided number [n] in a given [context].
     */
    @Composable
    public fun trnc(context: String, text: String, plural: String, n: Int, vararg args: Pair<String, String>): String =
        currentGettext?.trnc(context, text, plural, n, *args) ?: text

    /**
     * Translate formatted [text] with different [plural] form
     * with provided [args], chosen based on provided number [n] in a given [context].
     */
    @Composable
    public fun trnc(context: String, text: String, plural: String, n: Long, vararg args: Pair<String, String>): String =
        currentGettext?.trnc(context, text, plural, n, *args) ?: text

    /**
     * Mark [text] for translation, but do not translate it.
     */
    @Composable
    public fun marktr(text: String): String = currentGettext?.marktr(text) ?: text
}
