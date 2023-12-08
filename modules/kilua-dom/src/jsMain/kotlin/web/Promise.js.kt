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

package web

public actual external class Promise<T : JsAny?> actual constructor(executor: (resolve: (T) -> Unit, reject: (JsAny) -> Unit) -> Unit) :
    JsAny {
    public actual fun <S : JsAny?> then(onFulfilled: ((T) -> S)?): Promise<S>

    public actual fun <S : JsAny?> then(onFulfilled: ((T) -> S)?, onRejected: ((JsAny) -> S)?): Promise<S>

    public actual fun <S : JsAny?> catch(onRejected: (JsAny) -> S): Promise<S>

    public actual fun finally(onFinally: () -> Unit): Promise<T>

    public actual companion object {
        public actual fun <S : JsAny?> all(promise: JsArray<out Promise<S>>): Promise<JsArray<out S>>

        public actual fun <S : JsAny?> race(promise: JsArray<out Promise<S>>): Promise<S>

        public actual fun reject(e: JsAny): Promise<Nothing>

        public actual fun <S : JsAny?> resolve(e: S): Promise<S>

        public actual fun <S : JsAny?> resolve(e: Promise<S>): Promise<S>
    }
}
