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

package dev.kilua.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.register
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.tomlj.Toml

public abstract class KiluaPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        logger.debug("Applying Kilua plugin")

        val kiluaExtension = createKiluaExtension()

        val versions =
            Toml.parse(this@KiluaPlugin.javaClass.classLoader.getResourceAsStream("dev.kilua.versions.toml"))
        val kiluaVersions = versions.toMap().mapNotNull { (k, v) -> if (v is String) k to v else null }.toMap()
        with(KiluaPluginContext(project, kiluaExtension, kiluaVersions)) {
            plugins.withId("org.jetbrains.kotlin.multiplatform") {
                afterEvaluate {
                    configureProject()
                    configureNodeEcosystem()
                }
            }
        }
    }

    /**
     * Initialise the [KiluaExtension] on a [Project].
     */
    private fun Project.createKiluaExtension(): KiluaExtension {
        return extensions.create("kilua", KiluaExtension::class)
    }

    private data class KiluaPluginContext(
        private val project: Project,
        val kiluaExtension: KiluaExtension,
        val kiluaVersions: Map<String, String>
    ) : Project by project

    private fun KiluaPluginContext.configureProject() {
        logger.debug("Configuring Kotlin/MPP plugin")

        val webpackSsrExists = layout.projectDirectory.dir("webpack.config.ssr.d").asFile.exists()
        val jsMainExists = layout.projectDirectory.dir("src/jsMain").asFile.exists()
        val wasmJsMainExists = layout.projectDirectory.dir("src/wasmJsMain").asFile.exists()
        val webMainExists = jsMainExists || wasmJsMainExists

        if (webMainExists && webpackSsrExists && kiluaExtension.enableGradleTasks.get()) {

            val cssFiles = listOf(
                "zzz-kilua-assets/style.css",
                "bootstrap/dist/css/bootstrap.min.css",
                "@eonasdan/tempus-dominus/dist/css/tempus-dominus.min.css",
                "tabulator-tables/dist/css/tabulator.min.css",
                "tabulator-tables/dist/css/tabulator_bootstrap5.min.css",
                "tabulator-tables/dist/css/tabulator_bulma.min.css",
                "tabulator-tables/dist/css/tabulator_materialize.min.css",
                "tabulator-tables/dist/css/tabulator_midnight.min.css",
                "tabulator-tables/dist/css/tabulator_modern.min.css",
                "tabulator-tables/dist/css/tabulator_simple.min.css",
                "tabulator-tables/dist/css/tabulator_semanticui.min.css",
                "toastify-js/src/toastify.css",
                "tom-select/dist/css/tom-select.bootstrap5.min.css",
                "tom-select/dist/css/tom-select.default.min.css",
                "tom-select/dist/css/tom-select.min.css",
                "trix/dist/trix.css"
            ).map {
                rootProject.file("build/js/node_modules/$it")
            }

            if (jsMainExists) {
                val jsBrowserProductionExecutableDistributeResources =
                    tasks.getByName("jsBrowserProductionExecutableDistributeResources", Copy::class)
                tasks.register("jsBrowserProductionExecutableDistributeResourcesSSR", Copy::class) {
                    group = KILUA_TASK_GROUP
                    description = "Processes resources for js bundle for server-side rendering."
                    from(jsBrowserProductionExecutableDistributeResources.source)
                    into("build/dist/js.ssr/productionExecutable")
                }
                val kotlinWebpackJs = tasks.getByName("jsBrowserProductionWebpack", KotlinWebpack::class)
                tasks.register("jsBrowserProductionWebpackSSR", KotlinWebpack::class, kotlinWebpackJs.compilation, project.objects)
                tasks.getByName("jsBrowserProductionWebpackSSR", KotlinWebpack::class).apply {
                    dependsOn("jsBrowserProductionExecutableDistributeResourcesSSR", "jsProductionExecutableCompileSync")
                    group = KILUA_TASK_GROUP
                    description = "Builds webpack js bundle for server-side rendering."
                    mode = KotlinWebpackConfig.Mode.PRODUCTION
                    inputFilesDirectory.set(kotlinWebpackJs.inputFilesDirectory.get())
                    entryModuleName.set(kotlinWebpackJs.entryModuleName.get())
                    esModules.set(kotlinWebpackJs.esModules.get())
                    outputDirectory.set(file("build/dist/js.ssr/productionExecutable"))
                    mainOutputFileName.set("main.bundle.js")
                    this.webpackConfigApplier {
                        configDirectory = file("webpack.config.ssr.d")
                    }
                }
                tasks.create("jsArchiveSSR", Jar::class).apply {
                    dependsOn("jsBrowserProductionWebpackSSR")
                    group = KILUA_TASK_GROUP
                    description = "Packages webpack js bundle for server-side rendering."
                    archiveFileName.set("ssr.zip")
                    val distribution =
                        project.tasks.getByName(
                            "jsBrowserProductionWebpackSSR",
                            KotlinWebpack::class
                        ).outputDirectory
                    from(distribution) {
                        include("*.*")
                    }
                    val processedResources =
                        project.tasks.getByName("jsProcessResources", Copy::class).destinationDir
                    from(processedResources)
                    from(cssFiles)
                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    inputs.files(distribution, processedResources, cssFiles)
                    outputs.file(archiveFile)
                    manifest {
                        attributes(
                            mapOf(
                                "Implementation-Title" to rootProject.name,
                                "Implementation-Group" to rootProject.group,
                                "Implementation-Version" to rootProject.version,
                                "Timestamp" to System.currentTimeMillis()
                            )
                        )
                    }
                    eachFile {
                        if (this.name.endsWith(".css")) {
                            this.path = this.file.relativeTo(rootProject.file("build/js/node_modules")).toString()
                        }
                    }
                }
            }
            if (wasmJsMainExists) {
                val wasmJsBrowserProductionExecutableDistributeResources =
                    tasks.getByName("wasmJsBrowserProductionExecutableDistributeResources", Copy::class)
                tasks.register("wasmJsBrowserProductionExecutableDistributeResourcesSSR", Copy::class) {
                    group = KILUA_TASK_GROUP
                    description = "Processes resources for wasmJs bundle for server-side rendering."
                    from(wasmJsBrowserProductionExecutableDistributeResources.source)
                    into("build/dist/wasmJs.ssr/productionExecutable")
                }
                val kotlinWebpackWasmJs = tasks.getByName("wasmJsBrowserProductionWebpack", KotlinWebpack::class)
                tasks.register(
                    "wasmJsBrowserProductionWebpackSSR",
                    KotlinWebpack::class,
                    kotlinWebpackWasmJs.compilation,
                    project.objects
                )
                tasks.getByName("wasmJsBrowserProductionWebpackSSR", KotlinWebpack::class).apply {
                    dependsOn("wasmJsBrowserProductionExecutableDistributeResourcesSSR", "wasmJsProductionExecutableCompileSync")
                    group = KILUA_TASK_GROUP
                    description = "Builds webpack wasmJs bundle for server-side rendering."
                    mode = KotlinWebpackConfig.Mode.PRODUCTION
                    inputFilesDirectory.set(kotlinWebpackWasmJs.inputFilesDirectory.get())
                    entryModuleName.set(kotlinWebpackWasmJs.entryModuleName.get())
                    esModules.set(kotlinWebpackWasmJs.esModules.get())
                    outputDirectory.set(file("build/dist/wasmJs.ssr/productionExecutable"))
                    mainOutputFileName.set("main.bundle.js")
                    this.webpackConfigApplier {
                        configDirectory = file("webpack.config.ssr.d")
                    }
                }
                tasks.create("wasmJsArchiveSSR", Jar::class).apply {
                    dependsOn("wasmJsBrowserProductionWebpackSSR")
                    group = KILUA_TASK_GROUP
                    description = "Packages webpack wasmJs bundle for server-side rendering."
                    archiveFileName.set("ssr.zip")
                    val distribution =
                        project.tasks.getByName(
                            "wasmJsBrowserProductionWebpackSSR",
                            KotlinWebpack::class
                        ).outputDirectory
                    from(distribution) {
                        include("*.*")
                    }
                    val processedResources =
                        project.tasks.getByName("wasmJsProcessResources", Copy::class).destinationDir
                    from(processedResources)
                    from(cssFiles)
                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    inputs.files(distribution, processedResources, cssFiles)
                    outputs.file(archiveFile)
                    manifest {
                        attributes(
                            mapOf(
                                "Implementation-Title" to rootProject.name,
                                "Implementation-Group" to rootProject.group,
                                "Implementation-Version" to rootProject.version,
                                "Timestamp" to System.currentTimeMillis()
                            )
                        )
                    }
                    eachFile {
                        if (this.name.endsWith(".css")) {
                            this.path = this.file.relativeTo(rootProject.file("build/js/node_modules")).toString()
                        }
                    }
                }
            }
            plugins.withId("dev.kilua.rpc") {
                afterEvaluate {
                    tasks.findByName("jarWithJs")?.let {
                        tasks.getByName("jarWithJs", Jar::class) {
                            dependsOn("jsArchiveSSR")
                            from(project.tasks["jsArchiveSSR"].outputs.files)
                        }
                    }
                    tasks.findByName("jarWithWasmJs")?.let {
                        tasks.getByName("jarWithWasmJs", Jar::class) {
                            dependsOn("wasmJsArchiveSSR")
                            from(project.tasks["wasmJsArchiveSSR"].outputs.files)
                        }
                    }
                }
            }
        }
    }

    private fun KiluaPluginContext.configureNodeEcosystem() {
        logger.info("configuring Node")

        rootProject.extensions.configure<YarnRootExtension> {
            logger.info("configuring Yarn")
            if (kiluaExtension.enableResolutions.get() && kiluaVersions.isNotEmpty()) {
                resolution("aaa-kilua-assets", kiluaVersions["npm.kilua-assets"]!!)
                resolution("zzz-kilua-assets", kiluaVersions["npm.kilua-assets"]!!)
                resolution("css-loader", kiluaVersions["css-loader"]!!)
                resolution("style-loader", kiluaVersions["style-loader"]!!)
                resolution("imports-loader", kiluaVersions["imports-loader"]!!)
                resolution("split.js", kiluaVersions["splitjs"]!!)
                resolution("html-differ", kiluaVersions["html-differ"]!!)
                resolution("@popperjs/core", kiluaVersions["popperjs-core"]!!)
                resolution("bootstrap", kiluaVersions["bootstrap"]!!)
                resolution("bootstrap-icons", kiluaVersions["bootstrap-icons"]!!)
                resolution("@fortawesome/fontawesome-free", kiluaVersions["fontawesome"]!!)
                resolution("trix", kiluaVersions["trix"]!!)
                resolution("@eonasdan/tempus-dominus", kiluaVersions["tempus-dominus"]!!)
                resolution("tom-select", kiluaVersions["tom-select"]!!)
                resolution("imask", kiluaVersions["imask"]!!)
                resolution("tabulator-tables", kiluaVersions["tabulator"]!!)
                resolution("rsup-progress", kiluaVersions["rsup-progress"]!!)
            }
        }
    }

    public companion object {

        public const val KILUA_TASK_GROUP: String = "Kilua"
        public const val PACKAGE_TASK_GROUP: String = "package"

    }

}
