package dev.kilua

import dev.kilua.dom.JsAny
import dev.kilua.dom.Promise
import dev.kilua.utils.cast
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

/**
 * Convert Deferred to a JS Promise.
 */
public expect fun <T> Deferred<T>.asPromise(): Promise<JsAny?>

/**
 * Convert JS Promise to a Deferred.
 */
public expect fun <T : JsAny?> Promise<T>.asDeferred(): Deferred<T>

/**
 * Create a JS Promise from a suspending block.
 */
public fun <T : JsAny?> promise(block: suspend () -> T): Promise<T> = KiluaScope.async { block() }.asPromise().cast()
