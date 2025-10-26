@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2022 Adam S.
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

package dev.kilua.externals.leaflet.dom

import dev.kilua.externals.leaflet.dom.DomUtil.preventOutline
import dev.kilua.externals.leaflet.dom.DomUtil.setPosition
import dev.kilua.externals.leaflet.geometry.Point
import js.core.JsNumber
import web.html.HTMLElement
import kotlin.js.JsAny
import kotlin.js.JsModule
import kotlin.js.definedExternally

/**
 * Utility functions to work with the [DOM](https://developer.mozilla.org/en-US/docs/Web/API/Document_Object_Model)
 * tree, used by Leaflet internally.
 *
 * Most functions expecting or returning a [HTMLElement] also work for SVG elements. The only
 * difference is that classes refer to CSS classes in HTML and SVG classes in SVG.
 *
 * See [`https://leafletjs.com/reference.html#domutil`](https://leafletjs.com/reference.html#domutil)
 */
public external object DomUtil : JsAny {

    /** Get Element by the given HTML-Element */
    public fun get(element: HTMLElement): HTMLElement?

    /** Get Element by its ID */
    public fun get(element: String): HTMLElement?

    /**
     * Creates an HTML element with `tagName`, sets its class to `className`, and optionally
     * appends it to `container` element.
     *
     * @param tagName The name of the tag to create (for example: `div` or `canvas`).
     * @param className The class to set on the created element.
     * @param container The container to append the created element to.
     */
    public fun create(
        tagName: String,
        className: String = definedExternally,
        container: HTMLElement = definedExternally
    ): HTMLElement

    public fun toFront(el: HTMLElement)
    public fun toBack(el: HTMLElement)
    public fun setTransform(el: HTMLElement, offset: Point, scale: JsNumber = definedExternally)

    /**
     * Sets the position of el to coordinates specified by position, using CSS translate or
     * top/left positioning depending on the browser (used by Leaflet internally to position its
     * layers).
     */
    public fun setPosition(el: HTMLElement, position: Point)

    /** Returns the coordinates of an element previously positioned with [setPosition]. */
    public fun getPosition(el: HTMLElement): Point
    public fun disableTextSelection()
    public fun enableTextSelection()
    public fun disableImageDrag()
    public fun enableImageDrag()

    /**
     * Makes the outline of the element `el` invisible. Used internally by Leaflet to prevent
     * focusable elements from displaying an outline when the user performs a drag interaction on
     * them.
     */
    public fun preventOutline(el: HTMLElement)

    /**
     * Cancels the effects of a previous [preventOutline].
     */
    public fun restoreOutline()

    /**
     * Finds the closest parent node which size (width and height) is not null.
     */
    public fun getSizedParentNode(element: HTMLElement): HTMLElement

    /**
     * Computes the CSS scale currently applied on the element.
     */
    public fun getScale(el: HTMLElement): Point
}
