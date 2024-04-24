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
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.utils.toKebabCase
import web.dom.HTMLTableCellElement

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

/*
 * HTML Th component.
 */
public interface ITh : ITag<HTMLTableCellElement> {
    /**
     * The number of columns the cell extends.
     */
    public val colspan: Int?

    /**
     * Set the number of columns the cell extends.
     */
    @Composable
    public fun colspan(colspan: Int?)

    /**
     * The number of rows the cell extends.
     */
    public val rowspan: Int?

    /**
     * Set the number of rows the cell extends.
     */
    @Composable
    public fun rowspan(rowspan: Int?)

    /**
     * The cells that the header element relates to.
     */
    public val scope: ThScope?

    /**
     * Set the cells that the header element relates to.
     */
    @Composable
    public fun scope(scope: ThScope?)
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
    Tag<HTMLTableCellElement>("th", className, renderConfig = renderConfig), ITh {

    /**
     * The number of columns the cell extends.
     */
    public override var colspan: Int? by updatingProperty(colspan) {
        if (it != null) {
            element.colSpan = it
        } else {
            element.removeAttribute("colspan")
        }
    }

    /**
     * Set the number of columns the cell extends.
     */
    @Composable
    public override fun colspan(colspan: Int?): Unit = composableProperty("colspan", {
        this.colspan = null
    }) {
        this.colspan = colspan
    }

    /**
     * The number of rows the cell extends.
     */
    public override var rowspan: Int? by updatingProperty(rowspan) {
        if (it != null) {
            element.rowSpan = it
        } else {
            element.removeAttribute("rowspan")
        }
    }

    /**
     * Set the number of rows the cell extends.
     */
    @Composable
    public override fun rowspan(rowspan: Int?): Unit = composableProperty("rowspan", {
        this.rowspan = null
    }) {
        this.rowspan = rowspan
    }

    /**
     * The cells that the header element relates to.
     */
    public override var scope: ThScope? by updatingProperty(scope) {
        if (it != null) {
            element.scope = it.value
        } else {
            element.removeAttribute("scope")
        }
    }

    /**
     * Set the cells that the header element relates to.
     */
    @Composable
    public override fun scope(scope: ThScope?): Unit = composableProperty("scope", {
        this.scope = null
    }) {
        this.scope = scope
    }

    init {
        if (renderConfig.isDom) {
            if (colspan != null) {
                @Suppress("LeakingThis")
                element.colSpan = colspan
            }
            if (rowspan != null) {
                @Suppress("LeakingThis")
                element.rowSpan = rowspan
            }
            if (scope != null) {
                @Suppress("LeakingThis")
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
public fun IComponent.th(
    colspan: Int? = null, rowspan: Int? = null,
    scope: ThScope? = null, className: String? = null, content: @Composable ITh.() -> Unit = {}
): Th {
    val component = remember { Th(colspan, rowspan, scope, className, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(colspan) { updateProperty(Th::colspan, it) }
        set(rowspan) { updateProperty(Th::rowspan, it) }
        set(scope) { updateProperty(Th::scope, it) }
        set(className) { updateProperty(Th::className, it) }
    }, content)
    return component
}
