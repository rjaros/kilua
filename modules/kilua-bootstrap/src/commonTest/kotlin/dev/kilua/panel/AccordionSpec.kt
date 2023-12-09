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

package dev.kilua.panel

import dev.kilua.compose.root
import dev.kilua.html.p
import dev.kilua.test.DomSpec
import kotlinx.coroutines.delay
import kotlin.test.Test

class AccordionSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailableAsync {
        val root = root("test") {
            accordion(flush = true, openedIndex = 1) {
                item("Item 1") {
                    p("First item")
                }
                item("Item 2") {
                    p("Second item")
                }
                item("Item 3") {
                    p("Third item")
                }
            }
        }
        delay(100)
        assertEqualsHtml(
            """<div class="accordion accordion-flush" id="kilua_accordion_0">
<!--sid=1-->
<!--sid=2-->
<!--sid=3-->
<div class="accordion-item">
<h2 class="accordion-header">
<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#kilua_accordion_0-item-0" aria-expanded="false" aria-controls="kilua_accordion_0-item-0">
Item 1
</button>
</h2>
<div class="accordion-collapse collapse" id="kilua_accordion_0-item-0" data-bs-parent="#kilua_accordion_0">
<div class="accordion-body">
<p class="First item">
</p>
</div>
</div>
</div>
<div class="accordion-item">
<h2 class="accordion-header">
<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#kilua_accordion_0-item-1" aria-expanded="true" aria-controls="kilua_accordion_0-item-1">
Item 2
</button>
</h2>
<div class="accordion-collapse collapse show" id="kilua_accordion_0-item-1" data-bs-parent="#kilua_accordion_0">
<div class="accordion-body">
<p class="Second item">
</p>
</div>
</div>
</div>
<div class="accordion-item">
<h2 class="accordion-header">
<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#kilua_accordion_0-item-2" aria-expanded="false" aria-controls="kilua_accordion_0-item-2">
Item 3
</button>
</h2>
<div class="accordion-collapse collapse" id="kilua_accordion_0-item-2" data-bs-parent="#kilua_accordion_0">
<div class="accordion-body">
<p class="Third item">
</p>
</div>
</div>
</div>
</div>""".replace(Regex("sid=\\d+"), "sid=0").replace(Regex("kilua_accordion_[0-9]*"), "kilua_accordion_0"),
            root.element.innerHTML.replace(Regex("sid=\\d+"), "sid=0")
                .replace(Regex("kilua_accordion_[0-9]*"), "kilua_accordion_0"),
            "Should render an Accordion component to DOM"
        )
    }

    @Test
    fun renderToString() = runAsync {
        val root = root {
            accordion(flush = true, openedIndex = 1) {
                item("Item 1") {
                    p("First item")
                }
                item("Item 2") {
                    p("Second item")
                }
                item("Item 3") {
                    p("Third item")
                }
            }
        }
        delay(100)
        assertEqualsHtml(
            """<div class="accordion accordion-flush" id="kilua_accordion_0">
<!--sid=1-->
<!--sid=2-->
<!--sid=3-->
<div class="accordion-item">
<h2 class="accordion-header">
<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#kilua_accordion_0-item-0" aria-expanded="false" aria-controls="kilua_accordion_0-item-0">
Item 1
</button>
</h2>
<div class="accordion-collapse collapse" id="kilua_accordion_0-item-0" data-bs-parent="#kilua_accordion_0">
<div class="accordion-body">
<p class="First item">
</p>
</div>
</div>
</div>
<div class="accordion-item">
<h2 class="accordion-header">
<button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#kilua_accordion_0-item-1" aria-expanded="true" aria-controls="kilua_accordion_0-item-1">
Item 2
</button>
</h2>
<div class="accordion-collapse collapse show" id="kilua_accordion_0-item-1" data-bs-parent="#kilua_accordion_0">
<div class="accordion-body">
<p class="Second item">
</p>
</div>
</div>
</div>
<div class="accordion-item">
<h2 class="accordion-header">
<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#kilua_accordion_0-item-2" aria-expanded="false" aria-controls="kilua_accordion_0-item-2">
Item 3
</button>
</h2>
<div class="accordion-collapse collapse" id="kilua_accordion_0-item-2" data-bs-parent="#kilua_accordion_0">
<div class="accordion-body">
<p class="Third item">
</p>
</div>
</div>
</div>
</div>""".replace(Regex("sid=\\d+"), "sid=0").replace(Regex("kilua_accordion_[0-9]*"), "kilua_accordion_0"),
            root.innerHTML.replace(Regex("sid=\\d+"), "sid=0")
                .replace(Regex("kilua_accordion_[0-9]*"), "kilua_accordion_0"),
            "Should render an Accordion component to a String"
        )
    }
}
