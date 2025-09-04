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

package dev.kilua.externals.leaflet.layer.overlay

import dev.kilua.externals.JsArray
import dev.kilua.externals.leaflet.geo.LatLngBounds
import dev.kilua.externals.leaflet.layer.Layer
import dev.kilua.externals.leaflet.layer.overlay.ImageOverlay.ImageOverlayOptions
import js.core.JsString
import kotlin.js.JsModule
import web.html.HTMLVideoElement
import kotlin.js.definedExternally

public open external class VideoOverlay : Layer<VideoOverlay.VideoOverlayOptions>, MediaOverlay {

    public constructor(
        video: String,
        bounds: LatLngBounds,
        options: VideoOverlayOptions = definedExternally
    )

    public constructor(
        video: JsArray<JsString>,
        bounds: LatLngBounds,
        options: VideoOverlayOptions = definedExternally
    )

    public constructor(
        video: HTMLVideoElement,
        bounds: LatLngBounds,
        options: VideoOverlayOptions = definedExternally
    )

    override fun setOpacity(opacity: Double): VideoOverlay /* this */
    override fun bringToFront(): VideoOverlay /* this */
    override fun bringToBack(): VideoOverlay /* this */
    override fun setUrl(url: String): VideoOverlay /* this */
    override fun setBounds(bounds: LatLngBounds): VideoOverlay /* this */
    override fun setZIndex(value: Int): VideoOverlay /* this */
    override fun getElement(): HTMLVideoElement?

    public interface VideoOverlayOptions : ImageOverlayOptions {
        public var autoplay: Boolean?
        public var loop: Boolean?
        public var keepAspectRatio: Boolean?
        public var playsInline: Boolean?
        public var muted: Boolean?
    }

}
