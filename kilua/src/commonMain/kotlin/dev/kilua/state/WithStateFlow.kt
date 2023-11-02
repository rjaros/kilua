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

import dev.kilua.form.FormControl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A component with internal state which can be observed as StateFlow or MutableStateFlow
 */
public interface WithStateFlow<T> {
    /**
     * A state flow view of the component's state
     */
    public val stateFlow: StateFlow<T>

    /**
     * A mutable state flow view of the component's state
     */
    public val mutableStateFlow: MutableStateFlow<T>
}

/**
 * A delegate for [WithStateFlow] interface
 */
public interface WithStateFlowDelegate<T> : WithStateFlow<T> {
    public fun formControl(formControl: FormControl<T>)
    public fun updateStateFlow(value: T)
}

/**
 * A default implementation for the [WithStateFlowDelegate].
 */
public open class WithStateFlowDelegateImpl<T> : WithStateFlowDelegate<T> {

    protected lateinit var formControl: FormControl<T>
    protected var initialized: Boolean = false

    public override fun formControl(formControl: FormControl<T>) {
        this.formControl = formControl
    }

    /**
     * Internal mutable state flow instance (lazily initialized)
     */
    protected val _mutableStateFlow: MutableStateFlow<T> by lazy {
        initialized = true
        MutableStateFlow(formControl.value)
    }

    override val stateFlow: StateFlow<T>
        get() = _mutableStateFlow.asStateFlow()

    override val mutableStateFlow: MutableStateFlow<T>
        get() = _mutableStateFlow

    public override fun updateStateFlow(value: T) {
        if (initialized) {
            _mutableStateFlow.value = value
        }
    }
}
