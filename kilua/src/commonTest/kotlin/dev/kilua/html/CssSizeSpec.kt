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

package dev.kilua.html

import dev.kilua.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CssSizeSpec : SimpleSpec {

    @Test
    fun toStringFunc() {
        val size = 10.px
        assertEquals("10px", size.toString())
        val size2 = auto
        assertEquals("auto", size2.toString())
        val size3 = normal
        assertEquals("normal", size3.toString())
    }

    @Test
    fun plus() {
        val size = 10.pt
        val size2 = size + 20
        val str = size2.toString()
        assertTrue(str.startsWith("30") && str.endsWith("pt"))
    }

    @Test
    fun minus() {
        val size = 50.perc
        val size2 = size - 10
        val str = size2.toString()
        assertTrue(str.startsWith("40") && str.endsWith("%"))
    }

}

