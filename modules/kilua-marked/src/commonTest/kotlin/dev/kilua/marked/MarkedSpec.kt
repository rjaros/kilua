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

import dev.kilua.test.DomSpec
import kotlin.test.Test

class MarkedSpec : DomSpec {

    @Test
    fun parse() = run {
        val markdown = """
            |# Title
            |
            |## Subtitle
            |
            |Paragraph
            |
            |* List item 1
            |* List item 2
            |
            |[Link](https://example.com)
            |
            |```kotlin
            |fun main() {
            |    println("Hello, world!")
            |}
            |```
        """.trimMargin()
        val html = parseMarkdown(markdown)
        assertEqualsHtml(
            """
            <h1>Title</h1>
            <h2>Subtitle</h2>
            <p>Paragraph</p>
            <ul>
            <li>List item 1</li>
            <li>List item 2</li>
            </ul>
            <p><a href="https://example.com">Link</a></p>
            <pre><code class="language-kotlin">fun main() {
                println(&quot;Hello, world!&quot;)
            }
            </code></pre>
            """.trimIndent(),
            html,
            "Should parse markdown text to HTML"
        )
    }

}
