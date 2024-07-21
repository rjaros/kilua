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

package dev.kilua.dom.workers

import dev.kilua.dom.JsAny
import dev.kilua.dom.JsArray
import dev.kilua.dom.JsString
import dev.kilua.dom.api.AbstractWorker
import dev.kilua.dom.api.EventInit
import dev.kilua.dom.api.MessageEvent
import dev.kilua.dom.api.MessagePort
import dev.kilua.dom.api.WorkerGlobalScope
import dev.kilua.dom.api.WorkerType
import dev.kilua.dom.api.events.Event
import dev.kilua.dom.api.events.EventTarget
import dev.kilua.dom.fetch.Request
import dev.kilua.dom.fetch.Response
import dev.kilua.dom.notifications.GetNotificationOptions
import dev.kilua.dom.notifications.NotificationEvent
import dev.kilua.dom.notifications.NotificationOptions
import dev.kilua.dom.core.Promise

/**
 * Exposes the JavaScript [ServiceWorker](https://developer.mozilla.org/en/docs/Web/API/ServiceWorker) to Kotlin
 */
public abstract external class ServiceWorker : EventTarget, AbstractWorker, UnionMessagePortOrServiceWorker,
    UnionClientOrMessagePortOrServiceWorker, JsAny {
    open val scriptURL: String
    open val state: ServiceWorkerState
    open var onstatechange: ((Event) -> Unit)?
    fun postMessage(message: JsAny?, transfer: JsArray<JsAny>)
}

/**
 * Exposes the JavaScript [ServiceWorkerRegistration](https://developer.mozilla.org/en/docs/Web/API/ServiceWorkerRegistration) to Kotlin
 */
public abstract external class ServiceWorkerRegistration : EventTarget, JsAny {
    open val installing: ServiceWorker?
    open val waiting: ServiceWorker?
    open val active: ServiceWorker?
    open val scope: String
    open var onupdatefound: ((Event) -> Unit)?
    open val APISpace: JsAny?
    fun update(): Promise<Nothing?>
    fun unregister(): Promise<*>
    fun showNotification(title: String, options: NotificationOptions): Promise<Nothing?>
    fun getNotifications(filter: GetNotificationOptions): Promise<*>
    fun methodName(): Promise<*>
}

/**
 * Exposes the JavaScript [ServiceWorkerContainer](https://developer.mozilla.org/en/docs/Web/API/ServiceWorkerContainer) to Kotlin
 */
public abstract external class ServiceWorkerContainer : EventTarget, JsAny {
    open val controller: ServiceWorker?
    open val ready: Promise<*>
    open var oncontrollerchange: ((Event) -> Unit)?
    open var onmessage: ((MessageEvent) -> Unit)?
    fun register(
        scriptURL: String,
        options: RegistrationOptions
    ): Promise<*>

    fun getRegistration(clientURL: String): Promise<*>
    fun getRegistrations(): Promise<*>
    fun startMessages()
}

public external interface RegistrationOptions : JsAny {
    var scope: String?

    var type: WorkerType? /* = WorkerType.CLASSIC */

}

/**
 * Exposes the JavaScript [ServiceWorkerMessageEvent](https://developer.mozilla.org/en/docs/Web/API/ServiceWorkerMessageEvent) to Kotlin
 */
public open external class ServiceWorkerMessageEvent(
    type: String,
    eventInitDict: ServiceWorkerMessageEventInit
) : Event, JsAny {
    open val data: JsAny?
    open val origin: String
    open val lastEventId: String
    open val source: UnionMessagePortOrServiceWorker?
    open val ports: JsArray<out MessagePort>?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface ServiceWorkerMessageEventInit : EventInit, JsAny {
    var data: JsAny?

    var origin: String?

    var lastEventId: String?

    var source: UnionMessagePortOrServiceWorker?

    var ports: JsArray<MessagePort>?

}

/**
 * Exposes the JavaScript [ServiceWorkerGlobalScope](https://developer.mozilla.org/en/docs/Web/API/ServiceWorkerGlobalScope) to Kotlin
 */
public abstract external class ServiceWorkerGlobalScope : WorkerGlobalScope, JsAny {
    open val clients: Clients
    open val registration: ServiceWorkerRegistration
    open var oninstall: ((Event) -> Unit)?
    open var onactivate: ((Event) -> Unit)?
    open var onfetch: ((FetchEvent) -> Unit)?
    open var onforeignfetch: ((Event) -> Unit)?
    open var onmessage: ((MessageEvent) -> Unit)?
    open var onnotificationclick: ((NotificationEvent) -> Unit)?
    open var onnotificationclose: ((NotificationEvent) -> Unit)?
    open var onfunctionalevent: ((Event) -> Unit)?
    fun skipWaiting(): Promise<Nothing?>
}

/**
 * Exposes the JavaScript [Client](https://developer.mozilla.org/en/docs/Web/API/Client) to Kotlin
 */
public abstract external class Client : UnionClientOrMessagePortOrServiceWorker, JsAny {
    open val url: String
    open val frameType: FrameType
    open val id: String
    fun postMessage(message: JsAny?, transfer: JsArray<JsAny>)
}

/**
 * Exposes the JavaScript [WindowClient](https://developer.mozilla.org/en/docs/Web/API/WindowClient) to Kotlin
 */
public abstract external class WindowClient : Client, JsAny {
    open val visibilityState: JsAny?
    open val focused: Boolean
    fun focus(): Promise<*>
    fun navigate(url: String): Promise<*>
}

/**
 * Exposes the JavaScript [Clients](https://developer.mozilla.org/en/docs/Web/API/Clients) to Kotlin
 */
public abstract external class Clients : JsAny {
    fun get(id: String): Promise<*>
    fun matchAll(options: ClientQueryOptions): Promise<*>
    fun openWindow(url: String): Promise<*>
    fun claim(): Promise<Nothing?>
}

public external interface ClientQueryOptions : JsAny {
    var includeUncontrolled: Boolean? /* = false */

    var type: ClientType? /* = ClientType.WINDOW */

}

/**
 * Exposes the JavaScript [ExtendableEvent](https://developer.mozilla.org/en/docs/Web/API/ExtendableEvent) to Kotlin
 */
public open external class ExtendableEvent(type: String, eventInitDict: ExtendableEventInit) :
    Event, JsAny {
    fun waitUntil(f: Promise<*>)

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface ExtendableEventInit : EventInit, JsAny

/**
 * Exposes the JavaScript [InstallEvent](https://developer.mozilla.org/en/docs/Web/API/InstallEvent) to Kotlin
 */
public open external class InstallEvent(type: String, eventInitDict: ExtendableEventInit) :
    ExtendableEvent, JsAny {
    fun registerForeignFetch(options: ForeignFetchOptions)

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface ForeignFetchOptions : JsAny {
    var scopes: JsArray<JsString>?
    var origins: JsArray<JsString>?
}

/**
 * Exposes the JavaScript [FetchEvent](https://developer.mozilla.org/en/docs/Web/API/FetchEvent) to Kotlin
 */
public open external class FetchEvent(type: String, eventInitDict: FetchEventInit) : ExtendableEvent, JsAny {
    open val request: Request
    open val clientId: String?
    open val isReload: Boolean
    fun respondWith(r: Promise<*>)

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface FetchEventInit : ExtendableEventInit, JsAny {
    var request: Request?
    var clientId: String? /* = null */

    var isReload: Boolean? /* = false */

}

public open external class ForeignFetchEvent(type: String, eventInitDict: ForeignFetchEventInit) : ExtendableEvent,
    JsAny {
    open val request: Request
    open val origin: String
    fun respondWith(r: Promise<*>)

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface ForeignFetchEventInit : ExtendableEventInit, JsAny {
    var request: Request?
    var origin: String? /* = "null" */

}

public external interface ForeignFetchResponse : JsAny {
    var response: Response?
    var origin: String?

    var headers: JsArray<JsString>?

}

/**
 * Exposes the JavaScript [ExtendableMessageEvent](https://developer.mozilla.org/en/docs/Web/API/ExtendableMessageEvent) to Kotlin
 */
public open external class ExtendableMessageEvent(
    type: String,
    eventInitDict: ExtendableMessageEventInit
) : ExtendableEvent, JsAny {
    open val data: JsAny?
    open val origin: String
    open val lastEventId: String
    open val source: UnionClientOrMessagePortOrServiceWorker?
    open val ports: JsArray<out MessagePort>?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface ExtendableMessageEventInit : ExtendableEventInit, JsAny {
    var data: JsAny?

    var origin: String?

    var lastEventId: String?

    var source: UnionClientOrMessagePortOrServiceWorker?

    var ports: JsArray<MessagePort>?

}

/**
 * Exposes the JavaScript [Cache](https://developer.mozilla.org/en/docs/Web/API/Cache) to Kotlin
 */
public abstract external class Cache : JsAny {
    fun match(request: Request, options: CacheQueryOptions): Promise<*>
    fun match(request: String, options: CacheQueryOptions): Promise<*>
    fun matchAll(request: Request, options: CacheQueryOptions): Promise<*>
    fun matchAll(request: String, options: CacheQueryOptions): Promise<*>
    fun matchAll(): Promise<*>
    fun add(request: Request): Promise<Nothing?>
    fun add(request: String): Promise<Nothing?>
    fun addAll(requests: JsArray<JsAny? /* Request|String */>): Promise<Nothing?>
    fun put(request: Request, response: Response): Promise<Nothing?>
    fun put(request: String, response: Response): Promise<Nothing?>
    fun delete(request: Request, options: CacheQueryOptions): Promise<*>
    fun delete(request: String, options: CacheQueryOptions): Promise<*>
    fun keys(request: Request, options: CacheQueryOptions): Promise<*>
    fun keys(request: String, options: CacheQueryOptions): Promise<*>
    fun keys(): Promise<*>
}

public external interface CacheQueryOptions : JsAny {
    var ignoreSearch: Boolean? /* = false */

    var ignoreMethod: Boolean? /* = false */

    var ignoreVary: Boolean? /* = false */

    var cacheName: String?

}

public external interface CacheBatchOperation : JsAny {
    var type: String?

    var request: Request?

    var response: Response?

    var options: CacheQueryOptions?

}

/**
 * Exposes the JavaScript [CacheStorage](https://developer.mozilla.org/en/docs/Web/API/CacheStorage) to Kotlin
 */
public abstract external class CacheStorage : JsAny {
    fun match(request: Request, options: CacheQueryOptions): Promise<*>
    fun match(request: String, options: CacheQueryOptions): Promise<*>
    fun has(cacheName: String): Promise<*>
    fun open(cacheName: String): Promise<*>
    fun delete(cacheName: String): Promise<*>
    fun keys(): Promise<*>
}

public open external class FunctionalEvent : ExtendableEvent, JsAny {
    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface UnionMessagePortOrServiceWorker

public external interface UnionClientOrMessagePortOrServiceWorker

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ServiceWorkerState : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface FrameType : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ClientType : JsAny {
    companion object
}
