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
public enum class BorderStyle(public val borderStyle: String) {
    None("none"),
    Hidden("hidden"),
    Dotted("dotted"),
    Dashed("dashed"),
    Solid("solid"),
    Double("double"),
    Groove("groove"),
    Ridge("ridge"),
    Inset("inset"),
    Outset("outset"),
    Initial("initial"),
    Inherit("inherit"),
    Unset("unset")
}

/**
 * Definitions of CSS outline styles.
 */
public enum class OutlineStyle(public val outlineStyle: String) {
    Auto("auto"),
    None("none"),
    Hidden("hidden"),
    Dotted("dotted"),
    Dashed("dashed"),
    Solid("solid"),
    Double("double"),
    Groove("groove"),
    Ridge("ridge"),
    Inset("inset"),
    Outset("outset"),
    Initial("initial"),
    Inherit("inherit"),
    Unset("unset")
}

/**
 * Definitions of CSS color names.
 */
public enum class Col(public val color: String) {
    Aliceblue("aliceblue"),
    Antiquewhite("antiquewhite"),
    Aqua("aqua"),
    Aquamarine("aquamarine"),
    Azure("azure"),
    Beige("beige"),
    Bisque("bisque"),
    Black("black"),
    Blanchedalmond("blanchedalmond"),
    Blue("blue"),
    Blueviolet("blueviolet"),
    Brown("brown"),
    Burlywood("burlywood"),
    Cadetblue("cadetblue"),
    Chartreuse("chartreuse"),
    Chocolate("chocolate"),
    Coral("coral"),
    Cornflowerblue("cornflowerblue"),
    Cornsilk("cornsilk"),
    Crimson("crimson"),
    Cyan("cyan"),
    Darkblue("darkblue"),
    Darkcyan("darkcyan"),
    Darkgoldenrod("darkgoldenrod"),
    Darkgray("darkgray"),
    Darkgreen("darkgreen"),
    Darkkhaki("darkkhaki"),
    Darkmagenta("darkmagenta"),
    Darkolivegreen("darkolivegreen"),
    Darkorange("darkorange"),
    Darkorchid("darkorchid"),
    Darkred("darkred"),
    Darksalmon("darksalmon"),
    Darkseagreen("darkseagreen"),
    Darkslateblue("darkslateblue"),
    Darkslategray("darkslategray"),
    Darkturquoise("darkturquoise"),
    Darkviolet("darkviolet"),
    Deeppink("deeppink"),
    Deepskyblue("deepskyblue"),
    Dimgray("dimgray"),
    Dodgerblue("dodgerblue"),
    Firebrick("firebrick"),
    Floralwhite("floralwhite"),
    Forestgreen("forestgreen"),
    Fuchsia("fuchsia"),
    Gainsboro("gainsboro"),
    Ghostwhite("ghostwhite"),
    Gold("gold"),
    Goldenrod("goldenrod"),
    Gray("gray"),
    Green("green"),
    Greenyellow("greenyellow"),
    Honeydew("honeydew"),
    Hotpink("hotpink"),
    Indianred("indianred"),
    Indigo("indigo"),
    Ivory("ivory"),
    Khaki("khaki"),
    Lavender("lavender"),
    Lavenderblush("lavenderblush"),
    Lawngreen("lawngreen"),
    Lemonchiffon("lemonchiffon"),
    Lightblue("lightblue"),
    Lightcoral("lightcoral"),
    Lightcyan("lightcyan"),
    Lightgoldenrodyellow("lightgoldenrodyellow"),
    Lightgray("lightgray"),
    Lightgreen("lightgreen"),
    Lightpink("lightpink"),
    Lightsalmon("lightsalmon"),
    Lightseagreen("lightseagreen"),
    Lightskyblue("lightskyblue"),
    Lightslategray("lightslategray"),
    Lightsteelblue("lightsteelblue"),
    Lightyellow("lightyellow"),
    Lime("lime"),
    Limegreen("limegreen"),
    Linen("linen"),
    Magenta("magenta"),
    Maroon("maroon"),
    Mediumaquamarine("mediumaquamarine"),
    Mediumblue("mediumblue"),
    Mediumorchid("mediumorchid"),
    Mediumpurple("mediumpurple"),
    Mediumseagreen("mediumseagreen"),
    Mediumslateblue("mediumslateblue"),
    Mediumspringgreen("mediumspringgreen"),
    Mediumturquoise("mediumturquoise"),
    Mediumvioletred("mediumvioletred"),
    Midnightblue("midnightblue"),
    Mintcream("mintcream"),
    Mistyrose("mistyrose"),
    Moccasin("moccasin"),
    Navajowhite("navajowhite"),
    Navy("navy"),
    Oldlace("oldlace"),
    Olive("olive"),
    Olivedrab("olivedrab"),
    Orange("orange"),
    Orangered("orangered"),
    Orchid("orchid"),
    Palegoldenrod("palegoldenrod"),
    Palegreen("palegreen"),
    Paleturquoise("paleturquoise"),
    Palevioletred("palevioletred"),
    Papayawhip("papayawhip"),
    Peachpuff("peachpuff"),
    Peru("peru"),
    Pink("pink"),
    Plum("plum"),
    Powderblue("powderblue"),
    Purple("purple"),
    Rebeccapurple("rebeccapurple"),
    Red("red"),
    Rosybrown("rosybrown"),
    Royalblue("royalblue"),
    Saddlebrown("saddlebrown"),
    Salmon("salmon"),
    Sandybrown("sandybrown"),
    Seagreen("seagreen"),
    Seashell("seashell"),
    Sienna("sienna"),
    Silver("silver"),
    Skyblue("skyblue"),
    Slateblue("slateblue"),
    Slategray("slategray"),
    Snow("snow"),
    Springgreen("springgreen"),
    Steelblue("steelblue"),
    Tan("tan"),
    Teal("teal"),
    Thistle("thistle"),
    Tomato("tomato"),
    Turquoise("turquoise"),
    Violet("violet"),
    Wheat("wheat"),
    White("white"),
    Whitesmoke("whitesmoke"),
    Yellow("yellow"),
    Yellowgreen("yellowgreen")
}

/**
 * Definitions of CSS background size.
 */
public enum class BgSize(public val size: String) {
    Cover("cover"),
    Contain("contain")
}

/**
 * Definitions of CSS background repeat options.
 */
public enum class BgRepeat(public val repeat: String) {
    Repeat("repeat"),
    RepeatX("repeat-x"),
    RepeatY("repeat-y"),
    NoRepeat("no-repeat")
}

/**
 * Definitions of CSS background attachment options.
 */
public enum class BgAttach(public val attachment: String) {
    Scroll("scroll"),
    Fixed("fixed"),
    Local("local")
}

/**
 * Definitions of CSS background origin options.
 */
public enum class BgOrigin(public val origin: String) {
    PaddingBox("padding-box"),
    BorderBox("border-box"),
    ContentBox("content-box")
}

/**
 * Definitions of CSS background clipping options.
 */
public enum class BgClip(public val clip: String) {
    PaddingBox("padding-box"),
    BorderBox("border-box"),
    ContentBox("content-box")
}

/**
 * Definitions of CSS display options.
 */
public enum class Display(public val display: String) {
    Inline("inline"),
    Block("block"),
    Flex("flex"),
    Grid("grid"),
    InlineBlock("inline-block"),
    InlineFlex("inline-flex"),
    InlineGrid("inline-grid"),
    InlineTable("inline-table"),
    ListItem("list-item"),
    RunIn("run-in"),
    Table("table"),
    TableCaption("table-caption"),
    TableColumnGroup("table-column-group"),
    TableHeaderGroup("table-header-group"),
    TableFooterGroup("table-footer-group"),
    TableRowGroup("table-row-group"),
    TableCell("table-cell"),
    TableColumn("table-column"),
    TableTow("table-row"),
    Contents("contents"),
    None("none"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS position options.
 */
public enum class Position(public val position: String) {
    Static("static"),
    Relative("relative"),
    Fixed("fixed"),
    Absolute("absolute"),
    Sticky("sticky")
}

/**
 * Definitions of CSS overflow options.
 */
public enum class Overflow(public val overflow: String) {
    Visible("visible"),
    Hidden("hidden"),
    Scroll("scroll"),
    Auto("auto"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS overflow-wrap options.
 */
public enum class OverflowWrap(public val overflowWrap: String) {
    Normal("normal"),
    BreakWord("break-word"),
    Anywhere("anywhere")
}

/**
 * Definitions of CSS resize options.
 */
public enum class Resize(public val resize: String) {
    None("none"),
    Both("both"),
    Horizontal("horizontal"),
    Vertical("vertical"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS text direction options.
 */
public enum class Direction(public val direction: String) {
    Ltr("ltr"),
    Rtl("rtl"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS text align options.
 */
public enum class TextAlign(public val textAlign: String) {
    Left("left"),
    Right("right"),
    Center("center"),
    Justify("justify"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS text decoration line options.
 */
public enum class TextDecorationLine(public val textDecorationLine: String) {
    None("none"),
    Underline("underline"),
    Overline("overline"),
    LineThrough("line-through"),
    Justify("justify"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS text decoration style options.
 */
public enum class TextDecorationStyle(public val textDecorationStyle: String) {
    Solid("solid"),
    Double("double"),
    Dotted("dotted"),
    Dashed("dashed"),
    Wavy("wavy"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS text transform options.
 */
public enum class TextTransform(public val textTransform: String) {
    None("none"),
    Capitalize("capitalize"),
    Uppercase("uppercase"),
    Lowercase("lowercase"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS text overflow options.
 */
public enum class TextOverflow(public val textOverflow: String) {
    Clip("clip"),
    Ellipsis("ellipsis"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS unicode-bidi options.
 */
public enum class UnicodeBidi(public val unicodeBidi: String) {
    Normal("normal"),
    Embed("embed"),
    BidiOverride("bidi-override"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS vertical align options.
 */
public enum class VerticalAlign(public val verticalAlign: String) {
    Baseline("baseline"),
    Sub("sub"),
    Super("super"),
    Top("top"),
    TextTop("text-top"),
    Middle("middle"),
    Bottom("bottom"),
    TextBottom("text-bottom"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS white space options.
 */
public enum class WhiteSpace(public val whiteSpace: String) {
    Normal("normal"),
    Nowrap("nowrap"),
    Pre("pre"),
    PreLine("pre-line"),
    PreWrap("pre-wrap"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS font style options.
 */
public enum class FontStyle(public val fontStyle: String) {
    Normal("normal"),
    Italic("italic"),
    Oblique("oblique"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS font variant options.
 */
public enum class FontVariant(public val fontVariant: String) {
    Normal("normal"),
    SmallCaps("small-caps"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS font weight options.
 */
public enum class FontWeight(public val fontWeight: String) {
    Normal("normal"),
    Bold("bold"),
    Bolder("bolder"),
    Lighter("lighter"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS float options.
 */
public enum class CssFloat(public val cssFloat: String) {
    None("none"),
    Left("left"),
    Right("right"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS clear options.
 */
public enum class Clear(public val clear: String) {
    None("none"),
    Left("left"),
    Right("right"),
    Both("both"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * Definitions of CSS word-break options.
 */
public enum class WordBreak(public val wordBreak: String) {
    Normal("normal"),
    KeepAll("keep-all"),
    BreakAll("break-all")
}

/**
 * Definitions of CSS line-break options.
 */
public enum class LineBreak(public val lineBreak: String) {
    Auto("auto"),
    Loose("loose"),
    Normal("normal"),
    Strict("strict"),
    Anywhere("anywhere")
}

public enum class Cursor(public val cursor: String) {
    Default("default"),
    Auto("auto"),
    None("none"),
    Alias("alias"),
    AllScroll("all-scroll"),
    Cell("cell"),
    ContextMenu("context-menu"),
    ColResize("col-resize"),
    Copy("copy"),
    Crosshair("crosshair"),
    EResize("e-resize"),
    EwResize("ew-resize"),
    Grab("grab"),
    Grabbing("grabbing"),
    Help("help"),
    Move("move"),
    NResize("n-resize"),
    NeResize("ne-resize"),
    NeswResize("nesw-resize"),
    NsResize("ns-resize"),
    NwResize("nw-resize"),
    NwseResize("nwse-resize"),
    NoDrop("no-drop"),
    NotAllowed("not-allowed"),
    Pointer("pointer"),
    Progress("progress"),
    RowResize("row-resize"),
    SResize("s-resize"),
    SeResize("se-resize"),
    SwResize("sw-resize"),
    Text("text"),
    VerticalText("vertical-text"),
    WResize("w-resize"),
    Wait("wait"),
    ZoomIn("zoom-in"),
    ZoomOut("zoom-out"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * CSS flexbox directions.
 */
public enum class FlexDirection(public val flexDirection: String) {
    Row("row"),
    RowReverse("row-reverse"),
    Column("column"),
    ColumnReverse("column-reverse")
}

/**
 * CSS flexbox wrap modes.
 */
public enum class FlexWrap(public val flexWrap: String) {
    Nowrap("nowrap"),
    Wrap("wrap"),
    WrapReverse("wrap-reverse")
}

/**
 * CSS grid items justification options.
 */
public enum class JustifyItems(public val justifyItems: String) {
    Start("start"),
    End("end"),
    Center("center"),
    Stretch("stretch")
}

/**
 * CSS flexbox/grid content justification options.
 */
public enum class JustifyContent(public val justifyContent: String) {
    FlexStart("flex-start"),
    FlexEnd("flex-end"),
    Center("center"),
    SpaceBetween("space-between"),
    SpaceAround("space-around"),
    SpaceEvenly("space-evenly"),
    Start("start"),
    End("end"),
    Stretch("stretch")
}

/**
 * CSS flexbox/grid items alignments options.
 */
public enum class AlignItems(public val alignItems: String) {
    FlexStart("flex-start"),
    FlexEnd("flex-end"),
    Center("center"),
    Baseline("baseline"),
    Stretch("stretch"),
    Start("start"),
    End("end")
}

/**
 * CSS flexbox/grid content alignment options.
 */
public enum class AlignContent(public val alignContent: String) {
    FlexStart("flex-start"),
    FlexEnd("flex-end"),
    Center("center"),
    SpaceBetween("space-between"),
    SpaceAround("space-around"),
    Stretch("stretch"),
    Start("start"),
    End("end"),
    SpaceEvenly("space-evenly")
}

/**
 * CSS grid flow options.
 */
public enum class GridAutoFlow(public val gridAutoFlow: String) {
    Row("row"),
    Column("column"),
    RowDense("row dense"),
    ColumnDense("column dense")
}

/**
 * CSS list style type options.
 */
public enum class ListStyleType(public val type: String) {
    Disc("disc"),
    Circle("circle"),
    Square("square"),
    Decimal("decimal"),
    CjkDecimal("cjk-decimal"),
    DecimalLeadingZero("decimal-leading-zero"),
    LowerRoman("lower-roman"),
    UpperRoman("upper-roman"),
    LowerGreek("lower-greek"),
    LowerLatin("lower-latin"),
    UpperAlpha("upper-alpha"),
    LowerAlpha("lower-alpha"),
    UpperLatin("upper-latin"),
    ArabicIndic("arabic-indic"),
    Armenian("armenian"),
    Bengali("bengali"),
    Cambodian("cambodian"),
    CjkIdeographic("cjk-ideographic"),
    Georgian("georgian"),
    Hebrew("hebrew"),
    Hiragana("hiragana"),
    HiraganaIroha("hiragana-iroha"),
    JapaneseFormal("japanese-formal"),
    JapaneseInformal("japanese-informal"),
    Katakana("katakana"),
    KatakanaIroha("katakana-iroha"),
    None("none"),
    Initial("initial"),
    Inherit("inherit")
}

/**
 * CSS list style position options.
 */
public enum class ListStylePosition(public val position: String) {
    Inside("inside"),
    Outside("outside")
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
        return w.orEmpty() + " " + (style?.borderStyle).orEmpty() + " " + color?.asString().orEmpty()
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
        return w.orEmpty() + " " + (style?.outlineStyle).orEmpty() + " " + color?.asString().orEmpty()
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
            return Color(color.color)
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
                            sX.orEmpty() + " " + sY.orEmpty() + " " + (size?.size).orEmpty()
                } else {
                    ""
                } + " " + (repeat?.repeat).orEmpty() + " " + (origin?.origin).orEmpty() + " " +
                (clip?.clip).orEmpty() + " " + (attachment?.attachment).orEmpty()).trim()
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
        return ((line?.textDecorationLine).orEmpty() + " " +
                (style?.textDecorationStyle).orEmpty() + " " +
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
        return ("${type?.type.orEmpty()} ${position?.position.orEmpty()} ${img.orEmpty()}").trim()
    }

    override fun toString(): String = asString()
}
