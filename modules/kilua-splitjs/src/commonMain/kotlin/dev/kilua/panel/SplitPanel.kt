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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.SplitJsInstance
import dev.kilua.externals.SplitJsOptions
import dev.kilua.externals.buildCustomEventInit
import dev.kilua.externals.splitJs
import dev.kilua.html.IDiv
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.html.div
import dev.kilua.utils.cast
import dev.kilua.utils.toKebabCase
import dev.kilua.dom.api.HTMLDivElement
import kotlin.math.ceil

internal const val SPLIT_PANEL_DEFAULT_GUTTER_SIZE = 10

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
public interface ISplitPanel : ITag<HTMLDivElement> {
    /**
     * Split panel direction.
     */
    public val dir: Dir

    /**
     * Set the split panel direction.
     */
    @Composable
    public fun dir(dir: Dir)

    /**
     * The gutter size.
     */
    public val gutterSize: Int

    /**
     * Set the gutter size.
     */
    @Composable
    public fun gutterSize(gutterSize: Int)

    /**
     * The gutter align.
     */
    public val gutterAlign: GutterAlign?

    /**
     * Set the gutter align.
     */
    @Composable
    public fun gutterAlign(gutterAlign: GutterAlign?)

    /**
     * The minimum size.
     */
    public val minSize: Int

    /**
     * Set the minimum size.
     */
    @Composable
    public fun minSize(minSize: Int)

    /**
     * The maximum size.
     */
    public val maxSize: Int?

    /**
     * Set the maximum size.
     */
    @Composable
    public fun maxSize(maxSize: Int?)

    /**
     * Expand to minimum size.
     */
    public val expandToMin: Boolean?

    /**
     * Set expand to minimum size.
     */
    @Composable
    public fun expandToMin(expandToMin: Boolean?)

    /**
     * The snap offset.
     */
    public val snapOffset: Int

    /**
     * Set the snap offset.
     */
    @Composable
    public fun snapOffset(snapOffset: Int)

    /**
     * The drag interval.
     */
    public val dragInterval: Int?

    /**
     * Set the drag interval.
     */
    @Composable
    public fun dragInterval(dragInterval: Int?)

    /**
     * The native Split.js instance.
     */
    public val splitJsInstance: SplitJsInstance?

    /**
     * Configure left side of the SplitPanel.
     */
    public fun left(content: @Composable IDiv.() -> Unit)

    /**
     * Configure top side of the SplitPanel.
     */
    public fun top(content: @Composable IDiv.() -> Unit)

    /**
     * Configure right side of the SplitPanel.
     */
    public fun right(content: @Composable IDiv.() -> Unit)

    /**
     * Configure bottom side of the SplitPanel.
     */
    public fun bottom(content: @Composable IDiv.() -> Unit)
}


/**
 * Split panel component.
 */
public open class SplitPanel(
    dir: Dir = Dir.Vertical,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLDivElement>("div", className, id, renderConfig = renderConfig), ISplitPanel {

    /**
     * Split panel direction.
     */
    public override var dir: Dir by updatingProperty(dir) {
        refresh()
    }

    /**
     * Set the split panel direction.
     */
    @Composable
    public override fun dir(dir: Dir): Unit = composableProperty("dir", {
        this.dir = Dir.Vertical
    }) {
        this.dir = dir
    }

    /**
     * The gutter size.
     */
    public override var gutterSize: Int by updatingProperty(SPLIT_PANEL_DEFAULT_GUTTER_SIZE) {
        refresh()
    }

    /**
     * Set the gutter size.
     */
    @Composable
    public override fun gutterSize(gutterSize: Int): Unit = composableProperty("gutterSize", {
        this.gutterSize = SPLIT_PANEL_DEFAULT_GUTTER_SIZE
    }) {
        this.gutterSize = gutterSize
    }

    /**
     * The gutter align.
     */
    public override var gutterAlign: GutterAlign? by updatingProperty {
        refresh()
    }

    /**
     * Set the gutter align.
     */
    @Composable
    public override fun gutterAlign(gutterAlign: GutterAlign?): Unit = composableProperty("gutterAlign", {
        this.gutterAlign = null
    }) {
        this.gutterAlign = gutterAlign
    }

    /**
     * The minimum size.
     */
    public override var minSize: Int by updatingProperty(0) {
        refresh()
    }

    /**
     * Set the minimum size.
     */
    @Composable
    public override fun minSize(minSize: Int): Unit = composableProperty("minSize", {
        this.minSize = 0
    }) {
        this.minSize = minSize
    }

    /**
     * The maximum size.
     */
    public override var maxSize: Int? by updatingProperty {
        refresh()
    }

    /**
     * Set the maximum size.
     */
    @Composable
    public override fun maxSize(maxSize: Int?): Unit = composableProperty("maxSize", {
        this.maxSize = null
    }) {
        this.maxSize = maxSize
    }

    /**
     * Expand to minimum size.
     */
    public override var expandToMin: Boolean? by updatingProperty {
        refresh()
    }

    /**
     * Set expand to minimum size.
     */
    @Composable
    public override fun expandToMin(expandToMin: Boolean?): Unit = composableProperty("expandToMin", {
        this.expandToMin = null
    }) {
        this.expandToMin = expandToMin
    }

    /**
     * The snap offset.
     */
    public override var snapOffset: Int by updatingProperty(0) {
        refresh()
    }

    /**
     * Set the snap offset.
     */
    @Composable
    public override fun snapOffset(snapOffset: Int): Unit = composableProperty("snapOffset", {
        this.snapOffset = 0
    }) {
        this.snapOffset = snapOffset
    }

    /**
     * The drag interval.
     */
    public override var dragInterval: Int? by updatingProperty {
        refresh()
    }

    /**
     * Set the drag interval.
     */
    @Composable
    public override fun dragInterval(dragInterval: Int?): Unit = composableProperty("dragInterval", {
        this.dragInterval = null
    }) {
        this.dragInterval = dragInterval
    }

    /**
     * The native Split.js instance.
     */
    public override var splitJsInstance: SplitJsInstance? = null

    internal var first by mutableStateOf<@Composable (IDiv.() -> Unit)?>(null)
    internal var second by mutableStateOf<@Composable (IDiv.() -> Unit)?>(null)

    init {
        internalCssClasses.add("splitpanel-$dir")
        internalClassName = internalCssClasses.joinToString(" ")
        @Suppress("LeakingThis")
        initElementClassList()
    }

    /**
     * Configure left side of the SplitPanel.
     */
    public override fun left(content: @Composable IDiv.() -> Unit) {
        first = content
    }

    /**
     * Configure top side of the SplitPanel.
     */
    public override fun top(content: @Composable IDiv.() -> Unit) {
        first = content
    }

    /**
     * Configure right side of the SplitPanel.
     */
    public override fun right(content: @Composable IDiv.() -> Unit) {
        second = content
    }

    /**
     * Configure bottom side of the SplitPanel.
     */
    public override fun bottom(content: @Composable IDiv.() -> Unit) {
        second = content
    }

    override fun onInsert() {
        initializeSplitJs()
    }

    override fun onRemove() {
        splitJsInstance?.destroy()
        splitJsInstance = null
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
    @Suppress("MagicNumber")
    protected open fun initializeSplitJs() {
        if (renderConfig.isDom && children.size == 3) {
            val mainBoundingRect = element.getBoundingClientRect()
            val splitChildren = listOf(children[0], children[2])
            val splitter = children[1]
            val sizes = splitChildren.map {
                val boundingRect = it.cast<Tag<*>>().element.getBoundingClientRect()
                if (dir == Dir.Horizontal) {
                    val mainHeight = mainBoundingRect.height.toInt()
                    val childHeight = boundingRect.height.toInt()
                    ceil(childHeight.toDouble() * 100 / mainHeight.toDouble()).toInt()
                } else {
                    val mainWidth = mainBoundingRect.width.toInt()
                    val childWidth = boundingRect.width.toInt()
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
 * Create [SplitPanel] component, returning a reference.
 * @param dir the split panel direction
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 * @return the [SplitPanel] component
 */
@Composable
public fun IComponent.splitPanelRef(
    dir: Dir = Dir.Vertical,
    className: String? = null,
    id: String? = null,
    content: @Composable ISplitPanel.() -> Unit = {}
): SplitPanel {
    val component = remember { SplitPanel(dir, className, id, renderConfig = renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(dir) { updateProperty(SplitPanel::direction, it) }
        set(className) { updateProperty(SplitPanel::className, it) }
        set(id) { updateProperty(SplitPanel::id, it) }
    }) {
        content()
        div {
            component.first?.invoke(this)
        }
        div()
        div {
            component.second?.invoke(this)
        }
    }
    return component
}

/**
 * Create [SplitPanel] component.
 * @param dir the split panel direction
 * @param className the CSS class name
 * @param id the ID attribute of the component
 * @param content the content of the component
 * @return the [SplitPanel] component
 */
@Composable
public fun IComponent.splitPanel(
    dir: Dir = Dir.Vertical,
    className: String? = null,
    id: String? = null,
    content: @Composable ISplitPanel.() -> Unit = {}
) {
    val component = remember { SplitPanel(dir, className, id, renderConfig = renderConfig) }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(dir) { updateProperty(SplitPanel::direction, it) }
        set(className) { updateProperty(SplitPanel::className, it) }
        set(id) { updateProperty(SplitPanel::id, it) }
    }) {
        content()
        div {
            component.first?.invoke(this)
        }
        div()
        div {
            component.second?.invoke(this)
        }
    }
}
