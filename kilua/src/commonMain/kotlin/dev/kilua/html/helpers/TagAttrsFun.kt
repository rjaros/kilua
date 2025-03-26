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

package dev.kilua.html.helpers

import androidx.compose.runtime.Composable

/**
 * Common tag immutable attributes.
 */
public interface TagAttrsFun {
    /**
     * The title attribute.
     */
    public val title: String?

    /**
     * Set the title attribute.
     */
    @Composable
    public fun title(title: String?)

    /**
     * The tabindex attribute.
     */
    public val tabindex: Int?

    /**
     * Set the tabindex attribute.
     */
    @Composable
    public fun tabindex(tabindex: Int?)

    /**
     * The draggable attribute.
     */
    public val draggable: Boolean?

    /**
     * Set the draggable attribute.
     */
    @Composable
    public fun draggable(draggable: Boolean?)

    /**
     * The role attribute.
     */
    public val role: String?

    /**
     * Set the role attribute.
     */
    @Composable
    public fun role(role: String?)

    /**
     * The aria-label attribute.
     */
    public val ariaLabel: String?

    /**
     * Set the aria-label attribute.
     */
    @Composable
    public fun ariaLabel(ariaLabel: String?)

    /**
     * The aria-labelledby attribute.
     */
    public val ariaLabelledby: String?

    /**
     * Set the aria-labelledby attribute.
     */
    @Composable
    public fun ariaLabelledby(ariaLabelledby: String?)

    /**
     * The aria-describedby attribute.
     */
    public val ariaDescribedby: String?

    /**
     * Set the aria-describedby attribute.
     */
    @Composable
    public fun ariaDescribedby(ariaDescribedby: String?)

    /**
     * The accesskey attribute.
     */
    public val accesskey: Char?

    /**
     * Set the accesskey attribute.
     */
    @Composable
    public fun accesskey(accesskey: Char?)

    /**
     * The autofocus attribute.
     */
    public val autofocus: Boolean?

    /**
     * Set the autofocus attribute.
     */
    @Composable
    public fun autofocus(autofocus: Boolean?)

    /**
     * Get value of the given attribute.
     */
    public fun getAttribute(name: String): String?

    /**
     * Set value for the given attribute.
     */
    @Composable
    public fun attribute(name: String, value: String?)
}
