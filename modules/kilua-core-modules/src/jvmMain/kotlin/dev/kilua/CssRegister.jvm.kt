package dev.kilua

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.runBlocking

public actual fun registerCssModule(cssFile: String) {
    val coroutineContext = runBlocking { currentCoroutineContext() }
    val appContext = coroutineContext[AppContext]
        ?: throw IllegalStateException("Not the app context")
    appContext.registerCssFile(cssFile)
}

/**
 * Kilua CSS register.
 */
public class CssRegister {

    public val cssFiles: MutableList<String> = mutableListOf()

    /**
     * Register CSS file used by the Kilua Modules.
     */
    public fun register(cssFile: String) {
        cssFiles.add(cssFile)
    }
}
