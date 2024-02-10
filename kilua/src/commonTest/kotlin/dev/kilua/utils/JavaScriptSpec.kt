/*
 * Copyright (c) 2023 Robert Jaros
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

package dev.kilua.utils

import dev.kilua.externals.JSON
import dev.kilua.test.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class JavaScriptSpec : SimpleSpec {

    @Test
    fun toJsAny() {
        assertEquals("\"test\"", JSON.stringify("test".toJsAny()))
        assertEquals("45", JSON.stringify(45.toJsAny()))
        assertEquals("45.5", JSON.stringify(45.5.toJsAny()))
        assertEquals("true", JSON.stringify(true.toJsAny()))
        assertEquals(
            """{"string":"value","int":5,"boolean":true,"double":14.5,"obj":{"a":1,"b":2},"array":["string",5,true],"list":["string",5,false],"map":{"m1":"Some value","m2":{"m3":"Some value 3"}}}""",
            JSON.stringify(
                mapOf(
                    "string" to "value",
                    "int" to 5,
                    "boolean" to true,
                    "double" to 14.5,
                    "obj" to jsObjectOf("a" to 1, "b" to 2),
                    "array" to arrayOf("string", 5, true),
                    "list" to listOf("string", 5, false),
                    "map" to mapOf("m1" to "Some value", "m2" to mapOf("m3" to "Some value 3"))
                ).toJsAny()
            )
        )
    }

    @Test
    fun deepMerge() {
        run {
            val target = jsObjectOf(
                "a" to mapOf(
                    "b" to mapOf(
                        "c" to "c",
                        "d" to "d"
                    ),
                    "array" to listOf(1, 2)
                )
            )
            val source = jsObjectOf(
                "a" to mapOf(
                    "b" to mapOf(
                        "e" to "e"
                    ),
                    "array" to listOf(3, 4)
                )
            )
            val result = deepMerge(target, source)
            assertEquals(
                """{"a":{"b":{"c":"c","d":"d","e":"e"},"array":[1,2,3,4]}}""",
                JSON.stringify(result),
                "Should deeply merge two JS objects"
            )
        }
    }

}
