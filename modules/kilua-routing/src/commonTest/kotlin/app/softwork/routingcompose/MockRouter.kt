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

import androidx.compose.runtime.*

class MockRouter : Router {
    override val currentPath: Path
        get() = Path.from(currentState.value!!)

    private val currentState = mutableStateOf<String?>(null)

    @Composable
    override fun getPath(initPath: String) =
        derivedStateOf { currentState.value ?: initPath }

    override fun navigate(to: String, hide: Boolean, replace: Boolean) {
        currentState.value = to
    }
}

@Composable
operator fun MockRouter.invoke(initPath: String, routeBuilder: @Composable RouteBuilder.() -> Unit) =
    route(initPath, routeBuilder)
