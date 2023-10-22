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

package dev.kilua.utils

public actual external class Object

@Suppress("NOTHING_TO_INLINE", "UnsafeCastFromDynamic")
public actual inline fun obj(): Object {
    return js("{}")
}

public actual val isDom: Boolean by lazy {
    js("typeof document !== 'undefined'")
}

public actual typealias JsNonModule = kotlin.js.JsNonModule

/**
 * Helper function for creating JavaScript objects with given type.
 */
public inline fun <T : Any> obj(init: T.() -> Unit): T {
    return (js("{}").unsafeCast<T>()).apply(init)
}

/**
 * Helper function for creating JavaScript objects.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun obj(noinline init: dynamic.() -> Unit): dynamic {
    return (js("{}")).apply(init)
}

public external fun delete(p: dynamic): Boolean

public fun delete(thing: dynamic, key: String) {
    delete(thing[key])
}

public actual fun size(array: Object): Int = array.cast<Array<*>>().size

public actual fun jsString(value: String): Object {
    return value.unsafeCast<Object>()
}
