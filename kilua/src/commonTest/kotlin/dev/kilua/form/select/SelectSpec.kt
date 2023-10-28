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

package dev.kilua.form.select

import dev.kilua.DomSpec
import dev.kilua.compose.root
import dev.kilua.utils.listOfPairs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SelectSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                select(listOfPairs("A", "B"), name = "test", emptyOption = true, placeholder = "A placeholder")
            }
            assertEqualsHtml(
                """<select name="test" required=""><option value="" label="A placeholder" selected="" disabled="" hidden=""></option><option value="$SELECT_EMPTY_VALUE" label=""></option><option value="A" label="A"></option><option value="B" label="B"></option></select>""",
                root.element.innerHTML,
                "Should render select element to DOM"
            )
        }
    }

    @Test
    fun selectedIndexAndLabel() {
        run {
            lateinit var select: Select
            root("test") {
                select = select(listOfPairs("A", "B"), value = "B", name = "test", emptyOption = true, placeholder = "A placeholder")
            }
            assertEquals(1, select.selectedIndex, "Should initialize with correct selected index")
            assertEquals("B", select.selectedLabel, "Should initialize with correct selected label")
            assertEquals("B", select.value, "Should initialize with correct value")
            select.selectedIndex = 0
            assertEquals(0, select.selectedIndex, "Should set correct selected index")
            assertEquals("A", select.selectedLabel, "Should set correct selected label")
            assertEquals("A", select.value, "Should set correct value")
            select.selectedIndex = -1
            assertEquals(-1, select.selectedIndex, "Should clear selected index")
            assertNull(select.selectedLabel, "Should clear selected label")
            assertNull(select.value, "Should clear the value")
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                select(listOfPairs("A", "B"), name = "test", emptyOption = true, placeholder = "A placeholder")
            }
            assertEqualsHtml(
                """<select name="test" required=""><option value="" label="A placeholder" selected="" disabled="" hidden=""></option><option value="$SELECT_EMPTY_VALUE" label=""></option><option value="A" label="A"></option><option value="B" label="B"></option></select>""",
                root.innerHTML,
                "Should render select element to a String"
            )
        }
    }
}
