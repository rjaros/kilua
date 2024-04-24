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
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.h4t
import dev.kilua.html.i
import dev.kilua.html.img
import dev.kilua.html.li
import dev.kilua.html.navLink
import dev.kilua.html.pt
import dev.kilua.html.rawHtml
import dev.kilua.html.ul
import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.FeedType
import io.realworld.View
import io.realworld.layout.shared.pagination

@Composable
fun IComponent.profilePage(state: ConduitState, conduitManager: ConduitManager) {
    val profile = state.profile
    if (profile != null) {
        div("profile-page") {
            div("user-info") {
                div("container") {
                    div("row") {
                        div("col-xs-12 col-md-10 offset-md-1") {
                            val imageSrc = profile.image?.ifBlank { null }
                                ?: "https://static.productionready.io/images/smiley-cyrus.jpg"
                            img(imageSrc, className = "user-img")
                            h4t(profile.username ?: "")
                            pt(profile.bio ?: "")
                            if (state.user?.username != profile.username) {
                                if (profile.following == true) {
                                    button(className = "btn action-btn btn-secondary btn-sm") {
                                        i("ion-plus-round")
                                        rawHtml("&nbsp;")
                                        +" Unfollow ${profile.username}"
                                        onClick {
                                            conduitManager.toggleProfileFollow(profile)
                                        }
                                    }
                                } else {
                                    button(className = "btn action-btn btn-outline-secondary btn-sm") {
                                        i("ion-plus-round")
                                        rawHtml("&nbsp;")
                                        +" Follow ${profile.username}"
                                        onClick {
                                            conduitManager.toggleProfileFollow(profile)
                                        }
                                    }
                                }
                            } else {
                                navLink(View.SETTINGS.url, className = "btn btn-sm btn-outline-secondary action-btn") {
                                    i("ion-gear-a")
                                    rawHtml("&nbsp;")
                                    +" Edit Profile Settings"
                                }
                            }
                        }
                    }
                }
            }
            div("container") {
                div("row") {
                    div("col-xs-12 col-md-10 offset-md-1") {
                        div("articles-toggle") {
                            ul("nav nav-pills outline-active") {
                                li("nav-item") {
                                    val className =
                                        if (state.feedType == FeedType.PROFILE) "nav-link active" else "nav-link"
                                    navLink(
                                        "${View.PROFILE.url}/${profile.username}",
                                        "My Articles",
                                        className = className
                                    )
                                }
                                li(className = "nav-item") {
                                    val className =
                                        if (state.feedType == FeedType.PROFILE_FAVORITED) "nav-link active" else "nav-link"
                                    navLink(
                                        "${View.PROFILE.url}/${profile.username}/favorites",
                                        "Favorited Articles",
                                        className = className
                                    )
                                }
                            }
                        }
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
                }
            }
        }
    }
}
