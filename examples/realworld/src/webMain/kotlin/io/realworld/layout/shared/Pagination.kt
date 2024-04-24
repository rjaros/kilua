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
import dev.kilua.html.li
import dev.kilua.html.link
import dev.kilua.html.nav
import dev.kilua.html.ul
import io.realworld.ConduitManager
import io.realworld.ConduitState

@Composable
fun IComponent.pagination(state: ConduitState, conduitManager: ConduitManager) {
    val limit = state.pageSize
    if (state.articlesCount > limit) {
        nav {
            ul("pagination") {
                val numberOfPages = ((state.articlesCount - 1) / limit) + 1
                for (page in 0 until numberOfPages) {
                    val className = if (page == state.selectedPage) "page-item active" else "page-item"
                    li(className) {
                        link("", "${page + 1}", className = "page-link"){
                            onClick { e ->
                                e.preventDefault()
                                conduitManager.selectPage(page)
                            }
                        }
                    }
                }
            }
        }
    }
}
