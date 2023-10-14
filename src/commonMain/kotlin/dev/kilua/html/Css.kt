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

package dev.kilua.html

import dev.kilua.utils.asString
import dev.kilua.utils.toKebabCase

/**
 * Definitions of CSS units.
 */
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
    auto("auto"),
    normal("normal")
}

/**
 * This type is used for defining CSS dimensions (width, heights, margins, paddings, etc.).
 */
public typealias CssSize = Pair<Number, CssUnit>

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
    override fun toString(): String {
        return value
    }
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
    override fun toString(): String {
        return value
    }

}

/**
 * Definitions of CSS color names.
 */
public enum class Col {
    Aliceblue,
    Antiquewhite,
    Aqua,
    Aquamarine,
    Azure,
    Beige,
    Bisque,
    Black,
    Blanchedalmond,
    Blue,
    Blueviolet,
    Brown,
    Burlywood,
    Cadetblue,
    Chartreuse,
    Chocolate,
    Coral,
    Cornflowerblue,
    Cornsilk,
    Crimson,
    Cyan,
    Darkblue,
    Darkcyan,
    Darkgoldenrod,
    Darkgray,
    Darkgreen,
    Darkkhaki,
    Darkmagenta,
    Darkolivegreen,
    Darkorange,
    Darkorchid,
    Darkred,
    Darksalmon,
    Darkseagreen,
    Darkslateblue,
    Darkslategray,
    Darkturquoise,
    Darkviolet,
    Deeppink,
    Deepskyblue,
    Dimgray,
    Dodgerblue,
    Firebrick,
    Floralwhite,
    Forestgreen,
    Fuchsia,
    Gainsboro,
    Ghostwhite,
    Gold,
    Goldenrod,
    Gray,
    Green,
    Greenyellow,
    Honeydew,
    Hotpink,
    Indianred,
    Indigo,
    Ivory,
    Khaki,
    Lavender,
    Lavenderblush,
    Lawngreen,
    Lemonchiffon,
    Lightblue,
    Lightcoral,
    Lightcyan,
    Lightgoldenrodyellow,
    Lightgray,
    Lightgreen,
    Lightpink,
    Lightsalmon,
    Lightseagreen,
    Lightskyblue,
    Lightslategray,
    Lightsteelblue,
    Lightyellow,
    Lime,
    Limegreen,
    Linen,
    Magenta,
    Maroon,
    Mediumaquamarine,
    Mediumblue,
    Mediumorchid,
    Mediumpurple,
    Mediumseagreen,
    Mediumslateblue,
    Mediumspringgreen,
    Mediumturquoise,
    Mediumvioletred,
    Midnightblue,
    Mintcream,
    Mistyrose,
    Moccasin,
    Navajowhite,
    Navy,
    Oldlace,
    Olive,
    Olivedrab,
    Orange,
    Orangered,
    Orchid,
    Palegoldenrod,
    Palegreen,
    Paleturquoise,
    Palevioletred,
    Papayawhip,
    Peachpuff,
    Peru,
    Pink,
    Plum,
    Powderblue,
    Purple,
    Rebeccapurple,
    Red,
    Rosybrown,
    Royalblue,
    Saddlebrown,
    Salmon,
    Sandybrown,
    Seagreen,
    Seashell,
    Sienna,
    Silver,
    Skyblue,
    Slateblue,
    Slategray,
    Snow,
    Springgreen,
    Steelblue,
    Tan,
    Teal,
    Thistle,
    Tomato,
    Turquoise,
    Violet,
    Wheat,
    White,
    Whitesmoke,
    Yellow,
    Yellowgreen;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

}

/**
 * Definitions of CSS background size.
 */
public enum class BgSize {
    Cover,
    Contain;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

}

/**
 * Definitions of CSS background attachment options.
 */
public enum class BgAttach {
    Scroll,
    Fixed,
    Local;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

}

/**
 * Definitions of CSS background origin options.
 */
public enum class BgOrigin {
    PaddingBox,
    BorderBox,
    ContentBox;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

}

/**
 * Definitions of CSS background clipping options.
 */
public enum class BgClip {
    PaddingBox,
    BorderBox,
    ContentBox;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

}

/**
 * Definitions of CSS overflow-wrap options.
 */
public enum class OverflowWrap {
    Normal,
    BreakWord,
    Anywhere;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

}

/**
 * Definitions of CSS word-break options.
 */
public enum class WordBreak {
    Normal,
    KeepAll,
    BreakAll;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

}

/**
 * CSS flexbox wrap modes.
 */
public enum class FlexWrap {
    Nowrap,
    Wrap,
    WrapReverse;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

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

    override fun toString(): String {
        return value
    }

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
    override fun toString(): String {
        return value
    }

}

/**
 * CSS list style position options.
 */
public enum class ListStylePosition {
    Inside,
    Outside;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }

}

/**
 * Type-safe definition of CSS border.
 * @param width width of the border
 * @param style style of the border
 * @param color color of the border
 */
public open class Border(
    protected val width: CssSize? = null, protected val style: BorderStyle? = null,
    protected val color: Color? = null
) {

    public fun asString(): String {
        val w = width?.asString()
        return w.orEmpty() + " " + (style?.value).orEmpty() + " " + color?.asString().orEmpty()
    }

    override fun toString(): String = asString()
}

/**
 * Type-safe definition of CSS outline.
 * @param width width of the outline
 * @param style style of the outline
 * @param color color of the outline
 */
public open class Outline(
    protected val width: CssSize? = null, protected val style: OutlineStyle? = null,
    protected val color: Color? = null
) {

    public fun asString(): String {
        val w = width?.asString()
        return w.orEmpty() + " " + (style?.value).orEmpty() + " " + color?.asString().orEmpty()
    }

    override fun toString(): String = asString()
}

/**
 * Type-safe definition of CSS color.
 * @param color CSS color string
 */
public open class Color(protected val color: String? = null) {

    public fun asString(): String {
        return color.orEmpty()
    }

    override fun toString(): String = asString()

    public companion object {
        /**
         * Creates CSS Color with color given in hex format.
         * @param color color in hex format
         */
        @OptIn(ExperimentalStdlibApi::class)
        public fun hex(color: Int): Color {
            return Color("#" + color.toHexString().takeLast(6))
        }

        /**
         * Creates CSS Color with color given with named constant.
         * @param color color named constant
         */
        public fun name(color: Col): Color {
            return Color(color.value)
        }

        /**
         * Creates CSS Color with red, green and blue components
         */
        public fun rgb(red: Int, green: Int, blue: Int): Color {
            return Color("#" + listOf(red, green, blue).joinToString("") { it.toString(16).padStart(2, '0') })
        }

        /**
         * Creates CSS Color with red, green, blue and alpha channel components
         */
        public fun rgba(red: Int, green: Int, blue: Int, alpha: Int): Color {
            return Color("#" + listOf(red, green, blue, alpha).joinToString("") { it.toString(16).padStart(2, '0') })
        }
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
public open class Background(
    protected val color: Color? = null, protected val image: String? = null,
    protected val positionX: CssSize? = null, protected val positionY: CssSize? = null,
    protected val sizeX: CssSize? = null, protected val sizeY: CssSize? = null,
    protected val size: BgSize? = null, protected val repeat: BgRepeat? = null,
    protected val origin: BgOrigin? = null, protected val clip: BgClip? = null,
    protected val attachment: BgAttach? = null
) {

    public fun asString(): String {
        val img = image?.let {
            "url($image)"
        }
        val posX = positionX?.asString()
        val posY = positionY?.asString()
        val sX = sizeX?.asString()
        val sY = sizeY?.asString()
        return (color?.asString().orEmpty() + " " + img.orEmpty() + " " + posX.orEmpty() + " " + posY.orEmpty() +
                if (sX != null || sY != null || size != null) {
                    (if (posX != null || posY != null) " / " else " 0px 0px / ") +
                            sX.orEmpty() + " " + sY.orEmpty() + " " + (size?.value).orEmpty()
                } else {
                    ""
                } + " " + (repeat?.value).orEmpty() + " " + (origin?.value).orEmpty() + " " +
                (clip?.value).orEmpty() + " " + (attachment?.value).orEmpty()).trim()
    }

    override fun toString(): String = asString()
}

/**
 * Type-safe definition of CSS text decoration.
 * @param line text decoration line
 * @param style text decoration style
 * @param color text decoration color
 */
public open class TextDecoration(
    protected val line: TextDecorationLine? = null, protected val style: TextDecorationStyle? = null,
    protected val color: Color? = null
) {

    public fun asString(): String {
        return ((line?.value).orEmpty() + " " +
                (style?.value).orEmpty() + " " +
                color?.asString().orEmpty()).trim()
    }

    override fun toString(): String = asString()
}

/**
 * Type-safe definition of CSS text shadow.
 * @param hShadow the position of the horizontal shadow
 * @param vShadow the position of the vertical shadow
 * @param blurRadius the blur radius
 * @param color color of the shadow
 */
public open class TextShadow(
    protected val hShadow: CssSize? = null, protected val vShadow: CssSize? = null,
    protected val blurRadius: CssSize? = null, protected val color: Color? = null
) {

    public fun asString(): String {
        return ((hShadow?.asString()).orEmpty() + " " +
                (vShadow?.asString()).orEmpty() + " " +
                (blurRadius?.asString()).orEmpty() + " " +
                color?.asString().orEmpty()).trim()
    }

    override fun toString(): String = asString()
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
public open class BoxShadow(
    protected val hOffset: CssSize? = null, protected val vOffset: CssSize? = null,
    protected val blurRadius: CssSize? = null, protected val spreadRadius: CssSize? = null,
    protected val color: Color? = null, protected val inset: Boolean = false
) {

    public fun asString(): String {
        return (if (inset) "inset " else "" + (hOffset?.asString()).orEmpty() + " " +
                (vOffset?.asString()).orEmpty() + " " +
                (blurRadius?.asString()).orEmpty() + " " +
                (spreadRadius?.asString()).orEmpty() + " " +
                color?.asString().orEmpty()).trim()
    }

    override fun toString(): String = asString()
}

/**
 * Type-safe definition of CSS transition.
 * @param property the name of the property
 * @param duration the duration of the transition effect (in seconds)
 * @param timingFunction the timing function of the transition effect
 * @param delay the delay of the transition effect (in seconds)
 */
public open class Transition(
    protected val property: String,
    protected val duration: Double,
    protected val timingFunction: String? = null,
    protected val delay: Double? = null
) {
    public fun asString(): String {
        return ("$property ${duration}s ${timingFunction ?: ""} ${delay?.let { it.toString() + "s" } ?: ""}").trim()
    }

    override fun toString(): String = asString()
}

/**
 * Type-safe definition of CSS list style.
 * @param type list-item marker type
 * @param position list-item marker position
 * @param image list-item marker image
 */
public open class ListStyle(
    protected val type: ListStyleType? = null,
    protected val position: ListStylePosition? = null,
    protected val image: String? = null,
) {

    public fun asString(): String {
        val img = image?.let {
            "url($image)"
        }
        return ("${type?.value.orEmpty()} ${position?.value.orEmpty()} ${img.orEmpty()}").trim()
    }

    override fun toString(): String = asString()
}
