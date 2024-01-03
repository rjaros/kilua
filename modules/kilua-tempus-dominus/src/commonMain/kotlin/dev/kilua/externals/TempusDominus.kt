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

import dev.kilua.utils.JsModule
import web.JsAny
import web.JsArray
import web.JsNumber
import web.dom.HTMLElement

/**
 * Tempus Dominus restrictions configuration.
 */
public external class TempusDominusRestrictions : JsAny {
    public var minDate: Date
    public var maxDate: Date
    public var disabledDates: JsArray<Date>
    public var enabledDates: JsArray<Date>
    public var daysOfWeekDisabled: JsArray<JsNumber>
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
 * Tempus Dominus components configuration.
 */
public external class TempusDominusComponents : JsAny {
    public var calendar: Boolean
    public var clock: Boolean
    public var seconds: Boolean
}

/**
 * Tempus Dominus display configuration.
 */
public external class TempusDominusDisplay : JsAny {
    public var viewMode: String
    public var toolbarPlacement: String
    public var sideBySide: Boolean
    public var buttons: TempusDominusButtons
    public var inline: Boolean
    public var keepOpen: Boolean
    public var theme: String
    public var components: TempusDominusComponents
}

/**
 * Tempus Dominus options.
 */
public external class TempusDominusOptions : JsAny {
    public var useCurrent: Boolean
    public var defaultDate: Date
    public var stepping: Int
    public var allowInputToggle: Boolean
    public var viewDate: Date
    public var promptTimeOnDateChange: Boolean
    public var promptTimeOnDateChangeTransitionDelay: Int
    public var restrictions: TempusDominusRestrictions
    public var display: TempusDominusDisplay
    public var localization: JsAny
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

    /** These methods don't work
    public fun toggle()
    public fun show()
    public fun hide()
     **/
    public fun disable()
    public fun enable()
    public fun clear()
    public fun dispose()
}
