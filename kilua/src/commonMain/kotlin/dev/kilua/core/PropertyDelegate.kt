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

/**
 * Helper delegate used to define properties with custom update and notify functions.
 */
public open class PropertyDelegate(protected val propertyValues: MutableMap<String, Any>) {
    protected val notifyFunctions: MutableMap<String, Any> = nativeMapOf()
    protected val updateFunctions: MutableMap<String, Any> = nativeMapOf()
    protected val updateFunctionsWithOldValue: MutableMap<String, Any> = nativeMapOf()
    protected val propertiesSet: MutableSet<String> = mutableSetOf()

    /**
     * Create a property with a custom update and notify functions.
     * @param skipUpdate if true, the update function will not be called when the property is set
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> updatingProperty(
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T) -> Unit)? = null,
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, skipUpdate, notifyFunction, updateFunction, null)

    /**
     * Create a property with a custom update and notify functions.
     * @param initialValue initial value of the property
     * @param skipUpdate if true, the update function will not be called when the property is set
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> updatingProperty(
        initialValue: T,
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, skipUpdate, notifyFunction, updateFunction, null)

    /**
     * Create a property with a custom update and notify functions.
     * @param skipUpdate if true, the update function will not be called when the property is set
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> updatingPropertyWithOldValue(
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T, T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, skipUpdate, notifyFunction, null, updateFunction)

    @Suppress("NOTHING_TO_INLINE")
    /**
     * Create a property with a custom update and notify functions.
     * @param initialValue initial value of the property
     * @param skipUpdate if true, the update function will not be called when the property is set
     */
    protected inline fun <T> updatingPropertyWithOldValue(
        initialValue: T,
        skipUpdate: Boolean = false,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T, T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, skipUpdate, notifyFunction, null, updateFunction)

    /**
     * A delegate provider.
     */
    protected inner class ManagedPropertyDelegateProvider<T>(
        private val initialValue: T?,
        private val skipUpdate: Boolean,
        private val notifyFunction: ((T) -> Unit)?,
        private val updateFunction: ((T) -> Unit)?,
        private val updateFunctionWithOldValue: ((T, T) -> Unit)?,
    ) {
        public operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): UpdatingPropertyDelegate<T> {
            if (initialValue != null) propertyValues[prop.name] = initialValue
            notifyFunction?.let { notifyFunctions[prop.name] = notifyFunction }
            if (!skipUpdate) {
                updateFunction?.let { updateFunctions[prop.name] = updateFunction }
                updateFunctionWithOldValue?.let {
                    updateFunctionsWithOldValue[prop.name] = updateFunctionWithOldValue
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

    /**
     * A delegate for a property with custom update and notify functions.
     */
    protected inner class UpdatingPropertyDelegate<T>(
        private val skipUpdate: Boolean,
        private val notifyFunction: ((T) -> Unit)?,
        private val updateFunction: ((T) -> Unit)?,
        private val updateFunctionWithOldValue: ((T, T) -> Unit)?
    ) {
        /**
         * Get the value of the property.
         */
        public operator fun getValue(thisRef: PropertyDelegate, property: KProperty<*>): T {
            val value = propertyValues[property.name]
            return if (value != null) {
                value.cast()
            } else {
                null.cast()
            }
        }

        /**
         * Set the value of the property.
         */
        public operator fun setValue(thisRef: PropertyDelegate, property: KProperty<*>, value: T) {
            propertiesSet.add(property.name)
            val oldValue = propertyValues[property.name].cast<T>()
            if (oldValue != value) {
                if (value == null) {
                    propertyValues.remove(property.name)
                } else {
                    propertyValues[property.name] = value
                }
                notifyFunction?.invoke(value)
                if (!skipUpdate) {
                    updateFunction?.invoke(value)
                    updateFunctionWithOldValue?.invoke(value, oldValue)
                }
            }
        }
    }

    /**
     * Update the value of the property with lower priority (called by the compose runtime).
     */
    public fun <T> updateProperty(property: KProperty<*>, value: T) {
        if (!propertiesSet.contains(property.name)) {
            val oldValue = propertyValues[property.name].cast<T>()
            if (oldValue != value) {
                if (value == null) {
                    propertyValues.remove(property.name)
                } else {
                    propertyValues[property.name] = value
                }
            }
            updateFunctions[property.name]?.cast<(T) -> Unit>()?.invoke(value)
            updateFunctionsWithOldValue[property.name]?.cast<(T, T) -> Unit>()?.invoke(value, oldValue)
        }
    }
}
