package example

import dev.kilua.ssr.initSsr
import io.javalin.Javalin

fun main() {
    Javalin.create { config ->
        config.initSsr()
    }.start(8080)
}
