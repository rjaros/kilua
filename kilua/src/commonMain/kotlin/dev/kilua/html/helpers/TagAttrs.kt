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

import web.dom.HTMLElement

/**
 * Common tag attributes.
 */
public interface TagAttrs: TagAttrsFun {
    /**
     * The title attribute.
     */
    public override var title: String?

    /**
     * The tabindex attribute.
     */
    public override var tabindex: Int?

    /**
     * The draggable attribute.
     */
    public override var draggable: Boolean?

    /**
     * The role attribute.
     */
    public override var role: String?

    /**
     * The aria-label attribute.
     */
    public override var ariaLabel: String?

    /**
     * The aria-labelledby attribute.
     */
    public override var ariaLabelledby: String?

    /**
     * The aria-describedby attribute.
     */
    public override var ariaDescribedby: String?

    /**
     * The accesskey attribute.
     */
    public override var accesskey: Char?

    /**
     * The autofocus attribute.
     */
    public override var autofocus: Boolean?

    /**
     * Set value for a given attribute name.
     */
    public fun setAttribute(name: String, value: String?)

    /**
     * Get value of the given attribute.
     */
    public override fun getAttribute(name: String): String?

    /**
     * Remove attribute with the given name.
     */
    public fun removeAttribute(name: String)
}
