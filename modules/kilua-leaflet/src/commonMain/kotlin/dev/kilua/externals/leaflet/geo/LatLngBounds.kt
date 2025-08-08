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

package dev.kilua.externals.leaflet.geo

import js.core.JsAny
import kotlin.js.JsModule


/**
 * Represents a rectangular geographical area on a map.
 *
 * https://leafletjs.com/SlavaUkraini/reference.html#latlngbounds
 */
public open external class LatLngBounds(southWest: LatLng, northEast: LatLng) : JsAny {

    public open fun extend(latLng: LatLng): LatLngBounds /* this */
    public open fun extend(latLngBounds: LatLngBounds): LatLngBounds /* this */

    public open fun pad(bufferRatio: Double): LatLngBounds

    public open fun getCenter(): LatLng
    public open fun getSouthWest(): LatLng
    public open fun getNorthEast(): LatLng
    public open fun getNorthWest(): LatLng
    public open fun getSouthEast(): LatLng

    public open fun getWest(): Double
    public open fun getSouth(): Double
    public open fun getEast(): Double
    public open fun getNorth(): Double

    public open fun contains(otherBounds: LatLngBounds): Boolean
    public open fun contains(latlng: LatLng): Boolean
    public open fun intersects(otherBounds: LatLngBounds): Boolean
    public open fun overlaps(otherBounds: LatLngBounds): Boolean

    public open fun toBBoxString(): String

    @Suppress("CovariantEquals") // 'equals' is external, we can't change it, so the warning isn't useful
    public open fun equals(otherBounds: LatLngBounds): Boolean
    public open fun isValid(): Boolean
}
