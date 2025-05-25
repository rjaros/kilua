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

package dev.kilua.compose.ui

import androidx.compose.runtime.Composable
import web.uievents.DragEvent

/**
 * Sets D&D data for the current widget. It also makes it draggable.
 */
@Composable
public fun Modifier.setDragDropData(format: String, data: String): Modifier = dndModifier {
    setDragDropData(format, data)
}

/**
 * Clears D&D data for the current component. It also makes it not draggable.
 */
public fun Modifier.clearDragDropData(): Modifier = dndModifier {
    clearDragDropData()
}

/**
 * Sets the current component as a D&D drop target with helper callback accepting String data.
 */
@Composable
public fun Modifier.setDropTargetData(format: String, callback: (String?) -> Unit): Modifier = dndModifier {
    setDropTargetData(format, callback)
}

/**
 * Sets the current component as a D&D drop target.
 */
@Composable
public fun Modifier.setDropTarget(format: String, callback: (DragEvent) -> Unit): Modifier = dndModifier {
    setDropTarget(format, callback)
}

/**
 * Sets the current component as a D&D drop target.
 */
@Composable
public fun Modifier.setDropTarget(callback: (DragEvent) -> Unit): Modifier = dndModifier {
    setDropTarget(callback)
}

/**
 * Sets the current component as a D&D drop target.
 */
@Composable
public fun Modifier.setDropTarget(formats: Set<String>?, callback: (DragEvent) -> Unit): Modifier = dndModifier {
    setDropTarget(formats, callback)
}

/**
 * Unsets the current component as a D&D drop target.
 */
public fun Modifier.clearDropTarget(): Modifier = dndModifier {
    clearDropTarget()
}
