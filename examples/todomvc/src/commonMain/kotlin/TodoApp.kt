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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.softwork.routingcompose.HashRouter
import dev.kilua.Application
import dev.kilua.compose.root
import dev.kilua.core.IComponent
import dev.kilua.form.check.checkBox
import dev.kilua.form.text.Text
import dev.kilua.form.text.text
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.footer
import dev.kilua.html.h1
import dev.kilua.html.header
import dev.kilua.html.label
import dev.kilua.html.li
import dev.kilua.html.link
import dev.kilua.html.section
import dev.kilua.html.span
import dev.kilua.html.strong
import dev.kilua.html.ul
import dev.kilua.startApplication
import web.dom.events.FocusEvent
import web.dom.events.KeyboardEvent
import web.dom.events.MouseEvent

const val ENTER_KEY = 13
const val ESC_KEY = 27

class App : Application() {

    val viewModel = ViewModel()

    override fun start() {
        root("root") {
            HashRouter(initPath = "/") {
                route("/") {
                    todoView(Mode.All)
                }
                route("/active") {
                    todoView(Mode.Active)
                }
                route("/completed") {
                    todoView(Mode.Completed)
                }
            }
        }
    }

    @Composable
    private fun IComponent.todoView(mode: Mode) {
        val state by viewModel.state.collectAsState()
        section("todoapp") {
            header("header") {
                h1 { +"todos" }
                text(placeholder = "What needs to be done?", className = "new-todo") {
                    autofocus(true)
                    onEvent<KeyboardEvent>("keydown") { e ->
                        if (e.keyCode == ENTER_KEY) {
                            viewModel.addTodo(this.value)
                            this.value = null
                        }
                    }
                }
            }
            if (state.todos.isNotEmpty()) {
                section("main") {
                    checkBox(state.areAllCompleted(), className = "toggle-all") {
                        id("toggle-all")
                        onClick {
                            viewModel.processInput(Input.ToggleAll)
                        }
                    }
                    label("toggle-all") {
                        +"Mark all as complete"
                    }
                    ul("todo-list") {
                        when (mode) {
                            Mode.All -> state.allListIndexed()
                            Mode.Active -> state.activeListIndexed()
                            Mode.Completed -> state.completedListIndexed()
                        }.forEach { (index, todo) ->
                            li(className = if (todo.completed) "completed" else null) {
                                lateinit var edit: Text
                                div("view") {
                                    checkBox(todo.completed, className = "toggle") {
                                        onClick {
                                            viewModel.processInput(Input.ToggleActive(index))
                                        }
                                    }
                                    label {
                                        +todo.title
                                        onEvent<MouseEvent>("dblclick") {
                                            this@li.element.classList.add("editing")
                                            edit.value = todo.title
                                            edit.focus()
                                        }
                                    }
                                    button("", className = "destroy") {
                                        onClick {
                                            viewModel.processInput(Input.Delete(index))
                                        }
                                    }
                                }
                                edit = text(className = "edit") {
                                    onEvent<FocusEvent>("blur") {
                                        if (this@li.element.classList.contains("editing")) {
                                            this@li.element.classList.remove("editing")
                                            viewModel.editTodo(index, this.value)
                                        }
                                    }
                                    onEvent<KeyboardEvent>("keydown") { e ->
                                        if (e.keyCode == ENTER_KEY) {
                                            viewModel.editTodo(index, this.value)
                                            this@li.element.classList.remove("editing")
                                        }
                                        if (e.keyCode == ESC_KEY) {
                                            this@li.element.classList.remove("editing")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                footer("footer") {
                    val itemsLeftString = if (state.activeList().size == 1) " item left" else " items left"
                    span("todo-count") {
                        strong {
                            +"${state.activeList().size}"
                        }
                        +itemsLeftString
                    }
                    ul(className = "filters") {
                        li {
                            link("#/", "All", className = if (mode == Mode.All) "selected" else null)
                        }
                        li {
                            link(
                                "#/active", "Active",
                                className = if (mode == Mode.Active) "selected" else null
                            )
                        }
                        li {
                            link(
                                "#/completed", "Completed",
                                className = if (mode == Mode.Completed) "selected" else null
                            )
                        }
                    }
                    if (state.completedList().isNotEmpty()) {
                        button("Clear completed", className = "clear-completed") {
                            onClick {
                                viewModel.processInput(Input.ClearCompleted)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    startApplication(::App)
}
