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

package dev.kilua.marked

import dev.kilua.externals.MarkedOptionsJs
import dev.kilua.externals.obj
import dev.kilua.externals.parse
import dev.kilua.externals.use
import web.JsAny

/**
 * Marked parser options.
 */
public data class MarkedOptions(
    val gfm: Boolean? = null,
    val breaks: Boolean? = null,
    val pedantic: Boolean? = null,
    val silent: Boolean? = null,
    val renderer: JsAny? = null,
    val tokenizer: JsAny? = null,
    val walkTokens: ((JsAny) -> Unit)? = null
)

internal fun MarkedOptions.toJs(): MarkedOptionsJs {
    val self = this
    return obj {
        if (self.gfm != null) this.gfm = self.gfm
        if (self.breaks != null) this.breaks = self.breaks
        if (self.pedantic != null) this.pedantic = self.pedantic
        if (self.silent != null) this.silent = self.silent
        if (self.renderer != null) this.renderer = self.renderer
        if (self.tokenizer != null) this.tokenizer = self.tokenizer
        if (self.walkTokens != null) this.walkTokens = self.walkTokens
    }
}

/**
 * Parse markdown text to HTML.
 *
 * @param text markdown text
 * @param options parser options
 * @return HTML text
 */
public fun parseMarkdown(text: String, options: MarkedOptions = MarkedOptions()): String {
    return parse(text, options.toJs())
}

/**
 * Use extension.
 *
 * @param ext extension objects
 */
public fun useExtension(vararg ext: JsAny) {
    return use(*ext)
}
