/*
 * Copyright (c) 2023 Robert Jaros
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

// Inspired by the Compose Multiplatform Window Size API
// See also: https://github.com/JetBrains/compose-multiplatform-core/blob/6fd7ac43945492c0f61d42ff25594c34b4136d08/compose/material3/material3-window-size-class/src/commonMain/kotlin/androidx/compose/material3/windowsizeclass/WindowSizeClass.kt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.html.helpers.onGlobalWindowSize
import web.window
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * A composable function that calculates and returns the current window size class.
 *
 * This function dynamically monitors changes in the window's dimensions using [onGlobalWindowSize]
 * and calculates the appropriate [WindowSizeClass] based on the updated width and height.
 * The resulting [WindowSizeClass] can be used to adapt the UI to different screen sizes.
 *
 * @return A [WindowSizeClass] object representing the current size class of the window.
 *
 * Example usage:
 * ```
 * val windowSizeClass = currentWindowSizeClass()
 * when (windowSizeClass) {
 *     WindowSizeClass.Compact -> println("Compact layout")
 *     WindowSizeClass.Medium -> println("Medium layout")
 *     WindowSizeClass.Expanded -> println("Expanded layout")
 * }
 * ```
 *
 * @see WindowSizeClass
 * @see onGlobalWindowSize
 */
@Composable
public fun currentWindowSizeClass(): WindowSizeClass {
    var width by remember { mutableIntStateOf(window.innerWidth) }
    var height by remember { mutableIntStateOf(window.innerHeight) }
    val adaptiveInfo = remember(width, height) { WindowSizeClass.calculateFromSize(width, height) }
    onGlobalWindowSize { w, h ->
        width = w
        height = h
    }

    return adaptiveInfo
}

/**
 * Window size classes are a set of opinionated viewport breakpoints to design, develop, and test
 * responsive application layouts against. For more details check <a
 * href="https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes"
 * class="external" target="_blank">Support different screen sizes</a> documentation.
 *
 * WindowSizeClass contains a [WindowWidthSizeClass] and [WindowHeightSizeClass], representing the
 * window size classes for this window's width and height respectively.
 *
 * @property widthSizeClass width-based window size class ([WindowWidthSizeClass])
 * @property heightSizeClass height-based window size class ([WindowHeightSizeClass])
 */
@Immutable
public class WindowSizeClass private constructor(
    public val widthSizeClass: WindowWidthSizeClass,
    public val heightSizeClass: WindowHeightSizeClass,
) {
    public companion object {
        /**
         * Calculates the best matched [WindowSizeClass] for a given size according to the
         * provided [supportedWidthSizeClasses] and [supportedHeightSizeClasses].
         *
         * @param width width of the window
         * @param height height of the window
         * @param supportedWidthSizeClasses the set of width size classes that are supported
         * @param supportedHeightSizeClasses the set of height size classes that are supported
         * @return [WindowSizeClass] corresponding to the given width and height
         */
        public fun calculateFromSize(
            width: Int,
            height: Int,
            supportedWidthSizeClasses: Set<WindowWidthSizeClass> = WindowWidthSizeClass.DefaultSizeClasses,
            supportedHeightSizeClasses: Set<WindowHeightSizeClass> = WindowHeightSizeClass.DefaultSizeClasses,
        ): WindowSizeClass {
            val windowWidthSizeClass =
                WindowWidthSizeClass.fromWidth(width, supportedWidthSizeClasses)
            val windowHeightSizeClass =
                WindowHeightSizeClass.fromHeight(height, supportedHeightSizeClasses)
            return WindowSizeClass(windowWidthSizeClass, windowHeightSizeClass)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as WindowSizeClass

        if (widthSizeClass != other.widthSizeClass) return false
        if (heightSizeClass != other.heightSizeClass) return false

        return true
    }

    override fun hashCode(): Int {
        var result = widthSizeClass.hashCode()
        result = 31 * result + heightSizeClass.hashCode()
        return result
    }

    override fun toString(): String = "WindowSizeClass($widthSizeClass, $heightSizeClass)"
}

/**
 * Width-based window size class.
 *
 * A window size class represents a breakpoint that can be used to build responsive layouts. Each
 * window size class breakpoint represents a majority case for typical device scenarios so your
 * layouts will work well on most devices and configurations.
 *
 * For more details see <a
 * href="https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes#window_size_classes"
 * class="external" target="_blank">Window size classes documentation</a>.
 */
@Immutable
public sealed class WindowWidthSizeClass : Comparable<WindowWidthSizeClass> {
    /** Represents the majority of phones in portrait. */
    public data object Compact : WindowWidthSizeClass()

    /** Represents the majority of tablets in portrait and large unfolded inner displays in portrait. */
    public data object Medium : WindowWidthSizeClass()

    /** Represents the majority of tablets in landscape and large unfolded inner displays in landscape. */
    public data object Expanded : WindowWidthSizeClass()

    override operator fun compareTo(other: WindowWidthSizeClass): Int =
        breakpoint().compareTo(other.breakpoint())

    override fun toString(): String = "WindowWidthSizeClass." + when (this) {
        Compact -> "Compact"
        Medium -> "Medium"
        Expanded -> "Expanded"
    }

    public companion object {
        public var COMPACT_PX: Int = 0
        public var MEDIUM_PX: Int = 600
        public var EXPANDED_PX: Int = 840

        /**
         * The default set of size classes that includes [Compact], [Medium], and [Expanded] size
         * classes. Should never expand to ensure behavioral consistency.
         */
        @Suppress("PrimitiveInCollection")
        public val DefaultSizeClasses: Set<WindowWidthSizeClass> = setOf(Compact, Medium, Expanded)

        @Suppress("PrimitiveInCollection")
        private val AllSizeClassList = listOf(Expanded, Medium, Compact)

        /**
         * The set of all size classes. It's supposed to be expanded whenever a new size class is
         * defined. By default [WindowSizeClass.calculateFromSize] will only return size classes in
         * [DefaultSizeClasses] in order to avoid behavioral changes when new size classes are
         * added. You can opt in to support all available size classes by doing:
         * ```
         * WindowSizeClass.calculateFromSize(
         *     size = size,
         *     density = density,
         *     supportedWidthSizeClasses = WindowWidthSizeClass.AllSizeClasses,
         *     supportedHeightSizeClasses = WindowHeightSizeClass.AllSizeClasses
         * )
         * ```
         */
        @Suppress("ListIterator", "PrimitiveInCollection")
        public val AllSizeClasses: Set<WindowWidthSizeClass> = AllSizeClassList.toSet()

        private fun WindowWidthSizeClass.breakpoint(): Int = when (this) {
            Expanded -> EXPANDED_PX
            Medium -> MEDIUM_PX
            Compact -> COMPACT_PX
        }

        /**
         * Calculates the best matched [WindowWidthSizeClass] for a given [width] in Pixels.
         */
        internal fun fromWidth(
            width: Int,
            supportedSizeClasses: Set<WindowWidthSizeClass>
        ): WindowWidthSizeClass {
            require(width >= 0) { "Width must not be negative" }
            require(supportedSizeClasses.isNotEmpty()) { "Must support at least one size class" }
            var smallestSupportedSizeClass: WindowWidthSizeClass = Compact
            AllSizeClassList.fastForEach {
                if (it in supportedSizeClasses) {
                    if (width >= it.breakpoint()) {
                        return it
                    }
                    smallestSupportedSizeClass = it
                }
            }

            // If none of the size classes matches, return the largest one.
            return smallestSupportedSizeClass
        }
    }
}

/**
 * Height-based window size class.
 *
 * A window size class represents a breakpoint that can be used to build responsive layouts. Each
 * window size class breakpoint represents a majority case for typical device scenarios so your
 * layouts will work well on most devices and configurations.
 *
 * For more details see <a
 * href="https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes#window_size_classes"
 * class="external" target="_blank">Window size classes documentation</a>.
 */
@Immutable
public sealed class WindowHeightSizeClass : Comparable<WindowHeightSizeClass> {
    /** Represents the majority of phones in landscap. */
    public data object Compact : WindowHeightSizeClass()

    /** Represents the majority of tablets in landscape and majority of phones in portrait. */
    public data object Medium : WindowHeightSizeClass()

    /** Represents the majority of tablets in portrait. */
    public data object Expanded : WindowHeightSizeClass()

    override operator fun compareTo(other: WindowHeightSizeClass): Int =
        breakpoint().compareTo(other.breakpoint())

    override fun toString(): String = "WindowHeightSizeClass." + when (this) {
        Compact -> "Compact"
        Medium -> "Medium"
        Expanded -> "Expanded"
    }

    public companion object {
        public var COMPACT_PX: Int = 0
        public var MEDIUM_PX: Int = 480
        public var EXPANDED_PX: Int = 900

        /**
         * The default set of size classes that includes [Compact], [Medium], and [Expanded] size
         * classes. Should never expand to ensure behavioral consistency.
         */
        @Suppress("PrimitiveInCollection")
        public val DefaultSizeClasses: Set<WindowHeightSizeClass> = setOf(Compact, Medium, Expanded)

        @Suppress("PrimitiveInCollection")
        private val AllSizeClassList = listOf(Expanded, Medium, Compact)

        /**
         * The set of all size classes. It's supposed to be expanded whenever a new size class is
         * defined. By default [WindowSizeClass.calculateFromSize] will only return size classes in
         * [DefaultSizeClasses] in order to avoid behavioral changes when new size classes are
         * added. You can opt in to support all available size classes by doing:
         * ```
         * WindowSizeClass.calculateFromSize(
         *     size = size,
         *     density = density,
         *     supportedWidthSizeClasses = WindowWidthSizeClass.AllSizeClasses,
         *     supportedHeightSizeClasses = WindowHeightSizeClass.AllSizeClasses
         * )
         * ```
         */
        @Suppress("ListIterator", "PrimitiveInCollection")
        public val AllSizeClasses: Set<WindowHeightSizeClass> = AllSizeClassList.toSet()

        private fun WindowHeightSizeClass.breakpoint(): Int = when (this) {
            Expanded -> EXPANDED_PX
            Medium -> MEDIUM_PX
            Compact -> COMPACT_PX
        }

        /**
         * Calculates the best matched [WindowHeightSizeClass] for a given [height] in Pixels.
         */
        internal fun fromHeight(
            height: Int,
            supportedSizeClasses: Set<WindowHeightSizeClass>
        ): WindowHeightSizeClass {
            require(height >= 0) { "Width must not be negative" }
            require(supportedSizeClasses.isNotEmpty()) { "Must support at least one size class" }
            var smallestSupportedSizeClass: WindowHeightSizeClass = Expanded
            AllSizeClassList.fastForEach {
                if (it in supportedSizeClasses) {
                    if (height >= it.breakpoint()) {
                        return it
                    }
                    smallestSupportedSizeClass = it
                }
            }

            // If none of the size classes matches, return the largest one.
            return smallestSupportedSizeClass
        }
    }
}

/**
 * Iterates through a [List] using the index and calls [action] for each item. This does not
 * allocate an iterator like [Iterable.forEach].
 *
 * **Do not use for collections that come from public APIs**, since they may not support random
 * access in an efficient way, and this method may actually be a lot slower. Only use for
 * collections that are created by code we control and are known to support random access.
 */
@Suppress("BanInlineOptIn")
@OptIn(ExperimentalContracts::class)
private inline fun <T> List<T>.fastForEach(action: (T) -> Unit) {
    contract { callsInPlace(action) }
    for (index in indices) {
        val item = get(index)
        action(item)
    }
}
