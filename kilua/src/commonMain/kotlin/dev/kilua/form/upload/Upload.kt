/*
 * Copyright (c) 2023 Robert Jaros
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

package dev.kilua.form.upload

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.kilua.compose.ComponentNode
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.form.Input
import dev.kilua.form.InputType
import dev.kilua.form.KFilesFormControl
import dev.kilua.html.helpers.PropertyListBuilder
import dev.kilua.types.KFile
import dev.kilua.utils.getContent
import dev.kilua.utils.toKebabCase
import web.dom.asList
import web.files.File
import web.toInt

/**
 * File upload input capture mode values
 */
public enum class Capture {
    User,
    Environment,
    Capture;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * File upload input component.
 */
public open class Upload(
    multiple: Boolean = false,
    accept: List<String>? = null,
    capture: Capture? = null,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig()
) : Input<List<KFile>>(null, InputType.File, name, null, null, disabled, required, className, id, renderConfig = renderConfig),
    KFilesFormControl {

    /**
     * Temporary external value (used in tests)
     */
    protected var tmpValue: List<KFile>? = null

    /**
     * Internal native files map.
     */
    protected val nativeFiles: MutableMap<KFile, File> = mutableMapOf()

    /**
     * A list of selected files.
     */
    override var value: List<KFile>?
        get() = (getFiles() ?: tmpValue)?.ifEmpty { null }
        set(value) {
            if (value == null) clearFiles()
            tmpValue = value
            withStateFlowDelegate.updateStateFlow(value)
        }


    /**
     * Determines if multiple file upload is supported.
     */
    public var multiple: Boolean by updatingProperty(multiple) {
        element.multiple = it
    }

    /**
     * File types accepted by the file upload input.
     */
    public var accept: List<String>? by updatingProperty(accept) {
        if (!it.isNullOrEmpty()) {
            element.accept = it.joinToString(",")
        } else {
            element.removeAttribute("accept")
        }
    }

    /**
     * File upload input capture mode.
     */
    public var capture: Capture? by updatingProperty(capture) {
        setAttribute("capture", it?.toString())
    }

    /**
     * Only allow directories for selection.
     */
    public var webkitdirectory: Boolean? by updatingProperty {
        setAttribute("webkitdirectory", it?.toString())
    }

    init {
        @Suppress("LeakingThis")
        withStateFlowDelegate.formControl(this)
        if (renderConfig.isDom) {
            if (multiple) {
                element.multiple = multiple
            }
            if (!accept.isNullOrEmpty()) {
                element.accept = accept.joinToString(",")
            }
            if (capture != null) {
                element.setAttribute("capture", capture.toString())
            }
        }
    }

    /**
     * Get selected files.
     */
    protected open fun getFiles(): List<KFile>? {
        nativeFiles.clear()
        return if (renderConfig.isDom) {
            element.files?.let { files ->
                files.asList().map { file ->
                    val kfile = KFile(file.name, file.size.toInt())
                    nativeFiles[kfile] = file
                    kfile
                }
            }?.ifEmpty { null }
        } else null
    }

    /**
     * Clear selected files.
     */
    protected open fun clearFiles() {
        nativeFiles.clear()
        if (renderConfig.isDom) element.value = ""
    }

    override fun stringToValue(text: String?): List<KFile>? {
        return emptyList()
    }

    override fun getValueAsString(): String? {
        return value?.joinToString(", ") { it.name }
    }

    public override suspend fun getFilesWithContent(): List<KFile>? {
        return this.getFiles()?.map {
            val content = nativeFiles[it]?.getContent()
            it.copy(content = content)
        }
    }

    override fun buildHtmlPropertyList(propertyListBuilder: PropertyListBuilder) {
        super.buildHtmlPropertyList(propertyListBuilder)
        propertyListBuilder.add(
            ::multiple,
            ::accept,
            ::capture,
            ::webkitdirectory
        )
    }
}

/**
 * Creates [Upload] component.
 *
 * @param multiple whether multiple files can be selected
 * @param accept file types accepted by the file upload input
 * @param capture file upload input capture mode
 * @param name the name of the input
 * @param disabled whether the input is disabled
 * @param required whether the input is required
 * @param className the CSS class name
 * @param id the ID of the input
 * @param setup a function for setting up the component
 * @return a [Upload] component
 */
@Composable
public fun ComponentBase.upload(
    multiple: Boolean = false,
    accept: List<String>? = null,
    capture: Capture? = null,
    name: String? = null,
    disabled: Boolean? = null,
    required: Boolean? = null,
    className: String? = null,
    id: String? = null,
    setup: @Composable Upload.() -> Unit = {}
): Upload {
    val component =
        remember { Upload(multiple, accept, capture, name, disabled, required, className, id, renderConfig = renderConfig) }
    ComponentNode(component, {
        set(multiple) { updateProperty(Upload::multiple, it) }
        set(accept) { updateProperty(Upload::accept, it) }
        set(capture) { updateProperty(Upload::capture, it) }
        set(name) { updateProperty(Upload::name, it) }
        set(disabled) { updateProperty(Upload::disabled, it) }
        set(required) { updateProperty(Upload::required, it) }
        set(className) { updateProperty(Upload::className, it) }
        set(id) { updateProperty(Upload::id, it) }
    }, setup)
    return component
}
