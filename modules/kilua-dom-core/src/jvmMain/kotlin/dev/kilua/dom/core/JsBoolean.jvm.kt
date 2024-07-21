package dev.kilua.dom.core

public actual class JsBoolean internal constructor(internal val value: Boolean) : JsAny

public actual fun JsBoolean.toBoolean(): Boolean = value

public actual fun Boolean.toJsBoolean(): JsBoolean = JsBoolean(this)
