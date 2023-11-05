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

package dev.kilua.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.html.Border
import dev.kilua.html.BorderStyle
import dev.kilua.html.Button
import dev.kilua.html.ButtonType
import dev.kilua.html.Color
import dev.kilua.html.TextAlign
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.px
import dev.kilua.html.unaryPlus
import dev.kilua.test.DomSpec
import dev.kilua.test.normalizeHtml
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertEquals

class RootSpec : DomSpec {

    @Test
    fun renderToString() {
        run {
            val root = root {
                div("a_class") {
                    id = "main"
                    textAlign = TextAlign.Center
                    border = Border(1.px, BorderStyle.Dotted, Color.Red)
                    setAttribute("custom", "value")
                    setStyle("padding-top", "2px")
                    div {
                        +"Some content"
                    }
                    button("A button", type = ButtonType.Submit)
                }
            }
            assertEquals(
                normalizeHtml("""<div><div class="a_class" id="main" custom="value" style="text-align: center; border: 1px dotted red; padding-top: 2px;"><div>Some content</div><button type="submit">A button</button></div></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render root element with some content to a String"
            )
        }
    }

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                div("a_class") {
                    id = "main"
                    textAlign = TextAlign.Center
                    border = Border(1.px, BorderStyle.Dotted, Color.Red)
                    setAttribute("custom", "value")
                    setStyle("padding-top", "2px")
                    div {
                        +"Some content"
                    }
                    button("A button", type = ButtonType.Submit)
                }
            }
            assertEquals(
                normalizeHtml("""<div id="test"><div class="a_class" id="main" custom="value" style="text-align: center; border: 1px dotted red; padding-top: 2px;"><div>Some content</div><button type="submit">A button</button></div></div>"""),
                normalizeHtml(root.element.outerHTML),
                "Should render root element with some content to DOM"
            )
        }
    }

    @Test
    fun recomposeToString() = runAsync {
        lateinit var button: Button
        val root = root {
            var counter by remember { mutableStateOf(0) }
            div {
                +"Counter: $counter"
                button = button {
                    +"Increment"
                    onClick {
                        counter++
                    }
                }
            }
        }
        button.click()
        button.click()
        delay(10)
        assertEquals(
            normalizeHtml("""<div><div>Counter: 2<button type="button">Increment</button></div></div>"""),
            normalizeHtml(root.renderToString()),
            "Should recompose when state changes"
        )
    }

    @Test
    fun recompose() = runWhenDomAvailableAsync {
        lateinit var button: Button
        val root = root("test") {
            var counter by remember { mutableStateOf(0) }
            div {
                +"Counter: $counter"
                button = button {
                    +"Increment"
                    onClick {
                        counter++
                    }
                }
            }
        }
        button.click()
        button.click()
        delay(10)
        assertEquals(
            normalizeHtml("""<div>Counter: 2<button type="button">Increment</button></div>"""),
            normalizeHtml(root.element.innerHTML),
            "Should recompose DOM nodes when state changes"
        )
    }
}
