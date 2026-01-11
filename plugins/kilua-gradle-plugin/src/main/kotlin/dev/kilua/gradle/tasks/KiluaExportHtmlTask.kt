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
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.TextElement
import org.redundent.kotlin.xml.parse
import java.io.File
import java.net.URI

private const val DEFAULT_SLEEP_TIME = 5000L

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
        Thread.sleep(DEFAULT_SLEEP_TIME)
        println("Exporting pages ...")
        try {
            if (!process.isAlive) error("The SSR application failed to run (exit code: ${process.exitValue()})")
            val headers = if (exportLanguage.isPresent) {
                mapOf("Accept-Language" to exportLanguage.get())
            } else null
            val pagesList = mutableListOf<String>()
            try {
                val sitemap = parse("http://localhost:8080/sitemap.xml")
                sitemap.children.filterIsInstance<Node>().forEach {
                    val loc = it.first("loc")
                    if (loc.children.size == 1 && loc.children[0] is TextElement) {
                        val uri = URI((loc.children[0] as TextElement).text)
                        pagesList.add(uri.path)
                    }
                }
            } catch (e: Exception) {
                println("Sitemap parsing failed: ${e.message}")
            }
            if (exportPages.isPresent) {
                pagesList.addAll(exportPages.get().filter { it.startsWith("/") })
            }
            if (pagesList.isNotEmpty()) {
                pagesList.forEach { page ->
                    val content = URI("http://localhost:8080${page}").toURL().readContent(headers)
                    if (content != null) {
                        val isParentPage = pagesList.any { it != page && it.startsWith("$page/") }
                        val fileName = if (!isParentPage) {
                            if (page.endsWith("/")) {
                                page + "index.html"
                            } else {
                                "$page.html"
                            }
                        } else {
                            "$page/index.html"
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
