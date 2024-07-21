/*
 * Copyright (c) 2024 Robert Jaros
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

package dev.kilua.ssr

import dev.kilua.externals.get
import dev.kilua.utils.cast
import dev.kilua.utils.toList
import dev.kilua.dom.JsAny
import dev.kilua.dom.JsString

/**
 * Node.js process object.
 */
internal external val process: JsAny?

/**
 * Get a command line parameter value.
 */
public fun getCommandLineParameter(name: String): String? {
    return if (process != null) {
        val args = process["argv"].cast<dev.kilua.dom.JsArray<JsString>>().toList().map { it.toString() }.drop(2)
        val index = args.indexOfFirst { it == name }
        if (index != -1 && index < args.size - 1) {
            args[index + 1]
        } else null
    } else null
}
