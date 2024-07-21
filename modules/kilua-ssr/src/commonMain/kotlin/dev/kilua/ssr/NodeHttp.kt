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

package dev.kilua.ssr

import dev.kilua.JsModule
import dev.kilua.dom.JsAny

/**
 * Node.js HTTP server.
 */
public external class Server : JsAny {
    public fun listen(port: Int)
}

/**
 * Node.js HTTP server incoming message.
 */
public external class IncomingMessage : JsAny {
    public val method: String
    public val url: String
    public val headers: JsAny
}

/**
 * Node.js HTTP server server response.
 */
public external class ServerResponse : JsAny {
    public var statusCode: Int
    public fun setHeader(name: String, value: String)
    public fun end(data: String)
}

/**
 * Node.js HTTP class.
 */
public open external class Http : JsAny {
    public fun createServer(callback: (req: JsAny, res: JsAny) -> Unit): Server
}

/**
 * Node.js HTTP module.
 */
@JsModule("http")
public external object http : Http
