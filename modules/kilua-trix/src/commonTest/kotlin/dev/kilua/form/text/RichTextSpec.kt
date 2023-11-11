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

package dev.kilua.form.text

import dev.kilua.compose.root
import dev.kilua.test.DomSpec
import kotlin.test.Test
import kotlin.test.assertTrue

class RichTextSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                richText("Some text", placeholder = "A placeholder")
            }
            val innerHTML = root.element.innerHTML.replace(Regex("input=\"[^\"]*\""), "input=\"id_input\"")
                .replace(Regex("toolbar=\"[^\"]*\""), "toolbar=\"id_toolbar\"")

            assertTrue(innerHTML.contains("<input type=\"hidden\""), "Should render hidden input element")
            assertTrue(innerHTML.contains("<trix-toolbar"), "Should render trix toolbar element")
            assertTrue(
                innerHTML.contains("<trix-editor placeholder=\"A placeholder\" role=\"textbox\" input=\"id_input\" toolbar=\"id_toolbar\" contenteditable=\"\">"),
                "Should render trix editor element"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                richText("Some text", placeholder = "A placeholder")
            }
            assertEqualsHtml(
                """<input type="hidden" id="id_input"><trix-toolbar id="id_toolbar"></trix-toolbar><trix-editor placeholder="A placeholder" input="id_input" toolbar="id_toolbar" role="textbox"></trix-editor>""",
                root.innerHTML.replace(Regex("input=\"[^\"]*\""), "input=\"id_input\"")
                    .replace(Regex("toolbar=\"[^\"]*\""), "toolbar=\"id_toolbar\""),
                "Should render trix rich text editor to a String"
            )
        }
    }
}
