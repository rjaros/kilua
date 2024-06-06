plugins {
    kotlin("multiplatform")
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.nmcp)
    id("maven-publish")
    id("signing")
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom("../detekt-config.yml")
    buildUponDefaultConfig = true
}

kotlin {
    explicitApi()
    compilerOptions()
    kotlinJsTargets()
    kotlinWasmTargets()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(compose.runtime)
                api(libs.kotlinx.atomicfu)
                api(libs.kotlinx.coroutines)
                api(libs.kotlinx.serialization.json)
                api(project(":modules:kilua-annotations"))
                api(project(":modules:kilua-common-types"))
                api(project(":modules:kilua-dom"))
                api(project(":modules:kilua-core-modules"))
//                implementation(npm("aaa-kilua-assets", "http://localhost:8001/aaa-kilua-assets-0.0.9-SNAPSHOT.tgz"))
                implementation(npm("aaa-kilua-assets", libs.versions.npm.kilua.assets.get()))
                implementation(npm("css-loader", libs.versions.css.loader.get()))
                implementation(npm("style-loader", libs.versions.style.loader.get()))
                implementation(npm("imports-loader", libs.versions.imports.loader.get()))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(project(":modules:kilua-testutils"))
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

setupKsp()

tasks.register<Jar>("javadocJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

setupPublishing()

nmcp {
    publishAllPublications {}
}
