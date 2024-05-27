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

package dev.kilua.panel

import androidx.compose.runtime.Composable
import dev.kilua.core.IComponent
import dev.kilua.html.AlignItems
import dev.kilua.html.CssSize
import dev.kilua.html.Div
import dev.kilua.html.FlexDirection
import dev.kilua.html.FlexWrap
import dev.kilua.html.IDiv
import dev.kilua.html.JustifyContent

/**
 * Creates a container with a vertical layout, returning a reference.
 *
 * @param flexWrap the optional flex wrap
 * @param justifyContent the optional flexbox content justification
 * @param alignItems the optional flexbox items alignment
 * @param gap the optional gap between rows
 * @param columnGap the optional gap between columns
 * @param className the optional CSS class name
 * @param id the ID attribute of the container
 * @param content the content of the component
 * @return the created [dev.kilua.html.Div] component
 */
@Composable
public fun IComponent.vPanelRef(
    flexWrap: FlexWrap? = null,
    justifyContent: JustifyContent? = null,
    alignItems: AlignItems? = null,
    gap: CssSize? = null,
    columnGap: CssSize? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IDiv.() -> Unit,
): Div {
    return flexPanelRef(
        FlexDirection.Column,
        flexWrap,
        justifyContent,
        alignItems,
        null,
        gap,
        columnGap,
        className,
        id,
        content
    )
}

/**
 * Creates a container with a vertical layout.
 *
 * @param flexWrap the optional flex wrap
 * @param justifyContent the optional flexbox content justification
 * @param alignItems the optional flexbox items alignment
 * @param gap the optional gap between rows
 * @param columnGap the optional gap between columns
 * @param className the optional CSS class name
 * @param id the ID attribute of the container
 * @param content the content of the component
 */
@Composable
public fun IComponent.vPanel(
    flexWrap: FlexWrap? = null,
    justifyContent: JustifyContent? = null,
    alignItems: AlignItems? = null,
    gap: CssSize? = null,
    columnGap: CssSize? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IDiv.() -> Unit,
) {
    flexPanel(
        FlexDirection.Column,
        flexWrap,
        justifyContent,
        alignItems,
        null,
        gap,
        columnGap,
        className,
        id,
        content
    )
}
