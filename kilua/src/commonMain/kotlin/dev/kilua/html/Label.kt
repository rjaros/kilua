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
import dev.kilua.core.RenderConfig
import dev.kilua.html.helpers.PropertyListBuilder
import web.dom.HTMLLabelElement

/**
 * HTML Label component.
 */
public interface ILabel : ITag<HTMLLabelElement> {
    /**
     * The ID of the labeled element.
     */
    public val htmlFor: String?

    /**
     * Set the ID of the labeled element.
     */
    @Composable
    public fun htmlFor(htmlFor: String?)
}

/**
 * HTML Label component.
 */
public open class Label(
    htmlFor: String? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLLabelElement>("label", className, id, renderConfig = renderConfig), ILabel {

    /**
     * The ID of the labeled element.
     */
    public override var htmlFor: String? by updatingProperty(htmlFor, name = "for") {
        if (it != null) {
            element.htmlFor = it
        } else {
            element.removeAttribute("for")
        }
    }

    /**
     * Set the ID of the labeled element.
     */
    @Composable
    public override fun htmlFor(htmlFor: String?): Unit = composableProperty("htmlFor", {
        this.htmlFor = null
    }) {
        this.htmlFor = htmlFor
    }

    init {
        if (renderConfig.isDom) {
            if (htmlFor != null) {
                @Suppress("LeakingThis")
                element.htmlFor = htmlFor
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.addByName("for")
    }
}

/**
 * Creates a [Label] component, returning a reference.
 *
 * @param className the CSS class name
 * @param id the ID of the label
 * @param content the content of the component
 * @return the [Label] component
 */
@Composable
public fun IComponent.labelRef(
    htmlFor: String? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable ILabel.() -> Unit = {}
): Label {
    val component = remember { Label(htmlFor, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(htmlFor) { updateProperty("for", it) }
        set(className) { updateProperty(Label::className, it) }
        set(id) { updateProperty(Label::id, it) }
    }, content)
    return component
}

/**
 * Creates a [Label] component.
 *
 * @param className the CSS class name
 * @param id the ID of the label
 * @param content the content of the component
 */
@Composable
public fun IComponent.label(
    htmlFor: String? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable ILabel.() -> Unit = {}
) {
    val component = remember { Label(htmlFor, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(htmlFor) { updateProperty("for", it) }
        set(className) { updateProperty(Label::className, it) }
        set(id) { updateProperty(Label::id, it) }
    }, content)
}
