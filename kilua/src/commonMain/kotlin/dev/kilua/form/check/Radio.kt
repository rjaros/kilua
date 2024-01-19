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

package dev.kilua.form.check

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig

/**
 * Radio button input component.
 */
public open class Radio(
    value: Boolean = false,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : AbstractCheck(CheckInputType.Radio, value, name, disabled, required, className, id, renderConfig = renderConfig)

/**
 * Creates [Radio] component.
 *
 * @param value initial value
 * @param name the name of the input
 * @param disabled whether the input is disabled
 * @param required whether the input is required
 * @param id the ID of the input
 * @param className the CSS class name
 * @param setup a function for setting up the component
 * @return a [Radio] component
 */
@Composable
public fun ComponentBase.radio(
    value: Boolean = false,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable Radio.() -> Unit = {}
): Radio {
    val component = remember { Radio(value, name, disabled, required, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(value) { updateProperty(Radio::value, it) }
        set(name) { updateProperty(Radio::name, it) }
        set(disabled) { updateProperty(Radio::disabled, it) }
        set(required) { updateProperty(Radio::required, it) }
        set(className) { updateProperty(Radio::className, it) }
        set(id) { updateProperty(Radio::id, it) }
    }, setup)
    return component
}
