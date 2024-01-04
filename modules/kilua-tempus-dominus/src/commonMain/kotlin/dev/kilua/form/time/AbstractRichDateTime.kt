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
import dev.kilua.core.DefaultRenderConfig
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
import dev.kilua.html.i
import dev.kilua.html.span
import dev.kilua.i18n.DefaultLocale
import dev.kilua.i18n.Locale
import dev.kilua.utils.isDom
import dev.kilua.utils.toJsArray
import dev.kilua.utils.toKebabCase
import kotlinx.datetime.LocalDate
import web.JsAny
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
    format: String,
    inline: Boolean = false,
    locale: Locale = DefaultLocale(),
    className: String? = null,
    renderConfig: RenderConfig = DefaultRenderConfig(),
) : Div(className, renderConfig) {

    /**
     * The date/time format.
     */
    public open var format: String by updatingProperty(format, skipUpdate = skipUpdate) {
        refresh()
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
    internal open var inline: Boolean by updatingProperty(inline, skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * The locale for i18n.
     */
    public open var locale: Locale by updatingProperty(locale, skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Determines if the component is disabled.
     */
    public open var disabled: Boolean? by updatingProperty(disabled, skipUpdate = skipUpdate) {
        if (it == true) {
            tempusDominusInstance?.disable()
        } else {
            tempusDominusInstance?.enable()
        }
    }

    /**
     * Determines if the component is required.
     */
    public open var required: Boolean? by updatingProperty(skipUpdate = skipUpdate)

    /**
     * The component name.
     */
    public open var name: String? by updatingProperty(skipUpdate = skipUpdate)

    /**
     * The component custom validity message.
     */
    public open var customValidity: String? by updatingProperty(skipUpdate = skipUpdate)

    /**
     * Days of the week that should be disabled.
     */
    public open var daysOfWeekDisabled: List<Int>? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Determines if *Clear* button should be visible.
     */
    public open var showClear: Boolean by updatingProperty(true, skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Determines if *Close* button should be visible.
     */
    public open var showClose: Boolean by updatingProperty(true, skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Determines if *Today* button should be visible.
     */
    public open var showToday: Boolean by updatingProperty(true, skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * The increment used to build the hour view.
     */
    public open var stepping: Int by updatingProperty(1, skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Prevents date selection before this date.
     */
    public open var minDate: LocalDate? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Prevents date selection after this date.
     */
    public open var maxDate: LocalDate? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Shows date and time pickers side by side.
     */
    public open var sideBySide: Boolean by updatingProperty(false, skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * A list of enabled dates.
     */
    public open var enabledDates: List<LocalDate>? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * A list of disabled dates.
     */
    public open var disabledDates: List<LocalDate>? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Keep the popup open after selecting a date.
     */
    public open var keepOpen: Boolean by updatingProperty(false, skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Date/time chooser color theme.
     */
    public open var theme: Theme? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Automatically open the chooser popup.
     */
    public var allowInputToggle: Boolean by updatingProperty(true, skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * The view date of the date/time chooser.
     */
    public open var viewDate: LocalDate? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Automatically open time component after date is selected.
     */
    public open var promptTimeOnDateChange: Boolean by updatingProperty(false, skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * The delay for the time component opening after date is selected.
     */
    public open var promptTimeOnDateChangeTransitionDelay: Duration? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Default view mode of the date/time chooser.
     */
    public open var viewMode: ViewMode? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Date/time chooser toolbar placement.
     */
    public open var toolbarPlacement: ToolbarPlacement? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Date/time chooser month header format.
     */
    public open var monthHeaderFormat: MonthHeaderFormat? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * Date/time chooser year header format.
     */
    public open var yearHeaderFormat: YearHeaderFormat? by updatingProperty(skipUpdate = skipUpdate) {
        refresh()
    }

    /**
     * The refresh callback used by the theme change event handler.
     */
    protected var refreshCallback: (Event) -> Unit = {
        refresh()
    }

    override fun onInsert() {
        initializeTempusDominus()
        if (isDom) {
            document.addEventListener("kilua.theme.changed", refreshCallback)
        }
    }

    override fun onRemove() {
        tempusDominusInstance?.dispose()
        tempusDominusInstance = null
        if (isDom) {
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
    protected var tempusDominusInstance: TempusDominus? = null

    protected abstract fun initializeTempusDominus()

    /**
     * Initializes the Tempus Dominus instance.
     */
    @NoLiveLiterals
    protected fun initializeTempusDominus(defaultValue: Date?, calendarView: Boolean, clockView: Boolean) {
        if (renderConfig.isDom) {
            val secondsView = format.contains("ss")
            val language = locale.language
            val locale = tempusDominusLocales[language]?.localization ?: obj {}
            locale["locale"] = language.toJsString()
            locale["format"] = inputFormat.toJsString()
            if (monthHeaderFormat != null || yearHeaderFormat != null) {
                locale["dayViewHeaderFormat"] = obj<JsAny> {}.apply {
                    this["month"] = (if (monthHeaderFormat != null) monthHeaderFormat!!.value else "long").toJsString()
                    this["year"] = (if (yearHeaderFormat != null) yearHeaderFormat!!.value else "2-digit").toJsString()
                }
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
            val template = obj().apply {
                set("hour", "numeric".toJsString())
            }
            val hourCycle = Intl.DateTimeFormat(language, template).resolvedOptions().hourCycle
            return HourCycle.entries.find { it.name.lowercase() == hourCycle }
        }
    }
}

/**
 * Internal helper function.
 */
@Composable
internal fun Div.commonRichDateTime(
    bindId: String,
    name: String?,
    placeholder: String?,
    disabled: Boolean?,
    required: Boolean?,
    id: String?,
    inline: Boolean,
    icon: String
) {
    this.id = bindId
    setAttribute("data-td-target-input", "nearest")
    setAttribute("data-td-target-toggle", "nearest")
    text(
        name = name,
        placeholder = placeholder,
        disabled = disabled,
        required = required,
        id = id,
        className = "form-control"
    ) {
        visible = !inline
        setAttribute("data-td-target", "#$bindId")
        onChange {
            it.stopPropagation()
        }
    }
    span("input-group-text") {
        visible = !inline
        setAttribute("data-td-target", "#$bindId")
        setAttribute("data-td-toggle", "datetimepicker")
        i(icon) {}
    }
}
