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
import dev.kilua.form.InputType
import dev.kilua.form.form
import dev.kilua.form.text.Text
import dev.kilua.form.text.textRef
import dev.kilua.html.ButtonType
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.fieldset
import dev.kilua.html.h1t
import dev.kilua.html.li
import dev.kilua.html.navLink
import dev.kilua.html.p
import dev.kilua.html.ul
import io.realworld.ConduitManager
import io.realworld.ConduitState
import io.realworld.View

@Composable
fun IComponent.registerPage(state: ConduitState, conduitManager: ConduitManager) {
    div("auth-page") {
        div("container page") {
            div("row") {
                div("col-md-6 offset-md-3 col-xs-12") {
                    h1t("Sign up", className = "text-xs-center")
                    p(className = "text-xs-center") {
                        navLink(View.LOGIN.url, "Have an account?")
                    }
                    if (!state.registerErrors.isNullOrEmpty()) {
                        ul("error-messages") {
                            state.registerErrors.forEach {
                                li {
                                    +it
                                }
                            }
                        }
                    }
                    lateinit var usernameInput: Text
                    lateinit var emailInput: Text
                    lateinit var passwordInput: Text
                    form {
                        fieldset(className = "form-group") {
                            usernameInput =
                                textRef(value = state.registerUserName, className = "form-control form-control-lg") {
                                    placeholder("Your Name")
                                }
                        }
                        fieldset(className = "form-group") {
                            emailInput =
                                textRef(
                                    state.registerEmail,
                                    type = InputType.Email,
                                    className = "form-control form-control-lg"
                                ) {
                                    placeholder("Email")
                                }
                        }
                        fieldset(className = "form-group") {
                            passwordInput =
                                textRef(type = InputType.Password, className = "form-control form-control-lg") {
                                    placeholder("Password")
                                }
                        }
                        button(
                            "Sign up",
                            type = ButtonType.Submit,
                            className = "btn btn-primary btn-lg pull-xs-right"
                        )
                        onEvent<dev.kilua.dom.api.events.Event>("submit") { ev ->
                            ev.preventDefault()
                            conduitManager.register(usernameInput.value, emailInput.value, passwordInput.value)
                        }
                    }
                }
            }
        }
    }
}
