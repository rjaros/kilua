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
import dev.kilua.BootstrapCssModule
import dev.kilua.CoreModule
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
import dev.kilua.startApplication
import dev.kilua.externals.console
import dev.kilua.utils.listOfPairs
import dev.kilua.utils.rem
import kotlinx.serialization.Serializable

@Serializable
data class UserForm(
    val firstName: String? = null,
    val lastName: String? = null,
    val username: String? = null,
    val city: String? = null,
    val state: String? = null,
    val zip: String? = null,
    val agree: Boolean = false
)

class App : Application() {

    override fun start() {

        root("root") {
            div {
                margin(20.px)
                form<UserForm>(className = "row g-3 needs-validation") {
                    val validation by validationStateFlow.collectAsState()

                    if (validation.wasValidated) {
                        if (validation.isInvalid) {
                            div("alert alert-danger") {
                                role("alert")
                                +"Form is invalid."
                            }
                        } else {
                            div("alert alert-success") {
                                role("alert")
                                +"Form is valid."
                            }
                        }
                    }

                    fieldWithLabel("First name", "form-label", groupClassName = "col-md-4") {
                        text("Mark", required = true, id = it, className = "form-control").bind(UserForm::firstName)
                        div("valid-feedback") {
                            +"Looks good!"
                        }
                    }
                    fieldWithLabel("Last name", "form-label", groupClassName = "col-md-4") {
                        text("Otto", required = true, id = it, className = "form-control").bind(UserForm::lastName)
                        div("valid-feedback") {
                            +"Looks good!"
                        }
                    }
                    fieldWithLabel(
                        "Username", "form-label", groupClassName = "col-md-4",
                        wrapperClassName = "input-group has-validation"
                    ) {
                        span("input-group-text") {
                            id("inputGroupPrepend")
                            +"@"
                        }
                        val invalidClass = if (validation[UserForm::username]?.isInvalid == true) "is-invalid" else null
                        text(required = true, id = it, className = "form-control" % invalidClass) {
                            ariaDescribedby("inputGroupPrepend")
                        }.bindWithValidationMessage(UserForm::username) { text ->
                            val result = text.value == null || text.value!!.length >= 10
                            val message = if (!result) "Username must be at least 10 characters long." else null
                            result to message
                        }
                        div("invalid-feedback") {
                            +(validation[UserForm::username]?.invalidMessage ?: "Please choose a username.")
                        }
                    }
                    fieldWithLabel("City", "form-label", groupClassName = "col-md-6") {
                        text(required = true, id = it, className = "form-control").bind(UserForm::city)
                        div("invalid-feedback") {
                            +"Please provide a valid city."
                        }
                    }
                    fieldWithLabel("State", "form-label", groupClassName = "col-md-3") {
                        select(listOfPairs("Alaska"), id = it, className = "form-select", placeholder = "Choose...")
                            .bind(UserForm::state)
                        div("invalid-feedback") {
                            +"Please select a valid state."
                        }
                    }
                    fieldWithLabel("Zip", "form-label", groupClassName = "col-md-3") {
                        text(required = true, id = it, className = "form-control").bind(UserForm::zip)
                        div("invalid-feedback") {
                            +"Please provide a valid zip."
                        }
                    }
                    div("col-12") {
                        div("form-check") {
                            fieldWithLabel("Agree to terms and conditions", "form-check-label", labelAfter = true) {
                                checkBox(className = "form-check-input", required = true, id = it).bind(UserForm::agree)
                            }
                            div("invalid-feedback") {
                                +"You must agree before submitting."
                            }
                        }
                    }
                    div("col-12") {
                        button("Submit form", className = "btn btn-primary") {
                            onClick {
                                val isValid = this@form.validate()
                                console.log("isValid = $isValid")
                                this@form.className = "row g-3 was-validated"
                                val userForm = this@form.getData()
                                console.log(userForm.toString())
                            }
                        }
                    }
//                    Dynamic validation
//                    stateFlow.onEach {
//                        this.validate()
//                    }.launchIn(KiluaScope)
                }
            }
        }
    }
}

fun main() {
    startApplication(::App, null, BootstrapCssModule, CoreModule)
}
