@file:Suppress("UnstableApiUsage")

// The Gradle plugin is its own build so that the examples can apply it from source: a plugin
// applied through the `plugins {}` block is resolved through pluginManagement repositories, and an
// included build is the only way to redirect that to a local project. The KSP processor and the
// runtime modules stay in the root build, where they are reached by dependency substitution instead.
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    // The catalog lives once, at the root. An included build does not discover the root's
    // `gradle/libs.versions.toml` on its own, so it is loaded from there explicitly - the same
    // thing the root buildSrc does.
    versionCatalogs {
        create("libs") {
            from(files("../../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "kilua-gradle-plugin"
