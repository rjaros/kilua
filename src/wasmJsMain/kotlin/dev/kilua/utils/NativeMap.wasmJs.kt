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

public actual class NativeMap<out V>(private val internalMap: MutableMap<String, V> = mutableMapOf()) :
    Map<String, V> by internalMap {

    public actual fun clear() {
        internalMap.clear()
    }

    public actual fun remove(key: String): V? {
        return internalMap.remove(key)
    }

    public actual fun putAll(from: Map<out String, @UnsafeVariance V>) {
        internalMap.putAll(from)
    }

    public actual fun put(key: String, value: @UnsafeVariance V): V? {
        return internalMap.put(key, value)
    }
}

public actual fun <V> nativeMapOf(vararg pairs: Pair<String, V>): NativeMap<V> {
    return NativeMap(mutableMapOf(*pairs))
}
