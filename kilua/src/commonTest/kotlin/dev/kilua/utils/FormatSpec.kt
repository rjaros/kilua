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

class FormatSpec : SimpleSpec {

    @Test
    fun toFixed() {
        val formatted = 123.346.toFixed(2)
        assertEquals("123.35", formatted)
    }

    @Test
    fun toCamelCase() {
        val marginTop = "margin-top".toCamelCase()
        assertEquals("marginTop", marginTop, "Should convert a kebab-case string to camelCase")
    }

    @Test
    fun toKebabCase() {
        val marginTop = "marginTop".toKebabCase()
        assertEquals("margin-top", marginTop, "Should convert a camelCase string to kebab-case")
    }

    @Test
    fun toFixedNoRound() {
        val formatted = 10433.38.toFixedNoRound(2)
        assertEquals("10433.38", formatted)
        val formatted2 = 10433.3.toFixedNoRound(2)
        assertEquals("10433.3", formatted2)
        val formatted3 = (-10433.389).toFixedNoRound(2)
        assertEquals("-10433.38", formatted3)
    }
}
