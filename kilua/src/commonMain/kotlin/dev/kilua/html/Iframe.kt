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
import dev.kilua.utils.toKebabCase
import dev.kilua.dom.api.HTMLIFrameElement

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
public interface IIframe : ITag<HTMLIFrameElement> {
    /**
     * The URL of the page to embed.
     */
    public val src: String?

    /**
     * Set the URL of the page to embed.
     */
    @Composable
    public fun src(value: String?)

    /**
     * Inline HTML to embed.
     */
    public val srcdoc: String?

    /**
     * Set the inline HTML to embed.
     */
    @Composable
    public fun srcdoc(value: String?)

    /**
     * The name of the iframe.
     */
    public val name: String?

    /**
     * Set the name of the iframe.
     */
    @Composable
    public fun name(value: String?)

    /**
     * The width of the iframe.
     */
    public val iframeWidth: Int?

    /**
     * Set the width of the iframe.
     */
    @Composable
    public fun iframeWidth(value: Int?)

    /**
     * The height of the iframe.
     */
    public val iframeHeight: Int?

    /**
     * Set the height of the iframe.
     */
    @Composable
    public fun iframeHeight(value: Int?)

    /**
     * The sandbox options of the iframe.
     */
    public val sandbox: Set<Sandbox>?

    /**
     * Set the sandbox options of the iframe.
     */
    @Composable
    public fun sandbox(value: Set<Sandbox>?)
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
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLIFrameElement>("iframe", className, id, renderConfig = renderConfig), IIframe {

    /**
     * The URL of the page to embed.
     */
    public override var src: String? by updatingProperty(src) {
        if (it != null) {
            element.src = it
        } else {
            element.removeAttribute("src")
        }
    }

    /**
     * Set the URL of the page to embed.
     */
    @Composable
    public override fun src(value: String?): Unit = composableProperty("src", {
        this.src = null
    }) {
        this.src = value
    }

    /**
     * Inline HTML to embed.
     */
    public override var srcdoc: String? by updatingProperty(srcdoc) {
        if (it != null) {
            element.srcdoc = it
        } else {
            element.removeAttribute("srcdoc")
        }
    }

    /**
     * Set the inline HTML to embed.
     */
    @Composable
    public override fun srcdoc(value: String?): Unit = composableProperty("srcdoc", {
        this.srcdoc = null
    }) {
        this.srcdoc = value
    }

    /**
     * The name of the iframe.
     */
    public override var name: String? by updatingProperty(name) {
        if (it != null) {
            element.name = it
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * Set the name of the iframe.
     */
    @Composable
    public override fun name(value: String?): Unit = composableProperty("name", {
        this.name = null
    }) {
        this.name = value
    }

    /**
     * The width of the iframe.
     */
    public override var iframeWidth: Int? by updatingProperty(iframeWidth, name = "width") {
        if (it != null) {
            element.width = it.toString()
        } else {
            element.removeAttribute("width")
        }
    }

    /**
     * Set the width of the iframe.
     */
    @Composable
    public override fun iframeWidth(value: Int?): Unit = composableProperty("width", {
        this.iframeWidth = null
    }) {
        this.iframeWidth = value
    }

    /**
     * The height of the iframe.
     */
    public override var iframeHeight: Int? by updatingProperty(iframeHeight, name = "height") {
        if (it != null) {
            element.height = it.toString()
        } else {
            element.removeAttribute("height")
        }
    }

    /**
     * Set the height of the iframe.
     */
    @Composable
    public override fun iframeHeight(value: Int?): Unit = composableProperty("height", {
        this.iframeHeight = null
    }) {
        this.iframeHeight = value
    }

    /**
     * The sandbox options of the iframe.
     */
    public override var sandbox: Set<Sandbox>? by updatingProperty {
        setAttribute("sandbox", it?.joinToString(" ") { it.value })
    }

    /**
     * Set the sandbox options of the iframe.
     */
    @Composable
    public override fun sandbox(value: Set<Sandbox>?): Unit = composableProperty("sandbox") {
        this.sandbox = value
    }

    init {
        if (renderConfig.isDom) {
            if (src != null) {
                @Suppress("LeakingThis")
                element.src = src
            }
            if (srcdoc != null) {
                @Suppress("LeakingThis")
                element.srcdoc = srcdoc
            }
            if (name != null) {
                @Suppress("LeakingThis")
                element.name = name
            }
            if (iframeWidth != null) {
                @Suppress("LeakingThis")
                element.width = iframeWidth.toString()
            }
            if (iframeHeight != null) {
                @Suppress("LeakingThis")
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
 * Creates a [Iframe] component, returning a reference.
 *
 * @param src the URL of the page to embed
 * @param srcdoc the inline HTML to embed
 * @param name the name of the iframe
 * @param iframeWidth the width of the iframe
 * @param iframeHeight the height of the iframe
 * @param sandbox a set of sandbox options
 * @param className the CSS class name
 * @param id the ID of the iframe
 * @param content the content of the component
 * @return the [Iframe] component
 */
@Composable
public fun IComponent.iframeRef(
    src: String? = null,
    srcdoc: String? = null,
    name: String? = null,
    iframeWidth: Int? = null,
    iframeHeight: Int? = null,
    sandbox: Set<Sandbox>? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IIframe.() -> Unit = {}
): Iframe {
    val component = remember {
        Iframe(
            src,
            srcdoc,
            name,
            iframeWidth,
            iframeHeight,
            sandbox,
            className,
            id,
            renderConfig = renderConfig
        )
    }
    ComponentNode(component, {
        set(src) { updateProperty(Iframe::src, it) }
        set(srcdoc) { updateProperty(Iframe::srcdoc, it) }
        set(name) { updateProperty(Iframe::name, it) }
        set(iframeWidth) { updateProperty("width", it) }
        set(iframeHeight) { updateProperty("height", it) }
        set(sandbox) { updateProperty(Iframe::sandbox, it) }
        set(className) { updateProperty(Iframe::className, it) }
        set(id) { updateProperty(Iframe::id, it) }
    }, content)
    return component
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
 * @param id the ID of the iframe
 * @param content the content of the component
 */
@Composable
public fun IComponent.iframe(
    src: String? = null,
    srcdoc: String? = null,
    name: String? = null,
    iframeWidth: Int? = null,
    iframeHeight: Int? = null,
    sandbox: Set<Sandbox>? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IIframe.() -> Unit = {}
) {
    val component = remember {
        Iframe(
            src,
            srcdoc,
            name,
            iframeWidth,
            iframeHeight,
            sandbox,
            className,
            id,
            renderConfig = renderConfig
        )
    }
    ComponentNode(component, {
        set(src) { updateProperty(Iframe::src, it) }
        set(srcdoc) { updateProperty(Iframe::srcdoc, it) }
        set(name) { updateProperty(Iframe::name, it) }
        set(iframeWidth) { updateProperty("width", it) }
        set(iframeHeight) { updateProperty("height", it) }
        set(sandbox) { updateProperty(Iframe::sandbox, it) }
        set(className) { updateProperty(Iframe::className, it) }
        set(id) { updateProperty(Iframe::id, it) }
    }, content)
}
