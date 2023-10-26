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

import kotlin.test.*

class PathTest {
    @Test
    fun testingPathConverter() {
        val prefixPath = "/a"
        assertEquals(Path("/a", null), Path.from(prefixPath))
        assertEquals(Path("/a", null), Path.from("a"))
    }

    @Test
    fun relative() {
        val base = Path.from("/a/b/c/d")
        assertEquals("/a/b/c", base.relative("./").path)
        assertEquals("/a/b", base.relative("././").path)
        assertEquals("/a/b/g", base.relative("././g").path)

        assertEquals("/a/b/g?foo=bar", base.relative("././g?foo=bar").toString())
    }
}
