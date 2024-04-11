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

sealed class ConduitAction {
    object AppLoaded : ConduitAction()
    object HomePage : ConduitAction()
    data class SelectFeed(
        val feedType: FeedType,
        val tag: String?,
        val profile: User?
    ) : ConduitAction()

    object ArticlesLoading : ConduitAction()
    data class ArticlesLoaded(val articles: List<Article>, val articlesCount: Int) : ConduitAction()
    data class SelectPage(val selectedPage: Int) : ConduitAction()

    object ArticlePage : ConduitAction()
    object ClearArticle : ConduitAction()
    data class ShowArticle(val article: Article) : ConduitAction()
    data class ShowArticleCommets(val articleComments: List<Comment>) : ConduitAction()
    data class ArticleUpdated(val article: Article) : ConduitAction()

    object TagsLoading : ConduitAction()
    data class TagsLoaded(val tags: List<String>) : ConduitAction()

    data class AddComment(val comment: Comment) : ConduitAction()
    data class DeleteComment(val id: Int) : ConduitAction()

    data class ProfilePage(val feedType: FeedType) : ConduitAction()
    data class ProfileFollowChanged(val user: User) : ConduitAction()

    object LoginPage : ConduitAction()
    data class Login(val user: User) : ConduitAction()
    data class LoginError(val errors: List<String>) : ConduitAction()

    object SettingsPage : ConduitAction()
    data class SettingsError(val errors: List<String>) : ConduitAction()

    object RegisterPage : ConduitAction()
    data class RegisterError(val username: String?, val email: String?, val errors: List<String>) : ConduitAction()

    object Logout : ConduitAction()

    data class EditorPage(val article: Article?) : ConduitAction()
    data class EditorError(
        val article: Article,
        val errors: List<String>
    ) : ConduitAction()
}
