/*
 * Copyright (c) 2024 Robert Jaros
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

import dev.kilua.utils.buildCustomEventInit
import dev.kilua.utils.isDom
import dev.kilua.utils.toKebabCase
import js.core.JsAny
import web.cssom.MediaQuery
import web.cssom.matchMedia
import web.dom.document
import web.events.CustomEvent
import web.events.Event
import web.events.EventType
import web.events.addEventListener
import web.storage.localStorage

/**
 * Tailwindcss color themes.
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
                if (value == Theme.Auto && getPreferredTheme() == Theme.Dark || value == Theme.Dark) {
                    document.documentElement.classList.add("dark")
                } else {
                    document.documentElement.classList.remove("dark")
                }
                document.dispatchEvent(
                    CustomEvent(
                        EventType<CustomEvent<JsAny>>("kilua.theme.changed"),
                        buildCustomEventInit()
                    )
                )
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
            if (theme == Theme.Auto && getPreferredTheme() == Theme.Dark || theme == Theme.Dark) {
                document.documentElement.classList.add("dark")
            } else {
                document.documentElement.classList.remove("dark")
            }
            matchMedia(MediaQuery("(prefers-color-scheme: dark)")).addEventListener(EventType<Event>("change"), {
                val storedTheme = getStoredTheme() ?: Theme.Auto
                if (storedTheme == Theme.Auto) {
                    if (getPreferredTheme() == Theme.Dark) {
                        document.documentElement.classList.add("dark")
                    } else {
                        document.documentElement.classList.remove("dark")
                    }
                }
            })
        }
    }

    private fun getStoredTheme(): Theme? {
        return if (remember && isDom) {
            localStorage.getItem("kilua-tailwindcss-theme")?.let { theme ->
                return Theme.entries.find { theme == it.value }
            }
        } else null
    }

    private fun setStoredTheme(theme: Theme) {
        if (remember && isDom) localStorage.setItem("kilua-tailwindcss-theme", theme.value)
    }

    private fun getPreferredTheme(): Theme {
        return if (isDom && matchMedia(MediaQuery("(prefers-color-scheme: dark)")).matches) Theme.Dark else Theme.Light
    }

}
