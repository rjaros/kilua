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
import dev.kilua.core.IComponent
import dev.kilua.html.div
import dev.kilua.html.li
import dev.kilua.html.link
import dev.kilua.html.ul
import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.FeedType

@Composable
fun IComponent.feedToggle(state: ConduitState, conduitManager: ConduitManager) {
    div("feed-toggle") {
        ul("nav nav-pills outline-active") {
            if (state.user != null) {
                li("nav-item") {
                    val className = if (state.feedType == FeedType.USER) "nav-link active" else "nav-link"
                    link("", "Your Feed", className = className).onClick { e ->
                        e.preventDefault()
                        conduitManager.selectFeed(FeedType.USER)
                    }
                }
            }
            li("nav-item") {
                val className = if (state.feedType == FeedType.GLOBAL) "nav-link active" else "nav-link"
                link("", "Global Feed", className = className).onClick { e ->
                    e.preventDefault()
                    conduitManager.selectFeed(FeedType.GLOBAL)
                }
            }
            if (state.selectedTag != null) {
                li("nav-item") {
                    link("", "#${state.selectedTag}", className = "nav-link active").onClick { e ->
                        e.preventDefault()
                    }
                }
            }
        }
    }
}
