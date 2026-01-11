/*
 * Copyright (c) 2026 Robert Jaros
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

import dev.kilua.routing.globalRouter
import dev.kilua.utils.isDom
import js.array.asList
import web.dom.document
import web.events.EventType
import web.events.addEventHandler
import web.html.HTMLAnchorElement
import web.html.HTMLElement
import web.mouse.MouseEvent
import web.window.WindowTarget
import web.window._self
import kotlin.js.unsafeCast

/**
 * Augments all links (`<a>` elements) within the specified HTML element (or the entire document if no element is provided)
 * to use the global router for navigation instead of performing a full page reload.
 *
 * @param element The HTML element within which to augment links. Defaults to the entire document.
 */
public fun augmentLinks(element: HTMLElement = document.documentElement) {
    if (isDom && globalRouter != null) {
        element.getElementsByTagName("a").asList().forEach { element ->
            val anchor = element.unsafeCast<HTMLAnchorElement>()
            if ((anchor.target == js.reflect.unsafeCast("") || anchor.target == WindowTarget._self)
                && anchor.href.startsWith(document.location.origin)
            ) {
                val routeUrl = anchor.href.drop(document.location.origin.length)
                anchor.addEventHandler(EventType<MouseEvent>("click")) {
                    it.preventDefault()
                    globalRouter?.navigate(routeUrl)
                }
            }
        }
    }
}
