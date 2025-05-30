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
import js.import.JsModule
import kotlin.js.definedExternally

/**
 * Represents a geographical point with a certain latitude and longitude.
 *
 * See [`https://leafletjs.com/reference.html#latlng`](https://leafletjs.com/reference.html#latlng)
 */
public open external class LatLng(
    latitude: Double,
    longitude: Double,
    altitude: Double = definedExternally
) : JsAny {

    /** Latitude in degrees */
    public open var lat: Double

    /** Longitude in degrees */
    public open var lng: Double

    /** Altitude in meters */
    public open var alt: Double?

    public open fun equals(otherLatLng: LatLng, maxMargin: Double = definedExternally): Boolean

    @Suppress("CovariantEquals") // 'equals' is external, we can't change it, so the warning isn't useful
    public open fun equals(otherLatLng: LatLng): Boolean

    /**
     * Returns the distance (in meters) to the given [LatLng] calculated using the
     * [Spherical Law of Cosines](https://en.wikipedia.org/wiki/Spherical_law_of_cosines).
     */
    public open fun distanceTo(otherLatLng: LatLng): Double

    /**
     * Returns a new LatLng object with the longitude wrapped, so it's always between `-180` and
     * `+180` degrees.
     */
    public open fun wrap(): LatLng

    /**
     * Returns a new [LatLngBounds] object in which each boundary is `sizeInMeters/2` meters apart
     * from the [LatLng].
     */
    public open fun toBounds(sizeInMeters: Double): LatLngBounds
}
