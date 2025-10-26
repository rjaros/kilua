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

package dev.kilua.dropdown

import dev.kilua.compose.root
import dev.kilua.html.ButtonStyle
import dev.kilua.html.a
import dev.kilua.html.hr
import dev.kilua.html.li
import dev.kilua.test.DomSpec
import web.html.asStringOrNull
import kotlin.test.Test

class DropDownSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailable {
        val root = root("test") {
            dropDown("A dropdown", "fas fa-search", style = ButtonStyle.BtnDanger, arrowVisible = false) {
                li {
                    a("#link1", "Link 1", className = "dropdown-item")
                }
                li {
                    hr("dropdown-divider")
                }
                li {
                    a("#link2", "Link 2", className = "dropdown-item")
                }
            }
        }
        assertEqualsHtml(
            """<div class="dropdown">
<button class="btn btn-danger dropdown-toggle kilua-dropdown-no-arrow icon-link" type="button" data-bs-toggle="dropdown" data-bs-auto-close="true" aria-expanded="false">
<i class="fas fa-search">
</i>
A dropdown
</button>
<ul class="dropdown-menu">
<li>
<a class="dropdown-item" href="#link1">
Link 1
</a>
</li>
<li>
<hr class="dropdown-divider">
</li>
<li>
<a class="dropdown-item" href="#link2">
Link 2
</a>
</li>
</ul>
</div>""",
            root.element.innerHTML.asStringOrNull(),
            "Should render a Bootstrap dropdown component to DOM"
        )
    }

    @Test
    fun renderToString() = run {
        val root = root {
            dropDown("A dropdown", "fas fa-search", style = ButtonStyle.BtnDanger, arrowVisible = false) {
                li {
                    a("#link1", "Link 1", className = "dropdown-item")
                }
                li {
                    hr("dropdown-divider")
                }
                li {
                    a("#link2", "Link 2", className = "dropdown-item")
                }
            }
        }
        assertEqualsHtml(
            """<div class="dropdown">
<button class="btn btn-danger dropdown-toggle kilua-dropdown-no-arrow icon-link" type="button" data-bs-toggle="dropdown" data-bs-auto-close="true" aria-expanded="false">
<i class="fas fa-search">
</i>
A dropdown
</button>
<ul class="dropdown-menu">
<li>
<a class="dropdown-item" href="#link1">
Link 1
</a>
</li>
<li>
<hr class="dropdown-divider">
</li>
<li>
<a class="dropdown-item" href="#link2">
Link 2
</a>
</li>
</ul>
</div>""",
            root.innerHTML,
            "Should render a Bootstrap dropdown component to a String"
        )
    }
}
