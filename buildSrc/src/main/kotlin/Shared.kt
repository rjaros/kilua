import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.plugins.signing.Sign
import org.gradle.plugins.signing.SigningExtension
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.tasks.DokkaBaseTask
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import java.net.URI

fun KotlinMultiplatformExtension.compilerOptions(withWasmMetadata: Boolean = true) {
    targets.configureEach { target ->
        val targetName = target.name
        target.compilations.configureEach { compilation ->
            compilation.compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                    freeCompilerArgs.add("-Xdont-warn-on-error-suppression")
                    optIn.add("kotlin.time.ExperimentalTime")
                    if (targetName == "wasmJs" || targetName == "js" || (withWasmMetadata && targetName == "metadata")) {
                        optIn.add("kotlin.js.ExperimentalWasmJsInterop")
                    }
                }
            }
        }
    }
}

fun KotlinMultiplatformExtension.kotlinJsTargets(withNode: Boolean = true) {
    js(IR) {
        useEsModules()
        browser {
            testTask {
                it.useKarma {
                    useChromeHeadless()
                }
            }
        }
        if (withNode) {
            nodejs {
            }
        }
        compilerOptions {
            target.set("es2015")
        }
    }
}

@OptIn(ExperimentalWasmDsl::class)
fun KotlinMultiplatformExtension.kotlinWasmTargets(withNode: Boolean = true) {
    wasmJs {
        useEsModules()
        browser {
            testTask {
                it.useKarma {
                    useChromeHeadless()
                }
            }
        }
        if (withNode) {
            nodejs {
            }
        }
        compilerOptions {
            target.set("es2015")
        }
    }
}

fun KotlinMultiplatformExtension.kotlinJvmTargets(target: String = "21") {
    jvmToolchain {
        it.languageVersion.set(JavaLanguageVersion.of(target))
    }
    jvm {
        compilations.configureEach { compilation ->
            compilation.compileTaskProvider.configure {
                compilerOptions {
                    freeCompilerArgs.add("-Xjsr305=strict")
                }
            }
        }
    }
}

fun KotlinJvmProjectExtension.kotlinJvmTargets(target: String = "21") {
    jvmToolchain {
        it.languageVersion.set(JavaLanguageVersion.of(target))
    }
}

fun Project.setupKsp() {
    dependencies.add("kspCommonMainMetadata", project(":plugins:kilua-ksp-processor"))

    kotlinExtension.sourceSets.getByName("commonMain") {
        it.kotlin.srcDir("${layout.buildDirectory.asFile.get()}/generated/ksp/metadata/commonMain/kotlin")
    }

    tasks.getByName("dokkaGenerateModuleHtml") {
        it.dependsOn("kspCommonMainKotlinMetadata")
    }

    tasks.getByName("dokkaGeneratePublicationHtml") {
        it.dependsOn("kspCommonMainKotlinMetadata")
    }

    tasks.getByName("sourcesJar") {
        it.dependsOn("kspCommonMainKotlinMetadata")
    }

    tasks.getByName("jsSourcesJar") {
        it.dependsOn("kspCommonMainKotlinMetadata")
    }

    tasks.getByName("wasmJsSourcesJar") {
        it.dependsOn("kspCommonMainKotlinMetadata")
    }

    tasks.withType(KotlinCompilationTask::class.java)
        .matching { it.name == "compileKotlinJs" || it.name == "compileKotlinWasmJs" }.configureEach {
            it.dependsOn("kspCommonMainKotlinMetadata")
        }
}

const val kiluaProjectName = "Kilua"
const val kiluaProjectDescription = "Composable web framework for Kotlin/Wasm and Kotlin/JS"
const val kiluaUrl = "https://github.com/rjaros/kilua"
const val kiluaVcsUrl = "scm:git:git://github.com/rjaros/kilua.git"

fun MavenPom.defaultPom() {
    name.set(kiluaProjectName)
    description.set(kiluaProjectDescription)
    inceptionYear.set("2024")
    url.set(kiluaUrl)
    licenses {
        it.license {
            it.name.set("MIT")
            it.url.set("https://opensource.org/licenses/MIT")
            it.distribution.set("https://opensource.org/licenses/MIT")
        }
    }
    developers {
        it.developer {
            it.id.set("rjaros")
            it.name.set("Robert Jaros")
            it.url.set("https://github.com/rjaros/")
        }
    }
    scm {
        it.url.set(kiluaUrl)
        it.connection.set(kiluaVcsUrl)
        it.developerConnection.set(kiluaVcsUrl)
    }
}

fun Project.setupPublishing() {
    val isSnapshot = hasProperty("SNAPSHOT")
    extensions.getByType(PublishingExtension::class.java).run {
        publications.withType(MavenPublication::class.java).all {
            if (!isSnapshot) it.artifact(this@setupPublishing.tasks.getByName("javadocJar"))
            it.pom {
                it.defaultPom()
            }
        }
    }
    extensions.getByType(SigningExtension::class.java).run {
        if (!isSnapshot) {
            sign(extensions.getByType(PublishingExtension::class.java).publications)
        }
    }
    // Workaround https://github.com/gradle/gradle/issues/26091
    tasks.withType(AbstractPublishToMaven::class.java).configureEach {
        val signingTasks = tasks.withType(Sign::class.java)
        it.mustRunAfter(signingTasks)
    }
}

fun Project.setupDokka(provider: TaskProvider<DokkaBaseTask>, path: String = "modules/") {
    tasks.register("javadocJar", Jar::class.java) {
        it.dependsOn(provider)
        it.from(provider.map { it.outputs })
        it.archiveClassifier.set("javadoc")
    }

    extensions.getByType(DokkaExtension::class.java).run {
        dokkaSourceSets.configureEach {
            it.sourceLink {
                it.localDirectory.set(projectDir.resolve("src"))
                it.remoteUrl.set(URI("https://github.com/rjaros/kilua/tree/main/$path${project.name}/src"))
                it.remoteLineSuffix.set("#L")
            }
        }
        dokkaGeneratorIsolation.set(ProcessIsolation {
            maxHeapSize.set("8g")
        })
    }
}
