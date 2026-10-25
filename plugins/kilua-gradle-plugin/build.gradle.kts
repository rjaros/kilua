plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlinx.serialization)
    id("java-gradle-plugin")
    alias(libs.plugins.nmcp)
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
    alias(libs.plugins.gradle.plugin.publish)
}

// This is an included build, so the root's `allprojects { group/version }` does not reach it and
// both have to be set here. The version must match the root's so that the processor coordinate the
// plugin injects resolves to the same version - it is redirected to the local project anyway, but
// keeping them equal keeps a published plugin and a published library in step.
group = "dev.kilua"
version = libs.versions.kilua.asProvider().get() + if (hasProperty("SNAPSHOT")) "-SNAPSHOT" else ""

repositories {
    gradlePluginPortal()
    mavenCentral()
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
    kotlinJvmTargets("21")
}

dependencies {
    implementation(kotlin("gradle-plugin"))
    implementation(libs.compose.gradle.plugin)
    implementation(libs.tomlj)
    implementation(libs.kaml)
    implementation(libs.xml.builder)
}

tasks.processResources {
    // The plugin reads its own version out of a bundled copy of the catalog (see KiluaPlugin.apply).
    // It has to sit on the main runtime classpath, not only in the jar: when the plugin is applied from
    // this included build Gradle may put the project's classes and resources on the consumer's classpath
    // directly, and a version written into the jar alone would then be missing and fail the apply. A
    // published plugin packages processResources output into the jar anyway, so one location covers both.
    val versionsToml = rootProject.layout.projectDirectory.file("../../gradle/libs.versions.toml")
    val pluginVersion = project.version.toString()

    // The version is threaded through a filter, which Gradle cannot see as an input on its own, so
    // without this a change of version (e.g. adding -PSNAPSHOT) would leave the resource stale.
    inputs.property("kiluaPluginVersion", pluginVersion)
    from(versionsToml) {
        rename { "dev.kilua.versions.toml" }
        // Only the `[versions]` entry `kilua = "..."` is rewritten; the `[plugins]` alias
        // `kilua = { id = "dev.kilua", ... }` declares the same key and must be left alone.
        filter { line ->
            if (line.startsWith("kilua = \"")) {
                line.replaceAfter("kilua = ", "\"${pluginVersion}\"")
            } else {
                line
            }
        }
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

setupDokka()
