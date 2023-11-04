import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.softwork.routingcompose.BrowserRouter
import dev.kilua.Application
import dev.kilua.CoreModule
import dev.kilua.Hot
import dev.kilua.KiluaScope
import dev.kilua.compose.root
import dev.kilua.externals.obj
import dev.kilua.form.check.checkBox
import dev.kilua.form.check.radio
import dev.kilua.form.check.radioGroup
import dev.kilua.form.check.triStateCheckBox
import dev.kilua.form.color.colorPicker
import dev.kilua.form.number.numeric
import dev.kilua.form.number.range
import dev.kilua.form.number.spinner
import dev.kilua.form.select.select
import dev.kilua.form.text.textArea
import dev.kilua.form.time.date
import dev.kilua.form.upload.upload
import dev.kilua.html.*
import dev.kilua.html.helpers.onClickLaunch
import dev.kilua.panel.Dir
import dev.kilua.panel.SplitPanel
import dev.kilua.panel.splitPanel
import dev.kilua.startApplication
import dev.kilua.utils.cast
import dev.kilua.utils.today
import kotlinx.browser.window
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.w3c.dom.CustomEvent

class App : Application() {

    override fun start() {

        root("root") {
            div { +"Hello world" }
        }
    }
}

fun main() {
    startApplication(::App, js("import.meta.webpackHot").unsafeCast<Hot?>())
}
