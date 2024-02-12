plugins {
    kotlin("multiplatform") apply false
    `kotlin-dsl` apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.npm.publish) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.nmcp)
    id("maven-publish")
}

val versionVal = libs.versions.kilua.get()

allprojects {
    group = "dev.kilua"
    if (hasProperty("SNAPSHOT")) {
        version = "$versionVal-SNAPSHOT"
    } else {
        version = versionVal
    }
}

project.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin> {
    project.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().apply {
        resolution("aaa-kilua-assets", libs.versions.npm.kilua.assets.get())
        resolution("zzz-kilua-assets", libs.versions.npm.kilua.assets.get())
        resolution("css-loader", libs.versions.css.loader.get())
        resolution("style-loader", libs.versions.style.loader.get())
        resolution("imports-loader", libs.versions.imports.loader.get())
        resolution("split.js", libs.versions.splitjs.get())
        resolution("html-differ", libs.versions.html.differ.get())
        resolution("@popperjs/core", libs.versions.popperjs.core.get())
        resolution("bootstrap", libs.versions.bootstrap.asProvider().get())
        resolution("bootstrap-icons", libs.versions.bootstrap.icons.get())
        resolution("@fortawesome/fontawesome-free", libs.versions.fontawesome.get())
        resolution("trix", libs.versions.trix.get())
        resolution("@eonasdan/tempus-dominus", libs.versions.tempus.dominus.get())
        resolution("tom-select", libs.versions.tom.select.get())
        resolution("imask", libs.versions.imask.get())
        resolution("tabulator-tables", libs.versions.tabulator.get())
    }
}

nmcp {
    publishAggregation {
        project(":kilua")
        username = "a"//findProperty("mavenCentralUsername")?.toString()
        password = "b"//findProperty("mavenCentralPassword")?.toString()
        publicationType = "USER_MANAGED"
    }
}
