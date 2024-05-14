plugins {
    `kotlin-dsl`
    kotlin("jvm")
    id("java-gradle-plugin")
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.nmcp)
    id("maven-publish")
    id("signing")
    alias(libs.plugins.gradle.plugin.publish)
}

repositories {
    gradlePluginPortal()
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom("../../detekt-config.yml")
    buildUponDefaultConfig = true
}

@Suppress("UnstableApiUsage")
gradlePlugin {
    website.set(kiluaUrl)
    vcsUrl.set(kiluaVcsUrl)
    plugins {
        create("kiluaGradlePlugin") {
            displayName = kiluaProjectName
            description = kiluaProjectDescription
            id = "dev.kilua"
            implementationClass = "dev.kilua.gradle.KiluaPlugin"
            tags.set(
                listOf(
                    "kilua",
                    "kotlin",
                    "kotlin-js",
                    "kotlin-wasm",
                    "webassembly",
                    "kotlin-multiplatform",
                    "web",
                    "framework",
                    "compose"
                )
            )
        }
    }
}

kotlin {
    explicitApi()
    kotlinJvmTargets()
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation(libs.compose.gradle.plugin)
    implementation(libs.tomlj)
}

tasks.register<Jar>("javadocJar") {
    dependsOn(tasks.dokkaHtml)
    from(tasks.dokkaHtml.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

tasks.getByName("dokkaHtml").apply {
    enabled = !project.hasProperty("SNAPSHOT")
}

tasks.getByName("jar", Jar::class) {
    from(rootProject.layout.projectDirectory.file("gradle/libs.versions.toml")) {
        rename { "dev.kilua.versions.toml" }
        filter { line -> line.replaceAfter("kilua = ", "\"${version}\"") }
    }
}

publishing {
    publications {
        withType<MavenPublication>() {
            pom {
                defaultPom()
            }
        }
    }
}

extensions.getByType<SigningExtension>().run {
    isRequired = !project.hasProperty("SNAPSHOT")
    sign(extensions.getByType<PublishingExtension>().publications)
}

nmcp {
    publishAllPublications {}
}
