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
import dev.kilua.Application
import dev.kilua.compose.root
import dev.kilua.core.IComponent
import dev.kilua.form.check.checkBox
import dev.kilua.form.text.Text
import dev.kilua.form.text.text
import dev.kilua.form.text.textRef
import dev.kilua.html.a
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.footer
import dev.kilua.html.h1
import dev.kilua.html.header
import dev.kilua.html.label
import dev.kilua.html.li
import dev.kilua.html.section
import dev.kilua.html.span
import dev.kilua.html.strong
import dev.kilua.html.ul
import dev.kilua.routing.hashRouter
import dev.kilua.startApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import web.cssom.ClassName
import web.keyboard.Enter
import web.keyboard.Escape
import web.keyboard.KeyCode
import web.uievents.FocusEvent
import web.uievents.KeyboardEvent
import web.uievents.MouseEvent

class App : Application(), KoinComponent {

    private val todoViewModel: TodoViewModel by inject()

    override fun start() {
        root("root") {
            hashRouter {
                route("/") {
                    view {
                        todoView(Mode.All)
                    }
                }
                route("/active") {
                    view {
                        todoView(Mode.Active)
                    }
                }
                route("/completed") {
                    view {
                        todoView(Mode.Completed)
                    }
                }
            }
        }
    }

    @Composable
    private fun IComponent.todoView(mode: Mode) {
        val state by todoViewModel.observeStates().collectAsState()

        section("todoapp") {
            header("header") {
                h1 { +"todos" }
                text(placeholder = "What needs to be done?", className = "new-todo") {
                    autofocus(true)
                    onEvent<KeyboardEvent>("keydown") { e ->
                        if (e.code == KeyCode.Enter) {
                            todoViewModel.addTodo(this.value)
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
                            todoViewModel.trySend(TodoContract.Inputs.ToggleAll)
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
                                            todoViewModel.trySend(TodoContract.Inputs.ToggleActive(index))
                                        }
                                    }
                                    label {
                                        +todo.title
                                        onEvent<MouseEvent>("dblclick") {
                                            this@li.element.classList.add(ClassName("editing"))
                                            edit.value = todo.title
                                            edit.focus()
                                        }
                                    }
                                    button("", className = "destroy") {
                                        onClick {
                                            todoViewModel.trySend(TodoContract.Inputs.Delete(index))
                                        }
                                    }
                                }
                                edit = textRef(className = "edit") {
                                    onEvent<FocusEvent>("blur") {
                                        if (this@li.element.classList.contains(ClassName("editing"))) {
                                            this@li.element.classList.remove(ClassName("editing"))
                                            todoViewModel.editTodo(index, this.value)
                                        }
                                    }
                                    onEvent<KeyboardEvent>("keydown") { e ->
                                        if (e.code == KeyCode.Enter) {
                                            todoViewModel.editTodo(index, this.value)
                                            this@li.element.classList.remove(ClassName("editing"))
                                        }
                                        if (e.code == KeyCode.Escape) {
                                            this@li.element.classList.remove(ClassName("editing"))
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
                            a("#/", "All", className = if (mode == Mode.All) "selected" else null)
                        }
                        li {
                            a(
                                "#/active", "Active",
                                className = if (mode == Mode.Active) "selected" else null
                            )
                        }
                        li {
                            a(
                                "#/completed", "Completed",
                                className = if (mode == Mode.Completed) "selected" else null
                            )
                        }
                    }
                    if (state.completedList().isNotEmpty()) {
                        button("Clear completed", className = "clear-completed") {
                            onClick {
                                todoViewModel.trySend(TodoContract.Inputs.ClearCompleted)
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    startKoin {
        modules(todoModule)
    }
    startApplication(::App)
}
