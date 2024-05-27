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
import dev.kilua.core.IComponent
import dev.kilua.form.form
import dev.kilua.form.text.TextArea
import dev.kilua.form.text.textAreaRef
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.h1t
import dev.kilua.html.hr
import dev.kilua.html.img
import dev.kilua.html.li
import dev.kilua.html.p
import dev.kilua.html.rawHtml
import dev.kilua.html.ul
import dev.kilua.marked.parseMarkdown
import dev.kilua.sanitize.sanitizeHtml
import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.View

@Composable
fun IComponent.article(state: ConduitState, conduitManager: ConduitManager) {
    if (state.article != null) {
        val article = state.article
        div("article-page") {
            div("banner") {
                div("container") {
                    h1t(article.title ?: "")
                    articleMeta(article, state, conduitManager)
                }
            }
            div("container page") {
                div("row article-content") {
                    div("col-md-12") {
                        div {
                            rawHtml(sanitizeHtml(parseMarkdown(article.body!!)))
                        }
                        if (article.tagList.isNotEmpty()) {
                            ul("tag-list") {
                                article.tagList.forEach {
                                    li("tag-default tag-pill tag-outline") {
                                        +it
                                    }
                                }
                            }
                        }
                    }
                }
                hr()
                div("article-actions") {
                    articleMeta(article, state, conduitManager)
                }
                div("row") {
                    div("col-xs-12 col-md-8 offset-md-2") {
                        if (state.user != null) {
                            form(className = "card comment-form") {
                                lateinit var commentInput: TextArea
                                div("card-block") {
                                    commentInput = textAreaRef(rows = 3, className = "form-control") {
                                        placeholder("Write a comment...")
                                    }
                                }
                                div("card-footer") {
                                    img(state.user.image?.ifBlank { null }, className = "comment-author-img")
                                    button("Post Comment", className = "btn btn-primary btn-sm") {
                                        onClick {
                                            conduitManager.articleComment(article.slug!!, commentInput.value)
                                            commentInput.value = null
                                        }
                                    }
                                }
                            }
                        } else {
                            p {
                                rawHtml("<a href=\"${View.LOGIN.url}\">Sign in</a> or <a href=\"${View.REGISTER.url}\">sign up</a> to add comments on this article.")
                            }
                        }
                        state.articleComments?.forEach {
                            articleComment(state, it, article, conduitManager)
                        }
                    }
                }
            }
        }
    }
}
