/*
 * Copyright (c) 2025 Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.kilua.utils

import js.core.JsAny
import js.errors.JsError
import js.errors.JsErrorLike
import js.promise.Promise
import js.promise.PromiseReject
import js.promise.PromiseResolve
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.asDeferred
import kotlinx.coroutines.asPromise
import kotlin.js.Promise as PromiseJs

public actual fun <T> Deferred<T>.asPromise(): Promise<JsAny?> = asPromise().unsafeCast()

public actual fun <T : JsAny?> Promise<T>.asDeferred(): Deferred<T> = unsafeCast<PromiseJs<T>>().asDeferred()

public actual suspend fun <T : JsAny?> Promise<T>.awaitPromise(): T = await()

@Suppress("NOTHING_TO_INLINE")
public actual inline fun <T : JsAny?> PromiseResolve<T>.call(value: T) {
    this.asDynamic()(value)
}

@Suppress("NOTHING_TO_INLINE")
public actual inline fun PromiseReject.call() {
    this.asDynamic()()
}

@Suppress("NOTHING_TO_INLINE")
public actual inline fun PromiseReject.call(reason: JsError) {
    this.asDynamic()(reason)
}

@Suppress("NOTHING_TO_INLINE")
public actual inline fun PromiseReject.call(reason: JsErrorLike) {
    this.asDynamic()(reason)
}
