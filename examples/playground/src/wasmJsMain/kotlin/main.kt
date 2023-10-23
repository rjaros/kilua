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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.CoreModule
import dev.kilua.compose.root
import dev.kilua.externals.obj
import dev.kilua.form.number.numeric
import dev.kilua.form.number.range
import dev.kilua.form.number.spinner
import dev.kilua.form.text.textArea
import dev.kilua.form.time.date
import dev.kilua.html.*
import dev.kilua.panel.Dir
import dev.kilua.panel.SplitPanel
import dev.kilua.panel.splitPanel
import dev.kilua.startApplication
import dev.kilua.utils.JsNonModule
import dev.kilua.utils.cast
import dev.kilua.utils.console
import dev.kilua.utils.today
import dev.kilua.utils.useCssModule
import kotlinx.browser.window
import org.w3c.dom.CustomEvent

@JsModule("./css/style.css")
@JsNonModule
public external object css

public class App : Application() {

    init {
        useCssModule(css)
    }

    override fun start() {

        root("root") {
            console.log("recomposing")

            textArea("ala ma kota", cols = 20, rows = 20) {
                //+"ala ma kota"
                subscribe {
                    console.log(it)
                }
            }

            hr()

            date(today()) {
                onChange {
                    console.log(this.value?.toString())
                }
            }

            hr()

            val num = numeric(9.123, min = 1, max = 10, decimals = 3) {
                onChange {
                    console.log(this.value?.toString())
                }
            }

            button("test numeric") {
                onClick {
                    console.log(num.value.toString())
                    console.log(num.getValueAsString())
                }
            }

            button("set numeric") {
                onClick {
                    num.value = 123.456
                }
            }

            hr()

            range(step = 0.5) {
                onChange {
                    console.log(this.value?.toString())
                }
            }

            spinner {
                onChange {
                    console.log(this.value?.toString())
                }
            }

            hr()

            var count by remember { mutableStateOf(4) }
            var start by remember { mutableStateOf(1) }

            val ol = ol(start = start) {
                for (i in 0..<count) {
                    li {
                        +"Item ${i + 1}"
                    }
                }
            }
            button("Add item") {
                onClick {
                    count++
                }
            }
            button("Inc start") {
                onClick {
                    start++
                    ol.reversed = !(ol.reversed ?: false)
                }
            }

            hr()

            var id by remember { mutableStateOf("id") }

            label(id) {
                +"Label"
            }

            button("test label") {
                onClick {
                    id = "id2"
                }
            }

            hr()

            var src by remember { mutableStateOf("https://www.finn.pl") }

            val iframe = iframe(src, iframeWidth = 300, iframeHeight = 200, sandbox = setOf(Sandbox.AllowForms))

            button("test iframe") {
                onClick {
                    //src = "https://www.onet.pl"
                    //iframe.sandbox = setOf(Sandbox.AllowPopups)
                    window.setTimeout({
                        console.log(iframe.renderToString())
                        obj()
                    }, 0)
                }
            }

            var marg by remember { mutableStateOf(1) }
            hr {
                margin = marg.px
            }
            button("test hr") {
                onClick {
                    marg++
                }
            }

            var label by remember { mutableStateOf("A link") }
            var url by remember { mutableStateOf("https://google.com") }

            val link = link(url, label) {
                div()
            }

            button("Test link label") {
                onClick {
                    label = "Another link"
                }
            }
            button("Test link url") {
                onClick {
                    url = "https://github.com"
                }
            }
            button("Test link target") {
                onClick {
                    link.target = "_blank"
                }
            }
            button("Test link label") {
                onClick {
                    console.log(link.textContent)
                }
            }

            var visible by remember { mutableStateOf(true) }

            lateinit var sp: SplitPanel
            var widthv by remember { mutableStateOf(30.0) }

            if (visible) {
                sp = splitPanel(Dir.Vertical) {
                    self {
                        border = Border(1.px, BorderStyle.Solid, Color.name(C.Black))
                        width = 800.px
                        height = 300.px
                        onEvent<CustomEvent>("dragEndSplitPanel") {
                            console.log(it.detail.toString())
                            console.log((it.detail?.cast<List<Number>>()?.get(0)?.toDouble() ?: 0.0).toString())
                            widthv = it.detail?.cast<List<Number>>()?.get(0)?.toDouble() ?: 0.0
                        }
                    }
                    left {
                        width = widthv.perc
                        +"Tab 1"
                    }
                    right {
                        +"Tab 2"
                    }
                }
            }
            button("test split panel") {
                onClick {
                    visible = !visible
                }
            }
        }
    }
}

public fun main() {
    startApplication(::App, null, CoreModule)
}
