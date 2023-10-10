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

public actual open class NativeList<E>(private val internalList: MutableList<E> = mutableListOf()) :
    List<E> by internalList {

    public actual fun add(element: E): Boolean {
        return internalList.add(element)
    }

    public actual fun add(index: Int, element: E) {
        internalList.add(index, element)
    }

    public actual fun remove(element: E): Boolean {
        return internalList.remove(element)
    }

    public actual fun addAll(elements: Collection<E>): Boolean {
        return internalList.addAll(elements)
    }

    public actual fun clear() {
        internalList.clear()
    }

    public actual operator fun set(index: Int, element: E): E {
        return internalList.set(index, element)
    }

    public actual fun removeAt(index: Int): E {
        return internalList.removeAt(index)
    }

}

public actual fun <E> nativeListOf(vararg elements: E): NativeList<E> {
    return NativeList(mutableListOf(*elements))
}
