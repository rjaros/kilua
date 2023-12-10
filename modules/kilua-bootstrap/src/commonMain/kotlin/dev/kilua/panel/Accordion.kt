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
import dev.kilua.html.CommentNode
import dev.kilua.html.Div
import dev.kilua.html.Tag
import dev.kilua.html.button
import dev.kilua.html.commentNode
import dev.kilua.html.div
import dev.kilua.html.h2
import dev.kilua.panel.Accordion.Companion.idCounter
import dev.kilua.utils.rem
import web.dom.HTMLDivElement

internal data class AccordionItem(
    val label: String? = null,
    val icon: String? = null,
    val content: @Composable Div.() -> Unit
)

public open class Accordion(
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLDivElement>("div", className, renderConfig) {

    internal val items = mutableStateMapOf<Int, AccordionItem>()
    internal var itemsOrderList: List<Int> by mutableStateOf(emptyList())

    /**
     * Adds an accordion item.
     * @param label the label of the item
     * @param icon the icon of the item
     * @param content the content of the item
     */
    @Composable
    public fun item(
        label: String? = null,
        icon: String? = null,
        content: @Composable Div.() -> Unit
    ) {
        val itemId = remember { idCounter++ }
        commentNode("sid=$itemId")
        val item = AccordionItem(label, icon, content)
        if (items[itemId] != item) {
            items[itemId] = item
        }
        DisposableEffect(itemId) {
            refreshItemsOrderList()
            onDispose {
                refreshItemsOrderList()
                items.remove(itemId)
            }
        }
    }

    private fun refreshItemsOrderList() {
        itemsOrderList = this.children.filterIsInstance<CommentNode>().filter { it.data.startsWith("sid=") }
            .mapNotNull { it.data.drop("sid=".length).toIntOrNull() }
    }

    public companion object {
        internal var idCounter: Int = 0
    }
}

@Composable
private fun ComponentBase.accordion(
    className: String? = null,
    content: @Composable Accordion.() -> Unit,
): Accordion {
    val component = remember { Accordion(className, renderConfig) }
    ComponentNode(component, {
        set(className) { updateProperty(Accordion::className, it) }
    }, content)
    return component
}

/**
 * Creates an [Accordion] component.
 *
 * @param flush determines if the accordion has no rounded corners
 * @param alwaysOpen determines if the accordion items are automatically closing
 * @param openedIndex the index of the initially opened item
 * @param className the CSS class name
 * @param content the content of the accordion
 * @return the [Accordion] component
 */
@Composable
public fun ComponentBase.accordion(
    flush: Boolean = false,
    alwaysOpen: Boolean = false,
    openedIndex: Int = 0,
    className: String? = null,
    content: @Composable Accordion.() -> Unit,
): Accordion {
    val accordionId = remember { "kilua_accordion_${idCounter++}" }
    return accordion("accordion" % if (flush) "accordion-flush" else null % className) {
        id = accordionId
        content()
        itemsOrderList.forEachIndexed { index, itemId ->
            this.items[itemId]?.let { item ->
                div("accordion-item") {
                    h2("accordion-header") {
                        button(
                            item.label,
                            item.icon,
                            className = "accordion-button" % if (index != openedIndex) "collapsed" else null
                        ) {
                            setAttribute("data-bs-toggle", "collapse")
                            setAttribute("data-bs-target", "#$accordionId-item-$index")
                            setAttribute("aria-expanded", (index == openedIndex).toString())
                            setAttribute("aria-controls", "$accordionId-item-$index")
                        }
                    }
                    div("accordion-collapse collapse" % if (index == openedIndex) "show" else null) {
                        id = "$accordionId-item-$index"
                        if (!alwaysOpen) {
                            setAttribute("data-bs-parent", "#$accordionId")
                        }
                        div("accordion-body") {
                            item.content(this)
                        }
                    }
                }
            }
        }
    }
}
