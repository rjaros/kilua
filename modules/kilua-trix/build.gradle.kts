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

kotlin {
    explicitApi()
    compilerOptions()
    kotlinJsTargets(buildTarget, isInIdea)
    kotlinWasmTargets(buildTarget, isInIdea)
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kilua"))
                implementation(npm("trix", libs.versions.trix.get()))
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