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

import dev.kilua.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NativeListSpec : SimpleSpec {

    @Test
    fun nativeListOf() {
        val list = nativeListOf(1, 2, 3)
        assertEquals(3, list.size)
        assertEquals(1, list[0])
        assertEquals(2, list[1])
        assertEquals(3, list[2])
        assertFalse(list.isEmpty())
        assertTrue(list.contains(2))
        assertFalse(list.contains(5))
    }

    @Test
    fun set() {
        val list = nativeListOf(1, 2, 3)
        list[1] = 4
        assertEquals(1, list[0])
        assertEquals(4, list[1])
        assertEquals(3, list[2])
    }

    @Test
    fun add() {
        val list = nativeListOf(1, 2, 3)
        list.add(4)
        list.add(1, 5)
        list.addAll(listOf(6, 7))
        assertEquals(7, list.size)
        assertEquals(1, list[0])
        assertEquals(5, list[1])
        assertEquals(2, list[2])
        assertEquals(3, list[3])
        assertEquals(4, list[4])
        assertEquals(6, list[5])
        assertEquals(7, list[6])
    }

    @Test
    fun remove() {
        val list = nativeListOf(1, 2, 3)
        list.remove(1)
        list.removeAt(0)
        assertEquals(1, list.size)
        assertEquals(3, list[0])
    }

    @Test
    fun clear() {
        val list = nativeListOf(1, 2, 3)
        list.clear()
        assertEquals(0, list.size)
    }

    @Test
    fun forEach() {
        val list = nativeListOf(1, 2, 3)
        var counter = 0
        list.forEach {
            counter += it
        }
        assertEquals(6, counter)
    }
}
