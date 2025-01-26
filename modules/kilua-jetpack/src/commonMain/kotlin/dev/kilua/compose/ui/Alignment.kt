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

import dev.kilua.html.AlignItems
import dev.kilua.html.JustifyContent
import dev.kilua.html.JustifyItems

// Inspired by the official Jetpack Compose API and Kobweb API
// See also: https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:/compose/ui/ui/src/commonMain/kotlin/androidx/compose/ui/Alignment.kt
// See also: https://github.com/varabyte/kobweb/blob/main/frontend/kobweb-compose/src/jsMain/kotlin/com/varabyte/kobweb/compose/ui/Alignment.kt

/**
 * The alignment of elements in a row, column or box.
 * Maps Jetpack Compose alignment values to CSS flexbox or CSS grid.
 */
public sealed class Alignment(public val alignItems: AlignItems, public val justifyItems: JustifyItems) {
    public sealed class Vertical(public val alignItems: AlignItems, public val justifyContent: JustifyContent)
    public sealed class Horizontal(public val alignItems: AlignItems, public val justifyContent: JustifyContent)

    public object TopStart : Alignment(AlignItems.Start, JustifyItems.Start)
    public object TopCenter : Alignment(AlignItems.Start, JustifyItems.Center)
    public object TopEnd : Alignment(AlignItems.Start, JustifyItems.End)
    public object CenterStart : Alignment(AlignItems.Center, JustifyItems.Start)
    public object Center : Alignment(AlignItems.Center, JustifyItems.Center)
    public object CenterEnd : Alignment(AlignItems.Center, JustifyItems.End)
    public object BottomStart : Alignment(AlignItems.End, JustifyItems.Start)
    public object BottomCenter : Alignment(AlignItems.End, JustifyItems.Center)
    public object BottomEnd : Alignment(AlignItems.End, JustifyItems.End)

    public object Top : Vertical(AlignItems.Start, JustifyContent.Start)
    public object CenterVertically : Vertical(AlignItems.Center, JustifyContent.Center)
    public object Bottom : Vertical(AlignItems.Start, JustifyContent.End)

    public object Start : Horizontal(AlignItems.Start, JustifyContent.Start)
    public object CenterHorizontally : Horizontal(AlignItems.Center, JustifyContent.Center)
    public object End : Horizontal(AlignItems.End, JustifyContent.End)

    public val placeValue: String = if (alignItems.value == justifyItems.value) {
        alignItems.value
    } else {
        "${alignItems.value} ${justifyItems.value}"
    }
}
