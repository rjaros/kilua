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
import dev.kilua.dom.api.HTMLElement
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

/**
 * Common tag events delegate.
 */
public interface TagEventsDelegate<E : HTMLElement> : TagEvents<E> {
    /**
     * The map of events.
     */
    public val eventsMap: Map<String, Map<String, (dev.kilua.dom.api.events.Event) -> Unit>>

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

    protected val events: MutableMap<String, MutableMap<String, (dev.kilua.dom.api.events.Event) -> Unit>> by lazy { nativeMapOf() }

    public override val eventsMap: Map<String, Map<String, (dev.kilua.dom.api.events.Event) -> Unit>> by lazy { events }

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
    override fun onClick(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int {
        return onEvent("click", listener)
    }

    override fun onClickDirect(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int {
        return onEventDirect("click", listener)
    }

    @Composable
    override fun onContextmenu(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int {
        return onEvent("contextmenu", listener)
    }

    override fun onContextmenuDirect(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int {
        return onEventDirect("contextmenu", listener)
    }

    @Composable
    override fun onDblclick(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int {
        return onEvent("dblclick", listener)
    }

    override fun onDblclickDirect(listener: (dev.kilua.dom.api.events.MouseEvent) -> Unit): Int {
        return onEventDirect("dblclick", listener)
    }

    @Composable
    override fun onChange(listener: (dev.kilua.dom.api.events.Event) -> Unit): Int {
        return onEvent("change", listener)
    }

    override fun onChangeDirect(listener: (dev.kilua.dom.api.events.Event) -> Unit): Int {
        return onEventDirect("change", listener)
    }

    @Composable
    override fun onInput(listener: (dev.kilua.dom.api.events.InputEvent) -> Unit): Int {
        return onEvent("input", listener)
    }

    override fun onInputDirect(listener: (dev.kilua.dom.api.events.InputEvent) -> Unit): Int {
        return onEventDirect("input", listener)
    }

    @Composable
    override fun onFocus(listener: (dev.kilua.dom.api.events.FocusEvent) -> Unit): Int {
        return onEvent("focus", listener)
    }

    override fun onFocusDirect(listener: (dev.kilua.dom.api.events.FocusEvent) -> Unit): Int {
        return onEventDirect("focus", listener)
    }

    @Composable
    override fun onBlur(listener: (dev.kilua.dom.api.events.FocusEvent) -> Unit): Int {
        return onEvent("blur", listener)
    }

    override fun onBlurDirect(listener: (dev.kilua.dom.api.events.FocusEvent) -> Unit): Int {
        return onEventDirect("blur", listener)
    }

    @Composable
    override fun onKeydown(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int {
        return onEvent("keydown", listener)
    }

    override fun onKeydownDirect(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int {
        return onEventDirect("keydown", listener)
    }

    @Composable
    override fun onKeyup(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int {
        return onEvent("keyup", listener)
    }

    override fun onKeyupDirect(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int {
        return onEventDirect("keyup", listener)
    }

    @Composable
    override fun onKeypress(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int {
        return onEvent("keypress", listener)
    }

    override fun onKeypressDirect(listener: (dev.kilua.dom.api.events.KeyboardEvent) -> Unit): Int {
        return onEventDirect("keypress", listener)
    }

    @Composable
    override fun <E : dev.kilua.dom.api.events.Event> onEvent(name: String, listener: (E) -> Unit): Int {
        val id = remember { counter++ }
        onEventInternal(id, name, listener)
        DisposableEffect(id) {
            onDispose {
                removeEventListener(name, id)
            }
        }
        return id
    }

    override fun <E : dev.kilua.dom.api.events.Event> onEventDirect(name: String, listener: (E) -> Unit): Int {
        val id = counter++
        onEventInternal(id, name, listener)
        return id
    }

    protected fun <E : dev.kilua.dom.api.events.Event> onEventInternal(
        id: Int,
        name: String,
        listener: (E) -> Unit
    ) {
        val listeners = events[name] ?: nativeMapOf<(dev.kilua.dom.api.events.Event) -> Unit>().also { events[name] = it }
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
