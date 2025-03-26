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

import androidx.compose.runtime.Stable
import dev.kilua.utils.jsObjectOf
import dev.kilua.utils.toKebabCase
import js.core.JsAny
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Motion animation repeat types.
 */
public enum class RepeatType {
    Loop,
    Reverse,
    Mirror;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value
}

/**
 * Motion animation easing functions.
 */
public enum class MotionEasingFunction(public val value: String) {
    Linear("linear"),
    EaseIn("easeIn"),
    EaseOut("easeOut"),
    EaseInOut("easeInOut"),
    CircIn("circIn"),
    CircOut("circOut"),
    CircInOut("circInOut"),
    BackIn("backIn"),
    BackOut("backOut"),
    BackInOut("backInOut"),
    Anticipate("anticipate");

    override fun toString(): String = value
}

/**
 * Motion animation parameters.
 */
@Stable
public sealed class MotionAnimation(
    public val delay: Duration,
    public val repeat: Int,
    public val repeatType: RepeatType?,
    public val repeatDelay: Duration?,
) {
    /**
     * Tween animation.
     *
     * @param duration Duration of the animation (default is 1 second)
     * @param ease Easing function (default is [MotionEasingFunction.EaseInOut])
     * @param cubicBezier Custom cubic bezier easing function
     * @param delay Delay before the animation starts
     * @param repeat Number of times the animation should repeat
     * @param repeatType Type of the repeat
     * @param repeatDelay Delay between repeats
     */
    public class Tween(
        public val duration: Duration = 1.seconds,
        public val ease: MotionEasingFunction = MotionEasingFunction.EaseInOut,
        public val cubicBezier: List<Double>? = null,
        delay: Duration = Duration.ZERO,
        repeat: Int = 0,
        repeatType: RepeatType? = null,
        repeatDelay: Duration? = null
    ) : MotionAnimation(delay, repeat, repeatType, repeatDelay)

    /**
     * Spring animation based on time.
     *
     * @param duration Duration of the animation (default is 1 second)
     * @param bounce Bounce factor, 0 is no bounce, and 1 is extremely bouncy (default is 0.25)
     * @param visualDuration Duration of the visual part of the animation (overrides the duration)
     * @param delay Delay before the animation starts
     * @param repeat Number of times the animation should repeat
     * @param repeatType Type of the repeat
     * @param repeatDelay Delay between repeats
     */
    public class SpringTime(
        public val duration: Duration = 1.seconds,
        public val bounce: Double = 0.25,
        public val visualDuration: Duration? = null,
        delay: Duration = Duration.ZERO,
        repeat: Int = 0,
        repeatType: RepeatType? = null,
        repeatDelay: Duration? = null
    ) : MotionAnimation(delay, repeat, repeatType, repeatDelay)

    /**
     * Spring animation based on physics.
     *
     * @param stiffness Stiffness of the spring. Higher values will create more sudden movement. (default is 1)
     * @param damping Strength of opposing force. If set to 0, spring will oscillate indefinitely. (default is 10)
     * @param mass Mass of the object. Higher values will result in more lethargic movement. (default is 1)
     * @param velocity Initial velocity of the object
     * @param restSpeed End animation if absolute speed drops below this value and delta is smaller than [restDelta].
     *     (default is 0.1)
     * @param restDelta End animation if distance is below this value and speed is below [restSpeed] (default is 0.01)
     * @param delay Delay before the animation starts
     * @param repeat Number of times the animation should repeat
     * @param repeatType Type of the repeat
     * @param repeatDelay Delay between repeats
     */
    public class SpringPhysics(
        public val stiffness: Int = 1,
        public val damping: Int = 10,
        public val mass: Double = 1.0,
        public val velocity: Double? = null,
        public val restSpeed: Double = 0.1,
        public val restDelta: Double = 0.01,
        delay: Duration = Duration.ZERO,
        repeat: Int = 0,
        repeatType: RepeatType? = null,
        repeatDelay: Duration? = null
    ) : MotionAnimation(delay, repeat, repeatType, repeatDelay)
}

internal fun MotionAnimation.toJsAny(): JsAny {
    return when (this) {
        is MotionAnimation.Tween -> {
            jsObjectOf(
                "type" to "tween",
                "duration" to duration.inWholeMilliseconds / 1000.0,
                "ease" to (cubicBezier ?: ease.value),
                "delay" to delay.inWholeMilliseconds / 1000.0,
                "repeat" to repeat,
                "repeatType" to repeatType?.toString(),
                "repeatDelay" to repeatDelay?.inWholeMilliseconds?.let { it / 1000.0 }
            )
        }

        is MotionAnimation.SpringTime -> {
            jsObjectOf(
                "type" to "spring",
                "duration" to duration.inWholeMilliseconds / 1000.0,
                "bounce" to bounce,
                "visualDuration" to visualDuration?.inWholeMilliseconds?.let { it / 1000.0 },
                "delay" to delay.inWholeMilliseconds / 1000.0,
                "repeat" to repeat,
                "repeatType" to repeatType?.toString(),
                "repeatDelay" to repeatDelay?.inWholeMilliseconds?.let { it / 1000.0 }
            )
        }

        is MotionAnimation.SpringPhysics -> {
            jsObjectOf(
                "type" to "spring",
                "stiffness" to stiffness,
                "damping" to damping,
                "mass" to mass,
                "velocity" to velocity,
                "restSpeed" to restSpeed,
                "restDelta" to restDelta,
                "delay" to delay.inWholeMilliseconds / 1000.0,
                "repeat" to repeat,
                "repeatType" to repeatType?.toString(),
                "repeatDelay" to repeatDelay?.inWholeMilliseconds?.let { it / 1000.0 }
            )
        }
    }
}
