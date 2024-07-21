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

package dev.kilua.dom.xhr

import dev.kilua.dom.api.Document
import dev.kilua.dom.api.EventInit
import dev.kilua.dom.api.HTMLFormElement
import dev.kilua.dom.api.events.Event
import dev.kilua.dom.api.events.EventTarget
import dev.kilua.dom.api.url.URLSearchParams
import dev.kilua.dom.core.JsAny
import dev.kilua.dom.core.JsArray
import dev.kilua.dom.core.JsNumber
import dev.kilua.dom.files.Blob

/**
 * Exposes the JavaScript [XMLHttpRequestEventTarget](https://developer.mozilla.org/en/docs/Web/API/XMLHttpRequestEventTarget) to Kotlin
 */
public abstract external class XMLHttpRequestEventTarget : EventTarget, JsAny {
    open var onloadstart: ((ProgressEvent) -> Unit)?
    open var onprogress: ((ProgressEvent) -> Unit)?
    open var onabort: ((Event) -> Unit)?
    open var onerror: ((Event) -> Unit)?
    open var onload: ((Event) -> Unit)?
    open var ontimeout: ((Event) -> Unit)?
    open var onloadend: ((Event) -> Unit)?
}

public abstract external class XMLHttpRequestUpload : XMLHttpRequestEventTarget, JsAny

/**
 * Exposes the JavaScript [XMLHttpRequest](https://developer.mozilla.org/en/docs/Web/API/XMLHttpRequest) to Kotlin
 */
public open external class XMLHttpRequest : XMLHttpRequestEventTarget, JsAny {
    var onreadystatechange: ((Event) -> Unit)?
    open val readyState: Short
    var timeout: Int
    var withCredentials: Boolean
    open val upload: XMLHttpRequestUpload
    open val responseURL: String
    open val status: Short
    open val statusText: String
    var responseType: XMLHttpRequestResponseType
    open val response: JsAny?
    open val responseText: String
    open val responseXML: Document?
    fun open(method: String, url: String)
    fun open(
        method: String,
        url: String,
        async: Boolean,
        username: String?,
        password: String?
    )

    fun setRequestHeader(name: String, value: String)
    fun send(body: Document)
    fun send(body: Blob)
    fun send(body: FormData)
    fun send(body: URLSearchParams)
    fun send(body: String)
    fun send()
    fun abort()
    fun getResponseHeader(name: String): String?
    fun getAllResponseHeaders(): String
    fun overrideMimeType(mime: String)

    companion object {
        val UNSENT: Short
        val OPENED: Short
        val HEADERS_RECEIVED: Short
        val LOADING: Short
        val DONE: Short
    }
}

/**
 * Exposes the JavaScript [FormData](https://developer.mozilla.org/en/docs/Web/API/FormData) to Kotlin
 */
public open external class FormData(form: HTMLFormElement) : JsAny {
    fun append(name: String, value: String)
    fun append(name: String, value: Blob, filename: String)
    fun delete(name: String)
    fun get(name: String): JsAny? /* File|String */
    fun getAll(name: String): JsArray<JsAny? /* File|String */>
    fun has(name: String): Boolean
    fun set(name: String, value: String)
    fun set(name: String, value: Blob, filename: String)
}

/**
 * Exposes the JavaScript [ProgressEvent](https://developer.mozilla.org/en/docs/Web/API/ProgressEvent) to Kotlin
 */
public open external class ProgressEvent(type: String, eventInitDict: ProgressEventInit) : Event,
    JsAny {
    open val lengthComputable: Boolean
    open val loaded: JsNumber
    open val total: JsNumber

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface ProgressEventInit : EventInit, JsAny {
    var lengthComputable: Boolean? /* = false */

    var loaded: JsNumber? /* = 0 */

    var total: JsNumber? /* = 0 */

}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface XMLHttpRequestResponseType : JsAny {
    companion object
}
