/*
 * Copyright (c) 2023 Robert Jaros
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
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.FocusEvent
import org.w3c.dom.events.InputEvent
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent

/**
 * Common tag events.
 */
public interface TagEvents<E : HTMLElement> {
    /**
     * Add click event listener (composable).
     */
    @Composable
    public fun onClick(listener: (MouseEvent) -> Unit): Int

    /**
     * Add click event listener (not composable).
     */
    public fun onClickDirect(listener: (MouseEvent) -> Unit): Int

    /**
     * Add context menu event listener (composable).
     */
    @Composable
    public fun onContextmenu(listener: (MouseEvent) -> Unit): Int

    /**
     * Add context menu event listener (not composable).
     */
    public fun onContextmenuDirect(listener: (MouseEvent) -> Unit): Int

    /**
     * Add double click event listener (composable).
     */
    @Composable
    public fun onDblclick(listener: (MouseEvent) -> Unit): Int

    /**
     * Add double click event listener (not composable).
     */
    public fun onDblclickDirect(listener: (MouseEvent) -> Unit): Int

    /**
     * Add change event listener (composable).
     */
    @Composable
    public fun onChange(listener: (Event) -> Unit): Int

    /**
     * Add change event listener (not composable).
     */
    public fun onChangeDirect(listener: (Event) -> Unit): Int

    /**
     * Add input event listener (composable).
     */
    @Composable
    public fun onInput(listener: (InputEvent) -> Unit): Int

    /**
     * Add input event listener (not composable).
     */
    public fun onInputDirect(listener: (InputEvent) -> Unit): Int

    /**
     * Add focus event listener (composable).
     */
    @Composable
    public fun onFocus(listener: (FocusEvent) -> Unit): Int

    /**
     * Add focus event listener (not composable).
     */
    public fun onFocusDirect(listener: (FocusEvent) -> Unit): Int

    /**
     * Add blur event listener (composable).
     */
    @Composable
    public fun onBlur(listener: (FocusEvent) -> Unit): Int

    /**
     * Add blur event listener (not composable).
     */
    public fun onBlurDirect(listener: (FocusEvent) -> Unit): Int

    /**
     * Add key down event listener (composable).
     */
    @Composable
    public fun onKeydown(listener: (KeyboardEvent) -> Unit): Int

    /**
     * Add key down event listener (not composable).
     */
    public fun onKeydownDirect(listener: (KeyboardEvent) -> Unit): Int

    /**
     * Add key up event listener (composable).
     */
    @Composable
    public fun onKeyup(listener: (KeyboardEvent) -> Unit): Int

    /**
     * Add key up event listener (not composable).
     */
    public fun onKeyupDirect(listener: (KeyboardEvent) -> Unit): Int

    /**
     * Add key press event listener (composable).
     */
    @Composable
    public fun onKeypress(listener: (KeyboardEvent) -> Unit): Int

    /**
     * Add key press event listener (not composable).
     */
    public fun onKeypressDirect(listener: (KeyboardEvent) -> Unit): Int

    /**
     * Add event listener by event name (composable).
     */
    @Composable
    public fun <EV : Event> onEvent(name: String, listener: (EV) -> Unit): Int

    /**
     * Add event listener by event name (not composable).
     */
    public fun <EV : Event> onEventDirect(name: String, listener: (EV) -> Unit): Int

    /**
     * Remove event listener by event name and ID.
     */
    public fun removeEventListener(name: String, id: Int? = null)

    /**
     * Connects delegate with the DOM element.
     */
    public fun elementWithEvents(element: E?)
}
