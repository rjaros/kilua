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

package dev.kilua.compose.ui

import androidx.compose.runtime.Composable
import dev.kilua.html.ITag
import dev.kilua.html.helpers.ModifierBase
import web.dom.HTMLElement

// Inspired by the official Jetpack Compose API and Kobweb API
// See also: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/ui/ui/src/commonMain/kotlin/androidx/compose/ui/Modifier.kt
// See also: https://github.com/varabyte/kobweb/blob/main/frontend/kobweb-compose/src/jsMain/kotlin/com/varabyte/kobweb/compose/ui/Modifier.kt

/**
 * An ordered, immutable collection of [modifier elements][Modifier.Element].
 */
public interface Modifier : ModifierBase {
    /**
     * Accumulates a value starting with [initial] and applying [operation] to the current value and
     * each element from left to right.
     */
    @Composable
    public fun <R> fold(initial: R, operation: @Composable (R, Element) -> R): R

    /**
     * Concatenates this modifier with other modifier.
     */
    public infix fun then(other: Modifier): Modifier =
        if (other === Modifier) this else ChainedModifier(this, other)

    @Composable
    public override fun <E : HTMLElement> useOn(tag: ITag<E>) {
        this.fold(Unit) { _, modifierElement ->
            modifierElement.useOn(tag)
        }
    }

    /**
     * A single element contained within a [Modifier] chain.
     */
    public interface Element : Modifier {
        @Composable
        override fun <R> fold(initial: R, operation: @Composable (R, Element) -> R): R = operation(initial, this)
    }

    /**
     * The companion object `Modifier` is the empty, default, or starter [Modifier] that contains no
     * [elements][Element].
     */
    public companion object : Modifier {
        @Composable
        override fun <R> fold(initial: R, operation: @Composable (R, Element) -> R): R = initial
        override infix fun then(other: Modifier): Modifier = other
    }
}

/**
 * An entry in a [Modifier] chain.
 */
private class ChainedModifier(
    private val current: Modifier,
    private val next: Modifier
) : Modifier {
    @Composable
    override fun <R> fold(initial: R, operation: @Composable (R, Modifier.Element) -> R): R {
        return next.fold(current.fold(initial, operation), operation)
    }

    override fun equals(other: Any?): Boolean =
        other is ChainedModifier && current == other.current && next == other.next

    override fun hashCode(): Int = current.hashCode() + 31 * next.hashCode()
}
