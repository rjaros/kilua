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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.externals.AbortController
import dev.kilua.externals.buildAddEventListenerOptions
import dev.kilua.utils.cast
import dev.kilua.utils.nativeMapOf
import web.document
import web.dom.HTMLElement
import web.dom.TouchEvent
import web.dom.events.*
import web.dom.pointerevents.PointerEvent
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

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
        return onEvent("click", listener)
    }

    override fun onClickDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect("click", listener)
    }

    @Composable
    override fun onContextmenu(listener: (MouseEvent) -> Unit): Int {
        return onEvent("contextmenu", listener)
    }

    override fun onContextmenuDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect("contextmenu", listener)
    }

    @Composable
    override fun onDblclick(listener: (MouseEvent) -> Unit): Int {
        return onEvent("dblclick", listener)
    }

    override fun onDblclickDirect(listener: (MouseEvent) -> Unit): Int {
        return onEventDirect("dblclick", listener)
    }

    @Composable
    override fun onChange(listener: (Event) -> Unit): Int {
        return onEvent("change", listener)
    }

    override fun onChangeDirect(listener: (Event) -> Unit): Int {
        return onEventDirect("change", listener)
    }

    @Composable
    override fun onInput(listener: (InputEvent) -> Unit): Int {
        return onEvent("input", listener)
    }

    override fun onInputDirect(listener: (InputEvent) -> Unit): Int {
        return onEventDirect("input", listener)
    }

    @Composable
    override fun onFocus(listener: (FocusEvent) -> Unit): Int {
        return onEvent("focus", listener)
    }

    override fun onFocusDirect(listener: (FocusEvent) -> Unit): Int {
        return onEventDirect("focus", listener)
    }

    @Composable
    override fun onBlur(listener: (FocusEvent) -> Unit): Int {
        return onEvent("blur", listener)
    }

    override fun onBlurDirect(listener: (FocusEvent) -> Unit): Int {
        return onEventDirect("blur", listener)
    }

    @Composable
    override fun onKeydown(listener: (KeyboardEvent) -> Unit): Int {
        return onEvent("keydown", listener)
    }

    override fun onKeydownDirect(listener: (KeyboardEvent) -> Unit): Int {
        return onEventDirect("keydown", listener)
    }

    @Composable
    override fun onKeyup(listener: (KeyboardEvent) -> Unit): Int {
        return onEvent("keyup", listener)
    }

    override fun onKeyupDirect(listener: (KeyboardEvent) -> Unit): Int {
        return onEventDirect("keyup", listener)
    }

    @Composable
    override fun onKeypress(listener: (KeyboardEvent) -> Unit): Int {
        return onEvent("keypress", listener)
    }

    override fun onKeypressDirect(listener: (KeyboardEvent) -> Unit): Int {
        return onEventDirect("keypress", listener)
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
        element.addEventListener(name, { event ->
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

@Composable
public fun onGlobalPointerUp(callback: () -> Unit) {
    DisposableEffect(Unit) {
        val listener = { _: Event -> callback() }
        document.addEventListener(POINTER_UP, listener)
        onDispose { document.removeEventListener(POINTER_UP, listener) }
    }
}

private const val TOUCH_START = "touchstart"
private const val TOUCH_END = "touchend"
private const val TOUCH_CANCEL = "touchcancel"
private const val MOUSE_DOWN = "mousedown"
private const val MOUSE_UP = "mouseup"
private const val MOUSE_LEAVE = "mouseleave"
private const val MOUSE_OVER = "mouseover"
private const val MOUSE_OUT = "mouseout"
private const val POINTER_DOWN = "pointerdown"
private const val POINTER_UP = "pointerup"
