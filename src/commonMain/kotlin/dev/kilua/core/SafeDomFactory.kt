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

package dev.kilua.core

import dev.kilua.utils.isDom
import kotlinx.browser.document
import org.w3c.dom.Element
import org.w3c.dom.Text

public object SafeDomFactory {

    public fun createElement(name: String, renderConfig: RenderConfig): Element? {
        return if (renderConfig.isDom && isDom) {
            document.createElement(name)
        } else null
    }

    public fun createTextNode(text: String, renderConfig: RenderConfig): Text? {
        return if (renderConfig.isDom && isDom) {
            document.createTextNode(text)
        } else null
    }

    public fun getElementById(id: String, renderConfig: RenderConfig): Element? {
        return if (renderConfig.isDom && isDom) {
            document.getElementById(id)
        } else null
    }

}
