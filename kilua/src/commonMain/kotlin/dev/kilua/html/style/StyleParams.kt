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
import dev.kilua.compose.NonDisposableRoot
import dev.kilua.core.SafeDomFactory
import dev.kilua.utils.nativeMapOf
import dev.kilua.utils.renderAsCssStyle

/**
 * Internal container for CSS style parameters.
 */
internal data class StyleParams(
    val selector: String,
    val pClass: String? = null,
    val pElement: String? = null,
    val mediaQuery: String? = null,
    val styles: Map<String, Any> = emptyMap(),
    val parentStyle: StyleParams? = null,
    val forParent: Boolean = false
) {
    private fun getStyleDeclaration(): String {
        val pseudoElementName = pElement?.let { "::$it" } ?: ""
        val pseudoClassName = pClass?.let { ":$it" } ?: ""
        val fullSelector = "$selector$pseudoElementName$pseudoClassName"
        return (parentStyle?.let { it.getStyleDeclaration() + if (!forParent) " " else "" } ?: "") + fullSelector
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
        internal val stylesMap = nativeMapOf<StyleParams>()

        init {
            SafeDomFactory.getFirstElementByTagName("head")?.let { head ->
                NonDisposableRoot(head) {
                    style(stylesStateMap.values.joinToString("\n") { it.renderAsCss() })
                }
            }
        }

        internal fun disposeAllStyleParams() {
            stylesStateMap.clear()
            stylesMap.clear()
        }
    }
}
