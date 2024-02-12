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
import dev.kilua.html.helpers.PropertyListBuilder
import web.dom.HTMLTableCellElement

/**
 * HTML Td component.
 */
public open class Td(
    colspan: Int? = null,
    rowspan: Int? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLTableCellElement>("td", className, renderConfig = renderConfig) {

    /**
     * The number of columns the cell extends.
     */
    public open var colspan: Int? by updatingProperty(colspan) {
        if (it != null) {
            element.colSpan = it
        } else {
            element.removeAttribute("colspan")
        }
    }

    /**
     * The number of rows the cell extends.
     */
    public open var rowspan: Int? by updatingProperty(rowspan) {
        if (it != null) {
            element.rowSpan = it
        } else {
            element.removeAttribute("rowspan")
        }
    }

    init {
        if (renderConfig.isDom) {
            if (colspan != null) {
                element.colSpan = colspan
            }
            if (rowspan != null) {
                element.rowSpan = rowspan
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::colspan, ::rowspan)
    }
}

/**
 * Creates a [Td] component.
 *
 * @param colspan the number of columns the cell extends
 * @param rowspan the number of rows the cell extends
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [Td] component
 */
@Composable
public fun ComponentBase.td(
    colspan: Int? = null, rowspan: Int? = null, className: String? = null, content: @Composable Td.() -> Unit = {}
): Td {
    val component = remember { Td(colspan, rowspan, className, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(colspan) { updateProperty(Td::colspan, it) }
        set(rowspan) { updateProperty(Td::rowspan, it) }
        set(className) { updateProperty(Td::className, it) }
    }, content)
    return component
}
