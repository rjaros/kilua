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
import kotlinx.coroutines.Deferred
import web.JsAny
import web.Promise
import kotlinx.coroutines.asDeferred as asDeferredCrt
import kotlinx.coroutines.asPromise as asPromiseCrt
import kotlinx.coroutines.await as awaitCrt
import kotlin.js.Promise as PromiseJs

public actual fun <T> Deferred<T>.asPromise(): Promise<JsAny?> {
    return this.asPromiseCrt().cast()
}

public actual fun <T : JsAny?> Promise<T>.asDeferred(): Deferred<T> {
    return this.unsafeCast<PromiseJs<T>>().asDeferredCrt()
}

public actual suspend fun <T : JsAny?> Promise<T>.await(): T {
    return this.unsafeCast<PromiseJs<T>>().awaitCrt()
}
