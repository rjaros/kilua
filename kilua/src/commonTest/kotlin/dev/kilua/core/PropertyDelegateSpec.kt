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

import dev.kilua.test.SimpleSpec
import kotlin.test.Test
import kotlin.test.assertEquals

class PropertyDelegateSpec : SimpleSpec {

    @Test
    fun updatingProperty() {
        run {
            var notifyCounter = 0
            var updatesCounter = 0

            class TestComponent : PropertyDelegate() {
                var property1: String by updatingProperty(notifyFunction = { notifyCounter++ }) {
                    updatesCounter++
                }
            }

            val testComponent = TestComponent()
            testComponent.property1 = "test1"
            assertEquals(1, notifyCounter)
            assertEquals(1, updatesCounter)
            testComponent.property1 = "test1"
            assertEquals(1, notifyCounter)
            assertEquals(1, updatesCounter)
            testComponent.property1 = "test2"
            assertEquals(2, notifyCounter)
            assertEquals(2, updatesCounter)

            notifyCounter = 0
            updatesCounter = 0

            class TestComponent2 : PropertyDelegate(skipUpdates = true) {
                var property2: String by updatingProperty(notifyFunction = { notifyCounter++ }) {
                    updatesCounter++
                }
            }

            val testComponent2 = TestComponent2()
            testComponent2.property2 = "test1"
            assertEquals(1, notifyCounter)
            assertEquals(0, updatesCounter)
            testComponent2.property2 = "test1"
            assertEquals(1, notifyCounter)
            assertEquals(0, updatesCounter)
            testComponent2.property2 = "test2"
            assertEquals(2, notifyCounter)
            assertEquals(0, updatesCounter)
        }
    }
}