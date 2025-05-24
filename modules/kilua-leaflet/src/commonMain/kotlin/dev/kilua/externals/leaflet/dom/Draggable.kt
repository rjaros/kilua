@file:JsModule("leaflet")
@file:Suppress("INTERFACE_WITH_SUPERCLASS")
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

import dev.kilua.externals.leaflet.events.Evented
import js.core.JsAny
import js.import.JsModule
import web.html.HTMLElement
import kotlin.js.definedExternally

/**
 * A class for making DOM elements draggable (including touch support). Used internally for map and
 * marker dragging. Only works for elements that were positioned with [DomUtil.setPosition].
 *
 * See [`https://leafletjs.com/reference.html#draggable`](https://leafletjs.com/reference.html#draggable)
 */
public open external class Draggable(
    element: HTMLElement,
    dragStartTarget: HTMLElement = definedExternally,
    preventOutline: Boolean = definedExternally
) : Evented {
    /** Enables the dragging ability */
    public open fun enable()
    /** Disables the dragging ability */
    public open fun disable()


    public interface DraggableOptions : JsAny {
        /**
         * The max number of pixels a user can shift the mouse pointer during a click for it to be
         * considered a valid click (as opposed to a mouse drag).
         */
        public var clickTolerance: Int
    }
}
