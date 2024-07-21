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
                api(project(":modules:kilua-dom-core"))
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":modules:kilua-testutils"))
            }
        }
        jsCommonMain {
            dependencies {
                // implementation(npm("zzz-kilua-assets", "http://localhost:8001/zzz-kilua-assets-0.0.9-SNAPSHOT.tgz"))
                implementation(npm("zzz-kilua-assets", libs.versions.npm.kilua.assets.get()))
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
        jvmMain {
            dependencies {
                implementation(libs.kotlinx.coroutines)
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
