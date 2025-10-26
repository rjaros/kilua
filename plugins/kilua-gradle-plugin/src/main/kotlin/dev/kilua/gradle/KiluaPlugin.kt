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

import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import com.charleskorn.kaml.YamlNamingStrategy
import dev.kilua.gradle.tasks.KiluaExportHtmlTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.Sync
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.NpmVersions
import org.jetbrains.kotlin.gradle.targets.js.npm.BaseNpmExtension
import org.jetbrains.kotlin.gradle.targets.js.npm.NpmExtension
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.wasm.npm.WasmNpmExtension
import org.jetbrains.kotlin.gradle.targets.wasm.yarn.WasmYarnRootExtension
import org.jetbrains.kotlin.gradle.targets.web.yarn.BaseYarnRootExtension
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
        return extensions.create<KiluaExtension>("kilua")
    }

    private data class KiluaPluginContext(
        val project: Project,
        val kiluaExtension: KiluaExtension,
        val kiluaVersions: Map<String, String>
    )

    @Suppress("LongMethod", "ComplexMethod")
    private fun KiluaPluginContext.configureProject() {
        project.logger.debug("Configuring Kotlin/MPP plugin")

        val kotlinMppExtension = project.extensions.getByType<KotlinMultiplatformExtension>()

        kotlinMppExtension.targets.configureEach {
            val targetName = name
            compilations.configureEach {
                compileTaskProvider.configure {
                    compilerOptions {
                        optIn.add("kotlin.time.ExperimentalTime")
                        if (targetName == "metadata" || targetName == "js" || targetName == "wasmJs") {
                            optIn.add("kotlin.js.ExperimentalWasmJsInterop")
                        }
                    }
                }
            }
        }

        val webpackSsrExists = project.layout.projectDirectory.dir("webpack.config.ssr.d").asFile.exists()

        val srcDir = project.layout.projectDirectory.dir("src").asFile.absolutePath + "/**/*.kt"
        project.tasks.withType<Copy>().matching {
            it.name == "jsProcessResources" || it.name == "wasmJsProcessResources"
        }.configureEach {
            eachFile {
                if (this.name == "tailwind.config.js") {
                    this.filter {
                        it.replace(
                            "SOURCES",
                            srcDir
                        )
                    }
                }
            }
        }

        project.tasks.withType<Sync>().matching {
            it.name == "jsBrowserDistribution" || it.name == "wasmJsBrowserDistribution" || it.name == "composeCompatibilityBrowserDistribution"
        }.configureEach {
            exclude("/tailwind/**", "/modules/**")
        }

        if (webpackSsrExists && kiluaExtension.enableGradleTasks.get()) {
            val kiluaYmlFile = project.layout.projectDirectory.file(kiluaExtension.kiluaYml).get().asFile
            val kiluaConfiguration = if (kiluaYmlFile.exists()) {
                val yamlConfiguration = YamlConfiguration(yamlNamingStrategy = YamlNamingStrategy.SnakeCase)
                Yaml(configuration = yamlConfiguration).decodeFromString(
                    KiluaConfiguration.serializer(),
                    kiluaYmlFile.readText()
                )
            } else null
            val includedCssNames = kiluaConfiguration?.ssr?.includedCssFiles ?: emptyList()
            val cssNames = listOf(
                "zzz-kilua-assets/k-style.css",
                "zzz-kilua-assets/k-animation.css",
                "zzz-kilua-assets/k-bootstrap.css",
                "zzz-kilua-assets/k-jetpack.css",
                "zzz-kilua-assets/k-splitjs.css",
                "zzz-kilua-assets/k-tabulator.css",
                "zzz-kilua-assets/k-tempus-dominus.css",
                "zzz-kilua-assets/k-toastify.css",
                "zzz-kilua-assets/k-tom-select.css",
                "zzz-kilua-assets/k-trix.css",
                "bootstrap/dist/css/bootstrap.min.css",
                "@eonasdan/tempus-dominus/dist/css/tempus-dominus.min.css",
                "leaflet/dist/leaflet.css",
                "tabulator-tables/dist/css/tabulator.min.css",
                "tabulator-tables/dist/css/tabulator_bootstrap5.min.css",
                "tabulator-tables/dist/css/tabulator_bulma.min.css",
                "tabulator-tables/dist/css/tabulator_materialize.min.css",
                "tabulator-tables/dist/css/tabulator_midnight.min.css",
                "tabulator-tables/dist/css/tabulator_modern.min.css",
                "tabulator-tables/dist/css/tabulator_simple.min.css",
                "tabulator-tables/dist/css/tabulator_semanticui.min.css",
                "tabulator-tables/dist/css/tabulator_site_dark.min.css",
                "toastify-js/src/toastify.css",
                "tom-select/dist/css/tom-select.bootstrap5.min.css",
                "tom-select/dist/css/tom-select.default.min.css",
                "tom-select/dist/css/tom-select.min.css",
                "trix/dist/trix.css"
            ) + includedCssNames
            val cssFilesJs = cssNames.map {
                project.rootProject.file("build/js/node_modules/$it")
            }
            val cssFilesWasmJs = cssNames.map {
                project.rootProject.file("build/wasm/node_modules/$it")
            }
            val manifestMap = mapOf(
                "Implementation-Title" to project.rootProject.name,
                "Implementation-Group" to project.rootProject.group,
                "Implementation-Version" to project.rootProject.version,
                "Timestamp" to System.currentTimeMillis()
            )
            if (project.tasks.findByName("jsBrowserProductionWebpack") != null) {
                val kotlinWebpackJs = project.tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
                val jsProcessResources = project.tasks.getByName<Copy>("jsProcessResources")
                val projectObjects = project.objects
                val webpackConfigSsrDir = project.file("webpack.config.ssr.d")
                val productionExecutableDir = project.file("build/kotlin-webpack/js.ssr/productionExecutable")
                val productionExecutableDirSSR = project.file("build/dist/js.ssr/productionExecutable")
                val nodeModulesDir = project.rootProject.file("build/js/node_modules")
                project.tasks.register<KotlinWebpack>(
                    "jsBrowserProductionWebpackSSR",
                    kotlinWebpackJs.compilation,
                    projectObjects
                )
                project.tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpackSSR").apply {
                    dependsOn("jsProductionExecutableCompileSync")
                    group = KILUA_TASK_GROUP
                    description = "Builds webpack js bundle for server-side rendering."
                    try {
                        // Workaround to initialize internal properties of the KotlinWebpack class
                        val method =
                            javaClass.getDeclaredMethod("setVersions\$kotlin_gradle_plugin_common", Any::class.java)
                        val prop = projectObjects.property<NpmVersions>()
                        prop.set(NpmVersions())
                        method.invoke(this, prop)
                        val method2 =
                            javaClass.getDeclaredMethod("setGetIsWasm\$kotlin_gradle_plugin_common", Any::class.java)
                        val prop2 = projectObjects.property<Boolean>()
                        prop2.set(false)
                        method2.invoke(this, prop2)
                        val method3 =
                            kotlinWebpackJs.javaClass.getDeclaredMethod("getNpmToolingEnvDir\$kotlin_gradle_plugin_common")
                        val prop3 = method3.invoke(kotlinWebpackJs) as DirectoryProperty
                        val method4 = javaClass.getDeclaredMethod(
                            "setNpmToolingEnvDir\$kotlin_gradle_plugin_common",
                            Any::class.java
                        )
                        method4.invoke(this, prop3.get())
                    } catch (e: Exception) {
                        logger.error("Failed to initialize KotlinWebpack properties", e)
                    }
                    mode = KotlinWebpackConfig.Mode.PRODUCTION
                    inputFilesDirectory.set(kotlinWebpackJs.inputFilesDirectory.get())
                    entryModuleName.set(kotlinWebpackJs.entryModuleName.get())
                    esModules.set(kotlinWebpackJs.esModules.get())
                    outputDirectory.set(productionExecutableDir)
                    mainOutputFileName.set("main.bundle.js")
                    this.webpackConfigApplier {
                        configDirectory = webpackConfigSsrDir
                    }
                }
                project.tasks.register<Copy>("jsBrowserDistributionSSR") {
                    dependsOn("jsBrowserProductionWebpackSSR")
                    group = KILUA_TASK_GROUP
                    description = "Assembles js distribution files for server-side rendering."
                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    from(jsProcessResources)
                    exclude("/tailwind/**")
                    from("build/kotlin-webpack/js.ssr/productionExecutable")
                    into(productionExecutableDirSSR)
                }
                project.tasks.register<Jar>("jsArchiveSSR") {
                    dependsOn("jsBrowserDistributionSSR")
                    group = KILUA_TASK_GROUP
                    description = "Packages webpack js bundle for server-side rendering."
                    archiveFileName.set("ssr.zip")
                    from(productionExecutableDirSSR)
                    from(cssFilesJs)
                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    inputs.files(productionExecutableDirSSR, cssFilesJs, kiluaYmlFile)
                    outputs.file(archiveFile)
                    manifest {
                        attributes(
                            manifestMap
                        )
                    }
                    eachFile {
                        if (cssNames.any { this.file.toString().replace('\\', '/').endsWith(it) }) {
                            this.path = this.file.relativeTo(nodeModulesDir).toString()
                        }
                    }
                }
            }
            if (project.tasks.findByName("wasmJsBrowserProductionWebpack") != null) {
                val kotlinWebpackWasmJs = project.tasks.getByName<KotlinWebpack>("wasmJsBrowserProductionWebpack")
                val wasmJsProcessResources = project.tasks.getByName<Copy>("wasmJsProcessResources")
                val projectObjects = project.objects
                val webpackConfigSsrDir = project.file("webpack.config.ssr.d")
                val productionExecutableDir = project.file("build/kotlin-webpack/wasmJs.ssr/productionExecutable")
                val productionExecutableDirSSR = project.file("build/dist/wasmJs.ssr/productionExecutable")
                val nodeModulesDir = project.rootProject.file("build/wasm/node_modules")
                project.tasks.register<KotlinWebpack>(
                    "wasmJsBrowserProductionWebpackSSR",
                    kotlinWebpackWasmJs.compilation,
                    projectObjects
                )
                project.tasks.getByName<KotlinWebpack>("wasmJsBrowserProductionWebpackSSR").apply {
                    dependsOn("wasmJsBrowserProductionWebpack")
                    group = KILUA_TASK_GROUP
                    description = "Builds webpack wasmJs bundle for server-side rendering."
                    try {
                        // Workaround to initialize internal properties of the KotlinWebpack class
                        val method =
                            javaClass.getDeclaredMethod("setVersions\$kotlin_gradle_plugin_common", Any::class.java)
                        val prop = projectObjects.property<NpmVersions>()
                        prop.set(NpmVersions())
                        method.invoke(this, prop)
                        val method2 =
                            javaClass.getDeclaredMethod("setGetIsWasm\$kotlin_gradle_plugin_common", Any::class.java)
                        val prop2 = projectObjects.property<Boolean>()
                        prop2.set(true)
                        method2.invoke(this, prop2)
                        val method3 =
                            kotlinWebpackWasmJs.javaClass.getDeclaredMethod("getNpmToolingEnvDir\$kotlin_gradle_plugin_common")
                        val prop3 = method3.invoke(kotlinWebpackWasmJs) as DirectoryProperty
                        val method4 = javaClass.getDeclaredMethod(
                            "setNpmToolingEnvDir\$kotlin_gradle_plugin_common",
                            Any::class.java
                        )
                        method4.invoke(this, prop3.get())
                    } catch (e: Exception) {
                        logger.error("Failed to initialize KotlinWebpack properties", e)
                    }
                    mode = KotlinWebpackConfig.Mode.PRODUCTION
                    inputFilesDirectory.set(kotlinWebpackWasmJs.inputFilesDirectory.get())
                    entryModuleName.set(kotlinWebpackWasmJs.entryModuleName.get())
                    esModules.set(kotlinWebpackWasmJs.esModules.get())
                    outputDirectory.set(productionExecutableDir)
                    mainOutputFileName.set("main.bundle.js")
                    this.webpackConfigApplier {
                        configDirectory = webpackConfigSsrDir
                    }
                }
                project.tasks.register<Copy>("wasmJsBrowserDistributionSSR") {
                    dependsOn("wasmJsBrowserProductionWebpackSSR")
                    group = KILUA_TASK_GROUP
                    description = "Assembles wasmJs distribution files for server-side rendering."
                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    from(wasmJsProcessResources)
                    exclude("/tailwind/**")
                    from("build/kotlin-webpack/wasmJs.ssr/productionExecutable")
                    // A wasm file with original name is still loaded from the main js file
                    from("build/compileSync/wasmJs/main/productionExecutable/optimized") {
                        include { it.name.endsWith(".wasm") }
                    }
                    into(productionExecutableDirSSR)
                }
                project.tasks.register<Jar>("wasmJsArchiveSSR") {
                    dependsOn("wasmJsBrowserDistributionSSR")
                    group = KILUA_TASK_GROUP
                    description = "Packages webpack wasmJs bundle for server-side rendering."
                    archiveFileName.set("ssr.zip")
                    from(productionExecutableDirSSR)
                    from(cssFilesWasmJs)
                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    inputs.files(productionExecutableDirSSR, cssFilesWasmJs, kiluaYmlFile)
                    outputs.file(archiveFile)
                    manifest {
                        attributes(
                            manifestMap
                        )
                    }
                    filteringCharset = "UTF-8"
                    eachFile {
                        if (cssNames.any { this.file.toString().replace('\\', '/').endsWith(it) }) {
                            this.path = this.file.relativeTo(nodeModulesDir).toString()
                        } else if (this.name.equals("main.bundle.js")) {
                            this.filter {
                                it.replace(
                                    Regex("""([a-zA-Z_]+)=([a-zA-Z_]+)\.default\.createRequire\([^\)]+\)(.*)(\{\})\.resolve\(([a-zA-Z_]+)\),(.*)\.readFileSync\([a-zA-Z_]+\.fileURLToPath\(([a-zA-Z_]+)\)\)"""),
                                    """$1=$2.default.createRequire(__filename)$3$1("path").resolve($5),$6.readFileSync($7)"""
                                )
                            }
                        }
                    }
                }
            }
            project.plugins.withId("dev.kilua.rpc") {
                project.afterEvaluate {
                    project.afterEvaluate {
                        tasks.findByName("jarWithJs")?.let {
                            tasks.getByName<Jar>("jarWithJs") {
                                if (tasks.findByName("wasmJsArchiveSSR") != null) {
                                    dependsOn("wasmJsArchiveSSR")
                                    from(project.tasks["wasmJsArchiveSSR"].outputs.files)
                                } else {
                                    dependsOn("jsArchiveSSR")
                                    from(project.tasks["jsArchiveSSR"].outputs.files)
                                }
                                registerKiluaExportHtmlTask("exportHtmlWithJs", archiveFile, kiluaConfiguration) {
                                    dependsOn(it)
                                }
                                registerKiluaExportTask("exportWithJs", "exportHtmlWithJs", "js", false) {
                                    dependsOn("exportHtmlWithJs")
                                }
                            }
                        }
                        tasks.findByName("jarWithWasmJs")?.let {
                            tasks.getByName<Jar>("jarWithWasmJs") {
                                dependsOn("wasmJsArchiveSSR")
                                from(project.tasks["wasmJsArchiveSSR"].outputs.files)
                                registerKiluaExportHtmlTask("exportHtmlWithWasmJs", archiveFile, kiluaConfiguration) {
                                    dependsOn(it)
                                }
                                registerKiluaExportTask(
                                    "exportWithWasmJs",
                                    "exportHtmlWithWasmJs",
                                    "wasmJs",
                                    tasks.findByName("jarWithJs") != null
                                ) {
                                    dependsOn("exportHtmlWithWasmJs")
                                }
                            }
                        }
                    }
                }
            }
        }
        val dontDisableSkikoProjectProperty = project.findProperty("dev.kilua.plugin.disableSkiko") == "false"
        if (!dontDisableSkikoProjectProperty && kiluaExtension.disableSkiko.get()) {
            project.tasks.withType<org.jetbrains.compose.web.tasks.UnpackSkikoWasmRuntimeTask> {
                enabled = false
            }
        }
    }

    private fun KiluaPluginContext.registerKiluaExportHtmlTask(
        name: String,
        applicationArchive: Provider<RegularFile>,
        kiluaConfiguration: KiluaConfiguration?,
        configuration: KiluaExportHtmlTask.() -> Unit = {}
    ) {
        project.logger.debug("registering KiluaExportHtmlTask")
        project.tasks.register<KiluaExportHtmlTask>(name) {
            kiluaConfiguration?.export?.language?.let { exportLanguage.set(it) }
            kiluaConfiguration?.export?.serverParameters?.let { exportServerParameters.set(it) }
            kiluaConfiguration?.export?.pages?.let { exportPages.set(it) }
            applicationJar.set(applicationArchive)
            exportDirectory.set(project.layout.buildDirectory.dir("exportedHtml"))
            configuration()
        }
    }

    private fun KiluaPluginContext.registerKiluaExportTask(
        name: String,
        exportHtmlTaskName: String,
        prefix: String,
        isCompat: Boolean,
        configuration: Copy.() -> Unit = {}
    ) {
        project.logger.debug("registering KiluaExportTask")
        project.tasks.register<Copy>(name) {
            group = KILUA_TASK_GROUP
            description = "Export the SSR application as static files"
            destinationDir = project.layout.buildDirectory.dir("site").get().asFile
            val exported = project.tasks.getByName(exportHtmlTaskName).outputs
            val distributionTask = if (isCompat && prefix == "wasmJs") {
                "composeCompatibilityBrowserDistribution"
            } else "${prefix}BrowserDistribution"
            val distribution = project.tasks.getByName(distributionTask).outputs
            from(exported, distribution)
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            configuration()
        }
    }

    @Suppress("LongMethod")
    private fun KiluaPluginContext.configureNodeEcosystem() {
        project.logger.debug("configuring Node")

        fun BaseNpmExtension.configureOverrides() {
            if (kiluaExtension.enableResolutions.get() && kiluaVersions.isNotEmpty()) {
                override("aaa-kilua-assets", kiluaVersions["npm.kilua-assets"]!!)
                override("zzz-kilua-assets", kiluaVersions["npm.kilua-assets"]!!)
                override("split.js", kiluaVersions["splitjs"]!!)
                override("html-differ", kiluaVersions["html-differ"]!!)
                override("@popperjs/core", kiluaVersions["popperjs-core"]!!)
                override("bootstrap", kiluaVersions["bootstrap"]!!)
                override("bootstrap-icons", kiluaVersions["bootstrap-icons"]!!)
                override("@fortawesome/fontawesome-free", kiluaVersions["fontawesome"]!!)
                override("trix", kiluaVersions["trix"]!!)
                override("@eonasdan/tempus-dominus", kiluaVersions["tempus-dominus"]!!)
                override("tom-select", kiluaVersions["tom-select"]!!)
                override("imask", kiluaVersions["imask"]!!)
                override("tabulator-tables", kiluaVersions["tabulator"]!!)
                override("rsup-progress", kiluaVersions["rsup-progress"]!!)
                override("lz-string", kiluaVersions["lz-string"]!!)
                override("marked", kiluaVersions["marked"]!!)
                override("sanitize-html", kiluaVersions["sanitize-html"]!!)
                override("postcss", kiluaVersions["postcss"]!!)
                override("postcss-loader", kiluaVersions["postcss-loader"]!!)
                override("tailwindcss", kiluaVersions["tailwindcss"]!!)
                override("@tailwindcss/postcss", kiluaVersions["tailwindcss"]!!)
                override("cssnano", kiluaVersions["cssnano"]!!)
                override("mini-css-extract-plugin", kiluaVersions["mini-css-extract-plugin"]!!)
                override("motion", kiluaVersions["motion"]!!)
                override("leaflet", kiluaVersions["leaflet"]!!)
            }
        }

        project.rootProject.extensions.findByType<NpmExtension>()?.apply {
            configureOverrides()
        }

        project.rootProject.extensions.findByType<WasmNpmExtension>()?.apply {
            configureOverrides()
        }

        fun BaseYarnRootExtension.configureResolutions() {
            if (kiluaExtension.enableResolutions.get() && kiluaVersions.isNotEmpty()) {
                resolution("aaa-kilua-assets", kiluaVersions["npm.kilua-assets"]!!)
                resolution("zzz-kilua-assets", kiluaVersions["npm.kilua-assets"]!!)
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
                resolution("lz-string", kiluaVersions["lz-string"]!!)
                resolution("marked", kiluaVersions["marked"]!!)
                resolution("sanitize-html", kiluaVersions["sanitize-html"]!!)
                resolution("postcss", kiluaVersions["postcss"]!!)
                resolution("postcss-loader", kiluaVersions["postcss-loader"]!!)
                resolution("tailwindcss", kiluaVersions["tailwindcss"]!!)
                resolution("@tailwindcss/postcss", kiluaVersions["tailwindcss"]!!)
                resolution("cssnano", kiluaVersions["cssnano"]!!)
                resolution("mini-css-extract-plugin", kiluaVersions["mini-css-extract-plugin"]!!)
                resolution("motion", kiluaVersions["motion"]!!)
                resolution("leaflet", kiluaVersions["leaflet"]!!)
            }
        }

        project.rootProject.extensions.findByType<YarnRootExtension>()?.apply {
            configureResolutions()
        }

        project.rootProject.extensions.findByType<WasmYarnRootExtension>()?.apply {
            configureResolutions()
        }

    }

    public companion object {

        public const val KILUA_TASK_GROUP: String = "Kilua"
        public const val PACKAGE_TASK_GROUP: String = "package"

    }

}
