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

package dev.kilua.form

import dev.kilua.test.DomSpec
import dev.kilua.compose.root
import dev.kilua.form.check.CheckBox
import dev.kilua.form.check.checkBox
import dev.kilua.form.check.checkBoxRef
import dev.kilua.form.text.Text
import dev.kilua.form.text.text
import dev.kilua.form.text.textRef
import dev.kilua.types.KFile
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@Serializable
data class DataForm(
    val string: String? = null,
    val boolean: Boolean? = null,
    val booleanStrict: Boolean = false,
    val int: Int? = null,
    val double: Double? = null,
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val dateTime: LocalDateTime? = null,
    val files: List<KFile>? = null,
    val string2: String? = null
)

class FormSpec : DomSpec {

    @Test
    fun render() {
        runWhenDomAvailable {
            val root = root("test") {
                form<DataForm>(
                    method = FormMethod.Get,
                    action = "/action",
                    enctype = FormEnctype.Multipart,
                ) {
                    text {
                        bind(DataForm::string) { it.value != null }
                    }
                    checkBox {
                        bind(DataForm::boolean)
                    }
                }
            }
            assertEqualsHtml(
                """<form method="get" action="/action" enctype="multipart/form-data" novalidate><input type="text"><input type="checkbox"></form>""",
                root.element.innerHTML,
                "Should render form element to DOM"
            )
        }
    }

    @Test
    fun renderToString() {
        run {
            val root = root {
                form<DataForm>(
                    method = FormMethod.Get,
                    action = "/action",
                    enctype = FormEnctype.Multipart,
                ) {
                    text {
                        bind(DataForm::string)
                    }
                    checkBox {
                        bind(DataForm::boolean) { it.value }
                    }
                }
            }
            assertEqualsHtml(
                """<form method="get" action="/action" enctype="multipart/form-data" novalidate><input type="text"><input type="checkbox"></form>""",
                root.innerHTML,
                "Should render form element to a String"
            )
        }
    }

    @Test
    fun getData() {
        run {
            val form = Form.create<DataForm>()
            val initialData = form.getData()
            assertEquals(DataForm(), initialData, "Form should return empty class as initial data")
            val data = DataForm(string = "Test value")
            form.setData(data)
            val result = form.getData()
            assertEquals("Test value", result.string, "Form should return initial value without adding any control")
            assertEquals(data, result)
        }

    }

    @Test
    fun getControl() {
        run {
            lateinit var form: Form<DataForm>
            root {
                form = formRef<DataForm> {
                    text {
                        bind(DataForm::string)
                    }
                }
            }
            val control = form.getControl(DataForm::double)
            assertNull(control, "Should return null when there is no such control")
            val control2 = form.getControl(DataForm::string)
            assertNotNull(control2, "Should return correct control")
        }
    }

    @Test
    fun get() {
        run {
            lateinit var form: Form<DataForm>
            root {
                form = formRef<DataForm> {
                    text {
                        bind(DataForm::string)
                    }
                }
            }
            val data = DataForm(string = "Test value")
            val b = form[DataForm::boolean]
            assertNull(b, "Should return null value when there is no added control")
            val a = form[DataForm::string]
            assertNull(a, "Should return null value when control is empty")
            form.setData(data)
            val a2 = form[DataForm::string]
            assertEquals("Test value", a2, "Should return correct value")
        }
    }

    @Test
    fun validate() {
        run {
            lateinit var form: Form<DataForm>
            root {
                form = formRef<DataForm> {
                    text {
                        bind(DataForm::string) {
                            (it.value?.length ?: 0) > 4
                        }
                    }
                    text(required = true) {
                        bind(DataForm::string2)
                    }
                }
            }
            form.setData(DataForm(string = "123"))
            val valid = form.validate()
            assertEquals(false, valid, "Should be invalid with initial data")
            form.setData(DataForm(string = "12345"))
            val valid2 = form.validate()
            assertEquals(false, valid2, "Should be invalid with partially changed data")
            form.setData(DataForm(string = "12345", string2 = "abc"))
            val valid3 = form.validate()
            assertEquals(true, valid3, "Should be valid")
        }
    }

    @Test
    fun emptDynamicForm() {
        run {
            lateinit var form: Form<Map<String, Any?>>
            root {
                form = formRef {
                }
            }
            val data = mapOf("a" to "Test value", "b" to true, "c" to 5)
            form.setData(data)
            val result = form.getData()
            assertEquals(data, result, "Dynamic form should return initial value without adding any control")
        }
    }

    @Test
    fun dynamicForm() {
        run {
            lateinit var form: Form<Map<String, Any?>>
            lateinit var textField: Text
            lateinit var check: CheckBox
            root {
                form = formRef {
                    textField = textRef {
                        bind("a")
                    }
                    check = checkBoxRef {
                        bind("b")
                    }
                }
            }
            val data = mapOf("a" to "Test value", "b" to true, "c" to 5)
            form.setData(data)
            val result2 = form.getData()
            assertEquals(data, result2, "Dynamic form should return initial value after adding a control")
            assertEquals(data["a"], textField.value, "Text field value should be set correctly")
            assertEquals(data["b"], check.value, "CheckBox field value should be set correctly")
            textField.value = "Test value 2"
            check.value = false
            val result3 = form.getData()
            assertEquals(
                mapOf("a" to "Test value 2", "b" to false, "c" to 5),
                result3,
                "Dynamic form should return changed value"
            )
        }
    }

}
