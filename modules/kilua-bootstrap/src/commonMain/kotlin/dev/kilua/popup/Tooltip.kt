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
import dev.kilua.html.Tag
import kotlin.time.Duration

/**
 * Add a tooltip to this component.
 *
 * @param title tooltip title
 * @param animation use animation
 * @param delay delay before showing the tooltip (default 500ms)
 * @param hideDelay delay before hiding the tooltip (defaults to delay)
 * @param placement tooltip placement
 * @param triggers tooltip triggers
 * @param html use HTML in tooltip
 * @param sanitize sanitize HTML in tooltip
 *
 * @return Bootstrap tooltip instance
 */
@Composable
public fun Tag<*>.tooltip(
    title: String,
    animation: Boolean = true,
    delay: Duration? = null,
    hideDelay: Duration? = null,
    placement: Placement? = null,
    triggers: List<Trigger>? = null,
    html: Boolean = false,
    sanitize: Boolean = true
): Bootstrap.Tooltip {
    disposePopover()
    val tooltipTrigger = triggers?.joinToString(" ") { it.value }
    val tooltipDelay: Bootstrap.TooltipDelay? = if (delay != null && hideDelay != null) {
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
    val tooltip = Bootstrap.Tooltip(element, obj {
        this.title = title
        this.animation = animation
        if (tooltipDelay != null) this.delay = tooltipDelay
        if (placement != null) this.placement = placement.value
        if (tooltipTrigger != null) this.trigger = tooltipTrigger
        this.html = html
        this.sanitize = sanitize
    })
    DisposableEffect("kilua_tooltip_${this.componentId}") {
        tooltip.enable()
        onDispose {
            tooltip.disable()
        }
    }
    return tooltip
}

/**
 * Show tooltip.
 */
public fun Tag<*>.showTooltip() {
    Bootstrap.Tooltip.getInstance(element)?.show()
}

/**
 * Hide tooltip.
 */
public fun Tag<*>.hideTooltip() {
    Bootstrap.Tooltip.getInstance(element)?.hide()
}

/**
 * Toggle tooltip.
 */
public fun Tag<*>.toggleTooltip() {
    Bootstrap.Tooltip.getInstance(element)?.toggle()
}

/**
 * Enable tooltip.
 */
public fun Tag<*>.enableTooltip() {
    Bootstrap.Tooltip.getInstance(element)?.enable()
}

/**
 * Disable tooltip.
 */
public fun Tag<*>.disableTooltip() {
    Bootstrap.Tooltip.getInstance(element)?.disable()
}

/**
 * Dispose tooltip.
 */
public fun Tag<*>.disposeTooltip() {
    Bootstrap.Tooltip.getInstance(element)?.dispose()
}
