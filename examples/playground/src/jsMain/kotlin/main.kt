/*
 * Copyright (c) 2023-present Robert Jaros
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

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.CoreModule
import dev.kilua.Hot
import dev.kilua.TrixModule
import dev.kilua.compose.root
import dev.kilua.form.number.range
import dev.kilua.form.select.select
import dev.kilua.form.text.richText
import dev.kilua.form.text.text
import dev.kilua.html.Background
import dev.kilua.html.Border
import dev.kilua.html.BorderStyle
import dev.kilua.html.ButtonType
import dev.kilua.html.Color
import dev.kilua.html.Div
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.h1t
import dev.kilua.html.hr
import dev.kilua.html.px
import dev.kilua.html.style.PClass
import dev.kilua.html.style.style
import dev.kilua.html.tag
import dev.kilua.html.unaryPlus
import dev.kilua.i18n.Locale
import dev.kilua.i18n.SimpleLocale
import dev.kilua.startApplication
import dev.kilua.state.collectAsState
import dev.kilua.utils.JsNonModule
import dev.kilua.utils.console
import dev.kilua.utils.log
import dev.kilua.utils.useModule
import kotlinx.browser.window
import org.w3c.dom.Text
import org.w3c.dom.events.Event
import kotlin.js.Date
import kotlin.math.PI
import kotlin.math.floor
import kotlin.math.sin

@JsModule("./css/style.css")
@JsNonModule
external object css

class App : Application() {

    init {
        useModule(css)
    }

    override fun start() {

        root("root") {

            var disab by remember { mutableStateOf(true) }

            val language by select(listOf("en" to "English", "pl" to "Polish"), "en").collectAsState()

            val locale = SimpleLocale(language ?: "en")

            var someText by remember { mutableStateOf<String?>("Ala ma kota") }

            if (disab) {
                val trix = richText(someText, placeholder = "wprowadź dane", locale = locale) {
                    onChange {
                        someText = this.value
                    }
                }
                button("get trix") {
                    onClick {
                        console.log(trix.value)
                    }
                }
                button("set trix") {
                    onClick {
                        trix.value = "<strong>bold text</strong>"
                    }
                }
                button("clear trix") {
                    onClick {
                        trix.value = null
                    }
                }
                button("hide trix") {
                    onClick {
                        trix.visible = !trix.visible
                    }
                }
            }
            button("disable trix") {
                onClick {
                    disab = !disab
                }
            }

            hr()

            val i by range(0, 1, 255).collectAsState()

            val className = style(".test") {
                background = Background(color = Color.rgb(i?.toInt() ?: 0, 0, 0))
                style("h1", PClass.Hover) {
                    color = Color.Green
                }
                style("h1") {
                    style("div") {
                        color = Color.Blue
                    }
                }
                style("input", PClass.Focus) {
                    border = Border(1.px, BorderStyle.Solid, Color.Red)
                }
            }

            div(className) {
                h1t("Ala ma kota") {
                    div {
                        +"test"
                    }
                }
            }

            console.log("recomposing")
            val x = tag("address", "address3") {
                +"address23"
                setStyle("color", "red")
            }

            var size by remember { mutableStateOf(1) }
            var evenSize by remember { mutableStateOf(1) }
            var oddSize by remember { mutableStateOf(1) }
            var tn by remember { mutableStateOf("address") }
            var list by remember { mutableStateOf(listOf("cat", "dog", "mouse")) }
            var type by remember { mutableStateOf(ButtonType.Button) }

            div {
                for (name in list) {
//                    key(name) {
                    div {
                        +name
                        button {
                            +"click"
                            DisposableEffect("button") {
                                val f = { _: Event ->
                                    console.log("click $name of ${list.size}")
                                }
                                element.addEventListener("click", f)
                                onDispose {
                                    element.removeEventListener("click", f)
                                }
                            }
                        }
                        //                      }
                    }
                }
            }
            val xb = button("add $size") {
                onClick {
                    list = list + "test"
                }
            }
            button {
                +"remove"
                onClick {
                    list = list.filterIndexed { index, s -> index != 1 }
                    xb.textContent = "changed label"
                }
            }

            tag(tn) {
                +"address2 $size"
                id = "test"
                title = "Some title"
                role = "button"
                tabindex = 5
                draggable = true
                setAttribute("aria-label", "Ala ma kota")
            }
            val x2 = text("Ala ma kota")
            console.log("x")
            console.log(x2)
            button(type = type) {
                +"test span"
                onClick {
                    disabled = !(disabled ?: false)
                    window.setTimeout({
                        disabled = !(disabled ?: false)
                    }, 1000)
                }
            }

            if (size % 2 == 0) {
                div {
                    +"even $evenSize"
                    button {
                        +"button even$evenSize"
                        onClick {
                            evenSize++
                        }
                    }
                }
            } else {
                div {
                    +"odd $oddSize"
                    button {
                        +"button odd$oddSize"
                        onClick {
                            oddSize++
                        }
                    }
                }
            }

            lateinit var divB: Div
            div {
                +"$size"
                divB = div {
                    +"b"
                    div {
                        +"c"
                        button(disabled = (size % 4 == 0)) {
                            +"button1"
                            onClick {
                                divB.textContent = "test"
                                size++
                            }
                        }
                        if (size % 2 == 0) {
                            button {
                                +"button2"
                                onClick {
                                    size++
                                }
                                DisposableEffect("button2") {
                                    val f = { _: Event ->
                                        console.log("button2 click")
                                    }
                                    element.addEventListener("click", f)
                                    onDispose {
                                        element.removeEventListener("click", f)
                                    }
                                }
                            }
                        }
                        for (i in 0 until size) {
                            div {
                                +"$i"
                            }
                        }
                    }
                }
                DisposableEffect("code") {
                    element.firstChild?.unsafeCast<Text>()?.data = "ala ma kota"
                    onDispose { }
                }
            }
            /*            var countDiv by remember { mutableStateOf(0) }
                        button(if (countDiv == 0 || countDiv == 3) null else "$countDiv", {
                            println("empty button")
                        })
                        button("test div $countDiv", {
                            println("test div $countDiv")
                            countDiv++
                        })
                        div("Hello world $countDiv!") {
                            var countButton by remember { mutableStateOf(0) }
                            button("test button $countButton", {
                                println("test button $countButton")
                                countButton++
                            })
                        }*/
        }
    }

}

data class Bg(val index: Int, val bg: Background)

class App2 : Application() {
    override fun start() {
        val size = 25
        val ratio = 0.04

        @NoLiveLiterals
        fun sinToHex(i: Int, phase: Double): String {
            val sin = sin(PI / (size * size).toDouble() * 2 * i + phase)
            val int = floor(sin * 127) + 128
            return int.asDynamic().toString(16).padStart(2, "0")
        }

        val state = mutableStateOf((1..(size * size)).toList().map {
            val r = sinToHex(it, 0.0)
            val g = sinToHex(it, PI * 2.0 / 3.0)
            val b = sinToHex(it, 2 * PI * 2.0 / 3.0)
            Bg(it, Background(Color("#$r$g$b")))
        })
        val info = mutableStateOf("Calculating ...")

        var date: Date? = null
        var sumTime = 0.0
        var counter = 0

        fun rotateState() {
            if (state.value.first().index == 1) {
                if (date == null) date = Date() else {
                    counter++
                    val newDate = Date()
                    val cycleTime = (newDate.getTime() - date!!.getTime()) / 1000
                    sumTime += cycleTime
                    val averageTime = (sumTime / counter).asDynamic().toFixed(3)
                    kotlin.js.console.log("Cycle time: $cycleTime")
                    kotlin.js.console.log("Average time: $averageTime")
                    info.value = "Last cycle time: $cycleTime, average time: $averageTime"
                    date = newDate
                }
            }
            val count = (size * ratio * size).toInt()
            val part = state.value.takeLast(count)
            state.value = part + state.value.dropLast(count)
        }

        root("root") {
            div {
                setStyle("display", "flex")
                div {
                    setStyle("margin-bottom", "20px")
                    +info.value
                }
                div {
                    setStyle("padding", "10px")
                    div("gridstyle") {
                        setStyle("display", "grid")
                        setStyle("grid-template-columns", "repeat($size, minmax(5px, 1fr))")
                        setStyle("justify-items", "center")
                        for (i in state.value) {
                            //key("${i.index}") {
                            div {
                                background = i.bg
                            }
                            //}
                        }
                    }
                }
            }
        }
        window.setInterval({
            rotateState()
        }, 0)
    }
}

fun main() {
    startApplication(::App, js("import.meta.webpackHot").unsafeCast<Hot?>(), CoreModule, TrixModule)
}
