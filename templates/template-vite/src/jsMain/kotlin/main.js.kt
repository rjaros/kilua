import dev.kilua.Hot
import kotlin.js.unsafeCast

actual fun viteHot(): Hot? {
    return js("import.meta.hot").unsafeCast<Hot?>()
}
