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
                api(libs.kotlinx.serialization.json)
                api(libs.ktor.client.core)
                api(libs.ktor.client.cio)
                api(libs.resources.optimizer)
                api(libs.expiring.map)
                api(libs.xml.builder)
            }
        }
    }
}

setupDokka(tasks.dokkaGenerate)
setupPublishing()
