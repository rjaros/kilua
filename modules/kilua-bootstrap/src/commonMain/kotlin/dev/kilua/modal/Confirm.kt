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
import dev.kilua.html.ButtonStyle
import dev.kilua.html.bsButton
import dev.kilua.html.bsButtonRef
import dev.kilua.html.div
import dev.kilua.html.rawHtml
import web.dom.events.Event


/**
 * Shows the confirmation dialog.
 *
 * @param caption the caption of the confirmation dialog
 * @param content the content of the confirmation dialog
 * @param rich determines if the window should display rich HTML content
 * @param size the size of the modal window
 * @param fullscreenMode the fullscreen mode of the modal window
 * @param animation determines if the modal window is animated
 * @param centered determines if the modal window is vertically centered
 * @param scrollable determines if the modal window content is scrollable
 * @param cancelVisible determines if the Cancel button is visible
 * @param yesTitle the title of the Yes button
 * @param yesIcon the icon of the Yes button
 * @param noTitle the title of the No button
 * @param noIcon the icon of the No button
 * @param cancelTitle the title of the Cancel button
 * @param cancelIcon the icon of the Cancel button
 * @param noCallback the callback function that is called when the No button is selected
 * @param yesCallback the callback function that is called when the Yes button is selected
 */
public fun confirm(
    caption: String? = null,
    content: String? = null,
    rich: Boolean = false,
    size: ModalSize? = null,
    fullscreenMode: FullscreenMode? = null,
    animation: Boolean = true,
    centered: Boolean = false,
    scrollable: Boolean = false,
    cancelVisible: Boolean = false,
    yesTitle: String = "Yes",
    yesIcon: String? = "fas fa-check",
    noTitle: String = "No",
    noIcon: String? = "fas fa-ban",
    cancelTitle: String = "Cancel",
    cancelIcon: String? = "fas fa-times",
    noCallback: (() -> Unit)? = null,
    yesCallback: (() -> Unit)? = null
) {
    val modalId = Modal.counter++
    val modalComposable: @Composable IComponent.() -> Unit = {
        modal(
            caption,
            cancelVisible,
            null,
            size,
            fullscreenMode,
            animation,
            centered,
            scrollable,
            cancelVisible,
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
            lateinit var yesButton: Button
            var callback: (() -> Unit)? = null
            footer {
                if (cancelVisible) {
                    bsButton(cancelTitle, cancelIcon, style = ButtonStyle.BtnSecondary) {
                        onClick {
                            component.hide()
                        }
                    }
                }
                bsButton(noTitle, noIcon, style = ButtonStyle.BtnDanger) {
                    onClick {
                        callback = noCallback
                        component.hide()
                    }
                }
                yesButton = bsButtonRef(yesTitle, yesIcon) {
                    onClick {
                        callback = yesCallback
                        component.hide()
                    }
                }
            }
            onEvent<Event>("hidden.bs.modal") {
                Modal.modalStateMap.remove(modalId)
                callback?.invoke()
            }
            onEvent<Event>("shown.bs.modal") {
                yesButton.focus()
            }
            show()
        }
    }
    Modal.modalStateMap[modalId] = modalComposable
}
