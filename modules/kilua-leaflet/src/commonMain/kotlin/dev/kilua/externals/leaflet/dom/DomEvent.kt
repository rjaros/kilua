@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
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

import dev.kilua.externals.leaflet.geometry.Point
import web.events.Event
import web.events.EventTarget
import web.html.HTMLElement
import web.mouse.MouseEvent
import web.mouse.WheelEvent
import kotlin.js.JsAny
import kotlin.js.JsModule
import kotlin.js.definedExternally

/** Utilities for working with DOM events. */
public external object DomEvent : JsAny {
    /** Adds a listener function to a particular event type. */
    public fun on(
        el: EventTarget,
        types: String,
        fn: (event: Event) -> Unit,
        context: JsAny = definedExternally
    )

    /** Adds a set of type/listener pairs. */
    public fun on(el: EventTarget, eventMap: JsAny, context: JsAny = definedExternally)

    /** Removes a listener function from a particular event type. */
    public fun off(
        el: EventTarget,
        types: String,
        fn: (event: Event) -> Unit,
        context: JsAny = definedExternally
    )

    /** Removes a set of type/listener pairs. */
    public fun off(el: EventTarget, eventMap: JsAny, context: JsAny = definedExternally)

    /** Removes all listeners for the given event types. */
    public fun off(el: EventTarget, types: String)

    /** Removes all listeners from the element. */
    public fun off(el: EventTarget)

    /** Stops the given event from propagating to parent elements. */
    public fun stopPropagation(e: Event)

    /** Stops a Leaflet event's original DOM event from propagating. */
    public fun stopPropagation(e: JsAny)

    /** Adds `stopPropagation` to the element's wheel events. */
    public fun disableScrollPropagation(el: HTMLElement)

    /** Adds `stopPropagation` to the element's click, double click, context menu and pointer down events. */
    public fun disableClickPropagation(el: HTMLElement)

    /** Prevents the default action of a DOM event. */
    public fun preventDefault(e: Event)

    /** Stops propagation and prevents the default action. */
    public fun stop(e: Event)

    /** Stops propagation of a Leaflet event's original DOM event. */
    public fun stop(e: JsAny)

    /** Gets the normalized pointer position relative to the container or the page. */
    public fun getPointerPosition(e: MouseEvent, container: HTMLElement = definedExternally): Point

    /** Gets the normalized wheel delta in vertical pixels. */
    public fun getWheelDelta(e: WheelEvent): Double
}
