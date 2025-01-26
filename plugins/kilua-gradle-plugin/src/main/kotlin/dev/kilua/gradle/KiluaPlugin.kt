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
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.binaryen.BinaryenExec
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
        return extensions.create<KiluaExtension>("kilua")
    }

    private data class KiluaPluginContext(
        private val project: Project,
        val kiluaExtension: KiluaExtension,
        val kiluaVersions: Map<String, String>
    ) : Project by project

    private fun KiluaPluginContext.configureProject() {
        logger.debug("Configuring Kotlin/MPP plugin")

        val webpackSsrExists = layout.projectDirectory.dir("webpack.config.ssr.d").asFile.exists()

        tasks.withType<Copy>().matching {
            it.name == "jsProcessResources" || it.name == "wasmJsProcessResources"
        }.configureEach {
            eachFile {
                if (this.name == "tailwind.config.js") {
                    this.filter {
                        it.replace(
                            "SOURCES",
                            project.layout.projectDirectory.dir("src").asFile.absolutePath + "/**/*.kt"
                        )
                    }
                }
            }
        }

        tasks.withType<Copy>().matching {
            it.name == "jsBrowserDistribution" || it.name == "wasmJsBrowserDistribution"
        }.configureEach {
            exclude("/tailwind/**", "/modules/**")
        }

        if (webpackSsrExists && kiluaExtension.enableGradleTasks.get()) {
            val kiluaYmlFile = layout.projectDirectory.file(kiluaExtension.kiluaYml).get().asFile
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
            val cssFiles = cssNames.map {
                rootProject.file("build/js/node_modules/$it")
            }

            if (tasks.findByName("jsBrowserProductionWebpack") != null) {
                val kotlinWebpackJs = tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpack")
                tasks.register<KotlinWebpack>(
                    "jsBrowserProductionWebpackSSR",
                    kotlinWebpackJs.compilation,
                    project.objects
                )
                tasks.getByName<KotlinWebpack>("jsBrowserProductionWebpackSSR").apply {
                    dependsOn("jsProductionExecutableCompileSync")
                    group = KILUA_TASK_GROUP
                    description = "Builds webpack js bundle for server-side rendering."
                    mode = KotlinWebpackConfig.Mode.PRODUCTION
                    inputFilesDirectory.set(kotlinWebpackJs.inputFilesDirectory.get())
                    entryModuleName.set(kotlinWebpackJs.entryModuleName.get())
                    esModules.set(kotlinWebpackJs.esModules.get())
                    outputDirectory.set(file("build/kotlin-webpack/js.ssr/productionExecutable"))
                    mainOutputFileName.set("main.bundle.js")
                    this.webpackConfigApplier {
                        configDirectory = file("webpack.config.ssr.d")
                    }
                }
                val jsProcessResources = tasks.getByName<Copy>("jsProcessResources")
                tasks.register<Copy>("jsBrowserDistributionSSR") {
                    dependsOn("jsBrowserProductionWebpackSSR")
                    group = KILUA_TASK_GROUP
                    description = "Assembles js distribution files for server-side rendering."
                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    from(jsProcessResources)
                    exclude("/tailwind/**")
                    from("build/kotlin-webpack/js.ssr/productionExecutable")
                    into("build/dist/js.ssr/productionExecutable")
                }
                tasks.register<Jar>("jsArchiveSSR") {
                    dependsOn("jsBrowserDistributionSSR")
                    group = KILUA_TASK_GROUP
                    description = "Packages webpack js bundle for server-side rendering."
                    archiveFileName.set("ssr.zip")
                    val distribution =
                        project.tasks.getByName<Copy>(
                            "jsBrowserDistributionSSR",
                        ).outputs
                    from(distribution)
                    from(cssFiles)
                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    inputs.files(distribution, cssFiles, kiluaYmlFile)
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
                        if (cssNames.any { this.file.toString().replace('\\', '/').endsWith(it) }) {
                            this.path = this.file.relativeTo(rootProject.file("build/js/node_modules")).toString()
                        }
                    }
                }
            }
            if (tasks.findByName("wasmJsBrowserProductionWebpack") != null) {
                val kotlinWebpackWasmJs = tasks.getByName<KotlinWebpack>("wasmJsBrowserProductionWebpack")
                tasks.register<KotlinWebpack>(
                    "wasmJsBrowserProductionWebpackSSR",
                    kotlinWebpackWasmJs.compilation,
                    project.objects
                )
                tasks.getByName<KotlinWebpack>("wasmJsBrowserProductionWebpackSSR").apply {
                    dependsOn("wasmJsProductionExecutableCompileSync")
                    group = KILUA_TASK_GROUP
                    description = "Builds webpack wasmJs bundle for server-side rendering."
                    mode = KotlinWebpackConfig.Mode.PRODUCTION
                    inputFilesDirectory.set(kotlinWebpackWasmJs.inputFilesDirectory.get())
                    entryModuleName.set(kotlinWebpackWasmJs.entryModuleName.get())
                    esModules.set(kotlinWebpackWasmJs.esModules.get())
                    outputDirectory.set(file("build/kotlin-webpack/wasmJs.ssr/productionExecutable"))
                    mainOutputFileName.set("main.bundle.js")
                    this.webpackConfigApplier {
                        configDirectory = file("webpack.config.ssr.d")
                    }
                }
                val wasmJsProcessResources = tasks.getByName<Copy>("wasmJsProcessResources")
                tasks.register<Copy>("wasmJsBrowserDistributionSSR") {
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
                    into("build/dist/wasmJs.ssr/productionExecutable")
                }
                tasks.register<Jar>("wasmJsArchiveSSR") {
                    dependsOn("wasmJsBrowserDistributionSSR")
                    group = KILUA_TASK_GROUP
                    description = "Packages webpack wasmJs bundle for server-side rendering."
                    archiveFileName.set("ssr.zip")
                    val distribution = project.tasks.getByName<Copy>(
                        "wasmJsBrowserDistributionSSR",
                    ).outputs
                    from(distribution)
                    from(cssFiles)
                    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                    inputs.files(distribution, cssFiles, kiluaYmlFile)
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
                    filteringCharset = "UTF-8"
                    eachFile {
                        if (cssNames.any { this.file.toString().replace('\\', '/').endsWith(it) }) {
                            this.path = this.file.relativeTo(rootProject.file("build/js/node_modules")).toString()
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
            plugins.withId("dev.kilua.rpc") {
                afterEvaluate {
                    afterEvaluate {
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
                                registerKiluaExportTask("exportWithJs", "exportHtmlWithJs", "js") {
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
                                registerKiluaExportTask("exportWithWasmJs", "exportHtmlWithWasmJs", "wasmJs") {
                                    dependsOn("exportHtmlWithWasmJs")
                                }
                            }
                        }
                    }
                }
            }

        }
        tasks.withType<BinaryenExec> {
            binaryenArgs = mutableListOf(
                "--enable-nontrapping-float-to-int",
                "--enable-gc",
                "--enable-reference-types",
                "--enable-exception-handling",
                "--enable-bulk-memory",
                "--inline-functions-with-loops",
                "--traps-never-happen",
                "--fast-math",
                "--closed-world",
                "-O3", "--gufa",
                "-O3", "--gufa",
                "-O3", "--gufa",
            )
        }
        val dontDisableSkikoProjectProperty = project.findProperty("dev.kilua.plugin.disableSkiko") == "false"
        if (!dontDisableSkikoProjectProperty && kiluaExtension.disableSkiko.get()) {
            tasks.withType<org.jetbrains.compose.web.tasks.UnpackSkikoWasmRuntimeTask> {
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
        logger.debug("registering KiluaExportHtmlTask")
        tasks.register<KiluaExportHtmlTask>(name) {
            kiluaConfiguration?.export?.language?.let { exportLanguage.set(it) }
            kiluaConfiguration?.export?.serverParameters?.let { exportServerParameters.set(it) }
            kiluaConfiguration?.export?.pages?.let { exportPages.set(it) }
            applicationJar.set(applicationArchive)
            exportDirectory.set(layout.buildDirectory.dir("exportedHtml"))
            configuration()
        }
    }

    private fun KiluaPluginContext.registerKiluaExportTask(
        name: String,
        exportHtmlTaskName: String,
        prefix: String,
        configuration: Copy.() -> Unit = {}
    ) {
        logger.debug("registering KiluaExportTask")
        tasks.register<Copy>(name) {
            group = KILUA_TASK_GROUP
            description = "Export the SSR application as static files"
            destinationDir = layout.buildDirectory.dir("site").get().asFile
            val exported = project.tasks.getByName(exportHtmlTaskName).outputs
            val distribution = project.tasks.getByName<Copy>(
                "${prefix}BrowserDistribution",
            ).outputs
            from(exported, distribution)
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            configuration()
        }
    }

    private fun KiluaPluginContext.configureNodeEcosystem() {
        logger.debug("configuring Node")

        rootProject.extensions.findByType<org.jetbrains.kotlin.gradle.targets.js.npm.NpmExtension>()?.apply {
            logger.debug("configuring Npm")
            if (kiluaExtension.enableResolutions.get() && kiluaVersions.isNotEmpty()) {
                override("aaa-kilua-assets", kiluaVersions["npm.kilua-assets"]!!)
                override("zzz-kilua-assets", kiluaVersions["npm.kilua-assets"]!!)
                override("css-loader", kiluaVersions["css-loader"]!!)
                override("style-loader", kiluaVersions["style-loader"]!!)
                override("imports-loader", kiluaVersions["imports-loader"]!!)
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
                override("autoprefixer", kiluaVersions["autoprefixer"]!!)
                override("tailwindcss", kiluaVersions["tailwindcss"]!!)
                override("cssnano", kiluaVersions["cssnano"]!!)
                override("mini-css-extract-plugin", kiluaVersions["mini-css-extract-plugin"]!!)
            }
        }

        rootProject.extensions.findByType<YarnRootExtension>()?.apply {
            logger.debug("configuring Yarn")
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
                resolution("lz-string", kiluaVersions["lz-string"]!!)
                resolution("marked", kiluaVersions["marked"]!!)
                resolution("sanitize-html", kiluaVersions["sanitize-html"]!!)
                resolution("postcss", kiluaVersions["postcss"]!!)
                resolution("postcss-loader", kiluaVersions["postcss-loader"]!!)
                resolution("autoprefixer", kiluaVersions["autoprefixer"]!!)
                resolution("tailwindcss", kiluaVersions["tailwindcss"]!!)
                resolution("cssnano", kiluaVersions["cssnano"]!!)
                resolution("mini-css-extract-plugin", kiluaVersions["mini-css-extract-plugin"]!!)
            }
        }
    }

    public companion object {

        public const val KILUA_TASK_GROUP: String = "Kilua"
        public const val PACKAGE_TASK_GROUP: String = "package"

    }

}
