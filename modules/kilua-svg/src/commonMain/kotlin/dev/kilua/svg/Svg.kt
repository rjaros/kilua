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
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.html.CssSize
import dev.kilua.html.textNode
import dev.kilua.html.units

@Composable
private fun IComponent.svgTag(
    tagName: String,
    className: String? = null,
    id: String? = null,
    renderNamespaceToString: Boolean = false,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag {
    return key(tagName, renderNamespaceToString) {
        val component = remember { SvgTag(tagName, className, id, renderNamespaceToString, renderConfig) }
        ComponentNode(component, {
            set(className) { updateProperty(SvgTag::className, it) }
            set(id) { updateProperty(SvgTag::id, it) }
        }, content)
        component
    }
}

/**
 * SVG tag.
 */
@Composable
public fun IComponent.svg(
    viewBox: String? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("svg", className, id, true) {
    viewBox(viewBox)
    content()
}

/**
 * SVG "a" tag.
 */
@Composable
public fun ISvgTag.a(
    href: String,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("a", className, id) {
    href(href)
    content()
}

/**
 * SVG "circle" tag.
 */
@Composable
public fun ISvgTag.circle(
    cx: CssSize,
    cy: CssSize,
    r: CssSize,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("circle", className, id) {
    cx(cx)
    cy(cy)
    r(r)
    content()
}

/**
 * SVG "circle" tag with plain numer attributes.
 */
@Composable
public fun ISvgTag.circle(
    cx: Number,
    cy: Number,
    r: Number,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = circle(cx.units, cy.units, r.units, className, id, content)

/**
 * SVG "text" tag.
 */
@Composable
public fun ISvgTag.text(
    text: String,
    x: Number = 0,
    y: Number = 0,
    className: String? = null,
    id: String? = null,
    setup: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("text", className, id) {
    textNode(text)
    x(x.units)
    y(y.units)
    setup()
}

/**
 * SVG "view" tag.
 */
@Composable
public fun ISvgTag.view(
    id: String,
    viewBox: String,
    setup: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("view", null, id) {
    viewBox(viewBox)
    setup()
}

/**
 * SVG "rect" tag.
 */
@Composable
public fun ISvgTag.rect(
    x: CssSize,
    y: CssSize,
    width: CssSize,
    height: CssSize,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("rect", className, id) {
    x(x)
    y(y)
    width(width)
    height(height)
    content()
}

/**
 * SVG "rect" tag with plain number attributes.
 */
@Composable
public fun ISvgTag.rect(
    x: Number,
    y: Number,
    width: Number,
    height: Number,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = rect(x.units, y.units, width.units, height.units, className, id, content)

/**
 * SVG "ellipse" tag.
 */
@Composable
public fun ISvgTag.ellipse(
    cx: CssSize,
    cy: CssSize,
    rx: CssSize,
    ry: CssSize,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("ellipse", className, id) {
    cx(cx)
    cy(cy)
    rx(rx)
    ry(ry)
    content()
}

/**
 * SVG "ellipse" tag with plain number attributes.
 */
@Composable
public fun ISvgTag.ellipse(
    cx: Number,
    cy: Number,
    rx: Number,
    ry: Number,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = ellipse(cx.units, cy.units, rx.units, ry.units, className, id, content)

/**
 * SVG "symbol" tag.
 */
@Composable
public fun ISvgTag.symbol(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("symbol", className, id, false, content)

/**
 * SVG "use" tag.
 */
@Composable
public fun ISvgTag.use(
    href: String,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("use", className, id) {
    href(href)
    content()
}

/**
 * SVG "line" tag.
 */
@Composable
public fun ISvgTag.line(
    x1: CssSize,
    y1: CssSize,
    x2: CssSize,
    y2: CssSize,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("line", className, id) {
    x1(x1)
    y1(y1)
    x2(x2)
    y2(y2)
    content()
}

/**
 * SVG "line" tag with plain number attributes.
 */
@Composable
public fun ISvgTag.line(
    x1: Number,
    y1: Number,
    x2: Number,
    y2: Number,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = line(x1.units, y1.units, x2.units, y2.units, className, id, content)

/**
 * SVG "clipPath" tag.
 */
@Composable
public fun ISvgTag.clipPath(
    id: String,
    className: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("clipPath", className, id, false, content)

/**
 * SVG "path" tag.
 */
@Composable
public fun ISvgTag.path(
    d: String,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("path", className, id) {
    d(d)
    content()
}

/**
 * SVG "g" tag.
 */
@Composable
public fun ISvgTag.g(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("g", className, id, false, content)

/**
 * SVG "image" tag.
 */
@Composable
public fun ISvgTag.image(
    href: String,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("image", className, id) {
    href(href)
    content()
}

/**
 * SVG "mask" tag.
 */
@Composable
public fun ISvgTag.mask(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("mask", className, id, false, content)

/**
 * SVG "defs" tag.
 */
@Composable
public fun ISvgTag.defs(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("defs", className, id, false, content)

/**
 * SVG "pattern" tag.
 */
@Composable
public fun ISvgTag.pattern(
    id: String,
    className: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("pattern", className, id, false, content)

/**
 * SVG "polygon" tag.
 */
@Composable
public fun ISvgTag.polygon(
    vararg points: Number,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("polygon", className, id) {
    points(points.toList().chunked(2).joinToString(" ") { it.joinToString(",") })
    content()
}

/**
 * SVG "polyline" tag.
 */
@Composable
public fun ISvgTag.polyline(
    vararg points: Number,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("polyline", className, id) {
    points(points.toList().chunked(2).joinToString(" ") { it.joinToString(",") })
    content()
}

/**
 * SVG "textPath" tag.
 */
@Composable
public fun ISvgTag.textPath(
    href: String,
    text: String,
    className: String? = null,
    id: String? = null,
    setup: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("textPath", className, id) {
    href(href)
    textNode(text)
    setup()
}

/**
 * SVG "animate" tag.
 */
@Composable
public fun ISvgTag.animate(
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("animate", null, id, false, content)

/**
 * SVG "animateMotion" tag.
 */
@Composable
public fun ISvgTag.animateMotion(
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("animateMotion", null, id, false, content)

/**
 * SVG "animateTransform" tag.
 */
@Composable
public fun ISvgTag.animateTransform(
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("animateTransform", null, id, false, content)

/**
 * SVG "linearGradient" tag.
 */
@Composable
public fun ISvgTag.linearGradient(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("linearGradient", className, id, false, content)

/**
 * SVG "radialGradient" tag.
 */
@Composable
public fun ISvgTag.radialGradient(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("radialGradient", className, id, false, content)

/**
 * SVG "stop" tag.
 */
@Composable
public fun ISvgTag.stop(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("stop", className, id, false, content)

/**
 * SVG "switch" tag.
 */
@Composable
public fun ISvgTag.switch(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("switch", className, id, false, content)

/**
 * SVG "title" tag.
 */
@Composable
public fun ISvgTag.svgTitle(
    text: String,
    className: String? = null,
    id: String? = null,
    setup: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("title", className, id) {
    textNode(text)
    setup()
}

/**
 * SVG "tspan" tag.
 */
@Composable
public fun ISvgTag.tspan(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("tspan", className, id, false, content)

/**
 * SVG "desc" tag.
 */
@Composable
public fun ISvgTag.desc(
    text: String,
    className: String? = null,
    id: String? = null,
    setup: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("desc", className, id) {
    textNode(text)
    setup()
}

/**
 * SVG "marker" tag.
 */
@Composable
public fun ISvgTag.marker(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("marker", className, id, false, content)

/**
 * SVG "mpath" tag.
 */
@Composable
public fun ISvgTag.mpath(
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("mpath", null, id, false, content)

/**
 * SVG "filter" tag.
 */
@Composable
public fun ISvgTag.filter(
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("filter", className, id, false, content)

/**
 * SVG "set" tag.
 */
@Composable
public fun ISvgTag.set(
    attributeName: String,
    to: String,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag("set", null, id) {
    attributeName(attributeName)
    to(to)
    content()
}

/**
 * SVG custom element tag.
 */
@Composable
public fun ISvgTag.svgElement(
    name: String,
    className: String? = null,
    id: String? = null,
    content: @Composable ISvgTag.() -> Unit = {}
): SvgTag = svgTag(name, className, id, false, content)
