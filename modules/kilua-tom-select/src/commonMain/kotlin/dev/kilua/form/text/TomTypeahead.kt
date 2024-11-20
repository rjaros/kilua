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

package dev.kilua.form.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.TomSelectJs
import dev.kilua.externals.TomSelectOptionsJs
import dev.kilua.externals.assign
import dev.kilua.externals.get
import dev.kilua.externals.obj
import dev.kilua.form.Autocomplete
import dev.kilua.form.InputType
import dev.kilua.form.select.TomSelectCallbacks
import dev.kilua.form.select.toJs
import dev.kilua.html.div
import dev.kilua.utils.cast
import dev.kilua.utils.jsObjectOf
import dev.kilua.utils.rem
import dev.kilua.utils.toJsAny
import dev.kilua.utils.toJsArray
import dev.kilua.utils.toList
import dev.kilua.utils.unsafeCast
import web.JsAny
import web.JsArray
import web.JsBoolean
import web.JsString
import web.document
import web.toBoolean
import web.toJsString

/**
 * Tom Typeahead input component
 */
public interface ITomTypeahead : IText {
    /**
     * The list of options.
     */
    public val options: List<String>?

    /**
     * Set the list of options.
     */
    @Composable
    public fun options(options: List<String>?)

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
     * Tom Select native component instance.
     */
    public val tomSelectInstance: TomSelectJs?

}

/**
 * Tom Typeahead input component.
 */
public open class TomTypeahead(
    options: List<String>? = null,
    value: String? = null,
    type: InputType = InputType.Text,
    tsCallbacks: TomSelectCallbacks? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) : Text(
    value,
    type,
    name,
    null,
    placeholder,
    disabled,
    required,
    className,
    id,
    renderConfig
), ITomTypeahead {

    /**
     * The list of options.
     */
    public override var options: List<String>? by updatingProperty(options) {
        refresh()
    }

    /**
     * Set the list of options.
     */
    @Composable
    public override fun options(options: List<String>?): Unit = composableProperty("options", {
        this.options = null
    }) {
        this.options = options
    }

    public override var value: String? by updatingProperty(
        value,
        notifyFunction = { withStateFlowDelegate.updateStateFlow(it) }) {
        refreshValue()
    }

    override var id: String? by updatingProperty(id) {
        if (it != null) {
            element.id = it
        } else {
            element.removeAttribute("id")
        }
        refresh()
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

    public override var disabled: Boolean? by updatingProperty(disabled) {
        if (it != null) {
            element.disabled = it
        } else {
            element.removeAttribute("disabled")
        }
        refresh()
    }

    /**
     * Tom Select native component instance.
     */
    public override var tomSelectInstance: TomSelectJs? = null

    init {
        @Suppress("LeakingThis")
        autocomplete = Autocomplete.Off
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
            if (value != null) {
                val existingOption = tomSelectInstance?.getOption(value!!, false)
                if (existingOption == null) {
                    tomSelectInstance?.clearOptions { option ->
                        option["created"]?.unsafeCast<JsBoolean>()?.toBoolean() != true ||
                                option["value"]?.unsafeCast<JsString>()?.toString() == value
                    }
                    tomSelectInstance?.addOption(
                        jsObjectOf(
                            "value" to value,
                            "text" to value,
                            "created" to true
                        ), false
                    )
                }
                tomSelectInstance?.setValue(value!!.toJsString(), true)
            } else {
                tomSelectInstance?.clear(true)
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
            val tomSelectOptions = obj<TomSelectOptionsJs> {
                this.maxItems = 1
                this.create = { input: JsString ->
                    val oldValue = tomSelectInstance?.getValue()?.unsafeCast<JsString>()?.toString()?.ifBlank { null }
                        ?.let { "$it " } ?: ""
                    jsObjectOf(
                        "value" to "$oldValue$input",
                        "text" to "$oldValue$input",
                        "created" to true
                    )
                }.cast()
                this.createOnBlur = true
                this.persist = true
                this.openOnFocus = true
                this.duplicates = true
                if (self.options != null) {
                    this.options = self.options!!.map {
                        jsObjectOf(
                            "value" to it,
                            "text" to it
                        )
                    }.toJsArray()
                }
                this.render = obj {
                    this.option_create = { _: JsAny, _: (String) -> String ->
                        ""
                    }
                    this.no_results = null
                }
                this.plugins = listOf("restore_on_backspace", "change_listener").toJsAny()!!
                if (self.tsCallbacks != null) {
                    val callbacksObj = self.tsCallbacks!!.toJs()
                    assign(this, callbacksObj)
                    if (self.tsCallbacks!!.load != null) {
                        this.load = { query: String, callback: (JsArray<JsAny>) -> Unit ->
                            tsCallbacks!!.load!!(query) { options ->
                                callback(
                                    options.toList().map {
                                        jsObjectOf(
                                            "value" to it,
                                            "text" to it
                                        )
                                    }.toJsArray()
                                )
                            }
                        }
                    }
                }
                this.onOptionAdd = { value: String, _: JsAny ->
                    tomSelectInstance?.clearOptions { option: JsAny ->
                        option["created"]?.unsafeCast<JsBoolean>()?.toBoolean() != true ||
                                option["value"]?.unsafeCast<JsString>()?.toString() == value
                    }
                }
                this.onFocus = {
                    if (tomSelectInstance?.getValue()?.toString() != "") {
                        tomSelectInstance?.removeItem(tomSelectInstance?.getValue()?.toString()!!, false)
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
            element.insertAdjacentElement("afterend", emptyDiv)
        }
    }

}

/**
 * Creates [TomTypeahead] component, returning a reference.
 *
 * @param options a list of options
 * @param value initial value
 * @param type the type of the input
 * @param tsCallbacks Tom Select callbacks
 * @param name the name of the select
 * @param placeholder the placeholder for the input component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 * @return a [TomTypeahead] component
 */
@Composable
public fun IComponent.tomTypeaheadRef(
    options: List<String>? = null,
    value: String? = null,
    type: InputType = InputType.Text,
    tsCallbacks: TomSelectCallbacks? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITomTypeahead.() -> Unit = {}
): TomTypeahead {
    val component = remember {
        TomTypeahead(
            options,
            value,
            type,
            tsCallbacks,
            name,
            placeholder,
            disabled,
            required,
            className % "form-control kilua-typeahead",
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
        set(options) { updateProperty(TomTypeahead::options, it) }
        set(value) { updateProperty(TomTypeahead::value, it) }
        set(type) { updateProperty(TomTypeahead::type, it) }
        set(tsCallbacks) { updateProperty(TomTypeahead::tsCallbacks, it) }
        set(name) { updateProperty(TomTypeahead::name, it) }
        set(placeholder) { updateProperty(TomTypeahead::placeholder, it) }
        set(disabled) { updateProperty(TomTypeahead::disabled, it) }
        set(required) { updateProperty(TomTypeahead::required, it) }
        set(className) { updateProperty(TomTypeahead::className, it % "form-control kilua-typeahead") }
        set(id) { updateProperty(TomTypeahead::id, it) }
    }, setup)
    div() // Empty div as a placeholder for the generated HTML element
    return component
}

/**
 * Creates [TomTypeahead] component.
 *
 * @param options a list of options
 * @param value initial value
 * @param type the type of the input
 * @param tsCallbacks Tom Select callbacks
 * @param name the name of the select
 * @param placeholder the placeholder for the input component
 * @param disabled whether the select is disabled
 * @param required whether the select is required
 * @param className the CSS class name
 * @param id the ID of the select component
 * @param setup a function for setting up the component
 */
@Composable
public fun IComponent.tomTypeahead(
    options: List<String>? = null,
    value: String? = null,
    type: InputType = InputType.Text,
    tsCallbacks: TomSelectCallbacks? = null,
    name: String? = null,
    placeholder: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable ITomTypeahead.() -> Unit = {}
) {
    val component = remember {
        TomTypeahead(
            options,
            value,
            type,
            tsCallbacks,
            name,
            placeholder,
            disabled,
            required,
            className % "form-control kilua-typeahead",
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
        set(options) { updateProperty(TomTypeahead::options, it) }
        set(value) { updateProperty(TomTypeahead::value, it) }
        set(type) { updateProperty(TomTypeahead::type, it) }
        set(tsCallbacks) { updateProperty(TomTypeahead::tsCallbacks, it) }
        set(name) { updateProperty(TomTypeahead::name, it) }
        set(placeholder) { updateProperty(TomTypeahead::placeholder, it) }
        set(disabled) { updateProperty(TomTypeahead::disabled, it) }
        set(required) { updateProperty(TomTypeahead::required, it) }
        set(className) { updateProperty(TomTypeahead::className, it % "form-control kilua-typeahead") }
        set(id) { updateProperty(TomTypeahead::id, it) }
    }, setup)
    div() // Empty div as a placeholder for the generated HTML element
}
