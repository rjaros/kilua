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

package dev.kilua.externals

public actual external class Object

@Suppress("NOTHING_TO_INLINE", "UnsafeCastFromDynamic")
public actual inline fun obj(): Object {
    return js("{}")
}

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

/**
 * Operator to set property on JS Object
 */
public actual operator fun Object.set(key: String, value: Object) {
    this.asDynamic()[key] = value
}

/**
 * Operator to get property from JS Object
 */
@Suppress("UnsafeCastFromDynamic")
public actual operator fun Object.get(key: String): Object? {
    return this.asDynamic()[key]
}

/**
 * Get the list of keys from JS Object
 */
public actual fun keys(o: Object): List<String> {
    return js("Object").keys(o).unsafeCast<Array<String>>().toList()
}

/**
 * Delete property from JavaScript object.
 */
public external fun delete(p: dynamic): Boolean

/**
 * Delete property from JavaScript object by key.
 */
public fun delete(thing: dynamic, key: String) {
    delete(thing[key])
}

/**
 * Convert String value to JS Object for JS/Wasm interop
 */
public actual fun String.toJsObject(): Object {
    return this.unsafeCast<Object>()
}

/**
 * Convert Boolean value to JS Object for JS/Wasm interop
 */
public actual fun Boolean.toJsObject(): Object {
    return this.unsafeCast<Object>()
}

/**
 * Convert Int value to JS Object for JS/Wasm interop
 */
public actual fun Int.toJsObject(): Object {
    return this.unsafeCast<Object>()
}

/**
 * Convert Double value to JS Object for JS/Wasm interop
 */
public actual fun Double.toJsObject(): Object {
    return this.unsafeCast<Object>()
}
