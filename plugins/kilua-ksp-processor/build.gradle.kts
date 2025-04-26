plugins {
    kotlin("multiplatform")
    alias(libs.plugins.nmcp)
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

kotlin {
    explicitApi()
    compilerOptions()
    kotlinJvmTargets()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":modules:kilua-annotations"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.ksp.symbol.processing.api)
            }
        }
    }
}

setupDokka(tasks.dokkaGenerate, path = "plugins/")
setupPublishing()
