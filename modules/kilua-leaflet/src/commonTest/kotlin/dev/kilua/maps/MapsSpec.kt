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

package dev.kilua.maps

import dev.kilua.compose.root
import dev.kilua.externals.leaflet.geo.LatLng
import dev.kilua.externals.leaflet.layer.FeatureGroup
import dev.kilua.html.px
import dev.kilua.test.DomSpec
import web.html.asStringOrNull
import web.window.window
import kotlin.test.Test

class MapsSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailableAsync {
        val root = root("test") {
            val lat = 51.505
            val lng = -0.09
            val position = LatLng(lat, lng)
            maps {
                width(300.px)
                height(300.px)
                configureLeafletMap {
                    setView(position, 18)
                    val featureGroup = FeatureGroup()
                    featureGroup.addTo(this)
                    val marker = LeafletObjectFactory.marker(position)
                    marker.on("click", {
                        window.open("https://www.openstreetmap.org/?mlat=$lat&mlon=$lng#map=18/$lat/$lng&layers=N")
                    })
                    marker.addTo(featureGroup)
                }
            }
        }
        assertEqualsHtml(
            """<div role="region" aria-label="Map" class="leaflet-container leaflet-touch leaflet-fade-anim leaflet-box-zoom leaflet-grab leaflet-touch-drag leaflet-touch-zoom" tabindex="0" style="width: 300px; height: 300px; position: relative;">
<div class="leaflet-pane leaflet-map-pane" style="transform: translate3d(0px, 0px, 0px);">
<div class="leaflet-pane leaflet-tile-pane">
</div>
<div class="leaflet-pane leaflet-overlay-pane">
</div>
<div class="leaflet-pane leaflet-shadow-pane">
</div>
<div class="leaflet-pane leaflet-marker-pane">
<img src="data:image/svg+xml,&lt;svg xmlns=&quot;http://www.w3.org/2000/svg&quot; viewBox=&quot;0 0 25.59 47.09&quot; fill=&quot;%23fff&quot; stroke-width=&quot;1.1&quot;&gt;&lt;radialGradient id=&quot;c&quot;&gt;&lt;stop stop-opacity=&quot;.3&quot;/&gt;&lt;stop offset=&quot;1&quot; stop-opacity=&quot;0&quot;/&gt;&lt;/radialGradient&gt;&lt;ellipse cx=&quot;12.8&quot; cy=&quot;41.53&quot; rx=&quot;10.5&quot; ry=&quot;5&quot; fill=&quot;url(%23c)&quot;/&gt;&lt;linearGradient id=&quot;a&quot; x2=&quot;0&quot; y1=&quot;1&quot;&gt;&lt;stop stop-color=&quot;%23126FC6&quot;/&gt;&lt;stop offset=&quot;1&quot; stop-color=&quot;%23107BC2&quot; stop-opacity=&quot;.75&quot;/&gt;&lt;/linearGradient&gt;&lt;linearGradient id=&quot;b&quot; x2=&quot;0&quot; y1=&quot;.5&quot;&gt;&lt;stop stop-color=&quot;%232E6C97&quot;/&gt;&lt;stop offset=&quot;1&quot; stop-color=&quot;%233883B7&quot;/&gt;&lt;/linearGradient&gt;&lt;circle cx=&quot;12.8&quot; cy=&quot;13.63&quot; r=&quot;4.7&quot;/&gt;&lt;path id=&quot;p&quot; d=&quot;M12.84 1.74c-6.57 0-12.04 5.69-12.04 11.87 0 2.78 1.56 6.31 2.69 8.75l9.31 17.87 9.26-17.87c1.13-2.44 2.74-5.79 2.74-8.75 0-6.18-5.38-11.87-11.96-11.87Zm0 7.16a4.71 4.71 0 0 1 4.68 4.71c0 2.59-2.1 4.66-4.68 4.68-2.58-.02-4.68-2.09-4.68-4.68a4.71 4.71 0 0 1 4.68-4.71Z&quot;/&gt;&lt;use href=&quot;%23p&quot; fill=&quot;url(%23a)&quot; stroke=&quot;url(%23b)&quot;/&gt;&lt;/svg&gt;" class="leaflet-marker-icon leaflet-zoom-animated leaflet-interactive" alt="Marker" tabindex="0" role="button" style="margin-left: -12px; margin-top: -41px; transform-origin: 12px 41px; width: 25px; height: 46px; transform: translate3d(0px, 0px, 0px); z-index: 0;">
</div>
<div class="leaflet-pane leaflet-tooltip-pane">
</div>
<div class="leaflet-pane leaflet-popup-pane">
</div>
<div class="leaflet-proxy leaflet-zoom-animated" style="transform: translate3d(3.35377e+07px, 2.23161e+07px, 0px) scale(131072);">
</div>
</div>
<div class="leaflet-control-container">
<div class="leaflet-top leaflet-left">
<div class="leaflet-control-zoom leaflet-bar leaflet-control">
<a class="leaflet-control-zoom-in" href="#" title="Zoom in" role="button" aria-label="Zoom in" aria-disabled="false">
<svg width="12" height="12" stroke="currentColor" stroke-width="2" aria-hidden="true">
<path d="M0 6h12M6 0v12">
</path>
</svg>
</a>
<a class="leaflet-control-zoom-out" href="#" title="Zoom out" role="button" aria-label="Zoom out" aria-disabled="false">
<svg width="12" height="12" stroke="currentColor" stroke-width="2" aria-hidden="true">
<path d="M0 6h12">
</path>
</svg>
</a>
</div>
</div>
<div class="leaflet-top leaflet-right">
</div>
<div class="leaflet-bottom leaflet-left">
</div>
<div class="leaflet-bottom leaflet-right">
<div class="leaflet-control-attribution leaflet-control">
<a target="_blank" href="https://leafletjs.com" title="A JavaScript library for interactive maps">
<svg aria-hidden="true" width="12" height="8" viewBox="0 0 12 8" class="leaflet-attribution-flag">
<path fill="#4C7BE1" d="M0 0h12v4H0z">
</path>
<path fill="#FFD500" d="M0 4h12v3H0z">
</path>
<path fill="#E0BC00" d="M0 7h12v1H0z">
</path>
</svg>
Leaflet
</a>
</div>
</div>
</div>
</div>""",
            root.element.innerHTML.asStringOrNull(),
            "Should render a Leaflet map component with a marker"
        )
    }


    @Test
    fun renderToString() {
        run {
            val root = root {
                val lat = 51.505
                val lng = -0.09
                val position = LatLng(lat, lng)
                maps {
                    width(300.px)
                    height(300.px)
                    configureLeafletMap {
                        setView(position, 18)
                        val featureGroup = FeatureGroup()
                        featureGroup.addTo(this)
                        val marker = LeafletObjectFactory.marker(position)
                        marker.on("click", {
                            window.open("https://www.openstreetmap.org/?mlat=$lat&mlon=$lng#map=18/$lat/$lng&layers=N")
                        })
                        marker.addTo(featureGroup)
                    }
                }
            }
            assertEqualsHtml(
                """<div style="width: 300px; height: 300px;"></div>""",
                root.innerHTML,
                "Should render a placeholder for a Leaflet map component to a String"
            )
        }
    }
}