import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.CoreModule
import dev.kilua.Hot
import dev.kilua.compose.root
import dev.kilua.form.number.numeric
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.hr
import dev.kilua.html.unaryPlus
import dev.kilua.startApplication

class App : Application() {

    override fun start() {
        root("root") {


            val num = numeric(123.323) {
                onChange {
                    console.log(this.value?.toString())
                }
            }

            button("test numeric") {
                onClick {
                    console.log(num.value.toString())
                    console.log(num.getValueAsString())
                }
            }

            button("set numeric") {
                onClick {
                    num.value = 123.456
                }
            }

            hr()

            var state by remember { mutableStateOf("Hello, world!") }

            div {
                +state
            }
            button("Add an exclamation mark") {
                onClick {
                    state += "!"
                }
            }
        }
    }
}

fun main() {
    startApplication(::App, js("import.meta.webpackHot").unsafeCast<Hot?>(), CoreModule)
}
