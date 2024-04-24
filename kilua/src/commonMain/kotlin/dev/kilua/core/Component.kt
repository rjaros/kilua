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

package dev.kilua.core

/**
 * Base component interface.
 */
public interface Component {

    /**
     * Unique component id.
     */
    public val componentId: Int

    /**
     * Component visibility.
     */
    public var visible: Boolean

    /**
     * Parent component.
     */
    public val parent: Component?

    /**
     * List of child components.
     */
    public val children: List<Component>

    /**
     * Show the component.
     */
    public fun show() {
        visible = true
    }

    /**
     * Hide the component.
     */
    public fun hide() {
        visible = false
    }

    /**
     * Toggle the component visibility.
     */
    public fun toggle() {
        visible = !visible
    }

    /**
     * Render the component to the given [builder].
     */
    public fun renderToStringBuilder(builder: StringBuilder)

    /**
     * Render the component to string.
     */
    public fun renderToString(): String {
        val builder = StringBuilder()
        renderToStringBuilder(builder)
        return builder.toString()
    }

    /**
     * Render children components to a string.
     */
    public val innerHTML: String
        get() {
            val builder = StringBuilder()
            children.forEach {
                it.renderToStringBuilder(builder)
            }
            return builder.toString()
        }
}
