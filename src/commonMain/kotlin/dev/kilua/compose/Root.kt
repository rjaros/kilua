/*
 * Copyright (c) 2023-present Robert Jaros
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
import androidx.compose.runtime.ControlledComposition
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.Recomposer
import dev.kilua.core.ComponentBase
import dev.kilua.core.DefaultRenderConfig
import dev.kilua.core.HeadlessRenderConfig
import dev.kilua.core.RenderConfig
import dev.kilua.core.SafeDomFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.dom.clear
import org.w3c.dom.Element

public class Root(
    public val element: Element?,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    content: @Composable Root.() -> Unit = {}
) : ComponentBase(element, renderConfig) {

    // Not used
    override var visible: Boolean = true

    private val composition: Composition

    init {
        roots.add(this)
        composition = rootComposable(this, defaultMonotonicFrameClock, content)
    }

    override fun renderToStringBuilder(builder: StringBuilder) {
        builder.append("<${element?.tagName ?: "div"}")
        if (element?.id != null) {
            builder.append(" id=\"${element.id}\"")
        }
        builder.append(">")
        children.forEach {
            it.renderToStringBuilder(builder)
        }
        builder.append("</${element?.tagName ?: "div"}>")
    }

    public fun dispose() {
        composition.dispose()
        element?.clear()
    }

    public companion object {
        private val roots: MutableList<Root> = mutableListOf()

        public fun disposeAllRoots() {
            roots.forEach { it.dispose() }
            roots.clear()
        }
    }
}

internal expect val defaultMonotonicFrameClock: MonotonicFrameClock

internal fun rootComposable(
    root: Root,
    monotonicFrameClock: MonotonicFrameClock = defaultMonotonicFrameClock,
    content: @Composable Root.() -> Unit
): Composition {
    GlobalSnapshotManager.ensureStarted()

    val coroutineContext = monotonicFrameClock + PromiseDispatcher()
    val recomposer = Recomposer(coroutineContext)

    CoroutineScope(coroutineContext).launch(start = CoroutineStart.UNDISPATCHED) {
        recomposer.runRecomposeAndApplyChanges()
    }

    val composition = ControlledComposition(
        applier = ComponentApplier(root),
        parent = recomposer
    )

    composition.setContent @Composable {
        content(root)
    }
    return composition
}

public fun root(
    element: Element?,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    content: @Composable Root.() -> Unit = {}
): Root {
    return Root(element, renderConfig) {
        content()
    }
}

public fun root(
    id: String,
    renderConfig: RenderConfig = DefaultRenderConfig(),
    content: @Composable Root.() -> Unit = {}
): Root {
    return root(SafeDomFactory.getElementById(id, renderConfig), renderConfig, content)
}

public fun root(
    content: @Composable Root.() -> Unit = {}
): Root {
    return root(null, HeadlessRenderConfig(), content)
}
