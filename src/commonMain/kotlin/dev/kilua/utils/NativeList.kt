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

public expect class NativeList<E> : MutableList<E> {
    override val size: Int

    override fun contains(element: E): Boolean

    override fun containsAll(elements: Collection<E>): Boolean

    override fun get(index: Int): E

    override fun indexOf(element: E): Int

    override fun isEmpty(): Boolean

    override fun iterator(): MutableIterator<E>

    override fun lastIndexOf(element: E): Int

    override fun add(element: E): Boolean

    override fun add(index: Int, element: E)

    override fun addAll(index: Int, elements: Collection<E>): Boolean

    override fun addAll(elements: Collection<E>): Boolean

    override fun clear()

    override fun listIterator(): MutableListIterator<E>

    override fun listIterator(index: Int): MutableListIterator<E>

    override fun remove(element: E): Boolean

    override fun removeAll(elements: Collection<E>): Boolean

    override fun removeAt(index: Int): E

    override fun retainAll(elements: Collection<E>): Boolean

    override fun set(index: Int, element: E): E

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<E>
}

public expect fun <E> nativeListOf(vararg elements: E): MutableList<E>
