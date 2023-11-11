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

package dev.kilua.form.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.core.SafeDomFactory
import dev.kilua.externals.buildCustomEventInit
import dev.kilua.externals.getEditorFromElement
import dev.kilua.externals.obj
import dev.kilua.form.InputType
import dev.kilua.form.StringFormControl
import dev.kilua.form.text.i18n.getToolbarContent
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.html.tag
import dev.kilua.i18n.DefaultLocale
import dev.kilua.i18n.Locale
import dev.kilua.initializeTrix
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import kotlinx.dom.clear
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.asList
import org.w3c.dom.events.Event

/**
 * Trix rich text editor component.
 */
@Suppress("LongParameterList")
public open class RichText(
    value: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    id: String? = null,
    locale: Locale = DefaultLocale(),
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val withStateFlowDelegate: WithStateFlowDelegate<String?> = WithStateFlowDelegateImpl()
) : Tag<HTMLElement>("trix-editor", className, renderConfig),
    StringFormControl, WithStateFlow<String?> by withStateFlowDelegate {

    public override var value: String? by updatingProperty(
        value,
        skipUpdate,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        getEditorFromElement(element)?.loadHTML(it ?: "")
        if (disabled == true) toolbarDisable(true)
    }

    /**
     * The locale for i18n.
     */
    public open var locale: Locale by updatingProperty(locale, skipUpdate = skipUpdate) {
        toolbarLocalize()
    }

    public override var name: String? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.setAttribute("name", it)
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * The placeholder of the rich text editor.
     */
    public var placeholder: String? by updatingProperty(placeholder, skipUpdate) {
        if (it != null) {
            element.setAttribute("placeholder", it)
        } else {
            element.removeAttribute("placeholder")
        }
    }

    public override var disabled: Boolean? by updatingProperty(disabled, skipUpdate) {
        if (it == true) {
            element.setAttribute("disabled", "")
            element.removeAttribute("contenteditable")
            toolbarDisable(true)
        } else {
            element.removeAttribute("disabled")
            element.setAttribute("contenteditable", "")
            toolbarDisable(false)
        }
    }

    public override var required: Boolean? by updatingProperty(required, skipUpdate) {
        if (it == true) {
            element.setAttribute("required", "")
        } else {
            element.removeAttribute("required")
        }
    }

    /**
     * The autofocus of the rich text editor.
     */
    public override var autofocus: Boolean? by updatingProperty(skipUpdate = skipUpdate) {
        if (it == true) {
            element.setAttribute("autofocus", "")
        } else {
            element.removeAttribute("autofocus")
        }
    }

    public override var customValidity: String? by updatingProperty(skipUpdate = skipUpdate)

    override var visible: Boolean
        get() = super.visible
        set(value) {
            super.visible = value
            toolbar?.let { SafeDomFactory.getElementById(it) }?.unsafeCast<HTMLElement>()?.run {
                if (!value) {
                    style.display = "none"
                } else {
                    style.removeProperty("display")
                }
            }
        }

    /**
     * The ID of the connected input element
     */
    internal var input: String? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.setAttribute("input", it)
        } else {
            element.removeAttribute("input")
        }
    }

    /**
     * The ID of the connected toolbar element
     */
    internal var toolbar: String? by updatingProperty(skipUpdate = skipUpdate) {
        if (it != null) {
            element.setAttribute("toolbar", it)
        } else {
            element.removeAttribute("toolbar")
        }
    }

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
        if (renderConfig.isDom) {
            if (placeholder != null) {
                element.setAttribute("placeholder", placeholder)
            }
            if (disabled == true) {
                element.setAttribute("disabled", "")
            }
            if (required == true) {
                element.setAttribute("required", "")
            }
            @Suppress("LeakingThis")
            onEventDirect<Event>("trix-change") {
                input?.let { SafeDomFactory.getElementById(it) }?.let {
                    val elementValue = it.unsafeCast<HTMLInputElement>().value
                    setInternalValueFromString(elementValue)
                    dispatchEvent("change", buildCustomEventInit(obj()))
                }
            }
            @Suppress("LeakingThis")
            onEventDirect<Event>("trix-file-accept") { e -> e.preventDefault() }
        }
        @Suppress("LeakingThis")
        if (id != null) this.id = id
        role = "textbox"
    }

    /**
     * Disable/enable connected toolbar.
     */
    protected fun toolbarDisable(disable: Boolean) {
        toolbar?.let { SafeDomFactory.getElementById(it) }?.unsafeCast<HTMLElement>()?.run {
            querySelectorAll("button").asList().forEach {
                it.unsafeCast<HTMLButtonElement>().disabled = disable
            }
        }
    }

    /**
     * Localize connected toolbar.
     */
    protected fun toolbarLocalize() {
        toolbar?.let { SafeDomFactory.getElementById(it) }?.unsafeCast<HTMLElement>()?.innerHTML =
            getToolbarContent(locale)
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::name,
            ::placeholder,
            ::disabled,
            ::required,
            ::autofocus,
            ::input,
            ::toolbar
        )
    }

    override fun getValueAsString(): String? {
        return value
    }

    /**
     * Convert string to the correct value.
     */
    protected open fun stringToValue(text: String?): String? {
        return if (text.isNullOrEmpty()) {
            null
        } else {
            text
        }
    }

    /**
     * Set value from string without setting element value again.
     */
    protected open fun setInternalValueFromString(text: String?) {
        val newValue = stringToValue(text)
        if (value != newValue) {
            if (newValue != null) {
                propertyValues["value"] = newValue
            } else {
                propertyValues.remove("value")
            }
            withStateFlowDelegate.updateStateFlow(newValue)
        }
    }

    override fun onInsert() {
        if (renderConfig.isDom) {
            toolbarLocalize()
            if (disabled == true) {
                element.removeAttribute("contenteditable")
                toolbarDisable(true)
            }
        }
    }

    override fun onRemove() {
        if (renderConfig.isDom) {
            toolbar?.let { SafeDomFactory.getElementById(it) }?.unsafeCast<HTMLElement>()?.clear()
            element.clear()
        }
    }

    public companion object {
        internal var idCounter: Int = 0

        init {
            initializeTrix()
        }
    }

}

/**
 * Creates a [RichText] component.
 *
 * @param value the initial value
 * @param placeholder the placeholder attribute of the generated HTML textarea element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param id the ID of the component
 * @param locale the locale for i18n
 * @param className the CSS class name
 * @param setup a function for setting up the component
 * @return A [RichText] component.
 */
@Suppress("LongParameterList")
@Composable
public fun ComponentBase.richText(
    value: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    id: String? = null,
    locale: Locale = DefaultLocale(),
    className: String? = null,
    setup: @Composable RichText.() -> Unit = {}
): RichText {
    val bindId = remember { "kilua_trix_${RichText.idCounter++}" }
    val component =
        remember {
            RichText(
                value,
                placeholder,
                disabled,
                required,
                id,
                locale,
                className,
                renderConfig
            ).apply {
                input = bindId + "_input"
                toolbar = bindId + "_toolbar"
            }
        }
    text(value, type = InputType.Hidden, id = bindId + "_input")
    tag("trix-toolbar") { this.id = bindId + "_toolbar" }
    DisposableEffect(component.componentId) {
        component.onInsert()
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(value) { updateProperty(RichText::value, it) }
        set(placeholder) { updateProperty(RichText::placeholder, it) }
        set(disabled) { updateProperty(RichText::disabled, it) }
        set(required) { updateProperty(RichText::required, it) }
        set(id) { updateProperty(RichText::id, it) }
        set(locale) { updateProperty(RichText::locale, it) }
        set(className) { updateProperty(RichText::className, it) }
    }, setup)
    return component
}
