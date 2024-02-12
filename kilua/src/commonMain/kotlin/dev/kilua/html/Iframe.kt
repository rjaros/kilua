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
import dev.kilua.utils.toKebabCase
import web.dom.HTMLIFrameElement

/**
 * Iframe sandbox options.
 */
public enum class Sandbox {
    AllowForms,
    AllowPointerLock,
    AllowPopups,
    AllowSameOrigin,
    AllowScripts,
    AllowTopNavigation;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * HTML Iframe component.
 */
public open class Iframe(
    src: String? = null,
    srcdoc: String? = null,
    name: String? = null,
    iframeWidth: Int? = null,
    iframeHeight: Int? = null,
    sandbox: Set<Sandbox>? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLIFrameElement>("iframe", className, renderConfig = renderConfig) {

    /**
     * The URL of the page to embed.
     */
    public open var src: String? by updatingProperty(src) {
        if (it != null) {
            element.src = it
        } else {
            element.removeAttribute("src")
        }
    }

    /**
     * Inline HTML to embed.
     */
    public open var srcdoc: String? by updatingProperty(srcdoc) {
        if (it != null) {
            element.srcdoc = it
        } else {
            element.removeAttribute("srcdoc")
        }
    }

    /**
     * The name of the iframe.
     */
    public open var name: String? by updatingProperty(name) {
        if (it != null) {
            element.name = it
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * The width of the iframe.
     */
    public open var iframeWidth: Int? by updatingProperty(iframeWidth, name = "width") {
        if (it != null) {
            element.width = it.toString()
        } else {
            element.removeAttribute("width")
        }
    }

    /**
     * The height of the iframe.
     */
    public open var iframeHeight: Int? by updatingProperty(iframeHeight, name = "height") {
        if (it != null) {
            element.height = it.toString()
        } else {
            element.removeAttribute("height")
        }
    }

    /**
     * The sandbox options of the iframe.
     */
    public open var sandbox: Set<Sandbox>? by updatingProperty {
        setAttribute("sandbox", it?.joinToString(" ") { it.value })
    }

    init {
        if (renderConfig.isDom) {
            if (src != null) {
                element.src = src
            }
            if (srcdoc != null) {
                element.srcdoc = srcdoc
            }
            if (name != null) {
                element.name = name
            }
            if (iframeWidth != null) {
                element.width = iframeWidth.toString()
            }
            if (iframeHeight != null) {
                element.height = iframeHeight.toString()
            }
        }
        @Suppress("LeakingThis")
        setAttribute("sandbox", sandbox?.joinToString(" ") { it.value })
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::src, ::srcdoc, ::name)
        propertyListBuilder.addByName("width", "height")
    }
}

/**
 * Creates a [Iframe] component.
 *
 * @param src the URL of the page to embed
 * @param srcdoc the inline HTML to embed
 * @param name the name of the iframe
 * @param iframeWidth the width of the iframe
 * @param iframeHeight the height of the iframe
 * @param sandbox a set of sandbox options
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [Iframe] component
 */
@Composable
public fun ComponentBase.iframe(
    src: String? = null,
    srcdoc: String? = null,
    name: String? = null,
    iframeWidth: Int? = null,
    iframeHeight: Int? = null,
    sandbox: Set<Sandbox>? = null,
    className: String? = null, content: @Composable Iframe.() -> Unit = {}
): Iframe {
    val component = remember { Iframe(src, srcdoc, name, iframeWidth, iframeHeight, sandbox, className, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(src) { updateProperty(Iframe::src, it) }
        set(srcdoc) { updateProperty(Iframe::srcdoc, it) }
        set(name) { updateProperty(Iframe::name, it) }
        set(iframeWidth) { updateProperty("width", it) }
        set(iframeHeight) { updateProperty("height", it) }
        set(sandbox) { updateProperty(Iframe::sandbox, it) }
        set(className) { updateProperty(Iframe::className, it) }
    }, content)
    return component
}
