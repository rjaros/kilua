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

package dev.kilua.dom.notifications

import dev.kilua.dom.JsAny
import dev.kilua.dom.JsArray
import dev.kilua.dom.JsNumber
import dev.kilua.dom.api.events.Event
import dev.kilua.dom.api.events.EventTarget
import dev.kilua.dom.api.events.MouseEvent
import dev.kilua.dom.workers.ExtendableEvent
import dev.kilua.dom.workers.ExtendableEventInit
import dev.kilua.dom.core.Promise

/**
 * Exposes the JavaScript [Notification](https://developer.mozilla.org/en/docs/Web/API/Notification) to Kotlin
 */
public open external class Notification(title: String, options: NotificationOptions) : EventTarget,
    JsAny {
    var onclick: ((MouseEvent) -> Unit)?
    var onerror: ((Event) -> Unit)?
    open val title: String
    open val dir: NotificationDirection
    open val lang: String
    open val body: String
    open val tag: String
    open val image: String
    open val icon: String
    open val badge: String
    open val sound: String
    open val vibrate: JsArray<out JsNumber>
    open val timestamp: JsNumber
    open val renotify: Boolean
    open val silent: Boolean
    open val noscreen: Boolean
    open val requireInteraction: Boolean
    open val sticky: Boolean
    open val data: JsAny?
    open val actions: JsArray<out NotificationAction>
    fun close()

    companion object {
        val permission: NotificationPermission
        val maxActions: Int
        fun requestPermission(deprecatedCallback: (NotificationPermission) -> Unit): Promise<*>
    }
}

public external interface NotificationOptions : JsAny {
    var dir: NotificationDirection? /* = NotificationDirection.AUTO */

    var lang: String? /* = "" */

    var body: String? /* = "" */

    var tag: String? /* = "" */

    var image: String?

    var icon: String?

    var badge: String?

    var sound: String?

    var vibrate: JsAny? /* Int|JsArray<JsNumber> */

    var timestamp: JsNumber?

    var renotify: Boolean? /* = false */

    var silent: Boolean? /* = false */

    var noscreen: Boolean? /* = false */

    var requireInteraction: Boolean? /* = false */

    var sticky: Boolean? /* = false */

    var data: JsAny? /* = null */

    var actions: JsArray<NotificationAction>? /* = arrayOf() */

}

public external interface NotificationAction : JsAny {
    var action: String?
    var title: String?
    var icon: String?

}

public external interface GetNotificationOptions : JsAny {
    var tag: String? /* = "" */

}

/**
 * Exposes the JavaScript [NotificationEvent](https://developer.mozilla.org/en/docs/Web/API/NotificationEvent) to Kotlin
 */
public open external class NotificationEvent(type: String, eventInitDict: NotificationEventInit) : ExtendableEvent,
    JsAny {
    open val notification: Notification
    open val action: String

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface NotificationEventInit : ExtendableEventInit, JsAny {
    var notification: Notification?
    var action: String? /* = "" */

}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface NotificationPermission : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface NotificationDirection : JsAny {
    companion object
}
