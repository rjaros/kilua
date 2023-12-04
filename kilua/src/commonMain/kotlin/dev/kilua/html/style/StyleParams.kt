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

package dev.kilua.html.style

import androidx.compose.runtime.mutableStateMapOf
import dev.kilua.utils.renderAsCssStyle
import dev.kilua.utils.toKebabCase


/**
 * CSS pseudo classes.
 */
public enum class PClass {
    Active,
    Checked,
    Disabled,
    Empty,
    Enabled,
    FirstChild,
    FirstOfType,
    Focus,
    Hover,
    InRange,
    Invalid,
    LastChild,
    LastOfType,
    Link,
    OnlyOfType,
    OnlyChild,
    Optional,
    OutOfRange,
    ReadOnly,
    ReadWrite,
    Required,
    Root,
    Target,
    Valid,
    Visited;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value
}

/**
 * CSS pseudo elements.
 */
public enum class PElement {
    After,
    Before,
    FirstLetter,
    FirstLine,
    Marker,
    Selection;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value
}

/**
 * Internal container for CSS style parameters.
 */
internal data class StyleParams(
    val selector: String,
    val pClass: PClass? = null,
    val pElement: PElement? = null,
    val mediaQuery: String? = null,
    val styles: Map<String, Any> = emptyMap(),
    val parentStyle: StyleParams? = null
) {
    private fun getStyleDeclaration(): String {
        val pseudoElementName = pElement?.let { "::$it" } ?: ""
        val pseudoClassName = pClass?.let { ":$it" } ?: ""
        val fullSelector = "$selector$pseudoElementName$pseudoClassName"
        return (parentStyle?.let { it.getStyleDeclaration() + " " } ?: "") + fullSelector
    }

    fun renderAsCss(): String {
        return if (mediaQuery == null) {
            "${getStyleDeclaration()} { " + styles.renderAsCssStyle() + " }"
        } else {
            "@media ($mediaQuery) { ${getStyleDeclaration()} { " + styles.renderAsCssStyle() + " } }"
        }
    }

    internal companion object {
        internal var counter = 0

        /**
         * The compose state map of CSS style parameters
         * Used by the first [dev.kilua.compose.Root] component to generate CSS stylesheets.
         */
        internal val stylesStateMap = mutableStateMapOf<String, StyleParams>()

        /**
         * The map of CSS style parameters
         */
        internal val stylesMap = mutableMapOf<String, StyleParams>()
    }
}
