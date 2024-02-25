@file:Suppress("EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE")
@file:JsModule("rsup-progress")
/*
 * Copyright (c) 2024 Robert Jaros
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

package dev.kilua.externals

import dev.kilua.utils.JsModule
import web.JsAny
import web.Promise

internal external class RsupProgressOptions : JsAny {
    var height: Int?
    var className: String?
    var color: String?
    var container: JsAny?
    var maxWidth: String?
    var position: String?
    var duration: Int?
    var hideDuration: Int?
    var zIndex: Int?
    var timing: String?
}

internal external class RsupProgressPromiseOptions : JsAny {
    var min: Int?
    var delay: Int?
    var waitAnimation: Boolean?
}

@JsName("Progress")
internal external class RsupProgress(options: RsupProgressOptions) {
    val isInProgress: Boolean
    fun setOptions(options: RsupProgressOptions)
    fun start()
    fun end()
    fun end(immediately: Boolean)
    fun <T : JsAny> promise(promise: Promise<T>): Promise<T>
    fun <T : JsAny> promise(promise: Promise<T>, options: RsupProgressPromiseOptions): Promise<T>
}
