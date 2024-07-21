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

package dev.kilua.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import dev.kilua.BootstrapModule
import dev.kilua.core.IComponent
import dev.kilua.externals.Bootstrap
import dev.kilua.html.BsBgColor
import dev.kilua.html.BsColor
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.strongt
import dev.kilua.utils.rem
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Bootstrap toast positions.
 */
public enum class ToastPosition(public val classes: String) {
    TopEnd("top-0 end-0"),
    TopStart("top-0 start-0"),
    TopCenter("top-0 start-50 translate-middle-x"),
    BottomEnd("bottom-0 end-0"),
    BottomStart("bottom-0 start-0"),
    BottomCenter("bottom-0 start-50 translate-middle-x"),
    MiddleEnd("top-50 end-0 translate-middle-y"),
    MiddleStart("top-50 start-0 translate-middle-y"),
    MiddleCenter("top-50 start-50 translate-middle")
}

/**
 * Toast parameters.
 */
internal data class Toast(
    val message: String,
    val title: String? = null,
    val color: BsColor? = null,
    val bgColor: BsBgColor? = null,
    val autohide: Boolean = true,
    val delay: Duration = 5.seconds,
    val animation: Boolean = true,
    val className: String? = null
) {
    companion object {
        init {
            BootstrapModule.initialize()
        }

        internal var counter = 0

        internal val toastsMap = mutableStateMapOf<ToastPosition, Map<Int, Toast>>()
    }
}

@Composable
internal fun IComponent.toasts() {
    if (Toast.toastsMap.isNotEmpty()) {
        div("position-relative") {
            attribute("aria-live", "polite")
            attribute("aria-atomic", "true")
            Toast.toastsMap.map { (position, toasts) ->
                div("toast-container position-fixed p-3" % position.classes) {
                    toasts.forEach { (toastId, toast) ->
                        key(toastId) {
                            div("toast" % toast.color?.value % toast.bgColor?.value % toast.className) {
                                role("alert")
                                attribute("aria-live", "assertive")
                                attribute("aria-atomic", "true")
                                attribute("data-bs-autohide", toast.autohide.toString())
                                attribute("data-bs-delay", toast.delay.inWholeMilliseconds.toString())
                                attribute("data-bs-animation", toast.animation.toString())
                                if (toast.title != null) {
                                    div("toast-header") {
                                        strongt(toast.title, "me-auto")
                                        button(className = "btn-close") {
                                            ariaLabel("Close")
                                            attribute("data-bs-dismiss", "toast")
                                        }
                                    }
                                    div("toast-body") {
                                        +toast.message
                                    }
                                } else {
                                    div("d-flex") {
                                        div("toast-body") {
                                            +toast.message
                                        }
                                        button(className = "btn-close me-2 m-auto") {
                                            ariaLabel("Close")
                                            attribute("data-bs-dismiss", "toast")
                                        }
                                    }
                                }
                                DisposableEffect(toastId) {
                                    val bsToast = Bootstrap.Toast(element)
                                    bsToast.show()
                                    onDispose {
                                        bsToast.dispose()
                                    }
                                }
                                onEvent<dev.kilua.dom.api.events.Event>("hidden.bs.toast") {
                                    val newToasts = Toast.toastsMap[position]?.filterKeys { it != toastId }
                                    if (newToasts.isNullOrEmpty()) {
                                        Toast.toastsMap.remove(position)
                                    } else {
                                        Toast.toastsMap[position] = newToasts
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Show a toast.
 * @param message the toast message
 * @param title the toast title
 * @param position the toast position
 * @param color the toast foreground color
 * @param bgColor the toast background color
 * @param autohide whether the toast should be hidden automatically
 * @param delay the toast hide delay (default 5 seconds)
 * @param animation whether the toast should be animated
 * @param className the CSS class names
 */
public fun toast(
    message: String,
    title: String? = null,
    position: ToastPosition = ToastPosition.TopEnd,
    color: BsColor? = null,
    bgColor: BsBgColor? = null,
    autohide: Boolean = true,
    delay: Duration = 5.seconds,
    animation: Boolean = true,
    className: String? = null
) {
    val toastId = Toast.counter++
    val map = Toast.toastsMap[position] ?: emptyMap()
    Toast.toastsMap[position] =
        map + (toastId to Toast(message, title, color, bgColor, autohide, delay, animation, className))
}
