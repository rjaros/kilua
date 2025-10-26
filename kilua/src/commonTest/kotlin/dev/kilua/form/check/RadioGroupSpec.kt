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

package dev.kilua.form.check

import dev.kilua.test.DomSpec
import dev.kilua.compose.root
import dev.kilua.utils.listOfPairs
import web.html.asStringOrNull
import kotlin.test.Test

class RadioGroupSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                radioGroup(
                    listOfPairs("A", "B"),
                    name = "test",
                    inline = true,
                    groupClassName = "form-check",
                    inputClassName = "form-check-input",
                    labelClassName = "form-check-label"
                )
            }
            assertEqualsHtml(
                """<div><div class="kilua-radio-inline form-check"><input type="radio" name="test" id="id" value="A" class="form-check-input"><label class="form-check-label" for="id">A</label></div><div class="kilua-radio-inline form-check"><input type="radio" name="test" id="id" class="form-check-input" value="B"><label class="form-check-label" for="id">B</label></div></div>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render radio button group to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                radioGroup(
                    listOfPairs("A", "B"), name = "test", inline = true,
                    groupClassName = "form-check",
                    inputClassName = "form-check-input",
                    labelClassName = "form-check-label"
                )
            }
            assertEqualsHtml(
                """<div><div class="kilua-radio-inline form-check"><input type="radio" name="test" id="id" value="A" class="form-check-input"><label class="form-check-label" for="id">A</label></div><div class="kilua-radio-inline form-check"><input type="radio" name="test" id="id" class="form-check-input" value="B"><label class="form-check-label" for="id">B</label></div></div>""",
                root.innerHTML,
                "Should render radio button group to a String"
            )
        }
    }
}
