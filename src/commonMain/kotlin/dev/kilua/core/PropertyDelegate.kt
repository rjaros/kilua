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

package dev.kilua.core

import dev.kilua.utils.NativeMap
import dev.kilua.utils.cast
import dev.kilua.utils.nativeMapOf
import dev.kilua.utils.set
import kotlin.reflect.KProperty

public open class PropertyDelegate {
    protected val managedPropertyUpdateFunctions: NativeMap<Any> = nativeMapOf()
    protected val managedPropertiesSet: MutableSet<String> = mutableSetOf()

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> managedProperty(noinline updateFunction: ((T) -> Unit) = {}): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, updateFunction)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> managedProperty(
        initialValue: T,
        noinline updateFunction: ((T) -> Unit) = {}
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, updateFunction)

    protected inner class ManagedPropertyDelegateProvider<T>(
        private val initialValue: T?, private val updateFunction: (T) -> Unit
    ) {
        public operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): ManagedPropertyDelegate<T> {
            managedPropertyUpdateFunctions[prop.name] = updateFunction
            return ManagedPropertyDelegate(initialValue, updateFunction)
        }
    }

    protected inner class ManagedPropertyDelegate<T>(private var value: T?, private val updateFunction: ((T) -> Unit)) {
        public operator fun getValue(thisRef: PropertyDelegate, property: KProperty<*>): T {
            return value ?: null.cast()
        }

        public operator fun setValue(thisRef: PropertyDelegate, property: KProperty<*>, value: T) {
            managedPropertiesSet.add(property.name)
            if (this.value != value) {
                this.value = value
                updateFunction(value)
            }
        }
    }

    public fun <T> updateManagedProperty(property: KProperty<*>, value: T) {
        if (!managedPropertiesSet.contains(property.name)) {
            managedPropertyUpdateFunctions[property.name]?.cast<(T) -> Unit>()?.invoke(value)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> unmanagedProperty(noinline updateFunction: ((T) -> Unit) = {}): UnmanagedPropertyDelegateProvider<T> =
        UnmanagedPropertyDelegateProvider(null, updateFunction)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> unmanagedProperty(
        initialValue: T,
        noinline updateFunction: ((T) -> Unit) = {}
    ): UnmanagedPropertyDelegateProvider<T> =
        UnmanagedPropertyDelegateProvider(initialValue, updateFunction)

    protected inner class UnmanagedPropertyDelegateProvider<T>(
        private val initialValue: T?, private val updateFunction: (T) -> Unit
    ) {
        public operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): UnmanagedPropertyDelegate<T> {
            return UnmanagedPropertyDelegate(initialValue, updateFunction)
        }
    }

    protected inner class UnmanagedPropertyDelegate<T>(
        private var value: T?,
        private val updateFunction: ((T) -> Unit)
    ) {
        public operator fun getValue(thisRef: PropertyDelegate, property: KProperty<*>): T {
            return value ?: null.cast()
        }

        public operator fun setValue(thisRef: PropertyDelegate, property: KProperty<*>, value: T) {
            if (this.value != value) {
                this.value = value
                updateFunction(value)
            }
        }
    }
}
