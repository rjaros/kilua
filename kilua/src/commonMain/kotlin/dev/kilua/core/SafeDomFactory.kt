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

import dev.kilua.externals.nodeJsCreateComment
import dev.kilua.externals.nodeJsCreateElement
import dev.kilua.externals.nodeJsCreateText
import dev.kilua.utils.isDom
import web.dom.document
import web.dom.Comment
import web.dom.Element
import web.dom.Text

/**
 * Helper factory to safely call methods on the document object.
 */
public object SafeDomFactory {

    /**
     * Create DOM element with optional namespace.
     */
    public fun createElement(name: String, namespace: String? = null): Element {
        return if (isDom) {
            if (namespace != null)
                document.createElementNS(namespace, name)
            else
                document.createElement(name)
        } else nodeJsCreateElement()
    }

    /**
     * Create DOM text node.
     */
    public fun createTextNode(text: String): Text {
        return if (isDom) {
            document.createTextNode(text)
        } else nodeJsCreateText()
    }

    /**
     * Create DOM comment node.
     */
    public fun createComment(text: String): Comment {
        return if (isDom) {
            document.createComment(text)
        } else nodeJsCreateComment()
    }

    /**
     * Get DOM element by id.
     */
    public fun getElementById(id: String): Element? {
        return if (isDom) {
            document.getElementById(id)
        } else null
    }

    /**
     * Get first DOM element by tag name.
     */
    public fun getFirstElementByTagName(tagName: String): Element? {
        return if (isDom) {
            val collection = document.getElementsByTagName(tagName)
            if (collection.length > 0) collection.item(0) else null
        } else null
    }
}
