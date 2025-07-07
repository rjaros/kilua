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

import dev.kilua.utils.isDom
import dev.kilua.utils.jsGet
import kotlinx.serialization.json.Json
import web.window.window

/**
 * Get the SSR state from the global window object.
 * This is used to retrieve the state that was serialized on the server side and sent to the client.
 */
public fun getSsrState(): String? {
    return if (isDom) {
        window.jsGet("KILUA_SSR_STATE")?.toString()?.let {
            decompressFromEncodedURIComponent(it)
        }
    } else null
}

/**
 * Get the SSR state from the global window object and deserialize it to the specified type.
 * This is used to retrieve the state that was serialized on the server side and sent to the client,
 * and then decode it using the provided JSON serializer.
 *
 * @param json The JSON serializer to use for deserialization. Defaults to [Json.Default].
 */
public inline fun <reified T> getSsrState(json: Json = Json.Default): T? {
    return getSsrState()?.let {
        return json.decodeFromString(it)
    }
}
