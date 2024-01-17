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

import dev.kilua.compose.root
import dev.kilua.test.DomSpec
import dev.kilua.utils.listOfPairs
import kotlin.test.Test

class TomSelectSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                tomSelect(
                    options = listOfPairs("A", "B"),
                    "A",
                    name = "select",
                    multiple = true,
                    placeholder = "A placeholder"
                )
            }
            assertEqualsHtml(
                """<select multiple="multiple" name="select" placeholder="A placeholder" id="tomselect-1" tabindex="-1" class="tomselected ts-hidden-accessible">
<option value="A">
A
</option>
</select>
<div class="ts-wrapper multi plugin-change_listener has-items">
<div class="ts-control">
<div data-value="A" class="item" data-ts-item="">
A
</div>
<input type="text" autocomplete="off" size="1" tabindex="0" role="combobox" aria-haspopup="listbox" aria-expanded="false" aria-controls="tomselect-1-ts-dropdown" id="tomselect-1-ts-control" placeholder="A placeholder" aria-activedescendant="tomselect-1-opt-2">
</div>
<div class="ts-dropdown multi plugin-change_listener" style="display: none;">
<div role="listbox" tabindex="-1" class="ts-dropdown-content" id="tomselect-1-ts-dropdown">
<div data-selectable="" data-value="B" class="option active" role="option" id="tomselect-1-opt-2" aria-selected="true">
B
</div>
</div>
</div>
</div>""",
                root.element.innerHTML,
                "Should render Tom Select component to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                tomSelect(
                    options = listOfPairs("A", "B"),
                    "A",
                    name = "select",
                    multiple = true,
                    placeholder = "A placeholder"
                )
            }
            assertEqualsHtml(
                """<select name="select" multiple></select>""",
                root.innerHTML,
                "Should render Tom Select component to a String"
            )
        }
    }
}
