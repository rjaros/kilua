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
import dev.kilua.html.helpers.TagStyle
import dev.kilua.html.helpers.TagStyleDelegateImpl
import web.dom.HTMLStyleElement

/**
 * An object with CSS properties.
 */
public class CssStyle(internal val key: String, onSetCallback: ((Map<String, Any>) -> Unit)) :
    TagStyle<HTMLStyleElement> by TagStyleDelegateImpl(true, onSetCallback)

/**
 * Declares a CSS rule and returns its selector.
 *
 * @param selector an optional CSS selector (if not specified, a unique selector will be generated)
 * @param pClass CSS pseudo class
 * @param pElement CSS pseudo element
 * @param mediaQuery CSS media query
 * @param content the content of the component
 * @return the selector to be used with components.
 */
@Composable
public fun style(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    content: @Composable CssStyle.() -> Unit = {}
): String = style(selector, pClass, pElement, mediaQuery, null, content)

/**
 * Declares a CSS cascading rule and returns its selector.
 *
 * @param selector an optional CSS selector (if not specified, a unique selector will be generated)
 * @param pClass CSS pseudo class
 * @param pElement CSS pseudo element
 * @param mediaQuery CSS media query
 * @param content the content of the component
 * @return the selector to be used with components.
 */
@Composable
public fun CssStyle.style(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    content: @Composable CssStyle.() -> Unit = {}
): String = style(selector, pClass, pElement, mediaQuery, this, content)

@Composable
private fun style(
    selector: String? = null,
    pClass: PClass? = null,
    pElement: PElement? = null,
    mediaQuery: String? = null,
    parent: CssStyle? = null,
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
            parentStyle = parent?.let { StyleParams.stylesMap[it.key] })
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
    return selectorParam.split(' ').last().split('.').last()
}
