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

package dev.kilua.panel

import dev.kilua.compose.root
import dev.kilua.html.pt
import dev.kilua.test.DomSpec
import kotlinx.coroutines.delay
import web.html.asStringOrNull
import kotlin.test.Test

class OffcanvasSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailableAsync {
        lateinit var offcanvas: Offcanvas
        val root = root("test") {
            offcanvas = offcanvasRef("Caption", OffPlacement.OffcanvasStart, OffResponsiveType.OffcanvasLg) {
                pt("Offcanvas content")
            }
        }
        offcanvas.show()
        delay(100)
        assertEqualsHtml(
            """<div class="offcanvas-lg offcanvas-start show" id="kilua_offcanvas_0" tabindex="-1" aria-labelledby="kilua_offcanvas_0-label" data-bs-keyboard="true" data-bs-backdrop="true" aria-modal="true" role="dialog">
<div class="offcanvas-header">
<h5 class="offcanvas-title" id="kilua_offcanvas_0-label">
Caption
</h5>
<button class="btn-close" type="button" aria-label="Close">
</button>
</div>
<div class="offcanvas-body">
<p>
Offcanvas content
</p>
</div>
</div>
<div class="offcanvas-backdrop fade show">
</div>""",
            root.element.innerHTML.asStringOrNull(),
            "Should render an Offcanvas component to DOM"
        )
    }

    @Test
    fun renderToString() = run {
        val root = root {
            offcanvas("Caption", OffPlacement.OffcanvasStart, OffResponsiveType.OffcanvasLg) {
                pt("Offcanvas content")
            }
        }
        assertEqualsHtml(
            """<div class="offcanvas-lg offcanvas-start" id="kilua_offcanvas_0" tabindex="-1" aria-labelledby="kilua_offcanvas_0-label" data-bs-keyboard="true" data-bs-backdrop="true">
<div class="offcanvas-header">
<h5 class="offcanvas-title" id="kilua_offcanvas_0-label">
Caption
</h5>
<button class="btn-close" type="button" aria-label="Close">
</button>
</div>
<div class="offcanvas-body">
<p>
Offcanvas content
</p>
</div>
</div>
""",
            root.innerHTML,
            "Should render an Offcanvas component to a String"
        )
    }
}
