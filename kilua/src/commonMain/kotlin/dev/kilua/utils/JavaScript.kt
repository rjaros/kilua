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

import dev.kilua.externals.assign
import dev.kilua.externals.concat
import dev.kilua.externals.get
import dev.kilua.externals.isArray
import dev.kilua.externals.jsTypeOf
import dev.kilua.externals.keys
import dev.kilua.externals.obj
import dev.kilua.externals.set
import dev.kilua.externals.undefined
import dev.kilua.dom.JsAny
import dev.kilua.dom.get
import dev.kilua.dom.set
import dev.kilua.dom.toJsBoolean
import dev.kilua.dom.toJsNumber
import dev.kilua.dom.toJsString

/**
 * JavaScript encodeURIComponent function
 */
public external fun encodeURIComponent(value: String): String

/**
 * JavaScript decodeURIComponent function
 */
public external fun decodeURIComponent(value: String): String

/**
 * Utility extension function for casting. Uses unsafeCast() on JS.
 */
public expect inline fun <T> Any?.cast(): T

/**
 * Utility extension function for unsafe casting. Uses unsafeCast() on JS and WasmJs.
 */
public expect inline fun <T : JsAny> JsAny.unsafeCast(): T

/**
 * Convert JsArray to Kotlin Array.
 */
public inline fun <reified T : JsAny> dev.kilua.dom.JsArray<T>.toArray(): Array<T> {
    return Array(length) { get(it)!! }
}

/**
 * Convert Kotlin Array to JsArray.
 */
public fun <T : JsAny> Array<T>.toJsArray(): dev.kilua.dom.JsArray<T> {
    return jsArrayOf(*this)
}

/**
 * Convert JsArray to Kotlin List.
 */
public fun <T : JsAny> dev.kilua.dom.JsArray<T>.toList(): List<T> {
    return List(length) { get(it)!! }
}

/**
 * Convert Kotlin List to JsArray.
 */
public fun <T : JsAny> List<T>.toJsArray(): dev.kilua.dom.JsArray<T> {
    val array = dev.kilua.dom.JsArray<T>()
    for (i in this.indices) {
        array[i] = this[i]
    }
    return array
}

/**
 * Create a JsArray object.
 */
public fun <T : JsAny> jsArrayOf(vararg elements: T): dev.kilua.dom.JsArray<T> {
    val array = dev.kilua.dom.JsArray<T>()
    for (i in elements.indices) {
        array[i] = elements[i]
    }
    return array
}

/**
 * Convert a subset of Kotlin types (String, Int, Double, Boolean, Array, List, Map) to JS object.
 * All unsupported types are converted to null values on WasmJS or left unchanged on JS.
 */
public fun <T> T.toJsAny(): JsAny? {
    return when (this) {
        is String -> this.toJsString()
        is Int -> this.toJsNumber()
        is Double -> this.toJsNumber()
        is Boolean -> this.toJsBoolean()
        is Array<*> -> {
            val array = dev.kilua.dom.JsArray<JsAny?>()
            for (index in this.indices) {
                array[index] = this[index].toJsAny()
            }
            array
        }

        is List<*> -> {
            val array = dev.kilua.dom.JsArray<JsAny?>()
            for (index in this.indices) {
                array[index] = this[index].toJsAny()
            }
            array
        }

        is Map<*, *> -> {
            val obj = obj()
            for (entry in this.entries) {
                if (entry.value != null) {
                    obj[entry.key.toString()] = entry.value.toJsAny()!!
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
        return obj != null && obj != undefined() && jsTypeOf(obj) == "object"
    }
    if (!isObject(target) || !isObject(source)) return source
    for (key in keys(source)) {
        val targetValue = target[key]
        val sourceValue = source[key]
        if (isArray(targetValue) && isArray(sourceValue)) {
            target[key] = concat(targetValue!!.unsafeCast<dev.kilua.dom.JsArray<JsAny>>(), sourceValue.cast())
        } else if (isObject(targetValue) && isObject(sourceValue)) {
            val newObj = obj()
            assign(newObj, targetValue!!)
            target[key] = deepMerge(newObj, sourceValue!!)
        } else if (sourceValue != null) {
            target[key] = sourceValue
        }
    }
    return target
}
