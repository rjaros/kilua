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

package dev.kilua

import dev.kilua.compose.Root
import dev.kilua.utils.Object
import kotlinx.browser.document
import kotlinx.dom.clear
import kotlin.js.Promise

interface TestSpec {
    fun beforeTest()

    fun afterTest()

    fun run(code: () -> Unit) {
        beforeTest()
        code()
        afterTest()
    }

    fun runAsync(code: (resolve: () -> Unit) -> Unit): Promise<Object> {
        beforeTest()
        return Promise { resolve, _ ->
            code { resolve(Object()) }
        }.finally {
            afterTest()
        }
    }
}

interface SimpleSpec : TestSpec {

    override fun beforeTest() {
    }

    override fun afterTest() {
    }

}

interface DomSpec : TestSpec {

    fun getTestId() = "test"

    override fun beforeTest() {
        val fixture = "<div style=\"display: none\" id=\"pretest\">" +
                "<div id=\"${getTestId()}\"></div></div>"
        document.body?.insertAdjacentHTML("afterbegin", fixture)
    }

    override fun afterTest() {
        val div = document.getElementById("pretest")
        div?.clear()
        val modalBackdrop = document.getElementById(".modal-backdrop")
        modalBackdrop?.remove()
        Root.disposeAllRoots()
    }
}
