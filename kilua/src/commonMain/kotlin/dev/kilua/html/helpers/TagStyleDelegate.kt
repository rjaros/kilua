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
import dev.kilua.core.PropertyDelegate
import dev.kilua.html.*
import dev.kilua.utils.cast
import web.dom.HTMLElement

public interface TagStyleDelegate<E : HTMLElement> : TagStyle<E> {
    /**
     * The map of CSS styles.
     */
    public val stylesMap: Map<String, Any>

    /**
     * Connects the delegate with the given element.
     */
    public fun elementWithStyle(element: E?)
}

/**
 * Common tag CSS styles delegate implementation.
 */
@Suppress("LargeClass")
public open class TagStyleDelegateImpl<E : HTMLElement>(
    skipUpdates: Boolean,
    onSetCallback: ((Map<String, Any>) -> Unit)? = null
) : TagStyleDelegate<E>, PropertyDelegate(onSetCallback, skipUpdates) {

    public override val stylesMap: Map<String, Any> by lazy { propertyValues }

    protected lateinit var element: E
    protected var elementNullable: E? = null

    override fun elementWithStyle(element: E?) {
        this.elementNullable = element
        if (!skipUpdates && element != null) {
            this.element = element
        }
    }

    override var width: CssSize? by updatingProperty {
        if (it != null) {
            element.style.width = it.toString()
        } else {
            element.style.removeProperty("width")
        }
    }

    @Composable
    override fun width(width: CssSize?): Unit = composableProperty("width", {
        this.width = null
    }) {
        this.width = width
    }

    override var minWidth: CssSize? by updatingProperty {
        if (it != null) {
            element.style.minWidth = it.toString()
        } else {
            element.style.removeProperty("min-width")
        }
    }

    @Composable
    override fun minWidth(minWidth: CssSize?): Unit = composableProperty("minWidth", {
        this.minWidth = null
    }) {
        this.minWidth = minWidth
    }

    override var maxWidth: CssSize? by updatingProperty {
        if (it != null) {
            element.style.maxWidth = it.toString()
        } else {
            element.style.removeProperty("max-width")
        }
    }

    @Composable
    override fun maxWidth(maxWidth: CssSize?): Unit = composableProperty("maxWidth", {
        this.maxWidth = null
    }) {
        this.maxWidth = maxWidth
    }

    override var height: CssSize? by updatingProperty {
        if (it != null) {
            element.style.height = it.toString()
        } else {
            element.style.removeProperty("height")
        }
    }

    @Composable
    override fun height(height: CssSize?): Unit = composableProperty("height", {
        this.height = null
    }) {
        this.height = height
    }

    override var minHeight: CssSize? by updatingProperty {
        if (it != null) {
            element.style.minHeight = it.toString()
        } else {
            element.style.removeProperty("min-height")
        }
    }

    @Composable
    override fun minHeight(minHeight: CssSize?): Unit = composableProperty("minHeight", {
        this.minHeight = null
    }) {
        this.minHeight = minHeight
    }

    override var maxHeight: CssSize? by updatingProperty {
        if (it != null) {
            element.style.maxHeight = it.toString()
        } else {
            element.style.removeProperty("max-height")
        }
    }

    @Composable
    override fun maxHeight(maxHeight: CssSize?): Unit = composableProperty("maxHeight", {
        this.maxHeight = null
    }) {
        this.maxHeight = maxHeight
    }

    override var display: Display? by updatingProperty {
        if (it != null) {
            element.style.display = it.value
        } else {
            element.style.removeProperty("display")
        }
    }

    @Composable
    override fun display(display: Display?): Unit = composableProperty("display", {
        this.display = null
    }) {
        this.display = display
    }

    override var position: Position? by updatingProperty {
        if (it != null) {
            element.style.position = it.value
        } else {
            element.style.removeProperty("position")
        }
    }

    @Composable
    override fun position(position: Position?): Unit = composableProperty("position", {
        this.position = null
    }) {
        this.position = position
    }

    override var top: CssSize? by updatingProperty {
        if (it != null) {
            element.style.top = it.toString()
        } else {
            element.style.removeProperty("top")
        }
    }

    @Composable
    override fun top(top: CssSize?): Unit = composableProperty("top", {
        this.top = null
    }) {
        this.top = top
    }

    override var left: CssSize? by updatingProperty {
        if (it != null) {
            element.style.left = it.toString()
        } else {
            element.style.removeProperty("left")
        }
    }

    @Composable
    override fun left(left: CssSize?): Unit = composableProperty("left", {
        this.left = null
    }) {
        this.left = left
    }

    override var right: CssSize? by updatingProperty {
        if (it != null) {
            element.style.right = it.toString()
        } else {
            element.style.removeProperty("right")
        }
    }

    @Composable
    override fun right(right: CssSize?): Unit = composableProperty("right", {
        this.right = null
    }) {
        this.right = right
    }

    override var bottom: CssSize? by updatingProperty {
        if (it != null) {
            element.style.bottom = it.toString()
        } else {
            element.style.removeProperty("bottom")
        }
    }

    @Composable
    override fun bottom(bottom: CssSize?): Unit = composableProperty("bottom", {
        this.bottom = null
    }) {
        this.bottom = bottom
    }

    override var zIndex: Int? by updatingProperty {
        if (it != null) {
            element.style.zIndex = it.toString()
        } else {
            element.style.removeProperty("z-index")
        }
    }

    @Composable
    override fun zIndex(zIndex: Int?): Unit = composableProperty("zIndex", {
        this.zIndex = null
    }) {
        this.zIndex = zIndex
    }

    override var overflow: Overflow? by updatingProperty {
        if (it != null) {
            element.style.setProperty("overflow", it.value)
        } else {
            element.style.removeProperty("overflow")
        }
    }

    @Composable
    override fun overflow(overflow: Overflow?): Unit = composableProperty("overflow", {
        this.overflow = null
    }) {
        this.overflow = overflow
    }

    override var overflowX: Overflow? by updatingProperty {
        if (it != null) {
            element.style.overflowX = it.value
        } else {
            element.style.removeProperty("overflow-x")
        }
    }

    @Composable
    override fun overflowX(overflowX: Overflow?): Unit = composableProperty("overflowX", {
        this.overflowX = null
    }) {
        this.overflowX = overflowX
    }

    override var overflowY: Overflow? by updatingProperty {
        if (it != null) {
            element.style.overflowY = it.value
        } else {
            element.style.removeProperty("overflow-y")
        }
    }

    @Composable
    override fun overflowY(overflowY: Overflow?): Unit = composableProperty("overflowY", {
        this.overflowY = null
    }) {
        this.overflowY = overflowY
    }

    override var overflowWrap: OverflowWrap? by updatingProperty {
        if (it != null) {
            element.style.overflowWrap = it.value
        } else {
            element.style.removeProperty("overflow-wrap")
        }
    }

    @Composable
    override fun overflowWrap(overflowWrap: OverflowWrap?): Unit = composableProperty("overflowWrap", {
        this.overflowWrap = null
    }) {
        this.overflowWrap = overflowWrap
    }

    override var resize: Resize? by updatingProperty {
        if (it != null) {
            element.style.resize = it.value
        } else {
            element.style.removeProperty("resize")
        }
    }

    @Composable
    override fun resize(resize: Resize?): Unit = composableProperty("resize", {
        this.resize = null
    }) {
        this.resize = resize
    }

    override var border: Border? by updatingProperty {
        if (it != null) {
            element.style.border = it.toString()
        } else {
            element.style.removeProperty("border")
        }
    }

    @Composable
    override fun border(border: Border?): Unit = composableProperty("border", {
        this.border = null
    }) {
        this.border = border
    }

    override var borderTop: Border? by updatingProperty {
        if (it != null) {
            element.style.borderTop = it.toString()
        } else {
            element.style.removeProperty("border-top")
        }
    }

    @Composable
    override fun borderTop(borderTop: Border?): Unit = composableProperty("borderTop", {
        this.borderTop = null
    }) {
        this.borderTop = borderTop
    }

    override var borderRight: Border? by updatingProperty {
        if (it != null) {
            element.style.borderRight = it.toString()
        } else {
            element.style.removeProperty("border-right")
        }
    }

    @Composable
    override fun borderRight(borderRight: Border?): Unit = composableProperty("borderRight", {
        this.borderRight = null
    }) {
        this.borderRight = borderRight
    }

    override var borderBottom: Border? by updatingProperty {
        if (it != null) {
            element.style.borderBottom = it.toString()
        } else {
            element.style.removeProperty("border-bottom")
        }
    }

    @Composable
    override fun borderBottom(borderBottom: Border?): Unit = composableProperty("borderBottom", {
        this.borderBottom = null
    }) {
        this.borderBottom = borderBottom
    }

    override var borderLeft: Border? by updatingProperty {
        if (it != null) {
            element.style.borderLeft = it.toString()
        } else {
            element.style.removeProperty("border-left")
        }
    }

    @Composable
    override fun borderLeft(borderLeft: Border?): Unit = composableProperty("borderLeft", {
        this.borderLeft = null
    }) {
        this.borderLeft = borderLeft
    }

    override var margin: CssSize? by updatingProperty {
        if (it != null) {
            element.style.margin = it.toString()
        } else {
            element.style.removeProperty("margin")
        }
    }

    @Composable
    override fun margin(margin: CssSize?): Unit = composableProperty("margin", {
        this.margin = null
    }) {
        this.margin = margin
    }

    override var marginTop: CssSize? by updatingProperty {
        if (it != null) {
            element.style.marginTop = it.toString()
        } else {
            element.style.removeProperty("margin-top")
        }
    }

    @Composable
    override fun marginTop(marginTop: CssSize?): Unit = composableProperty("marginTop", {
        this.marginTop = null
    }) {
        this.marginTop = marginTop
    }

    override var marginRight: CssSize? by updatingProperty {
        if (it != null) {
            element.style.marginRight = it.toString()
        } else {
            element.style.removeProperty("margin-right")
        }
    }

    @Composable
    override fun marginRight(marginRight: CssSize?): Unit = composableProperty("marginRight", {
        this.marginRight = null
    }) {
        this.marginRight = marginRight
    }

    override var marginBottom: CssSize? by updatingProperty {
        if (it != null) {
            element.style.marginBottom = it.toString()
        } else {
            element.style.removeProperty("margin-bottom")
        }
    }

    @Composable
    override fun marginBottom(marginBottom: CssSize?): Unit = composableProperty("marginBottom", {
        this.marginBottom = null
    }) {
        this.marginBottom = marginBottom
    }

    override var marginLeft: CssSize? by updatingProperty {
        if (it != null) {
            element.style.marginLeft = it.toString()
        } else {
            element.style.removeProperty("margin-left")
        }
    }

    @Composable
    override fun marginLeft(marginLeft: CssSize?): Unit = composableProperty("marginLeft", {
        this.marginLeft = null
    }) {
        this.marginLeft = marginLeft
    }

    override var padding: CssSize? by updatingProperty {
        if (it != null) {
            element.style.padding = it.toString()
        } else {
            element.style.removeProperty("padding")
        }
    }

    @Composable
    override fun padding(padding: CssSize?): Unit = composableProperty("padding", {
        this.padding = null
    }) {
        this.padding = padding
    }

    override var paddingTop: CssSize? by updatingProperty {
        if (it != null) {
            element.style.paddingTop = it.toString()
        } else {
            element.style.removeProperty("padding-top")
        }
    }

    @Composable
    override fun paddingTop(paddingTop: CssSize?): Unit = composableProperty("paddingTop", {
        this.paddingTop = null
    }) {
        this.paddingTop = paddingTop
    }

    override var paddingRight: CssSize? by updatingProperty {
        if (it != null) {
            element.style.paddingRight = it.toString()
        } else {
            element.style.removeProperty("padding-right")
        }
    }

    @Composable
    override fun paddingRight(paddingRight: CssSize?): Unit = composableProperty("paddingRight", {
        this.paddingRight = null
    }) {
        this.paddingRight = paddingRight
    }

    override var paddingBottom: CssSize? by updatingProperty {
        if (it != null) {
            element.style.paddingBottom = it.toString()
        } else {
            element.style.removeProperty("padding-bottom")
        }
    }

    @Composable
    override fun paddingBottom(paddingBottom: CssSize?): Unit = composableProperty("paddingBottom", {
        this.paddingBottom = null
    }) {
        this.paddingBottom = paddingBottom
    }

    override var paddingLeft: CssSize? by updatingProperty {
        if (it != null) {
            element.style.paddingLeft = it.toString()
        } else {
            element.style.removeProperty("padding-left")
        }
    }

    @Composable
    override fun paddingLeft(paddingLeft: CssSize?): Unit = composableProperty("paddingLeft", {
        this.paddingLeft = null
    }) {
        this.paddingLeft = paddingLeft
    }

    override var color: Color? by updatingProperty {
        if (it != null) {
            element.style.color = it.toString()
        } else {
            element.style.removeProperty("color")
        }
    }

    @Composable
    override fun color(color: Color?): Unit = composableProperty("color", {
        this.color = null
    }) {
        this.color = color
    }

    /**
     * Opacity of the current component.
     */
    override var opacity: Double? by updatingProperty {
        if (it != null) {
            element.style.opacity = it.toString()
        } else {
            element.style.removeProperty("opacity")
        }
    }

    @Composable
    override fun opacity(opacity: Double?): Unit = composableProperty("opacity", {
        this.opacity = null
    }) {
        this.opacity = opacity
    }

    override var background: Background? by updatingProperty {
        if (it != null) {
            element.style.background = it.toString()
        } else {
            element.style.removeProperty("background")
        }
    }

    @Composable
    override fun background(background: Background?): Unit = composableProperty("background", {
        this.background = null
    }) {
        this.background = background
    }

    override var direction: Direction? by updatingProperty {
        if (it != null) {
            element.style.direction = it.value
        } else {
            element.style.removeProperty("direction")
        }
    }

    @Composable
    override fun direction(direction: Direction?): Unit = composableProperty("direction", {
        this.direction = null
    }) {
        this.direction = direction
    }

    override var letterSpacing: CssSize? by updatingProperty {
        if (it != null) {
            element.style.letterSpacing = it.toString()
        } else {
            element.style.removeProperty("letter-spacing")
        }
    }

    @Composable
    override fun letterSpacing(letterSpacing: CssSize?): Unit = composableProperty("letterSpacing", {
        this.letterSpacing = null
    }) {
        this.letterSpacing = letterSpacing
    }

    override var lineHeight: CssSize? by updatingProperty {
        if (it != null) {
            element.style.lineHeight = it.toString()
        } else {
            element.style.removeProperty("line-height")
        }
    }

    @Composable
    override fun lineHeight(lineHeight: CssSize?): Unit = composableProperty("lineHeight", {
        this.lineHeight = null
    }) {
        this.lineHeight = lineHeight
    }

    override var textAlign: TextAlign? by updatingProperty {
        if (it != null) {
            element.style.textAlign = it.value
        } else {
            element.style.removeProperty("text-align")
        }
    }

    @Composable
    override fun textAlign(textAlign: TextAlign?): Unit = composableProperty("textAlign", {
        this.textAlign = null
    }) {
        this.textAlign = textAlign
    }

    override var textDecoration: TextDecoration? by updatingProperty {
        if (it != null) {
            element.style.textDecoration = it.toString()
        } else {
            element.style.removeProperty("text-decoration")
        }
    }

    @Composable
    override fun textDecoration(textDecoration: TextDecoration?): Unit = composableProperty("textDecoration", {
        this.textDecoration = null
    }) {
        this.textDecoration = textDecoration
    }

    override var textIndent: CssSize? by updatingProperty {
        if (it != null) {
            element.style.textIndent = it.toString()
        } else {
            element.style.removeProperty("text-indent")
        }
    }

    @Composable
    override fun textIndent(textIndent: CssSize?): Unit = composableProperty("textIndent", {
        this.textIndent = null
    }) {
        this.textIndent = textIndent
    }

    override var textShadow: TextShadow? by updatingProperty {
        if (it != null) {
            element.style.textShadow = it.toString()
        } else {
            element.style.removeProperty("text-shadow")
        }
    }

    @Composable
    override fun textShadow(textShadow: TextShadow?): Unit = composableProperty("textShadow", {
        this.textShadow = null
    }) {
        this.textShadow = textShadow
    }

    override var textTransform: TextTransform? by updatingProperty {
        if (it != null) {
            element.style.textTransform = it.value
        } else {
            element.style.removeProperty("text-transform")
        }
    }

    @Composable
    override fun textTransform(textTransform: TextTransform?): Unit = composableProperty("textTransform", {
        this.textTransform = null
    }) {
        this.textTransform = textTransform
    }

    override var textOverflow: TextOverflow? by updatingProperty {
        if (it != null) {
            element.style.textOverflow = it.value
        } else {
            element.style.removeProperty("text-overflow")
        }
    }

    @Composable
    override fun textOverflow(textOverflow: TextOverflow?): Unit = composableProperty("textOverflow", {
        this.textOverflow = null
    }) {
        this.textOverflow = textOverflow
    }

    override var unicodeBidi: UnicodeBidi? by updatingProperty {
        if (it != null) {
            element.style.unicodeBidi = it.value
        } else {
            element.style.removeProperty("unicode-bidi")
        }
    }

    @Composable
    override fun unicodeBidi(unicodeBidi: UnicodeBidi?): Unit = composableProperty("unicodeBidi", {
        this.unicodeBidi = null
    }) {
        this.unicodeBidi = unicodeBidi
    }

    override var verticalAlign: VerticalAlign? by updatingProperty {
        if (it != null) {
            element.style.verticalAlign = it.value
        } else {
            element.style.removeProperty("vertical-align")
        }
    }

    @Composable
    override fun verticalAlign(verticalAlign: VerticalAlign?): Unit = composableProperty("verticalAlign", {
        this.verticalAlign = null
    }) {
        this.verticalAlign = verticalAlign
    }

    override var whiteSpace: WhiteSpace? by updatingProperty {
        if (it != null) {
            element.style.whiteSpace = it.value
        } else {
            element.style.removeProperty("white-space")
        }
    }

    @Composable
    override fun whiteSpace(whiteSpace: WhiteSpace?): Unit = composableProperty("whiteSpace", {
        this.whiteSpace = null
    }) {
        this.whiteSpace = whiteSpace
    }

    override var wordSpacing: CssSize? by updatingProperty {
        if (it != null) {
            element.style.wordSpacing = it.toString()
        } else {
            element.style.removeProperty("word-spacing")
        }
    }

    @Composable
    override fun wordSpacing(wordSpacing: CssSize?): Unit = composableProperty("wordSpacing", {
        this.wordSpacing = null
    }) {
        this.wordSpacing = wordSpacing
    }

    override var fontFamily: String? by updatingProperty {
        if (it != null) {
            element.style.fontFamily = it
        } else {
            element.style.removeProperty("font-family")
        }
    }

    @Composable
    override fun fontFamily(fontFamily: String?): Unit = composableProperty("fontFamily", {
        this.fontFamily = null
    }) {
        this.fontFamily = fontFamily
    }

    override var fontSize: CssSize? by updatingProperty {
        if (it != null) {
            element.style.fontSize = it.toString()
        } else {
            element.style.removeProperty("font-size")
        }
    }

    @Composable
    override fun fontSize(fontSize: CssSize?): Unit = composableProperty("fontSize", {
        this.fontSize = null
    }) {
        this.fontSize = fontSize
    }

    override var fontStyle: FontStyle? by updatingProperty {
        if (it != null) {
            element.style.fontStyle = it.value
        } else {
            element.style.removeProperty("font-style")
        }
    }

    @Composable
    override fun fontStyle(fontStyle: FontStyle?): Unit = composableProperty("fontStyle", {
        this.fontStyle = null
    }) {
        this.fontStyle = fontStyle
    }

    override var fontWeight: FontWeight? by updatingProperty {
        if (it != null) {
            element.style.fontWeight = it.value
        } else {
            element.style.removeProperty("font-weight")
        }
    }

    @Composable
    override fun fontWeight(fontWeight: FontWeight?): Unit = composableProperty("fontWeight", {
        this.fontWeight = null
    }) {
        this.fontWeight = fontWeight
    }

    override var fontVariant: FontVariant? by updatingProperty {
        if (it != null) {
            element.style.fontVariant = it.value
        } else {
            element.style.removeProperty("font-variant")
        }
    }

    @Composable
    override fun fontVariant(fontVariant: FontVariant?): Unit = composableProperty("fontVariant", {
        this.fontVariant = null
    }) {
        this.fontVariant = fontVariant
    }

    override var float: CssFloat? by updatingProperty {
        if (it != null) {
            element.style.cssFloat = it.value
        } else {
            element.style.removeProperty("float")
        }
    }

    @Composable
    override fun float(float: CssFloat?): Unit = composableProperty("float", {
        this.float = null
    }) {
        this.float = float
    }

    override var clear: Clear? by updatingProperty {
        if (it != null) {
            element.style.clear = it.value
        } else {
            element.style.removeProperty("clear")
        }
    }

    @Composable
    override fun clear(clear: Clear?): Unit = composableProperty("clear", {
        this.clear = null
    }) {
        this.clear = clear
    }

    override var wordBreak: WordBreak? by updatingProperty {
        if (it != null) {
            element.style.wordBreak = it.value
        } else {
            element.style.removeProperty("word-break")
        }
    }

    @Composable
    override fun wordBreak(wordBreak: WordBreak?): Unit = composableProperty("wordBreak", {
        this.wordBreak = null
    }) {
        this.wordBreak = wordBreak
    }

    override var lineBreak: LineBreak? by updatingProperty {
        if (it != null) {
            element.style.lineBreak = it.value
        } else {
            element.style.removeProperty("line-break")
        }
    }

    @Composable
    override fun lineBreak(lineBreak: LineBreak?): Unit = composableProperty("lineBreak", {
        this.lineBreak = null
    }) {
        this.lineBreak = lineBreak
    }

    override var cursor: Cursor? by updatingProperty {
        if (it != null) {
            element.style.cursor = it.value
        } else {
            element.style.removeProperty("cursor")
        }
    }

    @Composable
    override fun cursor(cursor: Cursor?): Unit = composableProperty("cursor", {
        this.cursor = null
    }) {
        this.cursor = cursor
    }

    override var flexDirection: FlexDirection? by updatingProperty {
        if (it != null) {
            element.style.flexDirection = it.value
        } else {
            element.style.removeProperty("flex-direction")
        }
    }

    @Composable
    override fun flexDirection(flexDirection: FlexDirection?): Unit = composableProperty("flexDirection", {
        this.flexDirection = null
    }) {
        this.flexDirection = flexDirection
    }

    override var flexWrap: FlexWrap? by updatingProperty {
        if (it != null) {
            element.style.flexWrap = it.value
        } else {
            element.style.removeProperty("flex-wrap")
        }
    }

    @Composable
    override fun flexWrap(flexWrap: FlexWrap?): Unit = composableProperty("flexWrap", {
        this.flexWrap = null
    }) {
        this.flexWrap = flexWrap
    }

    override var justifyItems: JustifyItems? by updatingProperty {
        if (it != null) {
            element.style.setProperty("justify-items", it.value)
        } else {
            element.style.removeProperty("justify-items")
        }
    }

    @Composable
    override fun justifyItems(justifyItems: JustifyItems?): Unit = composableProperty("justifyItems", {
        this.justifyItems = null
    }) {
        this.justifyItems = justifyItems
    }

    override var justifyContent: JustifyContent? by updatingProperty {
        if (it != null) {
            element.style.justifyContent = it.value
        } else {
            element.style.removeProperty("justify-content")
        }
    }

    @Composable
    override fun justifyContent(justifyContent: JustifyContent?): Unit = composableProperty("justifyContent", {
        this.justifyContent = null
    }) {
        this.justifyContent = justifyContent
    }

    override var alignItems: AlignItems? by updatingProperty {
        if (it != null) {
            element.style.alignItems = it.value
        } else {
            element.style.removeProperty("align-items")
        }
    }

    @Composable
    override fun alignItems(alignItems: AlignItems?): Unit = composableProperty("alignItems", {
        this.alignItems = null
    }) {
        this.alignItems = alignItems
    }

    override var alignContent: AlignContent? by updatingProperty {
        if (it != null) {
            element.style.alignContent = it.value
        } else {
            element.style.removeProperty("align-content")
        }
    }

    @Composable
    override fun alignContent(alignContent: AlignContent?): Unit = composableProperty("alignContent", {
        this.alignContent = null
    }) {
        this.alignContent = alignContent
    }

    override var order: Int? by updatingProperty {
        if (it != null) {
            element.style.order = it.toString()
        } else {
            element.style.removeProperty("order")
        }
    }

    @Composable
    override fun order(order: Int?): Unit = composableProperty("order", {
        this.order = null
    }) {
        this.order = order
    }

    override var flexGrow: Int? by updatingProperty {
        if (it != null) {
            element.style.flexGrow = it.toString()
        } else {
            element.style.removeProperty("flex-grow")
        }
    }

    @Composable
    override fun flexGrow(flexGrow: Int?): Unit = composableProperty("flexGrow", {
        this.flexGrow = null
    }) {
        this.flexGrow = flexGrow
    }

    override var flexShrink: Int? by updatingProperty {
        if (it != null) {
            element.style.flexShrink = it.toString()
        } else {
            element.style.removeProperty("flex-shrink")
        }
    }

    @Composable
    override fun flexShrink(flexShrink: Int?): Unit = composableProperty("flexShrink", {
        this.flexShrink = null
    }) {
        this.flexShrink = flexShrink
    }

    override var flexBasis: CssSize? by updatingProperty {
        if (it != null) {
            element.style.flexBasis = it.toString()
        } else {
            element.style.removeProperty("flex-basis")
        }
    }

    @Composable
    override fun flexBasis(flexBasis: CssSize?): Unit = composableProperty("flexBasis", {
        this.flexBasis = null
    }) {
        this.flexBasis = flexBasis
    }

    override var alignSelf: AlignItems? by updatingProperty {
        if (it != null) {
            element.style.alignSelf = it.value
        } else {
            element.style.removeProperty("align-self")
        }
    }

    @Composable
    override fun alignSelf(alignSelf: AlignItems?): Unit = composableProperty("alignSelf", {
        this.alignSelf = null
    }) {
        this.alignSelf = alignSelf
    }

    override var justifySelf: JustifyItems? by updatingProperty {
        if (it != null) {
            element.style.setProperty("justify-self", it.value)
        } else {
            element.style.removeProperty("justify-self")
        }
    }

    @Composable
    override fun justifySelf(justifySelf: JustifyItems?): Unit = composableProperty("justifySelf", {
        this.justifySelf = null
    }) {
        this.justifySelf = justifySelf
    }

    override var gridAutoColumns: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-auto-columns", it)
        } else {
            element.style.removeProperty("grid-auto-columns")
        }
    }

    @Composable
    override fun gridAutoColumns(gridAutoColumns: String?): Unit = composableProperty("gridAutoColumns", {
        this.gridAutoColumns = null
    }) {
        this.gridAutoColumns = gridAutoColumns
    }

    override var gridAutoRows: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-auto-rows", it)
        } else {
            element.style.removeProperty("grid-auto-rows")
        }
    }

    @Composable
    override fun gridAutoRows(gridAutoRows: String?): Unit = composableProperty("gridAutoRows", {
        this.gridAutoRows = null
    }) {
        this.gridAutoRows = gridAutoRows
    }

    override var gridAutoFlow: GridAutoFlow? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-auto-flow", it.value)
        } else {
            element.style.removeProperty("grid-auto-flow")
        }
    }

    @Composable
    override fun gridAutoFlow(gridAutoFlow: GridAutoFlow?): Unit = composableProperty("gridAutoFlow", {
        this.gridAutoFlow = null
    }) {
        this.gridAutoFlow = gridAutoFlow
    }

    override var gridTemplateColumns: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-template-columns", it)
        } else {
            element.style.removeProperty("grid-template-columns")
        }
    }

    @Composable
    override fun gridTemplateColumns(gridTemplateColumns: String?): Unit = composableProperty("gridTemplateColumns", {
        this.gridTemplateColumns = null
    }) {
        this.gridTemplateColumns = gridTemplateColumns
    }

    override var gridTemplateRows: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-template-rows", it)
        } else {
            element.style.removeProperty("grid-template-rows")
        }
    }

    @Composable
    override fun gridTemplateRows(gridTemplateRows: String?): Unit = composableProperty("gridTemplateRows", {
        this.gridTemplateRows = null
    }) {
        this.gridTemplateRows = gridTemplateRows
    }

    override var gridTemplateAreas: List<String>? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-template-areas", it.joinToString(" ") { "\"" + it + "\"" })
        } else {
            element.style.removeProperty("grid-template-areas")
        }
    }

    @Composable
    override fun gridTemplateAreas(gridTemplateAreas: List<String>?): Unit = composableProperty("gridTemplateAreas", {
        this.gridTemplateAreas = null
    }) {
        this.gridTemplateAreas = gridTemplateAreas
    }


    override var gridTemplate: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-template", it)
        } else {
            element.style.removeProperty("grid-template")
        }
    }

    @Composable
    override fun gridTemplate(gridTemplate: String?): Unit = composableProperty("gridTemplate", {
        this.gridTemplate = null
    }) {
        this.gridTemplate = gridTemplate
    }

    override var columnGap: CssSize? by updatingProperty {
        if (it != null) {
            element.style.setProperty("column-gap", it.toString())
        } else {
            element.style.removeProperty("column-gap")
        }
    }

    @Composable
    override fun columnGap(columnGap: CssSize?): Unit = composableProperty("columnGap", {
        this.columnGap = null
    }) {
        this.columnGap = columnGap
    }

    override var rowGap: CssSize? by updatingProperty {
        if (it != null) {
            element.style.setProperty("row-gap", it.toString())
        } else {
            element.style.removeProperty("row-gap")
        }
    }

    @Composable
    override fun rowGap(rowGap: CssSize?): Unit = composableProperty("rowGap", {
        this.rowGap = null
    }) {
        this.rowGap = rowGap
    }

    override var gridColumnStart: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-column-start", it)
        } else {
            element.style.removeProperty("grid-column-start")
        }
    }

    @Composable
    override fun gridColumnStart(gridColumnStart: String?): Unit = composableProperty("gridColumnStart", {
        this.gridColumnStart = null
    }) {
        this.gridColumnStart = gridColumnStart
    }

    override var gridRowStart: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-row-start", it)
        } else {
            element.style.removeProperty("grid-row-start")
        }
    }

    @Composable
    override fun gridRowStart(gridRowStart: String?): Unit = composableProperty("gridRowStart", {
        this.gridRowStart = null
    }) {
        this.gridRowStart = gridRowStart
    }

    override var gridColumnEnd: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-column-end", it)
        } else {
            element.style.removeProperty("grid-column-end")
        }
    }

    @Composable
    override fun gridColumnEnd(gridColumnEnd: String?): Unit = composableProperty("gridColumnEnd", {
        this.gridColumnEnd = null
    }) {
        this.gridColumnEnd = gridColumnEnd
    }

    override var gridRowEnd: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-row-end", it)
        } else {
            element.style.removeProperty("grid-row-end")
        }
    }

    @Composable
    override fun gridRowEnd(gridRowEnd: String?): Unit = composableProperty("gridRowEnd", {
        this.gridRowEnd = null
    }) {
        this.gridRowEnd = gridRowEnd
    }


    override var gridColumn: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-column", it)
        } else {
            element.style.removeProperty("grid-column")
        }
    }

    @Composable
    override fun gridColumn(gridColumn: String?): Unit = composableProperty("gridColumn", {
        this.gridColumn = null
    }) {
        this.gridColumn = gridColumn
    }

    override var gridRow: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-row", it)
        } else {
            element.style.removeProperty("grid-row")
        }
    }

    @Composable
    override fun gridRow(gridRow: String?): Unit = composableProperty("gridRow", {
        this.gridRow = null
    }) {
        this.gridRow = gridRow
    }

    override var gridArea: String? by updatingProperty {
        if (it != null) {
            element.style.setProperty("grid-area", it)
        } else {
            element.style.removeProperty("grid-area")
        }
    }

    @Composable
    override fun gridArea(gridArea: String?): Unit = composableProperty("gridArea", {
        this.gridArea = null
    }) {
        this.gridArea = gridArea
    }

    override var outline: Outline? by updatingProperty {
        if (it != null) {
            element.style.outline = it.toString()
        } else {
            element.style.removeProperty("outline")
        }
    }

    @Composable
    override fun outline(outline: Outline?): Unit = composableProperty("outline", {
        this.outline = null
    }) {
        this.outline = outline
    }

    override var boxShadow: BoxShadow? by updatingProperty {
        if (it != null && boxShadowList != null) boxShadowList = null
        if (it != null) {
            elementNullable?.style?.boxShadow = it.toString()
        } else {
            elementNullable?.style?.removeProperty("box-shadow")
        }
    }

    @Composable
    override fun boxShadow(boxShadow: BoxShadow?): Unit = composableProperty("boxShadow", {
        this.boxShadow = null
    }) {
        this.boxShadow = boxShadow
    }

    override var boxShadowList: List<BoxShadow>? by updatingProperty {
        if (it != null && boxShadow != null) boxShadow = null
        val value = it?.joinToString { s -> s.toString() }
        if (value != null) {
            elementNullable?.style?.boxShadow = value
        } else {
            elementNullable?.style?.removeProperty("box-shadow")
        }
    }

    @Composable
    override fun boxShadowList(boxShadowList: List<BoxShadow>?): Unit = composableProperty("boxShadowList", {
        this.boxShadowList = null
    }) {
        this.boxShadowList = boxShadowList
    }

    override var transition: Transition? by updatingProperty {
        if (it != null && transitionList != null) transitionList = null
        if (it != null) {
            elementNullable?.style?.transition = it.toString()
        } else {
            elementNullable?.style?.removeProperty("transition")
        }
    }

    @Composable
    override fun transition(transition: Transition?): Unit = composableProperty("transition", {
        this.transition = null
    }) {
        this.transition = transition
    }

    override var transitionList: List<Transition>? by updatingProperty {
        if (it != null && transition != null) transition = null
        val value = it?.joinToString { s -> s.toString() }
        if (value != null) {
            elementNullable?.style?.transition = value
        } else {
            elementNullable?.style?.removeProperty("transition")
        }
    }

    @Composable
    override fun transitionList(transitionList: List<Transition>?): Unit = composableProperty("transitionList", {
        this.transitionList = null
    }) {
        this.transitionList = transitionList
    }

    override var borderRadius: CssSize? by updatingProperty {
        if (it != null && borderRadiusList != null) borderRadiusList = null
        if (it != null) {
            elementNullable?.style?.borderRadius = it.toString()
        } else {
            elementNullable?.style?.removeProperty("border-radius")
        }
    }

    @Composable
    override fun borderRadius(borderRadius: CssSize?): Unit = composableProperty("borderRadius", {
        this.borderRadius = null
    }) {
        this.borderRadius = borderRadius
    }

    override var borderRadiusList: List<CssSize>? by updatingProperty {
        if (it != null && borderRadius != null) borderRadius = null
        val value = it?.joinToString(" ") { s -> s.toString() }
        if (value != null) {
            elementNullable?.style?.borderRadius = value
        } else {
            elementNullable?.style?.removeProperty("border-radius")
        }
    }

    @Composable
    override fun borderRadiusList(borderRadiusList: List<CssSize>?): Unit = composableProperty("borderRadiusList", {
        this.borderRadiusList = null
    }) {
        this.borderRadiusList = borderRadiusList
    }

    override var listStyle: ListStyle? by updatingProperty {
        if (it != null) {
            element.style.listStyle = it.toString()
        } else {
            element.style.removeProperty("list-style")
        }
    }

    @Composable
    override fun listStyle(listStyle: ListStyle?): Unit = composableProperty("listStyle", {
        this.listStyle = null
    }) {
        this.listStyle = listStyle
    }

    override fun setStyle(name: String, value: String?) {
        if (propertyValues[name] != value) {
            if (value != null) {
                propertyValues[name] = value
                elementNullable?.style?.setProperty(name, value)
            } else {
                propertyValues.remove(name)
                elementNullable?.style?.removeProperty(name)
            }
            onSetCallback?.invoke(propertyValues)
        }
    }

    override fun getStyle(name: String): String? {
        return propertyValues[name]?.cast()
    }

    override fun removeStyle(name: String) {
        if (propertyValues[name] != null) {
            propertyValues.remove(name)
            elementNullable?.style?.removeProperty(name)
        }
    }

    @Composable
    override fun style(name: String, value: String?): Unit = composableProperty(name, {
        removeStyle(name)
    }) {
        setStyle(name, value)
    }
}
