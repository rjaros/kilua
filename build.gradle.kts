plugins {
    kotlin("multiplatform") apply false
    `kotlin-dsl` apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.npm.publish) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.nmcp)
    id("org.jetbrains.dokka")
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

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.npm.NpmExtension>().apply {
        override("aaa-kilua-assets", libs.versions.npm.kilua.assets.get())
        override("zzz-kilua-assets", libs.versions.npm.kilua.assets.get())
        override("css-loader", libs.versions.css.loader.get())
        override("style-loader", libs.versions.style.loader.get())
        override("imports-loader", libs.versions.imports.loader.get())
        override("split.js", libs.versions.splitjs.get())
        override("html-differ", libs.versions.html.differ.get())
        override("@popperjs/core", libs.versions.popperjs.core.get())
        override("bootstrap", libs.versions.bootstrap.asProvider().get())
        override("bootstrap-icons", libs.versions.bootstrap.icons.get())
        override("@fortawesome/fontawesome-free", libs.versions.fontawesome.get())
        override("trix", libs.versions.trix.get())
        override("@eonasdan/tempus-dominus", libs.versions.tempus.dominus.get())
        override("tom-select", libs.versions.tom.select.get())
        override("imask", libs.versions.imask.get())
        override("tabulator-tables", libs.versions.tabulator.get())
        override("rsup-progress", libs.versions.rsup.progress.get())
        override("lz-string", libs.versions.lz.string.get())
        override("marked", libs.versions.marked.get())
        override("sanitize-html", libs.versions.sanitize.html.get())
        override("postcss", libs.versions.postcss.asProvider().get())
        override("postcss-loader", libs.versions.postcss.loader.get())
        override("tailwindcss", libs.versions.tailwindcss.get())
        override("@tailwindcss/postcss", libs.versions.tailwindcss.get())
        override("cssnano", libs.versions.cssnano.get())
        override("mini-css-extract-plugin", libs.versions.mini.css.extract.plugin.get())
        override("motion", libs.versions.motion.get())
    }
}

nmcp {
    publishAggregation {
        project(":kilua")
        project(":modules:kilua-animation")
        project(":modules:kilua-annotations")
        project(":modules:kilua-bootstrap")
        project(":modules:kilua-bootstrap-icons")
        project(":modules:kilua-common-types")
        project(":modules:kilua-core-modules")
        project(":modules:kilua-dom")
        project(":modules:kilua-fontawesome")
        project(":modules:kilua-i18n")
        project(":modules:kilua-imask")
        project(":modules:kilua-jetpack")
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
//        project(":modules:kilua-ssr-server-micronaut")
        project(":modules:kilua-ssr-server-spring-boot")
        project(":modules:kilua-ssr-server-vertx")
        project(":modules:kilua-svg")
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
        project(":plugins:kilua-ksp-processor")
        username = findProperty("mavenCentralUsername")?.toString()
        password = findProperty("mavenCentralPassword")?.toString()
        publicationType = "USER_MANAGED"
    }
}

dependencies {
    dokka(project(":kilua"))
    dokka(project(":modules:kilua-animation"))
    dokka(project(":modules:kilua-annotations"))
    dokka(project(":modules:kilua-bootstrap"))
    dokka(project(":modules:kilua-bootstrap-icons"))
    dokka(project(":modules:kilua-common-types"))
    dokka(project(":modules:kilua-core-modules"))
    dokka(project(":modules:kilua-dom"))
    dokka(project(":modules:kilua-fontawesome"))
    dokka(project(":modules:kilua-i18n"))
    dokka(project(":modules:kilua-imask"))
    dokka(project(":modules:kilua-jetpack"))
    dokka(project(":modules:kilua-lazy-layouts"))
    dokka(project(":modules:kilua-marked"))
    dokka(project(":modules:kilua-rest"))
    dokka(project(":modules:kilua-routing"))
    dokka(project(":modules:kilua-rsup-progress"))
    dokka(project(":modules:kilua-sanitize-html"))
    dokka(project(":modules:kilua-select-remote"))
    dokka(project(":modules:kilua-splitjs"))
    dokka(project(":modules:kilua-ssr"))
    dokka(project(":modules:kilua-ssr-server"))
    dokka(project(":modules:kilua-ssr-server-javalin"))
    dokka(project(":modules:kilua-ssr-server-jooby"))
    dokka(project(":modules:kilua-ssr-server-ktor"))
//    dokka(project(":modules:kilua-ssr-server-micronaut"))
    dokka(project(":modules:kilua-ssr-server-spring-boot"))
    dokka(project(":modules:kilua-ssr-server-vertx"))
    dokka(project(":modules:kilua-svg"))
    dokka(project(":modules:kilua-tabulator"))
    dokka(project(":modules:kilua-tabulator-remote"))
    dokka(project(":modules:kilua-tailwindcss"))
    dokka(project(":modules:kilua-tempus-dominus"))
    dokka(project(":modules:kilua-testutils"))
    dokka(project(":modules:kilua-toastify"))
    dokka(project(":modules:kilua-tom-select"))
    dokka(project(":modules:kilua-tom-select-remote"))
    dokka(project(":modules:kilua-trix"))
}
