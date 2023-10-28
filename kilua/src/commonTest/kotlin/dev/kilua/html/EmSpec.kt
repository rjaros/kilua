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

import dev.kilua.DomSpec
import dev.kilua.compose.root
import dev.kilua.normalizeHtml
import kotlin.test.Test
import kotlin.test.assertEquals

class EmSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                emt("Lorem ipsum", "test") {
                    id = "test-id"
                    title = "A title"
                    ariaLabel = "A title"
                    setAttribute("data-test", "test")
                    margin = 10.px
                    display = Display.Flex
                }
            }
            assertEquals(
                normalizeHtml("""<em class="test" id="test-id" title="A title" aria-label="A title" data-test="test" style="margin: 10px; display: flex;">Lorem ipsum</em>"""),
                normalizeHtml(root.element.innerHTML),
                "Should render an HTML Em tag to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                emt("Lorem ipsum", "test") {
                    id = "test-id"
                    title = "A title"
                    ariaLabel = "A title"
                    setAttribute("data-test", "test")
                    margin = 10.px
                    display = Display.Flex
                }
            }
            assertEquals(
                normalizeHtml("""<div><em class="test" id="test-id" title="A title" aria-label="A title" data-test="test" style="margin: 10px; display: flex;">Lorem ipsum</em></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render an HTML Em tag to a String"
            )
        }
    }

}

