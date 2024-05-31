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

package dev.kilua.panel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.BootstrapModule
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.Bootstrap
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.h5t
import dev.kilua.utils.cast
import dev.kilua.utils.rem
import dev.kilua.utils.toKebabCase
import web.dom.HTMLDivElement
import web.dom.events.Event

/**
 * The offcanvas placement.
 */
public enum class OffPlacement {
    OffcanvasStart,
    OffcanvasEnd,
    OffcanvasTop,
    OffcanvasBottom;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * The offcanvas responsive types.
 */
public enum class OffResponsiveType {
    OffcanvasSm,
    OffcanvasMd,
    OffcanvasLg,
    OffcanvasXl,
    OffcanvasXXl;


    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Bootstrap offcanvas component.
 */
public interface IOffcanvas : ITag<HTMLDivElement> {
    /**
     * Shows the offcanvas.
     */
    public fun show()

    /**
     * Hides the offcanvas.
     */
    public fun hide()

    /**
     * Toggles the offcanvas.
     */
    public fun toggle()
}

/**
 * Bootstrap offcanvas component.
 */
public open class Offcanvas(
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLDivElement>("div", className, id, renderConfig = renderConfig), IOffcanvas {

    /**
     * Whether the offcanvas should be visible.
     */
    protected var isShown: Boolean = false

    /**
     * The native Bootstrap offcanvas instance.
     */
    protected var offcanvasInstance: Bootstrap.Offcanvas? = null

    /**
     * Shows the offcanvas.
     */
    public override fun show() {
        isShown = true
        offcanvasInstance?.show()
    }

    /**
     * Hides the offcanvas.
     */
    public override fun hide() {
        isShown = false
        offcanvasInstance?.hide()
    }

    /**
     * Toggles the offcanvas.
     */
    public override fun toggle() {
        isShown = !isShown
        offcanvasInstance?.toggle()
    }

    override fun onInsert() {
        if (renderConfig.isDom) {
            offcanvasInstance = Bootstrap.Offcanvas(element)
            if (isShown) show()
        }
    }

    override fun onRemove() {
        if (renderConfig.isDom) {
            offcanvasInstance?.dispose()
            offcanvasInstance = null
            isShown = false
        }
    }

    public companion object {
        init {
            BootstrapModule.initialize()
        }

        internal var idCounter = 0
    }
}

@Composable
private fun IComponent.offcanvasRef(
    className: String? = null,
    id: String? = null,
    content: @Composable IOffcanvas.() -> Unit,
): Offcanvas {
    val component = remember { Offcanvas(className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(className) { updateProperty(Offcanvas::className, it) }
        set(id) { updateProperty(Offcanvas::id, it) }
    }, content)
    return component
}

@Composable
private fun IComponent.offcanvas(
    className: String? = null,
    id: String? = null,
    content: @Composable IOffcanvas.() -> Unit,
) {
    val component = remember { Offcanvas(className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(className) { updateProperty(Offcanvas::className, it) }
        set(id) { updateProperty(Offcanvas::id, it) }
    }, content)
}

/**
 * Creates an [Offcanvas] component, returning a reference.
 *
 * @param caption the caption of the offcanvas
 * @param placement the placement of the offcanvas
 * @param responsiveType the responsive type of the offcanvas
 * @param closeButton determines if the close button is visible
 * @param bodyScrolling determines if the body is scrolling
 * @param backdrop determines if the backdrop is visible
 * @param escape determines if the offcanvas can be closed with Esc key
 * @param className the CSS class name
 * @param id the ID attribute of the offcanvas
 * @param content the content of the offcanvas
 * @return the [Offcanvas] component
 */
@Composable
public fun IComponent.offcanvasRef(
    caption: String? = null,
    placement: OffPlacement = OffPlacement.OffcanvasStart,
    responsiveType: OffResponsiveType? = null,
    closeButton: Boolean = true,
    bodyScrolling: Boolean = false,
    backdrop: Boolean = true,
    escape: Boolean = true,
    className: String? = null,
    id: String? = null,
    content: @Composable IOffcanvas.() -> Unit = {}
): Offcanvas {
    val offcanvasId = id ?: remember { "kilua_offcanvas_${Offcanvas.idCounter++}" }
    return offcanvasRef((responsiveType?.value ?: "offcanvas") % placement.value % className, offcanvasId) {
        setupOffcanvas(offcanvasId, bodyScrolling, escape, backdrop, caption, closeButton, content)
    }
}

/**
 * Creates an [Offcanvas] component.
 *
 * @param caption the caption of the offcanvas
 * @param placement the placement of the offcanvas
 * @param responsiveType the responsive type of the offcanvas
 * @param closeButton determines if the close button is visible
 * @param bodyScrolling determines if the body is scrolling
 * @param backdrop determines if the backdrop is visible
 * @param escape determines if the offcanvas can be closed with Esc key
 * @param className the CSS class name
 * @param id the ID attribute of the offcanvas
 * @param content the content of the offcanvas
 * @return the [Offcanvas] component
 */
@Composable
public fun IComponent.offcanvas(
    caption: String? = null,
    placement: OffPlacement = OffPlacement.OffcanvasStart,
    responsiveType: OffResponsiveType? = null,
    closeButton: Boolean = true,
    bodyScrolling: Boolean = false,
    backdrop: Boolean = true,
    escape: Boolean = true,
    className: String? = null,
    id: String? = null,
    content: @Composable IOffcanvas.() -> Unit = {}
) {
    val offcanvasId = id ?: remember { "kilua_offcanvas_${Offcanvas.idCounter++}" }
    offcanvas((responsiveType?.value ?: "offcanvas") % placement.value % className, offcanvasId) {
        setupOffcanvas(offcanvasId, bodyScrolling, escape, backdrop, caption, closeButton, content)
    }
}

@Composable
private fun IOffcanvas.setupOffcanvas(
    offcanvasId: String,
    bodyScrolling: Boolean,
    escape: Boolean,
    backdrop: Boolean,
    caption: String?,
    closeButton: Boolean,
    content: @Composable (IOffcanvas.() -> Unit)
) {
    tabindex(-1)
    ariaLabelledby("$offcanvasId-label")
    if (bodyScrolling) {
        attribute("data-bs-scroll", "true")
    }
    attribute("data-bs-keyboard", "$escape")
    attribute("data-bs-backdrop", if (backdrop && !escape) "static" else backdrop.toString())
    val component = this.cast<Offcanvas>()
    if (caption != null || closeButton) {
        div("offcanvas-header") {
            if (caption != null) {
                h5t(caption, "offcanvas-title") {
                    id("$offcanvasId-label")
                }
            }
            if (closeButton) {
                button(className = "btn-close") {
                    ariaLabel("Close")
                    onClick {
                        component.hide()
                    }
                }
            }
        }
    }
    div("offcanvas-body") {
        content() // !!! the content is called on Offcanvas receiver (component), but dom nodes are emitted inside offcanvas-body div
    }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    onEvent<Event>("hidden.bs.offcanvas") {
        component.hide()
    }
}
