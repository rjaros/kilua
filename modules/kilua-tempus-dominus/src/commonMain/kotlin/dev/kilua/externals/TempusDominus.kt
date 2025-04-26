@file:Suppress("EXTERNAL_TYPE_EXTENDS_NON_EXTERNAL_TYPE")
@file:JsModule("@eonasdan/tempus-dominus")
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

package dev.kilua.externals

import js.import.JsModule
import js.core.JsAny
import js.core.JsInt
import js.date.Date
import web.html.HTMLElement

/**
 * Tempus Dominus restrictions configuration.
 */
public external class TempusDominusRestrictions : JsAny {
    public var minDate: Date
    public var maxDate: Date
    public var disabledDates: JsArray<Date>
    public var enabledDates: JsArray<Date>
    public var daysOfWeekDisabled: JsArray<JsInt>
    public var disabledHours: JsArray<JsInt>
    public var enabledHours: JsArray<JsInt>
}

/**
 * Tempus Dominus button configuration.
 */
public external class TempusDominusButtons : JsAny {
    public var today: Boolean
    public var clear: Boolean
    public var close: Boolean
}

/**
 * Tempus Dominus icons configuration.
 */
public external class TempusDominusIcons : JsAny {
    public var type: String
    public var time: String
    public var date: String
    public var up: String
    public var down: String
    public var previous: String
    public var next: String
    public var today: String
    public var clear: String
    public var close: String
}

/**
 * Tempus Dominus components configuration.
 */
public external class TempusDominusComponents : JsAny {
    public var calendar: Boolean
    public var date: Boolean
    public var month: Boolean
    public var year: Boolean
    public var decades: Boolean
    public var clock: Boolean
    public var hours: Boolean
    public var minutes: Boolean
    public var seconds: Boolean
}

/**
 * Tempus Dominus display configuration.
 */
public external class TempusDominusDisplay : JsAny {
    public var icons: TempusDominusIcons
    public var sideBySide: Boolean
    public var calendarWeeks: Boolean
    public var viewMode: String
    public var toolbarPlacement: String
    public var keepOpen: Boolean
    public var buttons: TempusDominusButtons
    public var components: TempusDominusComponents
    public var inline: Boolean
    public var theme: String
    public var keyboardNavigation: Boolean
}

/**
 * Tempus Dominus options.
 */
public external class TempusDominusOptions : JsAny {
    public var allowInputToggle: Boolean
    public var container: JsAny
    public var defaultDate: Date
    public var display: TempusDominusDisplay
    public var keepInvalid: Boolean
    public var localization: JsAny
    public var meta: JsAny
    public var promptTimeOnDateChange: Boolean
    public var promptTimeOnDateChangeTransitionDelay: Int
    public var restrictions: TempusDominusRestrictions
    public var stepping: Int
    public var useCurrent: Boolean
    public var viewDate: Date
}

/**
 * Tempus Dominus dates object.
 */
public external class TempusDominusDates : JsAny {
    public var lastPicked: Date?
    public fun setValue(date: Date)
    public fun parseInput(date: Date): Date
    public fun formatInput(date: Date): String
}

/**
 * Tempus Dominus class.
 */
public external class TempusDominus(element: HTMLElement, options: TempusDominusOptions?) : JsAny {

    public var dates: TempusDominusDates

    public fun toggle()
    public fun show()
    public fun hide()

    public fun disable()
    public fun enable()
    public fun clear()
    public fun dispose()
}
