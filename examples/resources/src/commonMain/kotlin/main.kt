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

import dev.kilua.Application
import dev.kilua.CoreModule
import dev.kilua.compose.root
import dev.kilua.externals.console
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.helpers.onClickLaunch
import dev.kilua.html.px
import dev.kilua.resources.generated.resources.Res
import dev.kilua.resources.generated.resources.app_name
import dev.kilua.startApplication
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.getString


class App : Application() {

    @OptIn(ExperimentalResourceApi::class)
    override fun start() {
        root("root") {
            div {
                margin(20.px)

                button("Test compose resources") {
                    onClickLaunch {
                        console.log(Res.readBytes("files/test.json").decodeToString())
                        console.log(getString(Res.string.app_name))
                    }
                }
            }
        }
    }
}

fun main() {
    startApplication(
        ::App,
        null,
        CoreModule
    )
}
