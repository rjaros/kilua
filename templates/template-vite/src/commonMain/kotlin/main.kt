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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.Hot
import dev.kilua.compose.root
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.startApplication

class App : Application() {

    var counter by mutableStateOf(0)

    override fun start(state: String?) {
        if (state != null) {
            counter = state.toInt()
        }
        root("root") {
            div {
                +"Counter value: $counter"
            }
            button("Increment") {
                onClick {
                    counter++
                }
            }
        }
    }

    override fun dispose(): String {
        return counter.toString()
    }
}

fun main() {
    startApplication(::App, viteHot())
}

expect fun viteHot(): Hot?
