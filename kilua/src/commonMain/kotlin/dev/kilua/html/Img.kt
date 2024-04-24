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
import web.dom.HTMLImageElement

/**
 * HTML Img component.
 */
public interface IImg : ITag<HTMLImageElement> {
    /**
     * The source of the image.
     */
    public val src: String?

    /**
     * Set the source of the image.
     */
    @Composable
    public fun src(src: String?)


    /**
     * The alternative text of the image.
     */
    public val alt: String?

    /**
     * Set the alternative text of the image.
     */
    @Composable
    public fun alt(alt: String?)
}

/**
 * HTML Img component.
 */
public open class Img(
    src: String? = null,
    alt: String? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLImageElement>("img", className, renderConfig = renderConfig), IImg {

    /**
     * The source of the image.
     */
    public override var src: String? by updatingProperty(src) {
        if (it != null) {
            element.src = it
        } else {
            element.removeAttribute("src")
        }
    }

    /**
     * Set the source of the image.
     */
    @Composable
    public override fun src(src: String?): Unit = composableProperty("src", {
        this.src = null
    }) {
        this.src = src
    }

    /**
     * The alternative text of the image.
     */
    public override var alt: String? by updatingProperty(alt) {
        if (it != null) {
            element.alt = it
        } else {
            element.removeAttribute("alt")
        }
    }

    /**
     * Set the alternative text of the image.
     */
    @Composable
    public override fun alt(alt: String?): Unit = composableProperty("alt", {
        this.alt = null
    }) {
        this.alt = alt
    }

    init {
        if (renderConfig.isDom) {
            if (src != null) {
                @Suppress("LeakingThis")
                element.src = src
            }
            if (alt != null) {
                @Suppress("LeakingThis")
                element.alt = alt
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::src, ::alt)
    }

}

/**
 * Creates a [Img] component.
 *
 * @param src the source of the image
 * @param alt the alternative text of the image
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [Img] component
 */
@Composable
public fun IComponent.img(
    src: String? = null, alt: String? = null,
    className: String? = null, content: @Composable IImg.() -> Unit = {}
): Img {
    val component = remember { Img(src, alt, className, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(src) { updateProperty(Img::src, it) }
        set(alt) { updateProperty(Img::alt, it) }
        set(className) { updateProperty(Img::className, it) }
    }, content)
    return component
}
