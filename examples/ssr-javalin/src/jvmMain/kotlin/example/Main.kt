package example

import dev.kilua.ssr.initSsr
import io.javalin.Javalin

fun main() {
    Javalin.create().start(8080).apply {
        initSsr()
    }
}
