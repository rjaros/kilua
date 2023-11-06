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

package dev.kilua.html.style

import dev.kilua.compose.root
import dev.kilua.html.Color
import dev.kilua.html.px
import dev.kilua.test.DomSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class CssStyleSpec : DomSpec {

    @Test
    fun render() {
        run {
            lateinit var className: String
            root("test") {
                className = style(".test") {
                    margin = 10.px
                    style("h1") {
                        color = Color.Red
                    }
                }
            }
            assertEquals(2, StyleParams.styles.size, "Should generate CSS style objects")
            assertEquals("test", className, "Should return the correct CSS class name")
            assertEquals(
                ".test { margin: 10px; }",
                StyleParams.styles.values.first().renderAsCss(),
                "Should render the correct CSS rule"
            )
            assertEquals(
                ".test h1 { color: red; }",
                StyleParams.styles.values.last().renderAsCss(),
                "Should render the correct cascading CSS rule"
            )
        }
    }
}
