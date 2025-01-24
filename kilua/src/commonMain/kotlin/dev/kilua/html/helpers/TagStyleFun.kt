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
@file:Suppress("WildcardImport")

package dev.kilua.html.helpers

import androidx.compose.runtime.Composable
import dev.kilua.html.*

/**
 * Common tag immutable CSS styles.
 */
public interface TagStyleFun {

    /**
     * Width of the current component.
     */
    public val width: CssSize?

    /**
     * Set the width of the current component.
     */
    @Composable
    public fun width(width: CssSize?)

    /**
     * Minimal width of the current component.
     */
    public val minWidth: CssSize?

    /**
     * Set the minimal width of the current component.
     */
    @Composable
    public fun minWidth(minWidth: CssSize?)

    /**
     * Maximal width of the current component.
     */
    public val maxWidth: CssSize?

    /**
     * Set the maximal width of the current component.
     */
    @Composable
    public fun maxWidth(maxWidth: CssSize?)

    /**
     * Height of the current component.
     */
    public val height: CssSize?

    /**
     * Set the height of the current component.
     */
    @Composable
    public fun height(height: CssSize?)

    /**
     * Minimal height of the current component.
     */
    public val minHeight: CssSize?

    /**
     * Set the minimal height of the current component.
     */
    @Composable
    public fun minHeight(minHeight: CssSize?)

    /**
     * Maximal height of the current component.
     */
    public val maxHeight: CssSize?

    /**
     * Set the maximal height of the current component.
     */
    @Composable
    public fun maxHeight(maxHeight: CssSize?)

    /**
     * CSS display of the current component.
     */
    public val display: Display?

    /**
     * Set the CSS display of the current component.
     */
    @Composable
    public fun display(display: Display?)

    /**
     * CSS visibility of the current component.
     */
    public val visibility: Visibility?

    /**
     * Set the CSS visibility of the current component.
     */
    @Composable
    public fun visibility(visibility: Visibility?)

    /**
     * CSS position of the current component.
     */
    public val position: Position?

    /**
     * Set the CSS position of the current component.
     */
    @Composable
    public fun position(position: Position?)

    /**
     * Top edge of the current component.
     */
    public val top: CssSize?

    /**
     * Set the top edge of the current component.
     */
    @Composable
    public fun top(top: CssSize?)

    /**
     * Left edge of the current component.
     */
    public val left: CssSize?

    /**
     * Set the left edge of the current component.
     */
    @Composable
    public fun left(left: CssSize?)

    /**
     * Right edge of the current component.
     */
    public val right: CssSize?

    /**
     * Set the right edge of the current component.
     */
    @Composable
    public fun right(right: CssSize?)

    /**
     * Bottom edge of the current component.
     */
    public val bottom: CssSize?

    /**
     * Set the bottom edge of the current component.
     */
    @Composable
    public fun bottom(bottom: CssSize?)

    /**
     * Z-index of the current component.
     */
    public val zIndex: Int?

    /**
     * Set the Z-index of the current component.
     */
    @Composable
    public fun zIndex(zIndex: Int?)

    /**
     * CSS overflow of the current component.
     */
    public val overflow: Overflow?

    /**
     * Set the CSS overflow of the current component.
     */
    @Composable
    public fun overflow(overflow: Overflow?)

    /**
     * CSS overflow-x of the current component.
     */
    public val overflowX: Overflow?

    /**
     * Set the CSS overflow-x of the current component.
     */
    @Composable
    public fun overflowX(overflowX: Overflow?)

    /**
     * CSS overflow-y of the current component.
     */
    public val overflowY: Overflow?

    /**
     * Set the CSS overflow-y of the current component.
     */
    @Composable
    public fun overflowY(overflowY: Overflow?)

    /**
     * CSS overflow-wrap of the current component.
     */
    public val overflowWrap: OverflowWrap?

    /**
     * Set the CSS overflow-wrap of the current component.
     */
    @Composable
    public fun overflowWrap(overflowWrap: OverflowWrap?)

    /**
     * CSS resize of the current component.
     */
    public val resize: Resize?

    /**
     * Set the CSS resize of the current component.
     */
    @Composable
    public fun resize(resize: Resize?)

    /**
     * Border of the current component.
     */
    public val border: Border?

    /**
     * Set the border of the current component.
     */
    @Composable
    public fun border(border: Border?)

    /**
     * Top border of the current component.
     */
    public val borderTop: Border?

    /**
     * Set the top border of the current component.
     */
    @Composable
    public fun borderTop(borderTop: Border?)

    /**
     * Right border of the current component.
     */
    public val borderRight: Border?

    /**
     * Set the right border of the current component.
     */
    @Composable
    public fun borderRight(borderRight: Border?)

    /**
     * Bottom border of the current component.
     */
    public val borderBottom: Border?

    /**
     * Set the bottom border of the current component.
     */
    @Composable
    public fun borderBottom(borderBottom: Border?)

    /**
     * Left border of the current component.
     */
    public val borderLeft: Border?

    /**
     * Set the left border of the current component.
     */
    @Composable
    public fun borderLeft(borderLeft: Border?)

    /**
     * Margin of the current component.
     */
    public val margin: CssSize?

    /**
     * Set the margin of the current component.
     */
    @Composable
    public fun margin(margin: CssSize?)

    /**
     * Top margin of the current component.
     */
    public val marginTop: CssSize?

    /**
     * Set the top margin of the current component.
     */
    @Composable
    public fun marginTop(marginTop: CssSize?)

    /**
     * Right margin of the current component.
     */
    public val marginRight: CssSize?

    /**
     * Set the right margin of the current component.
     */
    @Composable
    public fun marginRight(marginRight: CssSize?)

    /**
     * Bottom margin of the current component.
     */
    public val marginBottom: CssSize?

    /**
     * Set the bottom margin of the current component.
     */
    @Composable
    public fun marginBottom(marginBottom: CssSize?)

    /**
     * Left margin of the current component.
     */
    public val marginLeft: CssSize?

    /**
     * Set the left margin of the current component.
     */
    @Composable
    public fun marginLeft(marginLeft: CssSize?)

    /**
     * Padding of the current component.
     */
    public val padding: CssSize?

    /**
     * Set the padding of the current component.
     */
    @Composable
    public fun padding(padding: CssSize?)

    /**
     * Top padding of the current component.
     */
    public val paddingTop: CssSize?

    /**
     * Set the top padding of the current component.
     */
    @Composable
    public fun paddingTop(paddingTop: CssSize?)

    /**
     * Right padding of the current component.
     */
    public val paddingRight: CssSize?

    /**
     * Set the right padding of the current component.
     */
    @Composable
    public fun paddingRight(paddingRight: CssSize?)

    /**
     * Bottom padding of the current component.
     */
    public val paddingBottom: CssSize?

    /**
     * Set the bottom padding of the current component.
     */
    @Composable
    public fun paddingBottom(paddingBottom: CssSize?)

    /**
     * Left padding of the current component.
     */
    public val paddingLeft: CssSize?

    /**
     * Set the left padding of the current component.
     */
    @Composable
    public fun paddingLeft(paddingLeft: CssSize?)

    /**
     * Text color for the current component.
     */
    public val color: Color?

    /**
     * Set the text color for the current component.
     */
    @Composable
    public fun color(color: Color?)

    /**
     * Opacity of the current component.
     */
    public val opacity: Double?

    /**
     * Set the opacity of the current component.
     */
    @Composable
    public fun opacity(opacity: Double?)

    /**
     * Background of the current component.
     */
    public val background: Background?

    /**
     * Set the background of the current component.
     */
    @Composable
    public fun background(background: Background?)

    /**
     * CSS Text direction of the current component.
     */
    public val direction: Direction?

    /**
     * Set the CSS Text direction of the current component.
     */
    @Composable
    public fun direction(direction: Direction?)

    /**
     * CSS Text letter spacing of the current component.
     */
    public val letterSpacing: CssSize?

    /**
     * Set the CSS Text letter spacing of the current component.
     */
    @Composable
    public fun letterSpacing(letterSpacing: CssSize?)

    /**
     * CSS Text line height of the current component.
     */
    public val lineHeight: CssSize?

    /**
     * Set the CSS Text line height of the current component.
     */
    @Composable
    public fun lineHeight(lineHeight: CssSize?)

    /**
     * CSS Text align of the current component.
     */
    public val textAlign: TextAlign?

    /**
     * Set the CSS Text align of the current component.
     */
    @Composable
    public fun textAlign(textAlign: TextAlign?)

    /**
     * CSS Text decoration of the current component.
     */
    public val textDecoration: TextDecoration?

    /**
     * Set the CSS Text decoration of the current component.
     */
    @Composable
    public fun textDecoration(textDecoration: TextDecoration?)

    /**
     * CSS Text indent of the current component.
     */
    public val textIndent: CssSize?

    /**
     * Set the CSS Text indent of the current component.
     */
    @Composable
    public fun textIndent(textIndent: CssSize?)

    /**
     * CSS Text shadow of the current component.
     */
    public val textShadow: TextShadow?

    /**
     * Set the CSS Text shadow of the current component.
     */
    @Composable
    public fun textShadow(textShadow: TextShadow?)

    /**
     * CSS Text transform of the current component.
     */
    public val textTransform: TextTransform?

    /**
     * Set the CSS Text transform of the current component.
     */
    @Composable
    public fun textTransform(textTransform: TextTransform?)

    /**
     * CSS Text overflow of the current component.
     */
    public val textOverflow: TextOverflow?

    /**
     * Set the CSS Text overflow of the current component.
     */
    @Composable
    public fun textOverflow(textOverflow: TextOverflow?)

    /**
     * CSS Text unicode-bidi of the current component.
     */
    public val unicodeBidi: UnicodeBidi?

    /**
     * Set the CSS Text unicode-bidi of the current component.
     */
    @Composable
    public fun unicodeBidi(unicodeBidi: UnicodeBidi?)

    /**
     * CSS Text vertical align of the current component.
     */
    public val verticalAlign: VerticalAlign?

    /**
     * Set the CSS Text vertical align of the current component.
     */
    @Composable
    public fun verticalAlign(verticalAlign: VerticalAlign?)

    /**
     * CSS Text white space of the current component.
     */
    public val whiteSpace: WhiteSpace?

    /**
     * Set the CSS Text white space of the current component.
     */
    @Composable
    public fun whiteSpace(whiteSpace: WhiteSpace?)

    /**
     * CSS Text word spacing of the current component.
     */
    public val wordSpacing: CssSize?

    /**
     * Set the CSS Text word spacing of the current component.
     */
    @Composable
    public fun wordSpacing(wordSpacing: CssSize?)

    /**
     * CSS font family of the current component.
     */
    public val fontFamily: String?

    /**
     * Set the CSS font family of the current component.
     */
    @Composable
    public fun fontFamily(fontFamily: String?)

    /**
     * CSS font size of the current component.
     */
    public val fontSize: CssSize?

    /**
     * Set the CSS font size of the current component.
     */
    @Composable
    public fun fontSize(fontSize: CssSize?)

    /**
     * CSS font style of the current component.
     */
    public val fontStyle: FontStyle?

    /**
     * Set the CSS font style of the current component.
     */
    @Composable
    public fun fontStyle(fontStyle: FontStyle?)

    /**
     * CSS font weight of the current component.
     */
    public val fontWeight: FontWeight?

    /**
     * Set the CSS font weight of the current component.
     */
    @Composable
    public fun fontWeight(fontWeight: FontWeight?)

    /**
     * CSS font variant of the current component.
     */
    public val fontVariant: FontVariant?

    /**
     * Set the CSS font variant of the current component.
     */
    @Composable
    public fun fontVariant(fontVariant: FontVariant?)

    /**
     * CSS position float of the current component.
     */
    public val float: CssFloat?

    /**
     * Set the CSS position float of the current component.
     */
    @Composable
    public fun float(float: CssFloat?)

    /**
     * CSS clear float of the current component.
     */
    public val clear: Clear?

    /**
     * Set the CSS clear float of the current component.
     */
    @Composable
    public fun clear(clear: Clear?)

    /**
     * CSS word break of the current component.
     */
    public val wordBreak: WordBreak?

    /**
     * Set the CSS word break of the current component.
     */
    @Composable
    public fun wordBreak(wordBreak: WordBreak?)

    /**
     * CSS line break of the current component.
     */
    public val lineBreak: LineBreak?

    /**
     * Set the CSS line break of the current component.
     */
    @Composable
    public fun lineBreak(lineBreak: LineBreak?)

    /**
     * CSS cursor shape over the current component.
     */
    public val cursor: Cursor?

    /**
     * Set the CSS cursor shape over the current component.
     */
    @Composable
    public fun cursor(cursor: Cursor?)

    /**
     * CSS flexbox direction.
     */
    public val flexDirection: FlexDirection?

    /**
     * Set the CSS flexbox direction.
     */
    @Composable
    public fun flexDirection(flexDirection: FlexDirection?)

    /**
     * CSS flexbox wrap mode.
     */
    public val flexWrap: FlexWrap?

    /**
     * Set the CSS flexbox wrap mode.
     */
    @Composable
    public fun flexWrap(flexWrap: FlexWrap?)

    /**
     * CSS grid items justification.
     */
    public val justifyItems: JustifyItems?

    /**
     * Set the CSS grid items justification.
     */
    @Composable
    public fun justifyItems(justifyItems: JustifyItems?)

    /**
     * CSS flexbox/grid content justification.
     */
    public val justifyContent: JustifyContent?

    /**
     * Set the CSS flexbox/grid content justification.
     */
    @Composable
    public fun justifyContent(justifyContent: JustifyContent?)

    /**
     * CSS flexbox/grid items alignment.
     */
    public val alignItems: AlignItems?

    /**
     * Set the CSS flexbox/grid items alignment.
     */
    @Composable
    public fun alignItems(alignItems: AlignItems?)

    /**
     * CSS flexbox/grid content alignment.
     */
    public val alignContent: AlignContent?

    /**
     * Set the CSS flexbox/grid content alignment.
     */
    @Composable
    public fun alignContent(alignContent: AlignContent?)

    /**
     * CSS flexbox item order.
     */
    public val order: Int?

    /**
     * Set the CSS flexbox item order.
     */
    @Composable
    public fun order(order: Int?)

    /**
     * CSS flexbox item grow.
     */
    public val flexGrow: Int?

    /**
     * Set the CSS flexbox item grow.
     */
    @Composable
    public fun flexGrow(flexGrow: Int?)

    /**
     * CSS flexbox item shrink.
     */
    public val flexShrink: Int?

    /**
     * Set the CSS flexbox item shrink.
     */
    @Composable
    public fun flexShrink(flexShrink: Int?)

    /**
     * CSS flexbox item basis.
     */
    public val flexBasis: CssSize?

    /**
     * Set the CSS flexbox item basis.
     */
    @Composable
    public fun flexBasis(flexBasis: CssSize?)

    /**
     * CSS flexbox items self-alignment.
     */
    public val alignSelf: AlignItems?

    /**
     * Set the CSS flexbox items self-alignment.
     */
    @Composable
    public fun alignSelf(alignSelf: AlignItems?)

    /**
     * CSS grid items self-justification.
     */
    public val justifySelf: JustifyItems?

    /**
     * Set the CSS grid items self-justification.
     */
    @Composable
    public fun justifySelf(justifySelf: JustifyItems?)

    /**
     * CSS grid auto columns.
     */
    public val gridAutoColumns: String?

    /**
     * Set the CSS grid auto columns.
     */
    @Composable
    public fun gridAutoColumns(gridAutoColumns: String?)

    /**
     * CSS grid auto rows.
     */
    public val gridAutoRows: String?

    /**
     * Set the CSS grid auto rows.
     */
    @Composable
    public fun gridAutoRows(gridAutoRows: String?)

    /**
     * CSS grid auto flow.
     */
    public val gridAutoFlow: GridAutoFlow?

    /**
     * Set the CSS grid auto flow.
     */
    @Composable
    public fun gridAutoFlow(gridAutoFlow: GridAutoFlow?)

    /**
     * CSS grid columns template.
     */
    public val gridTemplateColumns: String?

    /**
     * Set the CSS grid columns template.
     */
    @Composable
    public fun gridTemplateColumns(gridTemplateColumns: String?)

    /**
     * CSS grid rows template.
     */
    public val gridTemplateRows: String?

    /**
     * Set the CSS grid rows template.
     */
    @Composable
    public fun gridTemplateRows(gridTemplateRows: String?)

    /**
     * CSS grid areas template.
     */
    public val gridTemplateAreas: List<String>?

    /**
     * Set the CSS grid areas template.
     */
    @Composable
    public fun gridTemplateAreas(gridTemplateAreas: List<String>?)

    /**
     * CSS grid template.
     */
    public val gridTemplate: String?

    /**
     * Set the CSS grid template.
     */
    @Composable
    public fun gridTemplate(gridTemplate: String?)

    /**
     * CSS grid/flex column gap.
     */
    public val columnGap: CssSize?

    /**
     * Set the CSS grid/flex column gap.
     */
    @Composable
    public fun columnGap(columnGap: CssSize?)

    /**
     * CSS grid/flex row gap.
     */
    public val rowGap: CssSize?

    /**
     * Set the CSS grid/flex row gap.
     */
    @Composable
    public fun rowGap(rowGap: CssSize?)

    /**
     * CSS grid column start.
     */
    public val gridColumnStart: String?

    /**
     * Set the CSS grid column start.
     */
    @Composable
    public fun gridColumnStart(gridColumnStart: String?)

    /**
     * CSS grid row start.
     */
    public val gridRowStart: String?

    /**
     * Set the CSS grid row start.
     */
    @Composable
    public fun gridRowStart(gridRowStart: String?)

    /**
     * CSS grid column end.
     */
    public val gridColumnEnd: String?

    /**
     * Set the CSS grid column end.
     */
    @Composable
    public fun gridColumnEnd(gridColumnEnd: String?)

    /**
     * CSS grid row end.
     */
    public val gridRowEnd: String?

    /**
     * Set the CSS grid row end.
     */
    @Composable
    public fun gridRowEnd(gridRowEnd: String?)

    /**
     * CSS grid column.
     */
    public val gridColumn: String?

    /**
     * Set the CSS grid column.
     */
    @Composable
    public fun gridColumn(gridColumn: String?)

    /**
     * CSS grid row.
     */
    public val gridRow: String?

    /**
     * Set the CSS grid row.
     */
    @Composable
    public fun gridRow(gridRow: String?)

    /**
     * CSS grid area.
     */
    public val gridArea: String?

    /**
     * Set the CSS grid area.
     */
    @Composable
    public fun gridArea(gridArea: String?)

    /**
     * Outline of the current component.
     */
    public val outline: Outline?

    /**
     * Set the outline of the current component.
     */
    @Composable
    public fun outline(outline: Outline?)

    /**
     * Box shadow of the current component.
     */
    public val boxShadow: BoxShadow?

    /**
     * Set the box shadow of the current component.
     */
    @Composable
    public fun boxShadow(boxShadow: BoxShadow?)

    /**
     * List of box shadows of the current component.
     */
    public val boxShadowList: List<BoxShadow>?

    /**
     * Set the list of box shadows of the current component.
     */
    @Composable
    public fun boxShadowList(boxShadowList: List<BoxShadow>?)

    /**
     * CSS transition effect for the current component.
     */
    public val transition: Transition?

    /**
     * Set the CSS transition effect for the current component.
     */
    @Composable
    public fun transition(transition: Transition?)

    /**
     * List of CSS transition effects for the current component.
     */
    public val transitionList: List<Transition>?

    /**
     * Set the list of CSS transition effects for the current component.
     */
    @Composable
    public fun transitionList(transitionList: List<Transition>?)

    /**
     * CSS border radius.
     */
    public val borderRadius: CssSize?

    /**
     * Set the CSS border radius.
     */
    @Composable
    public fun borderRadius(borderRadius: CssSize?)

    /**
     * List of CSS border radius values.
     */
    public val borderRadiusList: List<CssSize>?

    /**
     * Set the list of CSS border radius values.
     */
    @Composable
    public fun borderRadiusList(borderRadiusList: List<CssSize>?)

    /**
     * List style of the current component.
     */
    public val listStyle: ListStyle?

    /**
     * Set the list style of the current component.
     */
    @Composable
    public fun listStyle(listStyle: ListStyle?)

    /**
     * Get value of the given CSS style.
     */
    public fun getStyle(name: String): String?

    /**
     * Set value for the given CSS style.
     */
    @Composable
    public fun style(name: String, value: String?)

    public companion object {

        //
        // These extension functions are required as a workaround for Compose limitations (https://issuetracker.google.com/issues/165812010)
        //

        /**
         * Set the border of the current component.
         */
        @Composable
        public fun TagStyleFun.border(
            width: CssSize? = null,
            style: BorderStyle? = null,
            color: Color? = null
        ) {
            if (width != null || style != null || color != null) {
                border(Border(width, style, color))
            } else {
                border(null)
            }
        }

        /**
         * Set the top border of the current component.
         */
        @Composable
        public fun TagStyleFun.borderTop(
            width: CssSize? = null,
            style: BorderStyle? = null,
            color: Color? = null
        ) {
            if (width != null || style != null || color != null) {
                borderTop(Border(width, style, color))
            } else {
                borderTop(null)
            }
        }

        /**
         * Set the right border of the current component.
         */
        @Composable
        public fun TagStyleFun.borderRight(
            width: CssSize? = null,
            style: BorderStyle? = null,
            color: Color? = null
        ) {
            if (width != null || style != null || color != null) {
                borderRight(Border(width, style, color))
            } else {
                borderRight(null)
            }
        }

        /**
         * Set the bottom border of the current component.
         */
        @Composable
        public fun TagStyleFun.borderBottom(
            width: CssSize? = null,
            style: BorderStyle? = null,
            color: Color? = null
        ) {
            if (width != null || style != null || color != null) {
                borderBottom(Border(width, style, color))
            } else {
                borderBottom(null)
            }
        }

        /**
         * Set the left border of the current component.
         */
        @Composable
        public fun TagStyleFun.borderLeft(
            width: CssSize? = null,
            style: BorderStyle? = null,
            color: Color? = null
        ) {
            if (width != null || style != null || color != null) {
                borderLeft(Border(width, style, color))
            } else {
                borderLeft(null)
            }
        }


        /**
         * Set the background of the current component.
         */
        @Composable
        public fun TagStyleFun.background(
            color: Color? = null, image: String? = null,
            positionX: CssSize? = null, positionY: CssSize? = null,
            sizeX: CssSize? = null, sizeY: CssSize? = null,
            size: BgSize? = null, repeat: BgRepeat? = null,
            origin: BgOrigin? = null, clip: BgClip? = null,
            attachment: BgAttach? = null
        ) {
            if (color != null || image != null || positionX != null || positionY != null || sizeX != null || sizeY != null ||
                size != null || repeat != null || origin != null || clip != null || attachment != null
            ) {
                background(
                    Background(
                        color,
                        image,
                        positionX,
                        positionY,
                        sizeX,
                        sizeY,
                        size,
                        repeat,
                        origin,
                        clip,
                        attachment
                    )
                )
            } else {
                background(null)
            }
        }

        /**
         * Set the CSS Text decoration of the current component.
         */
        @Composable
        public fun TagStyleFun.textDecoration(
            line: TextDecorationLine? = null, style: TextDecorationStyle? = null, color: Color? = null
        ) {
            if (line != null || style != null || color != null) {
                textDecoration(TextDecoration(line, style, color))
            } else {
                textDecoration(null)
            }
        }


        /**
         * Set the CSS Text shadow of the current component.
         */
        @Composable
        public fun TagStyleFun.textShadow(
            hShadow: CssSize? = null, vShadow: CssSize? = null, blurRadius: CssSize? = null, color: Color? = null
        ) {
            if (hShadow != null || vShadow != null || blurRadius != null || color != null) {
                textShadow(TextShadow(hShadow, vShadow, blurRadius, color))
            } else {
                textShadow(null)
            }
        }

        /**
         * Set the outline of the current component.
         */
        @Composable
        public fun TagStyleFun.outline(
            width: CssSize? = null,
            style: OutlineStyle? = null,
            color: Color? = null
        ) {
            if (width != null || style != null || color != null) {
                outline(Outline(width, style, color))
            } else {
                outline(null)
            }
        }

        /**
         * Set the box shadow of the current component.
         */
        @Composable
        public fun TagStyleFun.boxShadow(
            hOffset: CssSize? = null, vOffset: CssSize? = null,
            blurRadius: CssSize? = null, spreadRadius: CssSize? = null,
            color: Color? = null, inset: Boolean = false
        ) {
            if (hOffset != null || vOffset != null || blurRadius != null || spreadRadius != null || color != null || inset) {
                boxShadow(BoxShadow(hOffset, vOffset, blurRadius, spreadRadius, color, inset))
            } else {
                boxShadow(null)
            }
        }

        /**
         * Set the CSS transition effect for the current component.
         */
        @Composable
        public fun TagStyleFun.transition(
            property: String? = null, duration: Double? = null, timingFunction: String? = null, delay: Double? = null
        ) {
            if (property != null && duration != null) {
                transition(Transition(property, duration, timingFunction, delay))
            } else {
                transition(null)
            }
        }

        /**
         * Set the list style of the current component.
         */
        @Composable
        public fun TagStyleFun.listStyle(
            type: ListStyleType? = null, position: ListStylePosition? = null, image: String? = null
        ) {
            if (type != null || position != null || image != null) {
                listStyle(ListStyle(type, position, image))
            } else {
                listStyle(null)
            }
        }
    }
}
