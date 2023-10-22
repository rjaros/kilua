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
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.externals.SplitJsInstance
import dev.kilua.externals.SplitJsOptions
import dev.kilua.externals.splitJs
import dev.kilua.html.Tag
import dev.kilua.html.div
import dev.kilua.externals.buildCustomEventInit
import dev.kilua.utils.cast
import dev.kilua.utils.toKebabCase
import org.w3c.dom.HTMLDivElement
import kotlin.math.ceil

/**
 * Split panel direction.
 */
public enum class Dir {
    Horizontal,
    Vertical;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Split panel gutter alignment.
 */
public enum class GutterAlign {
    Center,
    Start,
    End;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Split panel component.
 */
public open class SplitPanel(
    dir: Dir = Dir.Vertical,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLDivElement>("div", className, renderConfig) {

    /**
     * Split panel direction.
     */
    public open var dir: Dir by updatingProperty(dir, skipUpdate) {
        refresh()
    }

    /**
     * The gutter size.
     */
    public open var gutterSize: Int by updatingProperty(10, skipUpdate) {
        refresh()
    }

    /**
     * The gutter align.
     */
    public open var gutterAlign: GutterAlign? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * The minimum size.
     */
    public open var minSize: Int by updatingProperty(0, skipUpdate) {
        refresh()
    }

    /**
     * The maximum size.
     */
    public open var maxSize: Int? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Expand to minimum size.
     */
    public open var expandToMin: Boolean? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * The snap offset.
     */
    public open var snapOffset: Int by updatingProperty(0, skipUpdate) {
        refresh()
    }

    /**
     * The drag interval.
     */
    public open var dragInterval: Int? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * The native Split.js instance.
     */
    public var splitJsInstance: SplitJsInstance? = null

    init {
        internalCssClasses.add("splitpanel-$dir")
        internalClassName = internalCssClasses.joinToString(" ")
        @Suppress("LeakingThis")
        updateElementClassList()
    }

    override fun onInsert() {
        super.onInsert()
        initializeSplitJs()
    }

    override fun onRemove() {
        super.onRemove()
        splitJsInstance?.destroy()
    }

    /**
     * Refresh split panel by destroying and recreating it.
     */
    protected open fun refresh() {
        splitJsInstance?.destroy()
        internalCssClasses.remove("splitpanel-horizontal")
        internalCssClasses.remove("splitpanel-vertical")
        internalCssClasses.add("splitpanel-$dir")
        internalClassName = internalCssClasses.joinToString(" ")
        updateElementClassList()
        initializeSplitJs()
    }

    /**
     * Create and initialize Split.js instance.
     */
    protected open fun initializeSplitJs() {
        if (elementAvailable && children.size == 3) {
            val mainBoundingRect = elementNullable?.getBoundingClientRect()
            val splitChildren = listOf(children[0], children[2])
            val splitter = children[1]
            val sizes = splitChildren.map {
                val boundingRect = it.cast<Tag<*>>().elementNullable?.getBoundingClientRect()
                if (dir == Dir.Horizontal) {
                    val mainHeight = mainBoundingRect?.height?.toInt() ?: 0
                    val childHeight = boundingRect?.height?.toInt() ?: 0
                    ceil(childHeight.toDouble() * 100 / mainHeight.toDouble()).toInt()
                } else {
                    val mainWidth = mainBoundingRect?.width?.toInt() ?: 0
                    val childWidth = boundingRect?.width?.toInt() ?: 0
                    ceil(childWidth.toDouble() * 100 / mainWidth.toDouble()).toInt()
                }
            }
            splitJsInstance = splitJs(splitChildren.map { it.cast<Tag<*>>().element }, SplitJsOptions(
                sizes = sizes,
                direction = dir,
                gutterSize = gutterSize,
                gutterAlign = gutterAlign,
                minSize = minSize,
                maxSize = maxSize,
                expandToMin = expandToMin,
                snapOffset = snapOffset,
                dragInterval = dragInterval,
                gutter = { _, _ ->
                    splitter.cast<Tag<*>>().className = "splitter-$dir"
                    splitter.cast<Tag<*>>().element.style.removeProperty("width")
                    splitter.cast<Tag<*>>().element.style.removeProperty("height")
                    splitter.cast<Tag<*>>().element
                },
                onDrag = { eventSizes, _ ->
                    dispatchEvent("dragSplitPanel", buildCustomEventInit(eventSizes.cast()))
                },
                onDragStart = { eventSizes, _ ->
                    dispatchEvent("dragStartSplitPanel", buildCustomEventInit(eventSizes.cast()))
                },
                onDragEnd = { eventSizes, _ ->
                    for (i in eventSizes.indices) {
                        if (dir == Dir.Horizontal) {
                            splitChildren[i].cast<Tag<*>>().setStyle(
                                "height",
                                "calc(" + eventSizes[i].toDouble() + "% - " + (gutterSize / 2) + "px)"
                            )
                        } else {
                            splitChildren[i].cast<Tag<*>>().setStyle(
                                "width",
                                "calc(" + eventSizes[i].toDouble() + "% - " + (gutterSize / 2) + "px)"
                            )
                        }
                    }
                    dispatchEvent("dragEndSplitPanel", buildCustomEventInit(eventSizes.cast()))
                }
            ))
        }
    }

}

/**
 * Create [SplitPanel] component.
 * @param dir the split panel direction
 * @param className the CSS class name
 * @param contentBuilder the content of the component declared with the help of [SplitPanelBuilder]
 * @return the [SplitPanel] component
 */
@Composable
public fun ComponentBase.splitPanel(
    dir: Dir = Dir.Vertical,
    className: String? = null,
    contentBuilder: @Composable SplitPanelBuilder.() -> Unit = {}
): SplitPanel {
    val component = remember { SplitPanel(dir, className, renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(dir) { updateProperty(SplitPanel::direction, it) }
        set(className) { updateProperty(SplitPanel::className, it) }
    }) {
        val splitPanelBuilder = SplitPanelBuilder()
        contentBuilder(splitPanelBuilder)
        splitPanelBuilder.self?.invoke(component)
        div {
            splitPanelBuilder.first?.invoke(this)
        }
        div()
        div {
            splitPanelBuilder.second?.invoke(this)
        }
    }
    return component
}
