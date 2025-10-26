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

package io.realworld.layout.users

import androidx.compose.runtime.Composable
import dev.kilua.core.IComponent
import dev.kilua.form.form
import dev.kilua.form.text.Text
import dev.kilua.form.text.TextArea
import dev.kilua.form.text.textAreaRef
import dev.kilua.form.text.textRef
import dev.kilua.html.ButtonType
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.fieldset
import dev.kilua.html.li
import dev.kilua.html.ul
import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.View
import web.events.Event

@Composable
fun IComponent.editorPage(state: ConduitState, conduitManager: ConduitManager) {
    val isNewArticle = state.editedArticle?.slug == null
    val isMyArticle =
        state.editedArticle?.author?.username == null || state.editedArticle.author.username == state.user?.username
    div("editor-page") {
        div("container page") {
            div("row") {
                div("col-md-10 offset-md-1 col-xs-12") {
                    if (isNewArticle || isMyArticle) {
                        if (!state.editorErrors.isNullOrEmpty()) {
                            ul("error-messages") {
                                state.editorErrors.forEach {
                                    li {
                                        +it
                                    }
                                }
                            }
                        }
                        lateinit var titleInput: Text
                        lateinit var descriptionInput: Text
                        lateinit var bodyInput: TextArea
                        lateinit var tagsInput: Text
                        form {
                            fieldset {
                                fieldset(className = "form-group") {
                                    titleInput = textRef(
                                        value = state.editedArticle?.title,
                                        className = "form-control form-control-lg"
                                    ) {
                                        placeholder("Article Title")
                                    }
                                }
                                fieldset(className = "form-group") {
                                    descriptionInput = textRef(
                                        value = state.editedArticle?.description,
                                        className = "form-control"
                                    ) {
                                        placeholder("What's this article about?")
                                    }
                                }
                                fieldset(className = "form-group") {
                                    bodyInput = textAreaRef(
                                        value = state.editedArticle?.body,
                                        rows = 8,
                                        className = "form-control"
                                    ) {
                                        placeholder("Write your article (in markdown)")
                                    }
                                }
                                fieldset(className = "form-group") {
                                    tagsInput = textRef(
                                        value = state.editedArticle?.tagList?.joinToString(" "),
                                        className = "form-control"
                                    ) {
                                        placeholder("Enter tags")
                                    }
                                }
                                button(
                                    "Publish Article", type = ButtonType.Submit,
                                    className = "btn btn-primary btn-lg pull-xs-right"
                                )
                            }
                            onEvent<Event>("submit") { ev ->
                                ev.preventDefault()
                                if (isNewArticle) {
                                    conduitManager.createArticle(
                                        titleInput.value,
                                        descriptionInput.value,
                                        bodyInput.value,
                                        tagsInput.value
                                    )
                                } else {
                                    conduitManager.updateArticle(
                                        state.editedArticle.slug,
                                        titleInput.value,
                                        descriptionInput.value,
                                        bodyInput.value,
                                        tagsInput.value
                                    )
                                }
                            }
                        }
                    } else {
                        conduitManager.redirect(View.HOME)
                    }
                }
            }
        }
    }
}
