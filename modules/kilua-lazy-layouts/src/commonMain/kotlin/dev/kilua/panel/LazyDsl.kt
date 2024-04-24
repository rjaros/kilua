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
import androidx.compose.runtime.key as composeKey

private val ident: (Any) -> Any = { it }

/**
 * DSL to declare items in a lazy container.
 *
 * Items will be loaded in the same order as functions are called on this object.
 */
public class LazyDsl internal constructor() {
    internal val sections = ArrayList<Section>()

    /**
     * Adds [block] as an item of this lazy container.
     *
     * @param key A locally-unique identifier for this item.
     * For more information, see [key][composeKey].
     */
    public fun item(
        key: Any? = null,
        block: @Composable IComponent.() -> Unit,
    ) {
        sections.add(Section(Pair(key, block)) {
            if (it == 0)
                LoadedItem(key, block)
            else
                null
        })
    }

    /**
     * Adds [count] elements to this lazy container.
     *
     * Each item is displayed by calling [block] and passing the item index (from `0` inclusive to `count` exclusive).
     *
     * @param key A function which generates a locally-unique identifier for an item from its index.
     * For more information, see [key][composeKey].
     */
    public fun items(
        count: Int,
        key: (index: Int) -> Any = ident,
        block: @Composable IComponent.(index: Int) -> Unit,
    ) {
        sections.add(Section(Triple(count, key, block)) {
            if (it in 0..<count)
                LoadedItem(key(it)) { block(it) }
            else
                null // we're outside the requested range, give up
        })
    }

    /**
     * Adds all the items of [items] to this lazy container.
     *
     * Each item is displayed by calling [block] and passing the item.
     *
     * @param key A function which generates a locally-unique identifier for an item.
     * By default, uses the item itself as its own key.
     * For more information, see [key][composeKey].
     */
    public fun <K : Any> items(
        items: Iterable<K>,
        key: (item: K) -> Any = ident,
        block: @Composable IComponent.(item: K) -> Unit,
    ) {
        val data = items.toList()
        sections.add(Section(Triple(data, key, block)) {
            if (it in data.indices) {
                val value = data[it]
                LoadedItem(key(value)) { block(value) }
            } else {
                null
            }
        })
    }
}
