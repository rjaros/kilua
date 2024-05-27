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
import app.softwork.routingcompose.Router
import dev.kilua.core.IComponent

/**
 * Creates a [Link] component with router support, returning a reference.
 *
 * @param href the link URL
 * @param label the link label
 * @param icon the link icon
 * @param target the link target
 * @param hide hides the route from the browser history
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 * @return the [Link] component
 */
@Composable
public fun IComponent.navLinkRef(
    href: String? = null,
    label: String? = null,
    icon: String? = null,
    target: String? = null,
    hide: Boolean = false,
    className: String? = null,
    id: String? = null,
    content: @Composable ILink.() -> Unit = {}
): Link {
    return linkRef(href, label, icon, target, className, id) {
        setupNavLink(href, hide)
        content()
    }
}

/**
 * Creates a [Link] component with router support.
 *
 * @param href the link URL
 * @param label the link label
 * @param icon the link icon
 * @param target the link target
 * @param hide hides the route from the browser history
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 */
@Composable
public fun IComponent.navLink(
    href: String? = null,
    label: String? = null,
    icon: String? = null,
    target: String? = null,
    hide: Boolean = false,
    className: String? = null,
    id: String? = null,
    content: @Composable ILink.() -> Unit = {}
) {
    link(href, label, icon, target, className, id) {
        setupNavLink(href, hide)
        content()
    }
}

@Composable
private fun ILink.setupNavLink(href: String?, hide: Boolean) {
    if (href != null) {
        val router = Router.current
        onClick {
            router.navigate(href, hide)
            it.preventDefault()
        }
    }
}
