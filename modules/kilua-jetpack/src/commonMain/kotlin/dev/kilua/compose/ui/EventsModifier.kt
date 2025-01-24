package dev.kilua.compose.ui

import androidx.compose.runtime.Composable
import dev.kilua.html.ITag
import dev.kilua.html.helpers.TagEvents
import web.dom.HTMLElement

/**
 * A modifier class for events.
 */
public class EventsModifier(internal val events: @Composable TagEvents.() -> Unit) : Modifier.Element {
    @Composable
    override fun <E : HTMLElement> useOn(tag: ITag<E>) {
        events.invoke(tag)
    }
}

/**
 * Create a modifier instance for given events.
 */
public fun Modifier.eventsModifier(events: @Composable TagEvents.() -> Unit): Modifier =
    this then EventsModifier(events)
