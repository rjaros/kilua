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

package web.fetch

import web.JsAny
import web.Promise

/**
 * Exposes the JavaScript [Headers](https://developer.mozilla.org/en/docs/Web/API/Headers) to Kotlin
 */
public open external class Headers(init: JsAny? /* Headers|JsArray<JsArray<JsString>>|OpenEndedDictionary<JsString> */) :
    JsAny {
    fun append(name: String, value: String)
    fun delete(name: String)
    fun get(name: String): String?
    fun has(name: String): Boolean
    fun set(name: String, value: String)
}

/**
 * Exposes the JavaScript [Body](https://developer.mozilla.org/en/docs/Web/API/Body) to Kotlin
 */
public external interface Body : JsAny {
    val bodyUsed: Boolean
    fun arrayBuffer(): Promise<*>
    fun blob(): Promise<*>
    fun formData(): Promise<*>
    fun json(): Promise<*>
    fun text(): Promise<*>
}

/**
 * Exposes the JavaScript [Request](https://developer.mozilla.org/en/docs/Web/API/Request) to Kotlin
 */
public open external class Request(input: JsAny? /* Request|String */, init: RequestInit) : Body,
    JsAny {
    open val method: String
    open val url: String
    open val headers: Headers
    open val type: RequestType
    open val destination: RequestDestination
    open val referrer: String
    open val referrerPolicy: JsAny?
    open val mode: RequestMode
    open val credentials: RequestCredentials
    open val cache: RequestCache
    open val redirect: RequestRedirect
    open val integrity: String
    open val keepalive: Boolean
    override val bodyUsed: Boolean
    fun clone(): Request
    override fun arrayBuffer(): Promise<*>
    override fun blob(): Promise<*>
    override fun formData(): Promise<*>
    override fun json(): Promise<*>
    override fun text(): Promise<*>
}

public external interface RequestInit : JsAny {
    var method: String?

    var headers: JsAny /* Headers|JsArray<JsArray<JsString>>|OpenEndedDictionary<JsString> */

    var body: JsAny? /* Blob|BufferSource|FormData|URLSearchParams|String */

    var referrer: String?

    var referrerPolicy: JsAny?

    var mode: RequestMode?

    var credentials: RequestCredentials?

    var cache: RequestCache?

    var redirect: RequestRedirect?

    var integrity: String?

    var keepalive: Boolean?

    var window: JsAny?

}

/**
 * Exposes the JavaScript [Response](https://developer.mozilla.org/en/docs/Web/API/Response) to Kotlin
 */
public open external class Response(
    body: JsAny? /* JsAny?|ReadableStream */,
    init: ResponseInit
) : Body, JsAny {
    open val type: ResponseType
    open val url: String
    open val redirected: Boolean
    open val status: Short
    open val ok: Boolean
    open val statusText: String
    open val headers: Headers
    open val body: JsAny?
    open val trailer: Promise<*>
    override val bodyUsed: Boolean
    fun clone(): Response
    override fun arrayBuffer(): Promise<*>
    override fun blob(): Promise<*>
    override fun formData(): Promise<*>
    override fun json(): Promise<*>
    override fun text(): Promise<*>

    companion object {
        fun error(): Response
        fun redirect(url: String, status: Short): Response
    }
}

public external interface ResponseInit : JsAny {
    var status: Short? /* = 200 */

    var statusText: String? /* = "OK" */

    var headers: JsAny? /* Headers|JsArray<JsArray<JsString>>|OpenEndedDictionary<JsString> */

}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface RequestType : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface RequestDestination : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface RequestMode : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface RequestCredentials : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface RequestCache : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface RequestRedirect : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ResponseType : JsAny {
    companion object
}
