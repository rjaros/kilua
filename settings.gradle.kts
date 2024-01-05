@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "kilua"
include(":kilua")
include(":modules:kilua-common-types")
include(":modules:kilua-dom")
include(":modules:kilua-bootstrap")
include(":modules:kilua-bootstrap-icons")
include(":modules:kilua-fontawesome")
include(":modules:kilua-fontawesome")
include(":modules:kilua-rest")
include(":modules:kilua-splitjs")
include(":modules:kilua-tempus-dominus")
include(":modules:kilua-toastify")
include(":modules:kilua-trix")
include(":modules:kilua-testutils")
include(":kilua-assets")
include(":examples:bootstrap-form")
include(":examples:hello-world")
include(":examples:js-framework-benchmark")
include(":examples:playground")
