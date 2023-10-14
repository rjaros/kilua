/*
 * Copyright (c) 2023-present Robert Jaros
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
import dev.kilua.html.helpers.TagDnd
import dev.kilua.html.helpers.TagDndDelegate
import dev.kilua.html.helpers.TagEvents
import dev.kilua.html.helpers.TagEventsDelegate
import dev.kilua.html.helpers.TagStyle
import dev.kilua.html.helpers.TagStyleDelegate
import dev.kilua.html.helpers.buildPropertyList
import dev.kilua.utils.isDom
import dev.kilua.utils.nativeListOf
import dev.kilua.utils.nativeMapOf
import dev.kilua.utils.renderAsCssStyle
import dev.kilua.utils.renderAsHtmlAttributes
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import kotlin.reflect.KProperty

public open class Tag<E : HTMLElement>(
    protected val tagName: String,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val attributes: MutableMap<String, Any> = nativeMapOf(),
    protected val styles: MutableMap<String, Any> = nativeMapOf(),
    protected val events: MutableMap<String, MutableMap<String, (Event) -> Unit>> = nativeMapOf()
) :
    ComponentBase(SafeDomFactory.createElement(tagName, renderConfig), renderConfig),
    TagAttrs<E> by TagAttrsDelegate(!renderConfig.isDom || !isDom, attributes),
    TagStyle<E> by TagStyleDelegate(!renderConfig.isDom || !isDom, styles),
    TagEvents<E> by TagEventsDelegate(!renderConfig.isDom || !isDom, events),
    TagDnd<E> by TagDndDelegate(!renderConfig.isDom || !isDom) {

    protected val htmlPropertyList: List<KProperty<*>> by lazy { buildPropertyList(::buildHtmlPropertyList) }

    protected val internalCssClasses: MutableList<String> = nativeListOf()
    protected var internalClassName: String? = null

    public open val element: E
        get() = node?.unsafeCast<E>()
            ?: throw IllegalStateException("Can't use DOM element with the current render configuration")

    public open val elementNullable: E? = node?.unsafeCast<E>()

    public open val elementAvailable: Boolean = node != null

    protected val skipUpdate: Boolean = node == null

    public open var className: String? by managedProperty(className, skipUpdate) {
        updateElementClassList(internalClassName, it)
    }

    override var visible: Boolean = true
        set(value) {
            field = value
            display = if (visible) null else Display.None
        }

    public open var label: String?
        get() = children.firstOrNull()?.let { it as? TextNode }?.text
        set(value) {
            children.firstOrNull()?.let { it as? TextNode }?.text = value ?: ""
        }

    init {
        @Suppress("LeakingThis")
        elementWithAttrs(node?.unsafeCast<E>())
        @Suppress("LeakingThis")
        elementWithStyle(node?.unsafeCast<E>())
        @Suppress("LeakingThis")
        elementWithEvents(node?.unsafeCast<E>())
        @Suppress("LeakingThis")
        tagWithDnd(this)
        @Suppress("LeakingThis")
        elementNullable?.let {
            if (className != null) {
                it.className = className
            }
        }
    }

    protected open fun updateElementClassList(internalClassName: String?, className: String?) {
        if (internalClassName != null && className != null) {
            element.className = "$internalClassName $className"
        } else if (className != null) {
            element.className = className
        } else if (internalClassName != null) {
            element.className = internalClassName
        } else {
            val classList = element.classList
            while (classList.length > 0) {
                classList.remove(classList.item(0).toString())
            }
        }
    }

    /**
     * Builds a list of properties rendered as html attributes for the current component with a delegated PropertyListBuilder.
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
            val htmlPropertis = htmlPropertyList.mapNotNull { prop ->
                propertyValues[prop.name]?.let { prop.name to it }
            }.toMap()
            if (htmlPropertis.isNotEmpty()) {
                builder.append(" ${htmlPropertis.renderAsHtmlAttributes()}")
            }
            if (attributes.isNotEmpty()) {
                builder.append(" ${attributes.renderAsHtmlAttributes()}")
            }
            if (styles.isNotEmpty()) {
                builder.append(" style=\"${styles.renderAsCssStyle()}\"")
            }
            builder.append(">")
            children.forEach {
                it.renderToStringBuilder(builder)
            }
            builder.append("</$tagName>")
        }
    }
}

@Composable
public fun <E : HTMLElement> ComponentBase.tag(
    tagName: String,
    className: String? = null,
    content: @Composable Tag<E>.() -> Unit = {}
): Tag<E> {
    return key(tagName) {
        val component = remember { Tag<E>(tagName, className, renderConfig) }
        DisposableEffect(component.componentId) {
            component.onInsert()
            onDispose {
                component.onRemove()
            }
        }
        ComponentNode(component, {
            set(className) { updateManagedProperty(Tag<HTMLElement>::className, it) }
        }, content)
        component
    }
}

@Composable
public fun ComponentBase.tag(
    tagName: String,
    className: String? = null,
    content: @Composable Tag<HTMLElement>.() -> Unit = {}
): Tag<HTMLElement> {
    return tag<HTMLElement>(tagName, className, content)
}
