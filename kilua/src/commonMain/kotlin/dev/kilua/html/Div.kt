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
import dev.kilua.core.IComponent
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import web.dom.HTMLDivElement

/**
 * HTML Div component.
 */
public interface IDiv : ITag<HTMLDivElement>

/**
 * HTML Div component.
 */
public open class Div(className: String? = null, id: String? = null, renderConfig: RenderConfig = DefaultRenderConfig()) :
    Tag<HTMLDivElement>("div", className, id, renderConfig), IDiv

/**
 * Creates a [Div] component.
 *
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 * @return the [Div] component
 */
@Composable
public fun IComponent.div(className: String? = null, id: String? = null, content: @Composable IDiv.() -> Unit = {}): Div {
    val component = remember { Div(className, id, renderConfig) }
    ComponentNode(component, {
        set(className) { updateProperty(Div::className, it) }
        set(id) { updateProperty(Div::id, it) }
    }, content)
    return component
}
