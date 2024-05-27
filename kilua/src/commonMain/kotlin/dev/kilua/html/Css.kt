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

import androidx.compose.runtime.Stable
import dev.kilua.utils.toKebabCase

/**
 * Definitions of CSS border styles.
 */
public enum class BorderStyle {
    None,
    Hidden,
    Dotted,
    Dashed,
    Solid,
    Double,
    Groove,
    Ridge,
    Inset,
    Outset,
    Initial,
    Inherit,
    Unset;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value
}

/**
 * Definitions of CSS outline styles.
 */
public enum class OutlineStyle {
    Auto,
    None,
    Hidden,
    Dotted,
    Dashed,
    Solid,
    Double,
    Groove,
    Ridge,
    Inset,
    Outset,
    Initial,
    Inherit,
    Unset;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS background size.
 */
public enum class BgSize {
    Cover,
    Contain;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS background repeat options.
 */
public enum class BgRepeat {
    Repeat,
    RepeatX,
    RepeatY,
    NoRepeat;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS background attachment options.
 */
public enum class BgAttach {
    Scroll,
    Fixed,
    Local;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS background origin options.
 */
public enum class BgOrigin {
    PaddingBox,
    BorderBox,
    ContentBox;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS background clipping options.
 */
public enum class BgClip {
    PaddingBox,
    BorderBox,
    ContentBox;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS display options.
 */
public enum class Display {
    Inline,
    Block,
    Flex,
    Grid,
    InlineBlock,
    InlineFlex,
    InlineGrid,
    InlineTable,
    ListItem,
    RunIn,
    Table,
    TableCaption,
    TableColumnGroup,
    TableHeaderGroup,
    TableFooterGroup,
    TableRowGroup,
    TableCell,
    TableColumn,
    TableTow,
    Contents,
    None,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS position options.
 */
public enum class Position {
    Static,
    Relative,
    Fixed,
    Absolute,
    Sticky;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS overflow options.
 */
public enum class Overflow {
    Visible,
    Hidden,
    Scroll,
    Auto,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS overflow-wrap options.
 */
public enum class OverflowWrap {
    Normal,
    BreakWord,
    Anywhere;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS resize options.
 */
public enum class Resize {
    None,
    Both,
    Horizontal,
    Vertical,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS text direction options.
 */
public enum class Direction {
    Ltr,
    Rtl,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS text align options.
 */
public enum class TextAlign {
    Left,
    Right,
    Center,
    Justify,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS text decoration line options.
 */
public enum class TextDecorationLine {
    None,
    Underline,
    Overline,
    LineThrough,
    Justify,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS text decoration style options.
 */
public enum class TextDecorationStyle {
    Solid,
    Double,
    Dotted,
    Dashed,
    Wavy,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS text transform options.
 */
public enum class TextTransform {
    None,
    Capitalize,
    Uppercase,
    Lowercase,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS text overflow options.
 */
public enum class TextOverflow {
    Clip,
    Ellipsis,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS unicode-bidi options.
 */
public enum class UnicodeBidi {
    Normal,
    Embed,
    BidiOverride,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS vertical align options.
 */
public enum class VerticalAlign {
    Baseline,
    Sub,
    Super,
    Top,
    TextTop,
    Middle,
    Bottom,
    TextBottom,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS white space options.
 */
public enum class WhiteSpace {
    Normal,
    Nowrap,
    Pre,
    PreLine,
    PreWrap,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS font style options.
 */
public enum class FontStyle {
    Normal,
    Italic,
    Oblique,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS font variant options.
 */
public enum class FontVariant {
    Normal,
    SmallCaps,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS font weight options.
 */
public enum class FontWeight {
    Normal,
    Bold,
    Bolder,
    Lighter,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS float options.
 */
public enum class CssFloat {
    None,
    Left,
    Right,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS clear options.
 */
public enum class Clear {
    None,
    Left,
    Right,
    Both,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS word-break options.
 */
public enum class WordBreak {
    Normal,
    KeepAll,
    BreakAll;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Definitions of CSS line-break options.
 */
public enum class LineBreak {
    Auto,
    Loose,
    Normal,
    Strict,
    Anywhere;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

public enum class Cursor {
    Default,
    Auto,
    None,
    Alias,
    AllScroll,
    Cell,
    ContextMenu,
    ColResize,
    Copy,
    Crosshair,
    EResize,
    EwResize,
    Grab,
    Grabbing,
    Help,
    Move,
    NResize,
    NeResize,
    NeswResize,
    NsResize,
    NwResize,
    NwseResize,
    NoDrop,
    NotAllowed,
    Pointer,
    Progress,
    RowResize,
    SResize,
    SeResize,
    SwResize,
    Text,
    VerticalText,
    WResize,
    Wait,
    ZoomIn,
    ZoomOut,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * CSS flexbox directions.
 */
public enum class FlexDirection {
    Row,
    RowReverse,
    Column,
    ColumnReverse;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * CSS flexbox wrap modes.
 */
public enum class FlexWrap {
    Nowrap,
    Wrap,
    WrapReverse;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * CSS grid items justification options.
 */
public enum class JustifyItems {
    Start,
    End,
    Center,
    Stretch;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * CSS flexbox/grid content justification options.
 */
public enum class JustifyContent {
    FlexStart,
    FlexEnd,
    Center,
    SpaceBetween,
    SpaceAround,
    SpaceEvenly,
    Start,
    End,
    Stretch;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * CSS flexbox/grid items alignments options.
 */
public enum class AlignItems {
    FlexStart,
    FlexEnd,
    Center,
    Baseline,
    Stretch,
    Start,
    End;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * CSS flexbox/grid content alignment options.
 */
public enum class AlignContent {
    FlexStart,
    FlexEnd,
    Center,
    SpaceBetween,
    SpaceAround,
    Stretch,
    Start,
    End,
    SpaceEvenly;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * CSS grid flow options.
 */
public enum class GridAutoFlow(public val value: String) {
    Row("row"),
    Column("column"),
    Dense("dense"),
    RowDense("row dense"),
    ColumnDense("column dense");

    override fun toString(): String = value

}

/**
 * CSS list style type options.
 */
public enum class ListStyleType {
    Disc,
    Circle,
    Square,
    Decimal,
    CjkDecimal,
    DecimalLeadingZero,
    LowerRoman,
    UpperRoman,
    LowerGreek,
    LowerLatin,
    UpperAlpha,
    LowerAlpha,
    UpperLatin,
    ArabicIndic,
    Armenian,
    Bengali,
    Cambodian,
    CjkIdeographic,
    Georgian,
    Hebrew,
    Hiragana,
    HiraganaIroha,
    JapaneseFormal,
    JapaneseInformal,
    Katakana,
    KatakanaIroha,
    None,
    Initial,
    Inherit;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * CSS list style position options.
 */
public enum class ListStylePosition {
    Inside,
    Outside;

    public val value: String = name.toKebabCase()
    override fun toString(): String = value

}

/**
 * Type-safe definition of CSS border.
 * @param width width of the border
 * @param style style of the border
 * @param color color of the border
 */
@Stable
public open class Border(
    protected val width: CssSize? = null, protected val style: BorderStyle? = null,
    protected val color: Color? = null
) {
    public val value: String = listOfNotNull(width?.toString(), style?.toString(), color?.toString()).joinToString(" ")
    override fun toString(): String = value
    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

/**
 * Type-safe definition of CSS outline.
 * @param width width of the outline
 * @param style style of the outline
 * @param color color of the outline
 */
@Stable
public open class Outline(
    protected val width: CssSize? = null, protected val style: OutlineStyle? = null,
    protected val color: Color? = null
) {
    public val value: String = listOfNotNull(width?.toString(), style?.toString(), color?.toString()).joinToString(" ")

    override fun toString(): String = value
    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

/**
 * Type-safe definition of CSS color.
 * @param color CSS color string
 */
@Stable
public open class Color(protected val color: String? = null) {

    public val value: String = color.orEmpty()

    override fun toString(): String = value
    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    public companion object {
        /**
         * Creates CSS Color with color given in hex format.
         * @param color color in hex format
         */
        @Suppress("MagicNumber")
        @OptIn(ExperimentalStdlibApi::class)
        public fun hex(color: Int): Color {
            return Color("#" + color.toHexString().takeLast(6))
        }

        /**
         * Creates CSS Color with red, green and blue components
         */
        @Suppress("MagicNumber")
        public fun rgb(red: Int, green: Int, blue: Int): Color {
            return Color("#" + listOf(red, green, blue).joinToString("") { it.toString(16).padStart(2, '0') })
        }

        /**
         * Creates CSS Color with red, green, blue and alpha channel components
         */
        @Suppress("MagicNumber")
        public fun rgba(red: Int, green: Int, blue: Int, alpha: Int): Color {
            return Color("#" + listOf(red, green, blue, alpha).joinToString("") { it.toString(16).padStart(2, '0') })
        }

        public val Aliceblue: Color = Color("aliceblue")
        public val Antiquewhite: Color = Color("antiquewhite")
        public val Aqua: Color = Color("aqua")
        public val Aquamarine: Color = Color("aquamarine")
        public val Azure: Color = Color("azure")
        public val Beige: Color = Color("beige")
        public val Bisque: Color = Color("bisque")
        public val Black: Color = Color("black")
        public val Blanchedalmond: Color = Color("blanchedalmond")
        public val Blue: Color = Color("blue")
        public val Blueviolet: Color = Color("blueviolet")
        public val Brown: Color = Color("brown")
        public val Burlywood: Color = Color("burlywood")
        public val Cadetblue: Color = Color("cadetblue")
        public val Chartreuse: Color = Color("chartreuse")
        public val Chocolate: Color = Color("chocolate")
        public val Coral: Color = Color("coral")
        public val Cornflowerblue: Color = Color("cornflowerblue")
        public val Cornsilk: Color = Color("cornsilk")
        public val Crimson: Color = Color("crimson")
        public val Cyan: Color = Color("cyan")
        public val Darkblue: Color = Color("darkblue")
        public val Darkcyan: Color = Color("darkcyan")
        public val Darkgoldenrod: Color = Color("darkgoldenrod")
        public val Darkgray: Color = Color("darkgray")
        public val Darkgreen: Color = Color("darkgreen")
        public val Darkkhaki: Color = Color("darkkhaki")
        public val Darkmagenta: Color = Color("darkmagenta")
        public val Darkolivegreen: Color = Color("darkolivegreen")
        public val Darkorange: Color = Color("darkorange")
        public val Darkorchid: Color = Color("darkorchid")
        public val Darkred: Color = Color("darkred")
        public val Darksalmon: Color = Color("darksalmon")
        public val Darkseagreen: Color = Color("darkseagreen")
        public val Darkslateblue: Color = Color("darkslateblue")
        public val Darkslategray: Color = Color("darkslategray")
        public val Darkturquoise: Color = Color("darkturquoise")
        public val Darkviolet: Color = Color("darkviolet")
        public val Deeppink: Color = Color("deeppink")
        public val Deepskyblue: Color = Color("deepskyblue")
        public val Dimgray: Color = Color("dimgray")
        public val Dodgerblue: Color = Color("dodgerblue")
        public val Firebrick: Color = Color("firebrick")
        public val Floralwhite: Color = Color("floralwhite")
        public val Forestgreen: Color = Color("forestgreen")
        public val Fuchsia: Color = Color("fuchsia")
        public val Gainsboro: Color = Color("gainsboro")
        public val Ghostwhite: Color = Color("ghostwhite")
        public val Gold: Color = Color("gold")
        public val Goldenrod: Color = Color("goldenrod")
        public val Gray: Color = Color("gray")
        public val Green: Color = Color("green")
        public val Greenyellow: Color = Color("greenyellow")
        public val Honeydew: Color = Color("honeydew")
        public val Hotpink: Color = Color("hotpink")
        public val Indianred: Color = Color("indianred")
        public val Indigo: Color = Color("indigo")
        public val Ivory: Color = Color("ivory")
        public val Khaki: Color = Color("khaki")
        public val Lavender: Color = Color("lavender")
        public val Lavenderblush: Color = Color("lavenderblush")
        public val Lawngreen: Color = Color("lawngreen")
        public val Lemonchiffon: Color = Color("lemonchiffon")
        public val Lightblue: Color = Color("lightblue")
        public val Lightcoral: Color = Color("lightcoral")
        public val Lightcyan: Color = Color("lightcyan")
        public val Lightgoldenrodyellow: Color = Color("lightgoldenrodyellow")
        public val Lightgray: Color = Color("lightgray")
        public val Lightgreen: Color = Color("lightgreen")
        public val Lightpink: Color = Color("lightpink")
        public val Lightsalmon: Color = Color("lightsalmon")
        public val Lightseagreen: Color = Color("lightseagreen")
        public val Lightskyblue: Color = Color("lightskyblue")
        public val Lightslategray: Color = Color("lightslategray")
        public val Lightsteelblue: Color = Color("lightsteelblue")
        public val Lightyellow: Color = Color("lightyellow")
        public val Lime: Color = Color("lime")
        public val Limegreen: Color = Color("limegreen")
        public val Linen: Color = Color("linen")
        public val Magenta: Color = Color("magenta")
        public val Maroon: Color = Color("maroon")
        public val Mediumaquamarine: Color = Color("mediumaquamarine")
        public val Mediumblue: Color = Color("mediumblue")
        public val Mediumorchid: Color = Color("mediumorchid")
        public val Mediumpurple: Color = Color("mediumpurple")
        public val Mediumseagreen: Color = Color("mediumseagreen")
        public val Mediumslateblue: Color = Color("mediumslateblue")
        public val Mediumspringgreen: Color = Color("mediumspringgreen")
        public val Mediumturquoise: Color = Color("mediumturquoise")
        public val Mediumvioletred: Color = Color("mediumvioletred")
        public val Midnightblue: Color = Color("midnightblue")
        public val Mintcream: Color = Color("mintcream")
        public val Mistyrose: Color = Color("mistyrose")
        public val Moccasin: Color = Color("moccasin")
        public val Navajowhite: Color = Color("navajowhite")
        public val Navy: Color = Color("navy")
        public val Oldlace: Color = Color("oldlace")
        public val Olive: Color = Color("olive")
        public val Olivedrab: Color = Color("olivedrab")
        public val Orange: Color = Color("orange")
        public val Orangered: Color = Color("orangered")
        public val Orchid: Color = Color("orchid")
        public val Palegoldenrod: Color = Color("palegoldenrod")
        public val Palegreen: Color = Color("palegreen")
        public val Paleturquoise: Color = Color("paleturquoise")
        public val Palevioletred: Color = Color("palevioletred")
        public val Papayawhip: Color = Color("papayawhip")
        public val Peachpuff: Color = Color("peachpuff")
        public val Peru: Color = Color("peru")
        public val Pink: Color = Color("pink")
        public val Plum: Color = Color("plum")
        public val Powderblue: Color = Color("powderblue")
        public val Purple: Color = Color("purple")
        public val Rebeccapurple: Color = Color("rebeccapurple")
        public val Red: Color = Color("red")
        public val Rosybrown: Color = Color("rosybrown")
        public val Royalblue: Color = Color("royalblue")
        public val Saddlebrown: Color = Color("saddlebrown")
        public val Salmon: Color = Color("salmon")
        public val Sandybrown: Color = Color("sandybrown")
        public val Seagreen: Color = Color("seagreen")
        public val Seashell: Color = Color("seashell")
        public val Sienna: Color = Color("sienna")
        public val Silver: Color = Color("silver")
        public val Skyblue: Color = Color("skyblue")
        public val Slateblue: Color = Color("slateblue")
        public val Slategray: Color = Color("slategray")
        public val Snow: Color = Color("snow")
        public val Springgreen: Color = Color("springgreen")
        public val Steelblue: Color = Color("steelblue")
        public val Tan: Color = Color("tan")
        public val Teal: Color = Color("teal")
        public val Thistle: Color = Color("thistle")
        public val Tomato: Color = Color("tomato")
        public val Turquoise: Color = Color("turquoise")
        public val Violet: Color = Color("violet")
        public val Wheat: Color = Color("wheat")
        public val White: Color = Color("white")
        public val Whitesmoke: Color = Color("whitesmoke")
        public val Yellow: Color = Color("yellow")
        public val Yellowgreen: Color = Color("yellowgreen")
    }
}

/**
 * Type-safe definition of CSS background.
 * @param color color of the background
 * @param image background image
 * @param positionX horizontal position of the background image
 * @param positionY vertical position of the background image
 * @param sizeX horizontal size of the background image
 * @param sizeY vertical size of the background image
 * @param size resize of the background image
 * @param repeat repeat option of the background image
 * @param origin origin option of the background image
 * @param clip clipping option of the background image
 * @param attachment attachment option of the background image
 */
@Stable
public open class Background(
    protected val color: Color? = null, protected val image: String? = null,
    protected val positionX: CssSize? = null, protected val positionY: CssSize? = null,
    protected val sizeX: CssSize? = null, protected val sizeY: CssSize? = null,
    protected val size: BgSize? = null, protected val repeat: BgRepeat? = null,
    protected val origin: BgOrigin? = null, protected val clip: BgClip? = null,
    protected val attachment: BgAttach? = null
) {

    public val value: String = asString()

    protected fun asString(): String {
        val img = image?.let {
            "url($image)"
        }
        val posX = positionX?.toString()
        val posY = positionY?.toString()
        val sX = sizeX?.toString()
        val sY = sizeY?.toString()
        return listOfNotNull(
            color?.toString(), img, posX, posY,
            if (sX != null || sY != null || size != null) {
                (if (posX != null || posY != null) " / " else " 0px 0px / ") +
                        sX.orEmpty() + " " + sY.orEmpty() + " " + (size?.value).orEmpty()
            } else {
                null
            }, repeat?.toString(), origin?.toString(), clip?.toString(), attachment?.toString()
        ).joinToString(" ")
    }

    override fun toString(): String = value
    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

/**
 * Type-safe definition of CSS text decoration.
 * @param line text decoration line
 * @param style text decoration style
 * @param color text decoration color
 */
@Stable
public open class TextDecoration(
    protected val line: TextDecorationLine? = null, protected val style: TextDecorationStyle? = null,
    protected val color: Color? = null
) {

    public val value: String = listOfNotNull(line?.toString(), style?.toString(), color?.toString()).joinToString(" ")

    override fun toString(): String = value
    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

/**
 * Type-safe definition of CSS text shadow.
 * @param hShadow the position of the horizontal shadow
 * @param vShadow the position of the vertical shadow
 * @param blurRadius the blur radius
 * @param color color of the shadow
 */
@Stable
public open class TextShadow(
    protected val hShadow: CssSize? = null, protected val vShadow: CssSize? = null,
    protected val blurRadius: CssSize? = null, protected val color: Color? = null
) {

    public val value: String =
        listOfNotNull(hShadow?.toString(), vShadow?.toString(), blurRadius?.toString(), color?.toString())
            .joinToString(" ")

    override fun toString(): String = value
    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

/**
 * Type-safe definition of CSS box shadow.
 * @param hOffset the position of the horizontal shadow
 * @param vOffset the position of the vertical shadow
 * @param blurRadius the blur radius
 * @param spreadRadius the spread radius
 * @param color color of the shadow
 * @param inset changes the shadow from an outer shadow (outset) to an inner shadow
 */
@Stable
public open class BoxShadow(
    protected val hOffset: CssSize? = null, protected val vOffset: CssSize? = null,
    protected val blurRadius: CssSize? = null, protected val spreadRadius: CssSize? = null,
    protected val color: Color? = null, protected val inset: Boolean = false
) {

    public val value: String = listOfNotNull(
        if (inset) "inset" else null, hOffset?.toString(), vOffset?.toString(),
        blurRadius?.toString(), spreadRadius?.toString(), color?.toString()
    ).joinToString(" ")

    override fun toString(): String = value
    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

/**
 * Type-safe definition of CSS transition.
 * @param property the name of the property
 * @param duration the duration of the transition effect (in seconds)
 * @param timingFunction the timing function of the transition effect
 * @param delay the delay of the transition effect (in seconds)
 */
@Stable
public open class Transition(
    protected val property: String,
    protected val duration: Double,
    protected val timingFunction: String? = null,
    protected val delay: Double? = null
) {
    public val value: String = listOfNotNull(property, "${duration}s", timingFunction, delay?.let { "${it}s" })
        .joinToString(" ")

    override fun toString(): String = value
    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

/**
 * Type-safe definition of CSS list style.
 * @param type list-item marker type
 * @param position list-item marker position
 * @param image list-item marker image
 */
@Stable
public open class ListStyle(
    protected val type: ListStyleType? = null,
    protected val position: ListStylePosition? = null,
    protected val image: String? = null,
) {

    public val value: String = listOfNotNull(type?.toString(), position?.toString(), image?.let { "url($image)" })
        .joinToString(" ")

    override fun toString(): String = value
    override fun equals(other: Any?): Boolean {
        return this.toString() == other.toString()
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
