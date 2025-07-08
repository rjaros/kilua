/*
 * Copyright (c) 2025 Robert Jaros
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

package dev.kilua.routing

import app.softwork.routingcompose.Parameters

/**
 * Represents the context of a routing operation, providing access to the current path,
 * remaining path and parameters.
 */
public interface RoutingContext {
    public val path: String?
    public val remainingPath: String?
    public val parameters: Parameters?
}

/**
 * Represents a routing context that holds a string value.
 */
public interface StringRoutingContext : RoutingContext {
    public val value: String
}

/**
 * Represents a routing context that holds a numeric value.
 */
public interface IntRoutingContext : RoutingContext {
    public val value: Int
}

/**
 * Default implementation of [RoutingContext].
 * Provides properties for path, remaining path and parameters.
 */
public open class RoutingContextImpl : RoutingContext {
    private var pathInitialized: Boolean = false
    override var path: String? = null
        set(value) {
            field = value
            pathInitialized = true
        }
        get() = if (pathInitialized) field else error("Path is not set. Use context values inside view or action blocks only.")

    private var remainingPathInitialized: Boolean = false
    override var remainingPath: String? = null
        set(value) {
            field = value
            remainingPathInitialized = true
        }
        get() = if (remainingPathInitialized) field else error("Remaining path is not set. Use context values inside view or action blocks only.")

    private var parametersInitialized: Boolean = false
    override var parameters: Parameters? = null
        set(value) {
            field = value
            parametersInitialized = true
        }
        get() = if (parametersInitialized) field else error("Parameters are not set. Use context values inside view or action blocks only.")
}

/**
 * Default implementation of [StringRoutingContext].
 * Holds a string value that can be accessed within the routing context.
 */
public class StringRoutingContextImpl : RoutingContextImpl(), StringRoutingContext {
    private var valueInitialized: Boolean = false
    override var value: String = ""
        set(value) {
            field = value
            valueInitialized = true
        }
        get() = if (valueInitialized) field else error("Value is not set. Use context values inside view or action blocks only.")
}

/**
 * Default implementation of [IntRoutingContext].
 * Holds a numeric value that can be accessed within the routing context.
 */
public class IntRoutingContextImpl : RoutingContextImpl(), IntRoutingContext {
    private var valueInitialized: Boolean = false
    override var value: Int = 0
        set(value) {
            field = value
            valueInitialized = true
        }
        get() = if (valueInitialized) field else error("Value is not set. Use context values inside view or action blocks only.")
}
