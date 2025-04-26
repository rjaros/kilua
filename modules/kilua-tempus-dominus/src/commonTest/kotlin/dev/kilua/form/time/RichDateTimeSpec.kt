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

import dev.kilua.compose.root
import dev.kilua.test.DomSpec
import dev.kilua.test.normalizeHtml
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class RichDateTimeSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val date = LocalDateTime(2023, 10, 15, 12, 30, 0)
            val root = root("test") {
                richDateTime(
                    date,
                    name = "date"
                )
            }
            assertEqualsHtml(
                """<div class="input-group kilua-td" id="kilua_tempus_dominus_rdt_0" data-td-target-input="nearest" data-td-target-toggle="nearest">
<input class="form-control" type="text" name="date" data-td-target="#kilua_tempus_dominus_rdt_0">
<span class="input-group-text form-control" data-td-target="#kilua_tempus_dominus_rdt_0" data-td-toggle="datetimepicker" tabindex="0" style="max-width: 43px;">
<i class="fas fa-calendar-alt">
</i>
</span>
</div>""".replace(Regex("kilua_tempus_dominus_rdt_[0-9]*"), "kilua_tempus_dominus_rdt_0"),
                root.element.innerHTML
                    .replace(Regex("kilua_tempus_dominus_rdt_[0-9]*"), "kilua_tempus_dominus_rdt_0"),
                "Should render Tempus Dominus date and time component to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                val date = LocalDateTime(2023, 10, 15, 12, 30, 0)
                richDateTime(
                    date,
                    name = "date"
                )
            }
            assertEqualsHtml(
                """<div class="input-group kilua-td" id="kilua_tempus_dominus_rdt_0" data-td-target-input="nearest" data-td-target-toggle="nearest">
<input class="form-control" type="text" name="date" data-td-target="#kilua_tempus_dominus_rdt_0">
<span class="input-group-text form-control" data-td-target="#kilua_tempus_dominus_rdt_0" data-td-toggle="datetimepicker" tabindex="0" style="max-width: 43px;">
<i class="fas fa-calendar-alt">
</i>
</span>
</div>""".replace(Regex("kilua_tempus_dominus_rdt_[0-9]*"), "kilua_tempus_dominus_rdt_0"),
                root.innerHTML
                    .replace(Regex("kilua_tempus_dominus_rdt_[0-9]*"), "kilua_tempus_dominus_rdt_0"),
                "Should render Tempus Dominus date and time component to a String"
            )
        }
    }
}
