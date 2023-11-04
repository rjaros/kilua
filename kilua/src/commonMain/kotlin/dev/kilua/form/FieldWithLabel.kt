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

package dev.kilua.form

import androidx.compose.runtime.Composable
import dev.kilua.core.ComponentBase
import dev.kilua.html.div
import dev.kilua.html.label
import dev.kilua.html.unaryPlus

/**
 * Creates a given fields with a connected label.
 * @param label the label text
 * @param className the optional CSS class name for the label
 * @param labelAfter whether the label should be placed after the field
 * @param groupClassName the optional CSS class name for the group
 * @param wrapperClassName the optional CSS class name for the direct wrapper of the field
 */
@Composable
public fun <C : FormControl<*>> ComponentBase.fieldWithLabel(
    label: String,
    className: String? = null,
    labelAfter: Boolean = false,
    groupClassName: String? = null,
    wrapperClassName: String? = null,
    factory: @Composable ComponentBase.() -> C,
): C {
    @Composable
    fun ComponentBase.fieldFactory(id: String): C {
        lateinit var input: C
        if (wrapperClassName != null) {
            div(wrapperClassName) {
                input = factory().also { it.id = id }
            }
        } else {
            input = factory().also { it.id = id }
        }
        return input
    }

    @Composable
    fun ComponentBase.labelFactory(id: String): C {
        lateinit var input: C
        if (labelAfter) {
            input = fieldFactory(id)
            label(id, className) { +label }
        } else {
            label(id, className) { +label }
            input = fieldFactory(id)
        }
        return input
    }

    lateinit var input: C
    val forId = "for_id_${FieldWithLabel.forIdCounter++}"
    if (groupClassName != null) {
        div(groupClassName) {
            input = labelFactory(forId)
        }
    } else {
        input = labelFactory(forId)
    }
    return input
}

internal object FieldWithLabel {
    var forIdCounter: Int = 0
}
