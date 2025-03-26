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
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.core.SafeDomFactory
import dev.kilua.html.helpers.*
import dev.kilua.utils.cast
import dev.kilua.utils.isDom
import dev.kilua.utils.nativeListOf
import dev.kilua.utils.renderAsCssStyle
import dev.kilua.utils.renderAsHtmlAttributes
import dev.kilua.utils.unsafeCast
import web.html.HTMLElement

/**
 * Base interface for all HTML tags components.
 */
public interface ITag<E : HTMLElement> : IComponent, TagBaseFun, TagAttrsFun, TagStyleFun, TagEvents, TagDnd {
    /**
     * The render configuration of the current component.
     */
    public override val renderConfig: RenderConfig

    /**
     * The DOM element of the current component.
     */
    public val element: E

    /**
     * Makes the element focused.
     */
    public fun focus()

    /**
     * Makes the element blur.
     */
    public fun blur()

    /**
     * Use the modifier for the current tag.
     */
    @Composable
    public operator fun ModifierBase.unaryPlus() {
        this.useOn(this@ITag)
    }
}

/**
 * Base class for all HTML tags components.
 */
public open class Tag<E : HTMLElement>(
    protected val tagName: String,
    className: String? = null,
    id: String? = null,
    protected val namespace: String? = null,
    protected val renderNamespaceToString: Boolean = false,
    renderConfig: RenderConfig = RenderConfig.Default,
    protected val tagAttrs: TagAttrsDelegate<E> = TagAttrsDelegateImpl(!renderConfig.isDom || !isDom),
    protected val tagStyle: TagStyleDelegate<E> = TagStyleDelegateImpl(!renderConfig.isDom || !isDom),
    protected val tagEvents: TagEventsDelegate<E> = TagEventsDelegateImpl(!renderConfig.isDom || !isDom),
    protected val tagDnd: TagDndDelegate<E> = TagDndDelegateImpl(!renderConfig.isDom || !isDom)
) :
    ComponentBase(SafeDomFactory.createElement(tagName, namespace), renderConfig),
    TagAttrs by tagAttrs, TagStyle by tagStyle, TagEvents by tagEvents, TagDnd by tagDnd, ITag<E> {

    /**
     * A list of properties rendered as html attributes for the current component.
     */
    protected val htmlPropertyList: List<String> by lazy { buildPropertyList(::buildHtmlPropertyList) }

    /**
     * An internal list of CSS classes for the current component.
     */
    protected val internalCssClasses: MutableList<String> by lazy { nativeListOf() }

    /**
     * An internal CSS class for the current component.
     */
    protected var internalClassName: String? = null

    /**
     * The DOM element of the current component.
     */
    public override val element: E by lazy {
        if (renderConfig.isDom) node.unsafeCast<E>() else {
            error("Can't use DOM element with the current render configuration")
        }
    }

    /**
     * The CSS class of the current component.
     */
    public override var className: String? by updatingProperty(className) {
        updateElementClassList()
    }

    /**
     * Sets the CSS class of the current component.
     */
    @Composable
    public override fun className(className: String?): Unit = composableProperty("className", {
        this.className = null
    }) {
        this.className = className
    }

    /**
     * The ID attribute of the current component.
     */
    public override var id: String? by updatingProperty(id) {
        if (it != null) {
            element.id = it
        } else {
            element.removeAttribute("id")
        }
    }

    /**
     * Sets the ID attribute of the current component.
     */
    @Composable
    public override fun id(id: String?): Unit = composableProperty("id", {
        this.id = null
    }) {
        this.id = id
    }

    override var visible: Boolean = true
        set(value) {
            field = value
            display = if (visible) null else Display.None
        }

    /**
     * Sets the visibility of the current component.
     */
    @Composable
    public override fun visible(visible: Boolean): Unit = composableProperty("visible", {
        this.visible = true
    }) {
        this.visible = visible
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
        @Suppress("LeakingThis")
        val elementNullable = if (renderConfig.isDom) element else null
        tagAttrs.elementWithAttrs(elementNullable)
        tagStyle.elementWithStyle(elementNullable)
        tagEvents.elementWithEvents(elementNullable)
        @Suppress("LeakingThis")
        tagDnd.tagWithDnd(this)
        @Suppress("LeakingThis")
        initElementClassList()
        if (renderConfig.isDom && id != null) {
            @Suppress("LeakingThis")
            element.id = id
        }
    }

    /**
     * Initializes the CSS class of the DOM element of the current component.
     */
    protected open fun initElementClassList() {
        if (renderConfig.isDom) {
            val internalClassNameLoc = internalClassName
            val classNameLoc = className
            if (internalClassNameLoc != null && classNameLoc != null) {
                element.className = "$internalClassNameLoc $classNameLoc"
            } else if (classNameLoc != null) {
                element.className = classNameLoc
            } else if (internalClassNameLoc != null) {
                element.className = internalClassNameLoc
            }
        }
    }

    /**
     * Updates the CSS class of the DOM element of the current component.
     */
    protected open fun updateElementClassList() {
        if (renderConfig.isDom) {
            val internalClassNameLoc = internalClassName
            val classNameLoc = className
            if (internalClassNameLoc != null && classNameLoc != null) {
                element.className = "$internalClassNameLoc $classNameLoc"
            } else if (classNameLoc != null) {
                element.className = classNameLoc
            } else if (internalClassNameLoc != null) {
                element.className = internalClassNameLoc
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
            if (namespace != null && renderNamespaceToString) builder.append(" xmlns=\"$namespace\"")
            if (internalClassName != null && className != null) {
                builder.append(" class=\"$internalClassName $className\"")
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
            val filteredAttributesMap = tagAttrs.attributesMap.filterKeys { it !in htmlProperties }
            if (filteredAttributesMap.isNotEmpty()) {
                builder.append(" ${filteredAttributesMap.renderAsHtmlAttributes()}")
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
    public override fun focus() {
        if (renderConfig.isDom) element.focus()
    }

    /**
     * Makes the element blur.
     */
    public override fun blur() {
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
 * Creates a [Tag] component with given DOM element type, returning a reference.
 *
 * @param tagName the name of the HTML tag
 * @param className the CSS class name
 * @param id the ID attribute of the HTML tag
 * @param namespace the namespace of the HTML tag
 * @param content the content of the component
 * @return the [Tag] component
 */
@Composable
public fun <E : HTMLElement> IComponent.tagRef(
    tagName: String,
    className: String? = null,
    id: String? = null,
    namespace: String? = null,
    content: @Composable ITag<E>.() -> Unit = {}
): Tag<E> {
    return key(tagName, namespace) {
        val component = remember { Tag<E>(tagName, className, id, namespace, renderConfig = renderConfig) }
        ComponentNode(component, {
            set(className) { updateProperty(Tag<HTMLElement>::className, it) }
            set(id) { updateProperty(Tag<HTMLElement>::id, it) }
        }, content)
        component
    }
}

/**
 * Creates a [Tag] component with HTMLElement type, returning a reference.
 *
 * @param tagName the name of the HTML tag
 * @param className the CSS class name
 * @param id the ID attribute of the HTML tag
 * @param namespace the namespace of the HTML tag
 * @param content the content of the component
 * @return the [Tag] component
 */
@Composable
public fun IComponent.tagRef(
    tagName: String,
    className: String? = null,
    id: String? = null,
    namespace: String? = null,
    content: @Composable ITag<HTMLElement>.() -> Unit = {}
): Tag<HTMLElement> {
    return tagRef<HTMLElement>(tagName, className, id, namespace, content)
}


/**
 * Creates a [Tag] component with given DOM element type.
 *
 * @param tagName the name of the HTML tag
 * @param className the CSS class name
 * @param id the ID attribute of the HTML tag
 * @param namespace the namespace of the HTML tag
 * @param content the content of the component
 */
@Composable
public fun <E : HTMLElement> IComponent.tag(
    tagName: String,
    className: String? = null,
    id: String? = null,
    namespace: String? = null,
    content: @Composable ITag<E>.() -> Unit = {}
) {
    key(tagName, namespace) {
        val component = remember { Tag<E>(tagName, className, id, namespace, renderConfig = renderConfig) }
        ComponentNode(component, {
            set(className) { updateProperty(Tag<HTMLElement>::className, it) }
            set(id) { updateProperty(Tag<HTMLElement>::id, it) }
        }, content)
    }
}

/**
 * Creates a [Tag] component with HTMLElement type.
 *
 * @param tagName the name of the HTML tag
 * @param className the CSS class name
 * @param id the ID attribute of the HTML tag
 * @param namespace the namespace of the HTML tag
 * @param content the content of the component
 */
@Composable
public fun IComponent.tag(
    tagName: String,
    className: String? = null,
    id: String? = null,
    namespace: String? = null,
    content: @Composable ITag<HTMLElement>.() -> Unit = {}
) {
    tag<HTMLElement>(tagName, className, id, namespace, content)
}
