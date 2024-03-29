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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot
import dev.kilua.Application
import dev.kilua.KiluaScope
import dev.kilua.compose.root
import dev.kilua.dropdown.dropDown
import dev.kilua.externals.console
import dev.kilua.externals.get
import dev.kilua.externals.obj
import dev.kilua.form.EnumMask
import dev.kilua.form.ImaskOptions
import dev.kilua.form.MaskAutofix
import dev.kilua.form.NumberMask
import dev.kilua.form.PatternMask
import dev.kilua.form.RangeMask
import dev.kilua.form.check.checkBox
import dev.kilua.form.fieldWithLabel
import dev.kilua.form.form
import dev.kilua.form.number.imaskNumeric
import dev.kilua.form.number.numeric
import dev.kilua.form.number.range
import dev.kilua.form.select.TomSelectCallbacks
import dev.kilua.form.select.TomSelectRenders
import dev.kilua.form.select.tomSelect
import dev.kilua.form.text.richText
import dev.kilua.form.text.text
import dev.kilua.form.text.tomTypeahead
import dev.kilua.form.time.richDate
import dev.kilua.form.time.richDateTime
import dev.kilua.form.time.richTime
import dev.kilua.html.*
import dev.kilua.html.style.PClass
import dev.kilua.html.style.style
import dev.kilua.modal.FullscreenMode
import dev.kilua.modal.ModalSize
import dev.kilua.modal.confirm
import dev.kilua.modal.modal
import dev.kilua.panel.OffPlacement
import dev.kilua.panel.TabPosition
import dev.kilua.panel.accordion
import dev.kilua.panel.carousel
import dev.kilua.panel.lazyColumn
import dev.kilua.panel.offcanvas
import dev.kilua.panel.splitPanel
import dev.kilua.panel.tabPanel
import dev.kilua.panel.vPanel
import dev.kilua.popup.Placement
import dev.kilua.popup.Trigger
import dev.kilua.popup.disableTooltip
import dev.kilua.popup.enableTooltip
import dev.kilua.popup.popover
import dev.kilua.popup.toggleTooltip
import dev.kilua.popup.tooltip
import dev.kilua.progress.Progress
import dev.kilua.progress.ProgressOptions
import dev.kilua.promise
import dev.kilua.rest.RemoteRequestException
import dev.kilua.rest.RestClient
import dev.kilua.rest.callDynamic
import dev.kilua.state.collectAsState
import dev.kilua.tabulator.ColumnDefinition
import dev.kilua.tabulator.Layout
import dev.kilua.tabulator.PaginationMode
import dev.kilua.tabulator.TableType
import dev.kilua.tabulator.TabulatorOptions
import dev.kilua.tabulator.tabulator
import dev.kilua.theme.ThemeManager
import dev.kilua.theme.themeSwitcher
import dev.kilua.toast.ToastPosition
import dev.kilua.toast.toast
import dev.kilua.toastify.ToastType
import dev.kilua.utils.JsModule
import dev.kilua.utils.JsNonModule
import dev.kilua.utils.cast
import dev.kilua.utils.jsArrayOf
import dev.kilua.utils.jsObjectOf
import dev.kilua.utils.now
import dev.kilua.utils.rem
import dev.kilua.utils.toJsArray
import dev.kilua.utils.toList
import dev.kilua.utils.today
import dev.kilua.utils.useModule
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable
import web.JsAny
import web.JsArray
import web.Promise
import web.RegExp
import web.dom.CustomEvent
import web.dom.Text
import web.dom.events.Event
import web.window
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.random.nextUInt
import kotlin.time.Duration.Companion.seconds

@JsModule("./css/style.css")
@JsNonModule
external object css

@Serializable
data class Query(val q: String?)

@Serializable
data class SearchResult(val total_count: Int, val incomplete_results: Boolean)

@Serializable
data class Person(val name: String, val age: Int, val city: String)

class App : Application() {

    init {
        useModule(css)
    }

    override fun start() {

        ThemeManager.init()

        root("root") {
            div {
                margin = 20.px

                val progress = Progress(ProgressOptions(height = 10, color = Color.Lightgreen))

                button("Progress start") {
                    onClick {
                        progress.promise(Promise { resolve, reject ->
                            window.setTimeout({
                                resolve(obj()).cast()
                            }, 3000)
                        })
                    }
                }

                button("Progress end") {
                    onClick {
                        progress.end()
                    }
                }

                hr()

                val personList = remember {
                    mutableStateListOf(
                        Person("John", 30, "New York"),
                        Person("Alice", 25, "Los Angeles"),
                        Person("John", 30, "New York"),
                        Person("Alice", 25, "Los Angeles"),
                        Person("Alice", 25, "Los Angeles"),
                        Person("Alice", 25, "Los Angeles")
                    )
                }

                var heighttab by remember { mutableStateOf(300) }

                console.log("recompose before tabulator")

                tabulator(
                    personList,
                    options = TabulatorOptions(
                        height = "${heighttab}px",
                        layout = Layout.FitColumns,
                        columns = listOf(
                            ColumnDefinition("Name", "name"),
                            ColumnDefinition("Age", "age", formatterFunction = { cell, params, onRendered ->
                                cell.getValue()!!
                            }),
                            ColumnDefinition("City", "city"),
                        ), pagination = true, paginationMode = PaginationMode.Local, paginationSize = 10
                    ), types = setOf(TableType.TableBordered, TableType.TableStriped, TableType.TableSm)
                )

                button("Add row") {
                    onClick {
                        personList.add(Person("Mike", 70, "Chicago"))

                    }
                }
                button("Change height") {
                    onClick {
                        heighttab += 10
                    }
                }

                hr()

                val dynamicList = remember {
                    mutableStateListOf(
                        jsObjectOf("id" to 1, "name" to "John", "age" to 30, "city" to "New York"),
                        jsObjectOf("id" to 2, "name" to "Alice", "age" to 25, "city" to "Los Angeles"),
                        jsObjectOf("id" to 3, "name" to "Mike", "age" to 70, "city" to "Chicago"),
                    )
                }

                tabulator(
                    dynamicList, options = TabulatorOptions(
                        height = "300px",
                        layout = Layout.FitColumns,
                        columns = listOf(
                            ColumnDefinition("Name", "name"),
                            ColumnDefinition("Age", "age", formatterFunction = { cell, params, onRendered ->
                                cell.getValue()!!
                            }),
                            ColumnDefinition("City", "city"),
                        ), pagination = true, paginationMode = PaginationMode.Local, paginationSize = 10
                    ), types = setOf(TableType.TableBorderless, TableType.TableSm)
                )

                button("Add row") {
                    onClick {
                        dynamicList.add(jsObjectOf("id" to 4, "name" to "Tom", "age" to 40, "city" to "Boston"))
                    }
                }

                hr()

                val nativeList = remember {
                    jsArrayOf(
                        jsObjectOf("id" to 1, "name" to "John", "age" to 30, "city" to "New York"),
                        jsObjectOf("id" to 2, "name" to "Alice", "age" to 25, "city" to "Los Angeles"),
                        jsObjectOf("id" to 3, "name" to "Mike", "age" to 70, "city" to "Chicago"),
                    )
                }

                tabulator(
                    options = TabulatorOptions(
                        height = "300px",
                        data = nativeList,
                        layout = Layout.FitColumns,
                        columns = listOf(
                            ColumnDefinition("Name", "name"),
                            ColumnDefinition("Age", "age", formatterFunction = { cell, params, onRendered ->
                                cell.getValue()!!
                            }),
                            ColumnDefinition("City", "city"),
                        ), pagination = true, paginationMode = PaginationMode.Local, paginationSize = 10
                    ), types = setOf(TableType.TableHover, TableType.TableSm)
                )

                vPanel {
                    text {
                        maskOptions = ImaskOptions(pattern = PatternMask("000-000-000"))
                    }
                    text {
                        maskOptions = ImaskOptions(pattern = PatternMask("000-000-000", eager = true))
                    }
                    text {
                        maskOptions = ImaskOptions(pattern = PatternMask("000-000-000", lazy = false, eager = true))
                    }
                    text {
                        maskOptions = ImaskOptions(
                            pattern = PatternMask(
                                "{Ple\\ase fill ye\\ar 19}YY{, month }MM{ \\and v\\alue }VL",
                                lazy = false,
                                blocks = mapOf(
                                    "YY" to ImaskOptions(pattern = PatternMask("00")),
                                    "MM" to ImaskOptions(range = RangeMask(1, 12)),
                                    "VL" to ImaskOptions(enum = EnumMask(listOf("TV", "HD", "VR")))
                                )
                            )
                        )
                        onChange {
                            console.log(this.value)
                        }
                    }
                    text {
                        maskOptions = ImaskOptions(range = RangeMask(0, 100, maxLength = 3, autofix = MaskAutofix.Pad))
                    }
                    numeric(123.44, placeholder = "Enter a number") {
                        maskOptions = ImaskOptions(
                            number = NumberMask(
                                scale = 2,
                                padFractionalZeros = true,
                                normalizeZeros = true,
                                min = -10000,
                                max = 10000,
                            )
                        )
                        onInput {
                            console.log(this.value?.toString())
                        }
                    }
                    text {
                        maskOptions = ImaskOptions(regExp = RegExp("^[0-9]*$"))
                    }
                    text {
                        maskOptions = ImaskOptions(function = { it.startsWith("1") })
                        onInput {
                            console.log(this.value)
                        }
                    }
                    imaskNumeric(123.45) {
                        onInput {
                            console.log(this.value.toString())
                        }
                    }
                }

                hr()

                var ttdis by remember { mutableStateOf(false) }
                var ttid by remember { mutableStateOf("a") }

                val tt = tomTypeahead(
                    listOf("Alaska", "California", "Nevada", "Oregon", "Washington"),
                    placeholder = "enter",
                    disabled = ttdis,
                    id = ttid
                )

                button("get value") {
                    onClick {
                        console.log(tt.value)
                    }
                }
                button("set value") {
                    onClick {
                        tt.value = "New York"
                    }
                }
                button("set null") {
                    onClick {
                        tt.value = null
                    }
                }
                button("toggle disabled") {
                    onClick {
                        ttdis = !ttdis
                    }
                }
                button("change id") {
                    onClick {
                        ttid += "a"
                    }
                }

                hr()

                val restClient = RestClient()

                console.log("recomposing before tom select")

                tomSelect(emptyOption = true) {
                    emptyOption = true
                    tsCallbacks = TomSelectCallbacks(
                        load = { query, callback ->
                            promise {
                                val result = try {
                                    restClient.callDynamic("https://api.github.com/search/repositories") {
                                        data = jsObjectOf("q" to query)
                                        resultTransform = { it?.get("items") }
                                    }
                                } catch (e: RemoteRequestException) {
                                    console.log(e.toString())
                                    null
                                }
                                result?.let { items: JsAny ->
                                    callback(items.cast<JsArray<JsAny>>().toList().map { item ->
                                        jsObjectOf(
                                            "value" to item["id"]!!,
                                            "text" to item["name"]!!,
                                            "subtext" to item["owner"]!!["login"]!!
                                        )
                                    }.toJsArray())
                                } ?: callback(jsArrayOf())
                                obj()
                            }
                        },
                        shouldLoad = { it.length >= 3 }
                    )
                    tsRenders = TomSelectRenders(option = { item, escape ->
                        val subtext: String? = item["subtext"]?.toString()
                        """
                        <div>
                            <span class="title">${escape(item["text"].toString())}</span>
                            <small>${subtext?.let { "(" + escape(it) + ")" } ?: ""}</small>
                        </div>
                    """.trimIndent()
                    })
                    onChange {
                        console.log(this.value)
                    }
                }

                hr()

                var tsvalue by remember { mutableStateOf<String?>("dog") }
                var multi by remember { mutableStateOf(true) }
                var tsdis by remember { mutableStateOf(false) }

                val tselect = tomSelect(
                    listOf("cat" to "Cat", "dog" to "Dog", "mouse" to "Mouse"),
                    value = tsvalue,
                    placeholder = "Select an option",
                    emptyOption = true,
                    multiple = multi,
                    disabled = tsdis,
                    id = "test"
                ) {
                    LaunchedEffect(Unit) {
                        stateFlow.onEach {
                            console.log(it)
                        }.launchIn(KiluaScope)
                    }
                }

                button("Get value") {
                    onClick {
                        console.log(tselect.value)
                        console.log(tselect.selectedLabel)
                    }
                }
                button("Set value") {
                    onClick {
                        tselect.value = "mouse"
                    }
                }
                button("Set null") {
                    onClick {
                        tselect.value = null
                    }
                }
                button("Toggle disabled") {
                    onClick {
                        tsdis = !tsdis
                    }
                }

                hr()

                val letterIndexes = List(26) { it }

                val randomElements = remember { mutableStateListOf<UInt>() }

                div {
                    border = Border(1.px, style = BorderStyle.Solid, color = Color.Red)
                    height = 600.px
                    overflow = Overflow.Auto

                    lazyColumn {
                        item {
                            textNode("First element")
                        }

                        items(200) {
                            textNode("Element #$it")
                        }

                        item {
                            button("Generate random elements") {
                                onClick {
                                    Snapshot.withMutableSnapshot {
                                        randomElements.clear()
                                        repeat(Random.nextInt(10..100)) {
                                            randomElements.add(Random.nextUInt())
                                        }
                                    }
                                }
                            }
                        }

                        items(randomElements) {
                            textNode("Random value: $it")
                        }

                        items(letterIndexes) {
                            val lowerCase = it + 'a'.code

                            textNode("Letter ${lowerCase.toChar()}")
                        }

                        items(letterIndexes) {
                            val upperCase = it + 'A'.code

                            textNode("Letter ${upperCase.toChar()}")
                        }
                    }
                }

                var dtInline by remember { mutableStateOf(false) }

                val rd = richDateTime(
                    now(),
                    name = "data",
                    placeholder = "Podaj datę",
                    inline = dtInline,
                    id = "test"
                ) {
                    onChange {
                        console.log(this.getValueAsString())
                    }
                }

                richDate(today()) {
                    onChange {
                        console.log(this.getValueAsString())
                    }
                }

                richTime(now().time) {
                    onChange {
                        console.log(this.getValueAsString())
                    }
                }

                button("Toggle inline") {
                    onClick {
                        dtInline = !dtInline
                    }
                }

                button("Get value") {
                    onClick {
                        console.log(rd.value.toString())
                    }
                }

                button("Set value") {
                    onClick {
                        rd.value = now()
                    }
                }

                button("Set null") {
                    onClick {
                        rd.value = null
                    }
                }

                hr()

                button("Show toastify msg") {
                    onClick {
                        dev.kilua.toastify.toast(
                            "Test toastify",
                            type = ToastType.Danger,
                            duration = 30.seconds,
                            close = true
                        )
                    }
                }

                button("Test REST Client") {
                    onClick {
                        promise {
                            RestClient().callDynamic<Query>(
                                "https://api.github.com/search/repositories",
                                Query("kvision")
                            ) {
                                this.resultTransform = { it!!["total_count"] }
                            }
                        }.then {
                            console.log(it)
                            obj()
                        }
                    }
                }

                hr()

                dropDown("A dropdown", "fas fa-search", style = ButtonStyle.BtnDanger, arrowVisible = false) {
                    li {
                        link("#", "Link 1", className = "dropdown-item")
                    }
                    li {
                        link("#", "Link 2", className = "dropdown-item")
                    }
                    li {
                        dropDown("An inner dropdown", innerDropDown = true) {
                            li {
                                link("#", "Link 3", className = "dropdown-item")
                            }
                            li {
                                link("#", "Link 4", className = "dropdown-item")
                            }
                        }
                    }
                }

                hr()

                var positionIndex by remember { mutableStateOf(0) }
                val toastPosition = ToastPosition.entries[positionIndex % ToastPosition.entries.size]

                button("Show toast") {
                    onClick {
                        toast("Test toast", position = toastPosition, color = BsColor.TextBgSuccess)
                    }
                }
                button("test toast") {
                    onClick {
                        positionIndex++
                    }
                }

                hr()

                themeSwitcher(style = ButtonStyle.BtnSuccess, round = true)

                hr()

                val off =
                    offcanvas(
                        "Test offcanvas",
                        OffPlacement.OffcanvasEnd,
                        closeButton = true,
                        bodyScrolling = true,
                        backdrop = true,
                        escape = true
                    ) {
                        pt(
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam quis risus eget urna mollis ornare vel eu leo. " +
                                    "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nullam id dolor id nibh " +
                                    "ultricies vehicula ut id elit. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo " +
                                    "cursus magna, vel scelerisque nisl consectetur et. Fusce dapibus, tellus ac cursus commodo, tortor mauris " +
                                    "condimentum nibh, ut fermentum massa justo sit amet risus. Duis mollis, est non commodo luctus, nisi erat " +
                                    "porttitor ligula, eget lacinia odio sem nec elit. Aenean lacinia bibendum nulla sed consectetur. Praesent " +
                                    "commodo cursus magna, vel scelerisque nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non " +
                                    "metus auctor fringilla. Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac " +
                                    "facilisis in, egestas eget quam. Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Praesent " +
                                    "commodo cursus magna, vel scelerisque nisl consectetur et. Cras mattis consectetur purus sit amet fermentum. " +
                                    "Cras justo odio, dapibus ac facilisis in, egestas eget quam. Morbi leo risus, porta ac consectetur ac, " +
                                    "vestibulum at eros. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Cras mattis " +
                                    "consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Morbi " +
                                    "leo risus, porta ac consectetur ac, vestibulum at eros. Praesent commodo cursus magna, vel scelerisque " +
                                    "nisl consectetur et. Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac " +
                                    "facilisis in, egestas eget quam. Mor"
                        )
                    }

                var tooltip by remember { mutableStateOf("Test") }

                val bsButton = bsButton("toggle offcanvas") {
                    onClick {
                        off.toggle()
                    }
                    if (tooltip == "TestX") {
                        tooltip(tooltip, placement = Placement.Bottom, delay = 2.seconds)
                    } else {
                        popover(tooltip, tooltip, placement = Placement.Right, triggers = listOf(Trigger.Hover))
                    }
                }

                bsButton("change tooltip") {
                    onClick {
                        tooltip += "X"
                    }
                }

                bsButton("toggle tooltip") {
                    onClick {
                        bsButton.toggleTooltip()
                    }
                }


                bsButton("enable tooltip") {
                    onClick {
                        bsButton.enableTooltip()
                    }
                }

                bsButton("disable tooltip") {
                    onClick {
                        bsButton.disableTooltip()
                    }
                }

                hr()

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
                    escape = true
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
                            width = 20.perc
                            pt("left$splitState")
                        }
                        right {
                            pt("right$splitState")
                        }
                    } else {
                        left {
                            width = 50.perc
                            pt("top$splitState")
                        }
                        right {
                            width = 50.perc
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

                div {
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
                            tomTypeahead(
                                listOf("Alaska", "California"),
                                placeholder = "Choose...",
                                id = it,
                                required = true
                            ).bind("state")
                            div("invalid-feedback") {
                                +"Please select a valid state."
                            }
                            /*                            tomSelect(
                                                            listOfPairs("Alaska", "California"),
                                                            emptyOption = true,
                                                            placeholder = "Choose...",
                                                            id = it,
                                                            multiple = true,
                                                            className = "form-select",
                                                            required = true
                                                        ).bind("state")
                                                        div("invalid-feedback") {
                                                            +"Please select a valid state."
                                                        }*/

                            /*richDate(
                                placeholder = "Choose...",
                                id = it,
                                required = true
                            ).bind("state")
                            div("invalid-feedback") {
                                +"Please select a valid state."
                            }*/

                            /*select(listOfPairs("Alaska"), className = "form-select", placeholder = "Choose...")
                                .bind("state").also {
                                    div("invalid-feedback") {
                                        +"Please select a valid state."
                                    }
                                }*/
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

            console.log("recomposing")
            val x = tag("address", "address3") {
                +"address23"
                setStyle("color", "red")
            }

            var size by remember { mutableStateOf(1) }
            var evenSize by remember { mutableStateOf(1) }
            var oddSize by remember { mutableStateOf(1) }
            var tn by remember { mutableStateOf("address") }
            var list by remember { mutableStateOf(listOf("cat", "dog", "mouse")) }
            var type by remember { mutableStateOf(ButtonType.Button) }

            div {
                for (name in list) {
//                    key(name) {
                    div {
                        +name
                        button {
                            +"click"
                            DisposableEffect("button") {
                                val f = { _: Event ->
                                    console.log("click $name of ${list.size}")
                                }
                                element.addEventListener("click", f)
                                onDispose {
                                    element.removeEventListener("click", f)
                                }
                            }
                        }
                        //                      }
                    }
                }
            }
            val xb = button("add $size") {
                onClick {
                    list = list + "test"
                }
            }
            button {
                +"remove"
                onClick {
                    list = list.filterIndexed { index, s -> index != 1 }
                    xb.textContent = "changed label"
                }
            }

            tag(tn) {
                +"address2 $size"
                id = "test"
                title = "Some title"
                role = "button"
                tabindex = 5
                draggable = true
                setAttribute("aria-label", "Ala ma kota")
            }
            val x2 = text("Ala ma kota")
            button(type = type) {
                +"test span"
                onClick {
                    disabled = !(disabled ?: false)
                    window.setTimeout({
                        disabled = !(disabled ?: false)
                        obj()
                    }, 1000)
                }
            }

            if (size % 2 == 0) {
                div {
                    +"even $evenSize"
                    button {
                        +"button even$evenSize"
                        onClick {
                            evenSize++
                        }
                    }
                }
            } else {
                div {
                    +"odd $oddSize"
                    button {
                        +"button odd$oddSize"
                        onClick {
                            oddSize++
                        }
                    }
                }
            }

            lateinit var divB: Div
            div {
                +"$size"
                divB = div {
                    +"b"
                    div {
                        +"c"
                        button(disabled = (size % 4 == 0)) {
                            +"button1"
                            onClick {
                                divB.textContent = "test"
                                size++
                            }
                        }
                        if (size % 2 == 0) {
                            button {
                                +"button2"
                                onClick {
                                    size++
                                }
                                DisposableEffect("button2") {
                                    val f = { _: Event ->
                                        console.log("button2 click")
                                    }
                                    element.addEventListener("click", f)
                                    onDispose {
                                        element.removeEventListener("click", f)
                                    }
                                }
                            }
                        }
                        for (i in 0 until size) {
                            div {
                                +"$i"
                            }
                        }
                    }
                }
                DisposableEffect("code") {
                    element.firstChild?.cast<Text>()?.data = "ala ma kota"
                    onDispose { }
                }
            }

        }
    }
}
