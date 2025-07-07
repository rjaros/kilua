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
import app.softwork.routingcompose.BrowserRouter
import app.softwork.routingcompose.invoke
import dev.kilua.core.IComponent

/**
 * Creates a [BrowserRouter] with the specified initial route and context path.
 *
 * @param initRoute the initial route to navigate to (default is "/")
 * @param contextPath the context path to prepend to the initial route
 * @param routing the routing configuration block
 */
@Composable
public fun IComponent.browserRouter(
    initRoute: String = "/",
    contextPath: String = "",
    routing: @RoutingDsl RoutingBuilder.(RoutingContext) -> Unit
) {
    val routingBuilder = RoutingBuilderImpl()
    val routingContext = RoutingContextImpl()
    routingBuilder.routing(routingContext)
    val routingModel = routingBuilder.getRoutingModel()
    CompositionLocalProvider(RoutingModelCompositionLocal provides routingModel) {
        BrowserRouter("$contextPath$initRoute") {
            if (contextPath.isNotEmpty()) {
                route("$contextPath$initRoute") {
                    routingContext.apply {
                        path = this@route.path
                        parameters = this@route.parameters
                    }
                    routingModel.apply(this@browserRouter, this, routingContext, routingModel.defaultMeta)
                }
            } else {
                routingContext.apply {
                    path = this@BrowserRouter.path
                    parameters = this@BrowserRouter.parameters
                }
                routingModel.apply(this@browserRouter, this, routingContext, routingModel.defaultMeta)
            }
        }
        globalRouter = BrowserRouter
    }
    RoutingModel.globalRoutingModel = routingModel
}
