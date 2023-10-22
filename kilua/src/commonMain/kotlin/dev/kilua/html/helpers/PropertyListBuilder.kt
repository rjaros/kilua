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

package dev.kilua.html.helpers

import dev.kilua.utils.nativeListOf
import kotlin.reflect.KProperty

/**
 * Property list builder interface.
 */
public interface PropertyListBuilder {
    public fun addByName(vararg property: String)
    public fun add(vararg property: KProperty<*>)
    public fun addAllByName(properties: List<String>) {
        properties.forEach { addByName(it) }
    }

    public fun addAll(properties: List<KProperty<*>>) {
        properties.forEach { add(it) }
    }
}

/**
 * Property list builder implementation.
 */
internal class PropertyListBuilderImpl : PropertyListBuilder {

    val properties = nativeListOf<String>()

    override fun addByName(vararg property: String) {
        property.forEach {
            properties.add(it)
        }
    }

    override fun add(vararg property: KProperty<*>) {
        property.forEach {
            properties.add(it.name)
        }
    }
}

/**
 * Build a list of properties.
 */
public fun buildPropertyList(delegate: (builder: PropertyListBuilder) -> Unit): List<String> =
    PropertyListBuilderImpl().also { delegate(it) }.properties
