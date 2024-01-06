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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.core.ComponentBase
import dev.kilua.externals.console
import dev.kilua.externals.obj
import dev.kilua.html.div
import dev.kilua.utils.toList
import web.document
import web.dom.observers.IntersectionObserver
import kotlin.random.Random
import kotlin.random.nextUInt

/**
 * Invisible component that calls [onVisible] when it appears on screen.
 */
@Composable
internal fun ComponentBase.visibilityDetector(onVisible: () -> Unit) {
    val id = remember { "visibility-observer-" + Random.nextUInt() }
    var hit by remember { mutableStateOf(0) }

    DisposableEffect(onVisible, hit) {
        val div = document.getElementById(id) ?: run {
            console.log("Lazy Compose: could not find the div with identifier $id, the lazy elements are broken")
            return@DisposableEffect onDispose { /* Nothing to do */ }
        }

        val observer = IntersectionObserver(
            callback = { entries, _ ->
                if (entries.toList().any { it.isIntersecting }) {
                    onVisible()
                    hit++
                }
            }, obj {}
        )

        observer.observe(div)

        onDispose {
            observer.disconnect()
        }
    }

    div {
        this.id = id
    }
}
