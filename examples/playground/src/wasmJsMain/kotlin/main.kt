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
import dev.kilua.html.Border
import dev.kilua.html.BorderStyle
import dev.kilua.html.C
import dev.kilua.html.Color
import dev.kilua.html.button
import dev.kilua.html.perc
import dev.kilua.html.px
import dev.kilua.html.unaryPlus
import dev.kilua.panel.Dir
import dev.kilua.panel.SplitPanel
import dev.kilua.panel.splitPanel
import dev.kilua.startApplication
import dev.kilua.utils.JsNonModule
import dev.kilua.utils.cast
import dev.kilua.utils.console
import dev.kilua.utils.useCssModule
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
