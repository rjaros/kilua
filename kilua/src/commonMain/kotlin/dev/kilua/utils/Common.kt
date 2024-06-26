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

package dev.kilua.utils

import dev.kilua.html.BoxShadow
import dev.kilua.html.CssSize
import dev.kilua.html.Transition

/**
 * Whether the DOM is available
 */
public expect val isDom: Boolean

/**
 * Render map of properties to an CSS style string.
 */
public fun Map<String, Any>.renderAsHtmlAttributes(): String {
    return this.mapNotNull {
        @Suppress("UNCHECKED_CAST")
        when (it.key) {
            "accept" -> "accept=\"" + (it.value as List<String>).joinToString(",") + "\""
            "attributeName" -> "attributeName=\"${it.value}\""
            "viewBox" -> "viewBox=\"${it.value}\""
            else -> when (it.value) {
                true -> it.key.toKebabCase()
                false -> null
                else -> "${it.key.toKebabCase()}=\"${it.value}\""
            }
        }
    }.joinToString(" ")
}

/**
 * Render map of properties to an CSS style string.
 */
public fun Map<String, Any>.renderAsCssStyle(): String {
    return this.map {
        @Suppress("UNCHECKED_CAST")
        when (it.key) {
            "gridTemplateAreas" ->
                "grid-template-areas: " + (it.value as List<String>).joinToString(" ") { "&quot;$it&quot;" } + ";"

            "boxShadowList" ->
                "box-shadow: " + (it.value as List<BoxShadow>).joinToString(", ") { it.toString() } + ";"

            "transitionList" ->
                "transition: " + (it.value as List<Transition>).joinToString(", ") { it.toString() } + ";"

            "borderRadiusList" ->
                "border-radius: " + (it.value as List<CssSize>).joinToString(" ") { it.toString() } + ";"

            else -> "${it.key.toKebabCase()}: ${it.value};"
        }

    }.joinToString(" ")
}

/**
 * Utility extension function to synchronise elements of the MutableList.
 */
public fun <T> MutableList<T>.syncWithList(list: List<T>) {
    if (list.isEmpty()) {
        this.clear()
    } else {
        for (pos in (this.size - 1) downTo list.size) this.removeAt(pos)
        list.forEachIndexed { index, element ->
            if (index < this.size) {
                if (this[index] != element) this[index] = element
            } else {
                this.add(element)
            }
        }
    }
}

/**
 * Helper type used to define CSS style attributes.
 */
public typealias StringPair = Pair<String, String>

/**
 * Utility extension function to convert List<String> into List<StringPair>.
 */
public fun List<String>.pairs(): List<StringPair> = this.map { it to it }

/**
 * Builds List<StringPair> out of given Strings.
 */
public fun listOfPairs(vararg params: String): List<StringPair> = params.asList().pairs()

/**
 * Useful operator to concatenate two strings with a space (for CSS class names)
 */
public operator fun String?.rem(str: String?): String? {
    return if (this == null) {
        str
    } else if (str == null) {
        this
    } else {
        "$this $str"
    }
}
