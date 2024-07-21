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

package dev.kilua.dom.api.events

import dev.kilua.dom.JsAny
import dev.kilua.dom.JsArray
import dev.kilua.dom.JsNumber
import dev.kilua.dom.api.*

/**
 * Exposes the JavaScript [UIEvent](https://developer.mozilla.org/en/docs/Web/API/UIEvent) to Kotlin
 */
public open external class UIEvent(type: String, eventInitDict: UIEventInit) : Event, JsAny {
    open val view: Window?
    open val detail: Int

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface UIEventInit : EventInit, JsAny {
    var view: Window? /* = null */

    var detail: Int? /* = 0 */

}

/**
 * Exposes the JavaScript [FocusEvent](https://developer.mozilla.org/en/docs/Web/API/FocusEvent) to Kotlin
 */
public open external class FocusEvent(type: String, eventInitDict: FocusEventInit) : UIEvent,
    JsAny {
    open val relatedTarget: EventTarget?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface FocusEventInit : UIEventInit, JsAny {
    var relatedTarget: EventTarget? /* = null */

}

/**
 * Exposes the JavaScript [MouseEvent](https://developer.mozilla.org/en/docs/Web/API/MouseEvent) to Kotlin
 */
public open external class MouseEvent(type: String, eventInitDict: MouseEventInit) : UIEvent,
    UnionElementOrMouseEvent, JsAny {
    constructor(type: String)

    open val screenX: Int
    open val screenY: Int
    open val clientX: Int
    open val clientY: Int
    open val ctrlKey: Boolean
    open val shiftKey: Boolean
    open val altKey: Boolean
    open val metaKey: Boolean
    open val button: Short
    open val buttons: Short
    open val relatedTarget: EventTarget?
    open val region: String?
    open val pageX: Double
    open val pageY: Double
    open val x: Double
    open val y: Double
    open val offsetX: Double
    open val offsetY: Double
    fun getModifierState(keyArg: String): Boolean

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface MouseEventInit : EventModifierInit, JsAny {
    var screenX: Int? /* = 0 */

    var screenY: Int? /* = 0 */

    var clientX: Int? /* = 0 */

    var clientY: Int? /* = 0 */

    var button: Short? /* = 0 */

    var buttons: Short? /* = 0 */

    var relatedTarget: EventTarget? /* = null */

    var region: String? /* = null */

}

public external interface EventModifierInit : UIEventInit, JsAny {
    var ctrlKey: Boolean? /* = false */

    var shiftKey: Boolean? /* = false */

    var altKey: Boolean? /* = false */

    var metaKey: Boolean? /* = false */

    var modifierAltGraph: Boolean? /* = false */

    var modifierCapsLock: Boolean? /* = false */

    var modifierFn: Boolean? /* = false */

    var modifierFnLock: Boolean? /* = false */

    var modifierHyper: Boolean? /* = false */

    var modifierNumLock: Boolean? /* = false */

    var modifierScrollLock: Boolean? /* = false */

    var modifierSuper: Boolean? /* = false */

    var modifierSymbol: Boolean? /* = false */

    var modifierSymbolLock: Boolean? /* = false */

}

/**
 * Exposes the JavaScript [WheelEvent](https://developer.mozilla.org/en/docs/Web/API/WheelEvent) to Kotlin
 */
public open external class WheelEvent(type: String, eventInitDict: WheelEventInit) : MouseEvent,
    JsAny {
    open val deltaX: Double
    open val deltaY: Double
    open val deltaZ: Double
    open val deltaMode: Int

    companion object {
        val DOM_DELTA_PIXEL: Int
        val DOM_DELTA_LINE: Int
        val DOM_DELTA_PAGE: Int
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface WheelEventInit : MouseEventInit, JsAny {
    var deltaX: Double? /* = 0.0 */

    var deltaY: Double? /* = 0.0 */

    var deltaZ: Double? /* = 0.0 */

    var deltaMode: Int? /* = 0 */

}

/**
 * Exposes the JavaScript [InputEvent](https://developer.mozilla.org/en/docs/Web/API/InputEvent) to Kotlin
 */
public open external class InputEvent(type: String, eventInitDict: InputEventInit) : UIEvent,
    JsAny {
    open val data: String
    open val isComposing: Boolean

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface InputEventInit : UIEventInit, JsAny {
    var data: String? /* = "" */

    var isComposing: Boolean? /* = false */

}

/**
 * Exposes the JavaScript [KeyboardEvent](https://developer.mozilla.org/en/docs/Web/API/KeyboardEvent) to Kotlin
 */
public open external class KeyboardEvent(type: String, eventInitDict: KeyboardEventInit) : UIEvent,
    JsAny {
    open val key: String
    open val code: String
    open val location: Int
    open val ctrlKey: Boolean
    open val shiftKey: Boolean
    open val altKey: Boolean
    open val metaKey: Boolean
    open val repeat: Boolean
    open val isComposing: Boolean
    open val charCode: Int
    open val keyCode: Int
    open val which: Int
    fun getModifierState(keyArg: String): Boolean

    companion object {
        val DOM_KEY_LOCATION_STANDARD: Int
        val DOM_KEY_LOCATION_LEFT: Int
        val DOM_KEY_LOCATION_RIGHT: Int
        val DOM_KEY_LOCATION_NUMPAD: Int
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface KeyboardEventInit : EventModifierInit, JsAny {
    var key: String? /* = "" */

    var code: String? /* = "" */

    var location: Int? /* = 0 */

    var repeat: Boolean? /* = false */

    var isComposing: Boolean? /* = false */

}

/**
 * Exposes the JavaScript [CompositionEvent](https://developer.mozilla.org/en/docs/Web/API/CompositionEvent) to Kotlin
 */
public open external class CompositionEvent(type: String, eventInitDict: CompositionEventInit) :
    UIEvent, JsAny {
    open val data: String

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface CompositionEventInit : UIEventInit, JsAny {
    var data: String? /* = "" */

}

/**
 * Exposes the JavaScript [Event](https://developer.mozilla.org/en/docs/Web/API/Event) to Kotlin
 */
public open external class Event(type: String, eventInitDict: EventInit) : JsAny {
    open val type: String
    open val target: EventTarget?
    open val currentTarget: EventTarget?
    open val eventPhase: Short
    open val bubbles: Boolean
    open val cancelable: Boolean
    open val defaultPrevented: Boolean
    open val composed: Boolean
    open val isTrusted: Boolean
    open val timeStamp: JsNumber
    fun composedPath(): JsArray<EventTarget>
    fun stopPropagation()
    fun stopImmediatePropagation()
    fun preventDefault()
    fun initEvent(type: String, bubbles: Boolean, cancelable: Boolean)

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

/**
 * Exposes the JavaScript [EventTarget](https://developer.mozilla.org/en/docs/Web/API/EventTarget) to Kotlin
 */
public abstract external class EventTarget : JsAny {
    fun addEventListener(type: String, callback: EventListener?, options: AddEventListenerOptions)
    fun addEventListener(type: String, callback: ((Event) -> Unit)?, options: AddEventListenerOptions)
    fun addEventListener(type: String, callback: EventListener?, options: Boolean)
    fun addEventListener(type: String, callback: ((Event) -> Unit)?, options: Boolean)
    fun addEventListener(type: String, callback: EventListener?)
    fun addEventListener(type: String, callback: ((Event) -> Unit)?)
    fun removeEventListener(type: String, callback: EventListener?, options: EventListenerOptions)
    fun removeEventListener(type: String, callback: ((Event) -> Unit)?, options: EventListenerOptions)
    fun removeEventListener(type: String, callback: EventListener?, options: Boolean)
    fun removeEventListener(type: String, callback: ((Event) -> Unit)?, options: Boolean)
    fun removeEventListener(type: String, callback: EventListener?)
    fun removeEventListener(type: String, callback: ((Event) -> Unit)?)
    fun dispatchEvent(event: Event): Boolean
}

/**
 * Exposes the JavaScript [EventListener](https://developer.mozilla.org/en/docs/Web/API/EventListener) to Kotlin
 */
public external interface EventListener : JsAny {
    fun handleEvent(event: Event)
}
