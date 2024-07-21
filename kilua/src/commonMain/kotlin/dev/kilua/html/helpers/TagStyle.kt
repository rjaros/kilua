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

import dev.kilua.html.*
import dev.kilua.dom.api.HTMLElement

/**
 * Common tag CSS styles.
 */
public interface TagStyle<E : HTMLElement>: TagStyleFun<E> {

    /**
     * Width of the current component.
     */
    public override var width: CssSize?

    /**
     * Minimal width of the current component.
     */
    public override var minWidth: CssSize?

    /**
     * Maximal width of the current component.
     */
    public override var maxWidth: CssSize?

    /**
     * Height of the current component.
     */
    public override var height: CssSize?

    /**
     * Minimal height of the current component.
     */
    public override var minHeight: CssSize?

    /**
     * Maximal height of the current component.
     */
    public override var maxHeight: CssSize?

    /**
     * CSS display of the current component.
     */
    public override var display: Display?

    /**
     * CSS position of the current component.
     */
    public override var position: Position?

    /**
     * Top edge of the current component.
     */
    public override var top: CssSize?

    /**
     * Left edge of the current component.
     */
    public override var left: CssSize?

    /**
     * Right edge of the current component.
     */
    public override var right: CssSize?

    /**
     * Bottom edge of the current component.
     */
    public override var bottom: CssSize?

    /**
     * Z-index of the current component.
     */
    public override var zIndex: Int?

    /**
     * CSS overflow of the current component.
     */
    public override var overflow: Overflow?

    /**
     * CSS overflow-x of the current component.
     */
    public override var overflowX: Overflow?

    /**
     * CSS overflow-y of the current component.
     */
    public override var overflowY: Overflow?

    /**
     * CSS overflow-wrap of the current component.
     */
    public override var overflowWrap: OverflowWrap?

    /**
     * CSS resize of the current component.
     */
    public override var resize: Resize?

    /**
     * Border of the current component.
     */
    public override var border: Border?

    /**
     * Top border of the current component.
     */
    public override var borderTop: Border?

    /**
     * Right border of the current component.
     */
    public override var borderRight: Border?

    /**
     * Bottom border of the current component.
     */
    public override var borderBottom: Border?

    /**
     * Left border of the current component.
     */
    public override var borderLeft: Border?

    /**
     * Margin of the current component.
     */
    public override var margin: CssSize?

    /**
     * Top margin of the current component.
     */
    public override var marginTop: CssSize?

    /**
     * Right margin of the current component.
     */
    public override var marginRight: CssSize?

    /**
     * Bottom margin of the current component.
     */
    public override var marginBottom: CssSize?

    /**
     * Left margin of the current component.
     */
    public override var marginLeft: CssSize?

    /**
     * Padding of the current component.
     */
    public override var padding: CssSize?

    /**
     * Top padding of the current component.
     */
    public override var paddingTop: CssSize?

    /**
     * Right padding of the current component.
     */
    public override var paddingRight: CssSize?

    /**
     * Bottom padding of the current component.
     */
    public override var paddingBottom: CssSize?

    /**
     * Left padding of the current component.
     */
    public override var paddingLeft: CssSize?

    /**
     * Text color for the current component.
     */
    public override var color: Color?

    /**
     * Opacity of the current component.
     */
    public override var opacity: Double?

    /**
     * Background of the current component.
     */
    public override var background: Background?

    /**
     * CSS Text direction of the current component.
     */
    public override var direction: Direction?

    /**
     * CSS Text letter spacing of the current component.
     */
    public override var letterSpacing: CssSize?

    /**
     * CSS Text line height of the current component.
     */
    public override var lineHeight: CssSize?

    /**
     * CSS Text align of the current component.
     */
    public override var textAlign: TextAlign?

    /**
     * CSS Text decoration of the current component.
     */
    public override var textDecoration: TextDecoration?

    /**
     * CSS Text indent of the current component.
     */
    public override var textIndent: CssSize?

    /**
     * CSS Text shadow of the current component.
     */
    public override var textShadow: TextShadow?

    /**
     * CSS Text transform of the current component.
     */
    public override var textTransform: TextTransform?

    /**
     * CSS Text overflow of the current component.
     */
    public override var textOverflow: TextOverflow?

    /**
     * CSS Text unicode-bidi of the current component.
     */
    public override var unicodeBidi: UnicodeBidi?

    /**
     * CSS Text vertical align of the current component.
     */
    public override var verticalAlign: VerticalAlign?

    /**
     * CSS Text white space of the current component.
     */
    public override var whiteSpace: WhiteSpace?

    /**
     * CSS Text word spacing of the current component.
     */
    public override var wordSpacing: CssSize?

    /**
     * CSS font family of the current component.
     */
    public override var fontFamily: String?

    /**
     * CSS font size of the current component.
     */
    public override var fontSize: CssSize?

    /**
     * CSS font style of the current component.
     */
    public override var fontStyle: FontStyle?

    /**
     * CSS font weight of the current component.
     */
    public override var fontWeight: FontWeight?

    /**
     * CSS font public override variant of the current component.
     */
    public override var fontVariant: FontVariant?

    /**
     * CSS position float of the current component.
     */
    public override var float: CssFloat?

    /**
     * CSS clear float of the current component.
     */
    public override var clear: Clear?

    /**
     * CSS word break of the current component.
     */
    public override var wordBreak: WordBreak?

    /**
     * CSS line break of the current component.
     */
    public override var lineBreak: LineBreak?

    /**
     * CSS cursor shape over the current component.
     */
    public override var cursor: Cursor?

    /**
     * CSS flexbox direction.
     */
    public override var flexDirection: FlexDirection?

    /**
     * CSS flexbox wrap mode.
     */
    public override var flexWrap: FlexWrap?

    /**
     * CSS grid items justification.
     */
    public override var justifyItems: JustifyItems?

    /**
     * CSS flexbox/grid content justification.
     */
    public override var justifyContent: JustifyContent?

    /**
     * CSS flexbox/grid items alignment.
     */
    public override var alignItems: AlignItems?

    /**
     * CSS flexbox/grid content alignment.
     */
    public override var alignContent: AlignContent?

    /**
     * CSS flexbox item order.
     */
    public override var order: Int?

    /**
     * CSS flexbox item grow.
     */
    public override var flexGrow: Int?

    /**
     * CSS flexbox item shrink.
     */
    public override var flexShrink: Int?

    /**
     * CSS flexbox item basis.
     */
    public override var flexBasis: CssSize?

    /**
     * CSS flexbox items self-alignment.
     */
    public override var alignSelf: AlignItems?

    /**
     * CSS grid items self-justification.
     */
    public override var justifySelf: JustifyItems?

    /**
     * CSS grid auto columns.
     */
    public override var gridAutoColumns: String?

    /**
     * CSS grid auto rows.
     */
    public override var gridAutoRows: String?

    /**
     * CSS grid auto flow.
     */
    public override var gridAutoFlow: GridAutoFlow?

    /**
     * CSS grid columns template.
     */
    public override var gridTemplateColumns: String?

    /**
     * CSS grid rows template.
     */
    public override var gridTemplateRows: String?

    /**
     * CSS grid areas template.
     */
    public override var gridTemplateAreas: List<String>?

    /**
     * CSS grid/flex column gap.
     */
    public override var columnGap: CssSize?

    /**
     * CSS grid/flex row gap.
     */
    public override var rowGap: CssSize?

    /**
     * CSS grid column start.
     */
    public override var gridColumnStart: Int?

    /**
     * CSS grid row start.
     */
    public override var gridRowStart: Int?

    /**
     * CSS grid column end.
     */
    public override var gridColumnEnd: String?

    /**
     * CSS grid row end.
     */
    public override var gridRowEnd: String?

    /**
     * CSS grid area.
     */
    public override var gridArea: String?

    /**
     * Outline of the current component.
     */
    public override var outline: Outline?

    /**
     * Box shadow of the current component.
     */
    public override var boxShadow: BoxShadow?

    /**
     * List of box shadows of the current component.
     */
    public override var boxShadowList: List<BoxShadow>?

    /**
     * CSS transition effect for the current component.
     */
    public override var transition: Transition?

    /**
     * List of CSS transition effects for the current component.
     */
    public override var transitionList: List<Transition>?

    /**
     * CSS border radius.
     */
    public override var borderRadius: CssSize?

    /**
     * List of CSS border radius values.
     */
    public override var borderRadiusList: List<CssSize>?

    /**
     * List style of the current component.
     */
    public override var listStyle: ListStyle?

    /**
     * Set value for a given CSS style name.
     */
    public fun setStyle(name: String, value: String?)

    /**
     * Get value of the given CSS style.
     */
    public override fun getStyle(name: String): String?

    /**
     * Remove CSS style with the given name.
     */
    public fun removeStyle(name: String)

}
