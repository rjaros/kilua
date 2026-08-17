plugins {
    kotlin("multiplatform")
    alias(libs.plugins.detekt)
    alias(libs.plugins.nmcp)
    id("org.jetbrains.dokka")
    id("maven-publish")
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
        getByName("commonMain") {
            dependencies {
                implementation(project(":kilua"))
                implementation(npm("lucide-static", libs.versions.lucide.get()))
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
