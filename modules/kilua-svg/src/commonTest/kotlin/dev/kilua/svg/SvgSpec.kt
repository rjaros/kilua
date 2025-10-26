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

package dev.kilua.svg

import dev.kilua.compose.root
import dev.kilua.html.perc
import dev.kilua.html.px
import dev.kilua.test.DomSpec
import web.html.asStringOrNull
import kotlin.test.Test

class SvgSpec : DomSpec {

    @Test
    fun nodeNames() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    animate()
                    animateMotion()
                    animateTransform()
                    defs()
                    filter()
                    g()
                    marker()
                    mpath()
                    switch()
                    tspan()
                }
            }
            assertEqualsHtml(
                """<svg><animate></animate><animateMotion></animateMotion><animateTransform></animateTransform><defs></defs><filter></filter><g></g><marker></marker><mpath></mpath><switch></switch><tspan></tspan></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render an SVG nodes to DOM"
            )
        }
    }

    @Test
    fun nodeNamsToString() {
        run {
            val root = root {
                svg {
                    animate()
                    animateMotion()
                    animateTransform()
                    defs()
                    filter()
                    g()
                    marker()
                    mpath()
                    switch()
                    tspan()
                }
            }
            assertEqualsHtml(
                """<svg xmlns="http://www.w3.org/2000/svg"><animate></animate><animateMotion></animateMotion><animateTransform></animateTransform><defs></defs><filter></filter><g></g><marker></marker><mpath></mpath><switch></switch><tspan></tspan></svg>""",
                root.innerHTML,
                "Should render an SVG nodeNames to a String"
            )
        }
    }

    @Test
    fun clipPathTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    clipPath("myClip") {
                        circle(40.px, 35.px, 36.px)
                    }
                }
            }
            assertEqualsHtml(
                """<svg><clipPath id="myClip"><circle cx="40px" cy="35px" r="36px"></circle></clipPath></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a clipPath node to DOM"
            )
        }
    }

    @Test
    fun maskTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    mask(id = "myMask") {
                    }
                }
            }
            assertEqualsHtml(
                """<svg><mask id="myMask"></mask></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a mask node to DOM"
            )
        }
    }

    @Test
    fun svgATest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    a("/docs/Web/SVG/Element/circle") {
                        attribute("target", "_blank")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><a href="/docs/Web/SVG/Element/circle" target="_blank"></a></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render an a node to DOM"
            )
        }
    }

    @Test
    fun descTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    desc("some description") {
                        attribute("id", "myDesc")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><desc id="myDesc">some description</desc></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a desc node to DOM"
            )
        }
    }

    @Test
    fun setTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    rect(0, 0, 10, 10) {
                        set("class", "round") {
                            attribute("begin", "me.click")
                            attribute("dur", "2s")
                        }
                    }
                }
            }
            assertEqualsHtml(
                """<svg><rect x="0" y="0" style="width: 10px; height: 10px;"><set attributeName="class" to="round" begin="me.click" dur="2s"></set></rect></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a set node to DOM"
            )
        }
    }

    @Test
    fun titleTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    rect(10, 20, 30, 30) {
                        svgTitle("some title")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><rect x="10" y="20" style="width: 30px; height: 30px;"><title>some title</title></rect></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a title node to DOM"
            )
        }
    }

    @Test
    fun svgTextTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    text("some text", 20, 30) {
                        attribute("class", "small")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><text x="20" y="30" class="small">some text</text></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a text node to DOM"
            )
        }
    }

    @Test
    fun textPathTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    textPath("#someHref", "Some text")
                }
            }
            assertEqualsHtml(
                """<svg><textPath href="#someHref">Some text</textPath></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a textPath node to DOM"
            )
        }
    }

    @Test
    fun ellipseTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    ellipse(50, 60, 70, 20, "myClass") {
                        attribute("color", "yellow")
                    }
                    ellipse(50.px, 60.px, 70.perc, 20.px) {
                        attribute("color", "red")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><ellipse class="myClass" cx="50" cy="60" rx="70" ry="20" color="yellow"></ellipse><ellipse cx="50px" cy="60px" rx="70%" ry="20px" color="red"></ellipse></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render an ellipse node to DOM"
            )
        }
    }

    @Test
    fun ellipseTestToString() {
        run {
            val root = root("test") {
                svg {
                    ellipse(50, 60, 70, 20, "myClass") {
                        attribute("color", "yellow")
                    }
                    ellipse(50.px, 60.px, 70.perc, 20.px) {
                        attribute("color", "red")
                    }
                }
            }
            assertEqualsHtml(
                """<svg xmlns="http://www.w3.org/2000/svg"><ellipse class="myClass" cx="50" cy="60" rx="70" ry="20" color="yellow"></ellipse><ellipse cx="50px" cy="60px" rx="70%" ry="20px" color="red"></ellipse></svg>""",
                root.innerHTML,
                "Should render an ellipse node to String"
            )
        }
    }

    @Test
    fun circleTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    circle(50, 60, 70) {
                        attribute("color", "red")
                    }
                    circle(50.px, 60.px, 70.perc) {
                        attribute("color", "red")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><circle cx="50" cy="60" r="70" color="red"></circle><circle cx="50px" cy="60px" r="70%" color="red"></circle></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a circle node to DOM"
            )
        }
    }

    @Test
    fun rectTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    rect(0, 20, 100, 200) {
                        attribute("color", "red")
                    }
                    rect(0.px, 20.px, 100.px, 200.px) {
                        attribute("color", "red")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><rect x="0" y="20" style="width: 100px; height: 200px;" color="red"></rect><rect x="0px" y="20px" style="width: 100px; height: 200px;" color="red"></rect></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a rect node to DOM"
            )
        }
    }

    @Test
    fun imageTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    image("/image.png") {
                        attribute("preserveAspectRatio", "xMidYMid meet")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><image href="/image.png" preserveAspectRatio="xMidYMid meet"></image></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render an image node to DOM"
            )
        }
    }

    @Test
    fun lineTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    line(0, 80, 100, 20) {
                        attribute("stroke", "red")
                    }
                    line(0.px, 80.px, 100.px, 20.px) {
                        attribute("stroke", "black")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><line x1="0" y1="80" x2="100" y2="20" stroke="red"></line><line x1="0px" y1="80px" x2="100px" y2="20px" stroke="black"></line></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a line node to DOM"
            )
        }
    }

    @Test
    fun polylineTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    polyline(0, 100, 50, 25, 50, 75, 100, 0) {
                        attribute("stroke", "red")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><polyline points="0,100 50,25 50,75 100,0" stroke="red"></polyline></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a polyline node to DOM"
            )
        }
    }

    @Test
    fun polygonTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    polygon(0, 100, 50, 25, 50, 75, 100, 0) {
                        attribute("stroke", "red")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><polygon points="0,100 50,25 50,75 100,0" stroke="red"></polygon></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a polygon node to DOM"
            )
        }
    }

    @Test
    fun linearGradientTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    linearGradient(id = "myGradient") {
                        stop {
                            attribute("offset", "10%")
                            attribute("stop-color", "gold")
                        }
                        stop {
                            attribute("offset", "95%")
                            attribute("stop-color", "red")
                        }
                    }
                }
            }
            assertEqualsHtml(
                """<svg><linearGradient id="myGradient"><stop offset="10%" stop-color="gold"></stop><stop offset="95%" stop-color="red"></stop></linearGradient></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a linearGradient node to DOM"
            )
        }
    }

    @Test
    fun radialGradientTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    radialGradient(id = "myGradient") {
                        stop {
                            attribute("offset", "10%")
                            attribute("stop-color", "gold")
                        }
                        stop {
                            attribute("offset", "95%")
                            attribute("stop-color", "red")
                        }
                    }
                }
            }
            assertEqualsHtml(
                """<svg><radialGradient id="myGradient"><stop offset="10%" stop-color="gold"></stop><stop offset="95%" stop-color="red"></stop></radialGradient></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a radialGradient node to DOM"
            )
        }
    }

    @Test
    fun patternTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    pattern("myPattern") {
                        polygon(0, 100, 50, 25, 50, 75, 100, 0) {
                            attribute("stroke", "red")
                        }
                    }
                }
            }
            assertEqualsHtml(
                """<svg><pattern id="myPattern"><polygon points="0,100 50,25 50,75 100,0" stroke="red"></polygon></pattern></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a pattern node to DOM"
            )
        }
    }

    @Test
    fun viewTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    view("one", "0 0 100 100")
                }
            }
            assertEqualsHtml(
                """<svg><view id="one" viewBox="0 0 100 100"></view></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a view node to DOM"
            )
        }
    }

    @Test
    fun pathTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    path("M 10,30 A 20,20 0,0,1 50,30 A 20,20 0,0,1 90,30 Q 90,60 50,90 Q 10,60 10,30 z")
                }
            }
            assertEqualsHtml(
                """<svg><path d="M 10,30 A 20,20 0,0,1 50,30 A 20,20 0,0,1 90,30 Q 90,60 50,90 Q 10,60 10,30 z"></path></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a path node to DOM"
            )
        }
    }

    @Test
    fun useTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    symbol(id = "myDot") {
                        attribute("width", "10")
                        attribute("height", "10")
                        attribute("viewBox", "0 0 2 2")
                        circle(1.px, 1.px, 1.px)
                    }
                    use("myDot") {
                        attribute("x", "5")
                        attribute("y", "5")
                        opacity(1.0)
                    }
                }
            }
            assertEqualsHtml(
                """<svg><symbol id="myDot" width="10" height="10" viewBox="0 0 2 2"><circle cx="1px" cy="1px" r="1px"></circle></symbol><use style="opacity: 1;" href="myDot" x="5" y="5"></use></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render a use node to DOM"
            )
        }
    }

    @Test
    fun svgElementTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg {
                    svgElement("circle") {
                        attribute("cx", "12px")
                        attribute("cy", "22px")
                        attribute("r", "5%")
                    }
                }
            }
            assertEqualsHtml(
                """<svg><circle cx="12px" cy="22px" r="5%"></circle></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render an SVG element to DOM"
            )
        }
    }

    @Test
    fun svgElementWithViewBoxTest() {
        runWhenDomAvailable {
            val root = root("test") {
                svg(viewBox = "0 0 200 200")
            }
            assertEqualsHtml(
                """<svg viewBox="0 0 200 200"></svg>""",
                root.element.innerHTML.asStringOrNull(),
                "Should render an SVG element with viewBox to DOM"
            )
        }
    }
}
