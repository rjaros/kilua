plugins {
    kotlin("multiplatform")
    alias(libs.plugins.detekt)
    id("maven-publish")
    id("signing")
}

val isInIdea = System.getProperty("idea.vendor.name") != null
val buildTarget: String by project

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom("../../detekt-config.yml")
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
                implementation(libs.kotlinx.coroutines)
                implementation(project(":kilua"))
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(npm("html-differ", libs.versions.html.differ.get()))
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
