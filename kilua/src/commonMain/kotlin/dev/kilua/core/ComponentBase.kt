/*
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

package dev.kilua.core

import dev.kilua.utils.console
import dev.kilua.utils.nativeListOf
import dev.kilua.utils.nativeMapOf
import kotlinx.dom.clear
import org.w3c.dom.CustomEvent
import org.w3c.dom.CustomEventInit
import org.w3c.dom.Node
import org.w3c.dom.get

public abstract class ComponentBase(
    protected val node: Node?,
    internal val renderConfig: RenderConfig,
) : Component, PropertyDelegate(nativeMapOf()) {

    public override val componentId: Int = counter++

    override var parent: Component? = null
    override val children: MutableList<ComponentBase> = nativeListOf()

    internal fun insertChild(index: Int, component: ComponentBase) {
        component.parent = this
        val size = children.size
        if (index < size) {
            children.add(index, component)
            if (node != null && component.node != null) {
                node.insertBefore(component.node, node.childNodes[index]!!)
            }
        } else {
            children.add(component)
            if (node != null && component.node != null) {
                node.appendChild(component.node)
            }
        }
    }

    internal fun removeChildren(index: Int, count: Int) {
        repeat(count) {
            val child = children.removeAt(index)
            child.parent = null
            node?.removeChild(node.childNodes[index]!!)
        }
    }

    internal fun moveChildren(from: Int, to: Int, count: Int) {
        if (from != to) {
            if (count == 1 && (from == to + 1 || from == to - 1)) {
                val fromEl = children[from]
                val toEl = children.set(to, fromEl)
                children[from] = toEl
                if (node != null) {
                    val toIndex = if (from > to) to else to - 1
                    val childElement = node.removeChild(node.childNodes[from]!!)
                    node.insertBefore(childElement, node.childNodes[toIndex]!!)
                }
            } else {
                for (i in 0..<count) {
                    val fromIndex = if (from > to) from + i else from
                    val toIndex = if (from > to) to + i else to + count - 2
                    val child = children.removeAt(fromIndex)
                    val childElement = node?.let {
                        it.removeChild(it.childNodes[fromIndex]!!)
                    }
                    if (toIndex < children.size) {
                        children.add(toIndex, child)
                        if (node != null && childElement != null) {
                            node.insertBefore(childElement, node.childNodes[toIndex]!!)
                        }
                    } else {
                        children.add(child)
                        if (node != null && childElement != null) {
                            node.appendChild(childElement)
                        }
                    }
                }
            }
        }
    }

    internal fun removeAll() {
        children.forEach {
            it.parent = null
        }
        children.clear()
        node?.clear()
    }

    public open fun dispatchEvent(type: String, eventInitDict: CustomEventInit): Boolean {
        val event = CustomEvent(type, eventInitDict)
        return node?.dispatchEvent(event) ?: true
    }

    public open fun onInsert() {
        console.log("onInsert $componentId")
    }

    public open fun onRemove() {
        console.log("onRemove $componentId")
    }

    public companion object {
        protected var counter: Int = 0
    }
}
