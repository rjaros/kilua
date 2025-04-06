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

package dev.kilua.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import dev.kilua.utils.KiluaScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Extension function returning a substate flow.
 */
public fun <T, S> StateFlow<S>.subFlow(contextScope: CoroutineScope = KiluaScope, sub: (S) -> T): StateFlow<T> {
    return this.map { sub(it) }.stateIn(contextScope, SharingStarted.Eagerly, sub(this.value))
}

/**
 * Collects values from this component and represents its latest value via [State].
 * The [StateFlow.value] is used as an initial value. Every time there would be new value posted
 * into the [StateFlow] the returned [State] will be updated causing recomposition of every
 * [State.value] usage.
 *
 * @param context [CoroutineContext] to use for collecting.
 */
@Composable
public fun <T> WithStateFlow<T>.collectAsState(context: CoroutineContext = EmptyCoroutineContext): State<T> {
    return stateFlow.collectAsState(context)
}
