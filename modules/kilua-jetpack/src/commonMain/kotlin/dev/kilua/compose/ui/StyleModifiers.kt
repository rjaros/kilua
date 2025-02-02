/*
 * Copyright (c) 2025 Robert Jaros
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

package dev.kilua.compose.ui

import dev.kilua.html.*
import kotlin.time.Duration

/**
 * Width of the current component.
 */
public fun Modifier.width(width: CssSize?) = styleModifier {
    width(width)
}

/**
 * Minimal width of the current component.
 */
public fun Modifier.minWidth(minWidth: CssSize?) = styleModifier {
    minWidth(minWidth)
}

/**
 * Maximal width of the current component.
 */
public fun Modifier.maxWidth(maxWidth: CssSize?) = styleModifier {
    maxWidth(maxWidth)
}

/**
 * Height of the current component.
 */
public fun Modifier.height(height: CssSize?) = styleModifier {
    height(height)
}

/**
 * Minimal height of the current component.
 */
public fun Modifier.minHeight(minHeight: CssSize?) = styleModifier {
    minHeight(minHeight)
}

/**
 * Maximal height of the current component.
 */
public fun Modifier.maxHeight(maxHeight: CssSize?) = styleModifier {
    maxHeight(maxHeight)
}

/**
 * CSS display of the current component.
 */
public fun Modifier.display(display: Display?) = styleModifier {
    display(display)
}

/**
 * CSS visibility of the current component.
 */
public fun Modifier.visibility(visibility: Visibility?) = styleModifier {
    visibility(visibility)
}

/**
 * CSS position of the current component.
 */
public fun Modifier.position(position: Position?) = styleModifier {
    position(position)
}

/**
 * Top edge of the current component.
 */
public fun Modifier.top(top: CssSize?) = styleModifier {
    top(top)
}

/**
 * Left edge of the current component.
 */
public fun Modifier.left(left: CssSize?) = styleModifier {
    left(left)
}

/**
 * Right edge of the current component.
 */
public fun Modifier.right(right: CssSize?) = styleModifier {
    right(right)
}

/**
 * Bottom edge of the current component.
 */
public fun Modifier.bottom(bottom: CssSize?) = styleModifier {
    bottom(bottom)
}

/**
 * Z-index of the current component.
 */
public fun Modifier.zIndex(zIndex: Int?) = styleModifier {
    zIndex(zIndex)
}

/**
 * CSS overflow of the current component.
 */
public fun Modifier.overflow(overflow: Overflow?) = styleModifier {
    overflow(overflow)
}

/**
 * CSS overflow-x of the current component.
 */
public fun Modifier.overflowX(overflowX: Overflow?) = styleModifier {
    overflowX(overflowX)
}

/**
 * CSS overflow-y of the current component.
 */
public fun Modifier.overflowY(overflowY: Overflow?) = styleModifier {
    overflowY(overflowY)
}

/**
 * CSS overflow-wrap of the current component.
 */
public fun Modifier.overflowWrap(overflowWrap: OverflowWrap?) = styleModifier {
    overflowWrap(overflowWrap)
}

/**
 * CSS resize of the current component.
 */
public fun Modifier.resize(resize: Resize?) = styleModifier {
    resize(resize)
}

/**
 * Border of the current component.
 */
public fun Modifier.border(border: Border?) = styleModifier {
    border(border)
}

/**
 * Top border of the current component.
 */
public fun Modifier.borderTop(borderTop: Border?) = styleModifier {
    borderTop(borderTop)
}

/**
 * Right border of the current component.
 */
public fun Modifier.borderRight(borderRight: Border?) = styleModifier {
    borderRight(borderRight)
}

/**
 * Bottom border of the current component.
 */
public fun Modifier.borderBottom(borderBottom: Border?) = styleModifier {
    borderBottom(borderBottom)
}

/**
 * Left border of the current component.
 */
public fun Modifier.borderLeft(borderLeft: Border?) = styleModifier {
    borderLeft(borderLeft)
}

/**
 * Margin of the current component.
 */
public fun Modifier.margin(margin: CssSize?) = styleModifier {
    margin(margin)
}

/**
 * Top margin of the current component.
 */
public fun Modifier.marginTop(marginTop: CssSize?) = styleModifier {
    marginTop(marginTop)
}

/**
 * Right margin of the current component.
 */
public fun Modifier.marginRight(marginRight: CssSize?) = styleModifier {
    marginRight(marginRight)
}

/**
 * Bottom margin of the current component.
 */
public fun Modifier.marginBottom(marginBottom: CssSize?) = styleModifier {
    marginBottom(marginBottom)
}

/**
 * Left margin of the current component.
 */
public fun Modifier.marginLeft(marginLeft: CssSize?) = styleModifier {
    marginLeft(marginLeft)
}

/**
 * Padding of the current component.
 */
public fun Modifier.padding(padding: CssSize?) = styleModifier {
    padding(padding)
}

/**
 * Top padding of the current component.
 */
public fun Modifier.paddingTop(paddingTop: CssSize?) = styleModifier {
    paddingTop(paddingTop)
}

/**
 * Right padding of the current component.
 */
public fun Modifier.paddingRight(paddingRight: CssSize?) = styleModifier {
    paddingRight(paddingRight)
}

/**
 * Bottom padding of the current component.
 */
public fun Modifier.paddingBottom(paddingBottom: CssSize?) = styleModifier {
    paddingBottom(paddingBottom)
}

/**
 * Left padding of the current component.
 */
public fun Modifier.paddingLeft(paddingLeft: CssSize?) = styleModifier {
    paddingLeft(paddingLeft)
}

/**
 * Text color for the current component.
 */
public fun Modifier.color(color: Color?) = styleModifier {
    color(color)
}

/**
 * Opacity of the current component.
 */
public fun Modifier.opacity(opacity: Double?) = styleModifier {
    opacity(opacity)
}

/**
 * Background of the current component.
 */
public fun Modifier.background(background: Background?) = styleModifier {
    background(background)
}

/**
 * CSS Text direction of the current component.
 */
public fun Modifier.direction(direction: Direction?) = styleModifier {
    direction(direction)
}

/**
 * CSS Text letter spacing of the current component.
 */
public fun Modifier.letterSpacing(letterSpacing: CssSize?) = styleModifier {
    letterSpacing(letterSpacing)
}

/**
 * CSS Text line height of the current component.
 */
public fun Modifier.lineHeight(lineHeight: CssSize?) = styleModifier {
    lineHeight(lineHeight)
}

/**
 * CSS Text align of the current component.
 */
public fun Modifier.textAlign(textAlign: TextAlign?) = styleModifier {
    textAlign(textAlign)
}

/**
 * CSS Text decoration of the current component.
 */
public fun Modifier.textDecoration(textDecoration: TextDecoration?) = styleModifier {
    textDecoration(textDecoration)
}

/**
 * CSS Text indent of the current component.
 */
public fun Modifier.textIndent(textIndent: CssSize?) = styleModifier {
    textIndent(textIndent)
}

/**
 * CSS Text shadow of the current component.
 */
public fun Modifier.textShadow(textShadow: TextShadow?) = styleModifier {
    textShadow(textShadow)
}

/**
 * CSS Text transform of the current component.
 */
public fun Modifier.textTransform(textTransform: TextTransform?) = styleModifier {
    textTransform(textTransform)
}

/**
 * CSS Text overflow of the current component.
 */
public fun Modifier.textOverflow(textOverflow: TextOverflow?) = styleModifier {
    textOverflow(textOverflow)
}

/**
 * CSS Text unicode-bidi of the current component.
 */
public fun Modifier.unicodeBidi(unicodeBidi: UnicodeBidi?) = styleModifier {
    unicodeBidi(unicodeBidi)
}

/**
 * CSS Text vertical align of the current component.
 */
public fun Modifier.verticalAlign(verticalAlign: VerticalAlign?) = styleModifier {
    verticalAlign(verticalAlign)
}

/**
 * CSS Text white space of the current component.
 */
public fun Modifier.whiteSpace(whiteSpace: WhiteSpace?) = styleModifier {
    whiteSpace(whiteSpace)
}

/**
 * CSS Text word spacing of the current component.
 */
public fun Modifier.wordSpacing(wordSpacing: CssSize?) = styleModifier {
    wordSpacing(wordSpacing)
}

/**
 * CSS font family of the current component.
 */
public fun Modifier.fontFamily(fontFamily: String?) = styleModifier {
    fontFamily(fontFamily)
}

/**
 * CSS font size of the current component.
 */
public fun Modifier.fontSize(fontSize: CssSize?) = styleModifier {
    fontSize(fontSize)
}

/**
 * CSS font style of the current component.
 */
public fun Modifier.fontStyle(fontStyle: FontStyle?) = styleModifier {
    fontStyle(fontStyle)
}

/**
 * CSS font weight of the current component.
 */
public fun Modifier.fontWeight(fontWeight: FontWeight?) = styleModifier {
    fontWeight(fontWeight)
}

/**
 * CSS font variant of the current component.
 */
public fun Modifier.fontVariant(fontVariant: FontVariant?) = styleModifier {
    fontVariant(fontVariant)
}

/**
 * CSS position float of the current component.
 */
public fun Modifier.float(float: CssFloat?) = styleModifier {
    float(float)
}

/**
 * CSS clear float of the current component.
 */
public fun Modifier.clear(clear: Clear?) = styleModifier {
    clear(clear)
}

/**
 * CSS word break of the current component.
 */
public fun Modifier.wordBreak(wordBreak: WordBreak?) = styleModifier {
    wordBreak(wordBreak)
}

/**
 * CSS line break of the current component.
 */
public fun Modifier.lineBreak(lineBreak: LineBreak?) = styleModifier {
    lineBreak(lineBreak)
}

/**
 * CSS cursor shape over the current component.
 */
public fun Modifier.cursor(cursor: Cursor?) = styleModifier {
    cursor(cursor)
}

/**
 * CSS flexbox direction.
 */
public fun Modifier.flexDirection(flexDirection: FlexDirection?) = styleModifier {
    flexDirection(flexDirection)
}

/**
 * CSS flexbox wrap mode.
 */
public fun Modifier.flexWrap(flexWrap: FlexWrap?) = styleModifier {
    flexWrap(flexWrap)
}

/**
 * CSS grid items justification.
 */
public fun Modifier.justifyItems(justifyItems: JustifyItems?) = styleModifier {
    justifyItems(justifyItems)
}

/**
 * CSS flexbox/grid content justification.
 */
public fun Modifier.justifyContent(justifyContent: JustifyContent?) = styleModifier {
    justifyContent(justifyContent)
}

/**
 * CSS flexbox/grid items alignment.
 */
public fun Modifier.alignItems(alignItems: AlignItems?) = styleModifier {
    alignItems(alignItems)
}

/**
 * CSS flexbox/grid content alignment.
 */
public fun Modifier.alignContent(alignContent: AlignContent?) = styleModifier {
    alignContent(alignContent)
}

/**
 * CSS flexbox item order.
 */
public fun Modifier.order(order: Int?) = styleModifier {
    order(order)
}

/**
 * CSS flexbox item grow.
 */
public fun Modifier.flexGrow(flexGrow: Int?) = styleModifier {
    flexGrow(flexGrow)
}

/**
 * CSS flexbox item shrink.
 */
public fun Modifier.flexShrink(flexShrink: Int?) = styleModifier {
    flexShrink(flexShrink)
}

/**
 * CSS flexbox item basis.
 */
public fun Modifier.flexBasis(flexBasis: CssSize?) = styleModifier {
    flexBasis(flexBasis)
}

/**
 * CSS flexbox items self-alignment.
 */
public fun Modifier.alignSelf(alignSelf: AlignItems?) = styleModifier {
    alignSelf(alignSelf)
}

/**
 * CSS grid items self-justification.
 */
public fun Modifier.justifySelf(justifySelf: JustifyItems?) = styleModifier {
    justifySelf(justifySelf)
}

/**
 * CSS grid auto columns.
 */
public fun Modifier.gridAutoColumns(gridAutoColumns: String?) = styleModifier {
    gridAutoColumns(gridAutoColumns)
}

/**
 * CSS grid auto rows.
 */
public fun Modifier.gridAutoRows(gridAutoRows: String?) = styleModifier {
    gridAutoRows(gridAutoRows)
}

/**
 * CSS grid auto flow.
 */
public fun Modifier.gridAutoFlow(gridAutoFlow: GridAutoFlow?) = styleModifier {
    gridAutoFlow(gridAutoFlow)
}

/**
 * CSS grid columns template.
 */
public fun Modifier.gridTemplateColumns(gridTemplateColumns: String?) = styleModifier {
    gridTemplateColumns(gridTemplateColumns)
}

/**
 * CSS grid rows template.
 */
public fun Modifier.gridTemplateRows(gridTemplateRows: String?) = styleModifier {
    gridTemplateRows(gridTemplateRows)
}

/**
 * CSS grid areas template.
 */
public fun Modifier.gridTemplateAreas(gridTemplateAreas: List<String>?) = styleModifier {
    gridTemplateAreas(gridTemplateAreas)
}

/**
 * CSS grid template.
 */
public fun Modifier.gridTemplate(gridTemplate: String?) = styleModifier {
    gridTemplate(gridTemplate)
}

/**
 * CSS grid/flex column gap.
 */
public fun Modifier.columnGap(columnGap: CssSize?) = styleModifier {
    columnGap(columnGap)
}

/**
 * CSS grid/flex row gap.
 */
public fun Modifier.rowGap(rowGap: CssSize?) = styleModifier {
    rowGap(rowGap)
}

/**
 * CSS grid column start.
 */
public fun Modifier.gridColumnStart(gridColumnStart: String?) = styleModifier {
    gridColumnStart(gridColumnStart)
}

/**
 * CSS grid row start.
 */
public fun Modifier.gridRowStart(gridRowStart: String?) = styleModifier {
    gridRowStart(gridRowStart)
}

/**
 * CSS grid column end.
 */
public fun Modifier.gridColumnEnd(gridColumnEnd: String?) = styleModifier {
    gridColumnEnd(gridColumnEnd)
}

/**
 * CSS grid row end.
 */
public fun Modifier.gridRowEnd(gridRowEnd: String?) = styleModifier {
    gridRowEnd(gridRowEnd)
}

/**
 * CSS grid column.
 */
public fun Modifier.gridColumn(gridColumn: String?) = styleModifier {
    gridColumn(gridColumn)
}

/**
 * CSS grid row.
 */
public fun Modifier.gridRow(gridRow: String?) = styleModifier {
    gridRow(gridRow)
}

/**
 * CSS grid area.
 */
public fun Modifier.gridArea(gridArea: String?) = styleModifier {
    gridArea(gridArea)
}

/**
 * Outline of the current component.
 */
public fun Modifier.outline(outline: Outline?) = styleModifier {
    outline(outline)
}

/**
 * Box shadow of the current component.
 */
public fun Modifier.boxShadow(boxShadow: BoxShadow?) = styleModifier {
    boxShadow(boxShadow)
}

/**
 * List of box shadows of the current component.
 */
public fun Modifier.boxShadowList(boxShadowList: List<BoxShadow>?) = styleModifier {
    boxShadowList(boxShadowList)
}

/**
 * CSS transition effect for the current component.
 */
public fun Modifier.transition(transition: Transition?) = styleModifier {
    transition(transition)
}

/**
 * List of CSS transition effects for the current component.
 */
public fun Modifier.transitionList(transitionList: List<Transition>?) = styleModifier {
    transitionList(transitionList)
}

/**
 * CSS border radius.
 */
public fun Modifier.borderRadius(borderRadius: CssSize?) = styleModifier {
    borderRadius(borderRadius)
}

/**
 * List of CSS border radius values.
 */
public fun Modifier.borderRadiusList(borderRadiusList: List<CssSize>?) = styleModifier {
    borderRadiusList(borderRadiusList)
}

/**
 * List style of the current component.
 */
public fun Modifier.listStyle(listStyle: ListStyle?) = styleModifier {
    listStyle(listStyle)
}

/**
 * Set value for the given CSS style.
 */
public fun Modifier.style(name: String, value: String?) = styleModifier {
    style(name, value)
}

/**
 * Set the border of the current component.
 */
public fun Modifier.border(
    width: CssSize? = null,
    style: BorderStyle? = null,
    color: Color? = null
) = styleModifier {
    if (width != null || style != null || color != null) {
        border(Border(width, style, color))
    } else {
        border(null)
    }
}

/**
 * Set the top border of the current component.
 */
public fun Modifier.borderTop(
    width: CssSize? = null,
    style: BorderStyle? = null,
    color: Color? = null
) = styleModifier {
    if (width != null || style != null || color != null) {
        borderTop(Border(width, style, color))
    } else {
        borderTop(null)
    }
}

/**
 * Set the right border of the current component.
 */
public fun Modifier.borderRight(
    width: CssSize? = null,
    style: BorderStyle? = null,
    color: Color? = null
) = styleModifier {
    if (width != null || style != null || color != null) {
        borderRight(Border(width, style, color))
    } else {
        borderRight(null)
    }
}

/**
 * Set the bottom border of the current component.
 */
public fun Modifier.borderBottom(
    width: CssSize? = null,
    style: BorderStyle? = null,
    color: Color? = null
) = styleModifier {
    if (width != null || style != null || color != null) {
        borderBottom(Border(width, style, color))
    } else {
        borderBottom(null)
    }
}

/**
 * Set the left border of the current component.
 */
public fun Modifier.borderLeft(
    width: CssSize? = null,
    style: BorderStyle? = null,
    color: Color? = null
) = styleModifier {
    if (width != null || style != null || color != null) {
        borderLeft(Border(width, style, color))
    } else {
        borderLeft(null)
    }
}

/**
 * Set the background of the current component.
 */
public fun Modifier.background(
    color: Color? = null, image: String? = null,
    positionX: CssSize? = null, positionY: CssSize? = null,
    sizeX: CssSize? = null, sizeY: CssSize? = null,
    size: BgSize? = null, repeat: BgRepeat? = null,
    origin: BgOrigin? = null, clip: BgClip? = null,
    attachment: BgAttach? = null
) = styleModifier {
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
public fun Modifier.textDecoration(
    line: TextDecorationLine? = null, style: TextDecorationStyle? = null, color: Color? = null
) = styleModifier {
    if (line != null || style != null || color != null) {
        textDecoration(TextDecoration(line, style, color))
    } else {
        textDecoration(null)
    }
}

/**
 * Set the CSS Text shadow of the current component.
 */
public fun Modifier.textShadow(
    hShadow: CssSize? = null, vShadow: CssSize? = null, blurRadius: CssSize? = null, color: Color? = null
) = styleModifier {
    if (hShadow != null || vShadow != null || blurRadius != null || color != null) {
        textShadow(TextShadow(hShadow, vShadow, blurRadius, color))
    } else {
        textShadow(null)
    }
}

/**
 * Set the outline of the current component.
 */
public fun Modifier.outline(
    width: CssSize? = null,
    style: OutlineStyle? = null,
    color: Color? = null
) = styleModifier {
    if (width != null || style != null || color != null) {
        outline(Outline(width, style, color))
    } else {
        outline(null)
    }
}

/**
 * Set the box shadow of the current component.
 */
public fun Modifier.boxShadow(
    hOffset: CssSize? = null, vOffset: CssSize? = null,
    blurRadius: CssSize? = null, spreadRadius: CssSize? = null,
    color: Color? = null, inset: Boolean = false
) = styleModifier {
    if (hOffset != null || vOffset != null || blurRadius != null || spreadRadius != null || color != null || inset) {
        boxShadow(BoxShadow(hOffset, vOffset, blurRadius, spreadRadius, color, inset))
    } else {
        boxShadow(null)
    }
}

/**
 * Set the CSS transition effect for the current component.
 */
public fun Modifier.transition(
    property: String? = null, duration: Duration? = null, timingFunction: String? = null, delay: Duration? = null
) = styleModifier {
    if (property != null && duration != null) {
        transition(Transition(property, duration, timingFunction, delay))
    } else {
        transition(null)
    }
}

/**
 * Set the list style of the current component.
 */
public fun Modifier.listStyle(
    type: ListStyleType? = null, position: ListStylePosition? = null, image: String? = null
) = styleModifier {
    if (type != null || position != null || image != null) {
        listStyle(ListStyle(type, position, image))
    } else {
        listStyle(null)
    }
}

/**
 * Set the width of the current component to given (by default maximum) value.
 */
public fun Modifier.fillMaxWidth(fraction: Float = 1.0f) = styleModifier {
    width((100 * fraction).perc)
}

/**
 * Set the height of the current component to given (by default maximum) value.
 */
public fun Modifier.fillMaxHeight(fraction: Float = 1.0f) = styleModifier {
    height((100 * fraction).perc)
}

/**
 * Set the size (both width and height) of the current component to given (by default minimum) value.
 */
public fun Modifier.fillMaxSize(fraction: Float = 1.0f) = styleModifier {
    width((100 * fraction).perc)
    height((100 * fraction).perc)
}

/**
 * Set the size (both width and height) of the current component.
 */
public fun Modifier.size(size: CssSize?) = styleModifier {
    width(size)
    height(size)
}

/**
 * Set the size (both width and height) of the current component.
 */
public fun Modifier.size(width: CssSize?, height: CssSize?) = styleModifier {
    width(width)
    height(height)
}

/**
 * Set the minimal size (both width and height) of the current component.
 */
public fun Modifier.minSize(size: CssSize?) = styleModifier {
    minWidth(size)
    minHeight(size)
}

/**
 * Set the minimal size (both width and height) of the current component.
 */
public fun Modifier.minSize(width: CssSize?, height: CssSize?) = styleModifier {
    minWidth(width)
    minHeight(height)
}

/**
 * Set the maximal size (both width and height) of the current component.
 */
public fun Modifier.maxSize(size: CssSize?) = styleModifier {
    maxWidth(size)
    maxHeight(size)
}

/**
 * Set the maximal size (both width and height) of the current component.
 */
public fun Modifier.maxSize(width: CssSize?, height: CssSize?) = styleModifier {
    maxWidth(width)
    maxHeight(height)
}

/**
 * Constrain the width of the component to be between min and max.
 */
public fun Modifier.widthIn(min: CssSize? = null, max: CssSize? = null) = styleModifier {
    min?.let { minWidth(it) }
    max?.let { maxWidth(it) }
}

/**
 * Constrain the height of the component to be between min and max.
 */
public fun Modifier.heightIn(min: CssSize? = null, max: CssSize? = null) = styleModifier {
    min?.let { minHeight(it) }
    max?.let { maxHeight(it) }
}

/**
 * Constrain the size (both width and height) of the component to be between min and max.
 */
public fun Modifier.sizeIn(
    minWidth: CssSize? = null,
    minHeight: CssSize? = null,
    maxWidth: CssSize? = null,
    maxHeight: CssSize? = null
) = styleModifier {
    minWidth?.let { minWidth(it) }
    minHeight?.let { minHeight(it) }
    maxWidth?.let { maxWidth(it) }
    maxHeight?.let { maxHeight(it) }
}
