import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.tasks.bundling.Jar
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import java.net.URI

// A subset of the root buildSrc's Shared.kt, holding only what the Gradle plugin's own build needs.
// The plugin is an included build now, and an included build does not see the root buildSrc - nor
// does it inherit the root's group and version - so the few helpers it relies on are repeated here.
// Keep them in step with buildSrc/src/main/kotlin/Shared.kt.

fun KotlinJvmProjectExtension.kotlinJvmTargets(target: String = "25") {
    jvmToolchain {
        it.languageVersion.set(JavaLanguageVersion.of(target))
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

fun Project.setupDokka(path: String = "modules/") {
    tasks.register("javadocJar", Jar::class.java) {
        // javadocJar is empty, but it is required for publishing to Maven Central. Dokka generates the actual documentation.
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
