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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.utils.toKebabCase
import org.w3c.dom.HTMLTableCellElement

/**
 * Table header scopes.
 */
public enum class ThScope {
    Row,
    Col,
    Rowgroup,
    Colgroup;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * HTML Th component.
 */
public open class Th(
    colspan: Int? = null,
    rowspan: Int? = null,
    scope: ThScope? = null,
    className: String? = null, renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLTableCellElement>("th", className, renderConfig) {

    /**
     * The number of columns the cell extends.
     */
    public open var colspan: Int? by updatingProperty(colspan, skipUpdate) {
        if (it != null) {
            element.colSpan = it
        } else {
            element.removeAttribute("colspan")
        }
    }

    /**
     * The number of rows the cell extends.
     */
    public open var rowspan: Int? by updatingProperty(rowspan, skipUpdate) {
        if (it != null) {
            element.rowSpan = it
        } else {
            element.removeAttribute("rowspan")
        }
    }

    /**
     * The cells that the header element relates to.
     */
    public open var scope: ThScope? by updatingProperty(scope, skipUpdate) {
        if (it != null) {
            element.scope = it.value
        } else {
            element.removeAttribute("scope")
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
            if (scope != null) {
                element.scope = scope.value
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::colspan, ::rowspan, ::scope)
    }
}

/**
 * Creates a [Th] component.
 *
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [Th] component
 */
@Composable
public fun ComponentBase.th(
    colspan: Int? = null, rowspan: Int? = null,
    scope: ThScope? = null, className: String? = null, content: @Composable Th.() -> Unit = {}
): Th {
    val component = remember { Th(colspan, rowspan, scope, className, renderConfig) }
    ComponentNode(component, {
        set(colspan) { updateProperty(Th::colspan, it) }
        set(rowspan) { updateProperty(Th::rowspan, it) }
        set(scope) { updateProperty(Th::scope, it) }
        set(className) { updateProperty(Th::className, it) }
    }, content)
    return component
}
