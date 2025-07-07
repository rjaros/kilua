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
import androidx.compose.runtime.CompositionLocalProvider
import app.softwork.routingcompose.HashRouter
import app.softwork.routingcompose.invoke
import dev.kilua.core.IComponent

/**
 * Creates a [HashRouter] with the specified initial route.
 *
 * @param initRoute the initial route to navigate to (default is "/")
 * @param routing the routing configuration block
 */
@Composable
public fun IComponent.hashRouter(
    initRoute: String = "/",
    routing: @RoutingDsl RoutingBuilder.(RoutingContext) -> Unit
) {
    val routingBuilder = RoutingBuilderImpl()
    val routingContext = RoutingContextImpl()
    routingBuilder.routing(routingContext)
    val routingModel = routingBuilder.getRoutingModel()
    CompositionLocalProvider(RoutingModelCompositionLocal provides routingModel) {
        HashRouter(initRoute) {
            routingContext.apply {
                path = this@HashRouter.path
                parameters = this@HashRouter.parameters
            }
            routingModel.apply(this@hashRouter, this, routingContext, routingModel.defaultMeta)
        }
        globalRouter = HashRouter
    }
    RoutingModel.globalRoutingModel = routingModel
}
