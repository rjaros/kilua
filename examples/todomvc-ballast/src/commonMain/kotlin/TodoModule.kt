import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import web.storage.localStorage

val todoModule = module {
    single {
        Json {
            prettyPrint = true
        }
    }
    single {
        CoroutineScope(Dispatchers.Default + SupervisorJob())
    }
    single {
        localStorage
    }
    singleOf(::TodoInputHandler)
    singleOf(::TodoEventHandler)
    singleOf(::TodoSavedStateAdapter)
    singleOf(::TodoViewModel)
}
