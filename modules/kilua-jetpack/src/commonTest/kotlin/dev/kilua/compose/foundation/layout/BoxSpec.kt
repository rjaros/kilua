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
import dev.kilua.compose.ui.size
import dev.kilua.html.px
import dev.kilua.test.DomSpec
import kotlin.test.Test

class BoxSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailable {
        val root = root("test") {
            Box(Modifier.className("a_class"), contentAlignment = Alignment.Center) {
                Box(Modifier.align(Alignment.TopEnd)) {
                    +"Some text1"
                }
                Box(Modifier.size(100.px).align(Alignment.CenterEnd)) {
                    +"Some text2"
                }
            }
        }
        assertEqualsHtml(
            """<div class="kilua-jetpack-box a_class" style="place-items: center;"><div class="kilua-jetpack-box" style="place-items: start; place-self: start end;">Some text1</div><div class="kilua-jetpack-box" style="place-items: start; width: 100px; height: 100px; place-self: center end;">Some text2</div></div>""",
            root.element.innerHTML,
            "Should render a Box with some children"
        )
    }

    @Test
    fun renderToString() = run {
        val root = root {
            Box(Modifier.className("a_class"), contentAlignment = Alignment.Center) {
                Box(Modifier.align(Alignment.TopEnd)) {
                    +"Some text1"
                }
                Box(Modifier.size(100.px).align(Alignment.CenterEnd)) {
                    +"Some text2"
                }
            }
        }
        assertEqualsHtml(
            """<div class="kilua-jetpack-box a_class" style="place-items: center;"><div class="kilua-jetpack-box" style="place-items: start; place-self: start end;">Some text1</div><div class="kilua-jetpack-box" style="place-items: start; width: 100px; height: 100px; place-self: center end;">Some text2</div></div>""",
            root.innerHTML,
            "Should render a Box with some children to a String"
        )
    }

}
