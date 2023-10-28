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

import dev.kilua.DomSpec
import dev.kilua.compose.root
import dev.kilua.normalizeHtml
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTimeSpec : DomSpec {


    @Test
    fun render() {
        runWhenDomAvailable {
            val date = LocalDateTime(2023, 10, 15, 12, 30, 0)
            val root = root("test") {
                dateTime(
                    date,
                    date.date.minus(1, DateTimeUnit.DAY).atTime(0, 0, 0),
                    date.date.plus(1, DateTimeUnit.DAY).atTime(0, 0, 0),
                    name = "date"
                ) {
                    defaultValue = date
                }
            }
            assertEquals(
                normalizeHtml("""<input type="datetime-local" name="date" min="2023-10-14T00:00" max="2023-10-16T00:00" step="60" value="2023-10-15T12:30">"""),
                normalizeHtml(root.element.innerHTML),
                "Should render date and time input element to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                val date = LocalDateTime(2023, 10, 15, 12, 30, 0)
                dateTime(
                    date,
                    date.date.minus(1, DateTimeUnit.DAY).atTime(0, 0, 0),
                    date.date.plus(1, DateTimeUnit.DAY).atTime(0, 0, 0),
                    name = "date"
                ) {
                    defaultValue = date
                }
            }
            assertEquals(
                normalizeHtml("""<div><input type="datetime-local" name="date" min="2023-10-14T00:00" max="2023-10-16T00:00" step="60" value="2023-10-15T12:30"></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render date and time input element to a String"
            )
        }
    }

    @Test
    fun stepUpDown() {
        run {
            lateinit var dateTime: DateTime
            val date = LocalDateTime(2023, 10, 15, 12, 30)
            root("test") {
                dateTime = dateTime(
                    date,
                    LocalDateTime(2023, 10, 15, 12, 0),
                    LocalDateTime(2023, 10, 15, 12, 31),
                    name = "date"
                )
            }
            repeat(2) {
                dateTime.stepUp()
            }
            assertEquals(LocalDateTime(2023, 10, 15, 12, 31), dateTime.value)
            dateTime.stepDown()
            assertEquals(LocalDateTime(2023, 10, 15, 12, 30), dateTime.value)
        }
    }
}
