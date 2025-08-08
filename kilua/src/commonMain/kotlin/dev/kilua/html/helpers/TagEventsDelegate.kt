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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.utils.buildAddEventListenerOptions
import dev.kilua.utils.cast
import dev.kilua.utils.nativeMapOf
import web.abort.AbortController
import web.events.Event
import web.events.EventType
import web.events.addEventListener
import web.focus.FocusEvent
import web.html.HTMLElement
import web.input.InputEvent
import web.keyboard.KeyboardEvent
import web.mouse.MouseEvent
import web.pointer.PointerEvent
import web.touch.TouchEvent

/**
 * Common tag events delegate.
 */
public interface TagEventsDelegate<E : HTMLElement> : TagEvents {
    /**
     * The map of events.
     */
    public val eventsMap: Map<String, Map<String, (Event) -> Unit>>

    /**
     * Connects the delegate with the given element.
     */
    public fun elementWithEvents(element: E?)
}

/**
 * Common tag events delegate implementation.
 */
@Suppress("TooManyFunctions")
public open class TagEventsDelegateImpl<E : HTMLElement>(
    protected val skipUpdates: Boolean,
) : TagEventsDelegate<E> {

    protected val events: MutableMap<String, MutableMap<String, (Event) -> Unit>> by lazy { nativeMapOf() }

    public override val eventsMap: Map<String, Map<String, (Event) -> Unit>> by lazy { events }

    protected val eventsAbortControllers: MutableMap<String, AbortController> by lazy { nativeMapOf() }

    protected lateinit var element: E
    protected var elementNullable: E? = null

    override fun elementWithEvents(element: E?) {
        this.elementNullable = element
        if (!skipUpdates && element != null) {
            this.element = element
        }
    }

    @Composable
    override fun onClick(listener: (MouseEvent) -> Unit): Int {
        return onEvent(CLICK, listener)
    }

    override fun onClickDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect(CLICK, listener)
    }

    @Composable
    override fun onContextmenu(listener: (MouseEvent) -> Unit): Int {
        return onEvent(CONTEXT_MENU, listener)
    }

    override fun onContextmenuDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect(CONTEXT_MENU, listener)
    }

    @Composable
    override fun onDblclick(listener: (MouseEvent) -> Unit): Int {
        return onEvent(DBL_CLICK, listener)
    }

    override fun onDblclickDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect(DBL_CLICK, listener)
    }

    @Composable
    override fun onChange(listener: (Event) -> Unit): Int {
        return onEvent(CHANGE, listener)
    }

    override fun onChangeDirect(listener: (Event) -> Unit): Int {
        return onEventDirect(CHANGE, listener)
    }

    @Composable
    override fun onInput(listener: (InputEvent) -> Unit): Int {
        return onEvent(INPUT, listener)
    }

    override fun onInputDirect(listener: (InputEvent) -> Unit): Int {
        return onEventDirect(INPUT, listener)
    }

    @Composable
    override fun onFocus(listener: (FocusEvent) -> Unit): Int {
        return onEvent(FOCUS, listener)
    }

    override fun onFocusDirect(listener: (FocusEvent) -> Unit): Int {
        return onEventDirect(FOCUS, listener)
    }

    @Composable
    override fun onBlur(listener: (FocusEvent) -> Unit): Int {
        return onEvent(BLUR, listener)
    }

    override fun onBlurDirect(listener: (FocusEvent) -> Unit): Int {
        return onEventDirect(BLUR, listener)
    }

    @Composable
    override fun onKeydown(listener: (KeyboardEvent) -> Unit): Int {
        return onEvent(KEY_DOWN, listener)
    }

    override fun onKeydownDirect(listener: (KeyboardEvent) -> Unit): Int {
        return onEventDirect(KEY_DOWN, listener)
    }

    @Composable
    override fun onKeyup(listener: (KeyboardEvent) -> Unit): Int {
        return onEvent(KEY_UP, listener)
    }

    override fun onKeyupDirect(listener: (KeyboardEvent) -> Unit): Int {
        return onEventDirect(KEY_UP, listener)
    }

    @Composable
    override fun onKeypress(listener: (KeyboardEvent) -> Unit): Int {
        return onEvent(KEY_PRESS, listener)
    }

    override fun onKeypressDirect(listener: (KeyboardEvent) -> Unit): Int {
        return onEventDirect(KEY_PRESS, listener)
    }

    @Composable
    override fun onTouchStart(listener: (TouchEvent) -> Unit): Int {
        return onEvent(TOUCH_START, listener)
    }

    override fun onTouchStartDirect(listener: (TouchEvent) -> Unit): Int {
        return onEventDirect(TOUCH_START, listener)
    }

    @Composable
    override fun onTouchEnd(listener: (TouchEvent) -> Unit): Int {
        return onEvent(TOUCH_END, listener)
    }

    override fun onTouchEndDirect(listener: (TouchEvent) -> Unit): Int {
        return onEventDirect(TOUCH_END, listener)
    }

    @Composable
    override fun onTouchCancel(listener: (TouchEvent) -> Unit): Int {
        return onEvent(TOUCH_CANCEL, listener)
    }

    override fun onTouchCancelDirect(listener: (TouchEvent) -> Unit): Int {
        return onEventDirect(TOUCH_CANCEL, listener)
    }

    @Composable
    override fun onMouseDown(listener: (MouseEvent) -> Unit): Int {
        return onEvent(MOUSE_DOWN, listener)
    }

    override fun onMouseDownDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect(MOUSE_DOWN, listener)
    }

    @Composable
    override fun onMouseUp(listener: (MouseEvent) -> Unit): Int {
        return onEvent(MOUSE_UP, listener)
    }

    override fun onMouseUpDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect(MOUSE_UP, listener)
    }

    @Composable
    override fun onMouseEnter(listener: (MouseEvent) -> Unit): Int {
        return onEvent(MOUSE_ENTER, listener)
    }

    override fun onMouseEnterDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect(MOUSE_ENTER, listener)
    }

    @Composable
    override fun onMouseLeave(listener: (MouseEvent) -> Unit): Int {
        return onEvent(MOUSE_LEAVE, listener)
    }

    override fun onMouseLeaveDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect(MOUSE_LEAVE, listener)
    }

    @Composable
    override fun onMouseOver(listener: (MouseEvent) -> Unit): Int {
        return onEvent(MOUSE_OVER, listener)
    }

    override fun onMouseOverDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect(MOUSE_OVER, listener)
    }

    @Composable
    override fun onMouseOut(listener: (MouseEvent) -> Unit): Int {
        return onEvent(MOUSE_OUT, listener)
    }

    override fun onMouseOutDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect(MOUSE_OUT, listener)
    }

    @Composable
    override fun onMouseMove(listener: (MouseEvent) -> Unit): Int {
        return onEvent(MOUSE_MOVE, listener)
    }

    override fun onMouseMoveDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect(MOUSE_MOVE, listener)
    }

    @Composable
    override fun onPointerDown(listener: (PointerEvent) -> Unit): Int {
        return onEvent(POINTER_DOWN, listener)
    }

    override fun onPointerDownDirect(listener: (PointerEvent) -> Unit): Int {
        return onEventDirect(POINTER_DOWN, listener)
    }

    @Composable
    override fun onPointerUp(listener: (PointerEvent) -> Unit): Int {
        return onEvent(POINTER_UP, listener)
    }

    override fun onPointerUpDirect(listener: (PointerEvent) -> Unit): Int {
        return onEventDirect(POINTER_UP, listener)
    }

    @Composable
    override fun <E : Event> onEvent(name: String, listener: (E) -> Unit): Int {
        val id = remember { counter++ }
        onEventInternal(id, name, listener)
        DisposableEffect(id) {
            onDispose {
                removeEventListener(name, id)
            }
        }
        return id
    }

    override fun <E : Event> onEventDirect(name: String, listener: (E) -> Unit): Int {
        val id = counter++
        onEventInternal(id, name, listener)
        return id
    }

    protected fun <E : Event> onEventInternal(
        id: Int,
        name: String,
        listener: (E) -> Unit
    ) {
        val listeners = events[name] ?: nativeMapOf<(Event) -> Unit>().also { events[name] = it }
        listeners["$id"] = listener.cast()
        if (skipUpdates || elementNullable == null) return
        eventsAbortControllers[name]?.abort()
        val abortController = AbortController()
        eventsAbortControllers[name] = abortController
        element.addEventListener(EventType<Event>(name), { event ->
            listeners.forEach { (_, listener) ->
                listener(event)
            }
        }, buildAddEventListenerOptions(abortController.signal))
    }

    override fun removeEventListener(name: String, id: Int?) {
        if (id != null) {
            events[name]?.remove(id.toString())
        } else {
            events[name]?.clear()
        }
    }

    public companion object {
        private var counter = 0
    }
}

internal const val RESIZE = "resize"
internal const val CLICK = "click"
internal const val CONTEXT_MENU = "contextmenu"
internal const val DBL_CLICK = "dblclick"
internal const val CHANGE = "change"
internal const val INPUT = "input"
internal const val FOCUS = "focus"
internal const val BLUR = "blur"
internal const val KEY_DOWN = "keydown"
internal const val KEY_UP = "keyup"
internal const val KEY_PRESS = "keypress"
internal const val TOUCH_START = "touchstart"
internal const val TOUCH_END = "touchend"
internal const val TOUCH_CANCEL = "touchcancel"
internal const val MOUSE_DOWN = "mousedown"
internal const val MOUSE_UP = "mouseup"
internal const val MOUSE_ENTER = "mouseenter"
internal const val MOUSE_LEAVE = "mouseleave"
internal const val MOUSE_OVER = "mouseover"
internal const val MOUSE_OUT = "mouseout"
internal const val MOUSE_MOVE = "mousemove"
internal const val POINTER_DOWN = "pointerdown"
internal const val POINTER_UP = "pointerup"
