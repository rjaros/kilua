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

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.kilua.Application
import dev.kilua.CoreModule
import dev.kilua.compose.root
import dev.kilua.html.header
import dev.kilua.html.main
import dev.kilua.ssr.AsyncSsrRouter
import dev.kilua.ssr.routeAction
import dev.kilua.startApplication
import dev.kilua.utils.decodeURIComponent
import io.realworld.layout.articles.article
import io.realworld.layout.homePage
import io.realworld.layout.profilePage
import io.realworld.layout.shared.footer
import io.realworld.layout.shared.headerNav
import io.realworld.layout.users.editorPage
import io.realworld.layout.users.loginPage
import io.realworld.layout.users.registerPage
import io.realworld.layout.users.settingsPage

class App : Application() {

    val conduitManager = ConduitManager()

    override fun start() {

        conduitManager.initialize()

        root("root") {
            val state by conduitManager.state.collectAsState()

            AsyncSsrRouter(
                initPath = View.HOME.url,
                active = !state.appLoading,
                stateSerializer = { conduitManager.serializeStateForSsr() }
            ) {
                header {
                    headerNav(state)
                }
                main {
                    when (state.view) {
                        View.HOME -> homePage(state, conduitManager)
                        View.ARTICLE -> article(state, conduitManager)
                        View.PROFILE -> profilePage(state, conduitManager)
                        View.LOGIN -> loginPage(state, conduitManager)
                        View.REGISTER -> registerPage(state, conduitManager)
                        View.EDITOR -> editorPage(state, conduitManager)
                        View.SETTINGS -> settingsPage(state, conduitManager)
                    }
                }
                footer()

                route(View.HOME.url) {
                    routeAction {
                        conduitManager.homePage()
                    }
                }
                route(View.ARTICLE.url) {
                    string { slug ->
                        routeAction {
                            conduitManager.showArticle(slug)
                        }
                    }
                    noMatch {
                        routeAction {}
                    }
                }
                route(View.PROFILE.url) {
                    string {
                        val username = decodeURIComponent(it)
                        route("/favorites") {
                            routeAction {
                                conduitManager.showProfile(username, true)
                            }
                        }
                        noMatch {
                            routeAction {
                                conduitManager.showProfile(username, false)
                            }
                        }
                    }
                    noMatch {
                        routeAction {}
                    }
                }
                route(View.LOGIN.url) {
                    routeAction {
                        conduitManager.loginPage()
                    }
                }
                route(View.REGISTER.url) {
                    routeAction {
                        conduitManager.registerPage()
                    }
                }
                route(View.EDITOR.url) {
                    string { slug ->
                        routeAction(slug) {
                            conduitManager.editorPage(slug)
                        }
                    }
                    noMatch {
                        routeAction {
                            conduitManager.editorPage()
                        }
                    }
                }
                route(View.SETTINGS.url) {
                    routeAction {
                        conduitManager.settingsPage()
                    }
                }
                noMatch {
                    routeAction {}
                }
            }
        }
    }
}

fun main() {
    startApplication(::App, CoreModule)
}
