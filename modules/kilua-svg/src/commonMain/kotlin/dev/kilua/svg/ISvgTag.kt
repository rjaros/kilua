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
import dev.kilua.html.CssSize
import dev.kilua.html.ITag
import web.dom.HTMLElement

/**
 * Base SVG tag interface.
 */
public interface ISvgTag : ITag<HTMLElement> {

    /**
     * The attribute name.
     */
    public val attributeName: String?

    /**
     * Sets the attribute name.
     */
    @Composable
    public fun attributeName(attributeName: String?)

    /**
     * The fill attribute.
     */
    public val fill: String?

    /**
     * Sets the fill attribute.
     */
    @Composable
    public fun fill(fill: String?)

    /**
     * The fill rule attribute.
     */
    public val fillRule: String?

    /**
     * Sets the fill rule attribute.
     */
    @Composable
    public fun fillRule(fillRule: String?)

    /**
     * The fill opacity attribute.
     */
    public val fillOpacity: Number?

    /**
     * Sets the fill opacity attribute.
     */
    @Composable
    public fun fillOpacity(fillOpacity: Number?)

    /**
     * The href attribute.
     */
    public val href: String?

    /**
     * Sets the href attribute.
     */
    @Composable
    public fun href(href: String?)

    /**
     * The viewBox attribute.
     */
    public val viewBox: String?

    /**
     * Sets the viewBox attribute.
     */
    @Composable
    public fun viewBox(viewBox: String?)

    /**
     * The transform attribute.
     */
    public val transform: String?

    /**
     * Sets the transform attribute.
     */
    @Composable
    public fun transform(transform: String?)

    /**
     * The d attribute.
     */
    public val d: String?

    /**
     * Sets the d attribute.
     */
    @Composable
    public fun d(d: String?)

    /**
     * The points attribute.
     */
    public val points: String?

    /**
     * Sets the points attribute.
     */
    @Composable
    public fun points(points: String?)

    /**
     * The cx attribute.
     */
    public val cx: CssSize?

    /**
     * Sets the cx attribute.
     */
    @Composable
    public fun cx(cx: CssSize?)

    /**
     * The cy attribute.
     */
    public val cy: CssSize?

    /**
     * Sets the cy attribute.
     */
    @Composable
    public fun cy(cy: CssSize?)

    /**
     * The r attribute.
     */
    public val r: CssSize?

    /**
     * Sets the r attribute.
     */
    @Composable
    public fun r(r: CssSize?)

    /**
     * The rx attribute.
     */
    public val rx: CssSize?

    /**
     * Sets the rx attribute.
     */
    @Composable
    public fun rx(rx: CssSize?)

    /**
     * The ry attribute.
     */
    public val ry: CssSize?

    /**
     * Sets the ry attribute.
     */
    @Composable
    public fun ry(ry: CssSize?)

    /**
     * The x attribute.
     */
    public val x: CssSize?

    /**
     * Sets the x attribute.
     */
    @Composable
    public fun x(x: CssSize?)

    /**
     * The y attribute.
     */
    public val y: CssSize?

    /**
     * Sets the y attribute.
     */
    @Composable
    public fun y(y: CssSize?)

    /**
     * The x1 attribute.
     */
    public val x1: CssSize?

    /**
     * Sets the x1 attribute.
     */
    @Composable
    public fun x1(x1: CssSize?)

    /**
     * The y1 attribute.
     */
    public val y1: CssSize?

    /**
     * Sets the y1 attribute.
     */
    @Composable
    public fun y1(y1: CssSize?)

    /**
     * The x2 attribute.
     */
    public val x2: CssSize?

    /**
     * Sets the x2 attribute.
     */
    @Composable
    public fun x2(x2: CssSize?)

    /**
     * The y2 attribute.
     */
    public val y2: CssSize?

    /**
     * Sets the y2 attribute.
     */
    @Composable
    public fun y2(y2: CssSize?)

    /**
     * The to attribute.
     */
    public val to: String?

    /**
     * Sets the to attribute.
     */
    @Composable
    public fun to(to: String?)
}
