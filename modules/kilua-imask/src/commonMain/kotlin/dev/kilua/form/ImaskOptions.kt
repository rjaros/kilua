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

package dev.kilua.form

import dev.kilua.externals.EnumMaskOptionsJs
import dev.kilua.externals.FunctionMaskOptionsJs
import dev.kilua.externals.ImaskObjJs
import dev.kilua.externals.ImaskOptionsJs
import dev.kilua.externals.NumberMaskOptionsJs
import dev.kilua.externals.PatternMaskOptionsJs
import dev.kilua.externals.RangeMaskOptionsJs
import dev.kilua.utils.jsSet
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager
import dev.kilua.utils.assign
import dev.kilua.utils.toJsArray
import dev.kilua.utils.toKebabCase
import dev.kilua.utils.unsafeCast
import js.core.JsAny
import js.core.JsPrimitives.toJsBoolean
import js.core.JsPrimitives.toJsDouble
import js.core.JsPrimitives.toJsString
import js.objects.jso
import js.regexp.RegExp

/**
 * Text input mask overwrite modes.
 */
public enum class MaskOverwrite {
    True,
    False,
    Shift;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Text input number mask autofix modes.
 */
public enum class MaskAutofix {
    True,
    False,
    Pad;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}


/**
 * A text input mask configuration with a pattern.
 */
public data class PatternMask(
    val pattern: String,
    val lazy: Boolean? = null,
    val eager: Boolean? = null,
    val placeholderChar: Char? = null,
    val definitions: JsAny? = null,
    val blocks: Map<String, ImaskOptions>? = null,
)

/**
 * An extension function to convert configuration class to a JS object.
 */
internal fun PatternMask.toJs(): PatternMaskOptionsJs {
    val self = this
    return jso {
        this.mask = pattern
        if (self.lazy != null) this.lazy = self.lazy
        if (self.eager != null) this.eager = self.eager
        if (self.placeholderChar != null) this.placeholderChar = self.placeholderChar.toString()
        if (self.definitions != null) this.definitions = self.definitions
        if (self.blocks != null) {
            this.blocks = jso {
                self.blocks.forEach { (def, options) ->
                    jsSet(def, options.toJs())
                }
            }
        }
    }
}


/**
 * A text input mask configuration with a range.
 */
public data class RangeMask(
    val from: Int,
    val to: Int,
    val maxLength: Int? = null,
    val autofix: MaskAutofix? = null,
    val lazy: Boolean? = null,
    val eager: Boolean? = null,
    val placeholderChar: Char? = null,
)

/**
 * An extension function to convert configuration class to a JS object.
 */
internal fun RangeMask.toJs(): RangeMaskOptionsJs {
    val autofixJsAny = when (autofix) {
        MaskAutofix.True -> true.toJsBoolean()
        MaskAutofix.Pad -> autofix.value.toJsString()
        else -> null
    }
    val self = this
    return jso {
        this.mask = ImaskObjJs.MaskedRange
        this.from = self.from
        this.to = self.to
        if (self.maxLength != null) this.maxLength = self.maxLength
        if (autofixJsAny != null) this.autofix = autofixJsAny
        if (self.lazy != null) this.lazy = self.lazy
        if (self.eager != null) this.eager = self.eager
        if (self.placeholderChar != null) this.placeholderChar = self.placeholderChar.toString()
    }
}

/**
 * A text input mask configuration with a list of values.
 */
public data class EnumMask(
    val enum: List<String>,
    val lazy: Boolean? = null,
    val eager: Boolean? = null,
    val placeholderChar: Char? = null,
)

/**
 * An extension function to convert configuration class to a JS object.
 */
internal fun EnumMask.toJs(): EnumMaskOptionsJs {
    val self = this
    return jso {
        this.mask = ImaskObjJs.MaskedEnum
        this.enum = self.enum.map { it.toJsString() }.toJsArray()
        if (self.lazy != null) this.lazy = self.lazy
        if (self.eager != null) this.eager = self.eager
        if (self.placeholderChar != null) this.placeholderChar = self.placeholderChar.toString()
    }
}

/**
 * A text input mask configuration for a number value.
 */
public data class NumberMask(
    val scale: Int? = null,
    val padFractionalZeros: Boolean? = null,
    val normalizeZeros: Boolean? = null,
    val mapToRadix: List<Char> = listOf('.'),
    val min: Number? = null,
    val max: Number? = null,
    val locale: Locale = LocaleManager.currentLocale,
)

/**
 * An extension function to convert configuration class to a JS object.
 */
internal fun NumberMask.toJs(): NumberMaskOptionsJs {
    val self = this
    return jso {
        this.mask = jsNumber()
        if (self.scale != null) this.scale = self.scale
        if (self.locale.thousandsSeparator != null) this.thousandsSeparator = self.locale.thousandsSeparator.toString()
        if (self.padFractionalZeros != null) this.padFractionalZeros = self.padFractionalZeros
        if (self.normalizeZeros != null) this.normalizeZeros = self.normalizeZeros
        this.radix = self.locale.decimalSeparator.toString()
        this.mapToRadix = self.mapToRadix.map { it.toString().toJsString() }.toJsArray()
        if (self.min != null) this.min = self.min.toDouble().toJsDouble().unsafeCast()
        if (self.max != null) this.max = self.max.toDouble().toJsDouble().unsafeCast()
    }
}

/**
 * A text input mask configuration.
 */
public data class ImaskOptions(
    val pattern: PatternMask? = null,
    val range: RangeMask? = null,
    val enum: EnumMask? = null,
    val number: NumberMask? = null,
    val regExp: RegExp? = null,
    val function: ((String) -> Boolean)? = null,
    val list: List<ImaskOptions>? = null,
    val overwrite: MaskOverwrite? = null,
) : MaskOptions {
    override fun maskNumericValue(value: String): String {
        val radix = number?.locale?.decimalSeparator
        return if (radix != null) {
            value.replace(".", radix.toString())
        } else value
    }
}

/**
 * An extension function to convert configuration class to a JS object.
 */
internal fun ImaskOptions.toJs(): ImaskOptionsJs {
    val overwriteJsAny = when (overwrite) {
        MaskOverwrite.True -> true.toJsBoolean()
        MaskOverwrite.Shift -> overwrite.value.toJsString()
        else -> null
    }

    return jso {
        if (pattern != null) {
            val patternOptions = pattern.toJs()
            assign(this, patternOptions)
        } else if (range != null) {
            val rangeOptions = range.toJs()
            assign(this, rangeOptions)
        } else if (enum != null) {
            val enumOptions = enum.toJs()
            assign(this, enumOptions)
        } else if (number != null) {
            val numberOptions = number.toJs()
            assign(this, numberOptions)
        } else if (regExp != null) {
            jsSet("mask", regExp)
        } else if (function != null) {
            val functionOptions = jso<FunctionMaskOptionsJs> {
                this.mask = function
            }
            assign(this, functionOptions)
        } else if (list != null) {
            jsSet("mask", list.map { it.toJs() }.toJsArray())
        }
        if (overwrite != null) this.overwrite = overwriteJsAny
    }
}
