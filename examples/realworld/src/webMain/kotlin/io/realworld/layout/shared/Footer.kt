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

package io.realworld.layout.shared

import androidx.compose.runtime.Composable
import dev.kilua.core.IComponent
import dev.kilua.html.div
import dev.kilua.html.footer
import dev.kilua.html.navLink
import dev.kilua.html.rawHtml
import dev.kilua.html.span
import io.realworld.View

@Composable
fun IComponent.footer() {
    footer {
        div("container") {
            navLink(View.HOME.url, "conduit", className = "logo-font")
            span("attribution") {
                rawHtml("An interactive learning project from <a href=\"https://thinkster.io\">Thinkster</a>. " +
                        "Code &amp; design licensed under MIT.")
            }
        }
    }
}
