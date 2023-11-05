plugins {
    kotlin("multiplatform") apply false
    `kotlin-dsl` apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.npm.publish) apply false
}

group = "dev.kilua"
version = libs.versions.kilua.get()

allprojects {
    if (hasProperty("SNAPSHOT")) {
        version = "$version-SNAPSHOT"
    }
}
