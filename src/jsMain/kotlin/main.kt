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

import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.Hot
import dev.kilua.compose.root
import dev.kilua.form.number.range
import dev.kilua.form.number.spinner
import dev.kilua.form.text.WrapType
import dev.kilua.form.text.text
import dev.kilua.form.text.textArea
import dev.kilua.html.Background
import dev.kilua.html.ButtonType
import dev.kilua.html.Color
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.helpers.onClickLaunch
import dev.kilua.html.unaryPlus
import dev.kilua.startApplication
import dev.kilua.utils.JsNonModule
import dev.kilua.utils.console
import dev.kilua.utils.log
import dev.kilua.utils.obj
import dev.kilua.utils.useCssModule
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlin.js.Date
import kotlin.math.PI
import kotlin.math.floor
import kotlin.math.sin

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

            var counter by remember { mutableStateOf(0) }
            div {
                +"$counter"
            }

            val range = range(counter, min = 10, max = 20) {
                onChange {
                    console.log("range change $value")
                }
            }
            button("test range") {
                onClick {
                    console.log(range.value)
                    range.stepUp()
                }
            }

            val spin = spinner(counter, min = 10, max = 20) {
                onChange {
                    console.log("spinner change $value")
                }
            }
            button("test spinner") {
                onClick {
                    spin.stepUp()
                }
            }


            var wrap by remember { mutableStateOf(WrapType.Soft) }

            val ta = textArea("$wrap", cols = 30, rows = 10) {
                autofocus = true
                this.wrap = wrap
            }
            button("test ta") {
                onClick {
                    console.log(ta.value)
                }
            }
            button("test ta2") {
                onClick {
                    wrap = WrapType.Hard
                }
            }

            var type by remember { mutableStateOf(ButtonType.Button) }

            val i1 = text(type.value, className = "test")
            i1.subscribe {
                console.log("sub $it")
            }

            val b2 = button(type) {
                +"test span"
                onClickLaunch {
                    console.log("abc")
                    delay(1000)
                    console.log("def")
                    disabled = !(disabled ?: false)
                    type = ButtonType.Submit
                    i1.className = "test2 test3"
                    window.setTimeout({
                        disabled = !(disabled ?: false)
                        type = ButtonType.Button
                        i1.className = "test3"
                        obj()
                    }, 2000)
                }
            }
            if (type != ButtonType.Submit) {
                button("test b2") {
                    onClick {
                        console.log(i1.value)
                    }
                }
            }
            button("set b2") {
                onClick {
                    this@root.dispose()
                }
            }

            /*val inp = input {
                defaultValue = "ala ma kota"
                value = "test"
                autocomplete = Autocomplete.On
            }
            button("get value") {
                onClick {
                    console.log(inp.value)
                }
            }
            button("set value") {
                onClick {
                    inp.value = "new value"
                }
            }
            button("set null") {
                onClick {
                    inp.value = null
                }
            }
            button("set external value") {
                onClick {
                    inp.element.value = "ext"
                }
            }
            button("set external null") {
                onClick {
                    inp.element.value = ""
                }
            }

            val x = tag("address", "address3") {
                +"address23"
                colorName = Col.Blue
                border = Border(1.px, BorderStyle.Dashed, Color.hex(0xaabbcc))
            }

            div {
                border = Border(1.px, BorderStyle.Solid, Color.name(Col.Black))
                width = 100.px
                height = 100.px
                setDragDropData("text/plain", "test 1")
            }

            div {
                border = Border(1.px, BorderStyle.Solid, Color.name(Col.Black))
                width = 100.px
                height = 100.px
                setDropTarget("text/plain") {
                    console.log(it.dataTransfer?.getData("text/plain"))
                }
            }

            var size by remember { mutableStateOf(1) }
            var evenSize by remember { mutableStateOf(1) }
            var oddSize by remember { mutableStateOf(1) }
            val tn by remember { mutableStateOf("address") }
            var list by remember { mutableStateOf(listOf("cat", "dog", "mouse")) }

            div {
                for (name in list) {
                    key(name) {
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
                        }
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
                    xb.label = "changed label"
                }
            }

            tag(tn) {
                +"address2 $size"
                id = "test"
                className = "address3"
                title = "Some title"
                role = "button"
                tabindex = 5
                draggable = true
                setAttribute("aria-label", "Ala ma kota")
            }
            val x2 = textNode("Ala ma kota")
            console.log("x")
            console.log(x2)

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
                    val x = button {
                        +"button odd$oddSize"
                        onClick {
                            console.log("click")
                            oddSize++
                        }
                        onDblclick {
                            console.log("dblclick")
                            oddSize--
                        }
                    }
                    button("odd test") {
                        onClick {
                            x.onClickDirect {
                                console.log("click 2")
                            }
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
                                divB.label = "test"
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
            }*/
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

public data class Bg(val index: Int, val bg: Background)

public class App2 : Application() {
    override fun start() {
        val size = 50
        val ratio = 0.02

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

        val root = root("root") {
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
        println(root.renderToString())
        window.setInterval({
            rotateState()
        }, 0)
    }
}

public fun main() {
    startApplication(::App, js("import.meta.webpackHot").unsafeCast<Hot?>())
}
