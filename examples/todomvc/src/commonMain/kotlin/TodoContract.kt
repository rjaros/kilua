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

object TodoContract {
    data class State(val todos: List<Todo>, val mode: MODE) {
        fun areAllCompleted() = todos.find { !it.completed } == null
        fun activeList() = todos.filter { !it.completed }
        fun completedList() = todos.filter { it.completed }
        fun allListIndexed() = todos.mapIndexed { index, todo -> index to todo }
        fun activeListIndexed() = allListIndexed().filter { !it.second.completed }
        fun completedListIndexed() = allListIndexed().filter { it.second.completed }
    }

    sealed class Inputs {
        data class Load(val todos: List<Todo>) : Inputs()
        data class Add(val todo: Todo) : Inputs()
        data class ChangeTitle(val index: Int, val title: String) : Inputs()
        data class ToggleActive(val index: Int) : Inputs()
        data class Delete(val index: Int) : Inputs()
        data object ToggleAll : Inputs()
        data object ClearCompleted : Inputs()
        data object ShowAll : Inputs()
        data object ShowActive : Inputs()
        data object ShowCompleted : Inputs()
    }
}
