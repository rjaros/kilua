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

import dev.kilua.core.PropertyDelegate
import dev.kilua.html.*
import dev.kilua.utils.asString
import dev.kilua.utils.cast
import org.w3c.dom.HTMLElement

public open class TagStyleDelegate<E : HTMLElement>(
    protected val skipUpdates: Boolean,
    protected val styles: MutableMap<String, Any>
) : TagStyle<E>, PropertyDelegate(styles) {

    protected lateinit var element: E
    protected var elementNullable: E? = null

    override fun elementWithStyle(element: E?) {
        this.elementNullable = element
        if (!skipUpdates && element != null) {
            this.element = element
        }
    }

    override var width: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.width = it.asString()
        } else {
            element.style.removeProperty("width")
        }
    }

    override var minWidth: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.minWidth = it.asString()
        } else {
            element.style.removeProperty("min-width")
        }
    }

    override var maxWidth: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.maxWidth = it.asString()
        } else {
            element.style.removeProperty("max-width")
        }
    }

    override var height: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.height = it.asString()
        } else {
            element.style.removeProperty("height")
        }
    }

    override var minHeight: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.minHeight = it.asString()
        } else {
            element.style.removeProperty("min-height")
        }
    }

    override var maxHeight: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.maxHeight = it.asString()
        } else {
            element.style.removeProperty("max-height")
        }
    }

    override var display: Display? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.display = it.value
        } else {
            element.style.removeProperty("display")
        }
    }

    override var position: Position? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.position = it.value
        } else {
            element.style.removeProperty("position")
        }
    }

    override var top: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.top = it.asString()
        } else {
            element.style.removeProperty("top")
        }
    }

    override var left: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.left = it.asString()
        } else {
            element.style.removeProperty("left")
        }
    }

    override var right: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.right = it.asString()
        } else {
            element.style.removeProperty("right")
        }
    }

    override var bottom: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.bottom = it.asString()
        } else {
            element.style.removeProperty("bottom")
        }
    }

    override var zIndex: Int? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.zIndex = it.toString()
        } else {
            element.style.removeProperty("z-index")
        }
    }

    override var overflow: Overflow? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("overflow", it.value)
        } else {
            element.style.removeProperty("overflow")
        }
    }

    override var overflowX: Overflow? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.overflowX = it.value
        } else {
            element.style.removeProperty("overflow-x")
        }
    }

    override var overflowY: Overflow? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.overflowY = it.value
        } else {
            element.style.removeProperty("overflow-y")
        }
    }

    override var overflowWrap: OverflowWrap? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.overflowWrap = it.value
        } else {
            element.style.removeProperty("overflow-wrap")
        }
    }

    override var resize: Resize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.resize = it.value
        } else {
            element.style.removeProperty("resize")
        }
    }

    override var border: Border? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.border = it.asString()
        } else {
            element.style.removeProperty("border")
        }
    }

    override var borderTop: Border? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.borderTop = it.asString()
        } else {
            element.style.removeProperty("border-top")
        }
    }

    override var borderRight: Border? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.borderRight = it.asString()
        } else {
            element.style.removeProperty("border-right")
        }
    }

    override var borderBottom: Border? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.borderBottom = it.asString()
        } else {
            element.style.removeProperty("border-bottom")
        }
    }

    override var borderLeft: Border? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.borderLeft = it.asString()
        } else {
            element.style.removeProperty("border-left")
        }
    }

    override var margin: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.margin = it.asString()
        } else {
            element.style.removeProperty("margin")
        }
    }

    override var marginTop: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.marginTop = it.asString()
        } else {
            element.style.removeProperty("margin-top")
        }
    }

    override var marginRight: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.marginRight = it.asString()
        } else {
            element.style.removeProperty("margin-right")
        }
    }

    override var marginBottom: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.marginBottom = it.asString()
        } else {
            element.style.removeProperty("margin-bottom")
        }
    }

    override var marginLeft: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.marginLeft = it.asString()
        } else {
            element.style.removeProperty("margin-left")
        }
    }

    override var padding: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.padding = it.asString()
        } else {
            element.style.removeProperty("padding")
        }
    }

    override var paddingTop: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.paddingTop = it.asString()
        } else {
            element.style.removeProperty("padding-top")
        }
    }

    override var paddingRight: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.paddingRight = it.asString()
        } else {
            element.style.removeProperty("padding-right")
        }
    }

    override var paddingBottom: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.paddingBottom = it.asString()
        } else {
            element.style.removeProperty("padding-bottom")
        }
    }

    override var paddingLeft: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.paddingLeft = it.asString()
        } else {
            element.style.removeProperty("padding-left")
        }
    }

    override var color: Color? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.color = it.asString()
        } else {
            element.style.removeProperty("color")
        }
    }

    /**
     * Text color for the current component given in hex format (write only).
     *
     * This property gives a convenient way to set the value of [color] property e.g.:
     *
     * c.colorHex = 0x00ff00
     *
     * The value read from this property is always null.
     */
    override var colorHex: Int?
        get() = null
        set(value) {
            color = if (value != null) Color.hex(value) else null
        }

    /**
     * Text color for the current component given with named constant (write only).
     *
     * This property gives a convenient way to set the value of [color] property e.g.:
     *
     * c.colorName = Col.Green
     *
     * The value read from this property is always null.
     */
    override var colorName: Col?
        get() = null
        set(value) {
            color = if (value != null) Color.name(value) else null
        }

    /**
     * Opacity of the current component.
     */
    override var opacity: Double? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.opacity = it.toString()
        } else {
            element.style.removeProperty("opacity")
        }
    }


    override var background: Background? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.background = it.asString()
        } else {
            element.style.removeProperty("background")
        }
    }

    override var direction: Direction? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.direction = it.value
        } else {
            element.style.removeProperty("direction")
        }
    }

    override var letterSpacing: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.letterSpacing = it.asString()
        } else {
            element.style.removeProperty("letter-spacing")
        }
    }

    override var lineHeight: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.lineHeight = it.asString()
        } else {
            element.style.removeProperty("line-height")
        }
    }

    override var textAlign: TextAlign? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textAlign = it.value
        } else {
            element.style.removeProperty("text-align")
        }
    }

    override var textDecoration: TextDecoration? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textDecoration = it.asString()
        } else {
            element.style.removeProperty("text-decoration")
        }
    }

    override var textIndent: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textIndent = it.asString()
        } else {
            element.style.removeProperty("text-indent")
        }
    }

    override var textShadow: TextShadow? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textShadow = it.asString()
        } else {
            element.style.removeProperty("text-shadow")
        }
    }

    override var textTransform: TextTransform? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textTransform = it.value
        } else {
            element.style.removeProperty("text-transform")
        }
    }

    override var textOverflow: TextOverflow? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textOverflow = it.value
        } else {
            element.style.removeProperty("text-overflow")
        }
    }

    override var unicodeBidi: UnicodeBidi? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.unicodeBidi = it.value
        } else {
            element.style.removeProperty("unicode-bidi")
        }
    }

    override var verticalAlign: VerticalAlign? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.verticalAlign = it.value
        } else {
            element.style.removeProperty("vertical-align")
        }
    }

    override var whiteSpace: WhiteSpace? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.whiteSpace = it.value
        } else {
            element.style.removeProperty("white-space")
        }
    }

    override var wordSpacing: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.wordSpacing = it.asString()
        } else {
            element.style.removeProperty("word-spacing")
        }
    }

    override var fontFamily: String? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.fontFamily = it
        } else {
            element.style.removeProperty("font-family")
        }
    }

    override var fontSize: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.fontSize = it.asString()
        } else {
            element.style.removeProperty("font-size")
        }
    }

    override var fontStyle: FontStyle? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.fontStyle = it.value
        } else {
            element.style.removeProperty("font-style")
        }
    }

    override var fontWeight: FontWeight? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.fontWeight = it.value
        } else {
            element.style.removeProperty("font-weight")
        }
    }

    override var fontVariant: FontVariant? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.fontVariant = it.value
        } else {
            element.style.removeProperty("font-variant")
        }
    }

    override var float: CssFloat? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.cssFloat = it.value
        } else {
            element.style.removeProperty("float")
        }
    }

    override var clear: Clear? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.clear = it.value
        } else {
            element.style.removeProperty("clear")
        }
    }

    override var wordBreak: WordBreak? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.wordBreak = it.value
        } else {
            element.style.removeProperty("word-break")
        }
    }

    override var lineBreak: LineBreak? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.lineBreak = it.value
        } else {
            element.style.removeProperty("line-break")
        }
    }

    override var cursor: Cursor? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.cursor = it.value
        } else {
            element.style.removeProperty("cursor")
        }
    }

    override var flexDirection: FlexDirection? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.flexDirection = it.value
        } else {
            element.style.removeProperty("flex-direction")
        }
    }

    override var flexWrap: FlexWrap? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.flexWrap = it.value
        } else {
            element.style.removeProperty("flex-wrap")
        }
    }

    override var justifyItems: JustifyItems? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("justify-items", it.value)
        } else {
            element.style.removeProperty("justify-items")
        }
    }

    override var justifyContent: JustifyContent? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.justifyContent = it.value
        } else {
            element.style.removeProperty("justify-content")
        }
    }

    override var alignItems: AlignItems? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.alignItems = it.value
        } else {
            element.style.removeProperty("align-items")
        }
    }

    override var alignContent: AlignContent? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.alignContent = it.value
        } else {
            element.style.removeProperty("align-content")
        }
    }

    override var order: Int? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.order = it.toString()
        } else {
            element.style.removeProperty("order")
        }
    }

    override var flexGrow: Int? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.flexGrow = it.toString()
        } else {
            element.style.removeProperty("flex-grow")
        }
    }

    override var flexShrink: Int? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.flexShrink = it.toString()
        } else {
            element.style.removeProperty("flex-shrink")
        }
    }

    override var flexBasis: CssSize? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.flexBasis = it.asString()
        } else {
            element.style.removeProperty("flex-basis")
        }
    }

    override var alignSelf: AlignItems? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.alignSelf = it.value
        } else {
            element.style.removeProperty("align-self")
        }
    }

    override var justifySelf: JustifyItems? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("justify-self", it.value)
        } else {
            element.style.removeProperty("justify-self")
        }
    }

    override var gridAutoColumns: String? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-auto-columns", it)
        } else {
            element.style.removeProperty("grid-auto-columns")
        }
    }

    override var gridAutoRows: String? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-auto-rows", it)
        } else {
            element.style.removeProperty("grid-auto-rows")
        }
    }

    override var gridAutoFlow: GridAutoFlow? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-auto-flow", it.value)
        } else {
            element.style.removeProperty("grid-auto-flow")
        }
    }

    override var gridTemplateColumns: String? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-template-columns", it)
        } else {
            element.style.removeProperty("grid-template-columns")
        }
    }

    override var gridTemplateRows: String? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-template-rows", it)
        } else {
            element.style.removeProperty("grid-template-rows")
        }
    }

    override var gridTemplateAreas: List<String>? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-template-areas", it.joinToString("\n"))
        } else {
            element.style.removeProperty("grid-template-areas")
        }
    }

    override var gridColumnGap: Int? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-column-gap", "${it}px")
        } else {
            element.style.removeProperty("grid-column-gap")
        }
    }

    override var gridRowGap: Int? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-row-gap", "${it}px")
        } else {
            element.style.removeProperty("grid-row-gap")
        }
    }

    override var gridColumnStart: Int? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-column-start", it.toString())
        } else {
            element.style.removeProperty("grid-column-start")
        }
    }

    override var gridRowStart: Int? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-row-start", it.toString())
        } else {
            element.style.removeProperty("grid-row-start")
        }
    }

    override var gridColumnEnd: String? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-column-end", it)
        } else {
            element.style.removeProperty("grid-column-end")
        }
    }

    override var gridRowEnd: String? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-row-end", it)
        } else {
            element.style.removeProperty("grid-row-end")
        }
    }

    override var gridArea: String? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-area", it)
        } else {
            element.style.removeProperty("grid-area")
        }
    }

    override var outline: Outline? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.outline = it.asString()
        } else {
            element.style.removeProperty("outline")
        }
    }

    override var boxShadow: BoxShadow? by unmanagedProperty {
        if (it != null && boxShadowList != null) boxShadowList = null
        if (it != null) {
            elementNullable?.style?.boxShadow = it.asString()
        } else {
            elementNullable?.style?.removeProperty("box-shadow")
        }
    }

    override var boxShadowList: List<BoxShadow>? by unmanagedProperty {
        if (it != null && boxShadow != null) boxShadow = null
        val value = it?.joinToString { s -> s.asString() }
        if (value != null) {
            elementNullable?.style?.boxShadow = value
        } else {
            elementNullable?.style?.removeProperty("box-shadow")
        }
    }

    override var transition: Transition? by unmanagedProperty {
        if (it != null && transitionList != null) transitionList = null
        if (it != null) {
            elementNullable?.style?.transition = it.asString()
        } else {
            elementNullable?.style?.removeProperty("transition")
        }
    }

    override var transitionList: List<Transition>? by unmanagedProperty {
        if (it != null && transition != null) transition = null
        val value = it?.joinToString { s -> s.asString() }
        if (value != null) {
            elementNullable?.style?.transition = value
        } else {
            elementNullable?.style?.removeProperty("transition")
        }
    }

    override var borderRadius: CssSize? by unmanagedProperty {
        if (it != null && borderRadiusList != null) borderRadiusList = null
        if (it != null) {
            elementNullable?.style?.borderRadius = it.asString()
        } else {
            elementNullable?.style?.removeProperty("border-radius")
        }
    }

    override var borderRadiusList: List<CssSize>? by unmanagedProperty {
        if (it != null && borderRadius != null) borderRadius = null
        val value = it?.joinToString(" ") { s -> s.asString() }
        if (value != null) {
            elementNullable?.style?.borderRadius = value
        } else {
            elementNullable?.style?.removeProperty("border-radius")
        }
    }

    override var listStyle: ListStyle? by unmanagedProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.listStyle = it.asString()
        } else {
            element.style.removeProperty("list-style")
        }
    }

    override fun setStyle(name: String, value: String?) {
        if (styles[name] != value) {
            if (value != null) {
                styles[name] = value
                elementNullable?.style?.setProperty(name, value)
            } else {
                styles.remove(name)
                elementNullable?.style?.removeProperty(name)
            }
        }
    }

    override fun getStyle(name: String): String? {
        return styles[name]?.cast()
    }

    override fun removeStyle(name: String) {
        if (styles[name] != null) {
            styles.remove(name)
            elementNullable?.style?.removeProperty(name)
        }
    }
}
