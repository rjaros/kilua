plugins {
    kotlin("multiplatform") apply false
    `kotlin-dsl` apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.npm.publish) apply false
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
        override("lucide-static", libs.versions.lucide.get())
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
        override("lucide-static", libs.versions.lucide.get())
    }
}

// The examples are skipped by default so that `build` means the library. `-Pkilua.examples=true` brings
// them back for the runs that are meant to check them.
//
// The dependencies are removed rather than the task being disabled, because `enabled = false` only
// stops a task's own actions: everything it depends on still runs, so a disabled `build` left
// `assemble` and `check` - and the wasm optimiser behind them - to do the bulk of the work anyway.
//
// This is done once every project has been evaluated rather than through `configureEach`, because
// the lifecycle plugin sets `build`'s own dependencies while it is being applied - an action
// registered before that is quietly overwritten, which is what made the first attempt a no-op.
//
// The task container is walked rather than gated on the Kotlin plugin because some of the example
// subprojects apply kotlin("jvm") or a framework plugin instead of the multiplatform one, and
// their packaging is the expensive part.
val buildExamples = providers.gradleProperty("kilua.examples").map { it.toBoolean() }.getOrElse(false)

if (!buildExamples) {
    gradle.projectsEvaluated {
        subprojects.filter { it.path.startsWith(":examples") }.forEach { example ->
            example.tasks.findByName("build")?.setDependsOn(emptyList<Any>())
        }
    }
}

// The examples build the library from source. Their server backbone is a project dependency
// (`project(":modules:kilua-...")`), not a released coordinate, because a project dependency
// survives `Configuration.copy()` and the detached configuration a framework plugin builds from the
// runtime classpath. A Quarkus application model copies the base runtime classpath, and that copy
// does not carry a per-configuration dependency substitution - so a coordinate declared in an
// example would resolve from a repository there, which both fails for an unpublished version and
// silently pins the released artifact for a published one.
//
// The one coordinate still injected into the examples is the KSP processor, which the Gradle plugin
// adds as a string (`dev.kilua:kilua-ksp-processor:<version>`) to the `ksp*` configurations.
// Those are never copied, so a per-configuration substitution is enough. It is scoped to the
// examples rather than global so the modules and the nmcp aggregation keep resolving the
// coordinates they own. It is done from projectsEvaluated so the examples are already registered.
gradle.projectsEvaluated {
    val processor = rootProject.project(":plugins:kilua-ksp-processor")

    rootProject.subprojects.filter { it.path.startsWith(":examples") }.forEach { example ->
        example.configurations.configureEach {
            resolutionStrategy.dependencySubstitution {
                substitute(module("dev.kilua:kilua-ksp-processor")).using(project(processor.path))
            }
        }
    }
}

// The Gradle plugin is an included build now, so the root's `build`/`check`/`clean`/
// `publishToMavenLocal` no longer cover it: an included build's tasks are not matched by name.
// Delegate from the root lifecycle tasks so the everyday commands keep doing what they did while
// the plugin was a subproject.
val pluginTasks = mapOf(
    "build" to ":check",
    "check" to ":check",
    "clean" to ":clean",
    "publishToMavenLocal" to ":publishToMavenLocal",
)
tasks.matching { it.name in pluginTasks }.configureEach {
    dependsOn(gradle.includedBuild("kilua-gradle-plugin").task(pluginTasks.getValue(name)))
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
    nmcpAggregation(project(":modules:kilua-lucide"))
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
    nmcpAggregation(project(":modules:kilua-ssr-server-quarkus"))
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
    // The Gradle plugin now lives in an included build, so it is aggregated by coordinate rather
    // than by project. The version must be the snapshot-aware one, because the composite build only
    // substitutes a coordinate that matches the included build's own group:name:version.
    nmcpAggregation("dev.kilua:kilua-gradle-plugin:${version}")
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
    dokka(project(":modules:kilua-lucide"))
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
    dokka(project(":modules:kilua-ssr-server-quarkus"))
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
