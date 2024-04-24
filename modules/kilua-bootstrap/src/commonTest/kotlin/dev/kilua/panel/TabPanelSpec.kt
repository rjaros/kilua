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
import dev.kilua.html.tag
import dev.kilua.test.DomSpec
import kotlinx.coroutines.delay
import kotlin.test.Test

class TabPanelSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailableAsync {
        val root = root("test") {
            tabPanel {
                tab("tab1") {
                    tag("span") { +"abc" }
                }
                tab("tab2") {
                    tag("span") { +"def" }
                }
            }
        }
        delay(100)
        assertEqualsHtml(
            """<div class="kilua-tab-panel"><ul class="nav nav-tabs" role="tablist"><li class="nav-item" role="presentation"><button type="button" class="nav-link active" id="id" role="tab" aria-selected="true">tab1</button></li><li class="nav-item" role="presentation"><button type="button" class="nav-link" id="id" role="tab" aria-selected="false">tab2</button></li></ul><div class="tab-content"><div class="tab-pane active" role="tabpanel" aria-labelledby="id" tabindex="0"><span>abc</span></div></div></div>""",
            root.element.innerHTML,
            "Should render a TabPanel component to DOM"
        )
    }

    @Test
    fun renderToString() = runAsync {
        val root = root {
            tabPanel {
                tab("tab1") {
                    tag("span") { +"abc" }
                }
                tab("tab2") {
                    tag("span") { +"def" }
                }
            }
        }
        delay(100)
        assertEqualsHtml(
            """<div class="kilua-tab-panel"><ul class="nav nav-tabs" role="tablist"><li class="nav-item" role="presentation"><button type="button" class="nav-link active" id="id" role="tab" aria-selected="true">tab1</button></li><li class="nav-item" role="presentation"><button type="button" class="nav-link" id="id" role="tab" aria-selected="false">tab2</button></li></ul><div class="tab-content"><div class="tab-pane active" role="tabpanel" aria-labelledby="id" tabindex="0"><span>abc</span></div></div></div>""",
            root.innerHTML,
            "Should render a TabPanel component to a String"
        )
    }
}
