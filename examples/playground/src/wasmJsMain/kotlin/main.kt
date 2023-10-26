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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.softwork.routingcompose.BrowserRouter
import dev.kilua.Application
import dev.kilua.CoreModule
import dev.kilua.compose.root
import dev.kilua.externals.obj
import dev.kilua.form.check.checkBox
import dev.kilua.form.check.radio
import dev.kilua.form.check.radioGroup
import dev.kilua.form.check.triStateCheckBox
import dev.kilua.form.color.colorPicker
import dev.kilua.form.number.numeric
import dev.kilua.form.number.range
import dev.kilua.form.number.spinner
import dev.kilua.form.select.select
import dev.kilua.form.text.textArea
import dev.kilua.form.time.date
import dev.kilua.form.upload.upload
import dev.kilua.html.*
import dev.kilua.html.helpers.onClickLaunch
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
            div {
                gridTemplateAreas = listOf("a b", "c d")
            }

            hr()

            val upload = upload(multiple = true, accept = listOf("text/plain", "image/*"))

            button("test upload") {
                onClick {
                    console.log(upload.value.toString())
                }
            }

            button("test upload content") {
                onClickLaunch {
                    console.log(upload.getFilesWithContent().toString())
                }
            }

            button("clear upload") {
                onClick {
                    upload.value = null
                }
            }

            hr()

            link("https://google.com", "google")

            hr()

            BrowserRouter("/") {
                route("/test1") {
                    div {
                        +"test1"
                    }
                    navLink("/test2", "go to test2")
                }
                route("/test2") {
                    div {
                        +"test2"
                    }
                    navLink("/test3/5", "go to test3 (5)")
                }
                route("/test3") {
                    int {
                        div {
                            +"test3 ($it)"
                        }
                        navLink("/test4", "go to no match", hide = true)
                    }
                }
                noMatch {
                    div {
                        +"no match"
                    }
                    navLink("/test1", "go to test1")
                }
            }

            hr()

            val options = remember { mutableStateListOf("a" to "Ala", "b" to "Bela", "c" to "Cela") }
            var place by remember { mutableStateOf<String?>("First") }

            val sel = select(options, emptyOption = true, placeholder = "test") {
//                option("", "")
//                autofocus = true
//                optgroup("first group") {
                //option("a", "Ala")
                //    option("b", "Bela")
                //}
                //option("c", "Cela")
                onChange {
                    console.log(this.value.toString())
                }
            }

            button("test select") {
                onClick {
                    console.log(sel.value)
                    console.log(sel.selectedLabel)
                    console.log(sel.selectedIndex.toString())
                }
            }

            button("set select") {
                onClick {
                    sel.selectedIndex = 1
                }
            }

            button("clear select") {
                onClick {
                    sel.value = null
                }
            }

            button("test multiple") {
                onClick {
                    sel.multiple = !sel.multiple
                }
            }

            button("test placeholder") {
                onClick {
                    if (place != null) {
                        place = null
                    } else {
                        place = "Test"
                    }
                }
            }

            hr()

            val rg = radioGroup(options, "b", name = "a_group") {
                autofocus = true
                inline = true
                onChange {
                    console.log(this.value.toString())
                }
            }

            button("test radio group") {
                onClick {
                    options.add("d" to "Dela")
                }
            }

            button("set value") {
                onClick {
                    rg.value = "xxx"
                }
            }

            button("get value") {
                onClick {
                    console.log(rg.value.toString())
                }
            }

            button("set name") {
                onClick {
                    rg.name = "new_name"
                }
            }

            button("set disabled") {
                onClick {
                    rg.disabled = !(rg.disabled ?: false)
                }
            }

            button("test inline") {
                onClick {
                    rg.inline = !rg.inline
                }
            }

            hr()

            val rad = radio(true) {
                defaultChecked = true
                subscribe {
                    console.log(this.value.toString())
                }
            }

            button("test radio") {
                onClick {
                    console.log(rad.value.toString())
                }
            }

            button("set radio") {
                onClick {
                    rad.value = !rad.value
                }
            }

            hr()

            val tri = triStateCheckBox {
                subscribe {
                    console.log(this.value.toString())
                }
            }

            button("test tri") {
                onClick {
                    console.log(tri.value.toString())
                }
            }

            button("null tri") {
                onClick {
                    tri.value = null
                }
            }

            button("set tri") {
                onClick {
                    tri.value = tri.value != true
                }
            }

            hr()

            var checked by remember { mutableStateOf(false) }

            val check = checkBox(checked, disabled = true) {
                extraValue = "teston"
                subscribe {
                    console.log(this.value.toString())
                }
            }

            button("test check") {
                onClick {
                    console.log(check.value.toString())
                }
            }

            button("set check") {
                onClick {
                    checked = !checked
                }
            }

            hr()

            val c = colorPicker("#ff0000") {
                onChange {
                    console.log(this.value)
                }
            }

            button("test color picker") {
                onClick {
                    console.log(c.value)
                }
            }

            hr()

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
