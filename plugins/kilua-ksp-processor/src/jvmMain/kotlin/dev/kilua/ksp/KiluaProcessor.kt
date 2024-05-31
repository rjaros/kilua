/*
 * Copyright (c) 2024 Robert Jaros
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.kilua.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.innerArguments
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import dev.kilua.annotations.SimpleHtmlComponent
import java.io.File

public data class ExceptionNameDetails(val packageName: String, val className: String)

public class KiluaProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger
) : SymbolProcessor {

    private var isInitialInvocation = true

    @OptIn(KspExperimental::class)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (!isInitialInvocation) {
            // A subsequent invocation is for processing generated files. We do not need to process these.
            return emptyList()
        }
        isInitialInvocation = false
        resolver.getSymbolsWithAnnotation(SimpleHtmlComponent::class.qualifiedName.orEmpty())
            .filterIsInstance<KSClassDeclaration>().filter(KSNode::validate)
            .filter { it.classKind == ClassKind.INTERFACE && it.simpleName.asString().startsWith("I") }
            .forEach { classDeclaration ->
                val tagName = classDeclaration.getAnnotationsByType(SimpleHtmlComponent::class).first().tagName
                val withText = classDeclaration.getAnnotationsByType(SimpleHtmlComponent::class).first().withText
                val parentType = classDeclaration.superTypes.firstOrNull()?.resolve()
                val parentTypeName = parentType?.declaration?.qualifiedName?.asString()
                if (parentTypeName == "dev.kilua.html.ITag") {
                    val typeArg = parentType.innerArguments.firstOrNull()?.type?.resolve()
                    if (typeArg != null) {
                        val interfaceName = classDeclaration.simpleName.asString()
                        val packageName = classDeclaration.packageName.asString()
                        val className = interfaceName.drop(1)
                        val functionName = className.replaceFirstChar { it.lowercase() }
                        val typeArgName = typeArg.declaration.simpleName.asString()
                        val dependencies =
                            classDeclaration.containingFile?.let { Dependencies(true, it) } ?: Dependencies(true)
                        codeGenerator.createNewFile(dependencies, packageName, className).writer().use {
                            when (codeGenerator.generatedFile.first().toString().sourceSetBelow("ksp")) {
                                "commonMain" -> {
                                    it.write(
                                        generateCommonCode(
                                            packageName,
                                            className,
                                            interfaceName,
                                            functionName,
                                            typeArgName,
                                            tagName,
                                            withText
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        return emptyList()
    }

    private fun String.sourceSetBelow(startDirectoryName: String): String =
        substringAfter("${File.separator}$startDirectoryName${File.separator}").substringBefore("${File.separator}kotlin${File.separator}")
            .substringAfterLast(File.separatorChar)

    private fun generateCommonCode(
        packageName: String,
        className: String,
        interfaceName: String,
        functionName: String,
        typeArgName: String,
        tagName: String,
        withText: Boolean
    ): String {
        return StringBuilder().apply {
            appendLine("//")
            appendLine("// GENERATED by Kilua")
            appendLine("//")
            appendLine("package $packageName")
            appendLine()
            appendLine("import androidx.compose.runtime.Composable")
            appendLine("import androidx.compose.runtime.remember")
            appendLine("import dev.kilua.compose.ComponentNode")
            appendLine("import dev.kilua.core.IComponent")
            appendLine("import dev.kilua.core.RenderConfig")
            appendLine("import dev.kilua.html.Tag")
            appendLine("import web.dom.$typeArgName")
            appendLine()
            appendLine("/**")
            appendLine(" * HTML $className component.")
            appendLine(" */")
            appendLine("public open class $className(className: String? = null, id: String? = null, renderConfig: RenderConfig = RenderConfig.Default) :")
            appendLine("    Tag<$typeArgName>(\"$tagName\", className, id, renderConfig = renderConfig), $interfaceName")
            appendLine()
            appendLine("/**")
            appendLine(" * Creates a [$className] component, returning a reference.")
            appendLine(" *")
            appendLine(" * @param className the CSS class name")
            appendLine(" * @param id the ID attribute of the component")
            appendLine(" * @param content the content of the component")
            appendLine(" * @return the [$className] component reference")
            appendLine(" */")
            appendLine("@Composable")
            appendLine("public fun IComponent.${functionName}Ref(className: String? = null, id: String? = null, content: @Composable $interfaceName.() -> Unit = {}): $className {")
            appendLine("    val component = remember { $className(className, id, renderConfig = renderConfig) }")
            appendLine("    ComponentNode(component, {")
            appendLine("        set(className) { updateProperty($className::className, it) }")
            appendLine("        set(id) { updateProperty($className::id, it) }")
            appendLine("    }, content)")
            appendLine("    return component")
            appendLine("}")
            appendLine()
            appendLine("/**")
            appendLine(" * Creates a [$className] component.")
            appendLine(" *")
            appendLine(" * @param className the CSS class name")
            appendLine(" * @param id the ID attribute of the component")
            appendLine(" * @param content the content of the component")
            appendLine(" */")
            appendLine("@Composable")
            appendLine("public fun IComponent.${functionName}(className: String? = null, id: String? = null, content: @Composable $interfaceName.() -> Unit = {}) {")
            appendLine("    val component = remember { $className(className, renderConfig = renderConfig) }")
            appendLine("    ComponentNode(component, {")
            appendLine("        set(className) { updateProperty($className::className, it) }")
            appendLine("        set(id) { updateProperty($className::id, it) }")
            appendLine("    }, content)")
            appendLine("}")
            appendLine()
            if (withText) {
                appendLine("/**")
                appendLine(" * Creates a [$className] component with text, returning a reference.")
                appendLine(" *")
                appendLine(" * @param text the text of the component")
                appendLine(" * @param className the CSS class name")
                appendLine(" * @param id the ID attribute of the component")
                appendLine(" * @param content the content of the component")
                appendLine(" * @return the [$className] component")
                appendLine(" */")
                appendLine("@Composable")
                appendLine("public fun IComponent.${functionName}tRef(text: String, className: String? = null, id: String? = null, content: @Composable $interfaceName.() -> Unit = {}): $className {")
                appendLine("    return ${className.replaceFirstChar { it.lowercase() }}Ref(className, id) {")
                appendLine("        +text")
                appendLine("        content()")
                appendLine("    }")
                appendLine("}")
                appendLine()
                appendLine("/**")
                appendLine(" * Creates a [$className] component with text.")
                appendLine(" *")
                appendLine(" * @param text the text of the component")
                appendLine(" * @param className the CSS class name")
                appendLine(" * @param id the ID attribute of the component")
                appendLine(" * @param content the content of the component")
                appendLine(" */")
                appendLine("@Composable")
                appendLine("public fun IComponent.${functionName}t(text: String, className: String? = null, id: String? = null, content: @Composable $interfaceName.() -> Unit = {}) {")
                appendLine("    ${functionName}(className, id) {")
                appendLine("        +text")
                appendLine("        content()")
                appendLine("    }")
                appendLine("}")
                appendLine()
            }
        }.toString()
    }
}
