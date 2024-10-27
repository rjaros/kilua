/*
 * Copyright (c) 2024 Robert Jaros
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

package dev.kilua.ssr

import androidx.compose.runtime.Composable
import app.softwork.routingcompose.RouteBuilder
import dev.kilua.core.IComponent

/**
 * A router supporting Server-Side Rendering (SSR).
 *
 * This router can be used to directly declare UI components for each route,
 * which will be rendered on the server immediately for every request.
 */
@Composable
public fun IComponent.SimpleSsrRouter(
    initPath: String = "/",
    contextPath: String = getContextPath(),
    stateSerializer: (() -> String)? = null,
    routeBuilder: @Composable (RouteBuilder.() -> Unit)
) {
    if (renderConfig.isDom) {
        SsrRouter("$contextPath$initPath", active = true, useDoneCallback = false) {
            if (contextPath.isNotEmpty()) {
                route("$contextPath$initPath") {
                    routeBuilder()
                }
            } else {
                routeBuilder()
            }
        }
    } else {
        SsrRouter(initPath, active = true, stateSerializer, useDoneCallback = false) {
            routeBuilder()
        }
    }
}
