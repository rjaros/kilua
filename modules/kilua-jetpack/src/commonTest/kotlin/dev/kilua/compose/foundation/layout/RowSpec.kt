/*
 * Copyright (c) 2024 Robert Jaros
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

package dev.kilua.compose.foundation.layout

import dev.kilua.compose.root
import dev.kilua.compose.ui.Alignment
import dev.kilua.compose.ui.Modifier
import dev.kilua.compose.ui.className
import dev.kilua.html.div
import dev.kilua.test.DomSpec
import web.html.asStringOrNull
import kotlin.test.Test

class RowSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailable {
        val root = root("test") {
            Row(Modifier.className("a_class"), horizontalArrangement = Arrangement.SpaceBetween) {
                div {
                    +Modifier.align(Alignment.Top)
                    +"Some text1"
                }
                div {
                    +"Some text2"
                }
                div {
                    +Modifier.align(Alignment.Bottom)
                    +"Some text3"
                }
            }
        }
        assertEqualsHtml(
            """<div class="a_class" style="display: flex; justify-content: space-between; align-items: start;"><div style="align-self: flex-start;">Some text1</div><div>Some text2</div><div style="align-self: flex-end;">Some text3</div></div>""",
            root.element.innerHTML.asStringOrNull(),
            "Should render a Row with some children"
        )
    }

    @Test
    fun renderToString() = run {
        val root = root {
            Row(Modifier.className("a_class"), horizontalArrangement = Arrangement.SpaceBetween) {
                div {
                    +Modifier.align(Alignment.Top)
                    +"Some text1"
                }
                div {
                    +"Some text2"
                }
                div {
                    +Modifier.align(Alignment.Bottom)
                    +"Some text3"
                }
            }
        }
        assertEqualsHtml(
            """<div class="a_class" style="display: flex; justify-content: space-between; align-items: start;"><div style="align-self: flex-start;">Some text1</div><div>Some text2</div><div style="align-self: flex-end;">Some text3</div></div>""",
            root.innerHTML,
            "Should render a Row with some children to a String"
        )
    }

}
