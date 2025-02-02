/*
 * Copyright (c) 2023 Robert Jaros
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

package dev.kilua

import dev.kilua.utils.cast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import web.JsAny
import web.Promise

/**
 * Kilua coroutine scope.
 */
public val KiluaScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

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

/**
 * Suspend function to await a JS Promise.
 */
public expect suspend fun <T: JsAny?> Promise<T>.await(): T
