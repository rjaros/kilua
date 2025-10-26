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
import dev.kilua.html.p
import dev.kilua.test.DomSpec
import kotlinx.coroutines.delay
import web.html.asStringOrNull
import kotlin.test.Test

class CarouselSpec : DomSpec {

    @Test
    fun render() = runWhenDomAvailableAsync {
        val root = root("test") {
            carousel(activeIndex = 1) {
                item("Item 1") {
                    p("First item")
                }
                item("Item 2") {
                    p("Second item")
                }
                item("Item 3") {
                    p("Third item")
                }
            }
        }
        delay(100)
        assertEqualsHtml(
            """<div class="carousel slide" id="kilua_carousel_0">
<!--sid=0-->
<!--sid=0-->
<!--sid=0-->
<div class="carousel-indicators">
<button type="button" data-bs-target="#kilua_carousel_0" data-bs-slide-to="0" aria-label="Item 1">
</button>
<button class="active" type="button" data-bs-target="#kilua_carousel_0" data-bs-slide-to="1" aria-label="Item 2" aria-current="true">
</button>
<button type="button" data-bs-target="#kilua_carousel_0" data-bs-slide-to="2" aria-label="Item 3">
</button>
</div>
<div class="carousel-inner">
<div class="carousel-item">
<p class="First item">
</p>
<div class="carousel-caption d-none d-md-block">
<h5>
Item 1
</h5>
</div>
</div>
<div class="carousel-item active">
<p class="Second item">
</p>
<div class="carousel-caption d-none d-md-block">
<h5>
Item 2
</h5>
</div>
</div>
<div class="carousel-item">
<p class="Third item">
</p>
<div class="carousel-caption d-none d-md-block">
<h5>
Item 3
</h5>
</div>
</div>
</div>
<button class="carousel-control-prev" type="button" data-bs-target="#kilua_carousel_0" data-bs-slide="prev">
<span class="carousel-control-prev-icon" aria-hidden="true">
</span>
<span class="visually-hidden">
Previous
</span>
</button>
<button class="carousel-control-next" type="button" data-bs-target="#kilua_carousel_0" data-bs-slide="next">
<span class="carousel-control-next-icon" aria-hidden="true">
</span>
<span class="visually-hidden">
Next
</span>
</button>
</div>""".replace(Regex("sid=\\d+"), "sid=0").replace(Regex("kilua_carousel_[0-9]*"), "kilua_carousel_0"),
            root.element.innerHTML.asStringOrNull()?.replace(Regex("sid=\\d+"), "sid=0")
                ?.replace(Regex("kilua_carousel_[0-9]*"), "kilua_carousel_0"),
            "Should render an Carousel component to DOM"
        )
    }

    @Test
    fun renderToString() = runAsync {
        val root = root {
            carousel(activeIndex = 1) {
                item("Item 1") {
                    p("First item")
                }
                item("Item 2") {
                    p("Second item")
                }
                item("Item 3") {
                    p("Third item")
                }
            }
        }
        delay(100)
        assertEqualsHtml(
            """<div class="carousel slide" id="kilua_carousel_0">
<!--sid=0-->
<!--sid=0-->
<!--sid=0-->
<div class="carousel-indicators">
<button type="button" data-bs-target="#kilua_carousel_0" data-bs-slide-to="0" aria-label="Item 1">
</button>
<button class="active" type="button" data-bs-target="#kilua_carousel_0" data-bs-slide-to="1" aria-label="Item 2" aria-current="true">
</button>
<button type="button" data-bs-target="#kilua_carousel_0" data-bs-slide-to="2" aria-label="Item 3">
</button>
</div>
<div class="carousel-inner">
<div class="carousel-item">
<p class="First item">
</p>
<div class="carousel-caption d-none d-md-block">
<h5>
Item 1
</h5>
</div>
</div>
<div class="carousel-item active">
<p class="Second item">
</p>
<div class="carousel-caption d-none d-md-block">
<h5>
Item 2
</h5>
</div>
</div>
<div class="carousel-item">
<p class="Third item">
</p>
<div class="carousel-caption d-none d-md-block">
<h5>
Item 3
</h5>
</div>
</div>
</div>
<button class="carousel-control-prev" type="button" data-bs-target="#kilua_carousel_0" data-bs-slide="prev">
<span class="carousel-control-prev-icon" aria-hidden="true">
</span>
<span class="visually-hidden">
Previous
</span>
</button>
<button class="carousel-control-next" type="button" data-bs-target="#kilua_carousel_0" data-bs-slide="next">
<span class="carousel-control-next-icon" aria-hidden="true">
</span>
<span class="visually-hidden">
Next
</span>
</button>
</div>""".replace(Regex("sid=\\d+"), "sid=0").replace(Regex("kilua_carousel_[0-9]*"), "kilua_carousel_0"),
            root.innerHTML.replace(Regex("sid=\\d+"), "sid=0")
                .replace(Regex("kilua_carousel_[0-9]*"), "kilua_carousel_0"),
            "Should render an Carousel component to a String"
        )
    }
}
