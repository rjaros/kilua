import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    alias(libs.plugins.kilua.rpc)
    alias(libs.plugins.kilua)
}

val mainClassName = "io.ktor.server.netty.EngineMain"

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    jvmToolchain(17)
    jvm {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
            }
        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass.set(mainClassName)
        }
    }
    js(IR) {
        // useEsModules() workaround modules order (https://youtrack.jetbrains.com/issue/KT-64616)
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
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
        if (project.gradle.startParameter.taskNames.find {
                it.contains("wasmJsBrowserProductionWebpack") || it.contains("wasmJsArchive") || it.contains("jarWithWasmJs")
            } != null) {
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
                    "--closed-world",
                    "--metrics",
                    "-O3", "--gufa", "--metrics",
                    "-O3", "--gufa", "--metrics",
                    "-O3", "--gufa", "--metrics",
                )
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kilua.rpc.ktor.koin)
                implementation(project(":modules:kilua-common-types"))
            }
        }
        val webMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(project(":kilua"))
                implementation(project(":modules:kilua-bootstrap"))
                implementation(project(":modules:kilua-fontawesome"))
                implementation(project(":modules:kilua-select-remote"))
                implementation(project(":modules:kilua-tom-select-remote"))
                implementation(project(":modules:kilua-tabulator-remote"))
            }
        }
        val jsMain by getting {
            dependsOn(webMain)
        }
        val wasmJsMain by getting {
            dependsOn(webMain)
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.server.netty)
                implementation(libs.ktor.server.compression)
                implementation(libs.koin.annotations)
                implementation(libs.logback.classic)
            }
            kotlin.srcDir("build/generated/ksp/jvm/jvmMain/kotlin")
        }
    }
}

compose {
    platformTypes.set(platformTypes.get() - KotlinPlatformType.jvm)
    kotlinCompilerPlugin.set(libs.versions.compose.plugin)
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=${libs.versions.kotlin.get()}")
}

dependencies {
    add("kspJvm", "io.insert-koin:koin-ksp-compiler:${libs.versions.koin.annotations.get()}")
}

afterEvaluate {
    tasks {
        getByName("kspKotlinJvm").apply {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}
