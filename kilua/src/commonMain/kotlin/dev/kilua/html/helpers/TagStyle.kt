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

package dev.kilua.html.helpers

import dev.kilua.html.*
import org.w3c.dom.HTMLElement

/**
 * Common tag CSS styles.
 */
public interface TagStyle<E : HTMLElement> {

    /**
     * Width of the current component.
     */
    public var width: CssSize?

    /**
     * Minimal width of the current component.
     */
    public var minWidth: CssSize?

    /**
     * Maximal width of the current component.
     */
    public var maxWidth: CssSize?

    /**
     * Height of the current component.
     */
    public var height: CssSize?

    /**
     * Minimal height of the current component.
     */
    public var minHeight: CssSize?

    /**
     * Maximal height of the current component.
     */
    public var maxHeight: CssSize?

    /**
     * CSS display of the current component.
     */
    public var display: Display?

    /**
     * CSS position of the current component.
     */
    public var position: Position?

    /**
     * Top edge of the current component.
     */
    public var top: CssSize?

    /**
     * Left edge of the current component.
     */
    public var left: CssSize?

    /**
     * Right edge of the current component.
     */
    public var right: CssSize?

    /**
     * Bottom edge of the current component.
     */
    public var bottom: CssSize?

    /**
     * Z-index of the current component.
     */
    public var zIndex: Int?

    /**
     * CSS overflow of the current component.
     */
    public var overflow: Overflow?

    /**
     * CSS overflow-x of the current component.
     */
    public var overflowX: Overflow?

    /**
     * CSS overflow-y of the current component.
     */
    public var overflowY: Overflow?

    /**
     * CSS overflow-wrap of the current component.
     */
    public var overflowWrap: OverflowWrap?

    /**
     * CSS resize of the current component.
     */
    public var resize: Resize?

    /**
     * Border of the current component.
     */
    public var border: Border?

    /**
     * Top border of the current component.
     */
    public var borderTop: Border?

    /**
     * Right border of the current component.
     */
    public var borderRight: Border?

    /**
     * Bottom border of the current component.
     */
    public var borderBottom: Border?

    /**
     * Left border of the current component.
     */
    public var borderLeft: Border?

    /**
     * Margin of the current component.
     */
    public var margin: CssSize?

    /**
     * Top margin of the current component.
     */
    public var marginTop: CssSize?

    /**
     * Right margin of the current component.
     */
    public var marginRight: CssSize?

    /**
     * Bottom margin of the current component.
     */
    public var marginBottom: CssSize?

    /**
     * Left margin of the current component.
     */
    public var marginLeft: CssSize?

    /**
     * Padding of the current component.
     */
    public var padding: CssSize?

    /**
     * Top padding of the current component.
     */
    public var paddingTop: CssSize?

    /**
     * Right padding of the current component.
     */
    public var paddingRight: CssSize?

    /**
     * Bottom padding of the current component.
     */
    public var paddingBottom: CssSize?

    /**
     * Left padding of the current component.
     */
    public var paddingLeft: CssSize?

    /**
     * Text color for the current component.
     */
    public var color: Color?

    /**
     * Opacity of the current component.
     */
    public var opacity: Double?

    /**
     * Background of the current component.
     */
    public var background: Background?

    /**
     * CSS Text direction of the current component.
     */
    public var direction: Direction?

    /**
     * CSS Text letter spacing of the current component.
     */
    public var letterSpacing: CssSize?

    /**
     * CSS Text line height of the current component.
     */
    public var lineHeight: CssSize?

    /**
     * CSS Text align of the current component.
     */
    public var textAlign: TextAlign?

    /**
     * CSS Text decoration of the current component.
     */
    public var textDecoration: TextDecoration?

    /**
     * CSS Text indent of the current component.
     */
    public var textIndent: CssSize?

    /**
     * CSS Text shadow of the current component.
     */
    public var textShadow: TextShadow?

    /**
     * CSS Text transform of the current component.
     */
    public var textTransform: TextTransform?

    /**
     * CSS Text overflow of the current component.
     */
    public var textOverflow: TextOverflow?

    /**
     * CSS Text unicode-bidi of the current component.
     */
    public var unicodeBidi: UnicodeBidi?

    /**
     * CSS Text vertical align of the current component.
     */
    public var verticalAlign: VerticalAlign?

    /**
     * CSS Text white space of the current component.
     */
    public var whiteSpace: WhiteSpace?

    /**
     * CSS Text word spacing of the current component.
     */
    public var wordSpacing: CssSize?

    /**
     * CSS font family of the current component.
     */
    public var fontFamily: String?

    /**
     * CSS font size of the current component.
     */
    public var fontSize: CssSize?

    /**
     * CSS font style of the current component.
     */
    public var fontStyle: FontStyle?

    /**
     * CSS font weight of the current component.
     */
    public var fontWeight: FontWeight?

    /**
     * CSS font public variant of the current component.
     */
    public var fontVariant: FontVariant?

    /**
     * CSS position float of the current component.
     */
    public var float: CssFloat?

    /**
     * CSS clear float of the current component.
     */
    public var clear: Clear?

    /**
     * CSS word break of the current component.
     */
    public var wordBreak: WordBreak?

    /**
     * CSS line break of the current component.
     */
    public var lineBreak: LineBreak?

    /**
     * CSS cursor shape over the current component.
     */
    public var cursor: Cursor?

    /**
     * CSS flexbox direction.
     */
    public var flexDirection: FlexDirection?

    /**
     * CSS flexbox wrap mode.
     */
    public var flexWrap: FlexWrap?

    /**
     * CSS grid items justification.
     */
    public var justifyItems: JustifyItems?

    /**
     * CSS flexbox/grid content justification.
     */
    public var justifyContent: JustifyContent?

    /**
     * CSS flexbox/grid items alignment.
     */
    public var alignItems: AlignItems?

    /**
     * CSS flexbox/grid content alignment.
     */
    public var alignContent: AlignContent?

    /**
     * CSS flexbox item order.
     */
    public var order: Int?

    /**
     * CSS flexbox item grow.
     */
    public var flexGrow: Int?

    /**
     * CSS flexbox item shrink.
     */
    public var flexShrink: Int?

    /**
     * CSS flexbox item basis.
     */
    public var flexBasis: CssSize?

    /**
     * CSS flexbox items self-alignment.
     */
    public var alignSelf: AlignItems?

    /**
     * CSS grid items self-justification.
     */
    public var justifySelf: JustifyItems?

    /**
     * CSS grid auto columns.
     */
    public var gridAutoColumns: String?

    /**
     * CSS grid auto rows.
     */
    public var gridAutoRows: String?

    /**
     * CSS grid auto flow.
     */
    public var gridAutoFlow: GridAutoFlow?

    /**
     * CSS grid columns template.
     */
    public var gridTemplateColumns: String?

    /**
     * CSS grid rows template.
     */
    public var gridTemplateRows: String?

    /**
     * CSS grid areas template.
     */
    public var gridTemplateAreas: List<String>?

    /**
     * CSS grid column gap.
     */
    public var gridColumnGap: Int?

    /**
     * CSS grid row gap.
     */
    public var gridRowGap: Int?

    /**
     * CSS grid column start.
     */
    public var gridColumnStart: Int?

    /**
     * CSS grid row start.
     */
    public var gridRowStart: Int?

    /**
     * CSS grid column end.
     */
    public var gridColumnEnd: String?

    /**
     * CSS grid row end.
     */
    public var gridRowEnd: String?

    /**
     * CSS grid area.
     */
    public var gridArea: String?

    /**
     * Outline of the current component.
     */
    public var outline: Outline?

    /**
     * Box shadow of the current component.
     */
    public var boxShadow: BoxShadow?

    /**
     * List of box shadows of the current component.
     */
    public var boxShadowList: List<BoxShadow>?

    /**
     * CSS transition effect for the current component.
     */
    public var transition: Transition?

    /**
     * List of CSS transition effects for the current component.
     */
    public var transitionList: List<Transition>?

    /**
     * CSS border radius.
     */
    public var borderRadius: CssSize?

    /**
     * List of CSS border radius values.
     */
    public var borderRadiusList: List<CssSize>?

    /**
     * List style of the current component.
     */
    public var listStyle: ListStyle?

    /**
     * Set value for a given CSS style name.
     */
    public fun setStyle(name: String, value: String?)

    /**
     * Get value of the given CSS style.
     */
    public fun getStyle(name: String): String?

    /**
     * Remove CSS style with the given name.
     */
    public fun removeStyle(name: String)

}
