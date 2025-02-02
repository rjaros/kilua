@file:JsModule("motion")
@file:Suppress("EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE")
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

package dev.kilua.externals

import dev.kilua.JsModule
import web.JsAny
import web.JsNumber
import web.Promise
import web.dom.HTMLElement

/**
 * The animation playback controls.
 * Allows to control the Motion animation run and access its properties.
 */
public external interface AnimationPlaybackControls : JsAny {
    public val duration: JsNumber
    public var time: JsNumber
    public var speed: JsNumber

    public fun then(callback: () -> Unit): Promise<JsAny>
    public fun pause()
    public fun play()
    public fun complete()
    public fun cancel()
    public fun stop()
}

public external fun animate(selector: String, keyframes: JsAny): AnimationPlaybackControls

public external fun animate(selector: String, keyframes: JsAny, options: JsAny): AnimationPlaybackControls

public external fun animate(element: HTMLElement, keyframes: JsAny): AnimationPlaybackControls

public external fun animate(element: HTMLElement, keyframes: JsAny, options: JsAny): AnimationPlaybackControls

public external fun animate(obj: JsAny, keyframes: JsAny): AnimationPlaybackControls

public external fun animate(obj: JsAny, keyframes: JsAny, options: JsAny): AnimationPlaybackControls

public external class IntCallback : JsAny {
    public var onUpdate: ((value: Int) -> Unit)
}

public external class StringCallback : JsAny {
    public var onUpdate: ((value: String) -> Unit)
}

public external fun animate(from: Int, to: Int, options: JsAny): AnimationPlaybackControls

public external fun animate(from: String, to: String, options: JsAny): AnimationPlaybackControls

public external fun scroll(animation: AnimationPlaybackControls)

public external fun scroll(callback: (JsNumber) -> Unit): () -> Unit

public external fun scroll(callback: (JsNumber, JsAny) -> Unit): () -> Unit

public external fun animateMini(selector: String, keyframes: JsAny): AnimationPlaybackControls

public external fun animateMini(selector: String, keyframes: JsAny, options: JsAny): AnimationPlaybackControls

public external fun animateMini(element: HTMLElement, keyframes: JsAny): AnimationPlaybackControls

public external fun animateMini(element: HTMLElement, keyframes: JsAny, options: JsAny): AnimationPlaybackControls
