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

public actual class NativeList<E> : MutableList<E> {

    private val nativeList: Array<E> = emptyArray()

    override fun add(element: E): Boolean {
        nativeList.asDynamic().push(element)
        return true
    }

    override fun remove(element: E): Boolean {
        nativeList.indexOf(element).let {
            if (it >= 0) {
                nativeList.asDynamic().splice(it, 1)
                return true
            }
        }
        return false
    }

    override fun clear() {
        nativeList.asDynamic().splice(0, nativeList.size)
    }

    override operator fun set(index: Int, element: E): E {
        val oldElement = nativeList[index]
        nativeList[index] = element
        return oldElement
    }

    override fun add(index: Int, element: E) {
        nativeList.asDynamic().splice(index, 0, element)
    }

    @Suppress("UnsafeCastFromDynamic")
    override fun addAll(elements: Collection<E>): Boolean {
        if (elements.isNotEmpty()) {
            elements.forEach {
                nativeList.asDynamic().push(it)
            }
            return true
        }
        return false
    }

    override fun removeAt(index: Int): E {
        val element = nativeList[index]
        nativeList.asDynamic().splice(index, 1)
        return element
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E> {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun retainAll(elements: Collection<E>): Boolean {
        throw UnsupportedOperationException("Not implemented")
    }

    override val size: Int
        get() = nativeList.size

    override fun addAll(index: Int, elements: Collection<E>): Boolean {
        if (elements.isNotEmpty()) {
            elements.forEach { element ->
                nativeList.asDynamic().splice(index, 0, element)
            }
            return true
        }
        return false
    }

    override fun get(index: Int): E {
        return nativeList[index]
    }

    override fun isEmpty(): Boolean {
        return nativeList.isEmpty()
    }

    override fun iterator(): MutableIterator<E> {
        return nativeList.toMutableList().iterator()
    }

    override fun listIterator(): MutableListIterator<E> {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun listIterator(index: Int): MutableListIterator<E> {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun removeAll(elements: Collection<E>): Boolean {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun lastIndexOf(element: E): Int {
        return nativeList.lastIndexOf(element)
    }

    override fun indexOf(element: E): Int {
        return nativeList.indexOf(element)
    }

    override fun containsAll(elements: Collection<E>): Boolean {
        return nativeList.asList().containsAll(elements)
    }

    override fun contains(element: E): Boolean {
        return nativeList.contains(element)
    }

}

public actual fun <E> nativeListOf(vararg elements: E): MutableList<E> {
    val list = NativeList<E>()
    if (elements.isNotEmpty()) {
        elements.forEach {
            list.add(it)
        }
    }
    return list
}
