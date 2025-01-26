/*
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
package dev.kilua.compose.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

/**
 * Enum representing screen orientation.
 */
public enum class Orientation {
    /** Portrait orientation. */
    PORTRAIT,

    /** Landscape orientation. */
    LANDSCAPE,
}

/**
 * Returns a [State] object representing the current screen orientation.
 *
 * @return A [State] holding the current [Orientation].
 */
@Composable
public fun rememberOrientation(): State<Orientation> {
    val isPortrait by rememberMediaQueryAsState("(orientation: portrait)")

    return remember {
        derivedStateOf {
            when {
                isPortrait -> Orientation.PORTRAIT
                else -> Orientation.LANDSCAPE
            }
        }
    }
}
