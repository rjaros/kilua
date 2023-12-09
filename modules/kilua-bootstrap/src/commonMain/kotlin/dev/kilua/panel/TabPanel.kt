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
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.externals.buildCustomEventInit
import dev.kilua.html.CommentNode
import dev.kilua.html.Div
import dev.kilua.html.Tag
import dev.kilua.html.button
import dev.kilua.html.commentNode
import dev.kilua.html.div
import dev.kilua.html.li
import dev.kilua.html.ul
import dev.kilua.utils.rem
import web.dom.HTMLDivElement
import web.toJsString

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
    val content: @Composable Div.() -> Unit
)

public open class TabPanel(
    activeIndex: Int = -1,
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLDivElement>("div", className, renderConfig) {

    public open var activeIndex: Int by updatingProperty(activeIndex, skipUpdate, notifyFunction = {
        activeIndexState = it
    })

    internal var activeIndexState: Int by mutableStateOf(activeIndex)
    internal val tabs = mutableStateMapOf<Int, Tab>()
    internal var tabsOrderList: List<Int> by mutableStateOf(emptyList())

    @Composable
    public fun tab(
        label: String? = null,
        icon: String? = null,
        closable: Boolean = false,
        content: @Composable Div.() -> Unit
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
private fun ComponentBase.tabPanel(
    activeIndex: Int = -1,
    className: String? = null,
    content: @Composable TabPanel.() -> Unit,
): TabPanel {
    val component = remember { TabPanel(activeIndex, className, renderConfig) }
    ComponentNode(component, {
        set(activeIndex) { updateProperty(TabPanel::activeIndex, it) }
        set(className) { updateProperty(TabPanel::className, it) }
    }, content)
    return component
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
public fun ComponentBase.tabPanel(
    tabPosition: TabPosition = TabPosition.Top,
    sideTabSize: SideTabSize = SideTabSize.Size3,
    scrollableTabs: Boolean = false,
    draggableTabs: Boolean = false,
    activeIndex: Int = -1,
    className: String? = null,
    content: @Composable TabPanel.() -> Unit,
): TabPanel {
    val tabPanelId = remember { "kilua_tab_panel_${TabPanel.idCounter++}" }
    val tabPanelClassName = className % "kilua-tab-panel"
    return tabPanel(activeIndex, tabPanelClassName) {
        content()
        val navClasses = when (tabPosition) {
            TabPosition.Top -> if (scrollableTabs) "nav nav-tabs tabs-top" else "nav nav-tabs"
            TabPosition.Left -> "nav nav-tabs tabs-left flex-column"
            TabPosition.Right -> "nav nav-tabs tabs-right flex-column"
        }
        val component = this

        @Composable
        fun ComponentBase.generateNav() {
            ul(navClasses) {
                role = "tablist"
                tabsOrderList.forEachIndexed { index, tabId ->
                    component.tabs[tabId]?.let { tab ->
                        li("nav-item") {
                            role = "presentation"
                            val navLinkClassName =
                                if (index == component.activeIndexState) "nav-link active" else "nav-link"
                            val navLinkClassNameWithIcon =
                                if (tab.icon == null && tab.closable) "$navLinkClassName icon-link" else navLinkClassName
                            button(tab.label, tab.icon, className = navLinkClassNameWithIcon) {
                                id = "$tabPanelId-tab-$index"
                                role = "tab"
                                setAttribute("aria-selected", (index == component.activeIndexState).toString())
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
        fun ComponentBase.generateContent() {
            div("tab-content") {
                tabsOrderList.getOrNull(component.activeIndexState)?.let {
                    component.tabs[it]?.let { tab ->
                        div("tab-pane active") {
                            role = "tabpanel"
                            ariaLabelledby = "$tabPanelId-tab-$activeIndex"
                            tabindex = 0
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
}
