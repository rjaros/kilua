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

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Property

@Factory
public open class SsrEngineBean {

    @field:Property(name = "ssr.nodeExecutable")
    private var nodeExecutable: String? = null

    @field:Property(name = "ssr.port")
    private var port: Int? = null

    @field:Property(name = "ssr.externalSsrService")
    private var externalSsrService: String? = null

    @field:Property(name = "ssr.rpcUrlPrefix")
    private var rpcUrlPrefix: String? = null

    @field:Property(name = "ssr.rootId")
    private var rootId: String? = null

    @field:Property(name = "ssr.contextPath")
    private var contextPath: String? = null

    @field:Property(name = "ssr.cacheTime")
    private var cacheTime: Int? = null

    @Context
    public open fun ssrEngine(): SsrEngine {
        return SsrEngine(nodeExecutable, port, externalSsrService, rpcUrlPrefix, rootId, contextPath, cacheTime ?: DEFAULT_SSR_CACHE_TIME)
    }
}
