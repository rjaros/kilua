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

import dev.kilua.rest.HttpMethod
import dev.kilua.rest.RestClient
import dev.kilua.rest.call
import dev.kilua.rest.post
import dev.kilua.rest.requestDynamic
import dev.kilua.utils.StringPair
import io.realworld.model.Article
import io.realworld.model.ArticleDto
import io.realworld.model.ArticlesDto
import io.realworld.model.ArticlesQuery
import io.realworld.model.Comment
import io.realworld.model.CommentDto
import io.realworld.model.CommentsDto
import io.realworld.model.FeedQuery
import io.realworld.model.ProfileDto
import io.realworld.model.TagsDto
import io.realworld.model.User
import io.realworld.model.UserDto

const val API_URL = "https://api.realworld.io/api"

class Api(private val tokenProvider: TokenProvider) {

    private val restClient = RestClient()

    private fun authRequest(): List<StringPair> {
        return tokenProvider.getJwtToken()?.let {
            listOf("Authorization" to "Token $it")
        } ?: emptyList()
    }

    suspend fun login(email: String?, password: String?): User {
        return restClient.post<UserDto, UserDto>(
            "$API_URL/users/login",
            UserDto(
                User(
                    email = email,
                    password = password
                )
            )
        ).user
    }

    suspend fun register(username: String?, email: String?, password: String?): User {
        return restClient.post<UserDto, UserDto>(
            "$API_URL/users",
            UserDto(
                User(
                    username = username,
                    email = email,
                    password = password
                )
            )
        ).user
    }

    suspend fun user(): User {
        return restClient.call<UserDto>(
            "$API_URL/user"
        ) { headers = ::authRequest }.user
    }

    suspend fun settings(image: String?, username: String?, bio: String?, email: String?, password: String?): User {
        return restClient.call<UserDto, UserDto>(
            "$API_URL/user",
            UserDto(
                User(
                    image = image,
                    username = username,
                    bio = bio,
                    email = email,
                    password = password
                )
            )
        ) {
            method = HttpMethod.Put
            headers = ::authRequest
        }.user
    }

    suspend fun tags(): List<String> {
        return restClient.call<TagsDto>(
            "$API_URL/tags"
        ).tags
    }

    suspend fun articles(
        tag: String?,
        author: String?,
        favorited: String?,
        offset: Int = 0,
        limit: Int = 10
    ): ArticlesDto {
        return restClient.call<ArticlesDto, ArticlesQuery>(
            "$API_URL/articles",
            ArticlesQuery(tag, author, favorited, offset, limit)
        ) { headers = ::authRequest }
    }

    suspend fun feed(offset: Int = 0, limit: Int = 10): ArticlesDto {
        return restClient.call<ArticlesDto, FeedQuery>(
            "$API_URL/articles/feed",
            FeedQuery(offset, limit)
        ) { headers = ::authRequest }
    }

    suspend fun article(slug: String): Article {
        return restClient.call<ArticleDto>(
            "$API_URL/articles/$slug"
        ) { headers = ::authRequest }.article
    }

    suspend fun articleComments(slug: String): List<Comment> {
        return restClient.call<CommentsDto>(
            "$API_URL/articles/$slug/comments"
        ) { headers = ::authRequest }.comments
    }

    suspend fun articleComment(slug: String, comment: String?): Comment {
        return restClient.post<CommentDto, CommentDto>(
            "$API_URL/articles/$slug/comments",
            CommentDto(Comment(body = comment))
        ) { headers = ::authRequest }.comment
    }

    suspend fun articleCommentDelete(slug: String, id: Int) {
        restClient.requestDynamic(
            "$API_URL/articles/$slug/comments/$id"
        ) {
            method = HttpMethod.Delete
            headers = ::authRequest
        }
    }

    suspend fun articleFavorite(slug: String, favorite: Boolean = true): Article {
        return restClient.call<ArticleDto>(
            "$API_URL/articles/$slug/favorite"
        ) {
            method = if (favorite) HttpMethod.Post else HttpMethod.Delete
            headers = ::authRequest
        }.article
    }

    suspend fun profile(username: String): User {
        return restClient.call<ProfileDto>(
            "$API_URL/profiles/$username"
        ) { headers = ::authRequest }.profile
    }

    suspend fun profileFollow(username: String, follow: Boolean = true): User {
        return restClient.call<ProfileDto>(
            "$API_URL/profiles/$username/follow"
        ) {
            method = if (follow) HttpMethod.Post else HttpMethod.Delete
            headers = ::authRequest
        }.profile
    }

    suspend fun createArticle(title: String?, description: String?, body: String?, tags: List<String>): Article {
        return restClient.post<ArticleDto, ArticleDto>(
            "$API_URL/articles",
            ArticleDto(
                Article(
                    title = title,
                    description = description,
                    body = body,
                    tagList = tags
                )
            )
        ) { headers = ::authRequest }.article
    }

    suspend fun updateArticle(
        slug: String,
        title: String?,
        description: String?,
        body: String?,
        tags: List<String>
    ): Article {
        return restClient.call<ArticleDto, ArticleDto>(
            "$API_URL/articles/$slug",
            ArticleDto(
                Article(
                    title = title,
                    description = description,
                    body = body,
                    tagList = tags
                )
            )
        ) {
            method = HttpMethod.Put
            headers = ::authRequest
        }.article
    }

    suspend fun deleteArticle(slug: String) {
        restClient.requestDynamic(
            "$API_URL/articles/$slug",
        ) {
            method = HttpMethod.Delete
            headers = ::authRequest
        }
    }

}
