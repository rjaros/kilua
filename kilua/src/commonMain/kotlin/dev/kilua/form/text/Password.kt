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
import dev.kilua.core.IComponent
import dev.kilua.form.InputType

/**
 * Creates [Text] component with password input type.
 *
 * @param value initial value
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
public fun IComponent.password(
    value: String? = null,
    name: String? = null,
    maxlength: Int? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable IText.() -> Unit = {}
): Text {
    return text(
        value = value,
        type = InputType.Password,
        name = name,
        maxlength = maxlength,
        placeholder = placeholder,
        disabled = disabled,
        required = required,
        className = className,
        id = id,
        setup = setup
    )
}
