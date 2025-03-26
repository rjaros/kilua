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
import dev.kilua.html.Display
import dev.kilua.html.Position
import dev.kilua.html.div
import kotlinx.coroutines.delay
import kotlin.time.DurationUnit

/**
 * The [animatedVisibility] composable animates the appearance and disappearance of its content, as
 * [visible] value changes. Different in/out animations can be specified using [transition] and
 * [outTransition]. The function uses CSS animations to animate the visibility of the content.
 *
 * @param visible defines whether the content should be visible
 * @param transition [CssTransition] used for the appearing animation, defaults to [TransitionType.Fade]
 * @param outTransition [CssTransition] used for the disappearing animation, defaults to [transition]
 * @param content Content to appear or disappear based on the value of [visible]
 */
@Composable
public fun IComponent.animatedVisibility(
    visible: Boolean,
    transition: CssTransition = CssTransition(),
    outTransition: CssTransition = transition,
    content: @Composable IComponent.() -> Unit
) {

    var visibilityState by remember { mutableStateOf(visible) }
    var animation: String? by remember { mutableStateOf(null) }

    if (visibilityState) {
        div {
            position(Position.Relative)
            zIndex(10)
            display(Display.InlineBlock)
            style("animation", animation)
            content()
        }
    }

    LaunchedEffect(visible) {
        if (visible != visibilityState) {
            if (visible) {
                animation =
                    "kilua-${transition.type.value}-in ${transition.duration.toInt(DurationUnit.MILLISECONDS)}ms ${transition.customTimingFunction ?: transition.timingFunction.value}"
                visibilityState = true
                try {
                    delay(transition.duration)
                } finally {
                    animation = null
                }
            } else {
                // add 500ms to the out transition time to make sure the animation is still running
                animation =
                    "kilua-${outTransition.type.value}-out ${outTransition.duration.toInt(DurationUnit.MILLISECONDS) + 500}ms ${outTransition.customTimingFunction ?: outTransition.timingFunction.value}"
                try {
                    delay(outTransition.duration)
                } finally {
                    visibilityState = false
                    animation = null
                }
            }
        }
    }
}
