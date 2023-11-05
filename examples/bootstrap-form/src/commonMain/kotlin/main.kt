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
import dev.kilua.Application
import dev.kilua.compose.root
import dev.kilua.form.check.checkBox
import dev.kilua.form.fieldWithLabel
import dev.kilua.form.form
import dev.kilua.form.select.select
import dev.kilua.form.text.text
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.px
import dev.kilua.html.span
import dev.kilua.html.unaryPlus
import dev.kilua.startApplication
import dev.kilua.utils.console
import dev.kilua.utils.listOfPairs
import dev.kilua.utils.rem

class App : Application() {

    override fun start() {

        root("root") {
            div {
                margin = 20.px
                form(novalidate = true, className = "row g-3 needs-validation") {
                    val validation by validationStateFlow.collectAsState()

                    if (validation.wasValidated) {
                        if (validation.isInvalid) {
                            div("alert alert-danger") {
                                role = "alert"
                                +"Form is invalid."
                            }
                        } else {
                            div("alert alert-success") {
                                role = "alert"
                                +"Form is valid."
                            }
                        }
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
                        }.bind("username", {
                            "Username must be at least 10 characters long."
                        }) {
                            it.value == null || it.value!!.length > 10
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

fun main() {
    startApplication(::App)
}
