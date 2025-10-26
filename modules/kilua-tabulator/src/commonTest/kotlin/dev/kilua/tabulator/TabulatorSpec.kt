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

package dev.kilua.tabulator

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import dev.kilua.compose.root
import dev.kilua.test.DomSpec
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable
import web.html.asStringOrNull
import kotlin.test.Test

@Serializable
data class Person(val name: String, val age: Int, val city: String)

class TabulatorSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailableAsync {
        val root = root("test") {
            val personList = remember {
                mutableStateListOf(
                    Person("John", 30, "New York"),
                    Person("Alice", 25, "Los Angeles"),
                )
            }
            tabulator(
                personList,
                options = TabulatorOptions(
                    height = "300px",
                    layout = Layout.FitColumns,
                    columns = listOf(
                        ColumnDefinition("Name", "name"),
                        ColumnDefinition("Age", "age"),
                        ColumnDefinition("City", "city"),
                    )
                ), types = setOf(TableType.TableBordered, TableType.TableSm)
            )
        }
        delay(100)
        assertEqualsHtml(
            """
<div class="table-bordered table-sm tabulator" role="grid" tabulator-layout="fitColumns" style="height: 300px;">
<div class="tabulator-header" role="rowgroup">
<div class="tabulator-header-contents" role="rowgroup">
<div class="tabulator-headers" role="row" style="height: 0px;">
<div class="tabulator-col tabulator-sortable tabulator-col-sorter-element" role="columnheader" aria-sort="none" tabulator-field="name" style="min-width: 40px; width: 40px; height: 0px;">
<div class="tabulator-col-content">
<div class="tabulator-col-title-holder">
<div class="tabulator-col-title">
Name
</div>
<div class="tabulator-col-sorter">
<div class="tabulator-arrow">
</div>
</div>
</div>
</div>
</div>
<span class="tabulator-col-resize-handle" style="height: 0px;">
</span>
<div class="tabulator-col tabulator-sortable tabulator-col-sorter-element" role="columnheader" aria-sort="none" tabulator-field="age" style="min-width: 40px; width: 40px; height: 0px;">
<div class="tabulator-col-content">
<div class="tabulator-col-title-holder">
<div class="tabulator-col-title">
Age
</div>
<div class="tabulator-col-sorter">
<div class="tabulator-arrow">
</div>
</div>
</div>
</div>
</div>
<span class="tabulator-col-resize-handle" style="height: 0px;">
</span>
<div class="tabulator-col tabulator-sortable tabulator-col-sorter-element" role="columnheader" aria-sort="none" tabulator-field="city" style="min-width: 40px; width: 40px; height: 0px;">
<div class="tabulator-col-content">
<div class="tabulator-col-title-holder">
<div class="tabulator-col-title">
City
</div>
<div class="tabulator-col-sorter">
<div class="tabulator-arrow">
</div>
</div>
</div>
</div>
</div>
<span class="tabulator-col-resize-handle" style="height: 0px;">
</span>
</div>
<br>
<div class="tabulator-frozen-rows-holder" style="min-width: 0px;">
</div>
</div>
</div>
<div class="tabulator-tableholder" tabindex="0" style="height: calc(100% + 0px); max-height: calc(100% + 0px);">
<div class="tabulator-table" role="rowgroup">
</div>
</div>
</div>            """.trimIndent(),
            root.element.innerHTML.asStringOrNull(),
            "Should render a Tabulator component to DOM"
        )

    }


    @Test
    fun renderToString() {
        run {
            val root = root {
                val personList = remember {
                    mutableStateListOf(
                        Person("John", 30, "New York"),
                        Person("Alice", 25, "Los Angeles"),
                    )
                }
                tabulator(
                    personList,
                    options = TabulatorOptions(
                        height = "300px",
                        layout = Layout.FitColumns,
                        columns = listOf(
                            ColumnDefinition("Name", "name"),
                            ColumnDefinition("Age", "age"),
                            ColumnDefinition("City", "city"),
                        )
                    ), types = setOf(TableType.TableBordered, TableType.TableSm)
                )
            }
            assertEqualsHtml(
                """<div class="table-bordered table-sm"></div>""",
                root.innerHTML,
                "Should render a Tabulator component to String"
            )
        }
    }
}
