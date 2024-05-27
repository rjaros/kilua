/*
 * Copyright 2022 OpenSavvy
 *
 * This file is ported from the https://gitlab.com/opensavvy/ui/compose-lazy-html project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.kilua.panel

import androidx.compose.runtime.Composable
import dev.kilua.core.IComponent
import dev.kilua.html.Display
import dev.kilua.html.Div
import dev.kilua.html.FlexDirection
import dev.kilua.html.IDiv
import dev.kilua.html.div

/**
 * HTML-based lazy column implementation.
 *
 * Use this composable to display large amounts of elements on screen.
 * Items are only loaded as they appear on screen.
 *
 * Elements are loaded from top to bottom, in the same order as they are declared in [block].
 *
 * ### Example
 *
 * Lazily display a large list of users, unreferencing each from its by `UserId` by calling `requestUser()`:
 *
 * ```kotlin
 * @Composable
 * fun ShowUsers(users: List<UserId>) {
 *     LazyColumn {
 *         items(users) { userId ->
 *             var user by remember { mutableStateOf<User?>(null) }
 *
 *             LaunchedEffect(userId) {
 *                 user = requestUser(userId)
 *             }
 *
 *             user?.also { Show(it) }
 *         }
 *     }
 * }
 * ```
 */
@Composable
public fun IComponent.lazyColumn(
    setup: IDiv.() -> Unit = {},
    block: LazyDsl.() -> Unit,
) {
    div {
        display(Display.Flex)
        flexDirection(FlexDirection.Column)
        setup()
        lazyLinearLayout(block)
    }
}

/**
 * HTML-based lazy row implementation.
 *
 * Use this composable to display large amounts of elements on screen.
 * Items are only loaded as they appear on screen.
 *
 * Elements are loaded from side to side, respecting the user's reading direction, in the same order as they are declared in [block].
 */
@Composable
public fun IComponent.lazyRow(
    setup: IDiv.() -> Unit = {},
    block: LazyDsl.() -> Unit,
) {
    div {
        display(Display.Flex)
        flexDirection(FlexDirection.Row)
        setup()
        lazyLinearLayout(block)
    }
}
