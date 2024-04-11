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

package io.realworld.layout.shared

import androidx.compose.runtime.Composable
import dev.kilua.core.ComponentBase
import dev.kilua.html.div
import dev.kilua.html.i
import dev.kilua.html.img
import dev.kilua.html.li
import dev.kilua.html.nav
import dev.kilua.html.navLink
import dev.kilua.html.rawHtml
import dev.kilua.html.ul
import dev.kilua.html.unaryPlus
import io.realworld.ConduitState
import io.realworld.View

@Composable
fun ComponentBase.headerNav(state: ConduitState) {
    nav("navbar navbar-light") {
        div("container") {
            navLink(View.HOME.url, "conduit", className = "navbar-brand")
            if (!state.appLoading) {
                ul("nav navbar-nav pull-xs-right") {
                    li("nav-item") {
                        navLink(View.HOME.url, "Home", className = state.homeLinkClassName)
                    }
                    if (state.user == null) {
                        li("nav-item") {
                            navLink(View.LOGIN.url, "Sign in", className = state.loginLinkClassName)
                        }
                        li("nav-item") {
                            navLink(View.REGISTER.url, "Sign up", className = state.registerLinkClassName)
                        }
                    } else {
                        li("nav-item") {
                            navLink(View.EDITOR.url, className = state.editorLinkClassName) {
                                i("ion-compose")
                                rawHtml("&nbsp;")
                                +"New Post"
                            }
                        }
                        li("nav-item") {
                            navLink(View.SETTINGS.url, className = state.settingsLinkClassName) {
                                i("ion-gear-a")
                                rawHtml("&nbsp;")
                                +"Settings"
                            }
                        }
                        if (state.user.username != null) {
                            li("nav-item") {
                                navLink(
                                    "${View.PROFILE.url}/${state.user.username}",
                                    className = state.profileLinkClassName
                                ) {
                                    if (!state.user.image.isNullOrBlank()) {
                                        img(state.user.image, state.user.username, className = "user-pic")
                                    }
                                    +state.user.username
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
