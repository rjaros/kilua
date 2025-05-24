@file:Suppress("EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE")
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

package dev.kilua

import dev.kilua.externals.leaflet.layer.marker.Icon
import dev.kilua.utils.delete
import dev.kilua.utils.obj
import js.core.JsAny
import js.import.JsModule

@JsModule("leaflet/dist/leaflet.css")
internal external object LeafletCss : JsAny

@JsModule("leaflet/dist/images/marker-icon-2x.png")
internal external val markerIcon2xUrl: JsAny

@JsModule("leaflet/dist/images/marker-icon.png")
internal external val markerIconUrl: JsAny

@JsModule("leaflet/dist/images/marker-shadow.png")
internal external val markerShadowUrl: JsAny

/**
 * Initializer for Kilua Leaflet module.
 */
public object LeafletModule : ModuleInitializer {

    override fun initialize() {
        useModule(LeafletCss)
        CssRegister.register("leaflet/dist/leaflet.css")
        delete(Icon.DefaultIcon, "_getIconUrl")
        Icon.DefaultIcon.mergeOptions(obj<Icon.IconOptions> {
            this.iconRetinaUrl = markerIcon2xUrl.toString()
            this.iconUrl = markerIconUrl.toString()
            this.shadowUrl = markerShadowUrl.toString()
        })
    }
}
