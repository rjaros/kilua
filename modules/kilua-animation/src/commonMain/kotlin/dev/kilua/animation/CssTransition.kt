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

import dev.kilua.utils.toKebabCase
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * CSS animation timing functions.
 */
public enum class CssTimingFunction {
    Ease,
    Linear,
    EaseIn,
    EaseOut,
    EaseInOut,
    StepStart,
    StepEnd;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value
}

/**
 * CSS animation transition parameters.
 */
public data class CssTransition(
    val type: TransitionType = TransitionType.Fade,
    val duration: Duration = 1.seconds,
    val timingFunction: CssTimingFunction = CssTimingFunction.Ease,
    val customTimingFunction: String? = null,
)
