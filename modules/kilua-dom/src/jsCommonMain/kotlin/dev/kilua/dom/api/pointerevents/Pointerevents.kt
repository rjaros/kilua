@file:Suppress(
    "NO_EXPLICIT_VISIBILITY_IN_API_MODE",
    "NO_EXPLICIT_RETURN_TYPE_IN_API_MODE",
    "EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE",
)
/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

// NOTE: THIS FILE IS AUTO-GENERATED, DO NOT EDIT!
// See github.com/kotlin/dukat for details

package dev.kilua.dom.api.pointerevents

import dev.kilua.dom.JsAny
import dev.kilua.dom.api.events.MouseEvent
import dev.kilua.dom.api.events.MouseEventInit

public external interface PointerEventInit : MouseEventInit, JsAny {
    var pointerId: Int? /* = 0 */

    var width: Double? /* = 1.0 */

    var height: Double? /* = 1.0 */

    var pressure: Float? /* = 0f */

    var tangentialPressure: Float? /* = 0f */

    var tiltX: Int? /* = 0 */

    var tiltY: Int? /* = 0 */

    var twist: Int? /* = 0 */

    var pointerType: String? /* = "" */

    var isPrimary: Boolean? /* = false */

}

/**
 * Exposes the JavaScript [PointerEvent](https://developer.mozilla.org/en/docs/Web/API/PointerEvent) to Kotlin
 */
public open external class PointerEvent(type: String, eventInitDict: PointerEventInit) : MouseEvent,
    JsAny {
    open val pointerId: Int
    open val width: Double
    open val height: Double
    open val pressure: Float
    open val tangentialPressure: Float
    open val tiltX: Int
    open val tiltY: Int
    open val twist: Int
    open val pointerType: String
    open val isPrimary: Boolean

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}
