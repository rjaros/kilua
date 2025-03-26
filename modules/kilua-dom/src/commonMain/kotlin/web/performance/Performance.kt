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

package web.performance

import js.core.JsAny
import web.JsNumber
import web.events.EventTarget

/**
 * Exposes the JavaScript [Performance](https://developer.mozilla.org/en/docs/Web/API/Performance) to Kotlin
 */
public abstract external class Performance : EventTarget, JsAny {
    open val timing: PerformanceTiming
    open val navigation: PerformanceNavigation
    fun now(): Double
}

public external interface GlobalPerformance : JsAny {
    val performance: Performance
}

/**
 * Exposes the JavaScript [PerformanceTiming](https://developer.mozilla.org/en/docs/Web/API/PerformanceTiming) to Kotlin
 */
public abstract external class PerformanceTiming : JsAny {
    open val navigationStart: JsNumber
    open val unloadEventStart: JsNumber
    open val unloadEventEnd: JsNumber
    open val redirectStart: JsNumber
    open val redirectEnd: JsNumber
    open val fetchStart: JsNumber
    open val domainLookupStart: JsNumber
    open val domainLookupEnd: JsNumber
    open val connectStart: JsNumber
    open val connectEnd: JsNumber
    open val secureConnectionStart: JsNumber
    open val requestStart: JsNumber
    open val responseStart: JsNumber
    open val responseEnd: JsNumber
    open val domLoading: JsNumber
    open val domInteractive: JsNumber
    open val domContentLoadedEventStart: JsNumber
    open val domContentLoadedEventEnd: JsNumber
    open val domComplete: JsNumber
    open val loadEventStart: JsNumber
    open val loadEventEnd: JsNumber
}

/**
 * Exposes the JavaScript [PerformanceNavigation](https://developer.mozilla.org/en/docs/Web/API/PerformanceNavigation) to Kotlin
 */
public abstract external class PerformanceNavigation : JsAny {
    open val type: Short
    open val redirectCount: Short

    companion object {
        val TYPE_NAVIGATE: Short
        val TYPE_RELOAD: Short
        val TYPE_BACK_FORWARD: Short
        val TYPE_RESERVED: Short
    }
}