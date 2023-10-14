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

package dev.kilua.form.text

import dev.kilua.DomSpec
import dev.kilua.compose.root
import dev.kilua.normalizeHtml
import kotlin.test.Test
import kotlin.test.assertEquals

class TextAreaSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                textArea("Some text", 12, 13, "test", 200, "A placeholder") {
                    autofocus = true
                    readonly = true
                    wrap = WrapType.Hard
                }
            }
            assertEquals(
                normalizeHtml("""<textarea cols="12" rows="13" name="test" maxlength="200" placeholder="A placeholder" autofocus="" readonly="" wrap="hard"></textarea>"""),
                normalizeHtml(root.element?.innerHTML),
                "Should render html area element to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                textArea("Some text", 12, 13, "test", 200, "A placeholder") {
                    autofocus = true
                    readonly = true
                    wrap = WrapType.Hard
                }
            }
            assertEquals(
                normalizeHtml("""<div><textarea cols="12" rows="13" name="test" maxlength="200" placeholder="A placeholder" autofocus readonly wrap="hard"></textarea></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render html area element to a String"
            )
        }
    }
}
