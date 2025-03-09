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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.utils.nativeMapOf

/**
 * Use the DSL functions to build the expected route handled by a [Router].
 * If two routes match the same path, the first declared route is chosen.
 *
 * With dynamic routing displaying will not stop if two routes of the same kind match the current route:
 *
 * wrong usage:
 * ```kotlin
 *     if (true) {
 *       int {
 *         Text("Match")
 *       }
 *     }
 *     int {
 *       Text("Will be displayed too")
 *     }
 * ```
 * correct usage:
 * ```kotlin
 *     if (true) {
 *       int {
 *         Text("Match")
 *       }
 *     } else {
 *       int {
 *         Text("Won't be displayed")
 *       }
 *     }
 * ```
 */
@Routing
@Stable
public class RouteBuilder internal constructor(private val basePath: String, private val remainingPath: Path) {
    public val path: String = remainingPath.path
    public val parameters: Parameters? = remainingPath.parameters

    private var match by mutableStateOf(Match.NoMatch)

    private enum class Match {
        Constant, Integer, String, NoMatch
    }

    /**
     * Executes its children when the requested subroute matches one of these constant [route].
     *
     * To match `foo/bar`, create a [route] inside the first [route].
     */
    @Routing
    @Composable
    public fun route(
        vararg route: String,
        nestedRoute: @Composable RouteBuilder.() -> Unit
    ) {
        val relaxedRoute = route.check()
        val currentPath = remainingPath.currentPath
        if ((match == Match.NoMatch || match == Match.Constant) && currentPath in relaxedRoute) {
            execute(currentPath, nestedRoute)
            match = Match.Constant
        }
    }

    private fun Array<out String>.check(): List<String> {
        val relaxedRoute = map { it.removePrefix("/").removeSuffix("/") }
        require(relaxedRoute.none { it.contains("/") }) { "To use nested routes, use route() { route() { } } instead." }
        return relaxedRoute
    }

    @Routing
    @Composable
    public fun redirect(vararg route: String, target: String, hide: Boolean = false) {
        val routes = route.check()
        val currentPath = remainingPath.currentPath
        if (match == Match.NoMatch && currentPath in routes) {
            val router = Router.current
            LaunchedEffect(Unit) {
                router.navigate(target, hide)
            }
        }
    }

    @Composable
    private fun execute(currentPath: String, nestedRoute: @Composable RouteBuilder.() -> Unit) {
        val newPath = remainingPath.newPath(currentPath)
        val currentRouter = Router.current
        val delegatingRouter = remember(newPath) { DelegateRouter(basePath, currentRouter) }
        CompositionLocalProvider(RouterCompositionLocal provides delegatingRouter) {
            val newState = routeBuilderCache.getOrPut("${currentRouter.hashCode()} $basePath $newPath") {
                RouteBuilder(basePath, newPath)
            }
            newState.nestedRoute()
        }
    }

    /**
     * Executes its children when the requested subroute is a non-empty [String].
     */
    @Routing
    @Composable
    public fun string(nestedRoute: @Composable RouteBuilder.(String) -> Unit) {
        val currentPath = remainingPath.currentPath
        if ((match == Match.NoMatch || match == Match.String) && currentPath.isNotEmpty()) {
            execute(currentPath) {
                nestedRoute(currentPath)
            }
            match = Match.String
        }
    }

    /**
     * Executes its children when the requested subroute is a [Int].
     */
    @Routing
    @Composable
    public fun int(nestedRoute: @Composable RouteBuilder.(Int) -> Unit) {
        val currentPath = remainingPath.currentPath
        val int = currentPath.toIntOrNull()
        if ((match == Match.NoMatch || match == Match.Integer) && int != null) {
            execute(currentPath) {
                nestedRoute(int)
            }
            match = Match.Integer
        }
    }

    /**
     * Fallback if no matching route is found.
     */
    @Routing
    @Composable
    public fun noMatch(content: @Composable NoMatch.() -> Unit) {
        if (match == Match.NoMatch) {
            NoMatch(remainingPath.path, remainingPath.parameters).content()
        }
    }

    @Immutable
    @Routing
    public class NoMatch internal constructor(public val remainingPath: String, public val parameters: Parameters?) {
        @Routing
        @Composable
        public fun redirect(target: String, hide: Boolean = false) {
            val router = Router.current
            LaunchedEffect(Unit) {
                router.navigate(target, hide)
            }
        }
    }

    public companion object {
        /**
         * Cache for [RouteBuilder] to prevent memory leaks and
         * performance degradation after great number of recompositions.
         */
        internal var routeBuilderCache = nativeMapOf<RouteBuilder>()
    }
}
