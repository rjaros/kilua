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
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.h1t
import dev.kilua.html.img
import dev.kilua.html.li
import dev.kilua.html.navLink
import dev.kilua.html.pt
import dev.kilua.html.span
import dev.kilua.html.spant
import dev.kilua.html.ul
import dev.kilua.html.unaryPlus
import dev.kilua.utils.rem
import io.realworld.ConduitManager
import io.realworld.View
import io.realworld.externals.Date
import io.realworld.externals.format
import io.realworld.model.Article

@Composable
fun ComponentBase.articlePreview(article: Article, conduitManager: ConduitManager) {
    div("article-preview") {
        div("article-meta") {
            val image =
                article.author?.image?.ifBlank { null } ?: "https://static.productionready.io/images/smiley-cyrus.jpg"
            navLink("${View.PROFILE.url}/${article.author?.username}", "") {
                img(image, article.author?.username)
            }
            div("info") {
                navLink("${View.PROFILE.url}/${article.author?.username}", article.author?.username ?: "", className = "author")
                span("date") {
                    +Date(article.createdAt).format()
                }
            }
            val btnStyle = if (article.favorited) "btn-primary" else "btn-outline-primary"
            button(
                " " + article.favoritesCount.toString(),
                "ion-heart",
                className = "pull-xs-right btn" % btnStyle % "btn-sm"
            ) {
                onClick {
                    conduitManager.toggleFavoriteArticle(article)
                }
            }
        }
        navLink("${View.ARTICLE.url}/${article.slug}", "", className = "preview-link") {
            h1t(article.title ?: "")
            pt(article.description ?: "")
            spant("Read more...")
            if (article.tagList.isNotEmpty()) {
                ul("tag-list") {
                    article.tagList.forEach {
                        li("tag-default tag-pill tag-outline") {
                            +" $it "
                        }
                    }
                }
            }
        }
    }
}

