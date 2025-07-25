/*
 * Copyright (c) 2025 Robert Jaros
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

import js.core.JsPrimitives.toDouble
import js.core.JsPrimitives.toJsDouble
import js.date.Date
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atDate
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

/**
 * Converts [LocalDateTime] to JavaScript [Date].
 */
public fun LocalDateTime.toDate(): Date {
    return Date(
        this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds().toDouble()
    )
}

/**
 * Converts [LocalDate] to JavaScript [Date].
 */
public fun LocalDate.toDate(): Date {
    return Date(
        this.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds().toDouble()
    )
}

/**
 * Converts [LocalTime] to JavaScript [Date].
 */
public fun LocalTime.toDate(): Date {
    return this.atDate(today()).toDate()
}

/**
 * Converts JavaScript [Date] to [LocalDateTime].
 */
public fun Date.toLocalDateTime(): LocalDateTime {
    return Instant.fromEpochMilliseconds(getTime().toJsDouble().toDouble().toLong())
        .toLocalDateTime(TimeZone.currentSystemDefault())
}

/**
 * Converts JavaScript [Date] to [LocalDate].
 */
public fun Date.toLocalDate(): LocalDate {
    return toLocalDateTime().date
}

/**
 * Converts JavaScript [Date] to [LocalTime].
 */
public fun Date.toLocalTime(): LocalTime {
    return toLocalDateTime().time
}
