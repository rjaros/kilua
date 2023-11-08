import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
}

val isInIdea = System.getProperty("idea.vendor.name") != null
val buildTarget: String by project

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    if (buildTarget == "js" || !isInIdea) {
        js(IR) {
            // useEsModules()
            browser {
                commonWebpackConfig {
                    outputFileName = "main.bundle.js"
                }
                runTask {
                    sourceMaps = false
                }
                testTask {
                    useKarma {
                        useChromeHeadless()
                    }
                }
            }
            binaries.executable()
        }
    }
    if (buildTarget == "wasm" || !isInIdea) {
        wasmJs {
            useEsModules()
            browser {
                commonWebpackConfig {
                    outputFileName = "main.bundle.js"
                }
                runTask {
                    sourceMaps = false
                }
                testTask {
                    useKarma {
                        useChromeHeadlessWasmGc()
                    }
                }
            }
            binaries.executable()
            applyBinaryen {
                if (project.gradle.startParameter.taskNames.contains("wasmJsBrowserProductionWebpack")) {
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
                        "--metrics",
                        "-O3", "--gufa", "--metrics",
                        "-O3", "--gufa", "--metrics",
                        "-O3", "--gufa", "--metrics",
                    )
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kilua"))
            }
        }
        if (buildTarget == "js" || !isInIdea) {
            val jsMain by getting {
                dependencies {
                }
            }
        }
        if (buildTarget == "wasm" || !isInIdea) {
            val wasmJsMain by getting {
                dependencies {
                }
            }
        }
    }
}

compose {
    kotlinCompilerPlugin.set(libs.versions.compose.plugin)
//    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=${libs.versions.kotlin.get()}")
}
