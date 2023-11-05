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

package dev.kilua.form

import dev.kilua.compose.root
import dev.kilua.form.text.text
import dev.kilua.test.DomSpec
import kotlin.test.Test

class FieldWithLabelSpec : DomSpec {

    @Test
    fun fieldWithLabel() {
        runWhenDomAvailable {
            val root = root("test") {
                fieldWithLabel("A label", "form-label", groupClassName = "group", wrapperClassName = "wrapper") {
                    text(id = it)
                }
            }
            assertEqualsHtml(
                """<div class="group"><label for="id" class="form-label">A label</label><div class="wrapper"><input type="text" id="id"></div></div>""",
                root.element.innerHTML,
                "Should render form element with a label to DOM"
            )
        }
    }

    @Test
    fun fieldWithLabelToString() {
        run {
            val root = root {
                fieldWithLabel("A label", "form-label", groupClassName = "group", wrapperClassName = "wrapper") {
                    text(id = it)
                }
            }
            assertEqualsHtml(
                """<div class="group"><label for="id" class="form-label">A label</label><div class="wrapper"><input type="text" id="id"></div></div>""",
                root.innerHTML,
                "Should render form element with a label to a String"
            )
        }
    }


    @Test
    fun fieldWithLabelAfter() {
        runWhenDomAvailable {
            val root = root("test") {
                fieldWithLabel(
                    "A label",
                    "form-label",
                    labelAfter = true,
                    groupClassName = "group",
                    wrapperClassName = "wrapper"
                ) {
                    text(id = it)
                }
            }
            assertEqualsHtml(
                """<div class="group"><div class="wrapper"><input type="text" id="id"></div><label for="id" class="form-label">A label</label></div>""",
                root.element.innerHTML,
                "Should render form element with a label after the input to DOM"
            )
        }
    }

    @Test
    fun fieldWithLabelAfterToString() {
        run {
            val root = root {
                fieldWithLabel(
                    "A label",
                    "form-label",
                    labelAfter = true,
                    groupClassName = "group",
                    wrapperClassName = "wrapper"
                ) {
                    text(id = it)
                }
            }
            assertEqualsHtml(
                """<div class="group"><div class="wrapper"><input type="text" id="id"></div><label for="id" class="form-label">A label</label></div>""",
                root.innerHTML,
                "Should render form element with a label after the input to a String"
            )
        }
    }
}
