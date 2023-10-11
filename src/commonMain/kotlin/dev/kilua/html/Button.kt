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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import org.w3c.dom.HTMLButtonElement

/**
 * Button types.
 */
public enum class ButtonType(internal val buttonType: String) {
    Button("button"),
    Submit("submit"),
    Reset("reset")
}

public open class Button(
    type: ButtonType = ButtonType.Button,
    disabled: Boolean = false,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLButtonElement>("button", className, renderConfig) {

    public open var type: ButtonType by managedProperty(type, skipUpdate) {
        element.type = it.buttonType
    }

    public open var disabled: Boolean by managedProperty(disabled, skipUpdate) {
        element.disabled = it
    }

    public open fun click() {
        elementNullable?.click()
    }

    override fun renderToStringBuilder(builder: StringBuilder) {
        builder.append("<button>")
        children.forEach {
            it.renderToStringBuilder(builder)
        }
        builder.append("<button>")
    }

}

@Composable
public fun ComponentBase.button(
    type: ButtonType = ButtonType.Button,
    disabled: Boolean = false,
    className: String? = null,
    content: @Composable Button.() -> Unit = {}
): Button {
    val button by remember { mutableStateOf(Button(type, disabled, className, renderConfig)) }
    ComponentNode(button, {
        set(type) { updateManagedProperty(Button::type, it) }
        set(disabled) { updateManagedProperty(Button::disabled, it) }
        set(className) { updateManagedProperty(Button::className, it) }
    }, content)
    return button
}

@Composable
public fun ComponentBase.button(
    label: String? = null,
    type: ButtonType = ButtonType.Button,
    disabled: Boolean = false,
    className: String? = null,
    content: @Composable Button.() -> Unit = {}
): Button {
    return button(type, disabled, className) {
        if (label != null) {
            +label
        }
        content()
    }
}
