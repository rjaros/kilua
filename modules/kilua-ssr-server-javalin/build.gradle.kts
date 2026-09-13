plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
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
        getByName("jvmMain") {
            dependencies {
                implementation(project(":modules:kilua-ssr-server"))
                api(libs.javalin)
                api(libs.logback.classic)
            }
        }
    }
}

setupDokka(tasks.dokkaGenerate)
setupPublishing()
