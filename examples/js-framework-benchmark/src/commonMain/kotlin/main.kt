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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeRuntimeFlags
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.compose.root
import dev.kilua.core.IComponent
import dev.kilua.html.a
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.h1
import dev.kilua.html.span
import dev.kilua.html.table
import dev.kilua.html.tbody
import dev.kilua.html.td
import dev.kilua.html.tr
import dev.kilua.startApplication
import web.events.EventHandler

var idCounter = 1

data class Row(val id: Int, val label: String)

fun buildData(count: Int): List<Row> {
    return List(count, init = {
        Row(
            idCounter++,
            "${adjectives.random()} ${colours.random()} ${nouns.random()}"
        )
    })
}

val adjectives = arrayOf(
    "pretty",
    "large",
    "big",
    "small",
    "tall",
    "short",
    "long",
    "handsome",
    "plain",
    "quaint",
    "clean",
    "elegant",
    "easy",
    "angry",
    "crazy",
    "helpful",
    "mushy",
    "odd",
    "unsightly",
    "adorable",
    "important",
    "inexpensive",
    "cheap",
    "expensive",
    "fancy"
)
val colours = arrayOf("red", "yellow", "blue", "green", "pink", "brown", "purple", "brown", "white", "black", "orange")
val nouns = arrayOf(
    "table",
    "chair",
    "house",
    "bbq",
    "desk",
    "car",
    "pony",
    "cookie",
    "sandwich",
    "burger",
    "pizza",
    "mouse",
    "keyboard"
)

@Composable
fun IComponent.group(content: @Composable IComponent.() -> Unit) {
    content()
}

class App : Application() {
    var data by mutableStateOf(emptyList<Row>())
    var selected by mutableStateOf<Int?>(null)

    override fun start() {
        root("root") {
            div(id = "main") {
                div("container") {
                    div("jumbotron") {
                        div("row") {
                            div("col-md-6") {
                                h1 {
                                    +"Kilua"
                                }
                            }
                            div("col-md-6") {
                                div("row") {
                                    div("col-sm-6 smallpad") {
                                        button(
                                            "Create 1,000 rows",
                                            id = "run",
                                            className = "btn btn-primary btn-block"
                                        ) {
                                            onClick {
                                                data = buildData(1000)
                                            }
                                        }
                                    }
                                    div("col-sm-6 smallpad") {
                                        button(
                                            "Create 10,000 rows",
                                            id = "runlots",
                                            className = "btn btn-primary btn-block"
                                        ) {
                                            onClick {
                                                data = buildData(10_000)
                                            }
                                        }
                                    }
                                    div("col-sm-6 smallpad") {
                                        button(
                                            "Append 1,000 rows",
                                            id = "add",
                                            className = "btn btn-primary btn-block"
                                        ) {
                                            onClick {
                                                data += buildData(1000)
                                            }
                                        }
                                    }
                                    div("col-sm-6 smallpad") {
                                        button(
                                            "Update every 10th row",
                                            id = "update",
                                            className = "btn btn-primary btn-block"
                                        ) {
                                            onClick {
                                                data = data.mapIndexed { index, row ->
                                                    if (index % 10 == 0) {
                                                        row.copy(label = "${row.label} !!!")
                                                    } else {
                                                        row
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    div("col-sm-6 smallpad") {
                                        button("Clear", id = "clear", className = "btn btn-primary btn-block") {
                                            onClick {
                                                data = emptyList()
                                            }
                                        }
                                    }
                                    div("col-sm-6 smallpad") {
                                        button("Swap Rows", id = "swaprows", className = "btn btn-primary btn-block") {
                                            onClick {
                                                if (data.size > 998) {
                                                    val newData = data.toTypedArray()
                                                    val temp = newData[1]
                                                    newData[1] = newData[998]
                                                    newData[998] = temp
                                                    data = newData.asList()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    table("table table-hover table-striped test-data") {
                        tbody {
                            for (chunk in data.chunked(100)) {
                                group {
                                    for (item in chunk) {
                                        val isSelected = selected == item.id
                                        row(
                                            item, isSelected,
                                            select = {
                                                selected = item.id
                                            },
                                            delete = {
                                                val newData = data.toMutableList()
                                                newData.remove(item)
                                                data = newData
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    span("preloadicon glyphicon glyphicon-remove") {
                        attribute("aria-hidden", "true")
                    }
                }
            }
        }
    }

    @Composable
    private fun IComponent.row(item: Row, selected: Boolean, select: () -> Unit, delete: () -> Unit) {
        tr {
            className(if (selected) "danger" else null)
            td(className = "col-md-1") {
                +item.id.toString()
            }
            td(className = "col-md-4") {
                a(label = item.label) {
                    element.onclick = EventHandler {
                        select()
                    }
                }
            }
            td(className = "col-md-1") {
                a {
                    span("glyphicon glyphicon-remove") {
                        attribute("aria-hidden", "true")
                    }
                    element.onclick = EventHandler {
                        delete()
                    }
                }
            }
            td(className = "col-md-6")
        }
    }
}

@OptIn(ExperimentalComposeApi::class)
fun main() {
    ComposeRuntimeFlags.isLinkBufferComposerEnabled = true
    startApplication(::App)
}
