@file:JsModule("leaflet")
@file:Suppress("INTERFACE_WITH_SUPERCLASS")
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

package dev.kilua.externals.leaflet.map

import dev.kilua.externals.JsArray
import dev.kilua.externals.leaflet.control.Attribution
import dev.kilua.externals.leaflet.control.Control
import dev.kilua.externals.leaflet.control.Zoom
import dev.kilua.externals.leaflet.core.Handler
import dev.kilua.externals.leaflet.events.Evented
import dev.kilua.externals.leaflet.geo.CRS
import dev.kilua.externals.leaflet.geo.LatLng
import dev.kilua.externals.leaflet.geo.LatLngBounds
import dev.kilua.externals.leaflet.geometry.Bounds
import dev.kilua.externals.leaflet.geometry.Point
import dev.kilua.externals.leaflet.layer.Layer
import dev.kilua.externals.leaflet.layer.overlay.Popup
import dev.kilua.externals.leaflet.layer.overlay.Popup.PopupOptions
import dev.kilua.externals.leaflet.layer.overlay.Tooltip
import dev.kilua.externals.leaflet.layer.overlay.Tooltip.TooltipOptions
import dev.kilua.externals.leaflet.layer.vector.Path
import dev.kilua.externals.leaflet.layer.vector.Renderer
import dev.kilua.utils.obj
import js.core.JsAny
import js.import.JsModule
import web.html.HTMLElement
import web.uievents.MouseEvent
import kotlin.js.JsName
import kotlin.js.definedExternally

/**
 * The central class of the API — it is used to create a map on a page and manipulate it.
 *
 * https://leafletjs.com/reference.html#map-example
 */
@JsName("Map")
// rename the implemented class to `LeafletMap` to avoid name confusion with Kotlin's Map<K, V>
public open external class LeafletMap : Evented {

    public constructor(element: String, options: LeafletMapOptions = definedExternally)
    public constructor(element: HTMLElement, options: LeafletMapOptions = definedExternally)

    public open var attributionControl: Attribution
    public open var boxZoom: Handler
    public open var doubleClickZoom: Handler
    public open var dragging: Handler
    public open var keyboard: Handler
    public open var scrollWheelZoom: Handler
    public open var tap: Handler
    public open var touchZoom: Handler
    public open var zoomControl: Zoom
    public open var options: LeafletMapOptions

    //<editor-fold desc="Methods">
    public open fun getRenderer(layer: Path<*>): Renderer<Renderer.RendererOptions>

    //<editor-fold desc="Methods for Layers and Controls">
    public open fun addControl(control: Control<*>): LeafletMap
    public open fun removeControl(control: Control<*>): LeafletMap

    public open fun addLayer(layer: Layer<*>): LeafletMap
    public open fun removeLayer(layer: Layer<*>): LeafletMap
    public open fun hasLayer(layer: Layer<*>): Boolean
    public open fun eachLayer(fn: (layer: Layer<*>) -> Unit, context: JsAny = definedExternally): LeafletMap

    public open fun openPopup(popup: Popup): LeafletMap
    public open fun openPopup(
        content: String,
        latlng: LatLng,
        options: PopupOptions = definedExternally
    ): LeafletMap

    public open fun openPopup(
        content: HTMLElement,
        latlng: LatLng,
        options: PopupOptions = definedExternally
    ): LeafletMap

    public open fun closePopup(popup: Popup = definedExternally): LeafletMap

    public open fun openTooltip(tooltip: Tooltip): LeafletMap
    public open fun openTooltip(
        content: String,
        latlng: LatLng,
        options: TooltipOptions = definedExternally
    ): LeafletMap

    public open fun openTooltip(
        content: HTMLElement,
        latlng: LatLng,
        options: TooltipOptions = definedExternally
    ): LeafletMap

    public open fun closeTooltip(tooltip: Tooltip = definedExternally): LeafletMap
    //</editor-fold>

    //<editor-fold desc="Methods for modifying map state">
    public open fun setView(
        center: LatLng,
        zoom: Int = definedExternally,
        options: ZoomPanOptions = definedExternally
    ): LeafletMap

    public open fun setZoom(zoom: Int, options: ZoomPanOptions = definedExternally): LeafletMap
    public open fun zoomIn(
        delta: Double = definedExternally,
        options: ZoomOptions = definedExternally
    ): LeafletMap

    public open fun zoomOut(
        delta: Double = definedExternally,
        options: ZoomOptions = definedExternally
    ): LeafletMap

    public open fun setZoomAround(
        position: Point,
        zoom: Int,
        options: ZoomOptions = definedExternally
    ): LeafletMap

    public open fun setZoomAround(
        position: LatLng,
        zoom: Int,
        options: ZoomOptions = definedExternally
    ): LeafletMap

    public open fun fitBounds(
        bounds: LatLngBounds,
        options: FitBoundsOptions = definedExternally
    ): LeafletMap

    public open fun fitWorld(options: FitBoundsOptions = definedExternally): LeafletMap

    public open fun panTo(latlng: LatLng, options: PanOptions = definedExternally): LeafletMap
    public open fun panBy(offset: Point, options: PanOptions = definedExternally): LeafletMap

    public open fun flyTo(
        latlng: LatLng,
        zoom: Int = definedExternally,
        options: ZoomPanOptions = definedExternally
    ): LeafletMap

    public open fun flyToBounds(
        bounds: LatLngBounds,
        options: FitBoundsOptions = definedExternally
    ): LeafletMap

    public open fun setMaxBounds(bounds: LatLngBounds): LeafletMap
    public open fun setMinZoom(zoom: Int): LeafletMap
    public open fun setMaxZoom(zoom: Int): LeafletMap

    public open fun panInside(latLng: LatLng, options: PanInsideOptions = definedExternally): LeafletMap
    public open fun panInsideBounds(
        bounds: LatLngBounds,
        options: PanOptions = definedExternally
    ): LeafletMap


    /**
     * Checks if the map container size changed and updates the map if so — call it after you've
     * changed the map size dynamically, also animating pan by default.
     */
    public open fun invalidateSize(options: Boolean = definedExternally): LeafletMap

    /**
     * Checks if the map container size changed and updates the map if so — call it after you've
     * changed the map size dynamically, also animating pan by default. If
     * [InvalidateSizeOptions.pan] is false, panning will not occur. If
     * [InvalidateSizeOptions.debounceMoveend] is true, it will delay `moveend event` so that it
     * doesn't happen often even if the method is called many times in a row.
     */
    public open fun invalidateSize(options: ZoomPanOptions = definedExternally): LeafletMap

    /** Stops the currently running [panTo] or [flyTo] animation, if any. */
    public open fun stop(): LeafletMap
    //</editor-fold>

    //<editor-fold desc="Geolocation methods">
    public open fun locate(options: LocateOptions = definedExternally): LeafletMap
    public open fun stopLocate(): LeafletMap
    //</editor-fold>

    //<editor-fold desc="Other methods">
    public open fun addHandler(name: String, HandlerClass: JsAny): LeafletMap
    public open fun remove(): LeafletMap
    public open fun createPane(name: String, container: HTMLElement = definedExternally): HTMLElement
    public open fun getPane(pane: String): HTMLElement?
    public open fun getPane(pane: HTMLElement): HTMLElement?
    public open fun getPanes(): MapPanes
    public open fun getContainer(): HTMLElement
    public open fun whenReady(fn: () -> Unit, context: JsAny = definedExternally): LeafletMap
    //</editor-fold>

    //<editor-fold desc="Methods for Getting Map State">
    public open fun getCenter(): LatLng
    public open fun getZoom(): Int
    public open fun getBounds(): LatLngBounds
    public open fun getMinZoom(): Int
    public open fun getMaxZoom(): Int
    public open fun getBoundsZoom(
        bounds: LatLngBounds,
        inside: Boolean = definedExternally,
        padding: Point = definedExternally
    ): Int

    public open fun getSize(): Point
    public open fun getPixelBounds(): Bounds
    public open fun getPixelOrigin(): Point
    public open fun getPixelWorldBounds(zoom: Int = definedExternally): Bounds
    //</editor-fold>

    //<editor-fold desc="Conversion Methods">
    public open fun getZoomScale(toZoom: Int, fromZoom: Int = definedExternally): Int
    public open fun getScaleZoom(scale: Int, fromZoom: Int = definedExternally): Int
    public open fun project(latlng: LatLng, zoom: Int = definedExternally): Point
    public open fun unproject(point: Point, zoom: Int = definedExternally): LatLng
    public open fun layerPointToLatLng(point: Point): LatLng
    public open fun latLngToLayerPoint(latlng: LatLng): Point
    public open fun wrapLatLng(latlng: LatLng): LatLng
    public open fun wrapLatLngBounds(bounds: LatLngBounds): LatLngBounds
    public open fun distance(latlng1: LatLng, latlng2: LatLng): Double
    public open fun containerPointToLayerPoint(point: Point): Point
    public open fun layerPointToContainerPoint(point: Point): Point
    public open fun containerPointToLatLng(point: Point): LatLng
    public open fun latLngToContainerPoint(latlng: LatLng): Point
    public open fun mouseEventToContainerPoint(ev: MouseEvent): Point
    public open fun mouseEventToLayerPoint(ev: MouseEvent): Point
    public open fun mouseEventToLatLng(ev: MouseEvent): LatLng
    //</editor-fold>

    //</editor-fold>

    /** Constructor parameters for [LeafletMap] */
    public interface LeafletMapOptions : JsAny {

        // Options
        public var preferCanvas: Boolean?

        // Control options
        public var attributionControl: Boolean?
        public var zoomControl: Boolean?

        // Interaction options
        public var closePopupOnClick: Boolean?
        public var zoomSnap: Int?
        public var zoomDelta: Double?
        public var trackResize: Boolean?
        public var boxZoom: Boolean?
        public var doubleClickZoom: JsAny? /* Boolean? | "center" */
        public var dragging: Boolean?

        // Map State options
        public var crs: CRS?
        public var center: LatLng?
        public var zoom: Int?
        public var maxZoom: Int?
        public var minZoom: Int?
        public var layers: JsArray<Layer<*>>?
        public var maxBounds: LatLngBounds /* LatLngBounds? | LatLngBoundsLiteral? */
        public var renderer: Renderer<Renderer.RendererOptions>?

        // Animation options
        public var zoomAnimation: Boolean?
        public var zoomAnimationThreshold: Double?
        public var fadeAnimation: Boolean?
        public var markerZoomAnimation: Boolean?
        public var transform3DLimit: Double?

        // Panning Inertia options
        public var inertia: Boolean?
        public var inertiaDeceleration: Double?
        public var inertiaMaxSpeed: Double?
        public var easeLinearity: Double?
        public var worldCopyJump: Boolean?
        public var maxBoundsViscosity: Double?

        // Keyboard Navigation options
        public var keyboard: Boolean?
        public var keyboardPanDelta: Double?

        // Mouse wheel options
        public var scrollWheelZoom: JsAny? /* Boolean? | "center" */
        public var wheelDebounceTime: Double?
        public var wheelPxPerZoomLevel: Double?

        // Touch interaction options
        public var tap: Boolean?
        public var tapTolerance: Double?
        public var touchZoom: JsAny? /* Boolean? | "center" */
        public var bounceAtZoomLimits: Boolean?
    }

    /**
     * @see panTo
     * @see panBy
     * @see panInsideBounds
     */
    public interface PanOptions : AnimatedOption {
        public var duration: Double?
        public var easeLinearity: Double?
        public var noMoveStart: Boolean?
    }

    /**
     * @see fitBounds
     * @see fitWorld
     * @see flyToBounds
     */
    public interface FitBoundsOptions : ZoomOptions, PanOptions {
        public var paddingTopLeft: Point
        public var paddingBottomRight: Point
        public var padding: Point
        public var maxZoom: Int?
    }

    /** @see invalidateSize */
    public interface InvalidateSizeOptions : ZoomPanOptions {
        public var debounceMoveend: Boolean?
        public var pan: Boolean?
    }

    /**
     * @see flyTo
     * @see setZoom
     * @see setView
     * @see invalidateSize
     */
    public interface ZoomPanOptions : ZoomOptions, PanOptions

    /** @see locate */
    public interface LocateOptions {
        public var watch: Boolean?
        public var setView: Boolean?
        public var maxZoom: Int?
        public var timeout: Int?
        public var maximumAge: Int?
        public var enableHighAccuracy: Boolean?
    }

    /** @see panInside */
    public interface PanInsideOptions {
        public var paddingTopLeft: Point
        public var paddingBottomRight: Point
        public var padding: Point
    }

    /**
     * Options for [LeafletMap] methods
     * @see zoomIn
     * @see zoomOut
     * @see setZoomAround
     */
    public interface ZoomOptions : AnimatedOption

    /**
     * Artificial interface. Used so [ZoomOptions] and [PanOptions] both have an [animate]
     * property, and other interfaces (e.g. [ZoomPanOptions]) can inherit from both.
     */
    public interface AnimatedOption {
        public var animate: Boolean
    }

}
