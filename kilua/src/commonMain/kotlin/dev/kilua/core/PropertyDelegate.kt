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

package dev.kilua.core

import dev.kilua.utils.cast
import dev.kilua.utils.nativeMapOf
import kotlin.collections.set
import kotlin.reflect.KProperty

public open class PropertyDelegate(protected val propertyValues: MutableMap<String, Any>) {
    protected val managedPropertyNotifyFunctions: MutableMap<String, Any> = nativeMapOf()
    protected val managedPropertyUpdateFunctions: MutableMap<String, Any> = nativeMapOf()
    protected val managedPropertyUpdateFunctionsWithOldValue: MutableMap<String, Any> = nativeMapOf()
    protected val managedPropertiesSet: MutableSet<String> = mutableSetOf()

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> managedProperty(
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T) -> Unit)? = null,
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, skipUpdate, managed = true, notifyFunction, updateFunction, null)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> managedProperty(
        initialValue: T,
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, skipUpdate, managed = true, notifyFunction, updateFunction, null)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> unmanagedProperty(
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, skipUpdate, managed = false, notifyFunction, updateFunction, null)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> unmanagedProperty(
        initialValue: T,
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, skipUpdate, managed = false, notifyFunction, updateFunction, null)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> managedPropertyWithOldValue(
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T, T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, skipUpdate, managed = true, notifyFunction, null, updateFunction)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> managedPropertyWithOldValue(
        initialValue: T,
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T, T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, skipUpdate, managed = true, notifyFunction, null, updateFunction)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> unmanagedPropertyWithOldValue(
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T, T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, skipUpdate, managed = false, notifyFunction, null, updateFunction)

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> unmanagedPropertyWithOldValue(
        initialValue: T,
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T, T) -> Unit) = { _, _ -> }
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, skipUpdate, managed = false, notifyFunction, null, updateFunction)

    protected inner class ManagedPropertyDelegateProvider<T>(
        private val initialValue: T?,
        private val skipUpdate: Boolean,
        private val managed: Boolean,
        private val notifyFunction: ((T) -> Unit)?,
        private val updateFunction: ((T) -> Unit)?,
        private val updateFunctionWithOldValue: ((T, T) -> Unit)?,
    ) {
        public operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): UpdatingPropertyDelegate<T> {
            if (initialValue != null) propertyValues[prop.name] = initialValue
            notifyFunction?.let { managedPropertyNotifyFunctions[prop.name] = notifyFunction }
            if (!skipUpdate && managed) {
                updateFunction?.let { managedPropertyUpdateFunctions[prop.name] = updateFunction }
                updateFunctionWithOldValue?.let {
                    managedPropertyUpdateFunctionsWithOldValue[prop.name] = updateFunctionWithOldValue
                }
            }
            return UpdatingPropertyDelegate(
                skipUpdate,
                notifyFunction,
                updateFunction,
                updateFunctionWithOldValue
            )
        }
    }

    protected inner class UpdatingPropertyDelegate<T>(
        private val skipUpdate: Boolean,
        private val notfiyFunction: ((T) -> Unit)?,
        private val updateFunction: ((T) -> Unit)?,
        private val updateFunctionWithOldValue: ((T, T) -> Unit)?
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
            val oldValue = propertyValues[property.name].cast<T>()
            if (oldValue != value) {
                if (value == null) {
                    propertyValues.remove(property.name)
                } else {
                    propertyValues[property.name] = value
                }
                notfiyFunction?.invoke(value)
                if (!skipUpdate) {
                    updateFunction?.invoke(value)
                    updateFunctionWithOldValue?.invoke(value, oldValue)
                }
            }
        }
    }

    public fun <T> updateManagedProperty(property: KProperty<*>, value: T) {
        if (!managedPropertiesSet.contains(property.name)) {
            val oldValue = propertyValues[property.name].cast<T>()
            if (oldValue != value) {
                if (value == null) {
                    propertyValues.remove(property.name)
                } else {
                    propertyValues[property.name] = value
                }
            }
            managedPropertyUpdateFunctions[property.name]?.cast<(T) -> Unit>()?.invoke(value)
            managedPropertyUpdateFunctionsWithOldValue[property.name]?.cast<(T, T) -> Unit>()?.invoke(value, oldValue)
        }
    }
}
