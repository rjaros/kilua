import Mode.All
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.plusAssign
import com.copperleaf.ballast.savedstate.BallastSavedStateInterceptor
import com.copperleaf.ballast.withViewModel
import kotlinx.coroutines.CoroutineScope

class TodoViewModel(
    coroutineScope: CoroutineScope,
    todoInputHandler: TodoInputHandler,
    todoEventHandler: TodoEventHandler,
    todoSavedStateAdapter: TodoSavedStateAdapter
) : BasicViewModel<
        TodoContract.Inputs,
        TodoContract.Events,
        TodoContract.State>(
    config = BallastViewModelConfiguration.Builder().apply {
        this += LoggingInterceptor()
        logger = { PrintlnLogger(it) }
        this += BallastSavedStateInterceptor(
            todoSavedStateAdapter
        )
    }.withViewModel(
        initialState = TodoContract.State(emptyList(), All),
        inputHandler = todoInputHandler,
        name = "TodoScreen",
    ).build(),
    eventHandler = todoEventHandler,
    coroutineScope = coroutineScope,
) {

    fun addTodo(value: String?) {
        val v = value?.trim() ?: ""
        if (v.isNotEmpty()) {
            trySend(TodoContract.Inputs.Add(Todo(false, v)))
        }
    }

    fun editTodo(index: Int, value: String?) {
        val v = value?.trim() ?: ""
        if (v.isNotEmpty()) {
            trySend(TodoContract.Inputs.ChangeTitle(index, v))
        } else {
            trySend(TodoContract.Inputs.Delete(index))
        }
    }

}
