/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2025 Ghasem Shirdel
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

package dev.kilua.html.helpers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import web.dom.document
import web.events.Event
import web.events.EventType
import web.events.addEventListener
import web.events.removeEventListener
import web.uievents.PointerEvent
import web.window.window

/**
 * A composable function that triggers a callback whenever pointer up event is received.
 *
 * This function registers an event listener for the `pointerup` event on the global `document` object.
 * Whenever the pointer up event is received, the provided callback is invoked.
 * The event listener is automatically removed when the composable leaves the composition tree.
 *
 * @param callback A lambda function called.
 *
 * Example usage:
 * ```
 * onGlobalPointerUp {
 *     println("Pointer up received")
 * }
 * ```
 *
 * @see DisposableEffect
 */
@Composable
public fun onGlobalPointerUp(callback: () -> Unit) {
    DisposableEffect(Unit) {
        val listener = { _: Event -> callback() }
        document.addEventListener(EventType<PointerEvent>(POINTER_UP), listener)
        onDispose { document.removeEventListener(EventType<PointerEvent>(POINTER_UP), listener) }
    }
}

/**
 * A composable function that triggers a callback whenever the global window size changes.
 *
 * This function registers an event listener for the `resize` event on the global `window` object.
 * Whenever the window is resized, the provided callback is invoked with the current window dimensions.
 * The event listener is automatically removed when the composable leaves the composition tree.
 *
 * @param callback A lambda function that receives the current window width and height.
 *                 - `width`: The inner width of the window in pixels.
 *                 - `height`: The inner height of the window in pixels.
 *
 * Example usage:
 * ```
 * onGlobalWindowSize { width, height ->
 *     println("Window size: ${width}x${height}")
 * }
 * ```
 *
 * @see DisposableEffect
 */
@Composable
public fun onGlobalWindowSize(callback: (width: Int, height: Int) -> Unit) {
    DisposableEffect(Unit) {
        val listener = { _: Event ->
            callback(window.innerWidth, window.innerHeight)
        }
        window.addEventListener(EventType<Event>(RESIZE), listener)
        onDispose { window.removeEventListener(EventType<Event>(RESIZE), listener) }
    }
}
