plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
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
    kotlinJvmTargets()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":modules:kilua-ssr-server"))
                api(libs.vertx.web)
                api(libs.vertx.lang.kotlin)
                api(libs.vertx.lang.kotlin.coroutines)
                api(libs.vertx.launcher.application)
            }
        }
    }
}

setupDokka(tasks.dokkaGenerate)
setupPublishing()
