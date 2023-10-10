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
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.compose.DomScope
import dev.kilua.core.ComponentBase
import kotlinx.browser.document
import org.w3c.dom.Text

public open class TextNode(text: String) : ComponentBase(document.createTextNode(text)),
    DomScope<Text> {

    @Suppress("LeakingThis")
    public open val element: Text = node.unsafeCast<Text>()

    public open var text: String by managedProperty(text) {
        element.data = it
    }

    override var visible: Boolean by unmanagedProperty(true) {
        element.data = if (it) text else ""
    }

    override val DisposableEffectScope.element: Text
        get() = this@TextNode.element
}

@Composable
public fun text(text: String, content: TextNode.() -> Unit = {}): TextNode {
    val textNode by remember { mutableStateOf(TextNode(text)) }
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
