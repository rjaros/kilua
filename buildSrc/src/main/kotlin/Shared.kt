import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

fun KotlinMultiplatformExtension.compilerOptions() {
    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
}

fun KotlinMultiplatformExtension.kotlinJsTargets(withNode: Boolean = true) {
    js(IR) {
        useEsModules()
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        if (withNode) {
            nodejs {
                testTask {
                    useMocha()
                }
            }
        }
    }
}

@OptIn(ExperimentalWasmDsl::class)
fun KotlinMultiplatformExtension.kotlinWasmTargets(withNode: Boolean = true) {
    wasmJs {
        useEsModules()
        browser {
            testTask {
                useKarma {
                    useChromeHeadlessWasmGc()
                }
            }
        }
        if (withNode) {
            nodejs {
                testTask {
                    useMocha()
                }
            }
        }
    }
}

fun KotlinMultiplatformExtension.kotlinJvmTargets(target: String = "17") {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(target))
    }
    jvm {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xjsr305=strict")
            }
        }
    }
}

@Suppress("UnstableApiUsage")
fun Project.setupPublishing(version: String) {
    extensions.getByType<MavenPublishBaseExtension>().run {
        if (!this@setupPublishing.hasProperty("SNAPSHOT")) {
            coordinates("dev.kilua", this@setupPublishing.name, version)
            signAllPublications()
        } else {
            coordinates("dev.kilua", this@setupPublishing.name, "$version-SNAPSHOT")
            configure(KotlinMultiplatform(
                javadocJar = JavadocJar.None()
            ))
        }
        pom {
            name.set("Kilua")
            description.set("Experimental web framework for Kotlin/Wasm and Kotlin/JS.")
            inceptionYear.set("2024")
            url.set("https://github.com/rjaros/kilua")
            licenses {
                license {
                    name.set("MIT")
                    url.set("https://opensource.org/licenses/MIT")
                    distribution.set("https://opensource.org/licenses/MIT")
                }
            }
            developers {
                developer {
                    id.set("rjaros")
                    name.set("Robert Jaros")
                    url.set("https://github.com/rjaros/")
                }
            }
            scm {
                url.set("https://github.com/rjaros/kilua")
                connection.set("scm:git:git://github.com/rjaros/kilua.git")
                developerConnection.set("scm:git:ssh://git@github.com/rjaros/kilua.git")
            }
        }
    }
}
