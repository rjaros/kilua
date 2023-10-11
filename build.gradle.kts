/*
 * Copyright (c) 2023-present Robert Jaros
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

import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("multiplatform") version kotlinVersion
    val composeVersion: String by System.getProperties()
    id("org.jetbrains.compose") version composeVersion
}

val isInIdea = System.getProperty("idea.vendor.name") != null

allprojects {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/dev")
        mavenLocal()
    }
    version = project.properties["versionNumber"]!!
    if (hasProperty("SNAPSHOT")) {
        version = "$version-SNAPSHOT"
    }
}

val buildTarget: String by project

val kotlinVersion: String by System.getProperties()
val serializationVersion: String by project
val coroutinesVersion: String by project

val cssLoaderVersion: String by project
val styleLoaderVersion: String by project
val importsLoaderVersion: String by project

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("css-loader", cssLoaderVersion)
        resolution("style-loader", styleLoaderVersion)
        resolution("imports-loader", importsLoaderVersion)
    }
}

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    explicitApi()
    if (buildTarget == "js" || !isInIdea) {
        js(IR) {
            // useEsModules()
            browser {
                commonWebpackConfig {
                    outputFileName = "main.bundle.js"
                }
                runTask {
                    sourceMaps = false
                    devServer = KotlinWebpackConfig.DevServer(
                        static = mutableListOf("../../../processedResources/js/main")
                    )
                }
                testTask {
                    useKarma {
                        useChromeHeadless()
                    }
                }
            }
            nodejs {
                testTask {
                    useMocha()
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
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation(npm("css-loader", cssLoaderVersion))
                implementation(npm("style-loader", styleLoaderVersion))
                implementation(npm("imports-loader", importsLoaderVersion))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
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
    val composePluginVersion: String by System.getProperties()
    kotlinCompilerPlugin.set(composePluginVersion)
    val kotlinVersion: String by System.getProperties()
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=$kotlinVersion")
}
