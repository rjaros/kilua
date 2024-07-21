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
package dev.kilua

import dev.kilua.compose.Root
import dev.kilua.html.style.StyleParams
import dev.kilua.i18n.DefaultLocale
import dev.kilua.utils.isDom
import dev.kilua.dom.document

/**
 * Base class for Kilua applications.
 */
public abstract class Application {

    /**
     * Starting point for an application.
     */
    public open fun start() {}

    /**
     * Starting point for an application with the state managed by Hot Module Replacement (HMR).
     * @param state Initial state for Hot Module Replacement (HMR).
     */
    public open fun start(state: String?) {
        start()
    }

    /**
     * Ending point for an application.
     * @return final state for Hot Module Replacement (HMR).
     */
    public open fun dispose(): String? {
        return null
    }
}

/**
 * Main function for creating Kilua applications.
 * Initialize Kilua modules.
 * @param builder application builder function
 * @param moduleInitializers optional module initializers
 */
public fun startApplication(
    builder: () -> Application,
    vararg moduleInitializers: ModuleInitializer
) {
    startApplication(builder, null, *moduleInitializers)
}

/**
 * Main function for creating Kilua applications with HMR support.
 * Initialize Kilua modules.
 * @param builder application builder function
 * @param hot HMR module
 * @param moduleInitializers optional module initializers
 */
public fun startApplication(
    builder: () -> Application,
    hot: Hot? = null,
    vararg moduleInitializers: ModuleInitializer
) {
    DefaultLocale() // Workaround for compiler bug

    moduleInitializers.forEach {
        it.initialize()
    }

    fun start(state: HmrState?): Application {
        val application = builder()
        application.start(state?.appState)
        return application
    }

    var application: Application? = null

    val state: HmrState? = hot?.let {
        it.accept()

        it.dispose { data ->
            Root.disposeAllRoots()
            StyleParams.disposeAllStyleParams()
            data.appState = application?.dispose()
            application = null
        }

        it.data
    }

    if (!isDom || document.body != null) {
        application = start(state)
    } else {
        application = null
        @Suppress("MoveLambdaOutsideParentheses", "RedundantSuppression")
        document.addEventListener("DOMContentLoaded", { application = start(state) })
    }
}
