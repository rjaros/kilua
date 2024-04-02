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
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.slf4j.LoggerFactory
import java.nio.file.Path
import java.util.*
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.createTempDirectory
import kotlin.io.path.deleteRecursively
import kotlin.io.path.readText

/**
 * Server-Side Rendering engine for Kilua.
 *
 * @param nodeExecutable a path to the Node.js executable. If not provided, the default `node` command will be used.
 * @param port a port for the SSR service. If not provided, the default port `7788` will be used.
 * @param externalSsrService an external SSR service URL. If provided, the local SSR service will not be started.
 * @param rpcUrlPrefix a prefix for the Kilua RPC fullstack services.
 * @param rootId an ID of the root element in the HTML template.
 */
public class SsrEngine(
    nodeExecutable: String? = null,
    port: Int? = null,
    externalSsrService: String? = null,
    rpcUrlPrefix: String? = null,
    rootId: String? = null
) {

    private val logger = LoggerFactory.getLogger(SsrEngine::class.java)

    private val workingDir: Path = createTempDirectory("ssr")
    private val uniqueText: String = UUID.randomUUID().toString()
    private val httpClient = HttpClient(Apache)

    private val ssrService: String = externalSsrService ?: "http://localhost:${port ?: 7788}"
    private val root: String = rootId ?: "root"

    private var cssProcessed: Boolean = false
    private var indexTemplate: String = ""

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            @OptIn(ExperimentalPathApi::class)
            workingDir.deleteRecursively()
        })
        try {
            val ssrZip = this.javaClass.getResourceAsStream("/ssr.zip")
            if (ssrZip != null) {
                FileUtils.unzip(ssrZip, workingDir.toFile())
                indexTemplate = workingDir.resolve("index.html").readText()
                    .replace(
                        """<div id="$root"></div>""",
                        """<div id="$root">$uniqueText</div>"""
                    )
                if (externalSsrService == null) {
                    val processBuilderParams = mutableListOf(nodeExecutable ?: "node", "main.bundle.js")
                    if (port != null) processBuilderParams.addAll(listOf("--port", port.toString()))
                    if (rpcUrlPrefix != null) processBuilderParams.addAll(listOf("--rpc-url-prefix", rpcUrlPrefix))
                    val process = ProcessBuilder(*processBuilderParams.toTypedArray())
                    process.redirectErrorStream(true)
                    process.redirectOutput(ProcessBuilder.Redirect.INHERIT)
                    process.directory(workingDir.toFile())
                    process.start()
                }
            } else {
                logger.warn("Failed to find SSR resources")
            }
        } catch (e: Exception) {
            logger.error("Failed to initialize SSR engine", e)
        }
    }

    /**
     * Initialize global CSS stylesheet for SSR.
     */
    private suspend fun processCss() {
        val response = httpClient.post(ssrService)
        if (response.status == HttpStatusCode.OK) {
            val cssTemplate = response.bodyAsText().split("\n").joinToString("\n") {
                workingDir.resolve(it).readText()
            }
            indexTemplate = indexTemplate.replace("</head>", "<style>\n$cssTemplate\n</style>\n</head>")
        } else {
            logger.error("Failed to initialize CSS for SSR")
        }
    }

    /**
     * Get SSR content for the given URI.
     */
    public suspend fun getSsrContent(uri: String): String {
        return try {
            if (!cssProcessed) {
                processCss()
                cssProcessed = true
            }
            val response = httpClient.get("$ssrService$uri")
            if (response.status == HttpStatusCode.OK) {
                val content = response.bodyAsText()
                indexTemplate.replace(uniqueText, content)
            } else {
                logger.error("Connection to the SSR service failed with status ${response.status}")
                indexTemplate.replace(uniqueText, "")
            }
        } catch (e: Exception) {
            logger.error("Connection to the SSR service failed", e)
            indexTemplate.replace(uniqueText, "")
        }
    }
}
