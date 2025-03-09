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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Stable
public interface Router {
    /**
     * The current path
     */
    public fun currentPath(): Path

    /**
     * Navigate to a new path.
     * @param to The path to navigate to.
     * @param hide Whether to hide the current path.
     * @param replace Whether to replace the current path.
     */
    public fun navigate(to: String, hide: Boolean = false, replace: Boolean = false)

    @Composable
    public fun getPath(initPath: String): State<String>

    public companion object {
        /**
         * Provide the router implementation through a CompositionLocal so deeper level
         * Composables in the composition can have access to the current router.
         *
         * This is particularly useful for [NavLink], so we can have a single Composable
         * agnostic of the top level router implementation.
         *
         * To use this composition, you need to invoke any [Router] implementation first.
         */
        public val current: Router
            @Composable
            get() = RouterCompositionLocal.current
    }
}

internal val RouterCompositionLocal: ProvidableCompositionLocal<Router> =
    compositionLocalOf { error("Router not defined, cannot provide through RouterCompositionLocal.") }

@Routing
@Composable
public operator fun Router.invoke(
    initRoute: String,
    routing: @Composable RouteBuilder.() -> Unit
) {
    CompositionLocalProvider(RouterCompositionLocal provides this) {
        val rawPath by getPath(initRoute)
        val path = Path.from(rawPath)
        val node = remember(path) {
            RouteBuilder.routeBuilderCache.getOrPut("${this.hashCode()} ${path.path} $path") {
                RouteBuilder(path.path, path)
            }
        }
        node.routing()
    }
}

public fun Router.navigate(to: String, parameters: Parameters, hide: Boolean = false, replace: Boolean = false) {
    navigate("$to?$parameters", hide = hide, replace = replace)
}

public fun Router.navigate(
    to: String,
    parameters: Map<String, List<String>>,
    hide: Boolean = false,
    replace: Boolean = false
) {
    navigate(to, Parameters.from(parameters), hide = hide, replace = replace)
}

public fun Router.navigate(
    to: String,
    parameters: Map<String, String>,
    hide: Boolean = false,
    replace: Boolean = false
) {
    navigate(to, Parameters.from(parameters), hide = hide, replace = replace)
}
