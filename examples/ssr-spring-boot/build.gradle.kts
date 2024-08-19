import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("plugin.spring") version libs.versions.kotlin.get()
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.kilua.rpc)
    alias(libs.plugins.kilua)
}

extra["kotlin.version"] = libs.versions.kotlin.get()
extra["kotlin-coroutines.version"] = libs.versions.kotlinx.coroutines.get()

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    jvmToolchain(17)
    jvm {
        withJava()
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
    js(IR) {
        useEsModules()
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
                sourceMaps = false
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }
    wasmJs {
        useEsModules()
        browser {
            commonWebpackConfig {
                outputFileName = "main.bundle.js"
                sourceMaps = false
            }
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
            }
        }
        val webMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(project(":kilua"))
                implementation(project(":modules:kilua-bootstrap"))
                implementation(project(":modules:kilua-ssr"))
            }
        }
        val jsMain by getting {
            dependsOn(webMain)
        }
        val wasmJsMain by getting {
            dependsOn(webMain)
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("reflect"))
                implementation(project(":modules:kilua-ssr-server-spring-boot"))
                implementation("org.springframework.boot:spring-boot-starter")
                implementation("org.springframework.boot:spring-boot-devtools")
                implementation("org.springframework.boot:spring-boot-starter-webflux")
            }
        }
    }
}

composeCompiler {
    targetKotlinPlatforms.set(
        KotlinPlatformType.values()
            .filterNot { it == KotlinPlatformType.jvm }
            .asIterable()
    )
}
