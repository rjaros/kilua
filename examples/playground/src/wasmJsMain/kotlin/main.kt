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

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.compose.root
import dev.kilua.form.InputType
import dev.kilua.form.number.range
import dev.kilua.form.number.spinner
import dev.kilua.form.text.WrapType
import dev.kilua.form.text.text
import dev.kilua.form.text.textArea
import dev.kilua.form.time.date
import dev.kilua.form.time.dateTime
import dev.kilua.form.time.time
import dev.kilua.html.Background
import dev.kilua.html.Border
import dev.kilua.html.BorderStyle
import dev.kilua.html.ButtonType
import dev.kilua.html.Col
import dev.kilua.html.Color
import dev.kilua.html.Div
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.tag
import dev.kilua.html.textNode
import dev.kilua.html.unaryPlus
import dev.kilua.startApplication
import dev.kilua.utils.JsNonModule
import dev.kilua.utils.console
import dev.kilua.utils.hour
import dev.kilua.utils.now
import dev.kilua.utils.obj
import dev.kilua.utils.px
import dev.kilua.utils.today
import dev.kilua.utils.useCssModule
import kotlinx.browser.window
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import org.w3c.dom.Text
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
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
        val today = today()
        val now = now()
        val hour = hour()

        root("root") {
            console.log("recomposing")

            text(type = InputType.File) {
                onChange {
                    console.log("$value")
                }
            }
            div()

            val time = time(hour, step = 1) {
                onChange {
                    console.log("time change $value")
                }
            }
            button("test time") {
                onClick {
                    console.log(time.value.toString())
                }
            }
            button("test2 time up").onClick {
                time.stepUp()
            }
            button("test2 time down").onClick {
                time.stepDown()
            }
            div()

            val dateTime = dateTime(now) {
                onChange {
                    console.log("dateTime change $value")
                }
            }
            button("test dateTime") {
                onClick {
                    console.log(dateTime.value.toString())
                }
            }
            button("test2 dateTime up").onClick {
                dateTime.stepUp()
            }
            button("test2 dateTime down").onClick {
                dateTime.stepDown()
            }

            val date = date(today) {
                defaultValue = today.minus(2, DateTimeUnit.DAY)
                onChange {
                    console.log("date change $value")
                }
            }
            button("test date") {
                onClick {
                    console.log(date.value.toString())
                }
            }
            button("test2 date up").onClick {
                date.stepUp()
            }
            button("test2 date down").onClick {
                date.stepDown()
            }

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
                    console.log("" + (range.value!!.toInt() + 10))
                    range.stepUp()
                }
            }

            val spin = spinner(counter, min = 10, max = 20) {
                onChange {
                    console.log("spinner change $value")
                }
            }
            spin.subscribe { console.log("spinner $it") }
            button("test spinner") {
                onClick {
                    spin.stepUp()
                }
            }
            button("test spinner2") {
                onClick {
                    console.log("" + spin.value)
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

            val x = tag("address", "address3") {
                +"address2"
                setStyle("color", "red")
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
                            onClick {
                                console.log("click $name of ${list.size}")
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
            console.log(x2.text)
            button(type = type) {
                +"test span"
                if (type == ButtonType.Button) {
                    DisposableEffect("type") {
                        element
                        console.log("back to button")
                        onDispose { }
                    }
                }
                onEvent<MouseEvent>("click") {
                    disabled = !(disabled ?: false)
                    type = ButtonType.Submit
                    window.setTimeout({
                        disabled = !(disabled ?: false)
                        type = ButtonType.Button
                        obj()
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

public data class Bg(val index: Int, val bg: Background)

public external class Date {
    public fun getTime(): Double
}

public external class NumberF : JsAny {
    public fun toFixed(digits: Int): String
    public fun toString(radix: Int): String
}

public class App2 : Application() {
    override fun start() {
        val size = 50
        val ratio = 0.02

        @NoLiveLiterals
        fun sinToHex(i: Int, phase: Double): String {
            val sin = sin(PI / (size * size).toDouble() * 2 * i + phase)
            val int = floor(sin * 127) + 128
            return int.toJsNumber().unsafeCast<NumberF>().toString(16).padStart(2, '0')
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
                    val averageTime = ((sumTime / counter).toJsNumber()).unsafeCast<NumberF>().toFixed(3)
                    console.log("Cycle time: $cycleTime")
                    console.log("Average time: $averageTime")
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
                        console.log("recomposing grid")
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
            obj()
        }, 0)
    }
}

public fun main() {
    startApplication(::App)
}
