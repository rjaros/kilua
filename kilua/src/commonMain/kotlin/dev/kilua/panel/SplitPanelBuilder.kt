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
import dev.kilua.html.Div

/**
 * Builder for SplitPanel DSL style content declaration.
 */
public class SplitPanelBuilder {
    internal var self: @Composable (SplitPanel.() -> Unit)? = null
    internal var first: @Composable (Div.() -> Unit)? = null
    internal var second: @Composable (Div.() -> Unit)? = null

    /**
     * Configure SplitPanel instance.
     */
    public fun self(content: @Composable SplitPanel.() -> Unit) {
        self = content
    }

    /**
     * Configure left side of the SplitPanel.
     */
    public fun left(content: @Composable Div.() -> Unit) {
        first = content
    }

    /**
     * Configure top side of the SplitPanel.
     */
    public fun top(content: @Composable Div.() -> Unit) {
        first = content
    }

    /**
     * Configure right side of the SplitPanel.
     */
    public fun right(content: @Composable Div.() -> Unit) {
        second = content
    }

    /**
     * Configure bottom side of the SplitPanel.
     */
    public fun bottom(content: @Composable Div.() -> Unit) {
        second = content
    }
}
