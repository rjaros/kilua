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
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import app.softwork.routingcompose.RouteBuilder
import app.softwork.routingcompose.Router
import dev.kilua.core.IComponent

/**
 * The routing tree model.
 */
public class RoutingModel(
    public val routes: List<Route>? = null,
    internal val defaultContent: (@Composable IComponent.() -> Unit)? = null,
    public val defaultMeta: Meta?,
    internal val notFoundView: (@Composable IComponent.() -> Unit)? = null,
    internal val notFoundAction: (suspend () -> Unit)? = null,
    public val notFoundMeta: Meta?,
) {
    override fun toString(): String {
        return "RoutingModel(routes=$routes, defaultMeta=$defaultMeta, notFoundMeta=$notFoundMeta)"
    }

    /**
     * Returns a map of all static paths in the routing model with their associated metadata.
     */
    public fun pathList(): Map<String, Meta?> {
        val result = mutableMapOf<String, Meta?>()
        if (routes != null) processPaths("", routes, MetaImpl(), result)
        return result
    }

    private fun processPaths(
        prefix: String,
        routes: List<Route>,
        parentMeta: Meta?,
        results: MutableMap<String, Meta?>
    ) {
        routes.forEach { route ->
            if (route is Route.PathRoute) {
                route.paths.forEach { pathSegment ->
                    val segment = prefix + pathSegment
                    val resultMeta = mergeMetadataWithDefault(route.meta, parentMeta, defaultMeta)
                    results.put(segment, resultMeta)
                    if (!route.childRoutes.isNullOrEmpty()) {
                        val metaForChildren = mergeMetadataWithParent(route.meta, parentMeta)
                        processPaths(segment, route.childRoutes, metaForChildren, results)
                    }
                }
            }
        }
    }

    /**
     * Applies the routing model to the given component, route builder, and routing context.
     */
    @Composable
    public fun apply(
        component: IComponent,
        routeBuilder: RouteBuilder,
        routingContext: RoutingContextImpl,
        defaultMeta: Meta?,
    ) {
        defaultContent?.invoke(component)
        if (routes != null) applyRoutes(component, routeBuilder, routes, null, defaultMeta)
        routeBuilder.noMatch {
            routingContext.remainingPath = remainingPath
            if (notFoundMeta is MetaImpl) {
                notFoundMeta.view?.invoke(notFoundMeta)
            }
            notFoundView?.invoke(component)
            RouteEffect {
                Meta.current = mergeMetadataWithDefault(notFoundMeta, null, defaultMeta)
                notFoundAction?.invoke()
            }
        }
    }

    @Composable
    private fun applyRoutes(
        component: IComponent,
        routeBuilder: RouteBuilder,
        routes: List<Route>,
        parentMeta: Meta?,
        defaultMeta: Meta?
    ) {
        routes.forEach { routeModel ->
            when (routeModel) {
                is Route.PathRoute -> {
                    routeBuilder.route(*routeModel.paths.toTypedArray()) {
                        routeModel.context.apply {
                            path = this@route.path
                            parameters = this@route.parameters
                        }
                        if (routeModel.meta is MetaImpl) {
                            routeModel.meta.view?.invoke(routeModel.meta)
                        }
                        if (!routeModel.childRoutes.isNullOrEmpty()) {
                            val metaForChildren = mergeMetadataWithParent(routeModel.meta, parentMeta)
                            applyRoutes(component, this, routeModel.childRoutes, metaForChildren, defaultMeta)
                            noMatch {
                                routeModel.context.remainingPath = remainingPath
                                routeModel.view?.invoke(component)
                                RouteEffect {
                                    Meta.current = mergeMetadataWithDefault(routeModel.meta, parentMeta, defaultMeta)
                                    routeModel.action?.invoke()
                                }
                            }
                        } else {
                            routeModel.view?.invoke(component)
                            RouteEffect {
                                Meta.current = mergeMetadataWithDefault(routeModel.meta, parentMeta, defaultMeta)
                                routeModel.action?.invoke()
                            }
                        }
                    }
                }

                is Route.StringRoute -> {
                    routeBuilder.string { stringValue ->
                        routeModel.context.apply {
                            path = this@string.path
                            parameters = this@string.parameters
                            sValue = stringValue
                        }
                        if (routeModel.meta is MetaImpl) {
                            routeModel.meta.view?.invoke(routeModel.meta)
                        }
                        if (!routeModel.childRoutes.isNullOrEmpty()) {
                            val metaForChildren = mergeMetadataWithParent(routeModel.meta, parentMeta)
                            applyRoutes(component, this, routeModel.childRoutes, metaForChildren, defaultMeta)
                            noMatch {
                                routeModel.context.remainingPath = remainingPath
                                routeModel.view?.invoke(component, stringValue)
                                RouteEffect {
                                    Meta.current = mergeMetadataWithDefault(routeModel.meta, parentMeta, defaultMeta)
                                    routeModel.action?.invoke(stringValue)
                                }
                            }
                        } else {
                            routeModel.view?.invoke(component, stringValue)
                            RouteEffect {
                                Meta.current = mergeMetadataWithDefault(routeModel.meta, parentMeta, defaultMeta)
                                routeModel.action?.invoke(stringValue)
                            }
                        }
                    }
                }

                is Route.IntRoute -> {
                    routeBuilder.int { intValue ->
                        routeModel.context.apply {
                            path = this@int.path
                            parameters = this@int.parameters
                            iValue = intValue
                        }
                        if (routeModel.meta is MetaImpl) {
                            routeModel.meta.view?.invoke(routeModel.meta)
                        }
                        if (!routeModel.childRoutes.isNullOrEmpty()) {
                            val metaForChildren = mergeMetadataWithParent(routeModel.meta, parentMeta)
                            applyRoutes(component, this, routeModel.childRoutes, metaForChildren, defaultMeta)
                            noMatch {
                                routeModel.context.remainingPath = remainingPath
                                routeModel.view?.invoke(component, intValue)
                                RouteEffect {
                                    Meta.current = mergeMetadataWithDefault(routeModel.meta, parentMeta, defaultMeta)
                                    routeModel.action?.invoke(intValue)
                                }
                            }
                        } else {
                            routeModel.view?.invoke(component, intValue)
                            RouteEffect {
                                Meta.current = mergeMetadataWithDefault(routeModel.meta, parentMeta, defaultMeta)
                                routeModel.action?.invoke(intValue)
                            }
                        }
                    }
                }
            }
        }
    }

    public companion object {
        /**
         * Provide the routing model instance through a CompositionLocal so deeper level
         * Composables in the composition can have access to the current routing model.
         *
         * To use this composition, you need to invoke any [Router] implementation first.
         */
        public val current: RoutingModel
            @Composable
            get() = RoutingModelCompositionLocal.current

        /**
         * Internal global routing model instance for use outside of composition.
         * Do not use directly. Use RoutingModel.global instead.
         */
        public var globalRoutingModel: RoutingModel? = null

        /**
         * Provides the global routing modal instance for use outside of composition.
         */
        public val global: RoutingModel
            get() = globalRoutingModel ?: error("Routing model not defined")

    }
}

/**
 * CompositionLocal for providing the routing model instance.
 */
public val RoutingModelCompositionLocal: ProvidableCompositionLocal<RoutingModel> =
    compositionLocalOf { error("Routing model not defined, cannot provide through RoutingModelCompositionLocal.") }
