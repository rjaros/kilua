/*
 * Modelled after NodeApplierTest in Mosaic project by Jake Wharton
 * https://github.com/JakeWharton/mosaic/blob/trunk/mosaic-runtime/src/commonTest/kotlin/com/jakewharton/mosaic/NodeApplierTest.kt
 *
 * Copyright (c) 2023 Robert Jaros
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

import androidx.compose.runtime.Applier
import androidx.compose.runtime.Stable
import dev.kilua.test.SimpleSpec
import dev.kilua.core.Component
import dev.kilua.core.StringRenderConfig
import dev.kilua.core.SafeDomFactory
import dev.kilua.html.Tag
import web.html.HTMLElement
import kotlin.test.Test
import kotlin.test.assertEquals

@Stable // Workaround https://youtrack.jetbrains.com/issue/KT-67330
class ComponentApplierSpec : SimpleSpec {
    private val stringRenderConfig = StringRenderConfig()
    private val root = Root(SafeDomFactory.createElement("div"), renderConfig = stringRenderConfig)
    private val applier = ComponentApplier(root)

    private fun <T> Applier<T>.insert(index: Int, instance: T) {
        insertBottomUp(index, instance)
    }

    @Test
    fun insertAtEnd() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)
        assertChildren(one, two, three)
    }

    @Test
    fun insertAtStart() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(0, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(0, three)
        assertChildren(three, two, one)
    }

    @Test
    fun insertAtMiddle() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(1, three)
        assertChildren(one, three, two)
    }

    @Test
    fun removeSingleAtEnd() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)

        applier.remove(2, 1)
        assertChildren(one, two)
    }

    @Test
    fun removeSingleAtStart() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)

        applier.remove(0, 1)
        assertChildren(two, three)
    }

    @Test
    fun removeSingleInMiddle() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)

        applier.remove(1, 1)
        assertChildren(one, three)
    }

    @Test
    fun removeMultipleAtEnd() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)

        applier.remove(1, 2)
        assertChildren(one)
    }

    @Test
    fun removeMultipleAtStart() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)

        applier.remove(0, 2)
        assertChildren(three)
    }

    @Test
    fun removeMultipleInMiddle() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)
        val four = Tag<HTMLElement>("four")
        applier.insert(3, four)

        applier.remove(1, 2)
        assertChildren(one, four)
    }

    @Test
    fun removeAll() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)

        applier.remove(0, 3)
        assertChildren()
    }

    @Test
    fun moveSingleLower() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)

        applier.move(2, 0, 1)
        assertChildren(three, one, two)
    }

    @Test
    fun moveSingleHigher() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)

        applier.move(0, 2, 1)
        assertChildren(two, one, three)
    }

    @Test
    fun moveMultipleLower() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)
        val four = Tag<HTMLElement>("four")
        applier.insert(3, four)

        applier.move(2, 0, 2)
        assertChildren(three, four, one, two)
    }

    @Test
    fun moveMultipleHigher() {
        val one = Tag<HTMLElement>("one")
        applier.insert(0, one)
        val two = Tag<HTMLElement>("two")
        applier.insert(1, two)
        val three = Tag<HTMLElement>("three")
        applier.insert(2, three)
        val four = Tag<HTMLElement>("four")
        applier.insert(3, four)
        applier.move(0, 4, 2)
        assertChildren(three, four, one, two)
    }

    private fun assertChildren(vararg nodes: Component) {
        assertEquals(nodes.toList(), root.children.toList())
    }
}