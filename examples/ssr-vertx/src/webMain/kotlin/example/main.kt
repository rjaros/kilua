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

package example

import androidx.compose.runtime.Composable
import dev.kilua.Application
import dev.kilua.BootstrapCssModule
import dev.kilua.BootstrapModule
import dev.kilua.CoreModule
import dev.kilua.compose.root
import dev.kilua.core.IComponent
import dev.kilua.html.div
import dev.kilua.html.navLink
import dev.kilua.html.px
import dev.kilua.panel.vPanel
import dev.kilua.ssr.ssrRouter
import dev.kilua.startApplication
import kotlin.random.Random

@Composable
fun IComponent.template(content: @Composable IComponent.() -> Unit) {
    div {
        margin(20.px)
        vPanel(gap = 10.px) {
            content()
        }
    }
}

class App : Application() {

    override fun start() {

        root("root") {
            ssrRouter {
                route("/") {
                    view {
                        template {
                            div {
                                +"Root page"
                            }
                            navLink("/about", "Go to About page")
                            navLink("/page${Random.nextInt()}", "Go to random page")
                        }
                    }
                }
                route("/about") {
                    view {
                        template {
                            div {
                                +"About page"
                            }
                            navLink("/", "Return to root page")
                        }
                    }
                }
                string {
                    view {
                        template {
                            div {
                                +"A random page: $it"
                            }
                            navLink("/", "Return to root page")
                        }
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
        BootstrapModule,
        BootstrapCssModule,
        CoreModule
    )
}
