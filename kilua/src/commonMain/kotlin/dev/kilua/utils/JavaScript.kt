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

import dev.kilua.externals.obj
import dev.kilua.externals.set
import web.JsAny
import web.JsArray
import web.get
import web.set
import web.toJsBoolean
import web.toJsNumber
import web.toJsString

/**
 * Utility extension function for casting. Uses unsafeCast() on JS.
 */
public expect inline fun <T> Any?.cast(): T

/**
 * Helper annotation for JS/Wasm compatibility.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION, AnnotationTarget.FILE)
public expect annotation class JsModule(val import: String)

/**
 * Helper annotation for JS/Wasm compatibility.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY, AnnotationTarget.FUNCTION, AnnotationTarget.FILE)
public expect annotation class JsNonModule()

/**
 * Helper function for JS/Wasm compatibility.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> useModule(@Suppress("UNUSED_PARAMETER") module: T) {
    // empty body
}

/**
 * Convert JsArray to Kotlin Array.
 */
public inline fun <reified T : JsAny> JsArray<T>.toArray(): Array<T> {
    return Array(length) { get(it)!! }
}

/**
 * Convert Kotlin Array to JsArray.
 */
public fun <T : JsAny> Array<T>.toJsArray(): JsArray<T> {
    return jsArrayOf(*this)
}

/**
 * Convert JsArray to Kotlin List.
 */
public fun <T : JsAny> JsArray<T>.toList(): List<T> {
    return List(length) { get(it)!! }
}

/**
 * Convert Kotlin List to JsArray.
 */
public inline fun <reified T : JsAny> List<T>.toJsArray(): JsArray<T> {
    return jsArrayOf(*this.toTypedArray())
}

/**
 * Create a JsArray object.
 */
public fun <T : JsAny> jsArrayOf(vararg elements: T): JsArray<T> {
    val array = JsArray<T>()
    for (i in elements.indices) {
        array[i] = elements[i]
    }
    return array
}

/**
 * Convert a subset of Kotlin types (String, Int, Double, Boolean, Array, List, Map) to JS object.
 * All unsupported types are converted to null values.
 */
public fun <T> T.toJsAny(): JsAny? {
    return when (this) {
        is String -> this.toJsString()
        is Int -> this.toJsNumber()
        is Double -> this.toJsNumber()
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
                    obj[entry.key.toString()] = entry.value.toJsAny()!!
                }
            }
            obj
        }

        else -> null
    }
}
