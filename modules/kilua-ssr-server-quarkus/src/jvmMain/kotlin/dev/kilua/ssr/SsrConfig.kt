/*
 * Copyright (c) 2026 Robert Jaros
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

import jakarta.enterprise.context.Dependent
import jakarta.enterprise.inject.Produces
import jakarta.inject.Inject
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.util.Optional

/**
 * Quarkus CDI producer of the [SsrEngine] instance, based on `ssr.*` configuration properties.
 */
@Dependent
public open class SsrConfig {

    @Inject
    @ConfigProperty(name = "ssr.nodeExecutable")
    internal lateinit var nodeExecutable: Optional<String>

    @Inject
    @ConfigProperty(name = "ssr.port")
    internal lateinit var port: Optional<Int>

    @Inject
    @ConfigProperty(name = "ssr.externalSsrService")
    internal lateinit var externalSsrService: Optional<String>

    @Inject
    @ConfigProperty(name = "ssr.rpcUrlPrefix")
    internal lateinit var rpcUrlPrefix: Optional<String>

    @Inject
    @ConfigProperty(name = "ssr.rootId")
    internal lateinit var rootId: Optional<String>

    @Inject
    @ConfigProperty(name = "ssr.contextPath")
    internal lateinit var contextPath: Optional<String>

    @Inject
    @ConfigProperty(name = "ssr.cacheTime")
    internal lateinit var cacheTime: Optional<Int>

    /**
     * Produces the [SsrEngine] instance.
     */
    @Produces
    public fun ssrEngine(): SsrEngine {
        return SsrEngine(
            nodeExecutable.orElse(null),
            port.orElse(null),
            externalSsrService.orElse(null),
            rpcUrlPrefix.orElse(null),
            rootId.orElse(null),
            contextPath.orElse(null),
            cacheTime.orElse(DEFAULT_SSR_CACHE_TIME)!!
        )
    }
}
