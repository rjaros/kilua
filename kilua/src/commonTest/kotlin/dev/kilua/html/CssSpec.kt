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

import dev.kilua.test.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class CssSpec : SimpleSpec {

    @Test
    fun colorHex() {
        run {
            val color = Color.hex(0x112233)
            assertEquals(
                "#112233",
                color.toString(),
                "Should generate color from hex integer"
            )
            val colorWithAlpha = Color.hex(0xFF3B5C56)
            assertEquals(
                "#ff3b5c56",
                colorWithAlpha.toString(),
                "Should generate color from hex long value"
            )
        }
    }

    @Test
    fun colorName() {
        run {
            val color = Color.Blue
            assertEquals(
                "blue",
                color.toString(),
                "Should generate color from name"
            )
        }
    }

    @Test
    fun colorRgb() {
        run {
            val color = Color.rgb(17, 34, 51)
            assertEquals(
                "#112233",
                color.toString(),
                "Should generate color from RGB values"
            )
        }
    }

    @Test
    fun colorRgba() {
        run {
            val color = Color.rgba(17, 34, 51, 221)
            assertEquals(
                "#112233dd",
                color.toString(),
                "Should generate color from RGBA values"
            )
        }
    }

    @Test
    fun colorCopy() {
        run {
            val color = Color.rgb(17, 34, 51).copy(green = 51)
            assertEquals(
                "#113333",
                color.toString(),
                "Should copy color with new channel values"
            )
            val colorAlpha = Color.rgba(17, 34, 51, 221).copy(green = 51, alpha = 255)
            assertEquals(
                "#113333ff",
                colorAlpha.toString(),
                "Should copy color with alpha channel with new channel values"
            )
        }
    }
}
