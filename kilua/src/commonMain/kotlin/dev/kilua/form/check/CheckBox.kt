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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.div
import dev.kilua.html.label
import dev.kilua.html.unaryPlus

/**
 * CheckBox input component.
 */
public open class CheckBox(
    value: Boolean = false,
    name: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : AbstractCheck(CheckInputType.Checkbox, value, name, disabled, className, renderConfig)

/**
 * Creates [CheckBox] component.
 *
 * @param value initial value
 * @param name the name of the input
 * @param disabled whether the input is disabled
 * @param className the CSS class name
 * @param setup a function for setting up the component
 * @return a [CheckBox] component
 */
@Composable
public fun ComponentBase.checkBox(
    value: Boolean = false,
    name: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    setup: @Composable CheckBox.() -> Unit = {}
): CheckBox {
    val component = remember { CheckBox(value, name, disabled, className, renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(value) { updateProperty(CheckBox::value, it) }
        set(name) { updateProperty(CheckBox::name, it) }
        set(disabled) { updateProperty(CheckBox::disabled, it) }
        set(className) { updateProperty(CheckBox::className, it) }
    }, setup)
    return component
}

/**
 * Creates [CheckBox] component with a label.
 *
 * @param label the label of the input
 * @param value initial value
 * @param name the name of the input
 * @param disabled whether the input is disabled
 * @param className the CSS class name
 * @param groupClassName the CSS class name of the grouping div
 * @param setup a function for setting up the component
 * @return a [CheckBox] component
 */
@Composable
public fun ComponentBase.checkBox(
    label: String,
    value: Boolean = false,
    name: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    groupClassName: String? = null,
    setup: @Composable CheckBox.() -> Unit = {}
): CheckBox {
    lateinit var checkBox: CheckBox
    div(groupClassName) {
        checkBox = checkBox(value, name, disabled, className) {
            id = "id_${componentId}"
            setup()
        }
        label(checkBox.id) { +label }
    }
    return checkBox
}
