plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlinx.serialization)
    id("java-gradle-plugin")
    alias(libs.plugins.detekt)
    alias(libs.plugins.nmcp)
    id("org.jetbrains.dokka")
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
    implementation(libs.kaml)
    implementation(libs.xml.builder)
}

tasks.getByName("jar", Jar::class) {
    from(rootProject.layout.projectDirectory.file("gradle/libs.versions.toml")) {
        rename { "dev.kilua.versions.toml" }
        filter { line -> line.replaceAfter("kilua = ", "\"${version}\"") }
    }
}

publishing {
    publications {
        withType<MavenPublication> {
            pom {
                defaultPom()
            }
        }
    }
}

tasks.getByName("dokkaGeneratePublicationHtml").run {
    enabled = !project.hasProperty("SNAPSHOT")
}

extensions.getByType<SigningExtension>().run {
    isRequired = !project.hasProperty("SNAPSHOT")
    sign(extensions.getByType<PublishingExtension>().publications)
}

setupDokka(tasks.dokkaGenerate, path = "plugins/")
