import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
    alias(libs.plugins.kilua)
}

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    js(IR) {
        useEsModules()
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
                sourceMaps = false
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }
    wasmJs {
        useEsModules()
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
                sourceMaps = false
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":kilua"))
                implementation(project(":modules:kilua-core-css"))
                implementation(project(":modules:kilua-bootstrap"))
                implementation(project(":modules:kilua-bootstrap-icons"))
                implementation(project(":modules:kilua-fontawesome"))
                implementation(project(":modules:kilua-imask"))
                implementation(project(":modules:kilua-lazy-layouts"))
                implementation(project(":modules:kilua-rest"))
                implementation(project(":modules:kilua-splitjs"))
                implementation(project(":modules:kilua-tabulator"))
                implementation(project(":modules:kilua-tempus-dominus"))
                implementation(project(":modules:kilua-tom-select"))
                implementation(project(":modules:kilua-toastify"))
                implementation(project(":modules:kilua-trix"))
                implementation(project(":modules:kilua-rsup-progress"))
                implementation(project(":modules:kilua-i18n"))
                implementation(project(":modules:kilua-svg"))
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

composeCompiler {
    enableStrongSkippingMode = true
}
