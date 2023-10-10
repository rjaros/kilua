/*
 * Copyright (c) 2023-present Robert Jaros
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

import dev.kilua.core.CssSize
import dev.kilua.core.StringPair
import dev.kilua.html.CssUnit

/**
 * Utility extension function for casting. Uses unsafeCast() on JS.
 */
public expect inline fun <T> Any?.cast(): T

/**
 * Extension property to convert Int to CSS px units.
 */
public val Int.px: CssSize
    get() {
        return Pair(this, CssUnit.px)
    }

/**
 * Extension property to convert Int to CSS em units.
 */
public val Number.em: CssSize
    get() {
        return Pair(this, CssUnit.em)
    }

/**
 * Extension property to convert Int to CSS pt units.
 */
public val Int.pt: CssSize
    get() {
        return Pair(this, CssUnit.pt)
    }

/**
 * Extension property to convert Int to CSS percent units.
 */
public val Number.perc: CssSize
    get() {
        return Pair(this, CssUnit.perc)
    }

/**
 * Extension property to convert Int to CSS rem units.
 */
public val Number.rem: CssSize
    get() {
        return Pair(this, CssUnit.rem)
    }

/**
 * Extension property to convert Int to CSS ch units.
 */
public val Number.ch: CssSize
    get() {
        return Pair(this, CssUnit.ch)
    }

/**
 * Extension property to convert Int to CSS cm units.
 */
public val Number.cm: CssSize
    get() {
        return Pair(this, CssUnit.cm)
    }

/**
 * Extension property to convert Int to CSS mm units.
 */
public val Number.mm: CssSize
    get() {
        return Pair(this, CssUnit.mm)
    }

/**
 * Extension property to convert Int to CSS in units.
 */
@Suppress("TopLevelPropertyNaming")
public val Number.`in`: CssSize
    get() {
        return Pair(this, CssUnit.`in`)
    }

/**
 * Extension property to convert Int to CSS pc units.
 */
public val Number.pc: CssSize
    get() {
        return Pair(this, CssUnit.pc)
    }

/**
 * Extension property to convert Int to CSS vh units.
 */
public val Number.vh: CssSize
    get() {
        return Pair(this, CssUnit.vh)
    }

/**
 * Extension property to convert Int to CSS vw units.
 */
public val Number.vw: CssSize
    get() {
        return Pair(this, CssUnit.vw)
    }

/**
 * Extension property to convert Int to CSS vmin units.
 */
public val Number.vmin: CssSize
    get() {
        return Pair(this, CssUnit.vmin)
    }

/**
 * Extension property to convert Int to CSS vmax units.
 */
public val Number.vmax: CssSize
    get() {
        return Pair(this, CssUnit.vmax)
    }

/**
 * Helper property to describe CSS auto value.
 */
public val auto: CssSize = Pair(0, CssUnit.auto)

/**
 * Helper property to describe CSS normal value.
 */
public val normal: CssSize = Pair(0, CssUnit.normal)

/**
 * Extension function to convert CssSize to String.
 */
public fun CssSize.asString(): String {
    return when (this.second) {
        CssUnit.auto -> "auto"
        CssUnit.normal -> "normal"
        else -> this.first.toString() + this.second.cssUnit
    }
}

/**
 * Extension operator to increase CssSize units.
 */
public operator fun CssSize?.plus(i: Number): CssSize {
    return this?.let { CssSize(it.first.toDouble() + i.toDouble(), it.second) } ?: CssSize(i, CssUnit.px)
}

/**
 * Extension operator to decrease CssSize units.
 */
public operator fun CssSize?.minus(i: Number): CssSize {
    return this?.let { CssSize(it.first.toDouble() - i.toDouble(), it.second) } ?: CssSize(i, CssUnit.px)
}

/**
 * Utility extension function to synchronise elements of the MutableList.
 */
public fun <T> MutableList<T>.syncWithList(list: List<T>) {
    if (list.isEmpty()) {
        this.clear()
    } else {
        for (pos in (this.size - 1) downTo list.size) this.removeAt(pos)
        list.forEachIndexed { index, element ->
            if (index < this.size) {
                if (this[index] != element) this[index] = element
            } else {
                this.add(element)
            }
        }
    }
}

/**
 * Utility extension function to convert List<String> into List<StringPair>.
 */
public fun List<String>.pairs(): List<StringPair> = this.map { it to it }

/**
 * Builds List<StringPair> out of given Strings.
 */
public fun listOfPairs(vararg params: String): List<StringPair> = params.asList().pairs()
