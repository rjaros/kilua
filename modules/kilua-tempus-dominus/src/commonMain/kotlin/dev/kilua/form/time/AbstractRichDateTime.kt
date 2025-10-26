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

package dev.kilua.form.time

import androidx.compose.runtime.Composable
import androidx.compose.runtime.NoLiveLiterals
import dev.kilua.core.RenderConfig
import dev.kilua.externals.TempusDominus
import dev.kilua.externals.TempusDominusOptions
import dev.kilua.externals.tempusDominusLocales
import dev.kilua.form.Autocomplete
import dev.kilua.form.text.Text
import dev.kilua.form.text.textRef
import dev.kilua.html.Div
import dev.kilua.html.IDiv
import dev.kilua.html.i
import dev.kilua.html.px
import dev.kilua.html.span
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager
import dev.kilua.utils.jsObjectOf
import dev.kilua.utils.jsSet
import dev.kilua.utils.obj
import dev.kilua.utils.rem
import dev.kilua.utils.toDate
import dev.kilua.utils.toJsArray
import dev.kilua.utils.toKebabCase
import dev.kilua.utils.unsafeCast
import js.core.JsPrimitives.toJsInt
import js.date.Date
import js.intl.DateTimeFormat
import kotlinx.datetime.LocalDate
import web.dom.document
import web.events.Event
import web.events.EventType
import web.events.addEventListener
import web.events.removeEventListener
import kotlin.js.JsAny
import kotlin.js.toJsString
import kotlin.time.Duration

/**
 * Tempus Dominus component color themes.
 */
public enum class Theme {
    Light,
    Dark,
    Auto;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Tempus Dominus component view modes.
 */
public enum class ViewMode {
    Clock,
    Calendar,
    Months,
    Years,
    Decades;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Tempus Dominus component toolbar placements.
 */
public enum class ToolbarPlacement {
    Top,
    Bottom;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Tempus Dominus component month header format.
 */
public enum class MonthHeaderFormat(public val value: String) {
    TWODIGIT("2-digit"),
    NUMERIC("numeric"),
    NARROW("narrow"),
    SHORT("short"),
    LONG("long");

    override fun toString(): String {
        return value
    }
}

/**
 * Tempus Dominus component year header format.
 */
public enum class YearHeaderFormat(public val value: String) {
    TWODIGIT("2-digit"),
    NUMERIC("numeric");

    override fun toString(): String {
        return value
    }
}

/**
 * Tempus Dominus hour cycle.
 */
public enum class HourCycle {
    H11,
    H12,
    H23,
    H24;

    public val value: String = name.toKebabCase()
    override fun toString(): String {
        return value
    }
}

/**
 * Tempus Dominus abstract rich date time component.
 */
public abstract class AbstractRichDateTime(
    disabled: Boolean? = null,
    required: Boolean? = null,
    format: String,
    inline: Boolean = false,
    locale: Locale = LocaleManager.currentLocale,
    className: String? = null,
    id: String? = null,
    renderConfig: RenderConfig = RenderConfig.Default,
) : Div(className, id, renderConfig = renderConfig), IAbstractRichDateTime {

    private val initialFormat: String = format

    public override var format: String by updatingProperty(format) {
        refresh()
    }

    @Composable
    public override fun format(format: String): Unit = composableProperty("format", {
        this.format = initialFormat
    }) {
        this.format = format
    }

    /**
     * Date/time format with auto-detected hour cycle.
     */
    internal val inputFormat: String
        get() {
            val hourCycle = hourCycle ?: guessHourCycle(locale.language)
            return if (hourCycle == HourCycle.H11 || hourCycle == HourCycle.H12) {
                format.replace("HH:mm:ss", "hh:mm:ss T").replace("HH:mm", "hh:mm T")
            } else format
        }

    /**
     * Inline also modifies the layout, so it is not exposed as a public property.
     */
    internal open var inline: Boolean by updatingProperty(inline) {
        refresh()
    }

    public override var locale: Locale by updatingProperty(locale) {
        refresh()
    }

    @Composable
    public override fun locale(locale: Locale): Unit = composableProperty("locale", {
        this.locale = LocaleManager.currentLocale
    }) {
        this.locale = locale
    }

    public override var disabled: Boolean? by updatingProperty(disabled) {
        if (it == true) {
            tempusDominusInstance?.disable()
        } else {
            tempusDominusInstance?.enable()
        }
    }

    @Composable
    public override fun disabled(disabled: Boolean?): Unit = composableProperty("disabled", {
        this.disabled = null
    }) {
        this.disabled = disabled
    }

    public override var required: Boolean? by updatingProperty(required) {
        inputText?.required = it
    }

    @Composable
    public override fun required(required: Boolean?): Unit = composableProperty("required", {
        this.required = null
    }) {
        this.required = required
    }

    public override var name: String? by updatingProperty {
        inputText?.name = it
    }

    @Composable
    public override fun name(name: String?): Unit = composableProperty("name", {
        this.name = null
    }) {
        this.name = name
    }

    public override var autocomplete: Autocomplete? by updatingProperty {
        inputText?.autocomplete = it
    }

    @Composable
    public override fun autocomplete(autocomplete: Autocomplete?): Unit = composableProperty("autocomplete", {
        this.autocomplete = null
    }) {
        this.autocomplete = autocomplete
    }

    /**
     * The component custom validity message.
     */
    public open var customValidity: String? by updatingProperty()

    public override var daysOfWeekDisabled: List<Int>? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun daysOfWeekDisabled(daysOfWeekDisabled: List<Int>?): Unit =
        composableProperty("daysOfWeekDisabled", {
            this.daysOfWeekDisabled = null
        }) {
            this.daysOfWeekDisabled = daysOfWeekDisabled
        }

    public override var showClear: Boolean by updatingProperty(true) {
        refresh()
    }

    @Composable
    public override fun showClear(showClear: Boolean): Unit = composableProperty("showClear", {
        this.showClear = true
    }) {
        this.showClear = showClear
    }

    public override var showClose: Boolean by updatingProperty(true) {
        refresh()
    }

    @Composable
    public override fun showClose(showClose: Boolean): Unit = composableProperty("showClose", {
        this.showClose = true
    }) {
        this.showClose = showClose
    }

    public override var showToday: Boolean by updatingProperty(true) {
        refresh()
    }

    @Composable
    public override fun showToday(showToday: Boolean): Unit = composableProperty("showToday", {
        this.showToday = true
    }) {
        this.showToday = showToday
    }

    public override var stepping: Int by updatingProperty(1) {
        refresh()
    }

    @Composable
    public override fun stepping(stepping: Int): Unit = composableProperty("stepping", {
        this.stepping = 1
    }) {
        this.stepping = stepping
    }

    public override var minDate: LocalDate? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun minDate(minDate: LocalDate?): Unit = composableProperty("minDate", {
        this.minDate = null
    }) {
        this.minDate = minDate
    }

    public override var maxDate: LocalDate? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun maxDate(maxDate: LocalDate?): Unit = composableProperty("maxDate", {
        this.maxDate = null
    }) {
        this.maxDate = maxDate
    }

    public override var sideBySide: Boolean by updatingProperty(false) {
        refresh()
    }

    @Composable
    public override fun sideBySide(sideBySide: Boolean): Unit = composableProperty("sideBySide", {
        this.sideBySide = false
    }) {
        this.sideBySide = sideBySide
    }

    public override var enabledDates: List<LocalDate>? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun enabledDates(enabledDates: List<LocalDate>?): Unit = composableProperty("enabledDates", {
        this.enabledDates = null
    }) {
        this.enabledDates = enabledDates
    }

    public override var disabledDates: List<LocalDate>? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun disabledDates(disabledDates: List<LocalDate>?): Unit = composableProperty("disabledDates", {
        this.disabledDates = null
    }) {
        this.disabledDates = disabledDates
    }

    public override var enabledHours: List<Int>? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun enabledHours(enabledHours: List<Int>?): Unit = composableProperty("enabledHours", {
        this.enabledHours = null
    }) {
        this.enabledHours = enabledHours
    }

    public override var disabledHours: List<Int>? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun disabledHours(disabledHours: List<Int>?): Unit = composableProperty("disabledHours", {
        this.disabledHours = null
    }) {
        this.disabledHours = disabledHours
    }

    public override var keepOpen: Boolean by updatingProperty(false) {
        refresh()
    }

    @Composable
    public override fun keepOpen(keepOpen: Boolean): Unit = composableProperty("keepOpen", {
        this.keepOpen = false
    }) {
        this.keepOpen = keepOpen
    }

    public override var theme: Theme? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun theme(theme: Theme?): Unit = composableProperty("theme", {
        this.theme = null
    }) {
        this.theme = theme
    }

    public override var allowInputToggle: Boolean by updatingProperty(false) {
        refresh()
    }

    @Composable
    public override fun allowInputToggle(allowInputToggle: Boolean): Unit = composableProperty("allowInputToggle", {
        this.allowInputToggle = false
    }) {
        this.allowInputToggle = allowInputToggle
    }

    public override var viewDate: LocalDate? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun viewDate(viewDate: LocalDate?): Unit = composableProperty("viewDate", {
        this.viewDate = null
    }) {
        this.viewDate = viewDate
    }

    public override var promptTimeOnDateChange: Boolean by updatingProperty(false) {
        refresh()
    }

    @Composable
    public override fun promptTimeOnDateChange(promptTimeOnDateChange: Boolean): Unit =
        composableProperty("promptTimeOnDateChange", {
            this.promptTimeOnDateChange = false
        }) {
            this.promptTimeOnDateChange = promptTimeOnDateChange
        }

    public override var promptTimeOnDateChangeTransitionDelay: Duration? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun promptTimeOnDateChangeTransitionDelay(promptTimeOnDateChangeTransitionDelay: Duration?): Unit =
        composableProperty("promptTimeOnDateChangeTransitionDelay", {
            this.promptTimeOnDateChangeTransitionDelay = null
        }) {
            this.promptTimeOnDateChangeTransitionDelay = promptTimeOnDateChangeTransitionDelay
        }

    public override var viewMode: ViewMode? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun viewMode(viewMode: ViewMode?): Unit = composableProperty("viewMode", {
        this.viewMode = null
    }) {
        this.viewMode = viewMode
    }

    public override var toolbarPlacement: ToolbarPlacement? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun toolbarPlacement(toolbarPlacement: ToolbarPlacement?): Unit =
        composableProperty("toolbarPlacement", {
            this.toolbarPlacement = null
        }) {
            this.toolbarPlacement = toolbarPlacement
        }

    public override var monthHeaderFormat: MonthHeaderFormat? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun monthHeaderFormat(monthHeaderFormat: MonthHeaderFormat?): Unit =
        composableProperty("monthHeaderFormat", {
            this.monthHeaderFormat = null
        }) {
            this.monthHeaderFormat = monthHeaderFormat
        }

    public override var yearHeaderFormat: YearHeaderFormat? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun yearHeaderFormat(yearHeaderFormat: YearHeaderFormat?): Unit =
        composableProperty("yearHeaderFormat", {
            this.yearHeaderFormat = null
        }) {
            this.yearHeaderFormat = yearHeaderFormat
        }

    public override var customIcons: DateTimeIcons? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun customIcons(customIcons: DateTimeIcons?): Unit =
        composableProperty("customIcons", {
            this.customIcons = null
        }) {
            this.customIcons = customIcons
        }

    public override var keepInvalid: Boolean by updatingProperty(false) {
        refresh()
    }

    @Composable
    public override fun keepInvalid(keepInvalid: Boolean): Unit = composableProperty("keepInvalid", {
        this.keepInvalid = false
    }) {
        this.keepInvalid = keepInvalid
    }

    public override var container: JsAny? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun container(container: JsAny?): Unit = composableProperty("container", {
        this.container = null
    }) {
        this.container = container
    }

    public override var meta: JsAny? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun meta(meta: JsAny?): Unit = composableProperty("meta", {
        this.meta = null
    }) {
        this.meta = meta
    }

    public override var calendarWeeks: Boolean by updatingProperty(false) {
        refresh()
    }

    @Composable
    public override fun calendarWeeks(calendarWeeks: Boolean): Unit = composableProperty("calendarWeeks", {
        this.calendarWeeks = false
    }) {
        this.calendarWeeks = calendarWeeks
    }

    public override var hourCycle: HourCycle? by updatingProperty {
        refresh()
    }

    @Composable
    public override fun hourCycle(hourCycle: HourCycle?): Unit = composableProperty("hourCycle", {
        this.hourCycle = null
    }) {
        this.hourCycle = hourCycle
    }

    public override var keyboardNavigation: Boolean by updatingProperty(true) {
        refresh()
    }

    @Composable
    public override fun keyboardNavigation(keyboardNavigation: Boolean): Unit = composableProperty("keyboardNavigation", {
        this.keyboardNavigation = true
    }) {
        this.keyboardNavigation = keyboardNavigation
    }

    /**
     * The refresh callback used by the theme change event handler.
     */
    protected var refreshCallback: (Event) -> Unit = {
        refresh()
    }

    override fun onInsert() {
        initializeTempusDominus()
        if (renderConfig.isDom) {
            document.addEventListener(EventType<Event>("kilua.theme.changed"), refreshCallback)
        }
    }

    override fun onRemove() {
        tempusDominusInstance?.dispose()
        tempusDominusInstance = null
        if (renderConfig.isDom) {
            document.removeEventListener(EventType<Event>("kilua.theme.changed"), refreshCallback)
        }
    }

    /**
     * Re-creates the Tempus Dominus instance.
     */
    protected fun refresh() {
        if (tempusDominusInstance != null) {
            tempusDominusInstance?.dispose()
            initializeTempusDominus()
        }
    }

    /**
     * The Tempus Dominus instance.
     */
    public override var tempusDominusInstance: TempusDominus? = null

    internal var inputText: Text? = null

    protected abstract fun initializeTempusDominus()

    /**
     * Initializes the Tempus Dominus instance.
     */
    @NoLiveLiterals
    protected fun initializeTempusDominus(defaultValue: Date?, calendarView: Boolean, clockView: Boolean) {
        if (renderConfig.isDom) {
            val secondsView = format.contains("ss")
            val language = locale.language
            val locale = tempusDominusLocales[language]?.localization ?: obj()
            locale.jsSet("locale", language.toJsString())
            locale.jsSet("format", inputFormat.toJsString())
            if (monthHeaderFormat != null || yearHeaderFormat != null) {
                locale.jsSet(
                    "dayViewHeaderFormat", jsObjectOf(
                        "month" to if (monthHeaderFormat != null) monthHeaderFormat!!.value else "long",
                        "year" to if (yearHeaderFormat != null) yearHeaderFormat!!.value else "2-digit"
                    )
                )
            }
            if (hourCycle != null) {
                locale.jsSet("hourCycle", hourCycle!!.value.toJsString())
            }
            val initialViewMode = viewMode ?: if (calendarView) ViewMode.Calendar else ViewMode.Clock
            val currentTheme = if (theme == null || theme == Theme.Auto) {
                document.documentElement.getAttribute("data-bs-theme")?.let { theme ->
                    Theme.entries.find { theme == it.value }
                }
            } else {
                theme
            }
            val component = this
            val tempusDominusOptions = obj<TempusDominusOptions> {
                this.useCurrent = component.inline
                if (defaultValue != null) {
                    this.defaultDate = defaultValue
                }
                this.stepping = component.stepping
                this.allowInputToggle = component.allowInputToggle
                if (component.viewDate != null) {
                    this.viewDate = component.viewDate!!.toDate()
                } else {
                    if (inline && defaultValue != null) this.viewDate = defaultValue
                }
                this.promptTimeOnDateChange = component.promptTimeOnDateChange
                if (component.promptTimeOnDateChangeTransitionDelay != null)
                    this.promptTimeOnDateChangeTransitionDelay =
                        component.promptTimeOnDateChangeTransitionDelay!!.inWholeMilliseconds.toInt()
                this.keepInvalid = component.keepInvalid
                if (component.container != null) {
                    this.container = component.container!!
                }
                if (component.meta != null) {
                    this.meta = component.meta!!
                }
                this.restrictions = obj {
                    if (component.minDate != null) this.minDate = component.minDate!!.toDate()
                    if (component.maxDate != null) this.maxDate = component.maxDate!!.toDate()
                    if (!component.enabledDates.isNullOrEmpty()) this.enabledDates =
                        component.enabledDates!!.map { it.toDate() }.toJsArray()
                    if (!component.disabledDates.isNullOrEmpty()) this.disabledDates =
                        component.disabledDates!!.map { it.toDate() }.toJsArray()
                    if (!component.daysOfWeekDisabled.isNullOrEmpty()) this.daysOfWeekDisabled =
                        component.daysOfWeekDisabled!!.map { it.toJsInt() }.toJsArray()
                    if (!component.enabledHours.isNullOrEmpty()) this.enabledHours =
                        component.enabledHours!!.map { it.toJsInt() }.toJsArray()
                    if (!component.disabledHours.isNullOrEmpty()) this.disabledHours =
                        component.disabledHours!!.map { it.toJsInt() }.toJsArray()
                }
                this.display = obj {
                    if (component.customIcons != null) {
                        this.icons = obj {
                            component.customIcons!!.type?.let { this.type = it }
                            component.customIcons!!.time?.let { this.time = it }
                            component.customIcons!!.date?.let { this.date = it }
                            component.customIcons!!.up?.let { this.up = it }
                            component.customIcons!!.down?.let { this.down = it }
                            component.customIcons!!.previous?.let { this.previous = it }
                            component.customIcons!!.next?.let { this.next = it }
                            component.customIcons!!.today?.let { this.today = it }
                            component.customIcons!!.clear?.let { this.clear = it }
                            component.customIcons!!.close?.let { this.close = it }
                        }
                    }
                    this.sideBySide = component.sideBySide
                    this.calendarWeeks = component.calendarWeeks
                    this.viewMode = initialViewMode.value
                    component.toolbarPlacement?.let { this.toolbarPlacement = it.value }
                    this.keepOpen = component.keepOpen
                    this.buttons = obj {
                        this.clear = component.showClear
                        this.close = component.showClose
                        this.today = component.showToday
                    }
                    this.components = obj {
                        this.calendar = calendarView
                        this.clock = clockView
                        this.seconds = secondsView
                    }
                    this.inline = component.inline
                    currentTheme?.let { this.theme = it.value }
                    this.keyboardNavigation = component.keyboardNavigation
                }
                this.localization = locale
            }
            tempusDominusInstance = TempusDominus(element, tempusDominusOptions)
            if (disabled == true) {
                tempusDominusInstance?.disable()
            }
        }
    }

    /**
     * Shows the popup.
     */
    public fun showPopup() {
        tempusDominusInstance?.show()
    }

    /**
     * Hides the popup.
     */
    public fun hidePopup() {
        tempusDominusInstance?.hide()
    }

    /**
     * Toggles the popup.
     */
    public fun togglePopup() {
        tempusDominusInstance?.toggle()
    }

    override fun focus() {
        inputText?.focus()
    }

    override fun blur() {
        inputText?.blur()
    }

    public companion object {

        /**
         * Tries to guess the hour cycle for given language.
         */
        private fun guessHourCycle(language: String): HourCycle? {
            val template = jsObjectOf("hour" to "numeric")
            val hourCycle = DateTimeFormat(language, template.unsafeCast()).resolvedOptions().hourCycle.toString()
            return HourCycle.entries.find { it.name.lowercase() == hourCycle }
        }
    }
}

/**
 * Internal helper function.
 */
@Composable
internal fun IDiv.commonRichDateTime(
    bindId: String,
    name: String?,
    placeholder: String?,
    disabled: Boolean?,
    required: Boolean?,
    id: String?,
    inline: Boolean,
    icon: String,
    inputClassName: String?,
    onBlurCallback: () -> Unit,
): Text {
    attribute("data-td-target-input", "nearest")
    attribute("data-td-target-toggle", "nearest")
    val text = textRef(
        name = name,
        placeholder = placeholder,
        disabled = disabled,
        required = required,
        id = id,
        className = "form-control" % inputClassName
    ) {
        visible(!inline)
        attribute("data-td-target", "#$bindId")
        onChange {
            it.stopPropagation()
        }
        onBlur {
            onBlurCallback()
        }
    }
    span("input-group-text form-control") {
        maxWidth(43.px)
        visible(!inline)
        attribute("data-td-target", "#$bindId")
        attribute("data-td-toggle", "datetimepicker")
        tabindex(0)
        i(icon) {}
    }
    return text
}
