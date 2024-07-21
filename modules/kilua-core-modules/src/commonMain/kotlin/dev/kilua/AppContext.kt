package dev.kilua

import kotlin.coroutines.CoroutineContext

public class AppContext : CoroutineContext.Element {
    override val key: CoroutineContext.Key<*> = Key
    private val cssFiles = mutableListOf<String>()

    public fun registerCssFile(cssFile: String) {
        cssFiles.add(cssFile)
    }

    public companion object Key : CoroutineContext.Key<AppContext>
}
