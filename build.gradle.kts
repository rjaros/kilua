plugins {
    kotlin("multiplatform") apply false
    `kotlin-dsl` apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.npm.publish) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.nmcp) apply false
    alias(libs.plugins.nmcp.aggregation)
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
        override("leaflet", libs.versions.leaflet.get())
    }
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.wasm.npm.WasmNpmExtension>().apply {
        override("aaa-kilua-assets", libs.versions.npm.kilua.assets.get())
        override("zzz-kilua-assets", libs.versions.npm.kilua.assets.get())
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
        override("leaflet", libs.versions.leaflet.get())
    }
}

nmcpAggregation {
    centralPortal {
        username = findProperty("mavenCentralUsername")?.toString()
        password = findProperty("mavenCentralPassword")?.toString()
        publishingType = "USER_MANAGED"
        publicationName = "Kilua $version"
    }
    allowDuplicateProjectNames.set(true)
}

dependencies {
    nmcpAggregation(project(":kilua"))
    nmcpAggregation(project(":modules:kilua-animation"))
    nmcpAggregation(project(":modules:kilua-annotations"))
    nmcpAggregation(project(":modules:kilua-bootstrap"))
    nmcpAggregation(project(":modules:kilua-bootstrap-icons"))
    nmcpAggregation(project(":modules:kilua-common-types"))
    nmcpAggregation(project(":modules:kilua-core-modules"))
    nmcpAggregation(project(":modules:kilua-fontawesome"))
    nmcpAggregation(project(":modules:kilua-i18n"))
    nmcpAggregation(project(":modules:kilua-imask"))
    nmcpAggregation(project(":modules:kilua-jetpack"))
    nmcpAggregation(project(":modules:kilua-ktml"))
    nmcpAggregation(project(":modules:kilua-lazy-layouts"))
    nmcpAggregation(project(":modules:kilua-leaflet"))
    nmcpAggregation(project(":modules:kilua-marked"))
    nmcpAggregation(project(":modules:kilua-rest"))
    nmcpAggregation(project(":modules:kilua-routing"))
    nmcpAggregation(project(":modules:kilua-rsup-progress"))
    nmcpAggregation(project(":modules:kilua-sanitize-html"))
    nmcpAggregation(project(":modules:kilua-select-remote"))
    nmcpAggregation(project(":modules:kilua-splitjs"))
    nmcpAggregation(project(":modules:kilua-ssr"))
    nmcpAggregation(project(":modules:kilua-ssr-server"))
    nmcpAggregation(project(":modules:kilua-ssr-server-javalin"))
    nmcpAggregation(project(":modules:kilua-ssr-server-jooby"))
    nmcpAggregation(project(":modules:kilua-ssr-server-ktor"))
    nmcpAggregation(project(":modules:kilua-ssr-server-micronaut"))
    nmcpAggregation(project(":modules:kilua-ssr-server-spring-boot"))
    nmcpAggregation(project(":modules:kilua-ssr-server-vertx"))
    nmcpAggregation(project(":modules:kilua-svg"))
    nmcpAggregation(project(":modules:kilua-tabulator"))
    nmcpAggregation(project(":modules:kilua-tabulator-remote"))
    nmcpAggregation(project(":modules:kilua-tailwindcss"))
    nmcpAggregation(project(":modules:kilua-tempus-dominus"))
    nmcpAggregation(project(":modules:kilua-testutils"))
    nmcpAggregation(project(":modules:kilua-toastify"))
    nmcpAggregation(project(":modules:kilua-tom-select"))
    nmcpAggregation(project(":modules:kilua-tom-select-remote"))
    nmcpAggregation(project(":modules:kilua-trix"))
    nmcpAggregation(project(":plugins:kilua-gradle-plugin"))
    nmcpAggregation(project(":plugins:kilua-ksp-processor"))
    dokka(project(":kilua"))
    dokka(project(":modules:kilua-animation"))
    dokka(project(":modules:kilua-annotations"))
    dokka(project(":modules:kilua-bootstrap"))
    dokka(project(":modules:kilua-bootstrap-icons"))
    dokka(project(":modules:kilua-common-types"))
    dokka(project(":modules:kilua-core-modules"))
    dokka(project(":modules:kilua-fontawesome"))
    dokka(project(":modules:kilua-i18n"))
    dokka(project(":modules:kilua-imask"))
    dokka(project(":modules:kilua-jetpack"))
    dokka(project(":modules:kilua-ktml"))
    dokka(project(":modules:kilua-lazy-layouts"))
    dokka(project(":modules:kilua-leaflet"))
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
    dokka(project(":modules:kilua-ssr-server-micronaut"))
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
