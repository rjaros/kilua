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

package dev.kilua.form.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.form.IInput
import dev.kilua.form.Input
import dev.kilua.form.InputType
import dev.kilua.form.StringFormControl

/**
 * Text input component.
 */
public interface IText : IInput<String>, StringFormControl

/**
 * Text input component.
 */
public open class Text(
    value: String? = null,
    type: InputType = InputType.Text,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Input<String>(
    value,
    type,
    name,
    maxlength,
    placeholder,
    disabled,
    required,
    className,
    id,
    renderConfig = renderConfig
), StringFormControl, IText {

    override fun stringToValue(text: String?): String? {
        return if (text.isNullOrEmpty()) {
            null
        } else {
            text
        }
    }

}

/**
 * Creates [Text] component.
 *
 * @param value initial value
 * @param type the type of the input
 * @param name the name of the input
 * @param maxlength the maximum length of the input
 * @param placeholder the placeholder text
 * @param disabled whether the input is disabled
 * @param required whether the input is required
 * @param className the CSS class name
 * @param id the ID of the input
 * @param setup a function for setting up the component
 * @return a [Text] component
 */
@Composable
public fun IComponent.text(
    value: String? = null,
    type: InputType = InputType.Text,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IText.() -> Unit = {}
): Text {
    val component =
        remember {
            Text(
                value,
                type,
                name,
                maxlength,
                placeholder,
                disabled,
                required,
                className,
                id,
                renderConfig = renderConfig
            )
        }
    ComponentNode(component, {
        set(value) { updateProperty(Text::value, it) }
        set(type) { updateProperty(Text::type, it) }
        set(name) { updateProperty(Text::name, it) }
        set(maxlength) { updateProperty(Text::maxlength, it) }
        set(placeholder) { updateProperty(Text::placeholder, it) }
        set(disabled) { updateProperty(Text::disabled, it) }
        set(required) { updateProperty(Text::required, it) }
        set(className) { updateProperty(Text::className, it) }
        set(id) { updateProperty(Text::id, it) }
    }, setup)
    return component
}
