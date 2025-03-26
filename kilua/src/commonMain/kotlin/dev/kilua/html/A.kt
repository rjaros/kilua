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
import dev.kilua.utils.rem
import web.html.HTMLAnchorElement

/**
 * HTML A component.
 */
public interface IA : ITag<HTMLAnchorElement> {
    /**
     * The URL of the link.
     */
    public val href: String?

    /**
     * Set the URL of the link.
     */
    @Composable
    public fun href(href: String?)

    /**
     * The target of the link.
     */
    public val target: String?

    /**
     * Set the target of the link.
     */
    @Composable
    public fun target(target: String?)

    /**
     * The download attribute of the link.
     */
    public val download: String?

    /**
     * Set the download attribute of the link.
     */
    @Composable
    public fun download(download: String?)
}

/**
 * HTML A component.
 */
public open class A(
    href: String? = null,
    target: String? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLAnchorElement>("a", className, id, renderConfig = renderConfig), IA {

    /**
     * The URL of the link.
     */
    public override var href: String? by updatingProperty(href) {
        if (it != null) {
            element.href = it
        } else {
            element.removeAttribute("href")
        }
    }

    /**
     * Set the URL of the link.
     */
    @Composable
    public override fun href(href: String?): Unit = composableProperty("href", {
        this.href = null
    }) {
        this.href = href
    }

    /**
     * The target of the link.
     */
    public override var target: String? by updatingProperty(target) {
        if (it != null) {
            element.setAttribute("target", it)
        } else {
            element.removeAttribute("target")
        }
    }

    /**
     * Set the target of the link.
     */
    @Composable
    public override fun target(target: String?): Unit = composableProperty("target", {
        this.target = null
    }) {
        this.target = target
    }

    /**
     * The download attribute of the link.
     */
    public override var download: String? by updatingProperty {
        if (it != null) {
            element.download = it
        } else {
            element.removeAttribute("download")
        }
    }

    /**
     * Set the download attribute of the link.
     */
    @Composable
    public override fun download(download: String?): Unit = composableProperty("download", {
        this.download = null
    }) {
        this.download = download
    }

    init {
        if (renderConfig.isDom) {
            if (href != null) {
                @Suppress("LeakingThis")
                element.href = href
            }
            if (target != null) {
                @Suppress("LeakingThis")
                element.setAttribute("target", target)
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::href, ::target, ::download)
    }

}

/**
 * Creates a [A] component, returning a reference.
 *
 * @param href the link URL
 * @param target the link target
 * @param className the CSS class name
 * @param id the ID attribute of the link
 * @param content the content of the component
 * @return the [A] component
 */
@Composable
private fun IComponent.aRef(
    href: String? = null,
    target: String? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IA.() -> Unit = {}
): A {
    val component = remember { A(href, target, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(href) { updateProperty(A::href, it) }
        set(target) { updateProperty(A::target, it) }
        set(className) { updateProperty(A::className, it) }
        set(id) { updateProperty(A::id, it) }
    }, content)
    return component
}


/**
 * Creates a [A] component with a given label and icon, returning a reference.
 *
 * @param href the link URL
 * @param label the link label
 * @param icon the link icon
 * @param target the link target
 * @param className the CSS class name
 * @param id the ID attribute of the link
 * @param content the content of the component
 * @return the [A] component
 */
@Composable
public fun IComponent.aRef(
    href: String? = null,
    label: String? = null,
    icon: String? = null,
    target: String? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IA.() -> Unit = {}
): A {
    val iconClassName = if (label != null && icon != null) {
        className % "icon-link"
    } else className
    return aRef(href, target, iconClassName, id) {
        atom(label, icon)
        content()
    }
}

/**
 * Creates a [A] component.
 *
 * @param href the link URL
 * @param target the link target
 * @param className the CSS class name
 * @param id the ID attribute of the link
 * @param content the content of the component
 */
@Composable
private fun IComponent.a(
    href: String? = null,
    target: String? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IA.() -> Unit = {}
) {
    val component = remember { A(href, target, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(href) { updateProperty(A::href, it) }
        set(target) { updateProperty(A::target, it) }
        set(className) { updateProperty(A::className, it) }
        set(id) { updateProperty(A::id, it) }
    }, content)
}


/**
 * Creates a [A] component with a given label and icon.
 *
 * @param href the link URL
 * @param label the link label
 * @param icon the link icon
 * @param target the link target
 * @param className the CSS class name
 * @param id the ID attribute of the link
 * @param content the content of the component
 */
@Composable
public fun IComponent.a(
    href: String? = null,
    label: String? = null,
    icon: String? = null,
    target: String? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IA.() -> Unit = {}
) {
    val iconClassName = if (label != null && icon != null) {
        className % "icon-link"
    } else className
    a(href, target, iconClassName, id) {
        atom(label, icon)
        content()
    }
}
