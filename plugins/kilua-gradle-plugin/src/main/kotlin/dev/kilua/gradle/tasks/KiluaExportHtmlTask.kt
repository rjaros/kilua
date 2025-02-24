package dev.kilua.gradle.tasks

import dev.kilua.gradle.KiluaPlugin
import dev.kilua.gradle.readContent
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated
import java.io.File
import java.net.URI

public abstract class KiluaExportHtmlTask : DefaultTask(), KiluaTask {

    @get:Optional
    @get:Input
    public abstract val exportLanguage: Property<String>

    @get:Optional
    @get:Input
    public abstract val exportServerParameters: ListProperty<String>

    @get:Optional
    @get:Input
    public abstract val exportPages: ListProperty<String>

    @get:InputFile
    public abstract val applicationJar: RegularFileProperty

    @get:OutputDirectory
    public abstract val exportDirectory: DirectoryProperty

    init {
        group = KiluaPlugin.KILUA_TASK_GROUP
        description = "Export all declared SSR application pages as static HTML files"
    }

    @TaskAction
    public fun generate() {
        val javaHome = File(System.getProperty("java.home")).invariantSeparatorsPath
        val processParams = buildList<String> {
            add("$javaHome/bin/java")
            add("-jar")
            add(applicationJar.get().asFile.canonicalPath)
            exportServerParameters.orNull?.forEach {
                add(it)
            }
        }.toTypedArray()
        val processBuilder = ProcessBuilder(*processParams)
        processBuilder.redirectErrorStream(true)
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT)
        val process = processBuilder.start()
        Thread.sleep(5000)
        println("Exporting pages ...")
        try {
            if (!process.isAlive) error("The SSR application failed to run (exit code: ${process.exitValue()})")
            val headers = if (exportLanguage.isPresent) {
                mapOf("Accept-Language" to exportLanguage.get())
            } else null
            if (exportPages.isPresent) {
                exportPages.get().filter { it.startsWith("/") }.forEach { page ->
                    val content = URI("http://localhost:8080${page}").toURL().readContent(headers)
                    if (content != null) {
                        val fileName = if (page.endsWith("/")) {
                            page + "index.html"
                        } else {
                            "$page.html"
                        }
                        println("Exporting $fileName")
                        val destinationFile = exportDirectory.file(fileName.drop(1)).get().asFile
                        destinationFile.ensureParentDirsCreated()
                        destinationFile.writeText(content)
                    }
                }
            } else {
                URI("http://localhost:8080/").toURL().readContent(headers)?.let { index ->
                    println("Exporting /index.html")
                    exportDirectory.file("index.html").get().asFile.writeText(index)
                }
            }
        } finally {
            process.destroy()
        }
        println("Export finished")
    }
}
