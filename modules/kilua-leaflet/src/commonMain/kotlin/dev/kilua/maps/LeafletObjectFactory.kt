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

package dev.kilua.maps

import dev.kilua.externals.leaflet.control.Attribution
import dev.kilua.externals.leaflet.control.Attribution.AttributionOptions
import dev.kilua.externals.leaflet.control.Control.LayersObject
import dev.kilua.externals.leaflet.control.Layers
import dev.kilua.externals.leaflet.control.Layers.LayersOptions
import dev.kilua.externals.leaflet.control.Scale
import dev.kilua.externals.leaflet.control.Scale.ScaleOptions
import dev.kilua.externals.leaflet.control.set
import dev.kilua.externals.leaflet.geo.LatLng
import dev.kilua.externals.leaflet.geo.LatLngBounds
import dev.kilua.externals.leaflet.layer.FeatureGroup
import dev.kilua.externals.leaflet.layer.Layer
import dev.kilua.externals.leaflet.layer.Layer.LayerOptions
import dev.kilua.externals.leaflet.layer.marker.DivIcon
import dev.kilua.externals.leaflet.layer.marker.DivIcon.DivIconOptions
import dev.kilua.externals.leaflet.layer.marker.Icon
import dev.kilua.externals.leaflet.layer.marker.Icon.IconOptions
import dev.kilua.externals.leaflet.layer.marker.Marker
import dev.kilua.externals.leaflet.layer.marker.Marker.MarkerOptions
import dev.kilua.externals.leaflet.layer.overlay.DivOverlay
import dev.kilua.externals.leaflet.layer.overlay.DivOverlay.DivOverlayOptions
import dev.kilua.externals.leaflet.layer.overlay.ImageOverlay
import dev.kilua.externals.leaflet.layer.overlay.ImageOverlay.ImageOverlayOptions
import dev.kilua.externals.leaflet.layer.overlay.Popup
import dev.kilua.externals.leaflet.layer.overlay.Popup.PopupOptions
import dev.kilua.externals.leaflet.layer.overlay.Tooltip
import dev.kilua.externals.leaflet.layer.overlay.Tooltip.TooltipOptions
import dev.kilua.externals.leaflet.layer.overlay.VideoOverlay
import dev.kilua.externals.leaflet.layer.overlay.VideoOverlay.VideoOverlayOptions
import dev.kilua.externals.leaflet.layer.tile.TileLayer
import dev.kilua.externals.leaflet.layer.tile.TileLayer.TileLayerOptions
import dev.kilua.externals.leaflet.layer.vector.Canvas
import dev.kilua.externals.leaflet.layer.vector.Circle
import dev.kilua.externals.leaflet.layer.vector.CircleMarker
import dev.kilua.externals.leaflet.layer.vector.CircleMarker.CircleMarkerOptions
import dev.kilua.externals.leaflet.layer.vector.Polyline
import dev.kilua.externals.leaflet.layer.vector.Polyline.PolylineOptions
import dev.kilua.externals.leaflet.layer.vector.Rectangle
import dev.kilua.externals.leaflet.layer.vector.Renderer.RendererOptions
import dev.kilua.externals.leaflet.layer.vector.SVG
import dev.kilua.externals.leaflet.map.LeafletMap
import dev.kilua.externals.leaflet.map.LeafletMap.LeafletMapOptions
import dev.kilua.utils.obj
import web.html.HTMLElement
import web.html.HTMLVideoElement
import kotlin.js.JsArray
import kotlin.js.toJsArray
import kotlin.js.toJsString

/**
 * Leaflet constructors.
 *
 * Equivalent to Leaflet's `L` shortcut.
 *
 * See [`https://leafletjs.com/reference.html#class-class-factories`](https://leafletjs.com/reference.html#class-class-factories)
 */
public object LeafletObjectFactory {

    public fun map(
        element: HTMLElement,
        options: LeafletMapOptions?,
    ): LeafletMap =
        LeafletMap(
            element = element,
            options = options ?: obj {}
        )

    public fun map(
        element: HTMLElement,
        configure: LeafletMapOptions.() -> Unit = {},
    ): LeafletMap =
        LeafletMap(
            element = element,
            options = obj(configure)
        )

    public fun polyline(
        latLngs: Collection<LatLng>,
        configure: PolylineOptions.() -> Unit = {},
    ): Polyline = Polyline(
        latLngs.toTypedArray().toJsArray(),
        options = obj(configure),
    )

    public fun multiPolyline(
        latLngs: Collection<Collection<LatLng>>,
        configure: PolylineOptions.() -> Unit = {},
    ): Polyline = Polyline(
        latLngs.map { it.toTypedArray().toJsArray() }.toTypedArray().toJsArray(),
        options = obj(configure),
    )

    public fun tileLayer(
        urlTemplate: String,
        configure: TileLayerOptions.() -> Unit = {}
    ): TileLayer<TileLayerOptions> = TileLayer(
        urlTemplate = urlTemplate,
        options = obj(configure),
    )

    public fun layers(
        baseLayers: LayersObject = layersObject(emptyMap()),
        overlays: LayersObject = layersObject(emptyMap()),
        configure: LayersOptions.() -> Unit = {},
    ): Layers = Layers(
        baseLayers = baseLayers,
        overlays = overlays,
        options = obj(configure),
    )

    /**
     * @param[tileLayers] Map display names to tile layers. The names can contain HTML, which
     * allows you to add additional styling to the items.
     */
    public fun layersObject(tileLayers: Map<String, TileLayer<*>>): LayersObject =
        tileLayers
            .entries
            .fold(obj {}) { acc, (name, layer) ->
                acc[name] = layer
                acc
            }

    public fun imageOverlay(
        imageUrl: String,
        bounds: LatLngBounds,
        configure: ImageOverlayOptions.() -> Unit = {},
    ): ImageOverlay = ImageOverlay(
        imageUrl = imageUrl,
        bounds = bounds,
        options = obj(configure),
    )

    public fun attribution(
        configure: AttributionOptions.() -> Unit = {},
    ): Attribution = Attribution(options = obj(configure))

    public fun scale(
        configure: ScaleOptions.() -> Unit = {},
    ): Scale = Scale(options = obj(configure))

    public fun divIcon(
        configure: DivIconOptions.() -> Unit = {},
    ): DivIcon = DivIcon(options = obj(configure))

    public fun icon(
        configure: IconOptions.() -> Unit = {},
    ): Icon<IconOptions> = Icon(options = obj(configure))

    public fun marker(
        latlng: LatLng,
        configure: MarkerOptions.() -> Unit = {},
    ): Marker = Marker(latlng, options = obj(configure))

    public fun divOverlay(
        source: Layer<*>,
        configure: DivOverlayOptions.() -> Unit = {},
    ): DivOverlay<DivOverlayOptions> =
        DivOverlay(source = source, options = obj(configure))

    public fun popup(
        source: Layer<*>,
        configure: PopupOptions.() -> Unit = {},
    ): Popup = Popup(source = source, options = obj(configure))

    public fun tooltip(
        source: Layer<*>? = null,
        configure: TooltipOptions.() -> Unit = {},
    ): Tooltip = when (source) {
        null -> Tooltip(options = obj(configure))
        else -> Tooltip(options = obj(configure), source = source)
    }

    public fun videoOverlay(
        video: String,
        bounds: LatLngBounds,
        configure: VideoOverlayOptions.() -> Unit = {},
    ): VideoOverlay =
        VideoOverlay(video = video, bounds = bounds, options = obj(configure))

    public fun videoOverlay(
        video: Collection<String>,
        bounds: LatLngBounds,
        configure: VideoOverlayOptions.() -> Unit = {},
    ): VideoOverlay =
        VideoOverlay(
            video = video.map { it.toJsString() }.toTypedArray().toJsArray(),
            bounds = bounds,
            options = obj(configure)
        )

    public fun videoOverlay(
        video: HTMLVideoElement,
        bounds: LatLngBounds,
        configure: VideoOverlayOptions.() -> Unit = {},
    ): VideoOverlay =
        VideoOverlay(video = video, bounds = bounds, options = obj(configure))

    public fun canvas(
        configure: Canvas.CanvasOptions.() -> Unit = {},
    ): Canvas = Canvas(options = obj(configure))

    public fun circleMarker(
        latlng: LatLng,
        configure: CircleMarkerOptions.() -> Unit = {},
    ): CircleMarker = CircleMarker(latlng = latlng, options = obj(configure))

    public fun circle(
        latlng: LatLng,
        configure: CircleMarkerOptions.() -> Unit = {},
    ): Circle = Circle(latlng = latlng, options = obj(configure))

    /** See [`https://leafletjs.com/reference.html#polygon`](https://leafletjs.com/reference.html#polygon) */
    public fun polygon(
        latlngs: Collection<LatLng>,
        configure: PolylineOptions.() -> Unit = {},
    ): dev.kilua.externals.leaflet.layer.vector.Polygon =
        dev.kilua.externals.leaflet.layer.vector.Polygon(
            latlngs = latlngs.toTypedArray().toJsArray(),
            options = obj(configure)
        )

    /** See [`https://leafletjs.com/reference.html#polygon`](https://leafletjs.com/reference.html#polygon) */
    public fun multiPolygon(
        latlngs: Collection<Collection<Collection<LatLng>>>,
        configure: PolylineOptions.() -> Unit = {},
    ): dev.kilua.externals.leaflet.layer.vector.Polygon =
        dev.kilua.externals.leaflet.layer.vector.Polygon(
            latlngs = latlngs
                .map {
                    it.map { it.toTypedArray().toJsArray() }.toTypedArray().toJsArray()
                }
                .toTypedArray().toJsArray(),
            options = obj(configure)
        )

    public fun rectangle(
        latLngBounds: LatLngBounds,
        configure: PolylineOptions.() -> Unit = {},
    ): Rectangle = Rectangle(latLngBounds = latLngBounds, options = obj(configure))

    public fun svg(
        configure: RendererOptions.() -> Unit = {},
    ): SVG = SVG(options = obj(configure))

    public fun featureGroup(
        layers: JsArray<Layer<*>>,
        configure: LayerOptions.() -> Unit = {},
    ): FeatureGroup = FeatureGroup(layers = layers, options = obj(configure))

    public fun latLng(
        latitude: Double,
        longitude: Double
    ): LatLng = LatLng(latitude, longitude)

    public fun latLng(
        latitude: Double,
        longitude: Double,
        altitude: Double,
    ): LatLng = LatLng(latitude, longitude, altitude)

    public fun latLng(latLng: Pair<Double, Double>, altitude: Double? = null): LatLng =
        when (altitude) {
            null -> LatLng(latLng.first, latLng.second)
            else -> LatLng(latLng.first, latLng.second, altitude)
        }

    public fun latLngBounds(
        southWest: LatLng,
        northEast: LatLng,
    ): LatLngBounds = LatLngBounds(southWest, northEast)

    public fun latLngBounds(
        southWestToNorthEast: Pair<LatLng, LatLng>,
    ): LatLngBounds = latLngBounds(southWestToNorthEast.first, southWestToNorthEast.second)

}
