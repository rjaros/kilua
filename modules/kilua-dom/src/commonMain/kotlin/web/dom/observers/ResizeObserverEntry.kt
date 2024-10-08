@file:Suppress(
    "NO_EXPLICIT_VISIBILITY_IN_API_MODE",
    "NO_EXPLICIT_RETURN_TYPE_IN_API_MODE",
    "EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE",
)
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

package web.dom.observers

import web.JsAny
import web.JsArray
import web.dom.DOMRectReadOnly
import web.dom.Element

/** [MDN Reference](https://developer.mozilla.org/docs/Web/API/ResizeObserverEntry) */
external class ResizeObserverEntry : JsAny {
    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/ResizeObserverEntry/borderBoxSize) */
    val borderBoxSize: JsArray<ResizeObserverSize>

    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/ResizeObserverEntry/contentBoxSize) */
    val contentBoxSize: JsArray<ResizeObserverSize>

    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/ResizeObserverEntry/contentRect) */
    val contentRect: DOMRectReadOnly
    val devicePixelContentBoxSize: JsArray<ResizeObserverSize>

    /** [MDN Reference](https://developer.mozilla.org/docs/Web/API/ResizeObserverEntry/target) */
    val target: Element
}
