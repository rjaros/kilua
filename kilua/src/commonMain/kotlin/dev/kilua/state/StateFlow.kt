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

import dev.kilua.KiluaScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

/**
 * Extension function returning a substate flow.
 */
public fun <T, S> StateFlow<S>.subFlow(contextScope: CoroutineScope = KiluaScope, sub: (S) -> T): StateFlow<T> {
    return this.map { sub(it) }.stateIn(contextScope, SharingStarted.Eagerly, sub(this.value))
}

/**
 * Extension property returning a StateFlow<S> for an ObservableState<S>.
 */
public inline val <S> ObservableState<S>.stateFlow: StateFlow<S>
    get() = MutableStateFlow(value).apply {
        this@stateFlow.subscribe { this.value = it }
    }

/**
 * Extension property returning a MutableStateFlow<S> for a MutableState<S>.
 */
public inline val <S> MutableState<S>.mutableStateFlow: MutableStateFlow<S>
    get() = MutableStateFlow(value).apply {
        this@mutableStateFlow.subscribe { this.value = it }
        this.onEach {
            this@mutableStateFlow.value = it
        }.launchIn(KiluaScope)
    }
