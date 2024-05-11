/*
 * Copyright (c) 2024 Robert Jaros
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

package dev.kilua.svg

import androidx.compose.runtime.Composable
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.CssSize
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import web.dom.HTMLElement

/**
 * The SVG namespace.
 */
private const val SVG_NS = "http://www.w3.org/2000/svg"

/**
 * Base SVG tag.
 */
public open class SvgTag(
    tagName: String,
    className: String? = null,
    id: String? = null,
    renderNamespaceToString: Boolean = false,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Tag<HTMLElement>(tagName, className, id, SVG_NS, renderNamespaceToString, renderConfig), ISvgTag {

    override fun updateElementClassList() {
        if (renderConfig.isDom) {
            if (internalClassName != null && className != null) {
                element.setAttribute("class", "$internalClassName $className")
            } else if (className != null) {
                element.setAttribute("class", className!!)
            } else if (internalClassName != null) {
                element.setAttribute("class", internalClassName!!)
            } else {
                element.removeAttribute("class")
            }
        }
    }

    override var attributeName: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("attributeName", it)
        } else {
            element.removeAttribute("attributeName")
        }
    }

    @Composable
    override fun attributeName(attributeName: String?): Unit = composableProperty("attributeName", {
        this.attributeName = null
    }) {
        this.attributeName = attributeName
    }

    override var fill: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("fill", it)
        } else {
            element.removeAttribute("fill")
        }
    }

    @Composable
    override fun fill(fill: String?): Unit = composableProperty("fill", {
        this.fill = null
    }) {
        this.fill = fill
    }

    override var fillRule: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("fill-rule", it)
        } else {
            element.removeAttribute("fill-rule")
        }
    }

    @Composable
    override fun fillRule(fillRule: String?): Unit = composableProperty("fillRule", {
        this.fillRule = null
    }) {
        this.fillRule = fillRule
    }

    override var fillOpacity: Number? by updatingProperty {
        if (it != null) {
            element.setAttribute("fill-opacity", it.toString())
        } else {
            element.removeAttribute("fill-opacity")
        }
    }

    @Composable
    override fun fillOpacity(fillOpacity: Number?): Unit = composableProperty("fillOpacity", {
        this.fillOpacity = null
    }) {
        this.fillOpacity = fillOpacity
    }

    override var href: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("href", it)
        } else {
            element.removeAttribute("href")
        }
    }

    @Composable
    override fun href(href: String?): Unit = composableProperty("href", {
        this.href = null
    }) {
        this.href = href
    }

    override var viewBox: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("viewBox", it)
        } else {
            element.removeAttribute("viewBox")
        }
    }

    @Composable
    override fun viewBox(viewBox: String?): Unit = composableProperty("viewBox", {
        this.viewBox = null
    }) {
        this.viewBox = viewBox
    }

    override var transform: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("transform", it)
        } else {
            element.removeAttribute("transform")
        }
    }

    @Composable
    override fun transform(transform: String?): Unit = composableProperty("transform", {
        this.transform = null
    }) {
        this.transform = transform
    }

    override var d: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("d", it)
        } else {
            element.removeAttribute("d")
        }
    }

    @Composable
    override fun d(d: String?): Unit = composableProperty("d", {
        this.d = null
    }) {
        this.d = d
    }

    override var points: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("points", it)
        } else {
            element.removeAttribute("points")
        }
    }

    @Composable
    override fun points(points: String?): Unit = composableProperty("points", {
        this.points = null
    }) {
        this.points = points
    }

    override var cx: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("cx", it.toString())
        } else {
            element.removeAttribute("cx")
        }
    }

    @Composable
    override fun cx(cx: CssSize?): Unit = composableProperty("cx", {
        this.cx = null
    }) {
        this.cx = cx
    }

    override var cy: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("cy", it.toString())
        } else {
            element.removeAttribute("cy")
        }
    }

    @Composable
    override fun cy(cy: CssSize?): Unit = composableProperty("cy", {
        this.cy = null
    }) {
        this.cy = cy
    }

    override var r: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("r", it.toString())
        } else {
            element.removeAttribute("r")
        }
    }

    @Composable
    override fun r(r: CssSize?): Unit = composableProperty("r", {
        this.r = null
    }) {
        this.r = r
    }

    override var rx: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("rx", it.toString())
        } else {
            element.removeAttribute("rx")
        }
    }

    @Composable
    override fun rx(rx: CssSize?): Unit = composableProperty("rx", {
        this.rx = null
    }) {
        this.rx = rx
    }

    override var ry: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("ry", it.toString())
        } else {
            element.removeAttribute("ry")
        }
    }

    @Composable
    override fun ry(ry: CssSize?): Unit = composableProperty("ry", {
        this.ry = null
    }) {
        this.ry = ry
    }

    override var x: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("x", it.toString())
        } else {
            element.removeAttribute("x")
        }
    }

    @Composable
    override fun x(x: CssSize?): Unit = composableProperty("x", {
        this.x = null
    }) {
        this.x = x
    }

    override var y: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("y", it.toString())
        } else {
            element.removeAttribute("y")
        }
    }

    @Composable
    override fun y(y: CssSize?): Unit = composableProperty("y", {
        this.y = null
    }) {
        this.y = y
    }

    override var x1: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("x1", it.toString())
        } else {
            element.removeAttribute("x1")
        }
    }

    @Composable
    override fun x1(x1: CssSize?): Unit = composableProperty("x1", {
        this.x1 = null
    }) {
        this.x1 = x1
    }

    override var y1: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("y1", it.toString())
        } else {
            element.removeAttribute("y1")
        }
    }

    @Composable
    override fun y1(y1: CssSize?): Unit = composableProperty("y1", {
        this.y1 = null
    }) {
        this.y1 = y1
    }

    override var x2: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("x2", it.toString())
        } else {
            element.removeAttribute("x2")
        }
    }

    @Composable
    override fun x2(x2: CssSize?): Unit = composableProperty("x2", {
        this.x2 = null
    }) {
        this.x2 = x2
    }

    override var y2: CssSize? by updatingProperty {
        if (it != null) {
            element.setAttribute("y2", it.toString())
        } else {
            element.removeAttribute("y2")
        }
    }

    @Composable
    override fun y2(y2: CssSize?): Unit = composableProperty("y2", {
        this.y2 = null
    }) {
        this.y2 = y2
    }

    override var to: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("to", it)
        } else {
            element.removeAttribute("to")
        }
    }

    @Composable
    override fun to(to: String?): Unit = composableProperty("to", {
        this.to = null
    }) {
        this.to = to
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::attributeName, ::fill, ::fillRule, ::fillOpacity, ::href, ::viewBox, ::transform, ::d, ::points,
            ::cx, ::cy, ::r, ::rx, ::ry, ::x, ::y, ::x1, ::y1, ::x2, ::y2, ::to
        )
    }
}
