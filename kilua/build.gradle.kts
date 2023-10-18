import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose)
    id("maven-publish")
    id("signing")
}

val isInIdea = System.getProperty("idea.vendor.name") != null

group = "dev.kilua"
version = libs.versions.kilua.get()

allprojects {
    if (hasProperty("SNAPSHOT")) {
        version = "$version-SNAPSHOT"
    }
}

val buildTarget: String by project

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("kilua-assets", libs.versions.npm.kilua.assets.get())
        resolution("css-loader", libs.versions.css.loader.get())
        resolution("style-loader", libs.versions.style.loader.get())
        resolution("imports-loader", libs.versions.imports.loader.get())
        resolution("split.js", libs.versions.splitjs.get())
    }
}

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    explicitApi()
    if (buildTarget == "js" || !isInIdea) {
        js(IR) {
            useEsModules()
            browser {
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
        }
    }
    if (buildTarget == "wasm" || !isInIdea) {
        wasmJs {
            useEsModules()
            browser {
                testTask {
                    useKarma {
                        useChromeHeadlessWasmGc()
                    }
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(libs.kotlinx.coroutines)
                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.datetime)
                implementation(npm("kilua-assets", libs.versions.npm.kilua.assets.get()))
                implementation(npm("css-loader", libs.versions.css.loader.get()))
                implementation(npm("style-loader", libs.versions.style.loader.get()))
                implementation(npm("imports-loader", libs.versions.imports.loader.get()))
                implementation(npm("split.js", libs.versions.splitjs.get()))
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
    kotlinCompilerPlugin.set(libs.versions.compose.plugin)
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=${libs.versions.kotlin.get()}")
}
