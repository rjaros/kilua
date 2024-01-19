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
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.core.SafeDomFactory
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.html.helpers.TagAttrs
import dev.kilua.html.helpers.TagAttrsDelegate
import dev.kilua.html.helpers.TagAttrsDelegateImpl
import dev.kilua.html.helpers.TagDnd
import dev.kilua.html.helpers.TagDndDelegate
import dev.kilua.html.helpers.TagDndDelegateImpl
import dev.kilua.html.helpers.TagEvents
import dev.kilua.html.helpers.TagEventsDelegate
import dev.kilua.html.helpers.TagEventsDelegateImpl
import dev.kilua.html.helpers.TagStyle
import dev.kilua.html.helpers.TagStyleDelegate
import dev.kilua.html.helpers.TagStyleDelegateImpl
import dev.kilua.html.helpers.buildPropertyList
import dev.kilua.utils.cast
import dev.kilua.utils.isDom
import dev.kilua.utils.nativeListOf
import dev.kilua.utils.renderAsCssStyle
import dev.kilua.utils.renderAsHtmlAttributes
import web.dom.HTMLElement

/**
 * Base class for all HTML tags components.
 */
public open class Tag<E : HTMLElement>(
    protected val tagName: String,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val tagAttrs: TagAttrsDelegate<E> = TagAttrsDelegateImpl(!renderConfig.isDom || !isDom),
    protected val tagStyle: TagStyleDelegate<E> = TagStyleDelegateImpl(!renderConfig.isDom || !isDom),
    protected val tagEvents: TagEventsDelegate<E> = TagEventsDelegateImpl(!renderConfig.isDom || !isDom),
    protected val tagDnd: TagDndDelegate<E> = TagDndDelegateImpl(!renderConfig.isDom || !isDom)
) :
    ComponentBase(SafeDomFactory.createElement(tagName), renderConfig),
    TagAttrs<E> by tagAttrs, TagStyle<E> by tagStyle, TagEvents<E> by tagEvents, TagDnd<E> by tagDnd {

    /**
     * A list of properties rendered as html attributes for the current component.
     */
    protected val htmlPropertyList: List<String> by lazy { buildPropertyList(::buildHtmlPropertyList) }

    /**
     * An internal list of CSS classes for the current component.
     */
    protected val internalCssClasses: MutableList<String> = nativeListOf()

    /**
     * An internal CSS class for the current component.
     */
    protected var internalClassName: String? = null

    /**
     * The DOM element of the current component.
     */
    public val element: E by lazy {
        if (renderConfig.isDom) node.cast<E>() else {
            error("Can't use DOM element with the current render configuration")
        }
    }

    /**
     * Whether to skip updating the DOM element of the current component.
     */
    protected val skipUpdate: Boolean = !renderConfig.isDom

    /**
     * The CSS class of the current component.
     */
    public open var className: String? by updatingProperty(className, skipUpdate) {
        updateElementClassList()
    }

    /**
     * The ID attribute of the current component.
     */
    public open var id: String? by updatingProperty(id, skipUpdate) {
        if (it != null) {
            element.id = it
        } else {
            element.removeAttribute("id")
        }
    }

    override var visible: Boolean = true
        set(value) {
            field = value
            display = if (visible) null else Display.None
        }

    /**
     * The text content of the current component or null if the first child is not a TextNode.
     */
    public open var textContent: String?
        get() = children.find { it is TextNode }?.cast<TextNode>()?.data
        set(value) {
            children.find { it is TextNode }?.cast<TextNode>()?.data = value ?: ""
        }

    init {
        val elementNullable = if (renderConfig.isDom) element else null
        tagAttrs.elementWithAttrs(elementNullable)
        tagStyle.elementWithStyle(elementNullable)
        tagEvents.elementWithEvents(elementNullable)
        @Suppress("LeakingThis")
        tagDnd.tagWithDnd(this)
        @Suppress("LeakingThis")
        updateElementClassList()
        if (renderConfig.isDom && id != null) {
            element.id = id
        }
    }

    /**
     * Updates the CSS class of the DOM element of the current component.
     */
    protected open fun updateElementClassList() {
        if (renderConfig.isDom) {
            if (internalClassName != null && className != null) {
                element.className = "$internalClassName $className"
            } else if (className != null) {
                element.className = className!!
            } else if (internalClassName != null) {
                element.className = internalClassName!!
            } else {
                element.removeAttribute("class")
            }
        }
    }

    /**
     * Builds a list of properties rendered as html attributes for the current component with
     * a delegated PropertyListBuilder.
     * @param propertyListBuilder a delegated builder
     */
    protected open fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        // nothing to do
    }

    override fun renderToStringBuilder(builder: StringBuilder) {
        if (visible) {
            builder.append("<$tagName")
            if (internalClassName != null && className != null) {
                builder.append(" class=\"$internalCssClasses $className\"")
            } else if (className != null) {
                builder.append(" class=\"$className\"")
            } else if (internalClassName != null) {
                builder.append(" class=\"$internalClassName\"")
            }
            if (id != null) builder.append(" id=\"$id\"")
            val htmlProperties = htmlPropertyList.mapNotNull { prop ->
                propertyValues[prop]?.let { prop to it }
            }.toMap()
            if (htmlProperties.isNotEmpty()) {
                builder.append(" ${htmlProperties.renderAsHtmlAttributes()}")
            }
            if (tagAttrs.attributesMap.isNotEmpty()) {
                builder.append(" ${tagAttrs.attributesMap.renderAsHtmlAttributes()}")
            }
            if (tagStyle.stylesMap.isNotEmpty()) {
                builder.append(" style=\"${tagStyle.stylesMap.renderAsCssStyle()}\"")
            }
            builder.append(">")
            if (!voidTags.contains(tagName)) {
                children.forEach {
                    it.renderToStringBuilder(builder)
                }
                builder.append("</$tagName>")
            }
        }
    }

    /**
     * Makes the element focused.
     */
    public open fun focus() {
        if (renderConfig.isDom) element.focus()
    }

    /**
     * Makes the element blur.
     */
    public open fun blur() {
        if (renderConfig.isDom) element.blur()
    }

    public companion object {
        protected val voidTags: Set<String> = setOf(
            "area", "base", "br", "col", "embed", "hr", "img", "input", "link", "meta", "param",
            "source", "track", "wbr"
        )
    }
}

/**
 * Creates a [Tag] component with given DOM element type.
 *
 * @param tagName the name of the HTML tag
 * @param className the CSS class name
 * @param id the ID attribute of the HTML tag
 * @param content the content of the component
 * @return the [Tag] component
 */
@Composable
public fun <E : HTMLElement> ComponentBase.tag(
    tagName: String,
    className: String? = null,
    id: String? = null,
    content: @Composable Tag<E>.() -> Unit = {}
): Tag<E> {
    return key(tagName) {
        val component = remember { Tag<E>(tagName, className, id, renderConfig = renderConfig) }
        ComponentNode(component, {
            set(className) { updateProperty(Tag<HTMLElement>::className, it) }
            set(id) { updateProperty(Tag<HTMLElement>::id, it) }
        }, content)
        component
    }
}

/**
 * Creates a [Tag] component with HTMLElement type.
 *
 * @param tagName the name of the HTML tag
 * @param className the CSS class name
 * @param id the ID attribute of the HTML tag
 * @param content the content of the component
 * @return the [Tag] component
 */
@Composable
public fun ComponentBase.tag(
    tagName: String,
    className: String? = null,
    id: String? = null,
    content: @Composable Tag<HTMLElement>.() -> Unit = {}
): Tag<HTMLElement> {
    return tag<HTMLElement>(tagName, className, id, content)
}
