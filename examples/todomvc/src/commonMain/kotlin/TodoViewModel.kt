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

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import web.storage.localStorage

class ViewModel {
    private val appScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val json = Json {
        prettyPrint = true
    }

    private val _state = MutableStateFlow(State(emptyList()))
    val state = _state.asStateFlow()

    init {
        _state.update { it.copy(todos = loadModel()) }
        _state.onEach { state ->
            saveModel(state.todos)
        }.launchIn(appScope)
    }

    fun processInput(input: Input) {
        _state.update { handleInput(it, input) }
    }

    fun addTodo(value: String?) {
        val v = value?.trim() ?: ""
        if (v.isNotEmpty()) {
            processInput(Input.Add(Todo(false, v)))
        }
    }

    fun editTodo(index: Int, value: String?) {
        val v = value?.trim() ?: ""
        if (v.isNotEmpty()) {
            processInput(Input.ChangeTitle(index, v))
        } else {
            processInput(Input.Delete(index))
        }
    }

    private fun handleInput(state: State, input: Input): State {
        return when (input) {
            is Input.Add -> state.copy(todos = state.todos + input.todo)

            is Input.ChangeTitle -> state.copy(todos = state.todos.mapIndexed { index, todo ->
                if (index == input.index) todo.copy(title = input.title) else todo
            })

            is Input.ToggleActive -> state.copy(todos = state.todos.mapIndexed { index, todo ->
                if (index == input.index) todo.copy(completed = !todo.completed) else todo
            })

            is Input.ToggleAll -> {
                val areAllCompleted = state.areAllCompleted()
                state.copy(todos = state.todos.map { it.copy(completed = !areAllCompleted) })
            }

            is Input.Delete -> state.copy(todos = state.todos.filterIndexed { index, _ ->
                (index != input.index)
            })

            is Input.ClearCompleted -> state.copy(todos = state.activeList())
        }
    }

    private fun loadModel(): List<Todo> {
        return localStorage.getItem("todos-kilua")?.let {
            json.decodeFromString(
                ListSerializer(Todo.serializer()),
                it
            )
        } ?: emptyList()
    }

    private fun saveModel(todos: List<Todo>) {
        val jsonString = json.encodeToString(ListSerializer(Todo.serializer()), todos)
        localStorage.setItem("todos-kilua", jsonString)
    }

}
