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

package dev.kilua.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.core.IComponent
import dev.kilua.html.Button
import dev.kilua.html.ButtonStyle
import dev.kilua.html.IButton
import dev.kilua.html.bsButton
import dev.kilua.html.bsButtonRef
import dev.kilua.utils.rem

/**
 * The theme switcher component for Bootstrap, returning a reference.
 *
 * @param title the title of the theme switcher
 * @param style the style of the theme switcher
 * @param round the round state of the theme switcher
 * @param disabled the disabled state of the theme switcher
 * @param className the CSS class name
 * @param id the ID of the theme switcher
 * @param content the content of the theme switcher
 * @return the theme switcher component
 */
@Composable
public fun IComponent.themeSwitcherRef(
    title: String? = "Switch color theme",
    style: ButtonStyle = ButtonStyle.BtnSecondary,
    round: Boolean = false,
    disabled: Boolean = false,
    className: String? = null,
    id: String? = null,
    content: @Composable IButton.() -> Unit = {}
): Button {
    var icon by remember {
        mutableStateOf(
            when (ThemeManager.theme) {
                Theme.Auto -> "fas fa-circle-half-stroke"
                Theme.Light -> "fas fa-moon"
                Theme.Dark -> "fas fa-sun"
            }
        )
    }
    return bsButtonRef(
        null,
        icon,
        style,
        disabled = disabled,
        className = className % if (round) "rounded-circle" else null,
        id = id
    ) {
        this.title(title)
        content()
        onClick {
            if (ThemeManager.theme == Theme.Dark) {
                ThemeManager.theme = Theme.Light
                icon = "fas fa-moon"
            } else {
                ThemeManager.theme = Theme.Dark
                icon = "fas fa-sun"
            }
        }
    }
}

/**
 * The theme switcher component for Bootstrap.
 *
 * @param title the title of the theme switcher
 * @param style the style of the theme switcher
 * @param round the round state of the theme switcher
 * @param disabled the disabled state of the theme switcher
 * @param className the CSS class name
 * @param id the ID of the theme switcher
 * @param content the content of the theme switcher
 */
@Composable
public fun IComponent.themeSwitcher(
    title: String? = "Switch color theme",
    style: ButtonStyle = ButtonStyle.BtnSecondary,
    round: Boolean = false,
    disabled: Boolean = false,
    className: String? = null,
    id: String? = null,
    content: @Composable IButton.() -> Unit = {}
) {
    var icon by remember {
        mutableStateOf(
            when (ThemeManager.theme) {
                Theme.Auto -> "fas fa-circle-half-stroke"
                Theme.Light -> "fas fa-moon"
                Theme.Dark -> "fas fa-sun"
            }
        )
    }
    bsButton(
        null,
        icon,
        style,
        disabled = disabled,
        className = className % if (round) "rounded-circle" else null,
        id = id
    ) {
        this.title(title)
        content()
        onClick {
            if (ThemeManager.theme == Theme.Dark) {
                ThemeManager.theme = Theme.Light
                icon = "fas fa-moon"
            } else {
                ThemeManager.theme = Theme.Dark
                icon = "fas fa-sun"
            }
        }
    }
}
