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

package web.dom.mediacapture

import js.core.JsAny
import web.JsArray
import web.JsBoolean
import web.JsString
import web.dom.EventInit
import web.dom.MediaProvider
import web.events.Event
import web.events.EventTarget
import js.promise.Promise

/**
 * Exposes the JavaScript [MediaStream](https://developer.mozilla.org/en/docs/Web/API/MediaStream) to Kotlin
 */
public open external class MediaStream() : EventTarget, MediaProvider, JsAny {
    constructor(stream: MediaStream)
    constructor(tracks: JsArray<MediaStreamTrack>)

    open val id: String
    open val active: Boolean
    var onaddtrack: ((MediaStreamTrackEvent) -> Unit)?
    var onremovetrack: ((MediaStreamTrackEvent) -> Unit)?
    fun getAudioTracks(): JsArray<MediaStreamTrack>
    fun getVideoTracks(): JsArray<MediaStreamTrack>
    fun getTracks(): JsArray<MediaStreamTrack>
    fun getTrackById(trackId: String): MediaStreamTrack?
    fun addTrack(track: MediaStreamTrack)
    fun removeTrack(track: MediaStreamTrack)
    fun clone(): MediaStream
}

/**
 * Exposes the JavaScript [MediaStreamTrack](https://developer.mozilla.org/en/docs/Web/API/MediaStreamTrack) to Kotlin
 */
public abstract external class MediaStreamTrack : EventTarget, JsAny {
    open val kind: String
    open val id: String
    open val label: String
    open var enabled: Boolean
    open val muted: Boolean
    open var onmute: ((Event) -> Unit)?
    open var onunmute: ((Event) -> Unit)?
    open val readyState: MediaStreamTrackState
    open var onended: ((Event) -> Unit)?
    open var onoverconstrained: ((Event) -> Unit)?
    fun clone(): MediaStreamTrack
    fun stop()
    fun getCapabilities(): MediaTrackCapabilities
    fun getConstraints(): MediaTrackConstraints
    fun getSettings(): MediaTrackSettings
    fun applyConstraints(constraints: MediaTrackConstraints): Promise<Nothing?>
}

/**
 * Exposes the JavaScript [MediaTrackSupportedConstraints](https://developer.mozilla.org/en/docs/Web/API/MediaTrackSupportedConstraints) to Kotlin
 */
public external interface MediaTrackSupportedConstraints : JsAny {
    var width: Boolean? /* = true */

    var height: Boolean? /* = true */

    var aspectRatio: Boolean? /* = true */

    var frameRate: Boolean? /* = true */

    var facingMode: Boolean? /* = true */

    var resizeMode: Boolean? /* = true */

    var volume: Boolean? /* = true */

    var sampleRate: Boolean? /* = true */

    var sampleSize: Boolean? /* = true */

    var echoCancellation: Boolean? /* = true */

    var autoGainControl: Boolean? /* = true */

    var noiseSuppression: Boolean? /* = true */

    var latency: Boolean? /* = true */

    var channelCount: Boolean? /* = true */

    var deviceId: Boolean? /* = true */

    var groupId: Boolean? /* = true */

}

public external interface MediaTrackCapabilities : JsAny {
    var width: ULongRange?

    var height: ULongRange?

    var aspectRatio: DoubleRange?

    var frameRate: DoubleRange?

    var facingMode: JsArray<JsString>?

    var resizeMode: JsArray<JsString>?

    var volume: DoubleRange?

    var sampleRate: ULongRange?

    var sampleSize: ULongRange?

    var echoCancellation: JsArray<JsBoolean>?

    var autoGainControl: JsArray<JsBoolean>?

    var noiseSuppression: JsArray<JsBoolean>?

    var latency: DoubleRange?

    var channelCount: ULongRange?

    var deviceId: String?

    var groupId: String?

}

/**
 * Exposes the JavaScript [MediaTrackConstraints](https://developer.mozilla.org/en/docs/Web/API/MediaTrackConstraints) to Kotlin
 */
public external interface MediaTrackConstraints : MediaTrackConstraintSet, JsAny {
    var advanced: JsArray<MediaTrackConstraintSet>?

}

public external interface MediaTrackConstraintSet : JsAny {
    var width: JsAny? /* Int|ConstrainULongRange */

    var height: JsAny? /* Int|ConstrainULongRange */

    var aspectRatio: JsAny? /* Double|ConstrainDoubleRange */

    var frameRate: JsAny? /* Double|ConstrainDoubleRange */

    var facingMode: JsAny? /* String|JsArray<JsString>|ConstrainDOMStringParameters */

    var resizeMode: JsAny? /* String|JsArray<JsString>|ConstrainDOMStringParameters */

    var volume: JsAny? /* Double|ConstrainDoubleRange */

    var sampleRate: JsAny? /* Int|ConstrainULongRange */

    var sampleSize: JsAny? /* Int|ConstrainULongRange */

    var echoCancellation: JsAny? /* Boolean|ConstrainBooleanParameters */

    var autoGainControl: JsAny? /* Boolean|ConstrainBooleanParameters */

    var noiseSuppression: JsAny? /* Boolean|ConstrainBooleanParameters */

    var latency: JsAny? /* Double|ConstrainDoubleRange */

    var channelCount: JsAny? /* Int|ConstrainULongRange */

    var deviceId: JsAny? /* String|JsArray<JsString>|ConstrainDOMStringParameters */

    var groupId: JsAny? /* String|JsArray<JsString>|ConstrainDOMStringParameters */

}

/**
 * Exposes the JavaScript [MediaTrackSettings](https://developer.mozilla.org/en/docs/Web/API/MediaTrackSettings) to Kotlin
 */
public external interface MediaTrackSettings : JsAny {
    var width: Int?

    var height: Int?

    var aspectRatio: Double?

    var frameRate: Double?

    var facingMode: String?

    var resizeMode: String?

    var volume: Double?

    var sampleRate: Int?

    var sampleSize: Int?

    var echoCancellation: Boolean?

    var autoGainControl: Boolean?

    var noiseSuppression: Boolean?

    var latency: Double?

    var channelCount: Int?

    var deviceId: String?

    var groupId: String?

}

/**
 * Exposes the JavaScript [MediaStreamTrackEvent](https://developer.mozilla.org/en/docs/Web/API/MediaStreamTrackEvent) to Kotlin
 */
public open external class MediaStreamTrackEvent(type: String, eventInitDict: MediaStreamTrackEventInit) : Event,
    JsAny {
    open val track: MediaStreamTrack

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface MediaStreamTrackEventInit : EventInit, JsAny {
    var track: MediaStreamTrack?
}

public open external class OverconstrainedErrorEvent(type: String, eventInitDict: OverconstrainedErrorEventInit) :
    Event, JsAny {
    open val error: JsAny?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface OverconstrainedErrorEventInit : EventInit, JsAny {
    var error: JsAny? /* = null */

}

/**
 * Exposes the JavaScript [MediaDevices](https://developer.mozilla.org/en/docs/Web/API/MediaDevices) to Kotlin
 */
public abstract external class MediaDevices : EventTarget, JsAny {
    open var ondevicechange: ((Event) -> Unit)?
    fun enumerateDevices(): Promise<*>
    fun getSupportedConstraints(): MediaTrackSupportedConstraints
    fun getUserMedia(constraints: MediaStreamConstraints): Promise<*>
}

/**
 * Exposes the JavaScript [MediaDeviceInfo](https://developer.mozilla.org/en/docs/Web/API/MediaDeviceInfo) to Kotlin
 */
public abstract external class MediaDeviceInfo : JsAny {
    open val deviceId: String
    open val kind: MediaDeviceKind
    open val label: String
    open val groupId: String
    fun toJSON(): JsAny
}

public abstract external class InputDeviceInfo : MediaDeviceInfo, JsAny {
    fun getCapabilities(): MediaTrackCapabilities
}

/**
 * Exposes the JavaScript [MediaStreamConstraints](https://developer.mozilla.org/en/docs/Web/API/MediaStreamConstraints) to Kotlin
 */
public external interface MediaStreamConstraints : JsAny {
    var video: JsAny? /* Boolean|MediaTrackConstraints */

    var audio: JsAny? /* Boolean|MediaTrackConstraints */

}

public external interface ConstrainablePattern : JsAny {
    var onoverconstrained: ((Event) -> Unit)?


    fun getCapabilities(): Capabilities
    fun getConstraints(): Constraints
    fun getSettings(): Settings
    fun applyConstraints(constraints: Constraints): Promise<Nothing?>
}

/**
 * Exposes the JavaScript [DoubleRange](https://developer.mozilla.org/en/docs/Web/API/DoubleRange) to Kotlin
 */
public external interface DoubleRange : JsAny {
    var max: Double?

    var min: Double?

}

public external interface ConstrainDoubleRange : DoubleRange, JsAny {
    var exact: Double?

    var ideal: Double?

}

public external interface ULongRange : JsAny {
    var max: Int?

    var min: Int?

}

public external interface ConstrainULongRange : ULongRange, JsAny {
    var exact: Int?

    var ideal: Int?

}

/**
 * Exposes the JavaScript [ConstrainBooleanParameters](https://developer.mozilla.org/en/docs/Web/API/ConstrainBooleanParameters) to Kotlin
 */
public external interface ConstrainBooleanParameters : JsAny {
    var exact: Boolean?

    var ideal: Boolean?

}

/**
 * Exposes the JavaScript [ConstrainDOMStringParameters](https://developer.mozilla.org/en/docs/Web/API/ConstrainDOMStringParameters) to Kotlin
 */
public external interface ConstrainDOMStringParameters : JsAny {
    var exact: JsAny? /* String|JsArray<JsString> */

    var ideal: JsAny? /* String|JsArray<JsString> */

}

public external interface Capabilities : JsAny

public external interface Settings : JsAny

public external interface ConstraintSet : JsAny

public external interface Constraints : ConstraintSet, JsAny {
    var advanced: JsArray<ConstraintSet>?

}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface MediaStreamTrackState : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface VideoFacingModeEnum : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface VideoResizeModeEnum : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface MediaDeviceKind : JsAny {
    companion object
}
