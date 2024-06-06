@file:Suppress("EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE")
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

package dev.kilua

import web.JsAny

@JsModule("trix/dist/trix.css")
internal external object TrixCss : JsAny

@JsModule("zzz-kilua-assets/k-trix.css")
internal external object CoreTrixCss : JsAny

/**
 * Initializes Trix module.
 */
internal expect fun initializeTrix()

/**
 * Initializer for Kilua Trix module.
 */
public object TrixModule : ModuleInitializer {

    override fun initialize() {
        useModule(TrixCss)
        useModule(CoreTrixCss)
        CssRegister.register("trix/dist/trix.css")
        CssRegister.register("zzz-kilua-assets/k-trix.css")
        initializeTrix()
    }
}
