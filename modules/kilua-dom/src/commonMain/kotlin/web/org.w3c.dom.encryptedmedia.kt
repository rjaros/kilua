/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

// NOTE: THIS FILE IS AUTO-GENERATED, DO NOT EDIT!
// See github.com/kotlin/dukat for details

@file:Suppress(
    "NO_EXPLICIT_VISIBILITY_IN_API_MODE",
    "NO_EXPLICIT_RETURN_TYPE_IN_API_MODE"
) // TODO: Fix in dukat: https://github.com/Kotlin/dukat/issues/124

package web.dom.encryptedmedia

import web.JsAny
import web.JsArray
import web.JsString
import web.Promise
import web.dom.EventInit
import web.dom.MessageEvent
import web.dom.events.Event
import web.dom.events.EventTarget
import web.webgl.ArrayBuffer

/**
 * Exposes the JavaScript [MediaKeySystemConfiguration](https://developer.mozilla.org/en/docs/Web/API/MediaKeySystemConfiguration) to Kotlin
 */
public external interface MediaKeySystemConfiguration : JsAny {
    var label: String? /* = "" */

    var initDataTypes: JsArray<JsString>? /* = arrayOf() */

    var audioCapabilities: JsArray<MediaKeySystemMediaCapability>? /* = arrayOf() */

    var videoCapabilities: JsArray<MediaKeySystemMediaCapability>? /* = arrayOf() */

    var distinctiveIdentifier: MediaKeysRequirement? /* = MediaKeysRequirement.OPTIONAL */

    var persistentState: MediaKeysRequirement? /* = MediaKeysRequirement.OPTIONAL */

    var sessionTypes: JsArray<JsString>?

}

public external interface MediaKeySystemMediaCapability : JsAny {
    var contentType: String? /* = "" */

    var robustness: String? /* = "" */

}

/**
 * Exposes the JavaScript [MediaKeySystemAccess](https://developer.mozilla.org/en/docs/Web/API/MediaKeySystemAccess) to Kotlin
 */
public external abstract class MediaKeySystemAccess : JsAny {
    open val keySystem: String
    fun getConfiguration(): MediaKeySystemConfiguration
    fun createMediaKeys(): Promise<*>
}

/**
 * Exposes the JavaScript [MediaKeys](https://developer.mozilla.org/en/docs/Web/API/MediaKeys) to Kotlin
 */
public external abstract class MediaKeys : JsAny {
    fun createSession(sessionType: MediaKeySessionType): MediaKeySession
    fun setServerCertificate(serverCertificate: JsAny?): Promise<*>
}

/**
 * Exposes the JavaScript [MediaKeySession](https://developer.mozilla.org/en/docs/Web/API/MediaKeySession) to Kotlin
 */
public external abstract class MediaKeySession : EventTarget, JsAny {
    open val sessionId: String
    open val expiration: Double
    open val closed: Promise<Nothing?>
    open val keyStatuses: MediaKeyStatusMap
    open var onkeystatuseschange: ((Event) -> Unit)?
    open var onmessage: ((MessageEvent) -> Unit)?
    fun generateRequest(initDataType: String, initData: JsAny?): Promise<Nothing?>
    fun load(sessionId: String): Promise<*>
    fun update(response: JsAny?): Promise<Nothing?>
    fun close(): Promise<Nothing?>
    fun remove(): Promise<Nothing?>
}

/**
 * Exposes the JavaScript [MediaKeyStatusMap](https://developer.mozilla.org/en/docs/Web/API/MediaKeyStatusMap) to Kotlin
 */
public external abstract class MediaKeyStatusMap : JsAny {
    open val size: Int
    fun has(keyId: JsAny?): Boolean
    fun get(keyId: JsAny?): JsAny?
}

/**
 * Exposes the JavaScript [MediaKeyMessageEvent](https://developer.mozilla.org/en/docs/Web/API/MediaKeyMessageEvent) to Kotlin
 */
public external open class MediaKeyMessageEvent(type: String, eventInitDict: MediaKeyMessageEventInit) : Event, JsAny {
    open val messageType: MediaKeyMessageType
    open val message: ArrayBuffer

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface MediaKeyMessageEventInit : EventInit, JsAny {
    var messageType: MediaKeyMessageType?
    var message: ArrayBuffer?
}

public external open class MediaEncryptedEvent(
    type: String,
    eventInitDict: MediaEncryptedEventInit
) : Event, JsAny {
    open val initDataType: String
    open val initData: ArrayBuffer?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface MediaEncryptedEventInit : EventInit, JsAny {
    var initDataType: String? /* = "" */

    var initData: ArrayBuffer? /* = null */

}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface MediaKeysRequirement : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface MediaKeySessionType : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface MediaKeyStatus : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface MediaKeyMessageType : JsAny {
    companion object
}
