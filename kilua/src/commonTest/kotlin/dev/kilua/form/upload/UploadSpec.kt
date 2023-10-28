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

package dev.kilua.form.upload

import dev.kilua.DomSpec
import dev.kilua.compose.root
import kotlin.test.Test

class UploadSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                upload(multiple = true, accept = listOf("text/plain", "image/*"), name = "test") {
                    autofocus = true
                }
            }
            assertEqualsHtml(
                """<input type="file" name="test" multiple="" accept="text/plain,image/*" autofocus="">""",
                root.element.innerHTML,
                "Should render file input element to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                upload(multiple = true, accept = listOf("text/plain", "image/*"), name = "test") {
                    autofocus = true
                }
            }
            assertEqualsHtml(
                """<input type="file" name="test" multiple="" accept="text/plain,image/*" autofocus="">""",
                root.innerHTML,
                "Should render file input element to a String"
            )
        }
    }
}
