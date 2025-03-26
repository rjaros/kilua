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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.externals.IntCallback
import dev.kilua.externals.StringCallback
import dev.kilua.externals.animate
import dev.kilua.html.Color
import dev.kilua.html.CssSize
import dev.kilua.utils.deepMerge
import dev.kilua.utils.isDom
import js.objects.jso

// Similar behaviour to Compose Animation spring()
private val defaultAnimation = MotionAnimation.SpringPhysics(stiffness = 1700, damping = 80)

/**
 * Automatically animate [Double] value. When the provided [value] is changed, the animation will
 * run automatically. If there is already an active animation it will animate towards the new value.
 *
 * @param value Target value of the animation
 * @param animation The animation that will be used to change the value through time. [MotionAnimation.SpringPhysics]
 *   is used by default.
 * @return A [State] object, the value of which is updated by animation.
 */
@Composable
public fun animateDoubleAsState(value: Double, animation: MotionAnimation = defaultAnimation): State<Double> =
    animateAsIntState(value, { (it * 100).toInt() }, { (it.toDouble() / 100) }, animation)

/**
 * Automatically animate [Float] value. When the provided [value] is changed, the animation will
 * run automatically. If there is already an active animation it will animate towards the new value.
 *
 * @param value Target value of the animation
 * @param animation The animation that will be used to change the value through time. [MotionAnimation.SpringPhysics]
 *   is used by default.
 * @return A [State] object, the value of which is updated by animation.
 */
@Composable
public fun animateFloatAsState(value: Float, animation: MotionAnimation = defaultAnimation): State<Float> =
    animateAsIntState(value, { (it.toDouble() * 100).toInt() }, { (it.toDouble() / 100).toFloat() }, animation)

/**
 * Automatically animate [Int] value. When the provided [value] is changed, the animation will
 * run automatically. If there is already an active animation it will animate towards the new value.
 *
 * @param value Target value of the animation
 * @param animation The animation that will be used to change the value through time. [MotionAnimation.SpringPhysics]
 *   is used by default.
 * @return A [State] object, the value of which is updated by animation.
 */
@Composable
public fun animateIntAsState(value: Int, animation: MotionAnimation = defaultAnimation): State<Int> =
    animateAsIntState(value, { it }, { it }, animation)

/**
 * Automatically animate [Long] value. When the provided [value] is changed, the animation will
 * run automatically. If there is already an active animation it will animate towards the new value.
 *
 * @param value Target value of the animation
 * @param animation The animation that will be used to change the value through time. [MotionAnimation.SpringPhysics]
 *   is used by default.
 * @return A [State] object, the value of which is updated by animation.
 */
@Composable
public fun animateLongAsState(value: Long, animation: MotionAnimation = defaultAnimation): State<Long> =
    animateAsIntState(value, { it.toInt() }, { it.toLong() }, animation)

/**
 * Automatically animate [CssSize] value. When the provided [value] is changed, the animation will
 * run automatically. If there is already an active animation it will animate towards the new value.
 *
 * @param value Target value of the animation
 * @param animation The animation that will be used to change the value through time. [MotionAnimation.SpringPhysics]
 *   is used by default.
 * @return A [State] object, the value of which is updated by animation.
 */
@Composable
public fun animateCssSizeAsState(value: CssSize, animation: MotionAnimation = defaultAnimation): State<CssSize> =
    animateAsIntState(
        value,
        { (it.size.toDouble() * 100).toInt() },
        { CssSize((it.toDouble() / 100), value.unit) },
        animation
    )

/**
 * Automatically animate [Color] value. When the provided [value] is changed, the animation will
 * run automatically. If there is already an active animation it will animate towards the new value.
 *
 * @param value Target value of the animation. It needs to be provided using hex format e.g. Color.hex(0x00ff00).
 * @param animation The animation that will be used to change the value through time. [MotionAnimation.SpringPhysics]
 *   is used by default.
 * @return A [State] object, the value of which is updated by animation.
 */
@Composable
public fun animateColorAsState(value: Color, animation: MotionAnimation = defaultAnimation): State<Color> =
    animateAsStringState(value, { it.value }, { Color(it) }, animation)

@Composable
internal fun <T> animateAsIntState(
    value: T,
    convertToInt: (T) -> Int,
    convertFromInt: (Int) -> T,
    animation: MotionAnimation
): State<T> {
    var oldValue by remember { mutableStateOf(value) }
    return produceState<T>(oldValue, value) {
        if (isDom) {
            val animationOptions = animation.toJsAny()
            val intCallback = jso<IntCallback> {
                onUpdate = { v: Int ->
                    val newValue = convertFromInt(v)
                    oldValue = newValue
                    this@produceState.value = newValue
                }
            }
            val controls =
                animate(convertToInt(oldValue), convertToInt(value), deepMerge(animationOptions, intCallback))
            awaitDispose {
                controls.stop()
                controls.cancel()
            }
        } else {
            this.value = value
        }
    }
}

@Composable
internal fun <T> animateAsStringState(
    value: T,
    convertToString: (T) -> String,
    convertFromString: (String) -> T,
    animation: MotionAnimation
): State<T> {
    var oldValue by remember { mutableStateOf(value) }
    return produceState<T>(oldValue, value) {
        if (isDom) {
            val animationOptions = animation.toJsAny()
            val stringCallback = jso<StringCallback> {
                onUpdate = { v: String ->
                    val newValue = convertFromString(v)
                    oldValue = newValue
                    this@produceState.value = newValue
                }
            }
            val controls =
                animate(convertToString(oldValue), convertToString(value), deepMerge(animationOptions, stringCallback))
            awaitDispose {
                controls.stop()
                controls.cancel()
            }
        } else {
            this.value = value
        }
    }
}
