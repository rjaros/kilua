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
 * This router will defer the rendering on the server until some asynchronous
 * operation (e.g. fetching data) is finished. It requires the use of [dev.kilua.routing.RouteEffect],
 * [dev.kilua.routing.routeAction], [dev.kilua.routing.stringAction], [dev.kilua.routing.intAction]
 * or [dev.kilua.routing.noMatchAction] for every declared route.
 *
 * Note:
 * Be sure to use one of the above functions for every possible route to ensure the
 * server-side rendering is completed.
 */
@Composable
public fun IComponent.AsyncSsrRouter(
    initRoute: String = "/",
    contextPath: String = getContextPath(),
    active: Boolean = true,
    stateSerializer: (() -> String)? = null,
    routing: @Composable (RouteBuilder.() -> Unit)
) {
    if (renderConfig.isDom) {
        SsrRouter("$contextPath$initRoute", active, useDoneCallback = true) {
            if (contextPath.isNotEmpty()) {
                route("$contextPath$initRoute") {
                    routing()
                }
            } else {
                routing()
            }
        }
    } else {
        SsrRouter(initRoute, active, stateSerializer, useDoneCallback = true) {
            routing()
        }
    }
}
