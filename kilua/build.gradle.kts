plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.nmcp)
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
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
                api(libs.wrappers.browser)
                api(project(":modules:kilua-annotations"))
                api(project(":modules:kilua-common-types"))
                api(project(":modules:kilua-core-modules"))
//                implementation(npm("aaa-kilua-assets", "http://localhost:8001/aaa-kilua-assets-0.0.9-SNAPSHOT.tgz"))
                implementation(npm("aaa-kilua-assets", libs.versions.npm.kilua.assets.get()))
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
                implementation(npm("mini-css-extract-plugin", libs.versions.mini.css.extract.plugin.get()))
            }
        }
        val wasmJsMain by getting {
            dependencies {
            }
        }
    }
}

setupKsp()

setupDokka(tasks.dokkaGenerate, path = "")
setupPublishing()
