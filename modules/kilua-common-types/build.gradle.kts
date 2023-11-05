plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
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
    kotlinJvmTargets()
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
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
        val jvmMain by getting {
            dependencies {
            }
        }
    }
}
