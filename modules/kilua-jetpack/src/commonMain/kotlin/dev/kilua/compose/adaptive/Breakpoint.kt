/*
 * Copyright (c) 2025 Ghasem Shirdel
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
package dev.kilua.compose.adaptive

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

// Inspired by the SpotifyClone API
// See also: https://github.com/shubhamsinghshubham777/SpotifyClone/blob/main/src/commonMain/kotlin/util.kt

/**
 * Enum representing different screen size breakpoints.
 */
public enum class Breakpoint {
    /** Represents a mobile device breakpoint. */
    Mobile,

    /** Represents a small tablet device breakpoint. */
    SmallTablet,

    /** Represents a tablet device breakpoint. */
    Tablet,

    /** Represents a laptop device breakpoint. */
    Laptop,

    /** Represents a desktop device breakpoint. */
    Desktop;

    /**
     * Determines if the current breakpoint is greater than the given [breakpoint].
     *
     * @param breakpoint The breakpoint to compare against.
     * @return `true` if the current breakpoint is greater, `false` otherwise.
     */
    public infix fun isGreaterThan(breakpoint: Breakpoint): Boolean = this.ordinal > breakpoint.ordinal

    /**
     * Determines if the current breakpoint is smaller than the given [breakpoint].
     *
     * @param breakpoint The breakpoint to compare against.
     * @return `true` if the current breakpoint is smaller, `false` otherwise.
     */
    public infix fun isSmallerThan(breakpoint: Breakpoint): Boolean = this.ordinal < breakpoint.ordinal

    /**
     * Checks if the current breakpoint is between two given breakpoints (inclusive).
     *
     * @param smallBreakpoint The lower bound of the range.
     * @param largeBreakpoint The upper bound of the range.
     * @return `true` if the current breakpoint is within the range, `false` otherwise.
     */
    public fun isBetween(smallBreakpoint: Breakpoint, largeBreakpoint: Breakpoint): Boolean {
        return (smallBreakpoint.ordinal..largeBreakpoint.ordinal).contains(this.ordinal)
    }
}

/**
 * Returns a [State] object representing the current breakpoint of the screen.
 * It uses media queries to determine the current screen size.
 *
 * @return A [State] holding the current [Breakpoint].
 */
@Composable
public fun rememberBreakpoint(): State<Breakpoint> {
    val isMobile by rememberMediaQueryAsState("(max-width: 480px)")
    val isSmallTablet by rememberMediaQueryAsState("(min-width: 481px) and (max-width: 768px)")
    val isTablet by rememberMediaQueryAsState("(min-width: 769px) and (max-width: 991px)")
    val isLaptop by rememberMediaQueryAsState("(min-width: 992px) and (max-width: 1199px)")

    return remember {
        derivedStateOf {
            when {
                isMobile -> Breakpoint.Mobile
                isSmallTablet -> Breakpoint.SmallTablet
                isTablet -> Breakpoint.Tablet
                isLaptop -> Breakpoint.Laptop
                else -> Breakpoint.Desktop
            }
        }
    }
}
