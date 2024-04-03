package example

import dev.kilua.ssr.initSsr
import io.jooby.kt.runApp

fun main(args: Array<String>) {
    runApp(args) {
        initSsr()
    }
}
