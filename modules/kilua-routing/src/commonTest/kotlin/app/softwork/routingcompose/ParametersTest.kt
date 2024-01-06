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

class ParametersTest {
    @Test
    fun simpleTest() {
        val parameters = Parameters.from("key=value")
        assertEquals("key=value", parameters.raw)
        assertEquals(mapOf("key" to listOf("value")), parameters.map)

        assertEquals(Parameters.from(mapOf("key" to "value")), parameters)
    }

    @Test
    fun listTest() {
        val parameters = Parameters.from("key=value&key=value1")
        assertEquals("key=value&key=value1", parameters.raw)
        assertEquals(mapOf("key" to listOf("value", "value1")), parameters.map)
    }

    @Test
    fun simpleMultipleTest() {
        val parameters = Parameters.from("key=value&key2=value2")
        assertEquals("key=value&key2=value2", parameters.raw)
        assertEquals(mapOf("key" to listOf("value"), "key2" to listOf("value2")), parameters.map)
    }

    @Test
    fun listMultipleTest() {
        val parameters = Parameters.from("key=value&key2=value2&key=value1&key2=value2")
        assertEquals("key=value&key2=value2&key=value1&key2=value2", parameters.raw)
        assertEquals(mapOf("key" to listOf("value", "value1"), "key2" to listOf("value2", "value2")), parameters.map)
    }

    @Test
    fun emptyTest() {
        val parameters = Parameters.from("")
        assertEquals("", parameters.raw)
        assertEquals(emptyMap(), parameters.map)
    }

    @Test
    fun listMultipleTestEncoding() {
        val parameters = Parameters.from("key=va%26lue&key2=val%20ue2&key=val+ue1;key2=val%3Due2")
        assertEquals("key=va%26lue&key2=val%20ue2&key=val+ue1;key2=val%3Due2", parameters.raw)
        assertEquals(
            mapOf(
                "key" to listOf("va&lue", "val ue1"),
                "key2" to listOf("val ue2", "val=ue2")
            ),
            parameters.map
        )
    }
}
