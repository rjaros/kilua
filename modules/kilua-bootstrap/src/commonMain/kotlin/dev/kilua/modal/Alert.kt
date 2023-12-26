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
import dev.kilua.core.ComponentBase
import dev.kilua.html.Button
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.unaryPlus
import web.dom.events.Event


/**
 * Shows the alert dialog.
 *
 * @param caption the caption of the alert window
 * @param content the content of the alert window
 * @param size the size of the modal window
 * @param fullscreenMode the fullscreen mode of the modal window
 * @param animation determines if the modal window is animated
 * @param centered determines if the modal window is vertically centered
 * @param scrollable determines if the modal window content is scrollable
 * @param callback the callback function that is called when the alert window is hidden
 */
public fun alert(
    caption: String? = null,
    content: String? = null,
    size: ModalSize? = null,
    fullscreenMode: FullscreenMode? = null,
    animation: Boolean = true,
    centered: Boolean = false,
    scrollable: Boolean = false,
    callback: (() -> Unit)? = null
) {
    val modalId = Modal.counter++
    val modalComposable: @Composable ComponentBase.() -> Unit = {
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
            content?.let { div("text-start") { +it } }
            lateinit var button: Button
            footer {
                button = button("OK", "fas fa-check", className = "btn btn-primary") {
                    onClick {
                        component.hide()
                    }
                }
            }
            onEvent<Event>("hidden.bs.modal") {
                callback?.invoke()
                Modal.modalStateMap.remove(modalId)
            }
            onEvent<Event>("shown.bs.modal") {
                button.focus()
            }
            show()
        }
    }
    Modal.modalStateMap[modalId] = modalComposable
}
