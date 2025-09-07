import dev.kilua.Hot
import kotlin.js.unsafeCast

actual fun webpackHot(): Hot? {
    return js("import.meta.webpackHot").unsafeCast<Hot?>()
}
