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
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.externals.TomSelectJs
import dev.kilua.externals.TomSelectOptionsJs
import dev.kilua.externals.assign
import dev.kilua.externals.obj
import dev.kilua.form.StringFormControl
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.state.WithStateFlow
import dev.kilua.state.WithStateFlowDelegate
import dev.kilua.state.WithStateFlowDelegateImpl
import dev.kilua.utils.StringPair
import dev.kilua.utils.cast
import dev.kilua.utils.rem
import dev.kilua.utils.toJsAny
import dev.kilua.utils.toList
import web.JsAny
import web.JsArray
import web.JsString
import web.dom.HTMLOptionElement
import web.dom.HTMLSelectElement
import web.dom.asList
import web.toJsString

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
    renderConfig: RenderConfig = DefaultRenderConfig(),
    protected val withStateFlowDelegate: WithStateFlowDelegate<String?> = WithStateFlowDelegateImpl()
) : Tag<HTMLSelectElement>("select", className, id, renderConfig), StringFormControl,
    WithStateFlow<String?> by withStateFlowDelegate {

    /**
     * The list of options (value to label pairs).
     */
    public open var options: List<StringPair>? by updatingProperty(options) {
        refresh()
    }

    public override var value: String? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        refreshValue()
    }

    /**
     * Determines if an empty option is allowed.
     */
    public var emptyOption: Boolean by updatingProperty(emptyOption) {
        refresh()
    }

    /**
     * Determines if multiple value selection is allowed.
     */
    internal var multiple: Boolean by updatingProperty(multiple) {
    }

    /**
     * The maximum number of visible options.
     */
    public var maxOptions: Int? by updatingProperty(maxOptions) {
        refresh()
    }

    /**
     * Tom Select options.
     */
    public var tsOptions: TomSelectOptions? by updatingProperty(tsOptions) {
        refresh()
    }

    /**
     * Tom Select callbacks.
     */
    public var tsCallbacks: TomSelectCallbacks? by updatingProperty(tsCallbacks) {
        refresh()
    }

    /**
     * Tom Select renders.
     */
    public var tsRenders: TomSelectRenders? by updatingProperty(tsRenders) {
        refresh()
    }

    /**
     * Disable searching in options.
     */
    public var disableSearch: Boolean by updatingProperty(false) {
        refresh()
    }

    public override var name: String? by updatingProperty(name) {
        if (it != null) {
            element.name = it
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * The placeholder for the select component.
     */
    public var placeholder: String? by updatingProperty(placeholder) {
        refresh()
    }

    public override var disabled: Boolean? by updatingProperty(disabled) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
        refresh()
    }

    public override var required: Boolean? by updatingProperty(required) {
        if (it != null) {
            element.required = it
        } else {
            element.removeAttribute("required")
        }
    }

    /**
     * The autofocus attribute of the generated HTML element.
     */
    public override var autofocus: Boolean? by updatingProperty {
        if (it != null) {
            element.autofocus = it
        } else {
            element.removeAttribute("autofocus")
        }
    }

    public override var customValidity: String? by updatingProperty {
        element.setCustomValidity(it ?: "")
    }

    /**
     * Tom Select native component instance.
     */
    public var tomSelectInstance: TomSelectJs? = null

    /**
     * The label of the currently selected option.
     */
    public val selectedLabel: String?
        get() = element.options.asList().find {
            it.cast<HTMLOptionElement>().value == this.value
        }?.textContent

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
        if (renderConfig.isDom) {
            if (multiple) {
                element.multiple = true
            }
            if (name != null) {
                element.name = name
            }
            if (placeholder != null) {
                element.setAttribute("placeholder", placeholder)
            }
            if (disabled != null) {
                element.disabled = disabled
            }
            if (required != null) {
                element.required = required
            }
        }
        @Suppress("LeakingThis")
        onChangeDirect {
            val v = if (this.multiple) {
                tomSelectInstance?.getValue()?.cast<JsArray<JsString>>()?.toList()?.joinToString(",")
            } else {
                tomSelectInstance?.getValue()?.cast<JsString>().toString()
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
        tomSelectInstance?.destroy()
        tomSelectInstance = null
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
            tomSelectInstance?.destroy()
            initializeTomSelect()
        }
    }

    /**
     * Initializes the Tom Select instance.
     */
    protected open fun initializeTomSelect() {
        if (renderConfig.isDom) {
            val self = this
            val tomSelectOptions = obj<TomSelectOptionsJs> {
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
                    this.render = obj {
                        no_results = null
                    }
                }
            }
            tomSelectInstance = TomSelectJs(element, tomSelectOptions)
            refreshValue()
        }
    }

    /**
     * Removes all unselected options from the control.
     */
    public open fun clearOptions() {
        tomSelectInstance?.clearOptions { true }
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
 * @return a [TomSelect] component
 */
@Composable
public fun ComponentBase.tomSelect(
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
    setup: @Composable TomSelect.() -> Unit = {}
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
        component
    }
}
