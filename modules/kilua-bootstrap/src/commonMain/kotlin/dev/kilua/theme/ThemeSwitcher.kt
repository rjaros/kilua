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
import dev.kilua.core.ComponentBase
import dev.kilua.html.Button
import dev.kilua.html.ButtonStyle
import dev.kilua.html.bsButton
import dev.kilua.utils.rem

@Composable
public fun ComponentBase.themeSwitcher(
    title: String? = "Switch color theme",
    style: ButtonStyle = ButtonStyle.BtnSecondary,
    round: Boolean = false,
    disabled: Boolean = false,
    className: String? = null,
    content: @Composable Button.() -> Unit = {}
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
    return bsButton(
        null,
        icon,
        style,
        disabled = disabled,
        className = className % if (round) "rounded-circle" else null
    ) {
        this.title = title
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
