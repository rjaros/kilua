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
import dev.kilua.core.IComponent
import dev.kilua.html.IUl
import dev.kilua.html.Ul
import dev.kilua.html.ul
import dev.kilua.utils.rem
import dev.kilua.utils.toKebabCase

/**
 * Dropdown menu end alignment.
 */
public enum class EndAlignment {
    DropdownMenuEnd,
    DropdownMenuSmEnd,
    DropdownMenuMdEnd,
    DropdownMenuLgEnd,
    DropdownMenuXlEnd,
    DropdownMenuXxlEnd;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Dropdown menu start alignment.
 */
public enum class StartAlignment {
    DropdownMenuSmStart,
    DropdownMenuMdStart,
    DropdownMenuLgStart,
    DropdownMenuXlStart,
    DropdownMenuXllStart;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

@Composable
public fun IComponent.dropDownMenu(
    endAlignment: EndAlignment? = null,
    startAlignment: StartAlignment? = null,
    className: String? = null,
    content: @Composable IUl.() -> Unit = {}
): Ul {
    return ul("dropdown-menu" % endAlignment?.value % startAlignment?.value % className, content)
}
