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

package dev.kilua.utils

import kotlinx.coroutines.suspendCancellableCoroutine
import web.events.EventHandler
import web.file.File
import web.file.FileReader
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Extension function to get file content as a data: URL.
 */
public suspend fun File.getContent(): String = suspendCancellableCoroutine { cont ->
    val reader = FileReader()
    reader.onload = EventHandler {
        cont.resume(reader.result.toString())
    }
    reader.onerror = EventHandler { e ->
        cont.resumeWithException(Exception(e.type.toString()))
    }
    reader.readAsDataURL(this@getContent)
}
