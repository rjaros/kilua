plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.nmcp)
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

kotlin {
    explicitApi()
    compilerOptions()
    kotlinJsTargets()
    kotlinWasmTargets()
    sourceSets {
        getByName("commonMain") {
            dependencies {
                implementation(project(":kilua"))
                implementation(npm("toastify-js", libs.versions.toastify.get()))
            }
        }
        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":modules:kilua-testutils"))
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
    }
}

setupDokka(tasks.dokkaGenerate)
setupPublishing()
