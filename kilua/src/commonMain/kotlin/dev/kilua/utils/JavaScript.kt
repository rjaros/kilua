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

package dev.kilua.utils

/**
 * JavaScript Object class
 */
public expect class Object

/**
 * Return empty JS Object
 */
public expect fun obj(): Object

/**
 * Whether the DOM is available
 */
public expect val isDom: Boolean

/**
 * Helper annotation for JS/Wasm compatibility.
 */
public expect annotation class JsNonModule()

/**
 * Helper function for JS/Wasm compatibility.
 */
@Suppress("NOTHING_TO_INLINE")
public inline fun <T> useCssModule(cssModule: T) {
    // empty body
}

/**
 * Helper function for JS/Wasm compatibility.
 */
public expect fun size(array: Object): Int
