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

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.CoreModule
import dev.kilua.KiluaScope
import dev.kilua.compose.root
import dev.kilua.form.check.checkBox
import dev.kilua.form.check.triStateCheckBox
import dev.kilua.form.form
import dev.kilua.form.number.numeric
import dev.kilua.form.number.spinner
import dev.kilua.form.text.text
import dev.kilua.form.time.date
import dev.kilua.form.time.dateTime
import dev.kilua.form.time.time
import dev.kilua.form.upload.upload
import dev.kilua.html.Border
import dev.kilua.html.BorderStyle
import dev.kilua.html.button
import dev.kilua.html.div
import dev.kilua.html.hr
import dev.kilua.html.px
import dev.kilua.startApplication
import dev.kilua.state.collectAsState
import dev.kilua.types.KFile
import dev.kilua.utils.JsNonModule
import dev.kilua.utils.console
import dev.kilua.utils.hour
import dev.kilua.utils.log
import dev.kilua.utils.now
import dev.kilua.utils.today
import dev.kilua.utils.useCssModule
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@JsModule("./css/style.css")
@JsNonModule
public external object Css
class ObjectId(val id: Int) {
    override fun toString(): String {
        return "$id"
    }
}

object ObjectIdSerializer : KSerializer<ObjectId> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ObjectId")
    override fun deserialize(decoder: Decoder): ObjectId {
        val str = decoder.decodeString()
        return ObjectId(str.toInt())
    }

    override fun serialize(encoder: Encoder, value: ObjectId) {
        encoder.encodeString(value.id.toString())
    }
}


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
    @Contextual val objectId: ObjectId? = null
)

public class App : Application() {

    init {
        useCssModule(Css)
    }

    override fun start() {

        root("root") {
            console.log("recomposing")

            div {
                val value by spinner(1, min = 1).collectAsState()
                div {
                    console.log("recomposing div")
                    div {
                        console.log("recomposing div2")
                        border = Border(1.px, BorderStyle.Solid)
                        width = (value ?: 0).px
                        height = 100.px
                    }
                }
            }


            var dataForm by remember {
                mutableStateOf(
                    DataForm(
                        string = "Test value",
                        boolean = true,
                        booleanStrict = true,
                        int = 123,
                        double = 123.456,
                        date = today(),
                        time = hour(),
                        dateTime = now(),
                        files = listOf(KFile("test.txt", 123, "ABC")),
                        objectId = ObjectId(123)
                    )
                )
            }

            var visible by remember { mutableStateOf(true) }
            if (visible) {
                val form = form<DataForm>(dataForm, customSerializers = mapOf(ObjectId::class to ObjectIdSerializer)) {
                    text().bind(DataForm::string)
                    triStateCheckBox().bind(DataForm::boolean)
                    checkBox().bind(DataForm::booleanStrict)
                    spinner().bind(DataForm::int)
                    numeric().bind(DataForm::double)
                    date().bind(DataForm::date)
                    time().bind(DataForm::time)
                    dateTime().bind(DataForm::dateTime)
                    upload().bind(DataForm::files)
                    text().bindCustom(DataForm::objectId)
                    DisposableEffect("stateFlow") {
                        val job = stateFlow.onEach {
                            console.log("form changed")
                            console.log(it.toString())
                        }.launchIn(KiluaScope)
                        onDispose {
                            console.log("form disposed")
                            job.cancel()
                        }
                    }
                }
                val initialData = form.getData()
                //            console.log(initialData.toString())
                /*val data = DataForm(
                    string = "Test value",
                    boolean = true,
                    booleanStrict = true,
                    int = 123,
                    double = 123.456,
                    date = today(),
                    time = hour(),
                    dateTime = now(),
                    files = listOf(KFile("test.txt", 123, "ABC")),
                    objectId = ObjectId(123)
                )
                form.setData(data)*/
                //            console.log("xxx")
                button("test 1") {
                    onClick {
                        val result = form.getData()
                        console.log(result.toString())
                        console.log(result.objectId?.id.toString())
                        console.log(form.getDataJson())
                    }
                }
            }
            button("test 1") {
                onClick {
                    dataForm = DataForm(string = "baba jaga", boolean = false, double = 5.5)
                }
            }
            button("test 2") {
                onClick {
                    dataForm = DataForm(string = "ala ma kota", boolean = true, double = 6.0)
                }
            }
            button("test 3") {
                onClick {
                    visible = !visible
                }
            }

            hr()

            val form2 = form {
/*                text().bind("string")
                triStateCheckBox().bind("boolean")
                checkBox().bind("booleanStrict")
                spinner().bind("int")
                numeric().bind("double")
                date().bind("date")
                time().bind("time")
                dateTime().bind("dateTime")
                upload().bind("files")*/
            }
            val initialData2 = form2.getData()
//            console.log(initialData2.toString())
            val data2 = mapOf(
                "string" to "Test value",
                "boolean" to true,
                "booleanStrict" to true,
                "int" to 123,
                "double" to 123.456,
                "date" to today(),
                "time" to hour(),
                "dateTime" to now(),
                "files" to listOf(KFile("test.txt", 123, "ABC"))
            )
            form2.setData(data2)
//            console.log("xxx")
            button("test2 1") {
                onClick {
                    val result = form2.getData()
                    console.log(result.toString())
                }
            }
            button("test2 2") {
                onClick {
                    form2.setData(mapOf("string" to "Test value 2", "boolean" to false, "time" to hour()))
                }
            }
            button("test2 3") {
                onClick {
                    form2.clearData()
                }
            }

        }
    }
}

public fun main() {
    startApplication(::App, null, CoreModule)
}
