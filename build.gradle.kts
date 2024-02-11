plugins {
    kotlin("multiplatform") apply false
    `kotlin-dsl` apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.npm.publish) apply false
    alias(libs.plugins.detekt) apply false
    id(libs.plugins.dokka.get().pluginId)
}

group = "dev.kilua"
version = libs.versions.kilua.get()

allprojects {
    if (hasProperty("SNAPSHOT")) {
        version = "$version-SNAPSHOT"
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
