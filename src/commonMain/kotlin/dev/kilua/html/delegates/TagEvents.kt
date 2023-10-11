/*
 * Copyright (c) 2023-present Robert Jaros
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

package dev.kilua.html.delegates

import androidx.compose.runtime.Composable
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import org.w3c.dom.events.FocusEvent
import org.w3c.dom.events.InputEvent
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.events.MouseEvent

public interface TagEvents<E : HTMLElement> {
    @Composable
    public fun onClick(listener: (MouseEvent) -> Unit): Int
    public fun onClickDirect(listener: (MouseEvent) -> Unit): Int

    @Composable
    public fun onContextmenu(listener: (MouseEvent) -> Unit): Int
    public fun onContextmenuDirect(listener: (MouseEvent) -> Unit): Int

    @Composable
    public fun onDblclick(listener: (MouseEvent) -> Unit): Int
    public fun onDblclickDirect(listener: (MouseEvent) -> Unit): Int

    @Composable
    public fun onChange(listener: (Event) -> Unit): Int
    public fun onChangeDirect(listener: (Event) -> Unit): Int

    @Composable
    public fun onInput(listener: (InputEvent) -> Unit): Int
    public fun onInputDirect(listener: (InputEvent) -> Unit): Int

    @Composable
    public fun onFocus(listener: (FocusEvent) -> Unit): Int
    public fun onFocusDirect(listener: (FocusEvent) -> Unit): Int

    @Composable
    public fun onBlur(listener: (FocusEvent) -> Unit): Int
    public fun onBlurDirect(listener: (FocusEvent) -> Unit): Int

    @Composable
    public fun onKeydown(listener: (KeyboardEvent) -> Unit): Int
    public fun onKeydownDirect(listener: (KeyboardEvent) -> Unit): Int

    @Composable
    public fun onKeyup(listener: (KeyboardEvent) -> Unit): Int
    public fun onKeyupDirect(listener: (KeyboardEvent) -> Unit): Int

    @Composable
    public fun onKeypress(listener: (KeyboardEvent) -> Unit): Int
    public fun onKeypressDirect(listener: (KeyboardEvent) -> Unit): Int

    @Composable
    public fun <EV : Event> onEvent(name: String, listener: (EV) -> Unit): Int
    public fun <EV : Event> onEventDirect(name: String, listener: (EV) -> Unit): Int
    public fun removeEventListener(name: String, id: Int? = null)

    public fun elementWithEvents(element: E?)
}
