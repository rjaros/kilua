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

class BsButtonSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailable {
        val root = root("test") {
            bsButton("A button", "fas fa-home")
        }
        assertEqualsHtml(
            """<button type="button" class="btn btn-primary icon-link"><i class="fas fa-home"></i> A button</button>""",
            root.element.innerHTML.asStringOrNull(),
            "Should render a Bootstrap button component to DOM"
        )
    }

    @Test
    fun renderToString() = run {
        val root = root {
            bsButton("A button", "fas fa-home")
        }
        assertEqualsHtml(
            """<button type="button" class="btn btn-primary icon-link"><i class="fas fa-home"></i> A button</button>""",
            root.innerHTML,
            "Should render a Bootstrap button component to a String"
        )
    }
}
