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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.html.ITag
import dev.kilua.html.helpers.TagStyle
import dev.kilua.html.helpers.TagStyleDelegateImpl
import dev.kilua.utils.rem
import dev.kilua.utils.toKebabCase
import web.dom.HTMLElement
import web.dom.HTMLStyleElement

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
 * An object with CSS properties.
 */
public class CssStyle(internal val key: String, onSetCallback: ((Map<String, Any>) -> Unit)) :
    TagStyle<HTMLStyleElement> by TagStyleDelegateImpl(true, onSetCallback)

/**
 * Declares a global CSS rule and returns its selector.
 *
 * @param selector an optional CSS selector (if not specified, a unique selector will be generated)
 * @param pClass CSS pseudo class
 * @param pElement CSS pseudo element
 * @param mediaQuery CSS media query
 * @param content the content of the CSS style
 * @return the selector to be used with components.
 */
@Composable
public fun globalStyle(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    content: @Composable CssStyle.() -> Unit = {}
): String = style(selector, pClass?.value, pElement?.value, mediaQuery, null, false, content)

/**
 * Declares a CSS rule and applies it to the enclosing component. Returns the selector.
 *
 * @param selector an optional CSS selector (if not specified, a unique selector will be generated)
 * @param pClass CSS pseudo class
 * @param pElement CSS pseudo element
 * @param mediaQuery CSS media query
 * @param content the content of the CSS style
 * @return the selector to be used with components.
 */
@Composable
public fun <E : HTMLElement> ITag<E>.style(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    content: @Composable CssStyle.() -> Unit = {}
): String {
    val style = style(selector, pClass?.value, pElement?.value, mediaQuery, null, false, content)
    val originalClassName = className
    if (originalClassName?.endsWith(style) == true) {
        className(originalClassName)
    } else {
        className(originalClassName % style)
    }
    return style
}

/**
 * Declares a CSS cascading rule and returns its selector.
 *
 * @param selector an optional CSS selector (if not specified, a unique selector will be generated)
 * @param pClass CSS pseudo class
 * @param pElement CSS pseudo element
 * @param mediaQuery CSS media query
 * @param content the content of the CSS style
 * @return the selector to be used with components.
 */
@Composable
public fun CssStyle.style(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    content: @Composable CssStyle.() -> Unit = {}
): String = style(selector, pClass?.value, pElement?.value, mediaQuery, this, false, content)

/**
 * Declares a pseudo class for parent CSS selector.
 *
 * @param pClass CSS pseudo class
 * @param mediaQuery CSS media query
 * @param content the content of the CSS style
 * @return the selector to be used with components.
 */
@Composable
public fun CssStyle.pClass(
    pClass: PClass,
    mediaQuery: String? = null,
    content: @Composable CssStyle.() -> Unit = {}
): String = style("", pClass.value, null, mediaQuery, this, true, content)

/**
 * Declares a custom pseudo class for parent CSS selector.
 *
 * @param pClass custom CSS pseudo class
 * @param mediaQuery CSS media query
 * @param content the content of the CSS style
 * @return the selector to be used with components.
 */
@Composable
public fun CssStyle.pClass(
    pClass: String,
    mediaQuery: String? = null,
    content: @Composable CssStyle.() -> Unit = {}
): String = style("", pClass, null, mediaQuery, this, true, content)

/**
 * Declares a pseudo element for parent CSS selector.
 *
 * @param pElement CSS pseudo element
 * @param mediaQuery CSS media query
 * @param content the content of the CSS style
 * @return the selector to be used with components.
 */
@Composable
public fun CssStyle.pElement(
    pElement: PElement,
    mediaQuery: String? = null,
    content: @Composable CssStyle.() -> Unit = {}
): String = style("", null, pElement.value, mediaQuery, this, true, content)

/**
 * Declares a custom pseudo element for parent CSS selector.
 *
 * @param pElement custom CSS pseudo element
 * @param mediaQuery CSS media query
 * @param content the content of the CSS style
 * @return the selector to be used with components.
 */
@Composable
public fun CssStyle.pElement(
    pElement: String,
    mediaQuery: String? = null,
    content: @Composable CssStyle.() -> Unit = {}
): String = style("", null, pElement, mediaQuery, this, true, content)

@Composable
private fun style(
    selector: String? = null,
    pClass: String? = null,
    pElement: String? = null,
    mediaQuery: String? = null,
    parent: CssStyle? = null,
    forParent: Boolean = false,
    content: @Composable CssStyle.() -> Unit = {}
): String {
    val selectorKey = remember { ".kilua_styleclass_${StyleParams.counter++}" }
    val selectorParam = selector ?: selectorKey
    val styleParams =
        StyleParams(
            selectorParam,
            pClass,
            pElement,
            mediaQuery,
            parentStyle = parent?.let { StyleParams.stylesMap[it.key] },
            forParent = forParent
        )
    StyleParams.stylesStateMap[selectorKey] = styleParams
    StyleParams.stylesMap[selectorKey] = styleParams
    val tagStyleDelegateImpl = CssStyle(selectorKey) {
        val styleParamsCopy = styleParams.copy(
            styles = it.toMap()
        )
        StyleParams.stylesStateMap[selectorKey] = styleParamsCopy
        StyleParams.stylesMap[selectorKey] = styleParamsCopy
    }
    content(tagStyleDelegateImpl)
    DisposableEffect(selectorKey) {
        onDispose {
            StyleParams.stylesStateMap.remove(selectorKey)
            StyleParams.stylesMap.remove(selectorKey)
        }
    }
    return selectorParam.split(' ').last().split(':').first().split('.').last()
}
