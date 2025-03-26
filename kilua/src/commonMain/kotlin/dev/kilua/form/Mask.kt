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

package dev.kilua.form

import web.html.HTMLElement

/**
 * Generic input mask controller.
 */
public interface Mask {
    /**
     * Get current, unmasked value.
     */
    public fun getValue(): String?

    /**
     * Install callback when the input text value is changed.
     */
    public fun onChange(callback: (String?) -> Unit)

    /**
     * Refresh the mask controller after external change.
     */
    public fun refresh()

    /**
     * Destroy and cleanup.
     */
    public fun destroy()
}

/**
 * Generic input mask options.
 */
public interface MaskOptions {
    public fun maskNumericValue(value: String): String
}

/**
 * Input mask controller factory.
 */
public interface MaskFactory {
    /**
     * Create input mask controller.
     */
    public fun createMask(element: HTMLElement, options: MaskOptions): Mask
}

/**
 * Text input mask controller manager.
 */
public object MaskManager {
    /**
     * Current input mask controller factory
     */
    public var factory: MaskFactory? = null
}
