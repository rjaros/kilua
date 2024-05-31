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


package dev.kilua.core

/**
 * Render configuration interface.
 */
public interface RenderConfig {
    public val isDom: Boolean

    public companion object {
        public val Default: RenderConfig = DefaultRenderConfig()
    }
}

/**
 * Default render configuration. Auto-detects if DOM rendering is supported.
 */
public class DefaultRenderConfig : RenderConfig {
    override val isDom: Boolean = dev.kilua.utils.isDom
}

/**
 * DOM render configuration. Throws exception if DOM rendering is not supported.
 */
public class DomRenderConfig : RenderConfig {
    init {
        require(dev.kilua.utils.isDom) { "DOM rendering is not supported in this environment" }
    }

    override val isDom: Boolean = true
}

/**
 * String render configuration. Disables DOM rendering.
 */
public class StringRenderConfig : RenderConfig {
    override val isDom: Boolean = false
}
