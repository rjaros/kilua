import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.compose")
    kotlin("plugin.compose")
    kotlin("plugin.allopen") version libs.versions.kotlin.get()
    kotlin("kapt")
    alias(libs.plugins.shadow)
    alias(libs.plugins.micronaut.application)
    alias(libs.plugins.micronaut.aot)
    alias(libs.plugins.kilua.rpc)
    alias(libs.plugins.kilua)
}

val mainClassNameVal = "example.MainKt"

application {
    mainClass.set(mainClassNameVal)
}

allOpen {
    annotation("io.micronaut.aop.Around")
}

@OptIn(ExperimentalWasmDsl::class)
kotlin {
    jvmToolchain(17)
    jvm {
        withJava()
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass.set(mainClassNameVal)
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
                implementation(project(":modules:kilua-ssr-server-micronaut"))
                implementation(kotlin("reflect"))
                implementation("io.micronaut:micronaut-inject")
                implementation("io.micronaut.validation:micronaut-validation")
                implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
                implementation("io.micronaut:micronaut-runtime")
                implementation("io.micronaut:micronaut-http-server-netty")
                implementation("io.micronaut:micronaut-http-client")
                implementation("io.micronaut.session:micronaut-session")
                implementation("io.micronaut:micronaut-jackson-databind")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
                implementation("jakarta.validation:jakarta.validation-api")
                implementation("ch.qos.logback:logback-classic")
                implementation("org.yaml:snakeyaml")
            }
        }
    }
}

micronaut {
    runtime("netty")
    processing {
        incremental(true)
        annotations("example.*")
    }
    aot {
        optimizeServiceLoading.set(false)
        convertYamlToJava.set(false)
        precomputeOperations.set(true)
        cacheEnvironment.set(true)
        optimizeClassLoading.set(true)
        deduceEnvironment.set(true)
        optimizeNetty.set(true)
    }
}

tasks {
    withType<JavaExec> {
        jvmArgs("-XX:TieredStopAtLevel=1", "-Dcom.sun.management.jmxremote")
        if (gradle.startParameter.isContinuous) {
            systemProperties(
                mapOf(
                    "micronaut.io.watch.restart" to "true",
                    "micronaut.io.watch.enabled" to "true",
                    "micronaut.io.watch.paths" to "src/jvmMain"
                )
            )
        }
    }
}

kapt {
    arguments {
        arg("micronaut.processing.incremental", true)
        arg("micronaut.processing.annotations", "example.*")
        arg("micronaut.processing.group", "example")
        arg("micronaut.processing.module", "ssr-micronaut")
    }
}

dependencies {
    "kapt"(platform(libs.micronaut.platform))
    "kapt"("io.micronaut:micronaut-inject-java")
    "kapt"("io.micronaut.validation:micronaut-validation")
}

composeCompiler {
    targetKotlinPlatforms.set(
        KotlinPlatformType.values()
            .filterNot { it == KotlinPlatformType.jvm }
            .asIterable()
    )
}

tasks.getByName("inspectRuntimeClasspath") {
    dependsOn(
        "prepareComposeResourcesTaskForJvmMain",
        "copyNonXmlValueResourcesForJvmMain",
        "convertXmlValueResourcesForJvmMain"
    )
}
