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
import dev.kilua.core.ComponentBase
import dev.kilua.html.AlignContent
import dev.kilua.html.AlignItems
import dev.kilua.html.CssSize
import dev.kilua.html.Display
import dev.kilua.html.Div
import dev.kilua.html.GridAutoFlow
import dev.kilua.html.JustifyContent
import dev.kilua.html.JustifyItems
import dev.kilua.html.div

/**
 * Creates a CSS grid container.
 *
 * @param gridAutoColumns the optional grid auto columns
 * @param gridAutoRows the optional grid auto rows
 * @param gridAutoFlow the optional grid auto flow
 * @param gridTemplateColumns the optional grid template columns
 * @param gridTemplateRows the optional grid template rows
 * @param gridTemplateAreas the optional grid template areas
 * @param justifyItems the optional grid justify items
 * @param justifyContent the optional grid justify content
 * @param alignItems the optional grid align items
 * @param alignContent the optional grid align content
 * @param rowGap the optional gap between rows
 * @param columnGap the optional gap between columns
 * @param className the optional CSS class name
 * @param content the content of the component
 * @return the created [dev.kilua.html.Div] component
 */
@Composable
public fun ComponentBase.gridPanel(
    gridAutoColumns: String? = null,
    gridAutoRows: String? = null,
    gridAutoFlow: GridAutoFlow? = null,
    gridTemplateColumns: String? = null,
    gridTemplateRows: String? = null,
    gridTemplateAreas: List<String>? = null,
    justifyItems: JustifyItems? = null,
    justifyContent: JustifyContent? = null,
    alignItems: AlignItems? = null,
    alignContent: AlignContent? = null,
    rowGap: CssSize? = null,
    columnGap: CssSize? = null,
    className: String? = null,
    content: @Composable ComponentBase.() -> Unit,
): Div {
    return div(className) {
        this.display = Display.Grid
        this.gridAutoColumns = gridAutoColumns
        this.gridAutoRows = gridAutoRows
        this.gridAutoFlow = gridAutoFlow
        this.gridTemplateColumns = gridTemplateColumns
        this.gridTemplateRows = gridTemplateRows
        this.gridTemplateAreas = gridTemplateAreas
        this.justifyItems = justifyItems
        this.justifyContent = justifyContent
        this.alignItems = alignItems
        this.alignContent = alignContent
        this.rowGap = rowGap
        this.columnGap = columnGap
        content()
    }
}
