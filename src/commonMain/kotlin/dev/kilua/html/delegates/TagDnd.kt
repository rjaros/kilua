/*
 * Copyright (c) 2023-present Robert Jaros
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

package dev.kilua.html.delegates

import androidx.compose.runtime.Composable
import dev.kilua.html.Tag
import org.w3c.dom.DragEvent
import org.w3c.dom.HTMLElement

public interface TagDnd<E : HTMLElement> {
    /**
     * Sets D&D data for the current widget. It also makes it draggable.
     * @param format D&D data format
     * @param data D&D data transferred to a drop target
     */
    @Composable
    public fun setDragDropData(format: String, data: String)

    /**
     * Clears D&D data for the current component. It also makes it not draggable.
     */
    public fun clearDragDropData()

    /**
     * Sets the current component as a D&D drop target with helper callback accepting String data.
     * @param format accepted D&D data format
     * @param callback a callback function accepting String data called after any drop event
     */
    @Composable
    public fun setDropTargetData(format: String, callback: (String?) -> Unit)

    /**
     * Sets the current component as a D&D drop target.
     * @param format accepted D&D data format
     * @param callback a callback function accepting event object called after any drop event
     */
    @Composable
    public fun setDropTarget(format: String, callback: (DragEvent) -> Unit)

    /**
     * Sets the current component as a D&D drop target.
     * @param callback a callback function accepting event object called after any drop event
     */
    @Composable
    public fun setDropTarget(callback: (DragEvent) -> Unit)

    /**
     * Sets the current component as a D&D drop target.
     * @param formats a set of accepted D&D data formats
     * @param callback a callback function accepting event object called after any drop event
     */
    @Composable
    public fun setDropTarget(formats: Set<String>?, callback: (DragEvent) -> Unit)

    /**
     * Unsets the current component as a D&D drop target.
     */
    public fun clearDropTarget()

    public fun tagWithDnd(tag: Tag<E>)

}
