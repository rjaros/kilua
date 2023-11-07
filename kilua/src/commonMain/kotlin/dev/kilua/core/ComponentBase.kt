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

import dev.kilua.utils.nativeListOf
import dev.kilua.utils.nativeMapOf
import kotlinx.dom.clear
import org.w3c.dom.CustomEvent
import org.w3c.dom.CustomEventInit
import org.w3c.dom.Node
import org.w3c.dom.get

/**
 * Base class for all components.
 */
public abstract class ComponentBase(
    protected val node: Node,
    @PublishedApi
    internal val renderConfig: RenderConfig,
) : Component, PropertyDelegate(nativeMapOf()) {

    public override val componentId: Int = counter++

    override var parent: Component? = null
    override val children: MutableList<ComponentBase> = nativeListOf()

    /**
     * Insert child component at the given position.
     * @param index position where to insert the child
     */
    internal fun insertChild(index: Int, component: ComponentBase) {
        component.parent = this
        val size = children.size
        if (index < size) {
            children.add(index, component)
            if (renderConfig.isDom) {
                node.insertBefore(component.node, node.childNodes[index]!!)
            }
        } else {
            children.add(component)
            if (renderConfig.isDom) {
                node.appendChild(component.node)
            }
        }
    }

    /**
     * Remove child components at the given position.
     * @param index index of the first child to remove
     * @param count number of children to remove
     */
    internal fun removeChildren(index: Int, count: Int) {
        repeat(count) {
            val child = children.removeAt(index)
            child.parent = null
            if (renderConfig.isDom) node.removeChild(node.childNodes[index]!!)
        }
    }

    /**
     * Move children components to another position.
     * @param from index of the first child to move
     * @param to index of the destination position
     * @param count number of children to move
     */
    internal fun moveChildren(from: Int, to: Int, count: Int) {
        if (from != to) {
            if (count == 1 && (from == to + 1 || from == to - 1)) {
                val fromEl = children[from]
                val toEl = children.set(to, fromEl)
                children[from] = toEl
                if (renderConfig.isDom) {
                    val toIndex = if (from > to) to else to - 1
                    val childElement = node.removeChild(node.childNodes[from]!!)
                    node.insertBefore(childElement, node.childNodes[toIndex]!!)
                }
            } else {
                for (i in 0..<count) {
                    val fromIndex = if (from > to) from + i else from
                    val toIndex = if (from > to) to + i else to + count - 2
                    val child = children.removeAt(fromIndex)
                    val childElement = if (renderConfig.isDom) {
                        node.removeChild(node.childNodes[fromIndex]!!)
                    } else null
                    if (toIndex < children.size) {
                        children.add(toIndex, child)
                        if (childElement != null) {
                            node.insertBefore(childElement, node.childNodes[toIndex]!!)
                        }
                    } else {
                        children.add(child)
                        if (childElement != null) {
                            node.appendChild(childElement)
                        }
                    }
                }
            }
        }
    }

    /**
     * Remove all children components.
     */
    internal fun removeAll() {
        children.forEach {
            it.parent = null
        }
        children.clear()
        if (renderConfig.isDom) node.clear()
    }

    /**
     * Dispatches a custom event.
     */
    public open fun dispatchEvent(type: String, eventInitDict: CustomEventInit): Boolean {
        val event = CustomEvent(type, eventInitDict)
        return if (renderConfig.isDom) node.dispatchEvent(event) else true
    }

    /**
     * Function called after the component is inserted in the composition.
     */
    public open fun onInsert() {
    }

    /**
     * Function called after the component is removed from the composition.
     */
    public open fun onRemove() {
    }

    public companion object {
        /**
         * Counter used to generate unique component ids.
         */
        protected var counter: Int = 0
    }
}
