package dev.kilua.dom.core

public actual class JsNumber internal constructor(internal val value: Double) : JsAny

public actual fun JsNumber.toDouble(): Double = value

public actual fun Double.toJsNumber(): JsNumber = JsNumber(this)

public actual fun JsNumber.toInt(): Int = value.toInt()

public actual fun Int.toJsNumber(): JsNumber = JsNumber(toDouble())
