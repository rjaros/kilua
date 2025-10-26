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
import kotlinx.datetime.LocalDate
import web.html.asStringOrNull
import kotlin.test.Test

class RichDateSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val date = LocalDate(2023, 10, 15)
            val root = root("test") {
                richDate(
                    date,
                    name = "date"
                )
            }
            assertEqualsHtml(
                """<div class="input-group kilua-td" id="kilua_tempus_dominus_rd_0" data-td-target-input="nearest" data-td-target-toggle="nearest">
<input class="form-control" type="text" name="date" data-td-target="#kilua_tempus_dominus_rd_0">
<span class="input-group-text form-control" data-td-target="#kilua_tempus_dominus_rd_0" data-td-toggle="datetimepicker" tabindex="0" style="max-width: 43px;">
<i class="fas fa-calendar-alt">
</i>
</span>
</div>""".replace(Regex("kilua_tempus_dominus_rd_[0-9]*"), "kilua_tempus_dominus_rd_0"),
                root.element.innerHTML.asStringOrNull()
                    ?.replace(Regex("kilua_tempus_dominus_rd_[0-9]*"), "kilua_tempus_dominus_rd_0"),
                "Should render Tempus Dominus date component to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                val date = LocalDate(2023, 10, 15)
                richDate(
                    date,
                    name = "date"
                )
            }
            assertEqualsHtml(
                """<div class="input-group kilua-td" id="kilua_tempus_dominus_rd_0" data-td-target-input="nearest" data-td-target-toggle="nearest">
<input class="form-control" type="text" name="date" data-td-target="#kilua_tempus_dominus_rd_0">
<span class="input-group-text form-control" data-td-target="#kilua_tempus_dominus_rd_0" data-td-toggle="datetimepicker" tabindex="0" style="max-width: 43px;">
<i class="fas fa-calendar-alt">
</i>
</span>
</div>""".replace(Regex("kilua_tempus_dominus_rd_[0-9]*"), "kilua_tempus_dominus_rd_0"),
                root.innerHTML
                    .replace(Regex("kilua_tempus_dominus_rd_[0-9]*"), "kilua_tempus_dominus_rd_0"),
                "Should render Tempus Dominus date component to a String"
            )
        }
    }
}
