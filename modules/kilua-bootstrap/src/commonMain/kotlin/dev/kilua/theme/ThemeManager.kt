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

import dev.kilua.externals.buildCustomEventInit
import dev.kilua.utils.isDom
import dev.kilua.utils.toKebabCase
import dev.kilua.dom.document
import dev.kilua.dom.api.CustomEvent
import dev.kilua.dom.localStorage
import dev.kilua.dom.window

/**
 * Bootstrap color themes.
 */
public enum class Theme {
    Auto,
    Light,
    Dark;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

}

/**
 * Color theme manager.
 */
public object ThemeManager {

    private var remember = true

    /**
     * Current color theme.
     */
    public var theme: Theme = Theme.Auto
        set(value) {
            field = value
            setStoredTheme(value)
            if (isDom) {
                if (value == Theme.Auto) {
                    document.documentElement?.setAttribute("data-bs-theme", getPreferredTheme().value)
                } else {
                    document.documentElement?.setAttribute("data-bs-theme", value.value)
                }
                document.dispatchEvent(CustomEvent("kilua.theme.changed", buildCustomEventInit(null)))
            }
        }

    /**
     * Initialize the theme manager with the preferred color theme.
     * @param remember remember selected theme in the local storage
     */
    public fun init(initialTheme: Theme? = null, remember: Boolean = true) {
        this.remember = remember
        this.theme = initialTheme ?: getStoredTheme() ?: getPreferredTheme()
        if (isDom) {
            if (theme == Theme.Auto) {
                document.documentElement?.setAttribute("data-bs-theme", getPreferredTheme().value)
            } else {
                document.documentElement?.setAttribute("data-bs-theme", theme.value)
            }
            window.matchMedia("(prefers-color-scheme: dark)").addEventListener("change") {
                val storedTheme = getStoredTheme() ?: Theme.Auto
                if (storedTheme == Theme.Auto) {
                    document.documentElement?.setAttribute("data-bs-theme", getPreferredTheme().value)
                }
            }
        }
    }

    private fun getStoredTheme(): Theme? {
        return if (remember && isDom) {
            localStorage.getItem("kilua-bootstrap-theme")?.let { theme ->
                return Theme.entries.find { theme == it.value }
            }
        } else null
    }

    private fun setStoredTheme(theme: Theme) {
        if (remember && isDom) localStorage.setItem("kilua-bootstrap-theme", theme.value)
    }

    private fun getPreferredTheme(): Theme {
        return if (isDom && window.matchMedia("(prefers-color-scheme: dark)").matches) Theme.Dark else Theme.Light
    }

}
