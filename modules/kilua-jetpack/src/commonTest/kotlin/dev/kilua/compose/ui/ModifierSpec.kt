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

package dev.kilua.compose.ui

import dev.kilua.compose.root
import dev.kilua.html.BorderStyle
import dev.kilua.html.Display
import dev.kilua.html.TextAlign
import dev.kilua.html.div
import dev.kilua.html.px
import dev.kilua.test.DomSpec
import web.html.asStringOrNull
import kotlin.test.Test

class ModifierSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailable {
        val root = root("test") {
            val modifier = Modifier.display(Display.InlineBlock)
                .id("id")
                .title("Some title")
                .className("a_class")
                .border(1.px, BorderStyle.Solid) then (Modifier.textAlign(TextAlign.Center))
            div {
                +modifier
                +"Some text"
            }
        }
        assertEqualsHtml(
            """<div id="id" class="a_class" title="Some title" style="display: inline-block; border: 1px solid; text-align: center;">Some text</div>""",
            root.element.innerHTML.asStringOrNull(),
            "Should render an element with a custom modifier"
        )
    }

    @Test
    fun renderToString() = run {
        val root = root {
            val modifier = Modifier.display(Display.InlineBlock)
                .id("id")
                .title("Some title")
                .className("a_class")
                .border(1.px, BorderStyle.Solid) then (Modifier.textAlign(TextAlign.Center))
            div {
                +modifier
                +"Some text"
            }
        }
        assertEqualsHtml(
            """<div id="id" class="a_class" title="Some title" style="display: inline-block; border: 1px solid; text-align: center;">Some text</div>""",
            root.innerHTML,
            "Should render an element with a custom modifier to a String"
        )
    }

}
