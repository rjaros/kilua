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

package dev.kilua.utils

import js.array.ArrayLike
import js.core.JsPrimitives.toJsDouble
import js.core.JsPrimitives.toJsInt
import js.core.JsPrimitives.toKotlinString
import js.objects.Object
import js.objects.unsafeJso
import js.reflect.Reflect
import kotlin.js.JsAny
import kotlin.js.JsArray
import kotlin.js.set
import kotlin.js.toJsBoolean
import kotlin.js.toJsString
import kotlin.js.toList
import kotlin.js.undefined
import kotlin.js.unsafeCast

/**
 * Create a JS object with a block of code to set its properties.
 */
public inline fun <T : JsAny> obj(block: T.() -> Unit): T = unsafeJso(block)

/**
 * Create an empty JS object
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun obj(): JsAny = unsafeJso()

/**
 * Function to set property on JS Object
 */
public fun JsAny.jsSet(key: String, value: JsAny) {
    Reflect.set(this, key, value)
}

/**
 * Function to get property from JS Object
 */
public fun JsAny.jsGet(key: String): JsAny? {
    return Reflect.get(this, key)
}

/**
 * Get the list of keys from JS Object
 */
public fun keys(o: JsAny): List<String> =
    Object.keys(o).toList().map { it.toKotlinString() }

/**
 * Copies all properties from source object to target object
 */
public fun assign(target: JsAny, source: JsAny) {
    Object.assign(target, source)
}

/**
 * Delete a property from an object
 */
public expect fun delete(o: JsAny, key: String)

/**
 * Return native JS type of a given value
 */
public expect fun jsTypeOf(o: JsAny?): String

/**
 * Utility extension function for casting. Uses unsafeCast() on JS.
 */
public expect inline fun <T> Any?.cast(): T

/**
 * Returns whether the given value is an array
 */
public expect fun isArray(o: JsAny?): Boolean

/**
 * Merge two arrays.
 * Returns a new array containing the contents of both given arrays.
 */
public expect fun <T : JsAny> concat(array1: JsArray<T>, array2: JsArray<T>): JsArray<T>

/**
 * Convert ArrayLike to Kotlin List.
 */
public fun <T : JsAny> ArrayLike<T>.toList(): List<T> {
    return List(length) { get(it) }
}

/**
 * Convert a subset of Kotlin types (String, Int, Double, Boolean, Array, List, Map) to JS object.
 * All unsupported types are converted to null values on WasmJS or left unchanged on JS.
 */
public fun <T> T.toJsAny(): JsAny? {
    return when (this) {
        is String -> this.toJsString()
        is Int -> this.toJsInt()
        is Double -> this.toJsDouble()
        is Boolean -> this.toJsBoolean()
        is Array<*> -> {
            val array = JsArray<JsAny?>()
            for (index in this.indices) {
                array[index] = this[index].toJsAny()
            }
            array
        }

        is List<*> -> {
            val array = JsArray<JsAny?>()
            for (index in this.indices) {
                array[index] = this[index].toJsAny()
            }
            array
        }

        is Map<*, *> -> {
            val obj = obj()
            for (entry in this.entries) {
                if (entry.value != null) {
                    obj.jsSet(entry.key.toString(), entry.value.toJsAny()!!)
                }
            }
            obj
        }

        else -> this.cast()
    }
}

/**
 * Convert a map to a JS object.
 * All unsupported types are converted to null values on WasmJS or left unchanged on JS.
 */
public fun Map<String, Any?>.toJsAny(): JsAny = this.toJsAny<Map<String, Any?>>()!!

/**
 * Creates a JS object from a list of pairs.
 * All unsupported types are converted to null values on WasmJS or left unchanged on JS.
 */
public fun jsObjectOf(
    vararg pairs: Pair<String, Any?>
): JsAny = mapOf(*pairs).toJsAny()

/**
 * Helper function to deeply merge two JS objects.
 */
public fun deepMerge(target: JsAny, source: JsAny): JsAny {
    fun isObject(obj: JsAny?): Boolean {
        @Suppress("SENSELESS_COMPARISON")
        return obj != null && obj != undefined && jsTypeOf(obj) == "object"
    }
    if (!isObject(target) || !isObject(source)) return source
    for (key in keys(source)) {
        val targetValue = target.jsGet(key)
        val sourceValue = source.jsGet(key)
        if (isArray(targetValue) && isArray(sourceValue)) {
            target.jsSet(key, concat(targetValue!!.unsafeCast<JsArray<JsAny>>(), sourceValue.cast()))
        } else if (isObject(targetValue) && isObject(sourceValue)) {
            val newObj = obj()
            assign(newObj, targetValue!!)
            target.jsSet(key, deepMerge(newObj, sourceValue!!))
        } else if (sourceValue != null) {
            target.jsSet(key, sourceValue)
        }
    }
    return target
}
