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
import dev.kilua.core.ComponentBase
import dev.kilua.html.div
import dev.kilua.html.link
import dev.kilua.html.pt
import dev.kilua.html.unaryPlus
import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.FeedType

@Composable
fun ComponentBase.tags(state: ConduitState, conduitManager: ConduitManager) {
    div("sidebar") {
        pt("Popular Tags")
        if (state.tagsLoading) {
            div("post-preview") {
                +"Loading tags..."
            }
        } else if (!state.tags.isNullOrEmpty()) {
            div("tag-list") {
                state.tags.forEach { tag ->
                    link("", tag, className = "tag-pill tag-default") {
                        onClick {
                            it.preventDefault()
                            conduitManager.selectFeed(FeedType.TAG, selectedTag = tag)
                        }
                    }
                }
            }
        } else {
            div("post-preview") {
                +"No tags are here... yet."
            }
        }
    }
}
