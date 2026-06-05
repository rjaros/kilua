package example

import dev.kilua.rpc.applyRoutes
import dev.kilua.rpc.getAllServiceManagers
import dev.kilua.rpc.initRpcKoin
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val pingModule = module {
    factoryOf(::PingService) bind IPingService::class
}

fun Application.main() {
    install(Compression)
    install(WebSockets)
    routing {
        getAllServiceManagers().forEach { applyRoutes(it) }
    }
    initRpcKoin {
        modules(pingModule)
    }
}
