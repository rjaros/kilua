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
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.compose.ComponentScope
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.core.SafeDomFactory
import dev.kilua.html.delegates.TagAttrs
import dev.kilua.html.delegates.TagAttrsDelegate
import dev.kilua.html.delegates.TagStyle
import dev.kilua.html.delegates.TagStyleDelegate
import dev.kilua.utils.AbortController
import dev.kilua.utils.NativeMap
import dev.kilua.utils.buildAddEventListenerOptions
import dev.kilua.utils.isDom
import dev.kilua.utils.nativeMapOf
import dev.kilua.utils.renderAsCssStyle
import dev.kilua.utils.renderAsHtmlAttributes
import org.w3c.dom.HTMLElement

public open class Tag<E : HTMLElement> private constructor(
    protected val tagName: String,
    className: String? = null,
    renderConfig: RenderConfig,
    protected val attributes: NativeMap<Any>,
    protected val styles: NativeMap<String>
) :
    ComponentBase(SafeDomFactory.createElement(tagName, renderConfig), renderConfig), ComponentScope<E>,
    TagAttrs<E> by TagAttrsDelegate(!renderConfig.isDom() || !isDom(), attributes),
    TagStyle<E> by TagStyleDelegate(!renderConfig.isDom() || !isDom(), styles) {

    public constructor(
        tagName: String,
        className: String? = null,
        renderConfig: RenderConfig = DefaultRenderConfig()
    ) : this(
        tagName,
        className,
        renderConfig,
        nativeMapOf(),
        nativeMapOf()
    )

    public open val element: E
        get() = node?.unsafeCast<E>()
            ?: throw IllegalStateException("Can't use DOM element with the current render configuration")

    override val DisposableEffectScope.element: E
        get() = this@Tag.element

    public open val elementAvailable: Boolean = node != null

    init {
        @Suppress("LeakingThis")
        elementWithAttrs(node?.unsafeCast<E>())
        @Suppress("LeakingThis")
        elementWithStyle(node?.unsafeCast<E>())
    }

    protected val skipUpdates: Boolean = node == null

    public open var className: String? by managedProperty(className, skipUpdates) {
        if (it != null) {
            element.className = it
        } else {
            val classList = element.classList;
            while (classList.length > 0) {
                classList.remove(classList.item(0).toString())
            }
        }
    }

    override var visible: Boolean by unmanagedProperty(true, skipUpdates) {
        if (it) {
            element.style.removeProperty("display")
        } else {
            element.style.display = "none"
        }
    }

    public open var label: String?
        get() = children.firstOrNull()?.let { it as? TextNode }?.text
        set(value) {
            children.firstOrNull()?.let { it as? TextNode }?.text = value ?: ""
        }


    private var abortController: AbortController? = null

    public open var onClick: (() -> Unit)? = null
        set(value) {
            field = value
            if (value != null) {
                abortController?.abort()
                abortController = AbortController()
                node?.addEventListener("click", { value() }, buildAddEventListenerOptions(abortController!!.signal))
            }
        }

    override fun renderToStringBuilder(builder: StringBuilder) {
        if (visible) {
            builder.append("<$tagName")
            if (attributes.isNotEmpty()) {
                builder.append(" ${attributes.renderAsHtmlAttributes()}")
            }
            className?.let {
                builder.append(" class=\"$it\"")
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
        val tag by remember { mutableStateOf(Tag<E>(tagName, className, renderConfig)) }
        ComponentNode(tag, {
            set(className) { updateManagedProperty(Tag<HTMLElement>::className, it) }
        }, content)
        tag
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
