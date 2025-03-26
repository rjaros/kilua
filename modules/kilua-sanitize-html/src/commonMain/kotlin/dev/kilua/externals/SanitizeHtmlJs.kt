@file:JsModule("sanitize-html")
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

package dev.kilua.externals

import dev.kilua.utils.JsArray
import js.import.JsModule
import js.core.JsAny
import js.core.JsString

internal external class SanitizeHtmlOptionsJs : JsAny {
    var allowedTags: JsAny
    var nonBooleanAttributes: JsArray<JsString>
    var allowedAttributes: JsAny
    var disallowedTagsMode: String
    var selfClosing: JsArray<JsString>
    var allowedSchemes: JsArray<JsString>
    var allowedSchemesByTag: JsAny
    var allowedSchemesAppliedToAttributes: JsArray<JsString>
    var allowProtocolRelative: Boolean
    var enforceHtmlBoundary: Boolean
    var parseStyleAttributes: Boolean
    var parser: JsAny
    var allowedClasses: JsAny
    var allowedStyles: JsAny
    var transformTags: JsAny
    var nonTextTags: JsArray<JsString>
    var nestingLimit: Int
}

@kotlin.js.JsName("default")
internal external fun sanitizeHtml(text: String, options: SanitizeHtmlOptionsJs): String
