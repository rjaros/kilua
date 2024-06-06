@file:Suppress("EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE")
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

package dev.kilua.externals

import dev.kilua.JsModule
import dev.kilua.utils.nativeMapOf
import web.JsAny

/**
 * Tempus Dominus Locale.
 */
public open external class TempusDominusLocale : JsAny {
    public var name: String
    public var localization: JsAny
}

@JsModule("@eonasdan/tempus-dominus/dist/locales/ar.js")
internal external object TempusDominusLocaleAr : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/ar-SA.js")
internal external object TempusDominusLocaleArSa : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/ca.js")
internal external object TempusDominusLocaleCa : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/cs.js")
internal external object TempusDominusLocaleCs : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/de.js")
internal external object TempusDominusLocaleDe : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/es.js")
internal external object TempusDominusLocaleEs : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/fi.js")
internal external object TempusDominusLocaleFi : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/fr.js")
internal external object TempusDominusLocaleFr : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/hr.js")
internal external object TempusDominusLocaleHr : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/hy.js")
internal external object TempusDominusLocaleHy : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/it.js")
internal external object TempusDominusLocaleIt : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/nl.js")
internal external object TempusDominusLocaleNl : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/pl.js")
internal external object TempusDominusLocalePl : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/pt-PT.js")
internal external object TempusDominusLocalePtPt : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/ro.js")
internal external object TempusDominusLocaleRo : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/ru.js")
internal external object TempusDominusLocaleRu : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/sl.js")
internal external object TempusDominusLocaleSl : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/sr.js")
internal external object TempusDominusLocaleSr : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/sr-Latn.js")
internal external object TempusDominusLocaleSrLatn : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/tr.js")
internal external object TempusDominusLocaleTr : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/uk.js")
internal external object TempusDominusLocaleUk : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/zh-CN.js")
internal external object TempusDominusLocaleZhCn : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/zh-HK.js")
internal external object TempusDominusLocaleZhHk : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/zh-MO.js")
internal external object TempusDominusLocaleZhMo : TempusDominusLocale

@JsModule("@eonasdan/tempus-dominus/dist/locales/zh-TW.js")
internal external object TempusDominusLocaleZhTw : TempusDominusLocale

/**
 * Map of Tempus Dominus locales.
 */
public val tempusDominusLocales: Map<String, TempusDominusLocale> = nativeMapOf(
    TempusDominusLocaleArSa.name to TempusDominusLocaleArSa,
    TempusDominusLocaleAr.name to TempusDominusLocaleAr,
    TempusDominusLocaleCa.name to TempusDominusLocaleCa,
    TempusDominusLocaleCs.name to TempusDominusLocaleCs,
    TempusDominusLocaleDe.name to TempusDominusLocaleDe,
    TempusDominusLocaleEs.name to TempusDominusLocaleEs,
    TempusDominusLocaleFi.name to TempusDominusLocaleFi,
    TempusDominusLocaleFr.name to TempusDominusLocaleFr,
    TempusDominusLocaleHr.name to TempusDominusLocaleHr,
    TempusDominusLocaleHy.name to TempusDominusLocaleHy,
    TempusDominusLocaleIt.name to TempusDominusLocaleIt,
    TempusDominusLocaleNl.name to TempusDominusLocaleNl,
    TempusDominusLocalePl.name to TempusDominusLocalePl,
    TempusDominusLocalePtPt.name to TempusDominusLocalePtPt,
    TempusDominusLocaleRo.name to TempusDominusLocaleRo,
    TempusDominusLocaleRu.name to TempusDominusLocaleRu,
    TempusDominusLocaleSl.name to TempusDominusLocaleSl,
    TempusDominusLocaleSrLatn.name to TempusDominusLocaleSrLatn,
    TempusDominusLocaleSr.name to TempusDominusLocaleSr,
    TempusDominusLocaleTr.name to TempusDominusLocaleTr,
    TempusDominusLocaleUk.name to TempusDominusLocaleUk,
    TempusDominusLocaleZhCn.name to TempusDominusLocaleZhCn,
    TempusDominusLocaleZhHk.name to TempusDominusLocaleZhHk,
    TempusDominusLocaleZhMo.name to TempusDominusLocaleZhMo,
    TempusDominusLocaleZhTw.name to TempusDominusLocaleZhTw,
)
