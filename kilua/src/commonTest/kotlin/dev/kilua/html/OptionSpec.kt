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

class OptionSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                option("test1", selected = true) { +"Test 1" }
                option("test2") { +"Test 2" }
                option("test3", label = "Test 3") { +"Test 3" }
                option("test4", disabled = true) { +"Test 4" }
            }
            assertEquals(
                normalizeHtml("""<option value="test1" selected="">Test 1</option><option value="test2">Test 2</option><option value="test3" label="Test 3">Test 3</option><option value="test4" disabled="">Test 4</option>"""),
                normalizeHtml(root.element.innerHTML.asStringOrNull()),
                "Should render an HTML Option tag to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                option("test1", selected = true) { +"Test 1" }
                option("test2") { +"Test 2" }
                option("test3", label = "Test 3") { +"Test 3" }
                option("test4", disabled = true) { +"Test 4" }
            }
            assertEquals(
                normalizeHtml("""<div><option value="test1" selected>Test 1</option><option value="test2">Test 2</option><option value="test3" label="Test 3">Test 3</option><option value="test4" disabled>Test 4</option></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render an HTML Option tag to a String"
            )
        }
    }

}

