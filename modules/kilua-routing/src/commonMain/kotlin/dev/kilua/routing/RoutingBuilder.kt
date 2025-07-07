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
 * A marker for routing DSL.
 */
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@DslMarker
public annotation class RoutingDsl

/**
 * A base interface for routing builders.
 * Provides methods to define routes, views, actions, and metadata.
 */
@RoutingDsl
public interface BaseRoutingBuilder {
    /**
     * Adds a route for the specified path.
     *
     * @param path the path segments for the route
     * @param routingBuilder the routing configuration block
     */
    public fun route(vararg path: String, routingBuilder: PlainRoutingBuilder.(RoutingContext) -> Unit)

    /**
     * Adds a route for a string path segment.
     *
     * @param routingBuilder the routing configuration block
     */
    public fun string(routingBuilder: StringRoutingBuilder.(StringRoutingContext) -> Unit)

    /**
     * Adds a route for a numeric path segment.
     *
     * @param routingBuilder the routing configuration block
     */
    public fun int(routingBuilder: IntRoutingBuilder.(IntRoutingContext) -> Unit)

    /**
     * Adds metadata for the given route.
     *
     * @param meta the metadata configuration block
     */
    public fun meta(meta: @RoutingDsl Meta.() -> Unit)
}

/**
 * A routing builder interface for entry route.
 */
public interface RoutingBuilder : BaseRoutingBuilder {
    /**
     * Adds a default content view for the entry route.
     *
     * @param defaultContent the default content view configuration block
     */
    public fun defaultContent(defaultContent: @Composable @RoutingDsl IComponent.() -> Unit)

    /**
     * Adds default metadata for the entry route.
     *
     * @param defaultMeta the default metadata configuration block
     */
    public fun defaultMeta(defaultMeta: @RoutingDsl Meta.() -> Unit)

    /**
     * Adds a view for unmatched routes.
     *
     * @param view the view configuration block
     */
    public fun view(view: @Composable @RoutingDsl IComponent.() -> Unit)

    /**
     * Adds an action for unmatched routes.
     *
     * @param action the action configuration block
     */
    public fun action(action: @RoutingDsl suspend () -> Unit)
}

/**
 * A routing builder interface for given path routes.
 */
public interface PlainRoutingBuilder : BaseRoutingBuilder {
    /**
     * Adds a view for the route.
     *
     * @param view the view configuration block
     */
    public fun view(view: @Composable @RoutingDsl IComponent.() -> Unit)

    /**
     * Adds an action for the route.
     *
     * @param action the action configuration block
     */
    public fun action(action: @RoutingDsl suspend () -> Unit)
}

/**
 * A routing builder interface for string routes.
 */
public interface StringRoutingBuilder : BaseRoutingBuilder {
    /**
     * Adds a view for the route with a string parameter.
     *
     * @param view the view configuration block
     */
    public fun view(view: @Composable @RoutingDsl IComponent.(String) -> Unit)

    /**
     * Adds an action for the route with a string parameter.
     *
     * @param action the action configuration block
     */
    public fun action(action: @RoutingDsl suspend (String) -> Unit)
}

/**
 * A routing builder interface for numeric routes.
 */
public interface IntRoutingBuilder : BaseRoutingBuilder {
    /**
     * Adds a view for the route with a numeric parameter.
     *
     * @param view the view configuration block
     */
    public fun view(view: @Composable @RoutingDsl IComponent.(Int) -> Unit)

    /**
     * Adds an action for the route with a numeric parameter.
     *
     * @param action the action configuration block
     */
    public fun action(action: @RoutingDsl suspend (Int) -> Unit)
}

/**
 * An abstract base implementation of the [BaseRoutingBuilder] interface.
 * Provides common functionality for routing builders.
 */
public abstract class BaseRoutingBuilderImpl : BaseRoutingBuilder {

    private var routesList: MutableList<Route>? = null

    public var meta: Meta? = null

    public fun add(route: Route) {
        if (routesList == null) {
            routesList = mutableListOf()
        }
        routesList!!.add(route)
    }

    public open fun routes(): List<Route>? = routesList

    override fun route(vararg path: String, routingBuilder: PlainRoutingBuilder.(RoutingContext) -> Unit) {
        val childBuilder = PlainRoutingBuilderImpl()
        val childContext = RoutingContextImpl()
        routingBuilder(childBuilder, childContext)
        add(
            Route.PathRoute(
                path.toList(),
                meta = childBuilder.meta,
                childRoutes = childBuilder.routes(),
                view = childBuilder.view,
                action = childBuilder.action,
                context = childContext
            )
        )
    }

    override fun string(routingBuilder: StringRoutingBuilder.(StringRoutingContext) -> Unit) {
        val childBuilder = StringRoutingBuilderImpl()
        val childContext = StringRoutingContextImpl()
        routingBuilder(childBuilder, childContext)
        add(
            Route.StringRoute(
                meta = childBuilder.meta,
                childRoutes = childBuilder.routes(),
                view = childBuilder.view,
                action = childBuilder.action,
                context = childContext
            )
        )
    }

    override fun int(routingBuilder: IntRoutingBuilder.(IntRoutingContext) -> Unit) {
        val childBuilder = IntRoutingBuilderImpl()
        val childContext = IntRoutingContextImpl()
        routingBuilder(childBuilder, childContext)
        add(
            Route.IntRoute(
                meta = childBuilder.meta,
                childRoutes = childBuilder.routes(),
                view = childBuilder.view,
                action = childBuilder.action,
                context = childContext
            )
        )
    }

    public override fun meta(meta: @RoutingDsl Meta.() -> Unit) {
        if (this.meta == null) {
            this.meta = MetaImpl()
        }
        this.meta!!.meta()
    }
}

/**
 * An implementation of the [RoutingBuilder] interface for entry routes.
 * Provides methods to define default content, metadata, views, and actions.
 */
public open class RoutingBuilderImpl : BaseRoutingBuilderImpl(), RoutingBuilder {
    public var defaultContent: (@Composable @RoutingDsl IComponent.() -> Unit)? = null
    public var defaultMeta: Meta? = null
    public var view: (@Composable @RoutingDsl IComponent.() -> Unit)? = null
    public var action: (suspend () -> Unit)? = null

    override fun defaultContent(defaultContent: @Composable @RoutingDsl (IComponent.() -> Unit)) {
        this.defaultContent = defaultContent
    }

    override fun view(view: @Composable @RoutingDsl (IComponent.() -> Unit)) {
        this.view = view
    }

    override fun action(action: suspend () -> Unit) {
        this.action = action
    }

    override fun defaultMeta(defaultMeta: @RoutingDsl Meta.() -> Unit) {
        if (this.defaultMeta == null) {
            this.defaultMeta = MetaImpl()
        }
        this.defaultMeta!!.defaultMeta()
    }

    /**
     * Returns the routing model constructed by this builder.
     */
    public fun getRoutingModel(): RoutingModel {
        return RoutingModel(
            routes = routes(),
            defaultContent = defaultContent,
            defaultMeta = defaultMeta,
            notFoundView = view,
            notFoundAction = action,
            notFoundMeta = meta,
        )
    }
}

/**
 * An implementation of the [PlainRoutingBuilder] interface for plain routes.
 * Provides methods to define views and actions for routes without parameters.
 */
public open class PlainRoutingBuilderImpl : BaseRoutingBuilderImpl(), PlainRoutingBuilder {
    public var view: (@Composable @RoutingDsl IComponent.() -> Unit)? = null
    public var action: (suspend () -> Unit)? = null

    override fun view(view: @Composable @RoutingDsl (IComponent.() -> Unit)) {
        this.view = view
    }

    override fun action(action: suspend () -> Unit) {
        this.action = action
    }
}

/**
 * An implementation of the [StringRoutingBuilder] interface for string routes.
 * Provides methods to define views and actions for routes with string parameters.
 */
public class StringRoutingBuilderImpl : BaseRoutingBuilderImpl(), StringRoutingBuilder {
    public var view: (@Composable @RoutingDsl IComponent.(String) -> Unit)? = null
    public var action: (suspend (String) -> Unit)? = null

    override fun view(view: @Composable @RoutingDsl (IComponent.(String) -> Unit)) {
        this.view = view
    }

    override fun action(action: suspend (String) -> Unit) {
        this.action = action
    }
}

/**
 * An implementation of the [IntRoutingBuilder] interface for numeric routes.
 * Provides methods to define views and actions for routes with numeric parameters.
 */
public class IntRoutingBuilderImpl : BaseRoutingBuilderImpl(), IntRoutingBuilder {
    public var view: (@Composable @RoutingDsl IComponent.(Int) -> Unit)? = null
    public var action: (suspend (Int) -> Unit)? = null

    override fun view(view: @Composable @RoutingDsl (IComponent.(Int) -> Unit)) {
        this.view = view
    }

    override fun action(action: suspend (Int) -> Unit) {
        this.action = action
    }
}
