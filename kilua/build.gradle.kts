import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.detekt)
    id("maven-publish")
    id("signing")
}

val isInIdea = System.getProperty("idea.vendor.name") != null
val buildTarget: String by project

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("kilua-assets", libs.versions.npm.kilua.assets.get())
        resolution("css-loader", libs.versions.css.loader.get())
        resolution("style-loader", libs.versions.style.loader.get())
        resolution("imports-loader", libs.versions.imports.loader.get())
        resolution("split.js", libs.versions.splitjs.get())
        resolution("html-differ", libs.versions.html.differ.get())
    }
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom("../detekt-config.yml")
    buildUponDefaultConfig = true
}

kotlin {
    explicitApi()
    compilerOptions()
    kotlinJsTargets(buildTarget, isInIdea)
    kotlinWasmTargets(buildTarget, isInIdea)
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(libs.kotlinx.coroutines)
                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.datetime)
                api(project(":modules:kilua-common-types"))
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
                implementation(project(":modules:kilua-testutils"))
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

rootProject.the<NodeJsRootExtension>().apply {
    nodeVersion = "22.0.0-v8-canary20231027fc15c384ea"
    nodeDownloadBaseUrl = "https://mirrors.dotsrc.org/nodejs/v8-canary"
}

rootProject.tasks.withType<org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask>().configureEach {
    args.add("--ignore-engines")
}

compose {
    kotlinCompilerPlugin.set(libs.versions.compose.plugin)
//    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=${libs.versions.kotlin.get()}")
}
