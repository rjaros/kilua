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
import dev.kilua.core.IComponent

/**
 * A model for an application route.
 */
public sealed class Route(
    public open val meta: Meta?,
    public open val childRoutes: List<Route>?
) {
    /**
     * Represents a route that matches a specific path or set of paths.
     */
    public class PathRoute(
        public val paths: List<String>,
        public override val meta: Meta? = null,
        public override val childRoutes: List<Route>? = null,
        internal val view: (@Composable IComponent.() -> Unit)? = null,
        internal val action: (suspend () -> Unit)? = null,
        internal val context: RoutingContextImpl
    ) : Route(meta, childRoutes) {
        override fun toString(): String {
            return "PathRoute(paths=${paths}, meta=$meta, childRoutes=$childRoutes)"
        }
    }

    /**
     * Represents a route that matches a specific string segment.
     */
    public class StringRoute(
        public override val meta: Meta? = null,
        public override val childRoutes: List<Route>? = null,
        internal val view: (@Composable IComponent.(String) -> Unit)? = null,
        internal val action: (suspend (String) -> Unit)? = null,
        internal val context: StringRoutingContextImpl
    ) : Route(meta, childRoutes) {
        override fun toString(): String {
            return "StringRoute(meta=$meta, childRoutes=$childRoutes)"
        }
    }

    /**
     * Represents a route that matches a specific numeric segment.
     */
    public class IntRoute(
        public override val meta: Meta? = null,
        public override val childRoutes: List<Route>? = null,
        internal val view: (@Composable IComponent.(Int) -> Unit)? = null,
        internal val action: (suspend (Int) -> Unit)? = null,
        internal val context: IntRoutingContextImpl
    ) : Route(meta, childRoutes) {
        override fun toString(): String {
            return "IntRoute(meta=$meta, childRoutes=$childRoutes)"
        }
    }
}
