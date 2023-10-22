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

import dev.kilua.externals.delete

public actual class NativeMap<V> : MutableMap<String, V> {

    private var nativeMap: dynamic = js("{}")

    protected val keysArray: Array<String>
        get() = js("Object").keys(nativeMap).unsafeCast<Array<String>>()

    actual override val entries: MutableSet<MutableMap.MutableEntry<String, V>>
        get() = keysArray.mapNotNull {
            get(it)?.let { value ->
                object : MutableMap.MutableEntry<String, V> {
                    override val key: String = it
                    override val value: V = value
                    override fun setValue(newValue: V): V {
                        val oldValue = nativeMap[it]
                        nativeMap[it] = newValue
                        @Suppress("UnsafeCastFromDynamic")
                        return oldValue
                    }
                }
            }
        }.toMutableSet()
    actual override val keys: MutableSet<String>
        get() = keysArray.toMutableSet()


    actual override val size: Int
        get() = keysArray.size
    actual override val values: MutableCollection<V>
        get() = keysArray.mapNotNull { get(it) }.toMutableList()

    actual override fun clear() {
        nativeMap = js("{}")
    }

    actual override fun isEmpty(): Boolean {
        return size == 0
    }

    actual override fun remove(key: String): V? {
        val obj = nativeMap[key]
        delete(nativeMap, key)
        @Suppress("UnsafeCastFromDynamic")
        return obj
    }

    actual override fun putAll(from: Map<out String, V>) {
        from.forEach {
            nativeMap[it.key] = it.value
        }
    }

    actual override fun put(key: String, value: V): V? {
        val obj = nativeMap[key]
        nativeMap[key] = value
        @Suppress("UnsafeCastFromDynamic")
        return obj
    }

    @Suppress("UnsafeCastFromDynamic")
    actual override operator fun get(key: String): V? {
        return nativeMap[key]
    }

    actual override fun containsValue(value: V): Boolean {
        return keysArray.find {
            nativeMap[it] == value
        } != null
    }

    actual override fun containsKey(key: String): Boolean {
        return keysArray.contains(key)
    }

}

public actual fun <V> nativeMapOf(vararg pairs: Pair<String, V>): MutableMap<String, V> {
    val map = NativeMap<V>()
    if (pairs.isNotEmpty()) {
        map.putAll(pairs.toMap())
    }
    return map
}
