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

import app.softwork.routingcompose.Router
import dev.kilua.asDeferred
import dev.kilua.externals.JSON
import dev.kilua.externals.console
import dev.kilua.externals.get
import dev.kilua.externals.keys
import dev.kilua.externals.set
import dev.kilua.html.Color
import dev.kilua.progress.Progress
import dev.kilua.progress.ProgressOptions
import dev.kilua.rest.RemoteRequestException
import dev.kilua.ssr.getSsrState
import dev.kilua.utils.isDom
import dev.kilua.utils.toList
import dev.kilua.utils.unsafeCast
import io.realworld.model.Article
import io.realworld.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import dev.kilua.dom.JsAny
import dev.kilua.dom.JsString
import dev.kilua.dom.localStorage
import dev.kilua.dom.toJsString

const val JWT_TOKEN = "jwtToken"

class ConduitManager : TokenProvider {

    private val appScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    private val json = Json {
        prettyPrint = true
    }

    private val _state = MutableStateFlow(getSsrState<ConduitState>(json)?.copy(fromSsr = true) ?: ConduitState())
    val state = _state.asStateFlow()

    private val api = Api(this)
    private val progress = Progress(ProgressOptions(height = 10, color = Color.Lightgreen))
    private var progressCount = 0

    fun processAction(action: ConduitAction) {
        _state.update { conduitReducer(it, action) }
    }

    fun initialize() {
        if (getJwtToken() != null) {
            appScope.launch {
                try {
                    val user = api.user()
                    processAction(ConduitAction.Login(user))
                    saveJwtToken(user.token!!)
                    processAction(ConduitAction.AppLoaded)
                } catch (e: Exception) {
                    console.log("Invalid JWT Token")
                    deleteJwtToken()
                    processAction(ConduitAction.AppLoaded)
                }
            }
        } else {
            processAction(ConduitAction.AppLoaded)
        }
    }

    fun CoroutineScope.withProgress(block: suspend () -> Unit): Job {
        progress.start()
        progressCount++
        return launch {
            try {
                block()
                progressCount--
                if (progressCount <= 0) progress.end()
            } catch (e: Exception) {
                progressCount--
                if (progressCount <= 0) progress.end()
                throw e
            }
        }
    }

    fun redirect(view: View) {
        Router.global.navigate(view.url)
    }

    fun loginPage() {
        processAction(ConduitAction.LoginPage)
    }

    fun login(email: String?, password: String?) {
        appScope.withProgress {
            try {
                val user = api.login(email, password)
                processAction(ConduitAction.Login(user))
                saveJwtToken(user.token!!)
                Router.global.navigate(View.HOME.url)
            } catch (e: RemoteRequestException) {
                processAction(
                    ConduitAction.LoginError(
                        parseErrors(
                            e.response?.text()?.asDeferred()?.await()?.toString()
                        )
                    )
                )
            }
        }
    }

    fun settingsPage() {
        processAction(ConduitAction.SettingsPage)
    }

    fun settings(image: String?, username: String?, bio: String?, email: String?, password: String?) {
        appScope.withProgress {
            try {
                val user = api.settings(image, username, bio, email, password)
                processAction(ConduitAction.Login(user))
                saveJwtToken(user.token!!)
                Router.global.navigate("${View.PROFILE.url}/${user.username}")
            } catch (e: RemoteRequestException) {
                processAction(
                    ConduitAction.SettingsError(
                        parseErrors(
                            e.response?.text()?.asDeferred()?.await()?.toString()
                        )
                    )
                )
            }
        }
    }

    fun registerPage() {
        processAction(ConduitAction.RegisterPage)
    }

    fun register(username: String?, email: String?, password: String?) {
        appScope.withProgress {
            try {
                val user = api.register(username, email, password)
                processAction(ConduitAction.Login(user))
                saveJwtToken(user.token!!)
                Router.global.navigate(View.HOME.url)
            } catch (e: RemoteRequestException) {
                processAction(
                    ConduitAction.RegisterError(
                        username,
                        email,
                        parseErrors(e.response?.text()?.asDeferred()?.await()?.toString())
                    )
                )
            }
        }
    }

    fun logout() {
        deleteJwtToken()
        processAction(ConduitAction.Logout)
        Router.global.navigate(View.HOME.url)
    }

    fun homePage(done: () -> Unit) {
        processAction(ConduitAction.HomePage)
        if (state.value.user != null) {
            selectFeed(FeedType.USER, done = done)
        } else {
            selectFeed(FeedType.GLOBAL, done = done)
        }
        loadTags()
    }

    fun selectFeed(feedType: FeedType, selectedTag: String? = null, profile: User? = null, done: () -> Unit = {}) {
        processAction(ConduitAction.SelectFeed(feedType, selectedTag, profile))
        loadArticles(done)
    }

    fun selectPage(page: Int) {
        processAction(ConduitAction.SelectPage(page))
        loadArticles()
    }

    fun showArticle(slug: String, done: () -> Unit) {
        if (state.value.article?.slug != slug) {
            processAction(ConduitAction.ClearArticle)
        }
        appScope.withProgress {
            try {
                val article = appScope.async { api.article(slug) }
                val articleComments = appScope.async { api.articleComments(slug) }
                processAction(ConduitAction.ShowArticle(article.await()))
                processAction(ConduitAction.ShowArticleCommets(articleComments.await()))
                processAction(ConduitAction.ArticlePage)
                done()
            } catch (e: Exception) {
                console.log(e.message)
                done()
            }
        }
    }

    fun articleComment(slug: String, comment: String?) {
        appScope.withProgress {
            try {
                val newComment = api.articleComment(slug, comment)
                processAction(ConduitAction.AddComment(newComment))
            } catch (e: RemoteRequestException) {
                console.log(e.message)
            }
        }
    }

    fun articleCommentDelete(slug: String, id: Int) {
        appScope.withProgress {
            try {
                api.articleCommentDelete(slug, id)
                processAction(ConduitAction.DeleteComment(id))
            } catch (e: RemoteRequestException) {
                console.log(e.message)
            }
        }
    }

    fun toggleFavoriteArticle(article: Article) {
        if (state.value.user != null) {
            appScope.withProgress {
                try {
                    val articleUpdated = api.articleFavorite(article.slug!!, !article.favorited)
                    processAction(ConduitAction.ArticleUpdated(articleUpdated))
                } catch (e: RemoteRequestException) {
                    console.log(e.message)
                }
            }
        } else {
            Router.global.navigate(View.LOGIN.url)
        }
    }

    fun showProfile(username: String, favorites: Boolean, done: () -> Unit) {
        val feedType = if (favorites) FeedType.PROFILE_FAVORITED else FeedType.PROFILE
        if (state.value.profile?.username != username) {
            processAction(ConduitAction.SelectFeed(feedType, null, null))
        }
        appScope.withProgress {
            try {
                val user = api.profile(username)
                selectFeed(feedType, null, user, done)
                processAction(ConduitAction.ProfilePage(feedType))
            } catch (e: Exception) {
                console.log(e.message)
                done()
            }
        }
    }

    fun toggleProfileFollow(user: User) {
        if (state.value.user != null) {
            appScope.withProgress {
                try {
                    val changedUser = api.profileFollow(user.username!!, !user.following!!)
                    processAction(ConduitAction.ProfileFollowChanged(changedUser))
                } catch (e: RemoteRequestException) {
                    console.log(e.message)
                }
            }
        } else {
            Router.global.navigate(View.LOGIN.url)
        }
    }

    private fun loadArticles(done: () -> Unit = {}) {
        if (state.value.articles == null) {
            processAction(ConduitAction.ArticlesLoading)
        }
        appScope.withProgress {
            try {
                val state = state.value
                val limit = state.pageSize
                val offset = state.selectedPage * limit
                val articleDto = when (state.feedType) {
                    FeedType.USER -> api.feed(offset, limit)
                    FeedType.GLOBAL -> api.articles(null, null, null, offset, limit)
                    FeedType.TAG -> api.articles(state.selectedTag, null, null, offset, limit)
                    FeedType.PROFILE -> api.articles(null, state.profile?.username, null, offset, limit)
                    FeedType.PROFILE_FAVORITED -> api.articles(null, null, state.profile?.username, offset, limit)
                }
                processAction(ConduitAction.ArticlesLoaded(articleDto.articles, articleDto.articlesCount))
                done()
            } catch (e: Exception) {
                console.log(e.message)
                done()
            }
        }
    }

    private fun loadTags() {
        if (state.value.tags == null) processAction(ConduitAction.TagsLoading)
        appScope.withProgress {
            try {
                val tags = api.tags()
                processAction(ConduitAction.TagsLoaded(tags))
            } catch (e: RemoteRequestException) {
                console.log(e.message)
            }
        }
    }

    fun editorPage(slug: String? = null) {
        if (slug == null) {
            processAction(ConduitAction.EditorPage(null))
        } else {
            appScope.withProgress {
                try {
                    val article = api.article(slug)
                    processAction(ConduitAction.EditorPage(article))
                } catch (e: RemoteRequestException) {
                    console.log(e.message)
                }
            }
        }
    }

    fun createArticle(title: String?, description: String?, body: String?, tags: String?) {
        appScope.withProgress {
            val tagList = tags?.split(" ")?.toList() ?: emptyList()
            try {
                val article = api.createArticle(title, description, body, tagList)
                Router.global.navigate(View.ARTICLE.url + "/" + article.slug)
            } catch (e: RemoteRequestException) {
                processAction(
                    ConduitAction.EditorError(
                        Article(
                            title = title,
                            description = description,
                            body = body,
                            tagList = tagList
                        ), parseErrors(e.response?.text()?.asDeferred()?.await()?.toString())
                    )
                )
            }
        }
    }

    fun updateArticle(slug: String, title: String?, description: String?, body: String?, tags: String?) {
        appScope.withProgress {
            val tagList = tags?.split(" ")?.toList() ?: emptyList()
            try {
                val article = api.updateArticle(slug, title, description, body, tagList)
                Router.global.navigate(View.ARTICLE.url + "/" + article.slug)
            } catch (e: RemoteRequestException) {
                processAction(
                    ConduitAction.EditorError(
                        state.value.editedArticle!!.copy(
                            title = title,
                            description = description,
                            body = body,
                            tagList = tagList
                        ), parseErrors(e.response?.text()?.asDeferred()?.await()?.toString())
                    )
                )
            }
        }
    }

    fun deleteArticle(slug: String) {
        appScope.withProgress {
            try {
                api.deleteArticle(slug)
                Router.global.navigate(View.HOME.url)
            } catch (e: RemoteRequestException) {
                console.log(e.message)
            }
        }
    }

    override fun getJwtToken(): String? {
        return if (isDom) localStorage[JWT_TOKEN]?.toString() else null
    }

    private fun saveJwtToken(token: String) {
        if (isDom) localStorage[JWT_TOKEN] = token.toJsString()
    }

    private fun deleteJwtToken() {
        if (isDom) localStorage.removeItem(JWT_TOKEN)
    }

    private fun parseErrors(message: String?): List<String> {
        return message?.let {
            try {
                val result = mutableListOf<String>()
                val json = JSON.parse<JsAny>(it)
                val errors = json["errors"]!!
                for (key in keys(errors)) {
                    val tab: dev.kilua.dom.JsArray<JsString> = errors[key]!!.unsafeCast()
                    result.addAll(tab.toList().map { "$key $it" })
                }
                result
            } catch (e: Exception) {
                listOf("unknown error")
            }
        } ?: listOf("unknown error")
    }

    fun serializeStateForSsr(): String {
        return json.encodeToString(state.value)
    }
}
