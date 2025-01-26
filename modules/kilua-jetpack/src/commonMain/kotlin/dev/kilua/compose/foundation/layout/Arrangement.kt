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

package dev.kilua.compose.foundation.layout

import dev.kilua.compose.ui.Alignment
import dev.kilua.html.CssSize
import dev.kilua.html.JustifyContent
import dev.kilua.html.px

// Inspired by the official Jetpack Compose API and Kobweb API
// See also: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:/compose/foundation/foundation-layout/src/commonMain/kotlin/androidx/compose/foundation/layout/Arrangement.kt
// See also: https://github.com/varabyte/kobweb/blob/main/frontend/kobweb-compose/src/jsMain/kotlin/com/varabyte/kobweb/compose/foundation/layout/Arrangement.kt

/**
 * The arrangement of elements in a row or column.
 * Maps Jetpack Compose arrangement values to CSS flexbox.
 */
public object Arrangement {
    /**
     * Base interface for horizontal arrangement values.
     */
    public sealed interface Horizontal {
        public val justifyContent: JustifyContent
    }

    /**
     * Base interface for vertical arrangement values.
     */
    public sealed interface Vertical {
        public val justifyContent: JustifyContent
    }

    /**
     * Base interface for both horizontal and vertical arrangement values.
     */
    public sealed interface HorizontalOrVertical : Horizontal, Vertical

    public object End : Horizontal {
        override val justifyContent: JustifyContent = JustifyContent.End
    }

    public object Start : Horizontal {
        override val justifyContent: JustifyContent = JustifyContent.Start
    }

    public object Top : Vertical {
        override val justifyContent: JustifyContent = JustifyContent.Start
    }

    public object Bottom : Vertical {
        override val justifyContent: JustifyContent = JustifyContent.End
    }

    public object Center : HorizontalOrVertical {
        override val justifyContent: JustifyContent = JustifyContent.Center
    }

    public object SpaceEvenly : HorizontalOrVertical {
        override val justifyContent: JustifyContent = JustifyContent.SpaceEvenly
    }

    public object SpaceBetween : HorizontalOrVertical {
        override val justifyContent: JustifyContent = JustifyContent.SpaceBetween
    }

    public object SpaceAround : HorizontalOrVertical {
        override val justifyContent: JustifyContent = JustifyContent.SpaceAround
    }

    /**
     * Creates an arrangement value with a fixed [space] between the children for both horizontal and vertical orientations.
     */
    public fun spacedBy(space: CssSize): HorizontalOrVertical =
        SpacedAligned.HorizontalOrVertical(space)

    /**
     * Creates an arrangement value with a fixed [space] between the children, using the given [alignment] for vertical orientations.
     */
    public fun spacedBy(space: CssSize, alignment: Alignment.Vertical): Vertical =
        SpacedAligned.Vertical(space, alignment)

    /**
     * Creates an arrangement value with a fixed [space] between the children, using the given [alignment] for horizontal orientations.
     */
    public fun spacedBy(space: CssSize, alignment: Alignment.Horizontal): Horizontal =
        SpacedAligned.Horizontal(space, alignment)

    /**
     * Creates an arrangement value with no space between the children, using the given [alignment] for vertical orientations.
     */
    public fun aligned(alignment: Alignment.Vertical): Vertical =
        SpacedAligned.Vertical(0.px, alignment)

    /**
     * Creates an arrangement value with no space between the children, using the given [alignment] for horizontal orientations.
     */
    public fun aligned(alignment: Alignment.Horizontal): Horizontal =
        SpacedAligned.Horizontal(0.px, alignment)
}

internal sealed class SpacedAligned(
    val space: CssSize,
    override val justifyContent: JustifyContent,
) : Arrangement.HorizontalOrVertical {

    class HorizontalOrVertical(
        space: CssSize,
    ) : SpacedAligned(space, JustifyContent.Start)

    class Vertical(
        space: CssSize,
        val alignment: Alignment.Vertical,
    ) : SpacedAligned(space, alignment.justifyContent)

    class Horizontal(
        spacing: CssSize,
        val alignment: Alignment.Horizontal,
    ) : SpacedAligned(spacing, alignment.justifyContent)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as SpacedAligned

        if (space != other.space) return false
        if (justifyContent != other.justifyContent) return false
        return when (this) {
            is Vertical -> this.alignment == (other as Vertical).alignment
            is Horizontal -> this.alignment == (other as Horizontal).alignment
            else -> true
        }
    }

    override fun hashCode(): Int {
        var result = space.hashCode()
        result = 31 * result + justifyContent.hashCode()
        when (this) {
            is Vertical -> result = 31 * result + alignment.hashCode()
            is Horizontal -> result = 31 * result + alignment.hashCode()
            else -> Unit
        }
        return result
    }
}
