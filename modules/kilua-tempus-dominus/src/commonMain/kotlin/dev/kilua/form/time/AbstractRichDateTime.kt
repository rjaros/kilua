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
import dev.kilua.externals.Date
import dev.kilua.externals.Intl
import dev.kilua.externals.TempusDominus
import dev.kilua.externals.TempusDominusOptions
import dev.kilua.externals.obj
import dev.kilua.externals.set
import dev.kilua.externals.tempusDominusLocales
import dev.kilua.externals.toDate
import dev.kilua.form.text.text
import dev.kilua.html.Div
import dev.kilua.html.IDiv
import dev.kilua.html.i
import dev.kilua.html.span
import dev.kilua.i18n.Locale
import dev.kilua.i18n.LocaleManager
import dev.kilua.utils.jsObjectOf
import dev.kilua.utils.rem
import dev.kilua.utils.toJsArray
import dev.kilua.utils.toKebabCase
import kotlinx.datetime.LocalDate
import web.document
import web.dom.events.Event
import web.toJsNumber
import web.toJsString
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

    /**
     * The date/time format.
     */
    public override var format: String by updatingProperty(format) {
        refresh()
    }

    /**
     * Set the date/time format.
     */
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
            val hourCycle = guessHourCycle(locale.language)
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

    /**
     * The locale for i18n.
     */
    public override var locale: Locale by updatingProperty(locale) {
        refresh()
    }

    /**
     * Set the locale for i18n.
     */
    @Composable
    public override fun locale(locale: Locale): Unit = composableProperty("locale", {
        this.locale = LocaleManager.currentLocale
    }) {
        this.locale = locale
    }

    /**
     * Determines if the component is disabled.
     */
    public override var disabled: Boolean? by updatingProperty(disabled) {
        if (it == true) {
            tempusDominusInstance?.disable()
        } else {
            tempusDominusInstance?.enable()
        }
    }

    /**
     * Set the component disabled state.
     */
    @Composable
    public override fun disabled(disabled: Boolean?): Unit = composableProperty("disabled", {
        this.disabled = null
    }) {
        this.disabled = disabled
    }

    /**
     * Determines if the component is required.
     */
    public override var required: Boolean? by updatingProperty(required)

    /**
     * Set the component required state.
     */
    @Composable
    public override fun required(required: Boolean?): Unit = composableProperty("required", {
        this.required = null
    }) {
        this.required = required
    }

    /**
     * The component name.
     */
    public override var name: String? by updatingProperty()

    /**
     * Set the component name.
     */
    @Composable
    public override fun name(name: String?): Unit = composableProperty("name", {
        this.name = null
    }) {
        this.name = name
    }

    /**
     * The component custom validity message.
     */
    public open var customValidity: String? by updatingProperty()

    /**
     * Days of the week that should be disabled.
     */
    public override var daysOfWeekDisabled: List<Int>? by updatingProperty {
        refresh()
    }

    /**
     * Set the days of the week that should be disabled.
     */
    @Composable
    public override fun daysOfWeekDisabled(daysOfWeekDisabled: List<Int>?): Unit =
        composableProperty("daysOfWeekDisabled", {
            this.daysOfWeekDisabled = null
        }) {
            this.daysOfWeekDisabled = daysOfWeekDisabled
        }

    /**
     * Determines if *Clear* button should be visible.
     */
    public override var showClear: Boolean by updatingProperty(true) {
        refresh()
    }

    /**
     * Set if *Clear* button should be visible.
     */
    @Composable
    public override fun showClear(showClear: Boolean): Unit = composableProperty("showClear", {
        this.showClear = true
    }) {
        this.showClear = showClear
    }

    /**
     * Determines if *Close* button should be visible.
     */
    public override var showClose: Boolean by updatingProperty(true) {
        refresh()
    }

    /**
     * Set if *Close* button should be visible.
     */
    @Composable
    public override fun showClose(showClose: Boolean): Unit = composableProperty("showClose", {
        this.showClose = true
    }) {
        this.showClose = showClose
    }

    /**
     * Determines if *Today* button should be visible.
     */
    public override var showToday: Boolean by updatingProperty(true) {
        refresh()
    }

    /**
     * Set if *Today* button should be visible.
     */
    @Composable
    public override fun showToday(showToday: Boolean): Unit = composableProperty("showToday", {
        this.showToday = true
    }) {
        this.showToday = showToday
    }

    /**
     * The increment used to build the hour view.
     */
    public override var stepping: Int by updatingProperty(1) {
        refresh()
    }

    /**
     * Set the increment used to build the hour view.
     */
    @Composable
    public override fun stepping(stepping: Int): Unit = composableProperty("stepping", {
        this.stepping = 1
    }) {
        this.stepping = stepping
    }

    /**
     * Prevents date selection before this date.
     */
    public override var minDate: LocalDate? by updatingProperty {
        refresh()
    }

    /**
     * Set the minimum date.
     */
    @Composable
    public override fun minDate(minDate: LocalDate?): Unit = composableProperty("minDate", {
        this.minDate = null
    }) {
        this.minDate = minDate
    }

    /**
     * Prevents date selection after this date.
     */
    public override var maxDate: LocalDate? by updatingProperty {
        refresh()
    }

    /**
     * Set the maximum date.
     */
    @Composable
    public override fun maxDate(maxDate: LocalDate?): Unit = composableProperty("maxDate", {
        this.maxDate = null
    }) {
        this.maxDate = maxDate
    }

    /**
     * Shows date and time pickers side by side.
     */
    public override var sideBySide: Boolean by updatingProperty(false) {
        refresh()
    }

    /**
     * Set if date and time pickers should be shown side by side.
     */
    @Composable
    public override fun sideBySide(sideBySide: Boolean): Unit = composableProperty("sideBySide", {
        this.sideBySide = false
    }) {
        this.sideBySide = sideBySide
    }

    /**
     * A list of enabled dates.
     */
    public override var enabledDates: List<LocalDate>? by updatingProperty {
        refresh()
    }

    /**
     * Set the list of enabled dates.
     */
    @Composable
    public override fun enabledDates(enabledDates: List<LocalDate>?): Unit = composableProperty("enabledDates", {
        this.enabledDates = null
    }) {
        this.enabledDates = enabledDates
    }

    /**
     * A list of disabled dates.
     */
    public override var disabledDates: List<LocalDate>? by updatingProperty {
        refresh()
    }

    /**
     * Set the list of disabled dates.
     */
    @Composable
    public override fun disabledDates(disabledDates: List<LocalDate>?): Unit = composableProperty("disabledDates", {
        this.disabledDates = null
    }) {
        this.disabledDates = disabledDates
    }

    /**
     * Keep the popup open after selecting a date.
     */
    public override var keepOpen: Boolean by updatingProperty(false) {
        refresh()
    }

    /**
     * Set if the popup should be kept open after selecting a date.
     */
    @Composable
    public override fun keepOpen(keepOpen: Boolean): Unit = composableProperty("keepOpen", {
        this.keepOpen = false
    }) {
        this.keepOpen = keepOpen
    }

    /**
     * Date/time chooser color theme.
     */
    public override var theme: Theme? by updatingProperty {
        refresh()
    }

    /**
     * Set the date/time chooser color theme.
     */
    @Composable
    public override fun theme(theme: Theme?): Unit = composableProperty("theme", {
        this.theme = null
    }) {
        this.theme = theme
    }

    /**
     * Automatically open the chooser popup.
     */
    public override var allowInputToggle: Boolean by updatingProperty(true) {
        refresh()
    }

    /**
     * Set if the chooser popup should be automatically opened.
     */
    @Composable
    public override fun allowInputToggle(allowInputToggle: Boolean): Unit = composableProperty("allowInputToggle", {
        this.allowInputToggle = true
    }) {
        this.allowInputToggle = allowInputToggle
    }

    /**
     * The view date of the date/time chooser.
     */
    public override var viewDate: LocalDate? by updatingProperty {
        refresh()
    }

    /**
     * Set the view date of the date/time chooser.
     */
    @Composable
    public override fun viewDate(viewDate: LocalDate?): Unit = composableProperty("viewDate", {
        this.viewDate = null
    }) {
        this.viewDate = viewDate
    }

    /**
     * Automatically open time component after date is selected.
     */
    public override var promptTimeOnDateChange: Boolean by updatingProperty(false) {
        refresh()
    }

    /**
     * Set if time component should be automatically opened after date is selected.
     */
    @Composable
    public override fun promptTimeOnDateChange(promptTimeOnDateChange: Boolean): Unit =
        composableProperty("promptTimeOnDateChange", {
            this.promptTimeOnDateChange = false
        }) {
            this.promptTimeOnDateChange = promptTimeOnDateChange
        }

    /**
     * The delay for the time component opening after date is selected.
     */
    public override var promptTimeOnDateChangeTransitionDelay: Duration? by updatingProperty {
        refresh()
    }

    /**
     * Set the delay for the time component opening after date is selected.
     */
    @Composable
    public override fun promptTimeOnDateChangeTransitionDelay(promptTimeOnDateChangeTransitionDelay: Duration?): Unit =
        composableProperty("promptTimeOnDateChangeTransitionDelay", {
            this.promptTimeOnDateChangeTransitionDelay = null
        }) {
            this.promptTimeOnDateChangeTransitionDelay = promptTimeOnDateChangeTransitionDelay
        }

    /**
     * Default view mode of the date/time chooser.
     */
    public override var viewMode: ViewMode? by updatingProperty {
        refresh()
    }

    /**
     * Set the default view mode of the date/time chooser.
     */
    @Composable
    public override fun viewMode(viewMode: ViewMode?): Unit = composableProperty("viewMode", {
        this.viewMode = null
    }) {
        this.viewMode = viewMode
    }

    /**
     * Date/time chooser toolbar placement.
     */
    public override var toolbarPlacement: ToolbarPlacement? by updatingProperty {
        refresh()
    }

    /**
     * Set the date/time chooser toolbar placement.
     */
    @Composable
    public override fun toolbarPlacement(toolbarPlacement: ToolbarPlacement?): Unit =
        composableProperty("toolbarPlacement", {
            this.toolbarPlacement = null
        }) {
            this.toolbarPlacement = toolbarPlacement
        }

    /**
     * Date/time chooser month header format.
     */
    public override var monthHeaderFormat: MonthHeaderFormat? by updatingProperty {
        refresh()
    }

    /**
     * Set the date/time chooser month header format.
     */
    @Composable
    public override fun monthHeaderFormat(monthHeaderFormat: MonthHeaderFormat?): Unit =
        composableProperty("monthHeaderFormat", {
            this.monthHeaderFormat = null
        }) {
            this.monthHeaderFormat = monthHeaderFormat
        }

    /**
     * Date/time chooser year header format.
     */
    public override var yearHeaderFormat: YearHeaderFormat? by updatingProperty {
        refresh()
    }

    /**
     * Set the date/time chooser year header format.
     */
    @Composable
    public override fun yearHeaderFormat(yearHeaderFormat: YearHeaderFormat?): Unit =
        composableProperty("yearHeaderFormat", {
            this.yearHeaderFormat = null
        }) {
            this.yearHeaderFormat = yearHeaderFormat
        }

    /**
     * Date/time chooser custom icons.
     */
    public override var customIcons: DateTimeIcons? by updatingProperty {
        refresh()
    }

    /**
     * Set the date/time chooser custom icons.
     */
    @Composable
    public override fun customIcons(customIcons: DateTimeIcons?): Unit =
        composableProperty("customIcons", {
            this.customIcons = null
        }) {
            this.customIcons = customIcons
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
            document.addEventListener("kilua.theme.changed", refreshCallback)
        }
    }

    override fun onRemove() {
        tempusDominusInstance?.dispose()
        tempusDominusInstance = null
        if (renderConfig.isDom) {
            document.removeEventListener("kilua.theme.changed", refreshCallback)
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
            locale["locale"] = language.toJsString()
            locale["format"] = inputFormat.toJsString()
            if (monthHeaderFormat != null || yearHeaderFormat != null) {
                locale["dayViewHeaderFormat"] = jsObjectOf(
                    "month" to if (monthHeaderFormat != null) monthHeaderFormat!!.value else "long",
                    "year" to if (yearHeaderFormat != null) yearHeaderFormat!!.value else "2-digit"
                )
            }
            val initialViewMode = viewMode ?: if (calendarView) ViewMode.Calendar else ViewMode.Clock
            val currentTheme = if (theme == null || theme == Theme.Auto) {
                document.documentElement?.getAttribute("data-bs-theme")?.let { theme ->
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
                this.restrictions = obj {
                    if (component.minDate != null) this.minDate = component.minDate!!.toDate()
                    if (component.maxDate != null) this.maxDate = component.maxDate!!.toDate()
                    if (!component.enabledDates.isNullOrEmpty()) this.enabledDates =
                        component.enabledDates!!.map { it.toDate() }.toJsArray()
                    if (!component.disabledDates.isNullOrEmpty()) this.disabledDates =
                        component.disabledDates!!.map { it.toDate() }.toJsArray()
                    if (!component.daysOfWeekDisabled.isNullOrEmpty()) this.daysOfWeekDisabled =
                        component.daysOfWeekDisabled!!.map { it.toJsNumber() }.toJsArray()
                }
                this.display = obj {
                    this.viewMode = initialViewMode.value
                    component.toolbarPlacement?.let { this.toolbarPlacement = it.value }
                    this.sideBySide = component.sideBySide
                    this.buttons = obj {
                        this.clear = component.showClear
                        this.close = component.showClose
                        this.today = component.showToday
                    }
                    if (component.customIcons != null) {
                        this.icons = obj {
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
                    this.inline = component.inline
                    this.keepOpen = component.keepOpen
                    currentTheme?.let { this.theme = it.value }
                    this.components = obj {
                        this.calendar = calendarView
                        this.clock = clockView
                        this.seconds = secondsView
                    }
                }
                this.localization = locale
            }
            tempusDominusInstance = TempusDominus(element, tempusDominusOptions)
            if (disabled == true) {
                tempusDominusInstance?.disable()
            }
        }
    }

    public companion object {

        /**
         * Time format hour cycle.
         */
        private enum class HourCycle {
            H11, H12, H23, H24
        }

        /**
         * Tries to guess the hour cycle for given language.
         */
        private fun guessHourCycle(language: String): HourCycle? {
            val template = jsObjectOf("hour" to "numeric")
            val hourCycle = Intl.DateTimeFormat(language, template).resolvedOptions().hourCycle
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
) {
    attribute("data-td-target-input", "nearest")
    attribute("data-td-target-toggle", "nearest")
    text(
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
    }
    span("input-group-text") {
        visible(!inline)
        attribute("data-td-target", "#$bindId")
        attribute("data-td-toggle", "datetimepicker")
        i(icon) {}
    }
}
