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

package dev.kilua.form

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import dev.kilua.KiluaScope
import dev.kilua.compose.ComponentNode
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.externals.JSON
import dev.kilua.externals.keys
import dev.kilua.html.Tag
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.state.WithStateFlow
import dev.kilua.types.KFile
import dev.kilua.utils.Serialization
import dev.kilua.utils.Serialization.toObj
import dev.kilua.utils.cast
import dev.kilua.utils.jsGet
import dev.kilua.utils.jsSet
import dev.kilua.utils.nativeMapOf
import dev.kilua.utils.obj
import dev.kilua.utils.toJsAny
import dev.kilua.utils.toJsBoolean
import dev.kilua.utils.toJsNumber
import dev.kilua.utils.toJsString
import dev.kilua.utils.toKebabCase
import js.core.JsAny
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.overwriteWith
import kotlinx.serialization.serializer
import web.html.HTMLFormElement
import web.window.WindowName
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1

/**
 * Default error message for an empty required form field.
 */
public const val REQUIRED_FIELD_MESSAGE: String = "Value is required"

/**
 * Default error message for an invalid form field.
 */
public const val INVALID_FIELD_MESSAGE: String = "Invalid value"

/**
 * Form methods.
 */
public enum class FormMethod {
    Get,
    Post;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Form encoding types.
 */
public enum class FormEnctype(public val value: String) {
    Urlencoded("application/x-www-form-urlencoded"),
    Multipart("multipart/form-data"),
    Plain("text/plain");

    override fun toString(): String {
        return value
    }
}

/**
 * Form methods.
 */
public enum class FormAutocomplete {
    On,
    Off;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * HTML Form component.
 */
@Suppress("TooManyFunctions")
public class Form<K : Any>(
    method: FormMethod? = null, action: String? = null, enctype: FormEnctype? = null,
    private val requiredMessage: String = REQUIRED_FIELD_MESSAGE,
    private val invalidMessage: String = INVALID_FIELD_MESSAGE,
    private val serializer: KSerializer<K>? = null,
    private val customSerializers: Map<KClass<*>, KSerializer<*>>? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default
) :
    Tag<HTMLFormElement>("form", className, id, renderConfig = renderConfig), WithStateFlow<K> {

    /**
     * The method attribute of the generated HTML form element.
     */
    public var method: FormMethod? by updatingProperty(method) {
        if (it != null) {
            element.method = when (it) {
                FormMethod.Get -> web.form.FormMethod.get
                FormMethod.Post -> web.form.FormMethod.post
            }
        } else {
            element.removeAttribute("method")
        }
    }

    /**
     * Sets the method attribute of the generated HTML form element.
     */
    @Composable
    public fun method(value: FormMethod?): Unit = composableProperty("method", {
        method = null
    }) {
        method = value
    }

    /**
     * The action attribute of the generated HTML form element.
     */
    public var action: String? by updatingProperty(action) {
        if (it != null) {
            element.action = it.toString()
        } else {
            element.removeAttribute("action")
        }
    }

    /**
     * Sets the action attribute of the generated HTML form element.
     */
    @Composable
    public fun action(value: String?): Unit = composableProperty("action", {
        action = null
    }) {
        action = value
    }

    /**
     * The enctype attribute of the generated HTML form element.
     */
    public var enctype: FormEnctype? by updatingProperty(enctype) {
        if (it != null) {
            element.enctype = when(it) {
                FormEnctype.Urlencoded -> web.form.FormEncType.applicationXWwwFormUrlencoded
                FormEnctype.Multipart -> web.form.FormEncType.multipartFormData
                FormEnctype.Plain -> web.form.FormEncType.textPlain
            }
        } else {
            element.removeAttribute("enctype")
        }
    }

    /**
     * Sets the enctype attribute of the generated HTML form element.
     */
    @Composable
    public fun enctype(value: FormEnctype?): Unit = composableProperty("enctype", {
        enctype = null
    }) {
        enctype = value
    }

    /**
     * The name attribute of the generated HTML form element.
     */
    public var name: String? by updatingProperty {
        if (it != null) {
            element.name = it
        } else {
            element.removeAttribute("name")
        }
    }

    /**
     * Sets the name attribute of the generated HTML form element.
     */
    @Composable
    public fun name(value: String?): Unit = composableProperty("name", {
        name = null
    }) {
        name = value
    }

    /**
     * The target attribute of the generated HTML form element.
     */
    public var target: String? by updatingProperty {
        if (it != null) {
            element.target = WindowName(it)
        } else {
            element.removeAttribute("target")
        }
    }

    /**
     * Sets the target attribute of the generated HTML form element.
     */
    @Composable
    public fun target(value: String?): Unit = composableProperty("target", {
        target = null
    }) {
        target = value
    }

    /**
     * The target attribute of the generated HTML form element.
     */
    public var novalidate: Boolean? by updatingProperty {
        if (it != null) {
            element.noValidate = it
        } else {
            element.removeAttribute("novalidate")
        }
    }

    /**
     * Sets the target attribute of the generated HTML form element.
     */
    @Composable
    public fun novalidate(value: Boolean?): Unit = composableProperty("novalidate", {
        novalidate = null
    }) {
        novalidate = value
    }

    /**
     * The target attribute of the generated HTML form element.
     */
    public var autocomplete: FormAutocomplete? by updatingProperty {
        if (it != null) {
            element.autocomplete = when(it) {
                FormAutocomplete.On -> web.autofill.AutoFillBase.on
                FormAutocomplete.Off -> web.autofill.AutoFillBase.off
            }
        } else {
            element.removeAttribute("autocomplete")
        }
    }

    /**
     * Sets the target attribute of the generated HTML form element.
     */
    @Composable
    public fun autocomplete(value: FormAutocomplete?): Unit = composableProperty("autocomplete", {
        autocomplete = null
    }) {
        autocomplete = value
    }

    /**
     * A custom validator function.
     */
    public var validator: ((Validation<K>) -> Validation<K>)? = null

    /**
     * Keeps values of the form not bound to any input components.
     */
    private val dataMap: MutableMap<String, Any?> = nativeMapOf()

    /**
     * Helper functions to convert data between the form and the model.
     */
    private val mapToObjectConverter: ((Map<String, Any?>) -> JsAny)?
    private val mapToClassConverter: ((Map<String, Any?>) -> K)?
    private val classToObjectConverter: ((K) -> JsAny)?

    /**
     * Keeps all form controls.
     */
    private val fields: LinkedHashMap<String, FormControl<*>> = linkedMapOf()

    /**
     * Keeps all form controls parameters.
     */
    private val fieldsParams: MutableMap<String, Any> = nativeMapOf()

    /**
     * Determines if the data model was set outside compose.
     */
    private var dataSet: Boolean = false

    @OptIn(ExperimentalSerializationApi::class)
    private val jsonInstance: Json? = serializer?.let {
        Json(
            from = (Serialization.customConfiguration ?: Json.Default)
        ) {
            encodeDefaults = true
            explicitNulls = false
            serializersModule = serializersModule.overwriteWith(SerializersModule {
                customSerializers?.forEach { (kclass, serializer) ->
                    contextual(kclass.cast(), serializer.cast<KSerializer<Any>>())
                }
            })
        }
    }

    /**
     * Whether the data state flow is initialized.
     */
    private var initializedDataStateFlow: Boolean = false

    /**
     * Internal mutable state flow instance (lazily initialized)
     */
    private val _mutableDataStateFlow: MutableStateFlow<K> by lazy {
        initializedDataStateFlow = true
        MutableStateFlow(getData())
    }

    override val stateFlow: StateFlow<K>
        get() = _mutableDataStateFlow.asStateFlow()

    override val mutableStateFlow: MutableStateFlow<K>
        get() = _mutableDataStateFlow

    private fun updateStateFlow(value: K) {
        if (initializedDataStateFlow) {
            _mutableDataStateFlow.value = value
        }
    }

    /**
     * Whether the validation results state flow is initialized.
     */
    private var initializedValidationStateFlow: Boolean = false

    /**
     * Internal mutable state flow instance for validation results (lazily initialized).
     */
    private val _mutableValidationStateFlow: MutableStateFlow<Validation<K>> by lazy {
        initializedValidationStateFlow = true
        MutableStateFlow(Validation())
    }

    /**
     * Validation results state flow.
     */
    public val validationStateFlow: StateFlow<Validation<K>>
        get() = _mutableValidationStateFlow.asStateFlow()

    init {
        if (renderConfig.isDom) {
            if (method != null) {
                element.method = when(method) {
                    FormMethod.Get -> web.form.FormMethod.get
                    FormMethod.Post -> web.form.FormMethod.post
                }
            }
            if (action != null) {
                element.action = action
            }
            if (enctype != null) {
                element.enctype = when(enctype) {
                    FormEnctype.Urlencoded -> web.form.FormEncType.applicationXWwwFormUrlencoded
                    FormEnctype.Multipart -> web.form.FormEncType.multipartFormData
                    FormEnctype.Plain -> web.form.FormEncType.textPlain
                }
            }
        }
        mapToObjectConverter = serializer?.let {
            { map ->
                val json = obj()
                map.forEach { (key, value) ->
                    when (value) {
                        is LocalDate, is LocalDateTime, is LocalTime -> {
                            json.jsSet(key, value.toString().toJsString())
                        }

                        is String -> {
                            json.jsSet(key, value.toJsString())
                        }

                        is Boolean -> {
                            json.jsSet(key, value.toJsBoolean())
                        }

                        is Int -> {
                            json.jsSet(key, value.toJsNumber())
                        }

                        is Double -> {
                            json.jsSet(key, value.toJsNumber())
                        }

                        is List<*> -> {
                            @Suppress("UNCHECKED_CAST")
                            (value as? List<KFile>)?.toObj(ListSerializer(KFile.serializer()))?.let {
                                json.jsSet(key, it)
                            }
                        }

                        else -> {
                            if (value != null) {
                                json.jsSet(key, value.toJsAny()!!)
                            }
                        }
                    }
                }
                json
            }
        }
        mapToClassConverter = serializer?.let {
            { map ->
                jsonInstance!!.decodeFromString(serializer, JSON.stringify(mapToObjectConverter!!.invoke(map)))
            }
        }
        classToObjectConverter = serializer?.let {
            {
                JSON.parse(jsonInstance!!.encodeToString(serializer, it))
            }
        }
    }


    /**
     * Sets the values of all the controls from the single json Object.
     * @param json data model as Object
     */
    private fun setDataInternalFromSingleObject(json: JsAny, key: String) {
        val jsonValue = json.jsGet(key)
        if (jsonValue != null) {
            when (val formField = fields[key]) {
                is StringFormControl -> formField.value = jsonValue.toString()
                is DateFormControl -> formField.value = LocalDate.parse(jsonValue.toString())
                is DateTimeFormControl -> formField.value = LocalDateTime.parse(jsonValue.toString())
                is TimeFormControl -> formField.value = LocalTime.parse(jsonValue.toString())
                is TriStateFormControl -> formField.value = jsonValue.toString().toBoolean()
                is BoolFormControl -> formField.value = jsonValue.toString().toBoolean()
                is IntFormControl -> formField.value = jsonValue.toString().toInt()
                is NumberFormControl -> formField.value = jsonValue.toString().toDouble()
                is KFilesFormControl -> {
                    formField.value = Json.decodeFromString(
                        ListSerializer(KFile.serializer()),
                        JSON.stringify(jsonValue)
                    )
                }

                else -> {
                    if (formField != null) {
                        error("Unsupported form field type: ${formField::class.simpleName}")
                    } else {
                        dataMap[key] = jsonValue
                    }
                }
            }
        } else {
            fields[key]?.setValue(null)
        }
    }

    /**
     * Sets the values of all the controls from the json Object.
     * @param json data model as Object
     */
    private fun setDataInternalFromObject(json: JsAny) {
        val keys = keys(json)
        for (key in keys) {
            setDataInternalFromSingleObject(json, key)
        }
        fields.forEach { if (!keys.contains(it.key)) it.value.setValue(null) }
    }

    /**
     * Sets the values of all the controls from the map.
     * @param map data model as Map
     */
    private fun setDataInternalFromMap(map: Map<String, Any?>) {
        map.forEach { (key, value) ->
            if (value != null) {
                val formField = fields[key]
                if (formField != null) {
                    formField.setValue(value)
                } else {
                    dataMap[key] = value
                }
            } else {
                fields[key]?.setValue(null)
            }
        }
        fields.forEach { if (!map.contains(it.key)) it.value.setValue(null) }
    }

    /**
     * Sets the values of all the controls from the model.
     * @param model data model
     */
    private fun setDataInternal(model: K) {
        val oldData = getData()
        dataMap.clear()
        if (classToObjectConverter != null) {
            val json = classToObjectConverter.invoke(model)
            setDataInternalFromObject(json)
        } else {
            val map = model.cast<Map<String, Any?>>()
            setDataInternalFromMap(map)
        }
        if (oldData != model) updateStateFlow(model)
    }

    /**
     * Sets the values of all controls to null.
     */
    private fun clearDataInternal() {
        val oldData = getData()
        dataMap.clear()
        fields.forEach { it.value.setValue(null) }
        val newData = getData()
        if (oldData != newData) updateStateFlow(newData)
    }

    /**
     * Sets the values of all the controls from the model.
     * @param model data model
     */
    public fun setData(model: K) {
        dataSet = true
        setDataInternal(model)
    }

    /**
     * Sets the values of all controls to null.
     */
    public fun clearData() {
        dataSet = true
        clearDataInternal()
    }

    /**
     * Sets the values of all the controls from the model when not already set by setData or clearData.
     * @param model data model
     */
    @PublishedApi
    internal fun setDataFromCompose(model: K) {
        if (!dataSet) setDataInternal(model)
    }

    /**
     * Returns current data model.
     * @return data model
     */
    public fun getData(): K {
        val map = dataMap + fields.entries.associateBy({ it.key }, { it.value.getValue() })
        return mapToClassConverter?.invoke(map.withDefault { null }) ?: map.cast()
    }


    /**
     * Returns current data model with file content read for all KFiles controls.
     * @return data model
     */
    public suspend fun getDataWithFileContent(): K {
        val map = dataMap + fields.entries.associateBy({ it.key }, {
            val value = it.value
            if (value is KFilesFormControl) {
                value.getFilesWithContent()
            } else {
                value.getValue()
            }
        })
        return mapToClassConverter?.invoke(map.withDefault { null }) ?: map.cast()
    }

    /**
     * Returns current data model as JS object.
     * @return data model as JS object
     */
    public fun getDataJson(): JsAny {
        return if (serializer != null) {
            JSON.parse(
                jsonInstance!!.encodeToString(
                    serializer,
                    getData()
                )
            )
        } else {
            mapToObjectConverter!!.invoke(getData().cast())
        }
    }

    /**
     * Binds a form control to the form with a dynamic key.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @param validatorWithMessage optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    private fun <T, C : FormControl<T>> C.bind(
        key: String,
        validator: ((C) -> Boolean)? = null,
        validatorWithMessage: ((C) -> Pair<Boolean, String?>)? = null
    ): C {
        this@Form.fields[key] = this
        this@Form.fieldsParams[key] = FieldParams(validator, validatorWithMessage)
        if (validator != null || validatorWithMessage != null) novalidate = true
        DisposableEffect(key) {
            val job = this@bind.stateFlow.onEach {
                this@Form.updateStateFlow(getData())
            }.launchIn(KiluaScope)
            onDispose {
                job.cancel()
                unbind(key)
            }
        }
        return this
    }

    /**
     * Binds a form control to the form with a dynamic key.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <T, C : FormControl<T>> C.bind(
        key: String,
        validator: ((C) -> Boolean)? = null,
    ): C {
        return bind(key, validator, null)
    }

    /**
     * Binds a form control to the form with a dynamic key.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <T, C : FormControl<T>> C.bindWithValidationMessage(
        key: String,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key, null, validator)
    }

    /**
     * Bind a string control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <C : StringFormControl> C.bind(
        key: KProperty1<K, String?>,
        validator: ((C) -> Boolean)? = null
    ): C {
        return bind(key.name, validator, null)
    }

    /**
     * Bind a string control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    public fun <C : StringFormControl> C.bindWithValidationMessage(
        key: KProperty1<K, String?>,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key.name, null, validator)
    }

    /**
     * Bind a string control to the form bound to custom field type.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <C : StringFormControl> C.bindCustom(
        key: KProperty1<K, Any?>,
        validator: ((C) -> Boolean)? = null
    ): C {
        return bind(key.name, validator, null)
    }

    /**
     * Bind a string control to the form bound to custom field type.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    public fun <C : StringFormControl> C.bindCustomWithValidationMessage(
        key: KProperty1<K, Any?>,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key.name, null, validator)
    }

    /**
     * Bind a boolean control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <C : BoolFormControl> C.bind(
        key: KProperty1<K, Boolean?>,
        validator: ((C) -> Boolean)? = null
    ): C {
        return bind(key.name, validator, null)
    }

    /**
     * Bind a boolean control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    public fun <C : BoolFormControl> C.bindWithValidationMessage(
        key: KProperty1<K, Boolean?>,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key.name, null, validator)
    }

    /**
     * Bind a nullable boolean control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <C : TriStateFormControl> C.bind(
        key: KProperty1<K, Boolean?>,
        validator: ((C) -> Boolean)? = null
    ): C {
        return bind(key.name, validator, null)
    }

    /**
     * Bind a nullable boolean control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    public fun <C : TriStateFormControl> C.bindWithValidationMessage(
        key: KProperty1<K, Boolean?>,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key.name, null, validator)
    }

    /**
     * Bind an integer control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <C : IntFormControl> C.bind(
        key: KProperty1<K, Int?>,
        validator: ((C) -> Boolean)? = null
    ): C {
        return bind(key.name, validator, null)
    }

    /**
     * Bind an integer control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    public fun <C : IntFormControl> C.bindWithValidationMessage(
        key: KProperty1<K, Int?>,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key.name, null, validator)
    }

    /**
     * Bind a number control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <C : NumberFormControl> C.bind(
        key: KProperty1<K, Number?>,
        validator: ((C) -> Boolean)? = null
    ): C {
        return bind(key.name, validator, null)
    }

    /**
     * Bind a number control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    public fun <C : NumberFormControl> C.bindWithValidationMessage(
        key: KProperty1<K, Number?>,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key.name, null, validator)
    }

    /**
     * Bind a date control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <C : DateFormControl> C.bind(
        key: KProperty1<K, LocalDate?>,
        validator: ((C) -> Boolean)? = null
    ): C {
        return bind(key.name, validator, null)
    }

    /**
     * Bind a date control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    public fun <C : DateFormControl> C.bindWithValidationMessage(
        key: KProperty1<K, LocalDate?>,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key.name, null, validator)
    }

    /**
     * Bind a datetime control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <C : DateTimeFormControl> C.bind(
        key: KProperty1<K, LocalDateTime?>,
        validator: ((C) -> Boolean)? = null
    ): C {
        return bind(key.name, validator, null)
    }

    /**
     * Bind a datetime control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    public fun <C : DateTimeFormControl> C.bindWithValidationMessage(
        key: KProperty1<K, LocalDateTime?>,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key.name, null, validator)
    }

    /**
     * Bind a time control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <C : TimeFormControl> C.bind(
        key: KProperty1<K, LocalTime?>,
        validator: ((C) -> Boolean)? = null
    ): C {
        return bind(key.name, validator, null)
    }

    /**
     * Bind a time control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    public fun <C : TimeFormControl> C.bindWithValidationMessage(
        key: KProperty1<K, LocalTime?>,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key.name, null, validator)
    }

    /**
     * Bind a files control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     * @return the control itself
     */
    @Composable
    public fun <C : KFilesFormControl> C.bind(
        key: KProperty1<K, List<KFile>?>,
        validator: ((C) -> Boolean)? = null
    ): C {
        return bind(key.name, validator, null)
    }

    /**
     * Bind a files control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     * @return the control itself
     */
    @Composable
    public fun <C : KFilesFormControl> C.bindWithValidationMessage(
        key: KProperty1<K, List<KFile>?>,
        validator: ((C) -> Pair<Boolean, String?>)? = null,
    ): C {
        return bind(key.name, null, validator)
    }

    /**
     * Unbind a control from the form.
     * @param key key identifier of the control
     */
    public fun unbind(key: KProperty1<K, *>) {
        unbind(key.name)
    }

    /**
     * Unbind a control from the form with a dynamic key.
     * @param key key identifier of the control
     */
    public fun unbind(key: String) {
        this.fields.remove(key)
        this.fieldsParams.remove(key)
    }

    /**
     * Invokes validator function and validates the form.
     * @param updateState whether to update the validation state flow
     * @return validation result
     */
    @Suppress("ComplexMethod")
    public fun validate(updateState: Boolean = true): Boolean {
        checkValidity()
        val fieldsValidations = fields.map { (key, control) ->
            @Suppress("UNCHECKED_CAST")
            val fieldsParams = (fieldsParams[key] as FieldParams<*, FormControl<*>>)
            val required = control.required ?: false
            val isEmptyWhenRequired = (control.getValue() == null || control.value == false)
                    && control.visible && required
            val requiredMessage = if (isEmptyWhenRequired) requiredMessage else null
            val (isInvalid, validMessage, invalidMessage) = if (fieldsParams.validator != null) {
                val isInvalid = control.visible && !fieldsParams.validator.invoke(control)
                val invalidMessage = if (isInvalid) invalidMessage else null
                Triple(isInvalid, null, invalidMessage)
            } else if (fieldsParams.validatorWithMessage != null) {
                val (result, message) = fieldsParams.validatorWithMessage.invoke(control)
                val isInvalid = control.visible && !result
                val invalidMessage = if (isInvalid) (message ?: invalidMessage) else null
                val validMessage = if (!isInvalid) message else null
                Triple(isInvalid, validMessage, invalidMessage)
            } else {
                Triple(false, null, null)
            }
            val fieldValidation = FieldValidation(
                isEmptyWhenRequired,
                isInvalid,
                validMessage,
                invalidMessage ?: requiredMessage
            )
            key to fieldValidation
        }.toMap()
        val hasInvalidField = fieldsValidations.map { it.value }.find { it.isInvalid || it.isEmptyWhenRequired } != null
        val validation = Validation<K>(true, hasInvalidField, null, null, fieldsValidations)
        val validationWithValidator = if (validator != null) {
            validator!!.invoke(validation)
        } else {
            validation
        }
        validation.fieldsValidations.forEach { (key, fieldValidation) ->
            getControl(key)?.customValidity = fieldValidation.invalidMessage
        }
        if (updateState) _mutableValidationStateFlow.value = validationWithValidator
        return !validationWithValidator.isInvalid
    }

    /**
     * Clear validation information.
     */
    public fun clearValidation() {
        _mutableValidationStateFlow.value = Validation()
    }

    /**
     * Returns a control of given key.
     * @param key key identifier of the control
     * @return selected control
     */
    public fun getControl(key: KProperty1<K, *>): FormControl<*>? {
        return getControl(key.name)
    }

    /**
     * Returns a control of given dynamic key.
     * @param key key identifier of the control
     * @return selected control
     */
    public fun getControl(key: String): FormControl<*>? {
        return fields[key]
    }

    /**
     * Returns a value of the control of given key.
     * @param key key identifier of the control
     * @return value of the control
     */
    public operator fun <V> get(key: KProperty1<K, V>): V? {
        return get(key.name)
    }

    /**
     * Returns a value of the control of given dynamic key.
     * @param key key identifier of the control
     * @return value of the control
     */
    public operator fun <V> get(key: String): V? {
        return getControl(key)?.getValue()?.cast<V>()
    }

    /**
     * Returns the first control added to the form.
     * @return the first control
     */
    public fun getFirstFormControl(): FormControl<*>? {
        return this.fields.firstNotNullOfOrNull { it.value }
    }

    /**
     * Submit the html form.
     */
    public fun submit() {
        if (renderConfig.isDom) element.submit()
    }

    /**
     * Reset the html form.
     */
    public fun reset() {
        if (renderConfig.isDom) element.reset()
    }

    /**
     * Check validity of the html form.
     */
    public fun checkValidity(): Boolean {
        return if (renderConfig.isDom) element.checkValidity() else false
    }

    /**
     * Report validity of the html form.
     */
    public fun reportValidity(): Boolean {
        return if (renderConfig.isDom) element.reportValidity() else false
    }

    /**
     * Focuses the first form control on the form.
     */
    override fun focus() {
        getFirstFormControl()?.focus()
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::method,
            ::action,
            ::enctype,
            ::name,
            ::target,
            ::novalidate,
            ::autocomplete,
        )
    }

    public companion object {

        /**
         * Creates a [Form] component with a data class model.
         */
        public inline fun <reified K : Any> create(
            method: FormMethod? = null, action: String? = null, enctype: FormEnctype? = null,
            requiredMessage: String = REQUIRED_FIELD_MESSAGE,
            invalidMessage: String = INVALID_FIELD_MESSAGE,
            customSerializers: Map<KClass<*>, KSerializer<*>>? = null,
            className: String? = null, id: String? = null,
            renderConfig: RenderConfig = RenderConfig.Default,
        ): Form<K> {
            return Form(
                method,
                action,
                enctype,
                requiredMessage,
                invalidMessage,
                serializer(),
                customSerializers,
                className,
                id,
                renderConfig = renderConfig
            )
        }
    }

}

/**
 * Creates a [Form] component with a data class model, returning a reference.
 *
 * @param initialData the initial data model
 * @param method the method attribute of the generated HTML form element
 * @param action the action attribute of the generated HTML form element
 * @param enctype the enctype attribute of the generated HTML form element
 * @param requiredMessage the default error message for an empty required form field
 * @param invalidMessage the default error message for an invalid form field
 * @param customSerializers custom serializers for the data model
 * @param className the CSS class name
 * @param id the ID attribute of the form element
 * @param content the content of the component
 * @return the [Form] component
 */
@Composable
public inline fun <reified K : Any> IComponent.formRef(
    initialData: K? = null,
    method: FormMethod? = null, action: String? = null, enctype: FormEnctype? = null,
    requiredMessage: String = REQUIRED_FIELD_MESSAGE,
    invalidMessage: String = INVALID_FIELD_MESSAGE,
    customSerializers: Map<KClass<*>, KSerializer<*>>? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable Form<K>.() -> Unit = {}
): Form<K> {
    val component =
        remember {
            Form.create<K>(
                method,
                action,
                enctype,
                requiredMessage,
                invalidMessage,
                customSerializers,
                className,
                id,
                renderConfig = renderConfig
            )
        }
    DisposableEffect(component.componentId) {
        component.onInsert()
        if (initialData != null) {
            component.setDataFromCompose(initialData)
        }
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(initialData) { if (it != null) setDataFromCompose(it) }
        set(method) { updateProperty(Form<K>::method, it) }
        set(action) { updateProperty(Form<K>::action, it) }
        set(enctype) { updateProperty(Form<K>::enctype, it) }
        set(className) { updateProperty(Form<K>::className, it) }
        set(id) { updateProperty(Form<K>::id, it) }
    }, content)
    return component
}

/**
 * Creates a [Form] component with map model, returning a reference.
 *
 * @param initialData the initial data model
 * @param method the method attribute of the generated HTML form element
 * @param action the action attribute of the generated HTML form element
 * @param enctype the enctype attribute of the generated HTML form element
 * @param requiredMessage the default error message for an empty required form field
 * @param invalidMessage the default error message for an invalid form field
 * @param customSerializers custom serializers for the data model
 * @param className the CSS class name
 * @param id the ID attribute of the form element
 * @param content the content of the component
 * @return the [Form] component
 */
@Composable
public fun IComponent.formRef(
    initialData: Map<String, Any?>? = null,
    method: FormMethod? = null, action: String? = null, enctype: FormEnctype? = null,
    requiredMessage: String = REQUIRED_FIELD_MESSAGE,
    invalidMessage: String = INVALID_FIELD_MESSAGE,
    customSerializers: Map<KClass<*>, KSerializer<*>>? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable Form<Map<String, Any?>>.() -> Unit = {}
): Form<Map<String, Any?>> {
    val component =
        remember {
            Form<Map<String, Any?>>(
                method,
                action,
                enctype,
                requiredMessage,
                invalidMessage,
                null,
                customSerializers,
                className,
                id,
                renderConfig
            )
        }
    DisposableEffect(component.componentId) {
        component.onInsert()
        if (initialData != null) {
            component.setDataFromCompose(initialData)
        }
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(initialData) { if (it != null) setDataFromCompose(it) }
        set(method) { updateProperty(Form<Map<String, Any?>>::method, it) }
        set(action) { updateProperty(Form<Map<String, Any?>>::action, it) }
        set(enctype) { updateProperty(Form<Map<String, Any?>>::enctype, it) }
        set(className) { updateProperty(Form<Map<String, Any?>>::className, it) }
        set(id) { updateProperty(Form<Map<String, Any?>>::id, it) }
    }, content)
    return component
}

/**
 * Creates a [Form] component with a data class model.
 *
 * @param initialData the initial data model
 * @param method the method attribute of the generated HTML form element
 * @param action the action attribute of the generated HTML form element
 * @param enctype the enctype attribute of the generated HTML form element
 * @param requiredMessage the default error message for an empty required form field
 * @param invalidMessage the default error message for an invalid form field
 * @param customSerializers custom serializers for the data model
 * @param className the CSS class name
 * @param id the ID attribute of the form element
 * @param content the content of the component
 */
@Composable
public inline fun <reified K : Any> IComponent.form(
    initialData: K? = null,
    method: FormMethod? = null, action: String? = null, enctype: FormEnctype? = null,
    requiredMessage: String = REQUIRED_FIELD_MESSAGE,
    invalidMessage: String = INVALID_FIELD_MESSAGE,
    customSerializers: Map<KClass<*>, KSerializer<*>>? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable Form<K>.() -> Unit = {}
) {
    val component =
        remember {
            Form.create<K>(
                method,
                action,
                enctype,
                requiredMessage,
                invalidMessage,
                customSerializers,
                className,
                id,
                renderConfig = renderConfig
            )
        }
    DisposableEffect(component.componentId) {
        component.onInsert()
        if (initialData != null) {
            component.setDataFromCompose(initialData)
        }
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(initialData) { if (it != null) setDataFromCompose(it) }
        set(method) { updateProperty(Form<K>::method, it) }
        set(action) { updateProperty(Form<K>::action, it) }
        set(enctype) { updateProperty(Form<K>::enctype, it) }
        set(className) { updateProperty(Form<K>::className, it) }
        set(id) { updateProperty(Form<K>::id, it) }
    }, content)
}

/**
 * Creates a [Form] component with map model.
 *
 * @param initialData the initial data model
 * @param method the method attribute of the generated HTML form element
 * @param action the action attribute of the generated HTML form element
 * @param enctype the enctype attribute of the generated HTML form element
 * @param requiredMessage the default error message for an empty required form field
 * @param invalidMessage the default error message for an invalid form field
 * @param customSerializers custom serializers for the data model
 * @param className the CSS class name
 * @param id the ID attribute of the form element
 * @param content the content of the component
 */
@Composable
public fun IComponent.form(
    initialData: Map<String, Any?>? = null,
    method: FormMethod? = null, action: String? = null, enctype: FormEnctype? = null,
    requiredMessage: String = REQUIRED_FIELD_MESSAGE,
    invalidMessage: String = INVALID_FIELD_MESSAGE,
    customSerializers: Map<KClass<*>, KSerializer<*>>? = null,
    className: String? = null,
    id: String? = null,
    content: @Composable Form<Map<String, Any?>>.() -> Unit = {}
) {
    val component =
        remember {
            Form<Map<String, Any?>>(
                method,
                action,
                enctype,
                requiredMessage,
                invalidMessage,
                null,
                customSerializers,
                className,
                id,
                renderConfig
            )
        }
    DisposableEffect(component.componentId) {
        component.onInsert()
        if (initialData != null) {
            component.setDataFromCompose(initialData)
        }
        onDispose {
            component.onRemove()
        }
    }
    ComponentNode(component, {
        set(initialData) { if (it != null) setDataFromCompose(it) }
        set(method) { updateProperty(Form<Map<String, Any?>>::method, it) }
        set(action) { updateProperty(Form<Map<String, Any?>>::action, it) }
        set(enctype) { updateProperty(Form<Map<String, Any?>>::enctype, it) }
        set(className) { updateProperty(Form<Map<String, Any?>>::className, it) }
        set(id) { updateProperty(Form<Map<String, Any?>>::id, it) }
    }, content)
}
