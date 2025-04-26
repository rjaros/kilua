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

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import net.jodah.expiringmap.ExpirationPolicy
import net.jodah.expiringmap.ExpiringMap
import org.primefaces.extensions.optimizerplugin.optimizer.CssCompressor
import org.slf4j.LoggerFactory
import java.io.StringWriter
import java.nio.file.Path
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createTempDirectory
import kotlin.io.path.deleteRecursively
import kotlin.io.path.readText
import kotlin.io.path.reader

public const val DEFAULT_SSR_CACHE_TIME: Int = 10

/**
 * Server-Side Rendering engine for Kilua.
 *
 * @param nodeExecutable a path to the Node.js executable. If not provided, the default `node` command will be used.
 * @param port a port for the SSR service. If not provided, the default port `7788` will be used.
 * @param externalSsrService an external SSR service URL. If provided, the local SSR service will not be started.
 * @param rpcUrlPrefix a prefix for the Kilua RPC fullstack services.
 * @param rootId an ID of the root element in the HTML template.
 * @param contextPath a context path for the application.
 * @param cacheTime a default time to cache SSR content in minutes (default is 10).
 */
public class SsrEngine(
    nodeExecutable: String? = null,
    port: Int? = null,
    externalSsrService: String? = null,
    rpcUrlPrefix: String? = null,
    rootId: String? = null,
    contextPath: String? = null,
    private val cacheTime: Int = DEFAULT_SSR_CACHE_TIME
) {

    private val logger = LoggerFactory.getLogger(SsrEngine::class.java)

    private var isSSR: Boolean = true
    private var workingDir: Path? = null
    private var nodeJsProcess: Process? = null
    private var isShuttingDown: Boolean = false
    private val lockingFlow = MutableStateFlow(false)

    private val ssrService: String = externalSsrService ?: "http://localhost:${port ?: 7788}"
    private val root: String = rootId ?: "root"

    private val httpClient = HttpClient(CIO)

    private var cssContent: String? = null
    private var cssProcessed: Boolean = false
    private var indexTemplate: String = ""

    private val cssAssetsNames = setOf(
        "zzz-kilua-assets/k-style.css",
        "zzz-kilua-assets/k-animation.css",
        "zzz-kilua-assets/k-bootstrap.css",
        "zzz-kilua-assets/k-jetpack.css",
        "zzz-kilua-assets/k-splitjs.css",
        "zzz-kilua-assets/k-tabulator.css",
        "zzz-kilua-assets/k-tempus-dominus.css",
        "zzz-kilua-assets/k-toastify.css",
        "zzz-kilua-assets/k-tom-select.css",
        "zzz-kilua-assets/k-trix.css"
    )

    private val cache: MutableMap<CacheKey, String> = ExpiringMap.builder()
        .expiration(cacheTime.toLong(), TimeUnit.MINUTES)
        .expirationPolicy(ExpirationPolicy.CREATED)
        .build()

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            isShuttingDown = true
            nodeJsProcess?.destroy()
            Thread.sleep(1000)
            @OptIn(ExperimentalPathApi::class)
            workingDir?.deleteRecursively()
        })
        if (externalSsrService == null) {
            try {
                val ssrZip = this.javaClass.getResourceAsStream("/ssr.zip")
                if (ssrZip != null) {
                    workingDir = createTempDirectory("ssr")
                    FileUtils.unzip(ssrZip, workingDir!!.toFile())
                    indexTemplate = workingDir!!.resolve("index.html").readText()
                    val processBuilderParams = mutableListOf(nodeExecutable ?: "node", "main.bundle.js")
                    if (port != null) processBuilderParams.addAll(listOf("--port", port.toString()))
                    if (rpcUrlPrefix != null) processBuilderParams.addAll(listOf("--rpc-url-prefix", rpcUrlPrefix))
                    if (contextPath != null) processBuilderParams.addAll(listOf("--context-path", contextPath))
                    thread {
                        while (!isShuttingDown) {
                            val processBuilder = ProcessBuilder(*processBuilderParams.toTypedArray())
                            processBuilder.redirectErrorStream(true)
                            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT)
                            processBuilder.directory(workingDir!!.toFile())
                            print("NodeJS SSR process is starting ...")
                            nodeJsProcess = processBuilder.start()
                            println(" done.")
                            Thread.sleep(1000)
                            lockingFlow.value = true
                            nodeJsProcess!!.waitFor()
                            lockingFlow.value = false
                            nodeJsProcess = null
                        }
                    }
                } else {
                    logger.warn("No SSR resources. Disabling SSR.")
                    isSSR = false
                }
            } catch (e: Exception) {
                logger.error("Failed to initialize SSR engine. Disabling SSR.", e)
                isSSR = false
            }
        } else {
            lockingFlow.value = true
        }
    }

    /**
     * Get CSS stylesheet content for SSR.
     */
    public suspend fun getCssContent(): String {
        if (!isSSR) throw IllegalStateException("SSR is not enabled.")
        return cssContent ?: run {
            if (!lockingFlow.value) lockingFlow.first { it }
            val response = httpClient.post(ssrService)
            cssContent = if (response.status == HttpStatusCode.OK) {
                val textResponse = response.bodyAsText()
                if (textResponse.isNotEmpty()) {
                    withContext(Dispatchers.IO) {
                        textResponse.split("\n").joinToString("\n") {
                            if (cssAssetsNames.contains(it) || it.startsWith("css/") || it.startsWith("modules/css/")) {
                                val cssCompressor = CssCompressor(workingDir!!.resolve(it).reader())
                                val writer = StringWriter()
                                cssCompressor.compress(writer, -1)
                                writer.toString()
                            } else workingDir!!.resolve(it).readText()
                        }
                    }
                } else {
                    ""
                }
            } else {
                throw Exception("Connection to the SSR service failed with status ${response.status}")
            }
            cssContent!!
        }
    }

    /**
     * Get root content for SSR.
     */
    public suspend fun getRootContent(uri: String, locale: String? = null): String {
        if (!isSSR) throw IllegalStateException("SSR is not enabled.")
        if (uri.count { it == '?' } > 1) {
            throw Exception("Invalid URI: $uri")
        }
        if (!lockingFlow.value) lockingFlow.first { it }
        val response = httpClient.get("$ssrService$uri") {
            if (locale != null) {
                header("x-kilua-locale", locale)
            }
        }
        return if (response.status == HttpStatusCode.OK) {
            response.bodyAsText()
        } else {
            throw Exception("Connection to the SSR service failed with status ${response.status}")
        }
    }

    /**
     * Initialize global CSS stylesheet for SSR.
     */
    private suspend fun processCss() {
        if (!cssProcessed) {
            val cssTemplate = getCssContent()
            if (cssTemplate.isNotEmpty()) {
                indexTemplate = indexTemplate.replace("</head>", "<style>\n$cssTemplate\n</style>\n</head>")
            }
            cssProcessed = true
        }
    }

    /**
     * Get SSR content for the given URI and locale.
     */
    public suspend fun getSsrContent(uri: String, locale: String? = null): String {
        return try {
            processCss()
            if (cacheTime <= 0) {
                getInternalSsrContent(uri, locale)
            } else {
                cache.getOrPut(CacheKey(uri, locale)) {
                    getInternalSsrContent(uri, locale)
                }
            }
        } catch (e: Exception) {
            logger.error("Connection to the SSR service failed", e)
            indexTemplate
        }
    }

    private suspend fun getInternalSsrContent(uri: String, locale: String?): String {
        val content = getRootContent(uri, locale)
        return indexTemplate.replace("<div id=\"$root\"></div>", "<div id=\"$root\">$content</div>")
    }

    /**
     * Restart the SSR NodeJs process.
     */
    public suspend fun restartSsrProcess() {
        if (isSSR) {
            if (!lockingFlow.value) lockingFlow.first { it }
            try {
                httpClient.delete(ssrService)
            } catch (_: Exception) {
                // ignore exceptions
            }
        }
    }
}
