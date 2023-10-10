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

public actual class NativeMap<out V> : Map<String, V> {

    private var nativeMap: dynamic = js("{}")

    protected val keysArray: Array<String>
        get() = js("Object").keys(nativeMap).unsafeCast<Array<String>>()

    override val entries: Set<Map.Entry<String, V>>
        get() = keysArray.mapNotNull {
            get(it)?.let { value ->
                object : Map.Entry<String, V> {
                    override val key: String = it
                    override val value: V = value
                }
            }
        }.toSet()
    override val keys: Set<String>
        get() = keysArray.toSet()


    override val size: Int
        get() = keysArray.size
    override val values: Collection<V>
        get() = keysArray.mapNotNull { get(it) }

    public actual fun clear() {
        nativeMap = js("{}")
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    public actual fun remove(key: String): V? {
        val obj = nativeMap[key]
        delete(nativeMap, key)
        @Suppress("UnsafeCastFromDynamic")
        return obj
    }

    public actual fun putAll(from: Map<out String, @UnsafeVariance V>) {
        from.forEach {
            nativeMap[it.key] = it.value
        }
    }

    public actual fun put(key: String, value: @UnsafeVariance V): V? {
        val obj = nativeMap[key]
        nativeMap[key] = value
        @Suppress("UnsafeCastFromDynamic")
        return obj
    }

    @Suppress("UnsafeCastFromDynamic")
    override operator fun get(key: String): V? {
        return nativeMap[key]
    }

    override fun containsValue(value: @UnsafeVariance V): Boolean {
        return keysArray.find {
            nativeMap[it] == value
        } != null
    }

    override fun containsKey(key: String): Boolean {
        return keysArray.contains(key)
    }

}

public actual fun <V> nativeMapOf(vararg pairs: Pair<String, V>): NativeMap<V> {
    val map = NativeMap<V>()
    if (pairs.isNotEmpty()) {
        map.putAll(pairs.toMap())
    }
    return map
}
