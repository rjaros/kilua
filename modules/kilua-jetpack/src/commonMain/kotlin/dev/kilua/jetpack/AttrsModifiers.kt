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

package dev.kilua.jetpack

/**
 * Set the title attribute.
 */
public fun Modifier.title(title: String?) = attrsModifier {
    title(title)
}

/**
 * Set the tabindex attribute.
 */
public fun Modifier.tabindex(tabindex: Int?) = attrsModifier {
    tabindex(tabindex)
}

/**
 * Set the draggable attribute.
 */
public fun Modifier.draggable(draggable: Boolean?) = attrsModifier {
    draggable(draggable)
}

/**
 * Set the role attribute.
 */
public fun Modifier.role(role: String?) = attrsModifier {
    role(role)
}

/**
 * Set the aria-label attribute.
 */
public fun Modifier.ariaLabel(ariaLabel: String?) = attrsModifier {
    ariaLabel(ariaLabel)
}

/**
 * Set the aria-labelledby attribute.
 */
public fun Modifier.ariaLabelledby(ariaLabelledby: String?) = attrsModifier {
    ariaLabelledby(ariaLabelledby)
}

/**
 * Set the aria-describedby attribute.
 */
public fun Modifier.ariaDescribedby(ariaDescribedby: String?) = attrsModifier {
    ariaDescribedby(ariaDescribedby)
}

/**
 * Set the accesskey attribute.
 */
public fun Modifier.accesskey(accesskey: Char?) = attrsModifier {
    accesskey(accesskey)
}

/**
 * Set the autofocus attribute.
 */
public fun Modifier.autofocus(autofocus: Boolean?) = attrsModifier {
    autofocus(autofocus)
}

/**
 * Set value for the given attribute.
 */
public fun Modifier.attribute(name: String, value: String?) = attrsModifier {
    attribute(name, value)
}
