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

internal data class DelegateRouter(val basePath: String, val router: Router) : Router by router {
    override fun navigate(to: String, hide: Boolean, replace: Boolean) {
        when {
            to.startsWith("/") -> {
                router.navigate(to, hide, replace)
            }

            basePath == "/" -> {
                router.navigate("/$to", hide, replace)
            }

            to.startsWith("./") -> {
                val newPath = router.currentPath().relative(to)
                router.navigate(newPath.path)
            }

            else -> {
                router.navigate("$basePath/$to", hide, replace)
            }
        }
    }
}
