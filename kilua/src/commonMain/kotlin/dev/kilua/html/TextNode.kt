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

package dev.kilua.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.core.SafeDomFactory
import dev.kilua.utils.cast
import web.dom.Text

/**
 * HTML text node component.
 */
public open class TextNode(
    data: String,
    renderConfig: RenderConfig = DefaultRenderConfig(),
) : ComponentBase(SafeDomFactory.createTextNode(data), renderConfig) {

    /**
     * The DOM text node.
     */
    public open val text: Text by lazy {
        if (renderConfig.isDom) node.cast<Text>() else {
            error("Can't use DOM node with the current render configuration")
        }
    }

    /**
     * The text of the node.
     */
    public open var data: String by updatingProperty(data) {
        text.data = it
    }

    override var visible: Boolean by updatingProperty(true) {
        text.data = if (it) data else ""
    }

    override fun renderToStringBuilder(builder: StringBuilder) {
        builder.append(data)
    }
}

/**
 * Creates a [TextNode] component.
 * @param data the text of the node
 * @param setup a function for setting up the component
 * @return a [TextNode] component
 */
@Composable
public fun textNode(data: String, setup: TextNode.() -> Unit = {}): TextNode {
    // Always using DefaultRenderConfig because of plus operator String receiver.
    val component = remember { TextNode(data, DefaultRenderConfig()) }
    ComponentNode(component, {
        set(data) { updateProperty(TextNode::data, it) }
    }) {
        setup(component)
    }
    return component
}

/**
 * Creates a [TextNode] component with unary + operator.
 */
@Composable
public operator fun String.unaryPlus() {
    textNode(this)
}
