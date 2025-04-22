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

package dev.kilua.sanitize

import dev.kilua.externals.SanitizeHtmlOptionsJs
import dev.kilua.utils.obj
import dev.kilua.utils.toJsAny
import dev.kilua.utils.toJsArray
import js.core.JsAny
import js.core.JsPrimitives.toJsBoolean
import js.core.JsPrimitives.toJsString
import js.regexp.RegExp

/**
 * Disallowed tags modes.
 */
public enum class DisallowedTagsMode(public val mode: String) {
    Discard("discard"),
    CompletelyDiscard("completelyDiscard"),
    Escape("escape"),
    RecursiveEscape("recursiveEscape")
}

/**
 * Sanitize HTML options.
 */
public data class SanitizeHtmlOptions(
    val allowedTags: List<String>? = null,
    val allowAllTags: Boolean = false,
    val allowedAttributes: Map<String, List<String>>? = null,
    val allowAllAttributes: Boolean = false,
    val nonBooleanAttributes: List<String>? = null,
    val disallowedTagsMode: DisallowedTagsMode? = null,
    val selfClosing: List<String>? = null,
    val allowedSchemes: List<String>? = null,
    val allowedSchemesByTag: Map<String, List<String>>? = null,
    val allowedSchemesAppliedToAttributes: List<String>? = null,
    val allowProtocolRelative: Boolean? = null,
    val enforceHtmlBoundary: Boolean? = null,
    val parseStyleAttributes: Boolean? = null,
    val parser: JsAny? = null,
    val allowedClasses: Map<String, List<String>>? = null,
    val allowedStyles: Map<String, Map<String, RegExp>>? = null,
    val transformTags: Map<String, JsAny>? = null,
    val nonTextTags: List<String>? = null,
    val nestingLimit: Int? = null
)

internal fun SanitizeHtmlOptions.toJs(): SanitizeHtmlOptionsJs {
    val self = this
    return obj {
        if (self.allowAllTags) {
            this.allowedTags = false.toJsBoolean()
        } else if (self.allowedTags != null) {
            this.allowedTags = self.allowedTags.map { it.toJsString() }.toJsArray()
        }
        if (self.allowAllAttributes) {
            this.allowedAttributes = false.toJsBoolean()
        } else if (self.allowedAttributes != null) {
            this.allowedAttributes = self.allowedAttributes.toJsAny()
        }
        if (self.nonBooleanAttributes != null) {
            this.nonBooleanAttributes = self.nonBooleanAttributes.map { it.toJsString() }.toJsArray()
        }
        if (self.disallowedTagsMode != null) {
            this.disallowedTagsMode = self.disallowedTagsMode.mode
        }
        if (self.selfClosing != null) {
            this.selfClosing = self.selfClosing.map { it.toJsString() }.toJsArray()
        }
        if (self.allowedSchemes != null) {
            this.allowedSchemes = self.allowedSchemes.map { it.toJsString() }.toJsArray()
        }
        if (self.allowedSchemesByTag != null) {
            this.allowedSchemesByTag = self.allowedSchemesByTag.toJsAny()
        }
        if (self.allowedSchemesAppliedToAttributes != null) {
            this.allowedSchemesAppliedToAttributes =
                self.allowedSchemesAppliedToAttributes.map { it.toJsString() }.toJsArray()
        }
        if (self.allowProtocolRelative != null) {
            this.allowProtocolRelative = self.allowProtocolRelative
        }
        if (self.enforceHtmlBoundary != null) {
            this.enforceHtmlBoundary = self.enforceHtmlBoundary
        }
        if (self.parseStyleAttributes != null) {
            this.parseStyleAttributes = self.parseStyleAttributes
        }
        if (self.parser != null) {
            this.parser = self.parser
        }
        if (self.allowedClasses != null) {
            this.allowedClasses = self.allowedClasses.toJsAny()
        }
        if (self.allowedStyles != null) {
            this.allowedStyles = self.allowedStyles.toJsAny()
        }
        if (self.transformTags != null) {
            this.transformTags = self.transformTags.toJsAny()
        }
        if (self.nonTextTags != null) {
            this.nonTextTags = self.nonTextTags.map { it.toJsString() }.toJsArray()
        }
        if (self.nestingLimit != null) {
            this.nestingLimit = self.nestingLimit
        }
    }
}

/**
 * Sanitize HTML code.
 *
 * @param text HTML code
 * @param options sanitizer options
 * @return sanitized HTML
 */
public fun sanitizeHtml(text: String, options: SanitizeHtmlOptions = SanitizeHtmlOptions()): String {
    return dev.kilua.externals.sanitizeHtml(text, options.toJs())
}
