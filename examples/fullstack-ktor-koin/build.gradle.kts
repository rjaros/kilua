import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
    id("com.google.devtools.ksp")
    alias(libs.plugins.kilua.rpc)
    alias(libs.plugins.kilua)
}

val mainClassName = "io.ktor.server.netty.EngineMain"

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    jvmToolchain(21)
    jvm {
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
                sourceMaps = false
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
        compilerOptions {
            target.set("es2015")
        }
    }
    wasmJs {
        useEsModules()
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
                sourceMaps = false
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
        compilerOptions {
            target.set("es2015")
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
        }
    }
}

composeCompiler {
    targetKotlinPlatforms.set(
        KotlinPlatformType.values()
            .filterNot { it == KotlinPlatformType.jvm }
            .asIterable()
    )
}

dependencies {
    add("kspJvm", "io.insert-koin:koin-ksp-compiler:${libs.versions.koin.annotations.get()}")
}
