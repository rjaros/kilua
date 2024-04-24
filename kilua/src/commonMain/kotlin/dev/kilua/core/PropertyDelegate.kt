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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.utils.cast
import dev.kilua.utils.nativeMapOf
import kotlin.collections.set
import kotlin.reflect.KProperty

/**
 * Helper delegate used to define properties with custom update and notify functions.
 */
public open class PropertyDelegate(
    internal val propertyValues: MutableMap<String, Any>,
    internal val onSetCallback: ((values: Map<String, Any>) -> Unit)? = null,
    internal val skipUpdates: Boolean = false,
) {
    protected var composablePropertyCounter: Int = 0
    protected val composablePropertyMap: MutableMap<String, Int> = nativeMapOf()

    protected val notifyFunctions: MutableMap<String, Any> = nativeMapOf()
    protected val updateFunctions: MutableMap<String, Any> = nativeMapOf()
    protected val updateFunctionsWithOldValue: MutableMap<String, Any> = nativeMapOf()
    internal val propertiesSet: MutableSet<String> = mutableSetOf()

    /**
     * Allows direct access to set the value in the propertyValues map.
     */
    protected fun setPropertyValue(name: String, value: Any?) {
        if (value != null) {
            propertyValues[name] = value
        } else {
            propertyValues.remove(name)
        }
    }

    /**
     * Create a property with a custom update function.
     * @param updateFunction the update function
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> updatingProperty(
        noinline updateFunction: ((T) -> Unit)? = null,
    ): SimpleUpdatingPropertyDelegate<T> =
        SimpleUpdatingPropertyDelegate(updateFunction)

    /**
     * Create a property with a custom update and notify functions.
     * @param name the name of the property
     * @param notifyFunction the notify function
     * @param updateFunction the update function
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> updatingProperty(
        noinline notifyFunction: ((T) -> Unit)? = null,
        name: String? = null,
        noinline updateFunction: ((T) -> Unit)? = null,
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, name, notifyFunction, updateFunction, null)

    /**
     * Create a property with a custom update and notify functions.
     * @param initialValue initial value of the property
     * @param name the name of the property
     * @param notifyFunction the notify function
     * @param updateFunction the update function
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> updatingProperty(
        initialValue: T,
        noinline notifyFunction: ((T) -> Unit)? = null,
        name: String? = null,
        noinline updateFunction: ((T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, name, notifyFunction, updateFunction, null)

    /**
     * Create a property with a custom update and notify functions.
     * @param name the name of the property
     * @param notifyFunction the notify function
     * @param updateFunction the update function
     */
    @Suppress("NOTHING_TO_INLINE")
    protected inline fun <T> updatingPropertyWithOldValue(
        noinline notifyFunction: ((T) -> Unit)? = null,
        name: String? = null,
        noinline updateFunction: ((T, T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(null, name, notifyFunction, null, updateFunction)

    @Suppress("NOTHING_TO_INLINE")
    /**
     * Create a property with a custom update and notify functions.
     * @param initialValue initial value of the property
     * @param name the name of the property
     * @param notifyFunction the notify function
     * @param updateFunction the update function
     */
    protected inline fun <T> updatingPropertyWithOldValue(
        initialValue: T,
        noinline notifyFunction: ((T) -> Unit)? = null,
        name: String? = null,
        noinline updateFunction: ((T, T) -> Unit)? = null
    ): ManagedPropertyDelegateProvider<T> =
        ManagedPropertyDelegateProvider(initialValue, name, notifyFunction, null, updateFunction)

    /**
     * A delegate provider.
     */
    protected inner class ManagedPropertyDelegateProvider<T>(
        private val initialValue: T?,
        private val name: String?,
        private val notifyFunction: ((T) -> Unit)?,
        private val updateFunction: ((T) -> Unit)?,
        private val updateFunctionWithOldValue: ((T, T) -> Unit)?,
    ) {
        public operator fun provideDelegate(thisRef: Any?, prop: KProperty<*>): UpdatingPropertyDelegate<T> {
            val propName = name ?: prop.name
            if (initialValue != null) propertyValues[propName] = initialValue
            notifyFunction?.let { notifyFunctions[propName] = notifyFunction }
            if (!skipUpdates) {
                updateFunction?.let { updateFunctions[propName] = updateFunction }
                updateFunctionWithOldValue?.let {
                    updateFunctionsWithOldValue[propName] = updateFunctionWithOldValue
                }
            }
            return UpdatingPropertyDelegate(
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
                if (!skipUpdates) {
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
                notifyFunctions[property]?.cast<(T) -> Unit>()?.invoke(value)
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

    @Composable
    protected fun composableProperty(property: String, remover: (() -> Unit)? = null, updater: () -> Unit) {
        val id = remember { composablePropertyCounter++ }
        updater()
        composablePropertyMap[property] = id
        DisposableEffect(id) {
            onDispose {
                if (composablePropertyMap[property] == id) {
                    remover?.invoke()
                    composablePropertyMap.remove(property)
                }
            }
        }
    }
}

/**
 * A simplified delegate for a property with a custom update function
 * implemented as a value class for optimization.
 */
public value class SimpleUpdatingPropertyDelegate<T>(
    private val updateFunction: ((T) -> Unit)?,
) {
    /**
     * Get the value of the property.
     */
    public operator fun getValue(thisRef: PropertyDelegate, property: KProperty<*>): T {
        val value = thisRef.propertyValues[property.name]
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
        thisRef.propertiesSet.add(property.name)
        val oldValue = thisRef.propertyValues[property.name].cast<T>()
        if (oldValue != value) {
            if (value == null) {
                thisRef.propertyValues.remove(property.name)
            } else {
                thisRef.propertyValues[property.name] = value
            }
            if (!thisRef.skipUpdates) {
                updateFunction?.invoke(value)
            }
            thisRef.onSetCallback?.invoke(thisRef.propertyValues)
        }
    }
}
