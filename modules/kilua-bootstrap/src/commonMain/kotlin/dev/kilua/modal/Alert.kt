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
import dev.kilua.core.IComponent
import dev.kilua.html.Button
import dev.kilua.html.bsButtonRef
import dev.kilua.html.div
import dev.kilua.html.rawHtml
import web.dom.events.Event


/**
 * Shows the alert dialog.
 *
 * @param caption the caption of the alert dialog
 * @param content the content of the alert dialog
 * @param rich determines if the window should display rich HTML content
 * @param size the size of the modal window
 * @param fullscreenMode the fullscreen mode of the modal window
 * @param animation determines if the modal window is animated
 * @param centered determines if the modal window is vertically centered
 * @param scrollable determines if the modal window content is scrollable
 * @param okTitle the title of the OK button
 * @param okIcon the icon of the OK button
 * @param callback the callback function that is called when the alert window is hidden
 */
public fun alert(
    caption: String? = null,
    content: String? = null,
    rich: Boolean = false,
    size: ModalSize? = null,
    fullscreenMode: FullscreenMode? = null,
    animation: Boolean = true,
    centered: Boolean = false,
    scrollable: Boolean = false,
    okTitle: String = "OK",
    okIcon: String? = "fas fa-check",
    callback: (() -> Unit)? = null
) {
    val modalId = Modal.counter++
    val modalComposable: @Composable IComponent.() -> Unit = {
        modal(
            caption,
            true,
            size,
            fullscreenMode,
            animation,
            centered,
            scrollable,
            true,
            null,
        ) {
            val component = this
            content?.let {
                div("text-start") {
                    if (!rich) {
                        +it
                    } else {
                        rawHtml(it)
                    }
                }
            }
            lateinit var button: Button
            footer {
                button = bsButtonRef(okTitle, okIcon) {
                    onClick {
                        component.hide()
                    }
                }
            }
            onEvent<Event>("hidden.bs.modal") {
                Modal.modalStateMap.remove(modalId)
                callback?.invoke()
            }
            onEvent<Event>("shown.bs.modal") {
                button.focus()
            }
            show()
        }
    }
    Modal.modalStateMap[modalId] = modalComposable
}
