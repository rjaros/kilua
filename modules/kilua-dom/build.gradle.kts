plugins {
    kotlin("multiplatform")
    alias(libs.plugins.detekt)
    id(libs.plugins.dokka.get().pluginId)
    id(libs.plugins.maven.publish.get().pluginId)
    id("signing")
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom("../../detekt-config.yml")
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

setupPublishing(libs.versions.kilua.get())
