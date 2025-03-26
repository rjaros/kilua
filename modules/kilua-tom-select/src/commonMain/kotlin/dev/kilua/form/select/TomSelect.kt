/*
 * Copyright (c) 2024 Robert Jaros
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

package dev.kilua.form.select

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.TomSelectJs
import dev.kilua.externals.TomSelectOptionsJs
import dev.kilua.externals.assign
import dev.kilua.utils.jsGet
import dev.kilua.form.StringFormControl
import dev.kilua.html.ITag
import dev.kilua.html.Tag
import dev.kilua.html.div
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.JsArray
import dev.kilua.utils.StringPair
import dev.kilua.utils.cast
import dev.kilua.utils.clear
import dev.kilua.utils.rem
import dev.kilua.utils.toJsAny
import dev.kilua.utils.toJsString
import dev.kilua.utils.toList
import dev.kilua.utils.unsafeCast
import js.core.JsAny
import js.core.JsString
import js.objects.jso
import web.dom.InsertPosition
import web.dom.document
import web.html.HTMLOptionElement
import web.html.HTMLSelectElement

/**
 * Tom Select component.
 */
public interface ITomSelect : ITag<HTMLSelectElement>, StringFormControl, WithStateFlow<String?> {
    /**
     * The list of options (value to label pairs).
     */
    public val options: List<StringPair>?

    /**
     * Set the list of options (value to label pairs).
     */
    @Composable
    public fun options(options: List<StringPair>?)

    /**
     * Determines if an empty option is allowed.
     */
    public val emptyOption: Boolean

    /**
     * Set whether an empty option is allowed.
     */
    @Composable
    public fun emptyOption(emptyOption: Boolean)

    /**
     * Determines if multiple value selection is allowed.
     */
    public val multiple: Boolean

    /**
     * Set whether multiple value selection is allowed.
     */
    @Composable
    public fun multiple(multiple: Boolean)

    /**
     * The maximum number of visible options.
     */
    public val maxOptions: Int?

    /**
     * Set the maximum number of visible options.
     */
    @Composable
    public fun maxOptions(maxOptions: Int?)

    /**
     * Tom Select options.
     */
    public val tsOptions: TomSelectOptions?

    /**
     * Set Tom Select options.
     */
    @Composable
    public fun tsOptions(tsOptions: TomSelectOptions?)

    /**
     * Tom Select callbacks.
     */
    public val tsCallbacks: TomSelectCallbacks?

    /**
     * Set Tom Select callbacks.
     */
    @Composable
    public fun tsCallbacks(tsCallbacks: TomSelectCallbacks?)

    /**
     * Tom Select renders.
     */
    public val tsRenders: TomSelectRenders?

    /**
     * Set Tom Select renders.
     */
    @Composable
    public fun tsRenders(tsRenders: TomSelectRenders?)

    /**
     * Disable searching in options.
     */
    public val disableSearch: Boolean

    /**
     * Set whether searching in options is disabled.
     */
    @Composable
    public fun disableSearch(disableSearch: Boolean)

    /**
     * The name of the select.
     */
    public val name: String?

    /**
     * Set the name of the select.
     */
    @Composable
    public fun name(name: String?)

    /**
     * The placeholder for the select component.
     */
    public val placeholder: String?

    /**
     * Set the placeholder for the select component.
     */
    @Composable
    public fun placeholder(placeholder: String?)

    /**
     * Whether the select is disabled.
     */
    public val disabled: Boolean?

    /**
     * Set whether the select is disabled.
     */
    @Composable
    public fun disabled(disabled: Boolean?)

    /**
     * Set whether the select is required.
     */
    @Composable
    public fun required(required: Boolean?)

    /**
     * Tom Select native component instance.
     */
    public val tomSelectInstance: TomSelectJs?

    /**
     * The label of the currently selected option.
     */
    public val selectedLabel: String?

    /**
     * Removes all unselected options from the control.
     */
    public fun clearOptions()

}

/**
 * Tom Select component.
 */
public open class TomSelect(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    maxOptions: Int? = null,
    tsOptions: TomSelectOptions? = null,
    tsCallbacks: TomSelectCallbacks? = null,
    tsRenders: TomSelectRenders? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default,
    protected val withStateFlowDelegate: WithStateFlowDelegate<String?> = WithStateFlowDelegateImpl()
) : Tag<HTMLSelectElement>("select", className, id, renderConfig = renderConfig), StringFormControl,
    WithStateFlow<String?> by withStateFlowDelegate, ITomSelect {

    /**
     * The list of options (value to label pairs).
     */
    public override var options: List<StringPair>? by updatingProperty(options) {
        refresh()
    }

    /**
     * Set the list of options (value to label pairs).
     */
    @Composable
    public override fun options(options: List<StringPair>?): Unit = composableProperty("options", {
        this.options = null
    }) {
        this.options = options
    }

    public override var value: String? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        refreshValue()
    }

    /**
     * Determines if an empty option is allowed.
     */
    public override var emptyOption: Boolean by updatingProperty(emptyOption) {
        refresh()
    }

    /**
     * Set whether an empty option is allowed.
     */
    @Composable
    public override fun emptyOption(emptyOption: Boolean): Unit = composableProperty("emptyOption", {
        this.emptyOption = false
    }) {
        this.emptyOption = emptyOption
    }

    /**
     * Determines if multiple value selection is allowed.
     */
    override var multiple: Boolean by updatingProperty(multiple) {
    }

    /**
     * Set whether multiple value selection is allowed.
     */
    @Composable
    public override fun multiple(multiple: Boolean): Unit = composableProperty("multiple", {
        this.multiple = false
    }) {
        this.multiple = multiple
    }

    /**
     * The maximum number of visible options.
     */
    public override var maxOptions: Int? by updatingProperty(maxOptions) {
        refresh()
    }

    /**
     * Set the maximum number of visible options.
     */
    @Composable
    public override fun maxOptions(maxOptions: Int?): Unit = composableProperty("maxOptions", {
        this.maxOptions = null
    }) {
        this.maxOptions = maxOptions
    }

    /**
     * Tom Select options.
     */
    public override var tsOptions: TomSelectOptions? by updatingProperty(tsOptions) {
        refresh()
    }

    /**
     * Set Tom Select options.
     */
    @Composable
    public override fun tsOptions(tsOptions: TomSelectOptions?): Unit = composableProperty("tsOptions", {
        this.tsOptions = null
    }) {
        this.tsOptions = tsOptions
    }

    /**
     * Tom Select callbacks.
     */
    public override var tsCallbacks: TomSelectCallbacks? by updatingProperty(tsCallbacks) {
        refresh()
    }

    /**
     * Set Tom Select callbacks.
     */
    @Composable
    public override fun tsCallbacks(tsCallbacks: TomSelectCallbacks?): Unit = composableProperty("tsCallbacks", {
        this.tsCallbacks = null
    }) {
        this.tsCallbacks = tsCallbacks
    }

    /**
     * Tom Select renders.
     */
    public override var tsRenders: TomSelectRenders? by updatingProperty(tsRenders) {
        refresh()
    }

    /**
     * Set Tom Select renders.
     */
    @Composable
    public override fun tsRenders(tsRenders: TomSelectRenders?): Unit = composableProperty("tsRenders", {
        this.tsRenders = null
    }) {
        this.tsRenders = tsRenders
    }

    /**
     * Disable searching in options.
     */
    public override var disableSearch: Boolean by updatingProperty(false) {
        refresh()
    }

    /**
     * Set whether searching in options is disabled.
     */
    @Composable
    public override fun disableSearch(disableSearch: Boolean): Unit = composableProperty("disableSearch", {
        this.disableSearch = false
    }) {
        this.disableSearch = disableSearch
    }

    public override var name: String? by updatingProperty(name) {
        if (it != null) {
            element.name = it
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * Set the name of the select.
     */
    @Composable
    public override fun name(name: String?): Unit = composableProperty("name", {
        this.name = null
    }) {
        this.name = name
    }

    /**
     * The placeholder for the select component.
     */
    public override var placeholder: String? by updatingProperty(placeholder) {
        refresh()
    }

    /**
     * Set the placeholder for the select component.
     */
    @Composable
    public override fun placeholder(placeholder: String?): Unit = composableProperty("placeholder", {
        this.placeholder = null
    }) {
        this.placeholder = placeholder
    }

    public override var disabled: Boolean? by updatingProperty(disabled) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
        refresh()
    }

    /**
     * Set whether the select is disabled.
     */
    @Composable
    public override fun disabled(disabled: Boolean?): Unit = composableProperty("disabled", {
        this.disabled = null
    }) {
        this.disabled = disabled
    }

    public override var required: Boolean? by updatingProperty(required) {
        if (it != null) {
            element.required = it
        } else {
            element.removeAttribute("required")
        }
    }

    /**
     * Set whether the select is required.
     */
    @Composable
    public override fun required(required: Boolean?): Unit = composableProperty("required", {
        this.required = null
    }) {
        this.required = required
    }

    public override var customValidity: String? by updatingProperty {
        element.setCustomValidity(it ?: "")
    }

    /**
     * Tom Select native component instance.
     */
    public override var tomSelectInstance: TomSelectJs? = null

    /**
     * The label of the currently selected option.
     */
    public override val selectedLabel: String?
        get() = element.options.toList().find {
            it.unsafeCast<HTMLOptionElement>().value == this.value
        }?.textContent

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
        if (renderConfig.isDom) {
            if (multiple) {
                @Suppress("LeakingThis")
                element.multiple = true
            }
            if (name != null) {
                @Suppress("LeakingThis")
                element.name = name
            }
            if (placeholder != null) {
                @Suppress("LeakingThis")
                element.setAttribute("placeholder", placeholder)
            }
            if (disabled != null) {
                @Suppress("LeakingThis")
                element.disabled = disabled
            }
            if (required != null) {
                @Suppress("LeakingThis")
                element.required = required
            }
        }
        @Suppress("LeakingThis")
        onChangeDirect {
            val v = if (this.multiple) {
                tomSelectInstance?.getValue()?.unsafeCast<JsArray<JsString>>()?.toList()?.joinToString(",")
            } else {
                tomSelectInstance?.getValue()?.unsafeCast<JsString>().toString()
            }
            this.value = v?.ifBlank { null }
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::multiple,
            ::name,
            ::disabled,
            ::required,
            ::autofocus,
        )
    }

    override fun getValueAsString(): String? {
        return value
    }

    override fun onInsert() {
        initializeTomSelect()
    }

    override fun onRemove() {
        destroyTomSelect()
    }

    /**
     * Refresh the value of the Tom Select instance.
     */
    protected open fun refreshValue() {
        if (tomSelectInstance != null) {
            if (multiple) {
                if (value != null) {
                    tomSelectInstance?.setValue(value!!.split(",").toJsAny(), true)
                } else {
                    tomSelectInstance?.clear(true)
                }
            } else {
                if (value != null) {
                    tomSelectInstance?.setValue(value!!.toJsString(), true)
                } else {
                    tomSelectInstance?.clear(true)
                }
            }
        }
    }

    /**
     * Re-creates the Tom Select instance.
     */
    protected open fun refresh() {
        if (tomSelectInstance != null) {
            destroyTomSelect()
            initializeTomSelect()
        }
    }

    /**
     * Initializes the Tom Select instance.
     */
    protected open fun initializeTomSelect() {
        if (renderConfig.isDom) {
            val self = this
            val tomSelectOptions = jso<TomSelectOptionsJs> {
                this.maxItems = if (!self.multiple) 1 else null
                this.maxOptions = self.maxOptions
                this.allowEmptyOption = self.emptyOption
                if (self.options != null) {
                    val optionsWithEmpty = if (emptyOption) {
                        listOf(StringPair("", "\u00a0")) + self.options!!
                    } else {
                        self.options!!
                    }
                    this.options = optionsWithEmpty.map {
                        mapOf(
                            "value" to it.first,
                            "text" to it.second
                        )
                    }.toJsAny().cast()
                    if (self.disableSearch) this.controlInput = null
                }
                if (self.tsOptions != null) {
                    val optionsObj = self.tsOptions!!.toJs(self.emptyOption)
                    assign(this, optionsObj)
                } else {
                    this.plugins = listOf("change_listener").toJsAny()!!
                }
                if (self.tsCallbacks != null) {
                    val callbacksObj = self.tsCallbacks!!.toJs()
                    assign(this, callbacksObj)
                    if (self.tsCallbacks!!.load != null) {
                        this.load = { query: String, callback: (JsArray<JsAny>) -> Unit ->
                            tsCallbacks!!.load!!(query) { options ->
                                if (emptyOption) {
                                    callback(
                                        (listOf(
                                            mapOf(
                                                "value" to "",
                                                "text" to "\u00a0"
                                            )
                                        ) + options.toList()).toJsAny().cast()
                                    )
                                } else {
                                    callback(options)
                                }
                            }
                        }
                    }
                }
                if (tsRenders != null) {
                    this.render = tsRenders!!.toJs()
                } else {
                    this.render = jso {
                        no_results = null
                    }
                }
            }
            tomSelectInstance = TomSelectJs(element, tomSelectOptions)
            refreshValue()
            element.nextSibling?.nextSibling?.let {
                // Remove additional empty div to make place for the generated ts-wrapper
                it.parentNode?.removeChild(it)
            }
        }
    }

    /**
     * Destroys the Tom Select instance.
     */
    protected open fun destroyTomSelect() {
        if (renderConfig.isDom && tomSelectInstance != null) {
            tomSelectInstance!!.destroy()
            tomSelectInstance = null
            // Restore the empty div after the Tom Select instance is removed
            val emptyDiv = document.createElement("div")
            element.insertAdjacentElement(InsertPosition.afterend, emptyDiv)
        }
    }

    /**
     * Removes all unselected options from the control.
     */
    public override fun clearOptions() {
        tomSelectInstance?.clearOptions { false }
        if (tomSelectInstance != null && tomSelectInstance!!.jsGet("input") != null && tomSelectInstance!!.jsGet("input")!!.jsGet("clear") != null) {
            tomSelectInstance!!.jsGet("input")!!.unsafeCast<HTMLSelectElement>().clear()
        }
    }

    override fun focus() {
        if (tomSelectInstance != null) {
            tomSelectInstance?.focus()
        } else {
            super.focus()
        }
    }

    override fun blur() {
        if (tomSelectInstance != null) {
            tomSelectInstance?.blur()
        } else {
            super.blur()
        }
    }

}

/**
 * Creates [TomSelect] component, returning a reference.
 *
 * @param options a list of options (value to label pairs)
 * @param value initial value
 * @param emptyOption determines if an empty option is allowed
 * @param multiple determines if multiple value selection is allowed
 * @param maxOptions the maximum number of visible options
 * @param tsOptions Tom Select options
 * @param tsCallbacks Tom Select callbacks
 * @param tsRenders Tom Select renders
 * @param name the name of the select
 * @param placeholder the placeholder for the select component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 * @return a [TomSelect] component
 */
@Composable
public fun IComponent.tomSelectRef(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    maxOptions: Int? = null,
    tsOptions: TomSelectOptions? = null,
    tsCallbacks: TomSelectCallbacks? = null,
    tsRenders: TomSelectRenders? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITomSelect.() -> Unit = {}
): TomSelect {
    return key(multiple) {
        val component = remember {
            TomSelect(
                options,
                value,
                emptyOption,
                multiple,
                maxOptions,
                tsOptions,
                tsCallbacks,
                tsRenders,
                name,
                placeholder,
                disabled,
                required,
                className % "form-select",
                id,
                renderConfig
            )
        }
        DisposableEffect(component.componentId) {
            component.onInsert()
            onDispose {
                component.onRemove()
            }
        }
        ComponentNode(component, {
            set(options) { updateProperty(TomSelect::options, it) }
            set(value) { updateProperty(TomSelect::value, it) }
            set(emptyOption) { updateProperty(TomSelect::emptyOption, it) }
            set(maxOptions) { updateProperty(TomSelect::maxOptions, it) }
            set(tsOptions) { updateProperty(TomSelect::tsOptions, it) }
            set(tsCallbacks) { updateProperty(TomSelect::tsCallbacks, it) }
            set(tsRenders) { updateProperty(TomSelect::tsRenders, it) }
            set(name) { updateProperty(TomSelect::name, it) }
            set(placeholder) { updateProperty(TomSelect::placeholder, it) }
            set(disabled) { updateProperty(TomSelect::disabled, it) }
            set(required) { updateProperty(TomSelect::required, it) }
            set(className) { updateProperty(TomSelect::className, it % "form-select") }
            set(id) { updateProperty(TomSelect::id, it) }
        }, setup)
        div() // Empty div as a placeholder for the generated HTML element
        component
    }
}

/**
 * Creates [TomSelect] component.
 *
 * @param options a list of options (value to label pairs)
 * @param value initial value
 * @param emptyOption determines if an empty option is allowed
 * @param multiple determines if multiple value selection is allowed
 * @param maxOptions the maximum number of visible options
 * @param tsOptions Tom Select options
 * @param tsCallbacks Tom Select callbacks
 * @param tsRenders Tom Select renders
 * @param name the name of the select
 * @param placeholder the placeholder for the select component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 */
@Composable
public fun IComponent.tomSelect(
    options: List<StringPair>? = null,
    value: String? = null,
    emptyOption: Boolean = false,
    multiple: Boolean = false,
    maxOptions: Int? = null,
    tsOptions: TomSelectOptions? = null,
    tsCallbacks: TomSelectCallbacks? = null,
    tsRenders: TomSelectRenders? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITomSelect.() -> Unit = {}
) {
    key(multiple) {
        val component = remember {
            TomSelect(
                options,
                value,
                emptyOption,
                multiple,
                maxOptions,
                tsOptions,
                tsCallbacks,
                tsRenders,
                name,
                placeholder,
                disabled,
                required,
                className % "form-select",
                id,
                renderConfig
            )
        }
        DisposableEffect(component.componentId) {
            component.onInsert()
            onDispose {
                component.onRemove()
            }
        }
        ComponentNode(component, {
            set(options) { updateProperty(TomSelect::options, it) }
            set(value) { updateProperty(TomSelect::value, it) }
            set(emptyOption) { updateProperty(TomSelect::emptyOption, it) }
            set(maxOptions) { updateProperty(TomSelect::maxOptions, it) }
            set(tsOptions) { updateProperty(TomSelect::tsOptions, it) }
            set(tsCallbacks) { updateProperty(TomSelect::tsCallbacks, it) }
            set(tsRenders) { updateProperty(TomSelect::tsRenders, it) }
            set(name) { updateProperty(TomSelect::name, it) }
            set(placeholder) { updateProperty(TomSelect::placeholder, it) }
            set(disabled) { updateProperty(TomSelect::disabled, it) }
            set(required) { updateProperty(TomSelect::required, it) }
            set(className) { updateProperty(TomSelect::className, it % "form-select") }
            set(id) { updateProperty(TomSelect::id, it) }
        }, setup)
        div() // Empty div as a placeholder for the generated HTML element
    }
}
