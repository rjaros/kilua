import Mode.All
import com.copperleaf.ballast.savedstate.RestoreStateScope
import com.copperleaf.ballast.savedstate.SaveStateScope
import com.copperleaf.ballast.savedstate.SavedStateAdapter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import web.storage.Storage

class TodoSavedStateAdapter(private val json: Json, private val storage: Storage) :
    SavedStateAdapter<
            TodoContract.Inputs,
            TodoContract.Events,
            TodoContract.State> {

    override suspend fun SaveStateScope<
            TodoContract.Inputs,
            TodoContract.Events,
            TodoContract.State>.save() {
        saveAll { state ->
            val jsonString =
                json.encodeToString(ListSerializer(Todo.serializer()), state.todos)
            storage.setItem("todos-kilua", jsonString)
        }
    }

    override suspend fun RestoreStateScope<
            TodoContract.Inputs,
            TodoContract.Events,
            TodoContract.State>.restore(): TodoContract.State {
        return TodoContract.State(
            todos = storage.getItem("todos-kilua")?.let {
                json.decodeFromString(
                    ListSerializer(Todo.serializer()),
                    it
                )
            } ?: emptyList(), All
        )
    }
}
