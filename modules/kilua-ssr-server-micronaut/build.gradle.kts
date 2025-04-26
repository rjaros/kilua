plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ksp)
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
                implementation(kotlin("reflect"))
                api(libs.kotlinx.coroutines.reactor)
                api(project.dependencies.platform(libs.micronaut.platform))
                api("io.micronaut:micronaut-http")
                api("io.micronaut:micronaut-router")
                api("io.micronaut.reactor:micronaut-reactor")
            }
        }
    }
}

dependencies {
    add("kspJvm", platform(libs.micronaut.platform))
    add("kspJvm", "io.micronaut:micronaut-inject-kotlin")
}

setupDokka(tasks.dokkaGenerate)
setupPublishing()
