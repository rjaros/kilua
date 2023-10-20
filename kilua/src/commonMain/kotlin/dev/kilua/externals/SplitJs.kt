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

package dev.kilua.externals

import dev.kilua.panel.Dir
import dev.kilua.panel.GutterAlign
import org.w3c.dom.HTMLElement

/**
 * Split.js native instance.
 */
public external class SplitJsInstance {
    public fun destroy()
}

/**
 * A common interface to the Split.js library for both JS and Wasm targets.
 * Implemented with expect/actual function and target specific external declarations.
 */
internal class SplitJsOptions(
    val sizes: List<Int>,
    val direction: Dir = Dir.Vertical,
    val gutterSize: Int = 0,
    val gutterAlign: GutterAlign? = null,
    val minSize: Int = 0,
    val maxSize: Int? = null,
    val expandToMin: Boolean? = null,
    val snapOffset: Int = 0,
    val dragInterval: Int? = null,
    val gutter: (index: Int, direction: String) -> HTMLElement,
    val onDrag: (sizes: List<Number>, index: Int) -> Unit,
    val onDragStart: (sizes: List<Number>, index: Int) -> Unit,
    val onDragEnd: (sizes: List<Number>, index: Int) -> Unit,
)

/**
 * Create a Split.js instance.
 */
internal expect fun splitJs(elements: List<HTMLElement>, options: SplitJsOptions): SplitJsInstance
