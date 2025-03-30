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

package dev.kilua.test

import dev.kilua.compose.Root
import dev.kilua.utils.cast
import dev.kilua.utils.isDom
import dev.kilua.utils.obj
import js.core.JsAny
import js.coroutines.asPromise
import js.promise.Promise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import web.dom.InsertPosition
import web.dom.document
import kotlin.test.DefaultAsserter.assertTrue

public val testScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

public interface TestSpec {

    public fun beforeTest()

    public fun afterTest()

    public fun run(code: () -> Unit) {
        beforeTest()
        code()
        afterTest()
    }

    public fun runAsync(code: suspend () -> Unit): Promise<JsAny> {
        beforeTest()
        return testScope.async {
            code()
            obj()
        }.asPromise().finally {
            afterTest()
        }.cast()
    }
}

public interface SimpleSpec : TestSpec {

    override fun beforeTest() {
    }

    override fun afterTest() {
    }

}

public interface DomSpec : TestSpec {

    public fun getTestId(): String = "test"

    public fun runWhenDomAvailable(code: () -> Unit) {
        beforeTest()
        if (isDom) {
            run(code)
        }
        afterTest()
    }

    public fun runWhenDomAvailableAsync(code: suspend () -> Unit): Promise<JsAny> {
        beforeTest()
        return testScope.async {
            if (isDom) code()
            obj()
        }.asPromise().finally {
            afterTest()
        }.cast()
    }

    override fun beforeTest() {
        if (isDom) {
            val fixture = "<div style=\"display: none\" id=\"pretest\">" +
                    "<div id=\"${getTestId()}\"></div></div>"
            document.body.insertAdjacentHTML(InsertPosition.afterbegin, fixture)
        }
    }

    override fun afterTest() {
        if (isDom) {
            val div = document.getElementById("pretest")
            div?.let {
                while (it.hasChildNodes()) {
                    it.removeChild(it.firstChild!!)
                }
            }
            val modalBackdrop = document.getElementById(".modal-backdrop")
            modalBackdrop?.remove()
        }
        Root.disposeAllRoots()
    }

    public fun assertEqualsHtml(
        expected: String?,
        actual: String?,
        message: String? = null,
        normalizeHtml: Boolean = true,
    ) {
        val normalizedExpected = if (normalizeHtml) normalizeHtml(expected) else expected
        val normalizedActual = if (normalizeHtml) normalizeHtml(actual) else actual
        assertTrue(
            {
                (if (message == null) "" else "$message. ") +
                        "Expected <$normalizedExpected>, actual <$normalizedActual>."
            },
            htmlDifferEquals(expected, actual)
        )
    }

    public companion object {

        private val htmlDiffer = HtmlDiffer("bem")

        public fun htmlDifferEquals(expected: String?, actual: String?): Boolean {
            return if (expected.isNullOrBlank() || actual.isNullOrBlank()) {
                false
            } else {
                htmlDiffer.isEqual(expected, actual)
            }
        }
    }
}

/**
 * Format an HTML string in a standardized manner, with one HTML element per line.
 * Removes auto-generated IDs from the HTML.
 * This helps with highlighting HTML differences in test assertions.
 */
public fun normalizeHtml(raw: String): String {
    return raw
        .replace("<", "\n<")
        .replace(">", ">\n")
        .split("\n")
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .joinToString("\n")
}

/** @see [normalizeHtml] */
public fun normalizeHtml(raw: String?): String? {
    return if (raw == null) null else normalizeHtml(raw)
}
