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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.html.CommentNode
import dev.kilua.html.IDiv
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.html.button
import dev.kilua.html.commentNode
import dev.kilua.html.div
import dev.kilua.html.li
import dev.kilua.html.ul
import dev.kilua.utils.buildCustomEventInit
import dev.kilua.utils.cast
import dev.kilua.utils.rem
import web.html.HTMLDivElement
import kotlin.js.toJsString

/**
 * Tab position.
 */
public enum class TabPosition {
    Top,
    Left,
    Right
}

/**
 * Left or right tab size.
 */
public enum class SideTabSize {
    Size1,
    Size2,
    Size3,
    Size4,
    Size5,
    Size6
}

internal data class Tab(
    val label: String? = null,
    val icon: String? = null,
    val closable: Boolean = false,
    val content: @Composable IDiv.() -> Unit
)

/**
 * Tab panel component.
 */
public interface ITabPanel : ITag<HTMLDivElement> {
    //
    // Additional functions are required because @Composable functions
    // can't have default parameters.
    //

    public var activeIndex: Int

    /**
     * Adds a tab.
     * @param label the label of the tab
     * @param content the content of the tab
     */
    @Composable
    public fun tab(
        label: String,
        content: @Composable IDiv.() -> Unit
    )

    /**
     * Adds a tab.
     * @param label the label of the tab
     * @param icon the icon of the tab
     * @param content the content of the tab
     */
    @Composable
    public fun tab(
        label: String,
        icon: String,
        content: @Composable IDiv.() -> Unit
    )

    /**
     * Adds a tab.
     * @param label the label of the tab
     * @param icon the icon of the tab
     * @param closable determines if the tab is closable
     * @param content the content of the tab
     */
    @Composable
    public fun tab(
        label: String?,
        icon: String?,
        closable: Boolean,
        content: @Composable IDiv.() -> Unit
    )
}


/**
 * Tab panel component.
 */
public open class TabPanel(
    activeIndex: Int = -1,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLDivElement>("div", className, id, renderConfig = renderConfig), ITabPanel {

    public override var activeIndex: Int by updatingProperty(activeIndex, notifyFunction = {
        activeIndexState = it
    })

    internal var activeIndexState: Int by mutableStateOf(activeIndex)
    internal val tabs = mutableStateMapOf<Int, Tab>()
    internal var tabsOrderList: List<Int> by mutableStateOf(emptyList())

    /**
     * Adds a tab.
     * @param label the label of the tab
     * @param content the content of the tab
     */
    @Composable
    public override fun tab(
        label: String,
        content: @Composable IDiv.() -> Unit
    ) {
        tab(label, null, false, content)
    }

    /**
     * Adds a tab.
     * @param label the label of the tab
     * @param icon the icon of the tab
     * @param content the content of the tab
     */
    @Composable
    public override fun tab(
        label: String,
        icon: String,
        content: @Composable IDiv.() -> Unit
    ) {
        tab(label, icon, false, content)
    }

    /**
     * Adds a tab.
     * @param label the label of the tab
     * @param icon the icon of the tab
     * @param closable determines if the tab is closable
     * @param content the content of the tab
     */
    @Composable
    public override fun tab(
        label: String?,
        icon: String?,
        closable: Boolean,
        content: @Composable IDiv.() -> Unit
    ) {
        val tabId = remember { idCounter++ }
        commentNode("tid=$tabId")
        val tab = Tab(label, icon, closable, content)
        if (tabs[tabId] != tab) {
            tabs[tabId] = tab
        }
        DisposableEffect(tabId) {
            refreshTabsOrderList()
            if (activeIndex == -1) {
                updateProperty(::activeIndex, 0)
            }
            onDispose {
                refreshTabsOrderList()
                tabs.remove(tabId)
                if (activeIndex >= tabs.size) {
                    updateProperty(::activeIndex, tabs.size - 1)
                }
            }
        }

    }

    private fun refreshTabsOrderList() {
        tabsOrderList = this.children.filterIsInstance<CommentNode>().filter { it.data.startsWith("tid=") }
            .mapNotNull { it.data.drop("tid=".length).toIntOrNull() }
    }

    public companion object {
        internal var idCounter: Int = 0
    }
}

@Composable
private fun IComponent.tabPanelRef(
    activeIndex: Int = -1,
    className: String? = null,
    id: String? = null,
    content: @Composable ITabPanel.() -> Unit,
): TabPanel {
    val component = remember { TabPanel(activeIndex, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(activeIndex) { updateProperty(TabPanel::activeIndex, it) }
        set(className) { updateProperty(TabPanel::className, it) }
        set(id) { updateProperty(TabPanel::id, it) }
    }, content)
    return component
}

@Composable
private fun IComponent.tabPanel(
    activeIndex: Int = -1,
    className: String? = null,
    id: String? = null,
    content: @Composable ITabPanel.() -> Unit,
) {
    val component = remember { TabPanel(activeIndex, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(activeIndex) { updateProperty(TabPanel::activeIndex, it) }
        set(className) { updateProperty(TabPanel::className, it) }
        set(id) { updateProperty(TabPanel::id, it) }
    }, content)
}

/**
 * Creates a [TabPanel] component, returning a reference.
 *
 * @param tabPosition the tabs position
 * @param sideTabSize the size of tabs for left or right position
 * @param scrollableTabs determines if the tabs are scrollable
 * @param draggableTabs determines if the tabs are draggable
 * @param activeIndex the index of the active tab
 * @param className the CSS class name
 * @param content the content of the tab panel
 * @return the [TabPanel] component
 */
@Composable
public fun IComponent.tabPanelRef(
    tabPosition: TabPosition = TabPosition.Top,
    sideTabSize: SideTabSize = SideTabSize.Size3,
    scrollableTabs: Boolean = false,
    draggableTabs: Boolean = false,
    activeIndex: Int = -1,
    className: String? = null,
    id: String? = null,
    content: @Composable ITabPanel.() -> Unit,
): TabPanel {
    val tabPanelId = remember { "kilua_tab_panel_${TabPanel.idCounter++}" }
    val tabPanelClassName = className % "kilua-tab-panel"
    return tabPanelRef(activeIndex, tabPanelClassName, id) {
        setupTabPanel(content, tabPosition, scrollableTabs, tabPanelId, draggableTabs, activeIndex, sideTabSize)
    }
}

/**
 * Creates a [TabPanel] component.
 *
 * @param tabPosition the tabs position
 * @param sideTabSize the size of tabs for left or right position
 * @param scrollableTabs determines if the tabs are scrollable
 * @param draggableTabs determines if the tabs are draggable
 * @param activeIndex the index of the active tab
 * @param className the CSS class name
 * @param content the content of the tab panel
 * @return the [TabPanel] component
 */
@Composable
public fun IComponent.tabPanel(
    tabPosition: TabPosition = TabPosition.Top,
    sideTabSize: SideTabSize = SideTabSize.Size3,
    scrollableTabs: Boolean = false,
    draggableTabs: Boolean = false,
    activeIndex: Int = -1,
    className: String? = null,
    id: String? = null,
    content: @Composable ITabPanel.() -> Unit,
) {
    val tabPanelId = remember { "kilua_tab_panel_${TabPanel.idCounter++}" }
    val tabPanelClassName = className % "kilua-tab-panel"
    tabPanel(activeIndex, tabPanelClassName, id) {
        setupTabPanel(content, tabPosition, scrollableTabs, tabPanelId, draggableTabs, activeIndex, sideTabSize)
    }
}

@Composable
private fun ITabPanel.setupTabPanel(
    content: @Composable (ITabPanel.() -> Unit),
    tabPosition: TabPosition,
    scrollableTabs: Boolean,
    tabPanelId: String,
    draggableTabs: Boolean,
    activeIndex: Int,
    sideTabSize: SideTabSize
) {
    content()
    val navClasses = when (tabPosition) {
        TabPosition.Top -> if (scrollableTabs) "nav nav-tabs tabs-top" else "nav nav-tabs"
        TabPosition.Left -> "nav nav-tabs tabs-left flex-column"
        TabPosition.Right -> "nav nav-tabs tabs-right flex-column"
    }
    val component = this.cast<TabPanel>()

    @Composable
    fun IComponent.generateNav() {
        ul(navClasses) {
            role("tablist")
            component.tabsOrderList.forEachIndexed { index, tabId ->
                component.tabs[tabId]?.let { tab ->
                    li("nav-item") {
                        role("presentation")
                        val navLinkClassName =
                            if (index == component.activeIndexState) "nav-link active" else "nav-link"
                        val navLinkClassNameWithIcon = if (tab.label != null && tab.icon != null ||
                            tab.icon == null && tab.closable
                        ) "$navLinkClassName icon-link" else navLinkClassName
                        button(tab.label, tab.icon, className = navLinkClassNameWithIcon) {
                            id("$tabPanelId-tab-$index")
                            role("tab")
                            attribute("aria-selected", (index == component.activeIndexState).toString())
                            if (tab.closable) {
                                button(className = "btn-close kilua-tab-close") {
                                    onClick { e ->
                                        component.dispatchEvent(
                                            "closeTab",
                                            buildCustomEventInit("$index".toJsString())
                                        )
                                        e.stopPropagation()
                                    }
                                }
                            }
                        }
                        onClick { e ->
                            component.updateProperty(TabPanel::activeIndex, index)
                            e.preventDefault()
                        }
                        if (draggableTabs) {
                            setDragDropData("text/plain", "$index")
                            setDropTargetData("text/plain") { data ->
                                val fromIndex = data?.toInt() ?: index
                                if (fromIndex != index) {
                                    component.dispatchEvent(
                                        "moveTab",
                                        buildCustomEventInit("{ \"from\": $fromIndex, \"to\": $index }".toJsString())
                                    )
                                }
                            }
                        } else {
                            clearDragDropData()
                            clearDropTarget()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun IComponent.generateContent() {
        div("tab-content") {
            component.tabsOrderList.getOrNull(component.activeIndexState)?.let {
                component.tabs[it]?.let { tab ->
                    div("tab-pane active") {
                        role("tabpanel")
                        ariaLabelledby("$tabPanelId-tab-$activeIndex")
                        tabindex(0)
                        tab.content(this)
                    }
                }
            }
        }
    }

    val tabSizes = when (sideTabSize) {
        SideTabSize.Size1 -> Pair("col-sm-1", "col-sm-11")
        SideTabSize.Size2 -> Pair("col-sm-2", "col-sm-10")
        SideTabSize.Size3 -> Pair("col-sm-3", "col-sm-9")
        SideTabSize.Size4 -> Pair("col-sm-4", "col-sm-8")
        SideTabSize.Size5 -> Pair("col-sm-5", "col-sm-7")
        SideTabSize.Size6 -> Pair("col-sm-6", "col-sm-6")
    }

    when (tabPosition) {
        TabPosition.Top -> {
            generateNav()
            generateContent()
        }

        TabPosition.Left -> {
            div("container-fluid") {
                div("row") {
                    div("${tabSizes.first} ps-0 pe-0") {
                        generateNav()
                    }
                    div("${tabSizes.second} ps-0 pe-0") {
                        generateContent()
                    }
                }
            }
        }

        TabPosition.Right -> {
            div("container-fluid") {
                div("row") {
                    div("${tabSizes.second} ps-0 pe-0") {
                        generateContent()
                    }
                    div("${tabSizes.first} ps-0 pe-0") {
                        generateNav()
                    }
                }
            }
        }
    }
}
