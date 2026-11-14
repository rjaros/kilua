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

import kotlin.js.JsAny
import kotlin.js.JsModule

/**
 * [`L.Class`](https://leafletjs.com/SlavaUkraini/reference.html#class)
 * powers the OOP facilities of Leaflet and is used to create almost all the Leaflet classes
 * documented.
 *
 * In addition to implementing a simple classical inheritance model, it introduces several special
 * properties for convenient code organization — options, includes and statics.
 */
public open external class Class : JsAny {
    /** Runs all constructor hooks of the class chain once. */
    public fun callInitHooks()

    public companion object {
        /** Includes a mixin into the current class. */
        public fun include(props: JsAny): JsAny

        /** Merges options into the defaults of the class. */
        public fun mergeOptions(props: JsAny): JsAny

        /** Deprecated alias of [mergeOptions]. */
        public fun setDefaultOptions(props: JsAny): JsAny

        /** Adds a constructor hook to the class. */
        public fun addInitHook(initHookFn: () -> Unit): JsAny
        public fun addInitHook(methodName: String, vararg args: JsAny): JsAny
    }
}
