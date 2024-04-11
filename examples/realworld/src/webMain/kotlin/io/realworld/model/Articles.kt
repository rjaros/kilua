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

package io.realworld.model

import kotlinx.serialization.Serializable

@Serializable
data class ArticlesQuery(
    val tag: String?,
    val author: String?,
    val favorited: String?,
    val offset: Int = 0,
    val limit: Int = 10
)

@Serializable
data class FeedQuery(
    val offset: Int = 0,
    val limit: Int = 10
)

@Serializable
data class ArticleDto(val article: Article)

@Serializable
data class ArticlesDto(val articles: List<Article>, val articlesCount: Int)

@Serializable
data class Article(
    val slug: String? = null,
    val title: String? = null,
    val description: String? = null,
    val body: String? = null,
    val tagList: List<String> = listOf(),
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val favorited: Boolean = false,
    val favoritesCount: Int = 0,
    val author: User? = null
)

@Serializable
data class CommentDto(val comment: Comment)

@Serializable
data class CommentsDto(val comments: List<Comment>)

@Serializable
data class Comment(
    val id: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val body: String? = null,
    val author: User? = null
)

@Serializable
data class TagsDto(val tags: List<String>)
