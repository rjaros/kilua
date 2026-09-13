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
        getByName("jvmMain") {
            dependencies {
                implementation(project(":modules:kilua-ssr-server"))
                implementation(kotlin("reflect"))
                api(libs.kotlinx.coroutines)
                api(project.dependencies.platform(libs.quarkus.bom))
                api(libs.quarkus.core)
                api(libs.quarkus.vertx.http)
                implementation(libs.quarkus.vertx.lang.kotlin.coroutines)
                api(libs.quarkus.arc)
                api(libs.quarkus.config.yaml)
            }
        }
    }
}

setupDokka(tasks.dokkaGenerate)
setupPublishing()