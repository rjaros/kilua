package dev.kilua

import dev.kilua.dom.core.JsAny

@JsModule("zzz-kilua-assets/k-style.css")
internal external object CoreCss : JsAny

/**
 * Initializer for Kilua core module.
 */
public actual object CoreModule : ModuleInitializer {
    actual override fun initialize()  {
        useModule(CoreCss)
        CssRegister.register("zzz-kilua-assets/k-style.css")
    }
}
