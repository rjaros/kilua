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
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.core.SafeDomFactory
import dev.kilua.externals.buildCustomEventInit
import dev.kilua.externals.getEditorFromElement
import dev.kilua.externals.obj
import dev.kilua.form.InputType
import dev.kilua.form.StringFormControl
import dev.kilua.form.text.i18n.getToolbarContent
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.html.tag
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager
import dev.kilua.initializeTrix
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.unsafeCast
import web.clear
import web.dom.HTMLButtonElement
import web.dom.HTMLElement
import web.dom.HTMLInputElement
import web.dom.asList
import web.dom.events.Event

/**
 * Trix rich text editor component.
 */
public interface IRichText : ITag<HTMLElement>, StringFormControl, WithStateFlow<String?> {
    /**
     * The locale for i18n.
     */
    public val locale: Locale

    /**
     * Set the locale for i18n.
     */
    @Composable
    public fun locale(locale: Locale)

    /**
     * The name of the rich text editor.
     */
    public val name: String?

    /**
     * Set the name of the rich text editor.
     */
    @Composable
    public fun name(name: String?)

    /**
     * The placeholder of the rich text editor.
     */
    public val placeholder: String?

    /**
     * Set the placeholder of the rich text editor.
     */
    @Composable
    public fun placeholder(placeholder: String?)

    /**
     * Allow file uploads.
     */
    public val allowFileUploads: Boolean

    /**
     * Set to allow file uploads.
     */
    @Composable
    public fun allowFileUploads(allowFileUploads: Boolean)

    /**
     * Whether the rich text editor is disabled.
     */
    public val disabled: Boolean?

    /**
     * Set whether the rich text editor is disabled.
     */
    @Composable
    public fun disabled(disabled: Boolean?)

    /**
     * Set whether the rich text editor is required.
     */
    @Composable
    public fun required(required: Boolean?)
}

/**
 * Trix rich text editor component.
 */
@Suppress("LongParameterList")
public open class RichText(
    value: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default,
    protected val withStateFlowDelegate: WithStateFlowDelegate<String?> = WithStateFlowDelegateImpl()
) : Tag<HTMLElement>("trix-editor", className, id, renderConfig = renderConfig),
    StringFormControl, WithStateFlow<String?> by withStateFlowDelegate, IRichText {

    public override var value: String? by updatingProperty(
        initialValue = value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        getEditorFromElement(element)?.loadHTML(it ?: "")
        if (disabled == true) toolbarDisable(true)
    }

    /**
     * The locale for i18n.
     */
    public override var locale: Locale by updatingProperty(locale) {
        toolbarLocalize()
    }

    /**
     * Set the locale for i18n.
     */
    @Composable
    override fun locale(locale: Locale): Unit = composableProperty("locale", {
        this.locale = LocaleManager.currentLocale
    }) {
        this.locale = locale
    }

    /**
     * The name of the rich text editor.
     */
    public override var name: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("name", it)
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * Set the name of the rich text editor.
     */
    @Composable
    override fun name(name: String?): Unit = composableProperty("name", {
        this.name = null
    }) {
        this.name = name
    }

    /**
     * The placeholder of the rich text editor.
     */
    public override var placeholder: String? by updatingProperty(placeholder) {
        if (it != null) {
            element.setAttribute("placeholder", it)
        } else {
            element.removeAttribute("placeholder")
        }
    }

    /**
     * Set the placeholder of the rich text editor.
     */
    @Composable
    override fun placeholder(placeholder: String?): Unit = composableProperty("placeholder", {
        this.placeholder = null
    }) {
        this.placeholder = placeholder
    }

    /**
     * Allow file uploads.
     */
    public override var allowFileUploads: Boolean by updatingProperty(false)

    /**
     * Set to allow file uploads.
     */
    @Composable
    override fun allowFileUploads(allowFileUploads: Boolean): Unit = composableProperty("allowFileUploads", {
        this.allowFileUploads = false
    }) {
        this.allowFileUploads = allowFileUploads
    }

    /**
     * Whether the rich text editor is disabled.
     */
    public override var disabled: Boolean? by updatingProperty(disabled) {
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

    /**
     * Set whether the rich text editor is disabled.
     */
    @Composable
    override fun disabled(disabled: Boolean?): Unit = composableProperty("disabled", {
        this.disabled = null
    }) {
        this.disabled = disabled
    }

    /**
     * Set whether the rich text editor is required.
     */
    public override var required: Boolean? by updatingProperty(required) {
        if (it == true) {
            element.setAttribute("required", "")
        } else {
            element.removeAttribute("required")
        }
    }

    /**
     * Set whether the rich text editor is required.
     */
    @Composable
    override fun required(required: Boolean?): Unit = composableProperty("required", {
        this.required = null
    }) {
        this.required = required
    }

    public override var customValidity: String? by updatingProperty()

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
    internal var input: String? by updatingProperty {
        if (it != null) {
            element.setAttribute("input", it)
        } else {
            element.removeAttribute("input")
        }
    }

    /**
     * The ID of the connected toolbar element
     */
    internal var toolbar: String? by updatingProperty {
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
                @Suppress("LeakingThis")
                element.setAttribute("placeholder", placeholder)
            }
            if (disabled == true) {
                @Suppress("LeakingThis")
                element.setAttribute("disabled", "")
            }
            if (required == true) {
                @Suppress("LeakingThis")
                element.setAttribute("required", "")
            }
            @Suppress("LeakingThis")
            onEventDirect<Event>("trix-initialize") {
                if (disabled == true) {
                    element.removeAttribute("contenteditable")
                    toolbarDisable(true)
                }
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
            onEventDirect<Event>("trix-file-accept") { e -> if (!allowFileUploads) e.preventDefault() }
        }
        @Suppress("LeakingThis")
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
            setPropertyValue("value", newValue)
            withStateFlowDelegate.updateStateFlow(newValue)
        }
    }

    override fun onInsert() {
        if (renderConfig.isDom) {
            toolbarLocalize()
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
 * Creates a [RichText] component, returning a reference.
 *
 * @param value the initial value
 * @param placeholder the placeholder attribute of the generated HTML textarea element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for i18n
 * @param className the CSS class name
 * @param id the ID of the component
 * @param setup a function for setting up the component
 * @return A [RichText] component.
 */
@Suppress("LongParameterList")
@Composable
public fun IComponent.richTextRef(
    value: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichText.() -> Unit = {}
): RichText {
    val bindId = remember { "kilua_trix_${RichText.idCounter++}" }
    val component =
        remember {
            RichText(
                value,
                placeholder,
                disabled,
                required,
                locale,
                className,
                id,
                renderConfig
            ).apply {
                input = bindId + "_input"
                toolbar = bindId + "_toolbar"
            }
        }
    text(value, type = InputType.Hidden, id = bindId + "_input")
    tag("trix-toolbar", id = bindId + "_toolbar")
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
        set(locale) { updateProperty(RichText::locale, it) }
        set(className) { updateProperty(RichText::className, it) }
        set(id) { updateProperty(RichText::id, it) }
    }, setup)
    return component
}

/**
 * Creates a [RichText] component.
 *
 * @param value the initial value
 * @param placeholder the placeholder attribute of the generated HTML textarea element
 * @param disabled determines if the field is disabled
 * @param required determines if the field is required
 * @param locale the locale for i18n
 * @param className the CSS class name
 * @param id the ID of the component
 * @param setup a function for setting up the component
 * @return A [RichText] component.
 */
@Suppress("LongParameterList")
@Composable
public fun IComponent.richText(
    value: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    setup: @Composable IRichText.() -> Unit = {}
) {
    val bindId = remember { "kilua_trix_${RichText.idCounter++}" }
    val component =
        remember {
            RichText(
                value,
                placeholder,
                disabled,
                required,
                locale,
                className,
                id,
                renderConfig
            ).apply {
                input = bindId + "_input"
                toolbar = bindId + "_toolbar"
            }
        }
    text(value, type = InputType.Hidden, id = bindId + "_input")
    tag("trix-toolbar", id = bindId + "_toolbar")
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
        set(locale) { updateProperty(RichText::locale, it) }
        set(className) { updateProperty(RichText::className, it) }
        set(id) { updateProperty(RichText::id, it) }
    }, setup)
}
