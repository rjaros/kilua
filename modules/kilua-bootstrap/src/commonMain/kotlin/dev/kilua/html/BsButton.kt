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
import dev.kilua.core.IComponent
import dev.kilua.utils.rem
import dev.kilua.utils.toKebabCase

/**
 * Button styles.
 */
public enum class ButtonStyle {
    BtnPrimary,
    BtnSecondary,
    BtnSuccess,
    BtnDanger,
    BtnWarning,
    BtnInfo,
    BtnLight,
    BtnDark,
    BtnLink,
    BtnOutlinePrimary,
    BtnOutlineSecondary,
    BtnOutlineSuccess,
    BtnOutlineDanger,
    BtnOutlineWarning,
    BtnOutlineInfo,
    BtnOutlineLight,
    BtnOutlineDark;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Button sizes.
 */
public enum class ButtonSize {
    BtnLg,
    BtnSm,
    BtnXsm;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Creates a Bootstrap [Button] component with a given label and icon, returning a reference.
 *
 * @param label the label of the button
 * @param icon the icon of the button
 * @param style the style of the button
 * @param size the size of the button
 * @param type the type of the button
 * @param disabled whether the button is disabled
 * @param className the CSS class name
 * @param id the ID attribute of the button
 * @param content a function for setting up the component
 * @return the [Button] component
 */
@Composable
public fun IComponent.bsButtonRef(
    label: String? = null,
    icon: String? = null,
    style: ButtonStyle = ButtonStyle.BtnPrimary,
    size: ButtonSize? = null,
    type: ButtonType = ButtonType.Button,
    disabled: Boolean? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IButton.() -> Unit = {}
): Button {
    return buttonRef(label, icon, type, disabled, "btn" % style.value % size?.value % className, id, content)
}

/**
 * Creates a Bootstrap [Button] component with a given label and icon.
 *
 * @param label the label of the button
 * @param icon the icon of the button
 * @param style the style of the button
 * @param size the size of the button
 * @param type the type of the button
 * @param disabled whether the button is disabled
 * @param className the CSS class name
 * @param id the ID attribute of the button
 * @param content a function for setting up the component
 */
@Composable
public fun IComponent.bsButton(
    label: String? = null,
    icon: String? = null,
    style: ButtonStyle = ButtonStyle.BtnPrimary,
    size: ButtonSize? = null,
    type: ButtonType = ButtonType.Button,
    disabled: Boolean? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable IButton.() -> Unit = {}
) {
    val iconClassName = if (label != null && icon != null) {
        className % "icon-link"
    } else className
    button(label, icon, type, disabled, "btn" % style.value % size?.value % iconClassName, id, content)
}
