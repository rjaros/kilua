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

package dev.kilua.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import web.dom.HTMLHeadingElement

/**
 * HTML H4 component.
 */
public open class H4(className: String? = null, renderConfig: RenderConfig = DefaultRenderConfig()) :
    Tag<HTMLHeadingElement>("h4", className, renderConfig = renderConfig)

/**
 * Creates a [H4] component.
 *
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [H4] component
 */
@Composable
public fun ComponentBase.h4(className: String? = null, content: @Composable H4.() -> Unit = {}): H4 {
    val component = remember { H4(className, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(className) { updateProperty(H4::className, it) }
    }, content)
    return component
}

/**
 * Creates a [H4] component.
 *
 * @param text the text of the component
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [H4] component
 */
@Composable
public fun ComponentBase.h4t(text: String, className: String? = null, content: @Composable H4.() -> Unit = {}): H4 {
    return h4(className) {
        +text
        content()
    }
}
