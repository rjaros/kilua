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

val versionVal = libs.versions.kilua.asProvider().get()

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
        resolution("rsup-progress", libs.versions.rsup.progress.get())
        resolution("lz-string", libs.versions.lz.string.get())
        resolution("marked", libs.versions.marked.get())
        resolution("sanitize-html", libs.versions.sanitize.html.get())
        resolution("postcss", libs.versions.postcss.asProvider().get())
        resolution("postcss-loader", libs.versions.postcss.loader.get())
        resolution("autoprefixer", libs.versions.autoprefixer.get())
        resolution("tailwindcss", libs.versions.tailwindcss.get())
        resolution("cssnano", libs.versions.cssnano.get())
        resolution("mini-css-extract-plugin", libs.versions.mini.css.extract.plugin.get())
    }
}

nmcp {
    publishAggregation {
        project(":kilua")
        project(":modules:kilua-bootstrap")
        project(":modules:kilua-bootstrap-icons")
        project(":modules:kilua-common-types")
        project(":modules:kilua-core-css")
        project(":modules:kilua-dom")
        project(":modules:kilua-fontawesome")
        project(":modules:kilua-i18n")
        project(":modules:kilua-imask")
        project(":modules:kilua-lazy-layouts")
        project(":modules:kilua-marked")
        project(":modules:kilua-rest")
        project(":modules:kilua-routing")
        project(":modules:kilua-rsup-progress")
        project(":modules:kilua-sanitize-html")
        project(":modules:kilua-select-remote")
        project(":modules:kilua-splitjs")
        project(":modules:kilua-ssr")
        project(":modules:kilua-ssr-server")
        project(":modules:kilua-ssr-server-javalin")
        project(":modules:kilua-ssr-server-jooby")
        project(":modules:kilua-ssr-server-ktor")
        project(":modules:kilua-ssr-server-micronaut")
        project(":modules:kilua-ssr-server-spring-boot")
        project(":modules:kilua-ssr-server-vertx")
        project(":modules:kilua-tabulator")
        project(":modules:kilua-tabulator-remote")
        project(":modules:kilua-tailwindcss")
        project(":modules:kilua-tempus-dominus")
        project(":modules:kilua-testutils")
        project(":modules:kilua-toastify")
        project(":modules:kilua-tom-select")
        project(":modules:kilua-tom-select-remote")
        project(":modules:kilua-trix")
        project(":plugins:kilua-gradle-plugin")
        username = findProperty("mavenCentralUsername")?.toString()
        password = findProperty("mavenCentralPassword")?.toString()
        publicationType = "USER_MANAGED"
    }
}
