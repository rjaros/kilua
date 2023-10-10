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

package dev.kilua.core

import dev.kilua.utils.NativeList
import dev.kilua.utils.cast
import dev.kilua.utils.nativeListOf
import kotlinx.dom.clear
import org.w3c.dom.Node
import org.w3c.dom.get

public abstract class ComponentBase(override val node: Node) : Component, PropertyDelegate() {

    override var parent: Component? = null
    override val children: NativeList<Component> = nativeListOf()

    override fun insertChild(index: Int, component: Component) {
        component.cast<ComponentBase>().parent = this
        val size = children.size
        if (index < size) {
            children.add(index, component)
            node.insertBefore(component.node, node.childNodes[index]!!)
        } else {
            children.add(component)
            node.appendChild(component.node)
        }
    }

    override fun removeChildren(index: Int, count: Int) {
        repeat(count) {
            val child = children.removeAt(index)
            child.cast<ComponentBase>().parent = null
            node.removeChild(node.childNodes[index]!!)
        }
    }

    override fun moveChildren(from: Int, to: Int, count: Int) {
        if (from != to) {
            if (count == 1 && (from == to + 1 || from == to - 1)) {
                val fromEl = children[from]
                val toEl = children.set(to, fromEl)
                children[from] = toEl
                val toIndex = if (from > to) to else to - 1
                val childElement = node.removeChild(node.childNodes[from]!!)
                node.insertBefore(childElement, node.childNodes[toIndex]!!)
            } else {
                for (i in 0..<count) {
                    val fromIndex = if (from > to) from + i else from
                    val toIndex = if (from > to) to + i else to + count - 2
                    val child = children.removeAt(fromIndex)
                    val childElement = node.removeChild(node.childNodes[fromIndex]!!)
                    if (toIndex < children.size) {
                        children.add(toIndex, child)
                        node.insertBefore(childElement, node.childNodes[toIndex]!!)
                    } else {
                        children.add(child)
                        node.appendChild(childElement)
                    }
                }
            }
        }
    }

    override fun removeAll() {
        children.forEach {
            it.cast<ComponentBase>().parent = null
        }
        children.clear()
        node.clear()
    }

}
