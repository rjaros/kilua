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
import dev.kilua.html.h2
import dev.kilua.panel.Accordion.Companion.idCounter
import dev.kilua.utils.cast
import dev.kilua.utils.rem
import web.html.HTMLDivElement

internal data class AccordionItem(
    val label: String? = null,
    val icon: String? = null,
    val content: @Composable IDiv.() -> Unit
)

/**
 * Accordion component.
 */
public interface IAccordion : ITag<HTMLDivElement> {
    //
    // Additional functions are required because @Composable functions
    // can't have default parameters.
    //

    /**
     * Adds an accordion item.
     * @param content the content of the item
     */
    @Composable
    public fun item(content: @Composable IDiv.() -> Unit)

    /**
     * Adds an accordion item.
     * @param label the label of the item
     * @param content the content of the item
     */
    @Composable
    public fun item(
        label: String,
        content: @Composable IDiv.() -> Unit
    )

    /**
     * Adds an accordion item.
     * @param label the label of the item
     * @param icon the icon of the item
     * @param content the content of the item
     */
    @Composable
    public fun item(
        label: String?,
        icon: String?,
        content: @Composable IDiv.() -> Unit
    )
}

/**
 * Accordion component.
 */
public open class Accordion(
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLDivElement>("div", className, id, renderConfig = renderConfig), IAccordion {

    internal val items = mutableStateMapOf<Int, AccordionItem>()
    internal var itemsOrderList: List<Int> by mutableStateOf(emptyList())

    /**
     * Adds an accordion item.
     * @param content the content of the item
     */
    @Composable
    public override fun item(
        content: @Composable IDiv.() -> Unit
    ) {
        item(null, null, content)
    }

    /**
     * Adds an accordion item.
     * @param label the label of the item
     * @param content the content of the item
     */
    @Composable
    public override fun item(
        label: String,
        content: @Composable IDiv.() -> Unit
    ) {
        item(label, null, content)
    }

    /**
     * Adds an accordion item.
     * @param label the label of the item
     * @param icon the icon of the item
     * @param content the content of the item
     */
    @Composable
    public override fun item(
        label: String?,
        icon: String?,
        content: @Composable IDiv.() -> Unit
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
private fun IComponent.accordionRef(
    className: String? = null,
    id: String? = null,
    content: @Composable IAccordion.() -> Unit,
): Accordion {
    val component = remember { Accordion(className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(className) { updateProperty(Accordion::className, it) }
        set(id) { updateProperty(Accordion::id, it) }
    }, content)
    return component
}


@Composable
private fun IComponent.accordion(
    className: String? = null,
    id: String? = null,
    content: @Composable IAccordion.() -> Unit,
) {
    val component = remember { Accordion(className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(className) { updateProperty(Accordion::className, it) }
        set(id) { updateProperty(Accordion::id, it) }
    }, content)
}

/**
 * Creates an [Accordion] component, returning a reference.
 *
 * @param flush determines if the accordion has no rounded corners
 * @param alwaysOpen determines if the accordion items are automatically closing
 * @param openedIndex the index of the initially opened item
 * @param className the CSS class name
 * @param id the ID attribute of the accordion
 * @param content the content of the accordion
 * @return the [Accordion] component
 */
@Composable
public fun IComponent.accordionRef(
    flush: Boolean = false,
    alwaysOpen: Boolean = false,
    openedIndex: Int = 0,
    className: String? = null,
    id: String? = null,
    content: @Composable IAccordion.() -> Unit,
): Accordion {
    val accordionId = id ?: remember { "kilua_accordion_${idCounter++}" }
    return accordionRef("accordion" % if (flush) "accordion-flush" else null % className, accordionId) {
        setupAccordion(accordionId, content, openedIndex, alwaysOpen)
    }
}

/**
 * Creates an [Accordion] component.
 *
 * @param flush determines if the accordion has no rounded corners
 * @param alwaysOpen determines if the accordion items are automatically closing
 * @param openedIndex the index of the initially opened item
 * @param className the CSS class name
 * @param id the ID attribute of the accordion
 * @param content the content of the accordion
 */
@Composable
public fun IComponent.accordion(
    flush: Boolean = false,
    alwaysOpen: Boolean = false,
    openedIndex: Int = 0,
    className: String? = null,
    id: String? = null,
    content: @Composable IAccordion.() -> Unit,
) {
    val accordionId = id ?: remember { "kilua_accordion_${idCounter++}" }
    accordion("accordion" % if (flush) "accordion-flush" else null % className, accordionId) {
        setupAccordion(accordionId, content, openedIndex, alwaysOpen)
    }
}

@Composable
private fun IAccordion.setupAccordion(
    accordionId: String,
    content: @Composable (IAccordion.() -> Unit),
    openedIndex: Int,
    alwaysOpen: Boolean,
) {
    content()
    val accordion = this.cast<Accordion>()
    accordion.itemsOrderList.forEachIndexed { index, itemId ->
        accordion.items[itemId]?.let { item ->
            div("accordion-item") {
                h2("accordion-header") {
                    button(
                        item.label,
                        item.icon,
                        className = "accordion-button" % if (index != openedIndex) "collapsed" else null
                    ) {
                        attribute("data-bs-toggle", "collapse")
                        attribute("data-bs-target", "#$accordionId-item-$index")
                        attribute("aria-expanded", (index == openedIndex).toString())
                        attribute("aria-controls", "$accordionId-item-$index")
                    }
                }
                div("accordion-collapse collapse" % if (index == openedIndex) "show" else null) {
                    id("$accordionId-item-$index")
                    if (!alwaysOpen) {
                        attribute("data-bs-parent", "#$accordionId")
                    }
                    div("accordion-body") {
                        item.content(this)
                    }
                }
            }
        }
    }
}
