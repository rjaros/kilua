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
import dev.kilua.dom.api.HTMLElement

/**
 * Common tag events.
 */
@Suppress("TooManyFunctions")
public interface TagEvents<E : HTMLElement> {
    /**
     * Add click event listener (composable).
     */
    @Composable
    public fun onClick(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int

    /**
     * Add click event listener (not composable).
     */
    public fun onClickDirect(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int

    /**
     * Add context menu event listener (composable).
     */
    @Composable
    public fun onContextmenu(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int

    /**
     * Add context menu event listener (not composable).
     */
    public fun onContextmenuDirect(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int

    /**
     * Add double click event listener (composable).
     */
    @Composable
    public fun onDblclick(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int

    /**
     * Add double click event listener (not composable).
     */
    public fun onDblclickDirect(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int

    /**
     * Add change event listener (composable).
     */
    @Composable
    public fun onChange(listener: (dev.kilua.dom.api.events.Event) -> Unit): Int

    /**
     * Add change event listener (not composable).
     */
    public fun onChangeDirect(listener: (dev.kilua.dom.api.events.Event) -> Unit): Int

    /**
     * Add input event listener (composable).
     */
    @Composable
    public fun onInput(listener: (dev.kilua.dom.api.events.InputEvent) -> Unit): Int

    /**
     * Add input event listener (not composable).
     */
    public fun onInputDirect(listener: (dev.kilua.dom.api.events.InputEvent) -> Unit): Int

    /**
     * Add focus event listener (composable).
     */
    @Composable
    public fun onFocus(listener: (dev.kilua.dom.api.events.FocusEvent) -> Unit): Int

    /**
     * Add focus event listener (not composable).
     */
    public fun onFocusDirect(listener: (dev.kilua.dom.api.events.FocusEvent) -> Unit): Int

    /**
     * Add blur event listener (composable).
     */
    @Composable
    public fun onBlur(listener: (dev.kilua.dom.api.events.FocusEvent) -> Unit): Int

    /**
     * Add blur event listener (not composable).
     */
    public fun onBlurDirect(listener: (dev.kilua.dom.api.events.FocusEvent) -> Unit): Int

    /**
     * Add key down event listener (composable).
     */
    @Composable
    public fun onKeydown(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int

    /**
     * Add key down event listener (not composable).
     */
    public fun onKeydownDirect(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int

    /**
     * Add key up event listener (composable).
     */
    @Composable
    public fun onKeyup(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int

    /**
     * Add key up event listener (not composable).
     */
    public fun onKeyupDirect(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int

    /**
     * Add key press event listener (composable).
     */
    @Composable
    public fun onKeypress(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int

    /**
     * Add key press event listener (not composable).
     */
    public fun onKeypressDirect(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int

    /**
     * Add event listener by event name (composable).
     */
    @Composable
    public fun <EV : dev.kilua.dom.api.events.Event> onEvent(name: String, listener: (EV) -> Unit): Int

    /**
     * Add event listener by event name (not composable).
     */
    public fun <EV : dev.kilua.dom.api.events.Event> onEventDirect(name: String, listener: (EV) -> Unit): Int

    /**
     * Remove event listener by event name and ID.
     */
    public fun removeEventListener(name: String, id: Int? = null)
}
