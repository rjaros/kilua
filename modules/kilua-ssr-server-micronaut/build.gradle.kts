plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.nmcp)
    alias(libs.plugins.ksp)
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

tasks.register<Jar>("javadocJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

setupPublishing()

nmcp {
    publishAllPublications {}
}
