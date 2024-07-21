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

package dev.kilua.modal

import dev.kilua.compose.root
import dev.kilua.html.button
import dev.kilua.html.pt
import dev.kilua.test.DomSpec
import kotlinx.coroutines.delay
import dev.kilua.dom.document
import kotlin.test.Test

class ModalSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailableAsync {
        root("test") {
            val modal = modalRef("Caption") {
                pt("Body")
                footer {
                    button("OK")
                }
            }
            modal.show()
        }
        delay(100)
        assertEqualsHtml(
            """<div role="dialog" tabindex="-1" data-bs-keyboard="true" data-bs-backdrop="true" class="modal fade show" aria-modal="true" style="display: block;">
            <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
            <h5 class="modal-title">
            Caption
            </h5>
            <button class="btn-close" type="button" aria-label="Close">
            </button>
            </div>
            <div class="modal-body">
            <p>
            Body
            </p>
            </div>
            <div class="modal-footer">
            <button type="button">
            OK
            </button>
            </div>
            </div>
            </div>
            </div>""",
            document.querySelector(".modal")?.outerHTML,
            "Should render a Modal component to DOM"
        )
    }
}
