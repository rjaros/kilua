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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot

/**
 * Immutable representation of a section.
 */
internal data class Section(
	/**
	 * An object that represents the identity of this section.
	 */
	val dependencies: Any,
	/**
	 * Loads an item from this section at a given index.
	 *
	 * This lambda returns `null` when the index is out of range for this section.
	 */
	val loader: (index: Int) -> LoadedItem?,
)

/**
 * Mutable incremental loader for a [Section].
 */
internal data class SectionLoader(private val section: Section) {
	private var loadedMax by mutableStateOf(-1)
	private var knownMax by mutableStateOf<Int?>(null)

	private val loaded = mutableStateMapOf<Int, LoadedItem>()

	val items get() = (0..loadedMax).asSequence()
		.mapNotNull { loaded[it] }

	val endReached get() = loadedMax == knownMax

	fun loadMoreAtEnd() = Snapshot.withMutableSnapshot {
		repeat(step) {
			val last = loadedMax
			val datum = section.loader(last + 1)

			if (datum != null) {
				// We just loaded an item
				loadedMax = last + 1
				loaded[last + 1] = datum
			} else {
				// we loaded everything
				knownMax = last
			}
		}
	}
}

private const val step = 20
