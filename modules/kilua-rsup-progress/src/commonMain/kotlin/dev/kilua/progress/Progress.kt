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

package dev.kilua.progress

import dev.kilua.externals.RsupProgress
import dev.kilua.externals.RsupProgressOptions
import dev.kilua.externals.RsupProgressPromiseOptions
import dev.kilua.externals.obj
import dev.kilua.html.Color
import dev.kilua.utils.isDom
import dev.kilua.utils.toKebabCase
import dev.kilua.dom.JsAny
import dev.kilua.dom.Promise
import kotlin.time.Duration

/**
 * Position of the progress indicator.
 */
public enum class ProgressPosition {
    Top,
    Bottom,
    None;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

}

/**
 * Options for the progress indicator.
 */
public data class ProgressOptions(
    val height: Int? = null,
    val className: String? = null,
    val color: Color? = null,
    val container: JsAny? = null,
    val maxWidth: String? = null,
    val position: ProgressPosition? = null,
    val duration: Duration? = null,
    val hideDuration: Duration? = null,
    val zIndex: Int? = null,
    val timing: String? = null
)

internal fun ProgressOptions.toJs(): RsupProgressOptions {
    val self = this
    return obj {
        if (self.height != null) this.height = self.height
        if (self.className != null) this.className = self.className
        if (self.color != null) this.color = self.color.value
        if (self.container != null) this.container = self.container
        if (self.maxWidth != null) this.maxWidth = self.maxWidth
        if (self.position != null) this.position = self.position.value
        if (self.duration != null) this.duration = self.duration.inWholeMilliseconds.toInt()
        if (self.hideDuration != null) this.hideDuration = self.hideDuration.inWholeMilliseconds.toInt()
        if (self.zIndex != null) this.zIndex = self.zIndex
        if (self.timing != null) this.timing = self.timing
    }
}

/**
 * Options for the progress indicator promise function.
 */
public data class ProgressPromiseOptions(
    val min: Duration? = null,
    val delay: Duration? = null,
    val waitAnimation: Boolean? = null
)

internal fun ProgressPromiseOptions.toJs(): RsupProgressPromiseOptions {
    val self = this
    return obj {
        if (self.min != null) this.min = self.min.inWholeMilliseconds.toInt()
        if (self.delay != null) this.delay = self.delay.inWholeMilliseconds.toInt()
        if (self.waitAnimation != null) this.waitAnimation = self.waitAnimation
    }
}

/**
 * The progress indicator.
 */
public open class Progress(options: ProgressOptions = ProgressOptions()) {
    private val rsupProgress = if (isDom) RsupProgress(options.toJs()) else null

    /**
     * Whether the indicator is in progress.
     */
    public val isInProgress: Boolean
        get() = rsupProgress?.isInProgress ?: false

    /**
     * Change the options for the progress indicator.
     */
    public fun setOptions(options: ProgressOptions) {
        rsupProgress?.setOptions(options.toJs())
    }

    /**
     * Start the progress indicator.
     */
    public fun start() {
        rsupProgress?.start()
    }

    /**
     * Stop the progress indicator.
     */
    public fun end(immediately: Boolean = false) {
        rsupProgress?.end(immediately)
    }

    /**
     * Start a progress indicator for a given promise.
     */
    public fun <T : JsAny> promise(
        promise: Promise<T>,
        options: ProgressPromiseOptions? = null
    ): Promise<T> {
        return if (rsupProgress != null) {
            if (options != null) {
                rsupProgress.promise(promise, options.toJs())
            } else {
                rsupProgress.promise(promise)
            }
        } else {
            promise
        }
    }
}
