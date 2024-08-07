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

import kotlin.reflect.KProperty1

/**
 * Result of the single form field validation.
 */
public data class FieldValidation(
    val isEmptyWhenRequired: Boolean = false,
    val isInvalid: Boolean = false,
    val validMessage: String? = null,
    val invalidMessage: String? = null,
)

/**
 * Result of the form validation.
 */
public data class Validation<K : Any>(
    val wasValidated: Boolean = false,
    val isInvalid: Boolean = false,
    val validMessage: String? = null,
    val invalidMessage: String? = null,
    val fieldsValidations: Map<String, FieldValidation> = emptyMap()
) {
    public operator fun get(key: String): FieldValidation? = fieldsValidations[key]
    public operator fun get(key: KProperty1<K, *>): FieldValidation? = fieldsValidations[key.name]

    /**
     * Create a copy of this validation result with the new value of a given field validation.
     */
    public fun setFieldValidation(key: String, fieldValidation: FieldValidation): Validation<K> =
        copy(fieldsValidations = fieldsValidations + (key to fieldValidation))

    /**
     * Create a copy of this validation result with the new value of a given field validation.
     */
    public fun setFieldValidation(key: KProperty1<K, *>, fieldValidation: FieldValidation): Validation<K> =
        setFieldValidation(key.name, fieldValidation)
}
