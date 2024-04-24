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

package dev.kilua.form.time

import dev.kilua.test.DomSpec
import dev.kilua.compose.root
import dev.kilua.test.normalizeHtml
import kotlinx.datetime.LocalTime
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val localTime = LocalTime(12, 30, 0)
            val root = root("test") {
                time(
                    localTime,
                    LocalTime(12, 0, 0),
                    LocalTime(12, 50, 0),
                    name = "time"
                ) {
                    defaultValue(localTime)
                }
            }
            assertEquals(
                normalizeHtml("""<input type="time" name="time" min="12:00" max="12:50" step="60" value="12:30">"""),
                normalizeHtml(root.element.innerHTML),
                "Should render time input element to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                val localTime = LocalTime(12, 30, 0)
                time(
                    localTime,
                    LocalTime(12, 0, 0),
                    LocalTime(12, 50, 0),
                    name = "time"
                ) {
                    defaultValue(localTime)
                }
            }
            assertEquals(
                normalizeHtml("""<div><input type="time" name="time" min="12:00" max="12:50" step="60" value="12:30"></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render time input element to a String"
            )
        }
    }

    @Test
    fun stepUpDown() {
        run {
            lateinit var time: Time
            val localTime = LocalTime(12, 30)
            root("test") {
                time = time(
                    localTime,
                    LocalTime(12, 0),
                    LocalTime(12, 31),
                    name = "time"
                )
            }
            repeat(2) {
                time.stepUp()
            }
            assertEquals(LocalTime(12, 31), time.value)
            time.stepDown()
            assertEquals(LocalTime(12, 30), time.value)
        }
    }
}
