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

package dev.kilua.toastify

import dev.kilua.ToastifyModule
import dev.kilua.externals.ToastOptions
import dev.kilua.externals.Toastify
import js.objects.jso
import kotlin.time.Duration

/**
 * Toast message types.
 */
public enum class ToastType(public val className: String) {
    Primary("kilua-toastify-primary"),
    Secondary("kilua-toastify-secondary"),
    Info("kilua-toastify-info"),
    Success("kilua-toastify-success"),
    Warning("kilua-toastify-warning"),
    Danger("kilua-toastify-danger"),
    Light("kilua-toastify-light"),
    Dark("kilua-toastify-dark"),
}

/**
 * Toast positions.
 */
public enum class ToastPosition {
    TopRight,
    BottomRight,
    BottomLeft,
    TopLeft
}

/**
 * Show a toast message.
 *
 * @param text message text
 * @param type toast type
 * @param position toast position
 * @param className custom CSS class names
 * @param duration toast duration (default 3 seconds)
 * @param destination URL to open on click
 * @param newWindow open destination in a new window
 * @param close show close button
 * @param avatar URL of the avatar image
 * @param stopOnFocus stop timer when the toast is focused
 * @param escapeMarkup escape HTML in the message
 * @param oldestFirst show oldest toast first
 * @param ariaLive ARIA live attribute
 * @param onClick callback function on click
 * @param callback callback function after the toast is dismissed
 */
public fun toast(
    text: String,
    type: ToastType = ToastType.Primary,
    position: ToastPosition? = null,
    className: String? = null,
    duration: Duration? = null,
    destination: String? = null,
    newWindow: Boolean? = null,
    close: Boolean? = null,
    avatar: String? = null,
    stopOnFocus: Boolean? = null,
    escapeMarkup: Boolean? = null,
    oldestFirst: Boolean? = null,
    ariaLive: String? = null,
    onClick: (() -> Unit)? = null,
    callback: (() -> Unit)? = null,
) {
    ToastifyObj.show(
        text,
        type,
        position,
        className,
        duration,
        destination,
        newWindow,
        close,
        avatar,
        stopOnFocus,
        escapeMarkup,
        oldestFirst,
        ariaLive,
        onClick,
        callback
    )
}

/**
 * Toastify component object.
 */
internal object ToastifyObj {

    init {
        ToastifyModule.initialize()
    }

    fun show(
        text: String,
        type: ToastType = ToastType.Primary,
        position: ToastPosition? = null,
        className: String? = null,
        duration: Duration? = null,
        destination: String? = null,
        newWindow: Boolean? = null,
        close: Boolean? = null,
        avatar: String? = null,
        stopOnFocus: Boolean? = null,
        escapeMarkup: Boolean? = null,
        oldestFirst: Boolean? = null,
        ariaLive: String? = null,
        onClick: (() -> Unit)? = null,
        callback: (() -> Unit)? = null
    ) {
        val positionType = when (position) {
            ToastPosition.TopRight, ToastPosition.BottomRight -> "right"
            ToastPosition.TopLeft, ToastPosition.BottomLeft -> "left"
            else -> null
        }
        val gravityType = when (position) {
            ToastPosition.TopRight, ToastPosition.TopLeft -> "top"
            ToastPosition.BottomRight, ToastPosition.BottomLeft -> "bottom"
            else -> null
        }
        val optJs = jso<ToastOptions> {
            this.text = text
            if (duration != null) this.duration = duration.inWholeMilliseconds.toInt()
            if (destination != null) this.destination = destination
            if (newWindow != null) this.newWindow = newWindow
            if (close != null) this.close = close
            if (gravityType != null) this.gravity = gravityType
            if (positionType != null) this.position = positionType
            if (avatar != null) this.avatar = avatar
            this.className = className ?: type.className
            if (stopOnFocus != null) this.stopOnFocus = stopOnFocus
            if (escapeMarkup != null) this.escapeMarkup = escapeMarkup
            if (oldestFirst != null) this.oldestFirst = oldestFirst
            if (ariaLive != null) this.ariaLive = ariaLive
            if (onClick != null) this.onClick = onClick
            if (callback != null) this.callback = callback
        }
        Toastify(optJs).showToast()
    }

}
