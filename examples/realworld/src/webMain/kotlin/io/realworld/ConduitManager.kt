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
import dev.kilua.externals.JsArray
import dev.kilua.html.Color
import dev.kilua.progress.Progress
import dev.kilua.progress.ProgressOptions
import dev.kilua.rest.RemoteRequestException
import dev.kilua.routing.global
import dev.kilua.ssr.getSsrState
import dev.kilua.utils.isDom
import dev.kilua.utils.jsGet
import dev.kilua.utils.keys
import dev.kilua.utils.toList
import dev.kilua.utils.unsafeCast
import io.realworld.model.Article
import io.realworld.model.User
import js.json.parse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import web.console.console
import web.http.text
import web.storage.localStorage
import kotlin.Boolean
import kotlin.Exception
import kotlin.Int
import kotlin.String
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.emptyList
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.mutableListOf
import kotlin.collections.toList
import kotlin.js.JsAny
import kotlin.js.JsString
import kotlin.let
import kotlin.text.split

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

    suspend fun withProgress(block: suspend () -> Unit) {
        progress.start()
        progressCount++
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

    fun CoroutineScope.launchWithProgress(block: suspend () -> Unit): Job {
        return launch {
            withProgress(block)
        }
    }

    fun redirect(view: View) {
        Router.global.navigate(view.url)
    }

    fun loginPage() {
        processAction(ConduitAction.LoginPage)
    }

    fun login(email: String?, password: String?) {
        appScope.launchWithProgress {
            try {
                val user = api.login(email, password)
                processAction(ConduitAction.Login(user))
                saveJwtToken(user.token!!)
                Router.global.navigate(View.HOME.url)
            } catch (e: RemoteRequestException) {
                processAction(
                    ConduitAction.LoginError(
                        parseErrors(
                            e.response?.text()
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
        appScope.launchWithProgress {
            try {
                val user = api.settings(image, username, bio, email, password)
                processAction(ConduitAction.Login(user))
                saveJwtToken(user.token!!)
                Router.global.navigate("${View.PROFILE.url}/${user.username}")
            } catch (e: RemoteRequestException) {
                processAction(
                    ConduitAction.SettingsError(
                        parseErrors(
                            e.response?.text()
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
        appScope.launchWithProgress {
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
                        parseErrors(e.response?.text())
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

    suspend fun homePage() {
        processAction(ConduitAction.HomePage)
        if (state.value.user != null) {
            selectFeed(FeedType.USER)
        } else {
            selectFeed(FeedType.GLOBAL)
        }
        loadTags()
    }

    suspend fun selectFeed(
        feedType: FeedType,
        selectedTag: String? = null,
        profile: User? = null
    ) {
        processAction(ConduitAction.SelectFeed(feedType, selectedTag, profile))
        loadArticles()
    }

    suspend fun selectPage(page: Int) {
        processAction(ConduitAction.SelectPage(page))
        loadArticles()
    }

    suspend fun showArticle(slug: String) {
        if (state.value.article?.slug != slug) {
            processAction(ConduitAction.ClearArticle)
        }
        withProgress {
            try {
                val article = appScope.async { api.article(slug) }
                val articleComments = appScope.async { api.articleComments(slug) }
                processAction(ConduitAction.ShowArticle(article.await()))
                processAction(ConduitAction.ShowArticleCommets(articleComments.await()))
                processAction(ConduitAction.ArticlePage)
            } catch (e: Exception) {
                console.log(e.message)
            }
        }
    }

    fun articleComment(slug: String, comment: String?) {
        appScope.launchWithProgress {
            try {
                val newComment = api.articleComment(slug, comment)
                processAction(ConduitAction.AddComment(newComment))
            } catch (e: RemoteRequestException) {
                console.log(e.message)
            }
        }
    }

    fun articleCommentDelete(slug: String, id: Int) {
        appScope.launchWithProgress {
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
            appScope.launchWithProgress {
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

    suspend fun showProfile(username: String, favorites: Boolean) {
        val feedType = if (favorites) FeedType.PROFILE_FAVORITED else FeedType.PROFILE
        if (state.value.profile?.username != username) {
            processAction(ConduitAction.SelectFeed(feedType, null, null))
        }
        withProgress {
            try {
                val user = api.profile(username)
                selectFeed(feedType, null, user)
                processAction(ConduitAction.ProfilePage(feedType))
            } catch (e: Exception) {
                console.log(e.message)
            }
        }
    }

    fun toggleProfileFollow(user: User) {
        if (state.value.user != null) {
            appScope.launchWithProgress {
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

    private suspend fun loadArticles() {
        if (state.value.articles == null) {
            processAction(ConduitAction.ArticlesLoading)
        }
        withProgress {
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
            } catch (e: Exception) {
                console.log(e.message)
            }
        }
    }

    private fun loadTags() {
        if (state.value.tags == null) processAction(ConduitAction.TagsLoading)
        appScope.launchWithProgress {
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
            appScope.launchWithProgress {
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
        appScope.launchWithProgress {
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
                        ), parseErrors(e.response?.text())
                    )
                )
            }
        }
    }

    fun updateArticle(slug: String, title: String?, description: String?, body: String?, tags: String?) {
        appScope.launchWithProgress {
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
                        ), parseErrors(e.response?.text())
                    )
                )
            }
        }
    }

    fun deleteArticle(slug: String) {
        appScope.launchWithProgress {
            try {
                api.deleteArticle(slug)
                Router.global.navigate(View.HOME.url)
            } catch (e: RemoteRequestException) {
                console.log(e.message)
            }
        }
    }

    override fun getJwtToken(): String? {
        return if (isDom) localStorage.getItem(JWT_TOKEN) else null
    }

    private fun saveJwtToken(token: String) {
        if (isDom) localStorage.setItem(JWT_TOKEN, token)
    }

    private fun deleteJwtToken() {
        if (isDom) localStorage.removeItem(JWT_TOKEN)
    }

    private fun parseErrors(message: String?): List<String> {
        return message?.let {
            try {
                val result = mutableListOf<String>()
                val json = parse<JsAny>(it)
                val errors = json.jsGet("errors")!!
                for (key in keys(errors)) {
                    val tab: JsArray<JsString> = errors.jsGet(key)!!.unsafeCast()
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
