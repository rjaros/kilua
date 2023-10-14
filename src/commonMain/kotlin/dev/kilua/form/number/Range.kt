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

package dev.kilua.form.number

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.form.Input
import dev.kilua.form.InputType
import dev.kilua.form.NumberFormControl
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.utils.console
import dev.kilua.utils.log

internal const val RANGE_DEFAULT_MIN = 0
internal const val RANGE_DEFAULT_MAX = 100
internal const val RANGE_DEFAULT_STEP = 1

public open class Range(
    value: Number? = null,
    min: Int = RANGE_DEFAULT_MIN,
    max: Int = RANGE_DEFAULT_MAX,
    step: Int = RANGE_DEFAULT_STEP,
    name: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Input<Number>(value, InputType.Range, name, null, null, disabled, className, renderConfig),
    NumberFormControl {

    public open var min: Int by managedProperty(min, skipUpdate) {
        element.min = it.toString()
    }

    public open var max: Int by managedProperty(max, skipUpdate) {
        element.max = it.toString()
    }

    public open var step: Int by managedProperty(step, skipUpdate) {
        element.step = it.toString()
    }

    init {
        @Suppress("LeakingThis")
        elementNullable?.let {
            it.min = min.toString()
            it.max = max.toString()
            it.step = step.toString()
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::min, ::max, ::step)
    }

    override fun setValueFromString(text: String?) {
        value = if (text.isNullOrEmpty()) {
            null
        } else {
            text.toDoubleOrNull()
        }
    }

    public open fun stepUp() {
        elementNullable?.let {
            it.stepUp()
            setValueFromString(it.value)
        }
    }

    public open fun stepDown() {
        elementNullable?.let {
            it.stepDown()
            setValueFromString(it.value)
        }
    }

}

@Composable
public fun ComponentBase.range(
    value: Number? = null,
    min: Int = RANGE_DEFAULT_MIN,
    max: Int = RANGE_DEFAULT_MAX,
    step: Int = RANGE_DEFAULT_STEP,
    name: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    content: @Composable Range.() -> Unit = {}
): Range {
    val component = remember { Range(value, min, max, step, name, disabled, className, renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(value) { updateManagedProperty(Spinner::value, it) }
        set(min) { updateManagedProperty(Spinner::min, it) }
        set(max) { updateManagedProperty(Spinner::max, it) }
        set(step) { updateManagedProperty(Spinner::step, it) }
        set(name) { updateManagedProperty(Spinner::name, it) }
        set(disabled) { updateManagedProperty(Spinner::disabled, it) }
        set(className) { updateManagedProperty(Spinner::className, it) }
    }, content)
    return component
}
