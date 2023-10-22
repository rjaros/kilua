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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.html.helpers.PropertyListBuilder
import org.w3c.dom.HTMLOptGroupElement

/**
 * HTML Optgroup component.
 */
public open class Optgroup(
    label: String? = null,
    disabled: Boolean? = null,
    className: String? = null, renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLOptGroupElement>("optgroup", className, renderConfig) {

    /**
     * The label of the option group.
     */
    public open var label: String? by updatingProperty(label, skipUpdate) {
        if (it != null) {
            element.label = it
        } else {
            element.removeAttribute("label")
        }
    }

    /**
     * Whether the option group is disabled.
     */
    public open var disabled: Boolean? by updatingProperty(disabled, skipUpdate) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
    }

    init {
        @Suppress("LeakingThis")
        elementNullable?.let {
            if (label != null) {
                it.label = label
            }
            if (disabled != null) {
                it.disabled = disabled
            }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(::label, ::disabled)
    }

}

/**
 * Creates a [Optgroup] component.
 *
 * @param label the label of the option group
 * @param disabled whether the option group is disabled
 * @param className the CSS class name
 * @param content the content of the component
 * @return the [Optgroup] component
 */
@Composable
public fun ComponentBase.optgroup(
    label: String? = null,
    disabled: Boolean? = null,
    className: String? = null,
    content: @Composable Optgroup.() -> Unit = {}
): Optgroup {
    val component = remember { Optgroup(label, disabled, className, renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(label) { updateProperty(Optgroup::label, it) }
        set(disabled) { updateProperty(Optgroup::disabled, it) }
        set(className) { updateProperty(Optgroup::className, it) }
    }, content)
    return component
}
