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
import web.events.Event
import web.focus.FocusEvent
import web.input.InputEvent
import web.keyboard.KeyboardEvent
import web.mouse.MouseEvent
import web.pointer.PointerEvent
import web.touch.TouchEvent

/**
 * Common tag events.
 */
@Suppress("TooManyFunctions")
public interface TagEvents {
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
     * Add touch start event listener (composable).
     */
    @Composable
    public fun onTouchStart(listener: (TouchEvent) -> Unit): Int

    /**
     * Add touch start event listener (not composable).
     */
    public fun onTouchStartDirect(listener: (TouchEvent) -> Unit): Int

    /**
     * Add touch end event listener (composable).
     */
    @Composable
    public fun onTouchEnd(listener: (TouchEvent) -> Unit): Int

    /**
     * Add touch end event listener (not composable).
     */
    public fun onTouchEndDirect(listener: (TouchEvent) -> Unit): Int

    /**
     * Add touch cancel event listener (composable).
     */
    @Composable
    public fun onTouchCancel(listener: (TouchEvent) -> Unit): Int

    /**
     * Add touch cancel event listener (not composable).
     */
    public fun onTouchCancelDirect(listener: (TouchEvent) -> Unit): Int

    /**
     * Add mouse down event listener (composable).
     */
    @Composable
    public fun onMouseDown(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse down event listener (not composable).
     */
    public fun onMouseDownDirect(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse up event listener (composable).
     */
    @Composable
    public fun onMouseUp(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse up event listener (not composable).
     */
    public fun onMouseUpDirect(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse enter event listener (composable).
     */
    @Composable
    public fun onMouseEnter(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse enter event listener (not composable).
     */
    public fun onMouseEnterDirect(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse leave event listener (composable).
     */
    @Composable
    public fun onMouseLeave(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse leave event listener (not composable).
     */
    public fun onMouseLeaveDirect(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse over event listener (composable).
     */
    @Composable
    public fun onMouseOver(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse over event listener (not composable).
     */
    public fun onMouseOverDirect(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse out event listener (composable).
     */
    @Composable
    public fun onMouseOut(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse out event listener (not composable).
     */
    public fun onMouseOutDirect(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse move event listener (composable).
     */
    @Composable
    public fun onMouseMove(listener: (MouseEvent) -> Unit): Int

    /**
     * Add mouse move event listener (not composable).
     */
    public fun onMouseMoveDirect(listener: (MouseEvent) -> Unit): Int

    /**
     * Add pointer down event listener (composable).
     */
    @Composable
    public fun onPointerDown(listener: (PointerEvent) -> Unit): Int

    /**
     * Add pointer down event listener (not composable).
     */
    public fun onPointerDownDirect(listener: (PointerEvent) -> Unit): Int

    /**
     * Add pointer up event listener (composable).
     */
    @Composable
    public fun onPointerUp(listener: (PointerEvent) -> Unit): Int

    /**
     * Add pointer up event listener (not composable).
     */
    public fun onPointerUpDirect(listener: (PointerEvent) -> Unit): Int

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
}
