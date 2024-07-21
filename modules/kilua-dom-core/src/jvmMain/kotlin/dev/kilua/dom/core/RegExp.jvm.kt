package dev.kilua.dom.core

/**
 * JavaScript RegExp class.
 */
public actual class RegExp actual constructor(pattern: String, flags: String?) : JsAny {
    public actual constructor(pattern: String) : this(pattern, null)
}
