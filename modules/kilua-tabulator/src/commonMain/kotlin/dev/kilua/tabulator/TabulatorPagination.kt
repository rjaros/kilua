/*
 * Copyright (c) 2024 Robert Jaros
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

package dev.kilua.tabulator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.core.IComponent
import dev.kilua.html.Border
import dev.kilua.html.BorderStyle
import dev.kilua.html.IDiv
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.utils.rem
import dev.kilua.utils.toJsNumber

/**
 * Pagination state.
 *
 * @constructor
 * @param currentPage current page number
 * @param maxPages maximum number of pages
 * @param buttonCount number of page buttons
 */
public data class PaginationState(val currentPage: Int, val maxPages: Int, val buttonCount: Int = 5)

/**
 * Tabulator pagination component.
 *
 * @param T type of tabulator data
 * @param tabulatorGetter tabulator accessor function
 * @param iconFirst icon for the first page button
 * @param iconPrev icon for the previous page button
 * @param iconNext icon for the next page button
 * @param iconLast icon for the last page button
 * @param className CSS class name
 * @param id HTML ID
 * @param init initialization block
 */
@Composable
public fun <T : Any> IComponent.tabulatorPagination(
    tabulatorGetter: () -> Tabulator<T>,
    iconFirst: String = "fas fa-angles-left",
    iconPrev: String = "fas fa-angle-left",
    iconNext: String = "fas fa-angle-right",
    iconLast: String = "fas fa-angles-right",
    className: String? = null,
    id: String? = null,
    init: @Composable (IDiv.() -> Unit) = {}
) {

    var paginationState by remember { mutableStateOf(PaginationState(1, 0)) }

    div("tabulator" % className, id) {
        div(className = "tabulator-footer") {
            borderTop(Border(style = BorderStyle.None))
            button(icon = iconFirst, className = "tabulator-page") {
                role("button")
                ariaLabel("<<")
                title("<<")
                if (paginationState.currentPage == 1) {
                    disabled(true)
                }
                onClick {
                    tabulatorGetter().tabulatorJs?.setPage(1.toJsNumber())
                }
            }
            button(icon = iconPrev, className = "tabulator-page") {
                role("button")
                ariaLabel("<")
                title("<")
                if (paginationState.currentPage == 1) {
                    disabled(true)
                }
                onClick {
                    tabulatorGetter().tabulatorJs?.previousPage()
                }
            }
            var firstButtonNumber = maxOf(paginationState.currentPage - paginationState.buttonCount + 1, 1)
            var lastButtonNumber =
                minOf(paginationState.currentPage + paginationState.buttonCount - 1, paginationState.maxPages)
            while (lastButtonNumber - firstButtonNumber >= paginationState.buttonCount) {
                if (paginationState.currentPage - firstButtonNumber >= lastButtonNumber - paginationState.currentPage) {
                    firstButtonNumber++
                } else {
                    lastButtonNumber--
                }
            }
            for (pageNumber in firstButtonNumber..lastButtonNumber) {
                val buttonClassName =
                    if (pageNumber == paginationState.currentPage) "tabulator-page active" else "tabulator-page"
                button("$pageNumber", className = buttonClassName) {
                    role("button")
                    ariaLabel("# $pageNumber")
                    title("# $pageNumber")
                    onClick {
                        tabulatorGetter().tabulatorJs?.setPage(pageNumber.toJsNumber())
                    }
                }
            }
            button(icon = iconNext, className = "tabulator-page") {
                role("button")
                ariaLabel(">")
                title(">")
                if (paginationState.currentPage >= paginationState.maxPages) {
                    disabled(true)
                }
                onClick {
                    tabulatorGetter().tabulatorJs?.nextPage()
                }
            }
            button(icon = iconLast, className = "tabulator-page") {
                role("button")
                ariaLabel(">>")
                title(">>")
                if (paginationState.currentPage >= paginationState.maxPages) {
                    disabled(true)
                }
                onClick {
                    tabulatorGetter().tabulatorJs?.setPage(paginationState.maxPages.toJsNumber())
                }
            }
        }
        init()
        DisposableEffect(Unit) {
            val unregister = tabulatorGetter().registerPagination {
                paginationState = it
            }
            onDispose {
                unregister()
            }
        }
    }
}