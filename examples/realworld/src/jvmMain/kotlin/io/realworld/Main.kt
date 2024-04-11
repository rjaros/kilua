package io.realworld

import dev.kilua.ssr.initSsr
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*

fun Application.main() {
    install(Compression)
    initSsr()
}
