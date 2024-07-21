import Mode.All
import com.copperleaf.ballast.savedstate.RestoreStateScope
import com.copperleaf.ballast.savedstate.SaveStateScope
import com.copperleaf.ballast.savedstate.SavedStateAdapter
import dev.kilua.externals.get
import dev.kilua.externals.set
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import dev.kilua.dom.api.Storage
import dev.kilua.dom.core.toJsString

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
            storage["todos-kilua"] = jsonString.toJsString()
        }
    }

    override suspend fun RestoreStateScope<
            TodoContract.Inputs,
            TodoContract.Events,
            TodoContract.State>.restore(): TodoContract.State {
        return TodoContract.State(
            todos = storage["todos-kilua"]?.let {
                json.decodeFromString(
                    ListSerializer(Todo.serializer()),
                    it.toString()
                )
            } ?: emptyList(), All
        )
    }
}
