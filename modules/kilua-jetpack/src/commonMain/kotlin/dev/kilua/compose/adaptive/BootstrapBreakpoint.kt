/*
 * Copyright (c) 2025 Robert Jaros
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
import dev.kilua.compose.adaptive.BootstrapBreakpoint.Companion.MIN_WIDTH_LG
import dev.kilua.compose.adaptive.BootstrapBreakpoint.Companion.MIN_WIDTH_MD
import dev.kilua.compose.adaptive.BootstrapBreakpoint.Companion.MIN_WIDTH_SM
import dev.kilua.compose.adaptive.BootstrapBreakpoint.Companion.MIN_WIDTH_XL
import dev.kilua.compose.adaptive.BootstrapBreakpoint.Companion.MIN_WIDTH_XXL

/**
 * Enum representing Bootstrap screen size breakpoints.
 */
public enum class BootstrapBreakpoint {
    /** Default breakpoint (extra small). */
    DEFAULT,

    /** Small breakpoint (SM). */
    SM,

    /** Medium breakpoint (MD). */
    MD,

    /** Large breakpoint (LG). */
    LG,

    /** Extra large breakpoint (XL). */
    XL,

    /** Extra extra large breakpoint (XXL). */
    XXL;

    /**
     * Determines if the current breakpoint is greater than the given [breakpoint].
     *
     * @param breakpoint The breakpoint to compare against.
     * @return `true` if the current breakpoint is greater, `false` otherwise.
     */
    public infix fun isGreaterThan(breakpoint: BootstrapBreakpoint): Boolean = this.ordinal > breakpoint.ordinal

    /**
     * Determines if the current breakpoint is smaller than the given [breakpoint].
     *
     * @param breakpoint The breakpoint to compare against.
     * @return `true` if the current breakpoint is smaller, `false` otherwise.
     */
    public infix fun isSmallerThan(breakpoint: BootstrapBreakpoint): Boolean = this.ordinal < breakpoint.ordinal

    /**
     * Checks if the current breakpoint is between two given breakpoints (inclusive).
     *
     * @param smallBreakpoint The lower bound of the range.
     * @param largeBreakpoint The upper bound of the range.
     * @return `true` if the current breakpoint is within the range, `false` otherwise.
     */
    public fun isBetween(smallBreakpoint: BootstrapBreakpoint, largeBreakpoint: BootstrapBreakpoint): Boolean {
        return (smallBreakpoint.ordinal..largeBreakpoint.ordinal).contains(this.ordinal)
    }

    public companion object {
        /** Minimum width for the small breakpoint. */
        public var MIN_WIDTH_SM: Int = 576

        /** Minimum width for the medium breakpoint. */
        public var MIN_WIDTH_MD: Int = 768

        /** Minimum width for the large breakpoint. */
        public var MIN_WIDTH_LG: Int = 992

        /** Minimum width for the extra large breakpoint. */
        public var MIN_WIDTH_XL: Int = 1200

        /** Minimum width for the extra extra large breakpoint. */
        public var MIN_WIDTH_XXL: Int = 1400
    }
}

/**
 * Returns a [State] object representing the current Bootstrap breakpoint of the screen.
 *
 * @return A [State] holding the current [BootstrapBreakpoint].
 */
@Composable
public fun rememberBootstrapBreakpoint(): State<BootstrapBreakpoint> {
    val isSm by rememberMediaQueryAsState("(min-width: ${MIN_WIDTH_SM}px) and (max-width: ${MIN_WIDTH_MD - 1}px)")
    val isMd by rememberMediaQueryAsState("(min-width: ${MIN_WIDTH_MD}px) and (max-width: ${MIN_WIDTH_LG - 1}px)")
    val isLg by rememberMediaQueryAsState("(min-width: ${MIN_WIDTH_LG}px) and (max-width: ${MIN_WIDTH_XL - 1}px)")
    val isXl by rememberMediaQueryAsState("(min-width: ${MIN_WIDTH_XL}px) and (max-width: ${MIN_WIDTH_XXL - 1}px)")
    val isXxl by rememberMediaQueryAsState("(min-width: ${MIN_WIDTH_XXL}px)")

    return remember {
        derivedStateOf {
            when {
                isSm -> BootstrapBreakpoint.SM
                isMd -> BootstrapBreakpoint.MD
                isLg -> BootstrapBreakpoint.LG
                isXl -> BootstrapBreakpoint.XL
                isXxl -> BootstrapBreakpoint.XXL
                else -> BootstrapBreakpoint.DEFAULT
            }
        }
    }
}
