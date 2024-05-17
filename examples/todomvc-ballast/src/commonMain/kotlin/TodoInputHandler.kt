import Mode.*
import TodoContract.Inputs.*
import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope

class TodoInputHandler : InputHandler<TodoContract.Inputs, TodoContract.Events, TodoContract.State> {
    override suspend fun InputHandlerScope<TodoContract.Inputs, TodoContract.Events, TodoContract.State>.handleInput(
        input: TodoContract.Inputs
    ) = when (input) {
        is Load -> updateState { it.copy(todos = input.todos, mode = All) }
        is Add -> updateState { it.copy(todos = it.todos + input.todo) }
        is ChangeTitle -> updateState {
            it.copy(todos = it.todos.mapIndexed { index, todo ->
                if (index == input.index) todo.copy(title = input.title) else todo
            })
        }

        is ToggleActive -> updateState {
            it.copy(todos = it.todos.mapIndexed { index, todo ->
                if (index == input.index) todo.copy(completed = !todo.completed) else todo
            })
        }

        is ToggleAll -> {
            updateState {
                val areAllCompleted = it.areAllCompleted()
                it.copy(todos = it.todos.map { it.copy(completed = !areAllCompleted) })
            }
        }

        is Delete -> updateState {
            it.copy(todos = it.todos.filterIndexed { index, _ ->
                (index != input.index)
            })
        }

        is ClearCompleted -> updateState { it.copy(todos = it.activeList()) }
        is ShowAll -> updateState { it.copy(mode = All) }
        is ShowActive -> updateState { it.copy(mode = Active) }
        is ShowCompleted -> updateState { it.copy(mode = Completed) }
    }
}
