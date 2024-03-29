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

import dev.kilua.core.PropertyDelegate
import dev.kilua.utils.nativeMapOf
import web.dom.HTMLElement
import kotlin.collections.set

/**
 * Common tag attributes delegate.
 */
public interface TagAttrsDelegate<E : HTMLElement> : TagAttrs<E> {

    /**
     * The map of attributes.
     */
    public val attributesMap: Map<String, Any>

    /**
     * Connects the delegate with the given element.
     */
    public fun elementWithAttrs(element: E?)
}

/**
 * Common tag attributes delegate implementation.
 */
public open class TagAttrsDelegateImpl<E : HTMLElement>(
    skipUpdates: Boolean,
) : TagAttrsDelegate<E>, PropertyDelegate(nativeMapOf(), skipUpdates = skipUpdates) {

    public override val attributesMap: Map<String, Any> = propertyValues

    protected lateinit var element: E
    protected var elementNullable: E? = null

    override fun elementWithAttrs(element: E?) {
        this.elementNullable = element
        if (!skipUpdates && element != null) {
            this.element = element
        }
    }

    override var title: String? by updatingProperty {
        if (it != null) {
            element.title = it
        } else {
            element.removeAttribute("title")
        }
    }

    override var tabindex: Int? by updatingProperty {
        if (it != null) {
            element.tabIndex = it
        } else {
            element.removeAttribute("tabindex")
        }
    }

    override var draggable: Boolean? by updatingProperty {
        if (it != null) {
            element.draggable = it
        } else {
            element.removeAttribute("draggable")
        }
    }

    override var role: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("role", it)
        } else {
            element.removeAttribute("role")
        }
    }

    override var ariaLabel: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("aria-label", it)
        } else {
            element.removeAttribute("aria-label")
        }
    }

    override var ariaLabelledby: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("aria-labelledby", it)
        } else {
            element.removeAttribute("aria-labelledby")
        }
    }

    override var ariaDescribedby: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("aria-describedby", it)
        } else {
            element.removeAttribute("aria-describedby")
        }
    }

    override var accesskey: Char? by updatingProperty {
        if (it != null) {
            element.accessKey = it.toString()
        } else {
            element.removeAttribute("accesskey")
        }
    }

    override var autofocus: Boolean? by updatingProperty {
        if (it != null) {
            element.setAttribute("autofocus", "")
        } else {
            element.removeAttribute("autofocus")
        }
    }

    override fun setAttribute(name: String, value: String?) {
        if (propertyValues[name] != value) {
            if (value != null) {
                propertyValues[name] = value
                elementNullable?.setAttribute(name, value)
            } else {
                propertyValues.remove(name)
                elementNullable?.removeAttribute(name)
            }
        }
    }

    override fun getAttribute(name: String): String? {
        return propertyValues[name].toString()
    }

    override fun removeAttribute(name: String) {
        if (propertyValues[name] != null) {
            propertyValues.remove(name)
            elementNullable?.removeAttribute(name)
        }
    }
}
