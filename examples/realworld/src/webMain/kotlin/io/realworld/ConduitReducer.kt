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

fun conduitReducer(state: ConduitState, action: ConduitAction): ConduitState = when (action) {
    is ConduitAction.AppLoaded -> {
        state.copy(appLoading = false)
    }
    is ConduitAction.HomePage -> {
        state.copy(view = View.HOME)
    }
    is ConduitAction.SelectFeed -> {
        state.copy(
            feedType = action.feedType,
            selectedTag = action.tag,
            profile = action.profile,
            selectedPage = 0
        )
    }
    is ConduitAction.ArticlesLoading -> {
        state.copy(articlesLoading = true)
    }
    is ConduitAction.ArticlesLoaded -> {
        state.copy(articlesLoading = false, articles = action.articles, articlesCount = action.articlesCount, fromSsr = false)
    }
    is ConduitAction.SelectPage -> {
        state.copy(selectedPage = action.selectedPage)
    }
    is ConduitAction.ArticlePage -> {
        state.copy(view = View.ARTICLE)
    }
    is ConduitAction.ClearArticle -> {
        state.copy(article = null)
    }
    is ConduitAction.ShowArticle -> {
        state.copy(article = action.article)
    }
    is ConduitAction.ShowArticleCommets -> {
        state.copy(articleComments = action.articleComments)
    }
    is ConduitAction.ArticleUpdated -> {
        if (state.view == View.ARTICLE) {
            state.copy(article = action.article)
        } else {
            state.copy(articles = state.articles?.map {
                if (it.slug == action.article.slug) {
                    action.article
                } else {
                    it
                }
            })
        }
    }
    is ConduitAction.TagsLoading -> {
        state.copy(tagsLoading = true)
    }
    is ConduitAction.TagsLoaded -> {
        state.copy(tagsLoading = false, tags = action.tags, fromSsr = false)
    }
    is ConduitAction.AddComment -> {
        state.copy(articleComments = listOf(action.comment) + (state.articleComments ?: listOf()))
    }
    is ConduitAction.DeleteComment -> {
        state.copy(articleComments = state.articleComments?.filterNot { it.id == action.id })
    }
    is ConduitAction.ProfilePage -> {
        state.copy(view = View.PROFILE, feedType = action.feedType)
    }
    is ConduitAction.ProfileFollowChanged -> {
        if (state.view == View.PROFILE) {
            state.copy(profile = action.user)
        } else {
            state.copy(article = state.article?.copy(author = action.user))
        }
    }
    is ConduitAction.LoginPage -> {
        state.copy(view = View.LOGIN, loginErrors = null)
    }
    is ConduitAction.Login -> {
        state.copy(user = action.user)
    }
    is ConduitAction.LoginError -> {
        state.copy(user = null, loginErrors = action.errors)
    }
    is ConduitAction.SettingsPage -> {
        state.copy(view = View.SETTINGS, settingsErrors = null)
    }
    is ConduitAction.SettingsError -> {
        state.copy(settingsErrors = action.errors)
    }
    is ConduitAction.RegisterPage -> {
        state.copy(view = View.REGISTER, registerErrors = null, registerUserName = null, registerEmail = null)
    }
    is ConduitAction.RegisterError -> {
        state.copy(registerErrors = action.errors, registerUserName = action.username, registerEmail = action.email)
    }
    is ConduitAction.Logout -> {
        ConduitState(appLoading = false)
    }
    is ConduitAction.EditorPage -> {
        state.copy(
            view = View.EDITOR,
            editorErrors = null,
            editedArticle = action.article
        )
    }
    is ConduitAction.EditorError -> {
        state.copy(
            editorErrors = action.errors,
            editedArticle = action.article
        )
    }
}
