/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package dev.kilua.dom.core

public expect class JsArray<T : JsAny?>() : JsAny {
    public val length: Int
}

public expect operator fun <T : JsAny?> JsArray<T>.get(index: Int): T?

public expect operator fun <T : JsAny?> JsArray<T>.set(index: Int, value: T)
