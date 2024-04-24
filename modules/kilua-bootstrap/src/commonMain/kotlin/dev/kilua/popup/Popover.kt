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

package dev.kilua.popup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import dev.kilua.externals.Bootstrap
import dev.kilua.externals.obj
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.utils.cast
import kotlin.time.Duration

/**
 * Add a popover to this component.
 *
 * @param content popover content
 * @param title popover title
 * @param animation use animation
 * @param delay delay before showing the popover (default 500ms)
 * @param hideDelay delay before hiding the popover (defaults to delay)
 * @param placement popover placement
 * @param triggers popover triggers
 * @param html use HTML in popover
 * @param sanitize sanitize HTML in popover
 *
 * @return Bootstrap popover instance
 */
@Composable
public fun ITag<*>.popover(
    content: String,
    title: String? = null,
    animation: Boolean = true,
    delay: Duration? = null,
    hideDelay: Duration? = null,
    placement: Placement? = null,
    triggers: List<Trigger>? = null,
    html: Boolean = false,
    sanitize: Boolean = true
): Bootstrap.Popover {
    disposeTooltip()
    val popoverTrigger = triggers?.joinToString(" ") { it.value }
    val popoverDelay: Bootstrap.PopoverDelay? = if (delay != null && hideDelay != null) {
        obj {
            show = delay.inWholeMilliseconds.toInt()
            hide = hideDelay.inWholeMilliseconds.toInt()
        }
    } else if (delay != null) {
        obj {
            show = delay.inWholeMilliseconds.toInt()
            hide = delay.inWholeMilliseconds.toInt()
        }
    } else if (hideDelay != null) {
        obj {
            show = 0
            hide = hideDelay.inWholeMilliseconds.toInt()
        }
    } else null
    val popover = Bootstrap.Popover(element, obj {
        this.content = content
        if (title != null) this.title = title
        this.animation = animation
        if (popoverDelay != null) this.delay = popoverDelay
        if (placement != null) this.placement = placement.value
        if (popoverTrigger != null) this.trigger = popoverTrigger
        this.html = html
        this.sanitize = sanitize
    })

    DisposableEffect("kilua_popover_${this.cast<Tag<*>>().componentId}") {
        popover.enable()
        onDispose {
            popover.disable()
        }
    }
    return popover
}

/**
 * Show popover.
 */
public fun ITag<*>.showPopover() {
    Bootstrap.Popover.getInstance(element)?.show()
}

/**
 * Hide popover.
 */
public fun ITag<*>.hidePopover() {
    Bootstrap.Popover.getInstance(element)?.hide()
}

/**
 * Toggle popover.
 */
public fun ITag<*>.togglePopover() {
    Bootstrap.Popover.getInstance(element)?.toggle()
}

/**
 * Enable popover.
 */
public fun ITag<*>.enablePopover() {
    Bootstrap.Popover.getInstance(element)?.enable()
}

/**
 * Disable popover.
 */
public fun ITag<*>.disablePopover() {
    Bootstrap.Popover.getInstance(element)?.disable()
}

/**
 * Dispose popover.
 */
public fun ITag<*>.disposePopover() {
    Bootstrap.Popover.getInstance(element)?.dispose()
}
