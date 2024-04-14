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
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass.set(mainClassName)
        }
    }
    js(IR) {
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
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
        val webMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(npm("marked", "^12.0.1"))
                implementation(project(":kilua"))
                implementation(project(":modules:kilua-routing"))
                implementation(project(":modules:kilua-rest"))
                implementation(project(":modules:kilua-rsup-progress"))
                implementation(project(":modules:kilua-ssr"))
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
                implementation(project(":modules:kilua-ssr-server-ktor"))
                implementation(libs.ktor.server.netty)
                implementation(libs.ktor.server.compression)
                implementation(libs.ktor.server.caching.headers)
                implementation(libs.logback.classic)
            }
        }
    }
}

compose {
    platformTypes.set(platformTypes.get() - KotlinPlatformType.jvm)
    kotlinCompilerPlugin.set(libs.versions.compose.plugin)
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=${libs.versions.kotlin.get()}")
}
