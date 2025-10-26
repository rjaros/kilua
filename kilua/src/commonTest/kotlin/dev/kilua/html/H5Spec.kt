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

import dev.kilua.test.DomSpec
import dev.kilua.compose.root
import dev.kilua.test.normalizeHtml
import web.html.asStringOrNull
import kotlin.test.Test
import kotlin.test.assertEquals

class H5Spec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                h5t("Lorem ipsum", "test") {
                    id("test-id")
                    title("A title")
                    ariaLabel("A title")
                    attribute("data-test", "test")
                    margin(10.px)
                    display(Display.Flex)
                }
            }
            assertEquals(
                normalizeHtml("""<h5 class="test" id="test-id" title="A title" aria-label="A title" data-test="test" style="margin: 10px; display: flex;">Lorem ipsum</h5>"""),
                normalizeHtml(root.element.innerHTML.asStringOrNull()),
                "Should render an HTML H5 tag to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                h5t("Lorem ipsum", "test") {
                    id("test-id")
                    title("A title")
                    ariaLabel("A title")
                    attribute("data-test", "test")
                    margin(10.px)
                    display(Display.Flex)
                }
            }
            assertEquals(
                normalizeHtml("""<div><h5 class="test" id="test-id" title="A title" aria-label="A title" data-test="test" style="margin: 10px; display: flex;">Lorem ipsum</h5></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render an HTML H5 tag to a String"
            )
        }
    }

}

