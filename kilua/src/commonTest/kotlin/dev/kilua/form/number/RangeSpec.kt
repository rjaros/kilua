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

package dev.kilua.form.number

import dev.kilua.DomSpec
import dev.kilua.compose.root
import dev.kilua.normalizeHtml
import kotlin.test.Test
import kotlin.test.assertEquals

class RangeSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                range(15, 10, 20, name = "test") {
                    autofocus = true
                }
            }
            assertEquals(
                normalizeHtml("""<input type="range" name="test" min="10" max="20" step="1" autofocus="">"""),
                normalizeHtml(root.element?.innerHTML),
                "Should render range input element to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                range(15, 10, 20, name = "test") {
                    autofocus = true
                }
            }
            assertEquals(
                normalizeHtml("""<div><input type="range" name="test" autofocus min="10" max="20" step="1"></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render range input element to a String"
            )
        }
    }

    @Test
    fun stepUpDown() {
        run {
            lateinit var range: Range
            root("test") {
                range = range(19, 10, 20, step = 0.5) {
                    autofocus = true
                }
            }
            repeat(4) {
                range.stepUp()
            }
            assertEquals(20.0, range.value)
            range.stepDown()
            assertEquals(19.5, range.value)
        }
    }
}
