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

package dev.kilua.html

/**
 * Definitions of CSS units.
 */
@Suppress("EnumNaming")
public enum class CssUnit(public val cssUnit: String) {
    px("px"),
    pt("pt"),
    em("em"),
    cm("cm"),
    mm("mm"),
    `in`("in"),
    pc("pc"),
    ch("ch"),
    rem("rem"),
    vw("vw"),
    vh("vh"),
    vmin("vmin"),
    vmax("vmax"),
    perc("%"),
    units(""),
    auto("auto"),
    normal("normal"),
    initial("initial"),
    inherit("inherit"),
}

/**
 * A type used for defining CSS dimensions (width, heights, margins, paddings, etc.).
 */
public open class CssSize(protected val cssSize: Number, protected val unit: CssUnit) {

    public val value: String = when (this.unit) {
        CssUnit.auto -> "auto"
        CssUnit.normal -> "normal"
        CssUnit.initial -> "initial"
        CssUnit.inherit -> "inherit"
        else -> this.cssSize.toString() + this.unit.cssUnit
    }

    override fun toString(): String = value

    /**
     * Extension operator to increase CssSize units.
     */
    public operator fun plus(i: Number): CssSize {
        return CssSize(this.cssSize.toDouble() + i.toDouble(), this.unit)
    }

    /**
     * Extension operator to decrease CssSize units.
     */
    public operator fun minus(i: Number): CssSize {
        return CssSize(this.cssSize.toDouble() - i.toDouble(), this.unit)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CssSize) return false
        if (cssSize != other.cssSize) return false
        if (unit != other.unit) return false
        return true
    }

    override fun hashCode(): Int {
        var result = cssSize.hashCode()
        result = 31 * result + unit.hashCode()
        result = 31 * result + value.hashCode()
        return result
    }
}

/**
 * Extension property to convert Int to CSS px units.
 */
public val Int.px: CssSize
    get() {
        return CssSize(this, CssUnit.px)
    }

/**
 * Extension property to convert Int to CSS em units.
 */
public val Number.em: CssSize
    get() {
        return CssSize(this, CssUnit.em)
    }

/**
 * Extension property to convert Int to CSS pt units.
 */
public val Int.pt: CssSize
    get() {
        return CssSize(this, CssUnit.pt)
    }

/**
 * Extension property to convert Int to CSS percent units.
 */
public val Number.perc: CssSize
    get() {
        return CssSize(this, CssUnit.perc)
    }

/**
 * Extension property to convert Int to CSS rem units.
 */
public val Number.rem: CssSize
    get() {
        return CssSize(this, CssUnit.rem)
    }

/**
 * Extension property to convert Int to CSS ch units.
 */
public val Number.ch: CssSize
    get() {
        return CssSize(this, CssUnit.ch)
    }

/**
 * Extension property to convert Int to CSS cm units.
 */
public val Number.cm: CssSize
    get() {
        return CssSize(this, CssUnit.cm)
    }

/**
 * Extension property to convert Int to CSS mm units.
 */
public val Number.mm: CssSize
    get() {
        return CssSize(this, CssUnit.mm)
    }

/**
 * Extension property to convert Int to CSS in units.
 */
@Suppress("TopLevelPropertyNaming")
public val Number.`in`: CssSize
    get() {
        return CssSize(this, CssUnit.`in`)
    }

/**
 * Extension property to convert Int to CSS pc units.
 */
public val Number.pc: CssSize
    get() {
        return CssSize(this, CssUnit.pc)
    }

/**
 * Extension property to convert Int to CSS vh units.
 */
public val Number.vh: CssSize
    get() {
        return CssSize(this, CssUnit.vh)
    }

/**
 * Extension property to convert Int to CSS vw units.
 */
public val Number.vw: CssSize
    get() {
        return CssSize(this, CssUnit.vw)
    }

/**
 * Extension property to convert Int to CSS vmin units.
 */
public val Number.vmin: CssSize
    get() {
        return CssSize(this, CssUnit.vmin)
    }

/**
 * Extension property to convert Int to CSS vmax units.
 */
public val Number.vmax: CssSize
    get() {
        return CssSize(this, CssUnit.vmax)
    }

/**
 * Extension property to convert Number to empty units.
 */
public val Number.units: CssSize
    get() {
        return CssSize(this, CssUnit.units)
    }

/**
 * Helper property to describe CSS auto value.
 */
public val auto: CssSize = CssSize(0, CssUnit.auto)

/**
 * Helper property to describe CSS normal value.
 */
public val normal: CssSize = CssSize(0, CssUnit.normal)

/**
 * Helper property to describe CSS initial value.
 */
public val initial: CssSize = CssSize(0, CssUnit.initial)

/**
 * Helper property to describe CSS initial value.
 */
public val inherit: CssSize = CssSize(0, CssUnit.inherit)
