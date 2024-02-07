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

import dev.kilua.ImaskModule
import dev.kilua.compose.root
import dev.kilua.i18n.SimpleLocale
import dev.kilua.test.DomSpec
import dev.kilua.test.normalizeHtml
import kotlin.test.Test
import kotlin.test.assertEquals

class ImaskNumericSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            ImaskModule.initialize()
            val root = root("test") {
                imaskNumeric(1235.321, locale = SimpleLocale("en")) {
                    defaultValue = 1235.321
                }
            }
            assertEquals(
                normalizeHtml("""<input type="text" maxlength="14" value="1,235.32">"""),
                normalizeHtml(root.element.innerHTML),
                "Should render masked numeric input element to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            ImaskModule.initialize()
            val root = root {
                imaskNumeric(1235.321, locale = SimpleLocale("en")) {
                    defaultValue = 1235.321
                }
            }
            assertEquals(
                normalizeHtml("""<div><input type="text" maxlength="14" value="1,235.32"></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render masked numeric input element to a String"
            )
        }
    }

    @Test
    fun getValueAsString() {
        run {
            ImaskModule.initialize()
            lateinit var numeric: Numeric
            root("test") {
                numeric = imaskNumeric(1235.321, locale = SimpleLocale("en")) {
                    defaultValue = 1235.321
                }
            }
            assertEquals(1235.321, numeric.value)
            assertEquals("1,235.32", numeric.getValueAsString())
        }
    }
}
