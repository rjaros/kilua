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

import dev.kilua.utils.cast
import dev.kilua.utils.nativeMapOf
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.mutableSetOf
import kotlin.collections.set
import kotlin.reflect.KProperty

public open class PropertyDelegate(protected val propertyValues: MutableMap<String, Any>) {
    protected val managedPropertyUpdateFunctions: MutableMap<String, Any> = nativeMapOf()
    protected val managedPropertiesSet: MutableSet<String> = mutableSetOf()

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> managedProperty(
        skipUpdate: Boolean = false,
        noinline updateFunction: ((T) -> Unit) = {}
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, skipUpdate, updateFunction)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> managedProperty(
        initialValue: T,
        skipUpdate: Boolean = false,
        noinline updateFunction: ((T) -> Unit) = {}
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, skipUpdate, updateFunction)

    protected inner class ManagedPropertyDelegateProvider<T>(
        private val initialValue: T?, private val skipUpdate: Boolean, private val updateFunction: (T) -> Unit
    ) {
        public operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): UpdatingPropertyDelegate<T> {
            if (initialValue != null) propertyValues[prop.name] = initialValue
            if (!skipUpdate) managedPropertyUpdateFunctions[prop.name] = updateFunction
            return UpdatingPropertyDelegate(skipUpdate, updateFunction)
        }
    }

    public fun <T> updateManagedProperty(property: KProperty<*>, value: T) {
        if (!managedPropertiesSet.contains(property.name)) {
            managedPropertyUpdateFunctions[property.name]?.cast<(T) -> Unit>()?.invoke(value)
        }
    }

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> unmanagedProperty(
        skipUpdate: Boolean = false,
        noinline updateFunction: ((T) -> Unit) = {}
    ): UnmanagedPropertyDelegateProvider<T> =
        UnmanagedPropertyDelegateProvider(null, skipUpdate, updateFunction)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> unmanagedProperty(
        initialValue: T,
        skipUpdate: Boolean = false,
        noinline updateFunction: ((T) -> Unit) = {}
    ): UnmanagedPropertyDelegateProvider<T> =
        UnmanagedPropertyDelegateProvider(initialValue, skipUpdate, updateFunction)

    protected inner class UnmanagedPropertyDelegateProvider<T>(
        private val initialValue: T?, private val skipUpdate: Boolean, private val updateFunction: (T) -> Unit
    ) {
        public operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): UpdatingPropertyDelegate<T> {
            if (initialValue != null) propertyValues[prop.name] = initialValue
            return UpdatingPropertyDelegate(skipUpdate, updateFunction)
        }
    }

    protected inner class UpdatingPropertyDelegate<T>(
        private val skipUpdate: Boolean,
        private val updateFunction: (T) -> Unit
    ) {
        public operator fun getValue(thisRef: PropertyDelegate, property: KProperty<*>): T {
            val value = propertyValues[property.name]
            return if (value != null) {
                value.cast()
            } else {
                null.cast()
            }
        }

        public operator fun setValue(thisRef: PropertyDelegate, property: KProperty<*>, value: T) {
            managedPropertiesSet.add(property.name)
            val oldValue = propertyValues[property.name]
            if (oldValue != value) {
                if (value == null) {
                    propertyValues.remove(property.name)
                } else {
                    propertyValues[property.name] = value
                }
                if (!skipUpdate) updateFunction(value)
            }
        }
    }
}
