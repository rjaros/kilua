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

fun handleInput(state: TodoContract.State, input: TodoContract.Inputs): TodoContract.State {
    return when (input) {
        is TodoContract.Inputs.Load -> state.copy(todos = input.todos, mode = MODE.ALL)
        is TodoContract.Inputs.Add -> state.copy(todos = state.todos + input.todo)

        is TodoContract.Inputs.ChangeTitle -> state.copy(todos = state.todos.mapIndexed { index, todo ->
            if (index == input.index) todo.copy(title = input.title) else todo
        })

        is TodoContract.Inputs.ToggleActive -> state.copy(todos = state.todos.mapIndexed { index, todo ->
            if (index == input.index) todo.copy(completed = !todo.completed) else todo
        })

        is TodoContract.Inputs.ToggleAll -> {
            val areAllCompleted = state.areAllCompleted()
            state.copy(todos = state.todos.map { it.copy(completed = !areAllCompleted) })
        }

        is TodoContract.Inputs.Delete -> state.copy(todos = state.todos.filterIndexed { index, _ ->
            (index != input.index)
        })

        is TodoContract.Inputs.ClearCompleted -> state.copy(todos = state.activeList())
        is TodoContract.Inputs.ShowAll -> state.copy(mode = MODE.ALL)
        is TodoContract.Inputs.ShowActive -> state.copy(mode = MODE.ACTIVE)
        is TodoContract.Inputs.ShowCompleted -> state.copy(mode = MODE.COMPLETED)
    }
}
