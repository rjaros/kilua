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

package dev.kilua.gradle

import java.net.HttpURLConnection
import java.net.URL

private const val DEFAULT_READ_TIMEOUT = 5000

public fun URL.readContent(headers: Map<String, String>? = null): String? {
    val httpURLConnection = this.openConnection() as HttpURLConnection
    httpURLConnection.readTimeout = DEFAULT_READ_TIMEOUT
    headers?.forEach { (key, value) -> httpURLConnection.setRequestProperty(key, value) }
    val status = httpURLConnection.responseCode
    return if (status == HttpURLConnection.HTTP_OK) {
        httpURLConnection.inputStream.readAllBytes().toString(Charsets.UTF_8)
    } else null
}
