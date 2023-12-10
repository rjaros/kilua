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

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.BootstrapCssModule
import dev.kilua.BootstrapIconsModule
import dev.kilua.BootstrapModule
import dev.kilua.CoreModule
import dev.kilua.FontAwesomeModule
import dev.kilua.TrixModule
import dev.kilua.compose.root
import dev.kilua.form.check.checkBox
import dev.kilua.form.fieldWithLabel
import dev.kilua.form.form
import dev.kilua.form.number.range
import dev.kilua.form.select.select
import dev.kilua.form.text.richText
import dev.kilua.form.text.text
import dev.kilua.html.*
import dev.kilua.html.style.PClass
import dev.kilua.html.style.style
import dev.kilua.modal.FullscreenMode
import dev.kilua.modal.ModalSize
import dev.kilua.modal.confirm
import dev.kilua.modal.modal
import dev.kilua.panel.TabPosition
import dev.kilua.panel.accordion
import dev.kilua.panel.carousel
import dev.kilua.panel.splitPanel
import dev.kilua.panel.tabPanel
import dev.kilua.startApplication
import dev.kilua.state.collectAsState
import dev.kilua.utils.console
import dev.kilua.utils.listOfPairs
import dev.kilua.utils.log
import dev.kilua.utils.rem
import web.dom.CustomEvent
import web.toDouble
import web.toJsBoolean
import web.toJsNumber

class App : Application() {

    override fun start() {

        root("root") {
            div {
                margin = 20.px

                carousel(hideIndicators = true, autoPlay = true) {
                    item("First slide", "First slide label") {
                        div("d-block w-100") {
                            height = 200.px
                            background = Background(color = Color.Red)
                            pt("Nulla vitae elit libero, a pharetra augue mollis interdum.")
                        }
                    }
                    item("Second slide", "Second slide label") {
                        div("d-block w-100") {
                            height = 200.px
                            background = Background(color = Color.Green)
                            pt("Lorem ipsum dolor sit amet, consectetur adipiscing elit.")
                        }
                    }
                    item("Third slide", "Third slide label") {
                        div("d-block w-100") {
                            height = 200.px
                            background = Background(color = Color.Blue)
                            pt("Praesent commodo cursus magna, vel scelerisque nisl consectetur.")
                        }
                    }
                }

                hr()

                var accName by remember { mutableStateOf("Test") }

                accordion(true) {
                    item("Search item", "fas fa-search") {
                        pt("First item content", "mb-0")
                    }
                    if (accName == "Test2") {
                        item(accName, "fas fa-times") {
                            pt(accName, "mb-0")
                        }
                    } else {
                        item(accName, "fas fa-times") {
                            h2t(accName, "mb-0")
                        }
                    }
                    item("Third item", "fab fa-chrome") {
                        pt("Third item content", "mb-0")
                    }
                }

                button("modify accordion", "bi-star", className = "btn btn-primary") {
                    onClick {
                        accName += "2"
                    }
                }

                hr()

                fieldWithLabel("Floating label", "form-label", true, groupClassName = "form-floating") {
                    text("Mark", placeholder = "placeholder", className = "form-control form-control-sm")
                }

                fieldWithLabel("A switch", "form-check-label", true, groupClassName = "form-check form-switch") {
                    checkBox(true, className = "form-check-input", id = it) {
                        role = "switch"
                    }
                }

                hr()
                bsButton("Test", "fas fa-check", size = ButtonSize.BtnLg, style = ButtonStyle.BtnDanger) {
                    onClick {
                        console.log("test")
                    }
                }

                hr()

                var modalCaption by remember { mutableStateOf("Test") }

                if (modalCaption == "Test2") {
                    style(modalCaption) {
                        margin = 20.px
                    }
                }

                val modal = modal(
                    modalCaption,
                    size = ModalSize.ModalXl,
                    fullscreenMode = FullscreenMode.ModalFullscreenMdDown,
                    centered = false,
                    scrollable = true,
                    escape = false
                ) {
                    pt(modalCaption)
                    footer {
                        button("OK").onClick {
                            this@modal.hide()
                        }
                        button("Test").onClick {
                            modalCaption += "2"
                        }
                    }
                }
                button("show modal") {
                    onClick {
                        modal.show()
                    }
                }
                button("modal class") {
                    onClick {
                        confirm(modalCaption, "Test content", cancelVisible = true, noCallback = {
                            console.log("no callback")
                        }) {
                            console.log("yes callback")
                        }
                    }
                }
                button("Caption") {
                    onClick {
                        modalCaption += "2"
                    }
                }

                hr()

                var splitState by remember { mutableStateOf(0) }

                splitPanel {
                    width = 500.px
                    height = 300.px
                    margin = 30.px
                    if (splitState != 1) {
                        left {
                            pt("left$splitState")
                        }
                        right {
                            pt("right$splitState")
                        }
                    } else {
                        left {
                            pt("top$splitState")
                        }
                        right {
                            pt("bottom$splitState")
                        }
                    }
                }

                button("test split") {
                    onClick {
                        splitState += 1
                    }
                }

                hr()

                var draggableTabs by remember { mutableStateOf(false) }
                var selectedTab by remember { mutableStateOf(0) }

                var tabName by remember { mutableStateOf("Test") }

                tabPanel(activeIndex = selectedTab, tabPosition = TabPosition.Top, draggableTabs = draggableTabs) {
                    console.log("recompose tabPanel 1 (selectedTab: $selectedTab)")
                    margin = 20.px
                    tab("Test1", "fas fa-search", closable = true) {
                        pt("Test1")
                    }
                    if (tabName == "Test2") {
                        tab(tabName, "fas fa-times", closable = true) {
                            pt(tabName)
                        }
                    } else {
                        tab(tabName, "fas fa-times", closable = true) {
                            h2t(tabName)
                        }
                    }
                    tab("Test3", "fab fa-chrome", closable = true) {
                        pt("Test3")
                    }
                    onEvent<CustomEvent>("closeTab") {
                    }
                }

                link("https://www.google.com", "Google", "bi-google", className = "btn btn-primary")

                br()

                button("toggle tabPanel", "bi-star", className = "btn btn-primary") {
                    onClick {
                        tabName += "2"
                    }
                }

                hr()

                val tabs = remember { mutableStateListOf("First tab", "Second tab") }

                tabPanel(activeIndex = 0) {
                    margin = 30.px
                    console.log("recompose tabPanel")
                    tabs.forEach { tab ->
                        console.log("generate tab: $tab")
                        key(tab) {
                            tab(tab, closable = true) {
                                pt(tab)
                            }
                        }
                    }
                    onEvent<CustomEvent>("closeTab") {
                        tabs.removeAt(it.detail.toString().toIntOrNull() ?: 0)
                    }
                }

                button("add tab") {
                    onClick {
                        tabs.add("New tab")
                    }
                }

                button("remove tab") {
                    onClick {
                        tabs.removeAt(1)
                    }
                }

                hr()

                var disab by remember { mutableStateOf(true) }

                val trix = richText("ala ma kota", disabled = disab, placeholder = "wprowadź dane") {
                    onChange {
                        console.log(this.value)
                    }
                }
                button("get trix") {
                    onClick {
                        console.log(trix.value)
                    }
                }
                button("set trix") {
                    onClick {
                        trix.value = "<strong>bold text</strong>"
                    }
                }
                button("clear trix") {
                    onClick {
                        trix.value = null
                    }
                }
                button("hide trix") {
                    onClick {
                        trix.visible = !trix.visible
                    }
                }
                button("disable trix") {
                    onClick {
                        disab = !disab
                    }
                }

                hr()

                val i by range(0, 1, 255).collectAsState()

                val className = style(".test") {
                    console.log("recompose 1")
                    background = Background(color = Color.rgb(i?.toInt() ?: 0, 0, 0))
                    style("h1", PClass.Hover) {
                        color = Color.Green
                    }
                    style("h1") {
                        style("div") {
                            color = Color.Blue
                        }
                    }
                    style("input", PClass.Focus) {
                        border = Border(1.px, BorderStyle.Solid, Color.Red)
                    }
                }

                div(className) {
                    h1t("Ala ma kota") {
                        div {
                            +"test"
                        }
                    }
                    margin = 20.px
                    form(className = "row g-3 needs-validation") {
                        val validation by validationStateFlow.collectAsState()

                        if (validation.isInvalid) {
                            pt("form is invalid: ${validation.invalidMessage}")
                            validation.fieldsValidations.forEach { (key, value) ->
                                if (value.isEmptyWhenRequired) pt("field is empty: $key")
                                if (value.isInvalid) pt("field is invalid: $key - ${value.invalidMessage}")
                            }
                        } else if (validation.validMessage != null) {
                            pt("form is valid: ${validation.validMessage}")
                        }

                        fieldWithLabel("First name", "form-label", groupClassName = "col-md-4") {
                            text("Mark", required = true, className = "form-control").bind("firstName").also {
                                div("valid-feedback") {
                                    +"Looks good!"
                                }
                            }
                        }
                        fieldWithLabel("Last name", "form-label", groupClassName = "col-md-4") {
                            text("Otto", required = true, className = "form-control").bind("lastName").also {
                                div("valid-feedback") {
                                    +"Looks good!"
                                }
                            }
                        }
                        fieldWithLabel(
                            "Username", "form-label", groupClassName = "col-md-4",
                            wrapperClassName = "input-group has-validation"
                        ) {
                            span("input-group-text") {
                                id = "inputGroupPrepend"
                                +"@"
                            }
                            val invalidClass = if (validation["username"]?.isInvalid == true) "is-invalid" else null
                            text(required = true, className = "form-control" % invalidClass) {
                                ariaDescribedby = "inputGroupPrepend"
                            }.bindWithValidationMessage("username") { text ->
                                val result = text.value == null || text.value!!.length >= 10
                                val message = if (!result) "Username must be at least 10 characters long." else null
                                result to message
                            }.also {
                                div("invalid-feedback") {
                                    +(validation["username"]?.invalidMessage ?: "Please choose a username.")
                                }
                            }
                        }
                        fieldWithLabel("City", "form-label", groupClassName = "col-md-6") {
                            text(required = true, className = "form-control").bind("city").also {
                                div("invalid-feedback") {
                                    +"Please provide a valid city."
                                }
                            }
                        }
                        fieldWithLabel("State", "form-label", groupClassName = "col-md-3") {
                            select(listOfPairs("Alaska"), className = "form-select", placeholder = "Choose...")
                                .bind("state").also {
                                    div("invalid-feedback") {
                                        +"Please select a valid state."
                                    }
                                }
                        }
                        fieldWithLabel("Zip", "form-label", groupClassName = "col-md-3") {
                            text(required = true, className = "form-control").bind("zip").also {
                                div("invalid-feedback") {
                                    +"Please provide a valid zip."
                                }
                            }
                        }
                        div("col-12") {
                            div("form-check") {
                                fieldWithLabel("Agree to terms and conditions", "form-check-label", labelAfter = true) {
                                    checkBox(className = "form-check-input", required = true).bind("agree")
                                }
                                div("invalid-feedback") {
                                    +"You must agree before submitting."
                                }
                            }
                        }
                        div("col-12") {
                            button("Submit form", className = "btn btn-primary") {
                                onClick {
                                    val x = this@form.validate()
                                    console.log(x.toString())
                                    this@form.className = "row g-3 was-validated"
                                    val data = this@form.getData()
                                    console.log(data.toString())
                                }
                            }
                        }
//                    stateFlow.onEach {
//                        this.validate()
//                    }.launchIn(KiluaScope)
                    }

                }
            }
        }
    }
}

fun main() {
    startApplication(
        ::App,
        null,
        BootstrapModule,
        BootstrapCssModule,
        BootstrapIconsModule,
        FontAwesomeModule,
        TrixModule,
        CoreModule
    )
}
