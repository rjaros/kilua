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

import dev.kilua.compose.root
import dev.kilua.test.DomSpec
import web.html.asStringOrNull
import kotlin.test.Test

class ImgSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                img("/image.png", "An image", "test") {
                    id("test-id")
                    title("A title")
                }
            }
            assertEqualsHtml(
                """<img class="test" src="/image.png" alt="An image" id="test-id" title="A title">""",
                root.element.innerHTML.asStringOrNull(),
                "Should render an HTML Img tag to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                img("/image.png", "An image", "test") {
                    id("test-id")
                    title("A title")
                }
            }
            assertEqualsHtml(
                """<div><img class="test" src="/image.png" alt="An image" id="test-id" title="A title"></div>""",
                root.renderToString(),
                "Should render an HTML Img tag to a String"
            )
        }
    }

}

