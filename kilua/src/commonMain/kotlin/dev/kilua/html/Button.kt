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
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.utils.rem
import dev.kilua.utils.toKebabCase
import web.dom.HTMLButtonElement
import web.dom.events.MouseEvent

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

/**
 * HTML Button component.
 */
public open class Button(
    type: ButtonType = ButtonType.Button,
    disabled: Boolean? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLButtonElement>("button", className, renderConfig = renderConfig) {

    /**
     * The type of the button.
     */
    public open var type: ButtonType by updatingProperty(type) {
        element.type = it.value
    }

    /**
     * Whether the button is disabled.
     */
    public open var disabled: Boolean? by updatingProperty(disabled) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
    }

    init {
        if (renderConfig.isDom) {
            element.type = type.value
            if (disabled != null) {
                element.disabled = disabled
            }
        }
    }

    /**
     * Clicks the button.
     */
    public open fun click() {
        if (renderConfig.isDom) {
            element.click()
        } else {
            tagEvents.eventsMap["click"]?.forEach {
                it.value(MouseEvent("click"))
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::type, ::disabled)
    }

}

/**
 * Creates a [Button] component.
 *
 * @param type the type of the button
 * @param disabled whether the button is disabled
 * @param className the CSS class name
 * @param content a function for setting up the component
 * @return the [Button] component
 */
@Composable
private fun ComponentBase.button(
    type: ButtonType = ButtonType.Button,
    disabled: Boolean? = null,
    className: String? = null,
    content: @Composable Button.() -> Unit = {}
): Button {
    val component = remember { Button(type, disabled, className, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(type) { updateProperty(Button::type, it) }
        set(disabled) { updateProperty(Button::disabled, it) }
        set(className) { updateProperty(Button::className, it) }
    }, content)
    return component
}

/**
 * Creates a [Button] component with a given label and icon.
 *
 * @param label the label of the button
 * @param icon the icon of the button
 * @param type the type of the button
 * @param disabled whether the button is disabled
 * @param className the CSS class name
 * @param content a function for setting up the component
 * @return the [Button] component
 */
@Composable
public fun ComponentBase.button(
    label: String? = null,
    icon: String? = null,
    type: ButtonType = ButtonType.Button,
    disabled: Boolean? = null,
    className: String? = null,
    content: @Composable Button.() -> Unit = {}
): Button {
    val iconClassName = if (label != null && icon != null) {
        className % "icon-link"
    } else className
    return button(type, disabled, iconClassName) {
        atom(label, icon)
        content()
    }
}
