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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.Snapshot
import dev.kilua.html.Div
import dev.kilua.html.div

@Composable
internal fun Div.lazyLinearLayout(
    dsl: LazyDsl.() -> Unit,
) {
    val sections by remember(dsl) {
        derivedStateOf {
            LazyDsl().apply(dsl)
                .sections
                .toList()
        }
    }

    val loaders = sections.map {
        remember(it.dependencies) {
            SectionLoader(it)
        }
    }

    var visibleEndIndex: Int? = null
    for ((i, loader) in loaders.withIndex()) {
        for (item in loader.items) {
            div {
                key(item.key) {
                    item.block(this)
                }
            }
        }
        if (visibleEndIndex == null && !loader.endReached) {
            visibleEndIndex = i
            visibilityDetector {
                Snapshot.withMutableSnapshot {
                    loader.loadMoreAtEnd()
                }
            }
        }
    }
}
