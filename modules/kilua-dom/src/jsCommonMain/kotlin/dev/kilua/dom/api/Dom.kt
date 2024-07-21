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

package dev.kilua.dom.api

import dev.kilua.dom.JsAny
import dev.kilua.dom.JsArray
import dev.kilua.dom.JsNumber
import dev.kilua.dom.JsString
import dev.kilua.dom.core.Promise
import dev.kilua.dom.api.clipboard.Clipboard
import dev.kilua.dom.api.clipboard.ClipboardEvent
import dev.kilua.dom.api.css.*
import dev.kilua.dom.api.encryptedmedia.MediaKeySystemConfiguration
import dev.kilua.dom.api.encryptedmedia.MediaKeys
import dev.kilua.dom.api.events.*
import dev.kilua.dom.api.mediacapture.MediaDevices
import dev.kilua.dom.api.mediacapture.MediaStream
import dev.kilua.dom.api.mediacapture.MediaStreamConstraints
import dev.kilua.dom.api.mediasource.SourceBuffer
import dev.kilua.dom.api.pointerevents.PointerEvent
import dev.kilua.dom.api.svg.SVGSVGElement
import dev.kilua.dom.fetch.*
import dev.kilua.dom.files.Blob
import dev.kilua.dom.files.File
import dev.kilua.dom.files.FileList
import dev.kilua.dom.performance.GlobalPerformance
import dev.kilua.dom.webgl.*
import dev.kilua.dom.workers.CacheStorage
import dev.kilua.dom.workers.ServiceWorkerContainer
import dev.kilua.dom.workers.UnionClientOrMessagePortOrServiceWorker
import dev.kilua.dom.workers.UnionMessagePortOrServiceWorker
import dev.kilua.dom.xhr.ProgressEvent

public abstract external class HTMLAllCollection : JsAny {
    open val length: Int
    fun item(nameOrIndex: String): UnionElementOrHTMLCollection?
    fun namedItem(name: String): UnionElementOrHTMLCollection?
}

/**
 * Exposes the JavaScript [HTMLFormControlsCollection](https://developer.mozilla.org/en/docs/Web/API/HTMLFormControlsCollection) to Kotlin
 */
public abstract external class HTMLFormControlsCollection : HTMLCollection, JsAny

/**
 * Exposes the JavaScript [RadioNodeList](https://developer.mozilla.org/en/docs/Web/API/RadioNodeList) to Kotlin
 */
public abstract external class RadioNodeList : NodeList, UnionElementOrRadioNodeList, JsAny {
    open var value: String
}

/**
 * Exposes the JavaScript [HTMLOptionsCollection](https://developer.mozilla.org/en/docs/Web/API/HTMLOptionsCollection) to Kotlin
 */
public abstract external class HTMLOptionsCollection : HTMLCollection, JsAny {
    override var length: Int
    open var selectedIndex: Int
    fun add(element: UnionHTMLOptGroupElementOrHTMLOptionElement, before: HTMLElement)
    fun add(element: UnionHTMLOptGroupElementOrHTMLOptionElement, before: Int)
    fun add(element: UnionHTMLOptGroupElementOrHTMLOptionElement)
    fun remove(index: Int)
}

/**
 * Exposes the JavaScript [HTMLElement](https://developer.mozilla.org/en/docs/Web/API/HTMLElement) to Kotlin
 */
public abstract external class HTMLElement : Element, GlobalEventHandlers, DocumentAndElementEventHandlers,
    ElementContentEditable, ElementCSSInlineStyle, JsAny {
    open var title: String
    open var lang: String
    open var translate: Boolean
    open var dir: String
    open val dataset: DOMStringMap
    open var hidden: Boolean
    open var tabIndex: Int
    open var accessKey: String
    open val accessKeyLabel: String
    open var draggable: Boolean
    open val dropzone: DOMTokenList
    open var contextMenu: HTMLMenuElement?
    open var spellcheck: Boolean
    open var innerText: String
    open val offsetParent: Element?
    open val offsetTop: Int
    open val offsetLeft: Int
    open val offsetWidth: Int
    open val offsetHeight: Int
    fun click()
    fun focus()
    fun blur()
    fun forceSpellCheck()

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLUnknownElement](https://developer.mozilla.org/en/docs/Web/API/HTMLUnknownElement) to Kotlin
 */
public abstract external class HTMLUnknownElement : HTMLElement, JsAny {
    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [DOMStringMap](https://developer.mozilla.org/en/docs/Web/API/DOMStringMap) to Kotlin
 */
public abstract external class DOMStringMap : JsAny

/**
 * Exposes the JavaScript [HTMLHtmlElement](https://developer.mozilla.org/en/docs/Web/API/HTMLHtmlElement) to Kotlin
 */
public abstract external class HTMLHtmlElement : HTMLElement, JsAny {
    open var version: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLHeadElement](https://developer.mozilla.org/en/docs/Web/API/HTMLHeadElement) to Kotlin
 */
public abstract external class HTMLHeadElement : HTMLElement, JsAny {
    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTitleElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTitleElement) to Kotlin
 */
public abstract external class HTMLTitleElement : HTMLElement, JsAny {
    open var text: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLBaseElement](https://developer.mozilla.org/en/docs/Web/API/HTMLBaseElement) to Kotlin
 */
public abstract external class HTMLBaseElement : HTMLElement, JsAny {
    open var href: String
    open var target: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLLinkElement](https://developer.mozilla.org/en/docs/Web/API/HTMLLinkElement) to Kotlin
 */
public abstract external class HTMLLinkElement : HTMLElement, LinkStyle, JsAny {
    open var href: String
    open var crossOrigin: String?
    open var rel: String
    open var `as`: RequestDestination
    open val relList: DOMTokenList
    open var media: String
    open var nonce: String
    open var hreflang: String
    open var type: String
    open val sizes: DOMTokenList
    open var referrerPolicy: String
    open var charset: String
    open var rev: String
    open var target: String
    open var scope: String
    open var workerType: WorkerType

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLMetaElement](https://developer.mozilla.org/en/docs/Web/API/HTMLMetaElement) to Kotlin
 */
public abstract external class HTMLMetaElement : HTMLElement, JsAny {
    open var name: String
    open var httpEquiv: String
    open var content: String
    open var scheme: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLStyleElement](https://developer.mozilla.org/en/docs/Web/API/HTMLStyleElement) to Kotlin
 */
public abstract external class HTMLStyleElement : HTMLElement, LinkStyle, JsAny {
    open var media: String
    open var nonce: String
    open var type: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLBodyElement](https://developer.mozilla.org/en/docs/Web/API/HTMLBodyElement) to Kotlin
 */
public abstract external class HTMLBodyElement : HTMLElement, WindowEventHandlers, JsAny {
    open var text: String
    open var link: String
    open var vLink: String
    open var aLink: String
    open var bgColor: String
    open var background: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLHeadingElement](https://developer.mozilla.org/en/docs/Web/API/HTMLHeadingElement) to Kotlin
 */
public abstract external class HTMLHeadingElement : HTMLElement, JsAny {
    open var align: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLParagraphElement](https://developer.mozilla.org/en/docs/Web/API/HTMLParagraphElement) to Kotlin
 */
public abstract external class HTMLParagraphElement : HTMLElement, JsAny {
    open var align: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLHRElement](https://developer.mozilla.org/en/docs/Web/API/HTMLHRElement) to Kotlin
 */
public abstract external class HTMLHRElement : HTMLElement, JsAny {
    open var align: String
    open var color: String
    open var noShade: Boolean
    open var size: String
    open var width: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLPreElement](https://developer.mozilla.org/en/docs/Web/API/HTMLPreElement) to Kotlin
 */
public abstract external class HTMLPreElement : HTMLElement, JsAny {
    open var width: Int

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLQuoteElement](https://developer.mozilla.org/en/docs/Web/API/HTMLQuoteElement) to Kotlin
 */
public abstract external class HTMLQuoteElement : HTMLElement, JsAny {
    open var cite: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLOListElement](https://developer.mozilla.org/en/docs/Web/API/HTMLOListElement) to Kotlin
 */
public abstract external class HTMLOListElement : HTMLElement, JsAny {
    open var reversed: Boolean
    open var start: Int
    open var type: String
    open var compact: Boolean

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLUListElement](https://developer.mozilla.org/en/docs/Web/API/HTMLUListElement) to Kotlin
 */
public abstract external class HTMLUListElement : HTMLElement, JsAny {
    open var compact: Boolean
    open var type: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLLIElement](https://developer.mozilla.org/en/docs/Web/API/HTMLLIElement) to Kotlin
 */
public abstract external class HTMLLIElement : HTMLElement, JsAny {
    open var value: Int
    open var type: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLDListElement](https://developer.mozilla.org/en/docs/Web/API/HTMLDListElement) to Kotlin
 */
public abstract external class HTMLDListElement : HTMLElement, JsAny {
    open var compact: Boolean

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLDivElement](https://developer.mozilla.org/en/docs/Web/API/HTMLDivElement) to Kotlin
 */
public abstract external class HTMLDivElement : HTMLElement, JsAny {
    open var align: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLAnchorElement](https://developer.mozilla.org/en/docs/Web/API/HTMLAnchorElement) to Kotlin
 */
public abstract external class HTMLAnchorElement : HTMLElement, HTMLHyperlinkElementUtils, JsAny {
    open var target: String
    open var download: String
    open var ping: String
    open var rel: String
    open val relList: DOMTokenList
    open var hreflang: String
    open var type: String
    open var text: String
    open var referrerPolicy: String
    open var coords: String
    open var charset: String
    open var name: String
    open var rev: String
    open var shape: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLDataElement](https://developer.mozilla.org/en/docs/Web/API/HTMLDataElement) to Kotlin
 */
public abstract external class HTMLDataElement : HTMLElement, JsAny {
    open var value: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTimeElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTimeElement) to Kotlin
 */
public abstract external class HTMLTimeElement : HTMLElement, JsAny {
    open var dateTime: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLSpanElement](https://developer.mozilla.org/en/docs/Web/API/HTMLSpanElement) to Kotlin
 */
public abstract external class HTMLSpanElement : HTMLElement, JsAny {
    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLBRElement](https://developer.mozilla.org/en/docs/Web/API/HTMLBRElement) to Kotlin
 */
public abstract external class HTMLBRElement : HTMLElement, JsAny {
    open var clear: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLHyperlinkElementUtils](https://developer.mozilla.org/en/docs/Web/API/HTMLHyperlinkElementUtils) to Kotlin
 */
public external interface HTMLHyperlinkElementUtils : JsAny {
    var href: String
    val origin: String
    var protocol: String
    var username: String
    var password: String
    var host: String
    var hostname: String
    var port: String
    var pathname: String
    var search: String
    var hash: String
}

/**
 * Exposes the JavaScript [HTMLModElement](https://developer.mozilla.org/en/docs/Web/API/HTMLModElement) to Kotlin
 */
public abstract external class HTMLModElement : HTMLElement, JsAny {
    open var cite: String
    open var dateTime: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLPictureElement](https://developer.mozilla.org/en/docs/Web/API/HTMLPictureElement) to Kotlin
 */
public abstract external class HTMLPictureElement : HTMLElement, JsAny {
    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLSourceElement](https://developer.mozilla.org/en/docs/Web/API/HTMLSourceElement) to Kotlin
 */
public abstract external class HTMLSourceElement : HTMLElement, JsAny {
    open var src: String
    open var type: String
    open var srcset: String
    open var sizes: String
    open var media: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLImageElement](https://developer.mozilla.org/en/docs/Web/API/HTMLImageElement) to Kotlin
 */
public abstract external class HTMLImageElement : HTMLElement, HTMLOrSVGImageElement, TexImageSource, JsAny {
    open var alt: String
    open var src: String
    open var srcset: String
    open var sizes: String
    open var crossOrigin: String?
    open var useMap: String
    open var isMap: Boolean
    open var width: Int
    open var height: Int
    open val naturalWidth: Int
    open val naturalHeight: Int
    open val complete: Boolean
    open val currentSrc: String
    open var referrerPolicy: String
    open var name: String
    open var lowsrc: String
    open var align: String
    open var hspace: Int
    open var vspace: Int
    open var longDesc: String
    open var border: String
    open val x: Int
    open val y: Int

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLIFrameElement](https://developer.mozilla.org/en/docs/Web/API/HTMLIFrameElement) to Kotlin
 */
public abstract external class HTMLIFrameElement : HTMLElement, JsAny {
    open var src: String
    open var srcdoc: String
    open var name: String
    open val sandbox: DOMTokenList
    open var allowFullscreen: Boolean
    open var allowUserMedia: Boolean
    open var width: String
    open var height: String
    open var referrerPolicy: String
    open val contentDocument: Document?
    open val contentWindow: Window?
    open var align: String
    open var scrolling: String
    open var frameBorder: String
    open var longDesc: String
    open var marginHeight: String
    open var marginWidth: String
    fun getSVGDocument(): Document?

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLEmbedElement](https://developer.mozilla.org/en/docs/Web/API/HTMLEmbedElement) to Kotlin
 */
public abstract external class HTMLEmbedElement : HTMLElement, JsAny {
    open var src: String
    open var type: String
    open var width: String
    open var height: String
    open var align: String
    open var name: String
    fun getSVGDocument(): Document?

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLObjectElement](https://developer.mozilla.org/en/docs/Web/API/HTMLObjectElement) to Kotlin
 */
public abstract external class HTMLObjectElement : HTMLElement, JsAny {
    open var data: String
    open var type: String
    open var typeMustMatch: Boolean
    open var name: String
    open var useMap: String
    open val form: HTMLFormElement?
    open var width: String
    open var height: String
    open val contentDocument: Document?
    open val contentWindow: Window?
    open val willValidate: Boolean
    open val validity: ValidityState
    open val validationMessage: String
    open var align: String
    open var archive: String
    open var code: String
    open var declare: Boolean
    open var hspace: Int
    open var standby: String
    open var vspace: Int
    open var codeBase: String
    open var codeType: String
    open var border: String
    fun getSVGDocument(): Document?
    fun checkValidity(): Boolean
    fun reportValidity(): Boolean
    fun setCustomValidity(error: String)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLParamElement](https://developer.mozilla.org/en/docs/Web/API/HTMLParamElement) to Kotlin
 */
public abstract external class HTMLParamElement : HTMLElement, JsAny {
    open var name: String
    open var value: String
    open var type: String
    open var valueType: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLVideoElement](https://developer.mozilla.org/en/docs/Web/API/HTMLVideoElement) to Kotlin
 */
public abstract external class HTMLVideoElement : HTMLMediaElement, CanvasImageSource, TexImageSource, JsAny {
    open var width: Int
    open var height: Int
    open val videoWidth: Int
    open val videoHeight: Int
    open var poster: String
    open var playsInline: Boolean

    companion object {
        val NETWORK_EMPTY: Short
        val NETWORK_IDLE: Short
        val NETWORK_LOADING: Short
        val NETWORK_NO_SOURCE: Short
        val HAVE_NOTHING: Short
        val HAVE_METADATA: Short
        val HAVE_CURRENT_DATA: Short
        val HAVE_FUTURE_DATA: Short
        val HAVE_ENOUGH_DATA: Short
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLAudioElement](https://developer.mozilla.org/en/docs/Web/API/HTMLAudioElement) to Kotlin
 */
public abstract external class HTMLAudioElement : HTMLMediaElement, JsAny {
    companion object {
        val NETWORK_EMPTY: Short
        val NETWORK_IDLE: Short
        val NETWORK_LOADING: Short
        val NETWORK_NO_SOURCE: Short
        val HAVE_NOTHING: Short
        val HAVE_METADATA: Short
        val HAVE_CURRENT_DATA: Short
        val HAVE_FUTURE_DATA: Short
        val HAVE_ENOUGH_DATA: Short
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTrackElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTrackElement) to Kotlin
 */
public abstract external class HTMLTrackElement : HTMLElement, JsAny {
    open var kind: String
    open var src: String
    open var srclang: String
    open var label: String
    open var default: Boolean
    open val readyState: Short
    open val track: TextTrack

    companion object {
        val NONE: Short
        val LOADING: Short
        val LOADED: Short
        val ERROR: Short
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLMediaElement](https://developer.mozilla.org/en/docs/Web/API/HTMLMediaElement) to Kotlin
 */
public abstract external class HTMLMediaElement : HTMLElement, JsAny {
    open val error: MediaError?
    open var src: String
    open var srcObject: MediaProvider?
    open val currentSrc: String
    open var crossOrigin: String?
    open val networkState: Short
    open var preload: String
    open val buffered: TimeRanges
    open val readyState: Short
    open val seeking: Boolean
    open var currentTime: Double
    open val duration: Double
    open val paused: Boolean
    open var defaultPlaybackRate: Double
    open var playbackRate: Double
    open val played: TimeRanges
    open val seekable: TimeRanges
    open val ended: Boolean
    open var autoplay: Boolean
    open var loop: Boolean
    open var controls: Boolean
    open var volume: Double
    open var muted: Boolean
    open var defaultMuted: Boolean
    open val audioTracks: AudioTrackList
    open val videoTracks: VideoTrackList
    open val textTracks: TextTrackList
    open val mediaKeys: MediaKeys?
    open var onencrypted: ((Event) -> Unit)?
    open var onwaitingforkey: ((Event) -> Unit)?
    fun load()
    fun canPlayType(type: String): CanPlayTypeResult
    fun fastSeek(time: Double)
    fun getStartDate(): JsAny
    fun play(): Promise<Nothing?>
    fun pause()
    fun addTextTrack(kind: TextTrackKind, label: String, language: String): TextTrack
    fun setMediaKeys(mediaKeys: MediaKeys?): Promise<Nothing?>

    companion object {
        val NETWORK_EMPTY: Short
        val NETWORK_IDLE: Short
        val NETWORK_LOADING: Short
        val NETWORK_NO_SOURCE: Short
        val HAVE_NOTHING: Short
        val HAVE_METADATA: Short
        val HAVE_CURRENT_DATA: Short
        val HAVE_FUTURE_DATA: Short
        val HAVE_ENOUGH_DATA: Short
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [MediaError](https://developer.mozilla.org/en/docs/Web/API/MediaError) to Kotlin
 */
public abstract external class MediaError : JsAny {
    open val code: Short

    companion object {
        val MEDIA_ERR_ABORTED: Short
        val MEDIA_ERR_NETWORK: Short
        val MEDIA_ERR_DECODE: Short
        val MEDIA_ERR_SRC_NOT_SUPPORTED: Short
    }
}

/**
 * Exposes the JavaScript [AudioTrackList](https://developer.mozilla.org/en/docs/Web/API/AudioTrackList) to Kotlin
 */
public abstract external class AudioTrackList : EventTarget, JsAny {
    open val length: Int
    open var onchange: ((Event) -> Unit)?
    open var onaddtrack: ((TrackEvent) -> Unit)?
    open var onremovetrack: ((TrackEvent) -> Unit)?
    fun getTrackById(id: String): AudioTrack?
}

/**
 * Exposes the JavaScript [AudioTrack](https://developer.mozilla.org/en/docs/Web/API/AudioTrack) to Kotlin
 */
public abstract external class AudioTrack : UnionAudioTrackOrTextTrackOrVideoTrack, JsAny {
    open val id: String
    open val kind: String
    open val label: String
    open val language: String
    open var enabled: Boolean
    open val sourceBuffer: SourceBuffer?
}

/**
 * Exposes the JavaScript [VideoTrackList](https://developer.mozilla.org/en/docs/Web/API/VideoTrackList) to Kotlin
 */
public abstract external class VideoTrackList : EventTarget, JsAny {
    open val length: Int
    open val selectedIndex: Int
    open var onchange: ((Event) -> Unit)?
    open var onaddtrack: ((TrackEvent) -> Unit)?
    open var onremovetrack: ((TrackEvent) -> Unit)?
    fun getTrackById(id: String): VideoTrack?
}

/**
 * Exposes the JavaScript [VideoTrack](https://developer.mozilla.org/en/docs/Web/API/VideoTrack) to Kotlin
 */
public abstract external class VideoTrack : UnionAudioTrackOrTextTrackOrVideoTrack, JsAny {
    open val id: String
    open val kind: String
    open val label: String
    open val language: String
    open var selected: Boolean
    open val sourceBuffer: SourceBuffer?
}

public abstract external class TextTrackList : EventTarget, JsAny {
    open val length: Int
    open var onchange: ((Event) -> Unit)?
    open var onaddtrack: ((TrackEvent) -> Unit)?
    open var onremovetrack: ((TrackEvent) -> Unit)?
    fun getTrackById(id: String): TextTrack?
}

/**
 * Exposes the JavaScript [TextTrack](https://developer.mozilla.org/en/docs/Web/API/TextTrack) to Kotlin
 */
public abstract external class TextTrack : EventTarget, UnionAudioTrackOrTextTrackOrVideoTrack, JsAny {
    open val kind: TextTrackKind
    open val label: String
    open val language: String
    open val id: String
    open val inBandMetadataTrackDispatchType: String
    open var mode: TextTrackMode
    open val cues: TextTrackCueList?
    open val activeCues: TextTrackCueList?
    open var oncuechange: ((Event) -> Unit)?
    open val sourceBuffer: SourceBuffer?
    fun addCue(cue: TextTrackCue)
    fun removeCue(cue: TextTrackCue)
}

public abstract external class TextTrackCueList : JsAny {
    open val length: Int
    fun getCueById(id: String): TextTrackCue?
}

/**
 * Exposes the JavaScript [TextTrackCue](https://developer.mozilla.org/en/docs/Web/API/TextTrackCue) to Kotlin
 */
public abstract external class TextTrackCue : EventTarget, JsAny {
    open val track: TextTrack?
    open var id: String
    open var startTime: Double
    open var endTime: Double
    open var pauseOnExit: Boolean
    open var onenter: ((Event) -> Unit)?
    open var onexit: ((Event) -> Unit)?
}

/**
 * Exposes the JavaScript [TimeRanges](https://developer.mozilla.org/en/docs/Web/API/TimeRanges) to Kotlin
 */
public abstract external class TimeRanges : JsAny {
    open val length: Int
    fun start(index: Int): Double
    fun end(index: Int): Double
}

/**
 * Exposes the JavaScript [TrackEvent](https://developer.mozilla.org/en/docs/Web/API/TrackEvent) to Kotlin
 */
public open external class TrackEvent(type: String, eventInitDict: TrackEventInit) : Event, JsAny {
    open val track: UnionAudioTrackOrTextTrackOrVideoTrack?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface TrackEventInit : EventInit, JsAny {
    var track: UnionAudioTrackOrTextTrackOrVideoTrack? /* = null */

}

/**
 * Exposes the JavaScript [HTMLMapElement](https://developer.mozilla.org/en/docs/Web/API/HTMLMapElement) to Kotlin
 */
public abstract external class HTMLMapElement : HTMLElement, JsAny {
    open var name: String
    open val areas: HTMLCollection

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLAreaElement](https://developer.mozilla.org/en/docs/Web/API/HTMLAreaElement) to Kotlin
 */
public abstract external class HTMLAreaElement : HTMLElement, HTMLHyperlinkElementUtils, JsAny {
    open var alt: String
    open var coords: String
    open var shape: String
    open var target: String
    open var download: String
    open var ping: String
    open var rel: String
    open val relList: DOMTokenList
    open var referrerPolicy: String
    open var noHref: Boolean

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTableElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTableElement) to Kotlin
 */
public abstract external class HTMLTableElement : HTMLElement, JsAny {
    open var caption: HTMLTableCaptionElement?
    open var tHead: HTMLTableSectionElement?
    open var tFoot: HTMLTableSectionElement?
    open val tBodies: HTMLCollection
    open val rows: HTMLCollection
    open var align: String
    open var border: String
    open var frame: String
    open var rules: String
    open var summary: String
    open var width: String
    open var bgColor: String
    open var cellPadding: String
    open var cellSpacing: String
    fun createCaption(): HTMLTableCaptionElement
    fun deleteCaption()
    fun createTHead(): HTMLTableSectionElement
    fun deleteTHead()
    fun createTFoot(): HTMLTableSectionElement
    fun deleteTFoot()
    fun createTBody(): HTMLTableSectionElement
    fun insertRow(index: Int): HTMLTableRowElement
    fun deleteRow(index: Int)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTableCaptionElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTableCaptionElement) to Kotlin
 */
public abstract external class HTMLTableCaptionElement : HTMLElement, JsAny {
    open var align: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTableColElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTableColElement) to Kotlin
 */
public abstract external class HTMLTableColElement : HTMLElement, JsAny {
    open var span: Int
    open var align: String
    open var ch: String
    open var chOff: String
    open var vAlign: String
    open var width: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTableSectionElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTableSectionElement) to Kotlin
 */
public abstract external class HTMLTableSectionElement : HTMLElement, JsAny {
    open val rows: HTMLCollection
    open var align: String
    open var ch: String
    open var chOff: String
    open var vAlign: String
    fun insertRow(index: Int): HTMLElement
    fun deleteRow(index: Int)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTableRowElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTableRowElement) to Kotlin
 */
public abstract external class HTMLTableRowElement : HTMLElement, JsAny {
    open val rowIndex: Int
    open val sectionRowIndex: Int
    open val cells: HTMLCollection
    open var align: String
    open var ch: String
    open var chOff: String
    open var vAlign: String
    open var bgColor: String
    fun insertCell(index: Int): HTMLElement
    fun deleteCell(index: Int)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTableCellElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTableCellElement) to Kotlin
 */
public abstract external class HTMLTableCellElement : HTMLElement, JsAny {
    open var colSpan: Int
    open var rowSpan: Int
    open var headers: String
    open val cellIndex: Int
    open var scope: String
    open var abbr: String
    open var align: String
    open var axis: String
    open var height: String
    open var width: String
    open var ch: String
    open var chOff: String
    open var noWrap: Boolean
    open var vAlign: String
    open var bgColor: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLFormElement](https://developer.mozilla.org/en/docs/Web/API/HTMLFormElement) to Kotlin
 */
public abstract external class HTMLFormElement : HTMLElement, JsAny {
    open var acceptCharset: String
    open var action: String
    open var autocomplete: String
    open var enctype: String
    open var encoding: String
    open var method: String
    open var name: String
    open var noValidate: Boolean
    open var target: String
    open val elements: HTMLFormControlsCollection
    open val length: Int
    fun submit()
    fun reset()
    fun checkValidity(): Boolean
    fun reportValidity(): Boolean

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLLabelElement](https://developer.mozilla.org/en/docs/Web/API/HTMLLabelElement) to Kotlin
 */
public abstract external class HTMLLabelElement : HTMLElement, JsAny {
    open val form: HTMLFormElement?
    open var htmlFor: String
    open val control: HTMLElement?

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLInputElement](https://developer.mozilla.org/en/docs/Web/API/HTMLInputElement) to Kotlin
 */
public abstract external class HTMLInputElement : HTMLElement, JsAny {
    open var accept: String
    open var alt: String
    open var autocomplete: String
    open var autofocus: Boolean
    open var defaultChecked: Boolean
    open var checked: Boolean
    open var dirName: String
    open var disabled: Boolean
    open val form: HTMLFormElement?
    open val files: FileList?
    open var formAction: String
    open var formEnctype: String
    open var formMethod: String
    open var formNoValidate: Boolean
    open var formTarget: String
    open var height: Int
    open var indeterminate: Boolean
    open var inputMode: String
    open val list: HTMLElement?
    open var max: String
    open var maxLength: Int
    open var min: String
    open var minLength: Int
    open var multiple: Boolean
    open var name: String
    open var pattern: String
    open var placeholder: String
    open var readOnly: Boolean
    open var required: Boolean
    open var size: Int
    open var src: String
    open var step: String
    open var type: String
    open var defaultValue: String
    open var value: String
    open var valueAsDate: JsAny?
    open var valueAsNumber: Double
    open var width: Int
    open val willValidate: Boolean
    open val validity: ValidityState
    open val validationMessage: String
    open val labels: NodeList
    open var selectionStart: Int?
    open var selectionEnd: Int?
    open var selectionDirection: String?
    open var align: String
    open var useMap: String
    fun stepUp()
    fun stepDown()
    fun stepUp(n: Int)
    fun stepDown(n: Int)
    fun checkValidity(): Boolean
    fun reportValidity(): Boolean
    fun setCustomValidity(error: String)
    fun select()
    fun setRangeText(replacement: String)
    fun setRangeText(replacement: String, start: Int, end: Int, selectionMode: SelectionMode)
    fun setSelectionRange(start: Int, end: Int, direction: String)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLButtonElement](https://developer.mozilla.org/en/docs/Web/API/HTMLButtonElement) to Kotlin
 */
public abstract external class HTMLButtonElement : HTMLElement, JsAny {
    open var autofocus: Boolean
    open var disabled: Boolean
    open val form: HTMLFormElement?
    open var formAction: String
    open var formEnctype: String
    open var formMethod: String
    open var formNoValidate: Boolean
    open var formTarget: String
    open var name: String
    open var type: String
    open var value: String
    open var menu: HTMLMenuElement?
    open val willValidate: Boolean
    open val validity: ValidityState
    open val validationMessage: String
    open val labels: NodeList
    fun checkValidity(): Boolean
    fun reportValidity(): Boolean
    fun setCustomValidity(error: String)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLSelectElement](https://developer.mozilla.org/en/docs/Web/API/HTMLSelectElement) to Kotlin
 */
public abstract external class HTMLSelectElement : HTMLElement, ItemArrayLike<Element>, JsAny {
    open var autocomplete: String
    open var autofocus: Boolean
    open var disabled: Boolean
    open val form: HTMLFormElement?
    open var multiple: Boolean
    open var name: String
    open var required: Boolean
    open var size: Int
    open val type: String
    open val options: HTMLOptionsCollection
    override var length: Int
    open val selectedOptions: HTMLCollection
    open var selectedIndex: Int
    open var value: String
    open val willValidate: Boolean
    open val validity: ValidityState
    open val validationMessage: String
    open val labels: NodeList
    fun namedItem(name: String): HTMLOptionElement?
    fun add(element: UnionHTMLOptGroupElementOrHTMLOptionElement, before: HTMLElement)
    fun add(element: UnionHTMLOptGroupElementOrHTMLOptionElement, before: Int)
    fun add(element: UnionHTMLOptGroupElementOrHTMLOptionElement)
    fun remove(index: Int)
    fun checkValidity(): Boolean
    fun reportValidity(): Boolean
    fun setCustomValidity(error: String)
    override fun item(index: Int): Element?

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLDataListElement](https://developer.mozilla.org/en/docs/Web/API/HTMLDataListElement) to Kotlin
 */
public abstract external class HTMLDataListElement : HTMLElement, JsAny {
    open val options: HTMLCollection

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLOptGroupElement](https://developer.mozilla.org/en/docs/Web/API/HTMLOptGroupElement) to Kotlin
 */
public abstract external class HTMLOptGroupElement : HTMLElement, UnionHTMLOptGroupElementOrHTMLOptionElement, JsAny {
    open var disabled: Boolean
    open var label: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLOptionElement](https://developer.mozilla.org/en/docs/Web/API/HTMLOptionElement) to Kotlin
 */
public abstract external class HTMLOptionElement : HTMLElement, UnionHTMLOptGroupElementOrHTMLOptionElement, JsAny {
    open var disabled: Boolean
    open val form: HTMLFormElement?
    open var label: String
    open var defaultSelected: Boolean
    open var selected: Boolean
    open var value: String
    open var text: String
    open val index: Int

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTextAreaElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTextAreaElement) to Kotlin
 */
public abstract external class HTMLTextAreaElement : HTMLElement, JsAny {
    open var autocomplete: String
    open var autofocus: Boolean
    open var cols: Int
    open var dirName: String
    open var disabled: Boolean
    open val form: HTMLFormElement?
    open var inputMode: String
    open var maxLength: Int
    open var minLength: Int
    open var name: String
    open var placeholder: String
    open var readOnly: Boolean
    open var required: Boolean
    open var rows: Int
    open var wrap: String
    open val type: String
    open var defaultValue: String
    open var value: String
    open val textLength: Int
    open val willValidate: Boolean
    open val validity: ValidityState
    open val validationMessage: String
    open val labels: NodeList
    open var selectionStart: Int?
    open var selectionEnd: Int?
    open var selectionDirection: String?
    fun checkValidity(): Boolean
    fun reportValidity(): Boolean
    fun setCustomValidity(error: String)
    fun select()
    fun setRangeText(replacement: String)
    fun setRangeText(replacement: String, start: Int, end: Int, selectionMode: SelectionMode)
    fun setSelectionRange(start: Int, end: Int, direction: String)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLKeygenElement](https://developer.mozilla.org/en/docs/Web/API/HTMLKeygenElement) to Kotlin
 */
public abstract external class HTMLKeygenElement : HTMLElement, JsAny {
    open var autofocus: Boolean
    open var challenge: String
    open var disabled: Boolean
    open val form: HTMLFormElement?
    open var keytype: String
    open var name: String
    open val type: String
    open val willValidate: Boolean
    open val validity: ValidityState
    open val validationMessage: String
    open val labels: NodeList
    fun checkValidity(): Boolean
    fun reportValidity(): Boolean
    fun setCustomValidity(error: String)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLOutputElement](https://developer.mozilla.org/en/docs/Web/API/HTMLOutputElement) to Kotlin
 */
public abstract external class HTMLOutputElement : HTMLElement, JsAny {
    open val htmlFor: DOMTokenList
    open val form: HTMLFormElement?
    open var name: String
    open val type: String
    open var defaultValue: String
    open var value: String
    open val willValidate: Boolean
    open val validity: ValidityState
    open val validationMessage: String
    open val labels: NodeList
    fun checkValidity(): Boolean
    fun reportValidity(): Boolean
    fun setCustomValidity(error: String)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLProgressElement](https://developer.mozilla.org/en/docs/Web/API/HTMLProgressElement) to Kotlin
 */
public abstract external class HTMLProgressElement : HTMLElement, JsAny {
    open var value: Double
    open var max: Double
    open val position: Double
    open val labels: NodeList

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLMeterElement](https://developer.mozilla.org/en/docs/Web/API/HTMLMeterElement) to Kotlin
 */
public abstract external class HTMLMeterElement : HTMLElement, JsAny {
    open var value: Double
    open var min: Double
    open var max: Double
    open var low: Double
    open var high: Double
    open var optimum: Double
    open val labels: NodeList

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLFieldSetElement](https://developer.mozilla.org/en/docs/Web/API/HTMLFieldSetElement) to Kotlin
 */
public abstract external class HTMLFieldSetElement : HTMLElement, JsAny {
    open var disabled: Boolean
    open val form: HTMLFormElement?
    open var name: String
    open val type: String
    open val elements: HTMLCollection
    open val willValidate: Boolean
    open val validity: ValidityState
    open val validationMessage: String
    fun checkValidity(): Boolean
    fun reportValidity(): Boolean
    fun setCustomValidity(error: String)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLLegendElement](https://developer.mozilla.org/en/docs/Web/API/HTMLLegendElement) to Kotlin
 */
public abstract external class HTMLLegendElement : HTMLElement, JsAny {
    open val form: HTMLFormElement?
    open var align: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [ValidityState](https://developer.mozilla.org/en/docs/Web/API/ValidityState) to Kotlin
 */
public abstract external class ValidityState : JsAny {
    open val valueMissing: Boolean
    open val typeMismatch: Boolean
    open val patternMismatch: Boolean
    open val tooLong: Boolean
    open val tooShort: Boolean
    open val rangeUnderflow: Boolean
    open val rangeOverflow: Boolean
    open val stepMismatch: Boolean
    open val badInput: Boolean
    open val customError: Boolean
    open val valid: Boolean
}

/**
 * Exposes the JavaScript [HTMLDetailsElement](https://developer.mozilla.org/en/docs/Web/API/HTMLDetailsElement) to Kotlin
 */
public abstract external class HTMLDetailsElement : HTMLElement, JsAny {
    open var open: Boolean

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public abstract external class HTMLMenuElement : HTMLElement, JsAny {
    open var type: String
    open var label: String
    open var compact: Boolean

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public abstract external class HTMLMenuItemElement : HTMLElement, JsAny {
    open var type: String
    open var label: String
    open var icon: String
    open var disabled: Boolean
    open var checked: Boolean
    open var radiogroup: String
    open var default: Boolean

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public open external class RelatedEvent(type: String, eventInitDict: RelatedEventInit) : Event, JsAny {
    open val relatedTarget: EventTarget?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface RelatedEventInit : EventInit, JsAny {
    var relatedTarget: EventTarget? /* = null */

}

/**
 * Exposes the JavaScript [HTMLDialogElement](https://developer.mozilla.org/en/docs/Web/API/HTMLDialogElement) to Kotlin
 */
public abstract external class HTMLDialogElement : HTMLElement, JsAny {
    open var open: Boolean
    open var returnValue: String
    fun show(anchor: UnionElementOrMouseEvent)
    fun showModal(anchor: UnionElementOrMouseEvent)
    fun close(returnValue: String)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLScriptElement](https://developer.mozilla.org/en/docs/Web/API/HTMLScriptElement) to Kotlin
 */
public abstract external class HTMLScriptElement : HTMLElement, HTMLOrSVGScriptElement, JsAny {
    open var src: String
    open var type: String
    open var charset: String
    open var async: Boolean
    open var defer: Boolean
    open var crossOrigin: String?
    open var text: String
    open var nonce: String
    open var event: String
    open var htmlFor: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLTemplateElement](https://developer.mozilla.org/en/docs/Web/API/HTMLTemplateElement) to Kotlin
 */
public abstract external class HTMLTemplateElement : HTMLElement, JsAny {
    open val content: DocumentFragment

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLSlotElement](https://developer.mozilla.org/en/docs/Web/API/HTMLSlotElement) to Kotlin
 */
public abstract external class HTMLSlotElement : HTMLElement, JsAny {
    open var name: String
    fun assignedNodes(options: AssignedNodesOptions): JsArray<Node>

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public external interface AssignedNodesOptions : JsAny {
    var flatten: Boolean? /* = false */

}

/**
 * Exposes the JavaScript [HTMLCanvasElement](https://developer.mozilla.org/en/docs/Web/API/HTMLCanvasElement) to Kotlin
 */
public abstract external class HTMLCanvasElement : HTMLElement, CanvasImageSource, TexImageSource, JsAny {
    open var width: Int
    open var height: Int
    fun getContext(contextId: String, vararg arguments: JsAny?): RenderingContext?
    fun toDataURL(type: String, quality: JsAny?): String
    fun toBlob(_callback: (Blob?) -> Unit, type: String, quality: JsAny?)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public external interface CanvasRenderingContext2DSettings : JsAny {
    var alpha: Boolean? /* = true */

}

/**
 * Exposes the JavaScript [CanvasRenderingContext2D](https://developer.mozilla.org/en/docs/Web/API/CanvasRenderingContext2D) to Kotlin
 */
public abstract external class CanvasRenderingContext2D : CanvasState, CanvasTransform, CanvasCompositing,
    CanvasImageSmoothing, CanvasFillStrokeStyles, CanvasShadowStyles, CanvasFilters, CanvasRect, CanvasDrawPath,
    CanvasUserInterface, CanvasText, CanvasDrawImage, CanvasHitRegion, CanvasImageData, CanvasPathDrawingStyles,
    CanvasTextDrawingStyles, CanvasPath, RenderingContext, JsAny {
    open val canvas: HTMLCanvasElement
}

public external interface CanvasState : JsAny {
    fun save()
    fun restore()
}

public external interface CanvasTransform : JsAny {
    fun scale(x: Double, y: Double)
    fun rotate(angle: Double)
    fun translate(x: Double, y: Double)
    fun transform(a: Double, b: Double, c: Double, d: Double, e: Double, f: Double)
    fun getTransform(): DOMMatrix
    fun setTransform(a: Double, b: Double, c: Double, d: Double, e: Double, f: Double)
    fun setTransform(transform: JsAny?)
    fun resetTransform()
}

public external interface CanvasCompositing : JsAny {
    var globalAlpha: Double
    var globalCompositeOperation: String
}

public external interface CanvasImageSmoothing : JsAny {
    var imageSmoothingEnabled: Boolean
    var imageSmoothingQuality: ImageSmoothingQuality
}

public external interface CanvasFillStrokeStyles : JsAny {
    var strokeStyle: JsAny? /* String|CanvasGradient|CanvasPattern */
    var fillStyle: JsAny? /* String|CanvasGradient|CanvasPattern */
    fun createLinearGradient(x0: Double, y0: Double, x1: Double, y1: Double): CanvasGradient
    fun createRadialGradient(x0: Double, y0: Double, r0: Double, x1: Double, y1: Double, r1: Double): CanvasGradient
    fun createPattern(image: CanvasImageSource, repetition: String): CanvasPattern?
}

public external interface CanvasShadowStyles : JsAny {
    var shadowOffsetX: Double
    var shadowOffsetY: Double
    var shadowBlur: Double
    var shadowColor: String
}

public external interface CanvasFilters : JsAny {
    var filter: String
}

public external interface CanvasRect : JsAny {
    fun clearRect(x: Double, y: Double, w: Double, h: Double)
    fun fillRect(x: Double, y: Double, w: Double, h: Double)
    fun strokeRect(x: Double, y: Double, w: Double, h: Double)
}

public external interface CanvasDrawPath : JsAny {
    fun beginPath()
    fun fill(fillRule: CanvasFillRule)
    fun fill(path: Path2D, fillRule: CanvasFillRule)
    fun stroke()
    fun stroke(path: Path2D)
    fun clip(fillRule: CanvasFillRule)
    fun clip(path: Path2D, fillRule: CanvasFillRule)
    fun resetClip()
    fun isPointInPath(x: Double, y: Double, fillRule: CanvasFillRule): Boolean
    fun isPointInPath(path: Path2D, x: Double, y: Double, fillRule: CanvasFillRule): Boolean
    fun isPointInStroke(x: Double, y: Double): Boolean
    fun isPointInStroke(path: Path2D, x: Double, y: Double): Boolean
}

public external interface CanvasUserInterface : JsAny {
    fun drawFocusIfNeeded(element: Element)
    fun drawFocusIfNeeded(path: Path2D, element: Element)
    fun scrollPathIntoView()
    fun scrollPathIntoView(path: Path2D)
}

public external interface CanvasText : JsAny {
    fun fillText(text: String, x: Double, y: Double, maxWidth: Double)
    fun strokeText(text: String, x: Double, y: Double, maxWidth: Double)
    fun measureText(text: String): TextMetrics
}

public external interface CanvasDrawImage : JsAny {
    fun drawImage(image: CanvasImageSource, dx: Double, dy: Double)
    fun drawImage(image: CanvasImageSource, dx: Double, dy: Double, dw: Double, dh: Double)
    fun drawImage(
        image: CanvasImageSource,
        sx: Double,
        sy: Double,
        sw: Double,
        sh: Double,
        dx: Double,
        dy: Double,
        dw: Double,
        dh: Double
    )
}

public external interface CanvasHitRegion : JsAny {
    fun addHitRegion(options: HitRegionOptions)
    fun removeHitRegion(id: String)
    fun clearHitRegions()
}

public external interface CanvasImageData : JsAny {
    fun createImageData(sw: Double, sh: Double): ImageData
    fun createImageData(imagedata: ImageData): ImageData
    fun getImageData(sx: Double, sy: Double, sw: Double, sh: Double): ImageData
    fun putImageData(imagedata: ImageData, dx: Double, dy: Double)
    fun putImageData(
        imagedata: ImageData,
        dx: Double,
        dy: Double,
        dirtyX: Double,
        dirtyY: Double,
        dirtyWidth: Double,
        dirtyHeight: Double
    )
}

public external interface CanvasPathDrawingStyles : JsAny {
    var lineWidth: Double
    var lineCap: CanvasLineCap
    var lineJoin: CanvasLineJoin
    var miterLimit: Double
    var lineDashOffset: Double
    fun setLineDash(segments: JsArray<JsNumber>)
    fun getLineDash(): JsArray<JsNumber>
}

public external interface CanvasTextDrawingStyles : JsAny {
    var font: String
    var textAlign: CanvasTextAlign
    var textBaseline: CanvasTextBaseline
    var direction: CanvasDirection
}

public external interface CanvasPath : JsAny {
    fun closePath()
    fun moveTo(x: Double, y: Double)
    fun lineTo(x: Double, y: Double)
    fun quadraticCurveTo(cpx: Double, cpy: Double, x: Double, y: Double)
    fun bezierCurveTo(cp1x: Double, cp1y: Double, cp2x: Double, cp2y: Double, x: Double, y: Double)
    fun arcTo(x1: Double, y1: Double, x2: Double, y2: Double, radius: Double)
    fun arcTo(x1: Double, y1: Double, x2: Double, y2: Double, radiusX: Double, radiusY: Double, rotation: Double)
    fun rect(x: Double, y: Double, w: Double, h: Double)
    fun arc(x: Double, y: Double, radius: Double, startAngle: Double, endAngle: Double, anticlockwise: Boolean)
    fun ellipse(
        x: Double,
        y: Double,
        radiusX: Double,
        radiusY: Double,
        rotation: Double,
        startAngle: Double,
        endAngle: Double,
        anticlockwise: Boolean
    )
}

/**
 * Exposes the JavaScript [CanvasGradient](https://developer.mozilla.org/en/docs/Web/API/CanvasGradient) to Kotlin
 */
public abstract external class CanvasGradient : JsAny {
    fun addColorStop(offset: Double, color: String)
}

/**
 * Exposes the JavaScript [CanvasPattern](https://developer.mozilla.org/en/docs/Web/API/CanvasPattern) to Kotlin
 */
public abstract external class CanvasPattern : JsAny {
    fun setTransform(transform: JsAny?)
}

/**
 * Exposes the JavaScript [TextMetrics](https://developer.mozilla.org/en/docs/Web/API/TextMetrics) to Kotlin
 */
public abstract external class TextMetrics : JsAny {
    open val width: Double
    open val actualBoundingBoxLeft: Double
    open val actualBoundingBoxRight: Double
    open val fontBoundingBoxAscent: Double
    open val fontBoundingBoxDescent: Double
    open val actualBoundingBoxAscent: Double
    open val actualBoundingBoxDescent: Double
    open val emHeightAscent: Double
    open val emHeightDescent: Double
    open val hangingBaseline: Double
    open val alphabeticBaseline: Double
    open val ideographicBaseline: Double
}

public external interface HitRegionOptions : JsAny {
    var path: Path2D? /* = null */

    var fillRule: CanvasFillRule? /* = CanvasFillRule.NONZERO */

    var id: String? /* = "" */

    var parentID: String? /* = null */

    var cursor: String? /* = "inherit" */

    var control: Element? /* = null */

    var label: String? /* = null */

    var role: String? /* = null */

}

/**
 * Exposes the JavaScript [ImageData](https://developer.mozilla.org/en/docs/Web/API/ImageData) to Kotlin
 */
public open external class ImageData : ImageBitmapSource, TexImageSource, JsAny {
    constructor(sw: Int, sh: Int)
    constructor(data: Uint8ClampedArray, sw: Int, sh: Int)

    open val width: Int
    open val height: Int
    open val data: Uint8ClampedArray
}

/**
 * Exposes the JavaScript [Path2D](https://developer.mozilla.org/en/docs/Web/API/Path2D) to Kotlin
 */
public open external class Path2D() : CanvasPath, JsAny {
    constructor(path: Path2D)
    constructor(paths: JsArray<Path2D>, fillRule: CanvasFillRule)
    constructor(d: String)

    fun addPath(path: Path2D, transform: JsAny?)
    override fun closePath()
    override fun moveTo(x: Double, y: Double)
    override fun lineTo(x: Double, y: Double)
    override fun quadraticCurveTo(cpx: Double, cpy: Double, x: Double, y: Double)
    override fun bezierCurveTo(cp1x: Double, cp1y: Double, cp2x: Double, cp2y: Double, x: Double, y: Double)
    override fun arcTo(x1: Double, y1: Double, x2: Double, y2: Double, radius: Double)
    override fun arcTo(
        x1: Double,
        y1: Double,
        x2: Double,
        y2: Double,
        radiusX: Double,
        radiusY: Double,
        rotation: Double
    )

    override fun rect(x: Double, y: Double, w: Double, h: Double)
    override fun arc(
        x: Double,
        y: Double,
        radius: Double,
        startAngle: Double,
        endAngle: Double,
        anticlockwise: Boolean /* */
    )

    override fun ellipse(
        x: Double,
        y: Double,
        radiusX: Double,
        radiusY: Double,
        rotation: Double,
        startAngle: Double,
        endAngle: Double,
        anticlockwise: Boolean /* */
    )
}

/**
 * Exposes the JavaScript [ImageBitmapRenderingContext](https://developer.mozilla.org/en/docs/Web/API/ImageBitmapRenderingContext) to Kotlin
 */
public abstract external class ImageBitmapRenderingContext : JsAny {
    open val canvas: HTMLCanvasElement
    fun transferFromImageBitmap(bitmap: ImageBitmap?)
}

public external interface ImageBitmapRenderingContextSettings : JsAny {
    var alpha: Boolean? /* = true */

}

/**
 * Exposes the JavaScript [CustomElementRegistry](https://developer.mozilla.org/en/docs/Web/API/CustomElementRegistry) to Kotlin
 */
public abstract external class CustomElementRegistry : JsAny {
    fun define(name: String, constructor: () -> JsAny?, options: ElementDefinitionOptions)
    fun get(name: String): JsAny?
    fun whenDefined(name: String): Promise<Nothing?>
}

public external interface ElementDefinitionOptions : JsAny {
    var extends: String?

}

public external interface ElementContentEditable : JsAny {
    var contentEditable: String
    val isContentEditable: Boolean
}

/**
 * Exposes the JavaScript [DataTransfer](https://developer.mozilla.org/en/docs/Web/API/DataTransfer) to Kotlin
 */
public abstract external class DataTransfer : JsAny {
    open var dropEffect: String
    open var effectAllowed: String
    open val items: DataTransferItemList
    open val types: JsArray<out JsString>
    open val files: FileList
    fun setDragImage(image: Element, x: Int, y: Int)
    fun getData(format: String): String
    fun setData(format: String, data: String)
    fun clearData(format: String)
}

/**
 * Exposes the JavaScript [DataTransferItemList](https://developer.mozilla.org/en/docs/Web/API/DataTransferItemList) to Kotlin
 */
public abstract external class DataTransferItemList : JsAny {
    open val length: Int
    fun add(data: String, type: String): DataTransferItem?
    fun add(data: File): DataTransferItem?
    fun remove(index: Int)
    fun clear()
}

/**
 * Exposes the JavaScript [DataTransferItem](https://developer.mozilla.org/en/docs/Web/API/DataTransferItem) to Kotlin
 */
public abstract external class DataTransferItem : JsAny {
    open val kind: String
    open val type: String
    fun getAsString(_callback: ((String) -> Unit)?)
    fun getAsFile(): File?
}

/**
 * Exposes the JavaScript [DragEvent](https://developer.mozilla.org/en/docs/Web/API/DragEvent) to Kotlin
 */
public open external class DragEvent(type: String, eventInitDict: DragEventInit) : MouseEvent, JsAny {
    open val dataTransfer: DataTransfer?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface DragEventInit : MouseEventInit, JsAny {
    var dataTransfer: DataTransfer? /* = null */

}

/**
 * Exposes the JavaScript [Window](https://developer.mozilla.org/en/docs/Web/API/Window) to Kotlin
 */
public abstract external class Window : EventTarget, GlobalEventHandlers, WindowEventHandlers,
    WindowOrWorkerGlobalScope, WindowSessionStorage, WindowLocalStorage, GlobalPerformance,
    UnionMessagePortOrWindowProxy, JsAny {
    open val window: Window
    open val self: Window
    open val document: Document
    open var name: String
    open val location: Location
    open val history: History
    open val customElements: CustomElementRegistry
    open val locationbar: BarProp
    open val menubar: BarProp
    open val personalbar: BarProp
    open val scrollbars: BarProp
    open val statusbar: BarProp
    open val toolbar: BarProp
    open var status: String
    open val closed: Boolean
    open val frames: Window
    open val length: Int
    open val top: Window
    open var opener: JsAny?
    open val parent: Window
    open val frameElement: Element?
    open val navigator: Navigator
    open val applicationCache: ApplicationCache
    open val external: External
    open val screen: Screen
    open val innerWidth: Int
    open val innerHeight: Int
    open val scrollX: Double
    open val pageXOffset: Double
    open val scrollY: Double
    open val pageYOffset: Double
    open val screenX: Int
    open val screenY: Int
    open val outerWidth: Int
    open val outerHeight: Int
    open val devicePixelRatio: Double
    fun close()
    fun stop()
    fun focus()
    fun blur()
    fun open(url: String, target: String, features: String): Window?
    fun alert()
    fun alert(message: String)
    fun confirm(message: String): Boolean
    fun prompt(message: String, default: String): String?
    fun print()
    fun requestAnimationFrame(callback: (Double) -> Unit): Int
    fun cancelAnimationFrame(handle: Int)
    fun postMessage(message: JsAny?, targetOrigin: String, transfer: JsArray<JsAny>)
    fun captureEvents()
    fun releaseEvents()
    fun matchMedia(query: String): MediaQueryList
    fun moveTo(x: Int, y: Int)
    fun moveBy(x: Int, y: Int)
    fun resizeTo(x: Int, y: Int)
    fun resizeBy(x: Int, y: Int)
    fun scroll(options: ScrollToOptions)
    fun scroll(x: Double, y: Double)
    fun scrollTo(options: ScrollToOptions)
    fun scrollTo(x: Double, y: Double)
    fun scrollBy(options: ScrollToOptions)
    fun scrollBy(x: Double, y: Double)
    fun getComputedStyle(elt: Element, pseudoElt: String?): CSSStyleDeclaration
}

public abstract external class BarProp : JsAny {
    open val visible: Boolean
}

/**
 * Exposes the JavaScript [History](https://developer.mozilla.org/en/docs/Web/API/History) to Kotlin
 */
public abstract external class History : JsAny {
    open val length: Int
    open var scrollRestoration: ScrollRestoration
    open val state: JsAny?
    fun go(delta: Int)
    fun back()
    fun forward()
    fun pushState(data: JsAny?, title: String, url: String?)
    fun replaceState(data: JsAny?, title: String, url: String?)
}

/**
 * Exposes the JavaScript [Location](https://developer.mozilla.org/en/docs/Web/API/Location) to Kotlin
 */
public abstract external class Location : JsAny {
    open var href: String
    open val origin: String
    open var protocol: String
    open var host: String
    open var hostname: String
    open var port: String
    open var pathname: String
    open var search: String
    open var hash: String
    open val ancestorOrigins: JsArray<out JsString>
    fun assign(url: String)
    fun replace(url: String)
    fun reload()
}

/**
 * Exposes the JavaScript [PopStateEvent](https://developer.mozilla.org/en/docs/Web/API/PopStateEvent) to Kotlin
 */
public open external class PopStateEvent(type: String, eventInitDict: PopStateEventInit) : Event, JsAny {
    open val state: JsAny?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface PopStateEventInit : EventInit, JsAny {
    var state: JsAny? /* = null */

}

/**
 * Exposes the JavaScript [HashChangeEvent](https://developer.mozilla.org/en/docs/Web/API/HashChangeEvent) to Kotlin
 */
public open external class HashChangeEvent(type: String, eventInitDict: HashChangeEventInit) : Event, JsAny {
    open val oldURL: String
    open val newURL: String

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface HashChangeEventInit : EventInit, JsAny {
    var oldURL: String? /* = "" */

    var newURL: String? /* = "" */

}

/**
 * Exposes the JavaScript [PageTransitionEvent](https://developer.mozilla.org/en/docs/Web/API/PageTransitionEvent) to Kotlin
 */
public open external class PageTransitionEvent(type: String, eventInitDict: PageTransitionEventInit) : Event, JsAny {
    open val persisted: Boolean

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface PageTransitionEventInit : EventInit, JsAny {
    var persisted: Boolean? /* = false */

}

/**
 * Exposes the JavaScript [BeforeUnloadEvent](https://developer.mozilla.org/en/docs/Web/API/BeforeUnloadEvent) to Kotlin
 */
public open external class BeforeUnloadEvent : Event, JsAny {
    var returnValue: String

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public abstract external class ApplicationCache : EventTarget, JsAny {
    open val status: Short
    open var onchecking: ((Event) -> Unit)?
    open var onerror: ((Event) -> Unit)?
    open var onnoupdate: ((Event) -> Unit)?
    open var ondownloading: ((Event) -> Unit)?
    open var onprogress: ((ProgressEvent) -> Unit)?
    open var onupdateready: ((Event) -> Unit)?
    open var oncached: ((Event) -> Unit)?
    open var onobsolete: ((Event) -> Unit)?
    fun update()
    fun abort()
    fun swapCache()

    companion object {
        val UNCACHED: Short
        val IDLE: Short
        val CHECKING: Short
        val DOWNLOADING: Short
        val UPDATEREADY: Short
        val OBSOLETE: Short
    }
}

/**
 * Exposes the JavaScript [NavigatorOnLine](https://developer.mozilla.org/en/docs/Web/API/NavigatorOnLine) to Kotlin
 */
public external interface NavigatorOnLine : JsAny {
    val onLine: Boolean
}

/**
 * Exposes the JavaScript [ErrorEvent](https://developer.mozilla.org/en/docs/Web/API/ErrorEvent) to Kotlin
 */
public open external class ErrorEvent(type: String, eventInitDict: ErrorEventInit) : Event, JsAny {
    open val message: String
    open val filename: String
    open val lineno: Int
    open val colno: Int
    open val error: JsAny?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface ErrorEventInit : EventInit, JsAny {
    var message: String? /* = "" */

    var filename: String? /* = "" */

    var lineno: Int? /* = 0 */

    var colno: Int? /* = 0 */

    var error: JsAny? /* = null */

}

/**
 * Exposes the JavaScript [PromiseRejectionEvent](https://developer.mozilla.org/en/docs/Web/API/PromiseRejectionEvent) to Kotlin
 */
public open external class PromiseRejectionEvent(type: String, eventInitDict: PromiseRejectionEventInit) : Event,
    JsAny {
    open val promise: Promise<*>
    open val reason: JsAny?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface PromiseRejectionEventInit : EventInit, JsAny {
    var promise: Promise<*>?
    var reason: JsAny?

}

/**
 * Exposes the JavaScript [GlobalEventHandlers](https://developer.mozilla.org/en/docs/Web/API/GlobalEventHandlers) to Kotlin
 */
public external interface GlobalEventHandlers : JsAny {
    var onabort: ((Event) -> Unit)?

    var onblur: ((FocusEvent) -> Unit)?

    var oncancel: ((Event) -> Unit)?

    var oncanplay: ((Event) -> Unit)?

    var oncanplaythrough: ((Event) -> Unit)?

    var onchange: ((Event) -> Unit)?

    var onclick: ((MouseEvent) -> Unit)?

    var onclose: ((Event) -> Unit)?

    var oncontextmenu: ((MouseEvent) -> Unit)?

    var oncuechange: ((Event) -> Unit)?

    var ondblclick: ((MouseEvent) -> Unit)?

    var ondrag: ((DragEvent) -> Unit)?

    var ondragend: ((DragEvent) -> Unit)?

    var ondragenter: ((DragEvent) -> Unit)?

    var ondragexit: ((DragEvent) -> Unit)?

    var ondragleave: ((DragEvent) -> Unit)?

    var ondragover: ((DragEvent) -> Unit)?

    var ondragstart: ((DragEvent) -> Unit)?

    var ondrop: ((DragEvent) -> Unit)?

    var ondurationchange: ((Event) -> Unit)?

    var onemptied: ((Event) -> Unit)?

    var onended: ((Event) -> Unit)?

    var onerror: ((JsAny? /* Event|String */, String, Int, Int, JsAny?) -> JsAny?)?

    var onfocus: ((FocusEvent) -> Unit)?

    var oninput: ((InputEvent) -> Unit)?

    var oninvalid: ((Event) -> Unit)?

    var onkeydown: ((KeyboardEvent) -> Unit)?

    var onkeypress: ((KeyboardEvent) -> Unit)?

    var onkeyup: ((KeyboardEvent) -> Unit)?

    var onload: ((Event) -> Unit)?

    var onloadeddata: ((Event) -> Unit)?

    var onloadedmetadata: ((Event) -> Unit)?

    var onloadend: ((Event) -> Unit)?

    var onloadstart: ((ProgressEvent) -> Unit)?

    var onmousedown: ((MouseEvent) -> Unit)?

    var onmouseenter: ((MouseEvent) -> Unit)?

    var onmouseleave: ((MouseEvent) -> Unit)?

    var onmousemove: ((MouseEvent) -> Unit)?

    var onmouseout: ((MouseEvent) -> Unit)?

    var onmouseover: ((MouseEvent) -> Unit)?

    var onmouseup: ((MouseEvent) -> Unit)?

    var onwheel: ((WheelEvent) -> Unit)?

    var onpause: ((Event) -> Unit)?

    var onplay: ((Event) -> Unit)?

    var onplaying: ((Event) -> Unit)?

    var onprogress: ((ProgressEvent) -> Unit)?

    var onratechange: ((Event) -> Unit)?

    var onreset: ((Event) -> Unit)?

    var onresize: ((Event) -> Unit)?

    var onscroll: ((Event) -> Unit)?

    var onseeked: ((Event) -> Unit)?

    var onseeking: ((Event) -> Unit)?

    var onselect: ((Event) -> Unit)?

    var onshow: ((Event) -> Unit)?

    var onstalled: ((Event) -> Unit)?

    var onsubmit: ((Event) -> Unit)?

    var onsuspend: ((Event) -> Unit)?

    var ontimeupdate: ((Event) -> Unit)?

    var ontoggle: ((Event) -> Unit)?

    var onvolumechange: ((Event) -> Unit)?

    var onwaiting: ((Event) -> Unit)?

    var ongotpointercapture: ((PointerEvent) -> Unit)?

    var onlostpointercapture: ((PointerEvent) -> Unit)?

    var onpointerdown: ((PointerEvent) -> Unit)?

    var onpointermove: ((PointerEvent) -> Unit)?

    var onpointerup: ((PointerEvent) -> Unit)?

    var onpointercancel: ((PointerEvent) -> Unit)?

    var onpointerover: ((PointerEvent) -> Unit)?

    var onpointerout: ((PointerEvent) -> Unit)?

    var onpointerenter: ((PointerEvent) -> Unit)?

    var onpointerleave: ((PointerEvent) -> Unit)?

}

/**
 * Exposes the JavaScript [WindowEventHandlers](https://developer.mozilla.org/en/docs/Web/API/WindowEventHandlers) to Kotlin
 */
public external interface WindowEventHandlers : JsAny {
    var onafterprint: ((Event) -> Unit)?

    var onbeforeprint: ((Event) -> Unit)?

    var onbeforeunload: ((BeforeUnloadEvent) -> String?)?

    var onhashchange: ((HashChangeEvent) -> Unit)?

    var onlanguagechange: ((Event) -> Unit)?

    var onmessage: ((MessageEvent) -> Unit)?

    var onoffline: ((Event) -> Unit)?

    var ononline: ((Event) -> Unit)?

    var onpagehide: ((PageTransitionEvent) -> Unit)?

    var onpageshow: ((PageTransitionEvent) -> Unit)?

    var onpopstate: ((PopStateEvent) -> Unit)?

    var onrejectionhandled: ((Event) -> Unit)?

    var onstorage: ((StorageEvent) -> Unit)?

    var onunhandledrejection: ((PromiseRejectionEvent) -> Unit)?

    var onunload: ((Event) -> Unit)?

}

public external interface DocumentAndElementEventHandlers : JsAny {
    var oncopy: ((ClipboardEvent) -> Unit)?

    var oncut: ((ClipboardEvent) -> Unit)?

    var onpaste: ((ClipboardEvent) -> Unit)?

}

/**
 * Exposes the JavaScript [WindowOrWorkerGlobalScope](https://developer.mozilla.org/en/docs/Web/API/WindowOrWorkerGlobalScope) to Kotlin
 */
public external interface WindowOrWorkerGlobalScope : JsAny {
    val origin: String
    val caches: CacheStorage
    fun btoa(data: String): String
    fun atob(data: String): String
    fun setTimeout(handler: String, timeout: Int, vararg arguments: JsAny?): Int
    fun setTimeout(handler: () -> JsAny?, timeout: Int, vararg arguments: JsAny?): Int
    fun clearTimeout(handle: Int)
    fun setInterval(handler: String, timeout: Int, vararg arguments: JsAny?): Int
    fun setInterval(handler: () -> JsAny?, timeout: Int, vararg arguments: JsAny?): Int
    fun clearInterval(handle: Int)
    fun createImageBitmap(image: ImageBitmapSource, options: ImageBitmapOptions): Promise<*>
    fun createImageBitmap(
        image: ImageBitmapSource,
        sx: Int,
        sy: Int,
        sw: Int,
        sh: Int,
        options: ImageBitmapOptions
    ): Promise<*>

    fun fetch(input: Request, init: RequestInit): Promise<Response>
    fun fetch(input: String, init: RequestInit): Promise<Response>
}

/**
 * Exposes the JavaScript [Navigator](https://developer.mozilla.org/en/docs/Web/API/Navigator) to Kotlin
 */
public abstract external class Navigator : NavigatorID, NavigatorLanguage, NavigatorOnLine, NavigatorContentUtils,
    NavigatorCookies, NavigatorPlugins, NavigatorConcurrentHardware, JsAny {
    open val clipboard: Clipboard
    open val mediaDevices: MediaDevices
    open val maxTouchPoints: Int
    open val serviceWorker: ServiceWorkerContainer
    fun requestMediaKeySystemAccess(
        keySystem: String,
        supportedConfigurations: JsArray<MediaKeySystemConfiguration>
    ): Promise<*>

    fun getUserMedia(
        constraints: MediaStreamConstraints,
        successCallback: (MediaStream) -> Unit,
        errorCallback: (JsAny) -> Unit
    )

    fun vibrate(pattern: Int): Boolean
    fun vibrate(pattern: JsArray<JsNumber>): Boolean
}

/**
 * Exposes the JavaScript [NavigatorID](https://developer.mozilla.org/en/docs/Web/API/NavigatorID) to Kotlin
 */
public external interface NavigatorID : JsAny {
    val appCodeName: String
    val appName: String
    val appVersion: String
    val platform: String
    val product: String
    val productSub: String
    val userAgent: String
    val vendor: String
    val vendorSub: String
    val oscpu: String
    fun taintEnabled(): Boolean
}

/**
 * Exposes the JavaScript [NavigatorLanguage](https://developer.mozilla.org/en/docs/Web/API/NavigatorLanguage) to Kotlin
 */
public external interface NavigatorLanguage : JsAny {
    val language: String
    val languages: JsArray<out JsString>
}

public external interface NavigatorContentUtils : JsAny {
    fun registerProtocolHandler(scheme: String, url: String, title: String)
    fun registerContentHandler(mimeType: String, url: String, title: String)
    fun isProtocolHandlerRegistered(scheme: String, url: String): String
    fun isContentHandlerRegistered(mimeType: String, url: String): String
    fun unregisterProtocolHandler(scheme: String, url: String)
    fun unregisterContentHandler(mimeType: String, url: String)
}

public external interface NavigatorCookies : JsAny {
    val cookieEnabled: Boolean
}

/**
 * Exposes the JavaScript [NavigatorPlugins](https://developer.mozilla.org/en/docs/Web/API/NavigatorPlugins) to Kotlin
 */
public external interface NavigatorPlugins : JsAny {
    val plugins: PluginArray
    val mimeTypes: MimeTypeArray
    fun javaEnabled(): Boolean
}

/**
 * Exposes the JavaScript [PluginArray](https://developer.mozilla.org/en/docs/Web/API/PluginArray) to Kotlin
 */
public abstract external class PluginArray : ItemArrayLike<Plugin>, JsAny {
    fun refresh(reload: Boolean)
    override fun item(index: Int): Plugin?
    fun namedItem(name: String): Plugin?
}

/**
 * Exposes the JavaScript [MimeTypeArray](https://developer.mozilla.org/en/docs/Web/API/MimeTypeArray) to Kotlin
 */
public abstract external class MimeTypeArray : ItemArrayLike<MimeType>, JsAny {
    override fun item(index: Int): MimeType?
    fun namedItem(name: String): MimeType?
}

/**
 * Exposes the JavaScript [Plugin](https://developer.mozilla.org/en/docs/Web/API/Plugin) to Kotlin
 */
public abstract external class Plugin : ItemArrayLike<MimeType>, JsAny {
    open val name: String
    open val description: String
    open val filename: String
    override fun item(index: Int): MimeType?
    fun namedItem(name: String): MimeType?
}

/**
 * Exposes the JavaScript [MimeType](https://developer.mozilla.org/en/docs/Web/API/MimeType) to Kotlin
 */
public abstract external class MimeType : JsAny {
    open val type: String
    open val description: String
    open val suffixes: String
    open val enabledPlugin: Plugin
}

/**
 * Exposes the JavaScript [ImageBitmap](https://developer.mozilla.org/en/docs/Web/API/ImageBitmap) to Kotlin
 */
public abstract external class ImageBitmap : CanvasImageSource, TexImageSource, JsAny {
    open val width: Int
    open val height: Int
    fun close()
}

public external interface ImageBitmapOptions : JsAny {
    var imageOrientation: ImageOrientation? /* = ImageOrientation.NONE */

    var premultiplyAlpha: PremultiplyAlpha? /* = PremultiplyAlpha.DEFAULT */

    var colorSpaceConversion: ColorSpaceConversion? /* = ColorSpaceConversion.DEFAULT */

    var resizeWidth: Int?

    var resizeHeight: Int?

    var resizeQuality: ResizeQuality? /* = ResizeQuality.LOW */

}

/**
 * Exposes the JavaScript [MessageEvent](https://developer.mozilla.org/en/docs/Web/API/MessageEvent) to Kotlin
 */
public open external class MessageEvent(type: String, eventInitDict: MessageEventInit) : Event, JsAny {
    open val data: JsAny?
    open val origin: String
    open val lastEventId: String
    open val source: UnionMessagePortOrWindowProxy?
    open val ports: JsArray<out MessagePort>
    fun initMessageEvent(
        type: String,
        bubbles: Boolean,
        cancelable: Boolean,
        data: JsAny?,
        origin: String,
        lastEventId: String,
        source: UnionMessagePortOrWindowProxy?,
        ports: JsArray<MessagePort>
    )

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface MessageEventInit : EventInit, JsAny {
    var data: JsAny? /* = null */

    var origin: String? /* = "" */

    var lastEventId: String? /* = "" */

    var source: UnionMessagePortOrWindowProxy? /* = null */

    var ports: JsArray<MessagePort>? /* = arrayOf() */

}

/**
 * Exposes the JavaScript [EventSource](https://developer.mozilla.org/en/docs/Web/API/EventSource) to Kotlin
 */
public open external class EventSource(url: String, eventSourceInitDict: EventSourceInit) : EventTarget, JsAny {
    open val url: String
    open val withCredentials: Boolean
    open val readyState: Short
    var onopen: ((Event) -> Unit)?
    var onmessage: ((MessageEvent) -> Unit)?
    var onerror: ((Event) -> Unit)?
    fun close()

    companion object {
        val CONNECTING: Short
        val OPEN: Short
        val CLOSED: Short
    }
}

public external interface EventSourceInit : JsAny {
    var withCredentials: Boolean? /* = false */

}

/**
 * Exposes the JavaScript [WebSocket](https://developer.mozilla.org/en/docs/Web/API/WebSocket) to Kotlin
 */
public open external class WebSocket(url: String, protocols: JsAny? /* String|JsArray<JsString> */) : EventTarget,
    JsAny {
    open val url: String
    open val readyState: Short
    open val bufferedAmount: JsNumber
    var onopen: ((Event) -> Unit)?
    var onerror: ((Event) -> Unit)?
    var onclose: ((Event) -> Unit)?
    open val extensions: String
    open val protocol: String
    var onmessage: ((MessageEvent) -> Unit)?
    var binaryType: BinaryType
    fun close(code: Short, reason: String)
    fun send(data: String)
    fun send(data: Blob)
    fun send(data: ArrayBuffer)
    fun send(data: ArrayBufferView)

    companion object {
        val CONNECTING: Short
        val OPEN: Short
        val CLOSING: Short
        val CLOSED: Short
    }
}

/**
 * Exposes the JavaScript [CloseEvent](https://developer.mozilla.org/en/docs/Web/API/CloseEvent) to Kotlin
 */
public open external class CloseEvent(type: String, eventInitDict: CloseEventInit) : Event, JsAny {
    open val wasClean: Boolean
    open val code: Short
    open val reason: String

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface CloseEventInit : EventInit, JsAny {
    var wasClean: Boolean? /* = false */

    var code: Short? /* = 0 */

    var reason: String? /* = "" */

}

/**
 * Exposes the JavaScript [MessageChannel](https://developer.mozilla.org/en/docs/Web/API/MessageChannel) to Kotlin
 */
public open external class MessageChannel : JsAny {
    open val port1: MessagePort
    open val port2: MessagePort
}

/**
 * Exposes the JavaScript [MessagePort](https://developer.mozilla.org/en/docs/Web/API/MessagePort) to Kotlin
 */
public abstract external class MessagePort : EventTarget, UnionMessagePortOrWindowProxy,
    UnionMessagePortOrServiceWorker, UnionClientOrMessagePortOrServiceWorker, JsAny {
    open var onmessage: ((MessageEvent) -> Unit)?
    fun postMessage(message: JsAny?, transfer: JsArray<JsAny>)
    fun start()
    fun close()
}

/**
 * Exposes the JavaScript [BroadcastChannel](https://developer.mozilla.org/en/docs/Web/API/BroadcastChannel) to Kotlin
 */
public open external class BroadcastChannel(name: String) : EventTarget, JsAny {
    open val name: String
    var onmessage: ((MessageEvent) -> Unit)?
    fun postMessage(message: JsAny?)
    fun close()
}

/**
 * Exposes the JavaScript [WorkerGlobalScope](https://developer.mozilla.org/en/docs/Web/API/WorkerGlobalScope) to Kotlin
 */
public abstract external class WorkerGlobalScope : EventTarget, WindowOrWorkerGlobalScope, GlobalPerformance, JsAny {
    open val self: WorkerGlobalScope
    open val location: WorkerLocation
    open val navigator: WorkerNavigator
    open var onerror: ((JsAny? /* Event|String */, String, Int, Int, JsAny?) -> JsAny?)?
    open var onlanguagechange: ((Event) -> Unit)?
    open var onoffline: ((Event) -> Unit)?
    open var ononline: ((Event) -> Unit)?
    open var onrejectionhandled: ((Event) -> Unit)?
    open var onunhandledrejection: ((PromiseRejectionEvent) -> Unit)?
    fun importScripts(vararg urls: String)
}

/**
 * Exposes the JavaScript [DedicatedWorkerGlobalScope](https://developer.mozilla.org/en/docs/Web/API/DedicatedWorkerGlobalScope) to Kotlin
 */
public abstract external class DedicatedWorkerGlobalScope : WorkerGlobalScope, JsAny {
    open var onmessage: ((MessageEvent) -> Unit)?
    fun postMessage(message: JsAny?, transfer: JsArray<JsAny>)
    fun close()
}

/**
 * Exposes the JavaScript [SharedWorkerGlobalScope](https://developer.mozilla.org/en/docs/Web/API/SharedWorkerGlobalScope) to Kotlin
 */
public abstract external class SharedWorkerGlobalScope : WorkerGlobalScope, JsAny {
    open val name: String
    open val applicationCache: ApplicationCache
    open var onconnect: ((Event) -> Unit)?
    fun close()
}

/**
 * Exposes the JavaScript [AbstractWorker](https://developer.mozilla.org/en/docs/Web/API/AbstractWorker) to Kotlin
 */
public external interface AbstractWorker : JsAny {
    var onerror: ((Event) -> Unit)?

}

/**
 * Exposes the JavaScript [Worker](https://developer.mozilla.org/en/docs/Web/API/Worker) to Kotlin
 */
public open external class Worker(scriptURL: String, options: WorkerOptions) : EventTarget, AbstractWorker, JsAny {
    var onmessage: ((MessageEvent) -> Unit)?
    override var onerror: ((Event) -> Unit)?
    fun terminate()
    fun postMessage(message: JsAny?, transfer: JsArray<JsAny>)
}

public external interface WorkerOptions : JsAny {
    var type: WorkerType? /* = WorkerType.CLASSIC */

    var credentials: RequestCredentials? /* = RequestCredentials.OMIT */

}

/**
 * Exposes the JavaScript [SharedWorker](https://developer.mozilla.org/en/docs/Web/API/SharedWorker) to Kotlin
 */
public open external class SharedWorker(scriptURL: String, name: String, options: WorkerOptions) : EventTarget,
    AbstractWorker, JsAny {
    open val port: MessagePort
    override var onerror: ((Event) -> Unit)?
}

/**
 * Exposes the JavaScript [NavigatorConcurrentHardware](https://developer.mozilla.org/en/docs/Web/API/NavigatorConcurrentHardware) to Kotlin
 */
public external interface NavigatorConcurrentHardware : JsAny {
    val hardwareConcurrency: JsNumber
}

/**
 * Exposes the JavaScript [WorkerNavigator](https://developer.mozilla.org/en/docs/Web/API/WorkerNavigator) to Kotlin
 */
public abstract external class WorkerNavigator : NavigatorID, NavigatorLanguage, NavigatorOnLine,
    NavigatorConcurrentHardware, JsAny {
    open val serviceWorker: ServiceWorkerContainer
}

/**
 * Exposes the JavaScript [WorkerLocation](https://developer.mozilla.org/en/docs/Web/API/WorkerLocation) to Kotlin
 */
public abstract external class WorkerLocation : JsAny {
    open val href: String
    open val origin: String
    open val protocol: String
    open val host: String
    open val hostname: String
    open val port: String
    open val pathname: String
    open val search: String
    open val hash: String
}

/**
 * Exposes the JavaScript [Storage](https://developer.mozilla.org/en/docs/Web/API/Storage) to Kotlin
 */
public abstract external class Storage : JsAny {
    open val length: Int
    fun key(index: Int): String?
    fun removeItem(key: String)
    fun clear()
    fun getItem(key: String): String?
    fun setItem(key: String, value: String)
}

/**
 * Exposes the JavaScript [WindowSessionStorage](https://developer.mozilla.org/en/docs/Web/API/WindowSessionStorage) to Kotlin
 */
public external interface WindowSessionStorage : JsAny {
    val sessionStorage: Storage
}

/**
 * Exposes the JavaScript [WindowLocalStorage](https://developer.mozilla.org/en/docs/Web/API/WindowLocalStorage) to Kotlin
 */
public external interface WindowLocalStorage : JsAny {
    val localStorage: Storage
}

/**
 * Exposes the JavaScript [StorageEvent](https://developer.mozilla.org/en/docs/Web/API/StorageEvent) to Kotlin
 */
public open external class StorageEvent(type: String, eventInitDict: StorageEventInit) : Event, JsAny {
    open val key: String?
    open val oldValue: String?
    open val newValue: String?
    open val url: String
    open val storageArea: Storage?

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface StorageEventInit : EventInit, JsAny {
    var key: String? /* = null */

    var oldValue: String? /* = null */

    var newValue: String? /* = null */

    var url: String? /* = "" */

    var storageArea: Storage? /* = null */

}

public abstract external class HTMLAppletElement : HTMLElement, JsAny {
    open var align: String
    open var alt: String
    open var archive: String
    open var code: String
    open var codeBase: String
    open var height: String
    open var hspace: Int
    open var name: String
    open var _object: String
    open var vspace: Int
    open var width: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLMarqueeElement](https://developer.mozilla.org/en/docs/Web/API/HTMLMarqueeElement) to Kotlin
 */
public abstract external class HTMLMarqueeElement : HTMLElement, JsAny {
    open var behavior: String
    open var bgColor: String
    open var direction: String
    open var height: String
    open var hspace: Int
    open var loop: Int
    open var scrollAmount: Int
    open var scrollDelay: Int
    open var trueSpeed: Boolean
    open var vspace: Int
    open var width: String
    open var onbounce: ((Event) -> Unit)?
    open var onfinish: ((Event) -> Unit)?
    open var onstart: ((Event) -> Unit)?
    fun start()
    fun stop()

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLFrameSetElement](https://developer.mozilla.org/en/docs/Web/API/HTMLFrameSetElement) to Kotlin
 */
public abstract external class HTMLFrameSetElement : HTMLElement, WindowEventHandlers, JsAny {
    open var cols: String
    open var rows: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public abstract external class HTMLFrameElement : HTMLElement, JsAny {
    open var name: String
    open var scrolling: String
    open var src: String
    open var frameBorder: String
    open var longDesc: String
    open var noResize: Boolean
    open val contentDocument: Document?
    open val contentWindow: Window?
    open var marginHeight: String
    open var marginWidth: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public abstract external class HTMLDirectoryElement : HTMLElement, JsAny {
    open var compact: Boolean

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [HTMLFontElement](https://developer.mozilla.org/en/docs/Web/API/HTMLFontElement) to Kotlin
 */
public abstract external class HTMLFontElement : HTMLElement, JsAny {
    open var color: String
    open var face: String
    open var size: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public external interface External : JsAny {
    fun AddSearchProvider()
    fun IsSearchProviderInstalled()
}

public external interface EventInit : JsAny {
    var bubbles: Boolean? /* = false */

    var cancelable: Boolean? /* = false */

    var composed: Boolean? /* = false */

}

/**
 * Exposes the JavaScript [CustomEvent](https://developer.mozilla.org/en/docs/Web/API/CustomEvent) to Kotlin
 */
public open external class CustomEvent(type: String, eventInitDict: CustomEventInit) : Event, JsAny {
    open val detail: JsAny?
    fun initCustomEvent(type: String, bubbles: Boolean, cancelable: Boolean, detail: JsAny?)

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface CustomEventInit : EventInit, JsAny {
    var detail: JsAny? /* = null */

}

public external interface EventListenerOptions : JsAny {
    var capture: Boolean? /* = false */

}

public external interface AddEventListenerOptions : EventListenerOptions, JsAny {
    var passive: Boolean? /* = false */

    var once: Boolean? /* = false */

}

public external interface NonElementParentNode : JsAny {
    fun getElementById(elementId: String): Element?
}

/**
 * Exposes the JavaScript [DocumentOrShadowRoot](https://developer.mozilla.org/en/docs/Web/API/DocumentOrShadowRoot) to Kotlin
 */
public external interface DocumentOrShadowRoot : JsAny {
    val fullscreenElement: Element?
}

/**
 * Exposes the JavaScript [ParentNode](https://developer.mozilla.org/en/docs/Web/API/ParentNode) to Kotlin
 */
public external interface ParentNode : JsAny {
    val children: HTMLCollection
    val firstElementChild: Element?
    val lastElementChild: Element?
    val childElementCount: Int
    fun prepend(vararg nodes: Node)
    fun prepend(vararg nodes: String)
    fun append(vararg nodes: Node)
    fun append(vararg nodes: String)
    fun querySelector(selectors: String): Element?
    fun querySelectorAll(selectors: String): NodeList
}

/**
 * Exposes the JavaScript [NonDocumentTypeChildNode](https://developer.mozilla.org/en/docs/Web/API/NonDocumentTypeChildNode) to Kotlin
 */
public external interface NonDocumentTypeChildNode : JsAny {
    val previousElementSibling: Element?
    val nextElementSibling: Element?
}

/**
 * Exposes the JavaScript [ChildNode](https://developer.mozilla.org/en/docs/Web/API/ChildNode) to Kotlin
 */
public external interface ChildNode : JsAny {
    fun before(vararg nodes: Node)
    fun before(vararg nodes: String)
    fun after(vararg nodes: Node)
    fun after(vararg nodes: String)
    fun replaceWith(vararg nodes: Node)
    fun replaceWith(vararg nodes: String)
    fun remove()
}

/**
 * Exposes the JavaScript [Slotable](https://developer.mozilla.org/en/docs/Web/API/Slotable) to Kotlin
 */
public external interface Slotable : JsAny {
    val assignedSlot: HTMLSlotElement?
}

/**
 * Exposes the JavaScript [NodeList](https://developer.mozilla.org/en/docs/Web/API/NodeList) to Kotlin
 */
public abstract external class NodeList : ItemArrayLike<Node>, JsAny {
    override fun item(index: Int): Node?
}

public operator fun NodeList.get(index: Int): Node? = this.item(index)

/**
 * Exposes the JavaScript [HTMLCollection](https://developer.mozilla.org/en/docs/Web/API/HTMLCollection) to Kotlin
 */
public abstract external class HTMLCollection : ItemArrayLike<Element>, UnionElementOrHTMLCollection, JsAny {
    override fun item(index: Int): Element?
    fun namedItem(name: String): Element?
}

/**
 * Exposes the JavaScript [MutationObserver](https://developer.mozilla.org/en/docs/Web/API/MutationObserver) to Kotlin
 */
public open external class MutationObserver(callback: (JsArray<MutationRecord>, MutationObserver) -> Unit) : JsAny {
    fun observe(target: Node, options: MutationObserverInit)
    fun disconnect()
    fun takeRecords(): JsArray<MutationRecord>
}

/**
 * Exposes the JavaScript [MutationObserverInit](https://developer.mozilla.org/en/docs/Web/API/MutationObserverInit) to Kotlin
 */
public external interface MutationObserverInit : JsAny {
    var childList: Boolean? /* = false */

    var attributes: Boolean?

    var characterData: Boolean?

    var subtree: Boolean? /* = false */

    var attributeOldValue: Boolean?

    var characterDataOldValue: Boolean?

    var attributeFilter: JsArray<JsString>?

}

/**
 * Exposes the JavaScript [MutationRecord](https://developer.mozilla.org/en/docs/Web/API/MutationRecord) to Kotlin
 */
public abstract external class MutationRecord : JsAny {
    open val type: String
    open val target: Node
    open val addedNodes: NodeList
    open val removedNodes: NodeList
    open val previousSibling: Node?
    open val nextSibling: Node?
    open val attributeName: String?
    open val attributeNamespace: String?
    open val oldValue: String?
}

/**
 * Exposes the JavaScript [Node](https://developer.mozilla.org/en/docs/Web/API/Node) to Kotlin
 */
public abstract external class Node : EventTarget, JsAny {
    open val nodeType: Short
    open val nodeName: String
    open val baseURI: String
    open val isConnected: Boolean
    open val ownerDocument: Document?
    open val parentNode: Node?
    open val parentElement: Element?
    open val childNodes: NodeList
    open val firstChild: Node?
    open val lastChild: Node?
    open val previousSibling: Node?
    open val nextSibling: Node?
    open var nodeValue: String?
    open var textContent: String?
    fun getRootNode(options: GetRootNodeOptions): Node
    fun hasChildNodes(): Boolean
    fun normalize()
    fun cloneNode(deep: Boolean): Node
    fun isEqualNode(otherNode: Node?): Boolean
    fun isSameNode(otherNode: Node?): Boolean
    fun compareDocumentPosition(other: Node): Short
    fun contains(other: Node?): Boolean
    fun lookupPrefix(namespace: String?): String?
    fun lookupNamespaceURI(prefix: String?): String?
    fun isDefaultNamespace(namespace: String?): Boolean
    fun insertBefore(node: Node, child: Node?): Node
    fun appendChild(node: Node): Node
    fun replaceChild(node: Node, child: Node): Node
    fun removeChild(child: Node): Node

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public external interface GetRootNodeOptions : JsAny {
    var composed: Boolean? /* = false */

}

/**
 * Exposes the JavaScript [Document](https://developer.mozilla.org/en/docs/Web/API/Document) to Kotlin
 */
public open external class Document : Node, GlobalEventHandlers, DocumentAndElementEventHandlers, NonElementParentNode,
    DocumentOrShadowRoot, ParentNode, GeometryUtils, JsAny {
    open val implementation: DOMImplementation
    open val URL: String
    open val documentURI: String
    open val origin: String
    open val compatMode: String
    open val characterSet: String
    open val charset: String
    open val inputEncoding: String
    open val contentType: String
    open val doctype: DocumentType?
    open val documentElement: Element?
    open val location: Location?
    var domain: String
    open val referrer: String
    var cookie: String
    open val lastModified: String
    open val readyState: DocumentReadyState
    var title: String
    var dir: String
    var body: HTMLElement?
    open val head: HTMLHeadElement?
    open val images: HTMLCollection
    open val embeds: HTMLCollection
    open val plugins: HTMLCollection
    open val links: HTMLCollection
    open val forms: HTMLCollection
    open val scripts: HTMLCollection
    open val currentScript: HTMLOrSVGScriptElement?
    open val defaultView: Window?
    open val activeElement: Element?
    var designMode: String
    var onreadystatechange: ((Event) -> Unit)?
    var fgColor: String
    var linkColor: String
    var vlinkColor: String
    var alinkColor: String
    var bgColor: String
    open val anchors: HTMLCollection
    open val applets: HTMLCollection
    open val all: HTMLAllCollection
    open val scrollingElement: Element?
    open val styleSheets: StyleSheetList
    open val rootElement: SVGSVGElement?
    open val fullscreenEnabled: Boolean
    open val fullscreen: Boolean
    var onfullscreenchange: ((Event) -> Unit)?
    var onfullscreenerror: ((Event) -> Unit)?
    override var onabort: ((Event) -> Unit)?
    override var onblur: ((FocusEvent) -> Unit)?
    override var oncancel: ((Event) -> Unit)?
    override var oncanplay: ((Event) -> Unit)?
    override var oncanplaythrough: ((Event) -> Unit)?
    override var onchange: ((Event) -> Unit)?
    override var onclick: ((MouseEvent) -> Unit)?
    override var onclose: ((Event) -> Unit)?
    override var oncontextmenu: ((MouseEvent) -> Unit)?
    override var oncuechange: ((Event) -> Unit)?
    override var ondblclick: ((MouseEvent) -> Unit)?
    override var ondrag: ((DragEvent) -> Unit)?
    override var ondragend: ((DragEvent) -> Unit)?
    override var ondragenter: ((DragEvent) -> Unit)?
    override var ondragexit: ((DragEvent) -> Unit)?
    override var ondragleave: ((DragEvent) -> Unit)?
    override var ondragover: ((DragEvent) -> Unit)?
    override var ondragstart: ((DragEvent) -> Unit)?
    override var ondrop: ((DragEvent) -> Unit)?
    override var ondurationchange: ((Event) -> Unit)?
    override var onemptied: ((Event) -> Unit)?
    override var onended: ((Event) -> Unit)?
    override var onerror: ((JsAny? /* Event|String */, String, Int, Int, JsAny?) -> JsAny?)?
    override var onfocus: ((FocusEvent) -> Unit)?
    override var oninput: ((InputEvent) -> Unit)?
    override var oninvalid: ((Event) -> Unit)?
    override var onkeydown: ((KeyboardEvent) -> Unit)?
    override var onkeypress: ((KeyboardEvent) -> Unit)?
    override var onkeyup: ((KeyboardEvent) -> Unit)?
    override var onload: ((Event) -> Unit)?
    override var onloadeddata: ((Event) -> Unit)?
    override var onloadedmetadata: ((Event) -> Unit)?
    override var onloadend: ((Event) -> Unit)?
    override var onloadstart: ((ProgressEvent) -> Unit)?
    override var onmousedown: ((MouseEvent) -> Unit)?
    override var onmouseenter: ((MouseEvent) -> Unit)?
    override var onmouseleave: ((MouseEvent) -> Unit)?
    override var onmousemove: ((MouseEvent) -> Unit)?
    override var onmouseout: ((MouseEvent) -> Unit)?
    override var onmouseover: ((MouseEvent) -> Unit)?
    override var onmouseup: ((MouseEvent) -> Unit)?
    override var onwheel: ((WheelEvent) -> Unit)?
    override var onpause: ((Event) -> Unit)?
    override var onplay: ((Event) -> Unit)?
    override var onplaying: ((Event) -> Unit)?
    override var onprogress: ((ProgressEvent) -> Unit)?
    override var onratechange: ((Event) -> Unit)?
    override var onreset: ((Event) -> Unit)?
    override var onresize: ((Event) -> Unit)?
    override var onscroll: ((Event) -> Unit)?
    override var onseeked: ((Event) -> Unit)?
    override var onseeking: ((Event) -> Unit)?
    override var onselect: ((Event) -> Unit)?
    override var onshow: ((Event) -> Unit)?
    override var onstalled: ((Event) -> Unit)?
    override var onsubmit: ((Event) -> Unit)?
    override var onsuspend: ((Event) -> Unit)?
    override var ontimeupdate: ((Event) -> Unit)?
    override var ontoggle: ((Event) -> Unit)?
    override var onvolumechange: ((Event) -> Unit)?
    override var onwaiting: ((Event) -> Unit)?
    override var ongotpointercapture: ((PointerEvent) -> Unit)?
    override var onlostpointercapture: ((PointerEvent) -> Unit)?
    override var onpointerdown: ((PointerEvent) -> Unit)?
    override var onpointermove: ((PointerEvent) -> Unit)?
    override var onpointerup: ((PointerEvent) -> Unit)?
    override var onpointercancel: ((PointerEvent) -> Unit)?
    override var onpointerover: ((PointerEvent) -> Unit)?
    override var onpointerout: ((PointerEvent) -> Unit)?
    override var onpointerenter: ((PointerEvent) -> Unit)?
    override var onpointerleave: ((PointerEvent) -> Unit)?
    override var oncopy: ((ClipboardEvent) -> Unit)?
    override var oncut: ((ClipboardEvent) -> Unit)?
    override var onpaste: ((ClipboardEvent) -> Unit)?
    override val fullscreenElement: Element?
    override val children: HTMLCollection
    override val firstElementChild: Element?
    override val lastElementChild: Element?
    override val childElementCount: Int
    fun getElementsByTagName(qualifiedName: String): HTMLCollection
    fun getElementsByTagNameNS(namespace: String?, localName: String): HTMLCollection
    fun getElementsByClassName(classNames: String): HTMLCollection
    fun createElement(localName: String): Element
    fun createElement(localName: String, options: ElementCreationOptions): Element
    fun createElementNS(namespace: String?, qualifiedName: String): Element
    fun createElementNS(namespace: String?, qualifiedName: String, options: ElementCreationOptions): Element
    fun createDocumentFragment(): DocumentFragment
    fun createTextNode(data: String): Text
    fun createCDATASection(data: String): CDATASection
    fun createComment(data: String): Comment
    fun createProcessingInstruction(target: String, data: String): ProcessingInstruction
    fun importNode(node: Node, deep: Boolean): Node
    fun adoptNode(node: Node): Node
    fun createAttribute(localName: String): Attr
    fun createAttributeNS(namespace: String?, qualifiedName: String): Attr
    fun createEvent(param_interface: String): Event
    fun createRange(): Range
    fun createNodeIterator(root: Node, whatToShow: Int, filter: NodeFilter?): NodeIterator
    fun createNodeIterator(root: Node, whatToShow: Int, filter: ((Node) -> Short)?): NodeIterator
    fun createTreeWalker(root: Node, whatToShow: Int, filter: NodeFilter?): TreeWalker
    fun createTreeWalker(root: Node, whatToShow: Int, filter: ((Node) -> Short)?): TreeWalker
    fun getElementsByName(elementName: String): NodeList
    fun open(type: String, replace: String): Document
    fun open(url: String, name: String, features: String): Window
    fun close()
    fun write(vararg text: String)
    fun writeln(vararg text: String)
    fun hasFocus(): Boolean
    fun execCommand(commandId: String, showUI: Boolean, value: String): Boolean
    fun queryCommandEnabled(commandId: String): Boolean
    fun queryCommandIndeterm(commandId: String): Boolean
    fun queryCommandState(commandId: String): Boolean
    fun queryCommandSupported(commandId: String): Boolean
    fun queryCommandValue(commandId: String): String
    fun clear()
    fun captureEvents()
    fun releaseEvents()
    fun elementFromPoint(x: Double, y: Double): Element?
    fun elementsFromPoint(x: Double, y: Double): JsArray<Element>
    fun caretPositionFromPoint(x: Double, y: Double): CaretPosition?
    fun createTouch(
        view: Window,
        target: EventTarget,
        identifier: Int,
        pageX: Int,
        pageY: Int,
        screenX: Int,
        screenY: Int
    ): Touch

    fun createTouchList(vararg touches: Touch): TouchList
    fun exitFullscreen(): Promise<Nothing?>
    override fun getElementById(elementId: String): Element?
    override fun prepend(vararg nodes: Node)
    override fun prepend(vararg nodes: String)
    override fun append(vararg nodes: Node)
    override fun append(vararg nodes: String)
    override fun querySelector(selectors: String): Element?
    override fun querySelectorAll(selectors: String): NodeList
    override fun getBoxQuads(options: BoxQuadOptions /* */): JsArray<DOMQuad>
    override fun convertQuadFromNode(quad: JsAny?, from: Text, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertQuadFromNode(quad: JsAny?, from: Element, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertQuadFromNode(quad: JsAny?, from: Document, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Text,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Element,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Document,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Text,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Element,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Document,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [XMLDocument](https://developer.mozilla.org/en/docs/Web/API/XMLDocument) to Kotlin
 */
public open external class XMLDocument : Document, JsAny {
    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public external interface ElementCreationOptions : JsAny {
    var `is`: String?

}

/**
 * Exposes the JavaScript [DOMImplementation](https://developer.mozilla.org/en/docs/Web/API/DOMImplementation) to Kotlin
 */
public abstract external class DOMImplementation : JsAny {
    fun createDocumentType(qualifiedName: String, publicId: String, systemId: String): DocumentType
    fun createDocument(namespace: String?, qualifiedName: String, doctype: DocumentType?): XMLDocument
    fun createHTMLDocument(title: String): Document
    fun hasFeature(): Boolean
}

/**
 * Exposes the JavaScript [DocumentType](https://developer.mozilla.org/en/docs/Web/API/DocumentType) to Kotlin
 */
public abstract external class DocumentType : Node, ChildNode, JsAny {
    open val name: String
    open val publicId: String
    open val systemId: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [DocumentFragment](https://developer.mozilla.org/en/docs/Web/API/DocumentFragment) to Kotlin
 */
public open external class DocumentFragment : Node, NonElementParentNode, ParentNode, JsAny {
    override val children: HTMLCollection
    override val firstElementChild: Element?
    override val lastElementChild: Element?
    override val childElementCount: Int
    override fun getElementById(elementId: String): Element?
    override fun prepend(vararg nodes: Node)
    override fun prepend(vararg nodes: String)
    override fun append(vararg nodes: Node)
    override fun append(vararg nodes: String)
    override fun querySelector(selectors: String): Element?
    override fun querySelectorAll(selectors: String): NodeList

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [ShadowRoot](https://developer.mozilla.org/en/docs/Web/API/ShadowRoot) to Kotlin
 */
public open external class ShadowRoot : DocumentFragment, DocumentOrShadowRoot, JsAny {
    open val mode: ShadowRootMode
    open val host: Element
    override val fullscreenElement: Element?

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [Element](https://developer.mozilla.org/en/docs/Web/API/Element) to Kotlin
 */
public abstract external class Element : Node, ParentNode, NonDocumentTypeChildNode, ChildNode, Slotable, GeometryUtils,
    UnionElementOrHTMLCollection, UnionElementOrRadioNodeList, UnionElementOrMouseEvent,
    UnionElementOrProcessingInstruction, JsAny {
    open val namespaceURI: String?
    open val prefix: String?
    open val localName: String
    open val tagName: String
    open var id: String
    open var className: String
    open val classList: DOMTokenList
    open var slot: String
    open val attributes: NamedNodeMap
    open val shadowRoot: ShadowRoot?
    open var scrollTop: Double
    open var scrollLeft: Double
    open val scrollWidth: Int
    open val scrollHeight: Int
    open val clientTop: Int
    open val clientLeft: Int
    open val clientWidth: Int
    open val clientHeight: Int
    open var innerHTML: String
    open var outerHTML: String
    fun hasAttributes(): Boolean
    fun getAttributeNames(): JsArray<JsString>
    fun getAttribute(qualifiedName: String): String?
    fun getAttributeNS(namespace: String?, localName: String): String?
    fun setAttribute(qualifiedName: String, value: String)
    fun setAttributeNS(namespace: String?, qualifiedName: String, value: String)
    fun removeAttribute(qualifiedName: String)
    fun removeAttributeNS(namespace: String?, localName: String)
    fun hasAttribute(qualifiedName: String): Boolean
    fun hasAttributeNS(namespace: String?, localName: String): Boolean
    fun getAttributeNode(qualifiedName: String): Attr?
    fun getAttributeNodeNS(namespace: String?, localName: String): Attr?
    fun setAttributeNode(attr: Attr): Attr?
    fun setAttributeNodeNS(attr: Attr): Attr?
    fun removeAttributeNode(attr: Attr): Attr
    fun attachShadow(init: ShadowRootInit): ShadowRoot
    fun closest(selectors: String): Element?
    fun matches(selectors: String): Boolean
    fun webkitMatchesSelector(selectors: String): Boolean
    fun getElementsByTagName(qualifiedName: String): HTMLCollection
    fun getElementsByTagNameNS(namespace: String?, localName: String): HTMLCollection
    fun getElementsByClassName(classNames: String): HTMLCollection
    fun insertAdjacentElement(where: String, element: Element): Element?
    fun insertAdjacentText(where: String, data: String)
    fun getClientRects(): JsArray<DOMRect>
    fun getBoundingClientRect(): DOMRect
    fun scrollIntoView()
    fun scrollIntoView(arg: Boolean)
    fun scrollIntoView(arg: JsAny)
    fun scroll(options: ScrollToOptions)
    fun scroll(x: Double, y: Double)
    fun scrollTo(options: ScrollToOptions)
    fun scrollTo(x: Double, y: Double)
    fun scrollBy(options: ScrollToOptions)
    fun scrollBy(x: Double, y: Double)
    fun insertAdjacentHTML(position: String, text: String)
    fun setPointerCapture(pointerId: Int)
    fun releasePointerCapture(pointerId: Int)
    fun hasPointerCapture(pointerId: Int): Boolean
    fun requestFullscreen(): Promise<Nothing?>

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public external interface ShadowRootInit : JsAny {
    var mode: ShadowRootMode?
}

/**
 * Exposes the JavaScript [NamedNodeMap](https://developer.mozilla.org/en/docs/Web/API/NamedNodeMap) to Kotlin
 */
public abstract external class NamedNodeMap : ItemArrayLike<Attr>, JsAny {
    fun getNamedItemNS(namespace: String?, localName: String): Attr?
    fun setNamedItem(attr: Attr): Attr?
    fun setNamedItemNS(attr: Attr): Attr?
    fun removeNamedItem(qualifiedName: String): Attr
    fun removeNamedItemNS(namespace: String?, localName: String): Attr
    override fun item(index: Int): Attr?
    fun getNamedItem(qualifiedName: String): Attr?
}

/**
 * Exposes the JavaScript [Attr](https://developer.mozilla.org/en/docs/Web/API/Attr) to Kotlin
 */
public abstract external class Attr : Node, JsAny {
    open val namespaceURI: String?
    open val prefix: String?
    open val localName: String
    open val name: String
    open var value: String
    open val ownerElement: Element?
    open val specified: Boolean

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [CharacterData](https://developer.mozilla.org/en/docs/Web/API/CharacterData) to Kotlin
 */
public abstract external class CharacterData : Node, NonDocumentTypeChildNode, ChildNode, JsAny {
    open var data: String
    open val length: Int
    fun substringData(offset: Int, count: Int): String
    fun appendData(data: String)
    fun insertData(offset: Int, data: String)
    fun deleteData(offset: Int, count: Int)
    fun replaceData(offset: Int, count: Int, data: String)

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [Text](https://developer.mozilla.org/en/docs/Web/API/Text) to Kotlin
 */
public open external class Text(data: String) : CharacterData, Slotable, GeometryUtils, JsAny {
    open val wholeText: String
    override val assignedSlot: HTMLSlotElement?
    override val previousElementSibling: Element?
    override val nextElementSibling: Element?
    fun splitText(offset: Int): Text
    override fun getBoxQuads(options: BoxQuadOptions /* */): JsArray<DOMQuad>
    override fun convertQuadFromNode(quad: JsAny?, from: Text, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertQuadFromNode(quad: JsAny?, from: Element, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertQuadFromNode(quad: JsAny?, from: Document, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Text,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Element,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Document,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Text,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Element,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Document,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun before(vararg nodes: Node)
    override fun before(vararg nodes: String)
    override fun after(vararg nodes: Node)
    override fun after(vararg nodes: String)
    override fun replaceWith(vararg nodes: Node)
    override fun replaceWith(vararg nodes: String)
    override fun remove()

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [CDATASection](https://developer.mozilla.org/en/docs/Web/API/CDATASection) to Kotlin
 */
public open external class CDATASection : Text, JsAny {
    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [ProcessingInstruction](https://developer.mozilla.org/en/docs/Web/API/ProcessingInstruction) to Kotlin
 */
public abstract external class ProcessingInstruction : CharacterData, LinkStyle, UnionElementOrProcessingInstruction,
    JsAny {
    open val target: String

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [Comment](https://developer.mozilla.org/en/docs/Web/API/Comment) to Kotlin
 */
public open external class Comment(data: String) : CharacterData, JsAny {
    override val previousElementSibling: Element?
    override val nextElementSibling: Element?
    override fun before(vararg nodes: Node)
    override fun before(vararg nodes: String)
    override fun after(vararg nodes: Node)
    override fun after(vararg nodes: String)
    override fun replaceWith(vararg nodes: Node)
    override fun replaceWith(vararg nodes: String)
    override fun remove()

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [Range](https://developer.mozilla.org/en/docs/Web/API/Range) to Kotlin
 */
public open external class Range : JsAny {
    open val startContainer: Node
    open val startOffset: Int
    open val endContainer: Node
    open val endOffset: Int
    open val collapsed: Boolean
    open val commonAncestorContainer: Node
    fun setStart(node: Node, offset: Int)
    fun setEnd(node: Node, offset: Int)
    fun setStartBefore(node: Node)
    fun setStartAfter(node: Node)
    fun setEndBefore(node: Node)
    fun setEndAfter(node: Node)
    fun collapse(toStart: Boolean)
    fun selectNode(node: Node)
    fun selectNodeContents(node: Node)
    fun compareBoundaryPoints(how: Short, sourceRange: Range): Short
    fun deleteContents()
    fun extractContents(): DocumentFragment
    fun cloneContents(): DocumentFragment
    fun insertNode(node: Node)
    fun surroundContents(newParent: Node)
    fun cloneRange(): Range
    fun detach()
    fun isPointInRange(node: Node, offset: Int): Boolean
    fun comparePoint(node: Node, offset: Int): Short
    fun intersectsNode(node: Node): Boolean
    fun getClientRects(): JsArray<DOMRect>
    fun getBoundingClientRect(): DOMRect
    fun createContextualFragment(fragment: String): DocumentFragment

    companion object {
        val START_TO_START: Short
        val START_TO_END: Short
        val END_TO_END: Short
        val END_TO_START: Short
    }
}

/**
 * Exposes the JavaScript [NodeIterator](https://developer.mozilla.org/en/docs/Web/API/NodeIterator) to Kotlin
 */
public abstract external class NodeIterator : JsAny {
    open val root: Node
    open val referenceNode: Node
    open val pointerBeforeReferenceNode: Boolean
    open val whatToShow: Int
    open val filter: NodeFilter?
    fun nextNode(): Node?
    fun previousNode(): Node?
    fun detach()
}

/**
 * Exposes the JavaScript [TreeWalker](https://developer.mozilla.org/en/docs/Web/API/TreeWalker) to Kotlin
 */
public abstract external class TreeWalker : JsAny {
    open val root: Node
    open val whatToShow: Int
    open val filter: NodeFilter?
    open var currentNode: Node
    fun parentNode(): Node?
    fun firstChild(): Node?
    fun lastChild(): Node?
    fun previousSibling(): Node?
    fun nextSibling(): Node?
    fun previousNode(): Node?
    fun nextNode(): Node?
}

/**
 * Exposes the JavaScript [NodeFilter](https://developer.mozilla.org/en/docs/Web/API/NodeFilter) to Kotlin
 */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface NodeFilter : JsAny {
    fun acceptNode(node: Node): Short

    companion object {
        val FILTER_ACCEPT: Short
        val FILTER_REJECT: Short
        val FILTER_SKIP: Short
        val SHOW_ALL: Int
        val SHOW_ELEMENT: Int
        val SHOW_ATTRIBUTE: Int
        val SHOW_TEXT: Int
        val SHOW_CDATA_SECTION: Int
        val SHOW_ENTITY_REFERENCE: Int
        val SHOW_ENTITY: Int
        val SHOW_PROCESSING_INSTRUCTION: Int
        val SHOW_COMMENT: Int
        val SHOW_DOCUMENT: Int
        val SHOW_DOCUMENT_TYPE: Int
        val SHOW_DOCUMENT_FRAGMENT: Int
        val SHOW_NOTATION: Int
    }
}

/**
 * Exposes the JavaScript [DOMTokenList](https://developer.mozilla.org/en/docs/Web/API/DOMTokenList) to Kotlin
 */
public abstract external class DOMTokenList : ItemArrayLike<JsString>, JsAny {
    open var value: String
    fun contains(token: String): Boolean
    fun add(vararg tokens: String)
    fun remove(vararg tokens: String)
    fun toggle(token: String, force: Boolean): Boolean
    fun replace(token: String, newToken: String)
    fun supports(token: String): Boolean
    override fun item(index: Int): JsString?
}

/**
 * Exposes the JavaScript [DOMPointReadOnly](https://developer.mozilla.org/en/docs/Web/API/DOMPointReadOnly) to Kotlin
 */
public open external class DOMPointReadOnly(x: Double, y: Double, z: Double, w: Double) : JsAny {
    open val x: Double
    open val y: Double
    open val z: Double
    open val w: Double
    fun matrixTransform(matrix: DOMMatrixReadOnly): DOMPoint
}

/**
 * Exposes the JavaScript [DOMPoint](https://developer.mozilla.org/en/docs/Web/API/DOMPoint) to Kotlin
 */
public open external class DOMPoint : DOMPointReadOnly, JsAny {
    constructor(point: DOMPointInit)
    constructor(x: Double, y: Double, z: Double, w: Double)

    override var x: Double
    override var y: Double
    override var z: Double
    override var w: Double
}

/**
 * Exposes the JavaScript [DOMPointInit](https://developer.mozilla.org/en/docs/Web/API/DOMPointInit) to Kotlin
 */
public external interface DOMPointInit : JsAny {
    var x: Double? /* = 0.0 */

    var y: Double? /* = 0.0 */

    var z: Double? /* = 0.0 */

    var w: Double? /* = 1.0 */

}

/**
 * Exposes the JavaScript [DOMRect](https://developer.mozilla.org/en/docs/Web/API/DOMRect) to Kotlin
 */
public open external class DOMRect(x: Double, y: Double, width: Double, height: Double) : DOMRectReadOnly, JsAny {
    override var x: Double
    override var y: Double
    override var width: Double
    override var height: Double
}

/**
 * Exposes the JavaScript [DOMRectReadOnly](https://developer.mozilla.org/en/docs/Web/API/DOMRectReadOnly) to Kotlin
 */
public open external class DOMRectReadOnly(x: Double, y: Double, width: Double, height: Double) : JsAny {
    open val x: Double
    open val y: Double
    open val width: Double
    open val height: Double
    open val top: Double
    open val right: Double
    open val bottom: Double
    open val left: Double
}

public external interface DOMRectInit : JsAny {
    var x: Double? /* = 0.0 */

    var y: Double? /* = 0.0 */

    var width: Double? /* = 0.0 */

    var height: Double? /* = 0.0 */

}

public external interface DOMRectList : ItemArrayLike<DOMRect>, JsAny {
    override fun item(index: Int): DOMRect?
}

/**
 * Exposes the JavaScript [DOMQuad](https://developer.mozilla.org/en/docs/Web/API/DOMQuad) to Kotlin
 */
public open external class DOMQuad : JsAny {
    constructor(p1: DOMPointInit, p2: DOMPointInit, p3: DOMPointInit, p4: DOMPointInit)
    constructor(rect: DOMRectInit)

    open val p1: DOMPoint
    open val p2: DOMPoint
    open val p3: DOMPoint
    open val p4: DOMPoint
    open val bounds: DOMRectReadOnly
}

/**
 * Exposes the JavaScript [DOMMatrixReadOnly](https://developer.mozilla.org/en/docs/Web/API/DOMMatrixReadOnly) to Kotlin
 */
public open external class DOMMatrixReadOnly(numberSequence: JsArray<JsNumber>) : JsAny {
    open val a: Double
    open val b: Double
    open val c: Double
    open val d: Double
    open val e: Double
    open val f: Double
    open val m11: Double
    open val m12: Double
    open val m13: Double
    open val m14: Double
    open val m21: Double
    open val m22: Double
    open val m23: Double
    open val m24: Double
    open val m31: Double
    open val m32: Double
    open val m33: Double
    open val m34: Double
    open val m41: Double
    open val m42: Double
    open val m43: Double
    open val m44: Double
    open val is2D: Boolean
    open val isIdentity: Boolean
    fun translate(tx: Double, ty: Double, tz: Double): DOMMatrix
    fun scale(scale: Double, originX: Double, originY: Double): DOMMatrix
    fun scale3d(scale: Double, originX: Double, originY: Double, originZ: Double): DOMMatrix
    fun scaleNonUniform(
        scaleX: Double,
        scaleY: Double,
        scaleZ: Double,
        originX: Double,
        originY: Double,
        originZ: Double
    ): DOMMatrix

    fun rotate(angle: Double, originX: Double, originY: Double): DOMMatrix
    fun rotateFromVector(x: Double, y: Double): DOMMatrix
    fun rotateAxisAngle(x: Double, y: Double, z: Double, angle: Double): DOMMatrix
    fun skewX(sx: Double): DOMMatrix
    fun skewY(sy: Double): DOMMatrix
    fun multiply(other: DOMMatrix): DOMMatrix
    fun flipX(): DOMMatrix
    fun flipY(): DOMMatrix
    fun inverse(): DOMMatrix
    fun transformPoint(point: DOMPointInit): DOMPoint
    fun toFloat32Array(): Float32Array
    fun toFloat64Array(): Float64Array
}

/**
 * Exposes the JavaScript [DOMMatrix](https://developer.mozilla.org/en/docs/Web/API/DOMMatrix) to Kotlin
 */
public open external class DOMMatrix() : DOMMatrixReadOnly, JsAny {
    constructor(transformList: String)
    constructor(other: DOMMatrixReadOnly)
    constructor(array32: Float32Array)
    constructor(array64: Float64Array)
    constructor(numberSequence: JsArray<JsNumber>)

    override var a: Double
    override var b: Double
    override var c: Double
    override var d: Double
    override var e: Double
    override var f: Double
    override var m11: Double
    override var m12: Double
    override var m13: Double
    override var m14: Double
    override var m21: Double
    override var m22: Double
    override var m23: Double
    override var m24: Double
    override var m31: Double
    override var m32: Double
    override var m33: Double
    override var m34: Double
    override var m41: Double
    override var m42: Double
    override var m43: Double
    override var m44: Double
    fun multiplySelf(other: DOMMatrix): DOMMatrix
    fun preMultiplySelf(other: DOMMatrix): DOMMatrix
    fun translateSelf(tx: Double, ty: Double, tz: Double): DOMMatrix
    fun scaleSelf(scale: Double, originX: Double, originY: Double): DOMMatrix
    fun scale3dSelf(scale: Double, originX: Double, originY: Double, originZ: Double): DOMMatrix
    fun scaleNonUniformSelf(
        scaleX: Double,
        scaleY: Double,
        scaleZ: Double,
        originX: Double,
        originY: Double,
        originZ: Double
    ): DOMMatrix

    fun rotateSelf(angle: Double, originX: Double, originY: Double): DOMMatrix
    fun rotateFromVectorSelf(x: Double, y: Double): DOMMatrix
    fun rotateAxisAngleSelf(x: Double, y: Double, z: Double, angle: Double): DOMMatrix
    fun skewXSelf(sx: Double): DOMMatrix
    fun skewYSelf(sy: Double): DOMMatrix
    fun invertSelf(): DOMMatrix
    fun setMatrixValue(transformList: String): DOMMatrix
}

public external interface ScrollOptions : JsAny {
    var behavior: ScrollBehavior? /* = ScrollBehavior.AUTO */

}

/**
 * Exposes the JavaScript [ScrollToOptions](https://developer.mozilla.org/en/docs/Web/API/ScrollToOptions) to Kotlin
 */
public external interface ScrollToOptions : ScrollOptions, JsAny {
    var left: Double?

    var top: Double?

}

/**
 * Exposes the JavaScript [MediaQueryList](https://developer.mozilla.org/en/docs/Web/API/MediaQueryList) to Kotlin
 */
public abstract external class MediaQueryList : EventTarget, JsAny {
    open val media: String
    open val matches: Boolean
    open var onchange: ((Event) -> Unit)?
    fun addListener(listener: EventListener?)
    fun addListener(listener: ((Event) -> Unit)?)
    fun removeListener(listener: EventListener?)
    fun removeListener(listener: ((Event) -> Unit)?)
}

/**
 * Exposes the JavaScript [MediaQueryListEvent](https://developer.mozilla.org/en/docs/Web/API/MediaQueryListEvent) to Kotlin
 */
public open external class MediaQueryListEvent(type: String, eventInitDict: MediaQueryListEventInit) : Event, JsAny {
    open val media: String
    open val matches: Boolean

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

public external interface MediaQueryListEventInit : EventInit, JsAny {
    var media: String? /* = "" */

    var matches: Boolean? /* = false */

}

/**
 * Exposes the JavaScript [Screen](https://developer.mozilla.org/en/docs/Web/API/Screen) to Kotlin
 */
public abstract external class Screen : JsAny {
    open val availWidth: Int
    open val availHeight: Int
    open val width: Int
    open val height: Int
    open val colorDepth: Int
    open val pixelDepth: Int
}

/**
 * Exposes the JavaScript [CaretPosition](https://developer.mozilla.org/en/docs/Web/API/CaretPosition) to Kotlin
 */
public abstract external class CaretPosition : JsAny {
    open val offsetNode: Node
    open val offset: Int
    fun getClientRect(): DOMRect?
}

public external interface ScrollIntoViewOptions : ScrollOptions, JsAny {
    var block: ScrollLogicalPosition? /* = ScrollLogicalPosition.CENTER */

    var inline: ScrollLogicalPosition? /* = ScrollLogicalPosition.CENTER */

}

public external interface BoxQuadOptions : JsAny {
    var box: CSSBoxType? /* = CSSBoxType.BORDER */

    var relativeTo: JsAny? /* Text|Element|CSSPseudoElement|Document */

}

public external interface ConvertCoordinateOptions : JsAny {
    var fromBox: CSSBoxType? /* = CSSBoxType.BORDER */

    var toBox: CSSBoxType? /* = CSSBoxType.BORDER */

}

/**
 * Exposes the JavaScript [GeometryUtils](https://developer.mozilla.org/en/docs/Web/API/GeometryUtils) to Kotlin
 */
public external interface GeometryUtils : JsAny {
    fun getBoxQuads(options: BoxQuadOptions): JsArray<DOMQuad>
    fun convertQuadFromNode(quad: JsAny?, from: Text, options: ConvertCoordinateOptions): DOMQuad
    fun convertQuadFromNode(quad: JsAny?, from: Element, options: ConvertCoordinateOptions): DOMQuad
    fun convertQuadFromNode(quad: JsAny?, from: Document, options: ConvertCoordinateOptions): DOMQuad
    fun convertRectFromNode(rect: DOMRectReadOnly, from: Text, options: ConvertCoordinateOptions): DOMQuad
    fun convertRectFromNode(rect: DOMRectReadOnly, from: Element, options: ConvertCoordinateOptions): DOMQuad
    fun convertRectFromNode(rect: DOMRectReadOnly, from: Document, options: ConvertCoordinateOptions): DOMQuad
    fun convertPointFromNode(point: DOMPointInit, from: Text, options: ConvertCoordinateOptions): DOMPoint
    fun convertPointFromNode(point: DOMPointInit, from: Element, options: ConvertCoordinateOptions): DOMPoint
    fun convertPointFromNode(point: DOMPointInit, from: Document, options: ConvertCoordinateOptions): DOMPoint
}

/**
 * Exposes the JavaScript [Touch](https://developer.mozilla.org/en/docs/Web/API/Touch) to Kotlin
 */
public abstract external class Touch : JsAny {
    open val identifier: Int
    open val target: EventTarget
    open val screenX: Int
    open val screenY: Int
    open val clientX: Int
    open val clientY: Int
    open val pageX: Int
    open val pageY: Int
    open val region: String?
}

public abstract external class TouchList : ItemArrayLike<Touch>, JsAny {
    override fun item(index: Int): Touch?
}

public open external class TouchEvent : UIEvent, JsAny {
    open val touches: TouchList
    open val targetTouches: TouchList
    open val changedTouches: TouchList
    open val altKey: Boolean
    open val metaKey: Boolean
    open val ctrlKey: Boolean
    open val shiftKey: Boolean

    companion object {
        val NONE: Short
        val CAPTURING_PHASE: Short
        val AT_TARGET: Short
        val BUBBLING_PHASE: Short
    }
}

/**
 * Exposes the JavaScript [Image](https://developer.mozilla.org/en/docs/Web/API/Image) to Kotlin
 */
public open external class Image(width: Int, height: Int) : HTMLImageElement, JsAny {
    override var onabort: ((Event) -> Unit)?
    override var onblur: ((FocusEvent) -> Unit)?
    override var oncancel: ((Event) -> Unit)?
    override var oncanplay: ((Event) -> Unit)?
    override var oncanplaythrough: ((Event) -> Unit)?
    override var onchange: ((Event) -> Unit)?
    override var onclick: ((MouseEvent) -> Unit)?
    override var onclose: ((Event) -> Unit)?
    override var oncontextmenu: ((MouseEvent) -> Unit)?
    override var oncuechange: ((Event) -> Unit)?
    override var ondblclick: ((MouseEvent) -> Unit)?
    override var ondrag: ((DragEvent) -> Unit)?
    override var ondragend: ((DragEvent) -> Unit)?
    override var ondragenter: ((DragEvent) -> Unit)?
    override var ondragexit: ((DragEvent) -> Unit)?
    override var ondragleave: ((DragEvent) -> Unit)?
    override var ondragover: ((DragEvent) -> Unit)?
    override var ondragstart: ((DragEvent) -> Unit)?
    override var ondrop: ((DragEvent) -> Unit)?
    override var ondurationchange: ((Event) -> Unit)?
    override var onemptied: ((Event) -> Unit)?
    override var onended: ((Event) -> Unit)?
    override var onerror: ((JsAny? /* Event|String */, String, Int, Int, JsAny?) -> JsAny?)?
    override var onfocus: ((FocusEvent) -> Unit)?
    override var oninput: ((InputEvent) -> Unit)?
    override var oninvalid: ((Event) -> Unit)?
    override var onkeydown: ((KeyboardEvent) -> Unit)?
    override var onkeypress: ((KeyboardEvent) -> Unit)?
    override var onkeyup: ((KeyboardEvent) -> Unit)?
    override var onload: ((Event) -> Unit)?
    override var onloadeddata: ((Event) -> Unit)?
    override var onloadedmetadata: ((Event) -> Unit)?
    override var onloadend: ((Event) -> Unit)?
    override var onloadstart: ((ProgressEvent) -> Unit)?
    override var onmousedown: ((MouseEvent) -> Unit)?
    override var onmouseenter: ((MouseEvent) -> Unit)?
    override var onmouseleave: ((MouseEvent) -> Unit)?
    override var onmousemove: ((MouseEvent) -> Unit)?
    override var onmouseout: ((MouseEvent) -> Unit)?
    override var onmouseover: ((MouseEvent) -> Unit)?
    override var onmouseup: ((MouseEvent) -> Unit)?
    override var onwheel: ((WheelEvent) -> Unit)?
    override var onpause: ((Event) -> Unit)?
    override var onplay: ((Event) -> Unit)?
    override var onplaying: ((Event) -> Unit)?
    override var onprogress: ((ProgressEvent) -> Unit)?
    override var onratechange: ((Event) -> Unit)?
    override var onreset: ((Event) -> Unit)?
    override var onresize: ((Event) -> Unit)?
    override var onscroll: ((Event) -> Unit)?
    override var onseeked: ((Event) -> Unit)?
    override var onseeking: ((Event) -> Unit)?
    override var onselect: ((Event) -> Unit)?
    override var onshow: ((Event) -> Unit)?
    override var onstalled: ((Event) -> Unit)?
    override var onsubmit: ((Event) -> Unit)?
    override var onsuspend: ((Event) -> Unit)?
    override var ontimeupdate: ((Event) -> Unit)?
    override var ontoggle: ((Event) -> Unit)?
    override var onvolumechange: ((Event) -> Unit)?
    override var onwaiting: ((Event) -> Unit)?
    override var ongotpointercapture: ((PointerEvent) -> Unit)?
    override var onlostpointercapture: ((PointerEvent) -> Unit)?
    override var onpointerdown: ((PointerEvent) -> Unit)?
    override var onpointermove: ((PointerEvent) -> Unit)?
    override var onpointerup: ((PointerEvent) -> Unit)?
    override var onpointercancel: ((PointerEvent) -> Unit)?
    override var onpointerover: ((PointerEvent) -> Unit)?
    override var onpointerout: ((PointerEvent) -> Unit)?
    override var onpointerenter: ((PointerEvent) -> Unit)?
    override var onpointerleave: ((PointerEvent) -> Unit)?
    override var oncopy: ((ClipboardEvent) -> Unit)?
    override var oncut: ((ClipboardEvent) -> Unit)?
    override var onpaste: ((ClipboardEvent) -> Unit)?
    override var contentEditable: String
    override val isContentEditable: Boolean
    override val style: CSSStyleDeclaration
    override val children: HTMLCollection
    override val firstElementChild: Element?
    override val lastElementChild: Element?
    override val childElementCount: Int
    override val previousElementSibling: Element?
    override val nextElementSibling: Element?
    override val assignedSlot: HTMLSlotElement?
    override fun prepend(vararg nodes: Node)
    override fun prepend(vararg nodes: String)
    override fun append(vararg nodes: Node)
    override fun append(vararg nodes: String)
    override fun querySelector(selectors: String): Element?
    override fun querySelectorAll(selectors: String): NodeList
    override fun before(vararg nodes: Node)
    override fun before(vararg nodes: String)
    override fun after(vararg nodes: Node)
    override fun after(vararg nodes: String)
    override fun replaceWith(vararg nodes: Node)
    override fun replaceWith(vararg nodes: String)
    override fun remove()
    override fun getBoxQuads(options: BoxQuadOptions /* */): JsArray<DOMQuad>
    override fun convertQuadFromNode(quad: JsAny?, from: Text, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertQuadFromNode(quad: JsAny?, from: Element, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertQuadFromNode(quad: JsAny?, from: Document, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Text,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Element,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Document,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Text,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Element,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Document,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public open external class Audio(src: String) : HTMLAudioElement, JsAny {
    override var onabort: ((Event) -> Unit)?
    override var onblur: ((FocusEvent) -> Unit)?
    override var oncancel: ((Event) -> Unit)?
    override var oncanplay: ((Event) -> Unit)?
    override var oncanplaythrough: ((Event) -> Unit)?
    override var onchange: ((Event) -> Unit)?
    override var onclick: ((MouseEvent) -> Unit)?
    override var onclose: ((Event) -> Unit)?
    override var oncontextmenu: ((MouseEvent) -> Unit)?
    override var oncuechange: ((Event) -> Unit)?
    override var ondblclick: ((MouseEvent) -> Unit)?
    override var ondrag: ((DragEvent) -> Unit)?
    override var ondragend: ((DragEvent) -> Unit)?
    override var ondragenter: ((DragEvent) -> Unit)?
    override var ondragexit: ((DragEvent) -> Unit)?
    override var ondragleave: ((DragEvent) -> Unit)?
    override var ondragover: ((DragEvent) -> Unit)?
    override var ondragstart: ((DragEvent) -> Unit)?
    override var ondrop: ((DragEvent) -> Unit)?
    override var ondurationchange: ((Event) -> Unit)?
    override var onemptied: ((Event) -> Unit)?
    override var onended: ((Event) -> Unit)?
    override var onerror: ((JsAny? /* Event|String */, String, Int, Int, JsAny?) -> JsAny?)?
    override var onfocus: ((FocusEvent) -> Unit)?
    override var oninput: ((InputEvent) -> Unit)?
    override var oninvalid: ((Event) -> Unit)?
    override var onkeydown: ((KeyboardEvent) -> Unit)?
    override var onkeypress: ((KeyboardEvent) -> Unit)?
    override var onkeyup: ((KeyboardEvent) -> Unit)?
    override var onload: ((Event) -> Unit)?
    override var onloadeddata: ((Event) -> Unit)?
    override var onloadedmetadata: ((Event) -> Unit)?
    override var onloadend: ((Event) -> Unit)?
    override var onloadstart: ((ProgressEvent) -> Unit)?
    override var onmousedown: ((MouseEvent) -> Unit)?
    override var onmouseenter: ((MouseEvent) -> Unit)?
    override var onmouseleave: ((MouseEvent) -> Unit)?
    override var onmousemove: ((MouseEvent) -> Unit)?
    override var onmouseout: ((MouseEvent) -> Unit)?
    override var onmouseover: ((MouseEvent) -> Unit)?
    override var onmouseup: ((MouseEvent) -> Unit)?
    override var onwheel: ((WheelEvent) -> Unit)?
    override var onpause: ((Event) -> Unit)?
    override var onplay: ((Event) -> Unit)?
    override var onplaying: ((Event) -> Unit)?
    override var onprogress: ((ProgressEvent) -> Unit)?
    override var onratechange: ((Event) -> Unit)?
    override var onreset: ((Event) -> Unit)?
    override var onresize: ((Event) -> Unit)?
    override var onscroll: ((Event) -> Unit)?
    override var onseeked: ((Event) -> Unit)?
    override var onseeking: ((Event) -> Unit)?
    override var onselect: ((Event) -> Unit)?
    override var onshow: ((Event) -> Unit)?
    override var onstalled: ((Event) -> Unit)?
    override var onsubmit: ((Event) -> Unit)?
    override var onsuspend: ((Event) -> Unit)?
    override var ontimeupdate: ((Event) -> Unit)?
    override var ontoggle: ((Event) -> Unit)?
    override var onvolumechange: ((Event) -> Unit)?
    override var onwaiting: ((Event) -> Unit)?
    override var ongotpointercapture: ((PointerEvent) -> Unit)?
    override var onlostpointercapture: ((PointerEvent) -> Unit)?
    override var onpointerdown: ((PointerEvent) -> Unit)?
    override var onpointermove: ((PointerEvent) -> Unit)?
    override var onpointerup: ((PointerEvent) -> Unit)?
    override var onpointercancel: ((PointerEvent) -> Unit)?
    override var onpointerover: ((PointerEvent) -> Unit)?
    override var onpointerout: ((PointerEvent) -> Unit)?
    override var onpointerenter: ((PointerEvent) -> Unit)?
    override var onpointerleave: ((PointerEvent) -> Unit)?
    override var oncopy: ((ClipboardEvent) -> Unit)?
    override var oncut: ((ClipboardEvent) -> Unit)?
    override var onpaste: ((ClipboardEvent) -> Unit)?
    override var contentEditable: String
    override val isContentEditable: Boolean
    override val style: CSSStyleDeclaration
    override val children: HTMLCollection
    override val firstElementChild: Element?
    override val lastElementChild: Element?
    override val childElementCount: Int
    override val previousElementSibling: Element?
    override val nextElementSibling: Element?
    override val assignedSlot: HTMLSlotElement?
    override fun prepend(vararg nodes: Node)
    override fun prepend(vararg nodes: String)
    override fun append(vararg nodes: Node)
    override fun append(vararg nodes: String)
    override fun querySelector(selectors: String): Element?
    override fun querySelectorAll(selectors: String): NodeList
    override fun before(vararg nodes: Node)
    override fun before(vararg nodes: String)
    override fun after(vararg nodes: Node)
    override fun after(vararg nodes: String)
    override fun replaceWith(vararg nodes: Node)
    override fun replaceWith(vararg nodes: String)
    override fun remove()
    override fun getBoxQuads(options: BoxQuadOptions /* */): JsArray<DOMQuad>
    override fun convertQuadFromNode(quad: JsAny?, from: Text, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertQuadFromNode(quad: JsAny?, from: Element, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertQuadFromNode(quad: JsAny?, from: Document, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Text,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Element,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Document,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Text,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Element,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Document,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    companion object {
        val NETWORK_EMPTY: Short
        val NETWORK_IDLE: Short
        val NETWORK_LOADING: Short
        val NETWORK_NO_SOURCE: Short
        val HAVE_NOTHING: Short
        val HAVE_METADATA: Short
        val HAVE_CURRENT_DATA: Short
        val HAVE_FUTURE_DATA: Short
        val HAVE_ENOUGH_DATA: Short
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

/**
 * Exposes the JavaScript [Option](https://developer.mozilla.org/en/docs/Web/API/Option) to Kotlin
 */
public open external class Option(text: String, value: String, defaultSelected: Boolean, selected: Boolean) :
    HTMLOptionElement, JsAny {
    override var onabort: ((Event) -> Unit)?
    override var onblur: ((FocusEvent) -> Unit)?
    override var oncancel: ((Event) -> Unit)?
    override var oncanplay: ((Event) -> Unit)?
    override var oncanplaythrough: ((Event) -> Unit)?
    override var onchange: ((Event) -> Unit)?
    override var onclick: ((MouseEvent) -> Unit)?
    override var onclose: ((Event) -> Unit)?
    override var oncontextmenu: ((MouseEvent) -> Unit)?
    override var oncuechange: ((Event) -> Unit)?
    override var ondblclick: ((MouseEvent) -> Unit)?
    override var ondrag: ((DragEvent) -> Unit)?
    override var ondragend: ((DragEvent) -> Unit)?
    override var ondragenter: ((DragEvent) -> Unit)?
    override var ondragexit: ((DragEvent) -> Unit)?
    override var ondragleave: ((DragEvent) -> Unit)?
    override var ondragover: ((DragEvent) -> Unit)?
    override var ondragstart: ((DragEvent) -> Unit)?
    override var ondrop: ((DragEvent) -> Unit)?
    override var ondurationchange: ((Event) -> Unit)?
    override var onemptied: ((Event) -> Unit)?
    override var onended: ((Event) -> Unit)?
    override var onerror: ((JsAny? /* Event|String */, String, Int, Int, JsAny?) -> JsAny?)?
    override var onfocus: ((FocusEvent) -> Unit)?
    override var oninput: ((InputEvent) -> Unit)?
    override var oninvalid: ((Event) -> Unit)?
    override var onkeydown: ((KeyboardEvent) -> Unit)?
    override var onkeypress: ((KeyboardEvent) -> Unit)?
    override var onkeyup: ((KeyboardEvent) -> Unit)?
    override var onload: ((Event) -> Unit)?
    override var onloadeddata: ((Event) -> Unit)?
    override var onloadedmetadata: ((Event) -> Unit)?
    override var onloadend: ((Event) -> Unit)?
    override var onloadstart: ((ProgressEvent) -> Unit)?
    override var onmousedown: ((MouseEvent) -> Unit)?
    override var onmouseenter: ((MouseEvent) -> Unit)?
    override var onmouseleave: ((MouseEvent) -> Unit)?
    override var onmousemove: ((MouseEvent) -> Unit)?
    override var onmouseout: ((MouseEvent) -> Unit)?
    override var onmouseover: ((MouseEvent) -> Unit)?
    override var onmouseup: ((MouseEvent) -> Unit)?
    override var onwheel: ((WheelEvent) -> Unit)?
    override var onpause: ((Event) -> Unit)?
    override var onplay: ((Event) -> Unit)?
    override var onplaying: ((Event) -> Unit)?
    override var onprogress: ((ProgressEvent) -> Unit)?
    override var onratechange: ((Event) -> Unit)?
    override var onreset: ((Event) -> Unit)?
    override var onresize: ((Event) -> Unit)?
    override var onscroll: ((Event) -> Unit)?
    override var onseeked: ((Event) -> Unit)?
    override var onseeking: ((Event) -> Unit)?
    override var onselect: ((Event) -> Unit)?
    override var onshow: ((Event) -> Unit)?
    override var onstalled: ((Event) -> Unit)?
    override var onsubmit: ((Event) -> Unit)?
    override var onsuspend: ((Event) -> Unit)?
    override var ontimeupdate: ((Event) -> Unit)?
    override var ontoggle: ((Event) -> Unit)?
    override var onvolumechange: ((Event) -> Unit)?
    override var onwaiting: ((Event) -> Unit)?
    override var ongotpointercapture: ((PointerEvent) -> Unit)?
    override var onlostpointercapture: ((PointerEvent) -> Unit)?
    override var onpointerdown: ((PointerEvent) -> Unit)?
    override var onpointermove: ((PointerEvent) -> Unit)?
    override var onpointerup: ((PointerEvent) -> Unit)?
    override var onpointercancel: ((PointerEvent) -> Unit)?
    override var onpointerover: ((PointerEvent) -> Unit)?
    override var onpointerout: ((PointerEvent) -> Unit)?
    override var onpointerenter: ((PointerEvent) -> Unit)?
    override var onpointerleave: ((PointerEvent) -> Unit)?
    override var oncopy: ((ClipboardEvent) -> Unit)?
    override var oncut: ((ClipboardEvent) -> Unit)?
    override var onpaste: ((ClipboardEvent) -> Unit)?
    override var contentEditable: String
    override val isContentEditable: Boolean
    override val style: CSSStyleDeclaration
    override val children: HTMLCollection
    override val firstElementChild: Element?
    override val lastElementChild: Element?
    override val childElementCount: Int
    override val previousElementSibling: Element?
    override val nextElementSibling: Element?
    override val assignedSlot: HTMLSlotElement?
    override fun prepend(vararg nodes: Node)
    override fun prepend(vararg nodes: String)
    override fun append(vararg nodes: Node)
    override fun append(vararg nodes: String)
    override fun querySelector(selectors: String): Element?
    override fun querySelectorAll(selectors: String): NodeList
    override fun before(vararg nodes: Node)
    override fun before(vararg nodes: String)
    override fun after(vararg nodes: Node)
    override fun after(vararg nodes: String)
    override fun replaceWith(vararg nodes: Node)
    override fun replaceWith(vararg nodes: String)
    override fun remove()
    override fun getBoxQuads(options: BoxQuadOptions /* */): JsArray<DOMQuad>
    override fun convertQuadFromNode(quad: JsAny?, from: Text, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertQuadFromNode(quad: JsAny?, from: Element, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertQuadFromNode(quad: JsAny?, from: Document, options: ConvertCoordinateOptions /* */): DOMQuad
    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Text,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Element,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertRectFromNode(
        rect: DOMRectReadOnly,
        from: Document,
        options: ConvertCoordinateOptions /* */
    ): DOMQuad

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Text,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Element,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    override fun convertPointFromNode(
        point: DOMPointInit,
        from: Document,
        options: ConvertCoordinateOptions /* */
    ): DOMPoint

    companion object {
        val ELEMENT_NODE: Short
        val ATTRIBUTE_NODE: Short
        val TEXT_NODE: Short
        val CDATA_SECTION_NODE: Short
        val ENTITY_REFERENCE_NODE: Short
        val ENTITY_NODE: Short
        val PROCESSING_INSTRUCTION_NODE: Short
        val COMMENT_NODE: Short
        val DOCUMENT_NODE: Short
        val DOCUMENT_TYPE_NODE: Short
        val DOCUMENT_FRAGMENT_NODE: Short
        val NOTATION_NODE: Short
        val DOCUMENT_POSITION_DISCONNECTED: Short
        val DOCUMENT_POSITION_PRECEDING: Short
        val DOCUMENT_POSITION_FOLLOWING: Short
        val DOCUMENT_POSITION_CONTAINS: Short
        val DOCUMENT_POSITION_CONTAINED_BY: Short
        val DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC: Short
    }
}

public external interface UnionElementOrHTMLCollection

public external interface UnionElementOrRadioNodeList

public external interface UnionHTMLOptGroupElementOrHTMLOptionElement

public external interface UnionAudioTrackOrTextTrackOrVideoTrack

public external interface UnionElementOrMouseEvent

public external interface UnionMessagePortOrWindowProxy

public external interface MediaProvider

public external interface RenderingContext

public external interface HTMLOrSVGImageElement : CanvasImageSource

public external interface CanvasImageSource : ImageBitmapSource

public external interface ImageBitmapSource

public external interface HTMLOrSVGScriptElement

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface DocumentReadyState : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface CanPlayTypeResult : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface TextTrackMode : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface TextTrackKind : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface SelectionMode : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface CanvasFillRule : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ImageSmoothingQuality : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface CanvasLineCap : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface CanvasLineJoin : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface CanvasTextAlign : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface CanvasTextBaseline : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface CanvasDirection : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ScrollRestoration : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ImageOrientation : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface PremultiplyAlpha : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ColorSpaceConversion : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ResizeQuality : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface BinaryType : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface WorkerType : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ShadowRootMode : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ScrollBehavior : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface ScrollLogicalPosition : JsAny {
    companion object
}

/* please, don't implement this interface! */
@Suppress("NESTED_CLASS_IN_EXTERNAL_INTERFACE")
public external interface CSSBoxType : JsAny {
    companion object
}
