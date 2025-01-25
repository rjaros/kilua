/*
 * Copyright (c) 2025 Robert Jaros
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

package dev.kilua.compose.foundation.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import dev.kilua.compose.ui.Alignment
import dev.kilua.compose.ui.Modifier
import dev.kilua.compose.ui.alignSelf
import dev.kilua.compose.ui.attrsModifier
import dev.kilua.core.IComponent
import dev.kilua.html.AlignItems
import dev.kilua.panel.vPanel

@Immutable
public interface ColumnScope : FlexScope {
    public fun Modifier.align(alignment: Alignment.Horizontal): Modifier = attrsModifier {
        when (alignment) {
            Alignment.Start -> alignSelf(AlignItems.FlexStart)
            Alignment.CenterHorizontally -> alignSelf(AlignItems.Center)
            Alignment.End -> alignSelf(AlignItems.FlexEnd)
        }
    }
}

internal object ColumnScopeInstance : ColumnScope

@Composable
public fun IComponent.Column(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit
) {
    val gap = if (verticalArrangement is SpacedAligned) verticalArrangement.space else null
    vPanel(
        justifyContent = verticalArrangement.justifyContent,
        alignItems = horizontalAlignment.alignItems,
        gap = gap
    ) {
        +modifier
        ColumnScopeInstance.content()
    }
}
