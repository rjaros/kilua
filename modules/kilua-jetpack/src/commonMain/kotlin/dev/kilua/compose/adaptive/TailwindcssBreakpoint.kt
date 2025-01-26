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
import dev.kilua.compose.adaptive.TailwindcssBreakpoint.Companion.MIN_WIDTH_2XL
import dev.kilua.compose.adaptive.TailwindcssBreakpoint.Companion.MIN_WIDTH_LG
import dev.kilua.compose.adaptive.TailwindcssBreakpoint.Companion.MIN_WIDTH_MD
import dev.kilua.compose.adaptive.TailwindcssBreakpoint.Companion.MIN_WIDTH_SM
import dev.kilua.compose.adaptive.TailwindcssBreakpoint.Companion.MIN_WIDTH_XL

/**
 * Enum representing TailwindCSS screen size breakpoints.
 */
public enum class TailwindcssBreakpoint {
    /** Default breakpoint (smaller than SM). */
    DEFAULT,

    /** Small breakpoint (SM). */
    SM,

    /** Medium breakpoint (MD). */
    MD,

    /** Large breakpoint (LG). */
    LG,

    /** Extra large breakpoint (XL). */
    XL,

    /** 2x extra large breakpoint (2XL). */
    XL2;

    /**
     * Determines if the current breakpoint is greater than the given [breakpoint].
     *
     * @param breakpoint The breakpoint to compare against.
     * @return `true` if the current breakpoint is greater, `false` otherwise.
     */
    public infix fun isGreaterThan(breakpoint: TailwindcssBreakpoint): Boolean = this.ordinal > breakpoint.ordinal

    /**
     * Determines if the current breakpoint is smaller than the given [breakpoint].
     *
     * @param breakpoint The breakpoint to compare against.
     * @return `true` if the current breakpoint is smaller, `false` otherwise.
     */
    public infix fun isSmallerThan(breakpoint: TailwindcssBreakpoint): Boolean = this.ordinal < breakpoint.ordinal

    /**
     * Checks if the current breakpoint is between two given breakpoints (inclusive).
     *
     * @param smallBreakpoint The lower bound of the range.
     * @param largeBreakpoint The upper bound of the range.
     * @return `true` if the current breakpoint is within the range, `false` otherwise.
     */
    public fun isBetween(smallBreakpoint: TailwindcssBreakpoint, largeBreakpoint: TailwindcssBreakpoint): Boolean {
        return (smallBreakpoint.ordinal..largeBreakpoint.ordinal).contains(this.ordinal)
    }

    public companion object {
        /** Minimum width for the small breakpoint. */
        public var MIN_WIDTH_SM: Int = 640

        /** Minimum width for the medium breakpoint. */
        public var MIN_WIDTH_MD: Int = 768

        /** Minimum width for the large breakpoint. */
        public var MIN_WIDTH_LG: Int = 1024

        /** Minimum width for the extra large breakpoint. */
        public var MIN_WIDTH_XL: Int = 1280

        /** Minimum width for the 2x extra large breakpoint. */
        public var MIN_WIDTH_2XL: Int = 1536
    }
}

/**
 * Returns a [State] object representing the current TailwindCSS breakpoint of the screen.
 *
 * @return A [State] holding the current [TailwindcssBreakpoint].
 */
@Composable
public fun rememberTailwindcssBreakpoint(): State<TailwindcssBreakpoint> {
    val isSm by rememberMediaQueryAsState("(min-width: ${MIN_WIDTH_SM}px) and (max-width: ${MIN_WIDTH_MD - 1}px)")
    val isMd by rememberMediaQueryAsState("(min-width: ${MIN_WIDTH_MD}px) and (max-width: ${MIN_WIDTH_LG - 1}px)")
    val isLg by rememberMediaQueryAsState("(min-width: ${MIN_WIDTH_LG}px) and (max-width: ${MIN_WIDTH_XL - 1}px)")
    val isXl by rememberMediaQueryAsState("(min-width: ${MIN_WIDTH_XL}px) and (max-width: ${MIN_WIDTH_2XL - 1}px)")
    val is2Xl by rememberMediaQueryAsState("(min-width: ${MIN_WIDTH_2XL}px)")

    return remember {
        derivedStateOf {
            when {
                isSm -> TailwindcssBreakpoint.SM
                isMd -> TailwindcssBreakpoint.MD
                isLg -> TailwindcssBreakpoint.LG
                isXl -> TailwindcssBreakpoint.XL
                is2Xl -> TailwindcssBreakpoint.XL2
                else -> TailwindcssBreakpoint.DEFAULT
            }
        }
    }
}
