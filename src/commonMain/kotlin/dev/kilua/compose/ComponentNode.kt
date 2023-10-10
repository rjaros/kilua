/*
 * Copyright (c) 2023-present Robert Jaros
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

package dev.kilua.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.ExplicitGroupsComposable
import androidx.compose.runtime.SkippableUpdater
import androidx.compose.runtime.Updater
import androidx.compose.runtime.currentComposer

@Composable
@ExplicitGroupsComposable
public inline fun <C> ComponentNode(
    noinline factory: () -> C,
    componentInScope: C,
    update: @DisallowComposableCalls Updater<C>.() -> Unit,
    noinline skippableUpdate: @Composable SkippableUpdater<C>.() -> Unit,
    content: @Composable C.() -> Unit
) {
    currentComposer.startNode()
    if (currentComposer.inserting) {
        currentComposer.createNode(factory)
    } else {
        currentComposer.useNode()
    }
    Updater<C>(currentComposer).update()
    SkippableUpdater<C>(currentComposer).skippableUpdate()
    currentComposer.startReplaceableGroup(0x7ab4aae9)
    content.invoke(componentInScope)
    currentComposer.endReplaceableGroup()
    currentComposer.endNode()
}

@Composable
@ExplicitGroupsComposable
public inline fun <C> ComponentNode(
    componentInScope: C,
    update: @DisallowComposableCalls Updater<C>.() -> Unit,
    content: @Composable C.() -> Unit
) {
    ComponentNode({ componentInScope }, componentInScope, update, {}, { content(componentInScope) })
}
