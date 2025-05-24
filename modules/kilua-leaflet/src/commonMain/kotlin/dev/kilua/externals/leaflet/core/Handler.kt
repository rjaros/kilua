@file:JsModule("leaflet")
/*
 * Copyright (c) 2025 Robert Jaros
 * Copyright (c) 2022 Adam S.
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

package dev.kilua.externals.leaflet.core

import dev.kilua.externals.leaflet.map.LeafletMap
import js.import.JsModule

/**
 * Abstract class for map interaction handlers
 *
 * https://leafletjs.com/reference.html#handler
 */
public open external class Handler(map: LeafletMap) : Class {
    /** Enables the handler */
    public open fun enable(): Handler /* this */

    /** Disables the handler */
    public open fun disable(): Handler /* this */

    /** Returns `true` if the handler is enabled */
    public open fun enabled(): Boolean

    /** Called when the handler is enabled, should add event hooks. */
    public open val addHooks: (() -> Unit)?

    /** Called when the handler is disabled, should remove the event hooks added previously. */
    public open val removeHooks: (() -> Unit)?
}
