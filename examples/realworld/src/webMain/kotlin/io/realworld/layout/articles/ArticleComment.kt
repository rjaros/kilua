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

package io.realworld.layout.articles

import androidx.compose.runtime.Composable
import dev.kilua.core.ComponentBase
import dev.kilua.html.div
import dev.kilua.html.i
import dev.kilua.html.img
import dev.kilua.html.navLink
import dev.kilua.html.pt
import dev.kilua.html.rawHtml
import dev.kilua.html.span
import dev.kilua.html.unaryPlus
import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.View
import io.realworld.externals.Date
import io.realworld.externals.format
import io.realworld.model.Article
import io.realworld.model.Comment

@Composable
fun ComponentBase.articleComment(
    state: ConduitState,
    comment: Comment,
    article: Article,
    conduitManager: ConduitManager
) {
    div("card") {
        div("card-block") {
            pt(comment.body ?: "", className = "card-text")
        }
        div("card-footer") {
            val imageSrc =
                comment.author?.image?.ifBlank { null } ?: "https://static.productionready.io/images/smiley-cyrus.jpg"
            navLink("${View.PROFILE.url}/${comment.author?.username}", "", className = "comment-author") {
                img(imageSrc, className = "comment-author-img")
            }
            rawHtml("&nbsp;")
            navLink(
                "${View.PROFILE.url}/${comment.author?.username}",
                comment.author?.username ?: "",
                className = "comment-author"
            )
            span("date-posted") {
                +Date(comment.createdAt ?: "").format()
            }
            if (state.user != null && state.user.username == comment.author?.username) {
                span("mod-options") {
                    i("ion-trash-a").onClick {
                        conduitManager.articleCommentDelete(article.slug!!, comment.id!!)
                    }
                }
            }
        }
    }
}
