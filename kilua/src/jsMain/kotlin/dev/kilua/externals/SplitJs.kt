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
import dev.kilua.utils.obj
import org.w3c.dom.HTMLElement

internal actual fun splitJs(elements: List<HTMLElement>, options: SplitJsOptions): SplitJsInstance {
    val splitJsDirection = if (options.direction == Dir.Horizontal) "vertical" else "horizontal"
    return splitJsExt(elements.toTypedArray(), obj<SplitJsOptionsExt> {
        sizes = options.sizes.toTypedArray()
        direction = splitJsDirection
        gutterSize = options.gutterSize
        if (options.gutterAlign != null) gutterAlign = options.gutterAlign.toString()
        minSize = options.minSize
        if (options.maxSize != null) maxSize = options.maxSize
        if (options.expandToMin != null) expandToMin = options.expandToMin
        snapOffset = options.snapOffset
        if (dragInterval != null) dragInterval = options.dragInterval
        gutter = options.gutter
        onDrag = { sizes, index -> options.onDrag(sizes.toList().map { it.toDouble() }, index) }
        onDragStart = { sizes, index -> options.onDragStart(sizes.toList().map { it.toDouble() }, index) }
        onDragEnd = { sizes, index -> options.onDragEnd(sizes.toList().map { it.toDouble() }, index) }
    })
}
