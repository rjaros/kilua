package dev.kilua

public actual fun registerCssModule(cssFile: String): Unit = CssRegister.register(cssFile)

/**
 * Kilua CSS register.
 */
public object CssRegister {

    public val cssFiles: MutableList<String> = mutableListOf()

    /**
     * Register CSS file used by the Kilua Modules.
     */
    public fun register(cssFile: String) {
        cssFiles.add(cssFile)
    }
}
