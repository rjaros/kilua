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
import dev.kilua.KiluaScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import web.dom.events.Event
import web.dom.events.InputEvent
import web.dom.events.MouseEvent

/**
 * An extension function for defining on click suspending event handlers (composable).
 */
@Composable
public fun <C : TagEvents> C.onClickLaunch(
    coroutineScope: CoroutineScope = KiluaScope,
    listener: suspend (MouseEvent) -> Unit
): Int {
    return onClick {
        coroutineScope.launch {
            listener(it)
        }
    }
}

/**
 * An extension function for defining on click suspending event handlers (not composable).
 */
public fun <C : TagEvents> C.onClickDirectLaunch(
    coroutineScope: CoroutineScope = KiluaScope,
    listener: suspend (MouseEvent) -> Unit
): Int {
    return onClickDirect {
        coroutineScope.launch {
            listener(it)
        }
    }
}

/**
 * An extension function for defining on input suspending event handlers (composable).
 */
@Composable
public fun <C : TagEvents> C.onInputLaunch(
    coroutineScope: CoroutineScope = KiluaScope,
    listener: suspend (InputEvent) -> Unit
): Int {
    return onInput {
        coroutineScope.launch {
            listener(it)
        }
    }
}

/**
 * An extension function for defining on input suspending event handlers (not composable).
 */
public fun <C : TagEvents> C.onInputDirectLaunch(
    coroutineScope: CoroutineScope = KiluaScope,
    listener: suspend (InputEvent) -> Unit
): Int {
    return onInputDirect {
        coroutineScope.launch {
            listener(it)
        }
    }
}

/**
 * An extension function for defining on change suspending event handlers (composable).
 */
@Composable
public fun <C : TagEvents> C.onChangeLaunch(
    coroutineScope: CoroutineScope = KiluaScope,
    listener: suspend (Event) -> Unit
): Int {
    return onChange {
        coroutineScope.launch {
            listener(it)
        }
    }
}

/**
 * An extension function for defining on change suspending event handlers (not composable).
 */
public fun <C : TagEvents> C.onChangeDirectLaunch(
    coroutineScope: CoroutineScope = KiluaScope,
    listener: suspend (Event) -> Unit
): Int {
    return onChangeDirect {
        coroutineScope.launch {
            listener(it)
        }
    }
}

/**
 * An extension function for defining on change suspending event handlers (composable).
 */
@Composable
public fun <EV : Event, C : TagEvents> C.onEventLaunch(
    name: String,
    coroutineScope: CoroutineScope = KiluaScope,
    listener: suspend (EV) -> Unit
): Int {
    return onEvent<EV>(name) {
        coroutineScope.launch {
            listener(it)
        }
    }
}

/**
 * An extension function for defining on change suspending event handlers (not composable).
 */
public fun <EV : Event, C : TagEvents> C.onEventDirectLaunch(
    name: String,
    coroutineScope: CoroutineScope = KiluaScope,
    listener: suspend (EV) -> Unit
): Int {
    return onEventDirect<EV>(name) {
        coroutineScope.launch {
            listener(it)
        }
    }
}
