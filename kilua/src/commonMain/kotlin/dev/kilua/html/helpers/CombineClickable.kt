/*
 * Copyright (c) 2023 Robert Jaros
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import web.events.Event
import web.timers.Timeout
import web.timers.clearTimeout
import web.timers.setTimeout

/**
 * A Composable function that handles click, double-click, and long click events for a component.
 * It combines multiple input events such as mouse and touch events to trigger the respective
 * actions based on the user's interaction.
 *
 * @param onLongClick An optional lambda function that will be invoked on a long click. If `null`, no action is taken.
 * @param onDoubleClick An optional lambda function that will be invoked on a double click. If `null`,
 * no action is taken.
 * @param onClick A lambda function to be invoked on a regular single click (non-long click).
 */
@Composable
public fun TagEvents.onCombineClick(
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    var singleClickTimer by remember { mutableStateOf<Timeout?>(null) }
    var longClickTimer by remember { mutableStateOf<Timeout?>(null) }
    var clickCount by remember { mutableStateOf(0) }
    var isLongClickTriggered by remember { mutableStateOf(false) }

    // Long Click with Mouse and Touch
    val startEvent: (Event) -> Unit = remember {
        {
            isLongClickTriggered = false

            longClickTimer = setTimeout({
                isLongClickTriggered = true
                onLongClick?.invoke()
                clickCount = 0
                null
            }, LONG_CLICK_DURATION)
        }
    }

    val endEvent: (Event) -> Unit = remember {
        {
            longClickTimer?.let { clearTimeout(it) }
            longClickTimer = null
        }
    }

    // Mouse Events
    onMouseDown(startEvent)
    onMouseUp(endEvent)
    onMouseLeave(endEvent)

    // Touch Events
    onTouchStart(startEvent)
    onTouchEnd(endEvent)
    onTouchCancel(endEvent)

    // Click and Double Click
    onClick {
        if (isLongClickTriggered) {
            return@onClick
        }

        clickCount++
        if (clickCount == 1) {
            singleClickTimer = setTimeout({
                if (clickCount == 1) {
                    onClick()
                }
                clickCount = 0
                null
            }, DOUBLE_CLICK_DELAY)
        } else if (clickCount == 2) {
            singleClickTimer?.let { clearTimeout(it) }
            clickCount = 0
            onDoubleClick?.invoke()
        }
    }
}

private const val LONG_CLICK_DURATION = 500 // ms
private const val DOUBLE_CLICK_DELAY = 300 // ms
