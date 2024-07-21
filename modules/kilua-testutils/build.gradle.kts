@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.nmcp)
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
    kotlinJvmTargets()
    kotlinJsCommonTargets()
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.coroutines)
                implementation(project(":kilua"))
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        jsCommonMain {
            dependencies {
                implementation(project(":modules:kilua-dom"))
                implementation(npm("html-differ", libs.versions.html.differ.get()))
            }
        }
        jsMain {
            dependencies {
            }
        }
        wasmJsMain {
            dependencies {
            }
        }
    }
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
