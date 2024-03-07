package example

import dev.kilua.rpc.applyRoutes
import dev.kilua.rpc.getAllServiceManagers
import dev.kilua.rpc.initRpc
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

@Module
@ComponentScan
class PingModule

fun Application.main() {
    install(Compression)
    install(WebSockets)
    routing {
        getAllServiceManagers().forEach { applyRoutes(it) }
    }
    initRpc(PingModule().module)
}
