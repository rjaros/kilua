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

package dev.kilua.html

import dev.kilua.DomSpec
import dev.kilua.compose.root
import dev.kilua.normalizeHtml
import dev.kilua.utils.jsString
import org.w3c.dom.ImageData
import kotlin.test.Test
import kotlin.test.assertEquals

class CanvasSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                canvas(300, 200, "test") {
                    id = "test-id"
                }
            }
            assertEquals(
                normalizeHtml("""<canvas class="test" width="300" height="200" id="test-id"></canvas>"""),
                normalizeHtml(root.element.innerHTML),
                "Should render an HTML Canvas tag to DOM"
            )
        }
    }

    @Test
    fun context2D() {
        runWhenDomAvailable {
            lateinit var imageData: ImageData
            root("test") {
                canvas(300, 200) {
                    context2D?.let {
                        it.beginPath()
                        it.strokeStyle = jsString("black")
                        it.moveTo(0.0, 0.0)
                        it.lineTo(100.0, 100.0)
                        it.stroke()
                        imageData = it.getImageData(1.0, 1.0, 2.0, 2.0)
                    }
                }
            }
            assertEquals(2, imageData.width, "Should return an image data with correct width")
            assertEquals(2, imageData.height, "Should return an image data with correct height")
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                canvas(300, 200, "test") {
                    id = "test-id"
                }
            }
            assertEquals(
                normalizeHtml("""<div><canvas class="test" width="300" height="200" id="test-id"></canvas></div>"""),
                normalizeHtml(root.renderToString()),
                "Should render an HTML Canvas tag to a String"
            )
        }
    }

}

