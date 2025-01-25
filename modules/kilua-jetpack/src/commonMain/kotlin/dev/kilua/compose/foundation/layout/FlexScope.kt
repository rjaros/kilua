/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2025 Ghasem Shirdel
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

// Inspired by Kobweb API
// See also: https://github.com/varabyte/kobweb/blob/main/frontend/kobweb-compose/src/jsMain/kotlin/com/varabyte/kobweb/compose/foundation/layout/FlexScope.kt

import androidx.compose.runtime.Immutable
import dev.kilua.compose.ui.Alignment
import dev.kilua.compose.ui.Modifier
import dev.kilua.compose.ui.alignSelf
import dev.kilua.compose.ui.flexGrow
import dev.kilua.html.AlignItems

/**
 * Allows declaring mapping from Jetpack Compose weight() modifier to CSS Flexbox flex-grow.
 */
@Immutable
public interface FlexScope {
    public fun Modifier.weight(value: Int): Modifier = flexGrow(value)
}

/**
 * Allows declaring mapping from Jetpack Compose vertical align() modifier to CSS Flexbox align-self.
 */
@Immutable
public interface RowScope : FlexScope {
    public fun Modifier.align(alignment: Alignment.Vertical): Modifier = when (alignment) {
        Alignment.Top -> alignSelf(AlignItems.FlexStart)
        Alignment.CenterVertically -> alignSelf(AlignItems.Center)
        Alignment.Bottom -> alignSelf(AlignItems.FlexEnd)
    }
}

/**
 * Allows declaring mapping from Jetpack Compose horizontal align() modifier to CSS Flexbox align-self.
 */
@Immutable
public interface ColumnScope : FlexScope {
    public fun Modifier.align(alignment: Alignment.Horizontal): Modifier = when (alignment) {
        Alignment.Start -> alignSelf(AlignItems.FlexStart)
        Alignment.CenterHorizontally -> alignSelf(AlignItems.Center)
        Alignment.End -> alignSelf(AlignItems.FlexEnd)
    }
}
