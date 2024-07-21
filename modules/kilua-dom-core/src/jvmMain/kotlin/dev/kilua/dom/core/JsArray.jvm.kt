package dev.kilua.dom.core

public actual class JsArray<T : JsAny?>(internal val list: MutableList<T>) : JsAny {
    public actual constructor() : this(mutableListOf<T>())

    public actual val length: Int
        get() = list.size

    public fun toMutableList(): MutableList<T> = list
}

public fun <T : JsAny?> List<T>.toJsArray(): JsArray<T> = JsArray(toMutableList())

public actual operator fun <T : JsAny?> JsArray<T>.get(index: Int): T? = list.getOrNull(index)
public actual operator fun <T : JsAny?> JsArray<T>.set(index: Int, value: T) {
    list[index] = value
}
