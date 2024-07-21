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

package dev.kilua.dom.core

@JsName("Array")
public actual external class JsArray<T : JsAny?> public actual constructor() : JsAny {
    public actual val length: Int
}

public actual operator fun <T : JsAny?> JsArray<T>.get(index: Int): T? =
    jsArrayGet(this, index)

public actual operator fun <T : JsAny?> JsArray<T>.set(index: Int, value: T) {
    jsArraySet(this, index, value)
}

@Suppress("UnsafeCastFromDynamic")
private fun <T : JsAny?> jsArrayGet(array: JsArray<T>, index: Int): T? =
    js("array[index]")

private fun <T : JsAny?> jsArraySet(array: JsArray<T>, index: Int, value: T) {
    js("array[index] = value")
}
