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
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.i
import dev.kilua.html.img
import dev.kilua.html.navLink
import dev.kilua.html.px
import dev.kilua.html.rawHtml
import dev.kilua.html.span
import dev.kilua.html.spant
import dev.kilua.html.unaryPlus
import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.View
import io.realworld.externals.Date
import io.realworld.externals.format
import io.realworld.model.Article

@Composable
fun ComponentBase.articleMeta(article: Article, state: ConduitState, conduitManager: ConduitManager) {
    div("article-meta") {
        val image =
            article.author?.image?.ifBlank { null } ?: "https://static.productionready.io/images/smiley-cyrus.jpg"
        navLink("${View.PROFILE.url}/${article.author?.username}", "") {
            img(src = image)
        }
        div("info") {
            navLink(
                "${View.PROFILE.url}/${article.author?.username}",
                article.author?.username ?: "",
                className = "author"
            )
            span("date") {
                +Date(article.createdAt).format()
            }
        }
        if (article.author?.username != state.user?.username) {
            if (article.author?.following == true) {
                button(className = "btn btn-sm btn-secondary") {
                    i("ion-plus-round")
                    rawHtml("&nbsp;")
                    +" Unfollow ${article.author.username}"
                    onClick {
                        conduitManager.toggleProfileFollow(article.author)
                    }
                }
            } else {
                button(className = "btn btn-sm btn-outline-secondary") {
                    i("ion-plus-round")
                    rawHtml("&nbsp;")
                    +" Follow ${article.author?.username}"
                    onClick {
                        conduitManager.toggleProfileFollow(article.author!!)
                    }
                }
            }
            if (article.favorited) {
                button(className = "btn btn-sm") {
                    i("ion-heart")
                    rawHtml("&nbsp;")
                    +" Unfavorite post "
                    marginLeft = 5.px
                    spant("(${article.favoritesCount})", className = "counter")
                    onClick {
                        conduitManager.toggleFavoriteArticle(article)
                    }
                }
            } else {
                button(className = "btn btn-sm btn-outline-primary") {
                    i("ion-heart")
                    rawHtml("&nbsp;")
                    +" Favorite post "
                    marginLeft = 5.px
                    spant("(${article.favoritesCount})", className = "counter")
                    onClick {
                        conduitManager.toggleFavoriteArticle(article)
                    }
                }
            }
        } else {
            navLink("${View.EDITOR.url}/${article.slug}", className = "btn btn-outline-secondary btn-sm") {
                i("ion-edit")
                rawHtml("&nbsp;")
                +" Edit Article"
            }
            button(className = "btn btn-outline-danger btn-sm") {
                marginLeft = 5.px
                i("ion-trash-a")
                rawHtml("&nbsp;")
                +" Delete Article"
            }.onClick {
                conduitManager.deleteArticle(article.slug!!)
            }
        }
    }
}
