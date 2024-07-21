package dev.kilua.dom.core

public actual class JsString internal constructor(internal val value: String): JsAny

public actual fun String.toJsString(): JsString = JsString(this)
