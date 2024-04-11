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

package io.realworld.layout

import androidx.compose.runtime.Composable
import dev.kilua.core.ComponentBase
import dev.kilua.html.div
import dev.kilua.html.h1t
import dev.kilua.html.pt
import dev.kilua.html.unaryPlus
import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.layout.shared.pagination
import io.realworld.layout.shared.tags

@Composable
fun ComponentBase.homePage(state: ConduitState, conduitManager: ConduitManager) {
    div("home-page") {
        if (!state.appLoading && state.user == null) {
            div("banner") {
                div("container") {
                    h1t("conduit", className = "logo-font")
                    pt("A place to share your knowledge.")
                }
            }
        }
        div("container page") {
            div("row") {
                div("col-md-9") {
                    feedToggle(state, conduitManager)
                    if (state.articlesLoading) {
                        div("article-preview") {
                            +"Loading articles..."
                        }
                    } else if (!state.articles.isNullOrEmpty()) {
                        state.articles.forEach {
                            articlePreview(it, conduitManager)
                        }
                        pagination(state, conduitManager)
                    } else {
                        div("article-preview") {
                            +"No articles are here... yet."
                        }
                    }
                }
                div("col-md-3") {
                    tags(state, conduitManager)
                }
            }
        }
    }
}
