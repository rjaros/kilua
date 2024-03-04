import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

fun KotlinMultiplatformExtension.compilerOptions() {
    targets.configureEach {
        compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xexpect-actual-classes")
                freeCompilerArgs.add("-Xdont-warn-on-error-suppression")
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

val kiluaProjectName = "Kilua"
val kiluaProjectDescription = "Experimental web framework for Kotlin/Wasm and Kotlin/JS"
val kiluaUrl = "https://github.com/rjaros/kilua"
val kiluaVcsUrl = "scm:git:git://github.com/rjaros/kilua.git"

fun MavenPom.defaultPom() {
    name.set(kiluaProjectName)
    description.set(kiluaProjectDescription)
    inceptionYear.set("2024")
    url.set(kiluaUrl)
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
        url.set(kiluaUrl)
        connection.set(kiluaVcsUrl)
        developerConnection.set(kiluaVcsUrl)
    }
}

fun Project.setupPublishing() {
    val isSnapshot = hasProperty("SNAPSHOT")
    extensions.getByType<PublishingExtension>().run {
        publications.withType<MavenPublication>().all {
            if (!isSnapshot) artifact(tasks["javadocJar"])
            pom {
                defaultPom()
            }
        }
    }
    extensions.getByType<SigningExtension>().run {
        if (!isSnapshot) {
            sign(extensions.getByType<PublishingExtension>().publications)
        }
    }
    // Workaround https://github.com/gradle/gradle/issues/26091
    tasks.withType<AbstractPublishToMaven>().configureEach {
        val signingTasks = tasks.withType<Sign>()
        mustRunAfter(signingTasks)
    }
}
