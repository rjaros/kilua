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
                binaryenArgs = mutableListOf(
                    "--enable-nontrapping-float-to-int",
                    "--enable-gc",
                    "--enable-reference-types",
                    "--enable-exception-handling",
                    "--enable-bulk-memory",
                    "--inline-functions-with-loops",
                    "--traps-never-happen",
                    "--fast-math",
                    "-O1",
                    "-c" // Run passes while binary size decreases
                )
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