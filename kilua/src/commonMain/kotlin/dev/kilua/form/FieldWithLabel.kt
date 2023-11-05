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
import androidx.compose.runtime.remember
import dev.kilua.core.ComponentBase
import dev.kilua.html.div
import dev.kilua.html.label
import dev.kilua.html.unaryPlus

/**
 * Creates a block with an associated label.
 * @param label the label text
 * @param className the optional CSS class name for the label
 * @param labelAfter whether the label should be placed after the field
 * @param groupClassName the optional CSS class name for the group
 * @param wrapperClassName the optional CSS class name for the direct wrapper of the field
 */
@Composable
public fun ComponentBase.fieldWithLabel(
    label: String,
    className: String? = null,
    labelAfter: Boolean = false,
    groupClassName: String? = null,
    wrapperClassName: String? = null,
    content: @Composable ComponentBase.(id: String) -> Unit,
) {
    @Composable
    fun ComponentBase.generateContent(id: String) {
        if (wrapperClassName != null) {
            div(wrapperClassName) {
                content(id)
            }
        } else {
            content(id)
        }
    }

    @Composable
    fun ComponentBase.generateLabelWithContent(id: String) {
        if (labelAfter) {
            generateContent(id)
            label(id, className) { +label }
        } else {
            label(id, className) { +label }
            generateContent(id)
        }
    }

    val forId = remember { "for_id_${FieldWithLabel.forIdCounter++}" }
    if (groupClassName != null) {
        div(groupClassName) {
            generateLabelWithContent(forId)
        }
    } else {
        generateLabelWithContent(forId)
    }
}

internal object FieldWithLabel {
    var forIdCounter: Int = 0
}
