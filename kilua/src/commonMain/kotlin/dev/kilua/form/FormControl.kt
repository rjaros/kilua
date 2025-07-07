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
import dev.kilua.state.WithStateFlow
import dev.kilua.types.KFile
import dev.kilua.utils.cast
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.reflect.KProperty1

/**
 * Input controls validation status.
 */
public interface FormControl<T> : WithStateFlow<T> {
    /**
     * The current value of the form control.
     */
    public var value: T

    /**
     * Returns the value as a string.
     */
    public fun getValueAsString(): String?

    /**
     * Determines if the field is visible.
     */
    public val visible: Boolean

    /**
     * Determines if the field is required.
     */
    public val required: Boolean?

    /**
     * The custom validity of the HTML element.
     */
    public var customValidity: String?

    /**
     * Sets the value of the form control.
     */
    public fun setValue(value: Any?) {
        this.value = value.cast()
    }

    /**
     * Returns the value of the form control.
     */
    public fun getValue(): Any? {
        return this.value
    }

    /**
     * Focus the form control.
     */
    public fun focus()

    /**
     * Blur the form control.
     */
    public fun blur()

    /**
     * Binds a form control to the form with a dynamic key.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun bind(
        key: String,
        validator: ((FormControl<T>) -> Boolean)? = null,
    ) {
        Form.current.bind(this, key, validator)
    }

    /**
     * Binds a form control to the form with a dynamic key.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun bindWithValidationMessage(
        key: String,
        validator: ((FormControl<T>) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key, null, validator)
    }

}

/**
 * Base interface of a form component with a generic value.
 */
public interface GenericFormControl<T : Any> : FormControl<T?> {
    /**
     * Generic value.
     */
    public override var value: T?
}

/**
 * Base interface of a form control with a generic, non-nullable value.
 */
public interface GenericNonNullableFormControl<T : Any> : FormControl<T> {
    /**
     * Generic value.
     */
    override var value: T
}

/**
 * Base interface of a form control with a text value.
 */
public interface StringFormControl : GenericFormControl<String> {
    /**
     * Bind a string control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun <K: Any> bind(
        key: KProperty1<K, String?>,
        validator: ((StringFormControl) -> Boolean)? = null
    ) {
        Form.current.bind(this, key.name, validator)
    }

    /**
     * Bind a string control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <K: Any> bindWithValidationMessage(
        key: KProperty1<K, String?>,
        validator: ((StringFormControl) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key.name, null, validator)
    }

    /**
     * Bind a string control to the form bound to custom field type.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun <K: Any> bindCustom(
        key: KProperty1<K, Any?>,
        validator: ((StringFormControl) -> Boolean)? = null
    ) {
        Form.current.bind(this, key.name, validator)
    }

    /**
     * Bind a string control to the form bound to custom field type.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <K: Any> bindCustomWithValidationMessage(
        key: KProperty1<K, Any?>,
        validator: ((StringFormControl) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key.name, null, validator)
    }
}

/**
 * Base interface of a form control with a numeric value.
 */
public interface NumberFormControl : GenericFormControl<Number> {
    /**
     * Bind a numeric control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun <K: Any> bind(
        key: KProperty1<K, Number?>,
        validator: ((NumberFormControl) -> Boolean)? = null
    ) {
        Form.current.bind(this, key.name, validator)
    }

    /**
     * Bind a numeric control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <K: Any> bindWithValidationMessage(
        key: KProperty1<K, Number?>,
        validator: ((NumberFormControl) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key.name, null, validator)
    }
}

/**
 * Base interface of a form control with an integer value.
 */
public interface IntFormControl : GenericFormControl<Int> {

    /**
     * Bind an integer control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun <K: Any> bind(
        key: KProperty1<K, Int?>,
        validator: ((IntFormControl) -> Boolean)? = null
    ) {
        Form.current.bind(this, key.name, validator)
    }

    /**
     * Bind an integer control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <K: Any> bindWithValidationMessage(
        key: KProperty1<K, Int?>,
        validator: ((IntFormControl) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key.name, null, validator)
    }
}

/**
 * Base interface of a form control with a boolean value.
 */
public interface BoolFormControl : GenericNonNullableFormControl<Boolean> {
    override fun setValue(value: Any?) {
        this.value = value?.cast() ?: false
    }

    /**
     * Bind a boolean control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun <K: Any> bind(
        key: KProperty1<K, Boolean?>,
        validator: ((BoolFormControl) -> Boolean)? = null
    ) {
        Form.current.bind(this, key.name, validator)
    }

    /**
     * Bind a boolean control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <K: Any> bindWithValidationMessage(
        key: KProperty1<K, Boolean?>,
        validator: ((BoolFormControl) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key.name, null, validator)
    }
}

/**
 * Base interface of a form control with a nullable boolean value.
 */
public interface TriStateFormControl : GenericFormControl<Boolean> {
    /**
     * Bind a nullable boolean control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun <K: Any> bind(
        key: KProperty1<K, Boolean?>,
        validator: ((TriStateFormControl) -> Boolean)? = null
    ) {
        Form.current.bind(this, key.name, validator)
    }

    /**
     * Bind a nullable boolean control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <K: Any> bindWithValidationMessage(
        key: KProperty1<K, Boolean?>,
        validator: ((TriStateFormControl) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key.name, null, validator)
    }
}

/**
 * Base interface of a form control with a date and time value.
 */
public interface DateTimeFormControl : GenericFormControl<LocalDateTime> {

    /**
     * Bind a date and time control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun <K: Any> bind(
        key: KProperty1<K, LocalDateTime?>,
        validator: ((DateTimeFormControl) -> Boolean)? = null
    ) {
        Form.current.bind(this, key.name, validator)
    }

    /**
     * Bind a date and time control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <K: Any> bindWithValidationMessage(
        key: KProperty1<K, LocalDateTime?>,
        validator: ((DateTimeFormControl) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key.name, null, validator)
    }
}

/**
 * Base interface of a form control with a date value.
 */
public interface DateFormControl : GenericFormControl<LocalDate> {

    /**
     * Bind a date control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun <K: Any> bind(
        key: KProperty1<K, LocalDate?>,
        validator: ((DateFormControl) -> Boolean)? = null
    ) {
        Form.current.bind(this, key.name, validator)
    }

    /**
     * Bind a date control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <K: Any> bindWithValidationMessage(
        key: KProperty1<K, LocalDate?>,
        validator: ((DateFormControl) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key.name, null, validator)
    }
}

/**
 * Base interface of a form control with a time value.
 */
public interface TimeFormControl : GenericFormControl<LocalTime> {

    /**
     * Bind a time control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun <K: Any> bind(
        key: KProperty1<K, LocalTime?>,
        validator: ((TimeFormControl) -> Boolean)? = null
    ) {
        Form.current.bind(this, key.name, validator)
    }

    /**
     * Bind a time control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <K: Any> bindWithValidationMessage(
        key: KProperty1<K, LocalTime?>,
        validator: ((TimeFormControl) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key.name, null, validator)
    }
}

/**
 * Base interface of a form control with a list of files value.
 */
public interface KFilesFormControl : GenericFormControl<List<KFile>> {

    /**
     * Returns the list of selected files with content.
     */
    public suspend fun getFilesWithContent(): List<KFile>?

    /**
     * Bind a files control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function
     */
    @Composable
    public fun <K: Any> bind(
        key: KProperty1<K, List<KFile>?>,
        validator: ((KFilesFormControl) -> Boolean)? = null
    ) {
        Form.current.bind(this, key.name, validator)
    }

    /**
     * Bind a files control to the form.
     * @param key key identifier of the control
     * @param validator optional validation function which also returns a validation message
     */
    @Composable
    public fun <K: Any> bindWithValidationMessage(
        key: KProperty1<K, List<KFile>?>,
        validator: ((KFilesFormControl) -> Pair<Boolean, String?>)? = null,
    ) {
        Form.current.bind(this, key.name, null, validator)
    }
}
