plugins {
    kotlin("multiplatform")
    alias(libs.plugins.nmcp)
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

kotlin {
    explicitApi()
    compilerOptions(withWasmMetadata = false)
    kotlinJsTargets()
    kotlinWasmTargets()
    kotlinJvmTargets()
    sourceSets {
        getByName("commonMain") {
            dependencies {
            }
        }
        getByName("jsMain") {
            dependencies {
            }
        }
        getByName("wasmJsMain") {
            dependencies {
            }
        }
        getByName("jvmMain") {
            dependencies {
            }
        }
    }
}

setupDokka(tasks.dokkaGenerate)
setupPublishing()
