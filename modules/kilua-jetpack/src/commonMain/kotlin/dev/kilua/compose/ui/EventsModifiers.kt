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

package dev.kilua.compose.ui

import web.dom.events.Event
import web.dom.events.FocusEvent
import web.dom.events.InputEvent
import web.dom.events.KeyboardEvent
import web.dom.events.MouseEvent

/**
 * Add click event listener.
 */
public fun Modifier.onClick(listener: (MouseEvent) -> Unit) = eventsModifier {
    onClick(listener)
}

/**
 * Add context menu event listener.
 */
public fun Modifier.onContextmenu(listener: (MouseEvent) -> Unit) = eventsModifier {
    onContextmenu(listener)
}

/**
 * Add double click event listener.
 */
public fun Modifier.onDblclick(listener: (MouseEvent) -> Unit) = eventsModifier {
    onDblclick(listener)
}

/**
 * Add change event listener.
 */
public fun Modifier.onChange(listener: (Event) -> Unit) = eventsModifier {
    onChange(listener)
}

/**
 * Add input event listener.
 */
public fun Modifier.onInput(listener: (InputEvent) -> Unit) = eventsModifier {
    onInput(listener)
}

/**
 * Add focus event listener.
 */
public fun Modifier.onFocus(listener: (FocusEvent) -> Unit) = eventsModifier {
    onFocus(listener)
}

/**
 * Add blur event listener.
 */
public fun Modifier.onBlur(listener: (FocusEvent) -> Unit) = eventsModifier {
    onBlur(listener)
}

/**
 * Add key down event listener.
 */
public fun Modifier.onKeydown(listener: (KeyboardEvent) -> Unit) = eventsModifier {
    onKeydown(listener)
}

/**
 * Add key up event listener.
 */
public fun Modifier.onKeyup(listener: (KeyboardEvent) -> Unit) = eventsModifier {
    onKeyup(listener)
}

/**
 * Add key press event listener.
 */
public fun Modifier.onKeypress(listener: (KeyboardEvent) -> Unit) = eventsModifier {
    onKeypress(listener)
}

/**
 * Add event listener by event name.
 */
public fun <EV : Event> Modifier.onEvent(name: String, listener: (EV) -> Unit) = eventsModifier {
    onEvent(name, listener)
}

/**
 * Add click event listener when [enabled] is true.
 * This function mimics the behavior of clickable in Jetpack Compose.
 */
public fun Modifier.clickable(enabled: Boolean = true, onClick: (MouseEvent) -> Unit) = eventsModifier {
    if (enabled) {
        onClick(onClick)
    }
}

/**
 * Add click and dblclick events listeners when [enabled] is true.
 * This function mimics the behavior of combinedClickable in Jetpack Compose.
 */
public fun Modifier.combinedClickable(
    enabled: Boolean = true,
    onDblclick: ((MouseEvent) -> Unit)? = null,
    onClick: (MouseEvent) -> Unit
) = eventsModifier {
    if (enabled) {
        onDblclick?.let { onDblclick(it) }
        onClick(onClick)
    }
}
