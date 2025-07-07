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

package dev.kilua.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import app.softwork.routingcompose.Router
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

public val DoneCallbackCompositionLocal: ProvidableCompositionLocal<(() -> Unit)?> =
    compositionLocalOf { null }

/**
 * LaunchedEffect wrapper which automatically adds route path as a key.
 * It also supports executing done callback, which is used by the SSR router.
 */
@Composable
internal fun RouteEffect(vararg keys: String?, block: suspend () -> Unit) {
    val doneCallback = DoneCallbackCompositionLocal.current
    LaunchedEffect(Router.current.currentPath().toString(), *keys) {
        supervisorScope {
            launch {
                try {
                    block()
                } finally {
                    doneCallback?.invoke()
                }
            }
        }
    }
}
