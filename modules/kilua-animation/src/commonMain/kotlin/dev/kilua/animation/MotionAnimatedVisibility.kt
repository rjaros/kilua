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

package dev.kilua.animation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.core.IComponent
import dev.kilua.externals.AnimationPlaybackControls
import dev.kilua.externals.animate
import dev.kilua.html.Display
import dev.kilua.html.Position
import dev.kilua.html.divRef
import dev.kilua.utils.awaitPromise
import dev.kilua.utils.jsObjectOf

/**
 * The [motionAnimatedVisibility] composable animates the appearance and disappearance of its content, as
 * [visible] value changes. Different in/out animations can be specified using [transition] and
 * [outTransition]. The function uses Motion animations to animate the visibility of the content.
 *
 * @param visible defines whether the content should be visible
 * @param transition [MotionTransition] used for the appearing animation, defaults to [TransitionType.Fade]
 * @param outTransition [MotionTransition] used for the disappearing animation, defaults to [transition]
 * @param content Content to appear or disappear based on the value of [visible]
 */
@Composable
public fun IComponent.motionAnimatedVisibility(
    visible: Boolean,
    transition: MotionTransition = MotionTransition(),
    outTransition: MotionTransition = transition,
    content: @Composable IComponent.() -> Unit
) {
    val visibleResult = when (transition.type) {
        TransitionType.Fade -> jsObjectOf("opacity" to 1)
        TransitionType.Scale -> jsObjectOf("scale" to 1)
        TransitionType.TranslateLeft,
        TransitionType.TranslateRight,
        TransitionType.TranslateTop,
        TransitionType.TranslateBottom -> jsObjectOf("translate" to "0")
    }
    val invisibleResult = when (outTransition.type) {
        TransitionType.Fade -> jsObjectOf("opacity" to 0)
        TransitionType.Scale -> jsObjectOf("scale" to 0)
        TransitionType.TranslateLeft -> jsObjectOf("translate" to "-100vh")
        TransitionType.TranslateRight -> jsObjectOf("translate" to "100vw")
        TransitionType.TranslateTop -> jsObjectOf("translate" to "0 -100vh")
        TransitionType.TranslateBottom -> jsObjectOf("translate" to "0 100vh")
    }

    var visibilityState by remember { mutableStateOf(visible) }
    var animationOn by remember { mutableStateOf<AnimationPlaybackControls?>(null) }
    var animationOff by remember { mutableStateOf<AnimationPlaybackControls?>(null) }

    val div = divRef {
        position(Position.Relative)
        zIndex(10)
        display(if (visibilityState) Display.InlineBlock else Display.None)
        if (visibilityState) {
            content()
        }
    }

    LaunchedEffect(visible) {
        if (renderConfig.isDom) {
            if (visible) {
                animate(div.element, invisibleResult, jsObjectOf("duration" to 0)).then {}.awaitPromise()
                visibilityState = true
                animationOn = animate(
                    div.element,
                    visibleResult,
                    transition.animation.toJsAny()
                )
                try {
                    animationOn?.then {}?.awaitPromise()
                } finally {
                    animationOn?.stop()
                    animationOn = null
                }
            } else {
                animationOff = animate(
                    div.element,
                    invisibleResult,
                    outTransition.animation.toJsAny()
                )
                try {
                    animationOff?.then {}?.awaitPromise()
                } finally {
                    animationOff?.stop()
                    animationOff = null
                    visibilityState = false
                }
            }
        } else {
            visibilityState = visible
        }
    }
}
