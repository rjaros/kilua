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

package io.realworld

import io.realworld.model.Article
import io.realworld.model.Comment
import io.realworld.model.User
import kotlinx.serialization.Serializable

@Serializable
enum class View(val url: String) {
    HOME("/"),
    ARTICLE("/article"),
    PROFILE("/profile"),
    LOGIN("/login"),
    REGISTER("/register"),
    EDITOR("/editor"),
    SETTINGS("/settings"),
}

@Serializable
enum class FeedType {
    USER,
    GLOBAL,
    TAG,
    PROFILE,
    PROFILE_FAVORITED
}

@Serializable
data class ConduitState(
    val fromSsr: Boolean = false,
    val appLoading: Boolean = true,
    val view: View = View.HOME,
    val user: User? = null,
    val articlesLoading: Boolean = false,
    val articles: List<Article>? = null,
    val articlesCount: Int = 0,
    val article: Article? = null,
    val articleComments: List<Comment>? = null,
    val selectedPage: Int = 0,
    val feedType: FeedType = FeedType.GLOBAL,
    val selectedTag: String? = null,
    val profile: User? = null,
    val tagsLoading: Boolean = false,
    val tags: List<String>? = null,
    val editorErrors: List<String>? = null,
    val editedArticle: Article? = null,
    val loginErrors: List<String>? = null,
    val settingsErrors: List<String>? = null,
    val registerErrors: List<String>? = null,
    val registerUserName: String? = null,
    val registerEmail: String? = null
) {
    val pageSize = when (feedType) {
        FeedType.USER, FeedType.GLOBAL, FeedType.TAG -> 10
        FeedType.PROFILE, FeedType.PROFILE_FAVORITED -> 5
    }

    private fun linkClassName(view: View) = if (this.view == view) "nav-link active" else "nav-link"

    val homeLinkClassName = linkClassName(View.HOME)
    val loginLinkClassName = linkClassName(View.LOGIN)
    val registerLinkClassName = linkClassName(View.REGISTER)
    val editorLinkClassName = linkClassName(View.EDITOR)
    val settingsLinkClassName = linkClassName(View.SETTINGS)
    val profileLinkClassName =
        if (view == View.PROFILE && profile?.username == user?.username) "nav-link active" else "nav-link"
}
