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
import kotlin.test.Test

class TagSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                tag("ol", "test") {
                    id("test-id")
                    title("A title")
                    ariaLabel("A title")
                    attribute("data-test", "test")
                    margin(10.px)
                    display(Display.Flex)
                    autofocus(true)
                    gridTemplateAreas(listOf("a a a", "b c c", "b c c"))
                    boxShadowList(
                        listOf(
                            BoxShadow(hOffset = 10.px, vOffset = 20.px, color = Color.hex(0x00ffff)),
                            BoxShadow(hOffset = 30.px, vOffset = 40.px, color = Color.hex(0x00eeee))
                        )
                    )
                    transitionList(listOf(Transition("width", 0.5), Transition("height", 0.6)))
                    borderRadiusList(listOf(10.px, 20.px))
                }
            }
            assertEqualsHtml(
                """<ol class="test" id="test-id" title="A title" aria-label="A title" data-test="test" autofocus="" style="margin: 10px; display: flex; grid-template-areas: &quot;a a a&quot; &quot;b c c&quot; &quot;b c c&quot;; box-shadow: rgb(0, 255, 255) 10px 20px, rgb(0, 238, 238) 30px 40px; transition: width 0.5s, height 0.6s; border-radius: 10px 20px;"></ol>""",
                root.element.innerHTML,
                "Should render an HTML tag to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                tag("ol", "test") {
                    id("test-id")
                    title("A title")
                    ariaLabel("A title")
                    attribute("data-test", "test")
                    margin(10.px)
                    display(Display.Flex)
                    autofocus(true)
                    gridTemplateAreas(listOf("a a a", "b c c", "b c c"))
                    boxShadowList(
                        listOf(
                            BoxShadow(hOffset = 10.px, vOffset = 20.px, color = Color.hex(0x00ffff)),
                            BoxShadow(hOffset = 30.px, vOffset = 40.px, color = Color.hex(0x00eeee))
                        )
                    )
                    transitionList(listOf(Transition("width", 0.5), Transition("height", 0.6)))
                    borderRadiusList(listOf(10.px, 20.px))
                }
            }
            assertEqualsHtml(
                """<ol class="test" id="test-id" title="A title" aria-label="A title" data-test="test" autofocus="" style="margin: 10px; display: flex; grid-template-areas: &quot;a a a&quot; &quot;b c c&quot; &quot;b c c&quot;; box-shadow: 10px 20px #00ffff, 30px 40px #00eeee; transition: width 0.5s, height 0.6s; border-radius: 10px 20px;"></ol>""",
                root.innerHTML,
                "Should render an HTML tag to a String"
            )
        }
    }

}

