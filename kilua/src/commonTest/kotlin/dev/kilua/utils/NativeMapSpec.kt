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

import dev.kilua.test.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NativeMapSpec : SimpleSpec {

    @Test
    fun nativeMapOf() {
        val map = nativeMapOf("1" to 2, "2" to 3, "3" to 4)
        assertEquals(3, map.size)
        assertEquals(2, map["1"])
        assertEquals(3, map["2"])
        assertEquals(4, map["3"])
        assertFalse(map.isEmpty())
        assertTrue(map.containsKey("2"))
        assertFalse(map.containsKey("5"))
    }

    @Test
    fun entries() {
        val map = nativeMapOf("1" to 2, "2" to 3, "3" to 4)
        assertEquals(setOf("1", "2", "3"), map.entries.map { it.key }.toSet())
        assertEquals(setOf(2, 3, 4), map.entries.map { it.value }.toSet())
    }

    @Test
    fun keys() {
        val map = nativeMapOf("1" to 2, "2" to 3, "3" to 4)
        assertEquals(setOf("1", "2", "3"), map.keys)
    }

    @Test
    fun values() {
        val map = nativeMapOf("1" to 2, "2" to 3, "3" to 4)
        assertEquals(listOf(2, 3, 4).toString(), map.values.toString())
    }

    @Test
    fun remove() {
        val map = nativeMapOf("1" to 2, "2" to 3, "3" to 4)
        map.remove("2")
        assertEquals(setOf("1", "3"), map.keys)
        assertEquals(listOf(2, 4).toString(), map.values.toString())
    }

    @Test
    fun clear() {
        val map = nativeMapOf("1" to 2, "2" to 3, "3" to 4)
        map.clear()
        assertTrue(map.isEmpty())
    }

    @Test
    fun forEach() {
        val map = nativeMapOf("1" to 2, "2" to 3, "3" to 4)
        var counter = 0
        map.forEach { (_, value) ->
            counter += value
        }
        assertEquals(9, counter)
    }

}
