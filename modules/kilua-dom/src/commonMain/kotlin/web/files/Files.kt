@file:Suppress(
    "NO_EXPLICIT_VISIBILITY_IN_API_MODE",
    "NO_EXPLICIT_RETURN_TYPE_IN_API_MODE",
    "EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE",
)
/*
 * Copyright 2010-2023 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

// NOTE: THIS FILE IS AUTO-GENERATED, DO NOT EDIT!
// See github.com/kotlin/dukat for details

package web.files

import js.core.JsAny
import web.JsArray
import web.JsNumber
import web.dom.ImageBitmapSource
import web.dom.ItemArrayLike
import web.dom.MediaProvider
import web.events.Event
import web.events.EventTarget
import web.webgl.ArrayBuffer
import web.xhr.ProgressEvent

/**
 * Exposes the JavaScript [Blob](https://developer.mozilla.org/en/docs/Web/API/Blob) to Kotlin
 */
public open external class Blob(
    blobParts: JsArray<JsAny? /* BufferSource|Blob|String */>,
    options: BlobPropertyBag
) : MediaProvider, ImageBitmapSource, JsAny {
    open val size: JsNumber
    open val type: String
    open val isClosed: Boolean
    fun slice(
        start: Int,
        end: Int,
        contentType: String
    ): Blob

    fun close()
}

public external interface BlobPropertyBag : JsAny {
    var type: String? /* = "" */

}

/**
 * Exposes the JavaScript [File](https://developer.mozilla.org/en/docs/Web/API/File) to Kotlin
 */
public open external class File(
    fileBits: JsArray<JsAny? /* BufferSource|Blob|String */>,
    fileName: String,
    options: FilePropertyBag
) : Blob, JsAny {
    open val name: String
    open val lastModified: Int
}

public external interface FilePropertyBag : BlobPropertyBag, JsAny {
    var lastModified: Int?

}

/**
 * Exposes the JavaScript [FileList](https://developer.mozilla.org/en/docs/Web/API/FileList) to Kotlin
 */
public abstract external class FileList : ItemArrayLike<File>, JsAny {
    override fun item(index: Int): File?
}

/**
 * Exposes the JavaScript [FileReader](https://developer.mozilla.org/en/docs/Web/API/FileReader) to Kotlin
 */
public open external class FileReader : EventTarget, JsAny {
    open val readyState: Short
    open val result: JsAny? /* String|ArrayBuffer */
    open val error: JsAny?
    var onloadstart: ((ProgressEvent) -> Unit)?
    var onprogress: ((ProgressEvent) -> Unit)?
    var onload: ((Event) -> Unit)?
    var onabort: ((Event) -> Unit)?
    var onerror: ((Event) -> Unit)?
    var onloadend: ((Event) -> Unit)?
    fun readAsArrayBuffer(blob: Blob)
    fun readAsBinaryString(blob: Blob)
    fun readAsText(blob: Blob, label: String)
    fun readAsDataURL(blob: Blob)
    fun abort()

    companion object {
        val EMPTY: Short
        val LOADING: Short
        val DONE: Short
    }
}

/**
 * Exposes the JavaScript [FileReaderSync](https://developer.mozilla.org/en/docs/Web/API/FileReaderSync) to Kotlin
 */
public open external class FileReaderSync : JsAny {
    fun readAsArrayBuffer(blob: Blob): ArrayBuffer
    fun readAsBinaryString(blob: Blob): String
    fun readAsText(blob: Blob, label: String): String
    fun readAsDataURL(blob: Blob): String
}
