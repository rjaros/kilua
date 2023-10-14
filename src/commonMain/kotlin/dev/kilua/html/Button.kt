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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.utils.toKebabCase
import org.w3c.dom.HTMLButtonElement

/**
 * Button types.
 */
public enum class ButtonType {
    Button,
    Submit,
    Reset;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

public open class Button(
    type: ButtonType = ButtonType.Button,
    disabled: Boolean? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLButtonElement>("button", className, renderConfig) {

    public open var type: ButtonType by managedProperty(type, skipUpdate) {
        element.type = it.value
    }

    public open var disabled: Boolean? by managedProperty(disabled, skipUpdate) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
    }

    init {
        @Suppress("LeakingThis")
        elementNullable?.let {
            it.type = type.value
            if (disabled != null) {
                it.disabled = disabled
            }
        }
    }

    public open fun click() {
        elementNullable?.click()
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::type, ::disabled)
    }

}

@Composable
public fun ComponentBase.button(
    type: ButtonType = ButtonType.Button,
    disabled: Boolean? = null,
    className: String? = null,
    content: @Composable Button.() -> Unit = {}
): Button {
    val component = remember { Button(type, disabled, className, renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(type) { updateManagedProperty(Button::type, it) }
        set(disabled) { updateManagedProperty(Button::disabled, it) }
        set(className) { updateManagedProperty(Button::className, it) }
    }, content)
    return component
}

@Composable
public fun ComponentBase.button(
    label: String? = null,
    type: ButtonType = ButtonType.Button,
    disabled: Boolean? = null,
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
