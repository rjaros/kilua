import io.quarkus.gradle.tasks.QuarkusDev

plugins {
    kotlin("jvm")
    id("io.quarkus")
}

dependencies {
    implementation(project(":examples:ssr-quarkus"))
    implementation(project.dependencies.enforcedPlatform(libs.quarkus.bom))
    implementation(libs.quarkus.core)
}

tasks.withType<QuarkusDev> {
    jvmArguments.addAll("-Xmx2g")
}
