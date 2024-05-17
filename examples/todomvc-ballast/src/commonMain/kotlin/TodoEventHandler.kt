import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope

class TodoEventHandler :
    EventHandler<TodoContract.Inputs, TodoContract.Events, TodoContract.State> {
    override suspend fun EventHandlerScope<TodoContract.Inputs, TodoContract.Events, TodoContract.State>.handleEvent(
        event: TodoContract.Events
    ) {
    }
}
