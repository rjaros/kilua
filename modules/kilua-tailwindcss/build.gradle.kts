plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
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
        val commonMain by getting {
            dependencies {
                implementation(project(":kilua"))
                implementation(npm("postcss", libs.versions.postcss.asProvider().get()))
                implementation(npm("postcss-loader", libs.versions.postcss.loader.get()))
                implementation(npm("tailwindcss", libs.versions.tailwindcss.get()))
                implementation(npm("@tailwindcss/postcss", libs.versions.tailwindcss.get()))
                implementation(npm("cssnano", libs.versions.cssnano.get()))
                implementation(npm("mini-css-extract-plugin", libs.versions.mini.css.extract.plugin.get()))
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

setupDokka(tasks.dokkaGenerate)
setupPublishing()

nmcp {
    publishAllPublications {}
}
