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

package dev.kilua.form.color

import dev.kilua.test.DomSpec
import dev.kilua.compose.root
import dev.kilua.test.normalizeHtml
import kotlin.test.Test
import kotlin.test.assertEquals

class ColorPickerSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                colorPicker("#ffffff", name = "test") {
                    autofocus(true)
                }
            }
            assertEquals(
                normalizeHtml("""<input type="color" name="test" autofocus="">"""),
                normalizeHtml(root.element.innerHTML),
                "Should render color picker element to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                colorPicker("#ffffff", name = "test") {
                    autofocus(true)
                }
            }
            assertEquals(
                normalizeHtml("""<div><input type="color" name="test" autofocus></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render color picker element to a String"
            )
        }
    }
}
