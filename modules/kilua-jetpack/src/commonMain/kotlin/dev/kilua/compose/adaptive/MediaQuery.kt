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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import web.window

// Inspired by the SpotifyClone API
// See also: https://github.com/shubhamsinghshubham777/SpotifyClone/blob/main/src/commonMain/kotlin/util.kt

/**
 * Returns a [State] object representing whether the given media query matches the current screen.
 *
 * @param query A string representing the media query.
 * @return A [State] holding `true` if the query matches, `false` otherwise.
 */
@Composable
public fun rememberMediaQueryAsState(query: String): State<Boolean> {
    val mediaQuery = remember(query) { window.matchMedia(query) }
    val matches = remember { mutableStateOf(mediaQuery.matches) }
    LaunchedEffect(Unit) {
        mediaQuery.onchange = {
            matches.value = mediaQuery.matches
        }
    }
    return matches
}
