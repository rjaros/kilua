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

public data class Path(val path: String, val parameters: Parameters?) {
    internal fun newPath(currentPath: String) = Path(path = path.removePrefix("/$currentPath"), parameters)

    /**
     * https://datatracker.ietf.org/doc/html/rfc1808
     */
    internal fun relative(to: String): Path {
        val paths = path.split("/")
        val split = to.split("./")
        val result = split.last().let {
            if (it.isNotEmpty()) "/$it" else it
        }
        val number = split.count() - 1
        return from(
            paths.dropLast(number).joinToString(postfix = result, separator = "/") {
                it
            }
        )
    }

    internal companion object {
        fun from(rawPath: String): Path {
            val pathAndQuery = rawPath.split("?")
            val (path, rawParameters) = when (pathAndQuery.size) {
                1 -> {
                    pathAndQuery.first() to null
                }

                2 -> {
                    pathAndQuery.first() to pathAndQuery.last().let { Parameters.from(it) }
                }

                else -> {
                    error("path contains more than 1 '?' delimiter: $rawPath")
                }
            }
            return Path(path.addPrefix("/"), rawParameters)
        }

        private fun String.addPrefix(prefix: String) = if (startsWith(prefix)) this else "$prefix$this"
    }

    override fun toString(): String = if (parameters == null) {
        path
    } else {
        "$path?$parameters"
    }

    internal val currentPath get() = path.removePrefix("/").takeWhile { it != '/' }
}
