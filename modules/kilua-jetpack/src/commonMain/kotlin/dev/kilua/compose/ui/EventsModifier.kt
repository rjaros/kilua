/*
 * Copyright (c) 2025 Robert Jaros
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

package dev.kilua.compose.ui

import androidx.compose.runtime.Composable
import dev.kilua.html.ITag
import dev.kilua.html.helpers.TagEvents
import web.html.HTMLElement

/**
 * A modifier class for common tag events.
 */
public class EventsModifier(internal val events: @Composable TagEvents.() -> Unit) : Modifier.Element {
    @Composable
    override fun <E : HTMLElement> useOn(tag: ITag<E>) {
        events.invoke(tag)
    }
}

/**
 * Create a modifier instance for given common tag events.
 */
public fun Modifier.eventsModifier(events: @Composable TagEvents.() -> Unit): Modifier =
    this then EventsModifier(events)
