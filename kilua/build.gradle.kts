import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.detekt)
    id("maven-publish")
    id("signing")
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("aaa-kilua-assets", libs.versions.npm.kilua.assets.get())
        resolution("zzz-kilua-assets", libs.versions.npm.kilua.assets.get())
        resolution("css-loader", libs.versions.css.loader.get())
        resolution("style-loader", libs.versions.style.loader.get())
        resolution("imports-loader", libs.versions.imports.loader.get())
        resolution("split.js", libs.versions.splitjs.get())
        resolution("html-differ", libs.versions.html.differ.get())
        resolution("@popperjs/core", libs.versions.popperjs.core.get())
        resolution("bootstrap", libs.versions.bootstrap.asProvider().get())
        resolution("bootstrap-icons", libs.versions.bootstrap.icons.get())
        resolution("@fortawesome/fontawesome-free", libs.versions.fontawesome.get())
        resolution("trix", libs.versions.trix.get())
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
    kotlinJsTargets()
    kotlinWasmTargets()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(libs.kotlinx.atomicfu)
                api(libs.kotlinx.coroutines)
                api(libs.kotlinx.serialization.json)
                api(libs.kotlinx.datetime)
                api(project(":modules:kilua-common-types"))
                api(project(":modules:kilua-dom"))
//                implementation(npm("aaa-kilua-assets", "http://localhost:8001/aaa-kilua-assets-0.0.1-SNAPSHOT.1.tgz"))
                implementation(npm("aaa-kilua-assets", libs.versions.npm.kilua.assets.get()))
                implementation(npm("zzz-kilua-assets", libs.versions.npm.kilua.assets.get()))
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
        val jsMain by getting {
            dependencies {
            }
        }
        val wasmJsMain by getting {
            dependencies {
            }
        }
    }
}

rootProject.the<NodeJsRootExtension>().apply {
    nodeVersion = "22.0.0-v8-canary20231201483993aba6"
    nodeDownloadBaseUrl = "https://mirrors.dotsrc.org/nodejs/v8-canary"
}

rootProject.tasks.withType<org.jetbrains.kotlin.gradle.targets.js.npm.tasks.KotlinNpmInstallTask>().configureEach {
    args.add("--ignore-engines")
}

compose {
    kotlinCompilerPlugin.set(libs.versions.compose.plugin)
    kotlinCompilerPluginArgs.add("suppressKotlinVersionCompatibilityCheck=${libs.versions.kotlin.get()}")
}
