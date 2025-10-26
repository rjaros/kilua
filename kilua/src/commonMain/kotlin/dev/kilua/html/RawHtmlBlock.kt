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

package dev.kilua.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import web.html.HtmlSource

/**
 * HTML Div component with raw HTML content.
 */
public open class RawHtmlBlock(
    rawHtml: String,
    renderConfig: RenderConfig = RenderConfig.Default
) : Div(renderConfig = renderConfig) {

    init {
        @Suppress("LeakingThis")
        display = Display.Contents
        if (renderConfig.isDom) {
            @Suppress("LeakingThis")
            element.innerHTML = HtmlSource(rawHtml)
        }
    }

    public var rawHtml: String by updatingProperty(rawHtml) {
        element.innerHTML = HtmlSource(it)
    }

    override fun renderToStringBuilder(builder: StringBuilder) {
        builder.append("<div style=\"display: contents;\">")
        builder.append(rawHtml)
        builder.append("</div>")
    }
}

/**
 * Creates a [RawHtmlBlock] component.
 *
 * @param rawHtml the raw HTML content
 */
@Composable
public fun IComponent.rawHtmlBlock(rawHtml: String) {
    val component = remember { RawHtmlBlock(rawHtml, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(rawHtml) { updateProperty(RawHtmlBlock::rawHtml, it) }
    }) {}
}
