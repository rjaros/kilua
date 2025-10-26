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
            """<div class="leaflet-container leaflet-touch leaflet-fade-anim leaflet-grab leaflet-touch-drag leaflet-touch-zoom" tabindex="0" aria-keyshortcuts="ArrowLeft ArrowRight ArrowDown ArrowUp Equal NumpadAdd BracketRight Minus NumpadSubtract Digit6 Slash" style="width: 300px; height: 300px; position: relative;">
<div class="leaflet-pane leaflet-map-pane" style="transform: translate3d(0px, 0px, 0px);">
<div class="leaflet-pane leaflet-tile-pane">
</div>
<div class="leaflet-pane leaflet-overlay-pane">
</div>
<div class="leaflet-pane leaflet-shadow-pane">
<img src="marker-shadow.png" class="leaflet-marker-shadow leaflet-zoom-animated" alt="" style="margin-left: -12px; margin-top: -41px; width: 41px; height: 41px; transform: translate3d(0px, 0px, 0px);">
</div>
<div class="leaflet-pane leaflet-marker-pane">
<img src="marker-icon.png" class="leaflet-marker-icon leaflet-zoom-animated leaflet-interactive" alt="Marker" tabindex="0" role="button" style="margin-left: -12px; margin-top: -41px; width: 25px; height: 41px; transform: translate3d(0px, 0px, 0px); z-index: 0;">
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
<span aria-hidden="true">
+
</span>
</a>
<a class="leaflet-control-zoom-out" href="#" title="Zoom out" role="button" aria-label="Zoom out" aria-disabled="false">
<span aria-hidden="true">
−
</span>
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
<svg aria-hidden="true" xmlns="http://www.w3.org/2000/svg" width="12" height="8" viewBox="0 0 12 8" class="leaflet-attribution-flag">
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