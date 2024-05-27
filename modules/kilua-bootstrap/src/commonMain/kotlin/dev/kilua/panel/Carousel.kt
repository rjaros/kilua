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
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.Bootstrap
import dev.kilua.html.CommentNode
import dev.kilua.html.IDiv
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.html.button
import dev.kilua.html.commentNode
import dev.kilua.html.div
import dev.kilua.html.h5t
import dev.kilua.html.pt
import dev.kilua.html.span
import dev.kilua.panel.Carousel.Companion.idCounter
import dev.kilua.utils.cast
import dev.kilua.utils.rem
import web.dom.HTMLDivElement

internal data class CarouselItem(
    val caption: String? = null,
    val description: String? = null,
    val content: @Composable IDiv.() -> Unit
)

/**
 * Carousel component.
 */
public interface ICarousel : ITag<HTMLDivElement> {
    //
    // Additional functions are required because @Composable functions
    // can't have default parameters.
    //

    /**
     * Adds a carousel item.
     * @param content the content of the item
     */
    @Composable
    public fun item(
        content: @Composable IDiv.() -> Unit
    )

    /**
     * Adds a carousel item.
     * @param caption the caption of the item
     * @param content the content of the item
     */
    @Composable
    public fun item(
        caption: String,
        content: @Composable IDiv.() -> Unit
    )

    /**
     * Adds a carousel item.
     * @param caption the caption of the item
     * @param description the description of the item
     * @param content the content of the item
     */
    @Composable
    public fun item(
        caption: String?,
        description: String?,
        content: @Composable IDiv.() -> Unit
    )

    /**
     * Shows next carousel item.
     */
    public fun next()

    /**
     * Shows previous carousel item.
     */
    public fun prev()

    /**
     * Shows carousel item identified by its index.
     */
    public fun to(index: Int)

    /**
     * Cycles through the carousel items from left to right.
     */
    public fun cycle()

    /**
     * Stops the carousel from cycling through items.
     */
    public fun pause()
}

/**
 * Carousel component.
 */
public open class Carousel(
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) :
    Tag<HTMLDivElement>("div", className, id, renderConfig = renderConfig), ICarousel {

    internal val items = mutableStateMapOf<Int, CarouselItem>()
    internal var itemsOrderList: List<Int> by mutableStateOf(emptyList())

    /**
     * The native Bootstrap carousel instance.
     */
    protected var carouselInstance: Bootstrap.Carousel? = null

    /**
     * Adds a carousel item.
     * @param content the content of the item
     */
    @Composable
    public override fun item(
        content: @Composable IDiv.() -> Unit
    ) {
        item(null, null, content)
    }

    /**
     * Adds a carousel item.
     * @param caption the caption of the item
     * @param content the content of the item
     */
    @Composable
    public override fun item(
        caption: String,
        content: @Composable IDiv.() -> Unit
    ) {
        item(caption, null, content)
    }

    /**
     * Adds a carousel item.
     * @param caption the caption of the item
     * @param description the description of the item
     * @param content the content of the item
     */
    @Composable
    public override fun item(
        caption: String?,
        description: String?,
        content: @Composable IDiv.() -> Unit
    ) {
        val itemId = remember { idCounter++ }
        commentNode("sid=$itemId")
        val item = CarouselItem(caption, description, content)
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

    /**
     * Shows next carousel item.
     */
    public override fun next() {
        carouselInstance?.next()
    }

    /**
     * Shows previous carousel item.
     */
    public override fun prev() {
        carouselInstance?.prev()
    }

    /**
     * Shows carousel item identified by its index.
     */
    public override fun to(index: Int) {
        carouselInstance?.to(index)
    }

    /**
     * Cycles through the carousel items from left to right.
     */
    public override fun cycle() {
        carouselInstance?.cycle()
    }

    /**
     * Stops the carousel from cycling through items.
     */
    public override fun pause() {
        carouselInstance?.pause()
    }

    override fun onInsert() {
        if (renderConfig.isDom) {
            carouselInstance = Bootstrap.Carousel(element)
        }
    }

    override fun onRemove() {
        if (renderConfig.isDom) {
            carouselInstance?.dispose()
            carouselInstance = null
        }
    }

    public companion object {
        internal var idCounter: Int = 0
    }
}

@Composable
private fun IComponent.carouselRef(
    className: String? = null,
    id: String? = null,
    content: @Composable ICarousel.() -> Unit,
): Carousel {
    val component = remember { Carousel(className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(className) { updateProperty(Carousel::className, it) }
        set(id) { updateProperty(Carousel::id, it) }
    }, content)
    return component
}


@Composable
private fun IComponent.carousel(
    className: String? = null,
    id: String? = null,
    content: @Composable ICarousel.() -> Unit,
) {
    val component = remember { Carousel(className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(className) { updateProperty(Carousel::className, it) }
        set(id) { updateProperty(Carousel::id, it) }
    }, content)
}

/**
 * Creates an [Carousel] component, returning a reference.
 *
 * @param fade determines if the Carousel items are fading
 * @param hideControls determines if the Carousel controls are hidden
 * @param hideIndicators determines if the Carousel indicators are hidden
 * @param autoPlay determines if the Carousel items are automatically playing
 * @param interval the interval between changing Carousel items
 * @param disableTouch determines if the Carousel items are touch disabled
 * @param activeIndex the index of the initially active item
 * @param prevButtonTitle the title of the previous button
 * @param nextButtonTitle the title of the next button
 * @param className the CSS class name
 * @param id the ID attribute of the Carousel
 * @param content the content of the Carousel
 * @return the [Carousel] component
 */
@Composable
public fun IComponent.carouselRef(
    fade: Boolean = false,
    hideControls: Boolean = false,
    hideIndicators: Boolean = false,
    autoPlay: Boolean = false,
    interval: Int = 5000,
    disableTouch: Boolean = false,
    activeIndex: Int = 0,
    prevButtonTitle: String = "Previous",
    nextButtonTitle: String = "Next",
    className: String? = null,
    id: String? = null,
    content: @Composable ICarousel.() -> Unit,
): Carousel {
    val carouselId = id ?: remember { "kilua_carousel_${idCounter++}" }
    return carouselRef("carousel slide" % if (fade) "carousel-fade" else null % className, carouselId) {
        setupCarousel(
            carouselId,
            autoPlay,
            disableTouch,
            interval,
            content,
            hideIndicators,
            activeIndex,
            hideControls,
            prevButtonTitle,
            nextButtonTitle
        )
    }
}

/**
 * Creates an [Carousel] component.
 *
 * @param fade determines if the Carousel items are fading
 * @param hideControls determines if the Carousel controls are hidden
 * @param hideIndicators determines if the Carousel indicators are hidden
 * @param autoPlay determines if the Carousel items are automatically playing
 * @param interval the interval between changing Carousel items
 * @param disableTouch determines if the Carousel items are touch disabled
 * @param activeIndex the index of the initially active item
 * @param prevButtonTitle the title of the previous button
 * @param nextButtonTitle the title of the next button
 * @param className the CSS class name
 * @param id the ID attribute of the Carousel
 * @param content the content of the Carousel
 * @return the [Carousel] component
 */
@Composable
public fun IComponent.carousel(
    fade: Boolean = false,
    hideControls: Boolean = false,
    hideIndicators: Boolean = false,
    autoPlay: Boolean = false,
    interval: Int = 5000,
    disableTouch: Boolean = false,
    activeIndex: Int = 0,
    prevButtonTitle: String = "Previous",
    nextButtonTitle: String = "Next",
    className: String? = null,
    id: String? = null,
    content: @Composable ICarousel.() -> Unit,
) {
    val carouselId = id ?: remember { "kilua_carousel_${idCounter++}" }
    carousel("carousel slide" % if (fade) "carousel-fade" else null % className, carouselId) {
        setupCarousel(
            carouselId,
            autoPlay,
            disableTouch,
            interval,
            content,
            hideIndicators,
            activeIndex,
            hideControls,
            prevButtonTitle,
            nextButtonTitle
        )
    }
}

@Composable
private fun ICarousel.setupCarousel(
    carouselId: String,
    autoPlay: Boolean,
    disableTouch: Boolean,
    interval: Int,
    content: @Composable (ICarousel.() -> Unit),
    hideIndicators: Boolean,
    activeIndex: Int,
    hideControls: Boolean,
    prevButtonTitle: String,
    nextButtonTitle: String
) {
    if (autoPlay) {
        attribute("data-bs-ride", "carousel")
    }
    if (disableTouch) {
        attribute("data-bs-touch", "false")
    }
    if (interval != 5000) {
        attribute("data-bs-interval", interval.toString())
    }
    content()
    val component = this.cast<Carousel>()
    if (!hideIndicators) {
        div("carousel-indicators") {
            component.itemsOrderList.forEachIndexed { index, itemId ->
                component.items[itemId]?.let { item ->
                    button(className = if (index == activeIndex) "active" else null) {
                        attribute("data-bs-target", "#$carouselId")
                        attribute("data-bs-slide-to", index.toString())
                        if (item.caption != null) {
                            attribute("aria-label", item.caption)
                        }
                        if (index == activeIndex) {
                            attribute("aria-current", "true")
                        }
                    }
                }
            }
        }
    }
    div("carousel-inner") {
        component.itemsOrderList.forEachIndexed { index, itemId ->
            component.items[itemId]?.let { item ->
                div("carousel-item" % if (index == activeIndex) "active" else null) {
                    item.content(this)
                    if (item.caption != null || item.description != null) {
                        div("carousel-caption d-none d-md-block") {
                            if (item.caption != null) h5t(item.caption)
                            if (item.description != null) pt(item.description)
                        }
                    }
                }
            }
        }
    }
    if (!hideControls) {
        button(className = "carousel-control-prev") {
            attribute("data-bs-target", "#$carouselId")
            attribute("data-bs-slide", "prev")
            span("carousel-control-prev-icon") {
                attribute("aria-hidden", "true")
            }
            span("visually-hidden") { +prevButtonTitle }
        }
        button(className = "carousel-control-next") {
            attribute("data-bs-target", "#$carouselId")
            attribute("data-bs-slide", "next")
            span("carousel-control-next-icon") {
                attribute("aria-hidden", "true")
            }
            span("visually-hidden") { +nextButtonTitle }
        }
    }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
}
