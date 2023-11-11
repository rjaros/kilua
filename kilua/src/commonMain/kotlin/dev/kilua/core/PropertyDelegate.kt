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
public open class PropertyDelegate(
    protected val propertyValues: MutableMap<String, Any>,
    protected val onSetCallback: ((values: Map<String, Any>) -> Unit)? = null
) {
    protected val notifyFunctions: MutableMap<String, Any> = nativeMapOf()
    protected val updateFunctions: MutableMap<String, Any> = nativeMapOf()
    protected val updateFunctionsWithOldValue: MutableMap<String, Any> = nativeMapOf()
    protected val propertiesSet: MutableSet<String> = mutableSetOf()

    /**
     * Create a property with a custom update and notify functions.
     * @param skipUpdate if true, the update function will not be called when the property is set
     * @param name the name of the property
     * @param notifyFunction the notify function
     * @param updateFunction the update function
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> updatingProperty(
        skipUpdate: Boolean = false,
        name: String? = null,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T) -> Unit)? = null,
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, skipUpdate, name, notifyFunction, updateFunction, null)

    /**
     * Create a property with a custom update and notify functions.
     * @param initialValue initial value of the property
     * @param skipUpdate if true, the update function will not be called when the property is set
     * @param name the name of the property
     * @param notifyFunction the notify function
     * @param updateFunction the update function
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> updatingProperty(
        initialValue: T,
        skipUpdate: Boolean = false,
        name: String? = null,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, skipUpdate, name, notifyFunction, updateFunction, null)

    /**
     * Create a property with a custom update and notify functions.
     * @param skipUpdate if true, the update function will not be called when the property is set
     * @param name the name of the property
     * @param notifyFunction the notify function
     * @param updateFunction the update function
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> updatingPropertyWithOldValue(
        skipUpdate: Boolean = false,
        name: String? = null,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T, T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, skipUpdate, name, notifyFunction, null, updateFunction)

    @Suppress("NOTHING_TO_INLINE")
    /**
     * Create a property with a custom update and notify functions.
     * @param initialValue initial value of the property
     * @param skipUpdate if true, the update function will not be called when the property is set
     * @param name the name of the property
     * @param notifyFunction the notify function
     * @param updateFunction the update function
     */
    protected inline fun <T> updatingPropertyWithOldValue(
        initialValue: T,
        skipUpdate: Boolean = false,
        name: String? = null,
        noinline notifyFunction: ((T) -> Unit)? = null,
        noinline updateFunction: ((T, T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, skipUpdate, name, notifyFunction, null, updateFunction)

    /**
     * A delegate provider.
     */
    protected inner class ManagedPropertyDelegateProvider<T>(
        private val initialValue: T?,
        private val skipUpdate: Boolean,
        private val name: String?,
        private val notifyFunction: ((T) -> Unit)?,
        private val updateFunction: ((T) -> Unit)?,
        private val updateFunctionWithOldValue: ((T, T) -> Unit)?,
    ) {
        public operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): UpdatingPropertyDelegate<T> {
            val propName = name ?: prop.name
            if (initialValue != null) propertyValues[propName] = initialValue
            notifyFunction?.let { notifyFunctions[propName] = notifyFunction }
            if (!skipUpdate) {
                updateFunction?.let { updateFunctions[propName] = updateFunction }
                updateFunctionWithOldValue?.let {
                    updateFunctionsWithOldValue[propName] = updateFunctionWithOldValue
                }
            }
            return UpdatingPropertyDelegate(
                skipUpdate,
                propName,
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
        private val propName: String,
        private val notifyFunction: ((T) -> Unit)?,
        private val updateFunction: ((T) -> Unit)?,
        private val updateFunctionWithOldValue: ((T, T) -> Unit)?
    ) {
        /**
         * Get the value of the property.
         */
        public operator fun getValue(thisRef: PropertyDelegate, property: KProperty<*>): T {
            val value = propertyValues[propName]
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
            propertiesSet.add(propName)
            val oldValue = propertyValues[propName].cast<T>()
            if (oldValue != value) {
                if (value == null) {
                    propertyValues.remove(propName)
                } else {
                    propertyValues[propName] = value
                }
                notifyFunction?.invoke(value)
                if (!skipUpdate) {
                    updateFunction?.invoke(value)
                    updateFunctionWithOldValue?.invoke(value, oldValue)
                }
                onSetCallback?.invoke(propertyValues)
            }
        }
    }

    /**
     * Update the value of the property with lower priority (called by the compose runtime).
     */
    public fun <T> updateProperty(property: String, value: T) {
        if (!propertiesSet.contains(property)) {
            val oldValue = propertyValues[property].cast<T>()
            if (oldValue != value) {
                if (value == null) {
                    propertyValues.remove(property)
                } else {
                    propertyValues[property] = value
                }
                updateFunctions[property]?.cast<(T) -> Unit>()?.invoke(value)
                updateFunctionsWithOldValue[property]?.cast<(T, T) -> Unit>()?.invoke(value, oldValue)
            }
        }
    }

    /**
     * Update the value of the property with lower priority (called by the compose runtime).
     */
    public fun <T> updateProperty(property: KProperty<*>, value: T) {
        updateProperty(property.name, value)
    }
}
