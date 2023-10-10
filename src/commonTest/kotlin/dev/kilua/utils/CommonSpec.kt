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
import kotlin.test.assertTrue

class CommonSpec : SimpleSpec {

    @Test
    fun asString() {
        val size = 10.px
        assertEquals("10px", size.asString())
        val size2 = auto
        assertEquals("auto", size2.asString())
        val size3 = normal
        assertEquals("normal", size3.asString())
    }

    @Test
    fun plus() {
        val size = 10.pt
        val size2 = size + 20
        val str = size2.asString()
        assertTrue(str.startsWith("30") && str.endsWith("pt"))
    }

    @Test
    fun minus() {
        val size = 50.perc
        val size2 = size - 10
        val str = size2.asString()
        assertTrue(str.startsWith("40") && str.endsWith("%"))
    }

    @Test
    fun syncWithList() {
        val list = mutableListOf(1, 2, 3)
        list.syncWithList(listOf(2, 3))
        assertEquals(list.toString(), listOf(2, 3).toString())
    }

    @Test
    fun pairs() {
        val pairs = listOf("A", "B", "C").pairs()
        assertEquals(listOf("A" to "A", "B" to "B", "C" to "C"), pairs)
    }

    @Test
    fun listOfPairs() {
        val list = listOfPairs("A", "B", "C")
        assertEquals(listOf("A" to "A", "B" to "B", "C" to "C"), list)
    }

    @Test
    fun renderAsHtmlAttributes() {
        val props = mapOf("zIndex" to "3", "ariaLabel" to "test", "href" to "https://google.com")
        val str = props.renderAsHtmlAttributes()
        assertEquals("""z-index="3" aria-label="test" href="https://google.com"""", str)
    }

    @Test
    fun renderAsCssStyle() {
        val props = mapOf("border" to "1px solid red", "color" to "blue", "marginTop" to "10px")
        val str = props.renderAsCssStyle()
        assertEquals("border: 1px solid red; color: blue; margin-top: 10px;", str)
    }

}
