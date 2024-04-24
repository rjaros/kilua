/*
 * Copyright (c) 2024 Robert Jaros
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

package example

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.kilua.Application
import dev.kilua.BootstrapCssModule
import dev.kilua.BootstrapModule
import dev.kilua.CoreModule
import dev.kilua.FontAwesomeModule
import dev.kilua.TabulatorModule
import dev.kilua.TomSelectModule
import dev.kilua.compose.root
import dev.kilua.externals.console
import dev.kilua.externals.set
import dev.kilua.form.select.selectRemote
import dev.kilua.form.select.tomSelectRemote
import dev.kilua.form.text.tomTypeaheadRemote
import dev.kilua.html.px
import dev.kilua.panel.vPanel
import dev.kilua.rpc.getService
import dev.kilua.rpc.getServiceManager
import dev.kilua.rpc.types.toDecimal
import dev.kilua.startApplication
import dev.kilua.tabulator.ColumnDefinition
import dev.kilua.tabulator.Layout
import dev.kilua.tabulator.PaginationMode
import dev.kilua.tabulator.TabulatorOptions
import dev.kilua.tabulator.tabulatorRemote
import dev.kilua.types.KFile
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.serialization.serializer
import web.toJsString

class App : Application() {

    private val pingService = getService<IPingService>()

    override fun start() {

        root("root") {
            var value by remember { mutableStateOf("Hello World!") }
            vPanel(gap = 10.px) {
                +value
                margin(10.px)
                selectRemote(getServiceManager(), IPingService::dictionary, stateFunction = {
                    "Some state"
                }, requestFilter = {
                    headers["X-My-Header"] = "My value".toJsString()
                }, value = "2", placeholder = "Select value", className = "form-select")

                tomSelectRemote(getServiceManager(), IPingService::dictionaryTs, stateFunction = {
                    "Some state"
                }, requestFilter = {
                    headers["X-My-Header"] = "My value".toJsString()
                }, value = "uk", placeholder = "Select value", openOnFocus = true, preload = true, emptyOption = true)

                tomTypeaheadRemote(getServiceManager(), IPingService::suggestionList, stateFunction = {
                    "Some state"
                }, requestFilter = {
                    headers["X-My-Header"] = "My value".toJsString()
                }, placeholder = "Country")

                tabulatorRemote(
                    getServiceManager(),
                    IPingService::rowData,
                    options = TabulatorOptions(
                        layout = Layout.FitColumns,
                        pagination = true,
                        paginationMode = PaginationMode.Remote,
                        paginationSize = 5,
                        columns = listOf(
                            ColumnDefinition("Id", MyData::id.name),
                            ColumnDefinition("Name", MyData::name.name),
                        )
                    ), serializer = serializer()
                )
            }
            LaunchedEffect(Unit) {
                value = pingService.ping("Hello world from client!")
                try {
                    val data = pingService.getData(1, "")
                    console.log(data.toString())
                } catch (e: MyFirstException) {
                    console.log("MyFirstException: ${e.message}")
                } catch (e: MySecondException) {
                    console.log("MySecondException: ${e.message}")
                } catch (e: Exception) {
                    console.log("Exception: ${e.message}")
                }
                val result = pingService.getDataResult(1, "")
                result.fold(
                    onSuccess = { console.log("Success: $it") },
                    onFailure = {
                        when (it) {
                            is MyFirstException -> console.log("MyFirstException: ${it.message}")
                            is MySecondException -> console.log("MySecondException: ${it.message}")
                            else -> console.log("Error: $it")
                        }
                    }
                )
                val kiluaTypes = pingService.kiluaTypes(
                    listOf(KFile("name", 1, "content")),
                    LocalDate(2023, 1, 1),
                    LocalTime(12, 0),
                    LocalDateTime(2023, 1, 1, 12, 0),
                    1.0.toDecimal()
                )
                console.log("KiluaTypes: $kiluaTypes")
                launch {
                    pingService.wservice { sendChannel, receiveChannel ->
                        coroutineScope {
                            launch {
                                sendChannel.send(1)
                                sendChannel.send(2)
                            }
                            launch {
                                for (input in receiveChannel) {
                                    console.log(input)
                                }
                            }
                        }
                    }
                }
                launch {
                    pingService.sseConnection { receiveChannel ->
                        coroutineScope {
                            launch {
                                for (input in receiveChannel) {
                                    console.log(input)
                                }
                            }
                        }
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
        FontAwesomeModule,
        TomSelectModule,
        TabulatorModule,
        CoreModule
    )
}
