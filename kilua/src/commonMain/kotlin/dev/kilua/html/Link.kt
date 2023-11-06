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
import app.softwork.routingcompose.Router
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.helpers.PropertyListBuilder
import org.w3c.dom.HTMLAnchorElement

/**
 * HTML A component.
 */
public open class Link(
    href: String? = null,
    target: String? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLAnchorElement>("a", className, renderConfig) {

    /**
     * The URL of the link.
     */
    public open var href: String? by updatingProperty(href, skipUpdate) {
        if (it != null) {
            element.href = it
        } else {
            element.removeAttribute("href")
        }
    }

    /**
     * The target of the link.
     */
    public open var target: String? by updatingProperty(target, skipUpdate) {
        if (it != null) {
            element.target = it
        } else {
            element.removeAttribute("target")
        }
    }

    /**
     * The download attribute of the link.
     */
    public open var download: String? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.download = it
        } else {
            element.removeAttribute("download")
        }
    }

    init {
        if (renderConfig.isDom) {
            if (href != null) {
                element.href = href
            }
            if (target != null) {
                element.target = target
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::href, ::target, ::download)
    }

}

/**
 * Creates a [Link] component.
 *
 * @param href the link URL
 * @param label the link label
 * @param target the link target
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [Link] component
 */
@Composable
public fun ComponentBase.link(
    href: String? = null,
    label: String? = null,
    target: String? = null,
    className: String? = null,
    content: @Composable Link.() -> Unit = {}
): Link {
    val component = remember { Link(href, target, className, renderConfig) }
    ComponentNode(component, {
        set(href) { updateProperty(Link::href, it) }
        set(target) { updateProperty(Link::target, it) }
        set(className) { updateProperty(Link::className, it) }
    }) {
        if (label != null) {
            +label
        }
        content()
    }
    return component
}

/**
 * Creates a [Link] component with router support.
 *
 * @param href the link URL
 * @param label the link label
 * @param target the link target
 * @param hide hides the route from the browser history
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [Link] component
 */
@Composable
public fun ComponentBase.navLink(
    href: String? = null,
    label: String? = null,
    target: String? = null,
    hide: Boolean = false,
    className: String? = null,
    content: @Composable Link.() -> Unit = {}
): Link {
    return link(href, label, target, className) {
        if (href != null) {
            val router = Router.current
            onClick {
                router.navigate(href, hide)
                it.preventDefault()
            }
        }
        content()
    }
}
