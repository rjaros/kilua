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

package dev.kilua.dropdown

import androidx.compose.runtime.Composable
import dev.kilua.core.ComponentBase
import dev.kilua.html.ButtonSize
import dev.kilua.html.ButtonStyle
import dev.kilua.html.Div
import dev.kilua.html.Ul
import dev.kilua.html.div
import dev.kilua.utils.rem

/**
 * Dropdown directions.
 */
public enum class Direction(public val className: String) {
    Dropdown("dropdown"),
    DropdownCenter("dropdown-center"),
    Dropup("btn-group dropup"),
    DropupCenter("dropup-center dropup"),
    DropStart("btn-group dropstart"),
    DropEnd("btn-group dropend")
}

@Composable
public fun ComponentBase.dropDown(
    label: String? = null,
    icon: String? = null,
    style: ButtonStyle = ButtonStyle.BtnPrimary,
    size: ButtonSize? = null,
    disabled: Boolean? = null,
    autoClose: AutoClose = AutoClose.True,
    arrowVisible: Boolean = true,
    innerDropDown: Boolean = false,
    className: String? = null,
    direction: Direction = Direction.Dropdown,
    content: @Composable Ul.() -> Unit = {}
): Div {
    return div(direction.className % className) {
        dropDownButton(label, icon, style, size, disabled, autoClose, arrowVisible, innerDropDown)
        dropDownMenu(content = content)
    }
}
