/*
 * Copyright (c) 2023-present Robert Jaros
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
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlin.test.Test
import kotlin.test.assertEquals

class DateSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val day = LocalDate(2023, 10, 15)
            val root = root("test") {
                date(day, day.minus(1, DateTimeUnit.DAY), day.plus(1, DateTimeUnit.DAY), name ="date")
            }
            assertEquals(
                normalizeHtml("""<input type="date" name="date" min="2023-10-14" max="2023-10-16" step="1">"""),
                normalizeHtml(root.element?.innerHTML),
                "Should render date input element to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                val day = LocalDate(2023, 10, 15)
                date(day, day.minus(1, DateTimeUnit.DAY), day.plus(1, DateTimeUnit.DAY), name ="date")
            }
            assertEquals(
                normalizeHtml("""<div><input type="date" name="date" min="2023-10-14" max="2023-10-16" step="1"></input></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render date input element to a String"
            )
        }
    }
}
