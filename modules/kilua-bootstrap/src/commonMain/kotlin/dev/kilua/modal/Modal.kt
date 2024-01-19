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

package dev.kilua.modal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import dev.kilua.BootstrapModule
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.externals.Bootstrap
import dev.kilua.html.Div
import dev.kilua.html.Tag
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.h5t
import dev.kilua.utils.rem
import dev.kilua.utils.toKebabCase
import web.dom.HTMLDivElement
import web.dom.events.Event

/**
 * Modal window sizes.
 */
public enum class ModalSize {
    ModalXl,
    ModalLg,
    ModalSm;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Fullscreen modal window modes.
 */
public enum class FullscreenMode {
    ModalFullscreen,
    ModalFullscreenSmDown,
    ModalFullscreenMdDown,
    ModalFullscreenLgDown,
    ModalFullscreenXlDown,
    ModalFullscreenXxlDown;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Bootstrap modal component.
 */
public open class Modal(
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLDivElement>("div", className, renderConfig = renderConfig) {

    internal var footerContent: @Composable (Div.() -> Unit)? = null

    /**
     * Whether the modal should be visible.
     */
    protected var isShown: Boolean = false

    /**
     * The native Bootstrap modal instance.
     */
    protected var modalInstance: Bootstrap.Modal? = null

    /**
     * Configure footer of this Modal.
     */
    public fun footer(content: @Composable Div.() -> Unit) {
        footerContent = content
    }

    /**
     * Shows the modal window.
     */
    public override fun show() {
        isShown = true
        modalInstance?.show()
    }

    /**
     * Hides the modal window.
     */
    public override fun hide() {
        isShown = false
        modalInstance?.hide()
    }

    /**
     * Toggles the modal window.
     */
    public override fun toggle() {
        isShown = !isShown
        modalInstance?.toggle()
    }

    override fun onInsert() {
        if (renderConfig.isDom) {
            modalInstance = Bootstrap.Modal(element)
            if (isShown) show()
        }
    }

    override fun onRemove() {
        if (renderConfig.isDom) {
            modalInstance?.dispose()
            modalInstance = null
            isShown = false
        }
    }

    public companion object {
        init {
            BootstrapModule.initialize()
        }

        internal var counter = 0

        internal val modalStateMap = mutableStateMapOf<Int, @Composable ComponentBase.() -> Unit>()
    }
}

@Composable
internal fun ComponentBase.modals() {
    div {
        Modal.modalStateMap.values.forEach { it() }
    }
}

@Composable
private fun ComponentBase.modal(
    className: String? = null,
    content: @Composable Modal.() -> Unit,
): Modal {
    val component = remember { Modal(className, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(className) { updateProperty(Modal::className, it) }
    }, content)
    return component
}


/**
 * Creates a [Modal] component.
 *
 * @param caption the caption of the modal window
 * @param closeButton determines if the close button is visible
 * @param size the size of the modal window
 * @param fullscreenMode the fullscreen mode of the modal window
 * @param animation determines if the modal window is animated
 * @param centered determines if the modal window is vertically centered
 * @param scrollable determines if the modal window content is scrollable
 * @param escape determines if the modal window can be closed by pressing the escape key
 * @param className the CSS class name
 * @param content the content of the modal dialog
 * @return the [Modal] component
 */
@Composable
public fun ComponentBase.modal(
    caption: String? = null,
    closeButton: Boolean = true,
    size: ModalSize? = null,
    fullscreenMode: FullscreenMode? = null,
    animation: Boolean = true,
    centered: Boolean = false,
    scrollable: Boolean = false,
    escape: Boolean = true,
    className: String? = null,
    content: @Composable Modal.() -> Unit = {}
): Modal {
    return modal("modal" % if (animation) "fade" else null % className) {
        role = "dialog"
        tabindex = -1
        setAttribute("data-bs-keyboard", "$escape")
        setAttribute("data-bs-backdrop", if (escape) "true" else "static")
        val component = this
        div("modal-dialog" % size?.value % fullscreenMode?.value % if (centered) "modal-dialog-centered" else null % if (scrollable) "modal-dialog-scrollable" else null) {
            div("modal-content") {
                if (caption != null || closeButton) {
                    div("modal-header") {
                        if (caption != null) {
                            h5t(caption, "modal-title")
                        }
                        if (closeButton) {
                            button(className = "btn-close") {
                                ariaLabel = "Close"
                                onClick {
                                    component.hide()
                                }
                            }
                        }
                    }
                }
                div("modal-body") {
                    content() // !!! the content is called on Modal receiver (component), but dom nodes are emitted inside modal-body div
                }
                div("modal-footer") {
                    component.footerContent?.invoke(this)
                }
            }
        }
        DisposableEffect(component.componentId) {
            component.onInsert()
            onDispose {
                component.onRemove()
            }
        }
        onEvent<Event>("hidden.bs.modal") {
            component.hide()
        }
    }
}
