/*
 * Copyright 2022 Philip Wedemann
 * This file is copied from the https://github.com/hfhbd/routing-compose project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.softwork.routingcompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.browser.window
import org.w3c.dom.Location

/**
 * A router leveraging the History API (https://developer.mozilla.org/en-US/docs/Web/API/History).
 *
 * Using a BrowserRouter requires you to implement a catch-all to send the same resource for
 * every path the router intends to control.
 * BrowserRouter will handle the proper child composition.
 *
 * Without a catch-all rule, will get a 404 or "Cannot GET /path" error each time you refresh or
 * request a specific path.
 * Each server's implementation of a catch-all will be different, and you
 * should handle this based on the webserver environment you're running.
 *
 * For more information about this catch-all, check your webserver implementation's specific
 * instructions.
 * For development environments, see the RoutingCompose Readme
 * for full instructions.
 */
@Composable
public fun BrowserRouter(
    initPath: String,
    routeBuilder: @Composable RouteBuilder.() -> Unit
) {
    BrowserRouter().route(initPath, routeBuilder)
}

internal class BrowserRouter : Router {
    override val currentPath: Path
        get() = Path.from(currentLocation.value)

    private val currentLocation: MutableState<String> = mutableStateOf(window.location.newPath())

    @Composable
    override fun getPath(initPath: String): State<String> {
        LaunchedEffect(Unit) {
            currentLocation.value = window.location.newPath().takeUnless { it == "/" } ?: initPath
            window.onpopstate = {
                currentLocation.value = window.location.newPath()
                Unit
            }
        }
        return currentLocation
    }

    private fun Location.newPath() = "$pathname$search"

    override fun navigate(to: String, hide: Boolean) {
        if (hide) {
            currentLocation.value = to
        } else {
            window.history.pushState(null, "", to)
            /*
             The history API unfortunately provides no callback to listen to
             [window.history.pushState], so we need to notify subscribers when pushing a new path.
             */
            currentLocation.value = window.location.newPath()
        }
    }
}
