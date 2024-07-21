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

package dev.kilua.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.mutableStateListOf
import dev.kilua.core.ComponentBase
import dev.kilua.core.IComponent
import dev.kilua.core.RenderConfig
import dev.kilua.core.SafeDomFactory
import dev.kilua.core.StringRenderConfig
import dev.kilua.utils.nativeListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import dev.kilua.dom.clear
import dev.kilua.dom.api.Element

internal expect val defaultMonotonicFrameClock: MonotonicFrameClock

/**
 * A root component for the component tree.
 * @param element the root HTML element
 * @param renderConfig the render configuration
 * @param content the composable content
 */
public class Root(
    public val element: Element,
    renderConfig: RenderConfig = RenderConfig.Default,
    content: @Composable IComponent.() -> Unit = {}
) : ComponentBase(element, renderConfig) {

    // Not used
    override var visible: Boolean = true

    @Composable
    public override fun visible(visible: Boolean): Unit = composableProperty("visible", {
        this.visible = true
    }) {
        this.visible = visible
    }

    private val composition: Composition

    init {
        roots.add(this)
        composition = rootComposable(this, defaultMonotonicFrameClock, content)
    }

    override fun renderToStringBuilder(builder: StringBuilder) {
        val tagName = if (renderConfig.isDom) element.tagName.lowercase() else "div"
        val id = if (renderConfig.isDom) element.id else null
        builder.append("<$tagName")
        if (id != null) {
            builder.append(" id=\"$id\"")
        }
        builder.append(">")
        children.forEach {
            it.renderToStringBuilder(builder)
        }
        builder.append("</$tagName>")
    }

    /**
     * Disposes the root component and associated composition.
     */
    public fun dispose() {
        composition.dispose()
        if (renderConfig.isDom) element.clear()
    }

    public companion object {

        internal val roots: MutableList<Root> = nativeListOf()

        internal var topLevelComposablesInitialized = false

        internal val topLevelComposables = mutableStateListOf<@Composable IComponent.() -> Unit>()

        /**
         * Disposes all root components and associated compositions.
         */
        public fun disposeAllRoots() {
            roots.forEach { it.dispose() }
            roots.clear()
        }

        public fun addTopLevelComposable(topLevel: @Composable IComponent.() -> Unit) {
            topLevelComposables.add(topLevel)
        }

        public fun removeTopLevelComposable(topLevel: @Composable IComponent.() -> Unit) {
            topLevelComposables.remove(topLevel)
        }

        internal fun initializeTopLevelComposablesRoot() {
            if (!topLevelComposablesInitialized) {
                SafeDomFactory.getElementById("kilua_top_level_composables")?.remove()
                SafeDomFactory.getFirstElementByTagName("body")?.let { body ->
                    val topLevelComposablesRoot = SafeDomFactory.createElement("div")
                    topLevelComposablesRoot.id = "kilua_top_level_composables"
                    body.appendChild(topLevelComposablesRoot)
                    NonDisposableRoot(topLevelComposablesRoot) {
                        topLevelComposables.forEach {
                            it()
                        }
                    }
                }
                topLevelComposablesInitialized = true
            }
        }
    }
}

/**
 * Initializes the composition for the root component.
 */
internal fun rootComposable(
    root: ComponentBase,
    monotonicFrameClock: MonotonicFrameClock,
    content: @Composable IComponent.() -> Unit
): Composition {
    GlobalSnapshotManager.ensureStarted()

    val coroutineContext = monotonicFrameClock + PromiseDispatcher()
    val recomposer = Recomposer(coroutineContext)

    CoroutineScope(coroutineContext).launch(start = CoroutineStart.UNDISPATCHED) {
        recomposer.runRecomposeAndApplyChanges()
    }

    val composition = Composition(
        applier = ComponentApplier(root),
        parent = recomposer
    )
    composition.setContent @Composable {
        content(root)
    }
    return composition
}

/**
 * Main entry-point for building component tree with composable functions.
 */
public fun root(
    element: Element,
    clearSsrContent: Boolean = true,
    renderConfig: RenderConfig = RenderConfig.Default,
    content: @Composable IComponent.() -> Unit = {}
): Root {
    Root.initializeTopLevelComposablesRoot()
    if (renderConfig.isDom && clearSsrContent) {
        // Clear SSR content before rendering
        element.clear()
    }
    return Root(element, renderConfig = renderConfig) {
        content()
    }
}

/**
 * Main entry-point for building component tree with composable functions.
 */
public fun root(
    id: String,
    renderConfig: RenderConfig = RenderConfig.Default,
    content: @Composable IComponent.() -> Unit = {}
): Root {
    val element = SafeDomFactory.getElementById(id) ?: SafeDomFactory.createElement("div")
    return root(element, true, renderConfig, content)
}

/**
 * Main entry-point for building component tree with composable functions using StringRender configuration.
 */
public fun root(
    content: @Composable IComponent.() -> Unit = {}
): Root {
    val renderConfig = StringRenderConfig()
    val element = SafeDomFactory.createElement("div")
    return root(element, false, renderConfig, content)
}
