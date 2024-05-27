plugins {
    kotlin("multiplatform")
    alias(libs.plugins.dokka)
    alias(libs.plugins.nmcp)
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

tasks.register<Jar>("javadocJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

setupPublishing()

nmcp {
    publishAllPublications {}
}
