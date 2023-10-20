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

import dev.kilua.state.MutableState
import dev.kilua.types.KFile
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.w3c.files.File

/**
 * Input controls validation status.
 */
public interface FormControl<T> : MutableState<T> {
    /**
     * Returns the value as a string.
     */
    public fun getValueAsString(): String?

    /**
     * Parse the string value.
     */
    public fun setValueFromString(text: String?)

    /**
     * Determines if the field is disabled.
     */
    public var disabled: Boolean?

    /**
     * The name attribute of the generated HTML element.
     */
    public var name: String?
}

/**
 * Base interface of a form component with a generic value.
 */
public interface GenericFormControl<T : Any> : FormControl<T?> {
    /**
     * Generic value.
     */
    public override var value: T?

    /**
     * Returns the value as a string.
     */
    public override fun getValueAsString(): String? = value?.toString()
}

/**
 * Base interface of a form control with a generic, non-nullable value.
 */
public interface GenericNonNullableFormControl<T : Any> : FormControl<T> {
    /**
     * Generic value.
     */
    override var value: T

    /**
     * Returns the value as a string.
     */
    public override fun getValueAsString(): String = value.toString()
}

/**
 * Base interface of a form control with a text value.
 */
public interface StringFormControl : GenericFormControl<String> {
    /**
     * Returns the value as a string.
     */
    override fun getValueAsString(): String? = value
}

/**
 * Base interface of a form control with a numeric value.
 */
public interface NumberFormControl : GenericFormControl<Number>

/**
 * Base interface of a form control with a boolean value.
 */
public interface BoolFormControl : GenericNonNullableFormControl<Boolean>

/**
 * Base interface of a form control with a nullable boolean value.
 */
public interface TriStateFormControl : GenericFormControl<Boolean>

/**
 * Base interface of a form control with a date and time value.
 */
public interface DateTimeFormControl : GenericFormControl<LocalDateTime>

/**
 * Base interface of a form control with a date value.
 */
public interface DateFormControl : GenericFormControl<LocalDate>

/**
 * Base interface of a form control with a time value.
 */
public interface TimeFormControl : GenericFormControl<LocalTime>

/**
 * Base interface of a form control with a list of files value.
 */
public interface KFilesFormControl : GenericFormControl<List<KFile>> {
    /**
     * Returns the value as a string.
     */
    override fun getValueAsString(): String? = value?.joinToString(",") { it.name }

    /**
     * Returns the native JavaScript File object.
     * @param kFile KFile object
     * @return File object
     */
    public fun getNativeFile(kFile: KFile): File?
}
