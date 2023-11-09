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

import dev.kilua.core.PropertyDelegate
import dev.kilua.html.*
import dev.kilua.utils.cast
import dev.kilua.utils.nativeMapOf
import org.w3c.dom.HTMLElement
import kotlin.collections.Map

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
    protected val skipUpdates: Boolean,
    onSetCallback: ((Map<String, Any>) -> Unit)? = null
) : TagStyleDelegate<E>, PropertyDelegate(nativeMapOf(), onSetCallback) {

    public override val stylesMap: Map<String, Any> = propertyValues

    protected lateinit var element: E
    protected var elementNullable: E? = null

    override fun elementWithStyle(element: E?) {
        this.elementNullable = element
        if (!skipUpdates && element != null) {
            this.element = element
        }
    }

    override var width: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.width = it.toString()
        } else {
            element.style.removeProperty("width")
        }
    }

    override var minWidth: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.minWidth = it.toString()
        } else {
            element.style.removeProperty("min-width")
        }
    }

    override var maxWidth: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.maxWidth = it.toString()
        } else {
            element.style.removeProperty("max-width")
        }
    }

    override var height: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.height = it.toString()
        } else {
            element.style.removeProperty("height")
        }
    }

    override var minHeight: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.minHeight = it.toString()
        } else {
            element.style.removeProperty("min-height")
        }
    }

    override var maxHeight: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.maxHeight = it.toString()
        } else {
            element.style.removeProperty("max-height")
        }
    }

    override var display: Display? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.display = it.value
        } else {
            element.style.removeProperty("display")
        }
    }

    override var position: Position? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.position = it.value
        } else {
            element.style.removeProperty("position")
        }
    }

    override var top: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.top = it.toString()
        } else {
            element.style.removeProperty("top")
        }
    }

    override var left: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.left = it.toString()
        } else {
            element.style.removeProperty("left")
        }
    }

    override var right: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.right = it.toString()
        } else {
            element.style.removeProperty("right")
        }
    }

    override var bottom: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.bottom = it.toString()
        } else {
            element.style.removeProperty("bottom")
        }
    }

    override var zIndex: Int? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.zIndex = it.toString()
        } else {
            element.style.removeProperty("z-index")
        }
    }

    override var overflow: Overflow? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("overflow", it.value)
        } else {
            element.style.removeProperty("overflow")
        }
    }

    override var overflowX: Overflow? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.overflowX = it.value
        } else {
            element.style.removeProperty("overflow-x")
        }
    }

    override var overflowY: Overflow? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.overflowY = it.value
        } else {
            element.style.removeProperty("overflow-y")
        }
    }

    override var overflowWrap: OverflowWrap? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.overflowWrap = it.value
        } else {
            element.style.removeProperty("overflow-wrap")
        }
    }

    override var resize: Resize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.resize = it.value
        } else {
            element.style.removeProperty("resize")
        }
    }

    override var border: Border? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.border = it.toString()
        } else {
            element.style.removeProperty("border")
        }
    }

    override var borderTop: Border? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.borderTop = it.toString()
        } else {
            element.style.removeProperty("border-top")
        }
    }

    override var borderRight: Border? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.borderRight = it.toString()
        } else {
            element.style.removeProperty("border-right")
        }
    }

    override var borderBottom: Border? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.borderBottom = it.toString()
        } else {
            element.style.removeProperty("border-bottom")
        }
    }

    override var borderLeft: Border? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.borderLeft = it.toString()
        } else {
            element.style.removeProperty("border-left")
        }
    }

    override var margin: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.margin = it.toString()
        } else {
            element.style.removeProperty("margin")
        }
    }

    override var marginTop: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.marginTop = it.toString()
        } else {
            element.style.removeProperty("margin-top")
        }
    }

    override var marginRight: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.marginRight = it.toString()
        } else {
            element.style.removeProperty("margin-right")
        }
    }

    override var marginBottom: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.marginBottom = it.toString()
        } else {
            element.style.removeProperty("margin-bottom")
        }
    }

    override var marginLeft: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.marginLeft = it.toString()
        } else {
            element.style.removeProperty("margin-left")
        }
    }

    override var padding: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.padding = it.toString()
        } else {
            element.style.removeProperty("padding")
        }
    }

    override var paddingTop: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.paddingTop = it.toString()
        } else {
            element.style.removeProperty("padding-top")
        }
    }

    override var paddingRight: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.paddingRight = it.toString()
        } else {
            element.style.removeProperty("padding-right")
        }
    }

    override var paddingBottom: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.paddingBottom = it.toString()
        } else {
            element.style.removeProperty("padding-bottom")
        }
    }

    override var paddingLeft: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.paddingLeft = it.toString()
        } else {
            element.style.removeProperty("padding-left")
        }
    }

    override var color: Color? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.color = it.toString()
        } else {
            element.style.removeProperty("color")
        }
    }

    /**
     * Opacity of the current component.
     */
    override var opacity: Double? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.opacity = it.toString()
        } else {
            element.style.removeProperty("opacity")
        }
    }


    override var background: Background? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.background = it.toString()
        } else {
            element.style.removeProperty("background")
        }
    }

    override var direction: Direction? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.direction = it.value
        } else {
            element.style.removeProperty("direction")
        }
    }

    override var letterSpacing: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.letterSpacing = it.toString()
        } else {
            element.style.removeProperty("letter-spacing")
        }
    }

    override var lineHeight: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.lineHeight = it.toString()
        } else {
            element.style.removeProperty("line-height")
        }
    }

    override var textAlign: TextAlign? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textAlign = it.value
        } else {
            element.style.removeProperty("text-align")
        }
    }

    override var textDecoration: TextDecoration? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textDecoration = it.toString()
        } else {
            element.style.removeProperty("text-decoration")
        }
    }

    override var textIndent: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textIndent = it.toString()
        } else {
            element.style.removeProperty("text-indent")
        }
    }

    override var textShadow: TextShadow? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textShadow = it.toString()
        } else {
            element.style.removeProperty("text-shadow")
        }
    }

    override var textTransform: TextTransform? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textTransform = it.value
        } else {
            element.style.removeProperty("text-transform")
        }
    }

    override var textOverflow: TextOverflow? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.textOverflow = it.value
        } else {
            element.style.removeProperty("text-overflow")
        }
    }

    override var unicodeBidi: UnicodeBidi? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.unicodeBidi = it.value
        } else {
            element.style.removeProperty("unicode-bidi")
        }
    }

    override var verticalAlign: VerticalAlign? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.verticalAlign = it.value
        } else {
            element.style.removeProperty("vertical-align")
        }
    }

    override var whiteSpace: WhiteSpace? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.whiteSpace = it.value
        } else {
            element.style.removeProperty("white-space")
        }
    }

    override var wordSpacing: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.wordSpacing = it.toString()
        } else {
            element.style.removeProperty("word-spacing")
        }
    }

    override var fontFamily: String? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.fontFamily = it
        } else {
            element.style.removeProperty("font-family")
        }
    }

    override var fontSize: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.fontSize = it.toString()
        } else {
            element.style.removeProperty("font-size")
        }
    }

    override var fontStyle: FontStyle? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.fontStyle = it.value
        } else {
            element.style.removeProperty("font-style")
        }
    }

    override var fontWeight: FontWeight? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.fontWeight = it.value
        } else {
            element.style.removeProperty("font-weight")
        }
    }

    override var fontVariant: FontVariant? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.fontVariant = it.value
        } else {
            element.style.removeProperty("font-variant")
        }
    }

    override var float: CssFloat? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.cssFloat = it.value
        } else {
            element.style.removeProperty("float")
        }
    }

    override var clear: Clear? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.clear = it.value
        } else {
            element.style.removeProperty("clear")
        }
    }

    override var wordBreak: WordBreak? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.wordBreak = it.value
        } else {
            element.style.removeProperty("word-break")
        }
    }

    override var lineBreak: LineBreak? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.lineBreak = it.value
        } else {
            element.style.removeProperty("line-break")
        }
    }

    override var cursor: Cursor? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.cursor = it.value
        } else {
            element.style.removeProperty("cursor")
        }
    }

    override var flexDirection: FlexDirection? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.flexDirection = it.value
        } else {
            element.style.removeProperty("flex-direction")
        }
    }

    override var flexWrap: FlexWrap? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.flexWrap = it.value
        } else {
            element.style.removeProperty("flex-wrap")
        }
    }

    override var justifyItems: JustifyItems? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("justify-items", it.value)
        } else {
            element.style.removeProperty("justify-items")
        }
    }

    override var justifyContent: JustifyContent? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.justifyContent = it.value
        } else {
            element.style.removeProperty("justify-content")
        }
    }

    override var alignItems: AlignItems? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.alignItems = it.value
        } else {
            element.style.removeProperty("align-items")
        }
    }

    override var alignContent: AlignContent? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.alignContent = it.value
        } else {
            element.style.removeProperty("align-content")
        }
    }

    override var order: Int? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.order = it.toString()
        } else {
            element.style.removeProperty("order")
        }
    }

    override var flexGrow: Int? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.flexGrow = it.toString()
        } else {
            element.style.removeProperty("flex-grow")
        }
    }

    override var flexShrink: Int? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.flexShrink = it.toString()
        } else {
            element.style.removeProperty("flex-shrink")
        }
    }

    override var flexBasis: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.flexBasis = it.toString()
        } else {
            element.style.removeProperty("flex-basis")
        }
    }

    override var alignSelf: AlignItems? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.alignSelf = it.value
        } else {
            element.style.removeProperty("align-self")
        }
    }

    override var justifySelf: JustifyItems? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("justify-self", it.value)
        } else {
            element.style.removeProperty("justify-self")
        }
    }

    override var gridAutoColumns: String? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-auto-columns", it)
        } else {
            element.style.removeProperty("grid-auto-columns")
        }
    }

    override var gridAutoRows: String? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-auto-rows", it)
        } else {
            element.style.removeProperty("grid-auto-rows")
        }
    }

    override var gridAutoFlow: GridAutoFlow? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-auto-flow", it.value)
        } else {
            element.style.removeProperty("grid-auto-flow")
        }
    }

    override var gridTemplateColumns: String? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-template-columns", it)
        } else {
            element.style.removeProperty("grid-template-columns")
        }
    }

    override var gridTemplateRows: String? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-template-rows", it)
        } else {
            element.style.removeProperty("grid-template-rows")
        }
    }

    override var gridTemplateAreas: List<String>? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-template-areas", it.joinToString(" ") { "\"" + it + "\"" })
        } else {
            element.style.removeProperty("grid-template-areas")
        }
    }

    override var columnGap: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("column-gap", it.toString())
        } else {
            element.style.removeProperty("column-gap")
        }
    }

    override var rowGap: CssSize? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("row-gap", it.toString())
        } else {
            element.style.removeProperty("row-gap")
        }
    }

    override var gridColumnStart: Int? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-column-start", it.toString())
        } else {
            element.style.removeProperty("grid-column-start")
        }
    }

    override var gridRowStart: Int? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-row-start", it.toString())
        } else {
            element.style.removeProperty("grid-row-start")
        }
    }

    override var gridColumnEnd: String? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-column-end", it)
        } else {
            element.style.removeProperty("grid-column-end")
        }
    }

    override var gridRowEnd: String? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-row-end", it)
        } else {
            element.style.removeProperty("grid-row-end")
        }
    }

    override var gridArea: String? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.setProperty("grid-area", it)
        } else {
            element.style.removeProperty("grid-area")
        }
    }

    override var outline: Outline? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.outline = it.toString()
        } else {
            element.style.removeProperty("outline")
        }
    }

    override var boxShadow: BoxShadow? by updatingProperty {
        if (it != null && boxShadowList != null) boxShadowList = null
        if (it != null) {
            elementNullable?.style?.boxShadow = it.toString()
        } else {
            elementNullable?.style?.removeProperty("box-shadow")
        }
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

    override var transition: Transition? by updatingProperty {
        if (it != null && transitionList != null) transitionList = null
        if (it != null) {
            elementNullable?.style?.transition = it.toString()
        } else {
            elementNullable?.style?.removeProperty("transition")
        }
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

    override var borderRadius: CssSize? by updatingProperty {
        if (it != null && borderRadiusList != null) borderRadiusList = null
        if (it != null) {
            elementNullable?.style?.borderRadius = it.toString()
        } else {
            elementNullable?.style?.removeProperty("border-radius")
        }
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

    override var listStyle: ListStyle? by updatingProperty(skipUpdate = skipUpdates) {
        if (it != null) {
            element.style.listStyle = it.toString()
        } else {
            element.style.removeProperty("list-style")
        }
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
}
