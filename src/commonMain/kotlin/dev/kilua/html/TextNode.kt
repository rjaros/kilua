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

package dev.kilua.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.core.SafeDomFactory
import dev.kilua.utils.cast
import org.w3c.dom.Text

public open class TextNode(
    text: String,
    renderConfig: RenderConfig = DefaultRenderConfig(),
) : ComponentBase(SafeDomFactory.createTextNode(text, renderConfig), renderConfig) {

    public open val element: Text
        get() = node?.unsafeCast<Text>()
            ?: throw IllegalStateException("Can't use DOM element with the current render configuration")

    public open val elementNullable: Text? = node?.unsafeCast<Text>()

    public open val elementAvailable: Boolean = node != null

    private val skipUpdate = node == null

    public open var text: String by managedProperty(text, skipUpdate) {
        element.data = it
    }

    override var visible: Boolean by unmanagedProperty(true, skipUpdate) {
        element.data = if (it) text else ""
    }

    override fun renderToStringBuilder(builder: StringBuilder) {
        builder.append(if (visible) propertyValues[::text.name]?.cast<String>() ?: "" else "")
    }
}

@Composable
public fun text(text: String, content: TextNode.() -> Unit = {}): TextNode {
    // Always using DefaultRenderConfig because of plus operator String receiver.
    val textNode = remember { TextNode(text, DefaultRenderConfig()) }
    ComponentNode(textNode, {
        set(text) { updateManagedProperty(TextNode::text, it) }
    }) {
        content(textNode)
    }
    return textNode
}

@Composable
public operator fun String.unaryPlus() {
    text(this)
}
